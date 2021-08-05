<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="EUC-KR">
<title>글쓰기</title>
</head>
<body>
<%-- form 태그의 범위 --%>
<form action="<c:url value='write.jsp'/>" method="post">
제목: <input type="text" name="title" size="20"/><br/>
작성자: <input type="text" name="writerName"/><br/>
글암호: <input type="password" name="password"/><br/>
글내용: <br/>
<textarea name="content" cols="40" rows="5"></textarea>
<br/>
<input type="submit" value="전송"/>
<%--전송될 값들의 name(title, writerName, password, content) --%>
</form>
<%-- form 태그의 범위 --%>
</body>
</html>