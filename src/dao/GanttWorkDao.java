package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.GanttWorkCommentDto;
import dto.GanttWorkDto;
import dto.GanttSubworkDto;
import dto.GanttWorkInformationDto;
import dto.GanttWorkLikeDto;
import dto.GanttWorkReadMemberDto;
import dto.GanttWorkWriterDto;
import dto.GanttkCommentLikeDto;
import dto.QnaDto;

public class GanttWorkDao {
	
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
// ----------------------간트 삭제(업무)DELETE-----------------------------
	
	// deleteGanttWorkMember : 간트 업무 멤버 삭제
	// workIdx : 업무idx , memberIdx : 업무 담당 멤버idx
	public void deleteGanttWorkMember(int workIdx, int memberIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM work_member WHERE work_idx = ? AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
  
		pstmt.close();
		conn.close();
	}
	
	// deleteGanttWork : 간트 업무 삭제  
	// workIdx : 업무idx
	public void deleteGanttWork(int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM gantt_work WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
      
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
	}
	
	// deleteGanttWorkcomment : 간트 업무 댓글 삭제  
	// workCommentIdx : 업무댓글idx
	public void deleteGanttWorkcomment(int workCommentIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM work_comment WHERE work_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workCommentIdx);
      
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
	}
	
	// deleteGanttWorkCommentLike : 간트 업무 댓글 좋아요 삭제   
	// workCommentIdx :업무댓글idx , commentLikeMemberIdx : 업무 댓글 좋아요 누른 멤버idx
	public void deleteGanttWorkCommentLike(int workCommentIdx, int commentLikeMemberIdx) throws Exception { 
			Connection conn = getConnection();
		    String sql = "DELETE FROM comment_like WHERE work_comment_idx = ? AND comment_like_member_idx = ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, workCommentIdx);
		    pstmt.setInt(2, commentLikeMemberIdx);
		    pstmt.executeUpdate();
		      
		    pstmt.close();
		    conn.close();
	}
	
	// deleteGanttWorkLike : 간트업무 좋아요 삭제   
	// memberIdx : 업무 좋아요 누른 멤버idx , workIdx :업무 idx
	public void deleteGanttWorkLike(int memberIdx, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM work_like WHERE member_idx = ? AND work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, memberIdx);
	    pstmt.setInt(2, workIdx);
	    pstmt.executeUpdate();
	      
	    pstmt.close();
	    conn.close();
	}

