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
		return selectArticle(articleId, true);//글을 읽을 때는 boolean increaseCount에 true를 줘서 조회수를 올린다.
	}

	private Article selectArticle(int articleId, boolean increaseCount)throws ArticleNotFoundException{
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			ArticleDao articleDao = ArticleDao.getInstance();
			Article article = articleDao.selectById(conn, articleId);//매개변수로 받아온 articleId로 selectById메소드를 실행하고
			//메소드의 결과로 받아온 article을 저장한다.
			
			if(article == null) {//article이 널이라면, 해당 아이디로 등록된 글은 없다.
				throw new ArticleNotFoundException(
						"게시글이 존재하지 않습니다.: " + articleId);
			}
			if(increaseCount) {//조회수를 올린다.
				articleDao.increaseReadCount(conn, articleId);
				article.setReadCount(article.getReadCount() +1 );
			}
			return article;//int articleId 맞춰 골라온 글을 리턴한다.
		}catch(SQLException e) {
			throw new RuntimeException("DB 에러 발생: " + e.getMessage(), e);
		}finally {
			JdbcUtil.close(conn);
		}
	}//private Article selectArticle(int articleId, boolean increaseCount)
	
	public Article getArticle(int articleId) throws ArticleNotFoundException{
		return selectArticle(articleId, false);//getArticle을 사용할때는 boolean increaseCount에 false를 줘서 조회수를 올리지 않는다.
	}
}
