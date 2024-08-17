package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;

public class CreateTeamAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer memberIdx = (Integer) session.getAttribute("memberIdx");
		
		// 세션에 memberIdx가 없으면 로그인 페이지로 리다이렉트
        if (memberIdx == null) {
            response.sendRedirect("NamooLogin.jsp");
            return;
        } 
        
        request.setCharacterEncoding("utf-8");
        
        String teamName = request.getParameter("teamName");
        String teamDomain = request.getParameter("teamDomain");
        
        MemberDao mDao = new MemberDao();
        
        try {
        	int teamIdx = mDao.createTeam(teamName, teamDomain + ".jandi.com", "https://jandi-box.com/teams/0/logo.png?timestamp=20190628");
        	
        	mDao.addTeamFirstMember(teamIdx, memberIdx);
        	
        	response.setContentType("text/html; charset=UTF-8");
        	response.getWriter().println("<script>alert('팀이 생성되었습니다.') location.href='NamooMainTool.jsp';</script>");
        } catch (Exception e) {
        	e.printStackTrace();
        	response.setContentType("text/html; charset=UTF-8");
        	response.getWriter().println("<script>alert('팀 생성 중 오류가 발생했습니다.'); history.back();</script>");
        }
	}
}
