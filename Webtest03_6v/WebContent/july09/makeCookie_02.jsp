<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLDecoder"%>

<%
	Cookie cookie1 = new Cookie("name", URLEncoder.encode("권태민", "EUC-KR"));
	response.addCookie(cookie1);
%>

<html>
<head>
<meta charset="EUC-KR">
<title>makeCookie</title></head>
<body>
생성된 쿠키 내용 name=<%= cookie1.getName() %>, value= <%= URLDecoder.decode(cookie1.getValue(), "EUC-KR") %>
</body>
</html>