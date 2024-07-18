<%@page import="dao.NamooMemberDao"%>
<%@page import="dao.NamooDashboardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	int memberIdx = 1;
	NamooDashboardDao dashDao = new NamooDashboardDao();
	NamooMemberDao memberDao = new NamooMemberDao();

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooDashboardMain.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="js/NamooDashboardMain.js"></script>
</head>
<body>
	<!-- 투명 스크린  : 화면을 클릭했을 때 창이 꺼지는 기능 -->
	<div id="transparent_screen"></div>
	<!-- 회색 스크린 : 팝업 화면 떴을 떄 뒤에 화면  -->
	<div id="grey_screen1"></div>
	<div id="grey_screen2"></div>
	<!------------------------- 대시보드 -------------------------------->
	<div id="div_dashboard" class="border fl">
		<!-- 대시보드 영역 -->
		<div id="div_dashboard_area">
			<!-------------------------헤더 ------------------------------>
			<div id="div_dashboard_header">
				<!-- 헤더 > 인사 -->
				<div class="fl">
					<div class="fl"><%= memberDao.showName(memberIdx)%></div> 
					<div id="div_Greeting" class="fl"></div>
				</div>
				<!-- 헤더 > 날짜 -->
				<div class="fr" id="current_date">Loading...</div>
				<!-- 헤더 > 위젯설정 -->
				<div id="widget_setting_button" class="fr">
					<img src="https://flow.team/flow-renewal/assets/images/dashboard/icon-dashboard-widget-set-light.svg?v=88a6e5dbe751cde146937ff9e3264af3b6240444">
					<div class="fr">위젯 설정</div>
				</div>
			</div>
			<!------------------------ 위젯 ------------------------------>
			<div id="div_widget">
			<!------------------------초기화면 ---------------------------->
				<div id="div_add_widget" class="add_widget">
					<div><!-- 위젯추가 아이콘 --></div>
					<div>위젯을 추가해 주세요</div>
				</div>
				<!----------------------파일함 영역 ------------------------>
				<!-- 파일함 : 큰 버전(초기) -->
				<div class="widget_big fl">
					<div class="widget_header">
						파일함
						<div class="widget_menu_icon">
							<!-- 파일함 : 위젯 삭제 및 변경 창 -->
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<!-- 파일함 : 초기 화면 -->
					<div id="big_filebox_prime"class="widget_article">
						<div class="fl"> 
						선택된 프로젝트가 없습니다.<br/>
						버튼을 눌러 프로젝트를 선택하세요.
						</div>
						<!-- 파일함 : ㅣ추가 버튼 -->
						<div class="add_button fr">추가</div>
					</div>
				</div>
				<!-- 파일함 : 작은 버전(초기) -->
				<div class="widget_small fl">
					<div class="widget_header">
						파일함
						<div class="widget_menu_icon">
							<!-- 파일함 : 위젯 삭제 및 변경 창 -->
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<!-- 파일함 : 초기 화면 -->
					<div id="small_filebox_prime"class="widget_article">
						<div class="fl"> 
						선택된 프로젝트가 없습니다.<br/>
						버튼을 눌러 프로젝트를 선택하세요.
						</div>
						<!-- 파일함 : ㅣ추가 버튼 -->
						<div class="add_button fr">추가</div>
					</div>
				</div>
				<!-- 파일함 : 큰 버전 -->
				<div class="widget_big fl">
					<div class="widget_header">
						[나무] 이용가이드
						<div class="widget_menu_icon">
							<!-- 파일함 : 위젯 삭제 및 변경 창 -->
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<!-- 파일함 : 내용 영역 -->
					<div class="widget_article">
						<!-- 파일함 : 파일1 -->
						<div id="widget_file_big" class="filebox_file_big fl">
							<div class="fl"><img src="https://flow.team/flow-renewal/assets/images/icons/icon-file-img.svg?v=acc8a8380757dfb404ec8bbf84084032c1d64664"></div>
							<div class="fl">
								<div id="first_row" class="fl">
									<div class="fl">퀵가이드.jpg</div>
									<div class="fl">358.33 KB</div>
								</div>
								<div id="second_row" class="fl">
									<div class="fl">나무 운영자</div>
									<div class="fl">2024-06-12 09:37</div>
								</div>
							</div>
						</div>
						<!-- 파일함 : 파일2 -->
						<div id="widget_file_big" class="filebox_file_big fl margin_left">
							<div class="fl"><img src="https://flow.team/flow-renewal/assets/images/icons/icon-file-img.svg?v=acc8a8380757dfb404ec8bbf84084032c1d64664"></div>
							<div class="fl">
								<div id="first_row" class="fl">
									<div class="fl">퀵가이드.jpg</div>
									<div class="fl">358.33 KB</div>
								</div>
								<div id="second_row" class="fl">
									<div class="fl">나무 운영자</div>
									<div class="fl">2024-06-12 09:37</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 파일함 : 작은 버전 -->
				<div class="widget_small fl">
					<div class="widget_header">
						[나무] 이용가이드
						<div class="widget_menu_icon">
							<!-- 파일함 : 위젯 삭제 및 변경 창 -->
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<!-- 파일함 : 내용 영역 -->
					<div class="widget_article">
						<!-- 파일함 : 파일1 -->
						<div id="widget_file_small" class="filebox_file_small fl">
							<div class="fl"><img src="https://flow.team/flow-renewal/assets/images/icons/icon-file-img.svg?v=acc8a8380757dfb404ec8bbf84084032c1d64664"></div>
							<div class="fl">
								<div id="first_row" class="fl">
									<div class="fl">퀵가이드.jpg</div>
									<div class="fl">358.33 KB</div>
								</div>
								<div id="second_row" class="fl">
									<div class="fl">나무 운영자</div>
									<div class="fl">2024-06-12 09:37</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--------------------- 메모장 영역 --------------------------->
				<!-- 메모장 : 작은 버전 -->
				<div id="div_memo_small" class="widget_small fl">
					<div class="widget_header"> 
						메모장 
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div class="widget_article">
						<textarea placeholder="메모를 입력하세요."></textarea>
					</div>
				</div>
				<!-- <div style="clear:both"> -->
				</div>
				<!-- 메모장 : 큰 버전 -->
				<div id="div_memo_big" class="widget_big fl">
					<div class="widget_header"> 
						메모장 
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div class="widget_article">
						<textarea placeholder="메모를 입력하세요."></textarea>
					</div>
				</div>
				<!-- <div style="clear:both"> -->
				<!---------------------- 프로젝트 --------------------------->
				<!-- 프로젝트 : 초기창 큰 버전 -->
				<div id="div_project_big" class="widget_big fl">
					<div class="widget_header">
						프로젝트
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_project_prime" class="widget_article">
						<div> 
						선택된 프로젝트가 없습니다.<br/>
						버튼을 눌러 프로젝트를 선택하세요.
						</div>
						<div class="add_button"> 추가 </div>
					</div>
				</div>
				<!-- 프로젝트 : 초기창 작은 버전 -->
				<div id="div_project_small" class="widget_small fl">
					<div class="widget_header">
						프로젝트
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_project_prime" class="widget_article">
						<div> 
						선택된 프로젝트가 없습니다.<br/>
						버튼을 눌러 프로젝트를 선택하세요.
						</div>
						<div class="add_button"> 추가 </div>
					</div>
				</div>
				<!-- 프로젝트 : 큰 버전 -->
				<div id="div_project_big" class="widget_big fl">
					<div class="widget_header">
						[나무] 이용가이드
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_project" class="widget_article">
						<div id="big_project_table">
							<table class="project_td">
								<tr>
									<td><div class="post_icon"></div></td>
									<td>[오프라인 나무 설명회] 전문 컨설턴트를 직접 만나보세요.</td>
								</tr>
								<tr>
									<td><div class="todo_icon"></div></td>
									<td>[필수] 가이드 따라해보기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 1.프로필 세팅하기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 2.프로필 만들어보기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 3.핵심 기능! 업무 활용하기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 4.나무를 사용하다 궁금한 점이 생긴다면?</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 4.나무를 사용하다 궁금한 점이 생긴다면?</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 4.나무를 사용하다 궁금한 점이 생긴다면?</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!-- 프로젝트 : 작은 버전 -->
				<div id="div_project_small" class="widget_small fl">
					<div class="widget_header">
						[나무] 이용가이드
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_project" class="widget_article">
						<div id="small_project_table">
							<table class="project_td">
								<tr>
									<td><div class="post_icon"></div></td>
									<td>[오프라인 나무 설명회] 전문 컨설턴트를 직접 만나보세요.</td>
								</tr>
								<tr>
									<td><div class="todo_icon"></div></td>
									<td>[필수] 가이드 따라해보기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 1.프로필 세팅하기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 2.프로필 만들어보기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 3.핵심 기능! 업무 활용하기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 4.나무를 사용하다 궁금한 점이 생긴다면?</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 4.나무를 사용하다 궁금한 점이 생긴다면?</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 4.나무를 사용하다 궁금한 점이 생긴다면?</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!-- 프로젝트 추가 창 -->
