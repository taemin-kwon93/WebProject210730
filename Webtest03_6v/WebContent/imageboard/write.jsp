<%@ page language="java" contentType="text/html; charset=EUC-KR" %>
<%@ page errorPage = "../error/error_view.jsp" %>

<%@ page import = "java.sql.Timestamp" %>
<%@ page import = "java.io.File" %>
<%@ page import = "org.apache.commons.fileupload.FileItem" %>

<%@ page import = "util.ImageUtil" %>
<%@ page import = "ez.fileupload.FileUploadRequestWrapper" %>

<%@ page import = "ez.imageBoard.Theme" %>
<%@ page import = "ez.imageBoard.ThemeManager" %>
<%@ page import = "ez.imageBoard.ThemeManagerException" %>

<%
    FileUploadRequestWrapper requestWrap = new FileUploadRequestWrapper(
        request, -1, -1,
        "C:/Users/DELL/git/WebProject210730/Webtest03_6v/WebContent/temp");
		//C:\Users\DELL\git\WebProject210730\Webtest03_6v\WebContent\temp
		//C:\Users\DELL\git\WebProject210730\Webtest03_6v\WebContent\image
    HttpServletRequest tempRequest = request;
    request = requestWrap;
%>
<jsp:useBean id="theme" class="ez.imageBoard.Theme">
    <jsp:setProperty name="theme" property="*" />
</jsp:useBean>
<%
    FileItem imageFileItem = requestWrap.getFileItem("imageFile");
    String image = "";
    if (imageFileItem.getSize() > 0) {
        image = Long.toString(System.currentTimeMillis());
       
        // 이미지를 지정한 경로에 저장
        File imageFile = new File(
        		//C:/Users/DELL/git/WebProject210730/Webtest03_6v/WebContent/image
            "C:/Users/DELL/git/WebProject210730/Webtest03_6v/WebContent/image",
            image);
        // 같은 이름의 파일이름 처리
        if (imageFile.exists()) {
            for (int i = 0 ; true ; i++) {
                imageFile = new File(
                    "C:/Users/DELL/git/WebProject210730/Webtest03_6v/WebContent/image",
                    image+"_"+i);
                if (!imageFile.exists()) {
                    image = image + "_" + i;
                    break;
                }
            }
        }
        imageFileItem.write(imageFile);
       
        // 썸네일 이미지 생성
        File destFile = new File(
            "C:/Users/DELL/git/WebProject210730/Webtest03_6v/WebContent/image",
            image+".small.jpg");
        ImageUtil.resize(imageFile, destFile, 50, ImageUtil.RATIO);
    }
   
    theme.setRegister(new Timestamp(System.currentTimeMillis()));
    theme.setImage(image);
   
    ThemeManager manager = ThemeManager.getInstance();
    manager.insert(theme);
%>
<script>
alert("새로운 이미지를 등록했습니다.");
location.href = "list.jsp";
</script>