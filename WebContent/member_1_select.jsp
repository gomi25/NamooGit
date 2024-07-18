<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="../css/member_1_select.css"/>
</head>
<body>
	<form>
		<div id="member_select">
<!-------------------담당자 검색 부분------------------------>
			<div id="search1">
				<div id="search_parents" class="fl re"><input id="search" type="search" placeholder="담당자를 입력하세요" ></div>
				<div class="ab"><img id="search_icon" src="../img/돋보기.png"/></div>
			</div>
			
<!-------------------선택된 멤버------------------------>					
			<div id="select_member">
				<div id="box" class="re">
					<div class="ab"><img id="mini_profile" src="../img/profile_1.png"/></div>
					<div id="mini_profile_name" class="ab">김현지</div>
					<button class="ab" id="x_img"></button>
				</div>
				<div id="box" class="re">
					<div class="ab"><img id="mini_profile" src="../img/profile_1.png"/></div>
					<div id="mini_profile_name" class="ab">김현지</div>
					<button class="ab" id="x_img"></button>
				</div>
			</div>
<!-------------------멤버 전체------------------------>			
			<div id="allmember">
<!-------------------멤버 프로필 부분------------------------>		
				<div id="member"> 
					<div class="fl"><img id="check" src="../img/check_2.png"/></div>
					<div class="fl"><img id="profile" src="../img/profile_1.png"/></div>
					<div id="name" class="fl">김현지</div>
					
				</div>
				<div id="member"> 
					<div class="fl"><img id="check" src="../img/check_2.png"/></div>
					<div class="fl"><img id="profile" src="../img/profile_1.png"/></div>
					<div id="name" class="fl">김현지</div>
					
				</div>
			</div>
		
<!-------------------bottom------------------------>		
			<div id="bottom">
				<button id="delete">전체삭제</button>
				<button id="select">선택</button>
				
			</div>
			
			
				
		</div>
	</form>
</body>
</html>