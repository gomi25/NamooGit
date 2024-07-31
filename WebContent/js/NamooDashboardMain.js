$(function() {
	/*위젯 삭제, 크기 변경 창 줄이기*/
	$(".widget_menu_icon").click(function() { 
		console.log("widget_menu_icon clicked !");
		$(this).find(".change_widget_size").css('display', 'block');
		$("#transparent_screen").css('display', 'block');
	});
	/* 화면 누르면 위젯 크기 설정 착 없애는 기능 */
	$("#transparent_screen").click(function() { 
		$(this).css('display', 'none');
		$(".change_widget_size").css('display', 'none');
	});
	/* 위젯 삭제 기능 */
	$(".change_widget_size > div:nth-child(1)").click(function() { /* 위젯 삭제 기능 */
		$(this).css('display', 'none');  
		$(this).parent().parent().parent().parent().remove();  
		$(".change_widget_size").css('display', 'none');
		$("#transparent_screen").css('display', 'none');
	});
	/* 위젯 크기 변경 기능 */
	$(".change_widget_size > div:nth-child(2)").click(function(e) { 
		let the_parent = $(this).parent().parent().parent().parent();       // <div class="fl widget_small">
		if(the_parent.hasClass("widget_small")) {
			the_parent.removeClass("widget_small");
			the_parent.addClass("widget_big");
			let the_child = the_parent.find("#small_filebox_prime");
			the_child.attr("id", "big_filebox_prime");
			$(this).text("작은 위젯으로 변경");
			the_parent.find(".filebox_file_small").attr("id", "widget_file_big");
			the_parent.find(".filebox_file_small").removeClass("filebox_file_small").addClass("filebox_file_big");
		} else if(the_parent.hasClass("widget_big")) {
			the_parent.removeClass("widget_big");
			the_parent.addClass("widget_small");
			let the_child = the_parent.find("#big_filebox_prime");
			the_child.attr("id", "small_filebox_prime");
			$(this).text("큰 위젯으로 변경");
			the_parent.find(".filebox_file_big").attr("id", "widget_file_small");
			the_parent.find(".filebox_file_big").removeClass("filebox_file_big").addClass("filebox_file_small");
		}
		$(".change_widget_size").css('display', 'none');
		$("#transparent_screen").css('display', 'none');
		e.stopPropagation();   // 이벤트 전파 막기.
	});
	/* 프로젝트 추가 팝업 */
	$(".add_button").click(function() { 
		let the_parent = $(this).parent().parent();
		if(the_parent.attr("id")=="div_project_big" || the_parent.attr("id")=="div_project_small") {
			$("#popup_add_project").css('display', 'block');
			$("#grey_screen1").css('display', 'block');
		}
	});
	 /*파일함 추가 팝업 */
	$("#filebox_widget > div:nth-child(2) > div:nth-child(2)").click(function() { 
		let the_parent = $(this).parent();
		if(the_parent.attr("id")=="big_filebox_prime" ||the_parent.attr("id")=="small_filebox_prime") {
			$("#popup_add_filebox").css('display', 'block');
			$("#grey_screen1").css('display', 'block');
		}
	});
	 /* X누르면 프로젝트 팝업 꺼지게 */
	$(".project_popup_header > div:nth-child(2)").click(function() { 
			$("#popup_add_project").css('display', 'none');
			$("#popup_add_filebox").css('display', 'none');
			$("#grey_screen1").css('display', 'none');
			$(".project_popup_area").find("input[type='checkbox']").prop('checked', false);
	});
	 /* 취소버튼 누르면 프로젝트 팝업 꺼지게 */
	$(".project_popup_button > div:nth-child(1)").click(function() { 
			$("#popup_add_project").css('display', 'none');
			$("#popup_add_filebox").css('display', 'none');
			$("#grey_screen1").css('display', 'none');
			$(".project_popup_area").find("input[type='checkbox']").prop('checked', false);
	});
	 /* 위젯 설정  + 위젯 추가 팝업 */
	$("#widget_setting_button").click(function() { 
		let the_parent = $(this).parent();
		if(the_parent.attr("id")=="div_dashboard_header") {
			$("#popup_add_widget").css('display', 'block');
			$("#grey_screen2").css('display', 'block');
		}
	});
	$("#div_add_widget").click(function() {
		let the_parent = $(this).parent();
		if(the_parent.attr("id")=="div_widget") {
			$("#popup_add_widget").css('display', 'block');
			$("#grey_screen2").css('display', 'block');
		}
	});
	 /* '위젯이 추가되었습니다.' 버튼 떴다 사라지게 */
	$(".widget_popup_big").click(function() { 
		$("#div_added_widget").css('display', 'block');
		setTimeout(function() {
	        $("#div_added_widget").fadeOut(900);
	    }, 1200);
	});
	 /* '위젯이 추가되었습니다.' 버튼 떴다 사라지게 */
	$(".widget_popup_small").click(function() { 
		$("#div_added_widget").css('display', 'block');
		setTimeout(function() {
	        $("#div_added_widget").fadeOut(900);
	    }, 1200);
	});
	/* x표시 누르면 위젯팝업 창 꺼짐 */
	$("#widget_popup_header > div:nth-child(2)").click(function() { 
		$("#popup_add_widget").css('display', 'none');
		$("#grey_screen2").css('display', 'none');
	});
	/* 체크박스 하나만 선택! */										/* 프로퍼티 = 멤버변수 + getter/setter */
	$(".project_popup").click(function() {					/* 프로퍼티(property.속성) -> 멤버변수 비슷한 의미. */
		let checked = $(this).find("input[type='checkbox']").prop('checked');
		if(checked == false) {  // checkbox가 off->on 바뀌는 타이밍.
			$(".project_popup").find("input[type='checkbox']").prop('checked', false);  			// 다 꺼버려.
			$(this).find("input[type='checkbox']").prop('checked', true); // 나만 빼고.
		} else {
			$(this).find("input[type='checkbox']").prop('checked', false); // 꺼라...
		}
	});
	/* 시간에 따라 문구 변경 */
	 $(document).ready(function() {
            function setGreeting() {
               let currentTime = new Date().getHours();
               let greeting = '';
                if (currentTime >= 6 && currentTime < 11) {
                    greeting = "님 좋은 아침입니다! 🌞";
                } else if (currentTime >= 11 && currentTime < 16) {
                    greeting = "님 즐거운 점심입니다! 🤗";
                } else if (currentTime >= 16 && currentTime < 22) {
                    greeting = "님 좋은 저녁입니다! 🌄";
                } else {
                    greeting = "님 좋은 밤 되세요! 🌙";
                }
                $('#div_Greeting').text(greeting); // jQuery를 사용하여 #div_Greeting 요소의 텍스트 설정
            }
            setGreeting(); // 페이지 로드 시 초기 문구 설정
            // 1분마다 실행하여 시간에 따라 문구 변경
            setInterval(function() {
                setGreeting();
            }, 60000); // 1분마다 실행 (밀리초 단위)
        });
	/* 시간에 따라 날짜 변경 */		        
	 $(document).ready(function() {
            function setCurrentDate() {
                let days = ['일', '월', '화', '수', '목', '금', '토'];
                let currentDate = new Date();
                let year = currentDate.getFullYear();
                let month = currentDate.getMonth() + 1;
                let date = currentDate.getDate();
                let day = days[currentDate.getDay()];

                let formattedDate = year + '년 ' + month + '월 ' + date + '일 ' + day + '요일';
                $('#current_date').text(formattedDate);
            }
            setCurrentDate(); // 페이지 로드 시 초기 날짜 설정
            
            // 1분마다 실행하여 날짜 업데이트
            setInterval(function() {
                setCurrentDate();
            }, 60000); // 1분마다 실행 (밀리초 단위)
        });
});