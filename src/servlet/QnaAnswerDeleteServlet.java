package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.QnaDao;


@WebServlet("/QnaAnswerDeleteServlet")
public class QnaAnswerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//요청이 들어왔는지 확인
		System.out.println("요청 들어옴");
		
		//다오에 필요한 파라미터 값 선언
		int answerIdx = Integer.parseInt(request.getParameter("answer_idx"));
		QnaDao qnaDao = new QnaDao();
		try {
			qnaDao.changeToUnrepliedByAnswerIdx(answerIdx);  // answerIdx --> qnaIdx --> update with qnaIdx
			qnaDao.deleteQnaAnswer(answerIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//서버에서 클라이언트로 데이터 보냄 : json 형식으로. (권장 라이브러리: json-simple)
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("response", "성공");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
	}

}
