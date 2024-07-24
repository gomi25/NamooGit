package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.ServiceTalkContentDto;
import dto.ServiceTalkShowProfilePicAndNameDto;



public class NamooServiceTalkDao {
	Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver"; 	// 드라이버 정보
	 	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속정보
	 	String dbId = "namoo";
	 	String dbPw = "7777";
	 	Class.forName(driver);
	 	Connection conn = DriverManager.getConnection(url, dbId, dbPw);
	 	
	 	return conn;
	}
	// 서비스톡 목록 : 프사, 이름, 읽음표시 같이 표시하기
	public ArrayList<ServiceTalkContentDto> serviceTalkShowTalKroom(int serviceTalkroomIdx) throws Exception {
		ArrayList<ServiceTalkContentDto> listRet = new ArrayList<ServiceTalkContentDto>();
		Connection conn = getConnection();
		String sql = "SELECT m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read, t.talk_date" + 
					" FROM member m" + 
					" INNER JOIN service_talk t" + 
					" ON m.member_idx = t.member_idx" + 
					" WHERE t.service_talk_idx IN (SELECT MAX(service_talk_idx) FROM service_talk GROUP BY service_talkroom_idx)" + 
					" ORDER BY t.service_talk_idx DESC";
		// member_idx가 service_talkromm_idx를 알면 다 아는 건데 꼭 파라미터로 받아야하나?
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//pstmt.setInt(1, serviceTalkroomIdx);
		//pstmt.setInt(2, serviceTalkroomIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int memberIdx = rs.getInt("member_idx");
			int read = rs.getInt("read");
			int serviceTalkIdx = rs.getInt("service_talk_idx");
			String profilePicUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String message = rs.getString("message");
			String talkDate = rs.getString("talk_date");
			
			ServiceTalkContentDto dto = new ServiceTalkContentDto(memberIdx, serviceTalkroomIdx, serviceTalkIdx, profilePicUrl, name, talkDate, message, read);
			listRet.add(dto);
		}
	
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
	// 서비스톡방 : 프사와 이름 같이 표시하기
	public ArrayList<ServiceTalkShowProfilePicAndNameDto> serviceTalkShowProfilePicAndName(int memberIdx) throws Exception{ //모든 DTO를 가지고 오는 메서드
		ArrayList<ServiceTalkShowProfilePicAndNameDto> listRet = new ArrayList<ServiceTalkShowProfilePicAndNameDto>();
		Connection conn = getConnection();
		String sql ="SELECT profile_pic_url, name FROM member WHERE member_idx = ?"; 
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String profilePicUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			ServiceTalkShowProfilePicAndNameDto dto = new ServiceTalkShowProfilePicAndNameDto(profilePicUrl, name);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	//서비스톡방 조회
	public ArrayList<ServiceTalkContentDto> serviceTalkShoWTalkContent(int serviceTalkroomIdx) throws Exception {
		ArrayList<ServiceTalkContentDto> listRet = new ArrayList<ServiceTalkContentDto>();
		Connection conn = getConnection();
		String sql = "SELECT m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read" + 
					" FROM member m INNER JOIN service_talk t" + 
					" ON m.member_idx = t.member_idx" + 
					" WHERE service_talkroom_idx = ?" + 
					" ORDER BY t.service_talk_idx ASC ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, serviceTalkroomIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int memberIdx = rs.getInt("member_idx");
			int serviceTalkIdx = rs.getInt("service_talk_idx");
			int read = rs.getInt("read");
			String profilePicUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String message = rs.getString("message");
			
			ServiceTalkContentDto dto = new ServiceTalkContentDto(memberIdx, serviceTalkroomIdx, serviceTalkIdx, profilePicUrl, name, null, message, read);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
//	//서비스 톡방 : 회원 톡 보여주기
//	public ArrayList<ServiceTalkContentDto> serviceTalkShowRightTalkWithPicAndName(int serviceTalkroomIdx) throws Exception {
//		ArrayList<ServiceTalkContentDto> listRet = new ArrayList<ServiceTalkContentDto>();
//		Connection conn = getConnection();
//		String sql = "SELECT m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read" + 
//				" FROM member m INNER JOIN service_talk t" + 
//				" ON m.member_idx = t.member_idx" + 
//				" WHERE t.member_idx != 0" + 
//				" AND t.service_talkroom_idx = ?" + 
//				" ORDER BY t.service_talk_idx ASC ";
//		
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, serviceTalkroomIdx);
//		ResultSet rs = pstmt.executeQuery();
//		
//		while(rs.next()) {
//			int memberIdx = rs.getInt("member_idx");
//			int serviceTalkIdx = rs.getInt("service_talk_idx");
//			int read = rs.getInt("read");
//			String profilePicUrl = rs.getString("profile_pic_url");
//			String name = rs.getString("name");
//			String message = rs.getString("message");
//			
//			ServiceTalkContentDto dto = new ServiceTalkContentDto(memberIdx, serviceTalkroomIdx, serviceTalkIdx, profilePicUrl, name, null, message, read);
//			listRet.add(dto);
//		}
//	
//		rs.close();
//		pstmt.close();
//		conn.close();
//		return listRet;
//	}
	// 서비스 톡방 : 메시지 입력
	public void serviceTalkSendMessage(int serviceTalkroomIdx, int memberIdx, String message) throws Exception{
		Connection conn = getConnection();
		String sql = "INSERT INTO service_talk(service_talk_idx, service_talkroom_idx, member_idx, message, talk_date, read)" + 
					"VALUES(service_talk_seq.nextval, ?, ?, ?, sysdate, 1)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, serviceTalkroomIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, message);
		pstmt.executeUpdate();

		pstmt.close();
		conn.close();
	}
// 시간 표시하기 : 분이 달라질 떄만 표시하는 방법! 물어보기
//	public String serviceTalkTime (int serviceTalkIdx, int ServiceTalkRoomIdx) throws Exception {
//		Connection conn = getConnection();
//		String sql = "";
//		
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		
//		
//	}
// 서비스톡방 : 방 나가기	
	public void deleteServiceTalkRoom(int serviceTalkroomIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "DELETE FROM service_talkroom" + 
					" WHERE service_talkroom_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt (1, serviceTalkroomIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// getMemberIdxByServiceTalkroomIdx(int) : serviceTalkroomIdx 값을 받아서 (관리자와 1:1톡 하고 있는 고객의) memberIdx 값을 리턴.
	public int getMemberIdxByServiceTalkroomIdx(int serviceTalkroomIdx) throws Exception {
		System.out.println(serviceTalkroomIdx);
		String sql = "SELECT member_idx " + 
					" FROM service_talkroom " + 
					" WHERE service_talkroom_idx = ?";
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, serviceTalkroomIdx);
		ResultSet rs = pstmt.executeQuery();
		int memberIdxRet = -1;
		if(rs.next()) {
			memberIdxRet = rs.getInt("member_idx"); 
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return memberIdxRet;
	}
	public void createServiceTalkRoomByMemberIdx(int memberIdx) throws Exception {
		System.out.println(memberIdx);
		String sql = "INSERT INTO service_talkroom(service_talkroom_idx, member_idx)" 
				 	+ "VALUES(SERVICE_TALKROOM_SEQ.nextval,?)";
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}
	public int countTalkroomByMemberIdx(int memberIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT COUNT(*)FROM service_talkroom "
					+ "WHERE member_idx=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()){
			result = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
}























