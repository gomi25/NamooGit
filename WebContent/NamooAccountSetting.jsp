<%@page import="dto.MemberImageDto"%>
<%@page import="dao.NamooMemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	NamooMemberDao memberDao = new NamooMemberDao();
	int memberIdx = (Integer)session.getAttribute("memberIdx");
	//파일 올리기 앞 뒤 주소창 붙이기
	String strProfileImgUrl = memberDao.showProfilePic(memberIdx);
	if(strProfileImgUrl != null && 
			(!strProfileImgUrl.startsWith("http://") && 
			 !strProfileImgUrl.startsWith("https://"))) {
		// DB에 있는 imgUrl이 filename 이라는 것!
		strProfileImgUrl = "upload/" + strProfileImgUrl;
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>NAMOO</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooAccountSetting.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/NamooAccountSetting.js"></script>
	<script>
		$(function() {
			$("#pic_file").change(function() {
				$("#form_upload").submit();
			});
		});
	</script>
</head>
<body>
	<div id="transparent_screen"></div>
	<!-------------------------- 헤더  -------------------------->
	
	
	<div id="div_header" class="fl"> 
		<!-- 로고 -->
	 	<div id="div_logo" class="fl">
			<img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628" >
			<div><a href="Controller?command=enter_main_page_logined">NAMOO</a></div>
		</div>
		<!-- 다운로드 -->
		<div id="div_pc_app" class="fl">
			<div class="fl"></div>
			<div class="fl">PC 앱 다운로드</div>
		</div>
		<!-- 회원명 -->
		<div id="div_member_name" class="fr">
			<img src="<%=strProfileImgUrl%>">	
			<div><%=memberDao.showName(memberIdx)%></div>
		</div>
	</div>
	<!-------------------------- 바디  -------------------------->
	<div id="div_body">
		<div id="div_setting_box1" >
			<!-- 카테고리 -->
			<div id="div_category">
				<div class="fl"><a href="Controller?command=enter_main_page_logined">나무 메인</a> </div>
				<div class="fl">&nbsp > &nbsp<b> 계정 설정</b></div>
			</div>
			<!----------------------- 세팅박스 영역 ----------------------->
			<div id="div_setting_box2" member_idx="<%=memberIdx%>">
				<!-- 프사 영역 -->			
				<form id="form_upload" action="AccountSettingUploadServlet" method="post" enctype="multipart/form-data">
					<div id="div_profile_pic" >
						<div class="fl">프로필 사진</div>
						<div id="profile_pic" class="fl"><img src="<%=strProfileImgUrl%>"></div>
						<div id="select_pic" class="fl">
							<div>
								<label for="pic_file">
									<div class="pic-upload">사진 올리기</div>
								</label>
								<input type="file" name="pic_file" id="pic_file">
							</div>
							<div>
								<label for="resetImage">
									<div class="default_profile">기본 이미지로 변경</div>
								</label>
								<input type="button" id="resetImage">
							</div>
						</div>
					</div>
				</form>
				<!-- 이름 영역 -->				
				<div id="div_profile_name">
					<div class="fl">이름</div>
					<div class="fr">
						<div id="show_name"class="fl"><%=memberDao.showName(memberIdx)%></div>
						<div class="fr">∨</div>
					</div>
					<div id="div_input_name" class="fr">
						<div><input type="text" id="name" placeholder="새로운 이름 입력"></div>
						<div class="fl">취소</div>
						<div class="fl"><input type="button" value="확인"></div>
						<div class="fr">∧</div>
					</div>
				</div>
				<!-- 이메일 영역 -->				
				<div id="div_email" class="fl">
					<div class="fl">이메일</div>
					<div class="fl">
						<div><%=memberDao.showEmail(memberIdx)%></div>
					</div>
				</div>
				<!-- 전화번호 영역 -->			
				<div id="div_phone_number" class="fl">
					<div class="fl">전화번호</div>
					<div class="fl">
						<div><%=memberDao.showPhoneNumber(memberIdx)%></div>
					</div>
				</div>
				<!-- 비밀번호 영역 -->			
				<div id="div_password" class="fr">
					<div class="fl">비밀번호 변경하기</div>
					<div class="fl">
						<div class="fl">＊＊＊＊＊＊＊＊＊＊</div>
						<div class="fl">∨</div>
					</div>
					<div id="div_change_password" class="fr">
						<div class="fl">현재 비밀번호</div>
						<div class="fl">∧</div>
						<div>
							<input type="password" name="current_password" required>
						</div>
						<div>새 비밀번호</div>
						<div>
							<input type="password" name="new_password" required>
						</div>
						<div>비밀번호 확인</div>
						<div>
							<input type="password" name="check_password" required>
						</div>
						<div class="fl">취소</div>
						<div class="fl" ><input type="button" value="확인" id="change_pw"></div>
					</div>
				</div>
				<!-- 계정삭제 영역  -->				
				<div id="div_delete" class=" fl">
					<div class="fl">나무 계정 삭제</div>
					<div class="fr">
						<div class="fr">∨</div>
					</div>
						<div id="div_delete_input" class="fr">
							<div class="fl">비밀번호</div>
							<div class="fl">∧</div>
							<div><input type="password" id="pw_for_delete" placeholder="비밀번호"></div>
							<div class="fl">
								<input type="checkbox" name="delete_account_agreement" id="delete_account_agreement" value="agree" required>
								<label for="delete_account_agreement">계정 삭제에 동의합니다.</label>
							</div>
							<div id="delete_account"class="fl"><input type="button" value="나무 계정 삭제"></div>
							<div class="fl">취소</div>
						</div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<!-- 세팅박스 영역1 끝-->
			<div id="div_footer">
				<div class="fl">ⓒ YG Lab, Inc.</div>
			</div>
		</div>
	<!-- 세팅박스 영역2 끝-->
	</div>
</body>
</html>










