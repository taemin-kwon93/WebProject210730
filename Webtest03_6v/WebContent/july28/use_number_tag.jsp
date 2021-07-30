<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="EUC-KR">
<title>numberFormat 태그사용</title>
</head>
<body>
<c:set var="price" value="10000" />
<fmt:formatNumber value="${price}" type="number" var="numberTypecheck"/><br/>
통화: <fmt:formatNumber value="${price}" type="currency" currencySymbol="원"/><br>
퍼센트: <fmt:formatNumber value="${price}" type="percent" groupingUsed="true"/><br>
퍼센트: <fmt:formatNumber value="${price}" type="percent" groupingUsed="false"/>그룹핑 폴스<br>
숫자: ${numberTypecheck}<br>
패턴: <fmt:formatNumber value="${price}" pattern="00000000.0000"/>
</body>
</html>