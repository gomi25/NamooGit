<%@page import="dao.QnaDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");   // post방식 한글깨짐 방지.
	
	    String qnaIdx = request.getParameter("qna_idx");
	    String content = request.getParameter("content");
	
	    if (qnaIdx != null && content != null) {
	        int qna_idx = Integer.parseInt(qnaIdx);
	        
	        try {
	            QnaDao tDao = new QnaDao();
	            tDao.addQnaAnswer(qna_idx, content);
	            tDao.updateQnaAnswerCondition(qna_idx);
	            
	            
	            //request.setAttribute("memberIdx", memberIdx);
	
	            response.sendRedirect("Qna.jsp");
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("Error: " + e.getMessage());
	        }
	    }
	%>
</body>
</html>