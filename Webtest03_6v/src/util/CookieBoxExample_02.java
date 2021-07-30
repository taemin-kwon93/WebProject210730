package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import java.util.Map;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.IOException;


public class CookieBoxExample_02 {
	private Map<String, Cookie> cookieMap = 
			new java.util.HashMap<String, Cookie>();
	
	public CookieBoxExample_02(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
	}
	
	public static Cookie createCookie(String name, String value)
	throws IOException{
		return new Cookie(name, URLEncoder.encode(value,"euc-kr"));
	}//첫번째 createCookie
	
	public static Cookie createCookie(String name, String value, String path, int maxAge)
	throws IOException{
		Cookie cookie = new Cookie(name, URLEncoder.encode(value,"euc-kr"));
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}//두번째 createCookie(경로, 쿠키유지시간 추가)
	
	public static Cookie createCookie(String name, String value, String path, String domain, int maxAge)
	throws IOException{
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "euc-kr"));
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		return cookie;
	}//세번째 cookie 생성 (domain 추가)
	
	public String getValue(String name) throws IOException{
		Cookie cookie = cookieMap.get(name);
		if(cookie == null) {
			return null;
		}
		return URLDecoder.decode(cookie.getValue(),"euc-kr");
	}
	public boolean exists(String name) {
		return cookieMap.get(name) != null;
	}
}// class CookieBoxExample_02
