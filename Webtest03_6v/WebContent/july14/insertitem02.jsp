<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%
	String idValue = request.getParameter("id");
	
	Connection conn = null;
	PreparedStatement pstmtItem = null;
	PreparedStatement pstmtDetail = null;
	
	Throwable occuredException = null;
	try{
		int id = Integer.parseInt(idValue);
	
		String jdbcDriver = "jdbc:apache:commons:dbcp:/pool";
		/* String query
		="select * from MEMBER order by MEMBERID"; */
		
		conn = DriverManager.getConnection(jdbcDriver);
		conn.setAutoCommit(false);
		
		pstmtItem = conn.prepareStatement("insert into item values(?,?)");
		pstmtItem.setInt(1, id);
		pstmtItem.setString(2, "상품이름"+id);
		pstmtItem.executeUpdate();
		
		if(request.getParameter("error") != null){
			throw new Exception("의도적 예외 발생");
		}
		
		pstmtDetail = conn.prepareStatement(
			"insert into item_detail values(?,?)");
		pstmtDetail.setInt(1, id);
		pstmtDetail.setString(2, "상세설명"+id);
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
			try{pstmtItem.close();} catch(SQLException ex){}
		if(pstmtDetail != null)
			try{pstmtDetail.close();}catch(SQLException ex){}
		if(conn != null)
			try{conn.close();}catch(SQLException ex){}
	}
%>
<! DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<title>ITEM값 입력</title></head>
<body>
<% if(occuredException != null) %>
</body>
</html>