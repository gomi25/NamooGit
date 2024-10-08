package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class Common {
	
	private Connection getConnection() throws Exception{
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo"; 
		String pw = "7777";	
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		return conn;
	}
	
	// 파일 외부공유용url생성 - 난수 발생 방법
	// 리턴: 외부공유용url
	public String generateNewUrl() {
		// 97-122
		// 48-57
		
		// -9 ~ +26 : -9~0 = 숫자 | 1~26 => (+96) = 소문자
		String strCode = "";
		for(int i=1; i<=20; i++) {
			int r = (int)(Math.random()*36)-9;   // -9 ~ + 26
			//System.out.print(r + " ");
			if(r<=0) {  // 숫자.
				strCode += (r * -1);
			} else {
				strCode += (char)(r + 96);
			}
		}
		//System.out.println("strCode : " + strCode);
		return strCode;
	} 
	
	/*
	 * // Common클래스. (common 패키지) public static void main(String[] args) { String s
	 * = generateNewUrl(); System.out.println("strCode : " + s);
	 * 
	 * }
	 */
	
	// 파일 업로드 하는 기능 
	// 파라미터: 등록할파일명, 파일용량, 작성자idx, 파일유형idx, 프로젝트idx, 채팅방idx, 토픽idx, 외부공유용url);
	// 리턴: 파일idx
	public int registerFile(String fileName, String volume, int memberIdx, int fileTypeIdx, Integer projectIdx, Integer chatroomIdx, Integer topicIdx, String url) throws Exception {
		Connection conn = getConnection();
		
		int fileIdx = 0;
		String sql = "INSERT INTO file_box (file_idx, file_name, save_date, volume, member_idx, file_type_idx, project_idx, chatroom_idx, topic_idx, url)" + 
				     " VALUES (seq_file.nextval, ?, sysdate, ?, ?, ?, ?, ?, ?, ?)";
		
		String[] arrStr = {"file_idx"};
		PreparedStatement pstmt = conn.prepareStatement(sql, arrStr);
		pstmt.setString(1, fileName);
		pstmt.setString(2, volume);
		pstmt.setInt(3, memberIdx);
		pstmt.setInt(4, fileTypeIdx);
		if(projectIdx==null) {
			pstmt.setNull(5, Types.INTEGER);
		} else {
			pstmt.setInt(5, projectIdx);
		}
		if(chatroomIdx==null) {
			pstmt.setNull(6, Types.INTEGER);
		} else {
			pstmt.setInt(6, chatroomIdx);
		}
		if(topicIdx==null) {
			pstmt.setNull(7, Types.INTEGER);
		} else {
			pstmt.setInt(7, topicIdx);
		}
		pstmt.setString(8, url);
		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()) {
			fileIdx = rs.getInt(1);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return fileIdx;
	}
	
	// 파일명의 확장자를 확인하여 파일유형에 따라 idx리턴
	// 파라미터: 파일명
	// 리턴: 파일타입idx
	public int getFileTypeIdxFromFileName(String fileName) {
		int fileTypeIdx = 1;
		if(fileName != null) {
			if(fileName.endsWith(".png") || 
					fileName.endsWith(".jpg") ||
					fileName.endsWith(".jpeg")) {
				fileTypeIdx = 2;   // img
			} else if(fileName.endsWith(".pdf")) {
				fileTypeIdx = 3;	// PDF
			} else if(fileName.endsWith(".doc")  ||
					  fileName.endsWith(".docm") || 
					  fileName.endsWith(".docx") ||
					  fileName.endsWith("dot")) {
				fileTypeIdx = 4;	// 워드
			} else if(fileName.endsWith(".pptx")) {
				fileTypeIdx = 5;	// 파워포인트
			} else if(fileName.endsWith(".hwp") ||
				      fileName.endsWith(".hwpx")) {
				fileTypeIdx = 6;	// 한글
			} else if(fileName.endsWith(".xlsx")    ||
					  fileName.endsWith(".xlsm")  ||
				      fileName.endsWith(".xlsb") ||
					  fileName.endsWith(".xls")) {
				fileTypeIdx = 7;	// 엑셀
			} else if(fileName.endsWith(".zip") ||
					  fileName.endsWith(".7z")  ||
				      fileName.endsWith(".alz") ||
					  fileName.endsWith(".apk")) {
				fileTypeIdx = 8;	// 압축 파일
			} else if(fileName.endsWith(".mp3") ||
					  fileName.endsWith(".mp4") ||
				      fileName.endsWith(".avi") ||
					  fileName.endsWith(".mov")) {
				fileTypeIdx = 9;	// 오디오, 비디오
			} else {
				fileTypeIdx = 1;
			}
		}
		return fileTypeIdx;
	}
	
	// fileTypeIdx에 따라 문자열 리턴
	// 파라미터: fileTypeIdx
	// 리턴: IconImg
	public String getIconImgFromFileTypeIdx(int fileTypeIdx) {
		String IconImg = "all";
		
		if(fileTypeIdx==3) {
			IconImg = "pdf";
		} else if(fileTypeIdx==4) {
			IconImg = "word";
		} else if(fileTypeIdx==5) {
			IconImg = "ppt";
		} else if(fileTypeIdx==6) {
			IconImg = "hwp";
		} else if(fileTypeIdx==7) {
			IconImg = "xls";
		} else if(fileTypeIdx==8) {
			IconImg = "zip";
		} else if(fileTypeIdx==9) {
			IconImg = "video";
		} else {
			IconImg = "all";
		} 
		
		return IconImg;
	}
	
	// Common클래스. (common 패키지)
	public static void main(String[] args) {
		Common c = new Common();
		String s = c.generateNewUrl();
		System.out.println("strCode : " + s);
		
	}
}
