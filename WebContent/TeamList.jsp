<%@page import="java.util.ArrayList"%>
<%@page import="dto.*"%>
<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int memberIdx = (Integer)session.getAttribute("memberIdx");
	
// 	MemberDto memberDto = (MemberDto) request.getAttribute("memberDto");
// 	ArrayList<TeamListDto> list = (ArrayList<TeamListDto>)request.getAttribute("teamList");
	MemberDto memberDto = new MemberDto();
	MemberDao mDao = new MemberDao();
	ArrayList<TeamListDto> list = mDao.getListTeamListDto(memberIdx);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TeamList</title>
    <link rel="stylesheet" href="css/TeamList.css"/>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
       $(document).ready(function() {
	        // "div_email" 클릭 시 "dropdown_menu" 표시
	        $('#div_email').click(function(event) {
	            event.stopPropagation();
	            $('.profile_dropdown').toggle(); 
	        });

	        // "dropdown_menu" 외부 클릭 시 "dropdown_menu" 숨기기
	        $(document).click(function(event) { 
	            var $target = $(event.target);
	            if(!$target.closest('.profile_dropdown').length && 
	            $('.profile_dropdown').is(":visible")) {
	                $('.profile_dropdown').hide();
	            }        
	        });

	        // "teamName" 입력란 클릭 시 툴팁 표시
	        $('input[name="teamName"]').focus(function() {
	            $('#tooltip').fadeIn(); 
	        });
	        // "teamName" 입력란 포커스 아웃 시 툴팁 숨김
	        $('input[name="teamName"]').blur(function() {
	            $('#tooltip').fadeOut();
	        });
	
	        // "teamDomain" 입력란 클릭 시 툴팁2 표시
	        $('input[name="teamDomain"]').focus(function() {
	            $('#tooltip2').fadeIn(); 
	        });
	
	        // "teamDomain" 입력란 포커스 아웃 시 툴팁2 숨김
	        $('input[name="teamDomain"]').blur(function() {
	            $('#tooltip2').fadeOut(); 
	        });

	         // 입력란 값 변경 시 버튼 활성화 상태 업데이트
	        $('input[name="teamName"], input[name="teamDomain"]').on('input', function() {
	            var teamName = $('input[name="teamName"]').val();
	            var teamDomain = $('input[name="teamDomain"]').val();
	
	            // teamDomain 유효성 검사: 영어 5자 이상, 숫자, 하이픈 사용 가능, 공백 불가
	            var isValidDomain = /^[a-zA-Z0-9-]{5,}$/.test(teamDomain);
	
	            if (teamName.length > 0 && isValidDomain) {
	                $('#button').removeAttr('disabled').addClass('enabled');
	            } else {
	                $('#button').attr('disabled', 'disabled').removeClass('enabled');
	            }
	        });

	        // "팀으로 이동하기" 버튼 클릭 시 팀 생성 창 숨기기
	        $('#button').click(function() {
	            if (!$(this).is(':disabled')) {
	                $('#team-make-main').hide();
	            }
	        });
	
	        // "Xbtn" 클릭 시 팀 생성 창 숨기기
	        $('#Xbtn').click(function() {
	            $('#team-make-main').hide();
	        });
	
	         // 팀 생성하기 버튼과 + 팀 생성하기 버튼 클릭 시 팀 생성 창 보이기
	        $('#div_team_make_button .btn_btn_green, #div_team_make_big .btn_btn_big').click(function() {
	            $('#team-make-main').show(); 
	        });
	    });
    </script>
