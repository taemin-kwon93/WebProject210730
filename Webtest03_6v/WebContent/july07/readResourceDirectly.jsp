<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR" %>
<%@ page import ="java.io.*" %>	
<! DOCTYPE html>
<html>
<head><title>절대 경로 사용하여 자원읽기</title></head>
<body>
<%
	FileReader fr = null;
	char[] buff = new char[512];
	int len = -1;
	
	try{
		fr = new FileReader(
				"C:\\java\\App\\Webtest03_6v\\WebContent\\july07\\message\\notice\\notice.txt");
		while((len = fr.read(buff)) != -1){
			out.print(new String(buff, 0, len));
		}
		}catch(IOException ioex){
			out.println("예외발생: "+ioex.getMessage());
		}finally{
			if(fr != null) try{fr.close();}catch(IOException ioex){}
		}
%>

</body>
</html>