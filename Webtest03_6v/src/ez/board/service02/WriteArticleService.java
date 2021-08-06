package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import ez.board.dao.ArticleDao;
import ez.model.Article;
import ez.model.WritingRequest;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class WriteArticleService {
	private static WriteArticleService instance = new WriteArticleService();
	public static WriteArticleService getInstance() {
		return instance;
	}
	private WriteArticleService() {}
	
	public Article write(WritingRequest writingRequest)throws IdGenerationFailedException{
		//글 입력을 위한 셋팅
		int groupId = IdGenerator.getInstance().generateNextId("article");/*ID_SEQUENCE테이블 자료
		select next_value from id_sequence where sequence_name = ? for update;
		DB에서 sequence_name컬럼 'article'의 값 예.27
		따라서 IdGenerator를 통해 그룹번호를 가지고온 다음 int타입 groupId에 저장한다.*/
		Article article = writingRequest.toArticle();/*toArticle()에는 작성자, 비밀번호, 제목, 내용의 데이터가 담겨져있다.*/
		//System.out.println("Service"+article.getContent());
		article.setGroupId(groupId);//그룹번호를 지정해준다.
		//System.out.println(groupId);
		article.setPostingDate(new Date());//포스팅 날짜를 정해준다.
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");//DecimalFormat의 형태를 0으로 10자리 주고
		article.setSequenceNumber(decimalFormat.format(groupId)+"999999");/* 그룹번호로 저장한 groupId의 값을 "0000000000"형식으로 맞추어
		'0000000027'와 같이 그룹번호를 지정하고 댓글 분류를 위한 "999999"를 결합한다. 총 16자리 숫자가 sequenceNumber(0000000027999999)가 된다. */
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();//DB연동
			conn.setAutoCommit(false);//트랜잭션 시작점
			
			int articleId = ArticleDao.getInstance().insert(conn, article);//articleId에 insert리턴값인 글번호 담기.
			if(articleId == -1) {//insert(conn, article)에서 글 삽입이 되지 않으면, 리턴값 -1을 갖고온다. 그때의 조건문
				JdbcUtil.rollback(conn);//DB롤백
				throw new RuntimeException("DB삽입안됨: " + articleId);
			}
			conn.commit();//글 삽입이 되면 커밋!
			
			article.setId(articleId);
			//System.out.println("WriteArticleService commit 이후: " + article.getContent());  
			//System.out.println("postedArticle.id: " + article.getId());
			return article;
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB에러: " + e.getMessage(), e);
		}finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
				}catch(SQLException e) {}
			}
			JdbcUtil.close(conn);
		}
	}//public class WriteArticleService
}
