//		$(".div_member_list2").each(function(idx, item) {
//			alert(idx + " : " + $(item).find(".profile_name").text());
//		});			
//		let doo = $(".div_member_list2").eq(0).find(".profile_name").text();
//		let se = $(".div_member_list2").eq(1).find(".profile_name").text();
//		alert(doo + " , " + se);
	function insert_here(insert_name) {
		let idx_found = -1;
		$(".div_member_list2").each(function(idx, item) {
			let name = $(item).find(".profile_name").text().trim();  // hoxy : trim()
			if(insert_name > name) {
				console.log(insert_name+ "이(가) " + name + "보다 큼. 히히.")
				idx_found = idx;
			}
		});
		if(idx_found==-1)
			return null;
		return $(".div_member_list2").eq(idx_found);
	}
	
	// 토픽 클릭 시 페이지 이동
	$(function(){
		$(".topic_item").click(function () {
			alert("클릭버튼 작동");
			let topicIdx = $(this).attr("topic_idx");
			// 페이지 이동 --> "Ex11Detail.jsp?bno=213"로 이동
			// location.href = 새 주소(문자열);   ← 얘는 무조건 'GET방식'임
			location.href = "NamooTopic2.jsp?topicIdx=" + topicIdx;
		});
		
	});	
	
	// 클릭 시 form 제출
	$(function() {
		// 클릭 이벤트 
        $(".ic_comment_enter").click(function() {
			alert("!");
            $(this).closest('form[action="Test03.jsp"]').submit();
        });

        // Enter 키 입력 이벤트 
        $('textarea[name="textarea_comment"]').on('keyup', function(event) {
            // Enter 키가 눌렸는지 확인 && shift키가 동시에 놀리지 않았는지 확인 (줄바꿈으로 사용 가능)
			if (event.key === 'Enter' && !event.shiftKey) {
                event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
                $(this).closest('form[action="Test03.jsp"]').submit();
            }
        });
	});
	
//=====================================댓글 모두 보기 클릭=====================================			
	$(function() {
		// x 클릭 시 #div_side_message 사라지고 기존 화면대로 돌아감
		$("#div_side_msg_header > div > img:nth-child(3)").click(function() {
			$("#div_side_message").css('display','none');
			$("#div_side2").addClass('wide');
			$("#div_msg_input_body").addClass('wide');
		});
		// 댓글 모두 보기 클릭 시 사이드에 메시지창 생김
		$(".comment_count span").click(function() {
			$("#div_side_message").css('display','block');
			$("#div_side2").removeClass('wide');
			$("#div_msg_input_body").removeClass('wide');
		});
	});
	
