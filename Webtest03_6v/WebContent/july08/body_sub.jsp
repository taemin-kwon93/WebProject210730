<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Info</title>
</head>
<body>
body_sub에서 name 파라미터 값: <%= request.getParameter("name") %>
<br/>
name 파라미터 값 목록:
<ul>
<%
	String[] names = request.getParameterValues("name");
	for(String name : names){
%>
	<li> <%= name %></li>
<%
	}
%>
</ul>
</body>
</html>