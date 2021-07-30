<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="EUC-KR">
<title>시간대 목록</title>
</head>
<body>
<c:forEach var="id" items="<%=java.util.TimeZone.getAvailableIDs() %>"> ${id}<br>
</c:forEach>
</body>
</html>