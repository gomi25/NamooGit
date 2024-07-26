<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userInputCode = request.getParameter("user_input_code");
	int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
	
	// 먼저, (Dao) ----> 1 또는 0 으로 리턴 / true 또는 false 로 리턴하는 메서드.
	// 성공 ---> TeaList.jsp로 이동.
	MemberDao mDao = new MemberDao();
	if(mDao.enterVerifyCode(memberIdx, userInputCode)) {
%>
		<script>
			alert("인증되었습니다");
			location.href = "TeamList.jsp";	
		</script>
<%
	} else {
		// 실패 ---> alert("아님! 다시 시도해 주세요.");
%>
		<script>
			alert("아님! 다시 시도해 주세요.");
			location.href = "Signup3.jsp?member_idx=<%=memberIdx%>";
		</script>
<%
	}
%>