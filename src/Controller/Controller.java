package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ChatroomChoiceAction;
import action.CreateChatroomAction;
import action.CreateTeamAction;
import action.CreateTopicAction;
import action.DeleteTopicAction;
import action.DeleteTopicBoardAction;
import action.DeleteChatroomAction;
import action.LoginCheckAction;
import action.TeamListAction;
import action.TopicChoiceAction;
import action.UpdateTopicBoardAction;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String command = request.getParameter("command");
		System.out.println("Controller command : " + command);
		
		Action action = null;
		
		switch(command) {
//			case "go_topic": action = new GoTopicAction(); break; 
//			case "go_chatroom": action = new GoChatroomAction(); break; 
//			case "go_maintool": action = new GoChatroomAction(); break; 
			case "login_check" : action = new LoginCheckAction(); break;
			case "topic_choice" : action = new TopicChoiceAction(); break;
			case "chatroom_choice" : action = new ChatroomChoiceAction(); break;
			case "create_topic" : action = new CreateTopicAction(); break;
			case "create_chatroom" : action = new CreateChatroomAction(); break;
			case "update_topic_board" : action = new UpdateTopicBoardAction(); break;
			case "delete_topic_board" : action = new DeleteTopicBoardAction(); break;
			case "delete_topic" : action = new DeleteTopicAction(); break;
			case "delete_chatroom" : action = new DeleteChatroomAction(); break;
			
//			case "inviteTopicMember" : action = new AddTopicMemberAction(); break;
//			case "inviteChatroomMember" : action = new AddChatroomMemberAction(); break;
			
			case "team_list" : action = new TeamListAction(); break;
			case "create_team" : action = new CreateTeamAction(); break;
		}
		action.execute(request, response);	// 웹브라우저의 요청 처리
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
