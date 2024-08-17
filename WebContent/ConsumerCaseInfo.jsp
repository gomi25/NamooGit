<%@page import="dto.ConsumerCaseDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConsumerCaseDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	ConsumerCaseDto cDto = (ConsumerCaseDto) request.getAttribute("cDto"); 
    
    int caseIdx = (int)request.getAttribute("case_idx");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/ConsumerCaseInfo.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	 <script>
		 $(document).ready(function() {
	         // 오른쪽 패널의 각각 threeTxT 동작
	         $('.WingPanel_lists li').click(function(event) {
	             // 기본 동작 방지!
	             event.preventDefault(); 
	
	             // 1. 클릭된 li 태그의 인덱스 가져오기
	             var index = $(this).index();
	
	             // 해당 인덱스에 맞는 섹션 id를 생성
	             var targetId = 'body-' + index;
	
	             // 2. 클릭하면 이동할 곳
	             var target = $('#' + targetId + ' h3');
	
	             // 부드럽게 스크롤
	             $('html, body').animate({
	                 scrollTop: target.offset().top - 80 // 80px의 오프셋 추가(threeTxt 안 보이는 문제)
	             }, 600); // 600ms 동안 부드럽게 스크롤
	
	             // 3. li 태그 기본 css 속성
	             $('.WingPanel_lists li').css({
	                 'font-weight': 'normal',
	                 'color': '#333'
	             });
	
	             // 4. 클릭한 li 태그의 스타일 변경
	             $(this).css({
	                 'font-weight': 'bold',
	                 'color': '#00c473'
	             });
	         });
	     });
	</script>
