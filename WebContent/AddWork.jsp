<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>namoo</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="../css/addwork.css"/>
	<link rel="stylesheet" href="../css/prioritize.css"/>
	<link rel="stylesheet" href="../css/state.css"/>
	<script src="../js/AddWork.js"></script>
	<script>
	
	</script>
</head>
<body>
<form>
<div id="div_transparent_filter"></div>
	<div id="add_work" class="border re">
<!----------------- 업무작성 header부분 ------------------->
		<div id="addwork_header">
			<div class="fl">
				<div class="fl writework">업무작성 </div>
				<div id="addwork_thin" class="fl"></div>
				<div id="addwork_color" class="fl"></div> 
				<div class="addwork_name fl">namoo</div>
			</div>
			<div class="fr">
				<div class="fr"><img class="x_button" src="../img/x_button.png"></div>
				<div class="fr"><img class="_button" src="../img/addwork_header_thin.png"></div>
			</div>
		</div>
<!----------------- 입력,선택 부분 ------------------->
		<div id="addwork_body">
			<div id="work_name"><input type="text"  placeholder="제목을 입력하세요."></div>
			<div id="work_state">
				<div class="fl"><img src="https://flow.team/flow-renewal/assets/images/icons/icon-post-status.svg?v=e34bb28e53230b9b3126b8569fa68435513481cf"/></div>
				<div id="request" class="state fl"><div class="state_div request">요청</div></div>
				<div id="progress" class="state fl"><div class="state_div progress">진행</div></div>
				<div id="feedback" class="state fl"><div class="state_div feedback">피드백</div></div>
				<div id="finish" class="state fl"><div class="state_div finish">완료</div></div>
				<div id="defer" class="state fl"><div class="state_div defer">보류</div></div>
				
				
			</div>
			
				
			
			<table class="tr">
				<tr>
					<td><div id="work_member" class="fl"><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-worker.svg?v=380ce20a38f299cd0fe7e55945f32981cb48d5f9"></div></td>
					<td><input id="work_member_input" type="text" placeholder="담당자 추가"></td>
				</tr>
				<tr>
					<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=3262c0db463cb0f5234ebaf889864da403097027"></td>
					<td class="td_txt"><u>시작일 추가</u></td>
				</tr>
				<tr>
					<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=3262c0db463cb0f5234ebaf889864da403097027"></td>
					<td class="td_txt"><u>마감일 추가</u></td>
				</tr>
				<tr class="re">
					<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-rank.svg?v=aa8b193b920acc7458e719bb84e9e29c7329457d"></td>
					<td class="td_txt add_prioritize"><u>우선순위 추가</u></td>
					<td class="add_low"><div class="fl add_low"><img class="fl add_img" src="../img/low.png"><div class="add_font fl">낮음</div><img class="add_x_img" src="../img/x.png"></div></td>
					<td><div class="add_normal"><img class="fl add_img add_nomal_img"  src="../img/normal.png"><div class="add_font fl">보통</div><img class="add_x_img" src="../img/x.png"></div></td>
					<td><div class="add_hight"><img class="fl add_img" src="../img/hight.png"><div class="add_font fl">높음</div><img class="add_x_img" src="../img/x.png"></div></td>
					<td><div class="add_emergency"><img class="fl add_img" src="../img/emergency.png"><div class="add_font fl">긴급</div><img class="add_x_img" src="../img/x.png"></div></td>

					
				</tr>
				
				<tr class="re">
					<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-section.svg?v=7fb1fd5e61a6ba4028ee6bea5164b39dc443546d"></td>
					<td class="td_txt add_group" ><u>그룹 추가</u></td>
					<td><div class="select_group1" style="display:none;"><div class="fl">그룹1</div><img class="add_x_img" src="../img/x.png"></div></td>
					<td><div class="select_nogroup" style="display:none;"><div class="fl">그룹미지정</div><img class="add_x_img" src="../img/x.png"></div></td>
<!----------------- 그룹추가 박스 ------------------->
					<div id="addgroup" class="ab">
						<div class="groupname_box group1"><div class="groupname_font">그룹1</div></div>
						<div class="groupname_box nogroup"><div class="groupname_font">그룹 미지정</div></div>
					</div>
				</tr>
				
				
			</table>

<!----------------- 우선순위 박스 ------------------->
			<div id="prioritize" class="ab">
				<div class="prioritize_font low"><img class="img fl" src="../img/low.png">낮음</div>
				<div class="prioritize_font normal"><img class="img" src="../img/normal.png">보통</div>
				<div class="prioritize_font hight"><img class="img" src="../img/hight.png">높음</div>
				<div class="prioritize_font emergency"><img class="img" src="../img/emergency.png">긴급</div>
				
			</div>
			

			
			
