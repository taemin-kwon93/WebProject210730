<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="ez.model.ArticleListModel" %>
<%@ page import="ez.board.service02.ListArticleService" %>
<%
	String pageNumberString = request.getParameter("p");
	int pageNumber=1;//�⺻ ������ ����, �Խ����� ���� 1�������� ������.
	if(pageNumberString != null && pageNumberString.length() > 0){
		pageNumber = Integer.parseInt(pageNumberString);
	}
	ListArticleService listService = ListArticleService.getInstance();
	ArticleListModel articleListModel = listService.getArticleList(pageNumber);
	request.setAttribute("listModel", articleListModel);//�ٽ�
	
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
<jsp:forward page="list_view.jsp"/><!-- �ٽ� jsp:forward -->
<!--  �޸� ����
1. redirect�� forward�� �������� ũ�� �ΰ����� ���� �� �ִ�.
ù°, URL�� ��ȭ����(redirect(O), forward(X))
��°, ��ü�� ���뿩��(���� O -> forward, ���� X -> redirect) 

2. �ý���(session, DB)�� ��ȭ�� ����� ��û(�α���, ȸ������, �۾���)�� ��� 
redirect������� �����ϴ� ���� �ٶ����ϸ�, �ý��ۿ� ��ȭ�� ������ �ʴ� 
�ܼ���ȸ(����Ʈ����, �˻�)�� ��� forward������� �����ϴ� ���� �ٶ����ϴ�.-->