<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.board.service02.ReadArticleService" %>
<%@ page import="ez.board.service02.ArticleNotFoundException" %>
<%@ page import="ez.model.Article" %>
<%
	int articleId = Integer.parseInt(request.getParameter("articleId"));//list_view로 부터 받아온 파라미터 articleId를 int타입 articleId에 저장한다.
	String viewPage = null;
	try{
		Article article = ReadArticleService.getInstance().readArticle(articleId);
		request.setAttribute("article", article);
		viewPage = "read_view.jsp";
	}catch(ArticleNotFoundException ex){
		viewPage = "article_not_found.jsp";
	}
%>
<jsp:forward page="<%=viewPage%>"/>