package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.GanttSubworkCommentDto;
import dto.GanttSubworkDetailsProjectWorkNameDto;
import dto.GanttWorkCommentDto;
import dto.GanttSubworkDto;
import dto.GanttWorkInformationDto;
import dto.GanttWorkLikeDto;
import dto.GanttWorkReadMemberDto;
import dto.GanttWorkSubworkDto;
import dto.GanttWorkWriterDto;
import dto.GanttkCommentLikeDto;

public class GanttSubworkDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
	
//----------------------------간트 삭제(하위업무)--------------------
	// deleteGanttSubwork: 간트 하위업무 삭제
	// 파라미터: subwork_idx(하위업무idx)
	public void deleteGanttSubwork(int subworkIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM gantt_subwork WHERE subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
  
		pstmt.executeUpdate();
  
		pstmt.close();
		conn.close();
	}
	
	// deleteGanttSubworkMember: 간트 하위업무 담당자 삭제
	// 파라미터: member_idx(하위업무 담당자), subwork_idx(하위업무idx)
	public void deleteGanttSubworkMember(int memberIdx, int subworkIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM subwork_member WHERE member_idx = ? AND subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, subworkIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
	}
	
	// deleteGanttSubworkLike: 간트 하위업무 좋아요 삭제
	// 파라미터: member_idx(하위업무에 좋아요 누른 멤버idx), subwork_idx(하위업무 idx)
	public void deleteGanttSubworkLike(int memberIdx, int subworkIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM subwork_like WHERE member_idx = ? AND subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, subworkIdx);
		pstmt.executeUpdate();
		      
		pstmt.close();
		conn.close();
	}
	 
	// deleteGanttSubworkcomment: 간트 하위업무 댓글 삭제
	// 파라미터: subwork_comment_idx(하위업무댓글idx)
	public void deleteGanttSubworkcomment(int subworkCommentIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM subwork_comment WHERE subwork_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkCommentIdx);
		      
		pstmt.executeUpdate();
		      
		pstmt.close();
		conn.close();
	}	 
	 
	// deleteGanttSubworkCommentLike: 간트 하위업무 댓글 좋아요 삭제
	// 파라미터: subwork_comment_idx(하위업무댓글idx), comment_like_member_idx(하위업무 댓글에 좋아요 누른 멤버 idx)
	public void deleteGanttSubworkCommentLike(int subworkCommentIdx, int commentLikeMemberIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM comment_like WHERE subwork_comment_idx = ? AND comment_like_member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkCommentIdx);
		pstmt.setInt(2, commentLikeMemberIdx);
		pstmt.executeUpdate();
		      
		pstmt.close();
		conn.close();
	}	
	 
//----------------------------간트 변경(하위업무)--------------------
	 
	// updateGanttSubworkName: 간트 하위업무명 변경
	// 파라미터: subwork_name(하위업무명), subwork_idx
	public void updateGanttSubworkName(String subworkName, int subworkIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "UPDATE gantt_subwork" 
				+ " SET subwork_name = ?" 
				+ " WHERE subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subworkName);
		pstmt.setInt(2, subworkIdx);
		pstmt.executeUpdate();
		  
		pstmt.close();
		conn.close();
	}  
	 
	// updateGanttSubworkState: 간트 하위업무 상태 변경
	// 파라미터: work_state(하위업무상태), subwork_idx
	public void updateGanttSubworkState(int subworkState, int subworkIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_subwork"
				+ " SET work_state = ?"
				+ " WHERE subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkState);
		pstmt.setInt(2, subworkIdx);
		pstmt.executeUpdate();
		  
		pstmt.close();
		conn.close();
	}
	 
	// updateGanttSubworkPriority: 간트 하위업무 우선순위 변경
	// 파라미터: priority(우선순위), subwork_idx
	public void updateGanttSubworkPriority(int priority, int subworkIdx) throws Exception { // 간트 하위업무 우선순위 변경
		Connection conn = getConnection();
		String sql = "UPDATE gantt_subwork"
				+ " SET priority  = ?" 
				+ " WHERE subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, priority);
		pstmt.setInt(2, subworkIdx);
		pstmt.executeUpdate();
		  
		pstmt.close();
		conn.close();
	}
	 
	// updateGanttSubworkStartDate: 간트 하위업무 시작일 변경
	// 파라미터: start_date(시작일), subwork_idx
	public void updateGanttSubworkStartDate(int subworkIdx, String startDate) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_subwork" 
				+ " SET start_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi')" 
				+ " WHERE subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		pstmt.setString(2, startDate);
		pstmt.executeUpdate();
		  
		pstmt.close();
		conn.close();
	}  
	 
	// updateGanttSubworkFinishDate: 간트 하위업무 마감일 변경
	// 파라미터: finish_date(마감일), subwork_idx
	public void updateGanttSubworkFinishDate(int subworkIdx, String finishDate) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE gantt_subwork"
				+ " SET finish_date = TO_DATE(?, 'yyyy-mm-dd hh24:mi')"
				+ " WHERE subwork_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		pstmt.setString(2, finishDate);
		pstmt.executeUpdate();
		  
		pstmt.close();
		conn.close();
	} 
	 
	// updateGanttSubworkCommentLike: 간트 하위업무 댓글 좋아요
	// 파라미터: comment_like(댓글 좋아요), subwork_comment_idx(하위업무댓글 idx)
	public void updateGanttSubworkCommentLike(int commentLike, int subworkCommentIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE comment_like"
				+ " SET comment_like  = ?"
				+ " WHERE subwork_comment_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, commentLike);
		pstmt.setInt(2, subworkCommentIdx);
		pstmt.executeUpdate();
		  
		pstmt.close();
		conn.close();
	} 
	 
