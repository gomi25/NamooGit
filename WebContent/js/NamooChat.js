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
		
		$(function() {
//=====================================댓글 모두 보기 클릭=====================================			
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
		// [대화방 검색] 팝업창
		$("#div_room_search > div").click(function() {
			$("#search_all_room").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
//			$("#div_grey_filter").click(function() {
//				$("#search_all_room").css('display','none');
//				$("#div_grey_filter").css('display','none');
//			});	

		// [대화방 검색] 검색 시
		$("#search_all_room input[name='member_search']").keyup(function(e) {
			$(".div_not_exist").hide();
			$(".invite_member").hide();
			let input = $(this).val();
			if(input.length > 0) {	// (입력된 게 있으면)
				let found = false;  // 찾았냐? 있냐?
				$("#all_room_list .room_name").each(function(idx, item) { 
					if($(item).text().indexOf(input) >= 0) {	// (값이 있으면)
						$(item).parents("#all_room_list > div").show();
						found = true;
					} else {   // $(item).text().indexOf(input) == -1  (값이 없으면)
						$(item).parents("#all_room_list > div").hide();
					}
				});
				if(found) {
					$(".div_not_exist").hide();
					$(".invite_member").hide();
				} else {
					$(".div_not_exist").show();
					$(".invite_member").show();
				}
			} else {  // input.length == 0  (입력된 게 없으면)
				$("#all_room_list > div").show();
			}
		});
		
		// [대화방 검색] 'x' 클릭 시 
		$("#search_all_room > div:nth-child(1) > .exit").click(function() {
			$("#search_all_room").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
		
		// [대화방 검색] '+새 토픽 생성하기' 클릭 시
		$("#search_all_room > div:nth-child(4) > button:nth-child(2)").click(function() {
			$("#div_topic_create").show();
			$("#search_all_room").hide();
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
	
/****************************** 토픽 [+] 팝업창 ******************************/					
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
	
/****************************** [새 토픽 생성] 팝업창 ******************************/		
	$(function() {	
		// 토픽 '+버튼' 클릭 -> 새로운 토픽 생성하기 클릭 시
		$("#div_topic_plus > div:nth-child(1)").click(function() {
			$("#div_topic_plus").css('display','none');
			$("#div_topic_create").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#div_topic_create .exit").click(function() {
			$("#div_topic_create").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
		$("#div_topic_create > div:nth-child(3) > button:nth-child(2)").click(function() {
			$("#div_topic_create").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
	});
	
	$(function() {		
		// [새 토픽 생성] - "폴더 선택(옵션)" - 폴더목록 팝업창 뜸
		$(".folder_select_option").click(function() {
			$(".folder_list_pop_up").show();
		});		
		$(".folder_list_pop_up").click(function() {
			$(".folder_list_pop_up").hide();
		});		
		
		// [새 토픽 생성] - "폴더 선택(옵션)" - 폴더목록 팝업창 - 폴더 클릭 시 선택됨
		$(".folder_list_pop_up > div > div").click(function() {
			let folder_name = $(this).find("div:nth-child(2)").text();
			$(this).parents(".folder_list_pop_up").siblings(".folder_select_option").find("span").text(folder_name);
		});
		
		$(".my_folder_list").click(function() {
		    // 클릭된 폴더의 topic_folder_idx 값을 가져와 hidden input 태그에 설정
		   let topicFolderIdx = $(this).find("div:nth-child(2)").attr("topic_folder_idx");
		    $("#selected_folder").val(topicFolderIdx);
		    // 필요에 따라 다른 동작 수행 가능
		});

		
		// [새 토픽 생성] - "공개 여부" 체크박스 클릭 시 초록색으로 됨
		function resetStyles() {
	        $('#topic_public_div, #topic_private_div').css({
	            'border-color': '#ddd'
	        });
	        $('#topic_public_div > div, #topic_private_div > div').css({
	            'filter': 'invert(73%) sepia(52%) saturate(8%) hue-rotate(9deg) brightness(97%) contrast(91%)'
	        });
	        $('#topic_public_div > label, #topic_private_div > label').css({
	            'color': ''
	        });
	    }

		// [새 토픽 생성] - "공개 여부": 공개 체크	
	    $('#topic_public_div').click(function() {
	        $('#topic_public').prop('checked', true);
	        resetStyles();
	        $('#topic_public_div').css('border-color', '#00C473');
	        $('#topic_public_div > div').css('filter', 'invert(71%) sepia(69%) saturate(5840%) hue-rotate(123deg) brightness(99%) contrast(104%)');
	        $('#topic_public_div > label').css('color', '#00C473');
	    });
		// [새 토픽 생성] - "공개 여부": 비공개 체크		
	    $('#topic_private_div').click(function() {
	        $('#topic_private').prop('checked', true);
	        resetStyles();
	        $('#topic_private_div').css('border-color', '#00C473');
	        $('#topic_private_div > div').css('filter', 'invert(71%) sepia(69%) saturate(5840%) hue-rotate(123deg) brightness(99%) contrast(104%)');
	        $('#topic_private_div > label').css('color', '#00C473');
	    });

		// [새 토픽 생성] 토픽 이름 입력 시 글자수 체크 및 글자수 제한
		$("#div_topic_create input[name='name']").keyup(function() {
		    let name = $(this).val();
		
		    // 글자수 세기
		    if (name.length == 0 || name == '') {
		        $("#div_topic_create > div:nth-child(2) > div:nth-child(1) > .text_current_value").text('0');
		    } else {
		        $("#div_topic_create > div:nth-child(2) > div:nth-child(1) > .text_current_value").text(name.length);
		    }
		
		    // 글자수 제한
		    if (name.length > 60) {
		        $(this).val($(this).val().substring(0, 60));
		        alert('이름은 60자까지 입력이 가능합니다.');
		    }
		});
		
		// [새 토픽 생성] 토픽 설명 입력 시 글자수 체크 및 글자수 제한
		$("#div_topic_create textarea[name='info']").keyup(function() {
		    let info = $(this).val();
		
		    // 글자수 세기
		    if (info.length == 0 || info == '') {
		        $("#div_topic_create > div:nth-child(2) > div:nth-child(2) > .text_current_value").text('0');
		    } else {
		        $("#div_topic_create > div:nth-child(2) > div:nth-child(2) > .text_current_value").text(info.length);
		    }
		
		    // 글자수 제한
		    if (info.length > 300) {
		        $(this).val($(this).val().substring(0, 300));
		        alert('설명은 300자까지 입력이 가능합니다.');
		    }
		});
		
		// [새 토픽 생성] 토픽 이름 입력, 공개 여부 선택 시 [생성하기] 버튼 활성화
		function toggleCreateTopicButton() {
		    const topicName = $('#input_new_topic_name').val().trim();
		    const topicOpen = $('input[name="topic_open"]:checked').val();
			console.log("Name:", topicName);  // 디버깅용 콘솔 로그
		    console.log("Topic Open:", topicOpen);  // 디버깅용 콘솔 로그        
		
		    if (topicName.length > 0 && topicOpen !== undefined) {
				console.log("Button enabled");
		        $('#new_topic_create').addClass('green_light');
		    } else {
				console.log("Button disabled");
		        $('#new_topic_create').removeClass('green_light');
		    }
		}
		
		$('#input_new_topic_name').on('input', toggleCreateTopicButton);
		$('input[name="topic_open"]').on('change', toggleCreateTopicButton);	

		// 초기화 함수
		function resetForm() {
		    // 폴더 선택 옵션 초기화
		    $(".folder_select_option span").text("토픽을 생성 할 폴더를 선택해 주세요.");
		    // 폴더 선택 팝업창 숨김
		    $(".folder_list_pop_up").hide();
		    // 공개 여부 초기화
		    $('input[name="topic_open"]').prop('checked', false);
		    resetStyles();
			// input과 textarea 초기화
		    $('input[type="text"]').val('');
		    $('textarea').val('');
		}
		// 닫기 버튼과 exit 클릭 시 초기화
		$(".exit, #new_topic_exit").click(function() {
		    resetForm();
		});
	});
		


/****************************** [토픽 정렬] 팝업창 ******************************/				
// 토픽폴더 및 토픽 이름오름차순, 내림차순 하는 방법	
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


/****************************** 토픽목록 ******************************/	
	// 토픽 클릭 시 페이지 이동 
	$(function(){
		$(".topic_item > span").click(function () {
			let topicIdx = $(this).parent(".topic_item").attr("topic_idx");
			// 페이지 이동 --> "Ex11Detail.jsp?bno=213"로 이동
			// location.href = 새 주소(문자열);   ← 얘는 무조건 'GET방식'임
			location.href = context_path + "/NamooTopic.jsp?topicIdx=" + topicIdx;
		});
	});	

/****************************** 프로젝트 목록 ******************************/	
	// 프로젝트 클릭 시 페이지 이동 (예시)
	$(function(){
		$(".topic_item > span").click(function () {
			let projectIdx = $(this).parent(".topic_item").attr("project_idx");
			location.href = context_path + "/NamooTopic.jsp?projectIdx=" + projectIdx;
		});
	});	

/****************************** 채팅 목록 ******************************/	
	// 채팅방 클릭 시 페이지 이동 
	$(function(){
		$(".topic_item > span").click(function () {
			let chatroomIdx = $(this).parent(".topic_item").attr("chatroom_idx");
			location.href = context_path + "/NamooTopic.jsp?chatroomIdx=" + chatroomIdx;
		});
	});	
	
/****************************** 목록의 즐겨찾기 ******************************/		
	// 토픽 목록에 [즐겨찾기] 아이콘 클릭 시 즐겨찾기 등록 및 해제
	$(function(){
	 	// 이벤트 위임을 사용하여 동적으로 생성된 요소에도 이벤트가 바인딩되도록 수정
   		$(document).on("click", ".topic_item > div:first-child", function(e){
			alert("클림됨!");
			let idx = $(this).parent(".topic_item").attr("topic_idx");
			let params = { idx : idx, something: "topic_idx" } // 객체 생성
			let _this = $(this);
			if(_this.hasClass("ic_bookmark_on")===true){
				$.ajax({
					type : "POST",
					url : "AjaxRemoveBookmarkServlet",
					data : params,
					success : function(res) {
						alert("on -> off : " + res.result);
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
						$("#div_title > div:nth-child(1) > div:nth-child(2)").removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						alert("통신 실패.");
					}
				});
			} else if(_this.hasClass("ic_bookmark_off")===true) {
				$.ajax({
					type : "POST",
					url : "AjaxAddBookmarkServlet",
					data : params,
					success : function(res) {
						alert("off -> on : " + res.result);
						_this.removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
						$("#div_title > div:nth-child(1) > div:nth-child(2)").removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("통신 실패.");
					}
				});
			}
		});
	});
	
/* 여기까지 일단 완료 7.22(월) 17시 */	
	
/* 여기부터 이어서  */		

//===================================== div_side2 =====================================				
	
//=====================================채팅방 상단바 메뉴=====================================			
			// 채팅 멤버 클릭 시 
			$("#div_title > div:nth-child(2) > div:nth-child(1)").click(function() {
				$(".div_participants").css('display','block');
				$("#div_transparent_filter").css('display','block');
			});
			$("#div_transparent_filter").click(function() {
				$(".div_participants").css('display','none');
				$("#div_transparent_filter").css('display','none');
			});
			
			
			// 채팅 시작하기 '클릭' 시 팝업창 뜨도록
			$("#div_title > div:nth-child(2) > div:nth-child(3)").click(function() {
				$("#div_chat_start").css('display','block');
				$("#div_grey_filter").css('display', 'block');
			});
			
			// 채팅 시작하기에서  'x 클릭' 시 팝업창 꺼지도록 
			$("#div_chat_start > div:nth-child(1) > .exit").click(function(){
				$("#div_chat_start").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
			$("[name='close_window']").click(function() {
				$("#div_chat_start").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
			
			// 채팅 시작하기에서 '선택된 멤버' 선택 -> '팀멤버'로 이동
			$(document).on("click", ".choice_member > div:nth-child(3)", function() {
				let choice_member_tag = $(this).parent();
				let member_idx = choice_member_tag.attr("member_idx");
				let name = choice_member_tag.find("span:nth-child(2)").text();
				let img_url = choice_member_tag.find(".profile_img").attr("src");
				let team_name = choice_member_tag.attr("team_name");
				let position = choice_member_tag.attr("position");
				let power = choice_member_tag.attr("power");
				let str = '<div class="div_member_list2" member_idx="' + member_idx + '">'
						+ '<div class="fl">'
						+ '<img class="profile_img" src="' + img_url + '"/>'
						+ '</div>'
						+ '<div class="fl">'
						+ '<div class="profile_name">' + name + '</div>'
						+ '<div>'
						+ '<span>' + team_name + ' / </span>'
						+ '<span> &nbsp;' + position + '</span>'
						+ '</div>'
						+ '</div>'
						+ '<div class="fr">'
						+ '<div class="profile_power power_orange">' + power + '</div>'
						+ '</div>'
						+ '<div style="clear:both;"></div>'
						+ '</div>';
				$(this).parent().remove();   // .choice_member를 삭제.
				
				let insert_where = insert_here(name);
				if(insert_where == null) {
					$("#start_member").prepend(str);  // 첫째 자식으로 추가.
				} else {
					insert_where.after(str);
				}
				if($(".chremoveember").length==0) {
					$("[name='invite_member']").removeClass('green_light');
				}
			});
			
			// 채팅 시작하기에서 '팀멤버' 선택 -> '선택된 멤버'로 이동
			$(document).on("click", ".div_member_list2", function() {
				let name = $(this).find(".profile_name").text();
				let img_url = $(this).find(".profile_img").attr("src");
				let member_idx = $(this).attr("member_idx");
				let team_name = $(this).find("div > div > span:nth-child(1)").text().replace("/","").trim();
				let position = $(this).find("div > div > span:nth-child(2)").text().trim();
				let power = $(this).find("div:nth-child(3) > div").text();

				let str = '<div class="choice_member fl" member_idx="' + member_idx + '"'
						+ 'team_name="' + team_name + '" position="' + position + '" power="' + power + '">'
						+ '<img class="profile_img" src="' + img_url + '"/>'
						+ '<span>' + name + '</span>'				
						+ '<div></div>'
						+ '</div>';
				$("#start_choice > div:last-child").before(str);  // 바로 이전 태그(형제)로 추가.
				$(this).remove();
				$("[name='invite_member']").addClass('green_light');
			});
			
			
			// 알람이미지 클릭 시 
			$("#div_title > div:nth-child(2) > div:nth-child(4)").click(function(){
				if($("#alarm_on").css('display') === 'none'){
					$("#alarm_on").css('display','block');
					$("#alarm_off").css('display','none');
				} else {
					$("#alarm_on").css('display','none');
					$("#alarm_off").css('display','block');
				}
			});
			
			
			// 더보기 클릭 시
			$("#div_title > div:nth-child(2) > div:nth-child(5)").click(function() {
				$("#div_chatroom_more_menu").css('display','block');
				$("#div_transparent_filter").css('display','block');
			});
			$("#div_transparent_filter").click(function() {
				$("#div_chatroom_more_menu").css('display','none');
				$("#div_transparent_filter").css('display','none');
			});
			
			
			// 설명창
			$("[data-toggle='tooltip']").each(function(idx, item) {
				$(item).attr('data-placement', 'bottom');
			});
			$('[data-toggle="tooltip"]').tooltip();
			

//=====================================채팅방  내부=====================================			
			// 채팅방 상단바 더보기 클릭 - 공지등록하기 클릭 시 팝업창 뜨도록 
			$("#div_chatroom_more_menu > div:nth-child(1)").click(function() {
				$("#div_notice_register").css('display','block');
				$("#div_grey_filter").css('display', 'block');
				$("#div_chatroom_more_menu").css('display', 'none');
			});
			
			// 공지 등록하기에서  'x 클릭' 시 팝업창 꺼지도록 
			$("#div_notice_register .exit").click(function(){
				$("#div_notice_register").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
			$("[name='close_window']").click(function() {
				$("#div_notice_register").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
			
		});
