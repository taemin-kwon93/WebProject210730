<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String memberId = (String)session.getAttribute("MEMBERID");
	boolean login = (memberId == null? false:true);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인 여부 검사</title>
</head>
<body>
<%
	if(login){
%>
아이디 "<%= memberId %>"로 로그인 했습니다.
<%
	}else{
%>
로그인이 안됐습니다.
<%
	}
%>
</body>
</html>