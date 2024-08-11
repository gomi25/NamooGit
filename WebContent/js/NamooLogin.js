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
		let user_email = $("#user_email").val();
		//alert(user_email);
		if(user_email.length < 2){
			$("#user_email").css('border','1px solid red');
			return false;
		}
		$("#user_email").css('border','1px solid #e0e4ed');
		$("#div_find_password").css('display', 'none');
		$("#div_send_email").css('display', 'block');
		
		let params = {
			emailTo: user_email,
			nameTo: '고객님',
			subject: '나무 비밀번호 안내메일 제목'
		};
		
		$.ajax({
			type: "post",
		    url: 'AjaxSendEmailServlet', // 만들어둔 서블릿 파일 이름 적기
		    data : params,		// 받아놓은 파라미터
		    success: function (data) {
		    	//alert("메일이 발송되었습니다.");
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
		return true;		
	});
	/* 확인버튼 눌렀을 때 모든 창 꺼지기 */
	$("#div_send_email > div:nth-child(2) input").click(function() { 
		$("#div_send_email").css('display', 'none');
		$("#grey_screen").css('display', 'none');
	});
	// 로그인 버튼 색 바꾸기.
	$("#email, #password").keyup(function() {
	    let emailVal = $("#email").val();
	    let passwordVal = $("#password").val();
	
	    if(emailVal !== '' && passwordVal !== '') {
	        $("#login_btn").css('background-color', '#00c473'); // 입력란이 비어있지 않으면 버튼 배경을 녹색으로 변경
	    } else {
	        $("#login_btn").css('background-color', ''); // 입력란 중 하나라도 비어 있으면 원래 색으로 되돌림
	    }
	});
});