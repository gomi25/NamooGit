<%@page import="util.DateUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.ArrayList"%>

<%
 	int memberIdx = 1;   // 테스트
	//int memberIdx = (Integer)session.getAttribute("memberIdx");
	int teamIdx = 1;     // 테스트 중
	int serviceTalkroomIdx = 0;
	
	int cntUnreadTotal = 0; // 토픽방에서 안 읽은 메시지 전체 개수 
	int cntOfTopic = 0;     // 토픽방 개수
	int cntOfTopicMember = 0;
	
	SideDao sDao = new SideDao();
	BookmarkDao bDao = new BookmarkDao();
	ChatDao cDao = new ChatDao();
	TopicDao tDao = new TopicDao();
	Common common = new Common();
	NamooDashboardDao dashDao = new NamooDashboardDao();
	NamooMemberDao memberDao = new NamooMemberDao();
	//============================위젯 전체 조회 ===========================
	ArrayList<DashboardMainDto> showMainDashboardDto = dashDao.showMainDashboardByMemberIdx(memberIdx);
	//===========================프로젝트 리스트 조회=========================
	ArrayList<DashboardProjectDto> showProjectListDto = dashDao.showProjectListByTeamIdx(teamIdx);
	//============================메모장 조회==============================
	ArrayList<DashboardMemoDto> showMemoDto = dashDao.showMemoBymemberIdx(memberIdx);
	
	ArrayList<DashboardChatroomAndTopicNameDto> chatTopicNamDto = dashDao.showChatroomAndTopicNameByTeamIdx(teamIdx);
	//============================팀 전체 멤버 조회 테스트=======================
	ArrayList<TeamMemberDto> teamMemberList = sDao.getTeamMemberList(teamIdx);	
	//============================팀 멤버 조회 테스트==========================
	TeamMemberDto teamMember = sDao.getTeamMember(teamIdx, memberIdx);	
	//============================토픽방목록, 채팅방목록 조회 테스트===============
	// 대화창검색 - 전체 토픽목록
	ArrayList<TopicDto> listTopicAll = sDao.getAllTopicList(memberIdx, teamIdx);	
	// 폴더DTO 타입(int topicFolderIdx, int memberIdx, int teamIdx, String name)
	ArrayList<FolderBoxDto> listFolderBox = sDao.getFolderList(memberIdx, teamIdx);
	// 토픽DTO 타입(int topicIdx, String name, String information, int teamIdx, int open, int alarm, int unread, boolean bookmark)
	ArrayList<TopicDto> listTopic = sDao.getTopicListOutside(memberIdx, teamIdx);
	
	// 폴더에 포함된 토픽의 안 읽은 메시지 개수  및 토픽방 개수	
	for(FolderBoxDto dto : listFolderBox) {
		ArrayList<TopicDto> list2 = sDao.getTopicListFromFolderIdx(memberIdx, dto.getTopicFolderIdx());
		for(TopicDto dto2 : list2) {
			cntUnreadTotal += dto2.getUnread();	// 폴더에 포함된 토픽의 안 읽은 메시지 개수
			cntOfTopic++;	// 토픽방 개수 +1
		}
	}
	
	// 밖에 있는 토픽의 안 읽은 메시지 개수 및 토픽방 개수
	for(TopicDto dto : listTopic) {
		cntUnreadTotal += dto.getUnread();	// 밖에 있는 토픽의 안 읽은 메시지 개수
		cntOfTopic++;	// 토픽방 개수 +1
	}
	
	// 채팅방DTO 타입(int chatroomIdx, ArrayList<ProfileUrlColorDto> listProfileUrlColor, String chatroomName,
	//		String information, String chatRecentDateTime, boolean bookmarkYn, int alarm, int unread)
	ArrayList<ChatroomDto> listChatroom = sDao.getChatroomList(memberIdx, teamIdx);
	
	// 채팅방에서 안 읽은 메시지 개수
	int cntChatTotalUnread = 0;
	for(ChatroomDto dto : listChatroom) {
		cntChatTotalUnread += dto.getUnread();
	}

	//조직도
	int memberIdxFrom = memberIdx;
   	//	int memberIdx = (Integer)session.getAttribute("memberIdx");
   	//	int teamIdx =(Integer)session.getAttribute("teamIdx");
   	
    OrganizationalChartDao odao = new OrganizationalChartDao();
    ArrayList<OrganizationalMemberListDto> listMember = null;
    ArrayList<OrganizationalBookmarkMemberListDto> bookmarkMember = null;
    
    ArrayList<MemberProfileDto> memberProfile = null;
    
    try {
        listMember = odao.getOrganizationalMemberList(teamIdx, memberIdxFrom);
        bookmarkMember = odao.getOrganizationalBookmarkMemberList(teamIdx, memberIdxFrom);
        memberProfile = odao.getMemberProfile(teamIdx);
    } catch (Exception e) {
        e.printStackTrace();
    }
    
	// 팀이름 가지고 오기
    String teamName = "";
    try {
        teamName = odao.checkOrganizationalTeamName(teamIdx);
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    // 팀의 인원수
    int memberCount = 0;
    try {
        memberCount = odao.checkOrganizationalMemberCount(teamIdx);
    } catch (Exception e) {
        e.printStackTrace();
    }
    // 즐겨찾기 멤버수
    int bookmarkMemberCount = 0;
    try {
    	bookmarkMemberCount = odao.checkOrganizationalBookmarkMemberCount(teamIdx);
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    //서비스톡 
    String paramMidx = request.getParameter("midx");
	if(paramMidx != null) {
		memberIdx = Integer.parseInt(paramMidx);	
	}	
	
	if(request.getParameter("service_talkroom_idx") != null) {   // 이해 : 파라미터 'service_talkroom_idx'라는 게 있으면.
		serviceTalkroomIdx = Integer.parseInt(request.getParameter("service_talkroom_idx"));
	}
	NamooServiceTalkDao nDao = new NamooServiceTalkDao();
	ArrayList<ServiceTalkContentDto> serviceTalkListDto = nDao.serviceTalkShowTalKroom();
	ArrayList<ServiceTalkContentDto> serviceTalkContentDto = nDao.serviceTalkShoWTalkContent(serviceTalkroomIdx);
	int gogangMemberIdx = nDao.getMemberIdxByServiceTalkroomIdx(serviceTalkroomIdx);
	
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
	<title>NamooMainTool</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/OrganizationalChart.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Profile_1.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Profile_2.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AddProject.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/NamooMainTool.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/NamooDashboardMain.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/NamooServiceTalk.css"/>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<script>
		let context_path = '${pageContext.request.contextPath}';
		let member_idx = <%=memberIdx%>;
		let team_idx = <%=teamIdx%>;
		let login_member_idx = <%=memberIdx%>;

	</script>
	<script src="${pageContext.request.contextPath}/js/NamooMainTool.js"></script>
	<script src="${pageContext.request.contextPath}/js/OrganizationalChart.js"></script>
	<script src="${pageContext.request.contextPath}/js/NamooDashboardMain.js"></script>
	<script src="${pageContext.request.contextPath}/js/NamooServiceTalk.js"></script>
	<script>
		function func_on_message(e){
			//$("#div_message").append("<p class='chat'>" + e.data + "</p>");
			//alert("도착한 메시지 : " + e.data);

			let raw_msg = e.data;   // chat||5||1||0||나무에게
			if(!raw_msg.startsWith('chat')) {
				return;
			}
			let msg_talkroom_idx = raw_msg.split("///")[1];
			if(msg_talkroom_idx != <%=serviceTalkroomIdx%>) {
				return;
			}
			let msg_from = raw_msg.split("///")[2];
			let msg_to = raw_msg.split("///")[3];
			let msg_msg = raw_msg.split("///")[4];
			//alert("내가 받은 나한테 온 메시지 : " + msg_msg);
			
			let left_msg = '<div class="div_talk_time">오후 1:34 </div>'
						+ '<div class="div_talk_left">'
						+ '	<!-- 프사 -->'
						+ '	<div class="fl"><div class="profile"></div></div>'
						+ '	<div class="talk_area fl">'
						+ '		<!-- 이름 -->'
						+ '		<div class="left_name">NAMOO🌳</div>'
						+ '		<!-- 내용 -->'
						+ '		<div class="left_talk">'
						+ 			msg_msg
						+ '		</div>'
						+ '	</div>'
						+ '</div>';
			let right_msg = '<div class="div_talk_right">'
						+ '	<div class="talk_area fr">'
						+ '		<div class="right_talk fr">'
						+ 			msg_msg
						+ '		</div>'
						+ '	</div>'
						+ '</div>';

			// #service_talk_body 에 append 해.
			if(msg_from==0)
				$("#service_talk_body").append(left_msg);
			else
				$("#service_talk_body").append(right_msg);

			//$("#service_talk_body").scrollBottom(); (X)
			$("#service_talk_body").animate({ scrollTop: $('#service_talk_body').prop("scrollHeight")}, 1000);
			
		}
		function func_on_open(e){
			//$("#div_message").append("<p class='chat'> 채팅에 참여하였습니다.</p>");
			//alert("웹소켓 접속함.");
		}
		function func_on_error(e){
			alert("Error!");
		}
		let webSocket = new WebSocket("ws://localhost:9092/NamooGit2/broadcasting");
		webSocket.onmessage = func_on_message;
		webSocket.onopen = func_on_open;
		webSocket.onerror = func_on_error;
	
		$(function() {
			$("label[for='send_massage'] > div").click(function() {
				//누가 받아야 하는지
				let from = <%=memberIdx%>;
				let to = <%=(memberIdx==0 ? gogangMemberIdx : 0)%>;
				let msg = $(this).parent().prev().prev().val();
				let talkroom_idx = <%=serviceTalkroomIdx%>;
				let str = "chat///" + talkroom_idx + "///" + from + "///" + to + "///" + msg;
				webSocket.send(str);  // "talkroom_idx||from||to||msg"의 형식으로 보내기로.
				$(this).parent().prev().prev().val("");

				let left_msg = '<div class="div_talk_time">오후 1:34 </div>'
							+ '<div class="div_talk_left">'
							+ '	<!-- 프사 -->'
							+ '	<div class="fl"><div class="profile"></div></div>'
							+ '	<div class="talk_area fl">'
							+ '		<!-- 이름 -->'
							+ '		<div class="left_name">NAMOO🌳</div>'
							+ '		<!-- 내용 -->'
							+ '		<div class="left_talk">'
							+ 			msg
							+ '		</div>'
							+ '	</div>'
							+ '</div>';
				let right_msg = '<div class="div_talk_right">'
							+ '	<div class="talk_area fr">'
							+ '		<div class="right_talk fr">'
							+ 			msg
							+ '		</div>'
							+ '	</div>'
							+ '</div>';
				// #service_talk_body 에 append 해.
				if(from==0)
					$("#service_talk_body").append(left_msg);
				else
					$("#service_talk_body").append(right_msg);

				//$("#service_talk_body").scrollBottom(); (X)
				$("#service_talk_body").animate({ scrollTop: $('#service_talk_body').prop("scrollHeight")}, 1000);
				
			});
			$("input[type='text']").keyup(function(e){
				if(e.keyCode==13) {
					$("label[for='send_massage'] > div").trigger('click');
				}
			});
		});
	</script>
</head>
<body>
	<!--------------------------------------- #div_header (화면 최상단) --------------------------------------->	
	<div id="div_header">
		<div id="div_header_left" class="fl">
			<div id="div_logo" class="fl"><img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628"/></div>
			<div id="div_select_team" class="fl" data-toggle="tooltip" title="팀 전환하기">
				<span class="fl">7팀</span><!-- 요기 -->
				<div class="ic_arrow_bottom fl"></div>
			</div>
		</div>
		
		<div id="div_header_right" class="fr">
			<div class="fr" data-toggle="tooltip" title="환경설정">
				<div class="ic_header_setting"></div>
			</div>
			<div class="fr" data-toggle="tooltip" title="메뉴">
				<div class="ic_header_menu fl"></div>
				<div class="ic_arrow_bottom fl"></div>
			</div>
			<div class="fr" data-toggle="tooltip" title="검색">
				<div class="ic_header_search"></div>
			</div>
			<div class="fr ic_header_org_chat_img" data-toggle="tooltip" title="조직도">
				<div class="ic_header_org_chat"></div>
			</div>
			<div class="fr" data-toggle="tooltip" title="알림센터">
				<div class="ic_header_alarm"></div>
				<div class="on"></div>
			</div>

			<div id="pop_up_header_menu" class="pop_up_box">
				<div>
					<div class="ic_to_do_list fl"></div>
					할일
				</div>
				<div>
					<div class="ic_file_clip fl"></div>
					파일
				</div>
				<div>
					<div class="ic_bookmark_off fl"></div>
					즐겨찾기
				</div>
			</div>
			<div id="pop_up_header_setting" class="pop_up_box">
				<div>새로운 멤버 초대하기</div>
				<div>자주 묻는 질문</div>
				<div class="enter_service_talk">1:1 문의하기</div>
				<div>잔디메인으로</div>
				<div>로그아웃</div>
				<div>회원탈퇴</div>
			</div>
		</div>
		
		<div style="clear:both;"></div>
	</div>	
	
<!--------------------------------------- #div_total_side --------------------------------------->	
<div id="div_total_side">
<!--------------------------------------- #div_side1 --------------------------------------->	
	<div id="div_side1" class="fl">
	<!---------- 프로필 ---------->		
		<div> <!-- (1) 프로필 -->
			<div id="div_profile_box" member_idx="<%=memberIdx%>">
				<img src="<%=teamMember.getProfileUrl()%>"/>
				<div><%=teamMember.getName() %></div>
			</div>
		</div>		
		
	<!---------- 대화방 검색 ---------->			
		<div id="div_room_search" data-toggle="tooltip" title="토픽 또는 채팅방으로 바로 이동하기"> <!-- (2) 대화방 검색 -->
			<div>
				<div class="ic_search fl"></div>
				<span>대화방 검색</span>
			</div>
		</div>
		<!---------- 대화방 검색 클릭 시 팝업창 ---------->	
			<div id="search_all_room">
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span class="fl">대화방 검색</span>
					<div class="exit fr"></div>				
				</div>
				
				<!-- 중앙부1 / div:nth-child(2) -->
				<div>
					<input type="text" name="member_search" placeholder="토픽 또는 채팅방으로 바로 이동하기">
				</div>
				
				<!-- 중앙부2 / div:nth-child(3) -->
				<div>
					<div id="all_room_list" class="scrollbar">
						<!-- 테스트 - 전체 채팅방 불러오기 -->
						<%
							for(TopicDto tDto : listTopicAll){
						%>
						<div class="my_topicroom_item" topic_idx="<%= tDto.getTopicIdx()%>">
							<div class="ic_board fl"></div>
							<div>
								<span class="fl room_name"><%=tDto.getName()%></span>
							</div>
						</div>
						<%
							}
						%>
						
						<% 
							for(ChatroomDto chatDto : listChatroom) { 
								ArrayList<String> listImgUrls = sDao.getChatroomMembersImageUrl(chatDto.getChatroomIdx(), memberIdx);
						%>
						<div class="group_chatroom_item">
							<div class="fl">
								 <% if(listImgUrls.size()==0) { %> <!-- 여기여기 -->
									<img class="fl" src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628"/>
							     <% } %>
								 <%
									int cnt = 0;
									for(String imgUrl : listImgUrls) { 
										if(++cnt>4) 
								 			break;
							     %>
		 							<img class="fl" src="<%=imgUrl %>"/>
	 							 <% } %>
							</div>
							<div class="fl">
								<div>
									<span class="room_name"><%=chatDto.getChatroomName()%></span>
								</div>
								<div>
									<span><%=chatDto.getInformation()%></span>
								</div>
							</div>
						</div>
						<% } %>
						
						<div class="div_not_exist">
							검색 결과가 없습니다
						</div>
						<div class="invite_member creat_new_topic">
							새로운 토픽 생성하기
						</div>						
						
					</div>
				</div>

				<!-- 하단부 / div:nth-child(4) -->
				<div>
					<button class="fl"> 
						<div class="ic_board fl"></div>
						참여하지 않은 토픽 보기
					</button>
					<button class="fl"> 
						<div class="ic_plus fl"></div>
						새 토픽 생성하기
					</button>
				</div>
			
			</div>				
		
		<!---------- 토픽방 목록 / header ---------->			
		<div id="div_topicroom_list_header"> <!-- (3) 토픽-->
			<div></div>
			<div>토픽</div>
			<%-- <div class='<%= cntUnreadTotal >= 1 ? "div_topicroom_unread" : "" %>'>
				<%= cntUnreadTotal >= 1 ? cntUnreadTotal : ""%>
			</div> --%>
			<div class="ic_plus"></div>
		</div>
		<!---------- 토픽방 목록 / filter ---------->			
		<div id="div_topicroom_filter">
			<div>
				<div class="fl"></div>
				<span class="fl">이름 순</span> <!-- img 다음이라 child(2) -->
			</div>
			<span><%=cntOfTopic %> 개 토픽</span>
		</div>
		<!---------- 토픽방 목록 / body ---------->			
		<div id="div_topicroom_list_body" class="scrollbar"> <!-- (4) -->
			<!-- (4)-(1) 필터 -->
			<div></div>
			<!-- (4)-(2) 폴더 -->
			<%
				for(FolderBoxDto folderDto : listFolderBox) {
					ArrayList<TopicDto> listSubTopic = sDao.getTopicListFromFolderIdx(memberIdx, folderDto.getTopicFolderIdx());
			%>
			<div class="topic_folder"> 
				<div class="div_folder_item" topicFolderIdx="<%=folderDto.getTopicFolderIdx()%>" >
					<div class="ic_topic_folder_open fl"></div>
					<div class="ic_topic_folder_close fl"></div>
					<span class="span_folder_name fl" contenteditable="false"><%=folderDto.getName() %></span>
					<span class="fl"><%=listSubTopic.size() %></span>
					<div class="ic_topic_folder_more_menu fr"></div>
					<div class="topic_folder_more_menu">
						<div>이 폴더에 토픽 생성하기</div>
						<div>폴더 이름 변경하기</div>
						<div>폴더 삭제하기</div>
					</div>
				</div>
				<div>
					<%
						for(TopicDto topicDto : listSubTopic) {
					%>
					<!--  토픽 -->
					<div class="topic_item" topic_idx="<%=topicDto.getTopicIdx()%>">
						<div class='<%=(topicDto.isBookmark() ? "ic_bookmark_on" : "ic_bookmark_off") %>'></div>
						<span><%=topicDto.getName()%></span>
						<div class='<%= (topicDto.getAlarm()==1? "" : "ic_alarm_off" ) %>'></div>
						<%-- <div class='<%= (topicDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
							<%= topicDto.getUnread() >=1 ? topicDto.getUnread() : "" %>
						</div> --%>
					</div>
					<%
						}
					%>
				</div>
			</div>		
			<%
			}
			%>		
			
			<%
				for(TopicDto topicDto : listTopic){
			%>
			<div class="topic_item" topic_idx="<%=topicDto.getTopicIdx()%>">
				<div class='<%=(topicDto.isBookmark() ? "ic_bookmark_on" : "ic_bookmark_off") %>'></div>
				<span><%=topicDto.getName()%></span>
				<div class='<%=(topicDto.getAlarm()==1 ? "" : "ic_alarm_off") %>'></div>
				<%-- <div class='<%= (topicDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
					<%= topicDto.getUnread() >=1 ? topicDto.getUnread() : "" %>
				</div> --%>
			</div>
			<%
				}
			%>

		</div><!-- div_topicroom_list_body 닫는 태그 -->

		<!---------- 프로젝트 목록 ---------->	
		<div id="div_project_list_header">
			<div></div>
			<div onclick="location.href='Controller?command=project_list'">프로젝트</div>
			<div class="ic_plus" onclick="location.href='Controller?command=create_project_form'"></div>
		</div>
		
		<!-- <div id="div_project_list_body">
		</div> -->

		<!---------- 채팅방 목록 ---------->	
		<div id="div_chatroom_list_header"> <!-- (5) -->
			<div></div>
			<div>채팅</div>
			<div class='<%= (cntChatTotalUnread >= 1 ? "div_chatroom_unread" : "") %>'> 
				<%= cntChatTotalUnread >=1 ? cntChatTotalUnread : "" %>
			</div>
			<div class="ic_plus"></div>
		</div>
		<div id="div_chatroom_list_body"> <!-- (6) -->
			<% 
				for(ChatroomDto chatroomDto : listChatroom) { 
					ArrayList<String> listImgUrls = sDao.getChatroomMembersImageUrl(chatroomDto.getChatroomIdx(), memberIdx);
			%>
				<div class="topic_item" chatroom_idx="<%=chatroomDto.getChatroomIdx()%>">
					<div class='<%=(chatroomDto.isBookmarkYn() ? "ic_bookmark_on" : "ic_bookmark_off") %> fl'></div>
					<div class="fl">
						<% if(listImgUrls.size()==0) { %> <!-- 여기여기 -->
							<img class="fl" src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628"/>
						<% } %>
						<%
							int cnt = 0;
							for(String imgUrl : listImgUrls) { 
								if(++cnt>4) 
									break;
						%>
 							<img class="fl" src="<%=imgUrl%>"/>
						<% } %>
					</div>
					<span><%=chatroomDto.getChatroomName() %></span>
					<%-- <div class='<%= (chatroomDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
						<%= chatroomDto.getUnread() >=1 ? chatroomDto.getUnread() : "" %>
					</div> --%>
					<div class="exit"></div>
				</div>
			<%  } %>
			
		</div>
		
	<!--------------------------------------- div_side1 - 팝업창 --------------------------------------->
	<!---------- 토픽 '+버튼' 클릭 시 팝업창 ---------->			
		<div id="div_topic_plus">
			<div>새로운 토픽 생성하기</div>
			<div>폴더 생성하기</div>
			<div>참여 가능한 토픽 보기</div>
		</div>
			<!---------- 토픽 '+버튼' -> 새로운 토픽 생성 생성하기 ---------->	
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="create_topic"/>
				<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>				<div id="div_topic_create"> 
					<!-- 상단부 / div:nth-child(1) -->
					<div>
						<span>새 토픽 생성</span>
						<div class="exit fr"></div>
					</div>
					
					<!-- 중앙부 / div:nth-child(2) -->
					<div>
						<!-- div:nth-child(2) > div:nth-child(1) -->
						<div>
			     			<label for="input_new_topic_name">이름</label><span>*</span><br/>
			     			<input type="text" name="name" id="input_new_topic_name" placeholder="토픽 이름을 입력하세요" required>
			     			<span class="text_max_value fr">/ 60</span>
			     			<span class="text_current_value fr">0</span><br/><br/>
						</div>
						<!-- div:nth-child(2) > div:nth-child(2) -->
						<div>
				  			<label for="textarea_new_topic_info">토픽 설명(옵션)</label><br/>
							<textarea name="info" id="textarea_new_topic_info" placeholder="토픽에 대해 설명해주세요" rows="3"></textarea>
							<span class="text_max_value fr">/ 300</span>
							<span class="text_current_value fr">0</span><br/><br/>
						</div>
						<!-- div:nth-child(2) > div:nth-child(3) -->
						<div>
				  			<label for="topic_open">공개 여부</label>
				  			<span>*</span><span>토픽 생성 이후 변경 불가</span><br/>
							<div class="topic_open_select">
								<div id="topic_public_div">
									<div class="ic_topic_public fl"></div>
									<input type="radio" name="topic_open" id="topic_public" value="1">
									<label for="topic_public">공개</label>		
									<div class="fr"></div>
								</div>
			
								<div id="topic_private_div">
									<div class="ic_topic_private fl"></div>
							        <input type="radio" name="topic_open" id="topic_private"  value="0">
									<label for="topic_private">비공개</label>		
									<div class="fr"></div>
								</div>
							</div>
							
							<br/><br/>
						</div>
						<!-- div:nth-child(2) > div:nth-child(4) -->
						<div>
							<label>폴더 선택(옵션)</label><br/>
							<div class="folder_select_option">
								<span>토픽을 생성 할 폴더를 선택해 주세요.</span>
								<div class="fr"></div>
							</div>
							<span>선택한 폴더는 개인에게만 적용됩니다.</span>
							<!-- 폴더 선택(옵션) 팝업창 -->			 <!-- 나나 -->
							<div class="folder_list_pop_up"> 
								<div class="scrollbar">
									<div>
										<div>
											<div class="check fl"></div>
										</div>
										<div class="fl">폴더 선택 안함</div>
									</div>
	
									<input type="hidden" id="selected_folder"  name="selected_folder" value="">
								<%
									for(FolderBoxDto folderDto : listFolderBox) {
								%>
									<div class="my_folder_list">
										<div>
											<div class="check fl"></div>
										</div>
										<div class="fl" topic_folder_idx="<%=folderDto.getTopicFolderIdx()%>"><%=folderDto.getName() %></div>
										<!-- 각 폴더 리스트 항목에서 JavaScript를 사용하여 hidden input 태그의 값을 설정 -->
									</div>
								<%
									}
								%>	
								</div>
							</div>
						</div>
						
					</div>
							<!-- button태그와 input태그 차이 찾아보기 -->
							<!-- <input type="submit" value="생성하기"/> -->
					<div>
						<button class="fr" type="submit" id="new_topic_create">생성하기</button>
						<button class="fr" type="button" id="new_topic_cancel">닫기</button>
					</div>
					
				</div>
			</form><!-- 새 토픽 생성하기 -->
			
			<!---------- 토픽 '+버튼' -> 참여 가능한 토픽 보기 ---------->	
			<div id="div_topic_openlist">
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span>토픽 목록</span>
					<div class="exit"></div>				
				</div>
				<!-- 중앙부1 / div:nth-child(2) -->
				<div>
					<input type="text" name="topic_search" placeholder="토픽 이름을 검색하세요">
				</div>
				<!-- 중앙부2 / div:nth-child(3) -->
				<div>
					<div class="fl"></div>
					<span class="fl">이름순</span>					
					<div>
						<div class="other_topicroom_item">
							<div class="ic_board fl"></div>
							<div class="fl">
								<span class="fl">테스트2</span>
							</div>
							<div class="fr">
								<span class="fr">0</span>
								<div class="ic_member fr"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- 하단부 / div:nth-child(4) -->
				<div>
					<button type="button"> + 새 토픽 생성</button>
				</div>
			</div>
			
			<!---------- 토픽 '+버튼' -> 참여 가능한 토픽 보기 -> - 정렬박스  ---------->			
			<div class="list_view_option"> 
				<div>
					<div class="fl">보기 옵션</div>
				</div>
				<div>
					<div class="fl">이름 오름차순</div>
					<div class="check fr"></div>
				</div>
				<div>
					<div class="fl">이름 내림차순</div>
					<div class="check fr"></div>
				</div>
			</div>
	
		</div> <!-- div_side1의 div닫는태그 -->
	<!--------------------------------------- 채팅방 --------------------------------------->
	<div id="div_side2" class="wide fl">
	<!-- 투명 스크린  : 화면을 클릭했을 때 창이 꺼지는 기능 -->
	<div id="transparent_screen"></div>
	<!-- 회색 스크린 : 팝업 화면 떴을 떄 뒤에 화면  -->
	<div id="grey_screen1"></div>
	<div id="grey_screen2"></div>
		<!------------------------- 대시보드 -------------------------------->
	<div id="div_dashboard" class="fl">
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
				<% if(showMainDashboardDto.size() <= 1){ %>
					<div id="div_add_widget" class="add_widget">
						<div><!-- 위젯추가 아이콘 --></div>
						<div>위젯을 추가해 주세요</div>
					</div>
				<% } %>
				<!--------------------- 메모장 영역 --------------------------->
				<!-- 메모장 : 작은 버전 -->
				<%for(DashboardMainDto mDto : showMainDashboardDto){ %>
					<% if(mDto.getWidgetIdx() == 1){ %>
						<% for(DashboardMemoDto meDto : showMemoDto){ %>
							<% if(mDto.getwSize() == 1){ %>
							<div id="div_memo_small" class="widget_small fl">
								<div class="widget_header"> 
									메모장 
									<div class="widget_menu_icon">
										<div class="change_widget_size">
											<div class="delete_widget" widget_idx="<%=mDto.getWidgetIdx()%>">위젯 삭제</div>
											<div>큰 위젯으로 변경</div>
										</div>
									</div>
								</div>
								<div class="widget_article">
									<textarea placeholder="메모를 입력하세요."><%=meDto.getMemo()%></textarea>
								</div>
							</div>
							<% } %>
							<!-- <div style="clear:both"> -->
							<!-- 메모장 : 큰 버전 -->
							<% if(mDto.getwSize() == 2){ %>
								<div id="div_memo_big" class="widget_big fl">
									<div class="widget_header"> 
										메모장 
										<div class="widget_menu_icon">
											<div class="change_widget_size" >
												<div class="delete_widget" widget_idx="<%=mDto.getWidgetIdx() %>">위젯 삭제</div>
												<div>작은 위젯으로 변경</div>
											</div>
										</div>
									</div>
									<div class="widget_article">
										<textarea placeholder="메모를 입력하세요."><%=meDto.getMemo()%></textarea>
									</div>
								</div>
							<% } %>
						<% } %>
					<% } %>
					<!-- <div style="clear:both"> -->
					<!---------------------- 프로젝트 --------------------------->
					<!-- 프로젝트 : 초기창 큰 버전 -->
					<% if(mDto.getWidgetIdx() == 2){ %>
						<% if(mDto.getWidgetIdx() == 2){ %>
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
						<% } %>
						<!-- 프로젝트 : 초기창 작은 버전 -->
						<% if(mDto.getwSize() == 1){ %>
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
						<% } %>
						<!-- 프로젝트 : 큰 버전 -->
						<% if(mDto.getWidgetIdx() == 2){ %>
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
						<% } %>
						<!-- 프로젝트 : 작은 버전 -->
						<% if(mDto.getWidgetIdx() == 1){ %>
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
						<% } %>
					<% } %>
					<!-- 프로젝트 추가 창 -->
	<!-- 			<div id="div_add_project" class=" border">
					<div class="border"></div>
				</div> -->
				<!----------------------파일함 영역 ------------------------>
				<!-- 파일함 : 큰 버전(초기) -->
				<% if(mDto.getWidgetIdx() == 3){ %>
					<% if(mDto.getwSize() == 2){ %>
					<div id="filebox_widget" class="widget_big fl">
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
					<% } %>
					<!-- 파일함 : 작은 버전(초기) -->
					<% if(mDto.getwSize() == 1){ %>
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
					<% } %>
					<!-- 파일함 : 큰 버전 -->
					<% if(mDto.getwSize() == 2){ %>
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
					<% } %>
					<!-- 파일함 : 작은 버전 -->
					<% if(mDto.getwSize() == 1){ %>
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
					<% } %>
				<% } %>
				<!------------------------ 나를 언급 ------------------------>
				<!-- 나를 언급 : 초기창 큰 버전 -->
				<% if(mDto.getWidgetIdx() == 4){ %>
					<% if(mDto.getwSize() == 2) { %>
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
					<% } %>
					<!-- 나를 언급 : 초기창 작은 버전 -->
					<% if(mDto.getwSize() == 1) { %>
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
					<% } %>
					<!-- 나를 언급: 큰 버전 -->
					<% if(mDto.getwSize() == 2) { %>
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
					<% } %>
					<!-- 나를 언급 : 작은 버전 -->
					<% if(mDto.getwSize() == 1) { %>
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
					<% } %>
				<% } %>
				<!---------------------- 북마크 영역 ---------------------------->
				<!-- 북마크 : 초기창 큰 버전 -->
				<% if(mDto.getWidgetIdx() == 5){ %>
					<% if(mDto.getwSize() == 2) { %>
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
					<% } %>
					<!-- 북마크 : 초기창 작은 버전 -->
					<% if(mDto.getwSize() == 1) { %>
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
					<% } %>			
					<!-- 북마크 : 큰 버전 -->
					<% if(mDto.getwSize() == 2) { %>
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
					<% } %>
					<!-- 북마크 : 작은 버전 -->
					<% if(mDto.getwSize() == 1) { %>
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
					<% } %>			
				<% } %>
				<!---------------------- 미확인 알림 ---------------------------->
				<!-- 미확인 알림 : 초기창 큰 버전 -->
				<% if(mDto.getWidgetIdx() == 6){ %>
					<% if(mDto.getwSize() == 2) { %>
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
					<% } %>
					<!-- 미확인 알림 : 초기창 작은 버전 -->
					<% if(mDto.getwSize() == 1) { %>
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
					<% } %>
					<!-- 미확인 알림: 큰 버전 -->
					<% if(mDto.getwSize() == 2) { %>
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
					<% } %>
					<!-- 미확인 알림: 작은 버전 -->
					<% if(mDto.getwSize() == 1) { %>
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
						<% } %>
					<% } %>							
				<% } %>							
			</div>
		</div><!-- 대시보드 위젯 영역 끝  -->
	</div><!-- 대시보드 전체 영역 끝 -->
	
	<!--========================= 팝업창 ============================-->
	<!--____________________ 프로젝트 팝업 _______________________-->	
	<form>
		<div id="popup_add_project">
			<div class="project_popup_header">
				<div class="fl">프로젝트 선택</div>
				<div class="fr"></div>
			</div>	
			<div class="project_popup_search">
				<input type="text" placeholder="프로젝트명으로 검색">
				<div></div>
			</div>
			<div class="project_popup_area">
				<% for(DashboardProjectDto dsDto : showProjectListDto){ %>
					<div class="project_popup">
						<div class="project_popup1 fl">
							<input type="checkbox" id="project_checkbox001" name="project" value="select_project"/>
							<label for="project_checkbox001"></label>
						</div>
						<div class="fl"><label for="project_checkbox001"> <div class="fl project_color" style="background:<%=dsDto.getColor()%>; border: solid 1px <%=dsDto.getColor()%>;"><!-- 프로젝트 컬러 --></div></label></div>
						<div class="project_popup3 fl"><label for="project_checkbox001"> <div class="fl"><%=dsDto.getProjectName() %></div></label></div>
					</div>
				<% } %>
			</div>
			<div class="project_popup_button">
				<div class="fl">취소</div>
				<div class="fl"><input type="submit" value="확인" onclick="return select_project();"></div>
			</div>
		</div>
	</form>
	<!--____________________ 파일함 팝업 _______________________-->	
	<form>
		<div id="popup_add_filebox">
			<div class="project_popup_header">
				<div class="fl">채팅방 및 토픽 선택</div>
				<div class="fr"></div>
			</div>	
			<div class="project_popup_search">
				<input type="text" placeholder="채팅방 및 토픽 검색">
				<div></div>
			</div>
			<div class="project_popup_area">
				<% for(DashboardChatroomAndTopicNameDto ctDto : chatTopicNamDto){ %>
					<div class="project_popup">
						<div class="project_popup1 fl">
							<input type="checkbox" id="project_checkbox001" name="project" value="select_project"/>
							<label for="project_checkbox001"></label>
						</div>
						<div class="project_popup3 fl"><label for="project_checkbox001"> <div class="fl"><%=ctDto.getName() %></div></label></div>
					</div>
				<% } %>
			</div>
			<div class="project_popup_button">
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
		<!---------- 채팅방 생성하기 ---------->		
<!-- 		<form id="form_create_chatroom" action="CreateChatroomServlet" method="post">	 -->
		<form id="form_create_chatroom" action="Controller" method="post">	
			<input type="hidden" name="command" value="create_chatroom"/>
			<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
			<div id="div_create_chatroom" class="border">
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span>채팅방 생성하기</span> 
					<div class="exit fr"></div>
				</div>
				
				<!-- 중앙부 / div:nth-child(2) -->
				<div>
					<!-- div:nth-child(2) > div:nth-child(1) -->
					<div>
		     			<label for="input_name">이름</label><br/>
		     			<input type="text" name="name" id="input_name" placeholder="채팅방 이름을 입력하세요" required>
		     			<span class="text_max_value fr">/ 60</span>
		     			<span class="text_current_value fr">0</span><br/><br/>
					</div>
					<!-- div:nth-child(2) > div:nth-child(2) -->
					<div>
			  			<label for="textarea_info">채팅방 설명</label><br/>
						<textarea name="info" id="textarea_info" placeholder="채팅방에 대해 설명해주세요" rows="2"></textarea>
						<span class="text_max_value fr">/ 300</span>
						<span class="text_current_value fr">0</span>
					</div>
				</div>
				
				<!-- 하단부 / div:nth-child(3) -->						
				<div>
					<button class="fr" type="submit" id="btn_create_chatroom">완료</button>
					<button class="fr" type="button">취소</button>
				</div>
			</div> <!-- div_create_chatroom 닫는 태그 --> 
		</form>	<!-- form_create_chatroom --> 
		
	
	<div class="div_side2_container"></div>	<!-- side2에 아무것도 없을 경우 뜨도록  -->			
	
	</div>	<!-- div_side2 닫는 태그 -->
</div> <!-- div_total_side 닫는 태그 -->	

	<!--------------------------------------- 즐겨찾기 창 --------------------------------------->	
	<div id="div_side_bookmark">
		<div id="bookmark_header">
			<div class="fl">즐겨찾기</div>
			<div class="exit fr"></div>
		</div>
		<div id="bookmark_tab">
			<div id="all_content" class="select">모든 형식</div>
			<div id="file_content" class="">파일 형식</div>
		</div>
		<div id="bookmark_tab_content" class="scrollbar">
		<%
		List<BookmarkListDto> bookmarkList = bDao.getAllBookmarkList();
		for(BookmarkListDto bList : bookmarkList) {
			int bookmarkIdx = bList.getBookmarkIdx();
			String type = bList.getType();
			if(type.equals("file")){
				type = "file";
		%>
			<div class="bookmark_file_item" bookmarkIdx="<%=bookmarkIdx%>" bookmarkType="<%=type%>">
				<div class="bookmark_item_top">
					<div class="fl"><%=bList.getLocationName()%></div>
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_file_item_bottom">
					<div class="file_img"><img src="<%=bList.getProfileUrl()%>"></div><!-- 파일이미지 -->
					<div>
						<span class="profile_name"><%=bList.getContent()%></span>
						<span><%=bList.getAuthorName() %></span>
						<span><%=bList.getWriteDate() %></span>
					</div>
				</div>
			</div>
		<%			
					
			} else {
				type = "text";
		%>
			<div class="bookmark_text_item" bookmarkIdx="<%=bookmarkIdx%>" bookmarkType="<%=type%>">
				<div class="bookmark_item_top">
					<div class="fl"><%=bList.getLocationName()%></div> 
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src="<%=bList.getProfileUrl()%>"></div>
					<div>
						<span class="profile_name"><%=bList.getAuthorName() %></span>
						<span><%=bList.getWriteDate() %></span>
						<span><%=bList.getContent() %></span>
					</div>
				</div>
			</div>
		<%
			}
		}	
		%>	
			
			<div class="bookmark_ending_item">
				<div class="ic_ending"></div>
			</div>
		</div>
	</div> <!-- div_side_bookmark 닫는 태그 -->

	<!----------------조직도-------------------------->	
	<!---------------- 부서 -------------------->
	<div id="organizational_chart">
		<div class="re">
			<div id="memberlist_div_side2" class="fr">
				<div>
					<div id="list">
						<div class="fl organizational_chart_name">조직도</div>
						<div class="fl total_number">총 <%=memberCount%>명</div>
						<div class="fr delete"><img class="delete_img" src="img/x_button.png"/></div>
					</div>
					<div id="search">
						<div id="search_parents" class="fl re"><input id="search_input" name="member_search" type="text" placeholder="멤버 검색" ></div>
							<div class="ab"><img id="search_icon" src="img/img_icon_search.png"/></div>
					</div>
					<div id="select">
						<div id="teamlist"><button>팀</button></div>
						<div id="memberlist"><button>멤버</button></div>
					</div>
					<div id="select_list">
						<!-- 부서(즐겨찾기 멤버) -->
						<div id="bookmark_member">
							<button>
								<div class="fl">즐겨찾기 멤버</div>
								<div class="fr"><%=bookmarkMemberCount%></div>
								<div class="fr"><img src="img/people.png"/></div>
							</button>	
						</div>
						<!--부서 (팀) -->
						<div id="team_name">
							<button>
								<div class="fl"><%=teamName%></div>
								<div class="fr"><%=memberCount%></div>
								<div class="fr"><img src="img/people.png"/></div>
							</button>
						</div>
					</div>
					
					<!-- 즐겨찾기 멤버 리스트 -->
					<div id="bookmark_memberlist" class="re" style="display:none;">
					<% for (OrganizationalBookmarkMemberListDto bmDto : bookmarkMember) { %>
						<div class="member1" member_idx="<%=bmDto.getMemberIdx()%>">
							<img class="fl" src="<%=bmDto.getProfilePicUrl()%>"/>
							<div class="fl member1_detail member_name">
								<div id="name" class="fl"><%=bmDto.getMemberName()%></div> 
								<div id="state"><%=bmDto.getState()==null ? " " : "&nbsp-" + bmDto.getState()%></div>
							</div>
							<div class="fr bookmark_img"><img src="https://flow.team/flow-renewal/assets/images/icons/icon_star_on.png?v=ca949083bd3e2d74e7125167485cff818959483a"></div>
							<div class="fl member1_detail">
								<div id="team" class="fl"><%=bmDto.getTeamName()%></div>
								<div id="position"> /<%=bmDto.getPosition()%></div>
							</div>
							<div id="message" class="fl member1_detail"><%=bmDto.getStateMessage()==null ? " " : bmDto.getStateMessage()%></div>
						</div>
						<% } %>
					</div>
					
					<!-- 팀 멤버 리스트 -->
					<div id="team_memberlist" class="re">
					<% for (OrganizationalMemberListDto omDto : listMember) { %>
						<div class="member1" member_idx="<%=omDto.getMemberIdx()%>">
							<img class="fl" src="<%=omDto.getProfilePicUrl()%>"/>
							<div class="fl member1_detail member_name">
								<div id="name" class="fl"><%=omDto.getMemberName()%></div> 
								<div id="state"><%=omDto.getState()==null ? " " : "&nbsp-" + omDto.getState()%></div>
							</div>
							<% if (omDto.getMemberIdx() != memberIdx ) { %> 
							
				        	<% 
				        		String strDisplayNone1 = "", strDisplayNone2 = "";
				        		if ("Y".equals(omDto.getBookmarkMember()))
				        			strDisplayNone2 = "display: none;";
			        			else
			        				strDisplayNone1 = "display: none;";
				        	
				        	%>
				            <img class="fr bookmark_img team_bookmark_img" style="<%=strDisplayNone1%>" src="https://flow.team/flow-renewal/assets/images/icons/icon_star_on.png?v=ca949083bd3e2d74e7125167485cff818959483a" alt="Bookmark" />
				            <div class="fr nobookmark_img" style="<%=strDisplayNone2%>"></div>
				        	<% } %>
							<div class="fl member1_detail">
								<div id="team" class="fl"><%=omDto.getTeamName()%></div>
								<div id="position"> /<%=omDto.getPosition()%></div>
							</div>
							<div id="message" class="fl member1_detail"><%=omDto.getStateMessage()==null ? " " : omDto.getStateMessage()%></div>
						</div>
						<% } %>
						
					</div>
				</div>
			</div>
			
		<!---------------- 멤버 -------------------->	
			<div id="div_side3" class="fr re">
				<div id="list">
					<div class="fl organizational_chart_name">조직도</div>
					<div class="fl total_number">총 <%=memberCount%>명</div>
					<div class="fr delete"><img src="img/x_button.png"/></div>
				</div>
				<div id="search">
					<div id="search_parents" class="fl re"><input id="search_input" name="member_search" type="text" placeholder="멤버 검색" ></div>
					<div class="ab"><img id="search_icon" src="img/img_icon_search.png"/></div>
				</div>
				 <div id="select">
					<div id="select_teamlist"><button>팀</button></div>
					<div id="select_memberlist"><button>멤버</button></div>
				</div>
				<div id="allmemberlist">
					<button>
						<div class="fl">전체 멤버</div>
						<div class="fr"><%=memberCount%></div>
						<div class="fr"><img src="img/people.png"/></div>
					</button>	
				</div>
				
				<div id="team_memberlist" class="re">
				<% for (OrganizationalMemberListDto omDto : listMember) { %>
					<div class="member1" member_idx="<%=omDto.getMemberIdx()%>">
						<img class="fl" src="<%=omDto.getProfilePicUrl()%>"/>
						<div class="fl member1_detail member_name">
							<div id="name" class="fl"><%=omDto.getMemberName()%></div> 
							<div id="state"><%=omDto.getState()==null ? " " : "&nbsp-" + omDto.getState()%></div>
						</div>
						<% if (omDto.getMemberIdx() != memberIdx ) { %>
			        	<% if ("Y".equals(omDto.getBookmarkMember())) { %>
			            <img class="fr bookmark_img" src="https://flow.team/flow-renewal/assets/images/icons/icon_star_on.png?v=ca949083bd3e2d74e7125167485cff818959483a" alt="Bookmark" style="width:21px, heught:21px;"/>
			        	<% } else {  %>
			            <div class="fr nobookmark_img"></div>
			        	<% } %>
			        	<% } %>
						<div class="fl member1_detail">
							<div id="team" class="fl"><%=omDto.getTeamName()%></div>
							<div id="position"> /<%=omDto.getPosition()%></div>
						</div>
						<div id="message" class="fl member1_detail"><%=omDto.getStateMessage()==null ? " " : omDto.getStateMessage()%></div>
					</div>
					<% } %>
				</div>
			</div>
			
				<!-- 멤버 상세 프로필 -->
				<% for (MemberProfileDto mpDto : memberProfile) { %>
				<% if (mpDto.getMemberIdx() != memberIdx ) { %> 
				<div id="member_profile_container_<%=mpDto.getMemberIdx()%>" class="ab member_profile_container" member_idx="<%=mpDto.getMemberIdx()%>" style="display: none;">				
					<div id="profile_image" class="re">
						<img src="<%=mpDto.getProfilePicUrl()%>">
						<div class="ab member_positon"><em><%=mpDto.getPower() %></em></div>
						<div class="ab profile_member_name"><p><%=mpDto.getMemberName() %></p></div>
					</div>
					<div class="member_profile_state">
						<div><%=mpDto.getState()==null ? " " : mpDto.getState() %></div>
						<div><%=mpDto.getStateMessage()==null ? " " : mpDto.getStateMessage() %></div>
					</div>
					<div id="profileinner2">
						<button class="profileinner2_button"><div>1:1 메세지</div></button>
					</div>
					<div id="profileinner3">
						<table>
							<tr>
								<td><img src="img/organizational.png"  class="inner3_img"></td>
								<td><div class="inner3_td1"><%=mpDto.getTeamName() %></div></td>
							</tr>
							<tr>
								<td></td>
								<td><div class="inner3_td1"><%=mpDto.getPosition() %></div></td>
							</tr>
							<tr>
								<td><img src="img/gift_grey.png" class="inner3_img"></td>
								<td><div class="inner3_td2"><%=mpDto.getBirth().split(" ")[0].replace("-",".") %></div></td>
							</tr>
							<tr>
								<td><img src="img/phone_grey.png" class="inner3_img"></td>
								<td><div class="inner3_td2"><%=mpDto.getPhoneNumber() %></div></td>
							</tr>
							<tr>
								<td><img src="img/email_grey.png" class="inner3_img"></td>
								<td><div class="inner3_td1"><%=mpDto.getEmail() %></div></td>
							</tr>
						</table>
					</div>
				</div>  
				<% } %>
				<% } %> 
				
				<!-- 로그인 멤버 상세 프로필 -->
			 	<% for (MemberProfileDto mpDto : memberProfile) { %>
				<% if (mpDto.getMemberIdx() == memberIdx ) { %> 
					<div id="loginmember_profile_container" class="ab" member_idx="<%=memberIdx%>" style="display: none;">
						<div id="profile_image" class="re">
							<img src="<%=mpDto.getProfilePicUrl()%>">
							<div id="member_positon" class="ab"><em><%=mpDto.getPower() %></em></div>
							<div id="member_name" class="ab"><p><%=mpDto.getMemberName() %></p></div>
						</div>
						
						<div id="inner1">
							<%if(mpDto.getState()!=null) { %>
							<div class="member_inner1_state"><%=mpDto.getState() %><img class="fr inner1_state_x_img" src="img/x.png"></div>
							<% } %> 
							<%if(mpDto.getState() == null) { %>
							<input class="status_input" type="text" name="상태설정" placeholder="상태설정" /> 
							<% } %> 
							<div class="member_inner1_message"><%=mpDto.getStateMessage()==null ? " " : mpDto.getStateMessage() %><img class="fr inner1_message_x_img" src="img/x.png"></div>
							<input style="display:none;" type="text" name="상태메세지" placeholder="상태 메세지" />
						</div>
						<div id="inner2">
							<button class="inner2_button"><div>@멘션 확인하기</div></button>
						</div>
						
						<div id="inner3">
							<table>
								<tr>
									<td><img src="img/organizational.png"  class="inner3_img"></td>
									<td>
										<div class="inner3_font"><%=mpDto.getPosition() %></div>
										<input style="display:none;" class="inner3_td_box" type="text" name="직책" placeholder="직책" />
									</td>
								</tr>
								<tr>
									<td><img src="img/gift_grey.png" class="inner3_img"></td>
									<td>
										<div class="inner3_font"><%=mpDto.getBirth().split(" ")[0].replace("-",".") %></div>
										<input style="display:none;" class="inner3_td_box" type="text" name="생년월일" placeholder="생년월일" />
									</td>
								</tr>
								<tr>
									<td><img src="img/phone_grey.png" class="inner3_img"></td>
									<td>
										<div class="inner3_font"><%=mpDto.getPhoneNumber() %></div>
										<input style="display:none;" class="inner3_td_box" type="text" name="휴대전화" placeholder="휴대전화" />
									</td>
								</tr>
								<tr>
									<td><img src="img/email_grey.png" class="inner3_img"></td>
									<td><div class="inner3_td_email">k97328aa@gmail.com</div></td>
								</tr>
							</table>
						</div>
						<div id="logout">
							<button class="inner2_button"><div>로그아웃</div></button>
						</div>
					</div> 
				<% } %> 
			<% } %> 
		</div>
	</div>
	
	<!--------------------------------------- 기타 팝업창 --------------------------------------->	
	<!-- 투명판 -->
	<div id="div_transparent_filter"></div>
	<!-- 회색판 -->
	<div id="div_grey_filter"></div>
	<!--------------------------- 1:1 톡 -------------------------------->
	<!--_________________________톡 목록-관리자시점_______________________ -->
	<% if(memberIdx == 0 && request.getParameter("service_talkroom_idx") == null) { %>	
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
					<script>alert("DB에서 온 sDto의 service_talkroom_idx : <%=sDto.getServiceTalkroomIdx()%>");</script>
			<%
					String strProfileImgUrl = sDto.getProfilePicUrl();
					//파일 업로드로 업데이트 된 이미지의 경우를 위한 if문
					if(strProfileImgUrl != null && 
							(!strProfileImgUrl.startsWith("http://") && 
							 !strProfileImgUrl.startsWith("https://"))) {
						// DB에 있는 imgUrl이 filename 이라는 것!
						strProfileImgUrl = "upload/" + strProfileImgUrl;
					}
			%>
			<div class="talk_room_area">
				<!-- 톡목록 : 톡방 -->			
				<div class="talk_room">
					<div class="div_talk_room" talkroom_idx="<%=sDto.getServiceTalkroomIdx() %>">
						<div class="fl">
							<div id="talk_room_profile" class="fl"><!-- 프사 --><img src="<%= strProfileImgUrl %>"></div>
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
	<% if(memberIdx != 0 && request.getParameter("service_talkroom_idx")==null) { %>	
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
	<% if(request.getParameter("service_talkroom_idx") != null) { %>	
		<div id="div_service_talk2">
		<!-- 서비스톡 헤더 -->
		<div id="service_talk_header" member_idx="<%= memberIdx%>">
			<div class="header_back fl"><!-- '<' : 채팅방 목록으로 되돌아가기--></div>
			<div class="profile fl header_profile_pic" style="background: url(<%=miDto.getProfilePicUrl()%>) no-repeat center / cover;"></div>
			<div class="fl header_name"><%= miDto.getName() %></div>
			<div class="fr header_quit"><!-- 채팅방 나가기 아이콘 --></div>
			<div class="fr transparent_button"></div><!--서비스톡 닫을 때를 위한 투명 div  -->
			<div id="quit_service_talk">
				<div id="delete_talkroom" service_talkroom_idx="<%=request.getParameter("service_talkroom_idx") %>">
					<div class="fl"></div>
					<div class="fr" talkroom_idx="service_talkroom_idx">상담 나가기</div>
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
			<%-- <script>alert("serviceTalkContentDto.size() : <%=serviceTalkContentDto.size()%>");</script> --%>
			<% if(request.getParameter("service_talkroom_idx") != null) { %>
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
			<% } %>
		</div>
		<div id="service_talk_footer">
			<div>
				<input type="text" placeholder="메시지를 입력하세요...">
				<input type="submit" name="send" id="send_massage">
				<label for="send_massage">
					<div></div>
				</label>
			</div>
		</div>
	  </div><!-- 바디 영역 끝 -->
	<!-- 서비스톡 푸터: 입력칸 -->
	</div><!-- 서비스 톡영역 끝 -->
	<% } %>
	
		
</body>
</html>