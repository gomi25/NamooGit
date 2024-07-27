package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

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
										100*1024*1024,	
										"UTF-8",		
										new DefaultFileRenamePolicy());
		
		Enumeration<?> files = multi.getFileNames();
		String fileObject = (String)files.nextElement();
		String fileName = multi.getFilesystemName(fileObject);	// 서버에 저장된 파일 이름
		String fileOriginalName = multi.getOriginalFileName(fileObject);  // 웹브라우저에서 선택한 파일 이름
		long fileSize = 0;
		String chatContent = multi.getParameter("chat_content");
		int chatroomIdx = Integer.parseInt(multi.getParameter("chatroom_idx"));

		ChatDao dao = new ChatDao();
		Common com = new Common();

		int fileIdx = 0;
		if(multi.getFile(fileObject) != null) {	// (1) 파일이 있을 때.
			fileSize = multi.getFile(fileObject).length();  // 파일크기
			int fileTypeIdx = 1;
			if(fileName != null) {
				fileTypeIdx = com.getFileTypeIdxFromFileName(fileName);	// 파일타입idx 리턴
				try {
					// 파일 db추가 및 파일idx 리턴
					fileIdx = com.registerFile(fileName, fileSize+"Byte", memberIdx, fileTypeIdx, null, chatroomIdx, null, null);
					// 리턴받은 파일idx 넣어서 채팅글 db추가
					dao.writeChat(chatroomIdx, memberIdx, chatContent, fileIdx);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("filename : " + fileName);
			}
			
		} else {	// (2) 파일이 없을 때.
			try {
				dao.writeChat(chatroomIdx, memberIdx, chatContent, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("NamooChat.jsp");
		rd.forward(request, response);
	}
}
