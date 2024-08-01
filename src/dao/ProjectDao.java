package dao;

//5-2.간트차트 페이지에서 (프로젝트나열-참여중) 54p
//- input : 팀idx
//- output : (즐겨찾기 여부, 프로젝트컬러, 프로젝트이름, 프로젝트 참여 인원수,  미확인 알림 수)의 리스트.
//----------> 즐겨찾기 여부 확인은, 5-0 기능 사용.
//ProjectDao / ArrayList<ProjectDto> getListProjectJoinByTeamIdx(int teamIdx).

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

	//5-0. 프로젝트 즐겨찾기 여부 확인.
	//- input : member_idx, project_idx
	//- output : 1(즐겨찾기O), 0(즐겨찾기X)
	public int checkBookmarkProjectByMemberIdx(int memberIdx, int projectIdx) throws Exception {
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
	}
	
	// 5-1. 간트차트 페이지에서 (프로젝트나열-즐겨찾기) 54p
	// - input : 팀idx
	// - output : (즐겨찾기 여부, 프로젝트컬러, 프로젝트이름, 프로젝트 참여 인원수,  미확인 알림 수)의 리스트.
	//----------> 즐겨찾기 여부 확인은, 5-0 기능 사용.
	// ProjectDao / ArrayList<ProjectBookmarkDto> getListProjectBookmarkByTeamIdx(int teamIdx).
	
	
	
	
	//------------------------------INSERT-----------------------------------------------
	
	
	// ㅁ. 구글, "jdbc pstmt 인서트 키값 반환"
	// createProject : 프로젝트 추가
	// projectIdx :프로젝트idx , teamIdx : 팀 idx, projectName: 프로젝트 이름, colorIdx: 프로젝트 컬러
	// 리턴 값 : 생성된 프로젝트의 project_idx 값.
	public int createProject(String projectName, int teamIdx, int colorIdx, int writer) throws Exception {
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
	}
	

	
	// addProjectMember : 프로젝트멤버 추가
	// memberIdx : 멤버idx, projectIdx : 프로젝트idx
	public void addProjectMember(int memberIdx, int projectIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO PROJECT_MEMBER(MEMBER_IDX, PROJECT_IDX) VALUES(? , ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, projectIdx);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
		   
		   
	}
	
	// addProjectBookmark : 프로젝트 즐겨찾기(어떤 멤버가 즐겨찾기 했는지)
	public void addProjectBookmark(int memberIdxFrom, int projectIdx) throws Exception {
		Connection conn = getConnection();
		String sql = "INSERT INTO BOOKMARK(bookmark_idx, member_idx_from, project_idx) VALUES(seq_bookmark_project.nextval, ?, ?)";
		   
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdxFrom);
		pstmt.setInt(2, projectIdx);  
		   
		pstmt.executeUpdate();
		   
		pstmt.close();
		conn.close();
		   
		   
	}
	
//--------------------------SELECT------------------------------------------
	
	
	 // getProjectColorName : 프로젝트의 색상과 프로젝트명 출력
	// input : project_idx(프로젝트 idx)
	// output : 색상idx, 프로젝트명
	 public ProjectDto getProjectColorName(int projectIdx) throws Exception {
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
	 }	
	
	
	 // checkByProjectMemberCount : 지정 프로젝트 인원수 조회
	 // input : project_idx
	 // output : 지정 프로젝트별 인원수
	 public int checkByProjectMemberCount(int projectIdx) throws Exception {
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
	}
	
	 // getProjectBookmark : 즐겨찾기 된 프로젝트(팀별)
	 public ArrayList<ProjectBookmarkDto> getProjectBookmark(int teamIdx, int memberIdxFrom) throws Exception {
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
	 }	
	 
	 // 즐겨찾기 제외 전체 프로젝트
	 public ArrayList<ProjectNoBookmarkDto> getAllProjectNoBookmark(int memberIdxFrom, int teamIdx) throws Exception {
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
	 }
	 
	 

	 // 프로젝트 컬러 
	 public String checkProjectColor(int colorIdx) throws Exception {
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
	 }
	 
	 
	 public void bookmarkOnProject(int memberIdx, int projectIdx) throws Exception {
		 String sql = "INSERT INTO bookmark(bookmark_idx, member_idx_from, project_idx) " + 
				 	"VALUES (seq_bookmark_project.nextval, ?, ?)";
		 Connection conn = getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(sql);
		 pstmt.setInt(1, memberIdx);
		 pstmt.setInt(2, projectIdx);
		 pstmt.executeUpdate();
		 pstmt.close();
		 conn.close();
	 }
	
	 public void bookmarkOffProject(int memberIdx, int projectIdx) throws Exception {
		 String sql = "DELETE FROM bookmark WHERE member_idx_from=? AND project_idx=?";
		 Connection conn = getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(sql);
		 pstmt.setInt(1, memberIdx);
		 pstmt.setInt(2, projectIdx);
		 pstmt.executeUpdate();
		 pstmt.close();
		 conn.close();
	 }
	
	
	
	
}













