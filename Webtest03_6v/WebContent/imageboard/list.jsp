<%@ page contentType = "text/html; charset=euc-kr" %>
<%
    request.setCharacterEncoding("euc-kr");
%>
<jsp:forward page="../template/template.jsp"><%-- template.jsp로 포워딩 --%>
<jsp:param name="CONTENTPAGE" value="list_view.jsp" /><%-- template.jsp에 22행정도 
param.CONTENPAGE에 값을 넘겨준다. value는 말그대로 name에 등록될 값을 뜻한다.--%>
</jsp:forward>
