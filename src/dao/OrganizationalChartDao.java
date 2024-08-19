package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.GanttkCommentLikeDto;
import dto.MemberProfileDto;
import dto.OrganizationalBookmarkMemberListDto;
import dto.OrganizationalMemberListDto;

public class OrganizationalChartDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
	
	// checkOrganizationalMemberCount : 멤버수
	// 파라미터: team_idx
	// 리턴: 팀멤버수
	public int checkOrganizationalMemberCount(int teamIdx) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = -1;

	    try {
	        String sql = "SELECT COUNT(*) FROM team_member tm WHERE tm.team_idx = ?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        
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
	
	// getOrganizationalMemberList : 멤버에서 멤버 리스트
	// 파라미터: team_idx, member_idx_from
	// 리턴: member_idx, profile_pic_url, member_name, team_name, position, state, state_message 
	public ArrayList<OrganizationalMemberListDto> getOrganizationalMemberList(int teamIdx, int memberIdxFrom) throws Exception {
	    ArrayList<OrganizationalMemberListDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT m.member_idx, m.profile_pic_url, m.name AS member_name, t.name AS team_name, tm.position, tm.state, tm.state_message,"
	                   + " CASE WHEN b.member_idx_from IS NOT NULL THEN 'Y'"
	                   + " ELSE 'N'" 
	                   + " END AS bookmark_member"
	                   + " FROM member m" 
	                   + " LEFT JOIN team_member tm ON m.member_idx = tm.member_idx" 
	                   + " LEFT JOIN team t ON tm.team_idx = t.team_idx"
	                   + " LEFT JOIN bookmark b ON m.member_idx = b.member_idx_to AND b.member_idx_from = ?"
	                   + " WHERE tm.team_idx = ?";
	        
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, memberIdxFrom);
	        pstmt.setInt(2, teamIdx);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String profilePicUrl = rs.getString("profile_pic_url");
	            String memberName = rs.getString("member_name");
	            String teamName = rs.getString("team_name");
	            String position = rs.getString("position");
	            String state = rs.getString("state");
	            String stateMessage = rs.getString("state_message");
	            int memberIdx = rs.getInt("member_idx");
	            String bookmarkMember = rs.getString("bookmark_member");

	            OrganizationalMemberListDto dto = new OrganizationalMemberListDto(
	                profilePicUrl, memberName, teamName, position, state, stateMessage, 
	                teamIdx, memberIdx, bookmarkMember, memberIdxFrom
	            );
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
	
	// checkOrganizationalTeamName: 팀이름
	// 파라미터: team_idx
	// 리턴: name
	public String checkOrganizationalTeamName(int teamIdx) throws Exception {
	    String sql = "SELECT t.name FROM team t WHERE t.team_idx = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String result = "";

	    try {
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            result = rs.getString(1);
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
	
	// getOrganizationalBookmarkMemberList: 즐겨찾기된 멤버
	// 파라미터: team_idx, member_idx_from
	// 리턴: member_idx, profile_pic_url, member_name, team_name, position, state, state_message
	public ArrayList<OrganizationalBookmarkMemberListDto> getOrganizationalBookmarkMemberList(int teamIdx, int memberIdxFrom) throws Exception {
	    ArrayList<OrganizationalBookmarkMemberListDto> listRet = new ArrayList<>();
	    String sql = "SELECT m.member_idx, m.profile_pic_url, m.name AS member_name, t.name AS team_name, tm.position, tm.state, tm.state_message"
	               + " FROM member m"
	               + " LEFT JOIN team_member tm ON m.member_idx = tm.member_idx"
	               + " LEFT JOIN team t ON tm.team_idx = t.team_idx"
	               + " INNER JOIN bookmark b ON b.member_idx_to = m.member_idx"
	               + " WHERE tm.team_idx = ?"
	               + " AND b.member_idx_from = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        pstmt.setInt(2, memberIdxFrom);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String profilePicUrl = rs.getString("profile_pic_url");
	            String memberName = rs.getString("member_name");
	            String teamName = rs.getString("team_name");
	            String position = rs.getString("position");
	            String state = rs.getString("state");
	            String stateMessage = rs.getString("state_message");
	            int memberIdx = rs.getInt("member_idx");

	            OrganizationalBookmarkMemberListDto dto = new OrganizationalBookmarkMemberListDto(
	                profilePicUrl, memberName, teamName, position, state, stateMessage, teamIdx, memberIdx, memberIdxFrom
	            );
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
	
	// checkOrganizationalBookmarkMemberCount
	// 파라미터: team_idx
	// 리턴: 즐겨찾기된 멤버수
	public int checkOrganizationalBookmarkMemberCount(int teamIdx) throws Exception {
	    String sql = "SELECT COUNT(*) "
	               + " FROM team_member tm" 
	               + " INNER JOIN bookmark b ON tm.member_idx = b.member_idx_to"
	               + " WHERE tm.team_idx = ?";
	    
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int result = -1;

	    try {
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);

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
	
	
	// getMemberProfile: 상세 프로필
	// 파라미터: team_idx
	// 리턴: member_idx, member_name, team_name, power, state, state_message, position, birth, phone_number, email, profile_pic_url
	public ArrayList<MemberProfileDto> getMemberProfile(int teamIdx) throws Exception {
	    ArrayList<MemberProfileDto> listRet = new ArrayList<>();
	    String sql = "SELECT m.member_idx, m.name AS member_name, t.name AS team_name, tm.power, tm.state, tm.state_message, tm.position, m.birth, m.phone_number, m.email, m.profile_pic_url"
	               + " FROM member m"
	               + " LEFT JOIN team_member tm ON m.member_idx = tm.member_idx"
	               + " LEFT JOIN team t ON tm.team_idx = t.team_idx"
	               + " WHERE tm.team_idx = ?";
	    
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, teamIdx);
	        
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int memberIdx = rs.getInt("member_idx");
	            String profilePicUrl = rs.getString("profile_pic_url");
	            String memberName = rs.getString("member_name");
	            String teamName = rs.getString("team_name");
	            String position = rs.getString("position");
	            String state = rs.getString("state");
	            String stateMessage = rs.getString("state_message");
	            String power = rs.getString("power");
	            String birth = rs.getString("birth");
	            String phoneNumber = rs.getString("phone_number");
	            String email = rs.getString("email");
	        
	            MemberProfileDto dto = new MemberProfileDto(memberIdx, profilePicUrl, memberName, teamName, position, state, stateMessage, power, birth, phoneNumber, email);
	            listRet.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	       // throw e; 
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
	
	// updateLoginMemberState: 로그인한 멤버 상태 변경
	// 파라미터: member_idx
	public void updateLoginMemberState(String state, int memberIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE team_member SET state = ? WHERE member_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, state);
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
	
	// updateLoginMemberStateTxt: 로그인한 멤버 상태메세지 변경
	// 파라미터: state_txt, member_idx
	public void updateLoginMemberStateTxt(String stateTxt, int memberIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE team_member SET state_txt = ? WHERE member_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, stateTxt);
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
	
	// updateLoginMemberPosition: 로그인한 멤버 직책 변경
	// 파라미터: position, member_idx
	public void updateLoginMemberPosition(String position, int memberIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE team_member SET position = ? WHERE member_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, position);
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
	
	// updateLoginMemberPhoneNumber: 로그인한 멤버 핸드폰 번호 변경
	// 파라미터: phone_number, member_idx
	public void updateLoginMemberPhoneNumber(String phoneNumber, int memberIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE team_member SET phone_number = ? WHERE member_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, phoneNumber);
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
	
	// updateLoginMemberBirth: 로그인한 멤버 생년월일 변경
	// 파라미터: birth, member_idx
	public void updateLoginMemberBirth(String birth, int memberIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        String sql = "UPDATE team_member SET birth = ? WHERE member_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, birth);
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

}
