<%@page import="com.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.ArrayList"%>

<%
	// 테스트 필요
	String chatroomIdxParam = (String)request.getParameter("chatroomIdx");
	int chatroomIdx = (chatroomIdxParam != null) ? Integer.parseInt(chatroomIdxParam) : 1;

	int memberIdx = 2;   // 테스트
	int teamIdx = 1;     // 테스트
	
	int cntUnreadTotal = 0; // 토픽방에서 안 읽은 메시지 전체 개수 
	int cntOfTopic = 0;     // 토픽방 개수
	int cntOfTopicMember = 0;
	
	SideDao sDao = new SideDao();
	BookmarkDao bDao = new BookmarkDao();
	ChatDao cDao = new ChatDao();
	TopicDao tDao = new TopicDao();
	Common common = new Common();
	
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

//============================토픽전체멤버 조회==============================
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
//  	ArrayList<ChatroomDto> listChatroomMember = cDao.getChatroomMemberList(teamIdx, chatroomIdx);
	
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
			<div class='<%= cntUnreadTotal >= 1 ? "div_topicroom_unread" : "" %>'>
				<%= cntUnreadTotal >= 1 ? cntUnreadTotal : ""%>
			</div>
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
				<div>
					<div class="ic_topic_folder_open fl"></div>
					<div class="ic_topic_folder_close fl"></div>
					<span class="fl"><%=folderDto.getName() %></span>
					<span class="fl"><%=listSubTopic.size() %></span>
					<div class="ic_topic_folder_more_menu fr"></div>
				</div>
				<div>
					<%
						for(TopicDto topicDto : listSubTopic) {
					%>
					<!--  토픽 -->
					<div class="topic_item" topic_idx="<%=topicDto.getTopicIdx()%>">
						<div class='<%=(topicDto.isBookmark() ? "ic_bookmark_on" : "ic_bookmark_off") %>'></div>
						<span><%=topicDto.getName()%></span>
						<div class="ic_alarm_off"></div>
						<%-- <div class="div_unread"><%=topicDto.getUnread()%></div> --%>
						<div class='<%= (topicDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
							<%= topicDto.getUnread() >=1 ? topicDto.getUnread() : "" %>
						</div>
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
// 					System.out.println("370행, topicDto.isBookmark() = " + topicDto.isBookmark());

			%>
			<div class="topic_item" topic_idx="<%=topicDto.getTopicIdx()%>">
				<div class='<%=(topicDto.isBookmark() ? "ic_bookmark_on" : "ic_bookmark_off") %>'></div>
				<span><%=topicDto.getName()%></span>
				<div class='<%=(topicDto.getAlarm()==1 ? "" : "ic_alarm_off") %>'></div>
				<div class='<%= (topicDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
					<%= topicDto.getUnread() >=1 ? topicDto.getUnread() : "" %>
				</div>
			</div>
			<%
				}
			%>

		</div>


<!---------- 프로젝트 목록 ---------->	
		<div id="div_project_list_header">
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
<%-- 					<% { // 반복문을 돌려 : ProfileUrlColorDto의 리스트에 대해서. %> --%>
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
<!-- 	 					<img class="fl" src="https://jandi-box.com/files-profile/9836702f0b26c245a3bb3516afb6452d?size=80"/>
						<img class="fl" src="https://jandi-box.com/files-profile/049da7c0fc758a63b6df1f4d9d8bb454?size=80"/>
						<img class="fl" src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/> 
 -->						<% } %>
