package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.NamooServiceTalkDao;

@WebServlet("/AjaxServiceTalkCreateServiceTalkroomServlet")
public class AjaxServiceTalkCreateServiceTalkroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글깨짐 방지
		request.setCharacterEncoding("utf-8"); 
		System.out.println("서비스톡룸 생성 요청 들어옴");
		
		int loginMemberIdx = 1; // TODO: 나중에 세션에서 가져오는 걸로
		//String paramMemberIdx = request.getParameter("member_idx");
		//int memberIdx = Integer.parseInt(paramMemberIdx);
		
		
		NamooServiceTalkDao talkroomDao = new NamooServiceTalkDao();
		int talkroomIdx = 0;
		try {
			talkroomIdx = talkroomDao.createServiceTalkRoomByMemberIdx(loginMemberIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "성공"); // 여러개 쓸 수 있음
		jsonObj.put("service_talkroom_idx", talkroomIdx);
		writer.print(jsonObj);
	}
}
