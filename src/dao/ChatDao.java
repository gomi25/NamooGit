package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
	
	// getChatContents(int, int): 특정 채팅방의 채팅글 조회
	// 파라미터: team_idx, chatroom_idx, member_idx
	// 리턴: 채팅글 리스트(chat_idx, chatroom_idx, member_idx, profile_url, name, state, content, file_idx, file_name, write_date, unread_cnt, modified
	public ArrayList<ChatContentsDto> getChatContents(int teamIdx, int chatroomIdx, int memberIdx) {
		ArrayList<ChatContentsDto> listRet = new ArrayList<ChatContentsDto>(); 
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	    	String sql = " SELECT c.chat_idx, cr.chatroom_idx, " +
	    			"NVL(m2.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_url, m2.member_idx, " +
	    			"m2.name, tm2.state, c.content, c.file_idx, " +
	    			"TO_CHAR(c.chat_date, 'AM HH:MI', 'NLS_DATE_LANGUAGE=AMERICAN') write_date, " +
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
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, teamIdx);
	    	pstmt.setInt(2, teamIdx);
	    	pstmt.setInt(3, chatroomIdx);
	    	pstmt.setInt(4, memberIdx);
			
	    	rs = pstmt.executeQuery();
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
	
	// getChatCommentList(int, int): 특정 채팅글에 포함된 채팅댓글 리스트 조회
	// 파라미터: chat_comment_idx, team_idx 
	// 리턴: 채팅댓글 리스트(chat_comment_idx, chat_idx, member_idx, profile_pic_url, name, state, comments, write_date)
	public ArrayList<ChatCommentDto> getChatCommentList(int chatIdx, int teamIdx) {
		ArrayList<ChatCommentDto> listRet = new ArrayList<ChatCommentDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
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
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatIdx);
			pstmt.setInt(2, teamIdx);
			rs = pstmt.executeQuery();
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
	
	// createChatroom(int, int, String, String): 채팅방 생성하는 기능
	// 파라미터: chatroom_idx, name, information, team_idx, alarm
	// 리턴: chatroom_idx
	public int createChatroom(int teamIdx, int memberIdx, String name, String info) {
	    Connection conn = null;
	    PreparedStatement pstmt = null; 
	    ResultSet rs = null;
	    int chatroomIdx = 0;
	    try {
	    	conn = getConnection();
	    	String sql1 = "INSERT INTO chatroom (chatroom_idx, name, information, team_idx, alarm)" 
	    			+ " VALUES (seq_chatroom.nextval, ?, ?, ?, 1)";
	    	pstmt = conn.prepareStatement(sql1, new String[] {"chatroom_idx"});
	    	pstmt.setString(1, name);
	    	pstmt.setString(2, info);
	    	pstmt.setInt(3, teamIdx);
	    	pstmt.executeUpdate();
	    	
	    	rs = pstmt.getGeneratedKeys();
	    	if (rs.next()) {
	    		chatroomIdx = rs.getInt(1);
	    	}
	    	rs.close();
	    	pstmt.close();

	    	// 만든 사람 멤버로 추가
	    	String sql2 = "INSERT INTO chat_member (chatroom_idx, member_idx)" 
	    			+ " VALUES (?, ?)";
	    	pstmt = conn.prepareStatement(sql2);
	    	pstmt.setInt(1, chatroomIdx);
	    	pstmt.setInt(2, memberIdx);
	    	pstmt.executeUpdate();
	    
	    	pstmt.close();
	    	conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 

	    return chatroomIdx;
	}	
	
	// inviteMembersToChatroom(int, int[]): 채팅방에 멤버 초대하는 기능
	// 파라미터: chatroom_idx, member_idx
	public void inviteMembersToChatroom(int chatroomIdx, int[] memberIdxArray) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        String sql = "INSERT INTO chat_member(chatroom_idx, member_idx) VALUES(?, ?)";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        for (int memberIdx : memberIdxArray) {
	        	pstmt.setInt(1, chatroomIdx);
	            pstmt.setInt(2, memberIdx);
	            pstmt.addBatch(); // Batch 추가
	        }
	        pstmt.executeBatch(); // Batch 실행 - addBatch()와 executeBatch()를 사용하여 한 번의 데이터베이스 호출로 여러 레코드를 삽입
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
	
	// writeChat(int, int, String, Integer): 채팅글 작성하는 기능
	// 파라미터: chat_idx, chatroom_idx, member_idx, content, file_idx
	// 리턴 : chat_idx
	public int writeChat(int chatroomIdx, int memberIdx, String content, Integer fileIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int chatIdx = 0;

		try {
			String sql = "INSERT INTO chat (chat_idx, chatroom_idx, member_idx, content, chat_date, file_idx, modified)" +
					" VALUES (seq_chat.nextval, ?, ?, ?, sysdate, ?, 0)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql, new String[] {"chat_idx"});
			pstmt.setInt(1, chatroomIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setString(3, content);
			if(fileIdx==null){
				pstmt.setNull(4, Types.INTEGER);	
			} else {
				pstmt.setInt(4, fileIdx);
			}
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				chatIdx = rs.getInt(1);
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
		return chatIdx;
	}
	
	// addUnreadChatToMember(int, int[]): 채팅을 안 읽은 멤버 데이터 추가하는 기능
	// 파라미터: chat_idx, member_idx
	public void addUnreadChatToMember(int chatIdx, int[] memberIdxArray) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "INSERT INTO chat_unread(chat_idx, member_idx)"
						+ " VALUES(?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatIdx);
			for(int memberIdx : memberIdxArray) {
				pstmt.setInt(2, memberIdx);
				pstmt.addBatch(); // Batch 추가
			}
			pstmt.executeBatch();
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
	
	// getChatMembersExceptAuthor(int, int): 채팅글의 안 읽은 사람수 데이터 조회
	// 파라미터: chatroom_idx, member_idx
	// 리턴: member_idx
	public List<Integer> getChatMembersExceptAuthor(int chatroomIdx, int authorIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<Integer> memberIdxList = new ArrayList<>();

	    try {
	        String sql = "SELECT member_idx FROM chat_member WHERE chatroom_idx = ? AND member_idx != ?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.setInt(2, authorIdx);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            memberIdxList.add(rs.getInt("member_idx"));
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
	    
	    return memberIdxList;
	}
	
	// writeChatComment(int, int, String, Integer): 채팅댓글 작성
	// 파라미터: chat_comment_idx, chat_idx, member_idx, comments, file_idx
	public void writeChatComment(int chatIdx, int memberIdx, String comments, Integer fileIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO chat_comment(chat_comment_idx, chat_idx, member_idx, comments, chat_date, file_idx, modified)" +	 
					" VALUES(seq_chat_comment.nextval, ?, ?, ?, sysdate, ?, 0)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setString(3, comments);
			if(fileIdx==null) {
				pstmt.setNull(4, Types.INTEGER);
			} else {
				pstmt.setInt(4, fileIdx);
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// readChat(int, int): 채팅글 읽으면 안 읽은 멤버 숫자 줄어드는 기능
	// 파라미터: chat_idx, member_idx
	public void readChat(int chatIdx, int memberIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM chat_unread" + 
					" WHERE chat_idx = ?" + 
					" AND member_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// deleteChatContent(int): 채팅글 삭제하는 기능
	// 파라미터: chat_idx
	public void deleteChatContent(int chatIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT file_idx FROM chat WHERE chat_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatIdx);
			rs = pstmt.executeQuery();
			
			int fileIdx = 0;
			if (rs.next()) {
				fileIdx = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
			if (fileIdx != 0) {
				String sql2 = "DELETE FROM file_box WHERE file_idx = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, fileIdx);
				pstmt.executeUpdate();
				pstmt.close();
			}
			
			String sql3 = "DELETE FROM chat WHERE chat_idx = ?";
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, chatIdx);
			pstmt.executeUpdate();
			pstmt.close();
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
	}
	
	// deleteChatComment(int): 채팅댓글 삭제하는 기능
	// 파라미터: chat_comment_idx
	public void deleteChatComment(int chatCommentIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM chat_comment WHERE chat_comment_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatCommentIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// updateChatContent(String, int): 채팅글 내용 수정하는 기능
	// 파라미터: content, chat_idx
	public void updateChatContent(String content, int chatIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE chat" + 
					" SET content = ?, modified = 1" + 
					" WHERE chat_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, chatIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// updateChatComment(String, int): 채팅댓글 내용 수정하는 기능
	// 파라미터: comments, chat_comment_idx, 
	public void updateChatComment(String comments, int chatCommentIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE chat_comment SET comments = ?, modified = 1" + 
					" WHERE chat_comment_idx = ?";
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comments);
			pstmt.setInt(2, chatCommentIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}

	// registerChatNotice(int, int, String, Integer): 공지 등록하는 기능
	// 파라미터: chatroom_idx, member_idx, content, file_idx
	public void registerChatNotice(int chatroomIdx, int memberIdx, String content, Integer fileIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO notification_chatroom (chat_noti_idx, chatroom_idx, member_idx, content, file_idx, write_date)" + 
					" VALUES (seq_chat_noti.nextval, ?, ?, ?, ?, sysdate)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setString(3, content);
			if(fileIdx==null) {
				pstmt.setNull(4, Types.INTEGER);
			} else {
				pstmt.setInt(4, fileIdx);
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// updateChatNotice(String, int): 채팅방 공지 수정하는 기능
	// 파라미터: content, chat_noti_idx
	public void updateChatNotice(String content, int chatNotiIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE notification_chatroom" + 
					" SET content = ?" + 
					" WHERE chat_noti_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setInt(2, chatNotiIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// deleteChatNotice(int): 공지 삭제하는 기능
	// 파라미터: chat_noti_idx
	public void deleteChatNotice(int chatNotiIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM notification_chatroom WHERE chat_noti_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatNotiIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// createFileShareUrl(int): 파일외부공유용url생성
	// 파라미터: file_idx
	public void createFileShareUrl(int fileIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Common common = new Common();
		try {
			String sql = "UPDATE file_box SET url = ? WHERE file_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			String url = common.generateNewUrl();
			pstmt.setString(1, url);
			pstmt.setInt(2, fileIdx);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// deleteFileShareUrl(int): 파일외부공유용url 삭제하는 기능
	// 파라미터: file_idx
	public void deleteFileShareUrl(int fileIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE file_box SET url = null WHERE file_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fileIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}	
	
	// getChatroomMemberList(int, int): 채팅 참여 멤버 전체 조회하는 기능
	// 파라미터: team_idx, chatroom_idx
	// 리턴: chatroom_idx, member_idx, profile_url, state, name, department, position, power
	public ArrayList<ChatroomMemberDto> getChatroomMemberList(int teamIdx, int chatroomIdx) {
		ArrayList<ChatroomMemberDto> listRet = new ArrayList<ChatroomMemberDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "SELECT c.chatroom_idx, cm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_url, " + 
					"					        m.name, tm.state, tm.department, tm.position, tm.power " + 
					" FROM team_member tm INNER JOIN member m on tm.member_idx = m.member_idx " + 
					"					 INNER JOIN chat_member cm on m.member_idx = cm.member_idx " + 
					"					 INNER JOIN chatroom c on cm.chatroom_idx = c.chatroom_idx " + 
					" WHERE tm.team_idx = ?" + 
					" AND c.chatroom_idx = ?" + 
					" ORDER BY m.name";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamIdx);
			pstmt.setInt(2, chatroomIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int memberIdx = rs.getInt("member_idx");
				String profileUrl = rs.getString("profile_url");
				String name = rs.getString("name");
				String state = rs.getString("state");
				String department = rs.getString("department");
				String position = rs.getString("position");
				String power = rs.getString("power");
				ChatroomMemberDto dto = new ChatroomMemberDto(chatroomIdx, memberIdx, profileUrl, name, state, department, position, power);
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
	
	// getTeamMemberListOutOfChatroom(int, int): 해당 채팅에 소속되지 않은 팀 멤버 전체 조회하는 기능
	// 파라미터 : team_idx, chatroom_idx
	// 리턴 : 멤버 정보 리스트(team_idx, member_idx, profile_pic_url, name, department, position, power)
	public ArrayList<TeamMemberDto> getTeamMemberListOutOfChatroom(int teamIdx, int chatroomIdx) {
	    ArrayList<TeamMemberDto> listRet = new ArrayList<TeamMemberDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
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
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, chatroomIdx);
	    	pstmt.setInt(2, teamIdx);
	    	rs = pstmt.executeQuery();
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

	// deleteChatroom(int): 채팅방 삭제하는 기능
	// 파라미터: chatroom_idx
	public void deleteChatroom(int chatroomIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        // 1. 해당 채팅방의 모든 chat_idx 조회
	        String selectChatsSql = "SELECT chat_idx FROM chat WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(selectChatsSql);
	        pstmt.setInt(1, chatroomIdx);
	        rs = pstmt.executeQuery(); 

	        List<Integer> allChatIdx = new ArrayList<>();
	        while (rs.next()) {
	            allChatIdx.add(rs.getInt("chat_idx"));
	        }
	        rs.close();
	        pstmt.close();
	        
	        // 2. 조회한 chat_idx를 기반으로 관련된 chat_comment_idx, file_idx 조회 및 삭제
	        for (int chatIdx : allChatIdx) {
	            // chat_comment 조회 및 삭제
	            String selectCommentsSql = "SELECT chat_comment_idx, file_idx FROM chat_comment WHERE chat_idx = ?";
	            pstmt = conn.prepareStatement(selectCommentsSql);
	            pstmt.setInt(1, chatIdx);
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                int chatCommentIdx = rs.getInt("chat_comment_idx");
	                int fileIdx = rs.getInt("file_idx");

	                // 즐겨찾기에서 해당 chat_comment_idx 삭제
	                String deleteBookmarkSql = "DELETE FROM bookmark WHERE chat_comment_idx = ?";
	                PreparedStatement pstmt2 = conn.prepareStatement(deleteBookmarkSql);
	                pstmt2.setInt(1, chatCommentIdx);
	                pstmt2.executeUpdate();
	                pstmt2.close();

	                // file_box에서 해당 file_idx 삭제 (file_idx가 0보다 큰 경우에만)
	                if (fileIdx > 0) {
	                    String deleteFileSql = "DELETE FROM file_box WHERE file_idx = ?";
	                    pstmt2 = conn.prepareStatement(deleteFileSql);
	                    pstmt2.setInt(1, fileIdx);
	                    pstmt2.executeUpdate();
	                    pstmt2.close();
	                }
	            }
	            rs.close();
	            pstmt.close();

	            // 모든 chat_comment 삭제
	            String deleteCommentsSql = "DELETE FROM chat_comment WHERE chat_idx = ?";
	            pstmt = conn.prepareStatement(deleteCommentsSql);
	            pstmt.setInt(1, chatIdx);
	            pstmt.executeUpdate();
	            pstmt.close();

	            // 즐겨찾기에서 해당 chat_idx 삭제
	            String deleteBookmarkForChatSql = "DELETE FROM bookmark WHERE chat_idx = ?";
	            pstmt = conn.prepareStatement(deleteBookmarkForChatSql);
	            pstmt.setInt(1, chatIdx);
	            pstmt.executeUpdate();
	            pstmt.close();
	        }

	        // 3. chat 삭제
	        String deleteChatsSql = "DELETE FROM chat WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(deleteChatsSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        // 4. 즐겨찾기에서 해당 chatroom_idx 삭제
	        String deleteBookmarkForChatroomSql = "DELETE FROM bookmark WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(deleteBookmarkForChatroomSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        // 5. file_box에서 해당 chatroom_idx 관련 파일 삭제
	        String deleteFilesSql = "DELETE FROM file_box WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(deleteFilesSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        // 6. chat_member 삭제
	        String deleteChatMembersSql = "DELETE FROM chat_member WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(deleteChatMembersSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        // 7. chatroom 삭제
	        String deleteChatroomSql = "DELETE FROM chatroom WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(deleteChatroomSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) { rs.close(); }
	            if (pstmt != null) { pstmt.close(); }
	            if (conn != null) { conn.close(); }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	// exitChatroom(int, int): 채팅방 내보내기 기능
	// 파라미터: chatroom_idx, member_idx
	public void exitChatroom(int chatroomIdx, int memberIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM chat_member WHERE chatroom_idx = ? AND member_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
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
	
	// leaveChatroom(int, int): 채팅방 나가기 기능
	// 파라미터: chatroom_idx, member_idx
	public void leaveChatroom(int chatroomIdx, int memberIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        // 1. 해당 채팅방의 모든 chat_idx 조회
	        String selectChatsSql = "SELECT chat_idx FROM chat WHERE chatroom_idx = ?";
	        pstmt = conn.prepareStatement(selectChatsSql);
	        pstmt.setInt(1, chatroomIdx);
	        rs = pstmt.executeQuery(); 

	        List<Integer> allChatIdx = new ArrayList<>();
	        while (rs.next()) {
	            allChatIdx.add(rs.getInt("chat_idx"));
	        }
	        rs.close();
	        pstmt.close();
	        
	        // 2. 조회한 chat_idx를 기반으로 관련된 chat_comment_idx 조회 및 즐겨찾기 삭제
	        for (int chatIdx : allChatIdx) {
	            // chat_comment 조회
	            String selectCommentsSql = "SELECT chat_comment_idx FROM chat_comment WHERE chat_idx = ?";
	            pstmt = conn.prepareStatement(selectCommentsSql);
	            pstmt.setInt(1, chatIdx);
	            rs = pstmt.executeQuery();

	            List<Integer> allChatCommentIdx = new ArrayList<>();
	            while (rs.next()) {
	                allChatCommentIdx.add(rs.getInt("chat_comment_idx"));
	            }
	            rs.close();
	            pstmt.close();

	            // 즐겨찾기에서 해당 chat_idx와 chat_comment_idx에 대한 데이터 삭제
	            String deleteBookmarkForChatSql = "DELETE FROM bookmark WHERE (chat_idx = ? OR chat_comment_idx = ?) AND member_idx_from = ?";
	            pstmt = conn.prepareStatement(deleteBookmarkForChatSql);
	            pstmt.setInt(1, chatIdx);
	            
	            for (int chatCommentIdx : allChatCommentIdx) {
	                pstmt.setInt(2, chatCommentIdx);
	                pstmt.setInt(3, memberIdx);
	                pstmt.executeUpdate();
	            }
	            pstmt.close();
	        }
	        
	        // 3. 즐겨찾기에서 해당 chatroom_idx와 연결된 데이터 삭제
	        String deleteBookmarkForChatroomSql = "DELETE FROM bookmark WHERE chatroom_idx = ? AND member_idx_from = ?";
	        pstmt = conn.prepareStatement(deleteBookmarkForChatroomSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.setInt(2, memberIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        // 4. chat_member에서 해당 member 삭제
	        String deleteChatMemberSql = "DELETE FROM chat_member WHERE chatroom_idx = ? AND member_idx = ?";
	        pstmt = conn.prepareStatement(deleteChatMemberSql);
	        pstmt.setInt(1, chatroomIdx);
	        pstmt.setInt(2, memberIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) { rs.close(); }
	            if (pstmt != null) { pstmt.close(); }
	            if (conn != null) { conn.close(); }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
		
	// inviteMemberToTopic(int, int): 채팅방에 참여중인 멤버를 다른 토픽에 초대하는 기능
	// 파라미터: topic_idx, member_idx
	public void inviteMemberToTopic(int topicIdx, int memberIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "INSERT INTO topic_member(topic_idx, member_idx, manager) VALUES(?, ?, 0)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}

	// updateChatroomInfo(int, String, String): 채팅방 정보 변경하는 기능
	// 파라미터: chatroom_idx, name, information
	public void updateChatroomInfo(int chatroomIdx, String chatName, String chatInfo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE chatroom SET name = ?, information = ? WHERE chatroom_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chatName);
			pstmt.setString(2, chatInfo);
			pstmt.setInt(3, chatroomIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// alarmOnOffChatroom(int, int): 채팅방 알림 켜기/끄기 기능
	// 파라미터: alarm, chatroom_idx
	public void alarmOnOffChatroom(int alarm, int chatroomIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE chatroom SET alarm = ? WHERE chatroom_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, alarm);
			pstmt.setInt(2, chatroomIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	// getChatroomNameFromChatroomIdx(int): 채팅방 이름 조회하는 기능
	// 파라미터: chatroom_idx
	// 리턴: name
	public String getChatroomNameFromChatroomIdx(int chatroomIdx) {
		String strRet = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT name FROM chatroom WHERE chatroom_idx=?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				strRet = rs.getString("name");
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
		return strRet;
	}
		
	// getChatroomInformation(int): 채팅방의 설명 조회하는 기능
	// 파라미터: chatroom_idx
	// 리턴: information
	public String getChatroomInformation(int chatroomIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";

		try {
			String sql = "SELECT information FROM chatroom WHERE chatroom_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroomIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	            if (pstmt != null) {pstmt.close();}
	            if (conn != null) {conn.close();}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		return result;
	}	
		
	// getChatroomMemberCount(int): 채팅방 멤버 수 조회하는 기능
	// 파라미터: chatroom_idx
	// 리턴: 채팅방 내 멤버 수
	public int getChatroomMemberCount(int chatroomIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int memberCount = 0;
	    try {
	        String sql = "SELECT COUNT(*) FROM chat_member WHERE chatroom_idx = ?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, chatroomIdx);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            memberCount = rs.getInt(1);
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
	    return memberCount;
	}
		
}

