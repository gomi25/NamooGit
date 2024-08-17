<%@page import="dto.ConsumerCaseDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ConsumerCaseDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    ConsumerCaseDao cDao = new ConsumerCaseDao(); // 내가 쓸 dao
    ArrayList<ConsumerCaseDto> consumerCaseDto = null;
    int industry = 0; // 초기값

    if (request.getParameter("industry") == null) {
        consumerCaseDto = cDao.getListConsumerCaseDto(); // 내가 쓸 dto
    } else {
        industry = Integer.parseInt(request.getParameter("industry"));
        consumerCaseDto = cDao.getListConsumerCaseDtoByIndustry(industry);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>고객사례 메인</title>
    <link rel="stylesheet" href="css/ConsumerCase-Main.css"/>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        function hideMoreButtonIfTheEnd() {
            let cnt = 0;
            $("ul.CardList_col3 > li").each(function(idx, item) { // "제이쿼리 for-each 반복문"
                if ($(item).css('display') == 'none')
                    cnt++;
            });
            //alert(cnt);
            if (cnt == 0) {
                $(".FilterWithList_moreBtnBox__2").parent().css('display', 'none');
            }
        }

        $(function() {
            // 1. 카테고리 선택 시(=Filters_filterListItem) Filters_selected 클래스를 추가하여 폰트 색상 바꾸기
            $('.Filters_filterListItem').click(function() {
                // 1-1. 모든 Filters_filterListItem에 Filters_selected 클래스 삭제
                $('.Filters_filterListItem').removeClass('Filters_selected');
                // 1-2. 클릭한 항목에 Filters_selected 클래스 추가하하여 폰트 색상 바꾸기
                // 여기에서 this는 Filters_filterListItem의 요소를 의미함(클릭이 된 Filters_filterListItem의)
                $(this).addClass('Filters_selected');

                let idx = $('.Filters_filterListItem').index(this);
                //alert("선택한 li 요소의 인덱스 : " + idx);
                if (idx == 0) { // '전체'를 선택한 경우.
                    location.href = "ConsumerCase.jsp";
                } else {
                    location.href = "ConsumerCase.jsp?industry=" + idx;
                }
            });

            // 2. 더 보기 버튼으로 리스트 노출
            $('.CardList_cardList li').hide().slice(0, 9).show();
            hideMoreButtonIfTheEnd();

            // 3. 더 보기 버튼 클릭 이벤트
            $('.FilterWithList_moreBtnBox__2').click(function() {
                // 현재 보이는 카드의 개수
                var visibleCards = $('.CardList_cardList li:visible').length;
                // 다음에 보일 카드의 개수 (최대 9개)
                var newVisibleCards = $('.CardList_cardList li').slice(visibleCards, visibleCards + 9).fadeIn().length;
                hideMoreButtonIfTheEnd();
            });

            // .JndSelectBox_indSelectBox 클릭 이벤트
            $('.JndSelectBox_indSelectBox').click(function(event) {
                event.stopPropagation();
                $(this).find('.dropdownList').toggle(); // dropdownList를 토글로 보이게/숨기게 함
            });

            // 바깥 부분 클릭 시 dropdownList 숨김
            $(document).click(function() {
                $('.dropdownList').hide(); // dropdownList를 숨김
            });

            // li 클릭 이벤트
            $('.dropdownList li').click(function(event) {
                event.stopPropagation();
                var selectedValue = $(this).text();
                $('.JndSelectBox_selected').text(selectedValue); // 선택된 값을 표시
                $('.dropdownList li').removeClass('selectedBox'); // 모든 li에서 selectedBox 클래스 제거
                $(this).addClass('selectedBox'); // 클릭한 li에 selectedBox 클래스 추가
                $('.dropdownList').hide(); // dropdownList를 숨김
            });
        });
    </script>
</head>
<body>
    <div id="div_header1">
        <div class="fl">
            <img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628">
            <span>NAMOO</span>
        </div>
        <div class="fl"><a href="https://www.jandi.com/landing/kr/industry">고객사례</a></div>
        <div class="fl"><a href="https://www.jandi.com/landing/kr/consult">도입문의</a></div>
        <div class="fl"><a href="https://support.jandi.com/ko/">헬프센터</a></div>
        <div class="fl"><a href="https://blog.jandi.com/ko/">블로그</a></div>
        <div class="fr"><a href="http://localhost:9090/WebProject1/nammo_login.html"><strong>로그인</strong></a></div> 
        <div class="fr"><a href="https://www.jandi.com/landing/kr/download"><strong>다운로드</strong></a></div>
    </div>
    
    <!-- 여기까지 헤더 -->
    
    <main>
        <div>
            <div>
                <div class="TopBanner_bannerContainer">
                    <div class="TopBanner_bannerDesc">
                        <span class="TopBanner_category Industry_industryCustomTag">
                            <img src="img/company.png" style="margin-right: 10px;">
                            고객사례
                        </span>
                        <h1 class="TopBanner_title">
                            고객과 함께 자라는
                            <span>나무</span>
                        </h1>
                        <p class="TopBanner_description">성장하는 37만 팀이 나무 위에서 협업하고 있습니다.</p>
                    </div>
                </div>
            </div>
            
            <div id="div_body2"> <!-- 대표 고객사례부터 그 밑까지 -->
                <section class="Industry_recommend_section">
                    <h2>대표 고객사례</h2>
                    <div id="div_mainConsumerCase">
                        <ul class="CardList" style="margin: 0px -24px;">
                            <!-- 대표 고객사례 2개 -->
                            <li style="padding: 24px;">
                                <a href="/landing/kr/industry/nexentire">
                                    <div class="Card">
                                        <h3 class="Card_blind">해외 각국의 지점과 원활하게 소통합니다.</h3>
                                        <div class="Card_coverImgBox">
                                            <img src="https://cdn.jandi.com/landing/public/static/industry/recommendedExamples/customer_nexentire.png" alt="nexentire background">
                                        </div>
                                        <div class="Card_contentBox">
                                            <div class="Card_logoBox">
                                                <img src="https://cdn.jandi.com/landing/public/static/industry/companys/logos/industry-company-logo-nexentire.png" alt="nexentire Logo">
                                            </div>
                                            <p class="Card_title">해외 각국의 지점과 원활하게 소통합니다.</p>
                                            <p class="Card_description">국내외 모든 임직원이 효율적으로 소통하는 문화가 정착됐습니다. 중국, 체코 등에 소재한 주재원 및 현지인들과 원활하게 소통하고 있습니다.</p>
                                            <span>
                                                자세히 보기
                                                <img src="img/Card_icon.png" class="Card_icon">
                                            </span>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li style="padding: 24px;">
                                <a href="/landing/kr/industry/amorepacific">
                                    <div class="Card">
                                        <h3 class="Card_blind">원활한 IT 프로젝트 운영을 위한 실시간 협업 시스템 완성</h3>
                                        <div class="Card_coverImgBox">
                                            <img src="https://cdn.jandi.com/landing/public/static/industry/recommendedExamples/customer_amorepacific.png" alt="amorepacific background">
                                        </div>
                                        <div class="Card_contentBox">
                                            <div class="Card_logoBox">
                                                <img src="https://cdn.jandi.com/landing/public/static/industry/companys/logos/industry-company-logo-amorepacific.png" alt="amorepacific Logo">
                                            </div>
                                            <p class="Card_title">원활한 IT 프로젝트 운영을 위한 실시간 협업 시스템 완성</p>
                                            <p class="Card_description">수많은 IT 프로젝트의 내・외부 모든 관계자가 나무에서 실시간으로 협업합니다.</p>
                                            <span>
                                                자세히 보기
                                                <img src="img/Card_icon.png" class="Card_icon">
                                            </span>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </div>
                </section>
                <!-- 대표 고객사례 밑에 부분 -->
                <section class="Indusrty_allSection">
                    <h2 class="Industry_blind">전체 고객 사례</h2>
                    <div class="FilterWithList_filterWithList">
                        <div class="FilterWithList_filterBox">
                            <div class="FilterWithList_leftSide">
                                <div class="Filters_filters">
                                    <ul class="Filters_filtersList">
                                        <li data-value="total" class="Filters_filterListItem <%=(industry==0 ? "Filters_selected" : "")%>">전체</li>
                                        <li data-value="manufacturing" class="Filters_filterListItem <%=(industry==1 ? "Filters_selected" : "")%>">제조</li>
                                        <li data-value="commerce" class="Filters_filterListItem <%=(industry==2 ? "Filters_selected" : "")%>">유통·커머스</li>
                                        <li data-value="fnb" class="Filters_filterListItem <%=(industry==3 ? "Filters_selected" : "")%>">F&amp;B</li>
                                        <li data-value="healthcare" class="Filters_filterListItem <%=(industry==4 ? "Filters_selected" : "")%>">헬스케어</li>
                                        <li data-value="media" class="Filters_filterListItem <%=(industry==5 ? "Filters_selected" : "")%>">미디어</li>
                                        <li data-value="education" class="Filters_filterListItem <%=(industry==6 ? "Filters_selected" : "")%>">교육</li>
                                        <li data-value="others" class="Filters_filterListItem <%=(industry==7 ? "Filters_selected" : "")%>">기타</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="FilterWithList_cardListBox">
                            <ul class="CardList_cardList CardList_col3">
                                <% for (ConsumerCaseDto cDto : consumerCaseDto) { %>
                                <li style="padding: 24px 13px;">
                                    <!-- 링크에 case_idx를 URL 파라미터로 전달하도록 수정 -->
                                    <%-- <a href="ConsumerCaseInfo.jsp?case_idx=<%= cDto.getCaseIdx() %>"> --%>
                                    <form id="caseForm<%= cDto.getCaseIdx() %>" action="Controller" method="get">
                                        <input type="hidden" name="command" value="consumer_case_info">
                                        <input type="hidden" name="case_idx" value="<%= cDto.getCaseIdx() %>">
                                        <a href="#" onclick="document.getElementById('caseForm<%= cDto.getCaseIdx() %>').submit();">
                                            <div class="Card_card" id="Card_card">
                                                <h3 class="Card_blind">전국 50+ 지점과 전사 임직원을 연결해주는 통합 협업 공간</h3>
                                                <div class="Card_coverImgBox">
                                                    <%-- <img src="<%=list.get(0).getCompanyImgUrl() %>" alt="lotteshopping background"> --%>
                                                    <img src="<%=cDto.getCompanyImgUrl() %>" alt="lotteshopping background">
                                                </div>
                                                <div class="Card_contentBox">
                                                    <div class="Card_logoBox">
                                                        <img src="<%=cDto.getLogoImgUrl()%> ">
                                                    </div>
                                                    <p class="Card_description">
                                                        <%=cDto.getMainTitle()%>
                                                    </p>
                                                    <span>
                                                        자세히 보기
                                                        <img src="img/Card_icon.png" class="Card_icon">
                                                    </span>
                                                </div>
                                            </div>
                                        </a>
                                    </form>
                                </li>
                                <% } %>
                            </ul>
                            <!-- 여기부터 더보기 버튼 -->
                            <div class="FilterWithList_moreBtnBox__1">
                                <button type="button" class="FilterWithList_moreBtnBox__2">
                                    <span>더 보기</span>
                                    <img src="img/moreBtnBox.png" aria-hidden="true">
                                </button>
                            </div>
                        </div>
                    </div>
                </section>
                <!-- 여기부터 footer 부분 - 성공하는 팀의 나무 도입 이유 -->
                <div id="div_introduction_inquiry">
                    <img src="https://cdn.jandi.com/landing/_next/static/media/common-bottom-bg.98cfc45c7d76afc35f8388e47149adf8.png">
                    <div>
                        <h1>협업툴 전문가에게 지금 문의하세요</h1>
                        도입문의를 남겨주시면 나무 컨설턴트가 24시간 내 연락 드립니다.<br/>
                        <div class="fl">도입문의</div>
                        <div class="fl">회원가입</div>
                    </div>
                </div>
                <!-- 여기서부터 footer 부분 -->
                <!-- footer 부분 -->
                <div id="div_footer">
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
                            <span>(주)토스랩  | 대표이사: 김대현 | 서울특별시 강남구 봉은사로 524(인터컨티넨탈 서울 코엑스), 스파크플러스 코엑스점 지하1층 L226 | 이메일:support@tosslab.com </span>
                            <br>
                            <span>사업자등록번호: 220-88-81740 | 통신판매업신고번호: 2016-서울강남-00237 | © 2014-2024 Toss Lab, Inc.</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</html>