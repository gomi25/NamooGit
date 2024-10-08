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
	
	// getTeamMemberList(int): 팀 멤버 전체 조회하는 기능		
	// 파라미터 : team_idx
	// 리턴 : 팀 멤버 정보 리스트(team_idx, member_idx, profile_pic_url, name, department, position, power)
	public ArrayList<TeamMemberDto> getTeamMemberList(int teamIdx)  {
		ArrayList<TeamMemberDto> listRet = new ArrayList<TeamMemberDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT tm.team_idx, tm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
					"        m.name, tm.state, tm.department, tm.position, tm.power " + 
					" FROM member m INNER JOIN team_member tm ON m.member_idx = tm.member_idx" + 
					"    INNER JOIN team t ON tm.team_idx = t.team_idx " + 
					" WHERE t.team_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int memberIdx = rs.getInt("member_idx");
				String profileUrl = rs.getString("profile_pic_url");
				String name = rs.getString("name");
				String state = rs.getString("state");
				String department = rs.getString("department");
				String position = rs.getString("position");
				String power = rs.getString("power");
				TeamMemberDto dto = new TeamMemberDto(teamIdx, memberIdx, profileUrl, name, state, department, position, power);
				listRet.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return listRet;
	}
	
	// getTeamMember(int, int): 팀 멤버 정보 조회하는 기능		
	// 파라미터 : team_idx, member_idx
	// 리턴 : team_idx, member_idx, profile_pic_url, name, department, position, power
	public TeamMemberDto getTeamMember(int teamIdx, int memberIdx)  {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TeamMemberDto dto = null;
		
		try {
			String sql = "SELECT tm.team_idx, tm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
					"        m.name, tm.state, tm.department, tm.position, tm.power " + 
					" FROM member m INNER JOIN team_member tm ON m.member_idx = tm.member_idx" + 
					"    INNER JOIN team t ON tm.team_idx = t.team_idx " + 
					" WHERE t.team_idx = ?" + 
					" AND tm.member_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamIdx);
			pstmt.setInt(2, memberIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String profileUrl = rs.getString("profile_pic_url");
				String name = rs.getString("name");
				String state = rs.getString("state");
				String department = rs.getString("department");
				String position = rs.getString("position");
				String power = rs.getString("power");
				dto = new TeamMemberDto(teamIdx, memberIdx, profileUrl, name, state, department, position, power);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return dto;
	}
	
	// getFolderList(int, int): 특정 대상자의 폴더 리스트를 조회하는 기능
	// 파라미터: member_idx, team_idx
	// 리턴: 폴더 정보 리스트(topic_folder_idx, name)
	public ArrayList<FolderBoxDto> getFolderList(int memberIdx, int teamIdx)  {
		ArrayList<FolderBoxDto> listRet = new ArrayList<FolderBoxDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT topic_folder_idx, name" + 
					" FROM folder_box" + 
					" WHERE member_idx=? AND team_idx=?"; 
		conn = getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, teamIdx);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int topicFolderIdx = rs.getInt("topic_folder_idx");
			String name = rs.getString("name");
			FolderBoxDto dto = new FolderBoxDto(topicFolderIdx, memberIdx, teamIdx, name);
			listRet.add(dto);
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return listRet;
	}
	
	// getTopicListFromFolderIdx(int, int): 특정 폴더에 포함된 토픽 목록 조회하는 기능
	// 파라미터 : member_idx, topic_folder_idx
	// 리턴 : 폴더 안 토픽 정보 리스트(topic_idx, name, information, team_idx, open, alarm, unread, bookmark_count)
	public ArrayList<TopicDto> getTopicListFromFolderIdx(int memberIdx, int topicFolderIdx)  {
		ArrayList<TopicDto> listRet = new ArrayList<TopicDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT t.topic_idx, t.name, t.information, t.team_idx, t.open, t.alarm," + 
					" (SELECT COUNT(*) FROM topic_unread WHERE topic_board_idx=t.topic_idx) unread," + 
					" (SELECT COUNT(*) FROM bookmark b WHERE b.member_idx_from=? AND b.topic_idx=t.topic_idx) bookmark_count" + 
					" FROM topic_folder tf INNER JOIN topic t " + 
					" ON tf.topic_idx = t.topic_idx " + 
					" WHERE tf.topic_folder_idx = ? " + 
					" ORDER BY t.name";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdx);
			pstmt.setInt(2, topicFolderIdx);
			rs = pstmt.executeQuery();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return listRet;
	}
		
	// getTopicListOutside(int, int): (폴더 밖) 토픽 목록하는 조회하는 기능
	// 파라미터 : member_idx, team_idx
	// 리턴 : 폴더 밖 토픽 정보 리스트 (topic_idx, name, information, team_idx, open, alarm, unread, bookmark_count
	public ArrayList<TopicDto> getTopicListOutside(int memberIdx, int teamIdx)  {
		ArrayList<TopicDto> listRet = new ArrayList<TopicDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
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
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setInt(3, teamIdx);
			pstmt.setInt(4, memberIdx);
			pstmt.setInt(5, teamIdx);
			
			rs = pstmt.executeQuery();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return listRet;
	}
	
	// getAllTopicList(int, int): 전체  토픽 조회하는 기능
	// 파라미터 : member_idx 
	// 리턴 :전체 토픽 리스트 정보(topic_idx, name, information, team_idx, open, alarm, unread, bookmark_count)
	public ArrayList<TopicDto> getAllTopicList(int memberIdx, int teamIdx)  {
		ArrayList<TopicDto> listRet = new ArrayList<TopicDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = 
					"SELECT t.topic_idx, t.name, t.information, t.team_idx, t.open, t.alarm, " + 
							" (SELECT COUNT(*) FROM topic_unread WHERE topic_board_idx=t.topic_idx) unread," + 
							" (SELECT COUNT(*) FROM bookmark b WHERE b.member_idx_from = ? AND b.topic_idx=t.topic_idx) bookmark_count" + 
							" FROM topic t INNER JOIN topic_member tm" + 
							" ON t.topic_idx = tm.topic_idx" + 
							" WHERE tm.member_idx = ? AND t.team_idx = ?" + 
							" ORDER BY t.topic_idx";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setInt(3, teamIdx);
			rs = pstmt.executeQuery();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return listRet;
	}

	// getChatroomMembersImageUrl(int, int): 채팅방 멤버 이미지url
	// 파라미터: chatroom_idx, member_idx
	// 리턴: profile_pic_url
	public ArrayList<String> getChatroomMembersImageUrl(int chatroomIdx, int memberIdxMe)  {
		ArrayList<String> listRet = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url" + 
					" FROM chat_member cm " + 
					" INNER JOIN member m" + 
					" ON cm.member_idx = m.member_idx" + 
					" WHERE cm.chatroom_idx = ?" + 
					" AND m.member_idx != ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
			pstmt.setInt(2, memberIdxMe);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				listRet.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return listRet;
	}
	
	// getChatroomList(int, int): 채팅방 목록 조회하는 기능
	// 파라미터: member_idx, team_idx
	// 리턴: 채팅방리스트
	public ArrayList<ChatroomDto> getChatroomList(int memberIdx, int teamIdx)  {
		ArrayList<ChatroomDto> listRet = new ArrayList<ChatroomDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
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
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamIdx);
			pstmt.setInt(2, memberIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int chatroomIdx = rs.getInt("chatroom_idx");     // 채팅방idx
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	    
		return listRet;
	}

	// getStateOfMember(int, int): 특정 멤버의 상태를 조회하는 기능
	// 파라미터: team_idx, member_idx
	// 리턴: state
	public String getStateOfMember(int teamIdx, int memberIdx)  {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";

		try {
			String sql = "SELECT tm.state" + 
					" FROM team_member tm" + 
					" WHERE tm.team_idx = ?" + 
					" AND tm.member_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamIdx);
			pstmt.setInt(2, memberIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (rs != null) {rs.close();}
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		return result;
	}
	
}

