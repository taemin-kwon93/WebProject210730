<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.net.URLEncoder" %>
<%
	Cookie cookie = new Cookie("name", URLEncoder.encode("최범균","EUC-KR"));
	response.addCookie(cookie);
%>

<html>
<head><title>쿠키생성</title></head>
<body>
<%= cookie.getName() %>쿠키의 값 = "<%= cookie.getValue() %>"
</body>
</html>