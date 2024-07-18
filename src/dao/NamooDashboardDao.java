package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	// 위젯 사이즈 조절 ? - > DB에 넣어야하니까 필요.??
	// dashboardChangeWidgetSize(int) : member_idx, widgetIdx, wSize 값을 받아서  프로필 사진을 리턴
	// 파라미터 memberIdx : memberIdx, widgetIdx, wSize
	// 리턴 : 파라미터 값에 해당하는 멤버의 프사
	public void dashboardChangeWidgetSize(int wSize, int widgetIdx, int memberIdx) throws Exception{
		Connection conn  = getConnection();
		String sql = "UPDATE widget_form SET w_size = ?" +
					" WHERE widget_idx = ?" + 
					" AND member_idx = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, wSize);
		pstmt.setInt(2, widgetIdx);
		pstmt.setInt(3, memberIdx);
		pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
	}
	// 이름 표시 > NamooMemberDao: 12번 이름 select문
	
	
	
	
	
	
	
	
}
