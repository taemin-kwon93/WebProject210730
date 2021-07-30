<%@ page contentType="text/html;charset=euc-kr"%>
<% request.setCharacterEncoding("euc-kr");%>
<% String name = ""; %>
<HTML>
<HEAD>
<TITLE>표현언어의 사용예제2</TITLE>
</HEAD>

<BODY>

<H3>표현언어의 사용예제2 -파라미터값 처리</H3>
<P>
<FORM action="eLEx2.jsp" method="post">
   이름 : <input type="text" name="name" value="${param.name}">
          <input type="submit" value="확인">
</FORM>

<P>
이름은: ${param.name} 입니다.<br>
<!-- 위 는 표현언어(Expression Language사용) -->
<% name = request.getParameter("name"); %>
이름은: <%= name %> 입니다.<br>
<!-- 위 는 스크립트 익스프레션 사용 -->
</BODY>
</HTML>
