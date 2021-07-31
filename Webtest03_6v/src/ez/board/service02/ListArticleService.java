package ez.board.service02;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ez.board.dao.ArticleDao;
import ez.model.Article;
import ez.model.ArticleListModel;
import ez.loader.JdbcUtil;
import ez.jdbc.connection.ConnectionProvider;

public class ListArticleService {
	private static ListArticleService instance = new ListArticleService();
	public static ListArticleService getInstance() {
		return instance;
	}
	
	public static final int COUNT_PER_PAGE=10;
	
	//글 목록을 불러오는 메소드
	public ArticleListModel getArticleList(int requestPageNumber) {
		if(requestPageNumber < 0) {/*model패키지, 
		ArticleListModel 타입으로 리턴을 받는다.*/
			throw new IllegalArgumentException("page number < 0: " + requestPageNumber);
		}
		ArticleDao articleDao = ArticleDao.getInstance();
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int totalArticleCount = articleDao.selectCount(conn);
			
			if(totalArticleCount == 0) {
				return new ArticleListModel();
			}
			
			int totalPageCount = calculateTotalPageCount(totalArticleCount);
			
			int firstRow = (requestPageNumber -1)*COUNT_PER_PAGE +1;
			int endRow = firstRow + COUNT_PER_PAGE -1;
			
			if(endRow > totalArticleCount) {
				endRow = totalArticleCount;
			}
			
			List<Article> articleList = articleDao.select(conn, firstRow, endRow);
			
			ArticleListModel articleListView = new ArticleListModel(articleList, requestPageNumber, totalPageCount, firstRow, endRow);
			return articleListView;
		}catch(SQLException e){
			throw new RuntimeException("DB에러 발생: "+e.getMessage(), e);
		}finally {
			JdbcUtil.close(conn);
		}
	}//getArticleList
	
	//페이지 수가 총 몇인지 계산하는 메소드
	private int calculateTotalPageCount(int totalArticleCount) {
		if(totalArticleCount==0) {//게시글이 없으면, 0을 리턴한다.
			return 0;
		}
		int pageCount = totalArticleCount/COUNT_PER_PAGE;
		if(totalArticleCount%COUNT_PER_PAGE > 0) {
			pageCount++;
		}
		return pageCount;/*한페이지당 게시글 10개를 설정했을때(COUNT_PER_PAGE,
		불러온 전체 글 수(totalArticleCount)를 10으로 
		나눈다. 연산된 결과 리턴*/
	}
}
