package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.AlarmGanttNoticeDto;
import dto.GanttkCommentLikeDto;

public class AlarmDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}

	public void addGanttAlarm(int noticeIdx, int projectIdx, int workIdx, int subworkIdx, String alarmTxt, int memberIdx, String alarmDate) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO notice(notice_idx, project_idx, work_idx, subwork_idx, alarm_txt, member_idx, alarm_date) VALUES(?, ?, ?, ?, ?, ?, ?)";
				
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, noticeIdx);
		pstmt.setInt(2, projectIdx);
		pstmt.setInt(3, workIdx);
		pstmt.setInt(4, subworkIdx);
		pstmt.setString(5, alarmTxt);
		pstmt.setInt(6, memberIdx);
		pstmt.setString(7, alarmDate);

		
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	

	
	public ArrayList<AlarmGanttNoticeDto> getAlarmGanttNotice(int teamIdx) throws Exception {
		ArrayList<AlarmGanttNoticeDto> listRet = new ArrayList<AlarmGanttNoticeDto>();
		String sql = "SELECT * FROM (" 
				+ " SELECT m.profile_pic_url AS profile_pic_url, p.project_name AS txt_name, m.name AS member_name, n.alarm_txt AS alarm_txt, n.alarm_date AS alarm_date " 
				+ " FROM notice n " 
				+ " LEFT JOIN project p ON n.project_idx = p.project_idx" 
				+ " LEFT JOIN member m ON m.member_idx = n.member_idx" 
				+ " LEFT JOIN team_member tm ON tm.member_idx = n.member_idx" 
				+ " WHERE tm.team_idx = ?" 
				+ " AND n.project_idx is not null" 
				+ " UNION ALL" 
				+ " SELECT m.profile_pic_url AS profile_pic_url, gw.work_name AS txt_name, m.name AS member_name, n.alarm_txt AS alarm_txt, n.alarm_date AS alarm_date" 
				+ " FROM notice n" 
				+ " LEFT JOIN gantt_work gw ON n.work_idx = gw.work_idx" 
				+ " LEFT JOIN member m ON m.member_idx = n.member_idx" 
				+ " LEFT JOIN team_member tm ON tm.member_idx = n.member_idx" 
				+ " WHERE tm.team_idx = ?" 
				+ " AND n.work_idx is not null" 
				+ " UNION ALL" 
				+ " SELECT m.profile_pic_url AS profile_pic_url, gs.subwork_name AS txt_name, m.name AS member_name, n.alarm_txt AS alarm_txt, n.alarm_date AS alarm_date" 
				+ " FROM notice n" 
				+ " LEFT JOIN gantt_subwork gs ON n.subwork_idx = gs.subwork_idx"  
				+ " LEFT JOIN member m ON m.member_idx = n.member_idx" 
				+ " LEFT JOIN team_member tm ON tm.member_idx = n.member_idx" 
				+ " WHERE tm.team_idx = ?" 
				+ " AND n.subwork_idx is not null" 
				+ " ) ORDER BY alarm_date DESC";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, teamIdx);
		pstmt.setInt(3, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
	 		String profilePicUrl = rs.getString("profile_pic_url");
	 		String txtName = rs.getString("txt_name");
	 		String memberName = rs.getString("member_name");
	 		String alarmTxt = rs.getString("alarm_txt");
	 		String alarmDate = rs.getString("alarm_date");
	 	
	 		AlarmGanttNoticeDto dto = new AlarmGanttNoticeDto(profilePicUrl, txtName, memberName, alarmTxt, alarmDate, teamIdx);
		 	listRet.add(dto);
	 	}
	 	rs.close();
	 	pstmt.close();
	 	conn.close();
	 	return listRet;
	}
}
