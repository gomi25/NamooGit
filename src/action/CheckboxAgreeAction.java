package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;

public class CheckboxAgreeAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MemberDao mDao = new MemberDao();
		try {
			int memberIdx = mDao.agreeToTerms(1, 0);
			request.setAttribute("memberIdx", memberIdx);
			request.getRequestDispatcher("Signup2.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
