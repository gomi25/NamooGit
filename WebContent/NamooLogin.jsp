<%@page import="dao.NamooMemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	NamooMemberDao mDao = new NamooMemberDao();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인 | 업무용 협업툴 나무(NAMOO)</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooLogin.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/NamooLogin.js"></script>
</head>
<body>
	<!--===========================상단 헤더===============================-->
	<div id="div_header1">
		<div class="fl">
			<img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628">
			<span><a href="Controller?command=enter_main_page">NAMOO</a></span>
		</div>
		<div class="fl"><a href="Controller?command=enter_consumer_case">고객사례</a></div>
		<div class="fl"><a href="Controller?command=enter_qna">도입문의</a></div>
		<div class="fl"><a href="Controller?command=enter_help_center">헬프센터</a></div>
		<div class="fl"><a href="https://blog.jandi.com/ko/">블로그</a></div>
		<div class="fr"><a href="Controller?command=enter_login_page"><strong>로그인</strong></a></div>
		<div class="fr"><a href="https://www.jandi.com/landing/kr/download"><strong>다운로드</strong></a></div>
	</div>
	<!--=============================로그인==============================-->
	<div id="div_body" >
		<div id="div_login">
			<div><h1>로그인</h1></div>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="login_check"/>
				<!-- 인풋  -->
				<div id="div_email_pw" >
					<div>
						<input type="email" id="email" name="email" placeholder="이메일"/>
					</div>
					<div>
						<input type="password" id="password" name="pw" placeholder="비밀번호"/>
					</div>
				</div>
				<!-- 체크박스  -->				
				<div id="div_checkbox" class="fl">
					<div class="fl">
						<input type="checkbox" id="remember_email">
						<label for="remember_email">이메일 기억하기</label>
					</div>
					<div class="fl">
						<input type="checkbox" id="stay_signed_in">
						<label for="stay_signed_in">로그인 상태 유지</label>
					</div>
					<div class="fr">비밀번호 찾기 →</div>
				</div>
				<div id="div_login_submit">
				<button type="submit" id="login_btn">로그인</button></div>
			</form>
			<div id="div_signup">
				<div>
					<div class="fl">나무가 처음이신가요?</div>
					<div class="fl"><a href="Signup1.html">회원가입</a></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 비밀번호 변경창 	-->	
	<form action="NamooMain.jsp">
		<div id="grey_screen"></div>
		<div id="div_find_password">
				<div id="div_find_password_header">
					<h3>비밀번호 찾기</h3>
				</div>	
				<div id="div_find_password_body">
					<div>이메일 주소를 입력해주세요</div>
					<div><input type="email" id="user_email" placeholder="이메일" required></div>
					<div>
						<div class="fr">확인</div>
						<div class="fr">닫기</div>
					</div>
				</div>	
		</div>
		<div id="div_send_email">
			<div>이메일이 성공적으로 발송되었습니다.</div>
			<div><input type="submit" value="확인"></div>
		</div>
	</form>
</body>
</html>












