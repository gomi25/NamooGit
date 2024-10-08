	//=====================================댓글 모두 보기 클릭=====================================			
		$(function() {
			// x 클릭 시 #div_side_message 사라지고 기존 화면대로 돌아감
			$("#div_side_msg_header .exit").click(function() {
				$("#div_side_message").css('display','none');
				$("#div_side2").addClass('wide');
				$("#div_msg_box").addClass('wide');
			});
			// 댓글 모두 보기 클릭 시 사이드에 메시지창 생김
			$(".comment_count span").click(function() {
				$("#div_side_message").css('display','block');
				$("#div_side2").removeClass('wide');
				$("#div_msg_box").removeClass('wide');
				let chatIdx = $(this).parents(".chat_comment_message").attr("chat_idx");
				$("#div_side_message").find("#div_side_content").attr('chat_idx', chatIdx);
			});
		});
	
	//===================================== div_header =====================================			
	// 로고 클릭 시 페이지 이동
	$(function() {
		$("#div_logo").click(function() {
			location.href = context_path + "/Controller?command=mainTool_choice&teamIdx=" + team_idx;
		});
		$("#pop_up_header_setting > div:nth-child(4)").click(function() {
			location.href = context_path + "/Controller?command=mainTool_choice&teamIdx=" + team_idx;
		});
	});			
	
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
		
		$(document).on("click", ".topic_folder_more_menu > div", function(e){		
			$("#div_transparent_filter").hide();
			$(".topic_folder_more_menu").hide();
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
	    $(".topic_folder_more_menu > div:nth-child(2)").click(function() {
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
	        let folders = $(".topic_folder").sort(function(a, b) {
	            let nameA = $(a).find("span.fl").first().text().toUpperCase();
	            let nameB = $(b).find("span.fl").first().text().toUpperCase();
	            return (nameA > nameB) ? -1 : (nameA < nameB) ? 1 : 0;
	        });
	
	        // 각 topic_folder 내의 topic_item 내림차순 정렬
	        $(".topic_folder").each(function() {
	            let topics = $(this).find(".topic_item").sort(function(a, b) {
	                let nameA = $(a).find("span").text().toUpperCase();
	                let nameB = $(b).find("span").text().toUpperCase();
	                return (nameA > nameB) ? -1 : (nameA < nameB) ? 1 : 0;
	            });
	            $(this).children("div").last().append(topics);
	        });
	
	        // topic_item 내림차순 정렬
	        let items = $("#div_topicroom_list_body .topic_item").sort(function(a, b) {
	            let nameA = $(a).find("span").text().toUpperCase();
	            let nameB = $(b).find("span").text().toUpperCase();
	            return (nameA > nameB) ? -1 : (nameA < nameB) ? 1 : 0;
	        });
	
	        // topic_folder를 먼저 추가하고 그 다음에 topic_item을 추가
	        $("#div_topicroom_list_body").empty().append(folders).append(items);
	    });
	
	    $(".list_view_option").children(":nth-child(2)").click(function() {
	        // topic_folder 오름차순 정렬
	        let folders = $(".topic_folder").sort(function(a, b) {
	            let nameA = $(a).find("span.fl").first().text().toUpperCase();
	            let nameB = $(b).find("span.fl").first().text().toUpperCase();
	            return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
	        });
	
	        // 각 topic_folder 내의 topic_item 오름차순 정렬
	        $(".topic_folder").each(function() {
	            let topics = $(this).find(".topic_item").sort(function(a, b) {
	                let nameA = $(a).find("span").text().toUpperCase();
	                let nameB = $(b).find("span").text().toUpperCase();
	                return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
	            });
	            $(this).children("div").last().append(topics);
	        });
	
	        // topic_item 오름차순 정렬
	        let items = $("#div_topicroom_list_body .topic_item").sort(function(a, b) {
	            let nameA = $(a).find("span").text().toUpperCase();
	            let nameB = $(b).find("span").text().toUpperCase();
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
			location.href = context_path + "/Controller?command=chatroom_choice&teamIdx=" + team_idx + "&chatroomIdx=" + chatroomIdx;
		});
		$("#div_chatroom_list_header > .ic_plus").click(function () {
			$("#div_new_chatroom_create").show();
		});
	});	
	
	/****************************** 목록의 즐겨찾기 ******************************/		
	// 토픽 목록에 [즐겨찾기] 아이콘 클릭 시 즐겨찾기 등록 및 해제
	$(function(){
	 	// 이벤트 위임을 사용하여 동적으로 생성된 요소에도 이벤트가 바인딩되도록 수정
   		$(document).on("click", ".topic_item > div:first-child", function(e){
			let idx = $(this).parent(".topic_item").attr("chatroom_idx");
			let params = { idx : idx, something: "chatroom_idx" } // 객체 생성
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
	
	//===================================== div_side2 =====================================				
	//===================================== 채팅방 상단바 =====================================						
	$(function() {	
		// 채팅 - 즐겨찾기 등록 및 해제
        $("#div_title > div:nth-child(1) > div:nth-child(2)").click(function(){
			let idx = $(this).attr("chatroom_idx");
			let params = { idx : idx, something: "chatroom_idx" }  // 객체 생성
			let _this = $(this);
			
			// 즐겨찾기 해제  ic_bookmark_on 클릭 시 ic_bookmark_off로 
			if(_this.hasClass("ic_bookmark_on")===true){
				// on -> off
				//location.href = context_path + '/jsp/RemoveBookmark.jsp?something=topic_idx&idx=' + idx;
				$.ajax({
	                type : "POST",            
	                url : "AjaxRemoveBookmarkServlet",  	
	                data : params,           
	                success : function(res){ 
						_this.removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
						$(".topic_item[chatroom_idx='"+params.idx+"']").find("div:first-child").removeClass("ic_bookmark_on").addClass("ic_bookmark_off");
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
						$(".topic_item[chatroom_idx='"+params.idx+"']").find("div:first-child").removeClass("ic_bookmark_off").addClass("ic_bookmark_on");
	                },
	                error : function(XMLHttpRequest, textStatus, errorThrown){ // 비동기 통신이 실패할경우 error 콜백으로 들어옴.
	                    alert("통신 실패.")
	                }
	            });
			}
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
			$(".div_participants").css('display','block');
			$("#div_transparent_filter").css('display','block');
		});
		$("#div_transparent_filter").click(function() {
			$(".div_participants").css('display','none');
			$("#div_transparent_filter").css('display','none');
		});
	});		
	
	$(function() {			
		// [참여멤버] ".profile_power"쪽에 마우스 커서 올리면  
		// ".ic_exit_chatroom" 보이도록
		$(document).on('mouseover', '.div_participants .div_member_list', function() {
			$(".div_participants .ic_exit_chatroom").show();
		});
		// 마우스 커서 벗어나면 채팅방 제목 및 설명 팝업 사라짐
		$(".div_participants .div_member_list").mouseout(function(){
			$(".div_participants .ic_exit_chatroom").hide();
		});
		
		$("#remove_chatroomMember_pop_up .btn_ok").click(function(){
			$("#div_grey_filter").hide();
			$("#remove_topicMember_pop_up").hide();
		});
		$("#remove_chatroomMember_pop_up .btn_cancel").click(function(){
			$("#div_grey_filter").hide();
			$("#remove_topicMember_pop_up").hide();
		});
		
		// 토픽 멤버 내보내기 버튼 클릭 시 
		$(document).on('click', '.ic_exit_chatroom', function() {
			$(".div_participants").hide();
			
			let removeMemberIdx = $(this).parent(".div_member_list").attr("member_idx");
			let removeMemberImg = $(this).parent(".div_member_list").find(".profile_img").attr("src");
			let removeMemberName = $(this).parent(".div_member_list").find(".profile_name").text();
			
			$("#div_grey_filter").show();
			$("#remove_topicMember_pop_up").show();
			$("#remove_member_info > img").attr('src', removeMemberImg);
			$("#remove_member_info > span").text(removeMemberName);
			$("#removeChatroomMemberForm > input[name='removeMemberIdx']").val(removeMemberIdx);
			
		});
		
		// [참여 멤버] 검색 시
		$(".div_participants input[name='member_search']").keyup(function(e) {
			$(".div_not_exist").css('display', 'none');
			$(".invite_member").css('display', 'none');
			let input = $(this).val();
			if(input.length > 0) {	// (입력된 게 있으면)
				let found = false;  // 찾았냐? 있냐?
				$(".div_participants .div_member_list .profile_name").each(function(idx, item) { 
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
				$(".div_participants .div_member_list").css('display', 'block');
			}
		});
		
		// 검색결과 없을 때 "+토픽에 멤버 초대하기" 클릭 시 [멤버 초대하기] 팝업창 뜸
		$(".div_participants .invite_member").click(function(){
			$(".div_participants").hide();
			$("#div_transparent_filter").hide();
			$("#div_invite_member").show();
			$("#div_grey_filter").show();
		});
		
		// 사용자에 초록색불 들어오게
		$(".div_participants .div_member_list").each(function() {
			let memberIdx = $(this).attr("member_idx");
			if(memberIdx == $("#div_profile_box").attr("member_idx")) {
				$(this).find(".on_user").css('display','block');
			} else {
				$(this).find(".on_user").css('display','none');
			}
		});
	});		

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
			
			// 채팅 시작하기(초대하기) '클릭' 시 팝업창 뜨도록
			$("#div_title > div:nth-child(2) > div:nth-child(3)").click(function() {
				$("#div_chat_start").css('display','block');
				$("#div_grey_filter").css('display', 'block');
			});
			// 채팅 시작하기(초대하기)에서  'x'클릭 시  시 팝업창 꺼지도록 
			$("#div_chat_start > div:nth-child(1) > .exit").click(function(){
				$("#div_chat_start").css('display', 'none');
				$("#div_grey_filter").css('display', 'none');
			});
			$("#div_chat_start button[name='close_window']").click(function() {
				$("#div_chat_start").css('display', 'none');
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
	
	function insert_here(insert_name) {
		let idx_found = -1;
		$("#div_chat_start .div_member_list2").each(function(idx, item) {
			let name = $(item).find(".profile_name").text().trim();  
			if(insert_name > name) {
				console.log(insert_name+ "이(가) " + name + "보다 큼. 히히.");
				idx_found = idx;
				console.log("idx_found = " + idx_found);
			}
		});
		if(idx_found==-1) {
			return null;
		}
		return $("#div_chat_start .div_member_list2").eq(idx_found);
	}
			
	$(function() {					
			// 채팅 시작하기에서 '선택된 멤버' 선택 -> '팀멤버'로 이동
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
					$("#div_chat_start #start_member").prepend(str);  // 첫째 자식으로 추가.
				} else {
					insert_where.after(str);
				}

				if($(".choice_member").length==0) {
					$("[name='invite_member']").removeClass('green_light');
				}
				updateSelectedMembers(); // 선택된 멤버 목록을 업데이트.
			});
			
			// 채팅 시작하기에서 '팀멤버' 선택 -> '선택된 멤버'로 이동
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
			        $("#form_invite_chat_member").submit(); // 폼을 제출.
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
		
		
	$(function() {					
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
			
			// [더보기] - [채팅방 나가기] 클릭 시
			$("#div_chatroom_more_menu > div:nth-child(4)").click(function() {
				$("#div_chatroom_more_menu").hide();
				$("#div_transparent_filter").hide();
				$("#div_grey_filter").show();
				$("#delete_chatroom_pop_up").show();
			});
			$("#delete_chatroom_pop_up .btn_cancel").click(function() {
				$("#delete_chatroom_pop_up").hide();
				$("#div_grey_filter").hide();
			});
			$("#delete_chatroom_pop_up .btn_ok").click(function() {
				$("#delete_chatroom_pop_up").hide();
				$("#div_grey_filter").hide();
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
		
		
		
		
		
	//===================================== 채팅방 - 채팅글 - [입력창] =====================================
	// [입력창] - 채팅글 작성 
	$(function() {
		const $write_chat_content_space = $('#write_chat_content_space');
	
	    // #write_chat_content_space의 내용을 검사하여 비어 있으면 empty 클래스를 추가하고, 비어 있지 않으면 empty 클래스를 제거
		// contenteditable 요소가 비어 있을 때 CSS에서 data-placeholder 속성의 값을 표시
	    function togglePlaceholder() {
	        if ($write_chat_content_space.text().trim() === '') {
	            $write_chat_content_space.addClass('empty');
	        } else {
	            $write_chat_content_space.removeClass('empty');
	        }
	    }
	
	    // 문서가 준비되면 togglePlaceholder 함수를 호출하여 초기 상태를 설정
	    togglePlaceholder();
	
	    // #write_chat_content_space에서 input 이벤트가 발생할 때마다 togglePlaceholder 함수를 호출하여 placeholder 텍스트를 동적으로 관리
	    $write_chat_content_space.on('input', function() {
	        togglePlaceholder();
	    });
	});		
	
	


	//===================================== 채팅방 - 채팅글 - [더보기] =====================================	
	// 채팅글 - 마우스오버 시
	$(function() {				
		// 토픽글 마우스 커서 올리면(마우스오버) [더보기] 뜸	
		$(document).on("mouseover", ".chat_message", function(){
			$(this).find(".more_menu_box").css('display','block');
		});
		$(document).on("mouseout", ".chat_message", function(){
			$(this).find(".more_menu_box").css('display','none');
		});
		
		// 토픽글 [더보기] 클릭
		$(document).on("click", ".chat_message .more_menu_box", function(){
			// 판단.  
			let writer = $(this).parents(".chat_message").attr("writer");
			if(writer==$("#div_profile_box").attr("member_idx")) {
				$(this).next(".div_chat_more_menu_mine").css('display','block');		  // 내 토픽글의 [더보기]
			} else {
				$(this).next().next(".div_chat_more_menu_other").css('display','block'); // 상대 토픽글의 [더보기]
			}
			$("#div_transparent_filter").css('display','block');
		});
		$(document).on("click", "#div_transparent_filter", function(){
			$(".div_chat_more_menu_mine").css('display','none');	// 내 토픽글의 [더보기]
			$(".div_chat_more_menu_other").css('display','none');	// 상대 토픽글의 [더보기]
			$("#div_transparent_filter").css('display','none');
		});
	});	
	
	// 채팅글 - [더보기] - [댓글] 클릭 시
	$(function() {			
		// 내 채팅글 
		$(".div_chat_more_menu_mine > div:nth-child(1)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$(".div_chat_more_menu_mine").css('display','none');
			// #div_side_message 메시지창 생김
			$("#div_side_message").css('display','block');
			$("#div_side2").removeClass('wide');
			$("#div_msg_box").removeClass('wide');
		});
		// 상대 채팅글
		$(".div_chat_more_menu_other > div:nth-child(1)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$(".div_chat_more_menu_other").css('display','none');
			// #div_side_message 메시지창 생김
			$("#div_side_message").css('display','block');
			$("#div_side2").removeClass('wide');
			$("#div_msg_box").removeClass('wide');
		});
		
		// 'x' 클릭 시 #div_side_message 사라지고 기존 화면대로 돌아감
		$("#div_side_msg_header > div > img:nth-child(3)").click(function() {
			$("#div_side_message").css('display','none');
			$("#div_side2").addClass('wide');
			$("#div_msg_box").addClass('wide');
		});
	});	
	
	// 채팅글 - [더보기] - [공지등록] 클릭 시
	$(function() {			
		$(".div_chat_more_menu_mine > div:nth-child(2)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$(".div_chat_more_menu_mine").css('display','none');
			$("#div_notice_register").css('display','block');
		});
		$(".div_chat_more_menu_other > div:nth-child(2)").click(function(){
			$("#div_transparent_filter").css('display','none');
			$(".div_chat_more_menu_other").css('display','none');
			$("#div_notice_register").css('display','block');
		});
		
		// 채팅방 - 채팅글 - [더보기] - [즐겨찾기] 클릭 시
		/* 사용자 채팅글 */
		$(".div_chat_more_menu_mine > div:nth-child(3)").click(function() {
			$("#div_transparent_filter").hide();
			$(".div_chat_more_menu_mine").hide();
			
			let idx = $(this).parents(".chat_message").attr("chat_idx");
			let params = { idx:idx, something:"chat_idx" } // 객체 생성
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
		 
		/* 상대방 채팅글 */ 
		$(".div_chat_more_menu_other > div:nth-child(3)").click(function() {
			$("#div_transparent_filter").hide();
			$(".div_chat_more_menu_other").hide();
			
			let idx = $(this).parents(".chat_message").attr("chat_idx");
			let params = { idx:idx, something:"chat_idx" } // 객체 생성
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
	
	// 채팅글 - [더보기] - [복사] 클릭 시
	$(function() {	

	});	
	
	// 채팅글 - [더보기] - [수정] 클릭 시
	$(function() {	

	});
		
	// 채팅글 - [더보기] - [삭제] 클릭 시
	$(function() {	
	    let chatIdx;
	    $('.div_chat_more_menu_mine > div:nth-child(6)').click(function() {
	        // 부모 요소에서 topic_board_idx 가져오기
	        chatIdx = $(this).closest('.chat_message').attr('chat_idx');
			$("#div_transparent_filter").hide();
			$(".div_chat_more_menu_mine").hide();
	        // 팝업창 열기
			$("#div_grey_filter").show();
	        $('#delete_chat_content_pop_up').show();
	    });
	
	    // 팝업창 내 "확인" 버튼 클릭 
	    $('#delete_chat_content_pop_up .btn_danger').click(function() {
	        // 팝업창 닫기
	        $('#delete_chat_content_pop_up').hide();
	        $('#div_grey_filter').hide();
			
			let param = { chat_idx : chatIdx };
			$.ajax({
				type : "POST",           
				url : "AjaxDeleteChatContentServlet",	
				data : param,   
				success : function(res){  
					$(".chat_message[chat_idx='" + chatIdx + "']").remove();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){ 
					alert("통신 실패.")
				}
	     	});
	    });
	
	    // 팝업창 내 "취소" 버튼 클릭 
	    $('#delete_chat_content_pop_up .btn_cancel').click(function() {
	        // 팝업창 닫기
	        $('#delete_chat_content_pop_up').hide();
	        $('#div_grey_filter').hide();
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
	
	
	