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
	
	// dashboardChangeWidgetSizeSamll(int): 위젯크기 변경
	// 파라미터 : memberIdx, widgetIdx
	public void dashboardChangeWidgetSizeSmall(int widgetIdx, int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "UPDATE widget_form SET w_size = 1 "
	                   + "WHERE widget_idx = ? "
	                   + "AND member_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, widgetIdx);
	        pstmt.setInt(2, memberIdx);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close(); 
	            if (conn != null) conn.close(); 
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }
	    }
	}
	
	// dashboardChangeWidgetSizeBig(int): 위젯 사이즈 크게 변경
	// 파라미터: memberIdx, widgetIdx
	public void dashboardChangeWidgetSizeBig(int widgetIdx, int memberIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "UPDATE widget_form SET w_size = 2 "
	                   + "WHERE widget_idx = ? "
	                   + "AND member_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, widgetIdx);
	        pstmt.setInt(2, memberIdx);
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// showMainDashboardByMemberIdx(int): 메인 대시보드 표시
	// 파라미터  : memberIdx
	// 리턴 : 파라미터 값에 해당하는 멤버의 이름
	public ArrayList<DashboardMainDto> showMainDashboardByMemberIdx (int memberIdx) {
		ArrayList<DashboardMainDto> listRet = new ArrayList<DashboardMainDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT w_order, widget_idx, w_size "
	                   + "FROM widget_form "
	                   + "WHERE member_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int wOrder = rs.getInt("w_order");
	            int wSize = rs.getInt("w_size");
	            int widgetIdx = rs.getInt("widget_idx");
	            DashboardMainDto dto = new DashboardMainDto(wOrder, widgetIdx, wSize);
	            listRet.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return listRet;
	}
	
	// showProjectListByTeamIdx(int) : 프로젝트 창 프로젝트 리스트 보여주기.+ color
	// 파라미터  : teamIdx
	// 리턴 : p.project_idx, p.team_idx, p.project_name
	public ArrayList<DashboardProjectDto> showProjectListByTeamIdx (int teamIdx) {
		ArrayList<DashboardProjectDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT p.project_idx, p.team_idx, p.project_name, "
	                   + "p.color_idx, p.writer, p.registration_date, c.color "
	                   + "FROM project p LEFT JOIN color c "
	                   + "ON p.color_idx = c.color_idx "
	                   + "WHERE team_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int projectIdx = rs.getInt("project_idx");
	            String projectName = rs.getString("project_name");
	            String registrationDate = rs.getString("registration_date");
	            int colorIdx = rs.getInt("color_idx");
	            int writer = rs.getInt("writer");
	            String color = rs.getString("color");
	            DashboardProjectDto dto = new DashboardProjectDto(projectIdx, teamIdx, projectName, colorIdx, writer, registrationDate, color);
	            listRet.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return listRet;
	}
	
	// addWidget(int): 위젯 추가
	// 파라미터  : memberIdx, widgetIdx
	public void addWidget(int memberIdx, int widgetIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "INSERT INTO widget_form(member_idx, w_order, widget_idx) "
	                   + "VALUES(?, (SELECT COALESCE(MAX(w_order), 0) + 1 "
	                   + "FROM widget_form WHERE member_idx = ?), ?)";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setInt(2, memberIdx);
	        pstmt.setInt(3, widgetIdx);
	        pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// deleteWidget(int): 위젯 삭제
	// 파라미터  : memberIdx, widgetIdx
	public void deleteWidget(int memberIdx, int widgetIdx) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "DELETE FROM widget_form "
	                   + "WHERE member_idx = ? AND widget_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setInt(2, widgetIdx);
	        pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// addMemoWidget(int, String): 메모위젯 추가
	// 파라미터  : memberIdx, memo
	public int addMemoWidget(int memberIdx, String memo) {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int ret = 0;

	    try {
	        String[] arr = {"memo_idx"};
	        String sql = "INSERT INTO widget_memo(memo_idx, member_idx, memo) "
	                   + "VALUES(seq_memo_idx.nextval, ?, ?)";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql, arr);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setString(2, memo);
	        pstmt.executeUpdate();
	        
	        rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	            ret = rs.getInt(1);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return ret;
	}
	// saveMemo(int, String) : 메모 저장 
	// 파라미터  : memberIdx, memo
	public void saveMemo(int memberIdx, String memo) {
		Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        String sql = "INSERT INTO WIDGET_MEMO(MEMO_IDX, MEMBER_IDX, MEMO) "
	                   + "VALUES(SEQ_MEMO_IDX.nextval, ?, ?)";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        pstmt.setString(2, memo);
	        pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	// showMemoBymemberIdx(int): 메모 보여주기
	// 파라미터  : memberIdx
	// 리턴 : memo
	public ArrayList<DashboardMemoDto> showMemoBymemberIdx (int memberIdx) {
		ArrayList<DashboardMemoDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT memo_idx, widget_idx, member_idx, memo "
	                   + "FROM widget_memo WHERE member_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            int memoIdx = rs.getInt("memo_idx");
	            int widgetIdx = rs.getInt("widget_idx");
	            String memo = rs.getString("memo");
	            DashboardMemoDto dto = new DashboardMemoDto(memoIdx, widgetIdx, memberIdx, memo);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return listRet;
	}
	
	// showProjectColorByColorIdx(int): 프로젝트의 컬러 리턴
	// 파라미터  : projectIdx
	// 리턴 : color
	public String showProjectColorByColorIdx (int projectIdx) {
		String result = "";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT c.color "
	                   + "FROM COLOR c LEFT JOIN project p ON c.color_idx = p.color_idx "
	                   + "WHERE p.project_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, projectIdx);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            result = rs.getString("color");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return result;
	}
	
	// showFileListByProjectIdx(int) : 선택한 프로젝트 내의 파일을....▶▶▶▶ chatroom과 topic으로 
	// 파라미터  : projectIdx
	// 리턴 : 파일 목록 리턴
	public ArrayList<DashboardFileBoxDto> showFileListByProjectIdx (int projectIdx) {
		ArrayList<DashboardFileBoxDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT b.file_name, TO_CHAR(b.save_date, 'YYYY-MM-DD HH24:MI') AS save_date, "
	                   + "b.volume, b.file_type_idx, b.project_idx, f.image "
	                   + "FROM file_type f LEFT JOIN file_box b "
	                   + "ON f.file_type_idx = b.file_type_idx "
	                   + "WHERE b.project_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, projectIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            String fileName = rs.getString("file_name");
	            String saveDate = rs.getString("save_date");
	            String volume = rs.getString("volume");
	            String image = rs.getString("image");
	            int fileTypeIdx = rs.getInt("file_type_idx");
	            int projIdx = rs.getInt("project_idx");
	            DashboardFileBoxDto dto = new DashboardFileBoxDto(fileName, saveDate, volume, fileTypeIdx, projIdx, image);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return listRet;
	}
	
	// showChatroomAndTopicNameByTeamIdx(int) :  팝업에 채팅방과 토픽의 이름 띄우기
	// 파라미터 : temaIdx
	// 리턴: 채팅방과 토픽 이름 리턴
	public ArrayList<DashboardChatroomAndTopicNameDto> showChatroomAndTopicNameByTeamIdx (int teamIdx) {
		ArrayList<DashboardChatroomAndTopicNameDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT c.name FROM chatroom c "
	                   + "WHERE c.team_idx = ? AND c.name IS NOT NULL "
	                   + "UNION "
	                   + "SELECT t.name FROM topic t "
	                   + "WHERE t.team_idx = ? AND t.name IS NOT NULL";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        pstmt.setInt(2, teamIdx);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            String name = rs.getString("name");
	            DashboardChatroomAndTopicNameDto dto = new DashboardChatroomAndTopicNameDto(name, teamIdx);
	            listRet.add(dto);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return listRet;
	}
}
















