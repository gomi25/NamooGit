<%@page import="java.util.List"%>
<%@page import="com.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.ArrayList"%>

<%
	int memberIdx = (Integer)session.getAttribute("memberIdx");
	int teamIdx = (Integer)request.getAttribute("teamIdx");
	int chatroomIdx = (Integer)request.getAttribute("chatroomIdx");
	
// 	String chatroomIdxParam = (String)request.getParameter("chatroomIdx");
// 	int chatroomIdx = (chatroomIdxParam != null) ? Integer.parseInt(chatroomIdxParam) : 1;
// 	int memberIdx = 2;   // 테스트
// 	int teamIdx = 1;     // 테스트
	
// String paramMidx = request.getParameter("midx");
// if(paramMidx != null) {
// 	memberIdx = Integer.parseInt(paramMidx);
// }	
	
	int cntUnreadTotal = 0; // 토픽방에서 안 읽은 메시지 전체 개수 
	int cntOfTopic = 0;     // 토픽방 개수
	int cntOfTopicMember = 0;
	
	SideDao sDao = new SideDao();
	BookmarkDao bDao = new BookmarkDao();
	ChatDao cDao = new ChatDao();
	TopicDao tDao = new TopicDao();
	Common common = new Common();
	
	List<Integer> memberArray = cDao.getChatMembersExceptAuthor(chatroomIdx, memberIdx);
	//============================팀 전체 멤버 조회 테스트==============================
	ArrayList<TeamMemberDto> teamMemberList = sDao.getTeamMemberList(teamIdx);	
	//============================팀 멤버 조회 테스트==============================
	TeamMemberDto teamMember = sDao.getTeamMember(teamIdx, memberIdx);	
	//============================토픽방목록, 채팅방목록 조회 테스트==============================	
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

	//============================채팅전체멤버 조회==============================
	ArrayList<ChatroomMemberDto> listChatroomMember = cDao.getChatroomMemberList(teamIdx, chatroomIdx);
	String chatroomName = cDao.getChatroomNameFromChatroomIdx(chatroomIdx);
	String chatroomInformation = cDao.getChatroomInformation(chatroomIdx);

	//============================채팅글 조회==============================
	ArrayList<ChatContentsDto> chatContentsDto = cDao.getChatContents(teamIdx, chatroomIdx, memberIdx); 
	//============================토픽글 조회==============================
// 		ArrayList<TopicBoardDto> listTopicBoard = tDao.getTopicBoardList(teamIdx, topicIdx, memberIdx);
	//============================토픽댓글 조회==============================
//	 	ArrayList<TopicCommentDto> listTopicComment = tdao.getTopicCommentList(topicBoardIdx, teamIdx);		
	//============================토픽전체멤버 조회==============================
// 		ArrayList<TopicMemberDto> listTopicMember = tDao.getTopicMemberList(teamIdx, topicIdx);
// 		String topicName = tDao.getTopicNameFromTopicIdx(topicIdx);
// 		String topicInformation = tDao.getTopicInformation(topicIdx);
	//============================토픽전체멤버 조회 ==============================
// 		ArrayList<TeamMemberDto> teamMemberOutOfTopic = tDao.getTeamMemberListOutOfTopic(teamIdx, topicIdx);

	//============================채팅전체멤버 조회 테스트==============================
//  	ArrayList<ChatroomMemberDto> listChatroomMember = cDao.getChatroomMemberList(teamIdx, chatroomIdx);
	//============================채팅방에 없는 팀멤버 조회==============================
		ArrayList<TeamMemberDto> teamMemberOutOfChatroom = cDao.getTeamMemberListOutOfChatroom(teamIdx, chatroomIdx);	


