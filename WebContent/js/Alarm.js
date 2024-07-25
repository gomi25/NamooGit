$(function() {
	$('.mention_files').addClass('clicked');
	
	
	$(".tool-inner-tab").click(function() {
		$(".files").css('display','block');
		$(".mention_files").css('display','none');
	 });

	$(".tool-inner-tab-mention").click(function() {
		$(".mention_files").css('display','block');
		$(".files").css('display','none');
	 });
});