<%-- 					<% } %> --%>
					</div>
					<span><%=chatroomDto.getChatroomName() %></span>
					<%-- <div class="div_unread"><%=chatDto.getUnread() %></div> --%>
					<div class='<%= (chatroomDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
						<%= chatroomDto.getUnread() >=1 ? chatroomDto.getUnread() : "" %>
					</div>
					<div class="exit"></div>
				</div>
			<% } %>
			
		</div>
		
	<!-- 여기까지 일단 완료 7.22(월) 17시 -->	
	<!-- 여기서부터 이어서 !! -->	
	
	<!--------------------------------------- div_side1 - 팝업창 --------------------------------------->
	<!---------- 토픽 '+버튼' 클릭 시 팝업창 ---------->			
		<div id="div_topic_plus">
			<div>새로운 토픽 생성하기</div>
			<div>참여 가능한 토픽 보기</div>
			<div>폴더 생성하기</div>
		</div>
		<!---------- 토픽 '+버튼' -> 새로운 토픽 생성 생성하기 ---------->	
		<form action="#">
			<div id="div_topic_create"> 
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span>새 토픽 생성</span>
					<div class="exit"></div>
				</div>
				
				<!-- 중앙부 / div:nth-child(2) -->
				<div>
					<!-- div:nth-child(2) > div:nth-child(1) -->
					<div>
		     			<label for="input_name">이름</label><span>*</span><br/>
		     			<input type="text" name="name" id="input_name" placeholder="토픽 이름을 입력하세요" required>
		     			<span class="text_max_value fr">/ 60</span>
		     			<span class="text_current_value fr">0</span><br/><br/>
					</div>
					<!-- div:nth-child(2) > div:nth-child(2) -->
					<div>
			  			<label for="textarea_info">토픽 설명(옵션)</label><br/>
						<textarea name="info" id="textarea_info" placeholder="토픽에 대해 설명해주세요" rows="3"></textarea>
						<span class="text_max_value fr">/ 300</span>
						<span class="text_current_value fr">0</span><br/><br/>
					</div>
					<!-- div:nth-child(2) > div:nth-child(3) -->
					<div>
			  			<label for="topic_open">공개 여부</label>
			  			<span>*</span><span>토픽 생성 이후 변경 불가</span><br/>
						<div class="topic_open_select">
							<div class="topic_public">
								<div class="fl"></div>
								<input type="radio" id="topic_public" name="topic_open" value="public">
								<label for="topic_public">공개</label>		
								<div class="fr"></div>
							</div>
		
							<div class="topic_private">
								<div class="fl"></div>
						        <input type="radio" id="topic_private" name="topic_open" value="private">
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
					</div>
	
				</div>
						<!-- button태그와 input태그 차이 찾아보기 -->
						<!-- <input type="submit" value="생성하기"/> -->
				<div>
					<button class="fr" type="submit" id="new_topic">생성하기</button>
					<button class="fr" id="">닫기</button>
				</div>
				
			</div>
		</form>
		
		<!---------- 토픽 '+버튼' -> 참여 가능한 토픽 보기 ---------->	
		<div id="div_topic_openlist">
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span>토픽 목록</span>
					<div class="exit"></div>				
				</div>
				
				<!-- 중앙부1 / div:nth-child(2) -->
				<div>
					<input type="text" name="name" id="input_name" placeholder="토픽 이름을 검색하세요">
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
						<!-- <div class="div_member_list">
							<div class="fl">
								<img class="profile_img" src="https://jandi-box.com/files-profile/4065aa3b8f54d09d2c8d799718754681?size=80"/>
							</div>
							<div class="fl">
								<div class="profile_name">김현지</div>
							</div>
							<div class="fr">
								<div class="profile_power power_blue">소유자</div>
							</div>					
							<div style="clear:both;"></div>
						</div> -->
							<div class="div_not_exist">검색 결과가 없습니다.</div>
							<div class="invite_member">+토픽에 멤버 초대하기</div>				
					</div>
					
				</div>
			</div>
			
			<!---------- 채팅 시작하기 ---------->		
			<div id="div_chat_start" class="border">
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
						
						<img id="icon_reset" src="img/reset.png"/>
						<input type="reset" value="검색조건 초기화"/>
					</div>
					<div class="fl">
						<span class="span_chat_start">팀멤버</span>
						<div id="start_member" class="border">
							
							<div class="div_member_list2" member_idx="100">
								<div class="fl">
									<img class="profile_img" src="https://jandi-box.com/files-resource/characters/character_01.png"/>
								</div>
								<div class="fl">
									<div class="profile_name">이두걸</div>
									<div>
										<span>7팀 / </span>
										<span> 대표님</span>
									</div>
								</div>
								<div class="fr">
									<div class="profile_power power_orange">정회원</div>
								</div>					
								<div style="clear:both;"></div>
							</div>
							<div class="div_member_list2" member_idx="101">
								<div class="fl">
									<img class="profile_img" src="https://jandi-box.com/files-resource/characters/character_01.png"/>
								</div>
								<div class="fl">
									<div class="profile_name">이세걸</div>
									<div>
										<span>7팀 / </span>
										<span> 이사님</span>
									</div>
								</div>
								<div class="fr">
									<div class="profile_power power_orange">정회원</div>
								</div>					
								<div style="clear:both;"></div>
							</div>							
							<div class="div_member_list2" member_idx="102">
								<div class="fl">
									<img class="profile_img" src="https://jandi-box.com/files-resource/characters/character_01.png"/>
								</div>
								<div class="fl">
									<div class="profile_name">이영걸</div>
									<div>
										<span>7팀 / </span>
										<span> 전무님</span>
									</div>
								</div>
								<div class="fr">
									<div class="profile_power power_orange">정회원</div>
								</div>					
								<div style="clear:both;"></div>
							</div>							
							<div class="div_member_list2" member_idx="103">
								<div class="fl">
									<img class="profile_img" src="https://jandi-box.com/files-resource/characters/character_01.png"/>
								</div>
								<div class="fl">
									<div class="profile_name">이한걸</div>
									<div>
										<span>7팀 / </span>
										<span> 실장님</span>
									</div>
								</div>
								<div class="fr">
									<div class="profile_power power_orange">정회원</div>
								</div>					
								<div style="clear:both;"></div>
							</div>							
						</div>
						
						<span class="span_chat_start">선택된 멤버 <span class="count_memeber"> 5</span> </span>
						<div id="start_choice">
							<div class="fl" member_idx="15">
								<img class="profile_img" src="https://jandi-box.com/files-profile/62932f86e7cb4a9d8b6dd39149b4c0d5"/>
								<span>강하늘</span>						
							</div>
							<div class="fl" member_idx="14">
								<img class="profile_img" src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/>
								<span>김민지</span>						
							</div>
							<div class="fl" member_idx="13">
								<img class="profile_img" src="https://jandi-box.com/files-profile/4065aa3b8f54d09d2c8d799718754681?size=80"/>
								<span>김현지</span>						
							</div>
							<div class="fl" member_idx="12">
								<img class="profile_img" src="https://jandi-box.com/files-profile/b615e8e5e21064bc8a32231d8628a136?size=80"/>
								<span>원혜경</span>						
							</div>
							<div class="fl" member_idx="11">
								<img class="profile_img" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
								<span>이나무</span>						
							</div>
	<!-- 						<div class="choice_member fl">
								<img class="profile_img" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
								<span>노멤버</span>						
								<div></div>
							</div> -->
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
						<button type="button" name="invite_member">초대하기</button>
						<button type="button" name="close_window">닫기</button>
					</div>
				</div>
			</div>
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
				<div>2024년 4월 29일 월요일</div>
				<div></div>
			</div>
		<%
			for(ChatContentsDto chatContents : chatContentsDto) {
				if(chatContents.getMemberIdx() == -1){
		%>		
			<div class="chat_notice" chat_idx="<%=chatContents.getChatIdx()%>" writer="<%=chatContents.getMemberIdx()%>">
<!-- 				<span><strong>이나무</strong> 님이 비공개 토픽을 생성했습니다.<span class="time">PM 3:31</span></span> -->
				<span><%=chatContents.getContent()%><span class="time"><%=chatContents.getWriteDate()%></span></span>
			</div>
		<%
				} else {
		%>
			<div class="chat_message" chat_idx="<%=chatContents.getChatIdx()%>" writer="<%=chatContents.getMemberIdx()%>">
				<div class="fl">
<!-- 					<img class="profile_img" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/> -->
					<img class="profile_img" src="<%=chatContents.getProfileUrl()%>"/>
				</div>
				<div class="fl">
					<div class="profile_name">
						<%=chatContents.getName()%>
						<span> - <%=chatContents.getState()%></span>
					</div>
					<div class="msg">
						<div>
							<%=chatContents.getContent()%>
						</div>
						<span class="unread"><%=chatContents.getUnreadCnt()%></span>
						<span class="time"><%=chatContents.getWriteDate()%></span>
						<% if(chatContents.getFileIdx()!=null) { %>
						<div class="file_space" fileTypeIdx="<%=common.getFileTypeIdxFromFileName(chatContents.getFileName())%>">
<!-- 			수정필요				<div> -->
<%-- 								<img src="upload/<%=chatContents.getFileName()%>" fileTypeIdx="<%=common.getFileTypeIdxFromFileName(chatContents.getFileName())%>"/> --%>
<!-- 							</div> -->
<%-- <%-- 							<img class="ic_pdf" fileTypeIdx="<%=common.getFileTypeIdxFromFileName(chatContents.getFileName())%>"/> --%> --%>
<!-- 							<div class="not_img_file" > -->
<%-- 								<div class="" fileTypeIdx="<%=common.getFileTypeIdxFromFileName(chatContents.getFileName())%>"></div> --%>
<!-- 								<span></span> -->
<!-- 							</div> -->
						</div>
						<% } %>
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
			</div>
		<%
				}
			}
		%>			
		
			<div class="chat_notice">
				<span><strong>YG</strong> 님이 나갔습니다.<span class="time">PM 3:49</span></span>
			</div> -->
			
			<div class="chat_message">
				<div class="fl">
					<img class="profile_img" src="https://jandi-box.com/files-profile/b615e8e5e21064bc8a32231d8628a136?size=80"/>
				</div>
				<div class="fl">
					<div class="profile_name">원혜경</div>
					<div class="msg user">
						음..
						<span class="unread">1</span>
						<span class="time">PM 5:10</span>
					</div>
				</div>
				<div style="clear:both;"></div>
			</div>
			<!-- 채팅글원본+댓글-->
			<div class="chat_comment_message">
				<!-- 댓글프로필이미지 -->
				<div class="fl">
					<img class="profile_img" src="https://cdn.jandi.com/app/assets/images/icon/f8aa3b7e.ic-comment-fill.svg"/>
				</div>
				<!-- 원본+댓글수+댓글 -->
				<div class="fl">
					<!-- 원본 -->
					<div class="comment_origin">
						<div class="fl">
							<img class="profile_img" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
						</div>
						<div class="fl">
							<span>이나무</span>
							<span>2024/04/29 PM 03:33</span>
							<span>특정 재료에 알레르기가 있거나 선호하지 않는 음식이 있다면 말씀해주세요!</span>
						</div>
						<div style="clear:both;"></div>
					</div>
					<!-- 댓글수 -->
					<div class="comment_count">
						<span>2개 댓글 모두 보기 →</span>
					</div>
					<!-- 댓글 -->
					<div class="comments">
						<div class="fl">
							<img class="profile_img" src="https://jandi-box.com/files-profile/b615e8e5e21064bc8a32231d8628a136?size=80"/>
						</div>
						<div class="fl">
							<span>원혜경</span>
							<span> - 회의중</span>
							<span>굴, 전복, 골뱅이 빼고는 대체로 다 좋아합니다~</span>
							<span class="unread">1</span>
							<span class="time">PM 5:17</span>
						</div>
						<div style="clear:both;"></div>
					</div>
				</div>
				<div style="clear:both;"></div>
			</div>
		
	</div><!-- div_content 닫는 태그 -->
	
	
	<!---------- 채팅방 하단 -채팅입력 ---------->	<!-- (기존) 7.24(수) -->		
