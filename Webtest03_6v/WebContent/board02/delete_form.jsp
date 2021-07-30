<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="EUC-KR">
<title>삭제하기</title>
</head>
<body>
<form action="delete.jsp" method="post">
<input type="hidden" name="articleId" value="${param.articleId}"/>
글암호: <input type="password" name="password"/><br/>
<input type="submit" value="삭제"/>
</form>
</body>
</html>