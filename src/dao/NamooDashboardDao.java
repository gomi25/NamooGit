package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.DashboardChatroomAndTopicNameDto;
import dto.DashboardFileBoxDto;
import dto.DashboardMainDto;
import dto.DashboardMemoDto;
import dto.DashboardProjectDto;
import dto.HelpPostDto;

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
	// 메인 대시보드 표시
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
			int wOrder = rs.getInt("w_order");
			int wSize = rs.getInt("w_size");
			int widgetIdx = rs.getInt("widget_idx");
			DashboardMainDto dto = new DashboardMainDto(wOrder, widgetIdx, wSize);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	
	// 프로젝트 창 프로젝트 리스트 보여주기.+ color
	// showProjectListByTeamIdx(int) : teamIdx 값을 받아서 팀리스트 리턴
	// 파라미터  : teamIdx
	// 리턴 : 파라미터 값에 해당 팀의 프로젝트 목록
	public ArrayList<DashboardProjectDto> showProjectListByTeamIdx (int teamIdx) throws Exception {
		ArrayList<DashboardProjectDto> listRet = new ArrayList<DashboardProjectDto>();
		Connection conn = getConnection();
		String sql = "SELECT p.project_idx, p.team_idx, p.project_name,"
					+ " p.color_idx, p.writer, p.registration_date, c.color"
					+ " FROM project p LEFT JOIN color c"
					+ " ON p.color_idx = c.color_idx"
					+ " WHERE team_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int projectIdx = rs.getInt("project_idx");
			String projectName = rs.getString("project_name");
			String registrationDate = rs.getString("registration_date");
			int colorIdx = rs.getInt("color_idx");
			int writer = rs.getInt("writer");
			String color = rs.getString("color");
			DashboardProjectDto dto = new DashboardProjectDto(projectIdx, teamIdx, projectName, colorIdx, writer, registrationDate, color);
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
	// 메모 보여주기
	// showMemoBymemberIdx(int) : memberIdx 값을 받아서 메모값 리턴
	// 파라미터  : memberIdx
	// 리턴 : 해당 멤버의 메모
	public ArrayList<DashboardMemoDto> showMemoBymemberIdx (int memberIdx) throws Exception {
		ArrayList<DashboardMemoDto> listRet = new ArrayList<DashboardMemoDto>();
		Connection conn = getConnection();
		String sql = "SELECT memo_idx, widget_idx, member_idx, memo"
					+ " FROM widget_memo WHERE member_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memberIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int memoIdx = rs.getInt("memo_idx");
			int widgetIdx = rs.getInt("widget_idx");
			String memo = rs.getString("memo");
			DashboardMemoDto dto = new DashboardMemoDto(memoIdx, widgetIdx, memberIdx, memo);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	// 프로젝트 색상 보여주기
	// showProjectColorByColorIdx(int) : projectIdx 값을 받아서 해당 프로젝트의 컬러 리턴
	// 파라미터  : projectIdx
	// 리턴 : 해당 프로젝트의 컬러
	public String showProjectColorByColorIdx (int projectIdx) throws Exception{
		Connection conn = getConnection();
		String sql = "SELECT c.color_idx, c.color"
					+ " FROM COLOR c LEFT JOIN project p ON c.color_idx = p.color_idx"
					+ " WHERE p.project_idx= ?";
		 PreparedStatement pstmt = conn.prepareStatement(sql);
		 pstmt.setInt(1, projectIdx);
		 ResultSet rs = pstmt.executeQuery();
		 String result = "";
		 if(rs.next()){
			 result = rs.getString(1);
		 }
		 rs.close();
		 pstmt.close();
		 conn.close();
		 return result;
	}
	// 선택한 프로젝트 내의 파일을....▶▶▶▶ chatroom과 topic으로 
	// showFileListByProjectIdx(int) : projectIdx 값을 받아서  파일 목록 리턴
	// 파라미터  : projectIdx
	// 리턴 : 파일 목록 리턴
	public ArrayList<DashboardFileBoxDto> showFileListByProjectIdx (int projectIdx) throws Exception {
		ArrayList<DashboardFileBoxDto> listRet = new ArrayList<DashboardFileBoxDto>();
		Connection conn = getConnection();
		String sql ="SELECT b.file_name, TO_CHAR(b.save_date, 'YYYY-MM-DD HH24:MI') save_date,"
				+ " b.volume, b.file_type_idx, project_idx, f.image"
				+ " FROM file_type f LEFT JOIN file_box b"
				+ " ON f.file_type_idx = b.file_type_idx"
				+ " WHERE project_idx = 1";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, projectIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String fileName = rs.getString("file_name");
			String saveDate = rs.getString("save_date");
			String volume = rs.getString("volume");
			String image = rs.getString("image");
			int fileTypeIdx = rs.getInt("file_type_idx");
			DashboardFileBoxDto dto = new DashboardFileBoxDto(fileName, saveDate, volume, fileTypeIdx, projectIdx, image);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	// 팝업에 채팅방과 토픽의 이름 띄우기
	// showChatroomAndTopicNameByTeamIdx(int) : temaIdx 값을 받아서 이름 리턴
	// 파라미터  : temaIdx
	// 리턴 : 채팅방과 토픽 이름 리턴
	public ArrayList<DashboardChatroomAndTopicNameDto> showChatroomAndTopicNameByTeamIdx (int teamIdx) throws Exception {
		ArrayList<DashboardChatroomAndTopicNameDto> listRet = new ArrayList<DashboardChatroomAndTopicNameDto>();
		Connection conn = getConnection();
		String sql = "SELECT c.name FROM chatroom c"
					+ " WHERE c.team_idx = ? AND c.name IS NOT NULL"
					+ " UNION SELECT t.name FROM topic t"
					+ " WHERE t.team_idx =? AND t.name IS NOT NULL";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teamIdx);
		pstmt.setInt(2, teamIdx);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			String name = rs.getString("name");
			DashboardChatroomAndTopicNameDto dto = new DashboardChatroomAndTopicNameDto(name, teamIdx);
			listRet.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return listRet;
	}
	
}
















