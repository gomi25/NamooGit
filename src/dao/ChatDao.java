package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import com.Common;

import dto.ChatContentsDto;
import dto.ChatroomMemberDto;
import dto.ProfileUrlImgDto;
import dto.TopicMemberDto;

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
	
	//	특정 채팅방의 채팅글 조회 ---> 콘솔에서 프로필부분이 여러번 나옴..
	public ArrayList<ChatContentsDto> getChatContents(int chatroomIdx) throws Exception {
		ArrayList<ChatContentsDto> chatContents = new ArrayList<ChatContentsDto>(); 
		ArrayList<ProfileUrlImgDto> profileUrlColor = new ArrayList<ProfileUrlImgDto>(); 
		
		String sql = "	SELECT  c.chat_idx \"채팅글idx\", m.member_idx \"채팅멤버idx\", m.profile_pic_url profile_pic_url1," + 
				"    (SELECT img_url FROM profile_img WHERE profile_img_idx = m.profile_img_idx) profile_pic_url2," + 
				"    (SELECT color FROM color WHERE color_idx = m.color_idx) profile_color," + 
				"    m.name \"이름\", tm.state \"상태\", c.content \"내용\", c.file_idx \"파일\", c.emoticon_idx \"이모티콘\"," + 
				"    TO_CHAR(c.chat_date, 'PM HH:MI') \"작성일시\"," + 
				"    (SELECT COUNT(*) FROM chat_unread WHERE chat_idx=c.chat_idx) \"안읽은사람수\", c.modified \"수정여부\"" + 
				"	FROM team_member tm INNER JOIN member m on tm.member_idx = m.member_idx" + 
				"	INNER JOIN chat c on m.member_idx = c.member_idx" + 
				"	INNER JOIN chatroom cr on c.chatroom_idx = cr.chatroom_idx" + 
				"	WHERE c.chatroom_idx = ?" + 
				"	ORDER BY c.chat_date";
		
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatroomIdx);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int chatIdx = rs.getInt("채팅글idx");
			int memberIdx = rs.getInt("채팅멤버idx");
			String profilePicUrl1 = rs.getString("profile_pic_url1");
			int profilePicUrl2 = rs.getInt("profile_pic_url2");
			ProfileUrlImgDto dto = new ProfileUrlImgDto(memberIdx, profilePicUrl1, profilePicUrl2);
			profileUrlColor.add(dto);
			String name = rs.getString("이름");
			Integer state = rs.getInt("상태");
			String content = rs.getString("내용");
			Integer fileIdx = rs.getInt("파일");
			if(rs.getInt("파일") == 0) {
				fileIdx = null;
			}
			Integer emoticonIdx = rs.getInt("이모티콘");
			if(rs.getInt("이모티콘") == 0) {
				emoticonIdx = null;
			}
			String writeDate = rs.getString("작성일시");
			int unreadCnt = rs.getInt("안읽은사람수");
			int modified = rs.getInt("수정여부");
			
			ChatContentsDto chatcontent = new ChatContentsDto(chatIdx, profileUrlColor, name, state, content, fileIdx, emoticonIdx, writeDate, unreadCnt, modified);
			chatContents.add(chatcontent);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return chatContents;
	}
	

	
//	============================== 채팅 - 채팅 내용 관련 기능(1/4) ==============================
//	 *******채팅방 생성하는 기능*******
	
	
	
//	 *******채팅방에 멤버 추가*******
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
	
	// 파일idx 리턴하는 파일등록메서드를 먼저 수행 후 리턴 받은 파일idx를 파라미터로 받아서 채팅글 작성?
	// 파일 있는 경우 
	// 	INSERT INTO chat (chat_idx, chatroom_idx, member_idx, content, chat_date, emoticon_idx, file_idx, modified) 
	//	VALUES (chat_seq.nextval, '해당 채팅방idx', '작성자 idx', '작성 내용', sysdate, '이모티콘idx', file_seq.currval, 0);
