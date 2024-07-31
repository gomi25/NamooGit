package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.ProjectDao;

@WebServlet("/AjaxBookmarkOffServlet")
public class AjaxBookmarkOffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 요청이 들어왔는지 확인.
		System.out.println("get 요청이 들어옴.");
		
		// 2. 요청에 실린 데이터가 잘 왔는지 확인(서버).
		int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
		int projectIdx = Integer.parseInt(request.getParameter("project_idx"));
		System.out.println(memberIdx + " , " + projectIdx);
		
		ProjectDao pDao = new ProjectDao();
		try {
			pDao.bookmarkOffProject(memberIdx, projectIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 3. 응답에 실어주는 데이터가 잘 갔는지 확인(클라이언트 - success함수에서).
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
		writer.print(obj);

	}
}
