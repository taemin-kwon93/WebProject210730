<%@ page contentType = "text/html; charset=euc-kr" %>
<%
    request.setAttribute("name", "최범균");
%>
<html>
<head><title>EL Object</title></head>
<body>

요청 URI: ${pageContext.request.requestURI} <br>
<%--
	<%= pageContext.getRequest().getRequestURI()%>
 --%>
request의 name 속성: ${requestScope.name} <br>
<%--
	<%= request.getAttribute("name")%>
 --%>
code 파라미터: ${param.code}
<%--
	<%= request.getParameter("code")%>
 --%>

</body>
</html>