//===================================== div_header =====================================			
	$(function() {		
		// 최상단의 [메뉴] 클릭 시  #pop_up_header_menu
		$("#div_header_right > div:nth-child(2)").click(function() {
			$("#pop_up_header_menu").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#pop_up_header_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});	
		// 최상단의 [환경설정] 클릭 시  #pop_up_header_setting
		$("#div_header_right > div:nth-child(1)").click(function() {
			$("#pop_up_header_setting").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#pop_up_header_setting").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});	
	});
	
//===================================== div_side1 =====================================	
	$(function() {		
		// 대화방 검색의 'x' 또는 바탕화면 클릭 시 
		$("#div_room_search > div").click(function() {
			$("#search_all_room").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
//			$("#div_grey_filter").click(function() {
//				$("#search_all_room").css('display','none');
//				$("#div_grey_filter").css('display','none');
//			});	
		$("#search_all_room > div:nth-child(1) > .exit").click(function() {
			$("#search_all_room").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
	});
				
	$(function() {				
		// 토픽 필터 - [이름순] 클릭 시 
		$("#div_topicroom_filter > div:nth-child(1)").click(function() {
			$(".list_view_option").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$(".list_view_option").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});	
		
		// 토픽 필터 - [이름순] - [이름 오름차순] 클릭 시 
		$(".list_view_option > div:nth-child(2)").click(function() {
			$(".list_view_option > div:nth-child(2) > .check").css('display','block');
			$(".list_view_option > div:nth-child(3) > .check").css('display','none');
		});
		// 토픽 필터 - [이름순] - [이름 내림차순] 클릭 시 
		$(".list_view_option > div:nth-child(3)").click(function() {
			$(".list_view_option > div:nth-child(3) > .check").css('display','block');
			$(".list_view_option > div:nth-child(2) > .check").css('display','none');
		});
	});
				
	$(function() {
		// 토픽 '+버튼' 클릭 시 
		$("#div_topicroom_list_header > div:nth-child(4)").click(function() {
			$("#div_topic_plus").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_topic_plus").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});	
 
		// 토픽 '+버튼' 클릭 -> 새로운 토픽 생성하기 클릭 시
		$("#div_topic_plus > div:nth-child(1)").click(function() {
			$("#div_topic_plus").css('display','none');
			$("#div_topic_create").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
//			$("#div_grey_filter").click(function() {
//				$("#div_topic_create").css('display','none');
//				$("#div_grey_filter").css('display','none');
//			});	
		$("#div_topic_create .exit").click(function() {
			$("#div_topic_create").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
		$("#div_topic_create > div:nth-child(3) > button:nth-child(2)").click(function() {
			$("#div_topic_create").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
 
			
		// 토픽 '+버튼' 클릭 -> 참여 가능한 토픽 보기 클릭 시
		$("#div_topic_plus > div:nth-child(2)").click(function() {
			$("#div_topic_plus").css('display','none');
			$("#div_topic_openlist").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
//			$("#div_grey_filter").click(function() {
//				$("#div_topic_openlist").css('display','none');
//				$("#div_grey_filter").css('display','none');
//			});	
		$("#div_topic_openlist .exit").click(function() {
			$("#div_topic_openlist").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
			
		// 토픽 '+버튼' 클릭 -> 참여 가능한 토픽 보기 -> +새 토픽 생성 클릭 시 왜 화면이 꺼지는가?????????????????
		$("#div_topic_openlist > div:nth-child(4) button").click(function() {
			$("#div_topic_openlist").css('display','none');
			$("#div_topic_create").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
			
		// 토픽 '+버튼' 클릭 -> 참여 가능한 토픽 보기 -> 정렬순서 클릭 시
		$("#div_topic_openlist > div:nth-child(3) > span").click(function() {
			if( $(".list_view_option").css('display') == 'none' ){
				$(".list_view_option").css('display','block');
			} else {
				$(".list_view_option").css('display','none');
			}
		});
	});
	
// chatGPT..와우... 토픽폴더 및 토픽 이름오름차순, 내림차순 하는 방법	
$(document).ready(function() {
    // list_view_option 클릭 이벤트 설정
    $(".list_view_option").children(":nth-child(3)").click(function() {
        // topic_folder 내림차순 정렬
        var folders = $(".topic_folder").sort(function(a, b) {
            var nameA = $(a).find("span.fl").first().text().toUpperCase();
            var nameB = $(b).find("span.fl").first().text().toUpperCase();
            return (nameA > nameB) ? -1 : (nameA < nameB) ? 1 : 0;
        });

        // 각 topic_folder 내의 topic_item 내림차순 정렬
        $(".topic_folder").each(function() {
            var topics = $(this).find(".topic_item").sort(function(a, b) {
                var nameA = $(a).find("span").text().toUpperCase();
                var nameB = $(b).find("span").text().toUpperCase();
                return (nameA > nameB) ? -1 : (nameA < nameB) ? 1 : 0;
            });
            $(this).children("div").last().append(topics);
        });

        // topic_item 내림차순 정렬
        var items = $("#div_topicroom_list_body .topic_item").sort(function(a, b) {
            var nameA = $(a).find("span").text().toUpperCase();
            var nameB = $(b).find("span").text().toUpperCase();
            return (nameA > nameB) ? -1 : (nameA < nameB) ? 1 : 0;
        });

        // topic_folder를 먼저 추가하고 그 다음에 topic_item을 추가
        $("#div_topicroom_list_body").empty().append(folders).append(items);
    });

    $(".list_view_option").children(":nth-child(2)").click(function() {
        // topic_folder 오름차순 정렬
        var folders = $(".topic_folder").sort(function(a, b) {
            var nameA = $(a).find("span.fl").first().text().toUpperCase();
            var nameB = $(b).find("span.fl").first().text().toUpperCase();
            return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
        });

        // 각 topic_folder 내의 topic_item 오름차순 정렬
        $(".topic_folder").each(function() {
            var topics = $(this).find(".topic_item").sort(function(a, b) {
                var nameA = $(a).find("span").text().toUpperCase();
                var nameB = $(b).find("span").text().toUpperCase();
                return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
            });
            $(this).children("div").last().append(topics);
        });

        // topic_item 오름차순 정렬
        var items = $("#div_topicroom_list_body .topic_item").sort(function(a, b) {
            var nameA = $(a).find("span").text().toUpperCase();
            var nameB = $(b).find("span").text().toUpperCase();
            return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
        });

        // topic_folder를 먼저 추가하고 그 다음에 topic_item을 추가
        $("#div_topicroom_list_body").empty().append(folders).append(items);
    });
});



					
	$(function() {				
		// 토픽 폴더 안의 토픽방 펼치기 및 접기
		$(".topic_folder > div:first-child").click(function() {
			let the_display = $(this).next().css('display');
			if(the_display == 'none') {
				if($(this).parents(".topic_folder").find(".topic_item").length==0) return;
				$(this).next().slideDown();
				$(this).find(".ic_topic_folder_open").css('display', 'block');
				$(this).find(".ic_topic_folder_close").css('display', 'none');
			} else {
				if($(this).parents(".topic_folder").find(".topic_item").length==0) return;
				$(this).next().slideUp();
				$(this).find(".ic_topic_folder_open").css('display', 'none');
				$(this).find(".ic_topic_folder_close").css('display', 'block');
			}
		});
	});
						
			
		/*	// 폴더이미지 클릭 시 
			$("#div_title > div:nth-child(2) > div:nth-child(4)").click(function(){
				if($("#alarm_on").css('display') === 'none'){
					$("#alarm_on").css('display','block');
					$("#alarm_off").css('display','none');
				} else {
					$("#alarm_on").css('display','none');
					$("#alarm_off").css('display','block');
				}
			});*/
	

			
//===================================== 토픽방 상단바 메뉴 =====================================						
	$(function() {	
		// 마우스 커서 올리면 채팅방 제목 및 설명 팝업 뜸
		$(".ic_information").mouseover(function(){
			$(".information_pop_up").css('display','block');
		});
		// 마우스 커서 벗어나면 채팅방 제목 및 설명 팝업 사라짐
		$(".ic_information").mouseout(function(){
			$(".information_pop_up").css('display','none');
		});
			
		// [참여 멤버] 클릭 시 
		$("#div_title > div:nth-child(2) > div:nth-child(1)").click(function() {
			$("#div_title_participants").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_title_participants").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});
	});	
	
	$(function() {			
		// [참여멤버] ".profile_power"쪽에 마우스 커서 올리면  
		// ".ic_exit_chatroom" 보이도록
		$("#div_title_participants .div_member_list").mouseover(function(){
			$("#div_title_participants .ic_exit_chatroom").css('display','block');
		});
		// 마우스 커서 벗어나면 채팅방 제목 및 설명 팝업 사라짐
		$("#div_title_participants .div_member_list").mouseout(function(){
			$("#div_title_participants .ic_exit_chatroom").css('display','none');
		});
			
		// [참여 멤버] 검색 시
		$("input[name='member_search']").keyup(function(e) {
			$(".div_not_exist").css('display', 'none');
			$(".invite_member").css('display', 'none');
			let input = $(this).val();
			
			if(input.length > 0) {	// (입력된 게 있으면)
				$("#div_title_participants .div_member_list .profile_name").each(function(idx, item) { 
					if($(item).text().indexOf(input) >= 0) {	// (값이 있으면)
						$(item).parents(".div_member_list").css('display', 'block');
						$(".div_not_exist").css('display', 'none');
						$(".invite_member").css('display', 'none');
					} else {   // $(item).text().indexOf(input) == -1  (값이 없으면)
						$(item).parents(".div_member_list").css('display', 'none');
						$(".div_not_exist").css('display', 'block');
						$(".invite_member").css('display', 'block');
					}
				});
			} else {  // input.length == 0  (입력된 게 없으면)
				$("#div_title_participants .div_member_list").css('display', 'block');
			}
		});
	});	
	
	$(function() {				
		// [알람이미지] 클릭 시 
		$("#div_title > div:nth-child(2) > div:nth-child(3)").click(function(){
			if($("#alarm_on").css('display') === 'none'){
				$("#alarm_on").css('display','block');
				$("#alarm_off").css('display','none');
			} else {
				$("#alarm_on").css('display','none');
				$("#alarm_off").css('display','block');
			}
		});
	});	
	
	$(function() {				
		// [더보기] 클릭 시
		$("#div_title > div:nth-child(2) > div:nth-child(4)").click(function() {
			$("#div_topicroom_more_menu").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_topicroom_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});
			
		// [더보기] - [공지등록] 클릭 시
		$("#div_topicroom_more_menu > div:nth-child(1)").click(function() {
			$("#div_topicroom_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
			$("#div_notice_register").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#div_notice_register .exit").click(function() {
			$("#div_notice_register").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#div_notice_register #close_window").click(function() {
			$("#div_notice_register").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
			
		// [더보기] - [공지등록] 클릭 - 메시지 입력 시 [등록하기] 버튼 활성화
		$("#div_notice_register textarea").keyup(function(){
			let content = $(this).val();
		    // 글자수 세기
		    if (content.length > 0 || content != '') {
				$("#new_noti_content").addClass('green_light');
		    } else {
				$("#new_noti_content").removeClass('green_light');			
			}	
		});
			
		// [더보기] - [정보변경하기] 클릭 시
		$("#div_topicroom_more_menu > div:nth-child(3)").click(function() {
			$("#div_topicroom_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
			$("#div_topic_update").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#div_topic_update .exit").click(function() {
			$("#div_topic_update").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#div_topic_update #update_cancel").click(function() {
			$("#div_topic_update").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
			
		// [더보기] - [정보변경하기] - 토픽 관리자에서 팀멤버 클릭 시 체크박스 체크됨 
		$(".div_member_list").click(function(){
			let checkbox = $(this).find("input[type='checkbox']");
			checkbox.prop("checked", !checkbox.prop("checked"));
		});
		
		// [더보기] - [정보변경하기] - 토픽 관리자에서 팀멤버 클릭 시 토픽관리자 지정됨	
		$("#div_manager_participants .div_member_list").click(function() {
			let cnt = 0;
			let names = "";
			$("#div_manager_participants input[type='checkbox']").each(function(idx, item) {
				if($(item).prop('checked') == true) {
					cnt++;
					if(names.length>0)
						names += ", ";
					names += $(item).parent().find(".profile_name").text();
				}
			});
			$("#div_manager_participants > div:nth-child(2) > button:nth-child(2) > span").text(cnt);
			// 나열
			$("#div_topic_update .div_topic_manager_checkbox > span").text(names);
		});
			
		// [더보기] - [정보변경하기] - 토픽 관리자에서 팀멤버 검색
		$("input[name='member_search']").keyup(function(e) {
			let input = $(this).val();
			
			if(input.length > 0) {
				$("#div_manager_participants .div_member_list .profile_name").each(function(idx, item) { 
					if($(item).text().indexOf(input) >= 0) {
						$(item).parents(".div_member_list").css('display', 'block');
					} else {   // $(item).text().indexOf(input) == -1
						$(item).parents(".div_member_list").css('display', 'none');
					}
				});
			} else {  // input.length == 0
				$("#div_manager_participants .div_member_list").css('display', 'block');
			}
		});
			
		// [더보기] - [토픽 삭제하기] 클릭 시
		$("#div_topicroom_more_menu > div:nth-child(4)").click(function() {
			$("#div_topicroom_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
			$("#delete_topic_pop_up").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#delete_topic_pop_up .btn_cancel").click(function() {
			$("#delete_topic_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#delete_topic_pop_up .btn_danger").click(function() {
			$("#delete_topic_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
			
		// [더보기] - [토픽 나가기] 클릭 시
		$("#div_topicroom_more_menu > div:nth-child(5)").click(function() {
			$("#div_topicroom_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
			$("#exit_topic_pop_up").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#exit_topic_pop_up .btn_cancel").click(function() {
			$("#exit_topic_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#topic_exit_pop_up .btn_ok").click(function() {
			$("#exit_topic_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
	});	
	
	$(function() {				
		// 설명창
		$("[data-toggle='tooltip']").each(function(idx, item) {
			$(item).attr('data-placement', 'bottom');
		});
		$('[data-toggle="tooltip"]').tooltip();
	});	
	
	
//=====================================토픽방  내부=====================================
	$(function() {	
		// 토픽방 - 등록된 공지 - [더보기] 클릭
		$("#div_room_notice_view > .notice_header .ic_more_menu").click(function(){
			$("#div_notice_more_menu").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_notice_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});
			
		// 토픽방 - 등록된 공지 - [더보기] - [숨기기] 클릭 시
		$("#div_notice_more_menu > div:nth-child(1) ").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_notice_more_menu").css('display','none');
			$("#div_room_notice_view").css('display','none');
			$(".ic_notice_register_box").css('display','block');
		});
		$(".ic_notice_register_box").click(function() {
			$(".ic_notice_register_box").css('display','none');
			$("#div_room_notice_view").css('display','block');
		});
		// 토픽방 - 등록된 공지 - [더보기] - [삭제하기] 클릭 시
		$("#div_notice_more_menu > div:nth-child(3) ").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_notice_more_menu").css('display','none');
			$("#div_grey_filter").css('display','block');
			$("#delete_noti_pop_up").css('display','block');
		});	
		$("#delete_noti_pop_up .btn_cancel").click(function() {
			$("#delete_noti_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#delete_noti_pop_up .btn_ok").click(function() {
			$("#delete_noti_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
			/* 1. 게시글 삭제하는 기능 구현 필요 */
		});		
			
	});	
	
	// 토픽방 내부 하단의 게시글 등록 아이콘 클릭 시 팝업창 띄우고 & 없애기
	$(function() {				
		$(".ic_write_board_box").click(function() {
			$("#div_write_topic_board").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#div_write_topic_board .exit").click(function() {
			$("#div_write_topic_board").css('display','none');
			$("#div_grey_filter").css('display','none');
		});			
		$("#create_cancel").click(function() {
			$("#div_write_topic_board").css('display','none');
			$("#div_grey_filter").css('display','none');
		});			
	});	
	
	// 토픽글 메시지 입력 시 [등록하기] 버튼 활성화
	$(function() {	
		$("#div_write_topic_board textarea").keyup(function(){
			let content = $(this).val();
		    // 글자수 세기
		    if (content.length > 0 || content != '') {
				$("#create_complete").addClass('green_light');
		    } else {
				$("#create_complete").removeClass('green_light');			
			}	
		});
	});	
		
	// 토픽방 내부 하단의 위쪽 화살표 아이콘 클릭 시 토픽방 내부 상단으로 이동하기
	$(document).ready(function() {
	  // 아이콘 클릭 이벤트 설정
	  $(".ic_view_current_board_box").click(function() {
	    // div_content의 스크롤을 최상단으로 설정
	    $("#div_content").animate({ scrollTop: 0 }, "slow");
	  });
	});
	
	$(function() {				
		// 토픽글 마우스 커서 올리면(마우스오버) [더보기] 뜸	
		$(".div_content_center").mouseover(function(){
			$(this).find(".more_menu_box").css('display','block');
		});
		$(".div_content_center").mouseout(function(){
			$(this).find(".more_menu_box").css('display','none');
		});
		
		// 토픽글 [더보기] 클릭
		$(".div_content_center .more_menu_box").click(function(){
			$(this).next("#div_board_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
//			$(this).next().next("#div_board_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_board_more_menu_mine").css('display','none');	// 내 토픽글의 [더보기]
//			$("#div_board_more_menu_other").css('display','none');	// 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','none');
		});
	});	
	
	$(function() {			
		// 토픽방 - 토픽글 - [더보기] - [공지등록] 클릭 시
		$("#div_board_more_menu_mine > div:nth-child(1)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_board_more_menu_mine").css('display','none');
			$("#div_notice_register").css('display','block');
		});
		// 토픽방 - 토픽글 - [더보기] - [즐겨찾기] 클릭 시
		$("#div_board_more_menu_mine > div:nth-child(2)").click(function() {
			$("#div_transparent_filter").css('display','none');
			$("#div_board_more_menu_mine").css('display','none');
			/* 1. 즐겨찾기 노란색, 2. 즐겨찾기 등록 기능 */
		});
		// 토픽방 - 토픽글 - [더보기] - [복사] 클릭 시
		$("#div_board_more_menu_mine > div:nth-child(3)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_board_more_menu_mine").css('display','none');
			/* 1. 복사 기능 */
		});
		// 토픽방 - 토픽글 - [더보기] - [수정] 클릭 시
		$("#div_board_more_menu_mine > div:nth-child(4)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_board_more_menu_mine").css('display','none');
			/* 1. 게시글 수정 팝업창이 뜨도록 */
		});
		// 토픽방 - 토픽글 - [더보기] - [삭제] 클릭 시
		$("#div_board_more_menu_mine > div:nth-child(5)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_board_more_menu_mine").css('display','none');
			/* 1. 삭제하겠냐는 문구 뜨게 #delete_board_pop_up 팝업창 만들기 */
			$("#delete_board_pop_up").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#delete_board_pop_up .btn_cancel").click(function() {
			$("#delete_board_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#delete_board_pop_up .btn_danger").click(function() {
			$("#delete_board_pop_up").css('display','none');
			$("#div_grey_filter").css('display','none');
			/* 1. 게시글 삭제하는 기능 구현 필요 */
		});	
	});
	
	$(function() {				
		// 토픽댓글 마우스 커서 올리면(마우스오버) [더보기] 뜸	
		$(".div_comment").mouseover(function(){
			$(this).find(".more_menu_box").css('display','block');
		});
		$(".div_comment").mouseout(function(){
			$(this).find(".more_menu_box").css('display','none');
		});
		
		// 토픽댓글 [더보기] 클릭
		$(".div_comment .more_menu_box").click(function(){
			$(this).next("#div_comment_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
//			$(this).next().next("#div_comment_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_comment_more_menu_mine").css('display','none');	// 내 토픽글의 [더보기]
//			$("#div_comment_more_menu_other").css('display','none');	// 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','none');
		});
	});	
	
	// 파일 null일 경우 display:none 처리하는 방법
	$(document).ready(function() {
	  // (토픽글) 모든 .article_file 요소를 반복 처리
	  $(".article_file").each(function() {
	    // 현재 .article_file 요소의 텍스트 내용을 가져옴
	    let fileIdx = $(this).text().trim();
	    
	    // 텍스트 내용이 비어 있으면 (null 또는 "")
	    if (fileIdx === "") {
	      // 현재 .article_file 요소를 display: none으로 숨김
	      $(this).css("display", "none");
	    }
	  });

	  // (토픽댓글) 모든 .comment_file 요소를 반복 처리
	  $(".comment_file").each(function() {
	    // 현재 .comment_file 요소의 텍스트 내용을 가져옴
	    let fileIdx = $(this).text().trim();
	    
	    // 텍스트 내용이 비어 있으면 (null 또는 "")
	    if (fileIdx === "") {
	      // 현재 .comment_file 요소를 display: none으로 숨김
	      $(this).css("display", "none");
	    }
	  });
	});

	
	
//=====================================  =====================================
			
			
//=====================================채팅방 상단바 메뉴=====================================			
//			// 채팅 멤버 클릭 시 
//			$("#div_title > div:nth-child(2) > div:nth-child(1)").click(function() {
//				$("#div_participants").css('display','block');
//				$("#div_transparent_filter").css('display','block');
//			});
//			$("#div_transparent_filter").click(function() {
//				$("#div_participants").css('display','none');
//				$("#div_transparent_filter").css('display','none');
//			});
//			
//			
//			// 채팅 시작하기 '클릭' 시 팝업창 뜨도록
//			$("#div_title > div:nth-child(2) > div:nth-child(3)").click(function() {
//				$("#div_chat_start").css('display','block');
//				$("#div_grey_filter").css('display', 'block');
//			});
//			
//			// 채팅 시작하기에서  'x 클릭' 시 팝업창 꺼지도록 
//			$("#div_chat_start > div:nth-child(1) > .exit").click(function(){
//				$("#div_chat_start").css('display', 'none');
//				$("#div_grey_filter").css('display', 'none');
//			});
//			$("[name='close_window']").click(function() {
//				$("#div_chat_start").css('display', 'none');
//				$("#div_grey_filter").css('display', 'none');
//			});
//			
//			// 채팅 시작하기에서 '선택된 멤버' 선택 -> '팀멤버'로 이동
//			$(document).on("click", ".choice_member > div:nth-child(3)", function() {
//				let choice_member_tag = $(this).parent();
//				let member_idx = choice_member_tag.attr("member_idx");
//				let name = choice_member_tag.find("span:nth-child(2)").text();
//				let img_url = choice_member_tag.find(".profile_img").attr("src");
//				let team_name = choice_member_tag.attr("team_name");
//				let position = choice_member_tag.attr("position");
//				let power = choice_member_tag.attr("power");
//				let str = '<div class="div_member_list2" member_idx="' + member_idx + '">'
//						+ '<div class="fl">'
//						+ '<img class="profile_img" src="' + img_url + '"/>'
//						+ '</div>'
//						+ '<div class="fl">'
//						+ '<div class="profile_name">' + name + '</div>'
//						+ '<div>'
//						+ '<span>' + team_name + ' / </span>'
//						+ '<span> &nbsp;' + position + '</span>'
//						+ '</div>'
//						+ '</div>'
//						+ '<div class="fr">'
//						+ '<div class="profile_power power_orange">' + power + '</div>'
//						+ '</div>'
//						+ '<div style="clear:both;"></div>'
//						+ '</div>';
//				$(this).parent().remove();   // .choice_member를 삭제.
//				
//				let insert_where = insert_here(name);
//				if(insert_where == null) {
//					$("#start_member").prepend(str);  // 첫째 자식으로 추가.
//				} else {
//					insert_where.after(str);
//				}
//				if($(".chremoveember").length==0) {
//					$("[name='invite_member']").removeClass('green_light');
//				}
//			});
//			
//			// 채팅 시작하기에서 '팀멤버' 선택 -> '선택된 멤버'로 이동
//			$(document).on("click", ".div_member_list2", function() {
//				let name = $(this).find(".profile_name").text();
//				let img_url = $(this).find(".profile_img").attr("src");
//				let member_idx = $(this).attr("member_idx");
//				let team_name = $(this).find("div > div > span:nth-child(1)").text().replace("/","").trim();
//				let position = $(this).find("div > div > span:nth-child(2)").text().trim();
//				let power = $(this).find("div:nth-child(3) > div").text();
//
//				let str = '<div class="choice_member fl" member_idx="' + member_idx + '"'
//						+ 'team_name="' + team_name + '" position="' + position + '" power="' + power + '">'
//						+ '<img class="profile_img" src="' + img_url + '"/>'
//						+ '<span>' + name + '</span>'				
//						+ '<div></div>'
//						+ '</div>';
//				$("#start_choice > div:last-child").before(str);  // 바로 이전 태그(형제)로 추가.
//				$(this).remove();
//				$("[name='invite_member']").addClass('green_light');
//			});			
		
