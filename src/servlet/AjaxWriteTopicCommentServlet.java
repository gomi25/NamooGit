package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.SideDao;
import dao.TopicDao;

@WebServlet("/AjaxWriteTopicCommentServlet")
public class AjaxWriteTopicCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   // POST 방식에서 한글깨짐 방지. (request 사용 전에)
		System.out.println("요청 들어옴.");
		
//		int loginMemberIdx = 2;   // testing ----> ㅁ. TODO : 나중에 세션에서 가져오는 걸로 바꿔야.
//		int teamIdx = 2;   
		int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
		int teamIdx = Integer.parseInt(request.getParameter("team_idx"));
//		int topicIdx = 1;  
		int topicBoardIdx = Integer.parseInt(request.getParameter("topic_board_idx"));
		String comment = request.getParameter("topic_board_comment");
		Integer fileIdx = request.getParameter("fileIdx") != null ? Integer.parseInt(request.getParameter("fileIdx")) : null;
		
		TopicDao tDao = new TopicDao();
		SideDao sDao = new SideDao();

		int topicCommentIdx = 0;
		String state = "";
		try {
	        topicCommentIdx = tDao.writeTopicComment(topicBoardIdx, memberIdx, comment, fileIdx);			
	        state = sDao.getStateOfMember(teamIdx, memberIdx);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// 응답을 만들어서 보내줘야.
		response.setContentType("application/json; charset=utf-8");   // 기억 or 복붙.
		PrintWriter writer = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		//jsonObj.put("result", "성공");
		jsonObj.put("topicCommentIdx", topicCommentIdx);
		jsonObj.put("state", state);
		writer.print(jsonObj);
	}
	
}
