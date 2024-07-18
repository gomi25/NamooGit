package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.HelpPostDto;
import dto.ShowCategoryNameAndTitleDto;
import dto.ShowHelpPostCategoryDto;

public class NamooHelpCenterDao {
	Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver"; 	// 드라이버 정보
	 	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속정보
	 	String dbId = "namoo";
	 	String dbPw = "7777";
	 	Class.forName(driver);
	 	Connection conn = DriverManager.getConnection(url, dbId, dbPw);
	 	
	 	return conn;
	}
	/* ===============================헬프센터==================================== */
	//19. 기사 검색
	public ArrayList<HelpPostDto> getSearchedHelpPost(String keyword) throws Exception{ //모든 DTO를 가지고 오는 메서드
		ArrayList<HelpPostDto> listRet = new ArrayList<HelpPostDto>();
		Connection conn = getConnection();
		String sql ="SELECT * FROM( SELECT title, content, post_idx, help_category_idx" + 
					" FROM help_post WHERE content LIKE ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + keyword + "%");
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			//rs가 첫째 행(row) 바로 위에 생김
			//rs.next : 1) 다음행을 가리킴 2)있으면 true를 리턴, 없으면 false를 리턴 
			String title = rs.getString("title");
			String content = rs.getString("content");
			int helpCategoryIdx = rs.getInt("help_category_idx");
			int postIdx = rs.getInt("post_idx");
			HelpPostDto dto = new HelpPostDto(postIdx, helpCategoryIdx, title, content);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	//20. 이용가이드(대표글 표시)
	public ArrayList<HelpPostDto> mainHelpPost() throws Exception{ //모든 DTO를 가지고 오는 메서드
		ArrayList<HelpPostDto> listRet = new ArrayList<HelpPostDto>();
		String sql ="SELECT title, post_idx, help_category_idx FROM help_post WHERE title like '%사용방법%'" + 
					" UNION SELECT title, post_idx, help_category_idx FROM help_post WHERE title like '%채팅을%'"; 
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			//rs가 첫째 행(row) 바로 위에 생김
			//rs.next : 1) 다음행을 가리킴 2)있으면 true를 리턴, 없으면 false를 리턴 
			int postIdx = rs.getInt("post_idx");
			int helpCategoryIdx = rs.getInt("help_category_idx");
			String title = rs.getString("title");
			HelpPostDto dto = new HelpPostDto(postIdx, helpCategoryIdx, title, null);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	//21.자료 카테고리 표시
	public ArrayList<ShowHelpPostCategoryDto> showHelpPostCategory() throws Exception{ //모든 DTO를 가지고 오는 메서드
		ArrayList<ShowHelpPostCategoryDto> listRet = new ArrayList<ShowHelpPostCategoryDto>();
		String sql ="SELECT h.icon_img_url, h.title, h.subtitle, h.help_idx, ( " + 
					" SELECT count(*) " + 
					" FROM help h2 INNER JOIN help_category hc " + 
					" ON h2.help_idx = hc.help_idx INNER JOIN help_post hp " + 
					" ON hc.help_category_idx = hp.help_category_idx " + 
					" WHERE h2.help_idx=h.help_idx " + 
					" ) post_count " + 
					"FROM help h " + 
					"ORDER BY h.help_idx"; 
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String iconImgUrl = rs.getString("icon_img_url");
			String title = rs.getString("title");
			String subtitle = rs.getString("subtitle");
			int helpIdx = rs.getInt("help_idx");
			if(subtitle==null || "NULL".equals(subtitle))
				subtitle = "";
			int count = rs.getInt("post_count");
			ShowHelpPostCategoryDto dto = new ShowHelpPostCategoryDto(iconImgUrl, title, subtitle, count, helpIdx);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	//21-1.자료 카테고리 표시(하나만)
	public ArrayList<ShowHelpPostCategoryDto> showHelpPostCategoryOne(int helpIdx) throws Exception{ //모든 DTO를 가지고 오는 메서드
		ArrayList<ShowHelpPostCategoryDto> listRet = new ArrayList<ShowHelpPostCategoryDto>();
		String sql ="SELECT h.icon_img_url, h.title, h.subtitle, ( " + 
				" SELECT count(*) " + 
				" FROM help h2 INNER JOIN help_category hc " + 
				" ON h2.help_idx = hc.help_idx INNER JOIN help_post hp " + 
				" ON hc.help_category_idx = hp.help_category_idx " + 
				" WHERE h2.help_idx=h.help_idx " + 
				" ) post_count " + 
				"FROM help h " + 
				"WHERE help_idx = ?" +
				"ORDER BY h.help_idx"; 
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, helpIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String iconImgUrl = rs.getString("icon_img_url");
			String title = rs.getString("title");
			String subtitle = rs.getString("subtitle");
			if(subtitle==null || "NULL".equals(subtitle))
				subtitle = "";
			int count = rs.getInt("post_count");
			ShowHelpPostCategoryDto dto = new ShowHelpPostCategoryDto(iconImgUrl, title, subtitle, count, helpIdx);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	
	//22. 카테고리 명, 글제목 표시
	/*
	public ArrayList<ShowCategoryNameAndTitleDto> showCategoryNameAndTitle(int helpIdx) throws Exception{
		ArrayList<ShowCategoryNameAndTitleDto> listRet = new ArrayList<ShowCategoryNameAndTitleDto>();
		Connection conn = getConnection();
		String sql = "SELECT c.help_category_idx, p.post_idx, c.name, p.title" + 
				" FROM help h INNER JOIN help_category c" + 
				" ON h.help_idx = c.help_idx INNER JOIN  help_post p" + 
				" ON c.help_category_idx = p.help_category_idx" + 
				" AND c.help_idx = ?" +
				" ORDER BY c.help_category_idx, post_idx";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, helpIdx);
		ResultSet rs = pstmt.executeQuery();

		while(rs.next()) {
			int helpCategoryIdx = rs.getInt("help_category_idx");
			int postIdx = rs.getInt("post_idx");
			String categoryName = rs.getString("name");
			String title = rs.getString("title");
			ShowCategoryNameAndTitleDto dto = new ShowCategoryNameAndTitleDto(helpCategoryIdx, postIdx, categoryName, title);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	*/
	// 22-1 new) getListHelpCategoryName(int helpIdx) : 카테고리 명 (리스트)
	public ArrayList<String> getListHelpCategoryName(int helpIdx) throws Exception {
		ArrayList<String> listRet = new ArrayList<String>();
		String sql = "SELECT DISTINCT c.help_category_idx, c.name" + 
					"	FROM help h INNER JOIN help_category c" + 
					"	ON h.help_idx = c.help_idx INNER JOIN  help_post p" + 
					"	ON c.help_category_idx = p.help_category_idx" + 
					"	AND c.help_idx = ?" + 
					"	ORDER BY c.help_category_idx";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, helpIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			listRet.add(rs.getString("name"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	
	// 22-2) getListHelpPostTitle(int helpIdx, String helpCategoryName) : 글제목 (리스트)
	public ArrayList<ShowCategoryNameAndTitleDto> getListHelpPostTitle(int helpIdx, String helpCategoryName) throws Exception {
		ArrayList<ShowCategoryNameAndTitleDto> listRet = new ArrayList<ShowCategoryNameAndTitleDto>();
		String sql = "SELECT c.help_category_idx, p.post_idx, p.title " + 
					"	FROM help h INNER JOIN help_category c " + 
					"	ON h.help_idx = c.help_idx INNER JOIN  help_post p " + 
					"	ON c.help_category_idx = p.help_category_idx " + 
					"	AND c.help_idx = ?  " + 
					"	AND c.name = ? " + 
					"	ORDER BY c.help_category_idx, post_idx";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, helpIdx);
		pstmt.setString(2, helpCategoryName);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int helpCategoryIdx = rs.getInt("help_category_idx");
			int postIdx = rs.getInt("post_idx");
			String title = rs.getString("title");
			ShowCategoryNameAndTitleDto dto = new ShowCategoryNameAndTitleDto(helpCategoryIdx, postIdx, helpCategoryName, title);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}

	// post_idx 값 -----> HelpPostDto 객체 리턴.
	public HelpPostDto getHelpPostContentDto(int postIdx) throws Exception {
		String sql = "SELECT post_idx, help_category_idx, title, content " + 
					" FROM help_post " + 
					" WHERE post_idx = ?";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, postIdx);
		ResultSet rs = pstmt.executeQuery();
		HelpPostDto dtoRet = null;
		if(rs.next()) {
			int helpCategoryIdx = rs.getInt("help_category_idx");
			String title = rs.getString("title");
			String content = rs.getString("content");
			dtoRet = new HelpPostDto(postIdx, helpCategoryIdx, title, content);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return dtoRet;
	}
}













