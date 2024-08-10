package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TopicDao;

public class UpdateTopicBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int topicIdx = Integer.parseInt(request.getParameter("topicIdx"));
		int topicBoardIdx= Integer.parseInt(request.getParameter("topicBoardIdx"));
		String title = request.getParameter("topic_board_title");
		String content = request.getParameter("topic_board_content");
		
		TopicDao tDao = new TopicDao();
		try {
			tDao.updateTopicBoard(topicBoardIdx, title, content);
			request.setAttribute("teamIdx", teamIdx);
			request.setAttribute("topicIdx", topicIdx);
			request.getRequestDispatcher("/NamooTopic.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
