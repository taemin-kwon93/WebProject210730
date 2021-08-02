<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.addHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 1L);
%>
<%--read.jsp에 담겨있는 내용 = 
Article article = ReadArticleService.getInstance().readArticle(articleId);
request.setAttribute("article", article); --%>
<html>
<head>
<meta charset="EUC-KR">
<title>글읽기</title>
</head>
<body>
<table border="1">
<tr>
	<td>제목</td>
	<td>${article.title}</td>
</tr>
<tr>
	<td>작성자</td>
	<td>${article.writerName}</td>
</tr>
<tr>
	<td>작성일</td>
	<td><fmt:formatDate value="${article.postingDate}" pattern="yyyy-MM-dd"/></td>
</tr>
<tr>
	<td>내용</td>
	<td><pre><c:out value="${article.content}"/></pre>
	</td>
</tr>
<tr>
	<td colspan="2">
	<a href="list.jsp?p=${param.p}">목록보기</a>
	<a href="reply_form.jsp?parentId=${article.id}&p=${param.p}">답변쓰기</a>
	<a href="update_form.jsp?articleId=${article.id}&p=${param.p}">수정하기</a>
	<a href="delete_form.jsp?articleId=${article.id}">삭제하기</a>
	</td>
</tr>
</table>
</body>
</html>