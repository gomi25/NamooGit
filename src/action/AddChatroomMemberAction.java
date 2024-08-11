package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ChatDao;

public class AddChatroomMemberAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int chatroomIdx = Integer.parseInt(request.getParameter("chatroomIdx"));

	    String selectedMembersParam = request.getParameter("selected_members");
	    if (selectedMembersParam != null && !selectedMembersParam.trim().isEmpty()) {
	        // 마지막 쉼표가 있는지 확인하고 제거
	        if (selectedMembersParam.endsWith(",")) {
	            selectedMembersParam = selectedMembersParam.substring(0, selectedMembersParam.length() - 1);
	        }
	        
	        String[] memberIdxArray = selectedMembersParam.split(",");
	        
	        int[] memberIds = new int[memberIdxArray.length];
	        for (int i = 0; i < memberIdxArray.length; i++) {
	            memberIds[i] = Integer.parseInt(memberIdxArray[i].trim());
	        }
	        
	        try {
	        	ChatDao cDao = new ChatDao();	
	            cDao.inviteMembersToChatroom(chatroomIdx, memberIds);
	            
	            request.setAttribute("teamIdx", teamIdx);
	            request.setAttribute("chatroomIdx", chatroomIdx);
	            request.getRequestDispatcher("/NamooChat.jsp").forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("선택된 멤버가 없습니다.");
	    }		
	}
}
