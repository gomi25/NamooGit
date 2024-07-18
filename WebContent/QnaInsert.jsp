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
    String name = request.getParameter("name");
    String phone_number = request.getParameter("phone_number");
    String position = request.getParameter("position");
    String email = request.getParameter("email");
    int industry = Integer.parseInt(request.getParameter("industry"));
    int count = Integer.parseInt(request.getParameter("count"));
    int question = Integer.parseInt(request.getParameter("question"));
    int agreement = request.getParameter("agreement") != null ? Integer.parseInt(request.getParameter("agreement")) : 0;
    int reply_condition = 0;
    String content = request.getParameter("content");
    
    
    try {
        QnaDao tDao = new QnaDao();
        tDao.addQna3(name, phone_number, position, email, industry, count, question, agreement, reply_condition, content);

  
        RequestDispatcher disp = request.getRequestDispatcher("Qna.jsp");
        disp.forward(request, response);
        response.sendRedirect("Qna.jsp");
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Error: " + e.getMessage());
    }

    	
	%>
</body>
</html>