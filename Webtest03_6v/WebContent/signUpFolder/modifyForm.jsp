<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "ez.logon.*" %>
<%@ include file="color.jsp" %>
<!-- Webtest03_6v -->
<html>
<head>
<meta charset="EUC-KR">
<title>회원정보 수정</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function checkIt(){
	var userinput = eval("document.userinput");
	if(!userinput.passwd.value){
		alert("비밀번호를 입력하세요.");
		return false;
	}
	if(userinput.passwd.value != userinput.passwd2.value){
		alert("비밀번호를 동일하게 입력하세요.");
		return false;
	}
	if(!userinput.username.value){
		alert("사용자 이름을 입력하세요.");
		return false;
	}
	if(!userinput.jumin1.value || !userinput.jumin2.value){
		alert("주민번호를 입력하세요.");
		return false;
	}
}
//우편번호와 주소 function 추가하기
function zipCheck(){
   	url="ZipCheck.jsp?check=y";
   	window.open(url,"post","toolbar=no ,width=500 ,height=300 ,directories=no,status=yes,scrollbars=yes,menubar=no");
} //function zipCheck()
</script>
<%
	String id = (String)session.getAttribute("memId");
	if(request.getParameter("id") == null) {
		id = (String)session.getAttribute("memId");
	}else{
		id = request.getParameter("id");
	}
	LogonDBBean manager = LogonDBBean.getInstance();
	LogonDataBean c = manager.getMember(id);

	
	try{
%>
</head>
<body bgcolor="<%=bodyback_c %>">
<form action="modifyPro.jsp" method="post"  name="userinput" onsubmit="return checkIt()">
<table width="600" border="1" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<td colspan="2" height="39" bgcolor="<%=title_c %>" align="center">
		<font size="+1"><b>회원 정보수정</b></font></td>
	</tr>
	<tr>
		<td colspan="2" class="normal" align="center">회원정보를 수정합니다.</td>
	</tr>
	<tr>
		<td width="200" bgcolor="<%= value_c %>"><b>아이디입력</b></td>
		<td width="400" bgcolor="<%=value_c %>"></td>
	</tr>
	
	<tr>
		<td width="200">사용자 ID</td>
		<td width="400"><%=c.getId() %></td>
	</tr>
	<tr>
	<td width="200"> 비밀번호</td>
	<td width="400">
	<input type="password" name="passwd" size="10" maxlength="10" value="<%=c.getPasswd() %>">
	</td>
	</tr>
	<tr>
		<td width="200" bgcolor="<%=value_c %>"><b>개인정보 입력</b></td>
		<td width="400" bgcolor="<%=value_c %>"></td>
	</tr>
	<tr>
		<td width="200">사용자 이름</td>
		<td width="400">
			<input type="text" name="name" size="15" maxlength="20" value="<%=c.getName() %>">
			</td>
	</tr>
	<tr>
	<td width="200">주민번호</td>
	<td width="400">
	<%=c.getJumin1() %>-<%=c.getJumin2() %>
	</td>
	</tr>
	<tr>
	<td width="200">ㅌ-Mai1</td>
	<td width="400">
	<%if(c.getEmail()==null){ %>
	<input type="text" name="email" size="40" maxlength="30">
	<%}else{%>
		<input type="text" name="email" size="40" maxlength="30" value="<%=c.getEmail() %>">
		<%}%>
		</td>
	</tr>
	<tr>
	<td width="200">Blog</td>
	<td width="400">
	<%if(c.getBlog()==null){ %>
	<input type="text" name="blog" size="60" maxlength="50" >
	<%}else{%>
	<input type="text" name="blog" size="60" maxlength="50" value="<%=c.getBlog() %>">
	<%} %>
	</td>
	</tr>
<!-- 우편번호와 주소 추가 -->
	    <tr> 
       <td width="200">우편번호</td>
       <td> 
       <%if(c.getZipcode()==null){%>
       <input type="text" name="zipcode" size="7">
       <%}else{%>
       <input type="text" name="zipcode" size="7" value="<%=c.getZipcode() %>">
       <%}%>
               <input type="button" value="우편번호찾기" onClick="zipCheck()">
               우편번호를 검색 하세요.</td>
          </tr>
    <tr>
    <tr> 
       <td>주소</td>
       <td>
       <%if(c.getAddress()==null){%>
       <input type="text" name="address" size="70">
       <%}else{%>
       <input type="text" name="address" size="70" value="<%=c.getAddress() %>">
       <%}%>
       주소를 적어 주세요.</td>
    </tr>
<!-- 우편번호와 주소 추가 범위 -->
	<tr>
	<td colspan="2" align="center" bgcolor="<%=value_c %>">
	<input type="submit" name="modify" value="수정">
	<input type="button" value="취소" onclick="javascript:window.location='main.jsp'">
	</td>
	</tr>
</table>
</form>
</body>
<%}catch(Exception e){} %>
</html>