$(function() {
			/*-------------우선순위-----------------*/
		$(".add_prioritize").click(function() {
        	$("#prioritize").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$("#div_transparent_filter").click(function() {
            $("#prioritize").css('display','none');
			$("#addwork_prioritize").css('display','none');
            $("#div_transparent_filter").css('display','none');
         });   
         /*-------------우선순위(선택)-----------------*/

 		/*-------낮음------*/
		$(".low").click(function() {
			$(".add_prioritize").css('display','none');
			$("#prioritize").css('display','none');
            $(".addwork_add_low").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$(".add_x_img").click(function() {
			$(".addwork_add_low").css('display','none');
			$(".add_prioritize").css('display','none');
         });
		 
		/*-------보통------*/
		$(".normal").click(function() {
			$(".add_prioritize").css('display','none');
			$("#prioritize").css('display','none');
            $(".addwork_add_normal").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$(".add_x_img").click(function() {
			$(".addwork_add_normal").css('display','none');
            $(".add_prioritize").css('display','block');
         });
		/*-------높음------*/
		$(".hight").click(function() {
			$(".add_prioritize").css('display','none');
			$("#prioritize").css('display','none');
            $(".addwork_add_hight").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$(".add_x_img").click(function() {
			$(".addwork_add_hight").css('display','none');
            $(".add_prioritize").css('display','block');
         });
		/*-------긴급------*/
		$(".emergency").click(function() {
			$(".add_prioritize").css('display','none');
			$("#prioritize").css('display','none');
            $(".addwork_add_emergency").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$(".add_x_img").click(function() {
			$(".addwork_add_emergency").css('display','none');
            $(".add_prioritize").css('display','block');
         });

         /*-------------그룹추가-----------------*/
          $(".add_group").click(function() {
            $("#addgroup").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
         $("#div_transparent_filter").click(function() {
            $("#addgroup").css('display','none');
            $("#div_transparent_filter").css('display','none');
         }); 
		/*-------------그룹선택----------------*/
		$(".group1").click(function() {
			$(".add_group").css('display','none');
			$("#addgroup").css('display','none');
            $(".select_group1").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$(".add_x_img").click(function() {
			$(".select_group1").css('display','none');
            $(".add_group").css('display','block');
         });
		/*----그룹 미지정-----*/
		$(".nogroup").click(function() {
			$(".add_group").css('display','none');
			$("#addgroup").css('display','none');
            $(".select_nogroup").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
		$(".add_x_img").click(function() {
			$(".select_nogroup").css('display','none');
            $(".add_group").css('display','block');
         });
 
           /*-------------하위업무추가-----------------*/
          $("#subwork_button").click(function() {
            $("#mini_subwork").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
         $("#div_transparent_filter").click(function() {
            $("#mini_subwork").css('display','none');
            $("#div_transparent_filter").css('display','none');
         });  

		/*-------------하위업무(상태)-----------------*/
         $(".subwork_state").click(function() {
            $("#mini_state").css('display','block');
			$("#div_transparent_filter").css('display','block');
			$("#member_select").css('display', 'none');
			$("#prioritize2").css('display', 'none');

         });
 		$("#div_transparent_filter").click(function() {
            $("#mini_state").css('display','none');
            $("#div_transparent_filter").css('display','none');
         }); 

		/*-------------하위업무(상태) 추가-----------------*/

		function hideAllStates() {
	        $(".subwork_state").css('display', 'none');
	    }
	
	    /*------요청 클릭시------*/
	    $(".box_request").click(function() {
	        hideAllStates();
	        $("#mini_state").css('display', 'none');
	        $(".subwork_request").css('display', 'block');
	    });
	
	    /*------진행 클릭------*/
	    $(".box_progress").click(function() {
	        hideAllStates();
	        $("#mini_state").css('display', 'none');
	        $(".subwork_progress").css('display', 'block');
	    });
	
	    /*------피드백 클릭------*/
	    $(".box_feedback").click(function() {
	        hideAllStates();
	        $("#mini_state").css('display', 'none');
	        $(".subwork_feedback").css('display', 'block');
	    });
	
	    /*-----완료 클릭------*/
	    $(".box_finish").click(function() {
	        hideAllStates();
	        $("#mini_state").css('display', 'none');
	        $(".subwork_finish").css('display', 'block');
	    });
	
	    /*------보류 클릭------*/
	    $(".box_defer").click(function() {
	        hideAllStates();
	        $("#mini_state").css('display', 'none');
	        $(".subwork_defer").css('display', 'block');
	    });

         /*-------------하위업무 우선순위 추가-----------------*/
          $(".subwork_prioritize2").click(function() {
            $("#prioritize2").css('display','block');
            $("#div_transparent_filter").css('display','block');
 			$("#member_select").css('display', 'none');
 			$("#mini_state").css('display', 'none');
         });
         $("#div_transparent_filter").click(function() {
            $("#prioritize2").css('display','none');
            $("#div_transparent_filter").css('display','none');
         });  

	    // .subwork_member 클릭 시 #prioritize2 숨기고 #member_select 보이기
	    $(".subwork_member").click(function() {
	        $("#prioritize2").css('display', 'none');
	        $("#member_select").css('display', 'block');
	        $("#div_transparent_filter").css('display', 'block');
 			$("#mini_state").css('display', 'none');

	    });
	
	    // 필터 영역을 클릭하면 두 요소 모두 숨기기 (선택 사항)
	    $("#div_transparent_filter").click(function() {
	        $("#prioritize2").css('display', 'none');
	        $("#member_select").css('display', 'none');
	        $(this).css('display', 'none');
	    });


	    // '취소' 버튼 클릭 
	    $('.mini_delete').click(function() {
 			$('.mini_smalllow').css('display', 'none'); 
			$('.mini_smallnoaml').css('display', 'none'); 
			$('.mini_smallhight').css('display', 'none'); 
			$('.mini_smallemergency').css('display', 'none'); 
	        $('#prioritize2').css('display', 'none'); 
	        $('.subwork_prioritize2').css('display', 'block'); 
	    });
	
	    // '낮음' 우선순위 클릭
	    $('.mini_low').click(function() {
 			$('.mini_smalllow').css('display', 'none'); 
			$('.mini_smallnoaml').css('display', 'none'); 
			$('.mini_smallhight').css('display', 'none'); 
			$('.mini_smallemergency').css('display', 'none'); 
	        $('.subwork_prioritize2').css('display', 'none'); 
	        $('.mini_smalllow').css('display', 'block'); 
			$('#prioritize2').css('display', 'none');
	    });
	
	    // '보통' 우선순위 클릭
	    $('.mini_noaml').click(function() {
 			$('.mini_smalllow').css('display', 'none'); 
			$('.mini_smallnoaml').css('display', 'none'); 
			$('.mini_smallhight').css('display', 'none'); 
			$('.mini_smallemergency').css('display', 'none'); 
	        $('.subwork_prioritize2').css('display', 'none'); 
	        $('.mini_smallnoaml').css('display', 'block'); 
			$('#prioritize2').css('display', 'none');
	    });
	
	    // '높음' 우선순위 클릭 
	    $('.mini_hight').click(function() {
 			$('.mini_smalllow').css('display', 'none'); 
			$('.mini_smallnoaml').css('display', 'none'); 
			$('.mini_smallhight').css('display', 'none'); 
			$('.mini_smallemergency').css('display', 'none'); 
	        $('.subwork_prioritize2').css('display', 'none'); 
	        $('.mini_smallhight').css('display', 'block'); 
			$('#prioritize2').css('display', 'none');
	    });
	
	    // '긴급' 우선순위 클릭
	    $('.mini_emergency').click(function() {
 			$('.mini_smalllow').css('display', 'none'); 
			$('.mini_smallnoaml').css('display', 'none'); 
			$('.mini_smallhight').css('display', 'none'); 
			$('.mini_smallemergency').css('display', 'none'); 
	        $('.subwork_prioritize2').css('display', 'none'); 
	        $('.mini_smallemergency').css('display', 'block'); 
			$('#prioritize2').css('display', 'none');
	    });

  		/*-------------하위업무 우선순위 변경-----------------*/

	    $('.mini_smalllow').click(function() {
	        $('#prioritize2').css('display', 'block'); 
 			//$('.mini_smalllow').css('display', 'none'); 
	    });
	    $('.mini_smallnoaml').click(function() {
	        $('#prioritize2').css('display', 'block'); 
			//$('.mini_smallnoaml').css('display', 'none'); 
	    });
	    $('.mini_smallhight').click(function() {
	        $('#prioritize2').css('display', 'block'); 
			//$('.mini_smallhight').css('display', 'none'); 
	    });
	    $('.mini_smallemergency').click(function() {
	        $('#prioritize2').css('display', 'block'); 
			//$('.mini_smallemergency').css('display', 'none'); 
	    });
	   
          /*-------------하위업무 담당자 추가-----------------*/
          $(".subwork_member").click(function() {
            $("#member_select").css('display','block');
            $("#div_transparent_filter").css('display','block');
         });
         $("#div_transparent_filter").click(function() {
            $("#member_select").css('display','none');
            $("#div_transparent_filter").css('display','none');
         }); 
         
         /*-------------상태 변경-----------------*/
         
          $("#request").click(function() {
            $(".request").css('color','#fff');
            $("#request").css('background-color','#00B2FF');
            $("#progress, #feedback, #finish, #defer").css('background-color','#ddd');
            $(".progress, .feedback, .finish, .defer").css('color','#777');
         });
         $("#progress").click(function() {
        	$(".progress").css('color','#fff');
            $("#progress").css('background-color','#00B01C');
            $("#request, #feedback, #finish, #defer").css('background-color','#ddd');
            $(".request, .feedback, .finish, .defer").css('color','#777');
         }); 
         $("#feedback").click(function() {
        	 $(".feedback").css('color','#fff');
             $("#feedback").css('background-color','#FD7900');
             $("#request, #progress, #finish, #defer").css('background-color','#ddd');
             $(".request, .progress, .finish, .defer").css('color','#777');
          });
         $("#finish").click(function() {
        	 $(".finish").css('color','#fff');
             $("#finish").css('background-color','#402A9D');
             $("#request, #progress, #feedback, #defer").css('background-color','#ddd');
             $(".request, .progress, .feedback, .defer").css('color','#777');
          });
         $("#defer").click(function() {
        	 $(".defer").css('color','#fff');
             $("#defer").css('background-color','#777777');
             $("#request, #progress, #feedback, #finish").css('background-color','#ddd');
             $(".request, .progress, .feedback, .finish").css('color','#777');
          });
         
       
         
         
         
         
      });