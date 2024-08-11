package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDao;

public class DeleteChatroomAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberIdx = (Integer)request.getSession().getAttribute("memberIdx");
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int chatroomIdx = Integer.parseInt(request.getParameter("chatroomIdx"));
		
		ChatDao cDao = new ChatDao();
		try {
            // 채팅방의 멤버 수를 확인하는 메서드 호출
            int memberCount = cDao.getChatroomMemberCount(chatroomIdx);

            if (memberCount == 1) {  // 자신만 채팅방에 남아있는 경우
                // 채팅방 삭제 메서드 호출
                cDao.deleteChatroom(chatroomIdx);
            } else {
                // 채팅방 나가기 메서드 호출
                cDao.leaveChatroom(chatroomIdx, memberIdx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		request.setAttribute("teamIdx", teamIdx);
		RequestDispatcher disp = request.getRequestDispatcher("/NamooMainTool.jsp");
		disp.forward(request, response);	
		
	}
}
