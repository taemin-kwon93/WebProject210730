<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "util.CookieBox" %>
<%
	CookieBox cookieBox = new CookieBox(request);
%>
<html>
<head>
<meta charset="EUC-KR">
<title>Cookie사용</title></head>
<body>
name쿠키 = <%= cookieBox.getValue("name") %><br>
<% if(cookieBox.exists("id")){ %>
id 쿠키 = <%= cookieBox.getValue("id") %><br>
<% } %>
</body>
</html>