<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.*"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.*"%>

<%
	// 파라미터 받아서 하는 부분
	int memberIdx = (Integer)session.getAttribute("memberIdx");
	int teamIdx = (Integer)request.getAttribute("teamIdx");
	int topicIdx = (Integer)request.getAttribute("topicIdx"); 

	int cntOfTopic = 0;     // 토픽방 개수
	int cntOfTopicMember = 0;
	int cntUnreadTotal = 0;  
	
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
	
	//============================토픽글 조회==============================
	ArrayList<TopicBoardDto> listTopicBoard = tDao.getTopicBoardList(teamIdx, topicIdx, memberIdx);
	//============================토픽댓글 조회==============================
	// 	ArrayList<TopicCommentDto> listTopicComment = tdao.getTopicCommentList(topicBoardIdx, teamIdx);		
	//============================토픽전체멤버 조회==============================
	ArrayList<TopicMemberDto> listTopicMember = tDao.getTopicMemberList(teamIdx, topicIdx);
	String topicName = tDao.getTopicNameFromTopicIdx(topicIdx);
	String topicInformation = tDao.getTopicInformation(topicIdx);
	//============================토픽전체멤버 조회==============================
	ArrayList<TeamMemberDto> teamMemberOutOfTopic = tDao.getTeamMemberListOutOfTopic(teamIdx, topicIdx);

%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>NamooTopic</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/NamooTopic.css"/>
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
	<script src="${pageContext.request.contextPath}/js/NamooTopic.js"></script>
</head>
<body>
	<!--------------------------------------- 화면 최상단 --------------------------------------->	
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
	</div> <!-- div_header 닫는 태그 -->

	<!--------------------------------------- #div_total_side --------------------------------------->	
	<div id="div_total_side">
	
		<!--------------------------------------- #div_side1 --------------------------------------->	
		<div id="div_side1" class="fl">
			<!---------- 프로필 ---------->		
			<div>
				<div id="div_profile_box" member_idx="<%=memberIdx%>">
					<img src="<%=teamMember.getProfileUrl()%>"/>
					<div><%=teamMember.getName() %></div>
				</div>
			</div>
			
			<!---------- 대화방 검색 ---------->			
			<div id="div_room_search" data-toggle="tooltip" title="토픽 또는 채팅방으로 바로 이동하기"> <!-- (2) 대화방 검색 -->
				<div>
					<!-- <img src="img/img_icon_search_bg_grey.png"/> -->
					<div class="ic_search fl"></div>
					<span>대화방 검색</span>
					<!-- <span>Ctrl+J</span> -->
				</div>
			</div>
			
			<!---------- 대화방 검색 -클릭 시 팝업창---------->	
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
								<% if(listImgUrls.size()==0) { %>
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
			
			</div><!-- search_all_room 닫는 태그 -->	
			
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
					<span class="fl">이름 순</span> 
				</div>
				<span><%=cntOfTopic %> 개 토픽</span>
			</div>
			<!---------- 토픽방 목록 / body ---------->			
			<div id="div_topicroom_list_body" class="scrollbar"> 
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
			<div id="div_chatroom_list_header">
				<div></div>
				<div>채팅</div>
				<div class='<%= (cntChatTotalUnread >= 1 ? "div_chatroom_unread" : "") %>'> 
					<%= cntChatTotalUnread >=1 ? cntChatTotalUnread : "" %>
				</div>
				<div class="ic_plus"></div>
			</div>
			<div id="div_chatroom_list_body">
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
							if(++cnt>4) {
								break;
							}	
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
			<%	} %>
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
		
		</div> <!-- div_side1 닫는 태그 -->
	
		
		<!-- #div_new_chat_start 팝업창  ~ -->
		
		
	
		<!-- ~ #div_new_chat_start 팝업창  -->


		

	
		<!--------------------------------------- div_side2 --------------------------------------->		
		<!--------------------------------------- 토픽방 --------------------------------------->
		<div id="div_side2" class="fr wide">
		
			<!---------- 토픽방 상단바 - 토픽방 이름 & 메뉴 ---------->		
			<div id="div_title">
				<!-- 토픽방 상단(1) -->
				<div class=" fl">
					<div class="fl"><img src="${pageContext.request.contextPath}/img/room.png"/></div>
					<div class='fl <%= (bDao.isBookmarkTopic(memberIdx, topicIdx)==true ? "ic_bookmark_on" : "ic_bookmark_off") %>' topic_idx="<%=topicIdx%>"></div>
					<div class="fl"><%=topicName %></div>
					<div class="ic_information fl"></div>
				</div>
				<!-- 토픽방 상단(2) -->
				<div class="fr">
					<div class="fl" data-toggle="tooltip" title="참여 멤버">
						<img src="${pageContext.request.contextPath}/img/member.png"/>
						<span><%= listTopicMember.size() %></span>
					</div>
					<div class="fl" data-toggle="tooltip" title="멤버 초대하기">
						<img src="${pageContext.request.contextPath}/img/addmember.png"/>
					</div>
					<div class="fl" data-toggle="tooltip" title="대화방 알림">
						<img id="alarm_on" src="${pageContext.request.contextPath}/img/roomalarm.png"/>
						<img id="alarm_off" src="${pageContext.request.contextPath}/img/roomalarmoff.png"/>
					</div>
					<div class=" fl" data-toggle="tooltip" title="더보기"><img src="${pageContext.request.contextPath}/img/threespot.png"/></div>
				</div>
				<!-- i에 커서 올리면 토픽방 설명 나옴 -->
				<div class="information_pop_up">
					<div><%= topicName %></div>
					<div><%= topicInformation %></div>
				</div>
			</div> <!-- div_title 닫는 태그 -->	

			<!---------- 채팅방 생성하기 ---------->		
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


		
			<!---------- 멤버 초대하기 팝업창 ---------->	