// ----------------------간트 변경(업무)UPDATE-----------------------------
	
	// updateGanttWorkStartDate : 간트 업무 시작일 변경
	// startDate : 업무 시작일 , workIdx : 업무 idx
	public void updateGanttWorkStartDate(String startDate, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_work" + 
               " SET start_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi')" + 
               " WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, startDate);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   }  
	// updateGanttWorkFinishDate : 간트 업무 마감일 변경
	// finishDate : 마감일 , workIdx : 업무 idx
	public void updateGanttWorkFinishDate(String finishDate, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_work" + 
               " SET finish_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi') " + 
               " WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, finishDate);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   } 
	// updateGanttWorkName : 간트 업무명 변경 
	// workName : 업무명 , workIdx : 업무 idx
	public void updateGanttWorkName(String workName, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_work" + 
               " SET work_name = ?" + 
               " WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, workName);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   }  
	// updateGanttWorkState : 간트 업무상태 변경
	// workState : 업무상태 , workIdx : 업무  idx
	public void updateGanttWorkState(int workState, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_work" + 
               " SET work_state = ?" + 
               " WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workState);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   }  
	
	// updateGanttWorkPriority : 간트 업무 우선순위 변경
	// priority : 업무 우선순위 , workIdx : 업무idx
	public void updateGanttWorkPriority(int priority, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_work" + 
               " SET priority = ?" + 
               " WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, priority);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   }  
	
	// updateGanttWorkCommentLike : 간트 업무 댓글 좋아요 변경
	// commentLike : 좋아요변경, workCommentIdx : 업무 댓글  idx
	public void updateGanttWorkCommentLike(int commentLike, int workCommentIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE comment_like" + 
               " SET comment_like = ?" + 
               " WHERE work_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, commentLike);
		pstmt.setInt(2, workCommentIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   }  
	
	
	// updateGanttWorkGroup : 업무에서 그룹 추가, 변경하기
	// groupIdx : 추가할 그룹idx , groupIdx : 업무idx
	public void updateGanttWorkGroup(int groupIdx, int workIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_work" + 
               " SET group_idx = ?" + 
               " WHERE work_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, groupIdx);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   }  
	
	
// ----------------------간트 추가(업무)INSERT-----------------------------	
	
	
	

	// addGanttWork : 간트 업무 추가
	// startDate와 finishDate : (ex) "2024-06-26 16:40"   , 시간 : TO_DATE(?, 'yyyy-mm-dd hh24:mi')  , START_DATE, FINISH_DATE(update문으로)
	public void addGanttWork(int groupIdx, String workName, int workState,  int priority, String startDate, String finishDate, int writer) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO GANTT_WORK(WORK_IDX, GROUP_IDX, WORK_NAME, WORK_STATE, PRIORITY, REGISTRATION_DATE, START_DATE, FINISH_DATE, WRITER)" 
				+ " VALUES(seq_gantt_work.nextval, ?, ?, ?, ?, sysdate, null, null, ?)";
		String[] arr1 = {"work_idx"};   
		
		PreparedStatement pstmt = conn.prepareStatement(sql, arr1);
		pstmt.setInt(1, groupIdx); 
		pstmt.setString(2, workName); 
		pstmt.setInt(3, workState); 
		pstmt.setInt(4, priority); 
		pstmt.setInt(5, writer); 
		pstmt.executeUpdate();

		ResultSet rs = pstmt.getGeneratedKeys();
		int workIdx = 0;   // 0 은 초기 값일 뿐, 의미 없음. 
		if(rs.next()) {
			workIdx = rs.getInt(1);
		}
		   
		pstmt.close();
		conn.close();
		
		if(startDate != null)
			updateGanttWorkStartDate(workIdx, startDate);
		if(finishDate != null)
			updateGanttWorkFinishDate(workIdx, finishDate);
	}
	
	// updateGanttWorkStartDate(int, String) : 간트차트 업무 시작일시 변경.
	public void updateGanttWorkStartDate(int workIdx, String startDate) throws Exception {
		String sql = "UPDATE gantt_work SET start_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi') WHERE work_idx = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, startDate);
		pstmt.setInt(2, workIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
	    conn.close();
	}
	// updateGanttWorkStartDate(int, String) : 간트차트 업무 시작일시 변경.
	public void updateGanttWorkFinishDate(int workIdx, String finishDate) throws Exception {
		String sql = "UPDATE gantt_work SET finish_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi') WHERE work_idx = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, finishDate);
		pstmt.setInt(2, workIdx);																																																																																																																																																																																					
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
	
	
	// addGanttWorkMember : 프로젝트 업무 담당자 추가
	// memberIdx: 멤버idx, work_idx: 업무 idx, work_scope: 업무구분(전체, 내업무)
	public void addGanttWorkMember(int memberIdx, int workIdx, int workScope) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO work_member(member_idx, work_idx, work_scope) VALUES(?, ?, ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, workIdx);  
		pstmt.setInt(3, workScope);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
		   
		   
	}
	
	// addGanttWorkComment : 간트 업무 댓글 추가
	// workCommentIdx : 업무 댓글 idx, workIdx : 업무 idx, memberIdx : 멤버idx, workComment: 업무댓글
	public void addGanttWorkComment(int workCommentIdx, int workIdx, int memberIdx, String workComment) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO work_comment(work_comment_idx, work_idx, member_idx, work_comment, comment_date) VALUES(?, ?, ?, ? , sysdate)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workCommentIdx);
		pstmt.setInt(2, workIdx);  
		pstmt.setInt(3, memberIdx);  
		pstmt.setString(4, workComment);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
		   
		   
	}
	
	// addGanttWorkLike : 간트업무좋아요
	// workIdx : 업무idx, subworkIdx: 하위업무 idx, memberIdx: 좋아요 누른 멤버 idx, titleLike: 
	public void addGanttWorkLike(int workIdx, int memberIdx, int titleLike) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO work_like(work_idx, member_idx, title_like) VALUES(?, ?, ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		pstmt.setInt(2, memberIdx);  
		pstmt.setInt(3, titleLike);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
		   
		   
	}
	
//-------------------------SELECT------------------------------------------------
	// checkGanttWorkCountByGroupIdx : 간트 업무 개수(그룹별)
	// input: group_idx
	public int checkGanttWorkCountByGroupIdx(int groupIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM gantt_work gw" 
				+ " WHERE gw.group_idx =?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, groupIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	// getGanttWorkName : 간트 업무명 나열(프로젝트별)
	// input: project_idx (프로젝트idx)
	// ouput: work_name (업무명)
	 public ArrayList<String> getGanttWorkName(int projectIdx) throws Exception{
			Connection conn = getConnection();
			ArrayList<String> WorkName = new ArrayList<String>();
			
			String sql = "SELECT distinct gw.work_name " 
						+ " FROM  gantt_work gw, gantt_group gg" 
						+ " WHERE gg.project_idx = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, projectIdx);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				WorkName.add(rs.getString(1));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return WorkName;
	}	
	 
	 // getGanttWorkNameByGroupIdx : 간트 업무명 출력(그룹별)
	 // input: group_idx (그룹idx)
	 // ouput: work_name (업무명)
	 public ArrayList<String> getGanttWorkNameByGroupIdx(int groupIdx) throws Exception{
			Connection conn = getConnection();
			ArrayList<String> WorkName = new ArrayList<String>();
			
			String sql = "SELECT gw.work_name " 
						+ " FROM  gantt_work gw" 
						+ " WHERE gw.group_idx = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupIdx);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				WorkName.add(rs.getString(1));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return WorkName;
	}	
	   
	 // checkGanttWorkCommentLikeCount : 간트업무댓글좋아요개수 
	 // input: work_comment_idx
	 public int checkGanttWorkCommentLikeCount(int workCommentIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM comment_like" 
				+ " WHERE work_comment_idx =?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workCommentIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	 }	 
	 
	 // checkGanttWorkCommentCount : 간트 업무 댓글 수
	 // input : work_idx
	 public int checkGanttWorkCommentCount(int workCommentIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM work_comment" 
				+ " WHERE work_idx =?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workCommentIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	 
	 // checkGanttWorkReadMember : 간트 업무 읽은 멤버 수
	 // input: work_idx
	 public int checkGanttWorkReadMember(int workIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM gantt_work_read" 
				+ " WHERE work_idx =?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	 
	 
	 
	 
	 // getGanttWorkLikeMember : 간트 업무댓글 좋아요 누른 멤버
	 // input : work_comment_idx (업무댓글idx)
	 // ouput : 멤버 프로필 url , 멤버이름
	 public ArrayList<GanttkCommentLikeDto> getGanttWorkCommentLikeMember(int workCommentIdx) throws Exception {
		 	ArrayList<GanttkCommentLikeDto> listRet = new ArrayList<GanttkCommentLikeDto>();
		 	String sql = "SELECT m.profile_pic_url , m.name" 
		 			+ " FROM comment_like cl INNER JOIN member m ON m.member_idx = cl.comment_like_member_idx" 
		 			+ " WHERE  cl.work_comment_idx =?"; 
		 	Connection conn = getConnection();
  
		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, workCommentIdx);
		 	ResultSet rs = pstmt.executeQuery();
  
		 	while(rs.next()) {
		 		String profilePicUrl = rs.getString("profile_pic_url");
		 		String name = rs.getString("name");
		 	
			 	GanttkCommentLikeDto dto = new GanttkCommentLikeDto(profilePicUrl, name);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	return listRet;
	 }
	 
	 // getGanttWorkComment : 간트 지정 업무 댓글 출력
	 // input : work_idx(업무idx)
	 // ouput : 프로필사진, 작성자이름, 작성 날짜, 댓글
	 public ArrayList<GanttWorkCommentDto> getGanttWorkComment(int workIdx) throws Exception {
		 	ArrayList<GanttWorkCommentDto> listRet = new ArrayList<GanttWorkCommentDto>();
		 	String sql = "SELECT m.profile_pic_url, m.name , wc.comment_date , wc.work_comment"
		 			+ " FROM work_comment wc INNER JOIN member m ON wc.member_idx = m.member_idx" 
		 			+ " WHERE wc.work_idx  =?"; 
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, workIdx);
		 	ResultSet rs = pstmt.executeQuery();

		 	while(rs.next()) {
		 		String profilePicUrl = rs.getString("profile_pic_url");
		 		String name = rs.getString("name");
		 		String commentDate = rs.getString("comment_date");
		 		String workComment = rs.getString("work_comment");
		 	
		 		GanttWorkCommentDto dto = new GanttWorkCommentDto(profilePicUrl, name, commentDate, workComment, workIdx);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	return listRet;
	 }
	   
	 
	 // getGanttWorkLike : 업무에 대한 좋아요
	 // input: work_idx(업무idx)
	 // output: 좋아요번호, 멤버이름
	 public ArrayList<GanttWorkLikeDto> getGanttWorkLike(int workIdx) throws Exception{
			ArrayList<GanttWorkLikeDto> listRet = new ArrayList<GanttWorkLikeDto>();
			
			String sql = "SELECT wl.title_like ,m.name" 
						+ " FROM work_like wl INNER JOIN member m ON wl.member_idx = m.member_idx" 
						+ " WHERE wl.work_idx = ?";

			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, workIdx);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
		 		int title_like = rs.getInt("title_like");
		 		String name = rs.getString("name");
		 	
		 		GanttWorkLikeDto dto = new GanttWorkLikeDto(title_like, name);
			 	listRet.add(dto);
		 	}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return listRet;
	}
	 
	 // getGanttWorkWriter : 간트 업무 작성자 [프로필사진, 이름, 작성일자]
	 // input: work_idx (업무idx)
	 // output : 프로필사진, 이름, 작성일자
	 public ArrayList<GanttWorkWriterDto> getGanttWorkWriter(int workIdx) throws Exception{
			ArrayList<GanttWorkWriterDto> listRet = new ArrayList<GanttWorkWriterDto>();
			
			String sql = "SELECT m.profile_pic_url, m.name, gw.registration_date" 
						+ " FROM member m INNER JOIN work_member wm ON m.member_idx = wm.member_idx INNER JOIN gantt_work gw ON wm.work_idx = gw.work_idx" 
						+ " WHERE gw.work_idx = ?";

			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, workIdx);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
		 		String profile_pic_url = rs.getString("profile_pic_url");
		 		String registration_date = rs.getString("registration_date");
		 		String name = rs.getString("name");
		 	
		 		GanttWorkWriterDto dto = new GanttWorkWriterDto(profile_pic_url, name, registration_date);
			 	listRet.add(dto);
		 	}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return listRet;
	}
	 
	 // getGanttWorkInformation : 지정업무 자세히보기 [업무이름, 업무상태, 작성자이름, 시작일, 마감일]
	 // input : work_idx
	 // output : 업무명, 업무상태, 작성자이름, 시작일, 마감일
	 public ArrayList<GanttWorkInformationDto> getGanttWorkInformation(int groupIdx) throws Exception{
		ArrayList<GanttWorkInformationDto> listRet = new ArrayList<GanttWorkInformationDto>();
		
		String sql = "SELECT gw.work_idx, gw.work_name, m.name, gw.work_state, gw.start_date, gw.finish_date, (gw.finish_date-gw.start_date+1) duration" 
					+ " FROM gantt_work gw"
					+ " LEFT JOIN work_member wm ON gw.work_idx = wm.work_idx"
					+ " LEFT JOIN member m ON wm.member_idx = m.member_idx"
					+ " WHERE gw.group_idx = ?";

		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, groupIdx);

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int workIdx = rs.getInt("work_idx");
	 		String workName = rs.getString("work_name");
//	 		String workState = rs.getString("work_state");
	 		int ws = rs.getInt("work_state");
	 		String workState = "";
	 		switch(ws) {
	 		case 1: workState = "요청"; break;
	 		case 2: workState = "진행"; break;
	 		case 3: workState = "피드백"; break;
	 		case 4: workState = "완료"; break;
	 		case 5: workState = "보류"; break;
	 		}
	 		String name = rs.getString("name");
	 		String startDate = rs.getString("start_date");
	 		int duration = rs.getInt("duration");
	 		// 형식 변경 28-05-2024
	 		if(startDate != null) {
	 			startDate = startDate.split(" ")[0];   // "2024-07-16"
	 			String year = startDate.substring(0,4);
	 			String month = startDate.substring(5,7);
	 			String date = startDate.substring(8);
	 			startDate = date + "-" + month + "-" + year;
	 			System.out.println(startDate);
	 		}
	 		String finishDate = rs.getString("finish_date");
	 		// 형식 변경 28-05-2024
	 		if(finishDate != null) {
	 			finishDate = finishDate.split(" ")[0];   // "2024-07-16"
	 			finishDate = finishDate.split(" ")[0];   // "2024-07-16"
	 			String year = finishDate.substring(0,4);
	 			String month = finishDate.substring(5,7);
	 			String date = finishDate.substring(8);
	 			finishDate = date + "-" + month + "-" + year;
	 			System.out.println(finishDate);
	 		}
	 		GanttWorkInformationDto dto = new GanttWorkInformationDto(workIdx, workName, workState, name, startDate, finishDate, duration);
		 	listRet.add(dto);
	 	}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	 
	 // getGanttWorkReadMember : 업무 읽은 멤버 조회
	 // input : work_idx (업무idx)
	 // output : 프사, 멤버명, 팀명, 읽음일시
	 public ArrayList<GanttWorkReadMemberDto> getGanttWorkReadMember(int workIdx) throws Exception {
		 	ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
		 	String sql = "SELECT m.profile_pic_url, m.name AS member_name, t.name AS team_name, wr.readdate"
		 			+ " FROM  gantt_work_read wr INNER JOIN member m ON wr.member_idx = m.member_idx INNER JOIN team_member tm ON tm.member_idx = m.member_idx INNER JOIN team t ON tm.team_idx = t.team_idx" 
		 			+ " WHERE  wr.work_idx =?"; 
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, workIdx);
		 	ResultSet rs = pstmt.executeQuery();

		 	while(rs.next()) {
		 		String profile_pic_url = rs.getString("profile_pic_url");
		 		String member_name = rs.getString("member_name");
		 		String team_name = rs.getString("team_name");
		 		String read_date = rs.getString("readdate");
		 	
		 		GanttWorkReadMemberDto dto = new GanttWorkReadMemberDto(profile_pic_url, member_name, team_name, read_date);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	
		 	return listRet;
	 }
	 
	 // getGanttWorkUnreadMember : 업무 미확인 멤버 조회
	 // input : work_idx (업무 idx)
	 // output : 멤버명, 팀명
	 public ArrayList<GanttWorkReadMemberDto> getGanttWorkUnreadMember(int workIdx) throws Exception {
		 	ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
		 	String sql = "SELECT m.name AS member_name, t.name AS team_name"
		 			+ " FROM  member m INNER JOIN gantt_work_unread wu ON wu.member_idx = m.member_idx INNER JOIN team_member tm ON tm.member_idx = m.member_idx INNER JOIN team t ON tm.team_idx = t.team_idx" 
		 			+ " WHERE  wu.work_idx =?"; 
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, workIdx);
		 	ResultSet rs = pstmt.executeQuery();

		 	while(rs.next()) {
		 		String member_name = rs.getString("member_name");
		 		String team_name = rs.getString("team_name");
		 	
		 		GanttWorkReadMemberDto dto = new GanttWorkReadMemberDto(null, member_name, team_name, null);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	
		 	return listRet;
	 }
	   
	 
	 // getSearchWorkReadMember : 업무 읽은 멤버 조회(검색)
	 // input : work_idx (업무idx), m.name(멤버이름)
	 // output : 프사, 멤버명, 팀명, 읽음일시
	public ArrayList<GanttWorkReadMemberDto> getSearchWorkReadMember(int workIdx, String keyword) throws Exception{ 
		ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
		String sql = "SELECT m.profile_pic_url, m.name AS member_name, t.name AS team_name, wr.readdate"
					+ " FROM  gantt_work_read wr INNER JOIN member m ON wr.member_idx = m.member_idx INNER JOIN team_member tm ON  wr.member_idx = tm.member_idx INNER JOIN team t ON tm.team_idx = t.team_idx" 
					+ " WHERE  wr.work_idx =? AND m.name LIKE ?"; 
		Connection conn = getConnection();
	  
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		pstmt.setString(2, "%" + keyword + "%");
		ResultSet rs = pstmt.executeQuery();
	  
		while(rs.next()) {
			String profile_pic_url = rs.getString("profile_pic_url");
			String member_name = rs.getString("member_name");
			String team_name = rs.getString("team_name");
			String read_date = rs.getString("readdate");
			GanttWorkReadMemberDto dto = new GanttWorkReadMemberDto(profile_pic_url, member_name, team_name, read_date);
			listRet.add(dto);
		}
	      
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	 
	 
	// getSearchWorkUnreadMember : 업무 미확인 멤버 조회(검색)
	// input: work_idx (업무idx), m.name(멤버이름)
	// output: 멤버명, 팀명
	public ArrayList<GanttWorkReadMemberDto> getSearchWorkUnreadMember(int workIdx, String keyword) throws Exception{ 
		ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
		String sql = "SELECT m.name AS member_name , t.name AS team_name "
					+ " FROM  member m INNER JOIN gantt_work_unread wu ON wu.member_idx = m.member_idx INNER JOIN team_member tm ON wu.member_idx = tm.member_idx INNER JOIN team t ON tm.team_idx = t.team_idx" 
					+ " WHERE  wu.work_idx =? AND m.name LIKE ?"; 
		Connection conn = getConnection();
	  
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		pstmt.setString(2, "%" + keyword + "%");
		ResultSet rs = pstmt.executeQuery();
	  
		while(rs.next()) {
			String member_name = rs.getString("member_name");
			String team_name = rs.getString("team_name");
			GanttWorkReadMemberDto dto = new GanttWorkReadMemberDto(null, member_name, team_name, null);
			listRet.add(dto);
		}
	      
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}	 
	 
	 // checkGanttWorkUnreadMember : 업무 미확인 멤버수
	// input : work_idx(업무idx)
	// output : 업무 미확인 멤버수
	 public int checkGanttWorkUnreadMember(int workIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM gantt_work_unread" 
				+ " WHERE work_idx =?"; 
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	 
	 
	 

	 
	 
	public ArrayList<GanttWorkDto> getGanttWork(int groupIdx) throws Exception{ 
		ArrayList<GanttWorkDto> listRet = new ArrayList<GanttWorkDto>();
		String sql = "SELECT gw.work_name, m.name, gw.work_state, gw.start_date, gw.finish_date"
					+ " FROM gantt_work gw LEFT JOIN work_member wm ON gw.work_idx = wm.work_idx LEFT JOIN member m ON wm.member_idx = m.member_idx" 
					+ " WHERE gw.group_idx = ?"; 
		Connection conn = getConnection();
	  
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, groupIdx);
		ResultSet rs = pstmt.executeQuery();
	  
		while(rs.next()) {
			String work_name = rs.getString("work_name");
			String name = rs.getString("name");
			int work_state = rs.getInt("work_state");
			String start_date = rs.getString("start_date");
			String finish_date = rs.getString("finish_date");
			GanttWorkDto dto = new GanttWorkDto(work_name, name, work_state, start_date, finish_date);
			listRet.add(dto);
		}
	      
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	 
	 
	 
	 
	 
	 
	 

	
	
}
