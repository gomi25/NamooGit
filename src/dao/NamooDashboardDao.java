package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.DashboardMainDto;
import dto.DashboardProjectDto;

public class NamooDashboardDao {
	Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver"; 	// 드라이버 정보
	 	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속정보
	 	String dbId = "namoo";
	 	String dbPw = "7777";
	 	Class.forName(driver);
	 	Connection conn = DriverManager.getConnection(url, dbId, dbPw);
	 	
	 	return conn;
	}
	/* ===============================대시보드==================================== */
	// dashboardChangeWidgetSizeSamll(int) : widgetIdx, widgetIdx 값을 받아서  위젯크기 변경
	// 파라미터  : memberIdx, widgetIdx
	public void dashboardChangeWidgetSizeSmall(int widgetIdx, int memberIdx) throws Exception{
		Connection conn  = getConnection();
		String sql = "UPDATE widget_form SET w_size = 1" 
					+ " WHERE widget_idx = ?"
					+ " AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, widgetIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}
	// 위젯 사이즈 크게 변경
	// dashboardChangeWidgetSizeBig(int) : member_idx, widgetIdx 값을 받아서  위젯크기 변경
	// 파라미터  : memberIdx, widgetIdx
	public void dashboardChangeWidgetSizeBig(int widgetIdx, int memberIdx) throws Exception{
		Connection conn  = getConnection();
		String sql = "UPDATE widget_form SET w_size = 2"
					+ " WHERE widget_idx = ?"
					+ " AND member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, widgetIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	// 이름 표시 > NamooMemberDao: 12번 이름 select문
	// dashboardChangeWidgetSizeBig(int) : member_idx 값을 받아서 이름을 리턴
	// 파라미터  : memberIdx
	// 리턴 : 파라미터 값에 해당하는 멤버의 이름
	public ArrayList<DashboardMainDto> showMainDashboardByMemberIdx (int memberIdx) throws Exception{
		ArrayList<DashboardMainDto> listRet = new ArrayList<DashboardMainDto>();
		Connection conn = getConnection();
		String sql = "SELECT w_order, widget_idx, w_size"
					+ " FROM widget_form "
					+ " WHERE member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int wOrder = rs.getInt("wOrder");
			int wSize = rs.getInt("wSize");
			int widgetIdx = rs.getInt("widgetIdx");
			DashboardMainDto dto = new DashboardMainDto(wOrder, widgetIdx, wSize);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	// 프로젝트 창 프로젝트 리스트 보여주기.
	// showProjectListByTeamIdx(int) : teamIdx 값을 받아서 팀리스트 리턴
	// 파라미터  : teamIdx
	// 리턴 : 파라미터 값에 해당 팀의 프로젝트 목록
	public ArrayList<DashboardProjectDto> showProjectListByTeamIdx (int teamIdx) throws Exception {
		ArrayList<DashboardProjectDto> listRet = new ArrayList<DashboardProjectDto>();
		Connection conn = getConnection();
		String sql = "SELECT project_idx, team_idx, project_name,"
					+ " color_idx, writer, registration_date"
					+ " FROM project WHERE team_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int projectIdx = rs.getInt("projectIdx");
			String projectName = rs.getString("projectName");
			String registrationDate = rs.getString("registration_date");
			int colorIdx = rs.getInt("colorIdx");
			int writer = rs.getInt("writer");
			DashboardProjectDto dto = new DashboardProjectDto(projectIdx, teamIdx, projectName, colorIdx, writer, registrationDate);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	// 위젯 추가  ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ 드래그업!!!!!!!★★★★★★★★★★★
	// addWidget(int) : memberIdx, widgetIdx 값을 받아서  대시보드에 위젯 추가
	// 파라미터  : memberIdx, widgetIdx
	public void addWidget(int memberIdx, int widgetIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "INSERT INTO widget_form(member_idx, w_order, widget_idx)"
					+ " VALUES(?, (SELECT MAX(w_order) + 1"
					+ " FROM widget_form WHERE member_idx = ?),?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, memberIdx);
		pstmt.setInt(3, widgetIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	// 위젯 삭제 
	// deleteWidget(int) : memberIdx, widgetIdx 값을 받아서  대시보드에 위젯 추가
	// 파라미터  : memberIdx, widgetIdx
	public void deleteWidget(int memberIdx, int widgetIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "DELETE FROM widget_form "
					+ "WHERE member_idx=? AND widget_idx = ?;";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setInt(2, widgetIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	// 메모 저장 
	// deleteWidget(int, String) : memberIdx, memo 값을 받아서  대시보드에 위젯 추가
	// 파라미터  : memberIdx, memo
	public void SaveMemo(int memberIdx, String memo) throws Exception{
		Connection conn = getConnection();
		String sql = "INSERT INTO WIDGET_MEMO(MEMO_IDX, MEMBER_IDX, MEMO)"
					+ " VALUES(SEQ_MEMO_IDX.nextval, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		pstmt.setString(2, memo);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	
}
















