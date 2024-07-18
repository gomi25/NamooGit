<%@page import="java.net.URLDecoder"%>
<%@page import="dto.HelpPostDto"%>
<%@page import="dao.NamooHelpCenterDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String paramPostIdx = request.getParameter("post_idx");    // "1"
	int postIdx = Integer.parseInt(paramPostIdx);  // "1" ---> 1
	
	NamooHelpCenterDao helpDao = new NamooHelpCenterDao();
	HelpPostDto hpDto = helpDao.getHelpPostContentDto(postIdx);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>정회원과 준회원의 권한이 어떻게 다른지 궁금합니다 | NAMOO Help Center</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<link rel="stylesheet" href="css/HelpPost.css"/>
	<link rel="stylesheet" href="css/HelpPost02.css"/>
	<link rel="stylesheet" href="css/HelpPost03.css"/>
</head>
<body>
<!--------------------------------헤더-------------------------------->
	<div id="div_header">
		<!--로고 + 나무 + 나무로 이동 --->
		<div id="div_header_company" >
			<!-- 로고+나무 -->
			<div class="fl">
				<div class="fl"><img src="img/logo_white.png"/></div>
				<div class="fl"><a href="NamooHelpMain.jsp">NAMOO</a></div>
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
					<input type="text" name="keyword" placeholder="기사 검색...">
				</div>
			</div>
		</form>		
	</div>
<!--------------------------------바디-------------------------------->
	<div id="div_body" >
		<!-- post 전체 영역 -->
		<div id="div_body_area" >
			<!-- post 영역-->	
			<div id="div_post">
				<%=URLDecoder.decode( hpDto.getContent() , "utf-8" ) %>
