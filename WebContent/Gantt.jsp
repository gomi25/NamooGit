<%@page import="dto.*"%>
<%@page import="dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int workIdx = 3; // 테스트
	GanttWorkDao ganttWorkDao = new GanttWorkDao();
	ArrayList<GanttWorkWriterDto> ganttWorkWriterDto = ganttWorkDao.getGanttWorkWriter(workIdx);

	// 가정 : 파라미터 project_idx에 적당한 값(예를 들어 1)이 있을 것. -------- 이 문장은 조만간 삭제하세요.
	int projectIdx = 1;
	try {
		projectIdx = Integer.parseInt(request.getParameter("project_idx"));
	} catch(NumberFormatException e) { }
	
	GanttGroupDao ggDao = new GanttGroupDao();
	GanttWorkDao gwDao = new GanttWorkDao();
	GanttSubworkDao gsDao = new GanttSubworkDao();
	ArrayList<GanttGroupDto> listGanttGroup = ggDao.getGanttGroupName1(projectIdx);
	
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>namoo</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Gantt.css"/> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Member_select.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Addwork.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Prioritize.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/State.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Gantt_work.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Member_1_select.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/GanttWork.js"></script>
	<script src="${pageContext.request.contextPath}/js/AddWork.js"></script>

	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
	
	<script>
		let tasks = {
			"data":[
				<% for(GanttGroupDto dto : listGanttGroup) { %>
					{"id":<%=dto.getGanttgroupIdx() %>, "text":"<%=dto.getGanttgroupName()%>", "start_date":"28-05-2024", "progress": 0.6, "open": true},
					<%
						ArrayList<GanttWorkInformationDto> listGanttWorkInformation = gwDao.getGanttWorkInformation(dto.getGanttgroupIdx());
						for(GanttWorkInformationDto dto2 : listGanttWorkInformation) {
							//System.out.println("dto.getGanttgroupIdx() = " + dto.getGanttgroupIdx());
							//System.out.println("dto2.getStartDate() = " + dto2.getStartDate());
							//System.out.println("dto2.getDuration() = " + dto2.getDuration());
							//System.out.println("dto2.getWorkIdx() = " + dto2.getWorkIdx());
							String stWorkState = "";
								switch(dto2.getWorkState()) {
								case "요청": stWorkState="<div class='box_request gantt_box_state'><div class='state_font'>요청</div></div>"; break;
								case "진행": stWorkState="<div class='box_progress gantt_box_state'><div class='state_font'>진행</div></div>"; break;
								case "피드백": stWorkState="<div class='box_feedback gantt_box_state'><div class='state_font'>피드백</div></div>"; break;
								case "완료": stWorkState="<div class='box_finish gantt_box_state'><div class='state_font'>완료</div></div>"; break;
								case "보류": stWorkState="<div class='box_defer gantt_box_state'><div class='state_font'>보류</div></div>"; break;
								}
					%>
					
					{"id":"<%=dto.getGanttgroupIdx()%>_<%=dto2.getWorkIdx()%>", 
						 "text":"<%=dto2.getWorkName()%>", 
						 "in_charge":"<%=(dto2.getName()==null ? "-" : dto2.getName())%>", 
						 "status":"<%=stWorkState%>", 
							<% if(dto2.getStartDate()!=null) { %>
							"start_date":"<%=dto2.getStartDate()%>", 
							<% } %>
						"duration":<%=dto2.getDuration()%>, 
						"parent":<%=dto.getGanttgroupIdx()%>, 
						"progress": 1 },
						
					<%
							ArrayList<GanttWorkInformationDto> listGanttSubworkInformation
										= gsDao.getGanttSubworkInformation(dto2.getWorkIdx());
							for(GanttWorkInformationDto dto3 : listGanttSubworkInformation) {
								//System.out.println(dto3.getWorkState());
								String strWorkState = "";
								switch(dto3.getWorkState()) {
								case "요청": strWorkState="<div class='box_request gantt_box_state'><div class='state_font'>요청</div></div>"; break;
								case "진행": strWorkState="<div class='box_progress gantt_box_state'><div class='state_font'>진행</div></div>"; break;
								case "피드백": strWorkState="<div class='box_feedback gantt_box_state'><div class='state_font'>피드백</div></div>"; break;
								case "완료": strWorkState="<div class='box_finish gantt_box_state'><div class='state_font'>완료</div></div>"; break;
								case "보류": strWorkState="<div class='box_defer gantt_box_state'><div class='state_font'>보류</div></div>"; break;
								
								}
					%>
						{"id":"<%=dto.getGanttgroupIdx()%>_<%=dto2.getWorkIdx()%>_<%=dto3.getWorkIdx()%>", 
							 "text":"<%=dto3.getWorkName()%>", 
							 "in_charge":"<%=(dto3.getName()==null ? "-" : dto3.getName())%>", 
							 "status":"<%=strWorkState%>", 
							 	<% if(dto2.getStartDate()!=null) { %>
							 	"start_date":"<%=dto3.getStartDate()%>",
							 	<% } %>
							 "parent":"<%=dto.getGanttgroupIdx()%>_<%=dto2.getWorkIdx()%>", 
							 "duration":"<%=dto3.getDuration()%>"},
					<%
							}
						} 	
					%>
				<% } %>
				
				/* {"id":11, "text":"Project #1", "start_date":"28-05-2024", "duration":"10", "progress": 0.6, "open": true},
				
				{"id":12, "text":"Task #1", "start_date":"03-06-2024", "duration":"5", "parent":"11", "progress": 1},
				{"id":13, "text":"Task #2", "start_date":"02-06-2024", "duration":"7", "parent":"11", "progress": 0.5, "open": true},

				{"id":17, "text":"Task #2.1", "start_date":"03-06-2024", "duration":"2", "parent":"13", "progress": 1},
				{"id":18, "text":"Task #2.2", "start_date":"06-06-2024", "duration":"3", "parent":"13", "progress": 0.8},
				{"id":19, "text":"Task #2.3", "start_date":"10-06-2024", "duration":"4", "parent":"13", "progress": 0.2},
				{"id":20, "text":"Task #2.4", "start_date":"10-06-2024", "duration":"4", "parent":"13", "progress": 0},

				{"id":101, "text":"업무2", "in_charge":"김현지", "status":"<span work_idx='3' class='r'>미완료YG</span>", "start_date":"20-06-2024", "duration":"2", "open":true},
				{"id":102, "text":"업무", "in_charge":"김현지", "status":"<span work_idx='4' class='r'>미완료YG</span>", "start_date":"19-06-2024", "duration":"6", "open":true},
				{"id":103, "text":"하위업무", "in_charge":"김현지", "status":"<div class='box_request box_state'>요청</div>", "start_date":"19-06-2024", "parent":"102", "duration":"2"}, */
			]
		};
		console.log(tasks.data);
	</script>
	<script src="${pageContext.request.contextPath}/js/Gantt.js"></script>
	<script src="gantt/dhtmlxgantt.js?v=8.0.9"></script>
	<link rel="stylesheet" href="gantt/dhtmlxgantt.css?v=8.0.9">
	<style>
		.gantt_task_progress {
			background-color : #8ccf97; /*rgb(41, 156, 180);*/
		}
		.gantt_task_line {
			/*border: 1px solid red;*/
			border-radius: 5px;
		}
		.r {
			font-weight: 900;
			background-color: #a56d6d;			
			color: white;
			padding: 5px;
			border-radius: 30px;
			cursor: pointer;
		}
		.box_request {
			background: #00b2ff;
		}
		.box_progress {
			background: #00B01C;
		}
		.box_feedback {
			background: #FD7900;
		}
		.box_finish {
			background: #402A9D;
		}
		.box_defer {
			background: #777777;
		}
		.state_font {
			margin-top: -8px;
			color: white;
		}
		.gantt_box_state {
		    width: 64px;
		    height: 26px;
		    border-radius: 15px;
		    margin-top: 4px;
		    border: none;
		    outline: none;
		    font-size: 12px;
		    font-weight: 600;
		    color: white;
		    text-align: center;
		    padding-top: 3px;
		}
	</style>
	<script>
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
		<div id="div_hierarchy" class="fr"><img src="img/img_icon_hierarchy.png"/></div>
		<div id="div_notice" class="fr"><img src="img/img_icon_notice.png"/></div>
	</div>
