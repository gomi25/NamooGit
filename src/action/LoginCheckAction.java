package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NamooMemberDao;

public class LoginCheckAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		System.out.println("email: " + email + ", pw: " + pw);
		
		NamooMemberDao mDao = new NamooMemberDao();
		
		boolean result = false;
		int memberIdx = -1;
		
		try {
			result = mDao.loginCheck(email, pw);
			if(result) {
				memberIdx = mDao.getMemberIdxFromEmail(email);
				HttpSession session = request.getSession();
				session.setAttribute("memberIdx", memberIdx);
				request.getRequestDispatcher("NamooMainTool.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("NamooLogin.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
