package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.GanttkCommentLikeDto;
import dto.ProjectBookmarkDto;
import dto.ProjectDto;
import dto.ProjectNoBookmarkDto;
import dto.QnaAnswerDto;

public class ProjectDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}

	// checkBookmarkProjectByMemberIdx: 프로젝트 즐겨찾기 여부 확인.
	// 파라미터 : member_idx, project_idx
	// 리턴: 1(즐겨찾기O), 0(즐겨찾기X)
	/*public int checkBookmarkProjectByMemberIdx(int memberIdx, int projectIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM bookmark" 
				+ " WHERE member_idx_from=?" 
				+ " AND project_idx=?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, projectIdx);
		ResultSet rs = pstmt.executeQuery();
		// insert update delete -----> pstmt.executeUpdate(); 로 실행.(리턴타입 : int... 왜? '영향받은 행의 개수')
		// select -------------------> pstmt.executeQuery(); 로 실행. 리턴타입 : ResultSet
		int result = -1;
		if(rs.next()) {   // 결과테이블로 한줄일 때 : if(rs.next()) { ... }         // 결과테이블이 두 줄 이상일 수 있을 때 : while(rs.next()) { ... } 
			result = rs.getInt(1);    // 1 : 첫 번째 컬럼을 의미.
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}*/
	
	public int checkBookmarkProjectByMemberIdx(int memberIdx, int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = -1;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT COUNT(*) FROM bookmark WHERE member_idx_from = ? AND project_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setInt(2, projectIdx);
	        
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            result = rs.getInt(1);  
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
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
	
	//------------------------------INSERT-----------------------------------------------
	
	// "jdbc pstmt 인서트 키값 반환"
	// createProject: 프로젝트 추가
	// 파라미터: team_idx(팀 idx), project_name(프로젝트 이름), color_idx(프로젝트 컬러), writer
	// 리턴 값: 생성된 프로젝트의 project_idx 값.
	/*public int createProject(String projectName, int teamIdx, int colorIdx, int writer) throws Exception {
		Connection conn = getConnection();
		String sql ="INSERT INTO PROJECT(project_idx, team_idx, project_name, color_idx, writer, registration_date) VALUES(SEQ_NEW_PROJECT.nextval, ?, ?, ?, ?, sysdate)";
		String[] arrPk = {"project_idx"};
		PreparedStatement pstmt = conn.prepareStatement(sql, arrPk);
		pstmt.setInt(1, teamIdx); 
		pstmt.setString(2, projectName); 
		pstmt.setInt(3, colorIdx); 
		pstmt.setInt(4, writer); 
		   
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		int ret = 0;
		if(rs.next()) {
			ret = rs.getInt(1); // 1이 아닌 "project_idx" 로 적어줘도 됨
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return ret;
	}*/
	
	public int createProject(String projectName, int teamIdx, int colorIdx, int writer) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int ret = 0;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "INSERT INTO PROJECT(project_idx, team_idx, project_name, color_idx, writer, registration_date) "
	                   + "VALUES(SEQ_NEW_PROJECT.nextval, ?, ?, ?, ?, sysdate)";
	        String[] arrPk = {"project_idx"};
	        pstmt = conn.prepareStatement(sql, arrPk);
	        pstmt.setInt(1, teamIdx);
	        pstmt.setString(2, projectName);
	        pstmt.setInt(3, colorIdx);
	        pstmt.setInt(4, writer);
	        
	        pstmt.executeUpdate();
	        
	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            ret = rs.getInt(1);  // 생성된 프로젝트 ID
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
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
	    
	    return ret;
	}
	
	// addProjectMember : 프로젝트멤버 추가
	// 파라미터: member_idx(멤버idx), project_idx(프로젝트idx)
	/*public void addProjectMember(int memberIdx, int projectIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO project_member(member_idx, project_idx) VALUES(? , ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, projectIdx);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();   
	}*/
	
	public void addProjectMember(int memberIdx, int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "INSERT INTO project_member(member_idx, project_idx) VALUES(?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setInt(2, projectIdx);
	        
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
	
	// addProjectBookmark: 프로젝트 즐겨찾기(어떤 멤버가 즐겨찾기 했는지)
	// 파라미터: member_idx_from, project_idx
	/*public void addProjectBookmark(int memberIdxFrom, int projectIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO bookmark(bookmark_idx, member_idx_from, project_idx) VALUES(seq_bookmark.nextval, ?, ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, projectIdx);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
	}*/
	
	
	public void addProjectBookmark(int memberIdxFrom, int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "INSERT INTO bookmark(bookmark_idx, member_idx_from, project_idx) VALUES(seq_bookmark.nextval, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdxFrom);
	        pstmt.setInt(2, projectIdx);
	        
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
	
//--------------------------SELECT------------------------------------------
	
	// getProjectColorName : 프로젝트의 색상과 프로젝트명 출력
	// 파라미터 : project_idx(프로젝트 idx)
	// 리턴: color(색상), project_name(프로젝트명)
	/*public ProjectDto getProjectColorName(int projectIdx) throws Exception {
		String sql = "SELECT c.color , p.project_name" 
	 			+ " FROM project p LEFT JOIN color c ON p.color_idx = c.color_idx" 
	 			+ " WHERE  p.project_idx = ?"; 
	 	Connection conn = getConnection();

	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, projectIdx);
	 	ResultSet rs = pstmt.executeQuery();
	 	
	 	ProjectDto dto = null;
	 	if(rs.next()) {
	 		String projectName = rs.getString("project_name");
	 		String color = rs.getString("color");
	 		int colorIdx = rs.getInt("color_idx");
	 	
	 		dto = new ProjectDto(projectIdx, projectName, color, colorIdx);
	 	}
	 	rs.close();
	 	pstmt.close();
	 	conn.close();
	 	return dto;
	}	*/
	
	public ProjectDto getProjectColorName(int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    ProjectDto dto = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT c.color, p.project_name, p.color_idx" 
	                   + " FROM project p LEFT JOIN color c ON p.color_idx = c.color_idx" 
	                   + " WHERE p.project_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, projectIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            String projectName = rs.getString("project_name");
	            String color = rs.getString("color");
	            int colorIdx = rs.getInt("color_idx");
	            dto = new ProjectDto(projectIdx, projectName, color, colorIdx);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
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
	    
	    return dto;
	}
	
	
	// checkByProjectMemberCount : 지정 프로젝트 인원수 조회
	// 파라미터 : project_idx
	// 리턴 : 지정 프로젝트별 인원수
	/*public int checkByProjectMemberCount(int projectIdx) throws Exception {
		String sql = "SELECT COUNT(*) FROM project_member pm" 
				+ " WHERE pm.project_idx =?"; 
				
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, projectIdx);
		
		ResultSet rs = pstmt.executeQuery();
		int result = -1;
		if(rs.next()) {    
			result = rs.getInt(1);    
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}*/
	
	
	public int checkByProjectMemberCount(int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = -1;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT COUNT(*) FROM project_member pm WHERE pm.project_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, projectIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            result = rs.getInt(1); 
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
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
	
	// getProjectBookmark : 즐겨찾기 된 프로젝트(팀별)
	// 파라미터: team_idx, member_idx_from
	// 리턴: project_idx, color, project_name, member_count
	/*public ArrayList<ProjectBookmarkDto> getProjectBookmark(int teamIdx, int memberIdxFrom) throws Exception {
		ArrayList<ProjectBookmarkDto> listRet = new ArrayList<ProjectBookmarkDto>();
	 	String sql = "SELECT p.project_idx, c.color, p.project_name, " 
	               + " (SELECT count(*) FROM project_member pm WHERE pm.project_idx = p.project_idx) AS member_count"
	               + " FROM project p" 
	               + " INNER JOIN color c ON p.color_idx = c.color_idx"
	               + " INNER JOIN bookmark b ON p.project_idx = b.project_idx" 
	               + " WHERE p.team_idx = ?" 
	               + " AND b.member_idx_from = ?"
	               + " ORDER BY p.project_idx DESC"; 

	 	Connection conn = getConnection();
	 	
	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, teamIdx);
	 	pstmt.setInt(2, memberIdxFrom);
	 	
		ResultSet rs = pstmt.executeQuery();
	 	while(rs.next()) {
	 		int projectIdx = rs.getInt("project_idx");
	 		String projectName = rs.getString("project_name");
	 		String color = rs.getString("color");
	 		int memberCount = rs.getInt("member_count");
	 	
	 		ProjectBookmarkDto dto = new ProjectBookmarkDto(projectIdx, projectName, color, memberIdxFrom, teamIdx, memberCount);
		 	listRet.add(dto);
	 	}
	 	rs.close();
	 	pstmt.close();
	 	conn.close();
	 	return listRet;
	}	*/
	
	
	public ArrayList<ProjectBookmarkDto> getProjectBookmark(int teamIdx, int memberIdxFrom) {
	    ArrayList<ProjectBookmarkDto> listRet = new ArrayList<ProjectBookmarkDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT p.project_idx, c.color, p.project_name, " 
	                   + " (SELECT count(*) FROM project_member pm WHERE pm.project_idx = p.project_idx) AS member_count"
	                   + " FROM project p" 
	                   + " INNER JOIN color c ON p.color_idx = c.color_idx"
	                   + " INNER JOIN bookmark b ON p.project_idx = b.project_idx" 
	                   + " WHERE p.team_idx = ?" 
	                   + " AND b.member_idx_from = ?"
	                   + " ORDER BY p.project_idx DESC";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        pstmt.setInt(2, memberIdxFrom);
	        
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int projectIdx = rs.getInt("project_idx");
	            String projectName = rs.getString("project_name");
	            String color = rs.getString("color");
	            int memberCount = rs.getInt("member_count");
	            
	            ProjectBookmarkDto dto = new ProjectBookmarkDto(projectIdx, projectName, color, memberIdxFrom, teamIdx, memberCount);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
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
	    return listRet;
	}
	 
	// getAllProjectNoBookmark: 즐겨찾기 제외 전체 프로젝트
	// 파라미터: team_idx, member_idx_from
	// 리턴: project_idx, color, project_name, project_member_count
	/*public ArrayList<ProjectNoBookmarkDto> getAllProjectNoBookmark(int memberIdxFrom, int teamIdx) throws Exception {
		ArrayList<ProjectNoBookmarkDto> listRet = new ArrayList<ProjectNoBookmarkDto>();
	 	String sql = "SELECT p.project_idx, c.color AS color, p.project_name AS project_name, " 
	               + "    (SELECT count(*) FROM project_member pm WHERE pm.project_idx = p.project_idx) AS project_member_count "  
	               + " FROM project p" 
	               + " INNER JOIN color c ON p.color_idx = c.color_idx"
	               + " WHERE p.team_idx = ?" 
	               + " AND p.project_idx NOT IN " 
	               + "    (SELECT project_idx" 
	               + "    FROM bookmark" 
	               + "    WHERE project_idx IS NOT NULL" 
	               + "    AND member_idx_from = ?)"; 

	 	Connection conn = getConnection();
	 	
	 	PreparedStatement pstmt = conn.prepareStatement(sql);
	 	pstmt.setInt(1, teamIdx);
	 	pstmt.setInt(2, memberIdxFrom);
	 	
		ResultSet rs = pstmt.executeQuery();
	 	while(rs.next()) {
	 		int projectIdx = rs.getInt("project_idx");
	        String projectName = rs.getString("project_name");
	        String color = rs.getString("color");
	        int projectMemberCount = rs.getInt("project_member_count");
	        ProjectNoBookmarkDto dto = new ProjectNoBookmarkDto(projectIdx, projectName, color, teamIdx, memberIdxFrom, projectMemberCount);
	        listRet.add(dto);
	 	}
	 	rs.close();
	 	pstmt.close();
	 	conn.close();
	 	return listRet;
	}*/
	
	
	public ArrayList<ProjectNoBookmarkDto> getAllProjectNoBookmark(int memberIdxFrom, int teamIdx) {
	    ArrayList<ProjectNoBookmarkDto> listRet = new ArrayList<ProjectNoBookmarkDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT p.project_idx, c.color AS color, p.project_name AS project_name, " 
	                   + "    (SELECT count(*) FROM project_member pm WHERE pm.project_idx = p.project_idx) AS project_member_count "  
	                   + " FROM project p" 
	                   + " INNER JOIN color c ON p.color_idx = c.color_idx"
	                   + " WHERE p.team_idx = ?" 
	                   + " AND p.project_idx NOT IN " 
	                   + "    (SELECT project_idx" 
	                   + "    FROM bookmark" 
	                   + "    WHERE project_idx IS NOT NULL" 
	                   + "    AND member_idx_from = ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        pstmt.setInt(2, memberIdxFrom);
	        
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int projectIdx = rs.getInt("project_idx");
	            String projectName = rs.getString("project_name");
	            String color = rs.getString("color");
	            int projectMemberCount = rs.getInt("project_member_count");
	            
	            ProjectNoBookmarkDto dto = new ProjectNoBookmarkDto(projectIdx, projectName, color, teamIdx, memberIdxFrom, projectMemberCount);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
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
	    return listRet;
	}
	 
	 
	// checkProjectColor: 프로젝트 컬러 
	// 파라미터: color_idx
	// 리턴: color
	/*public String checkProjectColor(int colorIdx) throws Exception {
		String sql = "SELECT color FROM color" 
				+ " WHERE color_idx =?"; 
				
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, colorIdx);
		
		ResultSet rs = pstmt.executeQuery();
		String result = "";
	 	if(rs.next()) {
	 		result = rs.getString(1); 
	 	}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}*/
	
	public String checkProjectColor(int colorIdx) {
	    String result = "";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT color FROM color WHERE color_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, colorIdx);
	        
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            result = rs.getString("color");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
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
	
	// bookmarkOnProject: 프로젝트 즐겨찾기 
	// 파라미터: member_idx, project_idx
	/*public void bookmarkOnProject(int memberIdx, int projectIdx) throws Exception {
		String sql = "INSERT INTO bookmark(bookmark_idx, member_idx_from, project_idx)" 
				 	+ " VALUES (seq_bookmark.nextval, ?, ?)";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, projectIdx);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}*/
	
	
	public void bookmarkOnProject(int memberIdx, int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "INSERT INTO bookmark(bookmark_idx, member_idx_from, project_idx)"
	                   + " VALUES (seq_bookmark.nextval, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setInt(2, projectIdx);
	        
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
	
	// bookmarkOffProject: 프로젝트 즐겨찾기 해제
	// 파라미터: member_idx, project_idx
	/*public void bookmarkOffProject(int memberIdx, int projectIdx) throws Exception {
		String sql = "DELETE FROM bookmark WHERE member_idx_from=? AND project_idx=?";
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, projectIdx);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}*/
	
	public void bookmarkOffProject(int memberIdx, int projectIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "DELETE FROM bookmark WHERE member_idx_from = ? AND project_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setInt(2, projectIdx);
	        
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
	
	// checkProjectName
	// 파라미터: team_idx
	// 리턴: project_name
	/*public ArrayList<ProjectBookmarkDto> checkProjectName(int teamIdx) throws Exception {
		ArrayList<ProjectBookmarkDto> listRet = new ArrayList<>();
		String sql = "SELECT project_name"
		            + " FROM project"
		            + " WHERE team_idx = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		    int projectIdx = rs.getInt("project_idx");
		    String projectName = rs.getString("project_name");
		    String color = rs.getString("color");
		    int memberIdxFrom = rs.getInt("member_idx_from");
		    int memberCount = rs.getInt("member_count");
		
		    ProjectBookmarkDto dto = new ProjectBookmarkDto(projectIdx, projectName, color, memberIdxFrom, teamIdx, memberCount);
		    listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}*/
	
	
	public ArrayList<ProjectBookmarkDto> checkProjectName(int teamIdx) {
	    ArrayList<ProjectBookmarkDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "SELECT project_idx, project_name, color, member_idx_from, member_count"
	                   + " FROM project"
	                   + " WHERE team_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int projectIdx = rs.getInt("project_idx");
	            String projectName = rs.getString("project_name");
	            String color = rs.getString("color");
	            int memberIdxFrom = rs.getInt("member_idx_from");
	            int memberCount = rs.getInt("member_count");
	            
	            ProjectBookmarkDto dto = new ProjectBookmarkDto(projectIdx, projectName, color, memberIdxFrom, teamIdx, memberCount);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        
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
	    return listRet;
	}
}

