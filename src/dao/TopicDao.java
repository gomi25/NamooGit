package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dto.TeamMemberDto;
import dto.TopicBoardDto;
import dto.TopicBoardFileDto;
import dto.TopicCommentDto;
import dto.TopicMemberDto;

public class TopicDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";	
		String pw = "7777";	
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}
	
//	*******사용자가 참여하고 있는 전체 토픽목록 조회 기능*******	
//	※ SideDao 참고
	
//	============================== 토픽 - 토픽 관련 기능(1/3) ==============================
//	*******참여하지 않은 공개 토픽 목록 조회하는 기능*******

//  *******토픽 생성하는 기능(+만든사람을 토픽멤버로 추가하고 관리자 설정)*******	
//	파라미터: 토픽이름, 토픽설명, 팀idx, 공개여부, 생성자idx
//  리턴: 생성된 토픽idx	
	public int createTopic(String name, String info, int teamIdx, int open, int memberIdx) throws Exception {
	    int topicIdx = 0;  // pk값 : topic_idx
	    Connection conn = getConnection();
	    String sql1 = "INSERT INTO topic (topic_idx, name, information, team_idx, open, alarm)" 
	                + " VALUES (seq_topic.nextval, ?, ?, ?, ?, 1)";
	    PreparedStatement pstmt = conn.prepareStatement(sql1, new String[] {"topic_idx"});
	    pstmt.setString(1, name);
	    pstmt.setString(2, info);
	    pstmt.setInt(3, teamIdx);
	    pstmt.setInt(4, open);
	    pstmt.executeUpdate();
	    ResultSet rs = pstmt.getGeneratedKeys();
	    if (rs.next()) {
	        topicIdx = rs.getInt(1);
	    }
	    rs.close();
	    pstmt.close();

	    String sql2 = "INSERT INTO topic_member (topic_idx, member_idx, manager)" 
	                + " VALUES (?, ?, 1)";
	    pstmt = conn.prepareStatement(sql2);
	    pstmt.setInt(1, topicIdx);
	    pstmt.setInt(2, memberIdx);
	    pstmt.executeUpdate();

	    pstmt.close();
	    conn.close();

	    return topicIdx;
	}
	
//  *******폴더 조회 기능*******	
//	SideDao.java 에 있음

	
	
//	============================== 토픽 - 토픽 관련 기능(2/3) ==============================
//  *******폴더 생성하는 기능*******
//	INSERT INTO folder_box (topic_folder_idx, member_idx, team_idx, name) 
//	VALUES (토픽폴더idx, 토픽폴더사용자idx, 소속팀idx, '토픽폴더이름');
	public int createTopicFolder(int memberIdx, int teamIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO folder_box (topic_folder_idx, member_idx, team_idx, name)" 
				    + " VALUES (seq_topic_folder.nextval, ?, ?, '새폴더')";
		PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"topic_folder_idx"});
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, teamIdx);
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
	
//  *******폴더 이름 변경(수정)하는 기능*******	
//	UPDATE folder_box
//	SET name = '변경할폴더이름' 
//	WHERE  topic_folder_idx = 변경할토픽폴더idx; 	
	public void updateTopicFolder(int topicFolderIdx, String name) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE folder_box" + 
					 " SET name = ? " + 
					 " WHERE topic_folder_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, topicFolderIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  *******폴더 삭제하는 기능*******	
