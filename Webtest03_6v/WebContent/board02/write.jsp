<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.board.service02.WriteArticleService" %>
<%@ page import="ez.model.Article" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("euc-kr"); %>
<jsp:useBean id="writingRequest" class="ez.model.WritingRequest">
<jsp:setProperty name="writingRequest" property="*"/>
</jsp:useBean><%-- writeForm에서 받아온 내용들을 WritingRequest.java빈을 활용하여 담는다. --%>
<%
	Article postedArticle = 
			WriteArticleService.getInstance().write(writingRequest);
	request.setAttribute("postedArticle", postedArticle);
%>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글 작성</title>
</head>
<body>
게시글을 등록했습니다.
<br/>
<a href="<c:url value='list.jsp'/>">목록보기</a>
<a href="<c:url value='read.jsp?articleId=${postedArticle.id}'/>">게시글읽기</a>
</body>
</html>