<!-------------------- 왼쪽부분 ---------------------------->
	<div id="div_side1" class="fl" >
	
<!-------------------- 업무구분 ---------------------------->

		<div class="gantt_left_name">업무 구분</div>

		<div class="gantt_left">
			<input type="radio" name="업무구분" value="내업무"/> 
			<span>내 업무</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="업무구분" value="전체"/> 
			<span>전체</span>
		</div>
	
<!------------------- 상태 --------------------------->
		<div class="gantt_left_name">상태</div>
		<div class="gantt_left">
			<input class="fl state_checkbox" type="checkbox" name="요청" value="요청"/> 
			<div class="fl request" id="gantt_left_state" ></div>
			<span>요청</span>
		</div>
		<div class="gantt_left">
			<input class="fl state_checkbox" type="checkbox" name="진행" value="진행"/> 
			<div class="fl progress" id="gantt_left_state" ></div>
			<span>진행</span>
		</div>
		<div class="gantt_left">
			<input class="fl state_checkbox" type="checkbox" name="피드백" value="피드백"/> 
			<div class="fl feedback" id="gantt_left_state" ></div>
			<span>피드백</span>
		</div>
		<div class="gantt_left">
			<input class="fl state_checkbox" type="checkbox" name="완료" value="완료"/> 
			<div class="fl finish" id="gantt_left_state" ></div>
			<span>완료</span>
		</div>
		<div class="gantt_left">
			<input class="fl state_checkbox" type="checkbox" name="보류" value="보류"/> 
			<div class="fl defer" id="gantt_left_state" ></div>
			<span>보류</span>
		</div>
		
