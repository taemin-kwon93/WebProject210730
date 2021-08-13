package ez.controller;

import java.awt.image.ImagingOpException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ez.action.CommandAction;
import ez.action.NullAction;

public class ControllerUsingURI2 extends HttpServlet{
	private Map commandMap = new HashMap();
    //명령어와 처리클래스가 매핑되어 있는 properties 파일을 읽어서 Map객체인 commandMap에 저장
    //명령어와 처리클래스가 매핑되어 있는 properties 파일은 Command.properties파일
	public void init(ServletConfig config) throws ServletException{
		String props = config.getInitParameter("configFile2");//web.xml에서 propertyConfig에 해당하는 init-param 의 값을 읽어옴
		Properties pr = new Properties();//명령어와 처리 클래스의 매핑 정보를 저장할 Properties 객체를 생성한다.
		FileInputStream f = null;
		try {
			String configFilePath = config.getServletContext().getRealPath(props);
			f = new FileInputStream(configFilePath);//Command.properties 파일의 내용을 읽어옴
			pr.load(f);//Command.properties파일의 정보를 Properties객체에 저장
			//System.out.println("pr은?: "+pr);
		}catch(IOException e) {
			throw new ServletException(e);
		}finally {
			if(f != null) try {f.close();}catch(IOException ex) {}
		}
		Iterator keyIter = pr.keySet().iterator();//Iterator객체는 Enumeration객체를 확장시킨 개념의 객체
		while( keyIter.hasNext()) {
			String command = (String) keyIter.next();
			//System.out.println("keyIter: "+command); ketIter로 가지고 온 내용은?
			String className = pr.getProperty(command);
			//System.out.println("className: "+className);똑같이 나올거라 예
			try {
				
			}catch
		}
	}
}
