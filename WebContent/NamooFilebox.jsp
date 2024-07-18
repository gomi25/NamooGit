<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooFilebox.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function(){
			// 파일 명을 누르면 화살표 방향 반대로
			$("#file_name").click(function(){
				$("#file_volume, #file_uploader, #file_save_date").css('color','#888888');
				$("#file_name").css('color','#1edc8b');
				if($("#file_name > div:nth-child(1)").css('display')==='none'){
					$("#file_name > div:nth-child(1)").css('display','block');
					$("#file_name > div:nth-child(2)").css('display','none');
				} else {
					$("#file_name > div:nth-child(1)").css('display','none');
					$("#file_name > div:nth-child(2)").css('display','block');
				}
			});
			// 파일 용량을 누르면 화살표 방향 반대로
			$("#file_volume").click(function(){
				$("#file_name, #file_uploader, #file_save_date").css('color','#888888');
				$("#file_volume").css('color','#1edc8b');
				if($("#file_volume > div:nth-child(1)").css('display')==='none'){
					$("#file_volume > div:nth-child(1)").css('display','block');
					$("#file_volume > div:nth-child(2)").css('display','none');
				} else {
					$("#file_volume > div:nth-child(1)").css('display','none');
					$("#file_volume > div:nth-child(2)").css('display','block');
				}
			});
			// 파일 등록자를 누르면 화살표 방향 반대로
			$("#file_uploader").click(function(){
				$("#file_name, #file_volume, #file_save_date").css('color','#888888');
				$("#file_uploader").css('color','#1edc8b');
				if($("#file_uploader > div:nth-child(1)").css('display')==='none'){
					$("#file_uploader > div:nth-child(1)").css('display','block');
					$("#file_uploader > div:nth-child(2)").css('display','none');
				} else {
					$("#file_uploader > div:nth-child(1)").css('display','none');
					$("#file_uploader > div:nth-child(2)").css('display','block');
				}
			});
			// 업로드 날짜를 누르면 화살표 방향 반대로
			$("#file_save_date").click(function(){
				$("#file_name, #file_volume, #file_uploader").css('color','#888888');
				$("#file_save_date").css('color','#1edc8b');
				if($("#file_save_date > div:nth-child(1)").css('display')==='none'){
					$("#file_save_date > div:nth-child(1)").css('display','block');
					$("#file_save_date > div:nth-child(2)").css('display','none');
				} else {
					$("#file_save_date > div:nth-child(1)").css('display','none');
					$("#file_save_date > div:nth-child(2)").css('display','block');
				}
			});
		});
	</script>
</head>
<body>
	<!--------------------------- 파일함 ---------------------------------->
	<div id="div_filebox" class="fl">
		<!-- 파일함 헤더 -->
		<div id="filebox_header">파일함</div>
		<!-- 파일함 검색 및 다운로드 영역 -->
		<div id="filebox_search_download">
			<!-- 파일함 검색 및 다운로드 영역 : 검색 -->
			<form action="">
				<div id="filebox_search"class="fl">
					<div></div>
					<input type="search" id="input_filebox_search" placeholder="파일명을 입력해주세요.">
					<select name="talkroom_name">
						<option value="모든 대화방"> 모든 대화방
						<option value="참여한 대화방"> 참여한 대화방
					</select>
					<select name="member_name">
						<option value="전체"> 전체
						<option value="김현지"> 김현지
						<option value="원혜경"> 원혜경
						<option value="김민지"> 김민지
						<option value="강하늘"> 강하늘
					</select>
					<select name="file_type">
						<option value="PDF"> PDF
						<option value="워드"> 워드
						<option value="한글"> 한글
					</select>
				</div>
			</form>
			<!-- 파일함 검색 및 다운로드 영역 : 다운로드 -->
			<div id="filebox_download" class="fr">
				<div><!-- 다운로드 이미지 --></div>
				<div>다운로드</div>
			</div>
		</div>
		<!-- 파일함 바디 영역 -->
		<div id="filebox_body">
			<div id="filebox_body_header">
				<div id="file_name" class="fl">
					파일명
					<div class="desc_order"></div>
					<div class="asc_order"></div>
				</div>
				<div id="file_volume" class="fl">
					용량
					<div class="desc_order"></div>
					<div class="asc_order"></div>
				</div>
				<div id="file_uploader" class="fl">
					등록자
					<div class="desc_order"></div>
					<div class="asc_order"></div>
				</div>
				<div id="file_save_date" class="fl">
					등록일
					<div class="desc_order"></div>
					<div class="asc_order"></div>
				</div>
			</div>
			<!-- 파일 목록 -->
			<div id="filebox_body_body">
			<label for="file_checkbox001">
				<div class="file">
					<div id="body_file_name" class="fl">
						<div class="fl"><input type="checkbox" id="file_checkbox001" name="file" value="file01"/><label for="file_checkbox"></label></div>
						<div class="fl"><div class="file_img"><!-- 파일이미지 --></div></div>
						<div id="file_name_area">
							<div class="fl">퀵가이드.jpg</div>
							<div class="fl">[나무] 프로젝트 명 </div>
						</div>
					</div>
					<div class="fl">358.33KB</div>
					<div class="fl"> 나무 운영자 </div>
					<div class="fl">2024-06-29 14:33</div>
				</div>
			</label>
			<label for="file_checkbox002">
				<div class="file">
					<div id="body_file_name" class="fl">
						<div class="fl"><input type="checkbox" id="file_checkbox002" name="file" value="file02"/><label for="file_checkbox"></label></div>
						<div class="fl"><div class="file_img"><!-- 파일이미지 --></div></div>
						<div id="file_name_area">
							<div class="fl">퀵가이드.jpg</div>
							<div class="fl">[나무] 프로젝트 명 </div>
						</div>
					</div>
					<div class="fl">358.33KB</div>
					<div class="fl"> 나무 운영자 </div>
					<div class="fl">2024-06-29 14:33</div>
				</div>
			</label>
			</div>
		</div>
	</div>
</body>
</html>








