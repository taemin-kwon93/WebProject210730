<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.board.service02.ReplyArticleService" %>
<%@ page import="ez.model.Article" %>
<% request.setCharacterEncoding("euc-kr"); %>
<%--read_view.jsp에 <a>태그로 부터 시작된다.
<a href="reply_form.jsp?parentId=${article.id}&p=${param.p}">답변쓰기</a>
reply_form.jsp로 부터 받아온 값들로 Bean을 사용한다--%>
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