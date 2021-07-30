<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<! DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>layout2</title></head>
<body>
<table width="400" border="1" cellpadding="0" cellspacing="0">
<tr>
	<td colspan="2">
		<jsp:include page="./lo_module/top.jsp" flush="false" />
	</td>
</tr>
<tr>
	<td width="100" valign="top">
		<jsp:include page="./lo_module/left.jsp" flush="false" />
	</td>
	<td width="300" valign="top">
	레이아웃2<br>
	</td>
</tr>
<tr>
	<td colspan="2">
		<jsp:include page="./lo_module/bottom.jsp" flush ="false" />
	</td>
</tr>
</table>
</body>
</html>