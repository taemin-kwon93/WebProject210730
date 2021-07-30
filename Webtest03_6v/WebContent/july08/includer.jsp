<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	int number=10;
%>
<%@ include file="./includee.jspf" %>
공통변수 DATAOLDER = "<%= dataFolder %>"
</body>
</html>