</head>
<body>
    <!-- 팀 생성 창 -->
    <div id="team-make-main" style="display: none;">
        <div id="team-make-header">
            <img src="img/MakeTeamXbutton.png" id="Xbtn" class="fr teamMakeImg">
        </div>
        <form action="TeamListAction.jsp">
	        <div id="team-make-content">
	            <h2>팀 생성하기</h2>
	            <div id="border"></div>
		            <fieldset>
		                <h5><span>팀 이름 (회사 또는 단체명)</span></h5>
		                <!-- 도움말 메시지 창 -->
		                <div id="tooltip" class="tooltip">나무를 사용할 회사 또는 단체의 이름을 입력해 주세요. 
		                    <br/> (나중에 변경 가능)</div>
		                <input type="text" name="teamName" placeholder="팀 이름 (회사 또는 단체명)" autocomplete="off" maxlength="20" autofocus class="green_border">
		                
		                <h5 id="h5_team_domain"><span>팀 도메인</span></h5>
		                <input type="text" name="teamDomain" id="input_team_domain" placeholder="팀 도메인" autocomplete="off" maxlength="20" autofocus class="green_border">
		                <!-- 도움말 메시지 창 -->
		                <div id="tooltip2" class="tooltip">영어 5자 이상 입력해 주세요. <br/> (나중에 변경 가능)</div>
		                
		                <div id="div_sub_fix">.jandi.com</div>
		            </fieldset>
	            </div>
	            <!-- 나무 시작하기 버튼 -->
	            <div id="div_button">
	                <button type="submit" disabled id="button">팀으로 이동하기</button>
	            </div>
            </form>
        </div>
    <div id="div_header">
        <div id="div_logo" class="fl"><img src="img/namooCompanyLogo.png"/></div>
        <div id="div_email" class="fr">
        
		    <% 
		    	if (memberDto != null) { 
		    %>
		        <img src="<%= memberDto.getProfilePicUrl() != null ? memberDto.getProfilePicUrl() : "default_profile.png" %>">
		        <span id="select_email"><%= memberDto.getMemberName() != null ? memberDto.getMemberName() : "Unknown" %></span>
		    <% } else { %>
		        <img src="https://jandi-box.com/assets/ic-profile.png">
		        <span id="select_email">Unknown</span>
		    <% } %>
            <div class="dropdown_menu profile_dropdown" style="display: none; visibility: visible;">
                <h5 class="option-title">
                    <span class="option-txt">
                        <span>프로필</span>
                    </span>
                </h5>
                <ul class="option-list">
                    <li class="option-item">
                        <i class="option-icon icon-sign-out">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="24px" fill="#999"><path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z"/></svg>                            
                        </i>
                        <div class="option-txt">
                            <span class="settings">계정 설정</span>
                        </div>
                    </li>
                    <li class="option-item">
                        <i class="option-icon icon-sign-out">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="24px" fill="#999"><path d="M200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h280v80H200v560h280v80H200Zm440-160-55-58 102-102H360v-80h327L585-622l55-58 200 200-200 200Z"/></svg>
                        </i>
                        <span class="option-txt">
                            <span class="logout">로그아웃</span>
                        </span>
                    </li>
                </ul>
            </div>
        </div>
        <div id="div_border" class="fr"></div>
        <div id="div_lang" class="fr">
            <a id="select_language">한국어</a>
        </div>
        <div style="clear:both;"></div>
    </div> 
    <!-- 전체 -->
    <div id="div_main">
        <!-- 윗부분 -->
        <div id="div_profile">
            <!-- 프로필 텍스트 -->
            <div id="div_profile_txt" class="fl">
                <span>프로필</span>
                <!-- 프사, 이름, 이메일, 계정 설정 -->
                <div id="div_user_profile">
                    <div id="div_user_img">
                        <img src="<%=memberDto.getProfilePicUrl()%>">
                    </div>
                    <div id="div_user_name">
                        <span><%=memberDto.getMemberName() %></span>
                        <div id="div_email_txt">
                            <span><%=memberDto.getEmail() %></span>
                        </div>
                    </div>
                    <div id="div_button_setting" class="fr">
                        <button type="button" class="btn_profile_edit" ng-click="onProfileEditClick()">
                            <div id="div_pencil" class="fl">
                                <img src="img/pencill.png">
                            </div>
                            <div id="div_set_account">
                                <span>계정 설정</span>
                            </div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="div_team_list_main">
            <div id="div_team_list_txt">
                <span>팀 리스트</span>
            </div>
            <div class="section-body">
	            <%
	            if(list == null || list.size() == 0) {
	            %>
                <div id="div_main_pic"> <!-- 생성된 팀 리스트가 한 개라도 있으면 display: none 처리 -->
                    <div id="div_img_txt">
                        <img src="https://cdn.jandi.com/main/assets/images/ae79d593.bg_welcome.png">
                        <div id="div_main_txt_button" class="fr">
                            <div id="div_txt1">
                                <span>잔디는 파일 공유가 손쉬운 업무용 메신저입니다.</span>
                            </div>
                            <div id="div_txt2">
                                <span>지금 무료로 팀을 개설하시고 훨씬 빠르고 효율적인 업무 환경을 경험하세요!</span>
                            </div>
                            <div id="div_team_make_button">
                                <button type="button" class="btn_btn_green" ng-click="createTeam()" translate="">
                                    <span>팀 생성하기</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                }
                %>
                <%
                	for(TeamListDto tDto : list) {
                %>
                <ul class="main-team-list">
                    <li class="team-list-li">
                        <dl class="team-card">
                            <dt>
                                <span class="team-name"><%=tDto.getTeamName() %></span>
                            </dt>
                            <dd><%=tDto.getDomain() %></dd>
                            <dd class="logo-container">
                                <img src="<%=tDto.getTeamImage()%>">
                            </dd>
                        </dl>
                        <div class="btn-box position">
                            <button type="button" class="btn btn-blue">
                                <span>팀으로 가기</span>
                            </button>
                        </div>
                    </li>
                </ul>
                <%
                	}
                %>
                <div id="div_team_make_big">
                    <button type="button" class="btn_btn_big">
                        <span>+ 팀 생성하기</span>
                    </button>
                </div>
            </div>
        </div>
        <div id="div_footer">
            <div id="div_inner_footer">
                <div id="div_toss_lab" class="fl">
                    <span>ⓒ Toss Lab, Inc.</span>
                </div>
                <div id="div_three_txt" class="fr">
                    <div id="div_QnA" class="fl">
                        <a href="https://support.jandi.com" target="_blank" translate="">
                            <span class="ng-scope">자주 묻는 질문</span></a>
                    </div>
                    <div id="div_private" class="fl">
                        <a href="https://www.jandi.com/landing/kr/private/policy" target="_blank" translate="">
                            <span class="ng-scope">개인정보처리방침</span></a>
                    </div>
                    <div id="div_service" class="fl">
                        <a href="https://www.jandi.com/landing/kr/terms/service" target="_blank" translate="">
                            <span class="ng-scope">이용 약관</span></a>
                    </div>
                </div>
                <div style="clear: both;"></div>
            </div>
        </div>
    </div>
</body>
</html>
