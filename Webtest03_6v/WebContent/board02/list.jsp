<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.model.ArticleListModel" %>
<%@ page import="ez.board.service02.ListArticleService" %>
<%
	String pageNumberString = request.getParameter("p");
	int pageNumber=1;//기본 페이지 설정, 게시판을 열면 1페이지가 열린다.
	if(pageNumberString != null && pageNumberString.length() > 0){
		pageNumber = Integer.parseInt(pageNumberString);
	}
	ListArticleService listService = ListArticleService.getInstance();
	ArticleListModel articleListModel = listService.getArticleList(pageNumber);
	request.setAttribute("listModel", articleListModel);//핵심
	
	if(articleListModel.getTotalPageCount() > 0){
		int beginPageNumber = (articleListModel.getRequestPage() -1)/10*10+1;
		int endPageNumber = beginPageNumber + 9;
		if(endPageNumber > articleListModel.getTotalPageCount()){
			endPageNumber = articleListModel.getTotalPageCount();
		}
		request.setAttribute("beginPage", beginPageNumber);
		request.setAttribute("endPage", endPageNumber);
	}
%>
<jsp:forward page="list_view.jsp"/><!-- 핵심 jsp:forward -->
<!--  메모 공간
1. redirect와 forward의 차이점은 크게 두가지로 나눌 수 있다.
첫째, URL의 변화여부(redirect(O), forward(X))
둘째, 객체의 재사용여부(재사용 O -> forward, 재사용 X -> redirect) 

2. 시스템(session, DB)에 변화가 생기는 요청(로그인, 회원가입, 글쓰기)의 경우 
redirect방식으로 응답하는 것이 바람직하며, 시스템에 변화가 생기지 않는 
단순조회(리스트보기, 검색)의 경우 forward방식으로 응답하는 것이 바람직하다.-->