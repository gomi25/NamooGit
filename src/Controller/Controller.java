package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ChatroomChoiceAction;
import action.CreateTopicAction;
import action.WriteTopicBoardAction;
import action.LoginCheckAction;
import action.TopicChoiceAction;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String command = request.getParameter("command");
		System.out.println("Controller command : " + command);
		
		Action action = null;
		
		switch(command) {
			case "login_check" : action = new LoginCheckAction(); break;
			case "topic_choice" : action = new TopicChoiceAction(); break;
			case "chatroom_choice" : action = new ChatroomChoiceAction(); break;
			case "create_topic" : action = new CreateTopicAction(); break;
			
//			case "write_topic_board" : action = new WriteTopicBoardAction(); break;
//			case "create_topic_folder" : action = new CreateTopicFolderAction(); break;
//			case "3" : break;
		}
		action.execute(request, response);	// 웹브라우저의 요청 처리
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
