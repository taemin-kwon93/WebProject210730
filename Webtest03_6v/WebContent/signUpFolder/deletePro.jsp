<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.logon.LogonDBBean" %>
<%@ include file="color.jsp" %>

<html>
<head>
<meta charset="EUC-KR">
<title>회원탈퇴</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<%
	String id = (String)session.getAttribute("memId");
	String passwd = request.getParameter("passwd");
	
	LogonDBBean manager = LogonDBBean.getInstance();
	int check = manager.deleteMember(id,passwd);
	
	if(check==1){
		session.invalidate();
%>
<body bgcolor="<%= bodyback_c %>">
<form method="post" action="main.jsp" name="userinput">
<table width="270" border="0" cellspacing="0" cellpadding="5" align="center">
<tr bgcolor="<%=title_c %>">
	<td height="39" align="center">
	<font size="+1"><b>회원정보가 삭제됐습니다.</b></font></td>
</tr>
<tr bgcolor="<%=value_c %>">
	<td align="center">
	<p>안녕히가세요.</p>
	<meta http-equiv="Refresh" content="5;url=main.jsp">
	</td>
</tr>
<tr bgcolor="<%=value_c %>">
	<td align="center">
	<input type="submit" value="확인">
	</td>
	</tr>	
</table>
</form>
<%}else{%>
<script>
alert("비밀번호가 맞지 않습니다.");
history.go(-1);
</script>
<%}%>
</body>
</html>