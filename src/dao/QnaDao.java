package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import dto.QnaAllQuestionDto;
import dto.QnaAnswerDto;
import dto.QnaDto;

public class QnaDao {
	private Connection getConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "namoo";
		String pw = "7777";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}

	
	
	
//-----------------------DELETE----------------------------------------
	// deleteQnaQuestion : qna 질문 삭제
	// input : qna_idx
	public void deleteQnaQuestion(int qnaIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM qna WHERE qna_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, qnaIdx);
		pstmt.executeUpdate();
  
		pstmt.close();
		conn.close();
	}	
	
	
	// deleteQnaAnswer : qna 답변 삭제
	public void deleteQnaAnswer(int answerIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "DELETE FROM qna_answer WHERE answer_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, answerIdx);
		pstmt.executeUpdate();
  
		pstmt.close();
		conn.close();
	}
	
	
	
	
//-----------------------UPDATE----------------------------------------
	// updateQnaContent : qna의 content 변경
	// input : content[내용], qna_idx
	public void updateQnaContent(String content, int qnaIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE qna"  
               + " SET content = ?" 
               + " WHERE qna_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setInt(2, qnaIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   } 
	
	// updateQnaAnswer : qna 답변 변경
	// input : content[내용], answer_idx
	public void updateQnaAnswer(String content, int qnaIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE qna_answer"  
               + " SET content = ?" 
               + " WHERE qna_idx  = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setInt(2, qnaIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   } 
	
	
	// 답변상태 변경
	public void updateQnaAnswerCondition(int qnaIdx) throws Exception { 
		Connection conn = getConnection();
		String sql = "UPDATE qna"  
               + " SET reply_condition = 1" 
               + " WHERE qna_idx = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, qnaIdx);
		pstmt.executeUpdate();
      
		pstmt.close();
		conn.close();
   } 
	
	
	
	
//-----------------------INSERT----------------------------------------
	

	
	

	
	//qna
	public void addQna3(String name, String phone_number, String position, String email, int industry, int count, int question, int agreement, int reply_condition, String content) throws Exception {
	    Connection conn = getConnection();
	    String sql = "INSERT INTO qna(qna_idx, name, phone_number, position, email, industry, count, question, agreement, question_date, reply_condition, content)"
	            + " VALUES(seq_qna.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?)";
	       
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, name);  
	    pstmt.setString(2, phone_number);  
	    pstmt.setString(3, position);  
	    pstmt.setString(4, email);  
	    pstmt.setInt(5, industry);  
	    pstmt.setInt(6, count);  
	    pstmt.setInt(7, question);  
	    pstmt.setInt(8, agreement);  
	    pstmt.setInt(9, reply_condition);  
	    pstmt.setString(10, content);  


	    pstmt.executeUpdate();
	       
	    pstmt.close();
	    conn.close();
	}
	

	
	// qna 질문들 답변
	public void addQnaAnswer(int qna_idx, String content) throws Exception {
	    Connection conn = getConnection();
	    String sql = "INSERT INTO qna_answer(ANSWER_IDX, QnA_IDX, CONTENT, ANSWER_DATE) VALUES(seq_qna_answer.nextval, ?, ?, sysdate)";
	       
	    PreparedStatement pstmt = conn.prepareStatement(sql);
 
	    pstmt.setInt(1, qna_idx);  
	    pstmt.setString(2, content);  
 

	    pstmt.executeUpdate();
	       
	    pstmt.close();
	    conn.close();
	    
	}
	
	




	
//--------------------------SELECT------------------------------------------
	// getQnaAnswer : qna답변
	// input : qna_idx 
	// output : 답변 내용, 답변 날짜
	 public QnaAnswerDto getQnaAnswer(int qnaIdx) throws Exception {
		 	QnaAnswerDto dtoRet = null;
		 	String sql = "SELECT qa.content, qa.answer_date" 
		 			+ " FROM qna_answer qa" 
		 			+ " WHERE qna_idx =?"; 
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	pstmt.setInt(1, qnaIdx);
		 	ResultSet rs = pstmt.executeQuery();

		 	if(rs.next()) {
		 		String content = rs.getString("content");
		 		String answer_date = rs.getString("answer_date");
		 		dtoRet = new QnaAnswerDto(content, answer_date);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	
		 	return dtoRet;
		 	
		 	
	 }
	 
	 // 질문들 
	 public ArrayList<QnaAllQuestionDto> getQnaAllQuestion() throws Exception {
		 	ArrayList<QnaAllQuestionDto> listRet = new ArrayList<QnaAllQuestionDto>();
		 	String sql = "SELECT q.qna_idx, q.reply_condition, q.content, q.name, q.question_date" 
		 			+ " FROM qna q"
		 			+ " ORDER BY q.qna_idx DESC"; 
//		 			+ " WHERE qna_idx =?"; 
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
		 	ResultSet rs = pstmt.executeQuery();
		 	while(rs.next()) {
		 		int qnaIdx = rs.getInt("qna_idx");
		 		int replyCondition = rs.getInt("reply_condition");
		 		String content = rs.getString("content");
		 		String name = rs.getString("name");
		 		String questionDate = rs.getString("question_date");
		 		int open = rs.getInt("open");
		 	
		 		QnaAllQuestionDto dto = new QnaAllQuestionDto(qnaIdx, replyCondition, content, name, questionDate);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();
		 	return listRet;
	 }

	 
	// 질문들 paging 
	 public ArrayList<QnaAllQuestionDto> getQnaQuestionPage(int pageNum) throws Exception {
		 	ArrayList<QnaAllQuestionDto> listRet = new ArrayList<QnaAllQuestionDto>();
			int endNum = pageNum * 10;
			int startNum = endNum - 9;
			   
		 	String sql = "SELECT t2.*"
						+ " FROM (SELECT rownum rnum, t1.*"
						+ " FROM (SELECT q.qna_idx, q.reply_condition, q.content, q.name, q.question_date"
						+ " FROM qna q"
						+ " ORDER BY q.qna_idx DESC) t1) t2"
						+   " WHERE rnum>=? AND rnum<=?";
		 	Connection conn = getConnection();

		 	PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
		 	ResultSet rs = pstmt.executeQuery();

		 	while(rs.next()) {
		 		int qnaIdx = rs.getInt("qna_idx");
		 		int reply_condition = rs.getInt("reply_condition");
		 		String content = rs.getString("content");
		 		String name = rs.getString("name");
		 		String question_date = rs.getString("question_date");
		 	
		 		QnaAllQuestionDto dto = new QnaAllQuestionDto(qnaIdx, reply_condition, content, name, question_date);
			 	listRet.add(dto);
		 	}
		 	rs.close();
		 	pstmt.close();
		 	conn.close();

		 	return listRet;
	 }
	 
	   public int getLastPageNumber() throws Exception {
		   String sql = "SELECT COUNT(*) FROM qna";
		   Connection conn = getConnection();
		   PreparedStatement pstmt = conn.prepareStatement(sql);
		   ResultSet rs = pstmt.executeQuery();
		   int countRet = -1;
		   if(rs.next()) {
			   countRet = rs.getInt(1);
		   }
		   rs.close();
		   pstmt.close();
		   conn.close();
		   return countRet/10 + (countRet % 10 > 0 ? 1 : 0);
		   
		   
	   }
	
	
	
}