<%-- 	<div id="div_bottom">
		<!-- 채팅 입력창 -->
		<div id="div_msg_box" class="wide">
			<div id="div_msg_space">
				<!-- 댓글 입력하는 부분 -->
				<div id="div_msg_blank"> <!-- 이거 -->
					<div id="write_chat_content_space" contenteditable="true" data-placeholder="채팅을 입력하세요..." class="scrollbar"></div>
					<input type="hidden" name="chat_content" id="hidden_chat_content">
		 			<input type="hidden" name="chatroom_idx" value="<%=chatroomIdx%>">
					<!-- <textarea rows="1" name="comment" placeholder="댓글을 입력하세요..."></textarea> -->
					<!-- <input type="text" name="comment" placeholder="댓글을 입력하세요..."/> -->
					<div class="file_list_container"><img scr=""><img scr=""></div>
					<div class="ic_comment_enter"></div>
				</div>
				<!-- 이모티콘, 언급, 파일 -->
				<div id="div_msg_icon"> <!-- 이거 -->
					<div class="ic_mention fl"></div>
					<label class="ic_file_clip fl" for="upload_file"></label>
					<input type="file" id="upload_file" style="clip:rect(0, 0, 0, 0); display:none;"/>
				</div>
			</div>
		</div> --%>

		
