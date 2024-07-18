<%@page import="java.util.List"%>
<%@page import="dto.ShowCategoryNameAndTitleDto"%>
<%@page import="dto.ShowHelpPostCategoryDto"%>
<%@page import="dao.NamooHelpCenterDao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String paramHelpIdx = request.getParameter("help_idx");    // "1"
	int helpIdx = Integer.parseInt(paramHelpIdx);  // "1" ---> 1
	
	NamooHelpCenterDao helpDao = new NamooHelpCenterDao();
	ArrayList<ShowHelpPostCategoryDto> showHelpPostCategoryDto = helpDao.showHelpPostCategoryOne(helpIdx);
//	ArrayList<ShowCategoryNameAndTitleDto> showCategoryNameAndTitleDto = helpDao.showCategoryNameAndTitle(helpIdx);
	ArrayList<String> listHelpCategoryName = helpDao.getListHelpCategoryName(helpIdx);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>자주 묻는 질문 | NAMOO Help Center</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/HelpPostCategory.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function() {
			$(".div_category_01 > div:not(:first-child)").click(function() {
				let post_idx = $(this).attr("post_idx");
				//alert("다음페이지?post_idx=" + post_idx + " 로 이동 !!");
				location.href = "HelpPost.jsp?post_idx=" + post_idx;
			});
		});
//		div_category_01
	</script>
</head>
<body>
<!--------------------------------헤더-------------------------------->
	<div id="div_header">
		<!-- 로고 + 나무 + 나무로 이동 -->
		<div id="div_header_company" >
			<!------ 로고+나무 ------>
			<div class="fl">
				<div class="fl"><img src="img/logo_white.png"/></div>
				<div class="fl">NAMOO</div>
			</div>
			<!-- 나무로 이동 -->
			<div class="fr"> 
				<a href="NamooMain.jsp">NAMOO로 이동</a>
			</div>
		</div>
		<!-- 검색창 -->
		<form action="NamooHelpSearchResult.jsp">
			<div id="div_header_search">
				<div>
					<input type="search" name="keyword" placeholder="기사 검색...">
				</div>		
			</div>
		</form>
	</div>
<!--------------------------------바디-------------------------------->
	<div id="div_body">
		<div id="div_body_area">
			<!-- 모든 콜렉션 > 자주 묻는 질문-->
			<div>
				<div class="fl"><a href="NamooHelpMain.jsp">모든 콜렉션</a></div>
				<div class="fl"> > </div>
				<div class="fl"><%=showHelpPostCategoryDto.get(0).getTitle()%></div>
			</div>
			<!-- 이미지 > 제목 > 부제목 > n개의 자료-->
			<% for( ShowHelpPostCategoryDto shDto : showHelpPostCategoryDto) { %>
			<div>
				<div><img src="<%=shDto.getIconImgUrl()%>"></div>
				<div><%=shDto.getTitle()%></div>
				<div><%=shDto.getSubtitle() %></div>
				<div><%=shDto.getCount() %>개의 자료</div>
			</div>
			<%} %>
			<!-- 질문 목록 분류 -->
			<div class="div_category" >
				<!-- 목록 1번 (권한) -->
				<% 
				//for(ShowCategoryNameAndTitleDto sntDto : showCategoryNameAndTitleDto) { 
				for(String currentCategoryName : listHelpCategoryName) {
					//String currentCategoryname = sntDto.getCategoryName();
				%>
					<div class="div_category_01">
						<div><%=currentCategoryName%></div>
						<%
						ArrayList<ShowCategoryNameAndTitleDto> listTitleDto = helpDao.getListHelpPostTitle(helpIdx, currentCategoryName);
						for(ShowCategoryNameAndTitleDto sntDto : listTitleDto) {
						%>
							<div post_idx="<%=sntDto.getPostIdx()%>">
								<div class="fl"><%=sntDto.getTitle()%></div>
								<div class="fr"> > </div>
							</div>
						<% 
						}
						%>
					</div>
				<% } %>
				<%--
				<!-- 목록 2번 (잔디 시작하기) -->
				<div id="div_category_02">
					<div>잔디 시작하기</div>
					<div>
						<div class="fl">팀에 멤버를 초대하는 방법이 궁금합니다.</div>
						<div class="fr"> > </div>
					</div>
					<div>
						<div class="fl">채팅을 시작하고 싶습니다.</div>
						<div class="fr"> > </div>
					</div>
				</div>
				<!-- 목록 3번 (용량 및 백업) -->
				<div id="div_category_03">
					<div>용량 및 백업</div>
					<div>
						<div class="fl">용량이 부족해요. 용량을 확보하고 싶습니다.</div>
						<div class="fr"> > </div>
					</div>
					<div>
						<div class="fl">백업이 가능한지 알고 싶습니다. </div>
						<div class="fr"> > </div>
					</div>
				</div>
				<!-- 목록 4번 (설정) -->
				<div id="div_category_04">
					<div>설정</div>
					<div>
						<div class="fl">단축키를 사용하고 싶습니다.</div>
						<div class="fr"> > </div>
					</div>
					<div>
						<div class="fl">메시지 줄바꿈 설정을 변경하고 싶습니다.</div>
						<div class="fr"> > </div>
					</div>
				</div>
				<!-- 목록 5번 (커넥트) -->
				<div id="div_category_05">
					<div>커넥트</div>
					<div>
						<div class="fl">나무 커넥트가 무엇인지 궁금합니다.</div>
						<div class="fr"> > </div>
					</div>
					<div>
						<div class="fl">나무 커넥트 인커밍 웹훅(Incoming Webhook)으로 외부 데이터를 나무로 수신하고 싶습니다.</div>
						<div class="fr"> > </div>
					</div>
				</div>
				--%>
				<div style="height:80px;"></div>
			</div>
		</div>
	</div>
</body>
</html>









