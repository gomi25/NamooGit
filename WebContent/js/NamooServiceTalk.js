$(function(){
	// 계정에 따라 보이는 화면, Session으로 다시 수정해야함. 
	/* let member_idx = $("#div_service_talk1_1").attr("member_idx");
	alert
	 if(member_idx === 0) {
		$("#div_service_talk1_1").hide();
	} else{
		$("#div_service_talk1_1").show();
	}*/
	// 톡방 : 클릭시 톡방 나가기 창 뜸 		
	$(".header_quit").click(function() { 
		$("#quit_service_talk").css('display', 'block');
		$(".transparent_button").css('display', 'block');
	});
	// 톡방 : 재클릭시 톡방 나가기 창 거짐 	
	$("#service_talk_header > div:nth-child(5)").click(function() { 
		$("#quit_service_talk").css('display', 'none');
		$(".transparent_button").css('display', 'none');
	});	
	// 톡 목록 : 클릭시 톡방 나가기 창 켜짐	
	$(".talk_room > div:nth-child(2)").click(function() { 
		$(this).parent().find("#quit_talk_room_div").css('display', 'block');
		$(".transparent_button").css('display', 'block');
	});
	// 톡 목록 : 재클릭시 톡방 나가기 창 거짐 	
	$(".transparent_button").click(function() { 
		$(this).parent().find("#quit_talk_room_div").css('display', 'none');
		$(".transparent_button").css('display', 'none');
	});
	//새 문의하기 버튼 클릭시 방 이동
	$("#send_new_message").click(function() {
		//파라미터 값으로 들어갈 인풋 경로 넣기
		let member_idx = $(this).attr("member_idx");
		alert("member_idx : " + member_idx);
		
		$.ajax({
			type: "post",
		    url: 'AjaxServiceTalkCreateServiceTalkroomServlet', // 만들어둔 서블릿 파일 이름 적기
		    data : {member_idx : member_idx},		// 받아놓은 파라미터
		    success: function (data) {
				//성공시 원하는 기능 넣기
				alert("서비스톡 생성 되었습니다.");		
				alert(data.result);  // 성공		
				$("#div_service_talk2").css('display','block');
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
	});
	
});

















