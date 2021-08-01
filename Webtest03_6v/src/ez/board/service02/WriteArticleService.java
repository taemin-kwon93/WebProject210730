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
		int groupId = IdGenerator.getInstance().generateNextId("article");/*DB에서 sequence_name컬럼 내용 'article'
		따라서 IdGenerator를 통해 그룹번호를 가지고온 다음 int타입 groupId에 저장한다.*/
		Article article = writingRequest.toArticle();/*toArticle()에는 작성자, 비밀번호, 제목, 내용의 데이터가 담겨져있다.*/
		
		article.setGroupId(groupId);//그룹번호를 지정해준다.
		article.setPostingDate(new Date());//포스팅 날짜를 정해준다.
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");//DecimalFormat의 형태를 0으로 10자리 주고
		article.setSequenceNumber(decimalFormat.format(groupId)+"999999");//그룹번호 999999를 더해서 총 16자리 숫자가 sequenceNumber가 된다. 
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			int articleId = ArticleDao.getInstance().insert(conn, article);
			if(articleId == -1) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException("DB삽입안됨: " + articleId);
			}
			conn.commit();
			
			article.setId(articleId);
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
