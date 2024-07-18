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
//비밀번호 변경 서블릿
@WebServlet("/AjaxAccountSettingPwServlet")
public class AjaxAccountSettingPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("요청들어옴");
		
		String paramCurrentPw = request.getParameter("pw");
		String paramNewPw = request.getParameter("pw");
		int memberIdx = Integer.parseInt("member_idx");
		
		//다오에 있는 메서드 호출 -- > UPDATE 실행.
		NamooMemberDao memberDao = new NamooMemberDao();
		try {
			memberDao.accountSettingChangePassword(paramNewPw, memberIdx, paramCurrentPw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//서버에서 클라이언트로 데이터 보냄 : json 형식으로. (권장 라이브러리: json-simple)
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("pw", paramNewPw);
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
	}


}
