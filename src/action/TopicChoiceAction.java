package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TopicChoiceAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int topicIdx = Integer.parseInt(request.getParameter("topicIdx"));
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		request.setAttribute("topicIdx", topicIdx);
		request.setAttribute("teamIdx", teamIdx);
		request.getRequestDispatcher("NamooTopic.jsp").forward(request, response);
	}

}
