<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.board.service02.ReplyArticleService" %>
<%@ page import="ez.model.Article" %>
<% request.setCharacterEncoding("euc-kr"); %>
<jsp:useBean id="replyingRequest" class="ez.model.ReplyingRequest"/>
<jsp:setProperty name="replyingRequest" property="*"/>
<%
	String viewPage = null;//viewPage보게 될 페이지의 기본값으로 null을 저장.
	try{
		Article postedArticle = ReplyArticleService.getInstance().reply(replyingRequest);
		request.setAttribute("postedArticle", postedArticle);//request
		viewPage = "reply_success.jsp";
	}catch(Exception ex){
		viewPage="reply_error.jsp";
		request.setAttribute("replyException", ex);
	}
%>
<jsp:forward page="<%= viewPage %>"/>