//	 *******채팅글 작성*******
	public void writeChat(int chatroomIdx, int memberIdx, String content, Integer fileIdx, Integer emoticonIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO chat (chat_idx, chatroom_idx, member_idx, content, chat_date, file_idx, emoticon_idx, modified)" +
					 " VALUES (seq_chat.nextval, ?, ?, ?, sysdate, ?, ?, 0)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, chatIdx);
		pstmt.setInt(1, chatroomIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, content);
		if(fileIdx==null){
			pstmt.setNull(4, Types.INTEGER);	
		} else {
			pstmt.setInt(4,  fileIdx);
		}
		if(emoticonIdx==null) {
			pstmt.setNull(5, Types.INTEGER);
		} else {
			pstmt.setInt(5, emoticonIdx);
		}
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅댓글 작성*******
	public void writeChatComment(int chatIdx, int memberIdx, String comments, Integer fileIdx, Integer emoticonIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO chat_comment(chat_comment_idx, chat_idx, member_idx, comments, chat_date, file_idx, emoticon_idx, modified)" +	 
					 " VALUES(seq_chat_comment, ?, ?, ?, sysdate, ?, ?, 0)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, chatCommentIdx);
		pstmt.setInt(1, chatIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, comments);
		if(fileIdx==null) {
			pstmt.setNull(4, Types.INTEGER);
		} else {
			pstmt.setInt(4, fileIdx);
		}
		if(emoticonIdx==null) {
			pstmt.setNull(5, Types.INTEGER);
		} else {
			pstmt.setInt(5, emoticonIdx);
		}
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅 읽으면 숫자 줄어드는 기능*******
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
	
//	 *******채팅글 삭제*******
	public void deleteChatContent(int chatIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM chat" + 
					 " WHERE chat_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, chatIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//	 *******채팅댓글 삭제*******
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
		String sql = "SET comments = ?, modified = 1" + 
					 " WHERE chat_comment_idx = ?";
		
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
		String sql = ""
				+ ""
				+ ""
				+ ""
				+ ""
				+ ""
				+ "	수정!!!!!!!"
				+ ""
				+ ""
				+ ""
				+ ""
				+ ""
				+ ""
				+ "";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, chatroomIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int memberIdx = rs.getInt("member_idx");
			String profileUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String department = rs.getString("department");
			String position = rs.getString("position");
			String power = rs.getString("power");
			int manager = rs.getInt("manager");
			ChatroomMemberDto dto = new ChatroomMemberDto(chatroomIdx, memberIdx, profileUrl, name, department, position, power);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
	
	
//	 *******채팅 참여 멤버 검색하는 기능*******
	
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
	
	
	
//	 *******파일 업로드 하는 기능 *******
	/* 
	(1)
	file_box에 먼저 insert 하고
	chat 에 insert

	(2)
	chat 에 insert ( 파일 null)  해놓고
	file_box 에 insert
	그 다음에 그 chat 내용에 파일idx update 
	*/
	
	// 파일 등록 메서드 -> 파일idx를 리턴,,??? 외부 공유용url 생성
	//	INSERT INTO file_box (file_idx, file_name, save_date, volume, member_idx, file_type_idx, project_idx, chatroom_idx, topic_idx, url) 
	//	VALUES (seq_file.nextval, '등록할파일명', sysdate, '파일용량', 등록자idx, 파일유형idx, 프로젝트idx, 채팅방idx, 토픽idx, 외부공유용url);
	public int registerFile(String fileName, String volume, int memberIdx, int fileTypeIdx, Integer projectIdx, Integer chatroomIdx, Integer topicIdx, String url) throws Exception {
		Connection conn = getConnection();
		
//		String sql = "INSERT INTO file_box (file_idx, file_name, save_date, volume, member_idx, file_type_idx, project_idx, chatroom_idx, topic_idx, url)" + 
//					 " VALUES (seq_file.nextval, ?, sysdate, ?, ?, ?, ?, ?, ?, ?)";
		int fileIdx = 0;
		String sql = "INSERT INTO file_box (file_idx, file_name, save_date, volume, member_idx, file_type_idx, project_idx, chatroom_idx, topic_idx, url)" + 
				     " VALUES (seq_file.nextval, ?, sysdate, ?, ?, ?, ?, ?, ?, ?)";
		
		String[] arrStr = {"file_idx"};
		PreparedStatement pstmt = conn.prepareStatement(sql, arrStr);
//		pstmt.setInt(1, fileIdx);
		pstmt.setString(1, fileName);
		pstmt.setString(2, volume);
		pstmt.setInt(3, memberIdx);
		pstmt.setInt(4, fileTypeIdx);
		if(projectIdx==null) {
			pstmt.setNull(5, Types.INTEGER);
		} else {
			pstmt.setInt(5, projectIdx);
		}
		if(chatroomIdx==null) {
			pstmt.setNull(6, Types.INTEGER);
		} else {
			pstmt.setInt(6, chatroomIdx);
		}
		if(topicIdx==null) {
			pstmt.setNull(7, Types.INTEGER);
		} else {
			pstmt.setInt(7, topicIdx);
		}
		pstmt.setString(8, url);
		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()) {
			fileIdx = rs.getInt(1);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return fileIdx;
	}

	
	
}
