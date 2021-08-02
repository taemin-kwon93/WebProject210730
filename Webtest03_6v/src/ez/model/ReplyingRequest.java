package ez.model;
//EL&JSTL 게시판작업

public class ReplyingRequest extends WritingRequest{
	private int parentArticleId;

	public int getParentArticleId() {
		return parentArticleId;
	}
	
	public void setParentArticleId(int parentArticleId) {
		this.parentArticleId = parentArticleId;
	}
	
}
