package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.BookmarkDao;
import dao.OrganizationalChartDao;
import dto.OrganizationalMemberListDto;


@WebServlet("/AjaxMemberBookmarkOnServlet")
public class AjaxMemberBookmarkOnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get요청이 들어옴");
		
		String something = request.getParameter("something");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int loginMemberIdx = 2;   
		// 나중에 이걸로 바꿔야함
		//int memberIdx = (Integer)session.getAttribute("loginMemberIdx");
		
		BookmarkDao bDao = new BookmarkDao();
		
		try {
			bDao.addBookmarkSomething(loginMemberIdx, something, idx);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
		writer.print(obj);
	}
}
