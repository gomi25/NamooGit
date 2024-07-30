package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import com.Common;

import dto.ChatCommentDto;
import dto.ChatContentsDto;
import dto.ChatroomMemberDto;
import dto.TeamMemberDto;

public class ChatDao {
	
	private Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo"; 
		String pw = "7777";	
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}

	/* 'PM HH:MI' <- 0 나오게 */
	//	특정 채팅방의 채팅글 조회 -> 원본
	public ArrayList<ChatContentsDto> getChatContents(int teamIdx, int chatroomIdx, int memberIdx) throws Exception {
	    ArrayList<ChatContentsDto> listRet = new ArrayList<ChatContentsDto>(); 

	    String sql = " SELECT c.chat_idx, cr.chatroom_idx, " +
	                 "NVL(m2.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_url, m2.member_idx, " +
	                 "m2.name, tm2.state, c.content, c.file_idx, " +
	                 "TO_CHAR(c.chat_date, 'AM FMHH:MI', 'NLS_DATE_LANGUAGE=AMERICAN') write_date, " +
	                 "(SELECT COUNT(*) FROM chat_unread WHERE chat_idx = c.chat_idx) unread_cnt, c.modified, fb.file_name " +
	                 "FROM team_member tm " +
	                 "INNER JOIN member m ON tm.member_idx = m.member_idx " +
	                 "LEFT JOIN chat_member cm ON m.member_idx = cm.member_idx " +
	                 "LEFT JOIN chatroom cr ON cm.chatroom_idx = cr.chatroom_idx " +
	                 "LEFT JOIN chat c ON cr.chatroom_idx = c.chatroom_idx " +
	                 "LEFT JOIN member m2 ON c.member_idx = m2.member_idx " +
	                 "LEFT JOIN team_member tm2 ON m2.member_idx = tm2.member_idx " +
	                 "LEFT OUTER JOIN file_box fb ON c.file_idx = fb.file_idx " +
	                 "WHERE tm.team_idx = ? " +
	                 "AND (tm2.team_idx = ? OR tm2.team_idx IS NULL) " +
	                 "AND cr.chatroom_idx = ? " +
	                 "AND m.member_idx = ? " +
	                 "ORDER BY c.chat_date";

	    Connection conn = getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, teamIdx);
	    pstmt.setInt(2, teamIdx);
	    pstmt.setInt(3, chatroomIdx);
	    pstmt.setInt(4, memberIdx);

	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
	        int chatIdx = rs.getInt("chat_idx");
	        String profileUrl = rs.getString("profile_url");
	        Integer writerMemberIdx = rs.getInt("member_idx");
	        if (rs.wasNull()) {
	            writerMemberIdx = null;
	        }
	        String name = rs.getString("name");
	        String state = rs.getString("state");
	        String content = rs.getString("content");
	        Integer fileIdx = rs.getInt("file_idx");
	        if (rs.wasNull()) {
	            fileIdx = null;
	        }
	        String fileName = rs.getString("file_name");
	        String writeDate = rs.getString("write_date");
	        int unreadCnt = rs.getInt("unread_cnt");
	        int modified = rs.getInt("modified");

	        ChatContentsDto dto = new ChatContentsDto(chatIdx, chatroomIdx, writerMemberIdx, profileUrl, name, state, content, fileIdx, fileName, writeDate, unreadCnt, modified);
	        listRet.add(dto);
	    }
	    rs.close();
	    pstmt.close();
	    conn.close();

	    return listRet;
	}
	
	// *******채팅글의 채팅댓글 조회*******
	// 파라미터: 채팅글idx, 팀idx 
	// 리턴: 채팅댓글idx, 채팅글idx, 작성자idx, 작성자프로필이미지, 작성자이름, 상태, 댓글내용, 작성일시
	public ArrayList<ChatCommentDto> getChatCommentList(int chatIdx, int teamIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<ChatCommentDto> listRet = new ArrayList<ChatCommentDto>();
		String sql = "SELECT cc.chat_comment_idx,  " + 
				"        cc.chat_idx,  " + 
				"        NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url,     " + 
				"		m.member_idx,  " + 
				"        m.name,  " + 
				"        tm.state,  " + 
				"        cc.comments,     " + 
				"		TO_CHAR(cc.chat_date, 'YYYY-MM-DD PM HH:MI') write_date,  " + 
				"        cc.file_idx     " + 
				" FROM chat_comment cc INNER JOIN member m ON cc.member_idx = m.member_idx " + 
				" INNER JOIN team_member tm ON m.member_idx = tm.member_idx " + 
				" INNER JOIN team t ON t.team_idx = tm.team_idx " + 
				" WHERE cc.chat_idx = ? " + 
				" AND t.team_idx = ? " + 
				" ORDER BY cc.chat_date";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatIdx);
		pstmt.setInt(2, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int chatCommentIdx = rs.getInt("chat_comment_idx");
			int memberIdx = rs.getInt("member_idx");
			String profileUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String state = rs.getString("state");
			String comments = rs.getString("comments");
			String writeDate = rs.getString("write_date");
			Integer fileIdx = rs.getInt("file_idx");
			if(rs.getInt("file_idx") == 0) {
				fileIdx = null;
			}
			ChatCommentDto dto = new ChatCommentDto(chatCommentIdx, chatIdx, memberIdx, profileUrl, name, state, comments, writeDate, fileIdx);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}	
	
	// ============================== 채팅 - 채팅 내용 관련 기능(1/4) ==============================
	// *******채팅방 생성하는 기능*******
	
	
	// *******채팅방에 멤버 추가*******
	// 파라미터: 채팅방idx, 추가할멤버idx
	public void addChatMember(int chatroomIdx, int[] memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO chat_member(chatroom_idx, member_idx) VALUES (?, ?)";
		PreparedStatement pstmt;
		for(int i = 0; i < memberIdx.length; i++) {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
			pstmt.setInt(2, memberIdx[i]);
			pstmt.executeUpdate();
			pstmt.close();
		}
		conn.close();
	}
	
	// *******채팅글 작성*******
	// 파라미터: 채팅방idx,작성자 idx, 작성 내용, 파일idx
	// 리턴 : 채팅글idx
	public int writeChat(int chatroomIdx, int memberIdx, String content, Integer fileIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO chat (chat_idx, chatroom_idx, member_idx, content, chat_date, file_idx, modified)" +
					 " VALUES (seq_chat.nextval, ?, ?, ?, sysdate, ?, 0)";
		PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"chat_idx"});
		pstmt.setInt(1, chatroomIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, content);
		if(fileIdx==null){
			pstmt.setNull(4, Types.INTEGER);	
		} else {
			pstmt.setInt(4, fileIdx);
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		int ret = 0;
		if(rs.next()) {
			ret = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return ret;
	}
	
	
	// *******채팅댓글 작성*******
	// 파라미터: 채팅글idx, 작성자 idx, 댓글내용, 파일idx
	public void writeChatComment(int chatIdx, int memberIdx, String comments, Integer fileIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO chat_comment(chat_comment_idx, chat_idx, member_idx, comments, chat_date, file_idx, modified)" +	 
					 " VALUES(seq_chat_comment.nextval, ?, ?, ?, sysdate, ?, 0)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, comments);
		if(fileIdx==null) {
			pstmt.setNull(4, Types.INTEGER);
		} else {
			pstmt.setInt(4, fileIdx);
		}
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// *******채팅 읽으면 숫자 줄어드는 기능*******
	public void readChat(int chatIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM chat_unread" + 
						" WHERE chat_idx = ?" + 
						" AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// *******채팅글 삭제*******
	public void deleteChatContent(int chatIdx) throws Exception {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 파일이 있는지 확인
        String sql = "SELECT file_idx FROM chat WHERE chat_idx = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, chatIdx);
        rs = pstmt.executeQuery();
        
        int fileIdx = 0;
        if (rs.next()) {
            fileIdx = rs.getInt(1);
        }
        if (fileIdx != 0) {
            String sql2 = "DELETE FROM file_box WHERE file_idx = ?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, fileIdx);
            pstmt.executeUpdate();
        }
        
        String sql3 = "DELETE FROM chat WHERE chat_idx = ?";
        pstmt = conn.prepareStatement(sql3);
        pstmt.setInt(1, chatIdx);
        pstmt.executeUpdate();
        
        rs.close();
		pstmt.close();
		conn.close();
	}
	
	// *******채팅댓글 삭제*******
	public void deleteChatComment(int chatCommentIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM chat_comment WHERE chat_comment_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatCommentIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅글 내용 수정*******
	public void updateChatContent(String content, int chatIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE chat" + 
				     " SET content = ?, modified = 1" + 
					 " WHERE chat_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setInt(2, chatIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅댓글 내용 수정*******
	public void updateChatComment(String comments, int chatCommentIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE chat_comment SET comments = ?, modified = 1" + 
					 " WHERE chat_comment_idx = ? AND fil";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, comments);
		pstmt.setInt(2, chatCommentIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	
//	============================== 채팅 - 채팅 내용 관련 기능(2/4) ==============================	
//	 *******공지 등록*******  -----> 파일 등록 시 어떻게 해야 할까?
	public void registerChatNotice(int chatroomIdx, int memberIdx, String content, Integer fileIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO notification_chatroom (chat_noti_idx, chatroom_idx, member_idx, content, file_idx, write_date)" + 
					 " VALUES (seq_chat_noti.nextval, ?, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, chatNotiIdx);
		pstmt.setInt(1, chatroomIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, content);
		if(fileIdx==null) {
			pstmt.setNull(4, Types.INTEGER);
		} else {
			pstmt.setInt(4, fileIdx);
		}
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******공지 수정*******  -----> 파일 수정 시 어떻게 해야 할까?
	public void updateChatNotice(String content, int chatNotiIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE notification_chatroom" + 
					" SET content = ?" + 
					" WHERE chat_noti_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setInt(2, chatNotiIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******공지 삭제*******
	public void deleteChatNotice(int chatNotiIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM notification_chatroom WHERE chat_noti_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatNotiIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	============================== 채팅 - 채팅 내용 관련 기능(3/4) ==============================
//	 *******파일 전달하는 기능 *******
//	 *******공유 및 전달할 대화방(채팅) 조회 기능*******
//	 *******공유 및 전달할 대화방(토픽) 조회 기능 *******
//	 *******공유 및 전달할 대화방(채팅, 토픽 한번에) 검색하는 기능 *******

	
	
//	============================== 채팅 - 채팅 내용 관련 기능(4/4) ==============================
//	 *******파일외부공유용url생성 *******
	public void createFileShareUrl(int fileIdx) throws Exception {
		Connection conn = getConnection();
		Common common = new Common();
		String sql = "UPDATE file_box SET url = ? WHERE file_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		String url = common.generateNewUrl();
		pstmt.setString(1, url);
		pstmt.setInt(2, fileIdx);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
//	 *******파일외부공유용url삭제 *******
	public void deleteFileShareUrl(int fileIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE file_box SET url = null WHERE file_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, fileIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	
	

//	============================== 채팅 - 채팅 상단바 ==============================
//	 *******채팅 참여 멤버 전체 조회 기능 (수정중) *******
//	파라미터: 팀idx, 채팅방idx
//	리턴: 채팅방idx, 멤버idx, 프로필, 이름, 부서, 직책, 권한
	public ArrayList<ChatroomMemberDto> getChatroomMemberList(int teamIdx, int chatroomIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<ChatroomMemberDto> listRet = new ArrayList<ChatroomMemberDto>();
		String sql = "SELECT c.chatroom_idx, cm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_url, " + 
				"					        m.name, tm.department, tm.position, tm.power " + 
				" FROM team_member tm INNER JOIN member m on tm.member_idx = m.member_idx " + 
				"					 INNER JOIN chat_member cm on m.member_idx = cm.member_idx " + 
				"					 INNER JOIN chatroom c on cm.chatroom_idx = c.chatroom_idx " + 
				" WHERE tm.team_idx = ?" + 
				" AND c.chatroom_idx = ?" + 
				" ORDER BY m.name";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, chatroomIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int memberIdx = rs.getInt("member_idx");
			String profileUrl = rs.getString("profile_url");
			String name = rs.getString("name");
			String department = rs.getString("department");
			String position = rs.getString("position");
			String power = rs.getString("power");
			ChatroomMemberDto dto = new ChatroomMemberDto(chatroomIdx, memberIdx, profileUrl, name, department, position, power);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
	//  ******* 해당 채팅에 소속되지 않은 팀 멤버 전체 조회 기능 *******		
	//	파라미터 : 팀idx, 채팅idx
	//	리턴 : 팀idx, 회원idx, 프로필, 이름, 부서, 직책, 권한
	public ArrayList<TeamMemberDto> getTeamMemberListOutOfChatroom(int teamIdx, int chatroomIdx) throws Exception {
	    Connection conn = getConnection();
	    ArrayList<TeamMemberDto> listRet = new ArrayList<TeamMemberDto>();
	
	    String sql = "SELECT tm.team_idx, tm.member_idx, NVL(member.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url, member.name, tm.department, tm.position, tm.power" + 
	                 " FROM team_member tm" + 
	                 " LEFT JOIN (" + 
	                 "     SELECT cm.member_idx" + 
	                 "     FROM chat_member cm" + 
	                 "     JOIN chatroom c ON cm.chatroom_idx = c.chatroom_idx" + 
	                 "     JOIN team_member tm ON cm.member_idx = tm.member_idx" + 
	                 "     WHERE c.chatroom_idx = ?" + 
	                 " ) chat_members ON tm.member_idx = chat_members.member_idx" + 
	                 " INNER JOIN member ON tm.member_idx = member.member_idx" + 
	                 " WHERE tm.team_idx = ?" + 
	                 " AND chat_members.member_idx IS NULL";
	    
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, chatroomIdx);
	    pstmt.setInt(2, teamIdx);
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

	

	
//	 *******채팅방에서 내보내기 기능(or나가기) *******
	public void exitChatroom(int chatroomIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "DELETE FROM chat_member WHERE chatroom_idx = ? AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, chatroomIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅방 내에 있는 파일 조회하는 기능*******
	public void getChatroomFile(int chatroomIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "SELECT cr.name \"채팅방이름\", f.file_name \"파일명\", m.name \"작성자\", TO_CHAR(f.save_date, 'yyyy/mm/dd AM HH:MI') \"등록일시\"" + 
					" FROM chatroom cr INNER JOIN chat c ON c.chatroom_idx = cr.chatroom_idx" + 
					" INNER JOIN file_box f ON c.file_idx = f.file_idx" + 
					" INNER JOIN member m ON m.member_idx = f.member_idx" + 
					" WHERE cr.chatroom_idx = ?" + 
					" ORDER BY f.save_date DESC";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatroomIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			// 이어서 작성하기~~~~~~~~~~~~~~~
		}
		rs.close();
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅방에 참여중인 멤버를 다른 토픽에 초대하는 기능 *******
	public void inviteMemberToTopic(int topicIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO topic_member(topic_idx, member_idx, manager) VALUES(?, ?, 0)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	

//	 *******채팅방 정보 변경하는 기능 *******
	public void updateChatroomInfo(int chatroomIdx, String chatName, String chatInfo) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE chatroom SET name = ?, information = ? WHERE chatroom_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, chatName);
		pstmt.setString(2, chatInfo);
		pstmt.setInt(3, chatroomIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅방 알림 켜기/끄기 기능 *******
	public void alarmOnOffChatroom(int alarm, int chatroomIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE chatroom SET alarm = ? WHERE chatroom_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, alarm);
		pstmt.setInt(2, chatroomIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}

	
//	============================== 채팅 - 채팅 입력 ==============================	
//	 *******이모티콘 조회하는 기능 *******
	/*
	 * public ArrayList<EmoticonDto> getEmoticonList() throws Exception { Connection
	 * conn = getConnection();
	 * 
	 * ArrayList<EmoticonDto> list = new ArrayList<EmoticonDto>(); String sql =
	 * "SELECT * FROM emoticon"; PreparedStatement pstmt =
	 * conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery();
	 * while(rs.next()) { int emoticonIdx = rs.getInt("emoticon_idx"); String
	 * emoticonUrl = rs.getString("emoticon_url"); EmoticonDto dto = new
	 * EmoticonDto(emoticonIdx, emoticonUrl); list.add(dto); } rs.close();
	 * pstmt.close(); conn.close();
	 * 
	 * return list; }
	 */
	
//	 *******멘션(언급)하기 위한 채팅방 멤버 조회하는 기능 *******
	
	
	//============================== 기타  ==============================
	// 채팅방방이름 불러오는 메서드
	// 파라미터: 채팅방idx
	// 리턴: 채팅방이름
		public String getChatroomNameFromChatroomIdx(int chatroomIdx) throws Exception {
			String strRet = null;
			
			String sql = "SELECT name FROM chatroom WHERE chatroom_idx=?";
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				strRet = rs.getString("name");
			}
			rs.close();
			pstmt.close();
			conn.close();
			
			return strRet;
		}
		
	//  *******채팅방의 설명 조회 기능*******	
		public String getChatroomInformation(int chatroomIdx) throws Exception {
			Connection conn = getConnection();
			String sql = "SELECT information FROM chatroom WHERE chatroom_idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
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



