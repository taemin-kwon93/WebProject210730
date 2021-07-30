<%@ page contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import ="util.CookieBox" %>
<%
	response.addCookie(CookieBox.createCookie("name", "최범균"));
	response.addCookie(CookieBox.createCookie("id", "madvirus", "/july09", -1));
	response.addCookie(CookieBox.createCookie("kwon","KTM"));
	
%>
<html>
<head>
<meta charset="EUC-KR">
<title>CookieBox사용예</title></head>
<body>
CookieBox를 사용하여 쿠키 생성
</body>
</html>