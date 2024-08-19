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
	
	// serviceTalkShowTalKroom(): 서비스톡 목록 : 프사, 이름, 읽음표시 같이 표시하기
	// 리턴: m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read, t.talk_date
	public ArrayList<ServiceTalkContentDto> serviceTalkShowTalKroom() {
		ArrayList<ServiceTalkContentDto> listRet = new ArrayList<ServiceTalkContentDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read, t.talk_date" + 
	                     " FROM member m" + 
	                     " INNER JOIN service_talk t" + 
	                     " ON m.member_idx = t.member_idx" + 
	                     " WHERE t.service_talk_idx IN (SELECT MAX(service_talk_idx) FROM service_talk GROUP BY service_talkroom_idx)" + 
	                     " ORDER BY t.service_talk_idx DESC";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int memberIdx = rs.getInt("member_idx");
	            int read = rs.getInt("read");
	            int serviceTalkIdx = rs.getInt("service_talk_idx");
	            int serviceTalkroomIdx = rs.getInt("service_talkroom_idx");
	            String profilePicUrl = rs.getString("profile_pic_url");
	            String name = rs.getString("name");
	            String message = rs.getString("message");
	            String talkDate = rs.getString("talk_date");
	            
	            ServiceTalkContentDto dto = new ServiceTalkContentDto(memberIdx, serviceTalkroomIdx, serviceTalkIdx, profilePicUrl, name, talkDate, message, read);
	            listRet.add(dto);
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

	    return listRet;
	}
	
	// serviceTalkShowProfilePicAndName(int): 서비스톡방 : 프사와 이름 같이 표시하기
	// 파라미터: memberIdx
	// 리턴: profile_pic_url, name 
	public ArrayList<ServiceTalkShowProfilePicAndNameDto> serviceTalkShowProfilePicAndName(int memberIdx)  { 
		ArrayList<ServiceTalkShowProfilePicAndNameDto> listRet = new ArrayList<ServiceTalkShowProfilePicAndNameDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT profile_pic_url, name FROM member WHERE member_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            String profilePicUrl = rs.getString("profile_pic_url");
	            String name = rs.getString("name");
	            ServiceTalkShowProfilePicAndNameDto dto = new ServiceTalkShowProfilePicAndNameDto(profilePicUrl, name);
	            listRet.add(dto);
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

	    return listRet;
	}
	// serviceTalkShoWTalkContent(int): 서비스톡방 조회
	// 파라미터: serviceTalkroomIdx
	// 리턴: m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read
	public ArrayList<ServiceTalkContentDto> serviceTalkShoWTalkContent(int serviceTalkroomIdx) {
		ArrayList<ServiceTalkContentDto> listRet = new ArrayList<ServiceTalkContentDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT m.member_idx, t.service_talkroom_idx, t.service_talk_idx, m.name, m.profile_pic_url, t.message, t.read" + 
	                     " FROM member m INNER JOIN service_talk t" + 
	                     " ON m.member_idx = t.member_idx" + 
	                     " WHERE t.service_talkroom_idx = ?" + 
	                     " ORDER BY t.service_talk_idx ASC";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, serviceTalkroomIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int memberIdx = rs.getInt("member_idx");
	            int serviceTalkIdx = rs.getInt("service_talk_idx");
	            int read = rs.getInt("read");
	            String profilePicUrl = rs.getString("profile_pic_url");
	            String name = rs.getString("name");
	            String message = rs.getString("message");
	            
	            ServiceTalkContentDto dto = new ServiceTalkContentDto(memberIdx, serviceTalkroomIdx, serviceTalkIdx, profilePicUrl, name, null, message, read);
	            listRet.add(dto);
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

	    return listRet;
	}
	
	// serviceTalkSendMessage(int, int, String): 채팅 입력
	// 파라미터: serviceTalkroomIdx, memberIdx, message
	public void serviceTalkSendMessage(int serviceTalkroomIdx, int memberIdx, String message) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "INSERT INTO service_talk(service_talk_idx, service_talkroom_idx, member_idx, message, talk_date, read)" + 
	                     " VALUES(seq_service_talk.nextval, ?, ?, ?, sysdate, 1)";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, serviceTalkroomIdx);
	        pstmt.setInt(2, memberIdx);
	        pstmt.setString(3, message);
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

	// deleteServiceTalkRoom(int): 톡방 삭제
	// 파라미터: serviceTalkroomIdx
	public void deleteServiceTalkRoom(int serviceTalkroomIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "DELETE FROM service_talkroom WHERE service_talkroom_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, serviceTalkroomIdx);
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
	
	// getMemberIdxByServiceTalkroomIdx(int): serviceTalkroomIdx 값을 받아서 (관리자와 1:1톡 하고 있는 고객의) memberIdx 값을 리턴. 삭제
	// 파라미터: serviceTalkroomIdx
	// 리턴: member_idx 
	public int getMemberIdxByServiceTalkroomIdx(int serviceTalkroomIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int memberIdxRet = -1;

	    try {
	        System.out.println(serviceTalkroomIdx);

	        String sql = "SELECT member_idx " + 
	                     " FROM service_talkroom " + 
	                     " WHERE service_talkroom_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, serviceTalkroomIdx);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            memberIdxRet = rs.getInt("member_idx"); 
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

	    return memberIdxRet;
	}
	// 1) 리턴타입을 int로.
	// 2) 문자열 배열(arr) ---> pk컬럼의 이름.
	// 3) conn.prepareStatement(sql, arr);
	// 4) executeUpdate() 직후에 (executeQuery() 이게 아니고) getGeneratedKeys() 실행 (추가).
	// 5) rs.next() ---> rs.getInt(1) 로 받으면 그 값이 바로 (이번에 새롭게 만들어진) PK 값이다.
	
	// createServiceTalkRoomByMemberIdx(int): 리턴 타입을 int로 바꿈.
	// 파라미터: memberIdx
	// 리턴: member_idx 
	public int createServiceTalkRoomByMemberIdx(int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int ret = 0;

	    try {
	        System.out.println(memberIdx);

	        String[] generatedColumns = { "service_talkroom_idx" };
	        String sql = "INSERT INTO service_talkroom(service_talkroom_idx, member_idx)" + 
	                     " VALUES(SEQ_SERVICE_TALKROOM.nextval, ?)";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql, generatedColumns);
	        pstmt.setInt(1, memberIdx);
	        pstmt.executeUpdate();

	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            ret = rs.getInt(1);
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

	    return ret;
	}
}