</head>
<body id="jndBody" style="overflow: auto; touch-action: auto;">
    <div id="next">
        <div>
            <noscript>
                <iframe src="https://www.googletagmanager.com/ns.html?id=GTM-WDT2NRN" height="0" width="0" style="display:none;visibility:hidden"></iframe>
            </noscript>
            <!-- 여기부터 헤더 -->
            <div id="div_header1">
                <div class="fl">
                    <img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628">
                    <span>NAMOO</span>
                </div>
                <div class="fl"><a href="ConsumerCase.jsp">고객사례</a></div>
                <div class="fl"><a href="https://www.jandi.com/landing/kr/consult">도입문의</a></div>
                <div class="fl"><a href="https://support.jandi.com/ko/">헬프센터</a></div>
                <div class="fl"><a href="https://blog.jandi.com/ko/">블로그</a></div>
                <div class="fr"><a href="http://localhost:9090/WebProject1/nammo_login.html"><strong style="color: white;">로그인</strong></a></div>
                <div class="fr"><a href="https://www.jandi.com/landing/kr/download"><strong>다운로드</strong></a></div>
            </div>
            <!-- 여기까지 헤더 -->
            <main>
                <!-- 메인 안에 잔디 활용 사례 전체 -->
                <div class="Company_wrapper">
                    <section class="Company_container">
                        <section class="Company_headerSection">
                            <div class="Company_pictureBox">
                                <img src="<%= cDto.getCompanyImgUrl() %>" alt="company Image" class="Company_picture">
                            </div>
                            <div>
                                <div class="Company_labelBox">
                                    <span class="Company_label">나무 활용사례</span>
                                </div>
                                <img src="<%= cDto.getLogoImgUrl() %>" alt="company Logo Image">
                                <h1 class="Company_strong"><%= cDto.getTitle() %></h1>
                                <p><%= cDto.getSubTitle() %></p>
                            </div>
                        </section>
                        <!-- 여기서부터 left panel -->
                        <section class="Company_leftPanel">
                            <div class="WingPanel_wingPanel">
	                            <!-- WingPanel_logoBox 추가: 회사 로고와 이름을 포함하는 새로운 div -->
								<div class="WingPanel_logoBox">
                                	<img src="<%= cDto.getLogoImgUrl() %>" alt="company Logo Image" class="WingPanel_logoImg">
                                </div>
                                <!-- WingPanel_detailInfo: 산업군 및 인원수 정보 -->
						        <div class="WingPanel_detailInfo">
						            <dl>
						                <dt>산업군</dt>
						                <dd><span>제조</span></dd>
						            </dl>
						            <dl>
						                <dt>인원수</dt>
						                <dd><span>1,000명 이상</span></dd>
						            </dl>
						        </div>
						         <!-- WingPanel_lists: threeTxt를 줄바꿈 기준으로 분할하여 각 항목으로 표시 -->
                                <h4 class="WingPanel_blind"></h4>
                                <ul class="WingPanel_lists">
                                    <li><%= cDto.getThreeTxtPart1() %></li>
          							<li><%= cDto.getThreeTxtPart2() %></li>
           							<li><%= cDto.getThreeTxtPart3() %></li>
                                </ul>
                                <div>
                                    <div class="JndLink_linkBox">
                                        <a class="WingPanel_linkBtn" href="/landing/kr/consult">도입 컨설팅 받기</a>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!-- 여기서부터 컨텐트 부분 body-0 -->
                         <section class="Company_contentsSection">
                            <div class="BodyContent_bodyContent">
                            	<!-- 내용 부분 -->
                                <section class="BodyContent_mainSection">
                                    <%= cDto.getCaseTxt() %>
                                </section>
                            </div>
                        </section>
                        <!-- 목록으로 버튼 -->
                        <div class="Company_goIndustryBtn">
                            <div class="JndLink_linkBox">
                                <a class="JndLink_white" href="Controller?command=consumer_case_list">
                                    <img src="img/icon-big-arrow-right.png">목록으로
                                </a>
                            </div>
                        </div>
                    </section>
                </div>
            </main>
           <!-- 여기서부터 footer 부분 -->
            <footer id="div_footer">
                <div id="footerblock">
                    <!-- 왼쪽 부분 -->
                    <div id="footer_left" class="fl">
                        <div>
                            <img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628">
                            <p>NAMOO</p>
                        </div>
                        <ul>
                            <li><img src="img/facebook.png"><a href="https://www.facebook.com/JANDI.SKorea">Facebook</a></li>
                            <li><img src="img/instagram.png"><a href="https://www.instagram.com/jandi.korea">Instagram</a></li>
                            <li><img src="img/youtube.png"><a href="https://www.youtube.com/channel/UCtEhu2u_lDa58OAJg121flQ">YouTube</a></li>
                            <li><img src="img/blog.png"><a href="https://blog.naver.com/jandi-korea">Blog</a></li>
                            <li><img src="img/linkedin.png"><a href="https://www.linkedin.com/showcase/jandi-korea">LinkedIn</a></li>
                            <li><img src="img/newsletter.png"><a href="https://page.stibee.com/subscriptions/86645?groupIds=226036">Newsletter</a></li>
                        </ul>
                    </div>
                    <!-- 오른쪽 부분 -->
                    <div id="footer_right" class="fr">
                        <div id="footer_info1">
                            <span>NAMOO</span>
                            <ul>
                                <li><a href="https://www.jandi.com/landing/kr/features">기능</a></li>
                                <li><a href="https://www.jandi.com/landing/kr/security">보안</a></li>
                                <li><a href="https://www.jandi.com/landing/kr/pricing">요금안내</a></li>
                                <li><a href="https://support.jandi.com/ko/">헬프센터</a></li>
                                <li><a href="https://www.jandi.com/landing/kr/consult">도입문의</a></li>
                            </ul> 
                        </div>
                        <div id="footer_info2">
                            <span>다운로드</span>
                            <ul>
                                <li><a href="https://landing/kr/features">Windows</a></li>
                                <li><a href="https://landing/kr/security">Mac OS</a></li>
                                <li><a href="https://landing/kr/pricing">IOS</a></li>
                                <li><a href="https://support.jandi.com">Android</a></li>
                                <li><a href="https://landing/kr/consult">리소스</a></li>
                            </ul>
                        </div>
                        <div id="footer_info3">
                            <span>Toss Lab, Inc.</span>
                            <ul>
                                <li><a href="https://www.jandi.com/landing/kr/company">회사소개</a></li>
                                <li><a href="https://www.jandi.com/landing/kr/company">채용</a></li>
                                <li><a href="https://blog.jandi.com/ko/">나무 블로그</a></li>
                                <li><a href="https://www.jandi.com/landing/kr/partner/aws">파트너</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- footer 아래부분 -->
                <div id="footer_bottom">
                    <div id="footer_bottom_info">
                        <div>
                            <a href="/landing/kr/private/policy"><span>개인정보처리방침 | </span></a>
                            <a href="/landing/kr/terms/service"><span>이용약관</span></a>
                        </div>
                    </div>
                    <div id="footer_bottom_info2">
                        <span>(주)토스랩  | 대표이사: 김대현 | 서울특별시 강남구 봉은사로 524(인터컨티넨탈 서울 코엑스), 스파크플러스 코엑스점 지하1층 L226 | 이메일: support@tosslab.com </span>
                        <br>
                        <span>사업자등록번호: 220-88-81740 | 통신판매업신고번호: 2016-서울강남-00237 | © 2014-2024 Toss Lab, Inc.</span>
                    </div>
                </div>
            </footer>
        </div>
    </div>
</body>
</html>