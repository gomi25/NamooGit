<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>플로우 피드</title>
    <link rel="stylesheet" href="css/Todo.css"/>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        // 스크립트 쓰는 곳 
    </script>
</head>
<body>
    <div id="allMainContent" class="main-wrap">
        <div class="main-top">
            <header class="header">
                <div class="main-search-area cursor-pointer">
                    <form id="searchPopupTopButton" class="main-search clear-fix">
                        <img src="img/search-icon-white.png" style="margin-right: 10px;">
                        <div class="main-search-box">
                            <input type="text" class="cursor-pointer js-main-search-box-placeholder" placeholder="검색" readonly>
                        </div>
                        <button type="button" class="js-detail-search detail-search-button">
                            옵션 
                        </button>
                    </form>
                </div>
            </header>
            <div id="mainBodyWrap" class="main-body-wrap">
                <!-- 투두 할일 추가창 -->
                <div class="flow-all-background-1 back-area temp-popup" style="display: none;">
                    <div class="flow-project-make-1 back-area">
                        <div class="flow-project-make-2 back-area contents">
                            <div class="edit-item create-post-wrap">
                                <div class="create-post-header">
                                    <div class="temp-wrap">
                                        <h4 class="create-post-title">게시물 작성</h4>
                                    </div>
                                    <button id="minimizationBtn" type="button" class="btn-minimize" style="display: block;">
                                        <i class="icons-normal">
                                            <svg xmlns="http://www.w3.org/2000/svg" height="22px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M200-440v-80h560v80H200Z"/></svg>
                                        </i>
                                    </button>
                                    <button type="button" class="btn-close">
                                        <i class="icons-close-1">
                                            <svg xmlns="http://www.w3.org/2000/svg" height="22px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224-224 224Z"/></svg>
                                        </i>
                                    </button>
                                </div>
                                <div class="create-post-container scroll-mask">
                                    <div>
                                        <fieldset>
                                            <div class>
                                                <input id="postTitle" type="text" title="제목을 입력하세요" placeholder="제목을 입력하세요." autocomplete="off" class="create-title-input" maxlength="200" data-empty-msg="제목을 입력하세요" value="">
                                            </div>
                                            <div id="postCntn" class="create-content-area">
                                                <ul id="todoEditUl" class="todo-group ui-sortable"></ul>
                                                <div class="subtask-input-area todo-area">
                                                    <input id="todoContent" type="text" placeholder="할 일 추가 (Enter 또는 Tab) / 최대 60자" autocomplete="off" maxlength="60" data-empty-msg="할 일을 입력하세요">
                                                    <div class="todo-menu">
                                                        <span class="subtask-button">
                                                            <input type="hidden" class="flatpickr flatpickr-input" readonly="readonly">
                                                            <span class="create-icon-box">
                                                                <i class="icons-calendar">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M200-80q-33 0-56.5-23.5T120-160v-560q0-33 23.5-56.5T200-800h40v-80h80v80h320v-80h80v80h40q33 0 56.5 23.5T840-720v560q0 33-23.5 56.5T760-80H200Zm0-80h560v-400H200v400Zm0-480h560v-80H200v80Zm0 0v-80 80Z"/></svg>
                                                                </i>
                                                            </span>
                                                        </span>
                                                        <button id="todoWorkerButton" type="button" class="subtask-button">
                                                            <span class="create-icon-box">
                                                                <i class="icons-person-6">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M480-480q-66 0-113-47t-47-113q0-66 47-113t113-47q66 0 113 47t47 113q0 66-47 113t-113 47ZM160-160v-112q0-34 17.5-62.5T224-378q62-31 126-46.5T480-440q66 0 130 15.5T736-378q29 15 46.5 43.5T800-272v112H160Zm80-80h480v-32q0-11-5.5-20T700-306q-54-27-109-40.5T480-360q-56 0-111 13.5T260-306q-9 5-14.5 14t-5.5 20v32Zm240-320q33 0 56.5-23.5T560-640q0-33-23.5-56.5T480-720q-33 0-56.5 23.5T400-640q0 33 23.5 56.5T480-560Zm0-80Zm0 400Z"/></svg>
                                                                </i>
                                                            </span>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                                <div class="create-post-footer clear-fix">
                                    <div class="create-footer-menu">
                                        <div class="priviate-button create-submit-option full" style="display: inline-block;">
                                            전체 공개
                                        </div>
                                        <button class="create-button create-post-submit" style="display: inline-block;">등록</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="leftArea" class="main-header-1">
                    <div id="leftTask" class="left-task active">
                        <div class="logo-area logo-display-wrap" style="display: block;">
                            <a class="js-logo logo-box">
                                <h1 class="logo-1">
                                    <img src="https://flow.team/flow-renewal/assets/images/logo/logo-flow.svg">
                                </h1>
                            </a>
                        </div>
                        <a href="https://flow.team/main.act?dashboard" class="new-project-1"> <!-- 현지 님한테 화면 받아야 함 -->
                            <div id="projectMakeButton" class="new-project-1">
                                <div class="button-suport-1"></div>
                                새 프로젝트
                            </div>
                        </a>
                        <ul id="leftMenuUl" class="menu-group js-left-menu mgt-20">
                            <li class="left-menu-item">
                                <a href="https://flow.team/main.act?dashboard"> <!-- 현지님한테 화면 받아야 함! -->
                                    <i class="icon-home">
                                        <img src="img/ico-home.png">
                                    </i>
                                    대시보드
                                </a>
                            </li>
                            <li class="my-project left-menu-item">
                                <a href="https://flow.team/main.act?project">
                                    <i class="icon-home">
                                        <img src="img/icon-my-project.png">
                                    </i>
                                    내 프로젝트
                                </a>
                            </li>
                        </ul>
                        <ul id="leftScroll" class="menu-accordion-group scroll-mask thin">
                            <li>
                                <div class="menu-accordion-button active left-menu-item">
                                    모아보기
                                </div>
                                <div class="menu-accordion mg-30" style="display: block;">
                                    <ul class="menu-accordion-list">
                                        <li class="left-menu-item">
                                            <a href="#">
                                                <i class="icon-ganttchart">
                                                    <img src="img/ganttchart.png">
                                                </i>
                                                간트차트
                                            </a>
                                        </li>
                                        <li class="left-menu-item">
                                            <a href="https://flow.team/main.act?bookmark">
                                                <i class="icon-home">
                                                    <img src="img/bookmark.png"></img>
                                                </i>
                                                북마크
                                            </a>
                                        </li>
                                        <li class="left-menu-item">
                                            <a href="https://flow.team/main.act?mention">
                                                <i class="icon-home">
                                                    <img src="img/mention.png">
                                                </i>
                                                나를 언급
                                            </a>
                                        </li>
                                        <li class="left-menu-item">
                                            <a href="https://flow.team/main.act?mention">
                                                <i class="icon-home">
                                                    <img src="img/my-post.png">
                                                </i>
                                                내 게시물
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div> 
                </div>
                <!-- T자 제외 메인 컨테이너 -->
                <div class="main-container">
                    <div id="topSettingBar" class="main-header js-main-top" style="display: block">
                        <div id="detailTop" class="project-detail title-1" style="display: block;">
                            <div class="project-detail-header">
                                <div id="ProjectTopArea" style="display: block;">
                                    <div class="project-color-area">
                                        <i id="projectColor" class="project-color color-code-2"></i>
                                    </div>
                                    <div class="project-header-group">
                                        <div class="project-title-area">
                                            <div class="project-option-area">
                                                <button id="projectStar" class="bookmark-button unstar">
                                                    <span class="blind">즐겨찾기</span>
                                                </button>
                                                <button id="detailSettingTopButton" class="set-btn">
                                                    <span></span>
                                                    <span></span>
                                                    <span></span>
                                                </button>
                                                <h3 id="projectTitle" class="project-title ellipsis js-mouseover" mouseover-text="[플로우] namoo 전용 문의방">[플로우] namoo 전용 문의방</h3>
                                            </div>
                                        </div>
                                        <div class="project-description" style="display: block;">
                                            <p id="projectContents" class="description-text js-mouseover" data-tooltip-linebreak="true" mouseover-text="플로우 운영자에게 문의를 남기는 공간입니다🍀">플로우 운영자에게 문의를 남기는 공간입니다🍀</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <button id="openInviteLayerBtn" type="button" class="project-invite-button color-code-1" style="display: block;">
                                <i class="icons-invite">
                                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M720-400v-120H600v-80h120v-120h80v120h120v80H800v120h-80Zm-360-80q-66 0-113-47t-47-113q0-66 47-113t113-47q66 0 113 47t47 113q0 66-47 113t-113 47ZM40-160v-112q0-34 17.5-62.5T104-378q62-31 126-46.5T360-440q66 0 130 15.5T616-378q29 15 46.5 43.5T680-272v112H40Zm80-80h480v-32q0-11-5.5-20T580-306q-54-27-109-40.5T360-360q-56 0-111 13.5T140-306q-9 5-14.5 14t-5.5 20v32Zm240-320q33 0 56.5-23.5T440-640q0-33-23.5-56.5T360-720q-33 0-56.5 23.5T280-640q0 33 23.5 56.5T360-560Zm0-80Zm0 400Z"/></svg>
                                </i>
                                <span class="invite-txt">초대하기</span>
                            </button>
                        </div>
                    </div>
                    <div id="detailTimeline" class="project-detail-inner layer-scroll type2 survey borderMargin bcGrey" style="display: block;">
                        <div class="js-detail-wrap-area small-style-wrap">
                            <section id="surveyPostTimeline" class="project-detail-container" style="display: none; z-index: -1;"></section>
                            <section id="postTimeline" class="project-detail-container" style="display: block;">
                                <div class="project-detail-content">
                                    <div id="taskReportArea"></div>
                                    <div id="createPosrArea" class="work-design-wrapper">
                                        <ul id="createPostUl" class="work-design-group">
                                            <li class="post-filter" data-post-code="91">
                                                <i class="icons-write2 mg-5">
                                                    <img src="img/post-icon.png">
                                                </i>
                                                <span>할 일을 추가하세요.</span>
                                            </li>
                                        </ul>
                                        <div class="work-design-element">
                                            <p class="work-design-text">내용을 입력하세요.</p>
                                            <div class="js-work-icon-group work-icon-group">
                                                <i class="icons-file mg-10">
                                                    <img src="img/file-icon.png">
                                                </i>
                                                <i class="icons-picture mg-10">
                                                    <img src="img/picture-icon.png">
                                                </i>
                                                <i class="icons-font mg-10">
                                                    <img src="img/text-icon.png">
                                                </i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 미확인 -->
                                <div id="projectAlarmArea" class="detail-section unidentified-alert-section d-none" style="display: block;">
                                    <div class="section-title-area">
                                        <h4 class="section-title">
                                            <span>미확인</span>
                                            <span id="projectNotReadCount" class="section-number alarm" style="display: inline-block;">1</span>
                                        </h4>
                                    </div>
                                </div>
                                <div class="unidentified-content">
                                    <ul id="notReadAlarmUl" class="unidentified-list">
                                        <li id="unread" class="not-read-alarm-item">
                                            <div class="unidentified-item profile">
                                                <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                            </div>
                                            <div class="middle-wr">
                                                <div class="unidentified-item title">
                                                    <em class="unidentified-name">
                                                        <i class></i>
                                                        서태웅님의 댓글 등록
                                                    </em>
                                                    <span class="unidentified-time">방금 전</span>
                                                </div>
                                                <div class="unidentified-item task">
                                                    <div class="unidentified-task-title d-none"></div>
                                                    <div class="unidentified-task-content">
                                                        <span style="display:block;">미확인 창 추가해야 됨</span>
                                                        <ul class="unidentified-file-group"></ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="unidentified-item button">
                                                <button type="button" class="unidentified-detail-btn">보기</button>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <!-- 상단 고정 부분 -->
                                <div id="projectPinArea" class="detail-section fix-section d-none" style="display: block;">
                                    <div class="section-title-area">
                                        <h4 class="section-title">
                                            <span>상단고정</span>
                                            <span id="projectPinCount" class="section-number fontSize-18">2</span>
                                        </h4>
                                    </div>
                                </div>
                                <!-- 상단 고정 리스트 -->
                                <ul id="pinPostUl" class="ui-sortable pin-list fixed-list">
                                    <li id="pin-1" class="js-pin-item" index="2">
                                        <a href="#">
                                            <div class="fixed-kind mgr-20">
                                                <i class="icons-write2 mg-5">
                                                    <img src="img/icons-write2.png">
                                                </i>
                                            </div>
                                            <div class="fixed-kind">
                                                <p class="js-post-title fixed-text">
                                                    <img src="img/icon-pin.png" class="mgr-10">
                                                    📢 개인적인 업무는 다른 프로젝트에 올려주세요
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li id="pin-2" class="js-pin-item" index="2">
                                        <a href="#">
                                            <div class="fixed-kind mgr-20">
                                                <i class="icons-write2 mg-5">
                                                    <img src="img/icons-write2.png">
                                                </i>
                                            </div>
                                            <div class="fixed-kind">
                                                <p class="js-post-title fixed-text">
                                                    <img src="img/icon-pin.png" class="mgr-10">
                                                    📢 기업 전용 문의방 사용 안내
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                                <!-- 전체 피드 -->
                                <div id="projectFeedArea" class="detail-section feed-section">
                                    <div class="section-title-area">
                                        <div class="tit-type-area">
                                            <h4 id="allPostsFilterTitle" class="section-title ellipsis">전체</h4>
                                            <span class="filter-reset js-filter-reset" style="display: none;">취소</span>
                                        </div>
                                    </div>
                                    <div class="feed-content">
                                        <ul id="detailUl" class="post-group card">
                                            <!-- ** 님이 ** 님을 초대했습니다! -->
                                            <div class="invite-text-area" style="min-height: 1px;">
                                                <span>김현지님이 강하늘님을 초대했습니다.</span>
                                                <span>2024-06-19 18:07</span>
                                                <span class="invite-time"></span>
                                            </div>
                                            <li id="post-45408792" class="js-popup-before detail-item back-area" data-read-yn="Y" data-project-srno="2281074" data-post-srno="45408792" data-task-srno="" data-remark-srno="" data-section-srno="" data-vote-srno="" data-rgsr-id="0716@ruru.be" data-rgsr-nm="강하늘" data-rgsn-dttm="" data-commt-ttl="6/12 투두리스트 화면 개발" home-tab="FEED" mngr-wryn="N" mngr-dsnc="N" data-post-code="2" pin-yn="N" status="" todo-done-percent="0%" start-date-time="" end-date-time="" all-day-yn="" data-code="VIEW" data-post-url="https://flow.team/l/b1lMw" calendar-id="" anoymous-yn="N" official-yn="" owner="" organizer-yn="" data-repeat-id="" data-repeat-dttm="" reminder-yn="N" reminder-deadline-yn="N" style="min-height: 1px;">
                                                <div class="js-post-nav card-item post-card-wrapper todo">
                                                    <div class="post-card-header">
                                                        <div class="post-card-scroll">
                                                            <div class="card-header-top">
                                                                <div class="post-author js-post-author" data-author-id="0716@ruru.be">
                                                                    <dl class="post-author-info">
                                                                        <dt>
                                                                            <div class="profile-img">
                                                                                <img src="https://flow.team/flow-renewal/assets/images/profile-default.png" class="writer-user-img">
                                                                            </div>
                                                                            <div class="author ellipsis">강하늘</div>
                                                                            <em class="position ellipsis" style="display:block" data=""></em>
                                                                            <div class="date">2024-06-14 11:16</div>
                                                                            <div class="public-person">
                                                                                <img src="img/public-prson-icon.png" style="margin-left: 10px">
                                                                            </div>
                                                                            <span class="post-security"> 
                                                                                <i class="icons-person-7 js-mouseover" mouseover-text="전체 공개"></i>
                                                                            </span>
                                                                        </dt>
                                                                    </dl>
                                                                </div>
                                                                <div>
                                                                    <div class="post-option">
                                                                        <button id="pinToTopBnt" class="js-pin-post fixed-btn js-pin-authority " style="display:block" data="">
                                                                            <img src="img/pin-icons.png" style="margin-top: 5px;">
                                                                            <span class="blind">상단 고정 등록</span>
                                                                        </button>
                                                                        <button class="js-setting-button set-btn">
                                                                            <span></span>
                                                                            <span></span>
                                                                            <span></span>
                                                                        </button>
                                                                    </div>
                                                                    <!-- 글 수정 기능 등등 -->
                                                                    <ul class="setup-group d-none" style="display: block;">
                                                                        <li class="js-setting-item" style="display: block;">
                                                                            <a href="#">
                                                                                <i class="post-edit-icon"></i>
                                                                                수정
                                                                            </a>
                                                                        </li>
                                                                        <li class="js-setting-item" style="display: block;">
                                                                            <a href="#">
                                                                                <i class="icons-copy">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M360-240q-33 0-56.5-23.5T280-320v-480q0-33 23.5-56.5T360-880h360q33 0 56.5 23.5T800-800v480q0 33-23.5 56.5T720-240H360Zm0-80h360v-480H360v480ZM200-80q-33 0-56.5-23.5T120-160v-560h80v560h440v80H200Zm160-240v-480 480Z"/></svg>
                                                                                </i>
                                                                                <p class="icons-txt">링크 복사</p>
                                                                            </a>
                                                                        </li>
                                                                        <li class="js-setting-item" style="display: block;">
                                                                            <a href="#">
                                                                                <i class="icons-delete">
                                                                                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#5f6368"><path d="M280-120q-33 0-56.5-23.5T200-200v-520h-40v-80h200v-40h240v40h200v80h-40v520q0 33-23.5 56.5T680-120H280Zm400-600H280v520h400v-520ZM360-280h80v-360h-80v360Zm160 0h80v-360h-80v360ZM280-720v520-520Z"/></svg>
                                                                                </i>
                                                                                <p class="icons-txt">삭제</p>
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                            <div class="js-card-header-bottom card-header-bottom sticky-title">
                                                                <div class="post-title-area">
                                                                    <div class="js-post-title post-title">6/12 투두리스트 화면 개발</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="post-card-container">
                                                    <div id="originalPost" class="post-card-content" style="display: block;">
                                                        <div class="js-todo-status todo-progress-area todo-status-component">
                                                            <div class="todo-progress-header">
                                                                <div class="progress-header-left">
                                                                    <span id="progressCount" class="progress-count">0</span>
                                                                    <span id="progressTotalCount" calss="progress-total">/ 3</span>
                                                                </div>
                                                            </div>
                                                            <div class="todo-progress-bar">
                                                                <span style="width:0%;"></span>
                                                            </div>
                                                        </div>
                                                        <ul id="todoUl" class="js-todo-status js-todo-component todo-group todo-status-component">
                                                            <li class="todo-item">
                                                                <div class="subtask-input-area todo-area">
                                                                    <p class="todo-text checked">
                                                                        <!-- 새로 갱신될 때마다 input과 label의 id 값이 바뀌어야 함 -->
                                                                        <input type="checkbox" id="itemCheckBox1" style="display: none;">
                                                                        <label for="itemCheckBox1"></label>
                                                                        <div class="todo-item-text">개발</div>
                                                                    </p>
                                                                    <div class="todo-menu">
                                                                        <span id="todoDateSpan" class="todo-date js-mouseover deadline-exceeded">6/14</span>
                                                                        <span id="participantData" class="js-mouseover">
                                                                            <div class="profile-img">
                                                                                <img src="https://flow.team/flow-renewal/assets/images/profile-default.png?v=ca8b336a82f1b918b87e5ec6064ababacb688801" class="part-in-member">
                                                                            </div>
                                                                            <div class="tooltip">강하늘</div>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                        <ul id="todoUl" class="js-todo-status js-todo-component todo-group todo-status-component">
                                                            <li class="todo-item">
                                                                <div class="subtask-input-area todo-area">
                                                                    <p class="todo-text checked">
                                                                        <input type="checkbox" id="itemCheckBox2" style="display: none;">
                                                                        <label for="itemCheckBox2"></label>
                                                                        <div class="todo-item-text">개발</div>
                                                                    </p>
                                                                    <div class="todo-menu">
                                                                        <span id="todoDateSpan" class="todo-date js-mouseover deadline-exceeded">6/14</span>
                                                                        <span id="participantData" class="js-mouseover">
                                                                            <div class="profile-img">
                                                                                <img src="https://flow.team/flow-renewal/assets/images/profile-default.png?v=ca8b336a82f1b918b87e5ec6064ababacb688801" class="part-in-member">
                                                                            </div>
                                                                            <div class="tooltip">강하늘</div>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                        <ul id="todoUl" class="js-todo-status js-todo-component todo-group todo-status-component">
                                                            <li class="todo-item">
                                                                <div class="subtask-input-area todo-area">
                                                                    <p class="todo-text checked">
                                                                        <input type="checkbox" id="itemCheckBox" style="display: none;">
                                                                        <label for="itemCheckBox"></label>
                                                                        <div class="todo-item-text">개발</div>
                                                                    </p>
                                                                    <div class="todo-menu">
                                                                        <span id="todoDateSpan" class="todo-date js-mouseover deadline-exceeded">6/14</span>
                                                                        <span id="participantData" class="js-mouseover">
                                                                            <div class="profile-img">
                                                                                <img src="https://flow.team/flow-renewal/assets/images/profile-default.png?v=ca8b336a82f1b918b87e5ec6064ababacb688801" class="part-in-member">
                                                                            </div>
                                                                            <div class="tooltip">강하늘</div>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <!-- 좋아요, 북마크, 댓글 수, 읽음 수 -->
                                                    <div class="post-bottom-area">
                                                        <div class="post-bottom-menu">
                                                            <div class="emoji-area" style="display: block;">
                                                                <ul class="emoji-group">
                                                                    <li>
                                                                        <i class="icon-emoji like">
                                                                            <span class="blind">좋아요</span>
                                                                        </i>
                                                                    </li>
                                                                </ul>
                                                                <span class="emoji-count-area">
                                                                    <span class="emoji-txt">강하늘</span>
                                                                    <span class="emoji-count"></span>
                                                                </span>
                                                            </div>
                                                            <div class="bottom-button-area">
                                                                <button class="on post-bottom-button">
                                                                    <i class="icon-reaction"></i>
                                                                    <span>좋아요</span>
                                                                </button>
                                                                <!-- 투두 글 제목 이모티콘 기능 -->
                                                                <div class="emoji-select-group" style="display: none;">
                                                                    <ul>
                                                                        <li class="add-reaction">
                                                                            <a href="#" role="button">
                                                                                <i class="icon-emoji like"></i>
                                                                                <p class="emoji-text">좋아요</p>
                                                                            </a>
                                                                        </li>
                                                                        <li class="add-reaction">
                                                                            <a href="#" role="button">
                                                                                <i class="icon-emoji please"></i>
                                                                                <p class="emoji-text">부탁해요</p>  
                                                                            </a>
                                                                        </li>
                                                                        <li class="add-reaction">
                                                                            <a href="#" role="button">
                                                                                <i class="icon-emoji sad"></i>
                                                                                <p class="emoji-text">힘들어요</p>  
                                                                            </a>
                                                                        </li>
                                                                        <li class="add-reaction">
                                                                            <a href="#" role="button">
                                                                                <i class="icon-emoji great"></i>
                                                                                <p class="emoji-text">훌륭해요</p>  
                                                                            </a>
                                                                        </li>
                                                                        <li class="add-reaction">
                                                                            <a href="#" role="button">
                                                                                <i class="icon-emoji thank"></i>
                                                                                <p class="emoji-text">감사해요</p>  
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <button class="post-bottom-button on">
                                                                    <i class="icon-bookmark"></i>
                                                                    <span>북마크</span>
                                                                </button>
                                                            </div>
                                                            <div class="cmt-read-wr">
                                                                <div class="comment-count-area">
                                                                    <span>댓글</span>
                                                                    <span class="comment-count">7</span>
                                                                </div>
                                                                <div class="read-confirmation" style="display:block;">
                                                                    <span>읽음</span>
                                                                    <span class="confirmation-number">2</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- 댓글 부분 -->
                                                <div class="post-card-footer">
                                                    <div class="comment-header">
                                                        <buttom type="button" class="comment-more-button on mgl-30">이전 댓글 더보기 (5) </buttom>
                                                        <ul class="post-comment-group">
                                                            <li class="reamrk-item reply-wrap">
                                                                <div class="comment-thumbnail">
                                                                    <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                                                </div>
                                                                <div class="comment-container on">
                                                                    <div class="comment-user-area">
                                                                        <div class="comment-user">
                                                                            <span class="user-name">강하늘</span>
                                                                            <span class="user-position"></span>
                                                                            <span class="record-date">2024-06-19 10:44</span>
                                                                        </div>
                                                                        <div class="comment-writer-menu">
                                                                            <button type="button" class="comment-writer-button on">수정</button>
                                                                            <button type="button" class="comment-writer-button on">삭제</button>
                                                                        </div>
                                                                    </div>
                                                                    <div class="comment-content">
                                                                        <div class="comment-text-area">
                                                                            <div class="comment-text">
                                                                                <div>'플로우 전체 구조... 프로젝트 > 피드', 항목 완료!</div>
                                                                            </div>
                                                                        </div>
                                                                        <ul class="upload-document-group"></ul>
                                                                        <ul class="comment-upload-img"></ul>
                                                                    </div>
                                                                    <div class="comment-like">
                                                                        <span class="js-remark-like-button">
                                                                           <em class="txt-like">좋아요</em> 
                                                                        </span>
                                                                        <span class="comment-like-count">0</span>
                                                                    </div>
                                                                </div>
                                                            </li>
        
                                                            <li class="reamrk-item reply-wrap">
                                                                <div class="comment-thumbnail">
                                                                    <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                                                </div>
                                                                <div class="comment-container on">
                                                                    <div class="comment-user-area">
                                                                        <div class="comment-user">
                                                                            <span class="user-name">강하늘</span>
                                                                            <span class="user-position"></span>
                                                                            <span class="record-date">2024-06-19 10:44</span>
                                                                        </div>
                                                                        <div class="comment-writer-menu">
                                                                            <button type="button" class="comment-writer-button on">수정</button>
                                                                            <button type="button" class="comment-writer-button on">삭제</button>
                                                                        </div>
                                                                    </div>
                                                                    <div class="comment-content">
                                                                        <div class="comment-text-area">
                                                                            <div class="comment-text">
                                                                                <div>'플로우 전체 구조... 프로젝트 > 피드', 항목 완료!</div>
                                                                            </div>
                                                                        </div>
                                                                        <ul class="upload-document-group"></ul>
                                                                        <ul class="comment-upload-img"></ul>
                                                                    </div>
                                                                    <div class="comment-like on">
                                                                        <span class="js-remark-like-button">
                                                                           <em class="txt-like">좋아요 취소</em> 
                                                                        </span>
                                                                        <span class="comment-like-count">0</span>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                        <!-- 다음 댓글 더보기 -->
                                                        <div class="comment-footer">
                                                            <button type="button" class="comment-more-button"> 다음 댓글 더보기 (0) </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="comment-input-wrap">
                                                    <div class="comment-thumbnail">
                                                        <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                                    </div>
                                                    <form class="comment-container on" spellcheck="false">
                                                        <fieldset>
                                                            <legend class="blind">댓글 입력</legend>
                                                            <div class="comment-input" contenteditable="true" data-placeholder="줄바꿈 Shift + Enter / 입력 Enter 입니다."></div>
                                                            <label mouseover-text="파일 첨부" class="comment-upload-button" style="border: none;">
                                                                <img src="img/file-box-icon.png">
                                                            </label>
                                                        </fieldset>
                                                    </form>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </section>
                            <!-- 참여자 리스트 -->
                            <div class="participants-section">
                                <div id="projectParticipants" class="project-participants-wrap feed-section" style="transform: translateX(0px);">
                                    <div class="section-title-area">
                                        <h4 class="section-title">
                                            <span id="participantSpan">참여자</span>
                                            <span id="participantCount">2</span>
                                        </h4>
                                        <div class="feed-type-area">
                                            <button id="allSendienceBtn" class="btn-view-all" type="button">
                                                전체 보기
                                            </button>
                                        </div>
                                    </div>
                                    <div id="participantArea" class="participants-container">
                                        <div id="participantScrollArea" class="participants-content-group scroll-mask">
                                            <div id="joinParticipantsArea" class="participants-content d-none" style="display: none;">
                                                <span class="participants-title">
                                                    <em>가입 신청자</em>
                                                    <span id="joinParticipantsCount" class="number-of-participants">0</span>
                                                </span>
                                                <ul id="joinParticipantsUl" class="participants-list"></ul>
                                            </div>
                                            <ul id="participantsUl" class="participants-list">
                                                <span class="participants-title">
                                                    <em>프로젝트 관리자</em>
                                                    <span id="managerParticipantsCount" class="number-of-participants">1</span>
                                                </span>
                                                <li class="participant-item-li">
                                                    <div class="post-author">
                                                        <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                                        <dl class="post-author-info">
                                                            <dt>
                                                                <strong class="author ellipsis">김현지</strong>
                                                                <em class="position ellipsis" style="display: flex;"></em>
                                                            </dt>
                                                            <dd style="display: flex;">
                                                                <strong class="company">namoo</strong>
                                                                <span class="team">
                                                            </dd>
                                                        </dl>
                                                        </span>
                                                    </div>
                                                    <button type="button" class="participant-chat-button">
                                                        <i class="icons-chat" style="display: block;">
                                                            <img src="img/todo-chat-icon.png">
                                                            <span class="blind">채팅</span>
                                                        </i>
                                                    </button>
                                                </li>
                                                <span class="participants-title">
                                                    <em>임직원</em>
                                                    <span id="innerParticipantsCount" class="number-of-participants">1</span>
                                                </span>
                                                <li class="participant-item-li">
                                                    <div class="post-author">
                                                        <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                                        <dl class="post-author-info">
                                                            <dt>
                                                                <strong class="author ellipsis">강하늘 (나) </strong>
                                                                <em class="position ellipsis" style="display: flex;"></em>
                                                            </dt>
                                                            <dd style="display: flex;">
                                                                <strong class="company">namoo</strong>
                                                                <span class="team">
                                                            </dd>
                                                        </dl>
                                                    </div>
                                                    <button type="button" class="participant-chat-button">
                                                        <i class="icons-chat" style="display: block;">
                                                            <img src="img/todo-chat-icon.png">
                                                            <span class="blind">채팅</span>
                                                        </i>
                                                    </button>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 댓글 읽음, 미확인 창 -->
            <div class="flow-all-background-1 back-area temp-popup" id="redaCheckPopup" style="display: none;">
                <div class="flow-project-make-1 back-area">
                    <div class="flow-project-make-2 back-area contents">
                        <div class="read-confirmation name-type-seach-popup-type-1">
                            <div class="name-type-seach-popup-header-type-1">
                                <span>읽음확인</span>
                                <button class="btn-close">
                                    <i class="icons-close-1">
                                        <img src="img/btn-close.png">
                                    </i>
                                </button>
                            </div>
                            <div class="team-wrap-invite-type-1">
                                <a href="#" id="show-read">
                                    <!-- type-1이 미선택 시... type-2가 선택 시... -->
                                    <div class="team-job-invite-type-1 btn">읽음 (1)</div>
                                </a>
                                <a href="#" id="show-unread">
                                    <div class="team-job-invite-type-2 btn">미확인 (1)</div>
                                </a>
                            </div>
                            <div class="project-search">
                                <i class="icon-search">
                                    <img src="img/search-icon.png">
                                </i>
                                <input type="text" placeholder="이름으로 검색" autocomplete="off" class="project-search-input">
                            </div>
                            <!-- 읽음 멤버 리스트 -->
                            <ul class="participants-list reaction-box scroll-mask scroll-for-ie" style="display: none;">
                                <li class="reader-time">
                                    <div class="post-author">
                                        <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                        <dl class="post-author-info">
                                            <dt>
                                                <strong class="author ellipsis">강하늘</strong>
                                                <em class="position ellipsis"></em>
                                            </dt>
                                            <dd>
                                                <strong class="company ellipsis">namoo</strong>
                                                <span class="team ellipsis"></span>
                                            </dd>
                                        </dl>
                                        <div class="read-section mgtm-40">
                                            <strong id="readText">읽음</strong>
                                            <p>2024-06-20 10:44</p>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                            <!-- 미확인 멤버 리스트 -->
                            <ul class="participants-list reaction-box scroll-mask scroll-for-ie">
                                <li class="reader-time">
                                    <div class="post-author">
                                        <span class="thumbnail size40 radius16" style="background-image: url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.png), url(https://flow.team/flow-renewal/assets/images/profile-default.svg)"></span>
                                        <dl class="post-author-info">
                                            <dt>
                                                <strong class="author ellipsis">김현지</strong>
                                                <em class="position ellipsis"></em>
                                            </dt>
                                            <dd>
                                                <strong class="company ellipsis">namoo</strong>
                                                <span class="team ellipsis"></span>
                                            </dd>
                                        </dl>
                                        <div class="read-section">
                                            <strong id="readText"></strong>
                                            <p></p>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>