<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:if test="${param.dest == '1'}">
	<c:redirect url="/use_c_set.jsp" />
</c:if>
<c:if test="${param.dest == '2'}">
	<c:redirect url="use_c_set.jsp" />
</c:if>
<c:if test="${param.dest == '3'}">
	<c:redirect url="/el/viewToday.jsp" context="/SSOL" />
</c:if>
<c:if test="${param.dest == '4'}">
	<c:redirect url="http://www.youtube.com/results">
			<c:param name="search_query" value="ryu" />
	</c:redirect>
</c:if>
