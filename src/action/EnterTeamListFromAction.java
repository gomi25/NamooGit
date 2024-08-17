package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;

public class EnterTeamListFromAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
		String veryficationCode = request.getParameter("user_input_code");
		
		MemberDao mDao = new MemberDao();
		try {
			boolean isVerified = mDao.enterVerifyCode(memberIdx, veryficationCode);
			
			if (isVerified) {
				response.sendRedirect("Controller?command=team_list&memberIdx" + memberIdx);
			} else {
				response.sendRedirect("Signup3.jsp?error=invaild_code");
			}
		} catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Signup3.jsp?error=exception");
		}
	}
}