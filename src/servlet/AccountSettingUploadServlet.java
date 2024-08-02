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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.NamooMemberDao;

@WebServlet("/AccountSettingUploadServlet")
public class AccountSettingUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberIdx = 1;  // TODO : 나중에) 세션에서 읽어와야 돼요.

		ServletContext application = getServletContext();
		String path = application.getRealPath("/upload");
		System.out.println("(참고) real path : " + path);			 			// 파일이 저장될 절대경로(C: \~~~)
		
		File filePath = new File(path);										// 경로에 해당되는 FIle 객체 생성
		if(!filePath.exists()) {											// .exists() ▶  해당경로가 존재하면 true를 리턴
			filePath.mkdirs();												// dir : '디렉터리'(=폴더)
		}
		
		// 그래서 파일이 언제 저장? = MultipartRequest 객체가 생성될 때!
		MultipartRequest multi = new MultipartRequest(
										request,							// 요청 객체
										path,								// 파일이 저장될 절대경로
										100*1024*1024,						// 파일 최대 크기(ex. 100MB)
										"UTF-8",							// 한글 인코딩 설정 -> 파일명 한글깨짐 방지
										new DefaultFileRenamePolicy()		// 기본적인 'rename 정책'
				);
		//<?>: 꺽쇠 안에 뭐든 올 수 있다
		Enumeration<?> files = multi.getFileNames();
		String fileObject = (String)files.nextElement();
		String filename = multi.getFilesystemName(fileObject);				// 서버에 저장된 파일 이름
		String fileOriginalName = multi.getOriginalFileName(fileObject);	// 웹 브라우저에서 선택한 파일
		long fileSize = multi.getFile(fileObject).length();					// 파일 크기
		
		System.out.println(filename);

		// dao 처리. (update)
		NamooMemberDao memberDao = new NamooMemberDao();
		try {
			memberDao.accountSettingChangeProfilePic(memberIdx, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("NamooAccountSetting.jsp");
		rd.forward(request, response);
	}
}
