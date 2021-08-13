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
//System.out.println("1 pr은?: "+pr);
		}catch(IOException e) {
			throw new ServletException(e);
		}finally {
			if(f != null) try {f.close();}catch(IOException ex) {}
		}
		Iterator keyIter = pr.keySet().iterator();//Iterator객체는 Enumeration객체를 확장시킨 개념의 객체
		while( keyIter.hasNext()) {
			String command = (String) keyIter.next();
//System.out.println("2 keyIter: "+command); //ketIter로 가지고 온 내용은?
			String className = pr.getProperty(command);
//System.out.println("3 className: "+className);//똑같이 나올거라 예상
			try {
				Class commandClass = Class.forName(className);//해당 문자열을 클래스로 만든다.
				Object commandInstance = commandClass.newInstance();//해당 클래스의 객체를 생성
				commandMap.put(command, commandInstance);//Map객체인 commandMap에 객체 저장
			}catch(ClassNotFoundException e) {
				throw new ServletException(e);
			}catch(InstantiationException e) {
				throw new ServletException(e);
			}catch(IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}//public void init(ServletConfig config)
	
	public void doGet(//get 방식의 서비스 메소드
			HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		requestPro(request, response);
	}//public void doGet
	
	public void doPost(//post 방식의 서비스 메소드
			HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		requestPro(request, response);
	}//public void doPost
	
	//사용자 요청을 분석해서 해당 작업을 처리
	private void requestPro(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String view = null;
		CommandAction com = null;
		try {
			String command = request.getRequestURI();/*URI통합 자원 식별자(Uniform Resource Identifier, URI)는 
			인터넷에 있는 자원을 나타내는 유일한 주소이다. URI의 존재는 인터넷에서 요구되는 기본조건으로서 
			인터넷 프로토콜에 항상 붙어 다닌다. URI의 하위개념으로 URL, URN 이 있다.*/
//System.out.println("4 command 출력내용: " + command);
			if(command.indexOf(request.getContextPath())==0) {
				command = command.substring(request.getContextPath().length());
			}
			com = (CommandAction)commandMap.get(command);
			if(com == null) {
				com = new NullAction();
			}
			view = com.requestPro(request, response);
//System.out.println("5 view의 내용: "+ view);
		}catch(Throwable e) {
			throw new ServletException(e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
}//public class ControllerUsingURI2