// ----------------------간트 추가(하위업무)INSERT-----------------------------	
	 
	// addGanttsubworkMember: 하위업무 담당자 추가 
	// 파라미터: member_idx(멤버idx), subwork_idx, subwork_scope
	public void addGanttsubworkMember(int memberIdx, int subworkIdx, int subworkScope) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO subwork_member(member_idx, subwork_idx, subwork_scope) VALUES(?, ?, ?)";
			   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, subworkIdx);  
		pstmt.setInt(3, subworkScope);  
			   
		pstmt.executeUpdate();
			   
		pstmt.close();
		conn.close();
	}
	 
	// addGanttsubworkLike: 간트 하위업무 좋아요 추가
	// 파라미터: subwork_idx, member_idx(좋아요 누른 멤버idx), title_like(어떤 좋아요인지) 
	public void addGanttsubworkLike(int subworkIdx, int memberIdx, int titleLike) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO subwork_like(subwork_idx, member_idx, title_like) VALUES(?, ?, ?)";
			   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		pstmt.setInt(2, memberIdx);  
		pstmt.setInt(3, titleLike);  
			   
		pstmt.executeUpdate();
			   
		pstmt.close();
		conn.close();
	}
	 
	// addGanttsubworkComment: 간트 하위업무 댓글 추가
	// 파라미터: subwork_comment_idx(하위업무 댓글 idx), subwork_idx(하위업무idx), member_idx(댓글 단 멤버idx), subwork_comment(하위업무 댓글)
	public void addGanttsubworkComment(int subworkCommentIdx, int subworkIdx, int memberIdx, String subworkComment) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO subwork_comment(subwork_comment_idx, subwork_idx, member_idx, subwork_comment, comment_date) VALUES(?, ?, ?, ?, sysdate)";
			   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkCommentIdx);
		pstmt.setInt(2, subworkIdx);  
		pstmt.setInt(3, memberIdx);  
		pstmt.setString(4, subworkComment);  
			   
		pstmt.executeUpdate();
			   
		pstmt.close();
		conn.close();
	}
	 
	// addGanttSubwork: 간트 하위업무 추가
	// 파라미터: subwork_name(하위업무명) , work_idx(업무idx(하위업무가 포함된)), work_state(하위업무상태), priority(하위업무 우선순위), writer(작성자(member_idx)) 
	// startDate, finishDate( null이 아니면 위에있는 update문으로 ) (ex) "2024-06-26 16:40", 시간 : TO_DATE(?, 'yyyy-mm-dd hh24:mi')
	public void addGanttSubwork(String subworkName, int workIdx,  int workState, int priority, String startDate, String finishDate, int writer) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO gantt_subwork(subwork_idx, subwork_name, work_idx, work_state, priority, registration_date, start_date, finish_date, writer)" 
				+ " VALUES(seq_gantt_subwork.nextval, ?, ?, ?, ?, sysdate, null, null, ?)";
		String[] arr2 = {"subwork_idx"};   
		
		PreparedStatement pstmt = conn.prepareStatement(sql, arr2);
		  
		pstmt.setString(1, subworkName); 
		pstmt.setInt(2, workIdx); 
		pstmt.setInt(3, workState); 
		pstmt.setInt(4, priority); 
		pstmt.setInt(5, writer); 
		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		int subworkIdx = 0;   // 0 은 초기 값일 뿐, 의미 없음. 
		if(rs.next()) {
			subworkIdx = rs.getInt(1);
		}
		   
		pstmt.close();
		conn.close();
			 
		if(startDate != null)
			updateGanttSubworkStartDate(subworkIdx, startDate);
		if(finishDate != null)
			updateGanttSubworkFinishDate(subworkIdx, finishDate);
	}	 
	 
