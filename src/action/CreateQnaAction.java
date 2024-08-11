package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QnaDao;

public class CreateQnaAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String name = request.getParameter("name");
	   String phoneNumber = request.getParameter("phone_number");
	   String position = request.getParameter("position");
	   String email = request.getParameter("email");
	   int industry = Integer.parseInt(request.getParameter("industry"));
	   int count = Integer.parseInt(request.getParameter("count"));
	   int question = Integer.parseInt(request.getParameter("question"));
	   int agreement = Integer.parseInt(request.getParameter("agreement"));
	   String content = request.getParameter("content");
	   
	   QnaDao qDao = new QnaDao();
	   
	   try {
		qDao.addQna3(name, phoneNumber, position, email, industry, count, question, agreement, 0, content);
	   } catch (Exception e) {
		e.printStackTrace();
	   }
	   
	   request.getRequestDispatcher("/Qna.jsp").forward(request, response);
 
	}

}
