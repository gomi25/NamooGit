package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.Common;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.TopicDao;

@WebServlet("/TopicCommentFileUpdateServlet")
public class TopicCommentFileUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int memberIdx = 2; // 예시 값
        
        ServletContext application = getServletContext();
        String path = application.getRealPath("/upload");
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        MultipartRequest multi = new MultipartRequest(request, path, 100*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
        Enumeration<?> files = multi.getFileNames();
        String fileObject = (String) files.nextElement();
        String fileName = multi.getFilesystemName(fileObject);
        String fileOriginalName = multi.getOriginalFileName(fileObject);
        long fileSize = 0;

        int topicIdx = Integer.parseInt(multi.getParameter("topicIdx"));
        int topicCommentIdx = Integer.parseInt(multi.getParameter("topic_comment_idx"));

        TopicDao tDao = new TopicDao();
        Common com = new Common();

        int fileIdx = 0;
        JSONObject jsonObj = new JSONObject();

        if (fileName != null) {
            fileSize = multi.getFile(fileObject).length();
            int fileTypeIdx = com.getFileTypeIdxFromFileName(fileName);
            try {
                fileIdx = com.registerFile(fileName, fileSize + "Byte", memberIdx, fileTypeIdx, null, null, topicIdx, null);
                tDao.updateTopicCommentFile(topicCommentIdx, fileIdx);
                jsonObj.put("success", true);
                jsonObj.put("fileId", fileIdx);
            } catch (Exception e) {
                e.printStackTrace();
                jsonObj.put("success", false);
                jsonObj.put("message", e.getMessage());
            }
        } else {
            jsonObj.put("success", false);
            jsonObj.put("message", "파일 업로드 실패");
        }

        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(jsonObj);
    }

//	원본 7.29
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		int memberIdx = 2;   
//		
//		ServletContext application = getServletContext();
//		String path = application.getRealPath("/upload");
//		System.out.println("(참고) real path: " + path);	
//		
//		File filePath = new File(path);	
//		if(!filePath.exists()) {		
//			filePath.mkdirs();			
//		}
//		
//		MultipartRequest multi = new MultipartRequest(
//										request, 	
//										path,		
//										100*1024*1024,	
//										"UTF-8",		
//										new DefaultFileRenamePolicy());
//		
//		Enumeration<?> files = multi.getFileNames();
//		String fileObject = (String)files.nextElement(); 
//		String fileName = multi.getFilesystemName(fileObject);	// 서버에 저장된 파일 이름
//		String fileOriginalName = multi.getOriginalFileName(fileObject);  // 웹브라우저에서 선택한 파일 이름
//		long fileSize = 0;
//		
//		int topicIdx = Integer.parseInt(multi.getParameter("topicIdx"));
//		int topicCommemtIdx = Integer.parseInt(multi.getParameter("topic_comment_idx"));
//
//		TopicDao tDao = new TopicDao();
//		Common com = new Common();
//
//		int fileIdx = 0;
//		
//		fileSize = multi.getFile(fileObject).length();  // 파일크기
//		int fileTypeIdx = 1;
//		if(fileName != null) {
//			fileTypeIdx = com.getFileTypeIdxFromFileName(fileName);	
//			try {
//				fileIdx = com.registerFile(fileName, fileSize+"Byte", memberIdx, fileTypeIdx, null, null, topicIdx, null);
//				tDao.updateTopicCommentFile(topicCommemtIdx, fileIdx);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("filename : " + fileName);
//		}
//			
//		
//		RequestDispatcher rd = request.getRequestDispatcher("NamooTopic.jsp");
//		rd.forward(request, response);
//	
//	}

}
