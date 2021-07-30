package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;

import ez.board.dao.ArticleDao;
import ez.model.Article;

public class ArticleCheckHelper {
	
	//수정할때 꼭 필요한 메소드다.
	public Article checkExistsAndPassword(Connection conn, int articleId, String password) throws
		SQLException, ArticleNotFoundException, InvalidPasswordException{
		
		ArticleDao articleDao = ArticleDao.getInstance();
		Article article = articleDao.selectById(conn, articleId);//글번호(articleId)에 해당하는 article(글)을 가지고온다.
		if(article == null) {
			throw new ArticleNotFoundException(
				"게시글이 존재하지 않습니다. : " +articleId);
		}
		if(!checkPassword(article.getPassword(), password)) {
			//입력한 비밀번호(password)와 DB에서 가지고온 비밀번호(article.getPassword())를 비교한다.
			throw new InvalidPasswordException("잘못된 암호");
		}
		return article;
	}//public Article checkExistsAndPassword(Connection conn, int articleId, String password)
	
	private boolean checkPassword(String realPassword, String userInputPassword) {
		//realPassword가 DB에서 갖고온 비밀번호, userInputPassword가 사용자가 기입한 비밀번호
		if(realPassword == null) {
			return false;
		}
		if(realPassword.length() == 0) {
			return false;
		}
		return realPassword.equals(userInputPassword);
	}
}
