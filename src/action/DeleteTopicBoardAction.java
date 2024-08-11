package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TopicDao;

public class DeleteTopicBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int topicIdx = Integer.parseInt(request.getParameter("topicIdx"));
		int topicBoardIdx = Integer.parseInt(request.getParameter("topicBoardIdx"));
		
		TopicDao tDao = new TopicDao();
		try {
			tDao.deleteTopicBoard(topicBoardIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("teamIdx", teamIdx);
		request.setAttribute("topicIdx", topicIdx);
		request.getRequestDispatcher("/NamooTopic.jsp").forward(request, response);
	}

}
