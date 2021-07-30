<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원정보 삭제</title>
</head>
<body>
<%
	String sendid = request.getParameter("sendid");
	request.setCharacterEncoding("euc-kr");
	
	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try{
		String jdbcDriver = "jdbc:oracle:thin:@localhost:1521:XE"; 
		String dbUser = "ezz";
		String dbPass = "oracle";
		
		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
		pstmt = conn.prepareStatement(
				"DELETE FROM MEMBER WHERE MEMBERID= ?");
		pstmt.setString(1, sendid);
		pstmt.executeUpdate();

	}finally{
		if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
		if(conn != null)try{conn.close();}catch(SQLException ex){}
	}
%>
<%= sendid%>(이)가 삭제 됐습니다.
</body>
</html>