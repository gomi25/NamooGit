<%@page import="java.util.ArrayList"%>
<%@page import="dto.HelpPostDto"%>
<%@page import="dao.NamooHelpCenterDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String keyword = request.getParameter("keyword");
	NamooHelpCenterDao helpDao = new NamooHelpCenterDao();
	ArrayList<HelpPostDto> helpPostDto = helpDao.getSearchedHelpPost(keyword);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>나무 | 검색 결과</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/NamooHelpSearchResult.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script>
		$(function() {
			$(".search_result_box").click(function() {
				let post_idx = $(this).attr("post_idx");
				//alert("다음페이지?post_idx=" + post_idx + " 로 이동 !!");
				location.href = "HelpPost.jsp?post_idx=" + post_idx;
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
				<div class="fl"><a href="NamooHelpMain.jsp">NAMOO</a></div>
			</div>
			<!-- 나무로 이동  -->
			<div class="fr"> 
				<a href="NamooMain.jsp">NAMOO로 이동</a>
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
	<!--------------------------------헤더-------------------------------->
<style>
	.search_result_content {
		white-space: normal;
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
		overflow: hidden;
	}
</style>
	<div id="div_body">
		<div id="div_search_result_area">
			<!-- 검색 결과 : 검색어 -->
			<div>
				<div class="fl">검색 결과 : </div>
				<div class="fl"> <%= keyword %></div>
			</div>
			<!-- 검색결과박스  -->
			<% for(HelpPostDto hDto : helpPostDto) { 
				String content = hDto.getContent();
				content = content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				content = content.replaceAll("<[^>]*>", " ");
			%>
			<div class="search_result_box" post_idx="<%=hDto.getPostIdx()%>">
				<div class="search_result_title"> <%= hDto.getTitle() %></div>
				<div class="search_result_content"><%= content %></div>
			</div>
			<% } %>
		</div>
	</div>
	
</body>
</html>