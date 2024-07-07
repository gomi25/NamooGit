package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.ChatroomDto;
import dto.FolderBoxDto;
import dto.LaterProfileUrlColorDto;
import dto.TopicDto;

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
	
//		-- 3. 토픽 즐겨찾기 여부 조회
//		-- input : member_idx, topic_idx
//		--> return type : boolean
//	public boolean isBookmarkTopic(int memberIdx, int topicIdx) throws Exception {
//		String sql = "SELECT COUNT(*)" + 
//					" FROM bookmark" + 
//					" WHERE member_idx_from=? AND topic_idx=?";
//		Connection conn = getConnection();
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, memberIdx);
//		pstmt.setInt(2, topicIdx);
//		ResultSet rs = pstmt.executeQuery();
//		int result = 0;
//		if(rs.next()) {
//			result = rs.getInt(1);
//		}
//		rs.close();
//		pstmt.close();
//		conn.close();
//		return result == 1;
//	}
	
//		-- 4. (폴더 바깥) 토픽 목록 조회.
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
					" WHERE tm.member_idx = ? AND t.team_idx = ?";
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

	// class Chatroom {
			
	// }
	// 
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
			ArrayList<LaterProfileUrlColorDto> listProfileUrlColor = null;  // 프로필 이미지 or 색상
			
			String chatroomName = rs.getString("채팅방이름");	// 채팅방 이름
			String information = rs.getString("information");		// 채팅방 설명 상세
			String chatRecentDateTime = rs.getString("최근챗일시"); // 채팅방 최근 챗 일시
			boolean bookmarkYn = rs.getInt("bookmark_count")==1;   // 즐겨찾기 여부
			int alarm = rs.getInt("alarm");  // 1 or 0
			int unread = rs.getInt("unread");  // >=1 or 0
			ChatroomDto dto = new ChatroomDto(chatroomIdx, listProfileUrlColor, chatroomName, information, chatRecentDateTime, bookmarkYn, alarm, unread);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
}












