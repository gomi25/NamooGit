<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인 | 업무용 협업툴 나무(NAMOO)</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooLogin.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function() {
			/* 비밀번호 찾기 창 뜨게 */		
			$("#div_checkbox > div:nth-child(3)").click(function() { 
				$("#grey_screen").css('display', 'block');
				$("#div_find_password").css('display', 'block');
				$("#user_email").css('border','1px solid #e0e4ed');
 			});
			/* 취소버튼 눌렀을 때 창 꺼지기 */
			$("#div_find_password_body > div:nth-child(3) > div:nth-child(2)").click(function() { 
				$("#user_email").val('');
				$("#grey_screen").css('display', 'none');
				$("#div_find_password").css('display', 'none');
			});
			/* 확인버튼 눌렀을 때 이메일 발송창 뜨게 */
			$("#div_find_password_body > div:nth-child(3) > div:nth-child(1)").click(function() { 
				let user_email = $("user_email").val;
				if(user_email.length < 2){
					$("#user_email").css('border','1px solid red');
					return false;
				}
				return true;		
				$("#user_email").css('border','1px solid #e0e4ed');
				$("#div_find_password").css('display', 'none');
				$("#div_send_email").css('display', 'block');
			});
			/* 확인버튼 눌렀을 때 모든 창 꺼지기 */
			$("#div_send_email > div:nth-child(2) input").click(function() { 
				$("#div_send_email").css('display', 'none');
				$("#grey_screen").css('display', 'none');
			});
			
			$("#user_email").keyup(function() {
				let email_input = $(this).val();
				if(email_input.length > 0) {
					$(this).css('border', '1px solid #00c473');
				} else {
					$(this).css('border', '1px solid red');
				}
			});
		});
	</script>
</head>
<body>
<!--===========================상단 헤더===============================-->
	<div id="div_header1">
		<div class="fl">
			<img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628" >
			<span><a href="NamooMain.jsp">NAMOO</a></span>
		</div>
		<div class="fl"><a href="hsttps://www.jandi.com/landing/kr/industry">고객사례</a></div>
		<div class="fl"><a href="https://www.jandi.com/landing/kr/consult">도입문의</a></div>
		<div class="fl"><a href="NamooHelpMain.jsp">헬프센터</a></div>
		<div class="fl"><a href="https://blog.jandi.com/ko/">블로그</a></div>
		<div class="fr"><a href="NamooLogin.jsp"><strong>로그인</strong></a></div>
		<div class="fr"><a href="https://www.jandi.com/landing/kr/download"><strong>다운로드</strong></a></div>
	</div>
	<!--=============================로그인==============================-->
	<div id="div_body" >
		<div id="div_login">
			<div><h1>로그인</h1></div>
			<form action="NamooMain.jsp">
				<!-- 인풋  -->
				<div id="div_email_pw" >
					<div>
						<input type="email" name="email" placeholder="이메일"/>
					</div>
					<div>
						<input type="password" name="email" placeholder="비밀번호"/>
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
				<button type="submit" disabled>로그인</button></div>
			</form>
			<div id="div_signup">
				<div>
					<div class="fl">나무가 처음이신가요?</div>
					<div class="fl"><a href="https://www.jandi.com/landing/kr/signup">회원가입</a></div>
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
<!--_________________________ 이메일 발송창 __________________________-->
</body>
</html>












