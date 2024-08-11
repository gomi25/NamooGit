package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDao;

public class RemoveChatroomMemberAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int chatroomIdx = Integer.parseInt(request.getParameter("chatroomIdx"));
		int memberIdx = Integer.parseInt(request.getParameter("removeMemberIdx"));
		
		ChatDao cDao = new ChatDao();
		try {
			cDao.exitChatroom(chatroomIdx, memberIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("teamIdx", teamIdx);
	    request.setAttribute("chatroomIdx", chatroomIdx);
		request.getRequestDispatcher("/NamooChatjsp").forward(request, response);
	}

}
