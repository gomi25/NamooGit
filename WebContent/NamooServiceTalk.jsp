<%@page import="dto.MemberImageDto"%>
<%@page import="dao.NamooMemberDao"%>
<%@page import="util.DateUtil"%>
<%@page import="dto.ServiceTalkContentDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.NamooServiceTalkDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int memberIdx = 1;   // 로그인 member_idx 가정. ----> 이후에는 (Integer)session.getAttribute("loginId") 등으로 변경해야 돼요~
	//int loginMemberIdx = 5;
	int serviceTalkroomIdx = 1;
	
	if(request.getParameter("service_talkroom_idx") != null) {   // 이해 : 파라미터 'service_talkroom_idx'라는 게 있으면.
		serviceTalkroomIdx = Integer.parseInt(request.getParameter("service_talkroom_idx"));
	}
	NamooServiceTalkDao nDao = new NamooServiceTalkDao();
	ArrayList<ServiceTalkContentDto> serviceTalkListDto = nDao.serviceTalkShowTalKroom(serviceTalkroomIdx);
	ArrayList<ServiceTalkContentDto> serviceTalkContentDto = nDao.serviceTalkShoWTalkContent(serviceTalkroomIdx);
	
	NamooMemberDao memberDao = new NamooMemberDao();
	MemberImageDto miDto = null;
	if(memberIdx == 0) {   // 관리자
		int memberIdxTalkWith = nDao.getMemberIdxByServiceTalkroomIdx(serviceTalkroomIdx);  // 임시 -----> DB에서 관리자가 이야기하고 있는 대상이 누군지?
		miDto = memberDao.getMemberImageDtoFromIdx(memberIdxTalkWith);
		System.out.println(miDto == null);
		System.out.println(memberIdxTalkWith);
	} else {  // not 관리자
		miDto = memberDao.getMemberImageDtoFromIdx(0);
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooServiceTalk.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/NamooServiceTalk.js"></script>
</head>
<body>
<h1> 로그인 memberIdx=<%=memberIdx %> 인 상태.</h1>

	<!--------------------------- 1:1 톡 -------------------------------->
	<!--_________________________톡 목록-관리자시점_______________________ -->
	<% if(memberIdx == 0) { %>	
	<div id="div_service_talk1">
		<!-- 톡목록 헤더 -->
		<div id="service_talk_header">
			<div class="fl header_name">대화</div>
		</div>
		<div id="service_talk_body">
			<!-- 톡목록 : 목록 생겼을 때 -->			
			<%
				for(ServiceTalkContentDto sDto : serviceTalkListDto) {
			%>
			<div class="talk_room_area">
				<!-- 톡목록 : 톡방 -->			
				<div class="talk_room">
					<div id="div_talk_room">
						<div class="fl">
							<div id="talk_room_profile" class="fl"><!-- 프사 --><img src="<%=sDto.getProfilePicUrl() %>"></div>
						</div>
						<div id="first_row_profile_time" class="fl">
							<div class="fl"><%=sDto.getName() %></div>
							<div class="fl"><%=DateUtil.convertToBeautifulDateString(sDto.getTalkTime()) %></div>
							<div class="fl"><!-- 미확인 알림 빨간 점 --></div>
						</div>
						<div id="second_row_message" class="fl"><%=sDto.getMessage()%></div>
					</div>
					<div id="quit_talk_room"><!-- 채팅방 나가기 아이콘 --></div>
					<div class="fr transparent_button"></div><!--서비스톡 닫을 때를 위한 투명 div  -->
					<div id="quit_talk_room_div">
						<div>
							<div class="fl"></div>
							<div class="fr">상담 나가기</div>
						</div>
					</div>
				</div>

			</div><!-- 톡방 영역 끝 -->
			<%
				}
			%>
		</div>
	</div>
	<%} %>
	<!--__________________________톡 목록-멤버시점_________________________ -->
	<% if(memberIdx != 0 && nDao.countTalkroomByMemberIdx(memberIdx) > 1) { %>	
	<div id="div_service_talk1_1">
		<!-- 톡목록 헤더 -->
		<div id="service_talk_header">
			<div class="fl header_name">대화</div>
		</div>
		<div id="service_talk_body">
			<!-- 톡목록 : 초기  -->
			<div id="start_talk">
				<div><!-- 말풍선 이모티콘 --></div>
				<div>대화를 시작해보세요</div>
			</div>
		</div>
		<!-- 새 문의하기 버튼 -->		
		<div id="send_new_message" member_idx="<%= memberIdx%>">
			<div class="fl">새 문의하기</div>
			<div class="fl"><!-- 비행기 이미지 --></div>
		</div>
	</div>
	<% } %>

	<!--__________________________톡방____________________________ -->
	<div id="div_service_talk2">
		<!-- 서비스톡 헤더 -->
		<div id="service_talk_header" member_idx="<%= memberIdx%>">
			<div class="header_back fl"><!-- '<' : 채팅방 목록으로 되돌아가기--></div>
			<div class="profile fl header_profile_pic" style="background: url(<%=miDto.getProfilePicUrl()%>) no-repeat center / cover;"></div>
			<div class="fl header_name"><%= miDto.getName() %></div>
			<div class="fr header_quit"><!-- 채팅방 나가기 아이콘 --></div>
			<div class="fr transparent_button"></div><!--서비스톡 닫을 때를 위한 투명 div  -->
			<div id="quit_service_talk">
				<div>
					<div class="fl"></div>
					<div class="fr">상담 나가기</div>
				</div>
			</div>
		</div>
		<!-- 서비스톡 바디: 헤더 -->
		<div id="service_talk_body">
			<% if(memberIdx != 0) { %>		
				<div id="div_bady_header" >
					<div class="profile"></div>
					<div>나무에게 문의하기</div>
				</div>
				<div class="div_talk_time">오후 1:34 </div>
				<div class="div_talk_left">
					<div class="fl"><div class="profile"></div></div>
					<div class="talk_area fl">
						<div class="left_name">NAMOO🌳</div>
						<div class="left_talk">
							안녕하세요 협업툴 <b>나무</b> 입니다.🍀🍀 <br/>	
							나무 사용 중 궁금한 점은 헬프 센터에서 빠르게 찾아보실 수 있습니다.
							<div id="help_center_button"> 
								<a href="NamooHelpMain.jsp">👉헬프센터 바로가기👈 </a>
							</div>
						</div>
					</div>
				</div>
			<% } %>
			<% for( ServiceTalkContentDto cDto : serviceTalkContentDto ) { %>
				<!-- 서비스톡 바디: 왼쪽 톡 -->
				<% if ( cDto.getMemberIdx() == 0 ) { %>
					<div class="div_talk_time">오후 1:34 </div>
					<div class="div_talk_left">
						<!-- 프사 -->
						<div class="fl"><div class="profile"></div></div>
						<div class="talk_area fl">
							<!-- 이름 -->
							<div class="left_name">NAMOO🌳</div>
							<!-- 내용 -->
							<div class="left_talk">
								<%=cDto.getMessage() %>
							</div>
						</div>
					</div>
				<% } %>
				<!-- 서비스톡 바디: 오른쪽 톡 -->
				<% if ( cDto.getMemberIdx() != 0 ) { %>
				<div class="div_talk_right">
					<div class="talk_area fr">
						<div class="right_talk fr">
							<%= cDto.getMessage() %>
						</div>
					</div>
				</div>
				<% } %>
			<% } %>
		</div>
		<form action="">
			<div id="service_talk_footer">
				<div>
					<input type="text" placeholder="메시지를 입력하세요...">
					<input type="submit" name="send" id="send_massage">
					<label for="send_massage">
						<div></div>
					</label>
				</div>
			</div>
		</form>
	  </div><!-- 바디 영역 끝 -->
	<!-- 서비스톡 푸터: 입력칸 -->
	</div><!-- 서비스 톡영역 끝 -->
</body>
</html>
















