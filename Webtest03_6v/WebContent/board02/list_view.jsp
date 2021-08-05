<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--list.jsp 13행
request.setAttribute("listModel", articlelistModel); -->
<%--둘의 차이점은 html 소스 보기에서 확인할 수 있다.
html문서에서 소스보기를 할때 
<!-- -->는 주석이 소스코드로 보이게 된다.
하지만 <%-- --%><%--는 소스보기에도 보이지 않는다. 
--%>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글목록</title>
</head>
<body>
<table border="1">
<c:if test="${listModel.totalPageCount > 0}">
<tr>
	<td colspan="5">
	${listModel.startRow}~${listModel.endRow}&nbsp;&nbsp;&nbsp;
	[${listModel.requestPage}&nbsp;/&nbsp;${listModel.totalPageCount}]
	</td>
</tr></c:if>
<tr>
<td>글번호</td>
<td>제 목</td>
<td>작성자</td>
<td>작성일</td>
<td>조회수</td>
</tr>
<c:choose>
<c:when test="${listModel.hasArticle==false}"><%--게시글이 없는경우 --%>
<tr>
<td colspan="5">
게시글이 없습니다.
</td>
</tr>
</c:when>
<c:otherwise>
<c:forEach var="article" items="${listModel.articleList}"><%-- 해당 페이지에 articleList를 뽑는 반복문의 시작점 --%>
<tr>
<td>${article.id}</td>
<td>
<c:if test="${article.level > 0}">
<c:forEach begin="1" end="${article.level}">-</c:forEach>&gt;
</c:if>
<c:set var="query" value="articleId=${article.id}&p=${listModel.requestPage}"/><!-- 뷰에서 중요한 코드,
query라는 변수를 선언하고 'articleId=${article.id}&p=${listModel.requestPage}'라는 내용을 담는다. -->
<a href="<c:url value="read.jsp?${query}"/>">${article.title}</a></td><!-- 글제목을 입력하면 a 태그를 이용해서 이동한다. -->
<td>${article.writerName}</td>
<td>${article.postingDate}</td>
<td>${article.readCount}</td>
</tr>
</c:forEach><%-- 해당 페이지에 articleList를 뽑는 반복문의 끝 지점 --%>
<tr>
	<td colspan="5">
	<c:if test="${beginPage > 10}">
		<a href="<c:url value="list.jsp?=p=${beginPage-1}"/>">이전</a>
	</c:if>
	<c:forEach var="pno" begin="${beginPage}" end="${endPage}"><!-- 반복문을 통해 페이지를 출력-->
	<a href="<c:url value="list.jsp?p=${pno}"/>">[${pno}]</a>
	</c:forEach>
	<c:if test="${endPage < listModel.totalPageCount}">
	<a href="<c:url value="list.jsp?p=${endPage+1}"/>">다음</a>
	</c:if>
	</td>
</tr>
</c:otherwise>
</c:choose>
<tr>
<td colspan="5">
<a href="writeForm.jsp">글쓰기</a>
</td>
</tr>
</table>
</body>
</html>