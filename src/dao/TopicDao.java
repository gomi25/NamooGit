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
	
	
	/* =============================== 폴더 ==================================== */	
	
	// createTopicFolder(int, int): 폴더 생성하는 기능
	// 파라미터: member_idx, team_idx
	// 리턴: topic_folder_idx
	public int createTopicFolder(int memberIdx, int teamIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    int ret = 0;
	    try {
			conn = getConnection();
			String sql = "INSERT INTO folder_box (topic_folder_idx, member_idx, team_idx, name)" 
					    + " VALUES (seq_topic_folder.nextval, ?, ?, '새폴더')";
			pstmt = conn.prepareStatement(sql, new String[] {"topic_folder_idx"});
			pstmt.setInt(1, memberIdx);
			pstmt.setInt(2, teamIdx);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ret = rs.getInt(1);
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
		return ret;
	}
	
	// updateTopicFolder(int, String): 폴더이름 수정하는 기능
	// 파라미터: topic_folder_idx, name
	public void updateTopicFolder(int topicFolderIdx, String name) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "UPDATE folder_box SET name = ?" 
						 + " WHERE topic_folder_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, topicFolderIdx);
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
	
	// deleteTopicFolder(int): 폴더 삭제하는 기능
	// 파라미터: topic_folder_idx
	public void deleteTopicFolder(int topicFolderIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "DELETE FROM folder_box WHERE topic_folder_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicFolderIdx);
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
	
	// addTopicToFolder(int, int): 폴더에 토픽을 추가하는 기능	
	// 파라미터: topic_folder_idx, topic_idx
	public void addTopicToFolder(int topicFolderIdx, int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	    	conn = getConnection();
			String sql = "INSERT INTO topic_folder (topic_folder_idx, topic_idx) " + 
						 " VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicFolderIdx);
			pstmt.setInt(2, topicIdx);
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
	
	
	/* =============================== 토픽 ==================================== */
	
	// createTopic(String, String, int, int, int): 토픽 생성하는 기능(+만든 사람을 토픽 멤버로 추가하고 토픽매니저 설정)	
	// 파라미터: name, info, teamIdx, open(공개여부), memberIdx(사용자)
	// 리턴: topid_idx	
	public int createTopic(String name, String info, int teamIdx, int open, int memberIdx) throws Exception {
	    int topicIdx = 0;  // pk값(topic_idx)
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	    	conn = getConnection();
		    // 토픽 생성 sql
		    String sql1 = "INSERT INTO topic (topic_idx, name, information, team_idx, open, alarm)" 
		                + " VALUES (seq_topic.nextval, ?, ?, ?, ?, 1)";
		    pstmt = conn.prepareStatement(sql1, new String[] {"topic_idx"});
		    pstmt.setString(1, name);
		    pstmt.setString(2, info);
		    pstmt.setInt(3, teamIdx);
		    pstmt.setInt(4, open);
		    pstmt.executeUpdate();
		    rs = pstmt.getGeneratedKeys();
		    if (rs.next()) {
		        topicIdx = rs.getInt(1);
		    }
		    rs.close();
		    pstmt.close();
		    
		    // 사용자를 토픽 매니저로 멤버 추가 sql
		    String sql2 = "INSERT INTO topic_member (topic_idx, member_idx, manager)" 
		                + " VALUES (?, ?, 1)";
		    pstmt = conn.prepareStatement(sql2);
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
	    return topicIdx;
	}
	
	// getTopicInformation(int): 토픽의 정보 조회 기능		
	// 파라미터: topicIdx
	// 리턴: information
	public String getTopicInformation(int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String result = "";
	    try {
	    	conn = getConnection();
			String sql = "SELECT information FROM topic WHERE topic_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicIdx);
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
	
	
	/* =============================== 토픽글 ==================================== */	
	
	// writeTopicBoard(int, int, String, String): 토픽글 작성 기능
	// 파라미터: topic_idx, member_idx(작성자), title(글제목), content(글내용)
	// 리턴: topic_board_idx
	public int writeTopicBoard(int topicIdx, int memberIdx, String title, String content) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    int ret = -1;
	    try {
	    	conn = getConnection();
			String sql = "INSERT INTO topic_board (topic_board_idx, topic_idx, member_idx, title, content, write_date)" 
						+ " VALUES (seq_topic_board.nextval, ?, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(sql, new String[] {"topic_board_idx"});
			pstmt.setInt(1, topicIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ret = rs.getInt(1);
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
		return ret;
	}
	
	// addFileToTopic(int, int) : 토픽글의 파일 추가하는 기능
	// 파라미터: topic_board_idx, file_idx
	public void addFileToTopic(int topicBoardIdx, int fileIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	    	conn = getConnection();
			String sql = "INSERT INTO topic_file (topic_board_idx, file_idx)"
						+ " VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicBoardIdx);
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
	
	// getFileInfoFromTopicBoardIdx(int): 토픽글의 파일 조회 기능
	// 파라미터: topic_board_idx
	// 리턴: topic_board_idx, file_idx, file_name
	public TopicBoardFileDto getFileInfoFromTopicBoardIdx(int topicBoardIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    TopicBoardFileDto dto = null;
	    
	    try {
	    	conn = getConnection();
	    	String sql = "SELECT tf.topic_board_idx, fb.file_idx, fb.file_name " + 
	    			" FROM topic_file tf  " + 
	    			" INNER JOIN file_box fb ON tf.file_idx = fb.file_idx " + 
	    			" WHERE tf.topic_board_idx = ?";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicBoardIdx);
	    	rs = pstmt.executeQuery();
	    	
	    	if(rs.next()) {
	    		dto = new TopicBoardFileDto();
	    		dto.setTopicBoardIdx(rs.getInt("topic_board_idx"));
	    		dto.setFileIdx(rs.getInt("file_idx"));
	    		dto.setFileName(rs.getString("file_name"));
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
	
	// updateTopicBoard(int, String, String): 토픽글 수정하는 기능
	// 파라미터: title, content, topic_board_idx
	public void updateTopicBoard(int topicBoardIdx, String title, String content) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "UPDATE topic_board SET title = ?, content = ?"
						+ " WHERE topic_board_idx = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, topicBoardIdx);
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
	
	// deleteTopicBoard(int): 토픽글 삭제하는 기능(+토픽글에 포함된 댓글도 함께 삭제)
	// 파라미터: topic_board_idx
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
			
			// 파일이 있을 경우
			if (fileIdx > 0) {
				// topic_file 테이블에서 해당 file 삭제
				String sql2 = "DELETE FROM topic_file WHERE file_idx = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, fileIdx);
				pstmt.executeUpdate();
				pstmt.close();
				
				// file_box 테이블에서 해당 file 삭제
				String sql3 = "DELETE FROM file_box WHERE file_idx = ?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, fileIdx);
				pstmt.executeUpdate();
				pstmt.close();
			}		
			
			// 댓글 유무 확인
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
	        
	        // 글에 포함된 모든 댓글 삭제
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
			
	        // 글 삭제
			String sql8 = "DELETE FROM topic_board WHERE topic_board_idx = ?";
			pstmt = conn.prepareStatement(sql8);
			pstmt.setInt(1, topicBoardIdx);
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
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
	}
	
	// deleteAllTopicBoard(int): 토픽에 포함된 모든 토픽글 삭제하는 기능
	// 파라미터: topicIdx
	public void deleteAllTopicBoard(int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
		try {
			conn = getConnection();
			// topic_idx에 해당하는 모든 topic_board의 topic_board_idx를 SELECT
	        String sql = "SELECT topic_board_idx FROM topic_board WHERE topic_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, topicIdx);
	        rs = pstmt.executeQuery();

	        // SELECT된 결과 처리 (topic_board_idx만 가져옴)
	        while (rs.next()) {
	            int topicBoardIdx = rs.getInt("topic_board_idx");
	            // 각 topic_board_idx에 대해 deleteTopicBoard 메서드 호출
	            deleteTopicBoard(topicBoardIdx);
	        }    
		} catch(Exception e) {
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
	}
	
	// getTopicBoardList(int, int, int): 토픽글 리스트 조회하는 기능	
	// 파라미터: team_idx, topic_idx, member_idx(사용자)
	// 리턴: 토픽글 리스트(topic_board_idx, topic_idx, member_idx(작성자), profile_pic_url, name, state, title, content, write_date, unread)
	public ArrayList<TopicBoardDto> getTopicBoardList(int teamIdx, int paratopicIdx, int paramemberIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ArrayList<TopicBoardDto> listRet = new ArrayList<TopicBoardDto>();
		
	    try {
	    	conn = getConnection();
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
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamIdx);
			pstmt.setInt(2, teamIdx);
			pstmt.setInt(3, paratopicIdx);
			pstmt.setInt(4, paramemberIdx);
			rs = pstmt.executeQuery();
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
		} catch(Exception e) {
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
	
	// addUnreadMember(int, int[]): 토픽글을 읽지 않은 멤버 데이터 추가하는 기능		
	// 파라미터: topic_board_idx, member_idx(사용자 제외 멤버들)
	public void addUnreadMember(int topicBoardIdx, int[] memberIdxArray) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
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

	// getTopicMembersExceptAuthor(int, int): 작성자를 제외하고 글을 읽지 않은 멤버 조회하는 기능
	// 파라미터: topic_board_idx, member_idx 
	// 리턴: 작성자 제외 안 읽은 멤버idx 리스트
	public List<Integer> getTopicMembersExceptAuthor(int topicBoardIdx, int authorIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<Integer> memberIdxList = new ArrayList<>();

	    try {
	        String sql = "SELECT member_idx FROM topic_member WHERE topic_board_idx = ? AND member_idx != ?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, topicBoardIdx);
	        pstmt.setInt(2, authorIdx);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            memberIdxList.add(rs.getInt("member_idx"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }

	    return memberIdxList;
	}

	
	/* =============================== 토픽댓글 ==================================== */
	
	// getTopicCommentCnt(int): 토픽글에 포함된 댓글수 조회하는 기능
	// 파라미터: topic_board_idx
	// 리턴: 댓글수
	public int getTopicCommentCnt(int topicBoardIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = 0;

	    try {
	 		String sql = "SELECT count(*) FROM topic_comment WHERE topic_board_idx = ?";
	 		conn = getConnection();
	 		pstmt = conn.prepareStatement(sql);
	 		pstmt.setInt(1, topicBoardIdx);
	 		rs = pstmt.executeQuery();
	 		if(rs.next()) {
	 			result = rs.getInt(1);
	 		}
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		
		return result;
	}
	
	// getTopicCommentList(int, int): 토픽글의 토픽댓글 리스트 조회	
	// 파라미터: topic_board_idx, team_idx
	// 리턴: 댓글리스트(topic_comment_idx, topic_board_idx, member_idx, profile_pic_url, name, state, comments, write_date)
	public ArrayList<TopicCommentDto> getTopicCommentList(int topicBoardIdx, int teamIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ArrayList<TopicCommentDto> listRet = new ArrayList<TopicCommentDto>();
	    
	    try {
	    	conn = getConnection();
			String sql = "SELECT tc.topic_comment_idx, tc.topic_board_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url," + 
						"        m.member_idx, m.name, tm.state, tc.comments," + 
						"        TO_CHAR(tc.write_date, 'YYYY-MM-DD PM HH:MI') write_date, tc.file_idx" + 
						" FROM topic_comment tc INNER JOIN member m on tc.member_idx = m.member_idx" + 
						" INNER JOIN team_member tm on m.member_idx = tm.member_idx" + 
						" INNER JOIN team t on t.team_idx = tm.team_idx" + 
						" WHERE tc.topic_board_idx = ?" + 
						" AND t.team_idx = ?" + 
						" ORDER BY tc.write_date";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicBoardIdx);
			pstmt.setInt(2, teamIdx);
			rs = pstmt.executeQuery();
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
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}

	// writeTopicComment(int, int, String, Integer): 댓글 작성하는 기능	
	// 파라미터: topic_board_idx, member_idx(작성자), comments, file_idx
	// 리턴: topic_comment_idx(생성된 댓글의 idx)
	public int writeTopicComment(int topicBoardIdx, int memberIdx, String comments, Integer fileIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    int ret = 0;
	    try {
	    	conn = getConnection();
			String sql = "INSERT INTO topic_comment(topic_comment_idx, topic_board_idx, member_idx, comments, write_date, file_idx) " + 
						 " VALUES (seq_topic_comment.nextval, ?, ?, ?, sysdate, ?)";
			pstmt = conn.prepareStatement(sql, new String[] {"topic_comment_idx"});
			pstmt.setInt(1, topicBoardIdx);
			pstmt.setInt(2, memberIdx);
			pstmt.setString(3, comments);
			if(fileIdx==null){
				pstmt.setNull(4, Types.INTEGER);	
			} else {
				pstmt.setInt(4,  fileIdx);
			}
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ret = rs.getInt(1);
			}	
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		
		return ret;
	}
		
	// updateTopicComment(int, String, Integer): 토픽댓글 수정하는 기능
	// 파라미터: topic_comment_idx, comments, file_idx
	public void updateTopicComment(int topicCommemtIdx, String comments, Integer fileIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	conn = getConnection();
	 		String sql = "UPDATE topic_comment"
		 				 + " SET comments = ?, file_idx = ?"
		 				 + " WHERE topic_comment_idx = ?";
	 		pstmt = conn.prepareStatement(sql);
	 		pstmt.setString(1, comments);
	 		if(fileIdx==null){
	 			pstmt.setNull(2, Types.INTEGER);	
	 		} else {
	 			pstmt.setInt(2,  fileIdx);
	 		}
	 		pstmt.setInt(3, topicCommemtIdx);
	 		pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		
	}	
	
	// updateTopicCommentFile(int, Integer): 토픽댓글의 파일만 수정하는 기능
	// 파라미터: topic_comment_idx, file_idx
	public void updateTopicCommentFile(int topicCommemtIdx, Integer fileIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	conn = getConnection();
	    	String sql = "UPDATE topic_comment SET file_idx = ?"
	    			+ " WHERE topic_comment_idx = ?";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicCommemtIdx);
	    	pstmt.setInt(2, fileIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }

	}	
	
	// deleteTopicComment(int): 특정 댓글 삭제하는 기능
	// 파라미터: topic_comment_idx
	public void deleteTopicComment(int topicCommentIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	conn = getConnection();
			String sql =  "DELETE FROM topic_comment WHERE topic_comment_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicCommentIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}
	
	// deleteAllTopicComment(int): 토픽글 내에 전체 댓글 삭제하는 기능
	// 파라미터: topic_board_idx
	public void deleteAllTopicComment(int topicBoardIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	conn = getConnection();
			String sql =  "DELETE FROM topic_commen WHERE topic_board_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicBoardIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}
	
	
	/* =============================== 상단바 메뉴 ==================================== */
	
	//  getTopicMemberList(int, int): 토픽방에 참여하는 멤버 전체 조회하는 기능	
	//	파라미터: team_idx, topic_idx
	//	리턴: 토픽 참여멤버 리스트(topic_idx, member_idx, profile_pic_url, name, department, position, power, manager)
	public ArrayList<TopicMemberDto> getTopicMemberList(int teamIdx, int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    ArrayList<TopicMemberDto> listRet = new ArrayList<TopicMemberDto>();
	    try {
	 		String sql = "SELECT t.topic_idx, tpm.member_idx, NVL(m.profile_pic_url, 'https://jandi-box.com/assets/ic-profile.png') profile_pic_url,"
	 				 + "        m.name, tm.department, tm.position, tm.power, tpm.manager"
	 				 + " FROM team_member tm INNER JOIN member m on tm.member_idx = m.member_idx" 
	 				 + " INNER JOIN topic_member tpm on m.member_idx = tpm.member_idx"
	 				 + " INNER JOIN topic t on tpm.topic_idx = t.topic_idx"
	 				 + " WHERE tm.team_idx = ?"
	 				 + " AND t.topic_idx = ?" 
	 				 + " ORDER BY tpm.manager desc, m.name";
	 		conn = getConnection();
	 		pstmt = conn.prepareStatement(sql);
	 		pstmt.setInt(1, teamIdx);
	 		pstmt.setInt(2, topicIdx);
	 		rs = pstmt.executeQuery();
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
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	    
		return listRet;
	}
	
	// removeMemberInThisTopic(int, int): 토픽방에서 멤버 내보내기 기능		
	// 파라미터: topic_idx, member_idx	
	public void removeMemberInThisTopic(int topicIdx, int memberIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	String sql = "DELETE FROM topic_member WHERE topic_idx = ?"
	    				+ " AND member_idx = ?";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	pstmt.setInt(2, memberIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}

	// inviteOtherMembersToThisTopic(int, int[]): 토픽방으로 멤버 초대하는 기능
	// 파라미터: topic_idx, member_idx(1명 이상의 팀 멤버)	
	public void inviteOtherMembersToThisTopic(int topicIdx, int[] memberIdxArray) throws Exception {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        String sql = "INSERT INTO topic_member(topic_idx, member_idx, manager) VALUES(?, ?, 0)";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, topicIdx);
	        for (int memberIdx : memberIdxArray) {
	            pstmt.setInt(2, memberIdx);
	            pstmt.addBatch();
	        }
	        pstmt.executeBatch();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
	    }
	}
	
	
	// getTeamMemberListOutOfTopic(int, int): 해당 토픽에 소속되지 않은 팀 멤버 전체 조회 기능	
	// 파라미터 : team_idx, topic_idx
	// 리턴 : 팀 멤버 리스트(team_idx, member_idx, member_idx, profile_pic_url, name, department, position, power)
	public ArrayList<TeamMemberDto> getTeamMemberListOutOfTopic(int teamIdx, int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ArrayList<TeamMemberDto> listRet = new ArrayList<TeamMemberDto>();
	    
	    try {
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
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicIdx);
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
            if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		
		return listRet;
	}
	
	
	
	// registerTopicNoti(int, int, String): 토픽 공지 등록하는 기능
	// 파라미터: topic_idx, member_idx, content
	public void registerTopicNoti(int topicIdx, int memberIdx, String content) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	String sql = "INSERT INTO notification_topic (topic_noti_idx, topic_idx, member_idx, content, write_date)" + 
	    			" VALUES (seq_topic_noti.nextVal, ?, ?, ?, sysdate)";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	pstmt.setInt(2, memberIdx);
	    	pstmt.setString(3, content);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}
	
	// updateTopicNoti(int, String): 토픽 공지 수정하는 기능
	// 파라미터: topic_noti_idx, content	
	public void updateTopicNoti(int topicNotiIdx, String content) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	String sql = "UPDATE notification_topic" + 
	    			" SET content = ?" + 
	    			" WHERE topic_noti_idx = ?";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setString(1, content);
	    	pstmt.setInt(2, topicNotiIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		
	}

	// deleteTopicNoti(int): 토픽 공지 삭제하는 기능	
	// 파라미터: topic_noti_idx
	public void deleteTopicNoti(int topicNotiIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
			String sql = "DELETE FROM notification_topic WHERE topic_noti_idx = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicNotiIdx);
			pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    } 
	}	

	// deleteTopicNoti(int): 토픽 삭제 시 토픽 공지 삭제
	// 파라미터: topic_idx
	public void deleteNotiOfThisTopic(int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	String sql = "DELETE FROM notification_topic" + 
	    			" WHERE topic_idx = ?";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}	
	
	// updateTopic(int, String, String): 토픽 정보 변경하는 기능
	// 파라미터: topic_idx, name, information
	public void updateTopic(int topicIdx, String name, String information) throws Exception {
		Connection conn = getConnection();
		
		String sql = "UPDATE topic SET name = ?, information = ?"
					 + " WHERE topic_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setString(2, information);
		pstmt.setInt(3, topicIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	// deleteTopic(int): 토픽 삭제하는 기능(토픽, 토픽글, 토픽댓글, 토픽참여멤버)
	// 파라미터: topic_idx
	public void deleteTopic(int topicIdx) throws Exception {
		Connection conn = null;
        
        try {
        	conn = getConnection();
        	// 트랜잭션 시작 (commit()이 호출되기 전까지는 실행된 SQL 명령이 실제로 커밋되지 않도록)
        	conn.setAutoCommit(false);
        	
        	// 1. 해당 topic의 모든 topic_board_idx 조회
            String selectTopicBoardsSql = "SELECT topic_board_idx FROM topic_board WHERE topic_idx = ?";
            List<Integer> AlltopicBoardIdx = new ArrayList<>();

            try (PreparedStatement selectTopicBoardspstmt = conn.prepareStatement(selectTopicBoardsSql)) {
                selectTopicBoardspstmt.setInt(1, topicIdx);
                try (ResultSet rs = selectTopicBoardspstmt.executeQuery()) {
                    while (rs.next()) {
                    	AlltopicBoardIdx.add(rs.getInt("topic_board_idx"));
                    }
                }
            }

            // 2. 조회한 토픽글 ID를 기반으로 해당 토픽글에 포함된 모든 토픽댓글 삭제
            if (!AlltopicBoardIdx.isEmpty()) {
                String deleteTopicCommentsSql = "DELETE FROM topic_comment WHERE topic_board_idx = ?";
                try (PreparedStatement deleteTopicCommentspstmt = conn.prepareStatement(deleteTopicCommentsSql)) {
                    for (int topicBoardIdx : AlltopicBoardIdx) {
                        deleteTopicCommentspstmt.setInt(1, topicBoardIdx);
                        deleteTopicCommentspstmt.executeUpdate();
                    }
                }

                // 3. 해당 토픽글들 삭제
                String deleteTopicBoardsSql = "DELETE FROM topic_board WHERE topic_idx = ?";
                try (PreparedStatement deleteTopicBoardspstmt = conn.prepareStatement(deleteTopicBoardsSql)) {
                    deleteTopicBoardspstmt.setInt(1, topicIdx);
                    deleteTopicBoardspstmt.executeUpdate();
                }
            }
            
            // 4. 해당 토픽의 모든 topic_member 데이터 삭제
            String deleteTopicMembersSql = "DELETE FROM topic_member WHERE topic_idx = ?";
            try (PreparedStatement deleteTopicMemberspstmt = conn.prepareStatement(deleteTopicMembersSql)) {
                deleteTopicMemberspstmt.setInt(1, topicIdx);
                deleteTopicMemberspstmt.executeUpdate();
            }
            
            // 5. 해당 토픽 삭제
            String deleteTopicSql = "DELETE FROM topic WHERE topic_idx = ?";
            try (PreparedStatement deleteTopicpstmt = conn.prepareStatement(deleteTopicSql)) {
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
            // 자동 커밋 모드로 되돌림 및 Connection 닫기
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	// leaveTopic(int, int): 토픽 나가기 기능(사용자 본인이 토픽방을 나가는 기능)		
	// 파라미터: topicIdx, memberIdx
	public void leaveTopic(int topicIdx, int memberIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	String sql = "DELETE FROM topic_member WHERE topic_idx = ?"
	    			+ " AND member_idx = ? ";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	pstmt.setInt(2, memberIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}
	
	// designateTopicManager(int, int): 토픽관리자 지정하는 기능
	// 파라미터: topic_idx, member_idx
	public void designateTopicManager(int topicIdx, int memberIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	String sql = "UPDATE topic_member SET manager = 1"
	    				+ " WHERE topic_idx = ?"
	    				+ " AND member_idx = ?";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	pstmt.setInt(2, memberIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}
	
	// revokeTopicManager(int, int): 토픽관리자 해제하는 기능
	// 파라미터: topic_idx, member_idx
	public void revokeTopicManager(int topicIdx, int memberIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	    	conn = getConnection();
	    	String sql = "UPDATE topic_member SET manager = 0" 
	    				+ " WHERE topic_idx = ?" 
	    				+ " AND member_idx = ?";
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	pstmt.setInt(2, memberIdx);
	    	pstmt.executeUpdate();
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
	}	
	
	
	//	============================== 기타  ==============================
	// getTopicNameFromTopicIdx(int): 토픽방이름 불러오는 메서드
	// 파라미터: topic_idx
	// 리턴: name
	public String getTopicNameFromTopicIdx(int topicIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    String topicName = null;
	    try {
	    	String sql = "SELECT name FROM topic WHERE topic_idx=?";
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, topicIdx);
	    	rs = pstmt.executeQuery();
	    	if(rs.next()) {
	    		topicName = rs.getString("name");
	    	}
		} catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	if (rs != null) {rs.close();}
            if (pstmt != null) {pstmt.close();}
            if (conn != null) {conn.close();}
	    }
		
		return topicName;
	}
	
}