<!---------- 채팅방 하단 -채팅입력 ---------->	<!-- (변경) 7.25(목)-->		
	<form id="form_chat" action="ChatFileUploadServlet" method="post" enctype="multipart/form-data" >
		<div id="div_bottom">
			<!-- 채팅 입력창 -->
			<div id="div_msg_box" class="wide">
				<div id="div_msg_space" class="fl"> <%-- style="width:96%;" --%>
					<!-- 댓글 입력하는 부분 -->
					<div id="div_msg_blank"> <!-- 이거 -->
						<div class="fl"> 
							<img src="" style="width:50px; height:50px; display:none;"/>
						</div>
						<div id="write_chat_content_space" contenteditable="true" data-placeholder="채팅을 입력하세요..." class="scrollbar fl"></div>
						<input type="hidden" name="chat_content" id="hidden_chat_content">
			 			<input type="hidden" name="chatroom_idx" id="hidden_chatroom_idx" value="<%=chatroomIdx%>">
						<div class="ic_comment_enter"></div>
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
	</form>
	
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
		<div id="div_side_msg_body">
			<!-- 메시지내용 -->
			<div id="div_side_content">
				<div>
					<div class="fl">
						<img src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
					</div>
					<div class="fl">
						<span>이나무</span>
						<span></span>
						<span>2024/05/13 AM 02:27</span>
					</div>
				</div>
				<div>
					<span>
						특정 재료에 알레르기가 있거나 선호하지 않는 음식이 있다면 말씀해주세요!
					</span>
				</div>
			</div>
			
			<!-- 댓글수 -->
			<div id="div_side_comment_count">
				<span>댓글</span> 2
			</div>
			
			<!-- 댓글(1) -->
			<div class="div_side_comments" class="user">
				<div>
					<div class="fl">
						<img src="https://jandi-box.com/files-profile/b615e8e5e21064bc8a32231d8628a136?size=80"/>
					</div>
					<div class="fl">
						<span>원혜경</span>
						<span>- 회의중</span>
						<span>2024/06/04 PM 2:30</span>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div>
					<span>
						굴, 전복, 골뱅이 빼고는 대체로 다 좋아합니다~
					</span>
				</div>
			</div>
			<!-- 댓글(2) -->
			
			<div class="div_side_comments">
				<div>
					<div class="fl">
						<img src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/>
					</div>
					<div class="fl">
						<span>김민지</span>
						<span>- 재택근무</span>
						<span>2024/06/04 PM 2:35</span>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div>
					<span>
						매운 것만 아니면 좋습니다!
					</span>
				</div>
			</div>
		</div>
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
			<div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> <!-- 방 이름 -->
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src=""></div>
					<div>
						<span class="profile_name">원혜경</span>
						<span>2024/05/08 PM 09:02</span>
						<span>내용들... @김민지</span>
					</div>
				</div>
			</div>
			<div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> <!-- 방 이름 -->
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src=""></div>
					<div>
						<span class="profile_name">원혜경</span>
						<span>2024/05/08 PM 09:02</span>
						<span>내용들... @김민지</span>
					</div>
				</div>
			</div>
			<div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> <!-- 방 이름 -->
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src=""></div>
					<div>
						<span class="profile_name">원혜경</span>
						<span>2024/05/08 PM 09:02</span>
						<span>내용들... @김민지</span>
					</div>
				</div>
			</div>
			<div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> <!-- 방 이름 -->
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src=""></div>
					<div>
						<span class="profile_name">원혜경</span>
						<span>2024/05/08 PM 09:02</span>
						<span>내용들... @김민지</span>
					</div>
				</div>
			</div>
			<div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> <!-- 방 이름 -->
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src=""></div>
					<div>
						<span class="profile_name">원혜경</span>
						<span>2024/05/08 PM 09:02</span>
						<span>내용들... @김민지</span>
					</div>
				</div>
			</div>
			<div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> <!-- 방 이름 -->
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_item_bottom">
					<div class="profile_img"><img src=""></div>
					<div>
						<span class="profile_name">원혜경</span>
						<span>2024/05/08 PM 09:02</span>
						<span>내용들... @김민지</span>
					</div>
				</div>
			</div>
			<div class="bookmark_file_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div>
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_file_item_bottom">
					<div class="file_img"><img src=""></div><!-- 파일이미지 -->
					<div>
						<span class="profile_name">테스트 메모.txt</span>
						<span>원혜경</span>
						<span>2024/05/08 PM 09:02</span>
					</div>
				</div>
			</div>
			<div class="bookmark_file_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div>
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_file_item_bottom">
					<div class="file_img"><img src=""></div><!-- 파일이미지 -->
					<div>
						<span class="profile_name">테스트 메모.txt</span>
						<span>원혜경</span>
						<span>2024/05/08 PM 09:02</span>
					</div>
				</div>
			</div>
			<div class="bookmark_file_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div>
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_file_item_bottom">
					<div class="file_img"><img src=""></div><!-- 파일이미지 -->
					<div>
						<span class="profile_name">테스트 메모.txt</span>
						<span>원혜경</span>
						<span>2024/05/08 PM 09:02</span>
					</div>
				</div>
			</div>
			<div class="bookmark_file_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div>
					<div class="ic_bookmark_on fr"></div>
					<div class="ic_more_menu fr"></div>
				</div>
				<div class="bookmark_file_item_bottom">
					<div class="file_img"><img src=""></div><!-- 파일이미지 -->
					<div>
						<span class="profile_name">테스트 메모.txt</span>
						<span>원혜경</span>
						<span>2024/05/08 PM 09:02</span>
					</div>
				</div>
			</div>
			<div class="bookmark_ending_item">
				<div class="ic_ending"></div>
			</div>
		</div>
	</div>
		

	
	<!--------------------------------------- 기타 팝업창 --------------------------------------->	
	<!-- 투명판 -->
	<div id="div_transparent_filter"></div>
	<!-- 회색판 -->
	<div id="div_grey_filter"></div>
	
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