<%-- 			<form id="form_invite_member" action="${pageContext.request.contextPath}/jsp/InviteTopicMember.jsp" method="get"> --%>
			<form id="form_invite_member" action="Controller" method="post">	
				<input type="hidden" name="command" value="invite_topic_member">
				<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
				<input type="hidden" name="topicIdx" value="<%=topicIdx%>"/>
				<div id="div_invite_member" class="border">
					<div>
						<span class="fl">멤버 초대하기</span>
						<div class="exit fr"></div>
					</div>
					<div>
						<div class="fl">
							<span class="span_invite_member">이름</span>
							<div class="ic_search"></div>
							<input type="text" name="member_search" placeholder="멤버 검색"/>
							
							<span class="span_invite_member">부서</span>
			        		<select name="department">
								<option value="전체">전체</option>
								<option value="개발">개발</option>
								<option value="인사">인사</option>
								<option value="회계">회계</option>
							</select>
							
							<span class="span_invite_member">직책</span>
							<select name="position">
								<option value="전체">전체</option>
								<option value="대리">대리</option>
								<option value="사원">사원</option>
								<option value="인턴">인턴</option>
								<option value="주임">주임</option>
								<option value="팀장">팀장</option>
							</select>
							
							<span class="span_invite_member">권한</span>
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
							<span class="span_invite_member">팀멤버</span>
							<div id="team_member" class="border scrollbar">
						<%
							for(TeamMemberDto memberOutOfTopicDto : teamMemberOutOfTopic){
						%>
								<div class="div_member_list2" member_idx="<%=memberOutOfTopicDto.getMemberIdx()%>">
									<div class="fl">
										<img class="profile_img" src="<%=memberOutOfTopicDto.getProfileUrl() %>"/>
									</div>
									<div class="fl">
										<div class="profile_name"><%=memberOutOfTopicDto.getName() %></div>
										<div>
											<span><%=memberOutOfTopicDto.getDepartment()%> / </span>
											<span> <%=memberOutOfTopicDto.getPosition()%></span>
										</div>
									</div>
									<div class="fr">
										<div class="profile_power power_orange"><%=memberOutOfTopicDto.getPower()%></div>
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
							</div>
							
							<span class="span_invite_member">선택된 멤버 <span class="count_memeber"> 5</span> </span>
							<div id="start_choice">
								<div style="clear:both;"></div>
							</div>
						</div>
						<div style="clear:both;"></div>
					</div>
					<div>
						<button type="button" name="invite_member" id="btn_invite_member">초대하기</button>
						<button type="button" name="close_window">닫기</button>
					</div>
					<input type="hidden" id="selected_members" name="selected_members" value="">
				</div>
			</form> <!-- form_invite_member 닫는 태그 -->

			<!------------ 토픽방 상단바 - [더보기(메뉴)] - [공지등록] -------->
			<!-- form 예정 -->
			<div id="div_notice_register" class="border"> 
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span>공지 등록</span>
					<div class="exit fr"></div>
				</div>
				<!-- 중앙부 / div:nth-child(2) -->
				<div class="scrollbar">
		  			<label for="write_topic_noti_box">메시지</label>
		  			<div id="write_topic_noti_box">
		  				<textarea name="textarea_noti" id="textarea_noti" placeholder="메시지를 입력해주세요" rows="10" class="scrollbar"></textarea>
						<!-- 파일 업로드하는 공간 -->
					 	<div class="upload_file_box">
						 	<div class="upload_file_space scrollbar" class="scrollbar" >
						 		<div class="upload_file_list">
						 			<div class="ic_file_clip"></div>
						 			<div>download.png</div>
						 			<div>
						 				<div class="ic_delete"></div>
						 			</div>
						 		</div>
						 		<div class="upload_file_list">
						 			<div class="ic_file_clip"></div>
						 			<div>download.png</div>
						 			<div>
						 				<div class="ic_delete"></div>
						 			</div>
						 		</div>
						 		<div class="upload_file_list">
						 			<div class="ic_file_clip"></div>
						 			<div>download.png</div>
						 			<div>
						 				<div class="ic_delete"></div>
						 			</div>
						 		</div>
						 	</div>
						 	<div class="upload_file_count">
							 	<span class="text_max_value fr"> 10</span>
							 	<span class="text_current_value fr">0 / </span>
						 	</div>
					 	</div>
						<div class="ic_mention_file_box">
							<label class="ic_file_clip fl" for="upload_file_tnoti"></label>
							<input type="file" id="upload_file_tnoti" multiple style="clip:rect(0, 0, 0, 0); display:none;"/>
						</div>
		  			</div>
				</div>
				<!-- 하단부 / div:nth-child(3) -->
				<div>
					<button class="fr" type="submit" id="new_noti_content">등록하기</button>
					<button class="fr close_window" type="button" id="" name="close_window">취소</button>
				</div>
			</div> <!-- 공지등록 닫는 태그 -->					
		

			<!---------- 토픽방 상단 상단바 - [더보기(메뉴)] - [정보변경하기] ---------->		
			<div id="div_topic_update"> 
				<!-- 상단부 / div:nth-child(1) -->
				<div>
					<span>토픽 정보 변경하기</span> 
					<div class="exit fr"></div>
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
			  			<label for="">토픽 관리자</label>
			  			<div class="fl"></div> <!-- i 아이콘 -->
						<div class="div_topic_manager_checkbox">
							<span>원혜경</span>
	
							<!---------- 토픽멤버 검색창 ---------->
							<div id="div_manager_participants">
								<!-- #div_manager_participants > div:nth-child(1) -->
								<div class="scrollbar">
									<!-- 멤버검색(1) -->
									<div>
										<img src="${pageContext.request.contextPath}/img/searchMember.png"/>
										<input type="search" name="member_search" placeholder="멤버 검색"/>
									</div>
									<!-- 멤버목록(2) -->
									<div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list1" name="member_list_checkbox" />
											<div class="fl">
												<div class="on_user"></div>
												<img class="profile_img" src="https://jandi-box.com/files-profile/8eee92ad3f2be9d8a67aa0b0839f10b2?size=80"/>
											</div>
											<div class="fl">
												<div class="profile_name">원혜경</div>
											</div>
											<div class="fr">
												<div class="profile_power power_pupple">토픽 관리자</div>
											</div>					
											<div>
											</div>
											<div style="clear:both;"></div>
										</div>
										
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list2" name="member_list_checkbox" />
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
										</div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list3" name="member_list_checkbox" />
											<div class="fl">
												<img class="profile_img" src="https://jandi-box.com/files-profile/62932f86e7cb4a9d8b6dd39149b4c0d5"/>
											</div>
											<div class="fl">
												<div class="profile_name">강하늘</div>
											</div>
											<div class="fr">
												<div class="profile_power power_orange">정회원</div>
											</div>					
											<div style="clear:both;"></div>
										</div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list4" name="member_list_checkbox" />
											<div class="fl">
												<img class="profile_img" src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/>
											</div>
											<div class="fl">
												<div class="profile_name">김민지</div>
											</div>
											<div class="fr">
												<div class="profile_power power_orange">정회원</div>
											</div>					
											<div style="clear:both;"></div>
										</div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list5" name="member_list_checkbox" />
											<div class="fl">
												<img class="profile_img" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
											</div>
											<div class="fl">
												<div class="profile_name">이나무</div>
											</div>
											<div class="fr">
												<div class="profile_power power_orange">정회원</div>
											</div>					
											<div style="clear:both;"></div>
										</div>
									</div>
								</div>
								<!-- #div_manager_participants > div:nth-child(2) -->
								<div>
									<button type="button" class="btn_cancel fl">취소</button>
									<button type="button" class="fl">
										<span>0</span> 
										명 토픽 관리자 지정
									</button>
								</div>
							</div>
						</div>
						<br/><br/>
					</div>
					<!-- div:nth-child(2) > div:nth-child(4) -->
					<div>
						<label>멤버 내보내기</label><br/>
						<div class="div_topic_member_checkbox">
							<span>내보낼 멤버를 선택해주세요.</span>
							<!---------- 토픽멤버 검색창 ---------->
							<div id="div_remove_participants">
								<div class="scrollbar">
									<!-- 멤버검색(1) -->
									<div>
										<img src="${pageContext.request.contextPath}/img/searchMember.png"/>
										<input type="search" name="member_search" placeholder="멤버 검색"/>
									</div>
									<!-- 멤버목록(2) -->
									<div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list1" name="member_list_checkbox" />
											<div class="fl">
												<div class="on_user"></div>
												<img class="profile_img" src="https://jandi-box.com/files-profile/8eee92ad3f2be9d8a67aa0b0839f10b2?size=80"/>
											</div>
											<div class="fl">
												<div class="profile_name">원혜경</div>
											</div>
											<div class="fr">
												<div class="profile_power power_pupple">토픽 관리자</div>
											</div>					
											<div>
											</div>
											<div style="clear:both;"></div>
										</div>
										
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list2" name="member_list_checkbox" />
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
										</div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list3" name="member_list_checkbox" />
											<div class="fl">
												<img class="profile_img" src="https://jandi-box.com/files-profile/62932f86e7cb4a9d8b6dd39149b4c0d5"/>
											</div>
											<div class="fl">
												<div class="profile_name">강하늘</div>
											</div>
											<div class="fr">
												<div class="profile_power power_orange">정회원</div>
											</div>					
											<div style="clear:both;"></div>
										</div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list4" name="member_list_checkbox" />
											<div class="fl">
												<img class="profile_img" src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/>
											</div>
											<div class="fl">
												<div class="profile_name">김민지</div>
											</div>
											<div class="fr">
												<div class="profile_power power_orange">정회원</div>
											</div>					
											<div style="clear:both;"></div>
										</div>
										<div class="div_member_list">
											<input class="fl" type="checkbox" id="member_list5" name="member_list_checkbox" />
											<div class="fl">
												<img class="profile_img" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
											</div>
											<div class="fl">
												<div class="profile_name">이나무</div>
											</div>
											<div class="fr">
												<div class="profile_power power_orange">정회원</div>
											</div>					
											<div style="clear:both;"></div>
										</div>
									</div>
								</div>
								<div>
									<button type="button" class="btn_cancel fl">취소</button>
									<button type="button" class="fl">
										<span>0</span> 
										명 내보내기
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 하단부 / div:nth-child(2) -->						
				<div>
					<button class="fr" type="submit" id="update_complete">완료</button>
					<button class="fr" type="button" id="update_cancel">취소</button>
				</div>
				
			</div> <!-- div_topic_update 닫는 태그 -->		
		
			<!----------  [토픽글 작성] 토픽방 내부 하단의 게시글 등록 클릭 시 팝업창---------->	
<%-- 		<form action="${pageContext.request.contextPath}/jsp/WriteTopicBoard.jsp" id="writeTopicBoardForm" method="get">  --%>
			<form id="form_board_write" action="TopicFileUploadServlet" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="topic_idx" id="hidden_topic_idx" value="<%=topicIdx%>">
				<input type="hidden" name="team_idx" value="<%=teamIdx %>"/>
				<div id="div_write_topic_board">
					<!-- 상단부 / div:nth-child(1) -->
					<div>
						<span>신규 게시글 작성</span> 
						<div class="exit fr"></div>
					</div>
					
					<!-- 중앙부 / div:nth-child(2) -->
					<div class="scrollbar"> 
						<!-- div:nth-child(2) > div:nth-child(1) -->
						<div>
			     			<label for="input_board_title" class="fl">제목</label><span class="fl">*</span><br/>
			     			<input type="text" name="topic_board_title" id="input_board_title" placeholder="게시글 제목을 입력하세요" required />
			     			<span class="text_max_value fr">/ 50</span>
			     			<span class="text_current_value fr">0</span><br/><br/>
						</div>
						
						<!-- div:nth-child(2) > div:nth-child(2) -->
						<div>
				  			<label for="write_topic_board_box" id="label_board_content" class="fl">게시글 본문</label><span class="fl">*</span><br/>
							<div id="write_topic_board_box">
								<!-- 텍스트 작성하는 공간 -->
								<div id="write_topic_board_space" class="scrollbar" contenteditable="true" data-placeholder="게시글의 본문을 입력하세요"  aria-labelledby="label_board_content"></div>
							 	<input type="hidden" name="topic_board_content" id="hidden_board_content">
		                    
								<!-- 파일 업로드하는 공간 -->
							 	<div class="upload_file_box">
								 	<div class="upload_file_space scrollbar" class="scrollbar" >
								 		<div class="upload_file_list">
								 			<div>
								 				<img src=""/>
								 			</div>
								 			<div><!-- 파일이름 들어오는 공간 --></div>
								 			<div>
								 				<div class="ic_delete"></div>
								 			</div>
								 		</div>
								 	</div>
								 	<div class="upload_file_count">
									 	<span class="text_max_value fr"> 30</span>
									 	<span class="text_current_value fr">0 / </span>
								 	</div>
							 	</div>
								
								<!-- mention, file 아이콘 공간 -->
								<div class="ic_mention_file_box">
									<div class="ic_mention fl" data-toggle="tooltip" title="멘션"></div>
										<div class="mention_pop_up">
											<div class="scrollbar">
												<div class="mention_member_list">
													<div>
														<div class="ic_mention"></div>
													</div>
													<div class="member_all">
														<div class="fl">All</div>
														<div class="fl">토픽 모든 멤버</div>
													</div>
												</div>
											
										<%
											for(TopicMemberDto topicMemberDto : listTopicMember) {
										%>
												<div class="mention_member_list" member_idx="<%=topicMemberDto.getMemberIdx()%>">
													<!-- .mention_member_list > div:nth-child(1) -->
													<div>
														<img class="profile_img" src="<%=topicMemberDto.getProfileUrl()%>"/>
														<div class="on_user"></div>
													</div>
													<!-- .mention_member_list > div:nth-child(2) -->
													<div>
														<div class=" profile_name"><%=topicMemberDto.getName()%></div>
														<div class=""><%=topicMemberDto.getDepartment()%> / <%=topicMemberDto.getPosition()%></div>
													</div>
												</div>
										<%
											}
										%>		
											</div>
										</div>
									<label class="ic_file_clip fl" for="upload_file_tboard" data-toggle="tooltip" title="파일 업로드"></label>
									<input type="file" id="upload_file_tboard" name="file_upload" style="clip:rect(0, 0, 0, 0); display:none;"/>
								</div>
							</div>
								<span class="text_max_value fr">/ 5,000</span>
								<span class="text_current_value fr">0</span>
						</div>
					</div>		
					<!-- 하단부 / div:nth-child(3)	-->
					<div>
						<button class="fr" type="submit" id="create_complete">생성하기</button>
						<button class="fr" type="button" id="create_cancel">닫기</button>
					</div>
				</div><!-- "div_write_topic_board" 닫는 태그 -->
			</form>	<!-- form_board_write 닫는 태그 -->
		
			<!---------- [토픽글 수정] 팝업창 ---------->
<%-- 			<form action="${pageContext.request.contextPath}/jsp/UpdateTopicBoard.jsp" id="updateTopicBoardForm" method="get">  --%>
			<form action="Controller" id="updateTopicBoardForm" method="post"> 
				<input type="hidden" name="command" value="update_topic_board"/>
				<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
				<input type="hidden" name="topicIdx" value="<%=topicIdx%>"/>
				<input type="hidden" id="hidden_topic_board_idx" name="topicBoardIdx" value="">
				<div id="div_update_topic_board">
					<!-- 상단부 / div:nth-child(1) -->
					<div> 
						<span>게시글 수정</span> 
						<div class="exit fr"></div>
					</div>
					
					<!-- 중앙부 / div:nth-child(2) -->
					<div>
						<!-- div:nth-child(2) > div:nth-child(1) -->
						<div>
			     			<label for="input_board_title_update" class="fl">제목</label><span class="fl">*</span><br/>
			     			<input type="text" name="topic_board_title" id="input_board_title_update" placeholder="게시글 제목을 입력하세요" required />
			     			<span class="text_max_value fr">/ 50</span>
			     			<span class="text_current_value fr">0</span><br/><br/>
						</div>
						<!-- div:nth-child(2) > div:nth-child(2) -->
						<div>
				  			<label for="update_topic_board_box" id="label_board_content_update"  class="fl">게시글 본문</label><span class="fl">*</span><br/>
							<div id="update_topic_board_box">
								<!-- 텍스트 작성하는 공간 -->
								<div id="update_topic_board_space" class="scrollbar" contenteditable="true" data-placeholder="게시글의 본문을 입력하세요" aria-labelledby="label_board_content_update"></div>
							 	<input type="hidden" name="topic_board_content" id="hidden_board_update">
		                    
								<!-- 파일 업로드하는 공간 -->
							 	<div class="upload_file_box">
								 	<div class="upload_file_space" class="scrollbar" >
								 		<div class="upload_file_list">
								 			<div>
								 				<img src=""/>
								 			</div>
								 			<div>download.png</div>
								 			<div>
								 				<div class="ic_delete"></div>
								 			</div>
								 		</div>
								 	</div>
								 	<div class="upload_file_count">
									 	<span class="text_max_value fr"> 30</span>
									 	<span class="text_current_value fr">0 / </span>
								 	</div>
							 	</div>
								
								<!-- mention, file 아이콘 공간 -->
								<div class="ic_mention_file_box">
									<div class="ic_mention fl" data-toggle="tooltip" title="멘션"></div>
										<div class="mention_pop_up">
											<div class="scrollbar">
											<!-- 여기 반복 -->
												<div class="mention_member_list">
													<div>
														<div class="ic_mention"></div>
													</div>
													<div class="member_all">
														<div class="fl">All</div>
														<div class="fl">토픽 모든 멤버</div>
													</div>
												</div>
											
										<%
											for(TopicMemberDto topicMemberDto : listTopicMember) {
										%>
												<div class="mention_member_list" member_idx="<%=topicMemberDto.getMemberIdx()%>">
													<!-- .mention_member_list > div:nth-child(1) -->
													<div>
														<img class="profile_img" src="<%=topicMemberDto.getProfileUrl()%>"/>
														<div class="on_user"></div>
													</div>
													<!-- .mention_member_list > div:nth-child(2) -->
													<div>
														<div class=" profile_name"><%=topicMemberDto.getName()%></div>
														<div class=""><%=topicMemberDto.getDepartment()%> / <%=topicMemberDto.getPosition()%></div>
													</div>
												</div>
										<%
											}
										%>		
											</div>
										</div>
									<label class="ic_file_clip fl" for="upload_file_tboard_update" data-toggle="tooltip" title="파일 업로드"></label>
									<input type="file" id="upload_file_tboard_update" style="clip:rect(0, 0, 0, 0); display:none;"/>
								</div>
								
							</div>	
								<span class="text_max_value fr">/ 5,000</span>
								<span class="text_current_value fr">0</span>
						</div>
					</div>	
						
					<!-- 하단부 / div:nth-child(3)						 -->
					<div>
						<button class="fr" type="submit" id="btn_update_topic_board">수정</button>
						<button class="fr" type="button" id="btn_update_cancel">닫기</button>
					</div>
				</div> <!-- "div_update_topic_board" 닫는 태그 -->
			</form>		
		
			<!---------- 토픽방 내부 하단의 게시글 등록 아이콘 ---------->		
			<div class="ic_write_board_box">
				<div class="ic_edit_info"></div>
			</div>
			
			<!---------- 최신 게시글 보기 아이콘 ---------->		
			<div class="ic_view_current_board_box">
				<div class="ic_view_current_board"></div>
			</div>
			
 			<!---------- 등록된 공지 숨기기---------->		
			<div class="ic_notice_register_box">
				<div class="ic_notice_register"></div>
			</div>		
		 
			<!---------- 등록된 공지 ---------->
			<!-- <div id="div_room_notice_view">
				<div class="notice_header">
					<div class="fl">
						<img src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
					</div>
					<div class="fl">
						<span>이나무</span>
						<span></span>
						<span>2024/05/13 AM 02:27</span>
					</div>
					<div class="ic_more_menu fr"></div>
				</div>
				
				<div class="notice_content">
					<div>
						<span>공지사항</span>
					</div>
					<div>
						파일
					</div>
				</div>
				
				<div class="notice_footer">
					<div class="ic_arrow_bottom fr"></div>
				</div>
				
				-------- 등록된 공지창 - [더보기] --------		
				<div id="div_notice_more_menu">
					<div>
						<div class="ic_notice_hide"></div>
						<span>숨기기</span>
					</div>
					<div>
						<div class="ic_edit_info"></div>
						<span>수정</span>
					</div>
					<div>
						<div class="ic_delete"></div>
						<span>삭제하기</span>
					</div>
				</div>			
				
			</div>		
			
			---------- 등록된 공지창 - [더보기] - [수정] 클릭 시 팝업창 ------
			<div id="div_notice_update" class="border"> 
				상단부 / div:nth-child(1)
				<div>
					<span>공지 수정</span>
					<div class="exit fr"></div>
				</div>
				
				중앙부 / div:nth-child(2)
				<div class="scrollbar">
		  			<label for="write_topic_noti_box">메시지</label>
		  			<div id="update_topic_noti_box">
		  				<textarea name="textarea_noti" id="textarea_noti" placeholder="메시지를 입력해주세요" rows="10" class="scrollbar"></textarea>
						
						파일 업로드하는 공간
					 	<div class="upload_file_box">
						 	<div class="upload_file_space scrollbar" class="scrollbar" >
						 		<div class="upload_file_list">
						 			<div class="ic_file_clip"></div>
						 			<div>download.png</div>
						 			<div>
						 				<div class="ic_delete"></div>
						 			</div>
						 		</div>
						 		<div class="upload_file_list">
						 			<div class="ic_file_clip"></div>
						 			<div>download.png</div>
						 			<div>
						 				<div class="ic_delete"></div>
						 			</div>
						 		</div>
						 		<div class="upload_file_list">
						 			<div class="ic_file_clip"></div>
						 			<div>download.png</div>
						 			<div>
						 				<div class="ic_delete"></div>
						 			</div>
						 		</div>
						 	</div>
						 	<div class="upload_file_count">
							 	<span class="text_max_value fr"> 10</span>
							 	<span class="text_current_value fr">0 / </span>
						 	</div>
					 	</div>
						
						<div class="ic_mention_file_box">
							<label class="ic_file_clip fl" for="upload_file_tnoti"></label>
							<input type="file" id="upload_file_tnoti" multiple style="clip:rect(0, 0, 0, 0); display:none;"/>
						</div>
		  			</div>
				</div>
				
				하단부 / div:nth-child(3)
				<div>
					<button class="fr" type="submit" id="update_noti_content">수정</button>
					<button class="fr close_window" type="button" id="" name="close_window">취소</button>
				</div>
			</div>	div_notice_update 닫는 태그	
		 -->
			<!--------------------------------------- 토픽방 내부 --------------------------------------->		
			<div id="div_content" class="scrollbar">
				
				<!-- 토픽글 리스트 -->		
				<%
					for(TopicBoardDto topicBoardDto : listTopicBoard) {
				%>
				<div class="content_board" topic_board_idx="<%=topicBoardDto.getTopicBoardIdx()%>" writer="<%=topicBoardDto.getMemberIdx()%>">
					<!-- 토픽글 상단 - 프로필 -->
					<div class="div_content_header">
						<div class="div_profile">
							<div class="fl">
								<img src="<%=topicBoardDto.getProfileUrl()%>"/>
							</div>
							<div class="fl">
								<span><%=topicBoardDto.getName()%></span>
								<span><%=topicBoardDto.getState()==null ? "" : " - " + topicBoardDto.getState()%></span>
								<span><%=topicBoardDto.getWriteDate()%></span>
							</div>
							
							<div class="fr">
<%-- 								<span><%=topicBoardDto.getUnreadCnt()%></span> --%>
							</div>
							
						</div>
					</div> <!-- div_content_header 닫는 태그 -->
				
						<!-- 토픽글내용 -->
					<div class="div_content_center">
						<div class="article_title">
							<span><%=topicBoardDto.getTitle()%></span>
						</div>
						<div class="article_contents">
							<span><%=topicBoardDto.getContent()%></span>
						</div>
						
					<%
						TopicBoardFileDto tfDto = tDao.getFileInfoFromTopicBoardIdx(topicBoardDto.getTopicBoardIdx());
						if(tfDto!=null){
							Integer fileIdx = tfDto.getFileIdx();	
							String fileName = tfDto.getFileName();
							int fileTypeIdx = common.getFileTypeIdxFromFileName(tfDto.getFileName());
							
							if(fileIdx != null){
								if(fileTypeIdx==2){	// 이미지일 때
					%>
								<div class="article_file" fileIdx="<%=fileIdx%>">
									<img src="upload/<%=fileName%>" fileTypeIdx="<%=fileTypeIdx%>"/>
								</div>
					<%			} else { %>
								<div class="article_file" fileIdx="<%=fileIdx%>">
									<div class="ic_file_img <%=common.getIconImgFromFileTypeIdx(fileTypeIdx)%> fl" fileTypeIdx="<%=fileTypeIdx%>"></div>
									<span><%=fileName%></span>
								</div>
						
					<% 			} 
							}
						}					
					%>
						
						<!-- 토픽글 [더보기] --> 
						<div class="more_menu_box">
							<div class="ic_more_menu"></div>
						</div>	
						<!-- 토픽글 [더보기] - [내 토픽글 더보기 창] -->
						<div class="div_board_more_menu_mine">
						 	<div>
							 	<div class="ic_notice_register fl"></div>
								<span class="fl">공지등록</span>
						 	</div>
						 	<div>
						 		<div class='fl <%= bDao.isBookmarkTopicBoard(topicBoardDto.getMemberIdx(), topicBoardDto.getTopicBoardIdx()) ? "ic_bookmark_on" : "ic_bookmark_off" %>'>
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
						<!-- 토픽글 [더보기] - [상대 토픽글 더보기 창] --> 
						<div class="div_board_more_menu_other">
						 	<div>
							 	<div class="ic_notice_register fl"></div>
								<span class="fl">공지등록</span>
						 	</div>
						 	<div>
						 		<div class='fl <%= bDao.isBookmarkTopicBoard(memberIdx, topicBoardDto.getTopicBoardIdx()) ? "ic_bookmark_on" : "ic_bookmark_off" %>'>
						 		</div>
								<span class="fl">즐겨찾기</span>
						 	</div>
						 	<div>
						 		<div class="ic_copy fl"></div>
								<span class="fl">복사</span>
						 	</div>
					 	</div> 
					</div> <!-- div_content_center 닫는 태그 -->

					<!-- 댓글 수 --> 
					<div class="div_comment_count" topic_board_idx="<%=topicBoardDto.getTopicBoardIdx()%>" >
						<span>댓글</span>
						<span><%=tDao.getTopicCommentCnt(topicBoardDto.getTopicBoardIdx())%></span>
					</div>
					
					<!-- 댓글 반복되는 부분 -->
					<%
						ArrayList<TopicCommentDto> listTopicComment = tDao.getTopicCommentList(topicBoardDto.getTopicBoardIdx(), teamIdx);
						for(TopicCommentDto topicCommentDto : listTopicComment){
					%>
					<div class="div_comment" topic_comment_idx="<%=topicCommentDto.getTopicCommentIdx()%>" writer="<%=topicCommentDto.getMemberIdx()%>">
						<div class="div_profile">
							<div class="fl">
								<img src="<%=topicCommentDto.getProfileUrl()%>"/>
							</div>
							<div class="fl">
								<span><%=topicCommentDto.getName()%></span>
								<span><%=topicCommentDto.getState()==null ? "" : " - " + topicCommentDto.getState()%></span>
								<span><%=topicCommentDto.getWriteDate()%></span>
							</div>
						</div>
						<div class="comment_item" contenteditable="false">
							<%=topicCommentDto.getComments()%>
						</div>
						<div class="comment_file">
							<%=topicCommentDto.getFileIdx()==null ? "" : topicCommentDto.getFileIdx()%>
							<img src=""/>
						</div>
							<!-- 토픽댓글 [더보기] --> 
							<div class="more_menu_box">
								<div class="ic_more_menu"></div>
							</div>	
							<!-- 토픽댓글 [더보기] - [내 댓글 더보기 창] -->
							<div class="div_comment_more_menu_mine">
							 	<div>
							 		<div class='fl <%= bDao.isBookmarkTopicComment(topicCommentDto.getMemberIdx(), topicCommentDto.getTopicCommentIdx()) ? "ic_bookmark_on" : "ic_bookmark_off" %>'></div>
							 		<div class="ic_show_file fl"></div>
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
							<!-- 토픽댓글 [더보기] - [상대 댓글 더보기 창] --> 
							<div class="div_comment_more_menu_other">
							 	<div>
							 		<div class='fl <%= bDao.isBookmarkTopicComment(topicCommentDto.getMemberIdx(), topicCommentDto.getTopicCommentIdx()) ? "ic_bookmark_on" : "ic_bookmark_off" %>'></div>
									<span class="fl">즐겨찾기</span>
							 	</div>
							 	<div>
							 		<div class="ic_copy fl"></div>
									<span class="fl">복사</span>
							 	</div>
						 	</div> 
					</div><!-- div_comment 닫는 태그 -->
					<%
						}
					%>
				
	<%-- 			<form class="form_write_comment" action="${pageContext.request.contextPath}/jsp/WriteTopicComment.jsp" method="get">	 --%>
					<!-- 댓글 입력창 -->
					<div class="div_comment_box">
						<div class="div_comment_space">
							<!-- 댓글 입력하는 부분 -->
							<div class="div_comment_blank">
								<div class="write_topic_comment_space" contenteditable="true" data-placeholder="댓글을 입력하세요..."></div>
								<input type="hidden" name="topic_board_comment" id="hidden_board_comment">
								<input type="hidden" name="topic_board_idx" value="<%=topicBoardDto.getTopicBoardIdx()%>">
									<!-- 폼 제출 시 특정 데이터를 서버로 전송하는 데 사용. 해당 댓글이 어떤 게시글에 달린 것인지를 나타내는 정보  -->
								<div class="ic_comment_enter"></div>
							</div>
							<!-- 이모티콘, 언급, 파일 -->
							<div class="div_comment_icon">
								<div class="ic_mention fl" data-toggle="tooltip" title="멘션"></div>
									<div class="mention_pop_up">
										<div class="scrollbar">
											<div class="mention_member_list">
												<div>
													<div class="ic_mention"></div>
												</div>
												<div class="member_all">
													<div class="fl">All</div>
													<div class="fl">토픽 모든 멤버</div>
												</div>
											</div>
									<%
										for(TopicMemberDto topicMemberDto : listTopicMember) {
									%>
											<div class="mention_member_list" member_idx="<%=topicMemberDto.getMemberIdx()%>">
												<!-- .mention_member_list > div:nth-child(1) -->
												<div>
													<img class="profile_img" src="<%=topicMemberDto.getProfileUrl()%>"/>
													<div class="on_user"></div>
												</div>
												<!-- .mention_member_list > div:nth-child(2) -->
												<div>
													<div class=" profile_name"><%=topicMemberDto.getName()%></div>
													<div class=""><%=topicMemberDto.getDepartment()%> / <%=topicMemberDto.getPosition()%></div>
												</div>
											</div>
									<%
										}
									%>	
										</div>
									</div>
								<label class="ic_file_clip fl" for="upload_file_tcomment" data-toggle="tooltip" title="파일 업로드"></label>
								<input type="file" id="upload_file_tcomment" name="file_upload" style="clip:rect(0, 0, 0, 0); display:none;"/>
							</div>
							
						</div>
					</div> <!-- div_comment_box 닫는 태그 -->
					
				</div> <!-- content_board 닫는 태그 -->				
				<%
					}		// for(TopicBoardDto topicBoardDto : listTopicBoard) { 의 닫는 중괄호
				%>	
			
			</div> <!-- div_content 닫는 태그 -->		
		
		
			<!--------------------------------------- 토픽방 상단바 팝업창 --------------------------------------->
			<!---------- 토픽방 상단바 - 참여 멤버 팝업창 ---------->	
			<div id="div_title_participants">
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
					
					<!-- 멤버 반복되는 부분 -->					
					<%
						for(TopicMemberDto topicMemberDto : listTopicMember) {
					%>
						<div class="div_member_list" member_idx="<%= topicMemberDto.getMemberIdx() %>">
							<div class="fl">
								<img class="profile_img" src="<%= topicMemberDto.getProfileUrl() %>"/>
							</div>
							<div class="fl">
								<div class="profile_name"><%= topicMemberDto.getName() %></div>
							</div>
							<div class="ic_exit_chatroom fr"></div>
							<div class="fr">
								<div class='profile_power 
								<% if( (topicMemberDto.getPower()).equals("정회원")){ %>
									<%= " power_orange" %>
								<% } else if ( (topicMemberDto.getPower()).equals("소유자")){ %>
									<%= " power_blue" %>
								<% } else { %>
									<%= " power_pupple" %>
								<% } %>							
								'> <%= topicMemberDto.getPower() %>
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
			</div>	<!-- div_title_participants 닫는 태그 -->
			
			<!---------- 토픽방 상단바 - 더보기 팝업창 ---------->		
			<div id="div_topicroom_more_menu">
				<div>
					<div class="ic_notice_register fl"></div>
					<span class="fl">공지등록</span>
				</div>
				<div>
					<div class="ic_show_file fl"></div>
					<span class="fl">파일 보기</span>
				</div>
				<div>
					<div class="ic_edit_info fl"></div>
					<span class="fl">정보 변경하기</span>
				</div>
				<div>
					<div class="ic_delete fl"></div>
					<span class="fl">토픽 삭제하기</span>
				</div>
				<div>
					<div class="ic_exit_chatroom fl"></div>
					<span class="fl">토픽 나가기</span>
				</div>
			</div> <!-- div_topicroom_more_menu 닫는 태그 -->			
		
			<!-- side2에 아무것도 없을 경우 뜨도록  -->		
			<div class="div_side2_container"></div>				
		
		</div> <!-- div_side2 닫는 태그 -->
		<div style="clear:both;"></div>	
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
		
			<!-- <div class="bookmark_text_item">
				<div class="bookmark_item_top">
					<div class="fl">먹는 것에 진심인 사람들</div> 방 이름
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
					<div class="fl">먹는 것에 진심인 사람들</div> 방 이름
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
					<div class="fl">먹는 것에 진심인 사람들</div> 방 이름
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
					<div class="file_img"><img src=""></div>파일이미지
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
					<div class="file_img"><img src=""></div>파일이미지
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
					<div class="file_img"><img src=""></div>파일이미지
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
					<div class="file_img"><img src=""></div>파일이미지
					<div>
						<span class="profile_name">테스트 메모.txt</span>
						<span>원혜경</span>
						<span>2024/05/08 PM 09:02</span>
					</div>
				</div>
			</div> -->
			
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
	
	<!-- 토픽 삭제 시 알림창 -->
    <form action="Controller" id="deleteTopicForm" method="post">
    	<input type="hidden" name="command" value="delete_topic"/>
	    <input type="hidden" name="topicIdx" value="<%=topicIdx%>"/>
	    <input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
	 	<div id="delete_topic_pop_up" class="notification_pop_up">
		    <div>토픽을 정말 삭제하시겠습니까?</div>
		    <div>모든 메시지와 파일이 삭제되며 복구할 수 없습니다</div>
		    <div>
	            <button type="button" class="btn btn_cancel">취소</button>
	            <button type="submit" class="btn btn_danger">확인</button>
		    </div>
		</div> 
   	</form>

	<!-- 토픽 멤버 내보내기 시 알림창 -->
    <form action="Controller" id="removeTopicMemberForm" method="post">
    	<input type="hidden" name="command" value="remove_topic_member"/>
	    <input type="hidden" name="topicIdx" value="<%=topicIdx%>"/>
	    <input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
	    <input type="hidden" name="removeMemberIdx" value=""/>
		<div id="remove_topicMember_pop_up" class="notification_pop_up">
			<div>정말 이 멤버를 내보내시겠습니까?</div>
			<div id="remove_member_info">
				<img class="profile_img" src=""/> <!-- 내보낼 멤버 이미지 -->
				<span></span>
			</div>
			<div>
				<button type="button" class="btn_cancel">취소</button>
				<button type="submit" class="btn_ok">확인</button>
			</div>
		</div>
	</form>	
	
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
	
	<!-- 토픽글 삭제 시 알림창 -->
	<form id="delete_pop_up" action="Controller" method="post">
		<input type="hidden" name="command" value="delete_topic_board"/>
		<input type="hidden" name="teamIdx" value="<%=teamIdx%>"/>
		<input type="hidden" name="topicIdx" value="<%=topicIdx%>"/>
		<div id="delete_topic_board_pop_up" class="notification_pop_up">
			<div>이 토픽글을 삭제하시겠습니까?</div>
			<div>삭제하시면 복구할 수 없습니다.</div>
			<div>
				<button type="button" class="btn btn_cancel">취소</button>
				<button type="submit" class="btn btn_danger">확인</button>
			</div>
			<input type="hidden" name="topicBoardIdx" value=""/>
		</div>
	</form>
	
	<!-- 토픽댓글 삭제 시 알림창 -->
	<div id="delete_topic_comment_pop_up" class="notification_pop_up">
		<div>이 댓글을 삭제하시겠습니까?</div>
		<div>삭제하시면 복구할 수 없습니다.</div>
		<div>
			<button type="button" class="btn btn_cancel">취소</button>
			<button type="button" class="btn btn_danger">확인</button>
		</div>
	</div>

</body>
</html>