<%--
				<div class="relative z-3 w-full lg:max-w-160 "><div class="flex pb-6 max-md:pb-2 lg:max-w-160"><div class="flex flex-wrap items-baseline pb-4 text-base"><a href="/ko/" class="pr-2 text-body-primary-color no-underline hover:text-body-secondary-color">모든 콜렉션</a><div class="pr-2"><svg width="6" height="10" viewBox="0 0 6 10" class="block h-2 w-2 fill-body-secondary-color rtl:rotate-180" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M0.648862 0.898862C0.316916 1.23081 0.316916 1.769 0.648862 2.10094L3.54782 4.9999L0.648862 7.89886C0.316916 8.23081 0.316917 8.769 0.648862 9.10094C0.980808 9.43289 1.519 9.43289 1.85094 9.10094L5.35094 5.60094C5.68289 5.269 5.68289 4.73081 5.35094 4.39886L1.85094 0.898862C1.519 0.566916 0.980807 0.566916 0.648862 0.898862Z"></path></svg></div><a href="https://support.jandi.com/ko/collections/4014003-자주-묻는-질문" class="pr-2 text-body-primary-color no-underline hover:text-body-secondary-color" data-testid="breadcrumb-0">자주 묻는 질문</a><div class="pr-2"><svg width="6" height="10" viewBox="0 0 6 10" class="block h-2 w-2 fill-body-secondary-color rtl:rotate-180" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M0.648862 0.898862C0.316916 1.23081 0.316916 1.769 0.648862 2.10094L3.54782 4.9999L0.648862 7.89886C0.316916 8.23081 0.316917 8.769 0.648862 9.10094C0.980808 9.43289 1.519 9.43289 1.85094 9.10094L5.35094 5.60094C5.68289 5.269 5.68289 4.73081 5.35094 4.39886L1.85094 0.898862C1.519 0.566916 0.980807 0.566916 0.648862 0.898862Z"></path></svg></div><a href="https://support.jandi.com/ko/collections/4014130-권한" class="pr-2 text-body-primary-color no-underline hover:text-body-secondary-color" data-testid="breadcrumb-1">권한</a><div class="pr-2"><svg width="6" height="10" viewBox="0 0 6 10" class="block h-2 w-2 fill-body-secondary-color rtl:rotate-180" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M0.648862 0.898862C0.316916 1.23081 0.316916 1.769 0.648862 2.10094L3.54782 4.9999L0.648862 7.89886C0.316916 8.23081 0.316917 8.769 0.648862 9.10094C0.980808 9.43289 1.519 9.43289 1.85094 9.10094L5.35094 5.60094C5.68289 5.269 5.68289 4.73081 5.35094 4.39886L1.85094 0.898862C1.519 0.566916 0.980807 0.566916 0.648862 0.898862Z"></path></svg></div><div class="text-body-secondary-color">정회원과 준회원의 권한이 어떻게 다른 지 궁금합니다.</div></div></div><div class=""><div class="article intercom-force-break"><div class="mb-10 max-lg:mb-6"><div class="flex flex-col gap-4"><div class="flex flex-col"><header class="mb-1 font-primary text-2xl font-bold leading-10 text-body-primary-color">정회원과 준회원의 권한이 어떻게 다른 지 궁금합니다.</header></div><div class="avatar"><div class="avatar__info -mt-0.5 text-base"><span class="text-body-secondary-color"> <!-- -->1주 전에 업데이트함</span></div></div></div></div><div class="jsx-adf13c9b2a104cce flex-col"><div class="jsx-adf13c9b2a104cce mb-7 ml-0 text-md max-messenger:mb-6 lg:hidden"><div class="jsx-27f84a20f81f6ce9 table-of-contents max-h-[calc(100vh-96px)] overflow-y-auto rounded-2xl text-body-primary-color hover:text-primary max-lg:border max-lg:border-solid max-lg:border-body-border max-lg:shadow-solid-1"><div data-testid="toc-dropdown" class="jsx-27f84a20f81f6ce9 hidden cursor-pointer justify-between border-b max-lg:flex max-lg:flex-row max-lg:border-x-0 max-lg:border-t-0 max-lg:border-solid max-lg:border-b-body-border border-b-0"><div class="jsx-27f84a20f81f6ce9 my-2 max-lg:pl-4">목차</div><div class="jsx-27f84a20f81f6ce9 "><svg class="ml-2 mr-4 mt-3 transition-transform" transform="" width="16" height="16" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M3.93353 5.93451C4.24595 5.62209 4.75248 5.62209 5.0649 5.93451L7.99922 8.86882L10.9335 5.93451C11.246 5.62209 11.7525 5.62209 12.0649 5.93451C12.3773 6.24693 12.3773 6.75346 12.0649 7.06588L8.5649 10.5659C8.25249 10.8783 7.74595 10.8783 7.43353 10.5659L3.93353 7.06588C3.62111 6.75346 3.62111 6.24693 3.93353 5.93451Z" fill="currentColor"></path></svg></div></div><div data-testid="toc-body" class="jsx-27f84a20f81f6ce9 hidden my-2"><section data-testid="toc-section-0" class="jsx-27f84a20f81f6ce9 flex border-y-0 border-e-0 border-s-2 border-solid py-1.5 max-lg:border-none border-body-toc-active-border px-7"><a id="#h_9a6c982c1a" href="#h_9a6c982c1a" data-testid="toc-link-0" class="jsx-27f84a20f81f6ce9 w-full no-underline hover:text-body-primary-color max-lg:inline-block max-lg:text-body-primary-color max-lg:hover:text-primary lg:text-base font-toc-active text-body-primary-color">✅ 정회원 (소유자, 관리자 포함)</a></section><section data-testid="toc-section-1" class="jsx-27f84a20f81f6ce9 flex border-y-0 border-e-0 border-s-2 border-solid py-1.5 max-lg:border-none border-body-toc-inactive-border px-7"><a id="#h_d110361559" href="#h_d110361559" data-testid="toc-link-1" class="jsx-27f84a20f81f6ce9 w-full no-underline hover:text-body-primary-color max-lg:inline-block max-lg:text-body-primary-color max-lg:hover:text-primary lg:text-base text-body-toc-inactive-color">✅준회원</a></section><section data-testid="toc-section-2" class="jsx-27f84a20f81f6ce9 flex border-y-0 border-e-0 border-s-2 border-solid py-1.5 max-lg:border-none border-body-toc-inactive-border px-7"><a id="#h_62cffcca90" href="#h_62cffcca90" data-testid="toc-link-2" class="jsx-27f84a20f81f6ce9 w-full no-underline hover:text-body-primary-color max-lg:inline-block max-lg:text-body-primary-color max-lg:hover:text-primary lg:text-base text-body-toc-inactive-color">✅요금제 별 준회원 초대 정책</a></section><section data-testid="toc-section-3" class="jsx-27f84a20f81f6ce9 flex border-y-0 border-e-0 border-s-2 border-solid py-1.5 max-lg:border-none border-body-toc-inactive-border px-7"><a id="#h_b6720553ef" href="#h_b6720553ef" data-testid="toc-link-3" class="jsx-27f84a20f81f6ce9 w-full no-underline hover:text-body-primary-color max-lg:inline-block max-lg:text-body-primary-color max-lg:hover:text-primary lg:text-base text-body-toc-inactive-color">예시)</a></section></div></div></div><div class="jsx-adf13c9b2a104cce article_body"><article class="jsx-adf13c9b2a104cce "><div class="intercom-interblocks-subheading intercom-interblocks-align-left"><h2 id="h_9a6c982c1a">✅ 정회원 (소유자, 관리자 포함)</h2></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p>- 정회원은 <b>공개된 토픽 및 컨텐츠에 접근 가능</b>하며 자신이 작성한 콘텐츠의 소유 권한을 가집니다.</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p>- 모든 정회원은 팀 소유자 또는 관리자가 될 수 있습니다. (<b><a href="https://intercom.help/jandi/ko/articles/6352670-%ED%8C%80-%EC%86%8C%EC%9C%A0%EC%9E%90%EC%99%80-%EA%B4%80%EB%A6%AC%EC%9E%90%EB%8A%94-%EC%96%B4%EB%96%A4-%EA%B6%8C%ED%95%9C%EC%9D%B4-%EC%9E%88%EB%82%98%EC%9A%94" rel="nofollow noopener noreferrer" target="_blank">소유자 및 관리자 권한 알아보기</a></b><a href="https://support.jandi.com/hc/ko/articles/115000100423">)</a></p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p>- 팀 멤버 프로필도 자유롭게 확인할 수 있습니다.</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p><br>&ZeroWidthSpace;</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-subheading intercom-interblocks-align-left"><h2 id="h_d110361559">✅준회원</h2></div><div class="intercom-interblocks-unordered-nested-list"><ul><li><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p>준회원은 <b>외부 협력 직원을 초대하는 '게스트 초대' 기능</b>입니다. <br>ex. 인턴, 알바, 파트너사/협력사 직원, 홍보대사, 사외이사 등</p></div></li></ul></div><div class="intercom-interblocks-image intercom-interblocks-align-left"><a href=https://jandi-35ca3b37d578.intercom-attachments-1.com/i/o/539177499/db6148c64c063a021880f1f7/____________________________200421.pdf_55_107________2020-09-14_11-36-53.pngg" target="_blank" rel="noreferrer nofollow noopener"><img src="https://jandi-35ca3b37d578.intercom-attachments-1.com/i/o/539177499/db6148c64c063a021880f1f7/____________________________200421.pdf_55_107________2020-09-14_11-36-53.png" alt="____________________________200421.pdf_55_107________2020-09-14_11-36-53.png"></a></div><div class="intercom-interblocks-unordered-nested-list"><ul><li><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p>준회원은 <b>초대된 토픽과 공유받은 컨텐츠에만 접근 가능</b>합니다. 초대된 토픽의 과거 내용은 모두 확인이 가능합니다. href="https://intercom.help/jandi/ko/articles/6352677-%ED%8C%80%EC%97%90-%EB%A9%A4%EB%B2%84%EB%A5%BC-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%B4%88%EB%8C%80-%ED%95%98%EB%82%98%EC%9A%94" rel="nofollow noopener noreferrer" target="_blank">멤버 초대 방법 알아보기</a></b>)</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-subheading intercom-interblocks-align-left"><h2 id="h_62cffcca90">✅요금제 별 준회원 초대 정책</h2></div><div class="intercom-interblocks-image intercom-interblocks-align-left"><a href="https://jandi-35ca3b37d578.intercom-attachments-1.com/i/o/539177506/638d679e2b5f124aadceee34/______.png" target="_blank" rel="noreferrer nofollow noopener"><img src="https://jandi-35ca3b37d578.intercom-attachments-1.com/i/o/539177506/638d679e2b5f124aadceee34/______.png" alt="______.png"></a></div><div class="intercom-interblocks-subheading intercom-interblocks-align-left"><h2 id="h_b6720553ef">예시)</h2></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p><b>Q1:</b> 정회원 15명인 팀으로 Premium 요금제를 사용하고 있습니다. 무료로 초대 가능한 준회원 수는 몇 명인가요?</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p><b>A1:</b> 정회원 수 15명 + 10명 = 25명, 총 25명의 준회원을 팀에 무료로 초대가 가능합니다.</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p><b>Q2:</b> 잔디를 무료로 사용하고 있습니다. 정회원 40명으로 이용하고 있는데 준회원은 몇 명까지 초대가 가능한가요?</p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p> </p></div><div class="intercom-interblocks-paragraph no-margin intercom-interblocks-align-left"><p><b>A2:</b> 무료 준회원 초대는 10명까지 가능합니다. 10명 이상 준회원을 초대하고 싶으실 경우 유료 플랜으로 업그레이드를 고려해주세요.</p></div><section class="jsx-62724fba150252e0 related_articles my-6"><hr class="jsx-62724fba150252e0 my-6 sm:my-8"><div class="jsx-62724fba150252e0 mb-3 text-xl font-bold">관련 자료</div><section class="flex flex-col rounded-card border border-solid border-card-border bg-card-bg p-2 sm:p-3"><a class="duration-250 group/article flex flex-row justify-between gap-2 py-2 no-underline transition ease-linear hover:bg-primary-alpha-10 hover:text-primary sm:rounded-card-inner sm:py-3 rounded-card-inner px-3" href="https://support.jandi.com/ko/articles/6352670-팀-소유자와-관리자의-권한이-궁금합니다" data-testid="article-link"><div class="flex flex-col p-0"><span class="m-0 text-md text-body-primary-color group-hover/article:text-primary">팀 소유자와 관리자의 권한이 궁금합니다.</span></div><div class="flex shrink-0 flex-col justify-center p-0"><svg class="block h-4 w-4 text-primary ltr:-rotate-90 rtl:rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg></div></a><a class="duration-250 group/article flex flex-row justify-between gap-2 py-2 no-underline transition ease-linear hover:bg-primary-alpha-10 hover:text-primary sm:rounded-card-inner sm:py-3 rounded-card-inner px-3" href="https://support.jandi.com/ko/articles/6352677-팀에-멤버를-초대하는-방법이-궁금합니다" data-testid="article-link"><div class="flex flex-col p-0"><span class="m-0 text-md text-body-primary-color group-hover/article:text-primary">팀에 멤버를 초대하는 방법이 궁금합니다.</span></div><div class="flex shrink-0 flex-col justify-center p-0"><svg class="block h-4 w-4 text-primary ltr:-rotate-90 rtl:rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg></div></a><a class="duration-250 group/article flex flex-row justify-between gap-2 py-2 no-underline transition ease-linear hover:bg-primary-alpha-10 hover:text-primary sm:rounded-card-inner sm:py-3 rounded-card-inner px-3" href="https://support.jandi.com/ko/articles/6352696-정회원과-준회원의-권한이-어떻게-다른-지-궁금합니다" data-testid="article-link"><div class="flex flex-col p-0"><span class="m-0 text-md text-body-primary-color group-hover/article:text-primary">정회원과 준회원의 권한이 어떻게 다른 지 궁금합니다.</span></div><div class="flex shrink-0 flex-col justify-center p-0"><svg class="block h-4 w-4 text-primary ltr:-rotate-90 rtl:rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg></div></a><a class="duration-250 group/article flex flex-row justify-between gap-2 py-2 no-underline transition ease-linear hover:bg-primary-alpha-10 hover:text-primary sm:rounded-card-inner sm:py-3 rounded-card-inner px-3" href="https://support.jandi.com/ko/articles/6352717-멤버-권한이-어떻게-나뉘어져-있는-지-궁금합니다" data-testid="article-link"><div class="flex flex-col p-0"><span class="m-0 text-md text-body-primary-color group-hover/article:text-primary">멤버 권한이 어떻게 나뉘어져 있는 지 궁금합니다.</span></div><div class="flex shrink-0 flex-col justify-center p-0"><svg class="block h-4 w-4 text-primary ltr:-rotate-90 rtl:rotate-90" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg></div></a></section></section></article></div></div></div></div><div class="intercom-reaction-picker -mb-4 -ml-4 -mr-4 mt-6 rounded-card sm:-mb-2 sm:-ml-1 sm:-mr-1 sm:mt-8"><div class="intercom-reaction-prompt">답변이 도움되었나요?</div><button class="intercom-reaction" aria-label="실망스러운 반응" tabindex="0" data-reaction-text="disappointed" aria-pressed="false"><span title="실망">😞</span></button><button class="intercom-reaction" aria-label="무표정한 반응" tabindex="0" data-reaction-text="neutral" aria-pressed="false"><span title="무표정">😐</span></button><button class="intercom-reaction" aria-label="웃는 반응" tabindex="0" data-reaction-text="smiley" aria-pressed="false"><span title="웃음">😃</span></button></div></div>
--%>
			</div>
		</div>
	</div>
</body>
</html>
























