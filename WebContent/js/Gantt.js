$(function() {
	$(".option_button").click(function() {
    	$("#option").css('display','block');
        $("#div_transparent_filter").css('display','block');
	});
	$("#div_transparent_filter").click(function() {
        $("#option").css('display','none');
        $("#div_transparent_filter").css('display','none');
	});   

	// Gantt Chart. -----------------------------------------------
	let gantt_obj = $("#gantt").dhx_gantt({
		data: tasks
	});
	//console.log(gantt_obj);
	gantt_obj.config.grid_width = 800;
	gantt_obj.config.columns = [
	    {name:"text",       label:"업무명",  width:450, tree:true },
	    {name:"in_charge", 	label:"담당자", align:"center" },
	    {name:"status",   label:"상태",   align:"center" },
	    {name:"start_date",	label:"시작일",   align:"center" },
	    {name:"end_date",   label:"마감일",   align:"center" }
	];
	gantt_obj.init("gantt");
	gantt_obj.attachEvent("onTaskDblClick", function(id,e){
		alert("오잉 - 더블클릭을 왜 했음?");
	    return false;
	});
	// -------------------------------------------------- Gantt Chart.
	/*
	$(document).on("click", ".gantt_box_state", function(e) {
		let state_name = $(this).find("div").text();
		alert("클릭 - state : " + state_name);
		let work_idx = $(this).attr("work_idx");
		$(".mini_state").attr("work_idx", work_idx);
		$("#div_transparent_filter").css('display', 'block');
		$(".mini_state").css('display', 'block');
		$(".mini_state").css('top', (e.pageY) + 'px');
		$(".mini_state").css('left', e.pageX + 'px');
	});
	*/
	$("#div_transparent_filter").click(function() {
		$("#div_transparent_filter").css('display', 'none');
		$(".mini_state").css('display', 'none');
	});
	$(".mini_state > div").click(function() {
		alert("미니스테이트 div을 클릭했음. 왜?");
		let work_idx = $(this).parent().attr("work_idx");
		let button_class = "";
		if($(this).hasClass("request")) {
			alert("TODO(서버전송) : work_idx(" + work_idx + ")의 상태를 request로 변경 요청. &새로고침");
		} else if($(this).hasClass("progress")) {
			alert("TODO(서버전송) : work_idx(" + work_idx + ")의 상태를 progress로 변경 요청. &새로고침");
		} else if($(this).hasClass("feedback")) {
			alert("TODO(서버전송) : work_idx(" + work_idx + ")의 상태를 feedback로 변경 요청. &새로고침");
		} else if($(this).hasClass("finish")) {
			alert("TODO(서버전송) : work_idx(" + work_idx + ")의 상태를 finish로 변경 요청. &새로고침");
		} else if($(this).hasClass("defer")) {
			alert("TODO(서버전송) : work_idx(" + work_idx + ")의 상태를 defer로 변경 요청. &새로고침");
		}
	
	});
});
