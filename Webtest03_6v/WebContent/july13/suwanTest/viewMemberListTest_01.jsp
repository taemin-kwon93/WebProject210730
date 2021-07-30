<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.sql.DriverManager" %><!--JDBC을 위한 페이지 디렉티브 -->
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원목록</title>
</head>
<body>
MEMBER 테이블의 내용
<table width="100%" border="1"><!-- 테이블을 설정한다. 넓이. 테두리 -->
<tr><!-- 테이블 행을 만들고 -->
	<td>이름</td><td>아이디</td><td>이메일</td><td>삭제버튼</td><!-- 그 내용으로 이름, 아이디, 이메일을 넣는다. -->
</tr><!--[이름|아이디|이메일] 이런식으로 만들어 질거다  -->
<%
	Class.forName("oracle.jdbc.driver.OracleDriver");
	//Class.forName을 이용해 오라클드라이버에
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	try{
		String jdbcDriver = "jdbc:oracle:thin:@localhost:1521:XE";
		String dbUser = "ezz";
		String dbPass="oracle";
		
		String query
		="select * from MEMBER order by MEMBERID";
		
		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		
		while(rs.next()){
%>
<tr>
	<td><%= rs.getString("NAME") %></td>
	<td><a href="viewMember.jsp?memberID=<%=rs.getString("MEMBERID")%>">
	<%= rs.getString("MEMBERID") %></a></td>
	<td><%= rs.getString("EMAIL") %></td>
	<td><a href="delete_01.jsp?sendid=<%= rs.getString("MEMBERID") %>">삭제</a></td>
</tr>
<%
		}
	}catch(SQLException ex){
		out.println(ex.getMessage());
		ex.printStackTrace();
	}finally{
		if(rs != null)try {rs.close();}catch(SQLException ex){}
		if(stmt != null)try{stmt.close();}catch(SQLException ex){}
		if(conn != null)try{conn.close();}catch(SQLException ex){}
	}

%>

</table>
</body>
</html>