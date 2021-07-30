<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("euc-kr");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Info</title>
</head>
<body>
include 전 name 파라미터 값: <%= request.getParameter("name") %>
<hr>
<jsp:include page="body_sub.jsp" flush="false">
	<jsp:param name="name" value="최범균" />
</jsp:include>
</hr>
include 후 name 파라미터 값: <%= request.getParameter("name") %>
</body>
</html>