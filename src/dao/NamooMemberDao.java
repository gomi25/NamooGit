package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.MemberImageDto;

public class NamooMemberDao {
	Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver"; 	// 드라이버 정보
	 	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속정보
	 	String dbId = "namoo";
	 	String dbPw = "7777";
	 	Class.forName(driver);
	 	Connection conn = DriverManager.getConnection(url, dbId, dbPw);
	 	
	 	return conn;
	}
	
	// loginCheck(String, String): 로그인
	// 파라미터: email, pw
	// 리턴: 성공시 1의 값을 리턴
	public boolean loginCheck(String email, String pw) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT COUNT(*) FROM member WHERE email=? AND pw=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, email);
		pstmt.setString(2, pw);
		
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()){
			result = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return result==1;
	}
	
	// memberIdxFromEmail(String): 이메일 주소에 따른 memberIdx 리턴
	// 파라미터: email
	// 리턴: 성공시 1의 값을 리턴
	public int memberIdxFromEmail(String email) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT member_idx from member WHERE email = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,email);
		
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()) {
			result = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return result;
	}
	
	// emailCheck(int): 비밀번호 찾기
	// 파라미터: member_idx, email
	// 리턴: 성공시 1의 값을 리턴
	public boolean emailCheck(int memberIdx, String email) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT COUNT(*) FROM member WHERE member_idx=? AND email=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setString(2, email);
		
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()) {
			result = rs.getInt(1);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result == 1;
	}
	// getPwFromEmail(int): 비밀번호 찾기
	// 파라미터: member_idx, email
	// 리턴: pw
	public String getPwFromEmail(String email) throws Exception {
		String sql = "SELECT pw FROM member WHERE email=?";
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, email);
		ResultSet rs = pstmt.executeQuery();
		String ret = "";
		if(rs.next()) {
			ret = rs.getString("pw");
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return ret;
	}
	
	// showProfilePic(int): 프사 표시 select 문
	// 파라미터: member_idx
	// 리턴: profile_pic_url(파라미터 값에 해당하는 멤버의 프사)
	public String showProfilePic(int memberIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT profile_pic_url FROM member"
					+ " WHERE member_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		
		ResultSet rs = pstmt.executeQuery();
		String result = "";
		if(rs.next()) {
			result = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// showName(int): 이름 표시 select 문
	// 파라미터: member_idx
	// 리턴 : name
	public String showName(int memberIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT name FROM member WHERE member_idx=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		
		ResultSet rs = pstmt.executeQuery();
		String result = "";
		if(rs.next()){
			result = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// showEmail(int): 이메일 표시 select 문
	// 파라미터: member_idx
	// 리턴: email
	 public String showEmail(int memberIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT email FROM member WHERE member_idx=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		
		ResultSet rs = pstmt.executeQuery();
		String result = "";
		if(rs.next()){
			result = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	 
	 // showPhoneNumber(int): 전화번호 표시 select 문
	 // 파라미터: member_idx
	 // 리턴: phone_number
	 public String showPhoneNumber(int memberIdx) throws Exception{
		 Connection conn = getConnection();
		 String sql = "SELECT phone_number FROM member WHERE member_idx=?";
		 
		 PreparedStatement pstmt = conn.prepareStatement(sql);
		 pstmt.setInt(1, memberIdx);
		 
		 ResultSet rs = pstmt.executeQuery();
		 String result = "";
		 if(rs.next()){
			 result = rs.getString(1);
		 }
		 rs.close();
		 pstmt.close();
		 conn.close();
		 
		 return result;
	 }
	 
	// accountSettingChangeProfilePic(int,String): 프사 변경(직접 선택한 사진 변경)
	// 파라미터: member_idx, profilePicUrl
	public void accountSettingChangeProfilePic(int memberIdx, String profilePicUrl) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE member"
					+ " SET profile_pic_url = ?" 
					+ " WHERE member_idx = ?"; 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, profilePicUrl);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	
	
	// accountSettingChangeProfileDefault(int): 프사 변경 : 기본 이미지로 변경하기
	// 파라미터: member_idx
	public void accountSettingChangeProfileDefault(int memberIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE member" 
					+ " SET profile_pic_url = 'https://jandi-box.com/assets/ic-profile.png'"
					+ " WHERE member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// accountSettingChangeName(String, int): 이름 변경
	// 파라미터: name, member_idx
	public void accountSettingChangeName(String name, int memberIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE member"  
					+ " SET name = ?" 
					+ " WHERE member_idx = ?" ; 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// accountSettingChangePassword(String, int, String): n비밀번호 변경
	// 파라미터: newPw, memberIdx, pw
	public int accountSettingChangePassword(String newPw, int memberIdx, String pw) throws Exception {
		Connection conn = getConnection();
		String sql = "Update member"
					+ " SET pw = ?"
					+ " WHERE member_idx = ?" 
					+ " AND pw = ?" ;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, newPw);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, pw);
		int ret = pstmt.executeUpdate();	// 영향받은 행의 개수를 리턴함.
		
		pstmt.close();
		conn.close();
		
		return ret;   // 1 : 성공, 0 : 실패
	}

	// accountSettingLeaveNamoo(int, String): 계정 삭제(계정 삭제여부 1로 변경)
	// 파라미터: newPw, memberIdx, pw
	public void accountSettingLeaveNamoo(int memberIdx, String pw) throws Exception {
		System.out.println(memberIdx);
		System.out.println(pw);
		
		Connection conn = getConnection();
		String sql = "UPDATE member"
				 + " SET leave = 1"
				 + " WHERE member_idx = ?" 
				 + " AND pw = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setString(2, pw);
		int num = pstmt.executeUpdate();
		System.out.println(num);
		
		pstmt.close();
		conn.close();
	}
	
	// getMemberImageDtoFromIdx(int): member_idx 값을 받아서 MemberImageDto 객체를 리턴.
	// 파라미터: member_idx
	// 리턴 :member_idx, name, profile_pic_url 
	public MemberImageDto getMemberImageDtoFromIdx(int memberIdx) throws Exception {
		String sql = "SELECT member_idx, name, profile_pic_url "
					+ " FROM member "
					+ " WHERE member_idx = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		ResultSet rs = pstmt.executeQuery();
		MemberImageDto dtoRet = null;
		if(rs.next()) {
			String name = rs.getString("name");
			String profilePicUrl = rs.getString("profile_pic_url");
			dtoRet = new MemberImageDto(memberIdx, name, profilePicUrl);
		}
		rs.close();
		pstmt.close();
		conn.close();
	
		return dtoRet;
	}

}
