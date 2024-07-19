<%@page import="dao.ProjectDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//int colorIdx = 1; // 테스트
    //ProjectDao projectDao = new ProjectDao();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>namoo_addproject</title>
<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
<link rel="stylesheet" href="css/addproject.css"/>
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
		
		
	</div>
	
	
	
<form action="NewProjectInsert.jsp">
	<div class="parents">
		<div id="addproject" >
			<h2>프로젝트 만들기</h2>
			<div id="project_name"><input type="text" name="project_name" placeholder="제목을 입력하세요"></div>
			<div id="project_color"><p>프로젝트 색상 </p></div>
			<div id="project_colorselect" class="color_select_container fl">

				<% for(int i=0; i<=11; i++) { %>
						<div><div id="blue" class="color_select<%=i+1 %>"></div><input class="ab radio" type="radio" name="color_select" value="<%= i + 1 %>"/></div>
				<%} %>
					
				
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
	<div style="clear:both;"></div>



	
</body>
</html>