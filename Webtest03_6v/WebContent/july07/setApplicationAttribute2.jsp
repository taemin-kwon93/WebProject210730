<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	String name = request.getParameter("name");
	String value = request.getParameter("value");
	
	if(name != null && value != null){
		application.setAttribute(name, value);	
	}
%>

<! DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>setAttributeExample</title></head>
<body>
<% 
	if(name != null && value != null){
%>
application 기본 객체의 속성 설정:
	<%= name %> = <%= value %>
<%
	}else{
%>	
application 기본 객체의 속성 설정안함.
<%
}
%>

</body>
</html>