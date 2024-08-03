package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

import dto.MemberDto;
import dto.TeamListDto;

public class MemberDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// agreeToTerms(int agreement, int leave) : 약관 동의
	// agreement : 동의(1), 비동의(0)
	// leave : 탈퇴(1), 탈퇴x(0)
	// 리턴값 = 새롭게 생성된 member_idx값. (이 값을 회원가입 절차가 완료될 때까지 사용)
	public int agreeToTerms(int agreement, int leave) throws Exception {
		int memberIdxRet = 0;   // 리턴할 member_idx값. 0 은 그냥 초기 값. 
		Connection conn = getConnection();
		
		String sql = "INSERT INTO member(member_idx, pw, name, birth, phone_number, join_date, profile_pic_url, agreement, verification_code, expiration_date, leave, email)"
				+ " VALUES(seq_member_idx.nextval, null, null, null, null, null, null, ?, null, null, ?, null)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"member_idx"});
		pstmt.setInt(1, agreement);
		pstmt.setInt(2, leave);
		
		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()) {
			memberIdxRet = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return memberIdxRet;
	}
	
	// enterInformation(String name, String password, String email, int memberIdx) : 회원 정보 입력
	public void enterInformation(String name, String password, String email, int memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE member SET name = ?, pw = ?, email = ?" 
				+ " WHERE member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setString(2, password);
		pstmt.setString(3, email);
		pstmt.setInt(4, memberIdx);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// emailVerification(int memberIdx, String verificationCode) : 인증 코드 저장
	// memberIdx : 방금 생성된 memberIdx
	public void emailVerification(int memberIdx, String verificationCode) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE member SET verification_code = ?, expiration_date = ? WHERE member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, verificationCode);
		 
		// 현재 시간에서 20분을 더한 시간 계산
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 20);
        Timestamp expirationDate = new Timestamp(cal.getTimeInMillis());
        pstmt.setTimestamp(2, expirationDate);
        pstmt.setInt(3, memberIdx);
        
        pstmt.executeUpdate();
        
        pstmt.close();
		conn.close();
	}
	
	// enterVerifyCode(int memberIdx, String verificationCode) : 인증 코드 확인  *** 실행 안 됨 ***
	public boolean enterVerifyCode(int memberIdx, String verificationCode) throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "SELECT verification_code, expiration_date FROM member WHERE member_idx = ? AND verification_code = ? AND expiration_date > SYSDATE";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memberIdx);
            pstmt.setString(2, verificationCode);

            rs = pstmt.executeQuery();
            
            // 결과가 존재하면 true를 반환
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error verifying code", e);
        } finally {
            // 자원 해제
            if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        } 
	}
	
	// createTeam(String teamName, String domain, String image) : 팀 생성  
	// 리턴 값(int) : 생성된 team의 team_idx값.
	public int createTeam(String teamName, String domain, String image) throws Exception {
		Connection conn = getConnection();
		
		String[] arr = {"team_idx" };
		
		String sql = "INSERT INTO team(team_idx, name, domain, image)"
				+ " VALUES(seq_team.nextval, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql, arr);
		
		pstmt.setString(1, teamName);
		pstmt.setString(2, domain);
		if(image==null) {
	    	pstmt.setNull(3, Types.VARCHAR);
	    } else {
	    	pstmt.setString(3, image);
	    }
		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		int pkNum = 0;
		if(rs.next()) {
			pkNum = rs.getInt(1);
		}
		rs.close();
        pstmt.close();
		conn.close();

		return pkNum;
	}
	
	// 
	public void addTeamFirstMember(int teamIdx, int memberIdx) throws Exception {
		String sql = "INSERT INTO team_member(team_idx, member_idx, "
				+ " power, off, state, state_message, department, position, leave)"
				+ " values (?, ?, '소유자', 0, null, null, null, null, 0)";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// getListTeamListDto(int memberIdx) : 팀 리스트 보여 주기
	public ArrayList<TeamListDto> getListTeamListDto(int memberIdx) throws Exception {
		ArrayList<TeamListDto> list = new ArrayList<>();
		Connection conn = getConnection();
		
		String sql = "SELECT t.name 팀이름, t.domain 도메인, t.image 팀로고" 
				+ " FROM team t JOIN team_member tm ON t.team_idx = tm.team_idx JOIN member m ON m.member_idx = tm.member_idx" 
				+ " WHERE tm.member_idx = ?"
				+ " ORDER BY t.team_idx";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, memberIdx);

		ResultSet rs = pstmt.executeQuery();
	
		try {
			while(rs.next()) {
				String teamName = rs.getString(1);
				String domain = rs.getString(2);
				String teamImage = rs.getString(3);
				
				TeamListDto dto = new TeamListDto(teamName, domain, teamImage);
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return list;
	}
	
	// getMemberDtoByMemberIdx(int memberIdx) : 팀 생성 화면에서 회원 프로필 띄우기 
	public MemberDto getMemberDtoByMemberIdx(int memberIdx) throws Exception {
		MemberDto dto = null;
		Connection conn = getConnection();
		
		String sql = "SELECT m.name AS 멤버명, m.profile_pic_url AS 멤버프사, m.email AS 이메일"
					+ " FROM member m" 
					+ " WHERE m.member_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, memberIdx);
		
		ResultSet rs = pstmt.executeQuery();
		
		try {
			if(rs.next()) {
				String memberName = rs.getString(1);	
				String profilePicUrl = rs.getString(2);
				String email = rs.getString(3);
			
				dto = new MemberDto(memberName, profilePicUrl, email);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { 
			if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
		}
		return dto;
	}
}
