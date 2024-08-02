package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.NamooMemberDao;
import util.MailSender;

@WebServlet("/AjaxSendEmailServlet")
public class AjaxSendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String emailTo = request.getParameter("emailTo");
		String nameTo = request.getParameter("nameTo");
		String subject = request.getParameter("subject");
		
		NamooMemberDao memberDao = new NamooMemberDao();
		String pw = null;
		try {
			pw = memberDao.getPwFromEmail(emailTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String htmlMsg = "<h1>비밀번호는 " + pw + " 입니다</h1>";
		
		MailSender mailSender = new MailSender();
		mailSender.sendMail(emailTo, nameTo, subject, htmlMsg);
		
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(new JSONObject());
	}
}