%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>NamooChat</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/NamooChat.css"/>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<script>
		let context_path = '${pageContext.request.contextPath}';
		let member_idx = <%=memberIdx%>;
		let team_idx = <%=teamIdx%>;
	</script>
	<script src="${pageContext.request.contextPath}/js/NamooChat.js"></script>
		
	<script> 
		function scrollToBottom() {
	        $("#div_content").stop().animate({ scrollTop: $('#div_content').prop("scrollHeight") }, 1000);
	    }
		/* 8.3(토) 웹소켓 테스트 중 */
		function func_on_message(e) {
// 			alert("도착한 메시지 : " + e.data);
			alert("새로운 메시지가 도착하였습니다.");
			$("#div_content").append(e.data);
		}
		function func_on_open(e) {
			alert("웹소켓 접속");
		}
		function func_on_error(e) {
			alert("ERROR!")
		}
		
		let webSocket = new WebSocket("ws://localhost:9090/NamooPractice1/namooChatBroadcasting");
		webSocket.onmessage = func_on_message;
		webSocket.onopen = func_on_open;
		webSocket.onerror = func_on_error;
		
		$(function(){
			$("#div_msg_blank > .ic_comment_enter").click(function(){
				console.log("클림됨");
				// 사용자idx
				let myIdx = member_idx;
				// 입력한 내용
				let chatContent = $("#write_chat_content_space").text();
				// 사용사idx+///+채팅내용 을 변수에 넣고
				let msg = myIdx + " /// " + chatContent;
				
				/* 채팅 보낼 때 필요한 내용들 */
				// 1) 채팅글idx
				let chatIdx = 0;
				// 2) 사용자idx == myIdx
				// 3) 채팅방idx
				let chatroomIdx = <%=chatroomIdx%>;
				// 4) 사용자의 프로필 이미지 
				let myProfileImg = "<%=teamMember.getProfileUrl()%>";
				// 5) 사용자의 이름
				let myName = "<%=teamMember.getName()%>";
				let myState = "<%=teamMember.getState()%>";
				if (myState == "null") {
				    myState = "";
				} else {
					myState = " - " + myState
				}
				// 7) 입력한 내용 == chatContent
				// 8) 안 읽은 메시지 수 
				let unreadCnt = <%= listChatroomMember.size()%> -1;
				// 9) 시간
				const now = new Date();
				let hours = now.getHours();
				const minutes = String(now.getMinutes()).padStart(2, '0');
				const period = hours >= 12 ? 'PM' : 'AM';
				// 12시간 형식으로 변환
				hours = hours % 12;
				hours = hours ? String(hours).padStart(2, '0') : '12'; // 0을 12로 변환하고 두 자리로 맞춤
				let nowTime = period + ' ' + hours + ':' + minutes;
				console.log(nowTime);
				
// 				const now = new Date();
// 				const year = now.getFullYear();
// 				const month = String(now.getMonth() + 1).padStart(2, '0'); // 월을 두 자리로 맞춤
// 				const date = String(now.getDate()).padStart(2, '0');       // 일을 두 자리로 맞춤
// 				const hours = String(now.getHours()).padStart(2, '0');     // 시를 두 자리로 맞춤
// 				const minutes = String(now.getMinutes()).padStart(2, '0'); // 분을 두 자리로 맞춤
// 				const seconds = String(now.getSeconds()).padStart(2, '0'); // 초를 두 자리로 맞춤
// 				// let nowDate = `${year}/${month}/${date} ${hours}:${minutes}:${seconds}`;
// 				let nowDate = year + '/' + month + '/' + date + ' ' + hours + ':' + minutes + ':' + seconds;
// 				console.log(nowDate);
				
				
				// 10) 즐겨찾기 여부 
				let bookmark = "ic_bookmark_off";
				
				let str = '<div class="chat_message" chat_idx="' + chatIdx + '" writer="' + myIdx + '">' 
						+ '	<div class="fl">' 
						+ '		<img class="profile_img" src="' + myProfileImg + '"/>' 
						+ '	</div>' 
						+ '	<div class="fl">' 
						+ '		<div class="profile_name">' 
						+ 			myName
						+ '			<span>' + myState + '</span>' 
						+ '		</div>' 
						+ '		<div class="msg">' 
						+ '			<div>' 
						+ 				chatContent
						+ '			</div>' 
// 						+ '			<span class="unread">' + unreadCnt +  '</span>' 
						+ '			<span class="time">' + nowTime + '</span>' 
						+ '		</div>' 
						+ '	</div>' 
						+ '	<div style="clear:both;"></div>' 
						+ '	<!-- 채팅글 [더보기] 창 -->' 
						+ '	<div class="more_menu_box">' 
						+ '		<div class="ic_more_menu"></div>' 
						+ '	</div>' 	
						+ '	<!-- 채팅글 [더보기] - [내 토픽글 더보기 창] -->' 
						+ '	<div class="div_chat_more_menu_mine">' 
						+ '	 	<div>' 
						+ '		 	<div class="ic_comment fl"></div>' 
						+ '			<span class="fl">댓글</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '		 	<div class="ic_notice_register fl"></div>' 
						+ '			<span class="fl">공지등록</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '	 		<div class="fl ' + bookmark + '>' 
						+ '	 		</div>' 
						+ '			<span class="fl">즐겨찾기</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '	 		<div class="ic_copy fl"></div>' 
						+ '			<span class="fl">복사</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '	 		<div class="ic_edit_info fl"></div>' 
						+ '			<span class="fl">수정</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '	 		<div class="ic_delete fl"></div>' 
						+ '			<span class="fl">삭제</span>' 
						+ '	 	</div>' 
						+ '	 </div> ' 
						+ '	<!-- 채팅글 [더보기] - [상대 토픽글 더보기 창] -->' 
						+ '	<div class="div_chat_more_menu_other">' 
						+ '	 	<div>' 
						+ '		 	<div class="ic_comment fl"></div>' 
						+ '			<span class="fl">댓글</span>' 
						+ '	 	</div>' 					
						+ '	 	<div>' 
						+ '		 	<div class="ic_notice_register fl"></div>' 
						+ '			<span class="fl">공지등록</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '	 		<div class="fl ' + bookmark +'>' 
						+ '	 		</div>' 
						+ '			<span class="fl">즐겨찾기</span>' 
						+ '	 	</div>' 
						+ '	 	<div>' 
						+ '	 		<div class="ic_copy fl"></div>' 
						+ '			<span class="fl">복사</span>' 
						+ '	 	</div>' 
						+ '	</div>' 
						+ '</div>';
				
				// 웹소켓으로 보냄
				webSocket.send(str);
				// 입력창 비우기
				$("#write_chat_content_space").text('');
				// 보낸 후 내 화면의 #div_content 요소의 맨 마지막 태그로 넣어줌(사용자의 화면)
 				$("#div_content").append(str);
 				scrollToBottom();
//  				$("#div_content").animate({ scrollTop: $('#div_content').prop("scrollHeight")}, 1000);
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
			<div class="fr" data-toggle="tooltip" title="조직도">
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
				<div>1:1 문의하기</div>
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
			<div>프로젝트</div>
			<div class="ic_plus"></div>		
		</div>
		
		<div id="div_project_list_body">
		</div>

		
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
			<% } %>
			
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
				<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
				<div id="div_topic_create"> 
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
		<!---------- 채팅방 상단 - 채팅방 이름 & 메뉴 ---------->	
	  
		<div id="div_title" chatroom_idx="<%=chatroomIdx%>">
			<!-- 상단(1) -->
			<div class=" fl">
				<div class=" fl"><img src="${pageContext.request.contextPath}/img/room.png"/></div>
<!-- 				<div class=" fl"><img class="chat_profile" src="img/chat_profile.png"/></div> -->
				<div class='fl <%= (bDao.isBookmarkChatroom(memberIdx, chatroomIdx)==true ? "ic_bookmark_on" : "ic_bookmark_off") %>' chatroom_idx="<%=chatroomIdx%>"></div>
				<div class=" fl"><%=chatroomName%></div>
				<div class="ic_information fl"></div>
			</div>
			<!-- 상단(2) -->
			<div class=" fr">
				<div class=" fl" data-toggle="tooltip" title="참여 멤버">
					<img src="${pageContext.request.contextPath}/img/member.png"/>
				</div>
				<div class=" fl"><%= listChatroomMember.size() %></div>
				<div class=" fl" data-toggle="tooltip" title="채팅 시작하기">
					<img src="${pageContext.request.contextPath}/img/addmember.png"/>
				</div>
				<div class=" fl" data-toggle="tooltip" title="대화방 알림">
					<img id="alarm_on" src="${pageContext.request.contextPath}/img/roomalarm.png"/>
					<img id="alarm_off" src="${pageContext.request.contextPath}/img/roomalarmoff.png"/>
				</div>
				<div class=" fl" data-toggle="tooltip" title="더보기"><img src="${pageContext.request.contextPath}/img/threespot.png"/></div>
			</div>
			
			<!-- i에 커서 올리면 토픽방 설명 나옴 -->
			<div class="information_pop_up">
				<div><%= chatroomName %></div>
				<div><%= chatroomInformation %></div>
			</div>
			
			<!--------------------------------------- 채팅방 메뉴 팝업창 --------------------------------------->
			<!---------- 채팅멤버 검색창 ---------->
			<div class="div_participants">
				<!-- 멤버검색(1) -->
				<div>
					<img src="${pageContext.request.contextPath}/img/searchMember.png"/>
					<input type="search" name="member_search" placeholder="참여 멤버"/>
				</div>
				<!-- 멤버목록(2) -->
				<div>
					<!--타이틀 -->
					<div>팀멤버</div>
					<!--목록 -->
					<div class="scrollbar">
						<%
							for(ChatroomMemberDto chatroomMemberDto : listChatroomMember) {
						%>
						<!-- 멤버 반복되는 부분 -->	
						<div class="div_member_list" member_idx="<%= chatroomMemberDto.getMemberIdx() %>">
							<div class="fl">
								<img class="profile_img" src="<%= chatroomMemberDto.getProfileUrl() %>"/>
							</div>
							<div class="fl">
								<div class="profile_name"><%= chatroomMemberDto.getName() %></div>
							</div>
							<div class="ic_exit_chatroom fr"></div>
							<div class="fr">
								<div class='profile_power
									<% if( (chatroomMemberDto.getPower()).equals("정회원")){ %>
										<%= " power_orange" %>
									<% } else if ( (chatroomMemberDto.getPower()).equals("소유자")){ %>
										<%= " power_blue" %>
									<%  } else { %>
										<%= " power_pupple" %>
									<% } %>							
									'> <%= chatroomMemberDto.getPower() %>
								</div>
							</div>					
							<div>
								<div class="on_user"></div>
							</div>
							<div style="clear:both;"></div>
						</div>
						<%
							}
						%>
							<div class="div_not_exist">검색 결과가 없습니다.</div>
							<div class="invite_member">+토픽에 멤버 초대하기</div>				
					</div>
					
				</div>
			</div>
			
			<!---------- 채팅 시작하기 (초대하기 버튼) ---------->		
			<form id="form_invite_chat_member" action="InviteChatroomMemberServlet" method="post">	
				<input type="hidden" name="input_chatroom_idx" value="<%=chatroomIdx%>"/>
				<div id="div_chat_start" class="border">
					<!-- 상단부 -->
					<div>
						<span>채팅 시작하기</span>
						<div class="exit"></div>
					</div>
					
					<div>
						<div class="fl">
							<span class="span_chat_start">이름</span>
							<div class="ic_search"></div>
							<input type="text" placeholder="멤버 검색"/>
							
							<span class="span_chat_start">부서</span>
			        		<select name="department">
								<option value="전체">전체</option>
								<option value="개발">개발</option>
								<option value="인사">인사</option>
								<option value="회계">회계</option>
							</select>
							
							<span class="span_chat_start">직책</span>
							<select name="position">
								<option value="전체">전체</option>
								<option value="대리">대리</option>
								<option value="사원">사원</option>
								<option value="인턴">인턴</option>
								<option value="주임">주임</option>
								<option value="팀장">팀장</option>
							</select>
							
							<span class="span_chat_start">권한</span>
			        		<select name="department">
								<option value="전체">전체</option>
								<option value="관리자">관리자</option>
								<option value="소유자">소유자</option>
								<option value="정회원">정회원</option>
								<option value="준회원">준회원</option>
							</select>
							
							<div class="ic_reset fl"></div>
							<input type="reset" value="검색조건 초기화"/>
						</div>
						
						<div class="fl">
							<span class="span_chat_start">팀멤버</span>
							<div id="start_member" class="border">
						<%
							for(TeamMemberDto memberOutOfChatroom : teamMemberOutOfChatroom){
						%>							
								<div class="div_member_list2" member_idx="<%=memberOutOfChatroom.getMemberIdx()%>">
									<div class="fl">
										<img class="profile_img" src="<%=memberOutOfChatroom.getProfileUrl() %>"/>
									</div>
									<div class="fl">
										<div class="profile_name"><%=memberOutOfChatroom.getName()%> </div>
										<div>
											<span><%=memberOutOfChatroom.getDepartment()%> / </span>
												<span> <%=memberOutOfChatroom.getPosition()%></span>
											</div>
									</div>
									<div class="fr">
										<div class="profile_power power_orange"><%=memberOutOfChatroom.getPower()%></div>
									</div>					
									<div style="clear:both;"></div>
								</div>
						<%
							}
						%>
								
								<div class="div_not_exist">
									검색 결과가 없습니다<br/>
									지금 바로 7팀에 새로운 멤버를 초대해보세요<br/>
								</div>
								<div class="invite_member">
									팀에 멤버 초대하기
								</div>
							</div> <!-- start_member 닫는 태그 -->
							
							<span class="span_chat_start">선택된 멤버 <span class="count_memeber"> 5</span> </span>
							<div id="start_choice">
							<%
								for(ChatroomMemberDto chatroomMemberDto : listChatroomMember) {
							%>
								<div class="fl" member_idx="<%=chatroomMemberDto.getMemberIdx()%>">
									<img class="profile_img" src="<%=chatroomMemberDto.getProfileUrl()%>"/>
									<span><%=chatroomMemberDto.getName() %></span>						
								</div>
							<%
								}
							%>	
								<div style="clear:both;"></div>
							</div>
		
							<span class="span_chat_start">동일한 멤버와 참여 중인 채팅방</span>
							<div id="start_together" class="border">
								<div>
									<img class="chat_profile" src="img/chat_profile.png"/>
									<span>프로젝트 채팅방</span>
									<img src="img/arrowright.png"/>
								</div>
								<div>
									<img class="chat_profile" src="img/chat_profile.png"/>
									<span>테스트 채팅방</span>
									<img src="img/arrowright.png"/>
								</div>
							</div>
						</div>
						<div style="clear:both;"></div>
						
						<div id="div_chat_warning">
							<div class="fl">
								<img src="img/info_grey.png"/>
							</div>
							<div class="fl">
								<span>• 채팅은 최대 10명까지 참여할 수 있습니다. 더 많은 멤버의 참여가 필요한 경우 토픽을 생성하여 대화해 주세요.</span><br/>
								<span>• 채팅은 추후 토픽으로 변경이 불가능합니다. 주제별 토픽을 생성하여 관리해 보세요.</span>
							</div>
						</div>
						
						<div>
							<button type="button" name="invite_member" id="btn_invite_member">초대하기</button>
							<button type="button" name="close_window">닫기</button>
						</div>
						<input type="hidden" id="selected_members" name="selected_members" value="">						
					</div>
				</div><!-- div_chat_start 닫는 태그 -->
			</form>	
			
			<!---------- 더보기 팝업창 ---------->		
			<div id="div_chatroom_more_menu">
				<div>
					<div class="ic_notice_register"></div>
					<span>공지등록</span>
				</div>
				<div>
					<div class="ic_edit_info"></div>
					<span>정보 변경하기</span>
				</div>
				<div>
					<div class="ic_show_file"></div>
					<span>파일 보기</span>
				</div>
				<div>
					<div class="ic_exit_chatroom"></div>
					<span>채팅방 나가기</span>
				</div>
			</div>	
		</div><!-- div_title 닫는 태그 -->
		
		
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
		
		
		
		<!---------- 채팅방 중앙부  ---------->			
	<div id="div_content" class="scrollbar">
		<!--------------------------------------- 공지 --------------------------------------->
		<!---------- 공지 등록하기---------->
		<div id="div_notice_register" class="border"> 
			<!-- 상단부 / div:nth-child(1) -->
			<div>
				<span>공지 등록</span>
				<div class="exit fr"></div>
			</div>
			
			<!-- 중앙부 / div:nth-child(2) -->
			<div>
				<!-- div:nth-child(2) > div:nth-child(1) -->
	  			<label for="textarea_write">메시지</label>
	  			<div>
					<div>
						<textarea name="input_msg" id="textarea_write" placeholder="메시지를 입력해주세요" rows="10"></textarea>
					</div>
					<div>
						<label class="ic_file_clip fl" for="upload_file"></label>
						<input type="file" id="upload_file" style="clip:rect(0, 0, 0, 0); display:none;"/>
					</div>
	  			</div>
			</div>
			
			<!-- 하단부 / div:nth-child(3) -->
			<div>
				<button class="fr" type="submit" id="new_topic">등록하기</button>
				<button class="fr" id="" name="close_window">취소</button>
			</div>
		</div>		
			
		<!---------- 채팅방 내용 ---------->
		<div class="chat_date">
			<div>2024년 8월 2일 금요일</div>
			<div></div>
		</div>
		<%
			for(ChatContentsDto chatContents : chatContentsDto) {
				if(chatContents.getMemberIdx() == null){
		%>		
<%-- 		<div class="chat_notice" chat_idx="<%=chatContents.getChatIdx()%>" writer="<%=chatContents.getMemberIdx()%>"> --%>
<%-- 			<span><%=chatContents.getContent()%><span class="time"><%=chatContents.getWriteDate()%></span></span> --%>
<!-- 		</div> -->
		<%
				} else {
		%>
		<div class="chat_message" chat_idx="<%=chatContents.getChatIdx()%>" writer="<%=chatContents.getMemberIdx()%>">
			<div class="fl">
				<img class="profile_img" src="<%=chatContents.getProfileUrl()%>"/>
			</div>
			<div class="fl">
				<div class="profile_name">
					<%=chatContents.getName()%>
					<span><%=(chatContents.getState() == null) ? "" : (" - " + chatContents.getState()) %></span>
				</div>
				<div class="msg">
					<div>
						<%=chatContents.getContent() == null ? "" : chatContents.getContent()%>
					</div>
					<span class="unread"><%-- <%=(chatContents.getUnreadCnt() == 0) ? "" : chatContents.getUnreadCnt()%> --%></span>
					<span class="time"><%=chatContents.getWriteDate()%></span>
				<%
					if(chatContents.getFileIdx() != null) { 
						int fileIdx = chatContents.getFileIdx();
						String fileName = chatContents.getFileName();
						int fileTypeIdx = common.getFileTypeIdxFromFileName(fileName);
						
						if(common.getFileTypeIdxFromFileName(chatContents.getFileName())==2){	// 이미지일 때
				%>
					<div class="file_space" fileTypeIdx="<%=fileTypeIdx%>">
						<div>
							<img src="upload/<%=chatContents.getFileName()%>" fileTypeIdx="<%=fileTypeIdx%>"/>
						</div>
					</div>
				<% 		} else { %>
					<div class="file_space" fileTypeIdx="<%=fileTypeIdx%>">
						<div class="ic_file_img <%=common.getIconImgFromFileTypeIdx(fileTypeIdx)%> fl" fileTypeIdx="<%=fileTypeIdx%>"></div>
						<span><%=fileName%></span>
					</div>
				<%  	} 
				  } %>
					
				</div>
			</div>
			<div style="clear:both;"></div>
			<!-- 채팅글 [더보기] 창 -->
			<div class="more_menu_box">
				<div class="ic_more_menu"></div>
			</div>	
			<!-- 채팅글 [더보기] - [내 토픽글 더보기 창] -->
			<div class="div_chat_more_menu_mine">
			 	<div>
				 	<div class="ic_comment fl"></div>
					<span class="fl">댓글</span>
			 	</div>
			 	<div>
				 	<div class="ic_notice_register fl"></div>
					<span class="fl">공지등록</span>
			 	</div>
			 	<div>
			 		<div class='fl <%= bDao.isBookmarkChat(chatContents.getMemberIdx(), chatContents.getChatIdx()) ? "ic_bookmark_on" : "ic_bookmark_off" %>'>
			 		</div>
					<span class="fl">즐겨찾기</span>
			 	</div>
			 	<div>
			 		<div class="ic_copy fl"></div>
					<span class="fl">복사</span>
			 	</div>
			 	<div>
			 		<div class="ic_edit_info fl"></div>
					<span class="fl">수정</span>
			 	</div>
			 	<div>
			 		<div class="ic_delete fl"></div>
					<span class="fl">삭제</span>
			 	</div>
			 </div> 
			<!-- 채팅글 [더보기] - [상대 토픽글 더보기 창] -->
			<div class="div_chat_more_menu_other">
			 	<div>
				 	<div class="ic_comment fl"></div>
					<span class="fl">댓글</span>
			 	</div>					
			 	<div>
				 	<div class="ic_notice_register fl"></div>
					<span class="fl">공지등록</span>
			 	</div>
			 	<div>
			 		<div class='fl <%= bDao.isBookmarkChat(memberIdx, chatContents.getChatIdx()) ? "ic_bookmark_on" : "ic_bookmark_off" %>'>
			 		</div>
					<span class="fl">즐겨찾기</span>
			 	</div>
			 	<div>
			 		<div class="ic_copy fl"></div>
					<span class="fl">복사</span>
			 	</div>
		 	</div> 
		</div> <!-- chat_message 닫는 태그 -->
		<%
				}
			}
		%>			
	</div><!-- div_content 닫는 태그 -->
		
	<!---------- 채팅방 하단 -채팅입력 ---------->		
	<!-- 8.3(토) 테스트 중으로 폼 주석 처리 -->
<!-- 	<form id="form_chat" action="ChatFileUploadServlet" method="post" enctype="multipart/form-data" > -->
		<div id="div_bottom">
			<!-- 채팅 입력창 -->
			<div id="div_msg_box" class="wide">
				<div id="div_msg_space" class="fl"> 
					<!-- 댓글 입력하는 부분 -->
					<div id="div_msg_blank">
						<div class="fl"> 
							<img src="" style="width:50px; height:50px; display:none;"/>
						</div>
						<div id="write_chat_content_space" contenteditable="true" data-placeholder="채팅을 입력하세요..." class="scrollbar fl"></div>
						<div class="ic_comment_enter"></div>
						<input type="hidden" name="chat_content" id="hidden_chat_content">
			 			<input type="hidden" name="chatroom_idx" id="hidden_chatroom_idx" value="<%=chatroomIdx%>">
						<div style="clear:both;"></div>
					</div>
					<!-- 이모티콘, 언급, 파일 -->
					<div id="div_msg_icon"> <!-- 이거 -->
						<div class="ic_mention fl"></div>
						<label class="ic_file_clip fl" for="upload_file_chat_content"></label>
						<input type="file" id="upload_file_chat_content" name="file_upload" style="clip:rect(0, 0, 0, 0); display:none;"/>
					</div>
				</div>
			</div>
		</div>
<!-- 	</form> -->
	
	</div>	<!-- div_side2 닫는 태그 -->
</div> <!-- div_total_side 닫는 태그 -->	


<!-- ---------------NamooChatMessageWindow.html------------------ -->
	<!-- 메시지창 전체 -->
	<div id="div_side_message">
		<!-- 메시지창(1) -->
		<div id="div_side_msg_header">
			<img src="img/arrowleft.png"/>
			<span>메시지</span>
			<div class="exit fr"></div>
		</div>
		<!-- 메시지창(2) -->
		<div id="div_side_msg_body" chat_idx="">
			<%
			int chatIdx = 1;
			for(ChatContentsDto chatContents : chatContentsDto) {
				if(chatContents.getChatIdx() == chatIdx){
					
				ArrayList<ChatCommentDto> chatCommentDto = cDao.getChatCommentList(chatContents.getChatIdx(), teamIdx);
			%>	
			
			<!-- 메시지내용 -->
			<div id="div_side_content" chat_idx="<%=chatContents.getChatIdx()%>" writer="<%=chatContents.getMemberIdx()%>">
				<div>
					<div class="fl">
						<img src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
					</div>
					<div class="fl">
						<span><%=chatContents.getName()%></span>
						<span><%=chatContents.getState()%></span>
						<span><%=chatContents.getWriteDate()%></span>
					</div>
				</div>
				<div>
					<span><%=chatContents.getContent()%></span>
				</div>
				
			<%
				if(chatContents.getFileIdx() != null) { 
					int fileIdx = chatContents.getFileIdx();
					String fileName = chatContents.getFileName();
					int fileTypeIdx = common.getFileTypeIdxFromFileName(fileName);
					
					if(common.getFileTypeIdxFromFileName(chatContents.getFileName())==2){	// 이미지일 때
			%>
				<div class="file_space" fileTypeIdx="<%=fileTypeIdx%>">
					<div>
						<img src="upload/<%=chatContents.getFileName()%>" fileTypeIdx="<%=fileTypeIdx%>"/>
					</div>
				</div>
			<% 		} else { %>
				<div class="file_space" fileTypeIdx="<%=fileTypeIdx%>">
					<div class="ic_file_img <%=common.getIconImgFromFileTypeIdx(fileTypeIdx)%> fl" fileTypeIdx="<%=fileTypeIdx%>"></div>
					<span><%=fileName%></span>
				</div>
			<%  	} 
				}
			%>
				
			</div> <!-- div_side_content 닫는 태그 -->
			
			<!-- 댓글수 -->
			<div id="div_side_comment_count" chat_idx="<%=chatContents.getChatIdx()%>" >
				<span>댓글</span> 
				<span><%=chatCommentDto.size()%></span>
			</div>
			
			<%
					for(ChatCommentDto chatComments : chatCommentDto){
			%>
			<!-- 댓글(1) -->
			<div class="div_side_comments" class="user" chat_comment_idx="<%=chatComments.getChatCommentIdx()%>" writer="<%=chatComments.getMemberIdx()%>">
				<div>
					<div class="fl">
						<img src="<%=chatComments.getProfileUrl()%>"/>
					</div>
					<div class="fl">
						<span><%=chatComments.getName()%></span>
						<span>- <%=chatComments.getState()%></span>
						<span><%=chatComments.getWriteDate()%></span>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div>
					<span><%=chatComments.getComments() %></span>
				</div>
			</div>
			
			<%
					}
				
				} // if문 닫는 태그
			}
			%>
			
		</div> <!-- div_side_msg_body 닫는 태그 -->
		<!-- 메시지창(3) -->
		<div id="div_side_msg_footer">
			<!-- 채팅 입력창 -->
			<div id="div_side_msg_box">
<!-- 			<div id="div_side_msg_input_body"> css, js 바꾸기-->
				<div id="div_side_msg_space">
					<!-- 댓글 입력하는 부분 -->
					<div id="div_side_msg_blank">
						<div id="write_chat_comment_space" contenteditable="true" data-placeholder="댓글을 입력하세요..." class="scrollbar"></div>
<!-- 						<input type="hidden" name="chat_conmment" id="hidden_chat_comment"> -->
<%-- 			수정필요	<input type="hidden" name="chat_comment_idx" value="<%=chatContentsDto%>"> --%>
<!-- 						<textarea rows="1" placeholder="댓글을 입력하세요..."></textarea> -->
						<!-- <input type="text" name="comment" placeholder="댓글을 입력하세요..."/> -->
						<div class="ic_comment_enter"></div>
					</div>
					<!-- 이모티콘, 언급, 파일 -->
					<div id="div_side_msg_icon">
						<div class="ic_mention fl"></div>
						<label class="ic_file_clip fl" for="upload_file"></label>
						<input type="file" id="upload_file" style="clip:rect(0, 0, 0, 0); display:none;"/>
					</div>
				</div>
			</div>
		</div>
		
	</div><!-- div_side_message 닫는 태그 -->
	
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
		

	
	
	
	<!--------------------------------------- 기타 팝업창 --------------------------------------->	
	<!-- 투명판 -->
	<div id="div_transparent_filter"></div>
	<!-- 회색판 -->
	<div id="div_grey_filter"></div>
	
	<!-- 채팅방 나가기 시 알림창 -->
    <form action="Controller" id="deleteChatroomForm" method="post">
    	<input type="hidden" name="command" value="delete_chatroom"/>
	    <input type="hidden" name="chatroomIdx" value="<%=chatroomIdx%>"/>
	    <input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
	 	<div id="delete_chatroom_pop_up" class="notification_pop_up">
		    <div>이 채팅방에서 나가시겠습니까?</div>
		    <div>다시 초대를 받지 않는 한 동일한 채팅방에 참여하실 수 없습니다.</div>
		    <div>
	            <button type="button" class="btn btn_cancel">취소</button>
	            <button type="submit" class="btn btn_danger">확인</button>
		    </div>
		</div> 
   	</form>	
	
	<!-- 채팅글 삭제 시 알림창 -->
	<div id="delete_chat_content_pop_up" class="notification_pop_up">
		<div>이 메시지를 삭제하시겠습니까?</div>
		<div>삭제하시면 복구할 수 없습니다.</div>
		<div>
			<button type="button" class="btn btn_cancel">취소</button>
			<button type="button" class="btn btn_danger">확인</button>
		</div>
	</div>
	
	<!-- 채팅댓글 삭제 시 알림창 -->
	<div id="delete_chat_comment_pop_up" class="notification_pop_up">
		<div>이 댓글을 삭제하시겠습니까?</div>
		<div>삭제하시면 복구할 수 없습니다.</div>
		<div>
			<button type="button" class="btn btn_cancel">취소</button>
			<button type="button" class="btn btn_danger">확인</button>
		</div>
	</div>
	
	<!-- (토픽의 팝업창 예시) 여기서부터 -->
	<div id="delete_topic_pop_up" class="notification_pop_up">
	    <div>토픽을 정말 삭제하시겠습니까?</div>
	    <div>모든 메시지와 파일이 삭제되며 복구할 수 없습니다</div>
	    <div>
            <form action="${pageContext.request.contextPath}/jsp/DeleteTopic.jsp" id="deleteTopicForm" method="get">
	            <input type="hidden" name="input_topicIdx" value="<%= request.getParameter("topicIdx") %>"/>
	            <button type="button" class="btn btn_cancel">취소</button>
	            <button type="submit" class="btn btn_danger" id="confirmDelete">확인</button>
        	</form>
	    </div>
	</div>

	<!-- 토픽 멤버 내보내기 시 알림창 -->
	<div id="remove_topicMember_pop_up" class="notification_pop_up">
		<div>정말 이 멤버를 내보내시겠습니까?</div>
		<div id="remove_member_info">
			<img class="profile_img" src=""/> <!-- 내보낼 멤버 이미지 -->
			<span></span>
		</div>
		<div>
			<button class="btn_cancel">취소</button>
			<button class="btn_ok">확인</button>
		</div>
	</div>
	<!-- 토픽 나가기 시 알림창 -->
	<div id="exit_topic_pop_up" class="notification_pop_up">
		<div>현재 이 토픽의 관리자입니다.</div>
		<div>다른 멤버를 토픽관리자로 지정 후 나가실 수 있습니다.</div>
		<div>
			<button class="btn_cancel">취소</button>
			<button class="btn_ok">확인</button>
		</div>
	</div>
	<!-- 토픽 관리자 지정 시 알림창 -->
	<div id="topic_manager_pick_pop_up" class="notification_pop_up">
		<div>1명을 토픽 관리자로 지정하시겠습니까?</div>
		<div>토픽 정보 변경을 통해 토픽 관리자를 관리할 수 있습니다.</div>
		<div>
			<button class="btn_cancel">취소</button>
			<button class="btn_ok">확인</button>
		</div>
	</div>
	<!-- 공지 삭제하기 시 알림창 -->
	<div id="delete_noti_pop_up" class="notification_pop_up">
		<div>공지를 삭제하시겠습니까?</div>
		<div>대화방에서 공지가 삭제됩니다.</div>
		<div>
			<button class="btn_cancel">취소</button>
			<button class="btn_ok">확인</button>
		</div>
	</div>


	<!-- 토픽 예시 여기까지 -->	
		
</body>
</html>