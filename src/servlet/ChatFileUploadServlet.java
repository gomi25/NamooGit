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

import dao.ChatDao;

@WebServlet("/ChatFileUploadServlet")
public class ChatFileUploadServlet extends HttpServlet {
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
										100*1024*1024,		// MAX_UPLOAD_SIZE
										"UTF-8",		
										new DefaultFileRenamePolicy());
		
		String fileObject = null;
		Enumeration<?> files = multi.getFileNames();
		if (files.hasMoreElements()) {
            fileObject = (String) files.nextElement();
        }
		
		String fileName = (fileObject != null) ? multi.getFilesystemName(fileObject) : null;
        String fileOriginalName = (fileObject != null) ? multi.getOriginalFileName(fileObject) : null;
        long fileSize = (fileObject != null && multi.getFile(fileObject) != null) ? multi.getFile(fileObject).length() : 0;

		
		String chatContent = multi.getParameter("chat_content");
		int chatroomIdx = Integer.parseInt(multi.getParameter("chatroom_idx"));

		ChatDao cDao = new ChatDao();
		Common com = new Common();

		int chatIdx = 0;
		try {
            if (fileObject != null && fileName != null) { // 파일이 있을 때
                int fileTypeIdx = com.getFileTypeIdxFromFileName(fileName);
                int fileIdx = com.registerFile(fileName, fileSize + "Byte", memberIdx, fileTypeIdx, null, chatroomIdx, null, null);
                chatIdx = cDao.writeChat(chatroomIdx, memberIdx, chatContent, fileIdx);
                System.out.println("filename : " + fileName);
            } else { // 파일이 없을 때
                chatIdx = cDao.writeChat(chatroomIdx, memberIdx, chatContent, null);
            }

            List<Integer> unreadMembers = cDao.getChatMembersExceptAuthor(chatroomIdx, memberIdx);
            int[] memberIdxArray = unreadMembers.stream().mapToInt(i -> i).toArray();
            cDao.addUnreadChatToMember(chatIdx, memberIdxArray);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: 로깅 프레임워크 사용 권장
        }
		
		RequestDispatcher rd = request.getRequestDispatcher("NamooChat.jsp");
		rd.forward(request, response);
	}
}
