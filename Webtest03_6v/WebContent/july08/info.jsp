<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<! DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Info</title></head>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr>
	<td>제품번호</td><td>XXXX</td>
</tr>
<tr>
	<td>가격</td><td>10,000원</td>
</tr>
</table>
<jsp:include page="infoSub.jsp" flush="false">
	<jsp:param name="type" value="B"/> <!-- B가 있는곳에 조건을 주면 출력 결과물이 다르게 나온다. -->
</jsp:include>

</body>
</html>