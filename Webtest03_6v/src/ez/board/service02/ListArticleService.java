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
	
	//글 목록을 불러오는 메소드/*model패키지, ArticleListModel 타입으로 값을 받는다.*/
	public ArticleListModel getArticleList(int requestPageNumber) {//int타입 페이지 숫자를 매개변수로 받는다.
		if(requestPageNumber < 0) {//페이지 수가 0보다 작으면 예외처리를 한다.
			throw new IllegalArgumentException("page number < 0: " + requestPageNumber);//페이지 넘버가 0보다 작을때의 예외처리
		}
		ArticleDao articleDao = ArticleDao.getInstance();//articleDao 
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int totalArticleCount = articleDao.selectCount(conn);//글의 개수(select count(*)from article)
			//를 갖고와서 totalArticleCount에 저장한다.
			
			if(totalArticleCount == 0) {//등록된 글이 없을때의 경우
				return new ArticleListModel();
			}
			
			int totalPageCount = calculateTotalPageCount(totalArticleCount);
			
			int firstRow = (requestPageNumber -1)*COUNT_PER_PAGE +1;
			/*페이지 넘버가 2일때 계산 예시. (2-1)*10(상수count_per_page)+1 =11, 따라서 2페이지의 첫번째 행은 11  */
			int endRow = firstRow + COUNT_PER_PAGE -1;//위 주석에 따른 예시. 11+10-1 = 20, 따라서 마지막 행은 20
			
			if(endRow > totalArticleCount) {//글의 수 보다 계산된 endRow의 수가 클때, 마지막 번째의 글 수(예, 34번째)를 지정한다.
				endRow = totalArticleCount;
			}
			
			List<Article> articleList = articleDao.select(conn, firstRow, endRow);
			//<Article>타입의 List 'articleList'선언, 내용에는 articleDao.select메소드의 결과가 들어간다.
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
		/*예시, 글의 총 개수가 43개 일때,
		 * int타입 pageCount변수의 값은 43/(상수)10 이다.
		 * 따라서 pageCount에 저장될 값은 4이다.(%->나머지 3)*/
		if(totalArticleCount%COUNT_PER_PAGE > 0) {
			pageCount++;//나머지 값 3을 위한 페이지추가 연산작업을 한다.
		}
		return pageCount;/*한페이지당 게시글 10개를 설정했을때(COUNT_PER_PAGE,
		불러온 전체 글 수(totalArticleCount)를 10으로 
		나눈다. 연산된 결과 리턴*/
	}
}
