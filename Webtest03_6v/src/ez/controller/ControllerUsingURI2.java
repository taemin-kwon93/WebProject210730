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
    //��ɾ�� ó��Ŭ������ ���εǾ� �ִ� properties ������ �о Map��ü�� commandMap�� ����
    //��ɾ�� ó��Ŭ������ ���εǾ� �ִ� properties ������ Command.properties����
	public void init(ServletConfig config) throws ServletException{
		String props = config.getInitParameter("configFile2");//web.xml���� propertyConfig�� �ش��ϴ� init-param �� ���� �о��
		Properties pr = new Properties();//��ɾ�� ó�� Ŭ������ ���� ������ ������ Properties ��ü�� �����Ѵ�.
		FileInputStream f = null;
		try {
			String configFilePath = config.getServletContext().getRealPath(props);
			f = new FileInputStream(configFilePath);//Command.properties ������ ������ �о��
			pr.load(f);//Command.properties������ ������ Properties��ü�� ����
			//System.out.println("pr��?: "+pr);
		}catch(IOException e) {
			throw new ServletException(e);
		}finally {
			if(f != null) try {f.close();}catch(IOException ex) {}
		}
		Iterator keyIter = pr.keySet().iterator();//Iterator��ü�� Enumeration��ü�� Ȯ���Ų ������ ��ü
		while( keyIter.hasNext()) {
			String command = (String) keyIter.next();
			//System.out.println("keyIter: "+command); ketIter�� ������ �� ������?
			String className = pr.getProperty(command);
			//System.out.println("className: "+className);�Ȱ��� ���ðŶ� ��
			try {
				
			}catch
		}
	}
}
