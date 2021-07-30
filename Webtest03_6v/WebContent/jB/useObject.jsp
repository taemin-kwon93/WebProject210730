<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="member" scope="request"
class="ez.member.MemberInfo"/>
<html>
<head>
<meta charset="EUC-KR">
<title>인사말</title>
</head>
<body>
<%= member.getName() %>(<%= member.getId() %>)회원님, 안녕하세요.
</body>
</html>