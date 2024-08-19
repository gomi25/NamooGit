<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.*"%>
<%@page import="com.Common"%>
<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 	int memberIdx = 2;   // 테스트
	int teamIdx = 1;     // 테스트 중
	//int memberIdx = (Integer)session.getAttribute("memberIdx");
	//int teamIdx = (Integer)request.getAttribute("teamIdx");
	
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
	
	//int colorIdx = 1; // 테스트
    //ProjectDao projectDao = new ProjectDao();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>namoo_addproject</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AddProject.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/NamooMainTool.css"/>
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
	<script src="${pageContext.request.contextPath}/js/NamooMainTool.js"></script>
</head>
<body>
	<!--------------------------------------- 기타 팝업창 --------------------------------------->	
	<!-- 투명판 -->
	<div id="div_transparent_filter"></div>
	<!-- 회색판 -->
	<div id="div_grey_filter"></div>

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
						<% } %>
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
						<div class="ic_alarm_off"></div>
						<div class='<%= (topicDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
							<%= topicDto.getUnread() >=1 ? topicDto.getUnread() : "" %>
						</div>
					</div>
					<% } %>
				</div>
			</div>		
			<% } %>
			<%
				for(TopicDto topicDto : listTopic){
			%>
			<div class="topic_item" topic_idx="<%=topicDto.getTopicIdx()%>">
				<div class='<%=(topicDto.isBookmark() ? "ic_bookmark_on" : "ic_bookmark_off") %>'></div>
				<span><%=topicDto.getName()%></span>
				<div class='<%=(topicDto.getAlarm()==1 ? "" : "ic_alarm_off") %>'></div>
				<div class='<%= (topicDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
					<%= topicDto.getUnread() >=1 ? topicDto.getUnread() : "" %>
				</div>
			</div>
			<% } %>
		</div><!-- div_topicroom_list_body 닫는 태그 -->

		<!---------- 프로젝트 목록 ---------->	
		<div id="div_project_list_header">
			<div></div>
			<div onclick="location.href='Controller?command=project_list'">프로젝트</div>
			<div class="ic_plus" onclick="location.href='Controller?command=create_project_form'"></div>
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
					<div class='<%= (chatroomDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
						<%= chatroomDto.getUnread() >=1 ? chatroomDto.getUnread() : "" %>
					</div>
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
	
	<!-- 프로젝트 만들기 -->
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="create_project" />
			<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
			<input type="hidden" name="memberIdx" value="<%=memberIdx%>"/>		
			<div class="parents">
				<div id="addproject" >
					<div>프로젝트 만들기</div>
					<div id="project_name"><input type="text" name="project_name" placeholder="제목을 입력하세요"></div>
					<div id="project_color"><p>프로젝트 색상 </p></div>
					<div id="project_colorselect" class="color_select_container fl">
		
						<% for(int i=0; i<=11; i++) { %>
								<div><div id="blue" class="color_select<%=i+1 %>"></div><input class="ab radio" type="radio" name="color_select" value="<%= i + 1 %>"/></div>
						<% } %>
							
					</div>
					<div id="project_button" ><button>프로젝트 생성</button></div>
				</div>
				<div id="photo" >
					<img class="re" src="img/addproject.png"/>
						<button class="ab"><img src="img/x_button.png"></button>
						<img id="photo_top" class="ab" src="https://flow.team/flow-renewal/assets/images/project_template/template-tit.png?v=d5ff71180aa5f78c0e8110be3e4ff6ecf376e77c"/>
						<img id="photo_bottom" class="ab" src="https://flow.team/flow-renewal/assets/images/project_template/template-feed.png?v=a3c7e402a757051a39eb1555fc308db8f588a9b6"/>
				</div>
			</div>
		</form>
</div> 

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
		
	<div style="clear:both;"></div>

</body>
</html>