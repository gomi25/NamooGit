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


    public List<BookmarkListDto> getAllBookmarkList() throws Exception {
    	Connection conn = getConnection();
        List<BookmarkListDto> bookmarkList = new ArrayList<>();
        String sql = "SELECT  " + 
        		"    b.bookmark_idx, " + 
        		"    'chat' AS type, " + 
        		"    c.chat_idx AS item_idx, " + 
        		"    c.content AS content, " + 
        		"    TO_CHAR(c.chat_date, 'YYYY/MM/DD PM HH:MI') AS time, " + 
        		"    cr.name AS location_name, " + 
        		"    m.name AS author_name " + 
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
        		"    m.name AS author_name " + 
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
        		"    m.name AS author_name " + 
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
        		"    m.name AS author_name " + 
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
        		"    m.name AS author_name " + 
        		"FROM bookmark b " + 
        		"INNER JOIN file_box f ON b.file_idx = f.file_idx " + 
        		"LEFT JOIN chatroom cr ON f.chatroom_idx = cr.chatroom_idx " + 
        		"LEFT JOIN topic t ON f.topic_idx = t.topic_idx " + 
        		"LEFT JOIN member m ON f.member_idx = m.member_idx " + 
        		"WHERE b.file_idx IS NOT NULL " + 
        		"ORDER BY bookmark_idx";

        PreparedStatement pstmt = conn.prepareStatement(sql);
    	ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
        	BookmarkListDto dto = new BookmarkListDto();
        	dto.setBookmarkIdx(rs.getInt("bookmark_idx"));
            dto.setType(rs.getString("type"));
            dto.setItemIdx(rs.getInt("item_idx"));
            dto.setContent(rs.getString("content"));
            dto.setWriteDate(rs.getString("time"));
            dto.setLocationName(rs.getString("location_name")); // 채팅방 또는 토픽방 이름
            dto.setAuthorName(rs.getString("author_name"));
            bookmarkList.add(dto);
        }
        return bookmarkList;
    }

	
	
	
	//	******* 채팅 즐겨찾기 여부 *******	
	//	파라미터 : 사용자idx, 즐겨찾기 되어있는 대상
	//	리턴: true 또는 false
	public boolean isBookmarkChatroom(int memberIdxFrom, int chatroomIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND chatroom_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, chatroomIdx);
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	//	******* 채팅글 즐겨찾기 여부 *******	
	//	파라미터 : 사용자idx, 즐겨찾기 되어있는 대상
	//	리턴: true 또는 false
	public boolean isBookmarkChat(int memberIdxFrom, int chatIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT count(*) " + 
				" FROM bookmark" + 
				" WHERE member_idx_from = ?" + 
				" AND chat_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, chatIdx);
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}	
	
	//	******* 토픽 즐겨찾기 여부 *******	
	//	파라미터 : 사용자idx, 즐겨찾기 되어있는 대상
	//	리턴: true 또는 false
	public boolean isBookmarkTopic(int memberIdxFrom, int topicIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT count(*) " + 
				" FROM bookmark" + 
				" WHERE member_idx_from = ?" + 
				" AND topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, topicIdx);
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	//	******* 토픽글 즐겨찾기 여부 *******	
	//	파라미터 : 사용자idx, 즐겨찾기 되어있는 대상
	//	리턴: true 또는 false
	public boolean isBookmarkTopicBoard(int memberIdxFrom, int topicBoardIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT count(*) " + 
				" FROM bookmark" + 
				" WHERE member_idx_from = ?" + 
				" AND topic_board_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, topicBoardIdx);
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	//	******* 토픽댓글 즐겨찾기 여부 *******	
	//	파라미터 : 사용자idx, 즐겨찾기 되어있는 대상
	//	리턴: true 또는 false
	public boolean isBookmarkTopicComment(int memberIdxFrom, int topicCommentIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT count(*) " + 
				" FROM bookmark" + 
				" WHERE member_idx_from = ?" + 
				" AND topic_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, topicCommentIdx);
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	

	//	파라미터 : 사용자idx, 즐겨찾기 되어있는 대상, 대상의idx값
	//	리턴: true 또는 false
	public boolean isBookmark(int memberIdxFrom, String something, int idx) throws Exception {
		final boolean DEBUG = true;    
		// 디버그가  활성화되어 있으면 something 파라미터가 올바른지 확인
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
			// "arrSomething" 배열에 있는 값들과 비교하여 something이 올바른 값인지 체크
			for(String s : arrSomething) {
				if(s.equalsIgnoreCase(something))
					check = true;
			}
			// check이 true이면, 파라미터 something이 오타 없이 잘 들어온 것.
			// check가 false이면, 파라미터 something에 무언가 오타가 있는 것. (sysout) --> 고칠 수 있도록.
			if(!check) {
				System.out.println("WARNING! addBookmarkSomething()의 파라미터 something이 잘못됨. "
								 + "(something : " + something + ")");
				return false;
			}
		}
		
		Connection conn = getConnection();	
		String sql = "SELECT count(*) " + 
					" FROM bookmark" + 
					" WHERE member_idx_from = ?" + 
					" AND ? = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setString(2, something);
		pstmt.setInt(3, idx);	// something의 idx값
		ResultSet rs = pstmt.executeQuery();
		boolean result = false;
		if(rs.next()) {
			result = rs.getInt(1)==1;	// 등록되어 있으면 1(true) 반환 아니면 0(false)
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	
	
	
	//	============================== 즐겨찾기 등록 및 해제 ==============================
	//  *******즐겨찾기 등록 *******	
	// 	- something : 컬럼 이름. ex. "member_idx_to"
	// 	- idx : something's idx.
	//  - memberIdx가 지정한 something에 해당하는 idx를 즐겨찾기로 추가
	// OLD) addBookmarkMember(2, 7);
	// NEW) addBookmarkSomething(2, "member_idx_to", 7);
	public void addBookmarkSomething(int memberIdx, String something, int idx) throws Exception {
		final boolean DEBUG = true;    
		// 디버그가  활성화되어 있으면 something 파라미터가 올바른지 확인
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
			// "arrSomething" 배열에 있는 값들과 비교하여 something이 올바른 값인지 체크
			for(String s : arrSomething) {
				if(s.equalsIgnoreCase(something))
					check = true;
			}
			// check이 true이면, 파라미터 something이 오타 없이 잘 들어온 것.
			// check가 false이면, 파라미터 something에 무언가 오타가 있는 것. (sysout) --> 고칠 수 있도록.
			if(!check) {
				System.out.println("WARNING! addBookmarkSomething()의 파라미터 something이 잘못됨. "
								 + "(something : " + something + ")");
			}
		}
		
		Connection conn = getConnection();		
		String sql = "INSERT INTO bookmark (bookmark_idx, member_idx_from, " + something + ")" 
					+ " VALUES (seq_bookmark.nextVal, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, idx);	// something의 idx값
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	//  *******즐겨찾기 해제	 *******	
	// 	- something : 컬럼 이름. ex. "member_idx_to"
	// 	- idx : something's idx.
	//  - memberIdx가 지정한 something에 해당하는 idx를 즐겨찾기로 추가	
	public void removeBookmarkSomething(int memberIdx, String something, int idx) throws Exception {
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
	    
	    Connection conn = getConnection();
	    String sql = "DELETE FROM bookmark WHERE member_idx_from = ? AND " + something + " = ?";
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, memberIdx);
	    pstmt.setInt(2, idx);
	    pstmt.executeUpdate();
	    
	    pstmt.close();
	    conn.close();
	}


}
