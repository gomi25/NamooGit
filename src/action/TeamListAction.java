package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;
import dto.TeamListDto;

public class TeamListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int memberIdx = Integer.parseInt(request.getSession().getAttribute("memberIdx").toString());

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