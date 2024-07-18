<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/invite.css"/>
</head>
<body>
	<div id="invite"> 
	
<!--------------------- header 부분 ----------------------->
		<div id="invite_header">
			<h4 class="fl invite_header_name">참여자 초대</h4>
			<div class="fr"><img class="invite_header_img" src="../img/x.png"></div>
		</div>
<!--------------------- body 부분 ----------------------->
		<div class="invite_body re">
		<!--------------------- body left 부분 ----------------------->
			<div class="fl invite_left ab">
				<div class="invite_search">
					<div id="search_parents" class="fl re"><input id="search" type="search" placeholder="이름,소속,전화번호 검색" ></div>
					<div class="ab"><img id="search_icon" src="../img/돋보기.png"/></div>
				</div>
				<div class="invite_member_list">
					
					
						  <div class="member_list">
						     <div class="fl"><input type="checkbox" id="project_checkbox" name="project" value="select_project"/><label for="project_checkbox"></label></div>
						     <div class="fl"><label for="project_checkbox"><div><img src="../img/profile_1.png"></div></label></div>
						     <div><label for="project_checkbox"><div class="member_name">김현지</div></label></div>
						  </div>
						  <div class="member_list">
						     <div class="fl"><input type="checkbox" id="project_checkbox1" name="project" value="select_project"/><label for="project_checkbox1"></label></div>
						     <div class="fl"><label for="project_checkbox1"><div><img src="../img/profile_1.png"></div></label></div>
						     <div><label for="project_checkbox1"><div class="member_name">강하늘</div></label></div>
						  </div>
					

					
				</div>
			</div>
		<!--------------------- body right 부분 ----------------------->
			<div class="fr invite_right">
				<div class="invite_right_txt">대상을 선택해주세요.</div>
				
			</div>
			
			<div class="fr invite_right2">
				
				<div class="right_header"><button class="right_delete fr">전체삭제</button></div>
				
				<!--------------------- 선택된 멤버----------------------->
				<div id="select_member">
				<div id="box" class="fl">
					<div class="fl"><img id="mini_profile" src="../img/profile_1.png"/></div>
					<div class="fl" id="mini_profile_name" >김현지</div>
					<button class="fl" id="x_img"></button>
				</div>
				<div id="box" class="fl">
					<div class="fl"><img id="mini_profile" src="../img/profile_1.png"/></div>
					<div class="fl" id="mini_profile_name" >김현지</div>
					<button class="fl" id="x_img"></button>
				</div>
				<div id="box" class="fl">
					<div class="fl"><img id="mini_profile" src="../img/profile_1.png"/></div>
					<div class="fl" id="mini_profile_name" >김현지</div>
					<button class="fl" id="x_img"></button>
				</div>
				<div id="box" class="fl">
					<div class="fl"><img id="mini_profile" src="../img/profile_1.png"/></div>
					<div class="fl" id="mini_profile_name">김현지</div>
					<button class="fl" id="x_img"></button>
				</div>
				</div>
				
			</div>
			
	
		
<!--------------------- bottom ----------------------->		
		<div id="invite_bottom">
		<div class="invite_bottom_in">
			<div class="fl"><button class="invite_delete_btn">취소</button></div>
			<div class="fl"><button class="invite_invite_btn">초대하기</button></div>
		</div>
		</div>
		
	</div>
	</div>
</body>
</html>