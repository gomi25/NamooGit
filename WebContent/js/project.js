$(function() {
	$(document).on("click", ".star", function() {
		let _this = $(this);
		let project_idx = $(this).parent().attr("project_idx");
		$.ajax({
			type: 'get',
			url: '../AjaxBookmarkOnServlet',
			data: { member_idx:member_idx_from, project_idx:project_idx },
			success: function(response){
				//alert(response.result);   // success
				let clone_object = _this.parent().clone();
				$("#bookmark").append(clone_object);  // 마지막 자식으로~
				$("#bookmark > div:last-child .star").removeClass("star").html('<img class="yellow_star" src="https://flow.team/flow-renewal/assets/images/icons/icon_star_on.png?v=ca949083bd3e2d74e7125167485cff818959483a">');
				_this.parent().remove();  // 이전 것은 삭제~
			},
			error: function(){
				alert('ajax 통신 실패');		
			}
		});
	});
	$(document).on("click", "#bookmark .yellow_star", function() {
		let _this = $(this);
		let project_idx = $(this).parent().parent().attr("project_idx");
		$.ajax({
			type: 'get',
			url: '../AjaxBookmarkOffServlet',
			data: { member_idx:member_idx_from, project_idx:project_idx },
			success: function(response){
				//alert(response.result);   // success/
				let clone_object = _this.parent().parent().clone();
				$("#participating").append(clone_object);  // 마지막 자식으로~
				$("#participating > div:last-child .yellow_star").parent().addClass("star").html("");
				_this.parent().parent().remove();   // 이전 것은 삭제~
			},
			error: function(){
				alert('ajax 통신 실패');		
			}
		});

	});
});