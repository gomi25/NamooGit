package dao;

import java.sql.*;
import java.util.ArrayList;

import dto.ConsumerCaseDto;

public class ConsumerCaseDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}
	
	public static void main(String[] args) throws Exception {
		ConsumerCaseDao dao = new ConsumerCaseDao();
		ArrayList<ConsumerCaseDto> list = null;
		
//		======================== Test =========================
		
//		System.out.println("--------- 전체 고객 사례 조회 ----------");
//		try {
//			list = dao.getListConsumerCaseDto();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		for (ConsumerCaseDto ConsumerCaseDto : list) {
//			System.out.println(ConsumerCaseDto);
//		}
		
//		System.out.println("---------산업군별 고객 사례 조회 ----------");
//		try {
//			list = dao.getListConsumerCaseDtoByIndustry(1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		for (ConsumerCaseDto ConsumerCaseDto : list) {
//			System.out.println(ConsumerCaseDto);
//		}
		
//		System.out.println("--------- 규모별 고객 사례 조회 ----------");
//		try {
//		list = dao.getListConsumerCaseDtoByPersonCnt(1, 2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		for (ConsumerCaseDto ConsumerCaseDto : list) {
//			System.out.println(ConsumerCaseDto);
//		}
		
//		System.out.println("--------- 자세히 보기(내용) ----------");
//		try {
//			list = dao.getConsumerCaseDtoByCaseIdx(1);
//		} catch (Exception e) {
//				e.printStackTrace();
//		} 
//		for (ConsumerCaseDto ConsumerCaseDto : list) {
//				System.out.println(ConsumerCaseDto);
//			}
		
//		System.out.println("--------- 자세히 보기(오른쪽 박스) ----------");
//		try {
//			list = dao.getListConsumerCaseRightBox(1);
//		} catch (Exception e) {
//				e.printStackTrace();
//		} 
//		for (ConsumerCaseDto ConsumerCaseDto : list) {
//				System.out.println(ConsumerCaseDto);
//			}
	}
	
	// getListConsumerCaseDto() : 전체 고객 사례를 조회
	// 리턴 값 : 전체 고객 사례 리스트 (ArrayList<ConsumerCaseDto>)
	public ArrayList<ConsumerCaseDto> getListConsumerCaseDto() throws Exception {
		ArrayList<ConsumerCaseDto> list = new ArrayList<>();
		Connection conn = getConnection();
		
		String sql = "SELECT c.case_idx, c.company_img_url, c.logo_img_url, c.title, c.subtitle, c.main_title"
					+ " FROM consumer_case c" 
					+ " ORDER BY c.case_idx";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		try {
			while(rs.next()) {
				 int caseIdx = rs.getInt(1);
	                String companyImgUrl = rs.getString(2);
	                String logoImgUrl = rs.getString(3);
	                String title = rs.getString(4);
	                String subTitle = rs.getString(5);
	                String mainTitle = rs.getString(6);
	                
	                String caseTxt = "";
	                String threeTxt = "";
	                String threeUrl = "";
	                int industry = 0;
	                int personCnt = 0;
	                String threeTxtPart1 = "";
	                String threeTxtPart2 = "";
	                String threeTxtPart3 = "";
				
				ConsumerCaseDto dto = new ConsumerCaseDto(caseIdx, companyImgUrl, logoImgUrl, industry, personCnt, threeTxt, threeUrl, mainTitle, mainTitle, subTitle, caseTxt, threeTxtPart1, threeTxtPart2, threeTxtPart3);
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return list;
	}
	
	// getListConsumerCaseDtoByIndustry(int industry) : 산업군별 고객 사례를 조회
	// industry : 산업군의 인덱스 (int)
	// 리턴 값 : 해당 산업군의 고객 사례 리스트 (ArrayList<ConsumerCaseDto>)
	public ArrayList<ConsumerCaseDto> getListConsumerCaseDtoByIndustry(int industry) throws Exception {
		ArrayList<ConsumerCaseDto> list = new ArrayList<>();
		Connection conn = getConnection();
		
		String sql = "SELECT c.case_idx, c.company_img_url, c.logo_img_url, c.title, c.subtitle, c.main_title"
					+ " FROM consumer_case c " 
					+ " WHERE industry = ? "
					+ " ORDER BY c.case_idx";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, industry);
		
		ResultSet rs = pstmt.executeQuery();
		
		try {
			while(rs.next()) {
				int caseIdx = rs.getInt(1);
                String companyImgUrl = rs.getString(2);
                String logoImgUrl = rs.getString(3);
                String title = rs.getString(4);
                String subTitle = rs.getString(5);
                String mainTitle = rs.getString(6);
                
                // 기본 값 설정
                String caseTxt = "";
                String threeTxt = "";
                String threeUrl = "";
                int personCnt = 0;
                String threeTxtPart1 = "";
                String threeTxtPart2 = "";
                String threeTxtPart3 = "";
				
				ConsumerCaseDto dto = new ConsumerCaseDto(caseIdx, companyImgUrl, logoImgUrl, industry, personCnt, threeTxt, threeUrl, mainTitle, mainTitle, subTitle, caseTxt, threeTxtPart1, threeTxtPart2, threeTxtPart3);
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return list;
	}
	
	// 사용하지 않음
	// getListConsumerCaseDtoByPersonCnt(int personCnt, int industry) : 사원 수와 산업군별로 고객 사례를 조회
	// personCnt : 사원 수 (int)
	// industry : 산업군의 인덱스 (int)
	// 리턴 값 : 해당 조건에 맞는 고객 사례 리스트 (ArrayList<ConsumerCaseDto>)
	public ArrayList<ConsumerCaseDto> getListConsumerCaseDtoByPersonCnt(int personCnt, int industry) throws Exception {
		ArrayList<ConsumerCaseDto> list = new ArrayList<>();
		Connection conn = getConnection();
		
		String sql = "SELECT c.case_idx, c.company_img_url, c.logo_img_url, c.title, c.subtitle, c.main_title"
					+ " FROM consumer_case c " 
					+ " WHERE person_cnt = ?" 
					+ " AND industry = ?"
					+ " ORDER BY c.case_idx";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, personCnt);
		pstmt.setInt(2, industry);
		
		ResultSet rs = pstmt.executeQuery();
		
		try {
			while(rs.next()) {
				int caseIdx = rs.getInt(1);
                String companyImgUrl = rs.getString(2);
                String logoImgUrl = rs.getString(3);
                String title = rs.getString(4);
                String subTitle = rs.getString(5);
                String mainTitle = rs.getString(6);
                
                String caseTxt = "";
                String threeTxt = "";
                String threeUrl = "";
                String threeTxtPart1 = "";
                String threeTxtPart2 = "";
                String threeTxtPart3 = "";
				
				ConsumerCaseDto dto = new ConsumerCaseDto(caseIdx, companyImgUrl, logoImgUrl, industry, personCnt, threeTxt, threeUrl, mainTitle, mainTitle, subTitle, caseTxt, threeTxtPart1, threeTxtPart2, threeTxtPart3);
				list.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return list;
	}
	
	// getConsumerCaseDtoByCaseIdx(int caseIdx) : 자세히 보기(내용)
	// caseIdx : 조회할 사례의 인덱스 (int)
	// 리턴 값 : 해당 사례의 상세 정보 (ConsumerCaseDto)
	 public ConsumerCaseDto getConsumerCaseDtoByCaseIdx(int caseIdx) throws Exception {
	        ConsumerCaseDto dto = null;
	        Connection conn = getConnection();
	        
	        String sql = "SELECT c.company_img_url 회사이미지, c.logo_img_url 로고이미지, c.title 제목, c.subtitle 부제목, c.case_txt 내용, c.three_txt"
	                   + " FROM consumer_case c"
	                   + " WHERE c.case_idx = ?";
	        
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, caseIdx);
	        ResultSet rs = pstmt.executeQuery();
	        
	        try {
	            if (rs.next()) {
	                String companyImgUrl = rs.getString("회사이미지");
	                String logoImgUrl = rs.getString("로고이미지");
	                String title = rs.getString("제목");
	                String subTitle = rs.getString("부제목");
	                String caseTxt = rs.getString("내용");
	                String threeTxt = rs.getString("three_txt");
	                String threeUrl = "";
	                String mainTitle = "";
	                int industry = 0;
	                int personCnt = 0;

	                dto = new ConsumerCaseDto();
	                dto.setCaseIdx(caseIdx);
	                dto.setCompanyImgUrl(companyImgUrl);
	                dto.setLogoImgUrl(logoImgUrl);
	                dto.setTitle(title);
	                dto.setSubTitle(subTitle);
	                dto.setCaseTxt(caseTxt);
	                dto.setThreeTxt(threeTxt);
	                dto.setThreeUrl(threeUrl);
	                dto.setMainTitle(mainTitle);
	                dto.setIndustry(industry);
	                dto.setPersonCnt(personCnt);

	                // threeTxt를 줄바꿈으로 분할
	                String[] threeTxtParts = threeTxt.split("\\r?\\n");
	                if (threeTxtParts.length > 0) dto.setThreeTxtPart1(threeTxtParts[0]);
	                if (threeTxtParts.length > 1) dto.setThreeTxtPart2(threeTxtParts[1]);
	                if (threeTxtParts.length > 2) dto.setThreeTxtPart3(threeTxtParts[2]);
	            }
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            rs.close();
	            pstmt.close();
	            conn.close();
	        }
	        return dto;
	    }
	
	// getListConsumerCaseRightBox(int caseIdx) : 특정 고객 사례의 오른쪽 박스 정보를 조회
	// caseIdx : 조회할 사례의 인덱스 (int)
	// 리턴 값 : 오른쪽 박스 정보 리스트 (ArrayList<ConsumerCaseDto>)
	public ArrayList<ConsumerCaseDto> getListConsumerCaseRightBox(int caseIdx) throws Exception {
		ArrayList<ConsumerCaseDto> list = new ArrayList<>();
		Connection conn = getConnection();
		
		String sql = "SELECT c.logo_img_url 로고이미지, c.case_txt 내용, c.person_cnt 인원수, c.industry 산업군, c.three_txt 세줄소개, c.three_url 세줄링크, c.main_title 메인타이틀"
				+ " FROM consumer_case c"
				+ " WHERE c.case_idx = ?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, caseIdx);
		
		ResultSet rs = pstmt.executeQuery();
		
		try {
			while(rs.next()) {
			  	String companyImgUrl = "";
                String logoImgUrl = rs.getString("logo_img_url");
                String title = "";
                String subTitle = "";
                String caseTxt = rs.getString("case_txt");
                String threeTxt = rs.getString("three_txt");
                String threeUrl = rs.getString("three_url");
                String mainTitle = rs.getString("main_title");
                int industry = rs.getInt("industry");
                int personCnt = rs.getInt("person_cnt");

                // threeTxt를 줄바꿈으로 분할
                String threeTxtPart1 = "";
                String threeTxtPart2 = "";
                String threeTxtPart3 = "";
                String[] threeTxtParts = threeTxt.split("\\r?\\n");
                if (threeTxtParts.length > 0) threeTxtPart1 = threeTxtParts[0];
                if (threeTxtParts.length > 1) threeTxtPart2 = threeTxtParts[1];
                if (threeTxtParts.length > 2) threeTxtPart3 = threeTxtParts[2];
				
				ConsumerCaseDto dto = new ConsumerCaseDto(caseIdx, companyImgUrl, logoImgUrl, industry, personCnt, threeTxt, threeUrl, mainTitle, title, subTitle, caseTxt, threeTxtPart1, threeTxtPart2, threeTxtPart3);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		return list;
	}
}