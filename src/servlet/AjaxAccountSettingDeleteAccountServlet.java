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

@WebServlet("/AjaxAccountSettingDeleteAccountServlet")
public class AjaxAccountSettingDeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Ajax는 무조건 doget 메서드로! → post 안 됨 
	// throw Exception 건들지 X
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청이 들어왔는지 확인
		System.out.println("요청 들어옴");
		
		// 다오에 필요한 파라미터 값 선언
		String paramPw = request.getParameter("pw");
		int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
		
		// 클라이언트(AJAX)에서 서버로 데이터 전달
		// 다오에 있는 메서드 호출 -- > UPDATE 실행.
		// 빨간 줄 있으면 try ~ catch로 잡아야 함.
		NamooMemberDao memberDao = new NamooMemberDao();
		try {
			memberDao.accountSettingLeaveNamoo(memberIdx, paramPw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 서버에서 클라이언트로 데이터 보냄 : json 형식으로. (권장 라이브러리: json-simple)
		// 일단 이대로 복붙, 오류 나면 YG께.. 
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "성공");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
	}
}
