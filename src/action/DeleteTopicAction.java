package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TopicDao;

public class DeleteTopicAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int topicIdx = Integer.parseInt(request.getParameter("topicIdx"));
		
		TopicDao tDao = new TopicDao();
		try {
	        tDao.deleteTopic(topicIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("teamIdx", teamIdx);
		RequestDispatcher disp = request.getRequestDispatcher("/NamooMainTool.jsp");
		disp.forward(request, response);
	}
}
