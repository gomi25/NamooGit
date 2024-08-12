package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QnaDao;

public class CreateQnaAnswerAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int qna_idx = Integer.parseInt(request.getParameter("qna_idx"));
		String content = request.getParameter("content");
		
		QnaDao qDao = new QnaDao();
		
		try {
			qDao.addQnaAnswer(qna_idx, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/Qna.jsp").forward(request, response);
		
	}

}
