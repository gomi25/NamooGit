package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProjectDao;

public class CreateProject implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//int writer = (Integer)session.getAttribute("member_idx"); // 나중에 바꾸기
		//int teamIdx = (Integer)session.getAttribute("team_idx"); // 나중에 바꾸기
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int writer = Integer.parseInt(request.getParameter("memberIdx"));
		int colorIdx = Integer.parseInt(request.getParameter("color_select"));
		String projectName = request.getParameter("project_name");
		
	    

		
		ProjectDao pDao = new ProjectDao();
		try {
			pDao.createProject(projectName, teamIdx, colorIdx, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		request.getRequestDispatcher("Controller?command=project_list").forward(request, response);
	}

}
