<%@ page contentType = "text/html; charset=euc-kr" %>
<%
    request.setCharacterEncoding("euc-kr");
%>
<jsp:forward page="../template/template.jsp"><%-- template.jsp�� ������ --%>
<jsp:param name="CONTENTPAGE" value="list_view.jsp" /><%-- template.jsp�� 22������ 
param.CONTENPAGE�� ���� �Ѱ��ش�. value�� ���״�� name�� ��ϵ� ���� ���Ѵ�.--%>
</jsp:forward>
