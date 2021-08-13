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
    //���ɾ�� ó��Ŭ������ ���εǾ� �ִ� properties ������ �о Map��ü�� commandMap�� ����
    //���ɾ�� ó��Ŭ������ ���εǾ� �ִ� properties ������ Command.properties����
	public void init(ServletConfig config) throws ServletException{
		String props = config.getInitParameter("configFile2");//web.xml���� propertyConfig�� �ش��ϴ� init-param �� ���� �о��
		Properties pr = new Properties();//���ɾ�� ó�� Ŭ������ ���� ������ ������ Properties ��ü�� �����Ѵ�.
		FileInputStream f = null;
		try {
			String configFilePath = config.getServletContext().getRealPath(props);
			f = new FileInputStream(configFilePath);//Command.properties ������ ������ �о��
			pr.load(f);//Command.properties������ ������ Properties��ü�� ����
//System.out.println("1 pr��?: "+pr);
		}catch(IOException e) {
			throw new ServletException(e);
		}finally {
			if(f != null) try {f.close();}catch(IOException ex) {}
		}
		Iterator keyIter = pr.keySet().iterator();//Iterator��ü�� Enumeration��ü�� Ȯ���Ų ������ ��ü
		while( keyIter.hasNext()) {
			String command = (String) keyIter.next();
//System.out.println("2 keyIter: "+command); //ketIter�� ������ �� ������?
			String className = pr.getProperty(command);
//System.out.println("3 className: "+className);//�Ȱ��� ���ðŶ� ����
			try {
				Class commandClass = Class.forName(className);//�ش� ���ڿ��� Ŭ������ �����.
				Object commandInstance = commandClass.newInstance();//�ش� Ŭ������ ��ü�� ����
				commandMap.put(command, commandInstance);//Map��ü�� commandMap�� ��ü ����
			}catch(ClassNotFoundException e) {
				throw new ServletException(e);
			}catch(InstantiationException e) {
				throw new ServletException(e);
			}catch(IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}//public void init(ServletConfig config)
	
	public void doGet(//get ����� ���� �޼ҵ�
			HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		requestPro(request, response);
	}//public void doGet
	
	public void doPost(//post ����� ���� �޼ҵ�
			HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		requestPro(request, response);
	}//public void doPost
	
	//����� ��û�� �м��ؼ� �ش� �۾��� ó��
	private void requestPro(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String view = null;
		CommandAction com = null;
		try {
			String command = request.getRequestURI();/*URI���� �ڿ� �ĺ���(Uniform Resource Identifier, URI)�� 
			���ͳݿ� �ִ� �ڿ��� ��Ÿ���� ������ �ּ��̴�. URI�� ����� ���ͳݿ��� �䱸�Ǵ� �⺻�������μ� 
			���ͳ� �������ݿ� �׻� �پ� �ٴѴ�. URI�� ������������ URL, URN �� �ִ�.*/
//System.out.println("4 command ��³���: " + command);
			if(command.indexOf(request.getContextPath())==0) {
				command = command.substring(request.getContextPath().length());
			}
			com = (CommandAction)commandMap.get(command);
			if(com == null) {
				com = new NullAction();
			}
			view = com.requestPro(request, response);
//System.out.println("5 view�� ����: "+ view);
		}catch(Throwable e) {
			throw new ServletException(e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
}//public class ControllerUsingURI2