//	DELETE FROM folder_box 
//	WHERE topic_folder_idx = 3;
	public void deleteTopicFolder(int topicFolderIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "DELETE FROM folder_box"
				   + " WHERE topic_folder_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicFolderIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	
//  *******토픽을 폴더에 추가하는 기능*******	
//	INSERT INTO topic_folder (topic_folder_idx, topic_idx) 
//	VALUES (토픽폴더idx, 폴더에넣을토픽idx);
	public void addTopicToFolder(int topicFolderIdx, int topicIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO topic_folder (topic_folder_idx, topic_idx) " + 
					 " VALUES (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicFolderIdx);
		pstmt.setInt(2, topicIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}

//  *******토픽글의 토픽이름설명 조회 기능*******	
	public String getTopicInformation(int topicIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT information FROM topic WHERE topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
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
	
	

	
	
	//	============================== 토픽 - 토픽 관련 기능(3/3) ==============================
	// *******토픽글 작성하는 기능*******	 
	// 파라미터: 토픽idx, 작성자idx, 제목, 내용
	public int writeTopicBoard(int topicIdx, int memberIdx, String title, String content) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO topic_board (topic_board_idx, topic_idx, member_idx, title, content, write_date)" 
					+ " VALUES (seq_topic_board.nextval, ?, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"topic_board_idx"});
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, title);
		pstmt.setString(4, content);
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
	
	// *******토픽글 - 파일 추가하는 기능*******	 
	// 파라미터: 토픽글idx, 파일idx
	public void addFileToTopic(int topicBoardIdx, int fileIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO topic_file (topic_board_idx, file_idx)"
					+ " VALUES (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicBoardIdx);
		pstmt.setInt(2, fileIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// *******토픽글 - 파일 조회하는 기능*******	 
	// 파라미터: 토픽글idx
	// 리턴: 토픽글idx, 파일idx, 파일명
	public TopicBoardFileDto getFileInfoFromTopicBoardIdx(int topicBoardIdx) throws Exception {
		TopicBoardFileDto dto = null;
        Connection conn = getConnection();
	
		String sql = "SELECT tf.topic_board_idx, fb.file_idx, fb.file_name " + 
					" FROM topic_file tf  " + 
					" INNER JOIN file_box fb ON tf.file_idx = fb.file_idx " + 
					" WHERE tf.topic_board_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicBoardIdx);
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			dto = new TopicBoardFileDto();
			dto.setTopicBoardIdx(rs.getInt("topic_board_idx"));
            dto.setFileIdx(rs.getInt("file_idx"));
            dto.setFileName(rs.getString("file_name"));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return dto;
	}
	
//  *******토픽글 수정하는 기능*******	 ---> 파일 제외
//	UPDATE topic_board 
//	SET title = '변경할제목',
//	content = '변경할내용'
//	WHERE topic_board_idx = 변경할토픽글idx;
	public void updateTopicBoard(int topicBoardIdx, String title, String content) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE topic_board " + 
				" SET title = ?," + 
				" content = ?" + 
				" WHERE topic_board_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, title);
		pstmt.setString(2, content);
		pstmt.setInt(3, topicBoardIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  ******* 특정 토픽글 삭제하는 기능 *******	
//	파라미터: 토픽글idx
	public void deleteTopicBoard(int topicBoardIdx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			// 파일 유무 확인
			String sql1 = "SELECT file_idx FROM topic_file WHERE topic_board_idx = ?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, topicBoardIdx);
			rs = pstmt.executeQuery(); 
			
			int fileIdx = 0;
			if (rs.next()) {
				fileIdx = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
			if (fileIdx > 0) {
				String sql2 = "DELETE FROM topic_file WHERE file_idx = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, fileIdx);
				pstmt.executeUpdate();
				pstmt.close();
				
				String sql3 = "DELETE FROM file_box WHERE file_idx = ?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, fileIdx);
				pstmt.executeUpdate();
				pstmt.close();
			}		
			
			// 댓글 유무 확인 및 댓글 삭제
	        String sql4 = "SELECT topic_comment_idx FROM topic_comment WHERE topic_board_idx = ?";
	        pstmt = conn.prepareStatement(sql4);
	        pstmt.setInt(1, topicBoardIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int commentIdx = rs.getInt("topic_comment_idx");
	            
	            // 댓글 즐겨찾기 등록 여부 확인 후  삭제
	            String sql5 = "DELETE FROM bookmark WHERE topic_comment_idx = ?";
	            PreparedStatement pstmt2 = conn.prepareStatement(sql5);
	            pstmt2.setInt(1, commentIdx);
	            pstmt2.executeUpdate();
	            pstmt2.close();
	        }
	        rs.close();
	        pstmt.close();
	        
	        // 댓글 삭제
	        String sql6 = "DELETE FROM topic_comment WHERE topic_board_idx = ?";
	        pstmt = conn.prepareStatement(sql6);
	        pstmt.setInt(1, topicBoardIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
	        
	        // 즐겨찾기에서 topic_board_idx로 등록된 데이터 삭제
	        String sql7 = "DELETE FROM bookmark WHERE topic_board_idx = ?";
	        pstmt = conn.prepareStatement(sql7);
	        pstmt.setInt(1, topicBoardIdx);
	        pstmt.executeUpdate();
	        pstmt.close();
			
	        // topic_board 삭제
			String sql8 = "DELETE FROM topic_board WHERE topic_board_idx = ?";
			pstmt = conn.prepareStatement(sql8);
			pstmt.setInt(1, topicBoardIdx);
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//  ******* 토픽 내에 전체 토픽글 삭제하는 기능 *******		
//	파라미터: 토픽idx	
	public void deleteAllTopicBoard(int topicIdx) throws Exception {
		Connection conn = getConnection();
		// 삭제할 토픽의 전체 토픽글
		String sql = "DELETE FROM topic_board WHERE topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  *******토픽 내에 전체 토픽글 조회*******	
//	파라미터 : 내가 속한 팀idx, 내가 볼 토픽idx, 사용자 idx
//	리턴: 토픽글idx, 토픽idx, 작성자idx, 작성자프로필이미지, 작성자이름, 작성자상태, 글제목, 글내용, 작성일시, 안 읽은 사람 수
	public ArrayList<TopicBoardDto> getTopicBoardList(int teamIdx, int paratopicIdx, int paramemberIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<TopicBoardDto> listRet = new ArrayList<TopicBoardDto>();
		
		String sql = "SELECT tb.topic_board_idx, t.topic_idx, " + 
					"    NVL(m2.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
					"    m2.member_idx, m2.name, tm2.state, tb.title, tb.content," + 
					"    TO_CHAR(tb.write_date, 'YYYY-MM-DD PM HH:MI') write_date," + 
					"    (SELECT COUNT(*) FROM topic_unread WHERE topic_board_idx=tb.topic_board_idx) unread" + 
				"	FROM team_member tm INNER JOIN member m on tm.member_idx = m.member_idx" + 
					"	INNER JOIN topic_member tpm on m.member_idx = tpm.member_idx" + 
					"	INNER JOIN topic t on tpm.topic_idx = t.topic_idx" + 
					"	INNER JOIN topic_board tb on t.topic_idx = tb.topic_idx" + 
					"	INNER JOIN member m2 on tb.member_idx = m2.member_idx" + 
					"	INNER JOIN team_member tm2 on m2.member_idx = tm2.member_idx" + 
				"	WHERE tm.team_idx = ?" + 	
					"	AND tm2.team_idx = ?" + 
					"	AND t.topic_idx = ?" + 
					"	AND m.member_idx = ? " + 
					" ORDER BY tb.write_date DESC";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, teamIdx);
		pstmt.setInt(3, paratopicIdx);
		pstmt.setInt(4, paramemberIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int topicBoardIdx = rs.getInt("topic_board_idx");
	        int topicIdx = rs.getInt("topic_idx");
	        String profileUrl = rs.getString("profile_pic_url");
	        int memberIdx = rs.getInt("member_idx");
	        String name = rs.getString("name");
	        String state = rs.getString("state");
	        String title = rs.getString("title");
	        String content = rs.getString("content");
	        String writeDate = rs.getString("write_date");
	        int unreadCnt = rs.getInt("unread");
			
			TopicBoardDto dto = new TopicBoardDto(topicBoardIdx, topicIdx, memberIdx, profileUrl, name, state, title, content, writeDate, unreadCnt);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
	//  *******토픽글의 안 읽은 사람수 조회*******	
	//  파라미터: 토픽글idx, 작성자 제외 안 읽은 사람들의 idx 
	public void addUnreadMember(int topicBoardIdx, int[] memberIdxArray) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "INSERT INTO topic_unread(topic_board_idx, member_idx)"
						+ " VALUES(?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicBoardIdx);
			for(int memberIdx : memberIdxArray) {
				pstmt.setInt(2, memberIdx);
				pstmt.addBatch(); // Batch 추가
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
		}
	}
	
	/* 내용 추가 테스트 중 */
	public List<Integer> getTopicMembersExceptAuthor(int topicIdx, int authorIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<Integer> memberIdxList = new ArrayList<>();

	    try {
	        String sql = "SELECT member_idx FROM topic_member WHERE topic_idx = ? AND member_idx != ?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, topicIdx);
	        pstmt.setInt(2, authorIdx);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            memberIdxList.add(rs.getInt("member_idx"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
	    }

	    return memberIdxList;
	}

	
	//  *******토픽글의 댓글수 조회*******	
	//  파라미터: 토픽글idx 
	//	리턴: 토픽댓글수	
	public int getTopicCommentCnt(int topicBoardIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "SELECT count(*) FROM topic_comment WHERE topic_board_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicBoardIdx);
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		if(rs.next()) {
			result = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
//  *******토픽글의 토픽댓글 조회*******	
//  파라미터: 토픽글idx 
//	리턴: 토픽댓글Idx, 토픽글Idx, 작성자idx, 작성자프로필, 작성자이름, 상태, 댓글내용, 작성일시
	public ArrayList<TopicCommentDto> getTopicCommentList(int topicBoardIdx, int teamIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<TopicCommentDto> listRet = new ArrayList<TopicCommentDto>();
		String sql = "SELECT tc.topic_comment_idx, tc.topic_board_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
					"        m.member_idx, m.name, tm.state, tc.comments," + 
					"        TO_CHAR(tc.write_date, 'YYYY-MM-DD PM HH:MI') write_date, tc.file_idx" + 
					" FROM topic_comment tc INNER JOIN member m on tc.member_idx = m.member_idx" + 
					" INNER JOIN team_member tm on m.member_idx = tm.member_idx" + 
					" INNER JOIN team t on t.team_idx = tm.team_idx" + 
					" WHERE tc.topic_board_idx = ?" + 
					" AND t.team_idx = ?" + 
					" ORDER BY tc.write_date";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicBoardIdx);
		pstmt.setInt(2, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int topicCommentIdx = rs.getInt("topic_comment_idx");
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
			TopicCommentDto dto = new TopicCommentDto(topicCommentIdx, topicBoardIdx, memberIdx, profileUrl, name, state, comments, writeDate, fileIdx);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}

//  *******댓글 작성하는 기능*******	
//	파라미터: 토픽글idx, 작성자idx, 댓글내용, 파일idx
	public int writeTopicComment(int topicBoardIdx, int memberIdx, String comments, Integer fileIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO topic_comment(topic_comment_idx, topic_board_idx, member_idx, comments, write_date, file_idx) " + 
					 " VALUES (seq_topic_comment.nextval, ?, ?, ?, sysdate, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"topic_comment_idx"});
		pstmt.setInt(1, topicBoardIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, comments);
		if(fileIdx==null){
			pstmt.setNull(4, Types.INTEGER);	
		} else {
			pstmt.setInt(4,  fileIdx);
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
	
//  *******토픽댓글 수정하는 기능*******	
//	UPDATE topic_comment 
//	SET comments = '변경할댓글내용'
//	WHERE topic_comment_idx = 변경할토픽댓글idx; --> 파일 삭제
	public void updateTopicComment(int topicCommemtIdx, String comments, Integer fileIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE topic_comment" + 
				     "	SET comments = ?, file_idx = ?" + 
					 "	WHERE topic_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, comments);
		if(fileIdx==null){
			pstmt.setNull(2, Types.INTEGER);	
		} else {
			pstmt.setInt(2,  fileIdx);
		}
		pstmt.setInt(3, topicCommemtIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	
	
	//  *******토픽댓글 수정하는 기능(파일만 ) *******	
	//	UPDATE topic_comment 
	//	SET comments = '변경할댓글내용'
	//	WHERE topic_comment_idx = 변경할토픽댓글idx; --> 파일 삭제
	public void updateTopicCommentFile(int topicCommemtIdx, Integer fileIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE topic_comment" + 
					"	SET file_idx = ?" + 
					"	WHERE topic_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicCommemtIdx);
		pstmt.setInt(2, fileIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	
	
	//  ******특정 댓글 삭제하는 기능*******	
	//	파라미터: 삭제할토픽댓글idx
	public void deleteTopicComment(int topicCommentIdx) throws Exception {
		Connection conn = getConnection();
		String sql =  "DELETE FROM topic_comment" + 
					  "	WHERE topic_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicCommentIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  ******토픽글 내에 전체 댓글 삭제하는 기능*******		
//	파라미터: 삭제할토픽글idx
	public void deleteAllTopicComment(int topicBoardIdx) throws Exception {
		Connection conn = getConnection();
		String sql =  "DELETE FROM topic_comment" + 
					  "	WHERE topic_board_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicBoardIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	
//	============================== 토픽 - 상단바(1/3) ==============================
//  *******토픽 멤버 전체 조회 기능*******		
//	파라미터: 팀idx, 토픽idx
//	리턴: 토픽idx, 멤버idx, 프로필, 이름, 부서, 직책, 권한, 매니저여부
	public ArrayList<TopicMemberDto> getTopicMemberList(int teamIdx, int topicIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<TopicMemberDto> listRet = new ArrayList<TopicMemberDto>();
		String sql = "SELECT t.topic_idx, tpm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
					"        m.name, tm.department, tm.position, tm.power, tpm.manager" + 
					" FROM team_member tm INNER JOIN member m on tm.member_idx = m.member_idx" + 
					" INNER JOIN topic_member tpm on m.member_idx = tpm.member_idx" + 
					" INNER JOIN topic t on tpm.topic_idx = t.topic_idx" + 
					" WHERE tm.team_idx = ?" + 
					" AND t.topic_idx = ?" + 
					" ORDER BY tpm.manager desc, m.name";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, topicIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int memberIdx = rs.getInt("member_idx");
			String profileUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String department = rs.getString("department");
			String position = rs.getString("position");
			String power = rs.getString("power");
			int manager = rs.getInt("manager");
			TopicMemberDto dto = new TopicMemberDto(topicIdx, memberIdx, profileUrl, name, department, position, power, manager);
			listRet.add(dto);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
//  *******토픽 멤버 검색하는 기능(1)*******		
//	jQuery로 구현함
	
//  *******토픽 멤버 정보 조회 기능*******	
//  조직도 담당하는 팀원이 구현하는지 물어보기
	
//  *******토픽에서 내보내기 기능(1) *******		
//  파라미터: 현재 토픽방idx, 내보낼 멤버idx 	
	public void removeMemberInThisTopic(int topicIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "DELETE FROM topic_member " + 
					 " WHERE topic_idx = ? " + 
					 " AND member_idx = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  *******토픽에서 내보내기 기능(2) - 토픽 삭제 시 전체 나가게 *******		
//  파라미터: 현재 토픽방idx, 내보낼 멤버idx 	
	public void removeMemberAllInThisTopic(int topicIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM topic_member WHERE topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}

	
	
////  *******토픽에 멤버 초대하는 기능*******		
//	public void inviteOtherMemberToThisTopic(int topicIdx, int memberIdx) throws Exception {
//		Connection conn = getConnection();
//		
//		String sql = "INSERT INTO topic_member(topic_idx, member_idx, manager)" 
//					+ " VALUES(?, ?, 0)";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		
//		pstmt.setInt(1, topicIdx);
//		pstmt.setInt(2, memberIdx);
//		pstmt.executeUpdate();
//		
//		pstmt.close();
//		conn.close();
//	}
	
//  *******토픽에 여러 멤버 초대하는 기능*******		
	public void inviteOtherMembersToThisTopic(int topicIdx, int[] memberIdxArray) throws Exception {
		Connection conn = getConnection();
	    PreparedStatement pstmt = null;
	    
	    try {
	        String sql = "INSERT INTO topic_member(topic_idx, member_idx, manager) VALUES(?, ?, 0)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, topicIdx);
	        for (int memberIdx : memberIdxArray) {
	            pstmt.setInt(2, memberIdx);
	            pstmt.addBatch(); // Batch 추가
	        }
	        
	        pstmt.executeBatch(); // Batch 실행 - addBatch()와 executeBatch()를 사용하여 한 번의 데이터베이스 호출로 여러 레코드를 삽입
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Error while inviting members to topic.", e);
	    } finally {
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
	    }
	}
	
//	두 코드 모두 동일한 기능을 수행하지만 성능 최적화 측면에서 위의 방법이 더 좋다 함
//	public void inviteOtherMembersToThisTopic(int topicIdx, int[] memberIdxArray) throws Exception {
//		Connection conn = getConnection();
//		
//		String sql = "INSERT INTO topic_member(topic_idx, member_idx, manager) VALUES(?, ?, 0)";
//		PreparedStatement pstmt = null;
//		for(int i = 0; i < memberIdxArray.length; i++) {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, topicIdx);
//			pstmt.setInt(2, memberIdxArray[i]);
//			pstmt.executeUpdate();
//			pstmt.close();
//		}
//		conn.close();
//	}	
	
	
//  ******* 해당 토픽에 소속되지 않은 팀 멤버 전체 조회 기능 *******		
//	파라미터 : 팀idx, 토픽idx
//	리턴 : 팀idx, 회원idx, 프로필, 이름, 부서, 직책, 권한
	public ArrayList<TeamMemberDto> getTeamMemberListOutOfTopic(int teamIdx, int topicIdx) throws Exception {
		Connection conn = getConnection();
		ArrayList<TeamMemberDto> listRet = new ArrayList<TeamMemberDto>();
		
		String sql = "SELECT tm.team_idx, tm.member_idx, NVL(member.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url, member.name, tm.department, tm.position, tm.power" + 
				"	FROM team_member tm" + 
				"	LEFT JOIN (" + 
				"	    SELECT tm.member_idx" + 
				"	    FROM topic_member topicm" + 
				"	    JOIN topic t ON topicm.topic_idx = t.topic_idx" + 
				"	    JOIN team_member tm ON topicm.member_idx = tm.member_idx" + 
				"	    WHERE t.topic_idx = ?" + 
				"	) topic_members ON tm.member_idx = topic_members.member_idx INNER JOIN member ON tm.member_idx = member.member_idx" + 
				"	WHERE tm.team_idx = ?" + 
				"	AND topic_members.member_idx IS NULL";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
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
	
	
	
	
	
	
	
	
	
	
	
	
	

//  *******토픽 알림 켜기/끄기 조회 기능*******		
	public int getAlarm(int topicIdx, int memberIdx) throws Exception {
//		Connection conn = getConnection();
		int result = 1;
		
		
		return result;
	}
	
//  *******토픽 알림 켜기/끄기 기능*******		
	
	
//	============================== 토픽 - 상단바(2/3) ==============================
//  *******공지 조회(숨기기)하는 기능*******		
//	숨기기는jQuery로 구현함
	
	
//  *******토픽 공지 등록하는 기능(새로 작성한 글)*******		
//  파라미터: 토픽idx, 작성자idx, 등록할내용
	public void registerTopicNoti(int topicIdx, int memberIdx, String content) throws Exception {
		Connection conn = getConnection();
		
		String sql = "INSERT INTO notification_topic (topic_noti_idx, topic_idx, member_idx, content, write_date)" + 
					 " VALUES (seq_topic_noti.nextVal, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setString(3, content);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
// *******토픽 공지 수정하는 기능*******
// 파라미터: 수정할 토픽공지idx, 수정할 내용	
	public void updateTopicNoti(int topicNotiIdx, String content) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE notification_topic" + 
					" SET content = ?" + 
					" WHERE topic_noti_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, content);
		pstmt.setInt(2, topicNotiIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}

// *******토픽 공지 삭제하는 기능(1)*******		
// 파라미터: 삭제할 토픽공지idx
	public void deleteTopicNoti(int topicNotiIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM notification_topic" + 
					 " WHERE topic_noti_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicNotiIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	

// *******토픽 공지 삭제하는 기능(2)*******		
// 파라미터: 삭제할 공지가 있는 토픽idx
	public void deleteNotiOfThisTopic(int topicIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "DELETE FROM notification_topic" + 
				" WHERE topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	
	
	
	
//	============================== 토픽 - 상단바(3/3) ==============================
//  *******토픽 내에 있는 파일 조회하는 기능*******	
//	SELECT t.name "토픽방이름", f.file_name "파일명", m.name "작성자", TO_CHAR(f.save_date, 'yyyy/mm/dd AM HH:MI') "등록일시"
//	FROM topic t INNER JOIN topic_board tb ON t.topic_idx = tb.topic_idx
//	INNER JOIN topic_file tf ON tb.topic_board_idx = tb.topic_board_idx
//	INNER JOIN file_box f ON tf.file_idx = f.file_idx
//	INNER JOIN member m ON m.member_idx = f.member_idx
//	WHERE t.topic_idx = 조회할토픽방idx
//	ORDER BY f.save_date DESC;
	
	
	
//  *******토픽 정보 변경하는 기능*******
//	파라미터: 토픽idx, 변경할 이름, 변경할 내용
	public void updateTopic(int topicIdx, String name, String information) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE topic" + 
					" SET name = ?, information = ?" + 
					" WHERE topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setString(2, information);
		pstmt.setInt(3, topicIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  *******토픽 삭제하는 기능 - 테스트*******
	public void deleteTopic(int topicIdx) throws Exception {
		Connection conn = getConnection();
		// 트랜잭션 시작
        conn.setAutoCommit(false);
        
        try {
            // 1. 해당 토픽의 모든 토픽글 ID 조회
            String selectTopicBoardsQuery = "SELECT topic_board_idx FROM topic_board WHERE topic_idx = ?";
            List<Integer> topicBoardIds = new ArrayList<>();

            try (PreparedStatement selectTopicBoardspstmt = conn.prepareStatement(selectTopicBoardsQuery)) {
                selectTopicBoardspstmt.setInt(1, topicIdx);
                try (ResultSet rs = selectTopicBoardspstmt.executeQuery()) {
                    while (rs.next()) {
                        topicBoardIds.add(rs.getInt("topic_board_idx"));
                    }
                }
            }

            // 2. 조회한 토픽글 ID를 기반으로 해당 토픽글에 포함된 모든 토픽댓글 삭제
            if (!topicBoardIds.isEmpty()) {
                String deleteTopicCommentsQuery = "DELETE FROM topic_comment WHERE topic_board_idx = ?";
                try (PreparedStatement deleteTopicCommentspstmt = conn.prepareStatement(deleteTopicCommentsQuery)) {
                    for (int topicBoardId : topicBoardIds) {
                        deleteTopicCommentspstmt.setInt(1, topicBoardId);
                        deleteTopicCommentspstmt.executeUpdate();
                    }
                }

                // 3. 해당 토픽글들 삭제
                String deleteTopicBoardsQuery = "DELETE FROM topic_board WHERE topic_idx = ?";
                try (PreparedStatement deleteTopicBoardspstmt = conn.prepareStatement(deleteTopicBoardsQuery)) {
                    deleteTopicBoardspstmt.setInt(1, topicIdx);
                    deleteTopicBoardspstmt.executeUpdate();
                }
            }
            
            // 4. 해당 토픽의 모든 topic_member 데이터 삭제
            String deleteTopicMembersQuery = "DELETE FROM topic_member WHERE topic_idx = ?";
            try (PreparedStatement deleteTopicMemberspstmt = conn.prepareStatement(deleteTopicMembersQuery)) {
                deleteTopicMemberspstmt.setInt(1, topicIdx);
                deleteTopicMemberspstmt.executeUpdate();
            }
            
            // 5. 해당 토픽 삭제
            String deleteTopicQuery = "DELETE FROM topic WHERE topic_idx = ?";
            try (PreparedStatement deleteTopicpstmt = conn.prepareStatement(deleteTopicQuery)) {
                deleteTopicpstmt.setInt(1, topicIdx);
                deleteTopicpstmt.executeUpdate();
            }

            // 트랜잭션 커밋
            conn.commit();
        } catch (SQLException e) {
            // 트랜잭션 롤백
            conn.rollback();
            throw e;
        } finally {
            // 자동 커밋 모드로 되돌림
            conn.setAutoCommit(true);
        }
    }
	
	
//  ******* 토픽 삭제하는 기능 *******
//	파라미터: 토픽idx
//	public void deleteTopic(int topicIdx) throws Exception {
//		Connection conn = getConnection();
//		
//		String sql = "DELETE FROM topic WHERE topic_idx = ?";
//		PreparedStatement pstmt = conn.prepareStatement(sql);
//		
//		pstmt.setInt(1, topicIdx);
//		pstmt.executeUpdate();
//		
//		
//		pstmt.close();
//		conn.close();
//	}		
	
//	DELETE FROM topic_board WHERE topic_idx = 삭제할토픽글idx;
//	DELETE FROM topic_comment WHERE topic_board_idx = 삭제할토픽댓글idx;
//  DELETE FROM notification_topic  WHERE topic_noti_idx = 삭제할공지idx;
	
	
	
	
//  *******토픽 나가기 기능*******		
//	파라미터: 나갈토픽idx, 사용자idx
//	※ 토픽 나가는 사람이 토픽관리자인 경우 토픽관리자 변경 후 나갈 수 있음.
	public void leaveTopic(int topicIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		
		String sql = "DELETE FROM topic_member " + 
					 " WHERE topic_idx = ? " + 
					 " AND member_idx = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  *******토픽관리자 지정하는 기능*******	
//	UPDATE topic_member
//	SET manager = 1
//	WHERE topic_idx = 토픽방idx
//	AND member_idx = 관리자로 지정할 멤버idx;
	public void designateTopicManager(int topicIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE topic_member" + 
					"	SET manager = 1" + 
					"	WHERE topic_idx = ?" + 
					"	AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
//  *******토픽관리자 해제하는 기능*******	
//	UPDATE topic_member
//	SET manager = 0
//	WHERE topic_idx = 토픽방idx
//	AND member_idx = 토픽을 나갈 멤버idx;
	public void revokeTopicManager(int topicIdx, int memberIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE topic_member" + 
					"	SET manager = 0" + 
					"	WHERE topic_idx = ?" + 
					"	AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}	
	
//	============================== 기타  ==============================
	// 토픽방이름 불러오는 메서드
	// 파라미터: 토픽idx
	// 리턴: 토픽이름
	public String getTopicNameFromTopicIdx(int topicIdx) throws Exception {
		String strRet = null;

		String sql = "SELECT name FROM topic WHERE topic_idx=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, topicIdx);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			strRet = rs.getString("name");
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return strRet;
	}
	
	
	
}
