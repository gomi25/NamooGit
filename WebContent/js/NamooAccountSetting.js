$(function(){
	/* 프로필 변경 창 뜨게 */
	$("#profile_pic").click(function() { 
		$("#select_pic").css('display', 'block');
		$("#transparent_screen").css('display', 'block');
	});
	/* 프사 : 기본이미지로 변경 버튼 클릭시 */
	$("#resetImage").click(function() {
		let member_idx = $(this).closest("#div_setting_box2").attr("member_idx");
		//alert("member_idx : " + member_idx);
		
		$.ajax({
		    url: 'AjaxAccountSettingResetImageServlet',
		    data : {member_idx : member_idx},
		    success: function (data) {
				//화면 원상복귀
				$("#select_pic").css('display', 'none');
				$("#profile_pic > img").attr('src',data.url);
				alert("성공");
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
	});
	/* 배경 클릭시 창 꺼지기 */
	$("#transparent_screen").click(function() { 
		$("#select_pic").css('display', 'none');
		$("#transparent_screen").css('display', 'none');
	});
	/* 이름:클릭시 이름 변경 창이 뜨게 */
	$("#div_profile_name >  div:nth-child(2)").click(function() { 
		$("#div_profile_name >  div:nth-child(2)").css('display', 'none');
		$("#div_input_name").css('display', 'block');
		$('#div_profile_name').height('126px');
	});
	/* 이름: 변경창 꺽쇠 클릭시 원상복귀 */
	$("#div_input_name > div:nth-child(4)").click(function() { 
		$("#div_input_name").css('display', 'none');
		$("#div_profile_name >  div:nth-child(2)").css('display', 'block');
		$('#div_profile_name').height('70px');
		$("input[id='name']").val('');	
		
	});
	/* 이름: 변경창 취소버튼 클릭시 원상복귀 */
	$("#div_input_name > div:nth-child(2)").click(function() { 
		$("#div_input_name").css('display', 'none');
		$("#div_profile_name >  div:nth-child(2)").css('display', 'block');
		$('#div_profile_name').height('70px');	
		$("input[id='name']").val('');	
	});
	/* 이름 :확인 버튼 클릭시 */
	$("#div_input_name > div:nth-child(3)").click(function() {
		let name = $(this).closest("#div_input_name").find("#name").val();
		//alert("name : " + name);
		let member_idx = $(this).closest("#div_setting_box2").attr("member_idx");
		//alert("member_idx : " + member_idx);
		
		$.ajax({
		    url: 'AjaxAccountSettingNameServlet',
		    data : {name : name, member_idx : member_idx},
		    success: function (data) {
				//화면 원상복귀
				//ㄴalert("성공");
				$("#div_input_name").css('display', 'none');
				$("#div_profile_name >  div:nth-child(2)").css('display', 'block');
				$("#div_profile_name").height('70px');	
				$("input[id='name']").val('');			
				$("#show_name").text(data.name);
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
	});
	
	/* 비밀번호: 꺽쇠 클릭시 변경창 뜨게 */
	$("#div_password >div:nth-child(2) > div:nth-child(2)").click(function() { 
		$("#div_password >div:nth-child(2)").css('display', 'none');
		$("#div_change_password").css('display', 'block');
		$("#div_password").height('320px');
	});
	 /* 비밀번호: 꺽쇠 클릭시 원상복귀 */
	$("#div_change_password > div:nth-child(2)").click(function() { 
		$("#div_change_password").css('display', 'none');
		$("#div_password >div:nth-child(2)").css('display', 'block');
		$("#div_password").height('70px');
		$("input[name='current_password']").val('');
    	$("input[name='new_password']").val('');
    	$("input[name='check_password']").val('');
	});
	/* 비밀번호: 취소버튼 클릭시 원상복귀*/
	$("#div_change_password > div:nth-child(8)").click(function() { 
		$("#div_change_password").css('display', 'none');
		$("#div_password >div:nth-child(2)").css('display', 'block');
		$("#div_password").height('70px');
		$("input[name='current_password']").val('');
    	$("input[name='new_password']").val('');
    	$("input[name='check_password']").val('');
	});
	/*비밀번호가 일치하는지.*/
	$(document).ready(function() {
	    $('#change_pw').on('click', function() {
	        let newPassword = $("input[name='new_password']").val();
	        let checkPassword = $("input[name='check_password']").val();
	
	        if (newPassword !== checkPassword) {
	            $("input[name='new_password']").css('border', '1px solid #e03703');
	            $("input[name='check_password']").css('border', '1px solid #e03703');
	            $("input[name='check_password']").val('');
	            $("input[name='check_password']").attr('placeholder', '비밀번호가 일치하지 않습니다.');
	        } else {
				// 입력필드 초기화
				$("input[name='current_password']").val('');
            	$("input[name='new_password']").val('');
            	$("input[name='check_password']").val('');
				$("#div_change_password").css('display', 'none');
				$("#div_password >div:nth-child(2)").css('display', 'block');
				$("#div_password").height('70px');
	            // 여기에 비밀번호가 일치하는 경우의 처리 로직을 추가
	        }
	    });
	});
	/* 비밀번호 :확인 버튼 클릭시 */
	$(".chagepw").click(function() {
		let current_pw = $(this).closest("#div_change_password").find("input[name='current_password']").val();
		//alert("new_name : " + new_name);
		let new_pw = $(this).closest("#div_change_password").find("input[name='new_password']").val();
		//alert("new_name : " + new_name);
		let member_idx = $(this).closest("#div_setting_box2").attr("member_idx");
		//alert("member_idx : " + member_idx);
		
		$.ajax({
		    url: 'AjaxAccountSettingPwServlet',
		    data : {current_pw : current_pw, new_pw : new_pw, member_idx : member_idx},
		    success: function (data) {
				alert("SUCCESS!");
				alert(data.result);  // 성공						
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
	});
	/* 계정삭제: 꺽쇠 클릭시 변경창 뜨게 */
	$("#div_delete > div:nth-child(2) > div").click(function() { 
		$("#div_delete > div:nth-child(2)").css('display', 'none');
		$("#div_delete_input").css('display', 'block');
		$("#div_delete").height('200px');
	});
	/* 계정삭제: 꺽쇠 클릭시 원상복귀 */
	$("#div_delete_input > div:nth-child(2)").click(function() { 
		$("#div_delete_input").css('display', 'none');
		$("#div_delete > div:nth-child(2)").css('display', 'block');
		$("#div_delete").height('70px');
		$("#div_delete_input  input[type='password']").val('');
	});
	/* 계정삭제: 취소버튼 클릭시 원상복귀 */
	$("#div_delete_input > div:nth-child(6)").click(function() { 
		$("#div_delete_input").css('display', 'none');
		$("#div_delete > div:nth-child(2)").css('display', 'block');
		$("#div_delete").height('70px');
		$("#div_delete_input  input[type='password']").val('');
	});
	/* 계정삭제 :확인 버튼 클릭시 */
	$("#delete_account").click(function() {
		//파라미터 값으로 들어갈 인풋 경로 넣기
		let pw = $(this).parent("#div_delete_input").find("input[type='password']").val();
		alert("pw: " + pw);
		//member_idx가 호출된 div가 포함된 영역 아무데나~
		let member_idx = $(this).closest("#div_setting_box2").attr("member_idx");
		
		$.ajax({
		    url: 'AjaxAccountSettingDeleteAccountServlet', 	// 만들어둔 서블릿 파일 이름 적기
		    data : {pw : pw, member_idx : member_idx},		// 받아놓은 파라미터
		    success: function (data) {
				//성공시 원하는 기능 넣기
				alert("계정 삭제 되었습니다.");		
				alert(data.result);  // 성공		
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
	});
});
