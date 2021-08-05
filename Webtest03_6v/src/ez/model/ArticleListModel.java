package ez.model;

import java.util.ArrayList;
import java.util.List;
//4.ArticleListModel : 리스트에 보여줄 데이터 저장하는 자바빈
public class ArticleListModel {
	private List<Article> articleList;
	private int requestPage;
	private int totalPageCount;
	private int startRow;
	private int endRow;
	
	public ArticleListModel() {//글이 아무것도 없을때, ListArticleService 34행 쯤 에서 사용
		this(new ArrayList<Article>(), 0, 0, 0, 0);
	}
	
	public ArticleListModel(List<Article> articleList, int requestPageNumber, int totalPageCount, int startRow, int endRow) {
		//등록된 글이 있을때 매개변수를 담아 사용한다. ListArticleService 49행 쯤 에서 사용
		this.articleList = articleList;
		this.requestPage = requestPageNumber;
		this.totalPageCount = totalPageCount;
		this.startRow = startRow;
		this.endRow = endRow;
	}
	
	public List<Article> getArticleList(){
		return articleList;
	}
	
	public boolean isHasArticle() {
		return !articleList.isEmpty();
	}
	
	public int getRequestPage() {
		return requestPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}
	
	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

}
