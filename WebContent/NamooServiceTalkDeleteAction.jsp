<%@page import="dao.NamooServiceTalkDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//메서드 파라미터값 받아오기.
	int serviceTalkroomIdx = Integer.parseInt(request.getParameter("service_talkroom_idx"));
	//해당 DAO 호출	
	NamooServiceTalkDao sDao = new NamooServiceTalkDao();
	//넣을 기능의 메서드 호출
	sDao.deleteServiceTalkRoom(serviceTalkroomIdx); 

%>
<script>
	location.href="NamooServiceTalk.jsp";
</script>