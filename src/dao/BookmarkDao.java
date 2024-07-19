package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	

//	테스트
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
//  *******즐겨찾기 해제 *******	
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
