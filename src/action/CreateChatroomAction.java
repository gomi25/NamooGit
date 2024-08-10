package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDao;

public class CreateChatroomAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberIdx = (Integer)request.getSession().getAttribute("memberIdx");
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int chatroomIdx = -1;
		
		String name = request.getParameter("name");
		String info = request.getParameter("info");
		
		ChatDao cDao = new ChatDao();
		
		try {
			chatroomIdx = cDao.createChatroom(teamIdx, memberIdx, name, info);
			
			request.setAttribute("teamIdx", teamIdx);
 		    request.setAttribute("chatroomIdx", chatroomIdx);
			request.getRequestDispatcher("/NamooChat.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