<!------------------------- 우선순위 ---------------------------->
		<div class="gantt_left_name">우선순위</div>
		<div class="gantt_left">
			<input class="fl priority_checkbox" type="checkbox" name="긴급" value="긴급"/> 
			<div class="fl margin"><img class="priority" src="img/emergency.png"/></div>
			<span class="margin">긴급</span>
		</div>
		<div class="gantt_left">
			<input class="fl priority_checkbox" type="checkbox" name="높음" value="높음"/> 
			<div class="fl margin"><img class="priority" src="img/hight.png"/></div>
			<span>높음</span>
		</div>
		<div class="gantt_left">
			<input class="fl priority_checkbox" type="checkbox" name="보통" value="보통"/> 
			<div class="fl margin"><img class="priority" src="img/normal.png"/></div>
			<span>보통</span>
		</div>
		<div class="gantt_left">
			<input class="fl priority_checkbox" type="checkbox" name="낮음" value="낮음"/> 
			<div class="fl margin"><img class="priority" src="img/low.png"/></div>
			<span>낮음</span>
		</div>
		<div class="gantt_left">
			<input class="fl priority_checkbox" type="checkbox" name="낮음" value="낮음"/> 
			<span class="priority_none">없음</span>
		</div>
		
<!------------------------- 시작일 ---------------------------->		
		<div class="gantt_left_name">시작일</div>
		<div class="gantt_left">
			<input type="radio" name="시작일" value="전체"/> 
			<span>전체</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="시작일" value="오늘"/> 
			<span>오늘</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="시작일" value="이번 주"/> 
			<span>이번 주</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="시작일" value="이번 달"/> 
			<span>이번 달</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="시작일" value="미정"/> 
			<span>미정</span>
		</div>
<!------------------------- 마감일 ---------------------------->		
	<div class="gantt_left_name">마감일 </div>
		<div class="gantt_left">
			<input type="radio" name="마감일" value="전체"/> 
			<span>전체</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="마감일" value="지연"/> 
			<span>지연</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="마감일" value="오늘"/> 
			<span>오늘</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="마감일" value="이번 주"/> 
			<span>이번 주</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="마감일" value="이번 달"/> 
			<span>이번 달</span>
		</div>
		<div class="gantt_left">
			<input type="radio" name="마감일" value="미정"/> 
			<span>미정</span>
		</div>
	</div>

	<div id="div_gantt_side2" class="fr">
<!------------------------- header_projectname ---------------------------->	
		<div id="header_projectname">
			<div class="fl"><div id="header_color" ></div></div>
			<div class="fl"><img id="header_bookmark" src="https://flow.team/flow-renewal/assets/images/icons/icon_star.png?v=16689325f0dc5bb43f108043843b125fa3ef3b0e"/></div>
			<div class="fl"><img id="project_name" src="img/project_name.png"/></div>
			<div class="fl"><span>namoo</span></div>
			<div id="invite" class="fr"><img src="img/gantt_invite.png"><i>초대하기</i></div>
		</div>
