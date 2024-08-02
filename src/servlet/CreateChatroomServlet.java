package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDao;

@WebServlet("/CreateChatroomServlet")
public class CreateChatroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");		
		
		int memberIdx = 2;   // TODO : 나중에) 세션에서 읽어오는 걸로.
		int teamIdx = 1;
	    String name = request.getParameter("name");
	    String info = request.getParameter("info");
		
	    ChatDao cDao = new ChatDao();		
	    try {
	    	// 채팅방 생성 및 사용자 멤버로 추가
	    	cDao.createChatroom(teamIdx, memberIdx, name, info);
			
			RequestDispatcher disp = request.getRequestDispatcher("/NamooChat.jsp");
	        disp.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
