package ez.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ez.model.Article;
import ez.loader.JdbcUtil;

public class ArticleDao {

	private static ArticleDao instance = new ArticleDao();
	public static ArticleDao getInstance() {
		return instance;
	}

	private ArticleDao() {
	}

	//글 갯수 리턴
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}

	public List<Article> select(Connection conn, int firstRow, int endRow)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select article_id, group_id, sequence_no, posting_date, read_count, writer_name, password, title from ( "
					+ "    select rownum rnum, article_id, group_id, sequence_no, posting_date, read_count, writer_name, password, title from ( "
					+ "        select * from article m order by m.sequence_no desc "
					+ "    ) where rownum <= ? "
					+ ") where rnum >= ?");//46~50행의 쿼리문을 
			/*
			 * select * from article m order by m.sequence_no desc; 시퀀스번호(그룹번호+댓글분류번호, 16자리의 숫자)를 내림차순으로 정리한다.
			 * 즉, 큰 값부터 작은 값으로 쭉 나열하기때문에 최근 입력된 글이 먼저 나타난다.
			 * 다음 쿼리문의 핵심, select rownum rnum과 where rownum <= ? 이다. endRow를 기준으로 30개의 article내용을 갖고온다.
			 * 쿼리문에서 endRow=30,firstRow=21 이라면 (8월1일 20:47기준의 예시) 3페이지의 글들을 골라온다. */
			
			
			pstmt.setInt(1, endRow);//쿼리문에 담길 내용 1
			pstmt.setInt(2, firstRow);//쿼리문에 담길 내용 2
			rs = pstmt.executeQuery();//쿼리문의 결과를 담는다.
			if (!rs.next()) {//쿼리문의 결과가 없을 시에는 해당하는 데이터가 없는 것이다.
				return Collections.emptyList();//따라서, 비어있는 List를 리턴한다.
			}//int z = 1;
			List<Article> articleList = new ArrayList<Article>();//Article객체 타입의 List변수 articleList를 선언한다.
			do {
				Article article = makeArticleFromResultSet(rs, false);/*쿼리문 결과로 나온 데이터들을 makeArticleFromResultSet 메소드를 
				활용하여 Article타입 변수 article에 담는다. */
				articleList.add(article);//반복해서 article들을 articleList에 담는다.
				//System.out.println("글담기 테스트: " + z++);
				//System.out.println(article);
			} while (rs.next());
			//System.out.println("결과: " + articleList);
			//System.out.println("글담기 테스트 2 : " + z);
			return articleList;
			/*  글담기 테스트: 1
				글담기 테스트: 2
				글담기 테스트: 3
				글담기 테스트: 4
				글담기 테스트: 5
				글담기 테스트: 6
				글담기 테스트: 7
				글담기 테스트: 8
				글담기 테스트: 9
				글담기 테스트: 10
				결과: [ez.model.Article@700c0a90, ez.model.Article@803bf85, ez.model.Article@59f89575,
				 	 ez.model.Article@423e98e, ez.model.Article@146b2b10, ez.model.Article@181518f, 
				 	 ez.model.Article@1c4f2ff7, ez.model.Article@7bbda69, ez.model.Article@4a455225, ez.model.Article@41c37fcb]
				글담기 테스트 2 : 11*/
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	//PDF에서 161페이지
	//쿼리문 결과로부터 글 만들기(셋팅하기)
	private Article makeArticleFromResultSet(ResultSet rs, boolean readContent)
			throws SQLException {
		Article article = new Article();
		article.setId(rs.getInt("article_id"));
		article.setGroupId(rs.getInt("group_id"));
		article.setSequenceNumber(rs.getString("sequence_no"));
		article.setPostingDate(rs.getTimestamp("posting_date"));
		article.setReadCount(rs.getInt("read_count"));
		article.setWriterName(rs.getString("writer_name"));
		article.setPassword(rs.getString("password"));
		article.setTitle(rs.getString("title"));
		if (readContent) {
			article.setContent(rs.getString("content"));
		}
		return article;
	}//여기까지 list.jsp를 위한 기본 로직들

	public int insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("insert into article "//article 테이블에 삽입한다.
					+ "(article_id, group_id, sequence_no, posting_date, read_count, "
					+ "writer_name, password, title, content) "//해당되는 컬럼들에,
					+ "values (article_id_seq.NEXTVAL, ?, ?, ?, 0, ?, ?, ?, ?)");//괄호 안 값을 넣는다.
			pstmt.setInt(1, article.getGroupId());
			pstmt.setString(2, article.getSequenceNumber());
			pstmt.setTimestamp(3, new Timestamp(article.getPostingDate().getTime()));
			pstmt.setString(4, article.getWriterName());
			pstmt.setString(5, article.getPassword());
			pstmt.setString(6, article.getTitle());
			pstmt.setString(7, article.getContent());
			int insertedCount = pstmt.executeUpdate();//입력이 되면 1이 리턴된다. executeUpdate()는 리턴값이 int타입이다.
			//(1) DML(SQL Data Manipulation Language) 문의 행 개수 또는 (2) 아무것도 반환하지 않는 SQL 문의 경우 0
			//System.out.println("등록된 글 수: " + insertedCount);
			//System.out.println("ArticleDao: " + article.getContent());
			if (insertedCount > 0) {//pstmt.executeUpdate()가 실행되어 1이 입력이 되면,
				stmt = conn.createStatement();//아래에 쿼리문을 실행한다.
				rs = stmt.executeQuery("select article_id_seq.CURRVAL from dual");//방금 입력한 글의 시퀀스 값을 가지고 와라.
				if (rs.next()) {
					return rs.getInt(1);//삽입된 내용의 시퀀스 값이 있으면, 그 값을 리턴하고
				}
			}
			return -1;//위 내용이 제대로 되지 않았다면, -1을 리턴한다.
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}

	//ReadArticleService에서 사용하는 메소드
	public Article selectById(Connection conn, int articleId)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article where article_id = ?");
			pstmt.setInt(1, articleId);
			/*	예시, select * from article where article_id = 42 의 결과
			  42	22	0000000022989898	21/07/30	1	세번째	1	re: 세번째	세번째 */
			rs = pstmt.executeQuery();//쿼리문 결과로 나온 정보를 rs에 담는다.
			if (!rs.next()) {//articleId 값으로 쿼리문 실행한 결과가 없으면, 즉 글이 없으면
				return null;//null값을 리턴한다.
			}
			Article article = makeArticleFromResultSet(rs, true);//글이 있을 경우,
			//article.setId(rs.getInt("article_id")); 이런식으로 article에 글을 담아옴.
			//private Article makeArticleFromResultSet(ResultSet rs, boolean readContent), return article;
			//System.out.println(article);, 매번 다른 주소값이 출력됨
			return article;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	//조회수를 올리기위한 메소드
	public void increaseReadCount(Connection conn, int articleId)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update article set read_count = read_count + 1 where article_id = ?");
			pstmt.setInt(1, articleId);//이 쿼리문의 핵심 -> 해당되는 글번호의 read_count값에 1을 더해준다.
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}//public void increaseReadCount

	public String selectLastSequenceNumber(Connection conn,
			String searchMaxSeqNum, String searchMinSeqNum) 
	throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn
					.prepareStatement("select min(sequence_no) from article "
							+ "where sequence_no < ? and sequence_no >= ?");
			pstmt.setString(1, searchMaxSeqNum);
			pstmt.setString(2, searchMinSeqNum);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return rs.getString(1);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}//복습할 범위

	public int update(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update article "
					+ "set title = ?, content = ? where article_id = ?");
			pstmt.setString(1, article.getTitle());
			pstmt.setString(2, article.getContent());
			pstmt.setInt(3, article.getId());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	public void delete(Connection conn, int articleId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("delete from article "
					+ "where article_id = ?");
			pstmt.setInt(1, articleId);
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

}
