<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.model.ArticleListModel" %>
<%@ page import="ez.board.service02.ListArticleService" %>
<%
	String pageNumberString = request.getParameter("p");//request영역으로 부터 p값을 받아온다. 
	//받아온 p값을 String타입 pageNumberString에 저장한다.
	int pageNumber=1;//기본 페이지 설정, 게시판을 열면 1페이지가 열린다.
	if(pageNumberString != null && pageNumberString.length() > 0){//p값을 받아서 String변수에 담은 값이 있다면,
		pageNumber = Integer.parseInt(pageNumberString);//정수화 해서 값을 pageNumber로 저장한다.
	}
	
	ListArticleService listService = ListArticleService.getInstance();
	ArticleListModel articleListModel = listService.getArticleList(pageNumber);/*ListArticleService에 있는 getArticleList메소드에
	(pageNumber)매개변수 값을 주고 그 결과 값을 ArticleListModel(DTO)타입 변수 articleListModel에 저장한다. */
	request.setAttribute("listModel", articleListModel);//핵심
	
	if(articleListModel.getTotalPageCount() > 0){
		int beginPageNumber = (articleListModel.getRequestPage() -1)/10*10+1;//시작페이지 연산
		//System.out.println("articleListModel.getRequestPage(): " + articleListModel.getRequestPage());
		int endPageNumber = beginPageNumber + 9;//끝 페이지 연산
		//System.out.println("endPageNumber: " + endPageNumber);
		if(endPageNumber > articleListModel.getTotalPageCount()){//endPageNumber가 TotalPage보다 많으면 TotalPage값을 담아준다.
			endPageNumber = articleListModel.getTotalPageCount();
		}//System.out.println("endPageNumber: " + endPageNumber);
		request.setAttribute("beginPage", beginPageNumber);//시작페이지를 request 영역에 저장
		request.setAttribute("endPage", endPageNumber);//끝 페이지를 request 영역에 저장
	}
%>
<jsp:forward page="list_view.jsp"/><!-- 핵심 jsp:forward -->
<%--  메모 공간
1. redirect와 forward의 차이점은 크게 두가지로 나눌 수 있다.
첫째, URL의 변화여부(redirect(O), forward(X))
둘째, 객체의 재사용여부(재사용 O -> forward, 재사용 X -> redirect) 

2. 시스템(session, DB)에 변화가 생기는 요청(로그인, 회원가입, 글쓰기)의 경우 
redirect방식으로 응답하는 것이 바람직하며, 시스템에 변화가 생기지 않는 
단순조회(리스트보기, 검색)의 경우 forward방식으로 응답하는 것이 바람직하다.--%>