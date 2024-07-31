package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.GanttGroupDto;
import dto.GanttWorkInformationDto;

public class GanttGroupDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
	
	// addGanttGroup : 간트 그룹 추가
	// ganttGroupIdx : 간트그룹idx, projectIdx : 프로젝트idx, ganttGroupName : 간트그룹이름
	public void addGanttGroup(int ganttGroupIdx, int projectIdx, String ganttGroupName) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO GANTT_GROUP(GANTTGROUP_IDX, PROJECT_IDX, GANTTGROUP_NAME) VALUES(?, ?, ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, ganttGroupIdx);
		pstmt.setInt(2, projectIdx);  
		pstmt.setString(3, ganttGroupName); 
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
		   
		   
	}	
	
	
	
//-------------------------SELECT-------------------------------	
	
	// getGanttGroupName1 이랑 같음
	// getGanttGroupName : 간트차트 그룹명 출력 
	 public String[] getGanttGroupName(int projectIdx) throws Exception{
			Connection conn = getConnection();
			
			String sql1 = "SELECT count(*) FROM gantt_group gg WHERE gg.project_idx = ?";
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setInt(1, projectIdx);
			
			int count = 0;
			ResultSet rs1 = pstmt1.executeQuery();
			while(rs1.next()) {
				count = rs1.getInt(1);
			}
			
			rs1.close();
			pstmt1.close();
			
			String[] groupName = new String[count];
			
			String sql2 = "SELECT gg.ganttgroup_name" + " FROM gantt_group gg" + " WHERE gg.project_idx = ?";

			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, projectIdx);

			ResultSet rs2 = pstmt2.executeQuery();
			int i = 0;
			while (rs2.next()) {
					groupName[i] = rs2.getString("ganttgroup_name");
					i++;
			}
			
			rs2.close();
			pstmt2.close();
			conn.close();
			
			return groupName;

		}
	 
	 // getGanttGroupName랑 같음
	 // getGanttGroupName1 : 간트차트 그룹명 출력 (프로젝트별)
	 public ArrayList<GanttGroupDto> getGanttGroupName1(int projectIdx) throws Exception{
		ArrayList<GanttGroupDto> listRet = new ArrayList<GanttGroupDto>();

	 	Connection conn = getConnection();
		
		String sql = "SELECT ganttgroup_idx, ganttgroup_name" 
					+ " FROM gantt_group" 
					+ " WHERE project_idx = ?";

		PreparedStatement pstmt2 = conn.prepareStatement(sql);
		pstmt2.setInt(1, projectIdx);

		ResultSet rs = pstmt2.executeQuery();
		while (rs.next()) {
			int ganttgroupIdx = rs.getInt(1);
			String ganttgroupName = rs.getString(2);
			GanttGroupDto dto = new GanttGroupDto(ganttgroupIdx, ganttgroupName);
		 	listRet.add(dto);
		}
		rs.close();
		pstmt2.close();
		conn.close();
		
		return listRet;
	}
	 
	 
	 
	 public int checkGanttWorkSubworkCount(int groupIdx, int workIdx) throws Exception {
		String sql = "SELECT SUM(counts) AS total_count "
				+ "FROM  (SELECT COUNT(*) AS counts FROM gantt_work gw WHERE gw.group_idx = ?" 
				+ " UNION ALL" 
				+ " SELECT COUNT(*) AS counts FROM gantt_subwork gs WHERE gs.work_idx = ?)"; 
				
				
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, groupIdx);
		pstmt.setInt(2, workIdx);
		
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

}
