<%@page import="dto.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    	int loginMemberIdx = 2; 
        int teamIdx = 2; 
		int memberIdxFrom = loginMemberIdx;
    	//	int memberIdx = (Integer)session.getAttribute("loginMemberIdx");
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
    %>



<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>Insert title here</title>
	<link rel="stylesheet" href="css/OrganizationalChart.css"/>
	<link rel="stylesheet" href="css/profile_1.css"/>
	<link rel="stylesheet" href="css/profile_2.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/OrganizationalChart.js"></script>
	<script>
		let login_member_idx = <%=loginMemberIdx%>;
	</script>
</head>
<body>
	<div id="div_transparent_filter"></div>
	<div id="div_header">
		<div id="div_logo" class="fl"><img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628"/></div>
		<div id="div_select_team" class="fl">
			<span>7팀</span>
			<img src="img/img_icon_v.png"/>
		</div>
		<div id="div_menu3" class="fr"><img src="img/img_icon_menu3.png"/></div>
		<div id="div_menu4" class="fr">
			<img src="img/img_icon_menu4.png"/>
			<img src="img/img_icon_v.png"/>
		</div>
		<div id="div_help" class="fr">
			<img src="img/img_icon_help.png"/>
			<div class="on"></div>
		</div>
		<div id="div_search" class="fr"><img src="img/img_icon_search.png"/></div>
		<div id="div_hierarchy" class="fr"><img src="img/img_icon_hierarchy_green.png"/></div>
		<div id="div_notice" class="fr"><img src="img/img_icon_notice.png"/></div>
	</div>
	<div id="div_side1" class="fl">
		<div>
			<img src="https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80"/>
			<div>김민지</div>
		</div>
		<div>
			<div>
				<img src="img/img_icon_search_bg_grey.png"/>
				<span>대화방 검색</span>
				<span>Ctrl+J</span>
			</div>
		</div>
		<div>
			<div><img src="img/img_icon_v.png"/></div>
			<div>토픽</div>
			<div>8</div>
			<div><img src="img/img_icon_plus.png"/></div>
		</div>
		<div>이름 순</div>
		<div>
			<div><img src="img/img_icon_v.png"/></div>
			<div>채팅</div>
			<div><img src="img/img_icon_plus.png"/></div>
		</div>
		<div>김현지</div>
		<div>앱</div>
		<div>캘린더</div>
	</div>
	
<!---------------- 부서 -------------------->
<div class="re">
	<div id="div_side2" class="fr">
		<div>
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
					<% if (omDto.getMemberIdx() != loginMemberIdx ) { %> 
					
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
				<% if (omDto.getMemberIdx() != loginMemberIdx ) { %>
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
		<% if (mpDto.getMemberIdx() != loginMemberIdx ) { %> 
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
		<% if (mpDto.getMemberIdx() == loginMemberIdx ) { %> 
			<div id="loginmember_profile_container" class="ab" member_idx="<%=loginMemberIdx%>" style="display: none;">
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
	
	
	
	<div style="clear:both;"></div>


</body>
</html>