<!----------------- 내용 입력 ------------------->
			<textarea rows="6" cols="80" placeholder="내용을 입력하세요."></textarea>
			
<!----------------- 하위업무 입력 ------------------->			
			<div id="subwork">하위업무</div>
			<div>
				<div id="subwork_button">+ 추가</div>
				
<!----------------- 하위업무 박스 ------------------->					
			<div id="mini_subwork" class="re"> 
				<div class="fl subwork_state subwork_request"><div>요청</div></div>
				<div style="display: none;" class="fl subwork_state subwork_progress"><div>진행</div></div>
				<div style="display: none;" class="fl subwork_state subwork_feedback"><div>피드백</div></div>
				<div style="display: none;" class="fl subwork_state subwork_finish"><div>완료</div></div>
				<div style="display: none;" class="fl subwork_state subwork_defer"><div>보류</div></div>
				
				<div><input id="subwork_name" type="text" placeholder="업무명 입력"></div>
				<div class="fr mini_subwork_icon">
					<div class="mini_subwork_img_div subwork_member fr"><img class="mini_subwork_img2" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-worker.svg?v=dab609f4e3114ab334c0216bd41d1a6e27b6503a"></div>
					<div class="mini_subwork_img_div subwork_prioritize2 fr"><img class="mini_subwork_img" src="https://cdn-icons-png.flaticon.com/128/7434/7434629.png"></div>
					
					<div style="display: none;" class="fr mini_smalllow"><img class="mini_img" src="../img/low.png"/></div>
					<div style="display: none;"  class="fr mini_smallnoaml" ><img class="mini_img" src="../img/normal.png"/></div>
					<div style="display: none;" class="fr mini_smallhight"><img class="mini_img" src="../img/hight.png"/></div>
					<div style="display: none;" class="fr mini_smallemergency"><img class="mini_img" src="../img/emergency.png"/></div>
					
					<div class="mini_subwork_img_div fr"><img class="mini_subwork_img2" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=118ef1e91b7ced5f275a138083d2ddd7cf94773d"></div>
				</div>
				
				
				
			</div>
<!----------------하위업무 (상태)-------------------->			
			
			<div id="mini_state" class="ab" style="margin-top: -225px;">
				<div class="box_request box_state">요청</div>
				<div class="box_progress box_state">진행</div>
				<div class="box_feedback box_state">피드백</div>
				<div class="box_finish box_state">완료</div>
				<div class="box_defer box_state">보류</div>
			</div>
			
<!----------------- 하위업무 우선순위  ------------------->			
				<div id="prioritize2" class="fr">
					<div class="prioritize_between">
						<div class="fl"><img class="img" src="../img/x.png"/></div>
						<span class="font mini_delete">취소</span>
					</div>
					<div class="prioritize_between">
						<div class="fl"><img class="img" src="../img/low.png"/></div>
						<span class="font mini_low">낮음</span>
					</div>
					<div class="prioritize_between">
						<div class="fl"><img class="img" src="../img/normal.png"/></div>
						<span class="font mini_noaml">보통</span>
					</div>
					<div class="prioritize_between">
						<div class="fl"><img class="img" src="../img/hight.png"/></div>
						<span class="font mini_hight">높음</span>
					</div>
					<div class="prioritize_between">
						<div class="fl"><img class="img" src="../img/emergency.png"/></div>
						<span class="font mini_emergency">긴급</span>
					</div>
				</div>
					


			</div>
			
			
<!----------------- 하위업무 담당자  ------------------->


					<div id="member_select" class="ab">
			<!-------------------담당자 검색 부분------------------------>
						<div id="search1">
							<div id="search_parents" class="fl re">
								<input id="search" type="search" placeholder="담당자를 입력하세요" >
								<img class="ab" id="search_icon" src="../img/돋보기.png"/>
							</div>
							
						</div>
			<!-------------------멤버 전체------------------------>			
						<div id="allmember">
			<!-------------------멤버 프로필 부분------------------------>		
							<div id="member"> 
								<div class="fl"><img id="check" src="../img/check_1.png"/></div>
								<div class="fl"><img id="profile" src="../img/profile_1.png"/></div>
								<div id="name" class="fl">김현지</div>
								
							</div>
						</div>
					
			<!-------------------bottom------------------------>		
						<div id="member_bottom">
							<button id="alldelete">전체삭제</button>
							<button id="select">선택</button>
							
						</div>
			
			
				
				</div>
		
		</div>
			
<!----------------- bottom ------------------->				
		<div id="addwork_bottom">
			<div class="fr"><button id="registration">등록</button></div>
			<div class="fr"><button id="save">임시저장</button></div>
		</div>
	
	</div>
</form>
</body>
</html>