<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//MemberDao mDao = new MemberDao();
	//int memberIdx = mDao.agreeToTerms(1, 0);
	int memberIdx = (int)request.getAttribute("memberIdx");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입 정보 입력</title>
	<link rel="stylesheet" href="css/Signup2.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="js/Signup2.js" defer></script> <!-- 스크립트 파일 링크 추가 -->
</head>
<body>
	<!------------------------상단 헤더----------------------->
	<div id="div_header1">
		<div class="fl">
		   <img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628">
		   <span>NAMOO</span>
		</div>
		<div class="fl"><a href="hsttps://www.jandi.com/landing/kr/industry">고객사례</a></div>
		<div class="fl"><a href="https://www.jandi.com/landing/kr/consult">도입문의</a></div>
		<div class="fl"><a href="https://support.jandi.com/ko/">헬프센터</a></div>
		<div class="fl"><a href="https://blog.jandi.com/ko/">블로그</a></div>
		<div class="fr"><a href="http://localhost:9090/WebProject1/nammo_login.html"><strong>로그인</strong></a></div>
		<div class="fr"><a href="https://www.jandi.com/landing/kr/download"><strong>다운로드</strong></a></div>
	</div>
	<main style="margin-top: 125px;">
        <h1>회원가입</h1>
        <form id="signupForm" action="Controller?command=insert_member_info" method="post"> <!-- 폼에 ID 추가 -->
        	<input type="hidden" name="member_idx" value="<%=memberIdx%>"/>
            <fieldset>
                <div id="div_input_name">
                    <span>이름</span>
                    <input type="text" name="name" placeholder="이름 입력" autocomplete="off">
                    <span class="error" id="nameError"></span> <!-- 오류 메시지 추가 -->
                </div>
                <div id="div_input_email">
                    <span>이메일</span>
                    <input type="text" name="email" placeholder="이메일 입력" autocomplete="off" class="green_border" disabled> <!-- 초기 비활성화 -->
                    <span class="error" id="emailError"></span> <!-- 오류 메시지 추가 -->
                </div>
                <div id="div_input_pw">
                    <span>비밀번호</span> <br/>
                    <span id="span_pw_txt">비밀번호는 8~20자 영문, 숫자, 특수문자로 입력하세요.</span>
                    <input type="password" name="password" placeholder="비밀번호 입력" autocomplete="off" maxlength="20" disabled> <!-- 초기 비활성화 -->
                    <span class="error" id="passwordError"></span> <!-- 오류 메시지 추가 -->
                    <input type="password" name="confirmPassword" placeholder="비밀번호 확인" autocomplete="off" maxlength="20" style="margin-top: 20px" disabled> <!-- 초기 비활성화 -->
                    <span class="error" id="confirmPasswordError"></span> <!-- 오류 메시지 추가 -->
                </div>
            </fieldset>
            <button type="submit" disabled id="div_next">다음</button>
            <div id="div_already_login">
                <p>이미 가입하셨나요?</p>
                <a href="/landing/kr/signin">로그인</a>
            </div>
        </form>
    </main>
</body>
</html>