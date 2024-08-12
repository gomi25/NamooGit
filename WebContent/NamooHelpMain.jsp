<%@page import="dto.HelpPostDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.NamooHelpCenterDao"%>
<%@ page import="dto.ShowHelpPostCategoryDto"%>
<%@ page import="java.util.ArrayList"%>
    
<%
	NamooHelpCenterDao helpDao = new NamooHelpCenterDao();
	ArrayList<ShowHelpPostCategoryDto> listCategory = helpDao.showHelpPostCategory();
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>NAMOO Help Center</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooHelpMain.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function() {
			$(".category_area").click(function() {
				let help_idx = $(this).attr("help_idx");
				//alert("다음페이지?help_idx=" + help_idx + " 로 이동 !!");
				location.href = "HelpPostCategory.jsp?help_idx=" + help_idx;
			});
			$(".category_guide01").click(function() {
				let post_idx = $(this).attr("post_idx");
				//alert("다음페이지?post_idx=" + post_idx + " 로 이동 !!");
				location.href = "HelpPost.jsp?post_idx=" + 14;
			});
		});
	</script>
</head>
<body>
<!--------------------------------헤더-------------------------------->
	<div id="div_header">
		<!-- 로고 + 나무 + 나무로 이동 -->
		<div id="div_header_company" >
			<!-- 로고+나무  -->
			<div class="fl">
				<div class="fl"><img src="img/logo_white.png"/></div>
				<div class="fl"><a href="Controller?command=enter_main_page">NAMOO</a></div>
			</div>
			<!-- 나무로 이동  -->
			<div class="fr"> 
				<a href="Controller?command=enter_main_page">NAMOO로 이동</a>
			</div>
		</div>
		<!-- 검색창 -->
		<form action="NamooHelpSearchResult.jsp">
			<div id="div_header_search">
				<div>궁금한 점을 '단어'로 검색해 주세요. ex) 프로필, 비밀번호</div>
				<div>
					<input type="search" name="keyword" placeholder="기사 검색...">
				</div>		
			</div>
		</form>
	</div>
	<!--------------------------------바디-------------------------------->
	<div id="div_body">
		<!-- 카테고리 틀 -->
		<div id="div_category_body">
			<!-- 이용가이드 -->
			<div id="div_category_guide">
				<div> 이용 가이드</div>
				<div>
					<div class="fl">
						[관리자]의 NAMOO 사용방법
						<div class="fr"> > </div>
					</div>
					<div class="fl">
						[모든 사용자]의 NAMOO 사용방법
						<div class="fr"> > </div>
					</div>
					<div class="fl">
						채팅을 시작하고 싶습니다.
						<div class="fr"> > </div>
					</div>
				</div>
			</div>
			<!-- 목록 카테고리 -->
			<div id="div_category">
				<% for(int i=0; i<=8; i++) { %>
				<div id="div_category_0<%=i+1%>" class="fl category_area" help_idx="<%=listCategory.get(i).getHelpIdx()%>">
					<div><img src="<%=listCategory.get(i).getIconImgUrl()%>"></div>
					<div><%=listCategory.get(i).getTitle() %></div>
					<div><%=listCategory.get(i).getSubtitle() %></div>
					<div><%=listCategory.get(i).getCount() %>개의 자료</div>
				</div>
				<% } %>
			</div>
		</div>
	</div><!-- 바디 영역 끝  -->
</body>
</html>



















