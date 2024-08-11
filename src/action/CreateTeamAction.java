package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;

public class CreateTeamAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int memberIdx = Integer.parseInt(request.getSession().getAttribute("memberIdx").toString());
        
        String teamName = request.getParameter("teamName");
        String teamDomain = request.getParameter("teamDomain");
        
        MemberDao mDao = new MemberDao();
        int teamIdx = 0;
		try {
			teamIdx = mDao.createTeam(teamName, teamDomain + ".jandi.com", "https://jandi-box.com/teams/0/logo.png?timestamp=20190628");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        try {
			mDao.addTeamFirstMember(teamIdx, memberIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        response.sendRedirect("Controller?command=team_list");
    }
}
