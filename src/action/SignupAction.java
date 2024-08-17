package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.MemberDto;
import dto.TeamListDto;

public class SignupAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer memberIdx = (Integer) session.getAttribute("memberIdx");

        if (memberIdx == null) {
            response.sendRedirect("NamooLogin.jsp");
            return;
        }

        MemberDao mDao = new MemberDao();
        
        MemberDto memberDto = null;
        try {
            memberDto = mDao.getMemberDtoByMemberIdx(memberIdx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<TeamListDto> teamList = null;
        try {
            teamList = mDao.getListTeamListDto(memberIdx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("memberDto", memberDto);
        request.setAttribute("teamList", teamList);
        
        request.getRequestDispatcher("TeamList.jsp").forward(request, response);
    }
}
