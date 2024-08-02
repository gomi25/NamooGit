<%@page import="dto.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%



	// 즐겨찾기 프로젝트
	/*int memberIdxFrom = 2; // 테스트
	int teamIdx = 1; // 테스트
	ProjectDao projectDao = new ProjectDao();
	ArrayList<ProjectBookmarkDto> projectBookmarkDto = projectDao.getProjectBookmark(memberIdxFrom, teamIdx);*/
	
	
	// 참여중 프로젝트
	int teamIdx = 1; // 테스트
    int memberIdxFrom = 2; // 테스트
 	
    ProjectDao projectDao = new ProjectDao();
    ArrayList<ProjectNoBookmarkDto> projectNoBookmarkDto = projectDao.getAllProjectNoBookmark(memberIdxFrom, teamIdx);
    // 즐겨찾기된 프로젝트
    ArrayList<ProjectBookmarkDto> projectBookmarkDto = projectDao.getProjectBookmark(teamIdx, memberIdxFrom);
    
    // 프로젝트 멤버수
    //int projectIdx = 1; //테스트
    //int projectMember = projectDao.checkByProjectMemberCount(projectIdx);
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="css/Project.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		let member_idx_from = <%=memberIdxFrom%>;
	</script>
	<script src="${pageContext.request.contextPath}/js/project.js"></script>
</head>
<body>
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
		<div id="div_hierarchy" class="fr"><img src="img/img_icon_hierarchy.png"/></div>
		<div id="div_notice" class="fr"><img src="img/img_icon_notice.png"/></div>
	</div>
	<div id="div_side1" class="fl">
		
<!-------------------- 프로젝트 -------------------->		
	</div>
	<div id="div_side2" class="border fr" >
		<div id="side2_header">
			<div>내 프로젝트</div>
			<div></div>
		</div>
		<div id="side2_body">
<!-------------------- 프로젝트(즐겨찾기)----------------------->		
			<div id="bookmark">
				<div>즐겨찾기</div>
				<% for (ProjectBookmarkDto pbDto : projectBookmarkDto) { %>
				<div id="project_box" class="fl" project_idx="<%=pbDto.getProjectIdx()%>">
					<div id="projectbox_color" style="background:<%= pbDto.getColor() %>;"></div>
					<div><img class="yellow_star" src="https://flow.team/flow-renewal/assets/images/icons/icon_star_on.png?v=ca949083bd3e2d74e7125167485cff818959483a" /></div>
					<div id="projectbox_name"><%= pbDto.getProjectName() %></div>
					<div id="projectbox_people"><img src="img/project_people.png"> <span><%= pbDto.getMemberCount() %></span></div>
				</div>
				<% } %>
			</div>
<!-------------------- 프로젝트(참여중)----------------------->	
			<div id="participating">
				<div>참여중</div>
				<% for (ProjectNoBookmarkDto pDto : projectNoBookmarkDto) {	%>
				<div id="project_box" class="fl" project_idx="<%=pDto.getProjectIdx()%>">
					<div id="projectbox_color" style="background:<%= pDto.getColor() %>;"></div>
					<div class="star"></div>
					<div id="projectbox_name"><%= pDto.getProjectName() %></div>
					<div id="projectbox_people"><img src="img/project_people.png"> <span><%= pDto.getProjectMemberCount() %></span></div>
				</div>
				<% } %>
		</div>
		
		
	</div>

	
	
	<div style="clear:both;"></div>
</body>
</html>