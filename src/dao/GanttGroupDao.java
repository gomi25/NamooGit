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
	// 리턴: ganttgroup_idx(간트그룹idx), project_idx(프로젝트idx), ganttgroup_name(간트그룹이름)
	public void addGanttGroup(int ganttGroupIdx, int projectIdx, String ganttGroupName) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "INSERT INTO gantt_group(ganttgroup_idx, project_idx, ganttgroup_name) VALUES(?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setInt(1, ganttGroupIdx);
	        pstmt.setInt(2, projectIdx);
	        pstmt.setString(3, ganttGroupName);
	        
	        pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	    } finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
//-------------------------SELECT-------------------------------	
	
	// getGanttGroupName : 간트차트 그룹명, 그룹개수 출력  
	// 파라미터: project_idx
	// 리턴: 그룹수
	public String[] getGanttGroupName(int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs1 = null;
	    ResultSet rs2 = null;
	    String[] groupName = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql1 = "SELECT count(*) FROM gantt_group gg WHERE gg.project_idx = ?";
	        pstmt1 = conn.prepareStatement(sql1);
	        pstmt1.setInt(1, projectIdx);
	        
	        rs1 = pstmt1.executeQuery();
	        int count = 0;
	        if (rs1.next()) {
	            count = rs1.getInt(1);
	        }
	        
	        groupName = new String[count];
	        
	        String sql2 = "SELECT gg.ganttgroup_name FROM gantt_group gg WHERE gg.project_idx = ?";
	        pstmt2 = conn.prepareStatement(sql2);
	        pstmt2.setInt(1, projectIdx);
	        
	        rs2 = pstmt2.executeQuery();
	        int i = 0;
	        while (rs2.next()) {
	            groupName[i] = rs2.getString("ganttgroup_name");
	            i++;
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        groupName = new String[0];
	        
	    } finally {
	        try {
	            if (rs2 != null) {
	                rs2.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (pstmt2 != null) {
	                pstmt2.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (rs1 != null) {
	                rs1.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (pstmt1 != null) {
	                pstmt1.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return groupName;
	}
	 
	// getGanttGroupName1 : 간트차트 그룹명 출력 (프로젝트별) 
	// 파라미터: project_idx
	// 리턴: ganttgroup_idx, ganttgroup_name
	public ArrayList<GanttGroupDto> getGanttGroupName1(int projectIdx) {
	    ArrayList<GanttGroupDto> listRet = new ArrayList<GanttGroupDto>();
	    Connection conn = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT ganttgroup_idx, ganttgroup_name FROM gantt_group WHERE project_idx = ?";
	        pstmt2 = conn.prepareStatement(sql);
	        pstmt2.setInt(1, projectIdx);
	        
	        rs = pstmt2.executeQuery();
	        while (rs.next()) {
	            int ganttgroupIdx = rs.getInt("ganttgroup_idx");
	            String ganttgroupName = rs.getString("ganttgroup_name");
	            GanttGroupDto dto = new GanttGroupDto(ganttgroupIdx, ganttgroupName);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        listRet = new ArrayList<>(); 
	        
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (pstmt2 != null) {
	                pstmt2.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return listRet;
	}
	 
	// checkGanttWorkSubworkCount: (그룹별)업무 + 하위업무 개수
	// 파라미터: group_idx, work_idx
	// 리턴: (그룹별)업무 + 하위업무 개수
	public int checkGanttWorkSubworkCount(int groupIdx, int workIdx) {
	    String sql = "SELECT SUM(counts) AS total_count"
	               + " FROM (SELECT COUNT(*) AS counts FROM gantt_work gw WHERE gw.group_idx = ?" 
	               + " UNION ALL" 
	               + " SELECT COUNT(*) AS counts FROM gantt_subwork gs WHERE gs.work_idx = ?)";
	               
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = -1;
	    
	    try {
	        conn = getConnection();
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, groupIdx);
	        pstmt.setInt(2, workIdx);
	        
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            result = rs.getInt("total_count");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        result = -1;
	        
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}

}
