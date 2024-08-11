package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnterTeamList implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberIdx = (Integer)request.getSession().getAttribute("memberIdx");
		request.setAttribute("memberIdx", memberIdx);
		RequestDispatcher rd = request.getRequestDispatcher("TeamList.jsp");
		rd.forward(request, response);
	}

}
