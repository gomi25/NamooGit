<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    // Signup3.jsp에서 전달된 memberIdx와 userInputCode를 가져옴
    String userInputCode = request.getParameter("user_input_code");
    int memberIdx = Integer.parseInt(request.getParameter("member_idx"));

    // Dao를 사용하여 인증 코드 검증 로직을 수행
    MemberDao mDao = new MemberDao();

    // 인증 코드가 올바른지 확인
    if(mDao.enterVerifyCode(memberIdx, userInputCode)) {
        // 인증 성공 시, 세션에 memberIdx를 저장하고 TeamList.jsp로 이동
        session.setAttribute("memberIdx", memberIdx);
%>
        <script>
            alert("인증되었습니다");
            location.href = "TeamList.jsp";
        </script>
<%
    } else {
        // 인증 실패 시, 경고 메시지를 띄우고 다시 시도하도록 Signup3.jsp로 리디렉션
%>
        <script>
            alert("인증 코드가 맞지 않습니다. 다시 시도해 주세요.");
            location.href = "Signup3.jsp?member_idx=<%=memberIdx%>";
        </script>
<%
    }
%>
