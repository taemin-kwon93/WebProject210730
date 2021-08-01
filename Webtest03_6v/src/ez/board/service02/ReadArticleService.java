package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;

import ez.board.dao.ArticleDao;
import ez.model.Article;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class ReadArticleService {
	private static ReadArticleService instance = new ReadArticleService();
	
	public static ReadArticleService getInstance() {
		return instance;
	}
	
	private ReadArticleService() {}
	
	public Article readArticle(int articleId) throws ArticleNotFoundException{
		return selectArticle(articleId, true);
	}

	private Article selectArticle(int articleId, boolean increaseCount)throws ArticleNotFoundException{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			ArticleDao articleDao = ArticleDao.getInstance();
			Article article = articleDao.selectById(conn, articleId);//
			if(article == null) {
				throw new ArticleNotFoundException(
						"�Խñ��� �������� �ʽ��ϴ�.: " + articleId);
			}
			if(increaseCount) {
				articleDao.increaseReadCount(conn, articleId);
				article.setReadCount(article.getReadCount() +1 );
			}
			return article;
		}catch(SQLException e) {
			throw new RuntimeException("DB ���� �߻�: " + e.getMessage(), e);
		}finally {
			JdbcUtil.close(conn);
		}
	}//private Article selectArticle(int articleId, boolean increaseCount)
	
	public Article getArticle(int articleId) throws ArticleNotFoundException{
		return selectArticle(articleId, false);
	}
}
