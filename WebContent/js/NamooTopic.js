
	//===================================== div_header =====================================			
	$(function() {
		$("#div_logo").click(function() {
			location.href = context_path + "/NamooMainTool.jsp";
		});
		$("#pop_up_header_setting > div:nth-child(4)").click(function() {
			location.href = context_path + "/NamooMainTool.jsp";
		});
	});		
	
	$(function() {
		// [멤버 초대하기] '클릭' 시 팝업창 뜨도록
		$("#div_title > div:nth-child(2) > div:nth-child(2)").click(function() {
			$("#div_invite_member").show();
			$("#div_grey_filter").show();
		});
	});
	
	$(function() {
		// [멤버 초대하기]에서  'x 클릭' 시 팝업창 꺼지도록 
		$("#div_invite_member > div:nth-child(1) > .exit").click(function(){
			$("#div_invite_member").css('display', 'none');
			$("#div_grey_filter").css('display', 'none');
		});
		$("[name='close_window']").click(function() {
			$("#div_invite_member").css('display', 'none');
			$("#div_grey_filter").css('display', 'none');
		});
		
		// 멤버 이름 검색 시
		$("#div_invite_member input[name='member_search']").keyup(function(e) {
			$(".div_not_exist").hide();
			$(".invite_member").hide();
			let input = $(this).val();
			if(input.length > 0) {	// (입력된 게 있으면)
				let found = false;  // 찾았냐? 있냐?
				$("#div_invite_member .div_member_list2 .profile_name").each(function(idx, item) { 
					if($(item).text().indexOf(input) >= 0) {	// (값이 있으면)
						$(item).parents(".div_member_list2").show();
						found = true;
					} else {   // $(item).text().indexOf(input) == -1  (값이 없으면)
						$(item).parents(".div_member_list2").hide();
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
				$("#div_invite_member .div_member_list2").show();
			}
		});
	});
	
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
		// [멤버 초대하기]에서 '선택된 멤버' 선택 -> '팀멤버'로 이동
		$(document).on("click", ".choice_member > div:nth-child(3)", function() {
			console.log("'선택된 멤버' 선택 -> '팀멤버'");
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
				$("#team_member").prepend(str);  // 첫째 자식으로 추가.
			} else {
				insert_where.after(str);
			}
			if($(".choice_member").length==0) {
				$("[name='invite_member']").removeClass('green_light');
			}
			updateSelectedMembers(); // 선택된 멤버 목록을 업데이트.
		});
		
		// 멤버 초대하기에서 '팀멤버' 선택 -> '선택된 멤버'로 이동
		$(document).on("click", ".div_member_list2", function() {
			console.log("'팀멤버' 선택 -> '선택된 멤버'");
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
			updateSelectedMembers(); // 선택된 멤버 목록을 업데이트.
		});
		
		// 선택된 멤버 목록을 업데이트하는 함수
	    function updateSelectedMembers() {
			console.log("선택된 멤버 목록 업데이트");
	        let selectedMembers = [];
	        $("#start_choice .choice_member").each(function() {
	            selectedMembers.push($(this).attr("member_idx"));
	        });
			console.log("현재 선택된 멤버: ", selectedMembers);
	        $("#selected_members").val(selectedMembers.join(","));
			$(".count_memeber").text(selectedMembers.length);     
		}	

		$("#btn_invite_member").click(function(event) {
		    if ($("[name='invite_member']").hasClass('green_light')) {
		        updateSelectedMembers(); // 선택된 멤버 목록을 최신 상태로 업데이트.
		        $("#form_invite_member").submit(); // 폼을 제출.
		    } else {
		        event.preventDefault(); // green_light 클래스가 없으면 폼 제출을 막음.
		        alert("초대할 멤버를 선택해 주세요."); // 사용자에게 알림
		    }
		});
	
		 // 초기화 버튼 클릭 시 선택된 멤버 수 업데이트
	    $("input[type='reset']").click(function() {
	        setTimeout(function() {
	            updateSelectedMembers(); // 선택된 멤버 목록을 초기화 후 업데이트.
	        }, 0);
	    });
	
	    // 초기 상태에서도 선택된 멤버 수 업데이트
	    updateSelectedMembers();
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
		$("#div_topicroom_list_header .ic_plus").click(function() {
			$("#div_topic_plus").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$("#div_topic_plus").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});	
			
		// 토픽 '+버튼' 클릭 -> 참여 가능한 토픽 보기 클릭 시
		$("#div_topic_plus > div:nth-child(3)").click(function() {
			$("#div_topic_plus").css('display','none');
			$("#div_topic_openlist").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		
		$("#div_topic_openlist .exit").click(function() {
			$("#div_topic_openlist").css('display','none');
			$("#div_grey_filter").css('display','none');
		});	
			
		// 토픽 '+버튼' 클릭 -> 참여 가능한 토픽 보기
		$("#div_topic_openlist > div:nth-child(4) button").click(function() {
			$("#div_topic_openlist").css('display','none');
			$("#div_topic_create").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		
		// 토픽 '+버튼' 클릭 -> 폴더 생성하기
		$(document).on("click", "#div_topic_plus > div:nth-child(2)", function(e){
			$("#div_transparent_filter").hide();	
			$("#div_topic_plus").hide();	
			let teamIdx = team_idx;
			let memberIdx = member_idx; 
			let params = { team: teamIdx, member : memberIdx} 
			
			$.ajax({
				type : "POST",
				url : "AjaxCreateTopicFolderServlet",
				data : params,
				success : function(res) {
					let str = 
					  '<div class="topic_folder">'
					+ '	<div class="div_folder_item" topicFolderIdx="' + res.topicFolderIdx +'">'  
					+ '		<div class="ic_topic_folder_open fl"></div>'  
					+ '		<div class="ic_topic_folder_close fl"></div>'  
					+ '		<span class="span_folder_name fl" contenteditable="false">' + '새폴더' + '</span>'  
					+ '		<span class="fl">' + '0' + '</span>'  
					+ '		<div class="ic_topic_folder_more_menu fr"></div>'  
					+ '		<div class="topic_folder_more_menu">'  
					+ '			<div>이 폴더에 토픽 생성하기</div>'  
					+ '			<div>폴더 이름 변경하기</div>'  
					+ '			<div>폴더 삭제하기</div>'  
					+ '		</div>'  
					+ '	</div>'
					+ '	<div>'
					+ '	</div>'
					+ '</div>';
					
					$("#div_topicroom_list_body").prepend(str);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert("통신 실패.");
				}
			});
		});
		
		// 토픽 '+버튼' 클릭 -> 참여 가능한 토픽 보기 -> 정렬순서 클릭 시
		$("#div_topic_openlist > div:nth-child(3) > span").click(function() {
			if( $(".list_view_option").css('display') == 'none' ){
				$(".list_view_option").css('display','block');
			} else {
				$(".list_view_option").css('display','none');
			}
		});
//	});
	
//	$(function() {				
	/********** 토픽 [+] 팝업창  - [더보기] 팝업창 **********/			
		let isMenuOpen = false;		
	
		// 토픽폴더 [더보기] 클릭 
		$(document).on("click", ".topic_folder .ic_topic_folder_more_menu", function(e){
			e.stopPropagation();
			isMenuOpen = true; // 메뉴가 열렸음을 표시
			$("#div_transparent_filter").show();
			$(this).next(".topic_folder_more_menu").show();
		});	
		
		$(document).on("click", "#div_transparent_filter", function(e){		
			$("#div_transparent_filter").hide();
			$(".topic_folder_more_menu").hide();
		});
		
		$(document).on("click", ".topic_folder_more_menu > div:first-child", function(e){		
			$("#div_transparent_filter").hide();
			$(".topic_folder_more_menu").hide();
			$("#").show();
		});
	
		// 토픽 폴더 안의 토픽방 펼치기 및 접기
		$(".topic_folder > div:first-child").click(function() {
			// .topic_folder_more_menu가 열려 있으면 펼치기, 접기 실행 X
			if ($(".topic_folder_more_menu").is(":visible")) {
				return;
			}	
			if (isMenuOpen) {
				isMenuOpen = false; // 플래그 초기화
				return;
			}
			
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

	    // '수정' 버튼 클릭 시 댓글 수정 활성화
		$(document).on("click", ".topic_folder_more_menu > div:nth-child(2)", function(e) {
			$(".topic_folder_more_menu").hide();		
	        // '수정' 버튼이 클릭된 div_comment 내에서 comment_item 요소를 찾음
	        let updateItem = $(this).closest('.div_folder_item').find('.span_folder_name');
	        
	        updateItem.attr('contenteditable', 'true');
	        
	        // 편집 가능한 스타일을 추가할 수 있음 (예: 테두리, 배경색 등)
	        updateItem.css({
	            'border': '1px solid #ccc',
	            'border-radius': '6px',
	            'outline': 'none',
	            'padding': '5px',
	            'background-color': '#f9f9f9',
				'line-height': '12px'
	        });
	
	        // focus를 줘서 바로 수정할 수 있도록 함
	        updateItem.focus();
	    });

		// span_folder_name에서 Enter 키 입력 이벤트 처리
	    $(".span_folder_name").on('keydown', function(event) {
	        // Enter 키 눌렀을 때 동작
	        if (event.key === 'Enter' && !event.shiftKey) {
	            event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
	            
	            // 수정된 댓글 내용 가져오기
	            let topicFolderIdx = $(this).closest('.div_folder_item').attr('topicFolderIdx');
	            let folderName = $(this).text().trim(); // 텍스트 내용 가져오기
				let params = { 
					topic_folder_idx : topicFolderIdx,  
					folder_name : folderName
				};
				let _this = $(this);
	            $.ajax({
	                type: "POST",
	                url: "AjaxUpdateTopicFolderServlet",
	                data: params,
	                success: function(res) {
						_this.attr('contenteditable', 'false').attr('style','');
	                },
	                error: function(XMLHttpRequest, textStatus, errorThrown) {
	                    alert("통신 실패.");
	                }
	            });
	        }
	    });

		// 토픽폴더 [더보기] - [폴더 삭제하기] 클릭 시 
		$(document).on("click", ".topic_folder_more_menu > div:nth-child(3)", function(e){
			$("#div_transparent_filter").hide();
			let topicFolderIdx = $(this).closest('.topic_folder').find('div:first-child').attr('topicFolderIdx');
			let _this = $(this).closest('.topic_folder');
			let params = { topic_folder_idx: topicFolderIdx }; 
			$.ajax({
				type : "POST",
				url : "AjaxDeleteTopicFolderServlet",
				data : params,
				success : function(res) {
					_this.remove();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert("통신 실패.");
				}
			});
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
        	toggleCreateTopicButton(); // 버튼 상태 업데이트
	    });
		// [새 토픽 생성] - "공개 여부": 비공개 체크		
	    $('#topic_private_div').click(function() {
	        $('#topic_private').prop('checked', true);
	        resetStyles();
	        $('#topic_private_div').css('border-color', '#00C473');
	        $('#topic_private_div > div').css('filter', 'invert(71%) sepia(69%) saturate(5840%) hue-rotate(123deg) brightness(99%) contrast(104%)');
	        $('#topic_private_div > label').css('color', '#00C473');
			toggleCreateTopicButton(); // 버튼 상태 업데이트
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
		    const topicOpen = $('input[name="topic_open"]').filter(':checked').val();
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
			$('#new_topic_create').removeClass('green_light'); // 버튼 비활성화 초기화
		
		}
		// 닫기 버튼과 exit 클릭 시 초기화
		$(".exit, #new_topic_cancel").click(function() {
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

	
			
	
	/****************************** 토픽목록 ******************************/	
	// 토픽 클릭 시 페이지 이동 
	$(function(){
		$("#div_topicroom_list_body .topic_item > span").click(function () {
			let topicIdx = $(this).parent(".topic_item").attr("topic_idx");
//			location.href = context_path + "/NamooTopic.jsp?topicIdx=" + topicIdx;
			location.href = context_path + "/Controller?command=topic_choice&teamIdx=" + team_idx + "&topicIdx=" + topicIdx;
		});
	});	

	/****************************** 프로젝트 목록 ******************************/	
	// 프로젝트 클릭 시 페이지 이동 (예시)
	$(function(){
		$("#div_project_list_body .topic_item > span").click(function () {
			let projectIdx = $(this).parent(".topic_item").attr("project_idx");
			location.href = context_path + "/NamooTopic.jsp?projectIdx=" + projectIdx;
		});
	});	

	/****************************** 채팅 목록 ******************************/	
	// 채팅방 클릭 시 페이지 이동 
	$(function(){
		$("#div_chatroom_list_body > .topic_item > span").click(function () {
			let chatroomIdx = $(this).parent(".topic_item").attr("chatroom_idx");
//			location.href = context_path + "/NamooChat.jsp?chatroomIdx=" + chatroomIdx;
			location.href = context_path + "/Controller?command=chatroom_choice&teamIdx=" + team_idx + "&chatroomIdx=" + chatroomIdx;
		});
	});		
	
	// 채팅목록의 '+' 버튼 클릭 시 "채팅 시작하기" 팝업창 뜸
	$(function() {				
			// 채팅 시작하기(채팅방 생성) '클릭' 시 팝업창 뜨도록
			$("#div_chatroom_list_header > .ic_plus").click(function() {
				$("#div_create_chatroom").css('display','block');
				$("#div_grey_filter").css('display', 'block');
			});
			// 채팅 시작하기(채팅방 생성) 'x'클릭 시 팝업창 꺼지도록 
			$("#div_create_chatroom > div:nth-child(1) > .exit").click(function(){
				$("#div_create_chatroom").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
			$("#div_create_chatroom > div:nth-child(3) > button:nth-child(2)").click(function() {
				$("#div_create_chatroom").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
	});	
	
	$(function() {			
		// [채팅방 생성] 채팅방 이름 입력 시 글자수 체크 및 글자수 제한
		$("#div_create_chatroom input[name='name']").keyup(function() {
		    let name = $(this).val();
		
		    // 글자수 세기
		    if (name.length == 0 || name == '') {
		        $("#div_create_chatroom > div:nth-child(2) > div:nth-child(1) > .text_current_value").text('0');
		    } else {
		        $("#div_create_chatroom > div:nth-child(2) > div:nth-child(1) > .text_current_value").text(name.length);
		    }
		
		    // 글자수 제한
		    if (name.length > 60) {
		        $(this).val($(this).val().substring(0, 60));
		        alert('이름은 60자까지 입력이 가능합니다.');
		    }
		});
		
		// [채팅방 생성] 채팅방 설명 입력 시 글자수 체크 및 글자수 제한
		$("#div_create_chatroom textarea[name='info']").keyup(function() {
		    let info = $(this).val();
		
		    // 글자수 세기
		    if (info.length == 0 || info == '') {
		        $("#div_create_chatroom > div:nth-child(2) > div:nth-child(2) > .text_current_value").text('0');
		    } else {
		        $("#div_create_chatroom > div:nth-child(2) > div:nth-child(2) > .text_current_value").text(info.length);
		    }
		
		    // 글자수 제한
		    if (info.length > 300) {
		        $(this).val($(this).val().substring(0, 300));
		        alert('설명은 300자까지 입력이 가능합니다.');
		    }
		});
		
		// [채팅방 생성] 채팅방 이름, 설명 입력 시 [생성하기] 버튼 활성화
		function toggleCreateChatroomButton() {
		    const chatroomName = $('#div_create_chatroom #input_name').val().trim();
		    const chatroomInfo = $('#div_create_chatroom #textarea_info').val().trim();
		
		    if (chatroomName.length > 0 && chatroomInfo.length > 0) {
				console.log("Button enabled");
		        $('#btn_create_chatroom').addClass('green_light');
		    } else {
				console.log("Button disabled");
		        $('#btn_create_chatroom').removeClass('green_light');
		    }
		}
		$('#div_create_chatroom #input_name').on('input', toggleCreateChatroomButton);
		$('#div_create_chatroom #textarea_info').on('input', toggleCreateChatroomButton);
	});		
	
	
	
	
	
	
	
	

/****************************** 목록의 즐겨찾기 ******************************/		
	// 토픽 목록에 [즐겨찾기] 아이콘 클릭 시 즐겨찾기 등록 및 해제
	$(function(){
	 	// 이벤트 위임을 사용하여 동적으로 생성된 요소에도 이벤트가 바인딩되도록 수정
   		$(document).on("click", ".topic_item > div:first-child", function(e){
			let idx = $(this).parent(".topic_item").attr("topic_idx");
			let params = { idx : idx, something: "topic_idx" } // 객체 생성
			let _this = $(this);
			if(_this.hasClass("ic_bookmark_on")===true){
				$.ajax({
					type : "POST",
					url : "AjaxRemoveBookmarkServlet",
					data : params,
					success : function(res) {
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
//===================================== 토픽방 상단바 =====================================						
	$(function() {	
		// 토픽 - 즐겨찾기 등록 및 해제
        $("#div_title > div:nth-child(1) > div:nth-child(2)").click(function(){
			let idx = $(this).attr("topic_idx");
			let params = { idx : idx, something: "topic_idx" }  // 객체 생성
			let _this = $(this);
			
			// 즐겨찾기 해제  ic_bookmark_on 클릭 시 ic_bookmark_off로 
			if(_this.hasClass("ic_bookmark_on")===true){
				// on -> off
				//location.href = context_path + '/jsp/RemoveBookmark.jsp?something=topic_idx&idx=' + idx;
				$.ajax({
	                type : "POST",            // HTTP method type(GET, POST) 형식이다.
	                url : "AjaxRemoveBookmarkServlet",    // ?  		
	                data : params,            // JSON 형식의 데이터이다.
	                success : function(res){ // 비동기통신의 성공일경우 success콜백으로 들어옴. 'res'는 응답받은 데이터.
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
						$(".topic_item[topic_idx='"+params.idx+"']").find("div:first-child").removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
	                },
	                error : function(XMLHttpRequest, textStatus, errorThrown){ // 비동기 통신이 실패할경우 error 콜백으로 들어옴.
	                    alert("통신 실패.")
	                }
	            });
			}
			// 즐겨찾기 등록 ic_bookmark_off 클릭 시 ic_bookmark_on으로 
			else if(_this.hasClass("ic_bookmark_off")===true) {
				// off -> on
				// location.href = context_path + '/jsp/AddBookmark.jsp?something=topic_idx&idx=' + idx;
				$.ajax({
	                type : "POST",            // HTTP method type(GET, POST) 형식이다.
	                url : "AjaxAddBookmarkServlet",    // ?  		
	                data : params,            // JSON 형식의 데이터이다.
	                success : function(res){ // 비동기통신의 성공일경우 success콜백으로 들어옴. 'res'는 응답받은 데이터.
						_this.removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
						$(".topic_item[topic_idx='"+params.idx+"']").find("div:first-child").removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
	                },
	                error : function(XMLHttpRequest, textStatus, errorThrown){ // 비동기 통신이 실패할경우 error 콜백으로 들어옴.
	                    alert("통신 실패.")
	                }
	            });
			}
//            $(this).toggleClass("ic_bookmark_on ic_bookmark_off");
        });

		// 마우스 커서 올리면 채팅방 제목 및 설명 팝업 뜸
		$(document).on("mouseover", ".ic_information", function(){
			$(".information_pop_up").css('display','block');
		});
		// 마우스 커서 벗어나면 채팅방 제목 및 설명 팝업 사라짐
		$(document).on("mouseout", ".ic_information", function(){
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
			$("#div_title_participants .ic_exit_chatroom").show();
		});
		// 마우스 커서 벗어나면 채팅방 제목 및 설명 팝업 사라짐
		$("#div_title_participants .div_member_list").mouseout(function(){
			$("#div_title_participants .ic_exit_chatroom").hide();
		});
		
		$(".ic_exit_chatroom").click(function(){
			$("#div_title_participants").hide();
			
			let removeMemberIdx = $(this).parent(".div_member_list").attr("member_idx");
			let removeMemberImg = $(this).parent(".div_member_list").find(".profile_img").attr("src");
			let removeMemberName = $(this).parent(".div_member_list").find(".profile_name").text();
			
			$("#div_grey_filter").show();
			$("#remove_topicMember_pop_up").show();
			$("#remove_member_info > img").attr('src', removeMemberImg);
			$("#remove_member_info > span").text(removeMemberName);
			
			$("#remove_topicMember_pop_up .btn_ok").click(function(){
				$("#div_grey_filter").hide();
				$("#remove_topicMember_pop_up").hide();
				location.href = context_path + '/jsp/RemoveMemberInThisTopic.jsp?removeMemberIdx=' + removeMemberIdx;
			});
			$("#remove_topicMember_pop_up .btn_cancel").click(function(){
				$("#div_grey_filter").hide();
				$("#remove_topicMember_pop_up").hide();
			});
		});
		
		// [참여 멤버] 검색 시
		$("#div_title_participants input[name='member_search']").keyup(function(e) {
			$(".div_not_exist").css('display', 'none');
			$(".invite_member").css('display', 'none');
			let input = $(this).val();
			if(input.length > 0) {	// (입력된 게 있으면)
				let found = false;  // 찾았냐? 있냐?
				$("#div_title_participants .div_member_list .profile_name").each(function(idx, item) { 
					if($(item).text().indexOf(input) >= 0) {	// (값이 있으면)
						$(item).parents(".div_member_list").css('display', 'block');
						found = true;
					} else {   // $(item).text().indexOf(input) == -1  (값이 없으면)
						$(item).parents(".div_member_list").css('display', 'none');
					}
				});
				if(found) {
					$(".div_not_exist").css('display', 'none');
					$(".invite_member").css('display', 'none');
				} else {
					$(".div_not_exist").css('display', 'block');
					$(".invite_member").css('display', 'block');
				}
			} else {  // input.length == 0  (입력된 게 없으면)
				$("#div_title_participants .div_member_list").css('display', 'block');
			}
		});
		
		// 검색결과 없을 때 "+토픽에 멤버 초대하기" 클릭 시 [멤버 초대하기] 팝업창 뜸
		$("#div_title_participants .invite_member").click(function(){
			$("#div_title_participants").hide();
			$("#div_transparent_filter").hide();
			$("#div_invite_member").show();
			$("#div_grey_filter").show();
		});
		
		// 사용자에 초록색불 들어오게
		$("#div_title_participants .div_member_list").each(function() {
			let memberIdx = $(this).attr("member_idx");
			if(memberIdx == $("#div_profile_box").attr("member_idx")) {
				$(this).find(".on_user").css('display','block');
			} else {
				$(this).find(".on_user").css('display','none');
			}
		});
	});	
/* 여기까지 일단 완료 7.24(수) 11시 */	
	
/* 여기부터 이어서  */				
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
		$("#div_notice_register .close_window").click(function() {
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
		
	});
	
//		 [더보기] - [공지등록] 'textarea'의 입력을 감지하고, 하단 여백에 글씨가 작성되지 않도록 제어.
//		 			css도 추가 → #div_write_topic_board .ic_file_clip_box {pointer-events: none; /* 마우스 이벤트를 차단 */} 
//	    $(document).ready(function() {
//	      $('#div_notice_register textarea').on('input', function() {
//	        const textarea = $(this);
//	        const maxHeight = textarea.height() - 50; // 텍스트 영역 높이 - 하단 여백
//	        if (textarea[0].scrollHeight > maxHeight) {
//	          // 글자가 하단 여백을 넘어가면 스크롤을 조정
//			  // 텍스트의 높이가 textarea 높이에서 하단 여백을 뺀 값보다 크면, 스크롤을 조정하여 하단 여백에 텍스트가 작성되지 않도록
//	          textarea.scrollTop(textarea[0].scrollHeight - maxHeight); 
//	        }
//	      });
//	    });
		
	$(function() {			
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
		
		// [더보기] - [정보변경하기] - 토픽관리자 박스 클릭 시 팝업창 뜸
		$(".div_topic_manager_checkbox").click(function(){
			$("#div_manager_participants").show();
		});
		
		$(document).on('click', '#div_manager_participants .btn_cancel', function() {
		    $("#div_manager_participants").hide();
		});
			
		// [더보기] - [정보변경하기] - 토픽 관리자에서 팀멤버 검색
		$("#div_manager_participants input[name='member_search']").keyup(function(e) {
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

		// [더보기] - [정보변경하기] - 내보내기 박스 클릭 시 팝업창 뜸
		$(".div_topic_member_checkbox").click(function(){
			if($("#div_remove_participants").css('display') === 'none'){
				$("#div_remove_participants").show();
			} 
		});
		
		$(document).on('click', '#div_remove_participants .btn_cancel', function() {
		    $("#div_remove_participants").hide();
		});
	});	
	
	$(function() {		
		// [더보기] - [토픽 삭제하기] 클릭 시
		$("#div_topicroom_more_menu > div:nth-child(4)").click(function() {
			$("#div_topicroom_more_menu").css('display','none');
			$("#div_transparent_filter").css('display','none');
			$("#delete_topic_pop_up").css('display','block');
			$("#div_grey_filter").css('display','block');
		});
		$("#delete_topic_pop_up .btn_cancel").click(function() {
			$("#delete_topic_pop_up").hide();
			$("#div_grey_filter").hide();
		});
		$("#delete_topic_pop_up .btn_danger").click(function() {
			$("#delete_topic_pop_up").hide();
			$("#div_grey_filter").hide();
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
	
	
//==================================================================================
//                                   < 토픽방  내부 >
//==================================================================================


/****************************** 토픽방 - 등록된 공지 - [더보기] ******************************/	
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
		
		// 토픽방 - 등록된 공지 - [더보기] - [수정하기] 클릭 시
		$("#div_notice_more_menu > div:nth-child(2) ").click(function(){
			$("#div_transparent_filter").css('display','none');
			$("#div_notice_more_menu").css('display','none');
			$("#div_grey_filter").css('display','block');
			$("#div_notice_update").css('display','block');
		});
		$("#div_notice_update .exit").click(function(){
			$("#div_notice_update").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		$("#div_notice_update .close_window").click(function(){
			$("#div_notice_update").css('display','none');
			$("#div_grey_filter").css('display','none');
		});
		
		// [공지수정] 메시지 입력 시 [수정] 버튼 활성화 
		$("#div_notice_update textarea").keyup(function(){
			let content = $(this).val();
		    // 글자수 세기
		    if (content.length > 0 || content != '') {
				$("#update_noti_content").addClass('green_light');
		    } else {
				$("#update_noti_content").removeClass('green_light');			
			}	
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
		});		
			
	});	
	
	/****************************** [게시글 작성] 팝업창 ******************************/	
	// 토픽방 내부 하단의 게시글 등록 아이콘 클릭 시 [게시글 작성] 팝업창 띄우고 & 없애기
	$(function() {				
		$(".ic_write_board_box").click(function() {
			$("#div_write_topic_board").show();
			$("#div_grey_filter").show();
		});
		$("#div_write_topic_board .exit").click(function() {
			$("#div_write_topic_board").hide();
			$("#div_grey_filter").hide();
		});			
		$("#create_cancel").click(function() {
			$("#div_write_topic_board").hide();
			$("#div_grey_filter").hide();
		});			
	});	
	
	// 토픽글 작성 - 포커스 설정 및 글자수 체크
	$(function() {	
		const $writeTopicBoardSpace = $('#write_topic_board_space');
	
	    // #write_topic_board_space의 내용을 검사하여 비어 있으면 empty 클래스를 추가하고, 비어 있지 않으면 empty 클래스를 제거
		// contenteditable 요소가 비어 있을 때 CSS에서 data-placeholder 속성의 값을 표시
	    function togglePlaceholder() {
	        if ($writeTopicBoardSpace.text().trim() === '') {
	            $writeTopicBoardSpace.addClass('empty');
	        } else {
	            $writeTopicBoardSpace.removeClass('empty');
	        }
	    }
	
	    // 문서가 준비되면 togglePlaceholder 함수를 호출하여 초기 상태를 설정
	    togglePlaceholder();
	
	    // #write_topic_board_space에서 input 이벤트가 발생할 때마다 togglePlaceholder 함수를 호출하여 placeholder 텍스트를 동적으로 관리
	    $writeTopicBoardSpace.on('input', function() {
	        togglePlaceholder();
	    });

		// [게시글 작성] 토픽글 제목 입력 시 글자수 체크 및 글자수 제한
		$("#div_write_topic_board input[name='topic_board_title']").keyup(function(){
			let title = $(this).val();
			
			// 글자수 세기
		    if (title.length == 0 || title == '') {
				$("#div_write_topic_board > div:nth-child(2) >  div:nth-child(1) > .text_current_value").text('0');
		    } else {
				$("#div_write_topic_board > div:nth-child(2) >  div:nth-child(1) > .text_current_value").text(title.length);	
			}	
			
			// 글자수 제한
			if(title.length > 50) {
				let truncatedContent = title.substring(0, 50);
                $(this).val(truncatedContent);
                alert('제목은 50자까지 입력이 가능합니다.');

				// 커서를 맨 끝으로 이동
                let range = document.createRange();       // 새로운 Range 객체를 생성
				let sel = window.getSelection();          // 현재 Selection 객체를 가져옴
				range.selectNodeContents(this);           // Range 객체의 범위를 this(현재 div)의 모든 콘텐츠로 설정
				range.collapse(false);                    // Range 객체를 끝 위치로 압축하여 커서를 끝으로 이동
				sel.removeAllRanges();                    // 현재 선택된 모든 범위를 제거
				sel.addRange(range);                      // 새로운 Range 객체를 현재 선택으로 추가
				currentLength = 50;                       // 현재 길이를 50으로 설정
			}
		});
		
		// [게시글 작성] 본문 입력하는 곳에 focus 시 초록색 테두리 생김
        $('#write_topic_board_space').on('focus', function() {
            $('#write_topic_board_box').css('border', '1px solid #00C473');
        });

        $('#write_topic_board_space').on('blur', function() {
            $('#write_topic_board_box').css('border', '1px solid #ddd');
        });

		// [게시글 작성] 토픽글 본문 글자수를 실시간으로 체크하고, 글자수가 5000자를 초과하지 않도록 제한
        $("#write_topic_board_space").on('keyup', function(){
            let content = $(this).text();
            
            // 글자수 세기
            if (content.length == 0 || content == '') {
                $("#div_write_topic_board > div:nth-child(2) > div:nth-child(2) > .text_current_value").text('0');
            } else {
                $("#div_write_topic_board > div:nth-child(2) > div:nth-child(2) > .text_current_value").text(content.length);	
            }	
            
            // 글자수 제한
            if(content.length > 5000) {
 				let truncatedContent = content.substring(0, 5000);
                $(this).text(truncatedContent);
                alert('본문은 5000자까지 입력이 가능합니다.');

				// 커서를 맨 끝으로 이동
                let range = document.createRange();         // 새로운 Range 객체를 생성
				let sel = window.getSelection();            // 현재 Selection 객체를 가져옴
				range.selectNodeContents(this);             // Range 객체의 범위를 this(현재 div)의 모든 콘텐츠로 설정
				range.collapse(false);                      // Range 객체를 끝 위치로 압축하여 커서를 끝으로 이동
				sel.removeAllRanges();                      // 현재 선택된 모든 범위를 제거
				sel.addRange(range);                        // 새로운 Range 객체를 현재 선택으로 추가
				currentLength = 5000;                       // 현재 길이를 5000으로 설정
            }
        });
		
		// 파일 업로드 시 - 미리보기
		$("#upload_file_tboard").on("change", function(e) {
			let files = e.target.files;
			let reader = new FileReader();
		    let fileName = files[0].name;
			$(".upload_file_box").show();
    		$(".upload_file_list > div:nth-child(2)").text(fileName);
			
			reader.readAsDataURL(files[0]);
			reader.onload = function(e) {
				$(".upload_file_list img").attr("src", e.target.result);
				$(".upload_file_list img").css({
		            "display": "block",
		            "object-fit": "cover",
		        });
			}
		});
	});	
	
	
	// [게시글 작성] 토픽글 제목+본문 모두 입력 시 [등록하기] 버튼 활성화
    $(document).ready(function() {
		function toggleCreateButton() {	// toggleCreateButton 함수는 제목(input_name)과 본문(textarea_content)의 값을 동시에 확인
		    const title = $('#input_board_title').val().trim();
		    const content = $('#write_topic_board_space').text().trim();
		    if (title.length > 0 && content.length > 0) {
		      $('#create_complete').addClass('green_light');
		    } else {
		      $('#create_complete').removeClass('green_light');
		    }
		}
		
		$('#input_board_title').on('input', toggleCreateButton);	// input 이벤트를 사용하여 두 입력 필드에서 변경 사항을 감지하고, 
		$('#write_topic_board_space').on('input', toggleCreateButton);	// toggleCreateButton 함수를 호출함

		// form 제출
		$("#form_board_write").submit(function(event) {
	       $('#hidden_board_content').val($('#write_topic_board_space').text().trim());
//		    $("#hidden_topic_idx").val();
//		    $("#hidden_topic_idx").val();
		});	

//		// 전송버튼 클릭 시 
//		$(".create_complete").click(function() {
//			// 토픽글 폼 제출될 때 "입력한 내용" & "채팅방idx" 값 제출 
//			$("#form_board_write").submit();
//	    });		
		
    });
		
	// 토픽방 내부 하단의 위쪽 화살표 아이콘 클릭 시 토픽방 내부 상단으로 이동하기
	$(document).ready(function() {
	  // 아이콘 클릭 이벤트 설정
	  $(".ic_view_current_board_box").click(function() {
	    // div_content의 스크롤을 최상단으로 설정
	    $("#div_content").animate({ scrollTop: 0 }, "slow");
	  });
	});

	/****************************** [게시글 수정] 팝업창 ******************************/	
	$(function() {		
		let topicBoardIdx;		
		// 사용자의 토픽글 - [더보기] - [수정] - [게시글 수정] 팝업창 띄우고 & 없애기
		$(".div_board_more_menu_mine > div:nth-child(4)").click(function() {
			$("#div_transparent_filter").css('display','none');
			$(".div_board_more_menu_mine").css('display','none');
			$("#div_update_topic_board").show();
			$("#div_grey_filter").show();
			
			// topic_board_idx값 넣고
			topicBoardIdx = $(this).closest('.content_board').attr('topic_board_idx');
			let title =  $(this).parents(".div_content_center").find(".article_title > span").text();
			$("#input_board_title_update").val(title);
			let content =  $(this).parents(".div_content_center").find(".article_contents > span").text();
			$("#update_topic_board_space").text(content);
        
	        // hidden input에 topicBoardIdx 설정
	        $("#hidden_topic_board_idx").val(topicBoardIdx);			
		});
		
//		// [게시글 수정] 팝업창에 있는 "수정" 버튼 클릭 시 팝업창 띄우기
//		$("#div_update_topic_board #btn_update_topic_board").click(function() {
//			// form 제출
//	        $("#update_topic_form").submit();
//		});
		
		// 팝업창 내 "x" 버튼 클릭 
		$("#div_update_topic_board .exit").click(function() {
			$("#div_update_topic_board").hide();
			$("#div_grey_filter").hide();
		});		
		// 팝업창 내 "취소" 버튼 클릭 	
		$("#btn_update_cancel").click(function() {
			$("#div_update_topic_board").hide();
			$("#div_grey_filter").hide();
		});			
	});	
	
	$(function() {	
		const $updateTopicBoardSpace = $('#update_topic_board_space');
	
	    // #write_topic_board_space의 내용을 검사하여 비어 있으면 empty 클래스를 추가하고, 비어 있지 않으면 empty 클래스를 제거
		// contenteditable 요소가 비어 있을 때 CSS에서 data-placeholder 속성의 값을 표시
	    function togglePlaceholder() {
	        if ($updateTopicBoardSpace.text().trim() === '') {
	            $updateTopicBoardSpace.addClass('empty');
	        } else {
	            $updateTopicBoardSpace.removeClass('empty');
	        }
	    }
	
	    // 문서가 준비되면 togglePlaceholder 함수를 호출하여 초기 상태를 설정
	    togglePlaceholder();
	
	    // #write_topic_board_space에서 input 이벤트가 발생할 때마다 togglePlaceholder 함수를 호출하여 placeholder 텍스트를 동적으로 관리
	    $updateTopicBoardSpace.on('input', function() {
	        togglePlaceholder();
	    });
		
		// [게시글 수정] 제목 입력 시 글자수 체크 및 글자수 제한
		$("#div_update_topic_board input[name='topic_board_title']").keyup(function(){
			let title = $(this).val();
			
			// 글자수 세기
		    if (title.length == 0 || title == '') {
				$("#div_update_topic_board > div:nth-child(2) >  div:nth-child(1) > .text_current_value").text('0');
		    } else {
				$("#div_update_topic_board > div:nth-child(2) >  div:nth-child(1) > .text_current_value").text(title.length);	
			}	
			
			// 글자수 제한
			if(title.length > 50) {
				let truncatedContent = title.substring(0, 50);
                $(this).val(truncatedContent);
                alert('제목은 50자까지 입력이 가능합니다.');

				// 커서를 맨 끝으로 이동
                let range = document.createRange();       // 새로운 Range 객체를 생성
				let sel = window.getSelection();          // 현재 Selection 객체를 가져옴
				range.selectNodeContents(this);           // Range 객체의 범위를 this(현재 div)의 모든 콘텐츠로 설정
				range.collapse(false);                    // Range 객체를 끝 위치로 압축하여 커서를 끝으로 이동
				sel.removeAllRanges();                    // 현재 선택된 모든 범위를 제거
				sel.addRange(range);                      // 새로운 Range 객체를 현재 선택으로 추가
				currentLength = 50;    					  // 현재 길이를 50으로 설정
			}
		});
		
	    // [게시글 수정] 입력하는 곳에 focus 시 초록색 테두리 생김
        $('#update_topic_board_space').on('focus', function() {
            $('#update_topic_board_box').css('border', '1px solid #00C473');
        });

        $('#update_topic_board_space').on('blur', function() {
            $('#update_topic_board_box').css('border', '1px solid #ddd');
        });
		
		// [게시글 수정] 본문 입력 시 글자수 체크 및 글자수 제한
		$("#update_topic_board_space").on('keyup', function(){
			let content = $(this).text();
			
			// 글자수 세기
		    if (content.length == 0 || content == '') {
				$("#div_update_topic_board > div:nth-child(2) >  div:nth-child(2) > .text_current_value").text('0');
		    } else {
				$("#div_update_topic_board > div:nth-child(2) >  div:nth-child(2) > .text_current_value").text(content.length);	
			}	
			
			// 글자수 제한
			if(content.length > 5000) {
				let truncatedContent = content.substring(0, 5000);
                $(this).text(truncatedContent);
                alert('본문은 5000자까지 입력이 가능합니다.');

				// 커서를 맨 끝으로 이동
                let range = document.createRange();         // 새로운 Range 객체를 생성
				let sel = window.getSelection();            // 현재 Selection 객체를 가져옴
				range.selectNodeContents(this);             // Range 객체의 범위를 this(현재 div)의 모든 콘텐츠로 설정
				range.collapse(false);                      // Range 객체를 끝 위치로 압축하여 커서를 끝으로 이동
				sel.removeAllRanges();                      // 현재 선택된 모든 범위를 제거
				sel.addRange(range);                        // 새로운 Range 객체를 현재 선택으로 추가
				currentLength = 5000;                       // 현재 길이를 5000으로 설정
			}
		});
		
	});	
	
	// [게시글 수정] 토픽글 제목+본문 모두 입력 시 [수정] 버튼 활성화
    $(document).ready(function() {
       function toggleUpdateButton() {	// toggleUpdateButton 함수는 제목(input_name)과 본문(textarea_content)의 값을 동시에 확인
         const title = $('#input_board_title_update').val().trim();
         const content = $('#update_topic_board_space').text().trim();
         if (title.length > 0 && content.length > 0) {
           $('#btn_update_topic_board').addClass('green_light');
         } else {
           $('#btn_update_topic_board').removeClass('green_light');
         }
       }

       $('#input_board_title_update').on('input', toggleUpdateButton);	// input 이벤트를 사용하여 두 입력 필드에서 변경 사항을 감지하고, 
       $('#update_topic_board_space').on('input', toggleUpdateButton);	// toggleUpdateButton 함수를 호출함
    	// 폼 제출 시 div 내용을 hidden input에 동기화
	    $('#updateTopicBoardForm').on('submit', function() {
	        $('#hidden_board_update').val($('#update_topic_board_space').text());
	    });
	});
		


//===================================== 토픽방 - 토픽글 - [입력창] =====================================
	// [입력창] - 토픽댓글 작성 
	$(function() {
		const $writeTopicCommentSpace = $('#write_topic_comment_space');
	
	    // #write_topic_board_space의 내용을 검사하여 비어 있으면 empty 클래스를 추가하고, 비어 있지 않으면 empty 클래스를 제거
		// contenteditable 요소가 비어 있을 때 CSS에서 data-placeholder 속성의 값을 표시
	    function togglePlaceholder() {
	        if ($writeTopicCommentSpace.text().trim() === '') {
	            $writeTopicCommentSpace.addClass('empty');
	        } else {
	            $writeTopicCommentSpace.removeClass('empty');
	        }
	    }
	
	    // 문서가 준비되면 togglePlaceholder 함수를 호출하여 초기 상태를 설정
	    togglePlaceholder();
	
	    // #write_topic_board_space에서 input 이벤트가 발생할 때마다 togglePlaceholder 함수를 호출하여 placeholder 텍스트를 동적으로 관리
	    $writeTopicCommentSpace.on('input', function() {
	        togglePlaceholder();
	    });
	});		
	
	
	$(function() {
		let topicCommentCnt;
		$(".ic_comment_enter").click(function() {
			let topicBoardIdx = $(this).parents(".content_board").attr("topic_board_idx");
			let topicComment = $(this).siblings(".write_topic_comment_space").text(); 
			let params = { 	topic_board_idx : topicBoardIdx, 
						   	topic_board_comment : topicComment,
							team_idx : team_idx,
							member_idx : member_idx };
			let _this = $(this);
			// 여기여기
			$.ajax({
		            type : "POST",           
		            url : "AjaxWriteTopicCommentServlet",	
	             	data : params,   
	             	success : function(res){  
						// 댓글수 span 요소
						let cnt_span = _this.closest(".div_comment_box").siblings(".div_comment_count").find("span:nth-child(2)");
						// 댓글수 +1
						topicCommentCnt = parseInt(cnt_span.text().trim()) +1;
						// 댓글수 span 요소의 값 변경
						cnt_span.text(topicCommentCnt);
						
						let topic_comment_idx = res.topicCommentIdx;
						let state = res.state;
						let profileImgUrl = $("#div_profile_box > img").attr("src");
						let name = $("#div_profile_box > div").text();
						/*let date = new Date();
						date = date.getFullYear() + "-" + (date.getMonth()<=9 ? "0" : "") + date.getMonth() + "-" + (date.getDate()<=9 ? "0" : "") 
							+ date.getDate() + " " + (date.getHours()>12 ? "오후 " + date.getHours()-12 : "오전 " + date.getHours()) + ":" + date.getMinutes();*/
							
						let date = new Date();
						let year = date.getFullYear();
						let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 01, 02, ..., 12
						let day = date.getDate().toString().padStart(2, '0'); // 01, 02, ..., 31
						let hours = date.getHours();
						let minutes = date.getMinutes().toString().padStart(2, '0'); // 00, 01, ..., 59
						
						// 12시간제로 변환
						let ampm = hours >= 12 ? '오후' : '오전';
						hours = hours % 12;
						hours = hours ? hours : 12; // 0시를 12시로 변경
						
						let formattedDate = `${year}-${month}-${day} ${ampm} ${hours}:${minutes}`;
	
						
						let str = '<div class="div_comment" topic_comment_idx="' + topic_comment_idx + '" writer="' + member_idx + '">' 
							+ '<div class="div_profile">' 
							+ '	<div class="fl">' 
							+ '		<img src="' + profileImgUrl + '"/>' 
							+ '	</div>' 
							+ '	<div class="fl">' 
							+ '		<span>' + name + '</span>' 
							+ '		<span> - ' + state + '</span>' 
							+ '		<span>' + formattedDate +'</span>' 
							+ '	</div>' 
							+ '</div>' 
							+ '<div class="comment_item" contenteditable="false">' 
							+ topicComment 
							+ '</div>' 
							+ '<div class="comment_file">' 
							//+ '파일업로드 부분은 나중에 언젠간 처리해야' 
							+ '	<img src=""/>' 
							+ '</div>' 
							+ '	<!-- 토픽댓글 [더보기] --> ' 
							+ '	<div class="more_menu_box">' 
							+ '		<div class="ic_more_menu"></div>' 
							+ '	</div>	' 
							+ '	<!-- 토픽댓글 [더보기] - [내 댓글 더보기 창] -->' 
							+ '	<div class="div_comment_more_menu_mine">' 
							+ '	 	<div>' 
							+ '	 		<div class="fl ic_bookmark_off"></div>' 
							+ '	 		<div class="ic_show_file fl"></div>' 
							+ '			<span class="fl">즐겨찾기</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_copy fl"></div>' 
							+ '			<span class="fl">복사</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_edit_info fl"></div>' 
							+ '			<span class="fl">수정</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_delete fl"></div>' 
							+ '			<span class="fl">삭제</span>' 
							+ '	 	</div>' 
							+ '	 </div> ' 
							+ '	<!-- 토픽댓글 [더보기] - [상대 댓글 더보기 창] --> ' 
							+ '	<div class="div_comment_more_menu_other">' 
							+ '	 	<div>' 
							+ '	 		<div class="fl ic_bookmark_off"></div>' 
							+ '			<span class="fl">즐겨찾기</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_copy fl"></div>' 
							+ '			<span class="fl">복사</span>'
							+ '	 	</div>'
							+ '	</div>' 
							+ '</div>';
						_this.closest(".div_comment_box").before(str);
						_this.siblings(".write_topic_comment_space").text('');
						
						/*// form 제출
						$("#form_update_file").submit(function(event) {
					        $("#form_update_file input[name='topicIdx']").val();
					        $("#form_update_file input[name='topic_comment_idx']").val(topic_comment_idx);
						});	
				        
				        // 폼 제출
				        $("#form_update_file").submit();*/
					},
	             	error : function(XMLHttpRequest, textStatus, errorThrown){ 
	                	    		alert("통신 실패.")
	             	}
	     	});
		});
		
		// Enter 키 입력 이벤트
		$(".write_topic_comment_space").on('keyup', function(event) {
			event.stopPropagation();
		    // Enter 키가 눌렸는지 확인 && shift키가 동시에 놀리지 않았는지 확인 (줄바꿈으로 사용 가능)
		    if (event.key === 'Enter' && !event.shiftKey) {
		        event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지

			let topicBoardIdx = $(this).parents(".content_board").attr("topic_board_idx");
			let topicComment = $(this).text(); 
			let params = { 	topic_board_idx : topicBoardIdx, 
						   	topic_board_comment : topicComment,
							team_idx : team_idx,
							member_idx : member_idx };
			let _this = $(this);
			// 여기여기
			$.ajax({
		            type : "POST",           
		            url : "AjaxWriteTopicCommentServlet",	
	             	data : params,   
	             	success : function(res){  
						console.log(res);
						//alert(res.result);   
												   
						// 댓글수 span 요소
						let cnt_span = _this.closest(".div_comment_box").siblings(".div_comment_count").find("span:nth-child(2)");
						// 댓글수 +1
						topicCommentCnt = parseInt(cnt_span.text().trim()) +1;
						// 댓글수 span 요소의 값 변경
						cnt_span.text(topicCommentCnt);
						
						let topic_comment_idx = res.topicCommentIdx;
						let state = res.state;
						let profileImgUrl = $("#div_profile_box > img").attr("src");
						let name = $("#div_profile_box > div").text();
						let date = new Date();
						let year = date.getFullYear();
						let month = date.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더해야 합니다.
						let day = date.getDate();
						let hours = date.getHours();
						let minutes = date.getMinutes();
						let period = hours >= 12 ? "오후" : "오전";
						hours = hours % 12 || 12; // 0을 12로 변환하고, 12시간 형식으로 변경합니다.
						
						month = month <= 9 ? "0" + month : month; // 두 자리 수로 만들기 위해 0을 추가합니다.
						day = day <= 9 ? "0" + day : day;
						minutes = minutes <= 9 ? "0" + minutes : minutes; // 두 자리 수로 만들기 위해 0을 추가합니다.
						
						let formattedDate = `${year}-${month}-${day} ${period} ${hours}:${minutes}`;


						let str = '<div class="div_comment" topic_comment_idx="' + topic_comment_idx + '" writer="' + member_idx + '">' 
							+ '<div class="div_profile">' 
							+ '	<div class="fl">' 
							+ '		<img src="' + profileImgUrl + '"/>' 
							+ '	</div>' 
							+ '	<div class="fl">' 
							+ '		<span>' + name + '</span>' 
							+ '		<span> - ' + state + '</span>' 
							+ '		<span>' + formattedDate +'</span>' 
							+ '	</div>' 
							+ '</div>' 
							+ '<div class="comment_item" contenteditable="false">' 
							+ topicComment 
							+ '</div>' 
							+ '<div class="comment_file">' 
							//+ '파일업로드 부분은 나중에 언젠간 처리해야' 
							+ '	<img src=""/>' 
							+ '</div>' 
							+ '	<!-- 토픽댓글 [더보기] --> ' 
							+ '	<div class="more_menu_box">' 
							+ '		<div class="ic_more_menu"></div>' 
							+ '	</div>	' 
							+ '	<!-- 토픽댓글 [더보기] - [내 댓글 더보기 창] -->' 
							+ '	<div class="div_comment_more_menu_mine">' 
							+ '	 	<div>' 
							+ '	 		<div class="fl ic_bookmark_off"></div>' 
							+ '	 		<div class="ic_show_file fl"></div>' 
							+ '			<span class="fl">즐겨찾기</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_copy fl"></div>' 
							+ '			<span class="fl">복사</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_edit_info fl"></div>' 
							+ '			<span class="fl">수정</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_delete fl"></div>' 
							+ '			<span class="fl">삭제</span>' 
							+ '	 	</div>' 
							+ '	 </div> ' 
							+ '	<!-- 토픽댓글 [더보기] - [상대 댓글 더보기 창] --> ' 
							+ '	<div class="div_comment_more_menu_other">' 
							+ '	 	<div>' 
							+ '	 		<div class="fl ic_bookmark_off"></div>' 
							+ '			<span class="fl">즐겨찾기</span>' 
							+ '	 	</div>' 
							+ '	 	<div>' 
							+ '	 		<div class="ic_copy fl"></div>' 
							+ '			<span class="fl">복사</span>'
							+ '	 	</div>'
							+ '	</div>' 
							+ '</div>';
						_this.closest(".div_comment_box").before(str);
						_this.text('');
					},
	             	error : function(XMLHttpRequest, textStatus, errorThrown){ 
	                	    		alert("통신 실패.")
	             	}
	     	});



		    }
			
		});
	});			
	
	

	
	
	


		
//		// 클릭 시 form 제출
//		$(".ic_comment_enter").click(function() {
//		    let commentContent = $(this).closest(".form_write_comment").find(".write_topic_comment_space").text();
//		    $(this).closest(".form_write_comment").find("#hidden_board_comment").val(commentContent);
//		    $(this).closest(".form_write_comment").submit();
//		});
//		
//		// Enter 키 입력 이벤트
//		$(".write_topic_comment_space").on('keyup', function(event) {
//		    // Enter 키가 눌렸는지 확인 && shift키가 동시에 놀리지 않았는지 확인 (줄바꿈으로 사용 가능)
//		    if (event.key === 'Enter' && !event.shiftKey) {
//		        event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
//		        let commentContent = $(this).text();
//		        $(this).closest(".form_write_comment").find("#hidden_board_comment").val(commentContent);
//		        $(this).closest(".form_write_comment").submit();
//		    }
//		});

		
//		 클릭 시 form 제출
//        $(".ic_comment_enter").click(function() {
//			$(this).closest(".form_write_comment").submit();
//        });
//
//         Enter 키 입력 이벤트 
//        $(".write_topic_comment_space").on('keyup', function(event) {
//            // Enter 키가 눌렸는지 확인 && shift키가 동시에 놀리지 않았는지 확인 (줄바꿈으로 사용 가능)
//			if (event.key === 'Enter' && !event.shiftKey) {
//                event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
//                $(this).closest(".form_write_comment").submit();
//            }
//        });

//	    $('#writeTopicBoardForm').on('submit', function() {
//	        $('#hidden_board_content').val($('#write_topic_board_space').text());
//	    });


	$(function(){
		// [입력창] - 맨션(@) 클릭 시
		$(".ic_mention").click(function() {
			$(this).next(".mention_pop_up").show();	
			$("#div_transparent_filter").show();
		});
		$(".mention_pop_up").click(function() {
			$(".mention_pop_up").hide();
			$("#div_transparent_filter").hide();
		});
		$("#div_transparent_filter").click(function() {
			$(".mention_pop_up").hide();
			$("#div_transparent_filter").hide();
		});
	});

/*======= 테스트 중 ========*/


	// 사용자 idx
	$(function(){
		$(".mention_member_list").each(function() {
			let memberIdx = $(this).attr("member_idx");
			if(memberIdx == $("#div_profile_box").attr("member_idx")) {
				$(this).find(".on_user").css('display','block');
			} else {
				$(this).find(".on_user").css('display','none');
			}
		});
	});
	
	// 참고하고 지우기 ↓
	// 토픽글 [더보기] 클릭
//		$(".div_content_center .more_menu_box").click(function(){
//			// 판단.  
//			let writer = $(this).parents(".content_board").attr("writer");
//			if(writer==$("#div_profile_box").attr("member_idx")) {
//				$(this).next(".div_board_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
//			} else {
//				$(this).next().next(".div_board_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
//			}
//			$("#div_transparent_filter").css('display','block');
//		});
	
	

//===================================== 토픽방 - 토픽글 - [더보기] =====================================	
	// 토픽글 - [더보기]
	$(function() {				
		// 토픽글 마우스 커서 올리면(마우스오버) [더보기] 뜸	
		$(document).on("mouseover", ".div_content_center", function(){
			$(this).find(".more_menu_box").css('display','block');
		});
		$(document).on("mouseout", ".div_content_center", function(){
			$(this).find(".more_menu_box").css('display','none');
		});
		
		// 토픽글 [더보기] 클릭
		$(document).on("click", ".div_content_center .more_menu_box", function(){
			// 판단.  
			let writer = $(this).parents(".content_board").attr("writer");
			if(writer==$("#div_profile_box").attr("member_idx")) {
				$(this).next(".div_board_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
			} else {
				$(this).next().next(".div_board_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
			}
			$("#div_transparent_filter").css('display','block');
		});
		$(document).on("click", "#div_transparent_filter", function(){
			$(".div_board_more_menu_mine").css('display','none');	// 내 토픽글의 [더보기]
			$(".div_board_more_menu_other").css('display','none');	// 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','none');
		});
		
		/*$(".div_content_center .more_menu_box").click(function(){
			// 판단.  
			let writer = $(this).parents(".content_board").attr("writer");
			if(writer==$("#div_profile_box").attr("member_idx")) {
				$(this).next(".div_board_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
			} else {
				$(this).next().next(".div_board_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
			}
			$("#div_transparent_filter").css('display','block');
		});*/
		/*$("#div_transparent_filter").click(function() {
			$(".div_board_more_menu_mine").css('display','none');	// 내 토픽글의 [더보기]
			$(".div_board_more_menu_other").css('display','none');	// 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','none');
		});*/
	});	
	
	// 토픽글 - [더보기]
	$(function() {			
		
		// 토픽방 - 토픽글 - [더보기] - [공지등록] 클릭 시
		$(".div_board_more_menu_mine > div:nth-child(1)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$(".div_board_more_menu_mine").css('display','none');
			$("#div_notice_register").css('display','block');
		});
		
		// 토픽방 - 토픽글 - [더보기] - [즐겨찾기] 클릭 시
		/* 사용자 토픽글 */
		$(".div_board_more_menu_mine > div:nth-child(2)").click(function() {
			$("#div_transparent_filter").hide();
			$(".div_board_more_menu_mine").hide();
			
			let idx = $(this).parents(".content_board").attr("topic_board_idx");
			let params = { idx:idx, something:"topic_board_idx" } // 객체 생성
			let _this = $(this).find("div:first-child");
			if(_this.hasClass("ic_bookmark_on")===true){
				$.ajax({
					type : "POST",
					url : "AjaxRemoveBookmarkServlet",
					data : params,
					success : function(res) {
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
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
						_this.removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("통신 실패.");
					}
				});
			}
		});
		 
		/* 상대방 토픽글 */ 
		$(".div_board_more_menu_other > div:nth-child(2)").click(function() {
			$("#div_transparent_filter").hide();
			$(".div_board_more_menu_other").hide();
			
			let idx = $(this).parents(".content_board").attr("topic_board_idx");
			let params = { idx:idx, something:"topic_board_idx" } // 객체 생성
			let _this = $(this).find("div:first-child");
			if(_this.hasClass("ic_bookmark_on")===true){
				$.ajax({
					type : "POST",
					url : "AjaxRemoveBookmarkServlet",
					data : params,
					success : function(res) {
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
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
						_this.removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("통신 실패.");
					}
				});
			}
		});
		
		
		// 토픽방 - 토픽글 - [더보기] - [복사] 클릭 시
		$(".div_board_more_menu_mine > div:nth-child(3)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$(".div_board_more_menu_mine").css('display','none');
			/* 1. 복사 기능 */
				// 클릭된 요소의 텍스트를 가져와서 클립보드에 복사
			    let textToCopy = $(this).text().trim();
			
			    // 복사할 텍스트를 임시로 textarea에 넣어서 클립보드로 복사
			    let tempTextarea = $('<textarea>').val(textToCopy).appendTo('body').select();
			    document.execCommand('copy');
			    tempTextarea.remove();
			
			    // 복사가 완료되었음을 사용자에게 알리기 위한 메시지 혹은 동작 추가 가능
			    alert('복사되었습니다: ' + textToCopy);
		});
		
		$("#btn_paste").click(function() {
		    // 붙여넣기 버튼이 클릭되면 여기에 붙여넣기 동작을 구현합니다.
		    // 예를 들어, 특정 input 요소에 붙여넣기한 내용을 넣는 등의 동작을 추가할 수 있습니다.
		    let pastedText = prompt('텍스트를 붙여넣으세요:');
		    if (pastedText !== null) {
		        // 붙여넣은 텍스트를 특정 input 요소에 삽입하는 예시
		        $("#input_target").val(pastedText);
		    }
		});
		
	});
	
	// 토픽글 - [더보기] - [삭제] 클릭 시
	$(function() {
	    let topicBoardIdx;
	    $('.div_board_more_menu_mine > div:nth-child(5)').click(function() {
	        // 부모 요소에서 topic_board_idx 가져오기
	        topicBoardIdx = $(this).closest('.content_board').attr('topic_board_idx');
			$("#div_transparent_filter").hide();
			$(".div_board_more_menu_mine").hide();
			$("#div_grey_filter").show();
	        $('#delete_topic_board_pop_up').show();
			$('#delete_topic_board_pop_up input[name="topicBoardIdx"]').val(topicBoardIdx);
		});
	
	    // 팝업창 내 "확인" 버튼 클릭 
	    $('#delete_topic_board_pop_up .btn_danger').click(function() {
	        $('#delete_topic_board_pop_up').hide();
	        $('#div_grey_filter').hide();
	    });
	
	    // 팝업창 내 "취소" 버튼 클릭 
	    $('#delete_topic_board_pop_up .btn_cancel').click(function() {
	        $('#delete_topic_board_pop_up').hide();
	        $('#div_grey_filter').hide();
	    });
	});

//===================================== 토픽방 - 토픽댓글 - [더보기] =====================================	
	// 토픽댓글 - [더보기]
	$(function() {				
		// 토픽댓글 마우스 커서 올리면(마우스오버) [더보기] 뜸	
		$(document).on("mouseover", ".div_comment", function(){
			$(this).find(".more_menu_box").css('display','block');
		});
		$(document).on("mouseout", ".div_comment", function(){
			$(this).find(".more_menu_box").css('display','none');
		});
		
		// 토픽글 [더보기] 클릭
		$(document).on("click", ".div_comment .more_menu_box", function(){
			// 판단.  
			let writer = $(this).parents(".div_comment").attr("writer");
			if(writer==$("#div_profile_box").attr("member_idx")) {
				$(this).next(".div_comment_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
			} else {
				$(this).next().next(".div_comment_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
			}
			$("#div_transparent_filter").css('display','block');
		});
		
		$(document).on("click", "#div_transparent_filter", function(){
			$(".div_comment_more_menu_mine").css('display','none');		// 내 토픽글의 [더보기]
			$(".div_comment_more_menu_other").css('display','none');	// 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','none');
		});
	});	
	
	// 토픽댓글 - [더보기] - [수정]	
	$(document).ready(function() {
	    // '수정' 버튼 클릭 시 댓글 수정 활성화
		$(document).on("click", ".div_comment_more_menu_mine > div:nth-child(3)", function(){
			$(".div_comment_more_menu_mine").css('display','none');		
	        // '수정' 버튼이 클릭된 div_comment 내에서 comment_item 요소를 찾음
	        let commentItem = $(this).closest('.div_comment').find('.comment_item');
	        
	        // comment_item 요소에 contenteditable 속성을 추가하여 편집 가능하게 함
	        commentItem.attr('contenteditable', 'true');
	        
	        // 편집 가능한 스타일을 추가할 수 있음 (예: 테두리, 배경색 등)
	        commentItem.css({
	            'border': '1px solid #ccc',
	            'border-radius': '6px',
	            'outline': 'none',
	            'padding': '5px',
	            'background-color': '#f9f9f9'
	        });
	
	        // focus를 줘서 바로 수정할 수 있도록 함
	        commentItem.focus();
	    });

		// comment_item에서 Enter 키 입력 이벤트 처리
		$(document).on("keydown", ".comment_item", function(event){
	        // Enter 키 눌렀을 때 동작
	        if (event.key === 'Enter' && !event.shiftKey) {
	            event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
	            
	            // 수정된 댓글 내용 가져오기
	            let topicCommentIdx = $(this).closest('.div_comment').attr('topic_comment_idx');
	            let topicComment = $(this).text().trim(); // 텍스트 내용 가져오기
				let params = { topic_comment_idx: topicCommentIdx,  topic_board_comment: topicComment};
				
	            // Ajax 요청 보내기
	            $.ajax({
	                type: "POST",
	                url: "AjaxUpdateTopicCommentServlet",
	                data: params,
	                success: function(res) {
	                    //alert(res.result); // 성공 메시지 출력
						$(".div_comment[topic_comment_idx='" + topicCommentIdx + "'] > .comment_item").attr('contenteditable', 'false').attr('style','');
	                },
	                error: function(XMLHttpRequest, textStatus, errorThrown) {
	                    alert("통신 실패.");
	                }
	            });
	        }
	    });
	});

	// 토픽댓글 - [더보기] - [삭제]
	$(function() {
	    let topicCommentIdx;
		let topicBoardIdx;
		let topicCommentCnt;
		// 토픽댓글 - [더보기] - [삭제] 클릭 시
	    $('.div_comment_more_menu_mine > div:nth-child(4)').click(function() {
	        // 부모 요소에서 topic_board_idx 가져오기
	        topicCommentIdx = $(this).closest('.div_comment').attr('topic_comment_idx');
	        topicBoardIdx = $(this).attr('topic_board_idx');
			$("#div_transparent_filter").hide();
			$(".div_comment_more_menu_mine").hide();
	        // 팝업창 열기
			$("#div_grey_filter").show();
	        $('#delete_topic_comment_pop_up').show();
	    });
	
	    // 팝업창 내 "확인" 버튼 클릭 
	    $('#delete_topic_comment_pop_up .btn_danger').click(function() {
	        // 팝업창 닫기
	        $('#delete_topic_comment_pop_up').hide();
	        $('#div_grey_filter').hide();
	
			let param = { topic_comment_idx : topicCommentIdx };
			$.ajax({
				type : "POST",           
				url : "/NamooPractice1/AjaxDeleteTopicCommentServlet",	// 절대경로
				data : param,   
				success : function(res){  
					// 댓글수 span 요소
					let _this = $(".div_comment[topic_comment_idx='" + topicCommentIdx + "']").siblings(".div_comment_count").find("span:nth-child(2)");
					// 댓글 삭제
					$(".div_comment[topic_comment_idx='" + topicCommentIdx + "']").remove();
					// 댓글수-1
					topicCommentCnt = parseInt(_this.text().trim()) -1;
					// 댓글구 span 요소의 값 변경
					_this.text(topicCommentCnt);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){ 
					console.log("Error: " + textStatus + ", " + errorThrown);
					alert("통신 실패.")
				}
	     	});
	    });

/*<원본>  // 팝업창 내 "확인" 버튼 클릭 
	    $('#delete_topic_comment_pop_up .btn_danger').click(function() {
	        // 팝업창 닫기
	        $('#delete_topic_comment_pop_up').hide();
	        $('#div_grey_filter').hide();
	
	        // deleteTopicBoard.jsp로 이동하여 삭제 수행
	        location.href = context_path + '/jsp/DeleteTopicComment.jsp?topicCommentIdx=' + topicCommentIdx;
	    });*/
	
	    // 팝업창 내 "취소" 버튼 클릭 
	    $('#delete_topic_comment_pop_up .btn_cancel').click(function() {
	        // 팝업창 닫기
	        $('#delete_topic_comment_pop_up').hide();
	        $('#div_grey_filter').hide();
	    });
	});
	
	// 토픽댓글 - [더보기] - [즐겨찾기] 클릭 시
	$(function() {	
		/* 사용자 토픽댓글 */
		$(".div_comment_more_menu_mine > div:first-child").click(function() {
			$("#div_transparent_filter").hide();
			$(".div_comment_more_menu_mine").hide();
			
			let idx = $(this).parents(".div_comment").attr("topic_comment_idx");
			let params = { idx:idx, something:"topic_comment_idx" } // 객체 생성
			let _this = $(this).find("div:first-child");
			if(_this.hasClass("ic_bookmark_on")===true){
				$.ajax({
					type : "POST",
					url : "AjaxRemoveBookmarkServlet",
					data : params,
					success : function(res) {
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
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
						_this.removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("통신 실패.");
					}
				});
			}
		});
		 
		/* 상대방 토픽댓글 */ 
		$(".div_comment_more_menu_other > div:first-child").click(function() {
			$("#div_transparent_filter").hide();
			$(".div_comment_more_menu_other").hide();
			
			let idx = $(this).parents(".div_comment").attr("topic_comment_idx");
			let params = { idx:idx, something:"topic_comment_idx" } // 객체 생성
			let _this = $(this).find("div:first-child");
			if(_this.hasClass("ic_bookmark_on")===true){
				$.ajax({
					type : "POST",
					url : "AjaxRemoveBookmarkServlet",
					data : params,
					success : function(res) {
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
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
						_this.removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("통신 실패.");
					}
				});
			}
		});
	});	
	
	
	
	
	
	
	
	// 파일 null일 경우 display:none 처리하는 방법
	$(document).ready(function() {
	  // (토픽글) 모든 .article_file 요소를 반복 처리
	  $(".article_file").each(function() {
	    // 현재 .article_file 요소의 텍스트 내용을 가져옴
	    let fileIdx = $(this).fine(".img").attr();
	    
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
	
	
	
	
	
	

	//===================================== 즐겨찾기창 =====================================				
	$(function() {
		// div_header [메뉴] - [즐겨찾기]
		$("#pop_up_header_menu > div:nth-child(3)").click(function(){
			$("#pop_up_header_menu").hide();
			$("#div_transparent_filter").hide();
			$("#div_side_bookmark").show();
			$("#div_side2").removeClass("wide");
		});
		
		// "x"를 클릭하면 즐겨찾기창 꺼짐  
		$("#bookmark_header > .exit").click(function(){
			$("#div_side_bookmark").hide();
			$("#div_side2").addClass("wide");
		}); 
	});
	
	$(function() {
		// "모든 형식" 클릭 시
		$("#all_content").click(function(){
			$("#all_content").addClass("select");
			$("#file_content").removeClass("select");
			$(".bookmark_text_item").show();
		});
		
		// "파일 형식" 클릭 시
		$("#file_content").click(function(){
			$("#file_content").addClass("select");
			$("#all_content").removeClass("select");
			$(".bookmark_text_item").hide();
		}); 
	});
	
	
	