package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.GanttkCommentLikeDto;
import dto.MemberProfileDto;
import dto.OrganizationalBookmarkMemberListDto;
import dto.OrganizationalMemberListDto;

public class OrganizationalChartDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
	
	// checkOrganizationalMemberCount : 멤버수
	// input: team_idx
	public int checkOrganizationalMemberCount(int teamIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM team_member tm " 
				+ " WHERE tm.team_idx = ?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	// getOrganizationalMemberList : 멤버에서 멤버 리스트
	public ArrayList<OrganizationalMemberListDto> getOrganizationalMemberList(int teamIdx, int memberIdxFrom) throws Exception {
		 	ArrayList<OrganizationalMemberListDto> listRet = new ArrayList<OrganizationalMemberListDto>();
		 	String sql = "SELECT m.member_idx, m.profile_pic_url, m.name AS member_name, t.name AS team_name, tm.position, tm.state, tm.state_message,"
		 		    	+ " CASE WHEN b.member_idx_from IS NOT NULL THEN 'Y'"
		 		    	+ " ELSE 'N'" 
		 		    	+ " END AS bookmark_member"
		 		    	+ " FROM member m" 
		 		    	+ " LEFT JOIN team_member tm ON m.member_idx = tm.member_idx" 
		 		    	+ " LEFT JOIN team t ON tm.team_idx = t.team_idx"
		 		    	+ " LEFT JOIN bookmark b ON m.member_idx = b.member_idx_to AND b.member_idx_from = ?"
		 		    	+ " WHERE tm.team_idx = ?";
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, memberIdxFrom);
		 	pstmt.setInt(2, teamIdx);
		 	ResultSet rs = pstmt.executeQuery();

		 	while(rs.next()) {
		 		String profilePicUrl = rs.getString("profile_pic_url");
		 		String memberName = rs.getString("member_name");
		 		String teamName = rs.getString("team_name");
		 		String position = rs.getString("position");
		 		String state = rs.getString("state");
		 		String stateMessage = rs.getString("state_message");
		 		int memberIdx = rs.getInt("member_idx");
		 		String bookmarkMember = rs.getString("bookmark_member");

		 	
		 		OrganizationalMemberListDto dto = new OrganizationalMemberListDto(profilePicUrl, memberName, teamName, position, state, stateMessage, teamIdx, memberIdx, bookmarkMember, memberIdxFrom);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	return listRet;
	 }
	
	// 팀 이름
	public String checkOrganizationalTeamName(int teamIdx) throws Exception {
		String sql = "SELECT t.name "
				+ " FROM team t" 
				+ " WHERE t.team_idx = ?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		
		ResultSet rs = pstmt.executeQuery();
		String result = " ";
		if(rs.next()) {    
			result = rs.getString(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 즐겨찾기된 멤버
	public ArrayList<OrganizationalBookmarkMemberListDto> getOrganizationalBookmarkMemberList(int teamIdx, int memberIdxFrom) throws Exception {
	 	ArrayList<OrganizationalBookmarkMemberListDto> listRet = new ArrayList<OrganizationalBookmarkMemberListDto>();
	 	String sql = "SELECT m.member_idx, m.profile_pic_url, m.name AS member_name, t.name AS team_name, tm.position, tm.state, tm.state_message"
	               + " FROM member m"
	               + " LEFT JOIN team_member tm ON m.member_idx = tm.member_idx"
	               + " LEFT JOIN team t ON tm.team_idx = t.team_idx"
	               + " INNER JOIN bookmark b ON b.member_idx_to = m.member_idx"
	               + " WHERE tm.team_idx = ?"
	               + " AND b.member_idx_from = ?"; 
	 	Connection conn = getConnection();

	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, teamIdx);
	 	pstmt.setInt(2, memberIdxFrom);
	 	
	 	ResultSet rs = pstmt.executeQuery();

	 	while(rs.next()) {
	 		String profilePicUrl = rs.getString("profile_pic_url");
	 		String memberName = rs.getString("member_name");
	 		String teamName = rs.getString("team_name");
	 		String position = rs.getString("position");
	 		String state = rs.getString("state");
	 		String stateMessage = rs.getString("state_message");
	 		int memberIdx = rs.getInt("member_idx");

	 	
	 		OrganizationalBookmarkMemberListDto dto = new OrganizationalBookmarkMemberListDto(profilePicUrl, memberName, teamName, position, state, stateMessage, teamIdx, memberIdx, memberIdxFrom);
		 	listRet.add(dto);
	 	}
	 	rs.close();
	 	pstmt.close();
	 	conn.close();
	 	return listRet;
 }
	
	// 즐겨찾기된 멤버수
	public int checkOrganizationalBookmarkMemberCount(int teamIdx) throws Exception {
		String sql = "SELECT COUNT(*) "
					+ " FROM team_member tm" 
					+ " INNER JOIN bookmark b ON tm.member_idx = b.member_idx_to"
					+ " WHERE tm.team_idx = ?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	// 상세 프로필
	public ArrayList<MemberProfileDto> getMemberProfile(int teamIdx) throws Exception {
	 	ArrayList<MemberProfileDto> listRet = new ArrayList<MemberProfileDto>();
	 	String sql = "SELECT m.member_idx, m.name AS member_name, t.name AS team_name, tm.power, tm.state, tm.state_message, tm.position, m.birth, m.phone_number, m.email, m.profile_pic_url" 
	 			+ " FROM member m LEFT JOIN team_member tm ON m.member_idx = tm.member_idx LEFT JOIN team t ON tm.team_idx = t.team_idx" 
	 			+ " WHERE tm.team_idx = ?"; 
	 	Connection conn = getConnection();

	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, teamIdx);
	 	ResultSet rs = pstmt.executeQuery();

	 	while(rs.next()) {
	 		int memberIdx = rs.getInt("member_idx");
	 		String profilePicUrl = rs.getString("profile_pic_url");
	 		String memberName = rs.getString("member_name");
	 		String teamName = rs.getString("team_name");
	 		String position = rs.getString("position");
	 		String state = rs.getString("state");
	 		String stateMessage = rs.getString("state_message");
	 		String power = rs.getString("power");
	 		String birth = rs.getString("birth");
	 		String phoneNumber = rs.getString("phone_number");
	 		String email = rs.getString("email");
	 	
	 		MemberProfileDto dto = new MemberProfileDto(memberIdx, profilePicUrl, memberName, teamName, position, state, stateMessage, power, birth, phoneNumber, email);
		 	listRet.add(dto);
	 	}
	 	rs.close();
	 	pstmt.close();
	 	conn.close();
	 	return listRet;
 }
	

}
