package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.ChatDao;

@WebServlet("/AjaxDeleteChatContentServlet")
public class AjaxDeleteChatContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   // POST 방식에서 한글깨짐 방지. (request 사용 전에)
		System.out.println("요청 들어옴.");
		
//		int loginMemberIdx = 2;   // testing ----> ㅁ. TODO : 나중에 세션에서 가져오는 걸로 바꿔야.
//		int teamIdx = 2;   
//		int topicIdx = 1;  
		int chatIdx = Integer.parseInt(request.getParameter("chat_idx"));
		
		ChatDao cDao = new ChatDao();
		try {
			cDao.deleteChatContent(chatIdx);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// 응답을 만들어서 보내줘야.
		response.setContentType("application/json; charset=utf-8");   // 기억 or 복붙.
		PrintWriter writer = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "성공");
		writer.print(jsonObj);
	}
	
}
