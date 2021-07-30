<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date time = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	session.setAttribute("MEMBERID", "kwon");
	session.setAttribute("NAME", "taemin");
	session.setMaxInactiveInterval(2);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>세션에 정보 등록하기</title>
</head>
<body>
세션에 정보를 저장하였습니다.<br>

<%
    Object lobj_getdata = session.getAttribute("MEMBERID");
    String ls_getdata = (String)lobj_getdata;
    out.println("설정된 세션값 [1] → " + ls_getdata + "<br/><br/>");
%>


<% time.setTime(session.getCreationTime());%><br>
세션 생성시간: <%= sdf.format(time) %><br>
<% time.setTime(session.getLastAccessedTime()); %>
세션 마지막 접근시간: <%= sdf.format(time) %><br>



</body>
</html>