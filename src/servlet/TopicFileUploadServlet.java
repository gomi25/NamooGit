package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Common;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.TopicDao;

@WebServlet("/TopicFileUploadServlet")
public class TopicFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		int memberIdx = 2;   // TODO : 나중에) 세션에서 읽어오는 걸로.
		
		ServletContext application = getServletContext();
		String path = application.getRealPath("/upload");
		System.out.println("(참고) real path: " + path);	
		
		File filePath = new File(path);	
		if(!filePath.exists()) {		
			filePath.mkdirs();			
		}
		
		MultipartRequest multi = new MultipartRequest(
										request, 	
										path,		
										100*1024*1024,	
										"UTF-8",		
										new DefaultFileRenamePolicy());
		
		Enumeration<?> files = multi.getFileNames();
		String fileObject = (String)files.nextElement(); 
		String fileName = multi.getFilesystemName(fileObject);	// 서버에 저장된 파일 이름
		String fileOriginalName = multi.getOriginalFileName(fileObject);  // 웹브라우저에서 선택한 파일 이름
		long fileSize = 0;
		
		int topicIdx = Integer.parseInt(multi.getParameter("topic_idx"));
	    String topicBoardTitle = multi.getParameter("topic_board_title");
		String topicBoardContent = multi.getParameter("topic_board_content");

		TopicDao tDao = new TopicDao();
		Common com = new Common();

		int fileIdx = 0;
		int topicBoardIdx = 0;
		
	    try {
	        topicBoardIdx = tDao.writeTopicBoard(topicIdx, memberIdx, topicBoardTitle, topicBoardContent);
	        // 작성자를 제외한 멤버 목록 조회
	        List<Integer> unreadMembers = tDao.getTopicMembersExceptAuthor(topicIdx, memberIdx);
	        int[] memberIdxArray = unreadMembers.stream().mapToInt(i -> i).toArray();
	        tDao.addUnreadMember(topicBoardIdx, memberIdxArray);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		if(multi.getFile(fileObject) != null) {	// (1) 파일이 있을 때.
			fileSize = multi.getFile(fileObject).length();  // 파일크기
			int fileTypeIdx = 1;
			if(fileName != null) {
				fileTypeIdx = com.getFileTypeIdxFromFileName(fileName);	
				try {
					fileIdx = com.registerFile(fileName, fileSize+"Byte", memberIdx, fileTypeIdx, null, null, topicIdx, null);
					
					// 리턴받은 topicBoardIdx, fileIdx 넣어서 토픽파일 db추가
					tDao.addFileToTopic(topicBoardIdx, fileIdx);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("filename : " + fileName);
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("NamooTopic.jsp");
		rd.forward(request, response);
	}
}
