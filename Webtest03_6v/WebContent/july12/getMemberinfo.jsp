<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	String memberId = (String) session.getAttribute("MEMBERID");
	String name = (String) session.getAttribute("NAME");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>getMemberInfo</title>
</head>
<body>
 memberId = <%=memberId%><br>
 name = <%=name%><br>
</body>
</html>
