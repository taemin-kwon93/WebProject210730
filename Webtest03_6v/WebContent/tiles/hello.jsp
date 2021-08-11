<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
	request.setAttribute("greeting", "¾È³çÇÏ¼¼¿ä");
%>
<tiles:insertDefinition name="hello" />
