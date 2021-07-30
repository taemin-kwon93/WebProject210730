<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
버전: <%= application.getServerInfo() %><br>
서블릿 규약 메이저: <%= application.getMajorVersion() %><br>
서블릿 규약 마이너: <%= application.getMinorVersion()%>
</body>
</html>