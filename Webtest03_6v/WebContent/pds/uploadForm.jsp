<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<html>
<head>
<meta charset="EUC-KR">
<title>파일등록</title>
</head>
<body>
<form action = "upload.jsp" method="post" enctype="multipart/form-data">
파일: <input type="file" name="file"/><br/>
설명: <input type="text" name="description"/><br/>
<input type="submit" value="업로드"/>
</form>
</body>
</html>