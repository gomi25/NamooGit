$(function() {
	// QnA 항목 클릭 시 답변 보이기/숨기기
 	$(".qna").click(function() {
//      let the_display = $(this).nextUntil(".qna_answer").css('display');
		let qna_idx = $(this).attr("qna_idx");
		let the_display = $(".qna_answer[qna_idx='"+qna_idx+"']").css('display');
        if(the_display == 'none') {
//			$(".qna_answer[qna_idx='"+qna_idx+"']").slideDown();			
			$(".qna_answer[qna_idx='"+qna_idx+"']").fadeIn();			
//        	$(this).nextUntil(".qna_answer").slideDown();
        } else {
//			$(".qna_answer[qna_idx='"+qna_idx+"']").slideUp();			
			$(".qna_answer[qna_idx='"+qna_idx+"']").fadeOut();			
//        	$(this).nextUntil(".qna_answer").slideUp();
			//$(".add_answer").slideUp();
			//$(this).next(".add_answer").slideUp();
        }
 	});

	// 답글달기 버튼 클릭 시 답변 보이기
/*	function showAnswerForm(qnaIdx) {
	    // 모든 .add_answer 요소를 숨깁니다.
	    $('.add_answer').hide();
	
	    // 해당 qna_idx에 맞는 .add_answer 요소를 보입니다.
	    $('#answer_form_' + qnaIdx).toggle();
	}*/

	// 답글달기 누르면 아래가 펼쳐
	    $('.qna_button').click(function() {
        $(this).closest('.qna').next('.add_answer').toggle();  
    });




	// 답글이 달리면 답글달기 버튼이 사라짐
    $(".qna").each(function() {
        if ($(this).find("td").first().text().trim() == "답변완료") {
            $(this).find(".qna_button").hide();
        }
    });
	

	// 수정버튼을 누르면  답변이 사라지고 .add_answer가 펼쳐짐
    $(".answer_update").click(function() {
        // 클릭된 버튼의 부모 .qna_answer 요소에서 qna_idx 값을 가져옵니다.
        let qnaIdx = $(this).closest('.qna_answer').attr('qna_idx');
        
        // 모든 .add_answer 요소를 숨깁니다.
        $(".add_answer").hide();

        // 해당 qna_idx에 맞는 .add_answer 요소를 보입니다.
        $("#answer_form_" + qnaIdx).show();
		let txt = $(this).parent().prev().text();   // txt : 기존 답글 문자열
		$("#answer_form_" + qnaIdx + " .add_answer_input").val(txt);
    });

// 답글달기 누르면 수정 버튼 없애기

	$(".qna_button").click(function() {
		$(".add_answer_button").css('display','none');
		$(".add_answer_submit").css('display','block');
     });
// 수정버튼 누르면 입력 버튼 없애기
	$(".answer_update").click(function() {
		$(".add_answer_submit").css('display','none');
		$(".add_answer_button").css('display','block');
     });

// 답변 변경
	$(".add_answer_button").click(function() {
		//파라미터 값으로 들어갈 인풋 경로 넣기
		let content = $(this).closest(".add_answer").find(".add_answer_input").val();
		//alert("content : " + content);
		let qna_idx = $(this).closest("tr").prev().attr("qna_idx");
		//alert("qna_idx : " + qna_idx);
		
		$.ajax({
		    url: 'UpdateQnaAnswerServlet', // 만들어둔 서블릿 파일 이름 적기
		    data : {content : content, qna_idx : qna_idx},		// 받아놓은 파라미터
			contentType: 'application/json; charset=utf-8',

		    success: function (data) {
				//성공시 원하는 기능 넣기
				alert("수정되었습니다 : " + data.content);  // 성공		
		    },
		    error: function (data, status, err) {
				alert("error.");
		    }
		});
	});
	
	// 답변 삭제
	$(".answer_delete").click(function() {
    	let answer_idx = $(this).closest("tr").attr("answer_idx");
		let _this = $(this);
		$.ajax({
		    type: 'post',
		    url: 'QnaAnswerDeleteServlet',
		    data: { answer_idx: answer_idx },
		    success: function(response){
			// [정리] $.ajax()의 success함수 안에서 $(this)를 못써!
				_this.closest("tr").prev().prev().find(".reply_status").text("미답변");
            	_this.closest("tr").prev().prev().find(".qna_button").css('display','block');
		    	$(".qna_answer[answer_idx='" + answer_idx + "']").remove(); // 해당 답변 행 삭제

		    },
		    error: function(data, status, err){
		        alert('실패');
		    }
		});
	});
	
	

	

	
});

