<%@page import="dao.NamooDashboardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//메서드 파라미터값 받아오기.
	int widgetIdx = Integer.parseInt(request.getParameter("widget_idx"));
	int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
	//해당 DAO 호출	
	NamooDashboardDao dDao = new NamooDashboardDao();
	//넣을 기능의 메서드 호출
	dDao.deleteWidget(memberIdx, widgetIdx);

%>
<script>
	location.href="NamooDashboardMain.jsp";
</script>