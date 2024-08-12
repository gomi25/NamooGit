package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnterHelpCenterAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//헬프센터 메인으로 이동하기
		RequestDispatcher rd = request.getRequestDispatcher("NamooHelpMain.jsp");
		rd.forward(request, response);

	}

}
