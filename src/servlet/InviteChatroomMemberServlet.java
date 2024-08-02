package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDao;

@WebServlet("/InviteChatroomMemberServlet")
public class InviteChatroomMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");		
		
		int memberIdx = 2;   // TODO : 나중에) 세션에서 읽어오는 걸로.
		int teamIdx = 1;
		int chatroomIdx = Integer.parseInt(request.getParameter("chatroom_idx"));
		// 요청 파라미터에서 멤버 인덱스 배열을 가져오기
		String[] memberIdxParams = request.getParameterValues("selected_members");
		int[] memberIdxArray = new int[memberIdxParams.length];

	    for (int i = 0; i < memberIdxParams.length; i++) {
	        String[] splitParams = memberIdxParams[i].split(",");
	        for (String splitParam : splitParams) {
	            memberIdxArray[i] = Integer.parseInt(splitParam.trim());
	        }
	    }
	    
	    ChatDao cDao = new ChatDao();		
	    try {
	    	// 선택한 사람 초대하기
			cDao.inviteMembersToChatroom(chatroomIdx, memberIdxArray);
			RequestDispatcher disp = request.getRequestDispatcher("/NamooChat.jsp");
	        disp.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
