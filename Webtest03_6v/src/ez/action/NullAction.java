package ez.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import ez.action.CommandAction;

public class NullAction implements CommandAction{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "/view/nullCommand.jsp";
	}

}
