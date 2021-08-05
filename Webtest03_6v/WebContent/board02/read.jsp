<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.board.service02.ReadArticleService" %>
<%@ page import="ez.board.service02.ArticleNotFoundException" %>
<%@ page import="ez.model.Article" %>
<%
	int articleId = Integer.parseInt(request.getParameter("articleId"));//list_view로 부터 받아온 파라미터 articleId를 int타입 articleId에 저장한다.
	//System.out.println("articleId: " + articleId); 읽어오는 글의 articleId 출력
	String viewPage = null;//forward에 줄 page값을 위해 선언한 String타입 변수
	try{
		Article article = ReadArticleService.getInstance().readArticle(articleId);//매개변수 articleId에 담겨있는 article을 갖고온다.
		request.setAttribute("article", article);//해당 article을 request영역에 'article'로 등록한다.
		viewPage = "read_view.jsp";//위 내용을 실행하고 viewPage에 'read_view.jsp'를 저장한다.
	}catch(ArticleNotFoundException ex){//ArticleNotFound 예외가 발생했을때의 처리
		viewPage = "article_not_found.jsp";//예외가 발생했을 때, viewPage의 값은 'article_not_found.jsp'이다.
	}

	//System.out.println("viewPage: " + viewPage);
%>
<jsp:forward page="<%=viewPage%>"/>