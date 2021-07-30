package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;

import ez.board.dao.ArticleDao;
import ez.model.Article;
import ez.model.UpdateRequest;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class UpdateArticleService {
	
	private static UpdateArticleService instance = new UpdateArticleService();
	public static UpdateArticleService getInstance() {
		return instance;
	}
	private UpdateArticleService() {}
	
	public Article update(UpdateRequest updateRequest)throws ArticleNotFoundException, InvalidPasswordException{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();//DB연동
			conn.setAutoCommit(false);//트랜잭션 시작점
			
			ArticleCheckHelper checkHelper = new ArticleCheckHelper();
			checkHelper.checkExistsAndPassword(conn, updateRequest.getArticleId(), updateRequest.getPassword());
			
			Article updatedArticle = new Article();//자바빈객체 updatedArticle을 만든다
			updatedArticle.setId(updateRequest.getArticleId());//수정한 내용을 끌어와서 담아주고 
			updatedArticle.setTitle(updateRequest.getTitle());//담아주고2
			updatedArticle.setContent(updateRequest.getContent());//담아주고3
			
			ArticleDao articleDao = ArticleDao.getInstance();
			int updateCount = articleDao.update(conn, updatedArticle);
			if(updateCount == 0) {
				throw new ArticleNotFoundException(
					"게시글이 존재하지 않습니다 : " + updateRequest.getArticleId());
			}
			Article article = articleDao.selectById(conn, updateRequest.getArticleId());
			
			conn.commit();
			
			return article;
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB에러 발생: " + e.getMessage(), e);
		}catch(ArticleNotFoundException e) {
			JdbcUtil.rollback(conn);
			throw e;
		}catch(InvalidPasswordException e) {
			JdbcUtil.rollback(conn);
			throw e;
		}finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
				}catch(SQLException e) {}
				JdbcUtil.rollback(conn);
			}
		}
	}
}
