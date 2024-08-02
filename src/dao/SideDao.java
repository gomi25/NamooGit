package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.ChatroomDto;
import dto.FolderBoxDto;
import dto.TeamMemberDto;
import dto.TopicDto;

/*
	- 팀 멤버 전체 조회 기능
	- 팀 멤버 조회 기능
	-

*/

public class SideDao {
	private Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";	
		String pw = "7777";	
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}
	
//  ******* 팀 멤버 전체 조회 기능 *******		
//	파라미터 : 팀idx
//	리턴 : 팀idx, 회원idx, 프로필, 이름, 부서, 직책, 권한
	public ArrayList<TeamMemberDto> getTeamMemberList(int teamIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<TeamMemberDto> listRet = new ArrayList<TeamMemberDto>();

		String sql = "SELECT tm.team_idx, tm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
					"        m.name, tm.department, tm.position, tm.power " + 
					" FROM member m INNER JOIN team_member tm ON m.member_idx = tm.member_idx" + 
					"    INNER JOIN team t ON tm.team_idx = t.team_idx " + 
					" WHERE t.team_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int memberIdx = rs.getInt("member_idx");
			String profileUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String department = rs.getString("department");
			String position = rs.getString("position");
			String power = rs.getString("power");
			TeamMemberDto dto = new TeamMemberDto(teamIdx, memberIdx, profileUrl, name, department, position, power);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
//  ******* 팀 멤버 조회 기능 *******		
//	파라미터 : 팀idx, 회원idx
//	리턴 : 팀idx, 회원idx, 프로필, 이름, 부서, 직책, 권한
	public TeamMemberDto getTeamMember(int teamIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "SELECT tm.team_idx, tm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
				"        m.name, tm.department, tm.position, tm.power " + 
				" FROM member m INNER JOIN team_member tm ON m.member_idx = tm.member_idx" + 
				"    INNER JOIN team t ON tm.team_idx = t.team_idx " + 
				" WHERE t.team_idx = ?" + 
				" AND tm.member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, memberIdx);
		ResultSet rs = pstmt.executeQuery();
		TeamMemberDto dto = null;
		while(rs.next()) {
			String profileUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String department = rs.getString("department");
			String position = rs.getString("position");
			String power = rs.getString("power");
			dto = new TeamMemberDto(teamIdx, memberIdx, profileUrl, name, department, position, power);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return dto;
	}
	
	

//	-- 1. 특정 member_idx의 '폴더' 리스트:
//		-- INPUT : member_idx, team_idx
//		-- method's return type : ArrayList<FolderBoxDto>
	public ArrayList<FolderBoxDto> getFolderList(int memberIdx, int teamIdx) throws Exception {
		ArrayList<FolderBoxDto> listRet = new ArrayList<FolderBoxDto>();
		String sql = "SELECT topic_folder_idx, name" + 
					" FROM folder_box" + 
					" WHERE member_idx=? AND team_idx=?"; 
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int topicFolderIdx = rs.getInt("topic_folder_idx");
			String name = rs.getString("name");
			FolderBoxDto dto = new FolderBoxDto(topicFolderIdx, memberIdx, teamIdx, name);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
//		-- 2. 특정 폴더에 포함된 토픽 목록 조회
//		-- input : topic_folder_idx
//		--> return type : ArrayList<TopicDto>
	public ArrayList<TopicDto> getTopicListFromFolderIdx(int memberIdx, int topicFolderIdx) throws Exception {
		ArrayList<TopicDto> listRet = new ArrayList<TopicDto>();
		Connection conn = getConnection();
		String sql = "SELECT t.topic_idx, t.name, t.information, t.team_idx, t.open, t.alarm," + 
					" (SELECT COUNT(*) FROM topic_unread WHERE topic_board_idx=t.topic_idx) unread," + 
					" (SELECT COUNT(*) FROM bookmark b WHERE b.member_idx_from=? AND b.topic_idx=t.topic_idx) bookmark_count" + 
					" FROM topic_folder tf INNER JOIN topic t " + 
					" ON tf.topic_idx = t.topic_idx " + 
					" WHERE tf.topic_folder_idx = ? " + 
					" ORDER BY t.name";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, topicFolderIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int topicIdx = rs.getInt("topic_idx");
			String name = rs.getString("name");
			String information = rs.getString("information");
			int teamIdx = rs.getInt("team_idx");
			int open = rs.getInt("open");
			int alarm = rs.getInt("alarm");
			int unread = rs.getInt("unread");
			boolean bookmark = rs.getInt("bookmark_count")==1;
			TopicDto dto = new TopicDto(topicIdx, name, information, teamIdx, open, alarm, unread, bookmark);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
//		-- 3. (폴더 바깥) 토픽 목록 조회.
//		-- INPUT : member_idx (,team_idx 는 필요 없을 것 같음.... 일단은. NoNo 필요함)
//		--> return type : ArrayList<TopicDto>
	public ArrayList<TopicDto> getTopicListOutside(int memberIdx, int teamIdx) throws Exception {
		ArrayList<TopicDto> listRet = new ArrayList<TopicDto>();
		String sql = 
					"SELECT t.topic_idx, t.name, t.information, t.team_idx, t.open, t.alarm, " + 
					" (SELECT COUNT(*) FROM topic_unread WHERE topic_board_idx=t.topic_idx) unread," + 
					" (SELECT COUNT(*) FROM bookmark b WHERE b.member_idx_from=? AND b.topic_idx=t.topic_idx) bookmark_count" + 
					" FROM topic t INNER JOIN topic_member tm" + 
					" ON t.topic_idx = tm.topic_idx" + 
					" WHERE tm.member_idx = ? AND t.team_idx = ?" +
					" AND t.topic_idx NOT IN ( " + 
					"    select topic_idx " +  // --하단리스트에_나오지말아야할_topic_idx 
					"    from topic_folder  " + 
					"    where topic_folder_idx IN ( " + 
					"        SELECT topic_folder_idx  " + 
					"        FROM folder_box  " + 
					"        WHERE member_idx = ? AND team_idx = ? " + 
					"    ) " + 
					" ) " + 
					" ORDER BY t.topic_idx";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setInt(3, teamIdx);
		pstmt.setInt(4, memberIdx);
		pstmt.setInt(5, teamIdx);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int topicIdx = rs.getInt("topic_idx");
			String name = rs.getString("name");
			String information = rs.getString("information");
			int open = rs.getInt("open");
			int alarm = rs.getInt("alarm");
			int unread = rs.getInt("unread");
			boolean bookmark = rs.getInt("bookmark_count")==1;
			TopicDto dto = new TopicDto(topicIdx, name, information, teamIdx, open, alarm, unread, bookmark);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
//		-- 4. 토픽 전체 조회.
//		-- INPUT : member_idx 
//		--> return type : ArrayList<TopicDto>
	public ArrayList<TopicDto> getAllTopicList(int memberIdx, int teamIdx) throws Exception {
		ArrayList<TopicDto> listRet = new ArrayList<TopicDto>();
		String sql = 
				"SELECT t.topic_idx, t.name, t.information, t.team_idx, t.open, t.alarm, " + 
						" (SELECT COUNT(*) FROM topic_unread WHERE topic_board_idx=t.topic_idx) unread," + 
						" (SELECT COUNT(*) FROM bookmark b WHERE b.member_idx_from = ? AND b.topic_idx=t.topic_idx) bookmark_count" + 
						" FROM topic t INNER JOIN topic_member tm" + 
						" ON t.topic_idx = tm.topic_idx" + 
						" WHERE tm.member_idx = ? AND t.team_idx = ?" + 
						" ORDER BY t.topic_idx";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setInt(3, teamIdx);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int topicIdx = rs.getInt("topic_idx");
			String name = rs.getString("name");
			String information = rs.getString("information");
			int open = rs.getInt("open");
			int alarm = rs.getInt("alarm");
			int unread = rs.getInt("unread");
			boolean bookmark = rs.getInt("bookmark_count")==1;
			TopicDto dto = new TopicDto(topicIdx, name, information, teamIdx, open, alarm, unread, bookmark);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}

	public ArrayList<String> getChatroomMembersImageUrl(int chatroomIdx, int memberIdxMe) throws Exception {
		ArrayList<String> listRet = new ArrayList<String>();
		String sql = "SELECT NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url" + 
					" FROM chat_member cm " + 
					" INNER JOIN member m" + 
					" ON cm.member_idx = m.member_idx" + 
					" WHERE cm.chatroom_idx=?" + 
					" AND m.member_idx!=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatroomIdx);
		pstmt.setInt(2, memberIdxMe);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			listRet.add(rs.getString(1));
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	
	public ArrayList<ChatroomDto> getChatroomList(int memberIdx, int teamIdx) throws Exception {
		ArrayList<ChatroomDto> listRet = new ArrayList<ChatroomDto>();
		String sql = "SELECT  t1.member_idx, t1.chatroom_idx, (SELECT COUNT(*) FROM bookmark WHERE member_idx_from=2 AND chatroom_idx=t1.chatroom_idx) bookmark_count," + 
				"        t1.채팅방이름, t1.information, (CASE t1.최근챗일시 WHEN TO_DATE('2001/01/01','yyyy/mm/dd') THEN null ELSE t1.최근챗일시 END) 최근챗일시," + 
				"		 t1.alarm," + 
				"        (SELECT COUNT(*) " + 
				"        FROM chat_unread cu INNER JOIN chat c" + 
				"        ON cu.chat_idx = c.chat_idx" + 
				"        WHERE cu.member_idx=t1.member_idx AND c.chatroom_idx=t1.chatroom_idx) unread " + 
				" FROM   (SELECT cm.member_idx, cr.chatroom_idx, cr.information, cr.alarm, cr.name 채팅방이름, NVL((SELECT MAX(c.chat_date) FROM chat c WHERE c.chatroom_idx=cr.chatroom_idx),'2001/01/01') 최근챗일시" + 
				"        FROM chatroom cr INNER JOIN chat_member cm   " + 
				"        ON cr.chatroom_idx = cm.chatroom_idx  " + 
				"        WHERE cr.team_idx = ?" + 
				"        AND cm.member_idx = ?  " + 
				"        ORDER BY 최근챗일시 DESC) t1";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, memberIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int chatroomIdx = rs.getInt("chatroom_idx");     // 채팅방idx
			
			// 임시 - 수정해야 : 6/27(목) 이후 빠른 시일 내로~
			// ㅁ. 삭제하세요 - LaterProfileUrlColorDto 클래스 (.java)
			ArrayList<String> listProfileUrl = null;  // 프로필 이미지 or 색상
			String chatroomName = rs.getString("채팅방이름");	// 채팅방 이름
			String information = rs.getString("information");		// 채팅방 설명 상세
			String chatRecentDateTime = rs.getString("최근챗일시"); // 채팅방 최근 챗 일시
			boolean bookmarkYn = rs.getInt("bookmark_count")==1;   // 즐겨찾기 여부
			int alarm = rs.getInt("alarm");  // 1 or 0
			int unread = rs.getInt("unread");  // >=1 or 0
			ChatroomDto dto = new ChatroomDto(chatroomIdx, listProfileUrl, chatroomName, information, chatRecentDateTime, bookmarkYn, alarm, unread);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}

	// team_idx + member_idx -----> state를 리턴.(ex. 회의중)
	public String getStateOfMember(int teamIdx, int memberIdx) throws Exception {
		String sql = "SELECT tm.state" + 
					" FROM team_member tm" + 
					" WHERE tm.team_idx = ?" + 
					" AND tm.member_idx = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, memberIdx);
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
	
}












