$(function(){
	// 멤버 클릭
	$("#memberlist").click(function() {
		$("#div_side3").css('display','block');
		$("#div_side2").css('display','none');
     });
	// 부서 클릭
	$("#select_teamlist").click(function() {
		$("#div_side2").css('display','block');
		$("#div_side3").css('display','none');
     });
	// 부서-즐겨찾기 멤버 선택
	$("#bookmark_member").click(function() {
		$("#bookmark_memberlist").css('display','block');
		$("#team_memberlist").css('display','none');
     });
	// 부서-팀 선택
	$("#team_name").click(function() {
		$("#team_memberlist").css('display','block');
		$("#bookmark_memberlist").css('display','none');
     });



	// 초기상태
    $('#team_name > button').addClass('clicked');

    // 클릭 이벤트 처리
    $('#bookmark_member > button').click(function() {
        // 모든 버튼에서 클릭된 상태 제거
        $('#bookmark_member > button, #team_name > button').removeClass('clicked');
        // 클릭된 버튼에 클릭된 상태 추가
        $(this).addClass('clicked');
    });

    $('#team_name > button').click(function() {
        // 모든 버튼에서 클릭된 상태 제거
        $('#bookmark_member > button, #team_name > button').removeClass('clicked');
        // 클릭된 버튼에 클릭된 상태 추가
        $(this).addClass('clicked');
    });




	// 멤버 검색
	$("#search input").keyup(function(e) {
		let input = $(this).val();
		if(input.length > 0) {  
		    let found = false;  
		    $(".member1 #name").each(function(idx, item) { 
		       if($(item).text().indexOf(input) >= 0) {   
		          $(item).parents(".member1").show();
		          found = true;
		       } else {   
		          $(item).parents(".member1").hide();
		       }
			});
		} else {  // input.length == 0  (입력된 게 없으면)
			$(".member1").show();
		}
	});
	
	// 상세 프로필 띄우기
    $(".member1").on("click", function() {
		$("#div_transparent_filter").css('display','block');
        // 모든 상세 프로필을 숨김
        $(".member_profile_container").hide();
        // 클릭된 멤버의 member_idx
        let memberIdx = $(this).attr("member_idx");
        // 클릭된 요소의 위치를 기준으로 위치 설정
    	let offset = $(this).offset();
        let profileContainer = $("#member_profile_container_" + memberIdx);
        let containerWidth = profileContainer.outerWidth();
        
		profileContainer.css("left", offset.left - containerWidth + 3);
       	//profileContainer.css({
        //    left: offset.left - containerWidth +3 // 클릭된 요소의 왼쪽에 약간의 여백을 두고 표시
        //});
		profileContainer.show();
	});
	// 프로필 숨기기
	$("#div_transparent_filter").click(function() {
		$("#div_transparent_filter").css('display','none');
		$(".member_profile_container").hide();
     });
	
	// 로그인한 멤버 프로필 띄우기
	$(".member1").on("click", function() {
		$("#div_transparent_filter").css('display','block');
        
        // 클릭된 멤버의 member_idx
        let memberIdx = $(this).attr("member_idx");
		
        // 클릭된 요소의 위치를 기준으로 위치 설정
    	if ($("#loginmember_profile_container").attr("member_idx") == memberIdx) {
            $("#loginmember_profile_container").show();
        } else {
            $("#loginmember_profile_container").hide(); // 기본적으로 숨기기
        }
	});
	
	$("#div_transparent_filter").click(function() {
		$("#div_transparent_filter").css('display','none');
		$("#loginmember_profile_container").hide();
     });
	


	
	
	
	//멤버 즐겨찾기 
	$(document).on("click", ".nobookmark_img", function() {
		$(this).css('display','none');
		$(this).prev().css('display', 'block');
		$(".member_profile_container").css('display','none');
		let _this = $(this);
		let member_idx = $(this).parent().attr("member_idx");
		$.ajax({
			type: 'get',
			url: 'AjaxMemberBookmarkOnServlet',
			data: { idx: member_idx, something: "member_idx_to"},
			success: function(response){
				//alert("즐겨찾기 성공");
				//alert(response.result);   // success/
				let clone_object = _this.parent().clone();
				$("#bookmark_memberlist").append(clone_object);  // 마지막 자식으로
				let cnt = $("#bookmark_memberlist > .member1").length;
				$("#bookmark_member > button > div:nth-child(2)").text(cnt);
			},
			error: function(){
				alert('ajax 통신 실패');		
			}
		});
	});
	
	//멤버 즐겨찾기 해제
	$(document).on("click", ".bookmark_img", function() {
		$(this).css('display','none');
		$(this).next().css('display', 'block');
		$(".member_profile_container").css('display','none');
		let _this = $(this);
		let member_idx = $(this).parent().attr("member_idx");
		$.ajax({
			type: 'get',
			url: 'AjaxMemberBookmarkOffServlet',
			data: { idx: member_idx, something: "member_idx_to"},
			success: function(response){
				//alert("즐겨찾기 해제");
				// 클론한 객체를 즐겨찾기 목록에서 제거
				_this.remove();
				$("#bookmark_memberlist .member1[member_idx='" + member_idx + "']").remove();

				let cnt = $("#bookmark_memberlist > .member1").length;
           		$("#bookmark_member > button > div:nth-child(2)").text(cnt);
			},
			error: function(){
				alert('ajax 통신 실패');		
			}
		});
	});
	
	// 로그인한 멤버 상태 변경(처음에 입력할 때)
	$(".status_input").on('keyup', function(event) {
		 // span_folder_name에서 Enter 키 입력 이벤트 처리
        // Enter 키 눌렀을 때 동작
		if (event.key === 'Enter' && !event.shiftKey) {
			event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
     
			$(".member_inner1_state").hide(); 
			let state = $(this).val(); 
        	let memberIdx = $(this).parent().parent().attr('member_idx');

			let _this = $(this);  
			let params = { 
				state : state,
				member_idx : memberIdx
			};
			$.ajax({
				type: 'get',
				url: 'AjaxLoginMemberProfileStateServlet',
				data: params,
				success: function(response){
					alert("상태변경");
					$(".status_input").css('display','none');
					$(".member_inner1_state").css('display','block'); 
					$(".inner1_state_x_img").show(); 
					$(".member_inner1_state").text(state);
				},
				error: function(){
					alert('ajax 통신 실패');		
				}
			});
		}
	});
	
	
	// 로그인한 멤버 상태 변경(수정할 때)
	$(".inner1_state_x_img").click(function() {
		$(".member_inner1_state").hide(); 
		$(".status_input").show(); 
		
		
		$(".status_input").on('keyup', function(event) {
		 	// span_folder_name에서 Enter 키 입력 이벤트 처리
        	// Enter 키 눌렀을 때 동작
			if (event.key === 'Enter' && !event.shiftKey) {
				event.preventDefault(); // 기본 Enter 동작(줄바꿈) 방지
				let state = $(this).val(); 
	        	let memberIdx = $(this).parent().parent().attr('member_idx');
				let _this = $(this);  
				let params = { 
					state : state,
					member_idx : memberIdx
				};
				$.ajax({
					type: 'get',
					url: 'AjaxLoginMemberProfileStateServlet',
					data: params,
					success: function(response){
						alert("상태변경");
						$(".status_input").css('display','none');
						$(".member_inner1_state").css('display','block'); 
						$(".inner1_state_x_img").show(); 
						$(".member_inner1_state").text(state);
					},
					error: function(){
						alert('ajax 통신 실패');		
					}
				});
			}
		});
	});



		
});