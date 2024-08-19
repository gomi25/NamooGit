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
	public boolean loginCheck(String email, String pw) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    boolean isValid = false;

	    try {
	        conn = getConnection();
	        String sql = "SELECT COUNT(*) FROM member WHERE email = ? AND pw = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, email);
	        pstmt.setString(2, pw);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            int result = rs.getInt(1);
	            isValid = (result == 1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return isValid;
	}
	
	// memberIdxFromEmail(String): 이메일 주소에 따른 memberIdx 리턴
	// 파라미터: email
	// 리턴: 성공시 1의 값을 리턴
	public int memberIdxFromEmail(String email) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = 0;

	    try {
	        conn = getConnection();
	        String sql = "SELECT member_idx FROM member WHERE email = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, email);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            result = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return result;
	}
	
	// emailCheck(int): 비밀번호 찾기
	// 파라미터: member_idx, email
	// 리턴: 성공시 1의 값을 리턴
	public boolean emailCheck(int memberIdx, String email) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    boolean isValid = false;

	    try {
	        conn = getConnection();
	        String sql = "SELECT COUNT(*) FROM member WHERE member_idx = ? AND email = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setString(2, email);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            isValid = (count == 1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return isValid;
	}
	// getPwFromEmail(int): 비밀번호 찾기
	// 파라미터: member_idx, email
	// 리턴: pw
	public String getPwFromEmail(String email) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String password = "";

	    try {
	        conn = getConnection();
	        String sql = "SELECT pw FROM member WHERE email = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, email);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            password = rs.getString("pw");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return password;
	}
	
	// showProfilePic(int): 프사 표시 select 문
	// 파라미터: member_idx
	// 리턴: profile_pic_url(파라미터 값에 해당하는 멤버의 프사)
	public String showProfilePic(int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String profilePicUrl = "";

	    try {
	        conn = getConnection();
	        String sql = "SELECT profile_pic_url FROM member WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            profilePicUrl = rs.getString("profile_pic_url");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return profilePicUrl;
	}
	
	// showName(int): 이름 표시 select 문
	// 파라미터: member_idx
	// 리턴 : name
	public String showName(int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String name = "";

	    try {
	        conn = getConnection();
	        String sql = "SELECT name FROM member WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            name = rs.getString("name");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return name;
	}
	
	// showEmail(int): 이메일 표시 select 문
	// 파라미터: member_idx
	// 리턴: email
	 public String showEmail(int memberIdx) {
		 Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String email = "";

	    try {
	        conn = getConnection();
	        String sql = "SELECT email FROM member WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            email = rs.getString("email");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return email;
	}
	 
	 // showPhoneNumber(int): 전화번호 표시 select 문
	 // 파라미터: member_idx
	 // 리턴: phone_number
	 public String showPhoneNumber(int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String phoneNumber = "";

	    try {
	        conn = getConnection();
	        String sql = "SELECT phone_number FROM member WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            phoneNumber = rs.getString("phone_number");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return phoneNumber;
	 }
	 
	// accountSettingChangeProfilePic(int,String): 프사 변경(직접 선택한 사진 변경)
	// 파라미터: member_idx, profilePicUrl
	public void accountSettingChangeProfilePic(int memberIdx, String profilePicUrl) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET profile_pic_url = ? WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, profilePicUrl);
	        pstmt.setInt(2, memberIdx);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}	
	
	// accountSettingChangeProfileDefault(int): 프사 변경 : 기본 이미지로 변경하기
	// 파라미터: member_idx
	public void accountSettingChangeProfileDefault(int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET profile_pic_url = 'https://jandi-box.com/assets/ic-profile.png' WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// accountSettingChangeName(String, int): 이름 변경
	// 파라미터: name, member_idx
	public void accountSettingChangeName(String name, int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET name = ? WHERE member_idx = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, name);
	        pstmt.setInt(2, memberIdx);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// accountSettingChangePassword(String, int, String): n비밀번호 변경
	// 파라미터: newPw, memberIdx, pw
	public int accountSettingChangePassword(String newPw, int memberIdx, String pw) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    int result = 0;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET pw = ? WHERE member_idx = ? AND pw = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newPw);
	        pstmt.setInt(2, memberIdx);
	        pstmt.setString(3, pw);
	        result = pstmt.executeUpdate(); // 영향받은 행의 개수를 리턴함.
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return result; // 1 : 성공, 0 : 실패
	}

	// accountSettingLeaveNamoo(int, String): 계정 삭제(계정 삭제여부 1로 변경)
	// 파라미터: newPw, memberIdx, pw
	public void accountSettingLeaveNamoo(int memberIdx, String pw) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE member SET leave = 1 WHERE member_idx = ? AND pw = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setString(2, pw);
	        int num = pstmt.executeUpdate(); 
	        System.out.println(num); 
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close(); 
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }
	    }

	}
	
	// getMemberImageDtoFromIdx(int): member_idx 값을 받아서 MemberImageDto 객체를 리턴.
	// 파라미터: member_idx
	// 리턴 :member_idx, name, profile_pic_url 
	public MemberImageDto getMemberImageDtoFromIdx(int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    MemberImageDto dtoRet = null;

	    try {
	        String sql = "SELECT member_idx, name, profile_pic_url "
	                   + "FROM member "
	                   + "WHERE member_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String profilePicUrl = rs.getString("profile_pic_url");
	            dtoRet = new MemberImageDto(memberIdx, name, profilePicUrl);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력합니다.
	    } finally {
	        try {
	            if (rs != null) rs.close(); // ResultSet을 닫습니다.
	            if (pstmt != null) pstmt.close(); // PreparedStatement를 닫습니다.
	            if (conn != null) conn.close(); // Connection을 닫습니다.
	        } catch (Exception e) {
	            e.printStackTrace(); // 닫기 중 예외가 발생하면 스택 트레이스를 출력합니다.
	        }
	    }

	    return dtoRet;
	}

}