//------------------------------SELECT---------------------------------------	 
	 
	// checkGanttSubworkCountByWorkIdx: 간트 하위업무 개수(업무별)
	// 파라미터: work_idx
	// 리턴: 간트 하위업무 개수(업무별)
	public int checkGanttSubworkCountByWorkIdx(int workIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM gantt_subwork gs" 
				+ " WHERE gs.work_idx = ?"; 
				
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
	 
	// getGanttSubworkName: 간트 하위업무명 전체 출력(프로젝트별)
	// 파라미터: project_idx
	// 리턴: subwork_name
	public ArrayList<String> getGanttSubworkName(int projectIdx) throws Exception{
		Connection conn = getConnection();
		ArrayList<String> SubworkName = new ArrayList<String>();
		
		String sql = "SELECT distinct gs.subwork_name " 
				+ " FROM gantt_subwork gs, gantt_group gg" 
				+ " WHERE gs.work_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, projectIdx);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			SubworkName.add(rs.getString(1));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return SubworkName;
	}	
	 
	 
	// getGanttSubworkNameByWorkIdx: 간트 하위업무명 출력(업무별)
	// 파라미터: work_idx
	// 리턴: subwork_name
	public ArrayList<String> getGanttSubworkNameByWorkIdx(int WorkIdx) throws Exception{
		Connection conn = getConnection();
		ArrayList<String> SubworkName = new ArrayList<String>();
		
		String sql = "SELECT gs.subwork_name " 
				+ " FROM gantt_subwork gs " 
				+ " WHERE gs.work_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, WorkIdx);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			SubworkName.add(rs.getString(1));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return SubworkName;
	}
	 
	// getGanttWorkSubwork: 간트 업무에서 하위업무(밑에 뜨는 부분)
	// 파라미터: work_idx(업무idx)
	// 리턴 : work_state(하위업무상태), subwork_name(하위업무명), finish_date(하위업무 마감일), priority(하위업무 우선순위), comment_count(하위업무 댓글수), select_member_count(하위업무 담당자수)
	public ArrayList<GanttWorkSubworkDto> getGanttWorkSubwork(int workIdx) throws Exception {
		ArrayList<GanttWorkSubworkDto> listRet = new ArrayList<GanttWorkSubworkDto>();
		String sql = "SELECT gs.work_state, gs.subwork_name, gs.finish_date, gs.priority," 
				+ " (SELECT COUNT(*) FROM subwork_comment sc WHERE sc.subwork_idx = gs.subwork_idx) comment_count," 
				+ " (SELECT COUNT(*) FROM subwork_member sm WHERE sm.subwork_idx = gs.subwork_idx) select_member_count"
				+ " FROM gantt_subwork gs INNER JOIN gantt_work gw ON gs.work_idx = gw.work_idx "
				+ " WHERE gw.work_idx = ? ";
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int work_state = rs.getInt("work_state");
			String subwork_name = rs.getString("subwork_name");
			String finish_date = rs.getString("finish_date");
			int priority = rs.getInt("priority");
			int comment_count = rs.getInt("comment_count");
			int select_member_count = rs.getInt("select_member_count");
				
			GanttWorkSubworkDto dto = new GanttWorkSubworkDto(work_state, subwork_name, finish_date, priority, comment_count, select_member_count);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		  
		return listRet;
	}
	
	// getGanttSubworkDetailsProjectWorkName: 하위업무 자세히보기 윗부분
	// 파라미터: subwork_idx(하위업무 idx)
	// 리턴 : color_idx(프로젝트 색상), project_name(프로젝트이름), work_name(업무 이름)
	public ArrayList<GanttSubworkDetailsProjectWorkNameDto> getGanttSubworkDetailsProjectWorkName(int subworkIdx) throws Exception {
		ArrayList<GanttSubworkDetailsProjectWorkNameDto> listRet = new ArrayList<GanttSubworkDetailsProjectWorkNameDto>();
		String sql = "SELECT p.color_idx, p.project_name, gw.work_name" 
					+ " FROM gantt_group gg INNER JOIN gantt_work gw ON gw.group_idx = gg.ganttgroup_idx INNER JOIN project p ON gg.project_idx = p.project_idx INNER JOIN gantt_subwork gs ON gs.work_idx = gw.work_idx"
					+ " WHERE gs.subwork_idx = ? ";
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int color_idx = rs.getInt("color_idx");
			String project_name = rs.getString("project_name");
			String work_name = rs.getString("work_name");
			
			GanttSubworkDetailsProjectWorkNameDto dto = new GanttSubworkDetailsProjectWorkNameDto(color_idx, project_name, work_name);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
	  
		return listRet;
	}
	
	
	// getGanttSubworkWriter: 하위업무 자세히보기 
	// 파라미터 : subwork_idx (하위업무idx)
	// 리턴: profile_pic_url(프로필 사진), name(이름), registration_date(작성일자)
	public ArrayList<GanttWorkWriterDto> getGanttSubworkWriter(int subworkIdx) throws Exception{
		ArrayList<GanttWorkWriterDto> listRet = new ArrayList<GanttWorkWriterDto>();
			
		String sql = "SELECT m.profile_pic_url, m.name, gs.registration_date" 
				+ " FROM member m INNER JOIN subwork_member sm ON m.member_idx = sm.member_idx INNER JOIN gantt_subwork gs ON sm.subwork_idx = gs.subwork_idx" 
				+ " WHERE gs.subwork_idx = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		
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
	 
	 
	// getGanttSubworkInformation: 하위업무 자세히보기 정보
	// 파라미터 : subwork_idx(하위업무 idx)
	// 리턴: subwork_idx, subwork_name(하위업무명), work_state(업무 상태), name(작성자이름), start_date(시작일), finish_date(마감일), duration(기간)
	public ArrayList<GanttWorkInformationDto> getGanttSubworkInformation(int workIdx) throws Exception{
		ArrayList<GanttWorkInformationDto> listRet = new ArrayList<GanttWorkInformationDto>();
		
		String sql = "SELECT gs.subwork_idx, gs.subwork_name, m.name, gs.work_state, gs.start_date, gs.finish_date, (gs.finish_date-gs.start_date+1) duration" 
					+ " FROM gantt_subwork gs"
					+ " LEFT JOIN subwork_member sm ON gs.subwork_idx = sm.subwork_idx"
					+ " LEFT JOIN member m ON sm.member_idx = m.member_idx"
					+ " WHERE gs.work_idx = ?";

		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, workIdx);

		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int subworkIdx = rs.getInt("subwork_idx");
	 		String subworkName = rs.getString("subwork_name");
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
	 		String finishDate = rs.getString("finish_date");
	 		int duration = rs.getInt("duration");
	 		GanttWorkInformationDto dto = new GanttWorkInformationDto(subworkIdx, subworkName, workState, name, startDate, finishDate, duration);
		 	listRet.add(dto);
	 	}
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}
	
	// getGanttSubworkComment: 하위업무 댓글 출력
	// 파라미터 : subwork_idx (하위업무idx)
	// 리턴 : profile_pic_url(프로필 사진), name(작성자이름), comment_date(작성날짜), subwork_comment(댓글 내용)
	public ArrayList<GanttSubworkCommentDto> getGanttSubworkComment(int subworkIdx) throws Exception {
		ArrayList<GanttSubworkCommentDto> listRet = new ArrayList<GanttSubworkCommentDto>();
		String sql = "SELECT  m.profile_pic_url, m.name, sc.comment_date, sc.subwork_comment"
				+ " FROM subwork_comment sc INNER JOIN member m ON sc.member_idx = m.member_idx" 
				+ " WHERE sc.subwork_idx  =?"; 
		Connection conn = getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String profilePicUrl = rs.getString("profile_pic_url");
			String name = rs.getString("name");
			String commentDate = rs.getString("comment_date");
			String subwork_comment = rs.getString("subwork_comment");
		
			GanttSubworkCommentDto dto = new GanttSubworkCommentDto(profilePicUrl, name, commentDate, subwork_comment);
		 	listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	 
	// checkGanttSubworkReadMember: 하위업무 읽은 멤버수
	// 파라미터: subwork_idx(하위업무idx)
	// 리턴: 하위업무 읽은 멤버수
	public int checkGanttSubworkReadMember(int subworkIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM subwork_read sr" 
				+ " WHERE  sr.subwork_idx =?"; 
				
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		
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
	 
	// checkGanttSubworkCommentCount: 하위업무 댓글 수
	// 파라미터: subwork_idx(하위업무 idx)
	// 리턴: 하위업무 댓글 개수
	public int checkGanttSubworkCommentCount(int subworkIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM subwork_comment" 
				+ " WHERE subwork_idx =?"; 
				
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		
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
	 
	// checkGanttSubworkCommentLikeMemberCount: 하위업무 댓글 좋아요한 멤버수
	// 파라미터: subwork_comment_idx(하위업무 댓글 idx)
	// 리턴: 하위업무 댓글 좋아요한 멤버수
	public int checkGanttSubworkCommentLikeMemberCount(int subworkCommentIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM comment_like" 
				+ " WHERE subwork_comment_idx =?"; 
				
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkCommentIdx);
		
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
	 
	// getGanttSubworkCommentLikeMember: 하위업무 댓글 좋아요한 멤버
	// 파라미터: subwork_comment_idx(하위업무 댓글idx)
	// 리턴: profile_pic_url(멤버프로필), name(멤버명)
	public ArrayList<GanttkCommentLikeDto> getGanttSubworkCommentLikeMember(int subworkCommentIdx) throws Exception {
		ArrayList<GanttkCommentLikeDto> listRet = new ArrayList<GanttkCommentLikeDto>();
	 	String sql = "SELECT m.profile_pic_url , m.name" 
	 			+ " FROM comment_like cl INNER JOIN member m ON m.member_idx = cl.comment_like_member_idx" 
	 			+ " WHERE  cl.subwork_comment_idx  =?"; 
	 	Connection conn = getConnection();

	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, subworkCommentIdx);
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
	 
	// getGanttSubworkLike: 간트 하위업무에 대한 좋아요 
	// 파라미터: subwork_idx(하위업무idx)
	// 리턴: title_like(좋아요 이미지), name(멤버이름)
	public ArrayList<GanttWorkLikeDto> getGanttSubworkLike(int subworkIdx) throws Exception{
		ArrayList<GanttWorkLikeDto> listRet = new ArrayList<GanttWorkLikeDto>();
			
		String sql = "SELECT sl.title_like, m.name" 
					+ " FROM subwork_like sl INNER JOIN member m ON sl.member_idx = m.member_idx" 
					+ " WHERE sl.subwork_idx = ?";

		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);

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
	 
	// getGanttSubworkReadMember: 하위업무 읽은 사람 조회
	// 파라미터: subwork_idx(하위업무 idx)
	// 리턴: profile_pic_url(프로필 사진), member_name(멤버명), team_name(팀명), readdate(읽음일시)
	public ArrayList<GanttWorkReadMemberDto> getGanttSubworkReadMember(int subworkIdx) throws Exception {
		ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
	 	String sql = "SELECT m.profile_pic_url, m.name AS member_name, t.name AS team_name, sr.readdate"
	 			+ " FROM member m INNER JOIN subwork_read sr ON sr.member_idx = m.member_idx INNER JOIN team_member tm ON tm.member_idx = m.member_idx INNER JOIN team t ON tm.team_idx = t.team_idx" 
	 			+ " WHERE sr.subwork_idx = ? "; 
	 	Connection conn = getConnection();

	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, subworkIdx);
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
	 
	// getGanttSubworkUnreadMember: 하위업무 미확인 멤버 조회
	// 파라미터 : subwork_idx(하위업무 idx)
	// 리턴 : member_name(멤버이름), team_name(팀명) 
	public ArrayList<GanttWorkReadMemberDto> getGanttSubworkUnreadMember(int subworkIdx) throws Exception {
		ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
	 	String sql = "SELECT m.name AS member_name, t.name AS team_name"
	 			+ " FROM  team t INNER JOIN team_member tm ON tm.team_idx = t.team_idx JOIN member m ON tm.member_idx = m.member_idx JOIN subwork_unread su ON su.member_idx = m.member_idx" 
	 			+ " WHERE su.subwork_idx = ?"; 
	 	Connection conn = getConnection();

	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, subworkIdx);
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
	
	 
	// getSearchSubworkReadMember: 하위업무 읽은 사람 조회(검색)
	// 파라미터 : subwork_idx(하위업무idx), member_name(멤버이름)
	// 리턴 : profile_pic_url(프사), member_name(멤버명), team_name(팀명), readdate(읽음일시)
	public ArrayList<GanttWorkReadMemberDto> getSearchSubworkReadMember(int subworkIdx, String keyword) throws Exception{ 
		ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
		String sql = "SELECT m.profile_pic_url, m.name AS member_name , t.name AS team_name, sr.readdate"
				+ " FROM  member m INNER JOIN subwork_read sr ON sr.member_idx = m.member_idx INNER JOIN team_member tm ON sr.member_idx = tm.member_idx INNER  JOIN team t ON tm.team_idx = t.team_idx" 
				+ " WHERE  sr.subwork_idx =? AND m.name LIKE ?"; 
		Connection conn = getConnection();
	  
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
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
	
	// getSearchSubworkUnreadMember: 하위업무 미확인 멤버 조회(검색)
	// 파라미터: subwork_idx(하위업무idx), member_name(멤버이름)
	// 리턴: member_name(멤버명), team_name(팀명)
	public ArrayList<GanttWorkReadMemberDto> getSearchSubworkUnreadMember(int workIdx, String keyword) throws Exception{ 
		ArrayList<GanttWorkReadMemberDto> listRet = new ArrayList<GanttWorkReadMemberDto>();
		String sql = "SELECT m.name AS member_name , t.name AS team_name "
					+ " FROM  team_member tm INNER JOIN team t ON tm.team_idx = t.team_idx INNER JOIN subwork_unread su ON su.member_idx = tm.member_idx  INNER JOIN member m ON su.member_idx = m.member_idx" 
					+ " WHERE su.subwork_idx =? AND m.name LIKE ?"; 
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
	
	// checkGanttSubworkUnreadMember: 하위업무 미확인 멤버수
	// 파라미터: subwork_idx(하위업무 idx)
	// 리턴: 하위업무 미확인 멤버수
	public int checkGanttSubworkUnreadMember(int subworkIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM subwork_unread" 
				+ " WHERE subwork_idx =?"; 
				
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		
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
	 
	// getGanttSubwork: 간트 하위업무
	// 파라미터: work_idx
	// 리턴: subwork_name, name(멤버명), work_state, start_date, finish_date
	public ArrayList<GanttSubworkDto> getGanttSubwork(int subworkIdx) throws Exception{ 
		ArrayList<GanttSubworkDto> listRet = new ArrayList<GanttSubworkDto>();
		String sql = "SELECT gs.subwork_name, m.name, gs.work_state, gs.start_date, gs.finish_date"
					+ " FROM  gantt_subwork gs LEFT JOIN subwork_member sm ON gs.subwork_idx = sm.subwork_idx LEFT JOIN member m ON sm.member_idx = m.member_idx" 
					+ " WHERE  gs.work_idx = ?"; 
		Connection conn = getConnection();
	  
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, subworkIdx);
		ResultSet rs = pstmt.executeQuery();
	  
		while(rs.next()) {
			String subwork_name = rs.getString("subwork_name");
			String name = rs.getString("name");
			int work_state = rs.getInt("work_state");
			String start_date = rs.getString("start_date");
			String finish_date = rs.getString("finish_date");
			GanttSubworkDto dto = new GanttSubworkDto(subwork_name, name, work_state, start_date, finish_date);
			listRet.add(dto);
		}
	      
		rs.close();
		pstmt.close();
		conn.close();
		
		return listRet;
	}	
		   
}
