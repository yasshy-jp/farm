package member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
	public String execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
		
			HttpSession session=request.getSession();

			if (session.getAttribute("MEMBER")!=null) {
				session.removeAttribute("MEMBER");
				return "logout-out.jsp";
			} else if (session.getAttribute("OWNER")!=null) {
				session.removeAttribute("OWNER");
				return "logout-out.jsp";
		}
		
		return "logout-error.jsp";
		}
}
