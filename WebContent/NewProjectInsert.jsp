<%@page import="dao.*"%>
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
	int teamIdx = 1;   	// 테스트 
	int loginMemberIdx = 2;	  	// 테스트 
	
    String project_name = request.getParameter("project_name");
	int color_select = Integer.parseInt(request.getParameter("color_select"));

    
    
    try {
        ProjectDao pDao = new ProjectDao();
        int projectIdx = pDao.createProject( project_name, teamIdx, color_select );
        pDao.addProjectMember(loginMemberIdx, projectIdx);
        
       /*request.setAttribute("memberIdx", memberIdx);
        request.setAttribute("teamIdx", teamIdx); */

  
       // RequestDispatcher disp = request.getRequestDispatcher("AddProject.jsp");
       // disp.forward(request, response);
        response.sendRedirect("AddProject.jsp");
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Error: " + e.getMessage());
    }

    	
	%>
</body>
</html>