<!-- 			<div id="div_add_project" class=" border">
				<div class="border"></div>
			</div> -->
				<!---------------------- 북마크 영역 ---------------------------->
				<!-- 북마크 : 초기창 큰 버전 -->
				<div id="div_project_big" class="widget_big fl">
					<div class="widget_header">
						북마크
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_project_prime" class="widget_article">
						<div> 
						북마크한 게시글이 없습니다.
						</div>
					</div>
				</div>
				<!-- 북마크 : 초기창 작은 버전 -->
				<div id="div_project_small" class="widget_small fl">
					<div class="widget_header">
						북마크
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_project_prime" class="widget_article">
						<div> 
						북마크한 게시글이 없습니다.
						</div>
					</div>
				</div>			
				<!-- 북마크 : 큰 버전 -->
				<div id="div_project_big" class="widget_big fl">
					<div class="widget_header">
						북마크
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_project" class="widget_article">
						<div id="big_project_table">
							<table class="project_td">
								<tr>
									<td><div class="post_icon"></div></td>
									<td>[오프라인 나무 설명회] 전문 컨설턴트를 직접 만나보세요.</td>
								</tr>
								<tr>
									<td><div class="todo_icon"></div></td>
									<td>[필수] 가이드 따라해보기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 1.프로필 세팅하기</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!-- 북마크 : 작은 버전 -->
				<div id="div_project_small" class="widget_small fl">
					<div class="widget_header">
						북마크
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_project" class="widget_article">
						<div id="small_project_table">
							<table class="project_td">
								<tr>
									<td><div class="post_icon"></div></td>
									<td>[오프라인 나무 설명회] 전문 컨설턴트를 직접 만나보세요.</td>
								</tr>
								<tr>
									<td><div class="todo_icon"></div></td>
									<td>[필수] 가이드 따라해보기</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>STEP 1.프로필 세팅하기</td>
								</tr>
							</table>
						</div>
					</div>
				</div>			
				<!------------------------ 나를 언급 ------------------------>
				<!-- 나를 언급 : 초기창 큰 버전 -->
				<div id="div_project_big" class="widget_big fl">
					<div class="widget_header">
						나를 언급
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_project_prime" class="widget_article">
						<div> 
						나를 언급한 글이 없습니다.
						</div>
					</div>
				</div>
				<!-- 나를 언급 : 초기창 작은 버전 -->
				<div id="div_project_small" class="widget_small fl">
					<div class="widget_header">
						나를 언급
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_project_prime" class="widget_article">
						<div> 
						나를 언급한 글이 없습니다.
						</div>
					</div>
				</div>			
				<!-- 나를 언급: 큰 버전 -->
				<div id="div_project_big" class="widget_big fl">
					<div class="widget_header">
						나를 언급
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_project" class="widget_article">
						<div id="big_project_table">
							<table class="project_td">
								<tr>
									<td><div class="post_icon"></div></td>
									<td>member_idx 님 확인하셔야 할 것 같습니다.</td>
								</tr>
								<tr>
									<td><div class="todo_icon"></div></td>
									<td>memver_idx 투두리스트 확인하세요.</td>
								</tr>
								<tr>
									<td><div class="mention_icon"></div></td>
									<td>member_idx 확인 필요.</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>member_idx 업무 추가했습니다.</td>
								</tr>
								<tr>
									<td><div class="mention_icon"></div></td>
									<td>member_idx 확인 필요.</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!-- 나를 언급 : 작은 버전 -->
				<div id="div_project_small" class="widget_small fl">
					<div class="widget_header">
						나를 언급
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_project" class="widget_article">
						<div id="small_project_table">
							<table class="project_td">
								<tr>
									<td><div class="post_icon"></div></td>
									<td>member_idx 님 확인하셔야 할 것 같습니다.</td>
								</tr>
								<tr>
									<td><div class="todo_icon"></div></td>
									<td>memver_idx 투두리스트 확인하세요.</td>
								</tr>
								<tr>
									<td><div class="mention_icon"></div></td>
									<td>member_idx 확인 필요.</td>
								</tr>
								<tr>
									<td><div class="task_icon"></div></td>
									<td>member_idx 업무 추가했습니다.</td>
								</tr>
								<tr>
									<td><div class="mention_icon"></div></td>
									<td>member_idx 확인 필요.</td>
								</tr>
							</table>
						</div>
					</div>
				</div>	
				<!---------------------- 미확인 알림 ---------------------------->
				<!-- 미확인 알림 : 초기창 큰 버전 -->
				<div id="div_unread_notification" class="widget_big fl">
					<div class="widget_header">
						미확인 알림
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로
								
								 변경</div>
							</div>
						</div>
					</div>
					<div id="big_unread_notification_prime" class="widget_article">
						<div> 
						모든 알림을 확인했습니다.
						</div>
					</div>
				</div>
				<!-- 미확인 알림 : 초기창 작은 버전 -->
				<div id="div_unread_notification" class="widget_small fl">
					<div class="widget_header">
						미확인 알림
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>큰 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="small_unread_notification_prime" class="widget_article">
						<div> 
						모든 알림을 확인했습니다.
						</div>
					</div>
				</div>
				<!-- 미확인 알림: 큰 버전 -->
				<div id="div_unread_notification" class="widget_big fl">
					<div class="widget_header">
						미확인 알림
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_unread_notification" class="widget_article">
						<ul class="unread_ul">
							<li>
								<div class="list_area list_border">
									<div class="list_1">채팅방 이름</div>
									<div class="list_2">강하늘님의 할일 등록</div>
									<div class="list_3">무엇을 더 추가해야 될까</div>
								</div>
							</li>
							<li>
								<div class="list_area list_border">
									<div class="list_1">채팅방 이름</div>
									<div class="list_2">강하늘님의 댓글 등록</div>
									<div class="list_3">'글 수정 기능 등등', 항목 완료!</div>
								</div>
							</li>
							<li>
								<div class="list_area list_border">
									<div class="list_1">채팅방 이름</div>
									<div class="list_2">강하늘님의 댓글 등록</div>
									<div class="list_3">'할일 제목 이모티콘 기능 추가 창', 항목 완료!</div>
								</div>
							</li>
						</ul>
					</div>
				</div>				
				<!-- 미확인 알림: 작은 버전 -->
				<div id="div_unread_notification" class="widget_small fl">
					<div class="widget_header">
						미확인 알림
						<div class="widget_menu_icon">
							<div class="change_widget_size" >
								<div>위젯 삭제</div>
								<div>작은 위젯으로 변경</div>
							</div>
						</div>
					</div>
					<div id="big_unread_notification" class="widget_article">
						<ul class="unread_ul">
							<li>
								<div class="list_area list_border">
									<div class="list_1">채팅방 이름</div>
									<div class="list_2">강하늘님의 할일 등록</div>
									<div class="list_3">무엇을 더 추가해야 될까</div>
								</div>
							</li>
							<li>
								<div class="list_area list_border">
									<div class="list_1">채팅방 이름</div>
									<div class="list_2">강하늘님의 댓글 등록</div>
									<div class="list_3">'글 수정 기능 등등', 항목 완료!</div>
								</div>	
							</li>
							<li>
								<div class="list_area list_border">
									<div class="list_1">채팅방 이름</div>
									<div class="list_2">강하늘님의 댓글 등록</div>
									<div class="list_3">'할일 제목 이모티콘 기능 추가 창', 항목 완료!</div>
								</div>
							</li>
						</ul>
					</div>
				</div>								
			</div><!-- 대시보드 위젯 영역 끝  -->
		</div><!-- 대시보드 전체 영역 끝 -->
	
	<!--========================= 팝업창 ============================-->
	<!--____________________ 프로젝트 팝업 _______________________-->	
	<form>
		<div id="popup_add_project">
			<div id="project_popup_header">
				<div class="fl">프로젝트 선택</div>
				<div class="fr"></div>
			</div>	
			<div id="project_popup_search">
				<input type="text" placeholder="프로젝트명으로 검색">
				<div></div>
			</div>
			<div id="project_popup_area">
				<div class="project_popup">
					<div class="project_popup1 fl"><input type="checkbox" id="project_checkbox001" name="project" value="select_project"/><label for="project_checkbox001"></label></div>
					<div class="fl"><label for="project_checkbox001"> <div class="fl project_color"><!-- 프로젝트 컬러 --></div></label></div>
					<div class="project_popup3 fl"><label for="project_checkbox001"> <div class="fl">[나무] 이용가이드</div></label></div>
				</div>
				<div class="project_popup">
					<div class="project_popup1 fl"><input type="checkbox" id="project_checkbox002" name="project" value="select_project"/><label for="project_checkbox001"></label></div>
					<div class="project_popup2 fl"><label for="project_checkbox002"> <div class="fl project_color"><!-- 프로젝트 컬러 --></div></label></div>
					<div class="project_popup3 fl"><label for="project_checkbox002"> <div class="fl">[나무] namoo 전용 문의방</div></label></div>
				</div>
			</div>
			<div id="project_popup_button">
				<div class="fl">취소</div>
				<div class="fl"><input type="submit" value="확인" onclick="return select_project();"></div>
			</div>
		</div>
	</form>
	<!--________________________ 위젯 팝업 ________________________-->
	<div id="popup_add_widget">
		<div id="widget_popup_header">
			<div class="fl">위젯 설정</div>
			<div class="fr"><!-- 'x' 닫기 버튼 --></div>
		</div>
		<!-- (Big)메모장 위젯 추가 -->
		<div id="widget_popup_body">
			<div id="popup_memo" class="widget_popup_big font fl">
				메모장
				<div class="widget_popup_big_add">
					<div>추가</div>
				</div>
			</div>
			<!-- (Small)프로젝트 위젯 추가 -->
			<div id="popup_project" class="widget_popup_small font fl">
				프로젝트
				<div class="widget_popup_small_add">
					<div>추가</div>
				</div>
			</div>
			<div id="popup_project_added" class="widget_popup_small font fl">프로젝트</div>
			<!-- (Big)파일함 위젯 추가 -->			
			<div id="popup_filebox" class="widget_popup_big font fl">
				파일함
				<div class="widget_popup_big_add">
					<div>추가</div>
				</div>
			</div>
			<!-- (Small) 나를 언급 위젯 추가 -->
			<div id="popup_mention" class="widget_popup_small font fl">
				나를 언급
				<div class="widget_popup_small_add">
					<div>추가</div>
				</div>
			</div>
			<div id="popup_mention_added" class="widget_popup_small font fl">나를 언급</div>
			<!-- (Big) 북마크 위젯 추가 -->			
			<div id="popup_bookmark" class="widget_popup_big font fl">
				북마크
				<div class="widget_popup_big_add">
					<div>추가</div>
				</div>
			</div>
			<div id="popup_bookmark_added" class="widget_popup_big font fl">북마크</div>
			<!-- (Small)미확인 알림 위젯 추가 -->
			<div id="popup_notification" class="widget_popup_small font fl">
				미확인 알림
				<div class="widget_popup_small_add">
					<div>추가</div>
				</div>
			</div>
			<div id="popup_notification_added" class="widget_popup_small font fl">미확인 알림</div>
		</div>
		<div id="div_added_widget">위젯이 추가되었습니다.</div>
	</div>
	
</body>
</html>




















