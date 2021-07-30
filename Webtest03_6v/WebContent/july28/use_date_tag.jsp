<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta charset="EUC-KR">
<title>dateFormat 태그 사용</title>
</head>
<body>
<c:set var="now" value="<%=new java.util.Date() %>" />
<fmt:formatDate value="${now}" type="date" dateStyle="full"/><br>
<fmt:formatDate value="${now}" type="date" dateStyle="short" /><br>
<fmt:formatDate value="${now}" type="time"/> <br>
<fmt:formatDate value="${now}" type="both" dateStyle="full" timeStyle="full" /><br>
<fmt:formatDate value="${now}" pattern="z a h:mm"/><br>
<c:out value="${pageContext.request.locale}"/>
</body>
</html>