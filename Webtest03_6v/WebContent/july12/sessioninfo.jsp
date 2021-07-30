<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page session="true" %><!-- 기본 설정이 true이다. false값을 줄게 아니라면 생략해도 무관하다. -->
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date time = new Date();
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>세션정보</title>
</head>
<body>
세션ID: <%= session.getId() %><br>

<%
	time.setTime(session.getCreationTime());
%>
세션생성시간: <%= formatter.format(time) %><br>
<%
	time.setTime(session.getLastAccessedTime());
%>
최근접근시간: <%= formatter.format(time) %><br>
</body>
</html>