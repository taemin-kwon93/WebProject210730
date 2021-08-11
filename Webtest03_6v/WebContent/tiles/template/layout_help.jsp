<%@ page contentType="text/html; charset=euc-kr" %> 
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html>
<head>
	<title><tiles:getAsString name="title" /></title>
</head>
<body>

<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr>
	<td colspan="2">
	<tiles:insertAttribute name="header" />
	</td>
</tr>
<tr>
	<td valign="top" width="20%"><tiles:insertAttribute name="menu" /></td>
	<td valign="top"><tiles:insertAttribute name="body" /></td>
</tr>
<tr>
	<td colspan="2">
	<tiles:insertAttribute name="footer" />
	</td>
</tr>
</table>
</body>
</html>
