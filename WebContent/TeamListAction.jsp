<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int memberIdx = 34;   // TODO : 세션에서 가져오는 걸로 변경해야.

	request.setCharacterEncoding("utf-8");  // post 방식의 한글깨짐 방지.

	String teamName = request.getParameter("teamName");
	String teamDomain = request.getParameter("teamDomain");
	
	MemberDao mDao = new MemberDao();
	int teamIdx = mDao.createTeam(teamName, teamDomain + ".jandi.com", "https://jandi-box.com/teams/0/logo.png?timestamp=20190628");
	mDao.addTeamFirstMember(teamIdx, memberIdx);
	
%>
<script>
	location.href = "NamooMainTool.jsp";
</script>