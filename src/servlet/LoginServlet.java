package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dao.NamooMemberDao;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String email = request.getParameter("email");
		String pw = request.getParameter("password");
		
		int memberIdx = -1;
		System.out.println(email + "/" + pw);
		
		NamooMemberDao mDao = new NamooMemberDao();
		
		boolean result = false;
		try {
			result = mDao.loginCheck(email, pw);
			memberIdx = mDao.memberIdxFromEmail(email);
		} catch (Exception e) {	e.printStackTrace();}
		
	
		if(result) {	//Ex11List.jsp로 이동
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
