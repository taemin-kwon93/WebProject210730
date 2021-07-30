<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%
	String idValue = request.getParameter("id");
	
	Connection conn = null;
	PreparedStatement pstmtItem = null;
	PreparedStatement pstmtDetail = null;
	
	String jdbcDriver = "jdbc:oracle:thin:@localhost:1521:XE";
	String dbUser = "ezz";
	String dbPass = "oracle";
	
	Throwable occuredException = null;
	
	try{
		int id = Integer.parseInt(idValue);
		
		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
		conn.setAutoCommit(false);
		
		pstmtItem = conn.prepareStatement("insert into item values(?,?)");
		pstmtItem.setInt(1,id);
		pstmtItem.setString(2, "상품이름"+ id);
		pstmtItem.executeUpdate();
		
		if(request.getParameter("error") != null){
			throw new Exception("의도적 예외 발생");
		}
		pstmtDetail = conn.prepareStatement(
			"insert into item_detail values(?,?)");
		pstmtDetail.setInt(1, id);
		pstmtDetail.setString(2, "상세설명" + id);
		pstmtDetail.executeUpdate();
		
		conn.commit();
	}catch(Throwable e){
		if(conn != null){
			try{
				conn.rollback();
			}catch(SQLException ex){}
		}
		occuredException = e;
	}finally{
		if(pstmtItem != null)
			try{pstmtItem.close();}catch(SQLException ex){}
		if(pstmtDetail != null)
			try{pstmtDetail.close();}catch(SQLException ex){}
		if(conn != null) try{conn.close();}catch(SQLException ex){}
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>값입력</title>
</head>
<body>
<% if(occuredException != null){ %>
에러가 발생하였습니다: <%= occuredException.getMessage()%>
<% } else{ %>
데이터가 성공적으로 들어갔습니다.
<% } %>
</body>
</html>