<!------------------------- header_projectsearch ---------------------------->		
		<div id="projectsearch" class="re">
			<div id="search_parents" class="fl re"><input id="work_search" type="search" placeholder="업무명을 검색하세요" ></div>
			<div class="ab"><img id="work_search_icon" src="img/돋보기.png"/></div>
			<div id="option_button" class="ab"><div class="option_button">| 옵션</div></div>
			<div class="fr">
				<select id="addwork" name="addwork" >
					<option value="업무 추가">업무 추가</option>
					<option value="그룹 추가">그룹 추가</option>
				</select>
			</div>
			<div class="fr">
				<select id="select_day" name="area" >
					<option value="일">일</option>
					<option value="주">주</option>
					<option value="월">월</option>
					<option value="분기">분기</option>
				</select>
			</div>
			<div id="today_box" class="fr"><p>오늘</p></div>
			<div class="fr open_checkbox"><input type="checkbox"><div class="fr open">모두 펼치기</div></div>
			<!------------------------- 옵션창 ---------------------------->	
			<div id="option" class="ab">
				<div><span class="option_name">옵션</span></div>
				<table>
					<tr>
						<td class="option_input_name">작성자</td>
						<td><input class="option_input" type="search" placeholder="작성자명 입력(여러명 입력시,콤마로 구분)"></td>
					</tr>
					<tr>
						<td class="option_input_name">담당자</td>
						<td><input class="option_input" type="search" placeholder="담당자명 입력(여러명 입력시,콤마로 구분)"></td>
					</tr>
				</table>
				<div>
					<div id="reset" class="fl">
						<img id="reset_button_img" src="https://flow.team/flow-renewal/assets/images/icons/icon_reset.png?v=07104afe92536dd8a30b512c8df908caff2438d1"/>
						<button id="reset_button" type="button"> 초기화</button>
						
					</div>
					<div class="fr">
						<button class="option_button_cancel">취소</button>
						<button class="option_button_search">검색</button>
					</div>
				</div>
			</div>
			
		<!-- 업무 추가 -->	
			<div id="add_work_div_transparent_filter" ></div>		
			<div id="add_work" class="border ab" style="display:none;">
		<!----------------- 업무작성 header부분 ------------------->
				<div id="addwork_header">
					<div class="fl">
						<div class="fl writework">업무작성 </div>
						<div id="addwork_thin" class="fl"></div>
						<div id="addwork_color" class="fl"></div> 
						<div class="addwork_name fl">namoo</div>
					</div>
					<div class="fr">
						<div class="fr"><img class="x_button" src="img/x_button.png"></div>
						<div class="fr"><img class="_button" src="img/addwork_header_thin.png"></div>
					</div>
				</div>
		<!----------------- 입력,선택 부분 ------------------->
				<div id="addwork_body">
					<div id="work_name"><input type="text"  placeholder="제목을 입력하세요."></div>
					<div id="work_state">
						<div class="fl"><img src="https://flow.team/flow-renewal/assets/images/icons/icon-post-status.svg?v=e34bb28e53230b9b3126b8569fa68435513481cf"/></div>
						<div id="request" class="state fl"><div class="state_div addwork_request">요청</div></div>
						<div id="progress" class="state fl"><div class="state_div addwork_progress">진행</div></div>
						<div id="feedback" class="state fl"><div class="state_div addwork_feedback">피드백</div></div>
						<div id="finish" class="state fl"><div class="state_div addwork_finish">완료</div></div>
						<div id="defer" class="state fl"><div class="state_div addwork_defer">보류</div></div>
					</div>
					
					<table class="tr">
						<tr>
							<td><div id="work_member" class="fl"><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-worker.svg?v=380ce20a38f299cd0fe7e55945f32981cb48d5f9"></div></td>
							<td><input id="work_member_input" type="text" placeholder="담당자 추가"></td>
						</tr>
						<tr>
							<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=3262c0db463cb0f5234ebaf889864da403097027"></td>
							<td class="td_txt"><input type="text" id="startDate" placeholder="시작일 추가"></td>
						</tr>
						<tr>
							<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=3262c0db463cb0f5234ebaf889864da403097027"></td>
							<td class="td_txt"><input type="text" id="endDate" placeholder="마감일 추가"></td>
						</tr>
						<tr class="re">
							<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-rank.svg?v=aa8b193b920acc7458e719bb84e9e29c7329457d"></td>
							<td class="td_txt add_prioritize">우선순위 추가</td>
							<td class="add_low"><div class="fl add_low"><img class="fl add_img" src="img/low.png"><div class="add_font fl">낮음</div><img class="add_x_img" src="img/x.png"></div></td>
							<td><div class="add_normal"><img class="fl add_img add_nomal_img"  src="img/normal.png"><div class="add_font fl">보통</div><img class="add_x_img" src="img/x.png"></div></td>
							<td><div class="add_hight"><img class="fl add_img" src="img/hight.png"><div class="add_font fl">높음</div><img class="add_x_img" src="img/x.png"></div></td>
							<td><div class="add_emergency"><img class="fl add_img" src="img/emergency.png"><div class="add_font fl">긴급</div><img class="add_x_img" src="img/x.png"></div></td>
						</tr>
						<tr class="re">
							<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-section.svg?v=7fb1fd5e61a6ba4028ee6bea5164b39dc443546d"></td>
							<td class="td_txt add_group" >그룹 추가</td>
							<td><div class="select_group1" style="display:none;"><div class="fl">그룹1</div><img class="add_x_img" src="img/x.png"></div></td>
							<td><div class="select_nogroup" style="display:none;"><div class="fl">그룹미지정</div><img class="add_x_img" src="img/x.png"></div></td>
		<!----------------- 그룹추가 박스 ------------------->
							<div id="addwork_addgroup" class="ab">
								<div class="groupname_box group1"><div class="addwork_groupname_font">그룹1</div></div>
								<div class="groupname_box nogroup"><div class="addwork_groupname_font">그룹 미지정</div></div>
							</div>
						</tr>
					</table>
		<!----------------- 우선순위 박스 ------------------->
					<div id="addwork_prioritize" class="ab">
						<div class="prioritize_font low"><img class="img fl" src="img/low.png">낮음</div>
						<div class="prioritize_font normal"><img class="img" src="img/normal.png">보통</div>
						<div class="prioritize_font hight"><img class="img" src="img/hight.png">높음</div>
						<div class="prioritize_font emergency"><img class="img" src="img/emergency.png">긴급</div>
					</div>
		<!----------------- 내용 입력 ------------------->
					<textarea rows="6" cols="80" placeholder="내용을 입력하세요."></textarea>
		<!----------------- 하위업무 입력 ------------------->			
					<div id="subwork">하위업무</div>
					<div>
						<div id="addwork_subwork_button">+ 추가</div>
		<!----------------- 하위업무 박스 ------------------->					
						<div id="mini_subwork" class="re"> 
							<div class="fl subwork_state subwork_request"><div>요청</div></div>
							<div style="display: none;" class="fl subwork_state subwork_progress"><div>진행</div></div>
							<div style="display: none;" class="fl subwork_state subwork_feedback"><div>피드백</div></div>
							<div style="display: none;" class="fl subwork_state subwork_finish"><div>완료</div></div>
							<div style="display: none;" class="fl subwork_state subwork_defer"><div>보류</div></div>
							<div><input id="subwork_name" type="text" placeholder="업무명 입력"></div>
							<div class="fr mini_subwork_icon">
								<div class="mini_subwork_img_div subwork_member fr"><img class="mini_subwork_img2" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-worker.svg?v=dab609f4e3114ab334c0216bd41d1a6e27b6503a"></div>
								<div class="mini_subwork_img_div subwork_prioritize2 fr"><img class="mini_subwork_img" src="https://cdn-icons-png.flaticon.com/128/7434/7434629.png"></div>
								<div style="display: none;" class="fr mini_smalllow"><img class="mini_img" src="img/low.png"/></div>
								<div style="display: none;"  class="fr mini_smallnoaml" ><img class="mini_img" src="img/normal.png"/></div>
								<div style="display: none;" class="fr mini_smallhight"><img class="mini_img" src="img/hight.png"/></div>
								<div style="display: none;" class="fr mini_smallemergency"><img class="mini_img" src="img/emergency.png"/></div>
								<div class="mini_subwork_img_div fr"><img class="mini_subwork_img2" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=118ef1e91b7ced5f275a138083d2ddd7cf94773d"></div>
							</div>
						</div>
		<!----------------하위업무 (상태)-------------------->			
						<div id="mini_state" class="ab" style="margin-top: -225px;">
							<div class="box_request box_state">요청</div>
							<div class="box_progress box_state">진행</div>
							<div class="box_feedback box_state">피드백</div>
							<div class="box_finish box_state">완료</div>
							<div class="box_defer box_state">보류</div>
						</div>
		<!----------------- 하위업무 우선순위  ------------------->			
						<div id="prioritize2" class="fr">
							<div class="prioritize_between">
								<div class="fl"><img class="img" src="img/x.png"/></div>
								<span class="font mini_delete">취소</span>
							</div>
							<div class="prioritize_between">
								<div class="fl"><img class="img" src="img/low.png"/></div>
								<span class="font mini_low">낮음</span>
							</div>
							<div class="prioritize_between">
								<div class="fl"><img class="img" src="img/normal.png"/></div>
								<span class="font mini_noaml">보통</span>
							</div>
							<div class="prioritize_between">
								<div class="fl"><img class="img" src="img/hight.png"/></div>
								<span class="font mini_hight">높음</span>
							</div>
							<div class="prioritize_between">
								<div class="fl"><img class="img" src="img/emergency.png"/></div>
								<span class="font mini_emergency">긴급</span>
							</div>
						</div>
					</div>
		<!----------------- 하위업무 담당자  ------------------->
					<div id="member_select" class="ab">
					<!-------------------담당자 검색 부분------------------------>
						<div id="search1">
							<div id="search_parents" class="fl re">
								<input id="search" type="search" placeholder="담당자를 입력하세요" >
								<img class="ab" id="search_icon" src="img/돋보기.png"/>
							</div>
						</div>
					<!-------------------멤버 전체------------------------>			
						<div id="allmember">
					<!-------------------멤버 프로필 부분------------------------>		
							<div id="member"> 
								<div class="fl"><img id="check" src="img/check_1.png"/></div>
								<div class="fl"><img id="profile" src="img/profile_1.png"/></div>
								<div id="name" class="fl">김현지</div>
							</div>
						</div>
					<!-------------------bottom------------------------>		
						<div id="member_bottom">
							<button id="alldelete">전체삭제</button>
							<button id="select">선택</button>
						</div>
					</div>
				</div>
		<!----------------- bottom ------------------->				
				<div id="addwork_bottom">
					<div class="fr"><button id="registration">등록</button></div>
				</div>
			</div>
			
			<!-- 업무 클릭 -->			
			<div id="div_work_side2" class="fr ab" >
		
				<!-- 업무 자세히보기  -->
				<div id="project_work" class="ab" style="display:none;">
					<div id="work">
						<div class="fl">
							<div id="work_color" class="fl"></div> 
							<div class="work_name fl">namoo</div>
						</div>
						<div class="fr">
							<div class="fr"><img class="x_button" src="img/x_button.png"></div>
						</div>
					</div>
				
					<!-- 여기여기 -->
					<div id="writermember">
						<% for (GanttWorkWriterDto gDto : ganttWorkWriterDto) { %>
						<div class="fl"><img src="<%= gDto.getProfile_pic_url()%>" style="width:40px;"></div>
						<div class="writermembernametime">
							<div class="fl writermembername"><%= gDto.getName()%></div>
							<div class="fl writermembertime"><%= gDto.getRegistration_date() %></div>
						</div>
						<% } %>
					</div>
					<div id="body_work_title">
						<div class="body_work_name"><div>업무</div></div>
					</div>
					
					<!------------- 업무 상태 ---------------->
					<div id="work_addwork_body">
						<div id="work_state">
							<div class="fl"><img src="https://flow.team/flow-renewal/assets/images/icons/icon-post-status.svg?v=e34bb28e53230b9b3126b8569fa68435513481cf"/></div>
							<div id="work_request" class="state fl"><div class="state_div work_request">요청</div></div>
							<div id="work_progress" class="state fl"><div class="state_div work_progress">진행</div></div>
							<div id="work_feedback" class="state fl"><div class="state_div work_feedback">피드백</div></div>
							<div id="work_finish" class="state fl"><div class="state_div work_finish">완료</div></div>
							<div id="work_defer" class="state fl"><div class="state_div work_defer">보류</div></div>
						</div>
						<table class="tr">
							<tr>
								<td><div id="work_member" class="fl"><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-worker.svg?v=380ce20a38f299cd0fe7e55945f32981cb48d5f9"></div></td>
								<td class="td_txt"><u>담당자 추가</u></td>
							</tr>
							<tr>
								<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=3262c0db463cb0f5234ebaf889864da403097027"></td>
								<td class="td_txt"><u>시작일 추가</u></td>
							</tr>
							<tr>
								<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=3262c0db463cb0f5234ebaf889864da403097027"></td>
								<td class="td_txt"><u>마감일 추가</u></td>
							</tr>
							<tr class="re">
								<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-rank.svg?v=aa8b193b920acc7458e719bb84e9e29c7329457d"></td>
								<td class="td_txt add_prioritize"><u>우선순위 추가</u></td>
								<td><div class="fl add_low"><img class="fl add_img" src="img/low.png"><div class="add_font fl">낮음</div><img class="add_x_img" src="img/x.png"></div></td>
								<td><div class="add_normal"><img class="fl add_img add_nomal_img"  src="img/normal.png"><div class="add_font fl">보통</div><img class="add_x_img" src="img/x.png"></div></td>
								<td><div class="add_hight"><img class="fl add_img" src="img/hight.png"><div class="add_font fl">높음</div><img class="add_x_img" src="img/x.png"></div></td>
								<td><div class="add_emergency"><img class="fl add_img" src="img/emergency.png"><div class="add_font fl">긴급</div><img class="add_x_img" src="img/x.png"></div></td>
			<!----------------- 우선순위 박스 ------------------->
								<div id="prioritize" class="ab">
									<div class="prioritize_font low"><img class="img fl" src="img/low.png">낮음</div>
									<div class="prioritize_font normal"><img class="img" src="img/normal.png">보통</div>
									<div class="prioritize_font hight"><img class="img" src="img/hight.png">높음</div>
									<div class="prioritize_font emergency"><img class="img" src="img/emergency.png">긴급</div>
								</div>
							</tr>
							<tr class="re">
								<td><img class="addwork_body_img" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-section.svg?v=7fb1fd5e61a6ba4028ee6bea5164b39dc443546d"></td>
								<td class="td_txt add_group"><u>그룹 추가</u></td>
								<td><div class="select_group1" style="display:none;"><div class="fl">그룹1</div><img class="add_x_img" src="img/x.png"></div></td>
								<td><div class="select_nogroup" style="display:none;"><div class="fl">그룹미지정</div><img class="add_x_img" src="img/x.png"></div></td>
			<!----------------- 그룹추가 박스 ------------------->
								<div id="addgroup" class="ab">
									<div class="groupname_box group1"><div class="groupname_font" >그룹</div></div>
									<div class="groupname_box nogroup"><div class="groupname_font">그룹 미지정</div></div>
								</div>
							</tr>
						</table>
						<!----------------- 하위업무 입력 ------------------->			
						<div id="subwork">하위업무</div>
						<div>
							<div id="subwork_button">+ 하위업무 추가</div>
			<!----------------- 하위업무 박스 ------------------->					
							<div id="mini_subwork" class="re"> 
								<div id="subwork_state" class="fl"><button>요청</button></div>
								<div><input id="subwork_name" type="text" placeholder="업무명 입력"></div>
								<div class="fr mini_subwork_icon">
									<div class="mini_subwork_img_div subwork_member fr"><img class="mini_subwork_img2" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-worker.svg?v=dab609f4e3114ab334c0216bd41d1a6e27b6503a"></div>
									<div class="mini_subwork_img_div subwork_prioritize2 fr"><img class="mini_subwork_img" src="https://cdn-icons-png.flaticon.com/128/7434/7434629.png"></div>
									<div class="mini_subwork_img_div fr"><img class="mini_subwork_img2" src="https://flow.team/flow-renewal/assets/images/icons/icon-post-date.svg?v=118ef1e91b7ced5f275a138083d2ddd7cf94773d"></div>
								</div>
							</div>
			<!----------------하위업무 (상태)-------------------->			
							<div id="mini_state" class="ab" style="margin-top: -225px;">
								<div class="box_request box_state">요청</div>
								<div class="box_progress box_state">진행</div>
								<div class="box_feedback box_state">피드백</div>
								<div class="box_finish box_state">완료</div>
								<div class="box_defer box_state">보류</div>
							</div>
			<!----------------- 하위업무 우선순위  ------------------->			
							<div id="prioritize2" class="fr">
								<div class="prioritize_between">
									<div class="fl"><img class="img" src="img/x.png"/></div>
									<span class="font">취소</span>
								</div>
								<div class="prioritize_between">
									<div class="fl"><img class="img" src="img/low.png"/></div>
									<span class="font">낮음</span>
								</div>
								<div class="prioritize_between">
									<div class="fl"><img class="img" src="img/normal.png"/></div>
									<span class="font">보통</span>
								</div>
								<div class="prioritize_between">
									<div class="fl"><img class="img" src="img/hight.png"/></div>
									<span class="font">높음</span>
								</div>
								<div class="prioritize_between">
									<div class="fl"><img class="img" src="img/emergency.png"/></div>
									<span class="font">긴급</span>
								</div>
							</div>
						</div>
			<!----------------- 하위업무 담당자  ------------------->
						<div id="member_select" class="ab">
				<!-------------------담당자 검색 부분------------------------>
							<div id="search1">
								<div id="search_parents" class="fl re">
									<input id="search" type="search" placeholder="담당자를 입력하세요" >
									<img class="ab" id="search_icon" src="img/돋보기.png"/>
								</div>
							</div>
				<!-------------------멤버 전체------------------------>			
							<div id="allmember">
				<!-------------------멤버 프로필 부분------------------------>		
								<div id="member"> 
									<div class="fl"><img id="check" src="img/check_1.png"/></div>
									<div class="fl"><img id="profile" src="img/profile_1.png"/></div>
									<div id="name" class="fl">김현지</div>
								</div>
							</div>
						</div>
						
				<!-------------------좋아요, 북마크, 댓글, 읽음------------------------>
						<div id="like_bookmark" >
							<div id="like_img" class="fl"></div><div class="fl">좋아요</div>
							<div id="bookmark_img" class="fl"></div><div class="fl">북마크</div>
						</div>
						<div id="comment_read">
							<div class="fr read"> 1</div>
							<div class="fr">읽음 </div>
							<div class="fr comment"> 3</div>
							<div class="fr">댓글 </div>
						</div>
					</div>
				<!----------------------댓글부분----------------------->
					<div id="addwork_body2">
						<div id="content">
							<div id="profile" class="fl"><img class="writermember_img" src="https://flow.team/flow-renewal/assets/images/profile-default.svg"></div>
							<div id="content_input" >
								<div class="first">
									<div class="fl content_name">김민지</div>
									<div class="content_date">2024-07-01 16:51</div>
									<div class="fr delete">삭제</div>
									<div class="fr change">수정</div>
								</div>
								<div class="second">'완료'→ '요청' 상태를 변경하였습니다.</div>
								<div>
									<div class="fl content_like"></div><div class="content_like2">좋아요</div>
								</div>
							</div>
						</div>
		
					<!-- 공백부분 -->
						<div id="comment_bottom"></div>
					</div>
					<div id="bottom">
						<div id="content_profile01" class="fl"><img class="writermember_img01 border" src="https://flow.team/flow-renewal/assets/images/profile-default.svg"></div>
						<div class="content_text"><input type="text" placeholder="입력 Enter입니다."></div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 간트 -->
		<div id="gantt"  class="re" style="width:100%; height:760px;"></div>
		
		<!-- 상태 선택창  -->
		<div class="mini_state" class="ab">
			<button class="box_request box_state">요청</button>
			<button class="box_progress box_state">진행</button>
			<button class="box_feedback box_state">피드백</button>
			<button class="box_finish box_state">완료</button>
			<button class="box_defer box_state">보류</button>
		</div>
			
			<!-- 담당자 선택창 -->
		<div id="member_select">
<!-------------------담당자 검색 부분------------------------>
			<div id="search1">
				<div id="search_parents" class="fl re"><input id="search" type="search" placeholder="담당자를 입력하세요" ></div>
				<div class="ab"><img id="search_icon" src="img/돋보기.png"/></div>
			</div>
<!-------------------멤버 전체------------------------>			
			<div id="allmember">
<!-------------------멤버 프로필 부분------------------------>		
				<div id="member"> 
					<div class="fl"><img id="check" src="img/check_1.png"/></div>
					<div class="fl"><img id="profile" src="img/profile_1.png"/></div>
					<div id="name" class="fl">김현지</div>
				</div>
			</div>
		
<!-------------------bottom------------------------>		
			<div id="bottom">
				<button id="alldelete">전체삭제</button>
				<button id="select">선택</button>
			</div>
		</div>
	</div>	
	
	<div style="clear:both;"></div>
	
</body>
</html>


