package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.GanttkCommentLikeDto;
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
	// deleteQnaQuestion: qna 질문 삭제
	// 파라미터: qna_idx
	public void deleteQnaQuestion(int qnaIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "DELETE FROM qna WHERE qna_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaIdx);
	        
	        pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
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
	}
	
	// deleteQnaAnswer: qna 답변 삭제
	// 파라미터: answer_idx
	public void deleteQnaAnswer(int answerIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    try {
	        conn = getConnection();
	        
	        String sql = "DELETE FROM qna_answer WHERE answer_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, answerIdx);
	        
	        pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
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
	}
	
//-----------------------UPDATE----------------------------------------
	// updateQnaContent: qna의 content 변경
	// 파라미터: content(내용), qna_idx
	public void updateQnaContent(String content, int qnaIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();

	        String sql = "UPDATE qna SET content = ? WHERE qna_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, content);
	        pstmt.setInt(2, qnaIdx);

	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
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
	}
	
	// updateQnaAnswer: qna 답변 변경
	// 파라미터: content(내용), answer_idx
	public void updateQnaAnswer(String content, int qnaIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();

	        String sql = "UPDATE qna_answer SET content = ? WHERE qna_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, content);
	        pstmt.setInt(2, qnaIdx);

	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
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
	}
	
	// updateQnaAnswerCondition: 답변상태 변경(답변 완료)
	// 파라미터: qna_idx
	public void updateQnaAnswerCondition(int qnaIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();

	        String sql = "UPDATE qna SET reply_condition = 1 WHERE qna_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaIdx);

	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
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
	}
	
	// updateQnaAnswerNoCondition: 답변상태 변경(미답변)
	// 파라미터: qna_idx
	public void updateQnaAnswerNoCondition(int qnaIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();

	        String sql = "UPDATE qna SET reply_condition = 0 WHERE qna_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaIdx);

	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
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
	}
	
//-----------------------INSERT----------------------------------------
	
	// addQna3: 도입문의
	// 파라미터: name, phone_number, position, email, industry, count(인원수), question, agreement(동의여부), reply_condition(답변상태), content
	public void addQna3(String name, String phoneNumber, String position, String email, int industry, int count, int question, int agreement, int replyCondition, String content) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();

	        String sql = "INSERT INTO qna(qna_idx, name, phone_number, position, email, industry, count, question, agreement, question_date, reply_condition, content)"
	                   + " VALUES(seq_qna.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, name);
	        pstmt.setString(2, phoneNumber);
	        pstmt.setString(3, position);
	        pstmt.setString(4, email);
	        pstmt.setInt(5, industry);
	        pstmt.setInt(6, count);
	        pstmt.setInt(7, question);
	        pstmt.setInt(8, agreement);
	        pstmt.setInt(9, replyCondition);
	        pstmt.setString(10, content);

	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
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
	}
	
	// addQnaAnswer: qna 답변
	// 파라미터: qna_idx, content
	public void addQnaAnswer(int qnaIdx, String content) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();

	        String sql = "INSERT INTO qna_answer(ANSWER_IDX, QnA_IDX, CONTENT, ANSWER_DATE) VALUES(seq_qna_answer.nextval, ?, ?, sysdate)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaIdx);
	        pstmt.setString(2, content);

	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
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
	}
	
//--------------------------SELECT------------------------------------------
	// getQnaAnswer: qna답변
	// 파라미터: qna_idx
	// 리턴: answer_idx, content(답변 내용), answer_date(답변 날짜)
	public QnaAnswerDto getQnaAnswer(int qnaIdx) {
	    QnaAnswerDto dtoRet = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConnection();

	        String sql = "SELECT qa.answer_idx, qa.content, qa.answer_date"
	                   + " FROM qna_answer qa"
	                   + " WHERE qna_idx = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaIdx);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int answerIdx = rs.getInt("answer_idx");
	            String content = rs.getString("content");
	            String answerDate = rs.getString("answer_date");
	            dtoRet = new QnaAnswerDto(answerIdx, qnaIdx, content, answerDate);
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
	    return dtoRet;
	}
	 
	// getQnaAllQuestion: 질문들
	// 리턴: qna_idx, reply_condition(답변상태), content, name, question_date
	public ArrayList<QnaAllQuestionDto> getQnaAllQuestion() {
	    ArrayList<QnaAllQuestionDto> listRet = new ArrayList<QnaAllQuestionDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConnection();

	        String sql = "SELECT q.qna_idx, q.reply_condition, q.content, q.name, q.question_date"
	                   + " FROM qna q"
	                   + " ORDER BY q.qna_idx DESC";
	        pstmt = conn.prepareStatement(sql);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int qnaIdx = rs.getInt("qna_idx");
	            int replyCondition = rs.getInt("reply_condition");
	            String content = rs.getString("content");
	            String name = rs.getString("name");
	            String questionDate = rs.getString("question_date");

	            QnaAllQuestionDto dto = new QnaAllQuestionDto(qnaIdx, replyCondition, content, name, questionDate);
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
	 
	// getQnaQuestionPage: 질문들 paging 
	// 파라미터: pageNum
	// 리턴: qna_idx, reply_condition, content, name, question_date
	public ArrayList<QnaAllQuestionDto> getQnaQuestionPage(int pageNum) {
	    ArrayList<QnaAllQuestionDto> listRet = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        int endNum = pageNum * 10;
	        int startNum = endNum - 9;

	        String sql = "SELECT t2.*"
	                   + " FROM (SELECT rownum rnum, t1.*"
	                   + " FROM (SELECT q.qna_idx, q.reply_condition, q.content, q.name, q.question_date"
	                   + " FROM qna q"
	                   + " ORDER BY q.qna_idx DESC) t1) t2"
	                   + " WHERE rnum >= ? AND rnum <= ?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, startNum);
	        pstmt.setInt(2, endNum);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int qnaIdx = rs.getInt("qna_idx");
	            int replyCondition = rs.getInt("reply_condition");
	            String content = rs.getString("content");
	            String name = rs.getString("name");
	            String questionDate = rs.getString("question_date");

	            QnaAllQuestionDto dto = new QnaAllQuestionDto(qnaIdx, replyCondition, content, name, questionDate);
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
	 
	// getLastPageNumber: 마지막 페이지
	public int getLastPageNumber() {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    int countRet = -1;

	    try {
	        String sql = "SELECT COUNT(*) FROM qna";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            countRet = rs.getInt(1);
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
	    // 페이지 수 계산
	    return countRet / 10 + (countRet % 10 > 0 ? 1 : 0);
	}
	
	// changeToUnrepliedByAnswerIdx: 답변상태 미답변으로 바꾸기
	// 파라미터: answer_idx
	// 리턴: qna_idx
	public void changeToUnrepliedByAnswerIdx(int answerIdx) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        String sql = "SELECT qna_idx FROM qna_answer WHERE answer_idx=?";
	        conn = getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, answerIdx);
	        rs = pstmt.executeQuery();

	        int qnaIdx = 0;
	        if (rs.next()) {
	            qnaIdx = rs.getInt("qna_idx");
	        }

	        rs.close();
	        pstmt.close();

	        sql = "UPDATE qna SET reply_condition = 0 WHERE qna_idx=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, qnaIdx);
	        pstmt.executeUpdate();

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
	}
}
