package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
			memberIdx = mDao.memberIdxFromEmail(email);
		} catch (Exception e) {	e.printStackTrace();}
		
	
		if(result) {	//NamooMainLogined.jsp로 이동
			HttpSession session = request.getSession();
			session.setAttribute("memberIdx", memberIdx);
			RequestDispatcher rd = request.getRequestDispatcher("NamooMainLogined.jsp");
			rd.forward(request,response);
		} else {		//NamooLogin.jsp로 이동
			RequestDispatcher rd = request.getRequestDispatcher("NamooLogin.jsp");
			rd.forward(request,response);
		}
	}

}
