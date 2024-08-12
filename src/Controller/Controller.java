package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.AddChatroomMemberAction;
import action.AddTopicMemberAction;
import action.ChatroomChoiceAction;
import action.CreateChatroomAction;
import action.CreateProject;
import action.CreateProjectFormAction;
import action.CreateQnaAction;
import action.CreateQnaAnswerAction;
import action.CreateTeamAction;
import action.CreateTopicAction;
import action.DeleteChatroomAction;
import action.DeleteTopicAction;
import action.DeleteTopicBoardAction;
import action.EnterTeamList;
import action.LoginCheckAction;
import action.ProjectListAction;
import action.RemoveChatroomMemberAction;
import action.RemoveTopicMemberAction;
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
			case "login_check" : action = new LoginCheckAction(); break;
			case "topic_choice" : action = new TopicChoiceAction(); break;
			case "chatroom_choice" : action = new ChatroomChoiceAction(); break;
			case "create_topic" : action = new CreateTopicAction(); break;
			case "create_chatroom" : action = new CreateChatroomAction(); break;
			case "update_topic_board" : action = new UpdateTopicBoardAction(); break;
			case "delete_topic_board" : action = new DeleteTopicBoardAction(); break;
			case "delete_topic" : action = new DeleteTopicAction(); break;
			case "delete_chatroom" : action = new DeleteChatroomAction(); break;
			case "inviteTopicMember" : action = new AddTopicMemberAction(); break;
			case "inviteChatroomMember" : action = new AddChatroomMemberAction(); break;
			case "remove_topic_member" : action = new RemoveTopicMemberAction(); break;
			case "remove_chatroom_member" : action = new RemoveChatroomMemberAction(); break;

			case "enter_team_list" : action = new EnterTeamList(); break;
			
			case "create_team" : action = new CreateTeamAction(); break;
			
			case "create_qna" : action = new CreateQnaAction(); break;
			case "create_qna_answer" : action = new CreateQnaAnswerAction(); break;
			case "create_project_form" : action = new CreateProjectFormAction(); break;
			case "create_project" : action = new CreateProject(); break;
			case "project_list" : action = new ProjectListAction(); break;
		}
		action.execute(request, response);	// 웹브라우저의 요청 처리
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
