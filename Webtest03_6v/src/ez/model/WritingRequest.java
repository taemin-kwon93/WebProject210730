package ez.model;
//EL&JSTL 게시판작업

public class WritingRequest {
	private String writerName;
	private String password;
	private String title;
	private String content;
	
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	//WriteArticleService 클래스에서 ArticleDao에 전달할 Article객체를 생성할때 사용.
	public Article toArticle() {
		Article article = new Article();
		article.setWriterName(writerName);
		article.setPassword(password);
		article.setTitle(title);
		article.setContent(content);
		//System.out.println("WritingRequest: " + content); 
		//글 입력시 writingRequest 접근
		return article;
	}
	
}
