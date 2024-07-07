<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.*"%>
<%@ page import="dto.*"%>
<%@ page import="java.util.ArrayList"%>

<%

	int memberIdx = 2;     // 테스트
	int teamIdx = 1;       // 테스트
	int topicIdx = 1;	   // 테스트
	
	int cntUnreadTotal = 0; // 토픽방에서 안 읽은 메시지 전체 개수 
	int cntOfTopic = 0;     // 토픽방 개수
	int cntOfTopicMember = 0;
	
//============================토픽방목록, 채팅방목록 조회 테스트==============================
	SideDao sDao = new SideDao();
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
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Namoo_frame</title>
	<link rel="stylesheet" href="css/Namoo_frame.css"/>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="js/Namoo_frame.js"></script>
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
<!-- 도움말 필요없으면 지우기 -->
<!-- 			<div class="fr" data-toggle="tooltip" title="도움말"> -->
<!-- 				<div class="ic_header_question"></div> -->
<!-- 				<div class="on"></div> -->
<!-- 			</div> -->
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
				<div>할일</div>
				<div>파일</div>
				<div>즐겨찾기</div>
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
		
	
	</div>
	
	<div id="div_total_side">
	
	<!--------------------------------------- div_side1 --------------------------------------->	
		<div id="div_side1" class="fl">
		<!---------- 프로필 ---------->		
			<div> <!-- (1) 프로필 -->
				<div id="div_profile_box">
					<img src="https://jandi-box.com/files-profile/b615e8e5e21064bc8a32231d8628a136?size=80"/>
					<div>혜교미</div>
				</div>
			</div>
			
		<!---------- 대화방 검색 ---------->			
			<div id="div_room_search" data-toggle="tooltip" title="토픽 또는 채팅방으로 바로 이동하기"> <!-- (2) 대화방 검색 -->
				<div>
					<!-- <img src="img/img_icon_search_bg_grey.png"/> -->
					<div class="ic_search fl"></div>
					<span>대화방 검색</span>
					<span>Ctrl+J</span>
				</div>
			</div>
			<!---------- 대화방 검색 클릭 시 ---------->	
				<div id="search_all_room">
					<!-- 상단부 / div:nth-child(1) -->
					<div>
						<span class="fl">대화방 검색</span>
						<div class="exit fr"></div>				
					</div>
					
					<!-- 중앙부1 / div:nth-child(2) -->
					<div>
						<input type="text" name="name" id="input_name" placeholder="토픽 또는 채팅방으로 바로 이동하기">
					</div>
					
					<!-- 중앙부2 / div:nth-child(3) -->
					<div>
						<div>
							<!-- 참여하지 않은 토픽 -->
							<div class="other_topicroom_item">
								<div class="ic_board fl"></div>
								<div>
									<span class="fl">테스트2</span>
								</div>
								<div class="fr">
									<span class="fr">참여하지 않은 토픽</span>
								</div>
							</div>
							<!-- 참여하는 토픽 -->
							<div class="my_topicroom_item">
								<div class="ic_board fl"></div>
								<div>
									<span class="fl">공지사항</span>
								</div>
							</div>
							<!-- 단체 채팅방 -->
							<div class="group_chatroom_item">
								<div class="fl">
									<img class="fl" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
									<img class="fl" src="https://jandi-box.com/files-profile/9836702f0b26c245a3bb3516afb6452d?size=80"/>
									<img class="fl" src="https://jandi-box.com/files-profile/049da7c0fc758a63b6df1f4d9d8bb454?size=80"/>
	 								<img class="fl" src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/>
								</div>
								<div class="fl">
									<div>
										<span>프로젝트 채팅방</span>
									</div>
									<div>
										<span>업무 관련 채팅방으로 잡담금지</span>
									</div>
								</div>
							</div>
							<!-- 개인 채팅방1 -->
							<div class="one_chatroom_item">
								<div class="fl">
									<img class="fl" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
								</div>
								<div class="fl">
									<div>
										<span>이나무</span>
										<span>대리</span>
									</div>
									<div>
										<span>7팀</span>
									</div>
								</div>
							</div>
							<!-- 개인 채팅방2 -->
							<div class="one_chatroom_item">
								<div class="fl">
									<img class="fl" src="https://jandi-box.com/assets/ic-profile.png?size=80"/>
								</div>
								<div class="fl">
									<div>
										<span>김현지</span>
										<span>이사</span>
									</div>
									<div>
										<span>7팀</span>
									</div>
								</div>
							</div>
						</div>
					</div>
	
					<!-- 하단부 / div:nth-child(4) -->
					<div>
						<a href="#">
							<button class="fl"> 
								<div class="ic_board fl"></div>
								참여하지 않은 토픽 보기
							</button>
						</a>
						<a href="#">
							<button class="fl"> 
								<div class="ic_plus fl"></div>
								새 토픽 생성하기
							</button>
						</a>
					</div>
				
				</div>		
			
		<!---------- 토픽방 목록 / header ---------->			
			<div id="div_topicroom_list_header"> <!-- (3) 토픽-->
				<div></div>
				<div>토픽</div>
				<div><%=cntUnreadTotal %></div>
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
			<div id="div_topicroom_list_body"> <!-- (4) -->
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
				<div><%= cntChatTotalUnread %></div>
				<div class="ic_plus"></div>
			</div>
			<div id="div_chatroom_list_body"> <!-- (6) -->
		
				<% for(ChatroomDto chatDto : listChatroom) { %>
					<div class="topic_item">
						<div class='<%=(chatDto.isBookmarkYn() ? "ic_bookmark_on" : "ic_bookmark_off") %> fl'></div>
						<div class="fl">
<%-- 						<% { // 반복문을 돌려 : ProfileUrlColorDto의 리스트에 대해서. %> --%>
	 						<img class="fl" src="https://jandi-box.com/files-profile/444fd3d25ade24bc2ec6480e11949dfa?size=80"/>
		 					<img class="fl" src="https://jandi-box.com/files-profile/9836702f0b26c245a3bb3516afb6452d?size=80"/>
							<img class="fl" src="https://jandi-box.com/files-profile/049da7c0fc758a63b6df1f4d9d8bb454?size=80"/>
							<img class="fl" src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/> 
<%-- 						<% } %> --%>
						</div>
						<span><%=chatDto.getChatroomName() %></span>
						<div class='<%= (chatDto.getUnread()>=1 ? "div_unread" : "" ) %>'>
							<%= chatDto.getUnread() >=1 ? chatDto.getUnread() : "" %>
						</div>
						<div class="exit"></div>
					</div>
				<% } %>
			</div>
			
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
						<button class="fr" type="submit" id="new_topic_content">생성하기</button>
						<button class="fr" id="">닫기</button>
					</div>
					
				</div>
			</form>
			
			<!---------- 토픽 '+버튼' -> 참여 가능한 토픽 보기 ---------->	
			<form>
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
						<a href="#">
							<button> + 새 토픽 생성</button>
						</a>
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
				
			</form>
			
		
		</div> <!-- div_side1의 div닫는태그 -->
		
		
		<div id="div_side2" class="fr">
			<div class="div_side2_container"></div>
		</div>
		
		<div style="clear:both;"></div>
		
	</div><!-- div_total_side 닫는 태그 -->	


<!--------------------------------------- 기타 팝업창 --------------------------------------->	
	<!-- 투명판 -->
	<div id="div_transparent_filter"></div>
	<!-- 회색판 -->
	<div id="div_grey_filter"></div>
	
</body>
</html>