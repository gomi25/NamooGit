package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.ProfileUrlImgDto;

public class ProfileDao {
	
	private Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";	
		String pw = "7777";	
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}
	
	public String getMyProfileImgUrl(ProfileUrlImgDto imgUrlList, int memberIdx) throws Exception {
		String imgUrl = "";
		if (imgUrlList.getMemberIdx() == memberIdx) {
			if (imgUrlList.getProfilePicUrl1() == null) {
				Connection conn = getConnection();
				
				String sql = "SELECT pi.img_url FROM member m INNER JOIN profile_img pi ON m.profile_img_idx = pi.profile_img_idx "
						+ "WHERE m.member_idx = ? ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, memberIdx);
				
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					imgUrl = rs.getString(1);
				}
				
				rs.close();
				pstmt.close();
				conn.close();
			} else {
				imgUrl = imgUrlList.getProfilePicUrl1();
			}
		}

		return imgUrl;
	}
	
}
