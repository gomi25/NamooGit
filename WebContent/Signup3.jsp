<%@page import="util.MailSender"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int memberIdx = Integer.parseInt(request.getParameter("member_idx"));
%>
<script>alert("메일 발송 되었습니다!");</script>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>이메일 인증</title>
	<link rel="stylesheet" href="css/Signup3.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function() {
	        // 1. 숫자만 입력 허용, 아니면 막기
	        // 2. 숫자 외에 입력 시 다음 칸으로 넘어가지 않게
	        // 3. 숫자 외에 문자 지울 시 다시 원래 css 속성으로
	        $('#div_verify_code > input[type="text"]').on('input', function () {
	            if (this.value.match(/[^0-9]/)) {
	                $(this).val('');
	            } else {
	                $(this).css('border', '1px solid #ddd');
	                // 한 칸 입력 후 자동으로 다음 입력 칸으로 이동
	                // $(this).attr("id")   // 'input_verify_code1'
	                // $(this).attr("id").charAt($(this).attr("id").length-1)   // '1'
	                // Number($(this).attr("id").charAt($(this).attr("id").length-1))   // 1
	                let the_number = Number($(this).attr("id").charAt($(this).attr("id").length-1));
	                if(the_number <= 3) {
	                    $('#input_verify_code' + (the_number+1)).focus();
	                }
	            }
	        });
	
	        // 백스페이스를 누르면 현재 커서에서 이전 칸으로 이동
	        $('input').on('keydown', function(e) {
	            let the_number = Number($(this).attr("id").charAt($(this).attr("id").length - 1));
	            if (e.key === 'Backspace' && $(this).val().length === 0) {
	                e.preventDefault(); // 기본 백스페이스 동작 방지
	                if (the_number > 1) {
	                    let prevInput = $('#input_verify_code' + (the_number - 1));
	                    prevInput.focus();
	                    prevInput.val(''); // 이전 칸의 값도 지움
	                    $(this).css('border', '1px solid #ddd');
	                }
	            }
	        });  
	
	        // 공백 입력 막기
	        $('input').on('keypress', function(e) {
	            if (e.key === ' ') {
	                e.preventDefault();
	            }
	        });
	        
	        // 모든 input이 채워졌는지 확인
	        $('button').attr('disabled', 'disabled');
	
	        $('input[type=text]').on('input', function() {
	            function checkAllFilled() {
	                var allFiled = true;
	                $('input[type=text]').each(function() {
	                    if ($(this).val() === '') {
	                        allFiled = false;
	                        return false; // input이 비어 있으면 루프를 종료
	                    }
	                });
	                return allFiled;
	            }
	
	            // 버튼 활성화 & 비활성화
	            if (checkAllFilled()) {
	                $('button').removeAttr('disabled'); // submit 버튼의 disabled를 비활성화함
	            } else {
	                $('button').attr('disabled', 'disabled');
	            }
	        });
	        
	        $("#button").click(function() {
	        	let user_input_code = Number($("#input_verify_code1").val()) * 1000 
	        						+ Number($("#input_verify_code2").val()) * 100
	        						+ Number($("#input_verify_code3").val()) * 10
	        						+ Number($("#input_verify_code4").val());
	        	
	        	location.href = "SignupAction.jsp?user_input_code=" + user_input_code + "&member_idx=<%=memberIdx%>";
	        });
	    });
	</script>
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
    <main style="margin-top: 180px;">
        <div id="div_container">
            <div id="div_email_box">
                <div id="div_email_content">
                    <img src="data:image/png;base64,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" alt="이메일 인증">
                    <p>인증번호 이메일을 발송했습니다.<br>받으신 인증번호를 입력해주세요.</p>
                </div>
                <form action="Controller?command=enter_team_list_from_signup" method="post">
                    <input type="hidden" name="command" value="signup">
    				<input type="hidden" name="member_idx" value="<%= memberIdx %>">
                    <fieldset>
                        <div id="div_verify_code">
                            <span id="code_1"></span>
                                <input type="text" id="input_verify_code1" autocomplete="off" maxlength="1" autofocus class="green_border"></input>
                            <span id="code_2"></span>
                                <input type="text" id="input_verify_code2" autocomplete="off" maxlength="1" autofocus class="green_border"></input>
                            <span id="code_3"></span>
                                <input type="text" id="input_verify_code3" autocomplete="off" maxlength="1" autofocus class="green_border"></input>
                            <span id="code_4"></span>
                                <input type="text" id="input_verify_code4" autocomplete="off" maxlength="1" autofocus class="green_border"></input>
                        </div>
                    </fieldset>
                </form>
            </div>
			<button type="button" disabled id="button">인증하기</button>
			<div id="div_again_email">
				<p>이메일을 받지 못하셨나요?</p>
				<a href="/landing/kr/signin">다시 시도해주세요.</a>
			</div>
        </div>
    </main>
</body>
</html>