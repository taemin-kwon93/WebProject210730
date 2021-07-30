<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR" %>
<%
	String code = request.getParameter("code");
	String viewPageURI = null;
	
	if(code.equals("A")){
		viewPageURI = "./viewExModule/a.jsp";
	}else if (code.equals("B")){
		viewPageURI = "./viewExModule/b.jsp";
	}else if (code.equals("C")){
		viewPageURI = "./viewExModule/c.jsp";
	}
%>
<jsp:forward page="<%= viewPageURI %>">