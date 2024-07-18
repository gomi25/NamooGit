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


@WebServlet("/UpdateQnaAnswerServlet")
public class UpdateQnaAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청이 들어왔는지 확인
		System.out.println("요청 들어옴");
		
		//다오에 필요한 파라미터 값 선언
		String content = request.getParameter("content");
		int qnaIdx = Integer.parseInt(request.getParameter("qna_idx"));
		System.out.println("content : " + content);
		
		//다오에 있는 메서드 호출 -- > UPDATE 실행.
		//빨간 줄 있으면 try ~ catch로 잡아야 함.
		QnaDao qnaDao = new QnaDao();
		try {
			qnaDao.updateQnaAnswer(content, qnaIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//서버에서 클라이언트로 데이터 보냄 : json 형식으로. (권장 라이브러리: json-simple)
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("content", content);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(jsonObj);
	}
	

	

}
