<%@page import="dto.AlarmGanttNoticeDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String previousDate = "";
	int teamIdx = 2; 
	//int teamIdx =(Integer)session.getAttribute("teamIdx");
	
	AlarmDao aDao = new AlarmDao();
	ArrayList<AlarmGanttNoticeDto> ganttAlarm = null;
	
	try {
		ganttAlarm = aDao.getAlarmGanttNotice(teamIdx);
	} catch (Exception e) {
        e.printStackTrace();
    }
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="../css/JndAlarm_alarm.css"/>
	<link rel="stylesheet" href="../css/JndAlarm_mention.css"/>
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/Alarm.js"></script>
	
</head>
<body>
<!-- 멘션 -->
 <!-- ng-show="isOpenToolDetail.primary" JS에서 사용하는 디렉티브.  -->
    <!-- isOpenToolDetail.primary가 true일 때만 보이고, false일 때는 위 요소가 화면에서 사라짐. -->
    <div class="panel panel-tool non-selectable toolPanel shadow" id="primary_detail_panel" style="width: 440px;">
        <div class="mention_files">
            <div class="tool-wrapper border">
                <div class="tool-list">
                    <div class="tool-head file-row-box is-alone">
                        <h3 class="tool-title flex-rel">
                            <span class="tool-title-block">알림 센터</span>
                        </h3>
                        <i class="icon-ic-close btn-close fn-20 flex-fix" style="margin-right:-45px;">
                            <img src="../img/btn-close.png" style="width:15px;">
                        </i>
                        <i class="icon-ic-setup btn-setting fn-20 flex-fix" tooltip="알림 센터 설정" tooltip-placement="bottom" tooltip-class="nowrap"
                        style="margin-right: -50px;">
                            <img src="../img/btn-setting.png" style="width:20px;  margin-top: 6px; width:20px;">
                        </i>
                    </div>
                </div>
            </div>
            <!-- 여기서부터 헤더 밑 -->
            <div class="tool-body">
                <div class="tab-head border">
                    <ul class="nav nav-tabs">
                        <li class="tool-inner-tab-mention active">
                            <a id="mentionBox">
                                <span class="menton">멘션</span>
                            </a>
                        </li>
                        <li class="tool-inner-tab">
                            <a id=alarmBox>
                                <span class="menton">알림</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- 여기서부터 탭 밑... 알림 부분은 달라져야 함 -->
                <div class="tab-content">
                    <div class="tab-pane active">
                        <div class="tool-section mention-tab" status="tabs.mention">
                            <div class="tool-result">
                                <div class="tool-result-scroll">
                                    <div class="tool-result-status">
                                        <!-- 여기서부터 반복 -->
                                        <div class="scroll-loading-list mention-list">
                                            <div class="mention-item">
                                                <div class="tool-date-divider">
                                                    <time>2024년 5월 10일 금요일</time>
                                                </div> <!--알림 박스-->
                                                <div class="blur-effect tool-card message-card" message-date="mention">
                                                    <div class="too-card-header">
                                                        <div class="tool-card-topic ellip-1">
                                                            <span class="belong-to topic-start ellip-1">공지사항</span>
                                                        </div>
                                                    </div>
                                                    <div class="message-card-header">
                                                        <div class="message-thumb-box">
                                                            <!--사용자 프로필 컨테이너-->
                                                             <div class="user-profile-container">
                                                                <!-- 사용자 프로필 이미지 -->
                                                                <img class="img-user-profile" src="	https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80">
                                                             </div>
                                                        </div>
                                                        <!-- 프로필 옆에 이름, 일시, 메시지 박스 등등 -->
                                                        <div class="message-meta">
                                                            <div class="message-title">
                                                                <span class="message-writer fn-user-name">김민지</span>
                                                                <span class="message-date">2024/05/10 AM 00:25</span>
                                                            </div>
                                                            <!-- 메시지 박스 -->
                                                             <div class="message-card-body keep selectable">
                                                                <strong class="article-title">토픽 제목</strong>
                                                                <br/>모두 멘션 알림 갔나요? 
                                                                <a class="mention">@all</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="scroll-loading-list mention-list">
                                            <div class="mention-item">
                                                <div class="tool-date-divider">
                                                    <time>2024년 5월 10일 금요일</time>
                                                </div> <!--알림 박스-->
                                                <div class="blur-effect tool-card message-card" message-date="mention">
                                                    <div class="too-card-header">
                                                        <div class="tool-card-topic ellip-1">
                                                            <span class="belong-to topic-start ellip-1">공지사항</span>
                                                        </div>
                                                    </div>
                                                    <div class="message-card-header">
                                                        <div class="message-thumb-box">
                                                            <!--사용자 프로필 컨테이너-->
                                                             <div class="user-profile-container">
                                                                <!-- 사용자 프로필 이미지 -->
                                                                <img class="img-user-profile" src="	https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80">
                                                             </div>
                                                        </div>
                                                        <!-- 프로필 옆에 이름, 일시, 메시지 박스 등등 -->
                                                        <div class="message-meta">
                                                            <div class="message-title">
                                                                <span class="message-writer fn-user-name">김민지</span>
                                                                <span class="message-date">2024/05/10 AM 00:25</span>
                                                            </div>
                                                            <!-- 메시지 박스 -->
                                                             <div class="message-card-body keep selectable">
                                                                <strong class="article-title">토픽 제목</strong>
                                                                <br/>모두 멘션 알림 갔나요? 
                                                                <a class="mention">@all</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="blur-effect tool-card message-card" message-date="mention">
                                                    <div class="too-card-header">
                                                        <div class="tool-card-topic ellip-1">
                                                            <span class="belong-to topic-start ellip-1">공지사항</span>
                                                        </div>
                                                    </div>
                                                    <div class="message-card-header">
                                                        <div class="message-thumb-box">
                                                            <!--사용자 프로필 컨테이너-->
                                                             <div class="user-profile-container">
                                                                <!-- 사용자 프로필 이미지 -->
                                                                <img class="img-user-profile" src="	https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80">
                                                             </div>
                                                        </div>
                                                        <!-- 프로필 옆에 이름, 일시, 메시지 박스 등등 -->
                                                        <div class="message-meta">
                                                            <div class="message-title">
                                                                <span class="message-writer fn-user-name">김민지</span>
                                                                <span class="message-date">2024/05/10 AM 00:25</span>
                                                            </div>
                                                            <!-- 메시지 박스 -->
                                                             <div class="message-card-body keep selectable">
                                                                <strong class="article-title">토픽 제목</strong>
                                                                <br/>모두 멘션 알림 갔나요? 
                                                                <a class="mention">@all</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="scroll-loading-list mention-list">
                                            <div class="mention-item">
                                                <div class="tool-date-divider">
                                                    <time>2024년 5월 10일 금요일</time>
                                                </div> <!--알림 박스-->
                                                <div class="blur-effect tool-card message-card" message-date="mention">
                                                    <div class="too-card-header">
                                                        <div class="tool-card-topic ellip-1">
                                                            <span class="belong-to topic-start ellip-1">공지사항</span>
                                                        </div>
                                                    </div>
                                                    <div class="message-card-header">
                                                        <div class="message-thumb-box">
                                                            <!--사용자 프로필 컨테이너-->
                                                             <div class="user-profile-container">
                                                                <!-- 사용자 프로필 이미지 -->
                                                                <img class="img-user-profile" src="	https://jandi-box.com/files-profile/6a4eaf2b7a89126e44e9eb0cc81854d6?size=80">
                                                             </div>
                                                        </div>
                                                        <!-- 프로필 옆에 이름, 일시, 메시지 박스 등등 -->
                                                        <div class="message-meta">
                                                            <div class="message-title">
                                                                <span class="message-writer fn-user-name">김민지</span>
                                                                <span class="message-date">2024/05/10 AM 00:25</span>
                                                            </div>
                                                            <!-- 메시지 박스 -->
                                                             <div class="message-card-body keep selectable">
                                                                <strong class="article-title">토픽 제목</strong>
                                                                <br/>모두 멘션 알림 갔나요? 
                                                                <a class="mention">@all</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 알림 -->
    
     <!-- ng-show="isOpenToolDetail.primary" JS에서 사용하는 디렉티브.  -->
    <!-- isOpenToolDetail.primary가 true일 때만 보이고, false일 때는 위 요소가 화면에서 사라짐. -->
    <div class="panel panel-tool non-selectable toolPanel shadow" id="primary_detail_panel" style="width: 440px;">
        <div class="files" >
            <div class="tool-wrapper border">
                <div class="tool-list">
                    <div class="tool-head file-row-box is-alone">
                        <h3 class="tool-title flex-rel">
                            <span class="tool-title-block">알림 센터</span>
                        </h3>
                        <i class="icon-ic-close btn-close fn-20 flex-fix" style="margin-right:-45px;">
                            <img src="../img/btn-close.png" style="width:15px;">
                        </i>
                        <i class="icon-ic-setup btn-setting fn-20 flex-fix" tooltip="알림 센터 설정" tooltip-placement="bottom" tooltip-class="nowrap"
                        style="margin-right: -50px;">
                            <img src="../img/btn-setting.png" style="width:20px;  margin-top: 6px; width:20px;">
                        </i>
                    </div>
                </div>
            </div>
            <!-- 여기서부터 헤더 밑 -->
            <div class="tool-body">
                <div class="tab-head border">
                    <ul class="nav nav-tabs">
                        <li class="tool-inner-tab-mention">
                            <a id="mentionBox">
                                <span class="menton">멘션</span>
                            </a>
                        </li>
                        <li class="tool-inner-tab active">
                            <a id="alarmBox">
                                <span class="menton">알림</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- 여기서부터 탭 밑... 알림 부분은 달라져야 함 -->
                <div class="tab-content">
                    <div class="tab-pane active">
                        <div class="tool-section mention-tab" status="tabs.mention">
                            <div class="tool-result">
                                <div class="tool-result-scroll">
                                    <div class="tool-result-status">
                                        <!-- 여기서부터 반복 -->
                                        <div class="scroll-loading-list mention-list">
                                        <% for( AlarmGanttNoticeDto aDto : ganttAlarm ) {%>
                                            <div class="mention-item">
                                           <% 
									            String currentDate = aDto.getAlarmDate().split(" ")[0]; // 날짜만 추출
									            if (!currentDate.equals(previousDate)) {
									        %>
                                                <div class="tool-date-divider">
                                                    <time><%=currentDate  %></time>
                                                </div> <!--알림 박스-->
                                                <% 
										            previousDate = currentDate; 
										        }
										        %>
                                                <div class="blur-effect tool-card message-card" message-date="mention">
                                                    <div class="too-card-header">
                                                        <div class="tool-card-topic ellip-1">
                                                            <%-- <span class="belong-to topic-start ellip-1"><%=aDto.getTxtName() %></span> --%>
                                                        </div>
                                                    </div>
                                                    <div class="message-card-header">
                                                        <div class="message-thumb-box">
                                                            <!--사용자 프로필 컨테이너-->
                                                             <div class="user-profile-container">
                                                                <!-- 사용자 프로필 이미지 -->
                                                                <img class="img-user-profile" src="<%=aDto.getProfilePicUrl()%>">
                                                             </div>
                                                        </div>
                                                        <!-- 프로필 옆에 이름, 일시, 메시지 박스 등등 -->
                                                        <div class="message-meta">
                                                            <div class="message-title">
                                                                <span class="message-writer fn-user-name"><%=aDto.getMemberName() %></span>
                                                                <span class="message-date"><%=aDto.getAlarmDate() %></span>
                                                            </div>
                                                            <!-- 메시지 박스 -->
                                                             <div class="message-card-body keep selectable">
                                                                <strong class="article-title"><%=aDto.getTxtName() %></strong>
                                                                <br/><%=aDto.getMemberName() %><%=aDto.getAlarmTxt() %>
                                                            </div>
                                                            <!-- <div class="todo-comment-txt">
                                                                <p style="color:blue;">할 일 완료했습니다~</p>
                                                            </div> -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <% } %>
                                        </div>
</body>
</html>