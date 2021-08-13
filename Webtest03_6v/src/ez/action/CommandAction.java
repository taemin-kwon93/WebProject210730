package ez.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAction {
	public String process(HttpServletRequest request, HttpServletResponse response)throws Throwable;
	
}
