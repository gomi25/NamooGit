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
import action.CheckboxAgreeAction;
import action.ConsumerCaseAction;
import action.ConsumerCaseInfoAction;
import action.ConsumerCaseListAction;
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
import action.EnterAccountSettingPageAction;
import action.EnterConsumerCaseAction;
import action.EnterHelpCenterAction;
import action.EnterLoginPageAction;
import action.EnterMainPageAction;
import action.EnterMainPageLogined;
import action.EnterQnaAction;
import action.EnterSignup1PageAction;
import action.EnterTeamList;
import action.EnterTeamListFromAction;
import action.InsertMemberInfoAction;
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
//			case "go_topic": action = new GoTopicAction(); break; 
//			case "go_chatroom": action = new GoChatroomAction(); break; 
//			case "go_maintool": action = new GoChatroomAction(); break; 
			case "login_check" : action = new LoginCheckAction(); break; 					// 로그인 체크 커맨드
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


//			case "inviteTopicMember" : action = new AddTopicMemberAction(); break;
//			case "inviteChatroomMember" : action = new AddChatroomMemberAction(); break;
			//잠시 영역좀 나누겠습니다. hj-------------------------------------------------------
			case "enter_team_list" : action = new EnterTeamList(); break;
			case "enter_help_center" : action = new EnterHelpCenterAction(); break; 		// 헬프센터로 이동
			case "enter_consumer_case" : action = new EnterConsumerCaseAction(); break;
			case "enter_qna" : action = new EnterQnaAction(); break;
			case "enter_login_page" : action = new EnterLoginPageAction(); break;
			case "enter_main_page" : action = new EnterMainPageAction(); break;
			case "enter_signup1_page" : action = new EnterSignup1PageAction(); break;
			case "enter_account_setting_page" : action = new EnterAccountSettingPageAction();break;
			case "enter_main_page_logined" : action = new EnterMainPageLogined(); break;
			
			
			//---------------------------------------------------------------------------
			case "create_qna" : action = new CreateQnaAction(); break;
			case "create_qna_answer" : action = new CreateQnaAnswerAction(); break;
			case "create_project_form" : action = new CreateProjectFormAction(); break;
			case "create_project" : action = new CreateProject(); break;
			case "project_list" : action = new ProjectListAction(); break;
			
			// ------------------- 하늘 -------------------
			case "checkbox_agree" : action = new CheckboxAgreeAction(); break;
			case "insert_member_info" : action = new InsertMemberInfoAction(); break;
			case "enter_team_list_from_signup" : action = new EnterTeamListFromAction(); break;
			case "create_team" : action = new CreateTeamAction();
			case "consumer_case" : action = new ConsumerCaseAction(); break;
			case "consumer_case_info" : action = new ConsumerCaseInfoAction(); break;
			case "consumer_case_list" : action = new ConsumerCaseListAction(); break;
			
		}
		action.execute(request, response);	// 웹브라우저의 요청 처리
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
