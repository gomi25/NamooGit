package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TopicDao;

public class CreateTopicAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int memberIdx = (Integer)session.getAttribute("memberIdx");
		int teamIdx = Integer.parseInt(request.getParameter("teamIdx"));
		int topicIdx = -1;
		
		 try {
			    String name = request.getParameter("name");	// 토픽방 이름
			    String info = request.getParameter("info"); //  토픽방 정보
			    int open = Integer.parseInt(request.getParameter("topic_open")); // 공개 여부
			    String selectedFolderParam = request.getParameter("selected_folder"); // 선택한 폴더
			    int topicFolderIdx = selectedFolderParam.isEmpty() ? 0 : Integer.parseInt(selectedFolderParam);
		        
			    // 새로 생성된 토픽idx
			    TopicDao tDao = new TopicDao();
			    topicIdx = tDao.createTopic(name, info, teamIdx, open, memberIdx);	
				
		        if (topicFolderIdx != 0) {
				    tDao.addTopicToFolder(topicFolderIdx, topicIdx); 
				}
				
	 	        request.setAttribute("teamIdx", teamIdx);
	 		    request.setAttribute("topicIdx", topicIdx);
				request.getRequestDispatcher("/NamooTopic.jsp").forward(request, response);

//				RequestDispatcher disp = request.getRequestDispatcher("/NamooTopic.jsp");
//		        disp.forward(request, response);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }		
		
	}

}
