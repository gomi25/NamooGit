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

@WebServlet("/AjaxAccountSettingNameServlet")
public class AjaxAccountSettingNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("요청 들어옴..");
		///////////////////////////////
		
		String paramNewName = request.getParameter("name");
		String paramMemberIdx = request.getParameter("member_idx");
		int memberIdx = Integer.parseInt(paramMemberIdx);
		
		//System.out.println("paramNewName : " + paramNewName);
		//System.out.println("paramMemberIdx : " + paramMemberIdx);
		
		// TODO: 다오에 있는 메서드 호출(memberIdx, paramNewName) ---> UPDATE 실행.
		NamooMemberDao memberDao = new NamooMemberDao();
		try {
			memberDao.accountSettingChangeName(paramNewName, memberIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		///////////////////////////////
		// 서버에서 클라이언트로 데이터 보냄 : json 형식으로 하세요. (권장 라이브러리: json-simple)
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", paramNewName); // 여러개 쓸 수 있음
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
	}
}
