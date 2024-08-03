package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.BookmarkDao;


@WebServlet("/AjaxMemberBookmarkOffServlet")
public class AjaxMemberBookmarkOffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   // POST 방식에서 한글깨짐 방지. (request 사용 전에)
		System.out.println("해제하고싶다.");
		
		String something = request.getParameter("something"); // ?
		int idx = Integer.parseInt(request.getParameter("idx"));
		int loginMemberIdx = 2;  
		// 나중에 이걸로 바꿔야함
		//int memberIdx = (Integer)session.getAttribute("loginMemberIdx");

		BookmarkDao bDao = new BookmarkDao();
		try {
			bDao.removeBookmarkSomething(loginMemberIdx, something, idx);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		response.setContentType("application/json; charset=utf-8");   
		PrintWriter writer = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", "성공");
		writer.print(jsonObj);
	}

}
