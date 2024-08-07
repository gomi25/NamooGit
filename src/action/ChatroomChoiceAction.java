package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChatroomChoiceAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int chatroomIdx = Integer.parseInt(request.getParameter("chatroomIdx"));
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		request.setAttribute("chatroomIdx", chatroomIdx);
		request.setAttribute("teamIdx", teamIdx);
		request.getRequestDispatcher("NamooChat.jsp").forward(request, response);
	}

}
