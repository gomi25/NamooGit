package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dto.BookmarkListDto;

public class BookmarkDao {
	
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";	
		String pw = "7777";	
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}

	// getAllBookmarkList(): 즐겨찾기 리스트 조회하는 기능
	// 리턴: bookmark_idx, type, item_idx, content, time, location_name, author_name, profile_pic_url
	public List<BookmarkListDto> getAllBookmarkList() {
	    List<BookmarkListDto> bookmarkList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        String sql = "SELECT  " + 
	                "    b.bookmark_idx, " + 
	                "    'chat' AS type, " + 
	                "    c.chat_idx AS item_idx, " + 
	                "    c.content AS content, " + 
	                "    TO_CHAR(c.chat_date, 'YYYY/MM/DD PM HH:MI') AS time, " + 
	                "    cr.name AS location_name, " + 
	                "    m.name AS author_name, " + 
	                "    m.profile_pic_url AS profile_pic_url " +  
	                "FROM bookmark b " + 
	                "INNER JOIN chat c ON b.chat_idx = c.chat_idx " + 
	                "INNER JOIN chatroom cr ON c.chatroom_idx = cr.chatroom_idx " + 
	                "INNER JOIN member m ON c.member_idx = m.member_idx " + 
	                "WHERE b.chat_idx IS NOT NULL " + 
	                
	                "UNION ALL " + 
	                
	                "SELECT  " + 
	                "    b.bookmark_idx, " + 
	                "    'chat_comment' AS type, " + 
	                "    cc.chat_comment_idx AS item_idx, " + 
	                "    cc.comments AS content, " + 
	                "    TO_CHAR(cc.chat_date, 'YYYY/MM/DD PM HH:MI') AS time, " + 
	                "    cr.name AS location_name, " + 
	                "    m.name AS author_name, " + 
	                "    m.profile_pic_url AS profile_pic_url " +  
	                "FROM bookmark b " + 
	                "INNER JOIN chat_comment cc ON b.chat_comment_idx = cc.chat_comment_idx " + 
	                "INNER JOIN chat c ON cc.chat_idx = c.chat_idx " + 
	                "INNER JOIN chatroom cr ON c.chatroom_idx = cr.chatroom_idx " + 
	                "INNER JOIN member m ON cc.member_idx = m.member_idx " + 
	                "WHERE b.chat_comment_idx IS NOT NULL " + 

	                "UNION ALL " + 

	                "SELECT  " + 
	                "    b.bookmark_idx, " + 
	                "    'topic_board' AS type, " + 
	                "    tb.topic_board_idx AS item_idx, " + 
	                "    tb.title AS content, " + 
	                "    TO_CHAR(tb.write_date, 'YYYY/MM/DD PM HH:MI') AS time, " + 
	                "    t.name AS location_name, " + 
	                "    m.name AS author_name, " + 
	                "    m.profile_pic_url AS profile_pic_url " +  
	                "FROM bookmark b " + 
	                "INNER JOIN topic_board tb ON b.topic_board_idx = tb.topic_board_idx " + 
	                "INNER JOIN topic t ON tb.topic_idx = t.topic_idx " + 
	                "INNER JOIN member m ON tb.member_idx = m.member_idx " + 
	                "WHERE b.topic_board_idx IS NOT NULL " + 

	                "UNION ALL " + 

	                "SELECT  " + 
	                "    b.bookmark_idx, " + 
	                "    'topic_comment' AS type, " + 
	                "    tc.topic_comment_idx AS item_idx, " + 
	                "    tc.comments AS content, " + 
	                "    TO_CHAR(tc.write_date, 'YYYY/MM/DD PM HH:MI') AS time, " + 
	                "    t.name AS location_name, " + 
	                "    m.name AS author_name, " + 
	                "    m.profile_pic_url AS profile_pic_url " +  
	                "FROM bookmark b " + 
	                "JOIN topic_comment tc ON b.topic_comment_idx = tc.topic_comment_idx " + 
	                "JOIN topic_board tb ON tc.topic_board_idx = tb.topic_board_idx " + 
	                "JOIN topic t ON tb.topic_idx = t.topic_idx " + 
	                "JOIN member m ON tc.member_idx = m.member_idx " + 
	                "WHERE b.topic_comment_idx IS NOT NULL " + 

	                "UNION ALL " + 

	                "SELECT  " + 
	                "    b.bookmark_idx, " + 
	                "    'file' AS type, " + 
	                "    f.file_idx AS item_idx, " + 
	                "    f.file_name AS content, " + 
	                "    TO_CHAR(f.save_date, 'YYYY/MM/DD PM HH:MI') AS time, " + 
	                "    COALESCE(cr.name, t.name) AS location_name, " + 
	                "    m.name AS author_name, " + 
	                "    'https://cdn-icons-png.flaticon.com/512/281/281760.png' AS profile_pic_url " + // 추가된 부분
	                "FROM bookmark b " + 
	                "INNER JOIN file_box f ON b.file_idx = f.file_idx " + 
	                "LEFT JOIN chatroom cr ON f.chatroom_idx = cr.chatroom_idx " + 
	                "LEFT JOIN topic t ON f.topic_idx = t.topic_idx " + 
	                "LEFT JOIN member m ON f.member_idx = m.member_idx " + 
	                "WHERE b.file_idx IS NOT NULL " + 
	                "ORDER BY bookmark_idx DESC";

	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            BookmarkListDto dto = new BookmarkListDto();
	            dto.setBookmarkIdx(rs.getInt("bookmark_idx"));
	            dto.setType(rs.getString("type"));
	            dto.setItemIdx(rs.getInt("item_idx"));
	            dto.setContent(rs.getString("content"));
	            dto.setWriteDate(rs.getString("time"));
	            dto.setLocationName(rs.getString("location_name")); 
	            dto.setAuthorName(rs.getString("author_name"));
	            dto.setProfileUrl(rs.getString("profile_pic_url"));  
	            bookmarkList.add(dto);
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
	    return bookmarkList;
	}
	
	// isBookmarkChatroom(int, int): 채팅 즐겨찾기 여부 조회하는 기능	
	// 파라미터 : member_idx, chatroom_idx
	// 리턴: true / false
	public boolean isBookmarkChatroom(int memberIdxFrom, int chatroomIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			conn = getConnection();
			String sql = "SELECT count(*) " + 
						" FROM bookmark" + 
						" WHERE member_idx_from = ?" + 
						" AND chatroom_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdxFrom);
			pstmt.setInt(2, chatroomIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1)==1;	
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

	// isBookmarkChat(int, int): 채팅글 즐겨찾기 여부 조회하는 기능	
	// 파라미터 : member_idx, chat_idx
	// 리턴: true / false
	public boolean isBookmarkChat(int memberIdxFrom, int chatIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND chat_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdxFrom);
			pstmt.setInt(2, chatIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1)==1;	
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
	
	// isBookmarkTopic(int, int): 토픽 즐겨찾기 여부 조회하는 기능	
	// 파라미터 : member_idx, topic_idx
	// 리턴: true / false
	public boolean isBookmarkTopic(int memberIdxFrom, int topicIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND topic_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdxFrom);
			pstmt.setInt(2, topicIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1)==1;	
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
	
	// isBookamrkTopicBoard: 토픽글 즐겨찾기 여부 조회하는 기능	
	// 파라미터 : member_idx, topic_board_idx
	// 리턴: true / false
	public boolean isBookmarkTopicBoard(int memberIdxFrom, int topicBoardIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND topic_board_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdxFrom);
			pstmt.setInt(2, topicBoardIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1)==1;	
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
	
	// isBookmarkTopicComment(int, int): 토픽댓글 즐겨찾기 여부 조회하는 기능	
	// 파라미터 : member_idx, topic_comment_idx
	// 리턴: true / false
	public boolean isBookmarkTopicComment(int memberIdxFrom, int topicCommentIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			conn = getConnection();
			String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND topic_comment_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdxFrom);
			pstmt.setInt(2, topicCommentIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1)==1;	
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
	
	// isBookmark(int, String, int): 특정 항목이 즐겨찾기 되어 있는지 조회하는 기능
	// 파라미터 : member_idx, something, idx
	// 리턴: true / false
	public boolean isBookmark(int memberIdxFrom, String something, int idx) {
		final boolean DEBUG = true;    

		if(DEBUG) {		
			String[] arrSomething = {
				"member_idx_to",
				"chatroom_idx",
				"chat_idx",
				"chat_comment_idx",
				"topic_idx",
				"topic_board_idx",
				"topic_comment_idx",
				"file_idx",
				"todo_title_idx",
				"project_idx",
				"work_idx",
				"subwork_idx"
			};
			boolean check = false;
			for(String s : arrSomething) {
				if(s.equalsIgnoreCase(something))
					check = true;
			}
			if(!check) {
				System.out.println("WARNING! addBookmarkSomething()의 파라미터 something이 잘못됨. "
								 + "(something : " + something + ")");
				return false;
			}
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			conn = getConnection();	
			String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND ? = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdxFrom);
			pstmt.setString(2, something);
			pstmt.setInt(3, idx);	// something의 idx값
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
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
	
	// addBookmarkSomething(int, String, int) : 즐겨찾기 추가하는 기능 
	// 파라미터 : member_idx, something, idx
	public void addBookmarkSomething(int memberIdx, String something, int idx) {
		final boolean DEBUG = true;    

		if(DEBUG) {		
			String[] arrSomething = {
				"member_idx_to",
				"chatroom_idx",
				"chat_idx",
				"chat_comment_idx",
				"topic_idx",
				"topic_board_idx",
				"topic_comment_idx",
				"file_idx",
				"todo_title_idx",
				"project_idx",
				"work_idx",
				"subwork_idx"
			};
			boolean check = false;
			for(String s : arrSomething) {
				if(s.equalsIgnoreCase(something))
					check = true;
			}
			if(!check) {
				System.out.println("WARNING! addBookmarkSomething()의 파라미터 something이 잘못됨. "
								 + "(something : " + something + ")");
			}
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();		
			String sql = "INSERT INTO bookmark (bookmark_idx, member_idx_from, " + something + ")" 
					+ " VALUES (seq_bookmark.nextVal, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberIdx);
			pstmt.setInt(2, idx);	
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
	
	// removeBookmarkSomething(int, String, int): 즐겨찾기 해제하는 기능	
	// 파라미터 : member_idx, something, idx
	public void removeBookmarkSomething(int memberIdx, String something, int idx) {
	    final boolean DEBUG = true;
	    if(DEBUG) {
	        String[] arrSomething = {
					"member_idx_to",
					"chatroom_idx",
					"chat_idx",
					"chat_comment_idx",
					"topic_idx",
					"topic_board_idx",
					"topic_comment_idx",
					"file_idx",
					"todo_title_idx",
					"project_idx",
					"work_idx",
					"subwork_idx"
	        };
	        boolean check = false;
	        for(String s : arrSomething) {
	            if(s.equalsIgnoreCase(something))
	                check = true;
	        }
	        if(!check) {
	            System.out.println("WARNING! removeBookmarkSomething()의 파라미터 something이 잘못됨. "
	                             + "(something : " + something + ")");
	        }
	    }
	    
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	    	conn = getConnection();
	    	String sql = "DELETE FROM bookmark WHERE member_idx_from = ? AND " + something + " = ?";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, memberIdx);
	    	pstmt.setInt(2, idx);
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

}
