package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.ChatDao;

public class SendChatAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int chatroomIdx = Integer.parseInt(request.getParameter("chatroomIdx"));
        int memberIdx = Integer.parseInt(request.getParameter("memberIdx"));
        String content = request.getParameter("content");

        ChatDao chatDao = new ChatDao();
        int chatIdx = 0;
		try {
			chatIdx = chatDao.writeChat(chatroomIdx, memberIdx, content, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

        // 응답을 JSON 형식으로 작성
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter writer = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("chatIdx", chatIdx);
		writer.print(jsonObj);
	}

}
