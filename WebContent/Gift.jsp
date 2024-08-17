<%@page import="dto.GiftDto"%>
<%@page import="dao.GiftDao"%>
<%@page import="dto.MemberDto"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%
    GiftDao giftDao = new GiftDao();
    ArrayList<GiftDto> productList = new ArrayList<>();
    try {
        productList = giftDao.getProductsByName();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>나무 선물하기</title>
    <link rel="stylesheet" href="css/Gift.css"/>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function(){
            // 초기 상태
            $(".main_content").css("display", "block");
            $(".coupon_box").css("display", "none");

            // tab 클릭 시 on 클래스 설정, 화면 display 속성
            $(".gnb li a").click(function(e){
                e.preventDefault();
                $(".gnb li").removeClass("on");
                $(this).parent().addClass("on");

                if ($(this).parent().hasClass("home")) {
                    $(".main_content").css("display", "block");
                    $(".coupon_box").css("display", "none");
                } else if ($(this).parent().hasClass("coupon")) {
                    $(".main_content").css("display", "none");
                    $(".coupon_box").css("display", "block");
                }
            });
            
            // 쿠폰함 탭 클릭 이벤트
            $(".coupon_box ul li").click(function(){
                $(".coupon_box ul li").removeClass("active");
                $(this).addClass("active");

                if ($(this).attr("rel") == "in_gift") {
                    $(".coupon_area").css("display", "block");
                    $(".purchase_list").css("display", "none");
                } else if ($(this).attr("rel") == "purchase") {
                    $(".coupon_area").css("display", "none");
                    $(".purchase_list").css("display", "block");
                }
            });
            
            
            $(".product-list-item").click(function() {
                var giftIdx = $(this).data("gift-idx");
                console.log("Gift Index: ", giftIdx); // 디버그 로그 추가
                // Ajax 요청
                $.ajax({
                    url: "GetProductDetail.jsp",
                    type: "GET",
                    data: {giftIdx: giftIdx},
                    success: function(response) {
                        $(".product_detail").html(response);
                        $(".main_content").css("display", "none");
                        $(".coupon_box").css("display", "none");
                        $(".product_detail").css("display", "block");
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error("AJAX Error: ", textStatus, errorThrown); // 디버그 로그 추가
                        alert("상품 상세 정보를 가져오는데 실패했습니다.");
                    }
                });
            });
        });
    </script>
</head>
<body>
    <div id="wrapper">
        <header class="gift-header">
            <div class="header_inner">
                <div class="top_header">
                    <h2 class="mvMain">선물하기</h2>
                    <button class="btn_close"></button>
                </div>
                <div class="gnb">
                    <ul>
                        <li class="on home">
                            <a href="#">홈</a>
                        </li>
                        <li style="display: none;">
                            <a href="#">카테고리</a>
                        </li>
                        <li style="display: none;">
                            <a href="#">검색</a>
                        </li>
                        <li class="coupon">
                            <a href="#">쿠폰함</a>
                        </li>
                    </ul>
                </div>
            </div>
        </header>
        <div id="container">
            <div class="main_content" style="display: ;">
                <div class="main_sw" style="display: none;">
                    <div class="sw_pc">
                        <div>
                            <img src="https://image.multicon.co.kr/ServiceCommon/admin/JANDI/Advertise/20211103191400629638.jpg"
                            class="banner_img">
                        </div>
                    </div>
                </div>
                <div class="list_area">
                    <h2>전체상품</h2>
                    <div class="list_type_a">
                        <ul>
                            <% for (GiftDto product : productList) { %>
                            <li class="product-list-item" data-gift-idx="<%= product.getGiftIdx() %>">
                                <div class="p_img">
                                    <img class="lazy" src="<%= product.getGiftImgUrl() %>" onerror="this.src='/resources/images/noImg.png'" alt="<%= product.getGiftName() %>" style="">
                                </div>
                                <div class="p_info">
                                    <p class="brand"><%= product.getBrandName() %></p>
                                    <h5><%= product.getGiftName() %></h5>
                                </div>
                                <div class="price_info">
                                    <% if (product.getReducedPrice() != null) { 
                                        int discountRate = 100 - (product.getReducedPrice() * 100 / product.getPrice());
                                    %>
                                    <span class="num discounted-price"><%= product.getPrice() %>원</span>
                                    <em class="num">
                                        <strong><%= discountRate %></strong>
                                        <i>%</i>
                                    </em>
                                    <p class="price">
                                        <strong class="num"><%= product.getReducedPrice() %></strong>
                                        <i>원</i>
                                    </p>
                                    <% } else { %>
                                    <span class="num base-price"><%= product.getPrice() %>원</span>
                                    <% } %>
                                </div>
                            </li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="product_detail" style="display: none;"></div>
            <!-- 카테고리 부분 -->
            <div class="thema_content" style="display: none;">
                <div class="thema_sw">
                    <div class="swiper-container-thema swiper-container-initialized swiper-container-horizontal">
                        <div class="swiper-wrapper thema_m" style="transform: translate3d(0px, 0px, 0px);">
                            <input type="hidden" id="idx" value="8">
                            <input type="hidden" id="page" value="1">
                        </div>
                        <div class="swiper-slide themeTop on swiper-slide-active">
                            <div class="thema_box">
                                <div class="img_ar">
                                    <div class="img_box">
                                        <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20230530132005461142.svg" onerror="this.src='/resources/images/noImg.png'" alt="세일">
                                    </div>
                                </div>
                                <p>세일</p>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide themeTop swiper-slide-next">
                        <div class="thema_box">
                            <div class="img_ar">
                                <div class="img_box">
                                    <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20211025133956437474.png" onerror="this.src='/resources/images/noImg.png'" alt="베스트">
                                </div>
                            </div>
                            <p>베스트</p>
                        </div>
                    </div>
                    <div class="swiper-slide themeTop">
                        <div class="thema_box">
                            <div class="img_ar">
                                <div class="img_box">
                                    <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20211025134110516721.png" onerror="this.src='/resources/images/noImg.png'" alt="감사">
                                </div>
                            </div>
                            <p>감사</p>
                        </div>
                    </div>
                    <div class="swiper-slide themeTop" data-idx="3">
                        <div class="thema_box">
                            <div class="img_ar">
                                <div class="img_box">
                                    <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20211025134329628441.png" onerror="this.src='/resources/images/noImg.png'" alt="위로">
                                </div>
                            </div>
                            <p>위로</p>
                        </div>
                    </div>
                    <div class="swiper-slide themeTop" data-idx="4">
                        <div class="thema_box">
                            <div class="img_ar">
                                <div class="img_box">
                                    <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20211025134520451551.png" onerror="this.src='/resources/images/noImg.png'" alt="기념일">
                                </div>
                            </div>
                            <p>기념일</p>
                        </div>
                    </div>
                    <div class="swiper-slide themeTop" data-idx="5">
                        <div class="thema_box">
                            <div class="img_ar">
                                <div class="img_box">
                                    <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20211025134632477213.png" onerror="this.src='/resources/images/noImg.png'" alt="생일">
                                </div>
                            </div>
                            <p>생일</p>
                        </div>
                    </div>
                    <div class="swiper-slide themeTop" data-idx="6">
                        <div class="thema_box">
                            <div class="img_ar">
                                <div class="img_box">
                                    <img src="https://image.multicon.co.kr/ServiceCommon/admin/CategoryTheme/JANDI/20211025134745048878.png" onerror="this.src='/resources/images/noImg.png'" alt="이벤트">
                                </div>
                            </div>
                            <p>이벤트</p>
                        </div>
                    </div>
                </div>
                <div class="list_area">
                    <div class="list_top">
                        <div class="total">
                          	 총
                            <strong>9</strong>
                           	 개
                        </div>
                        <div class="sort_ar">
                            <label for="sort"></label>
                            <div class="select">
                                <select id="sort" class="select-hidden">
                                    <option value="N" selected>상품명순</option>
                                    <option value="H">높은가격순</option>
                                    <option value="L">낮은가격순</option>
                                </select>
                                <div class="select-styled">
                                    	상품명순
                                </div>
                                <ul class="select-options" style="display: none;">
                                    <li rel="N">상품명순</li>
                                    <li rel="H">높은가격순</li>
                                    <li rel="L">낮은가격순</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="list_type_a">
                        <ul>
                            <li>
                                <div class="p_img">
                                    <img class="lazy" src="https://image.multicon.co.kr/PS58/07/0003.jpg" onerror="this.src='/resources/images/noImg.png'" alt="[Pay´s] 브레댄코 디지털상품권 1만원권" style="">
                                </div>
                                <div class="p_info">
                                    <p class="brand">브래댄코</p>
                                    <h5>[Pay´s] 브레댄코 디지털상품권 1만원권</h5>
                                </div>
                                <div class="price_info">
                                    <span class="num">10,000원</span>
                                    <em class="num">
                                        <strong>3</strong>
                                        <i>%</i>
                                    </em>
                                    <p class="price">
                                        <strong class="num">9,700</strong>
                                        <i>원</i>
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- 검색 부분 -->
            <div class="brand_content" style="display: none;">
                <div class="sub_search" style="display: none;">
                    <div class="sh_ar nobpd">
                        <!-- value에 검색어 입력하면 됨 -->
                        <input type="search" id="searchText" value="" 
                        placeholder="원하는 상품을 검색해보세요">
                        <button class="btn_sh">
                            <img src="https://imagec.multicon.co.kr/ServiceCommon/jandi/resources//images/ic_serch.svg">
                        </button>
                    </div>
                </div>
                <div class="list_area min" style="display: none;">
                    <!-- 검색 결과 없을 경우 -->
                    <div class="none_data"
                    style="display: none;">검색결과가 없습니다. </div>
                    <!-- 검색 결과 있을 경우 -->
                    <div class="list_top">
                        <div class="total">
                     	       총
                            <strong>1</strong>
                           	 개
                        </div>
                    </div>
                    <div class="list_type_a">
                        <ul>
                            <li onclick="location.href='/goods?idx=8753';">
                                <div class="p_img">
                                    <img class="lazy" src="https://image.multicon.co.kr/SP26/WL/0003.jpg" onerror="this.src='/resources/images/noImg.png'" alt="<파리바게뜨> 생크림 폭탄 도넛(3개입)" style="">
                                </div>
                                <div class="p_info">
                                    <p class="brand">파리바게뜨</p>
                                    <h5><파리바게뜨> 생크림 폭탄 도넛(3개입)</h5>
                                </div>
                                <div class="price_info">
                                    <p class="price">
                                        <strong class="num">3,900</strong>
                                        <i>원</i>
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- 제품 상세 -->
                <div class="p_detail">
                    <div class="img_box">
                        <img class="lazy" src="https://image.multicon.co.kr/CM01/04/0003.jpg" onerror="this.src='/resources/images/noImg.png'" alt="아메리카노(Ice)(TAKE-OUT)" style=""> 
                    </div>
                    <div class="p_d_info">
                        <p class="brand_name">컴포즈커피</p>
                        <h5>아메리카노(Ice)(TAKE-OUT)</h5>
                        <p class="sale_price">
                            <em class="num">1,500</em>
                        </p>
                        <span class="sale_percent">
                            <em class="num">2%</em>
                        </span>
                        <span class="real_price">
                            <em class="num">1,470</em>
                           	 원
                        </span>
                        <p class="date_ar">
                            <span class="bl_title">유효기간 366일</span>
                        </p>
                    </div>
                </div>
                <div class="detail_to">
                    <div class="ad_info">
                        <span class="btn_ad_01">쿠폰상세정보</span>
                        <div class="info_inner info_ad_01" style="display: none"> <!-- 아이콘 클릭 시 none 사라짐 -->
                            <div>
                                <h3>발행자</h3>
                                <p>(주)쿠프마케팅</p>
                                <h3>유효기간</h3>
                                <p> 12개월(366일) </p>
                                <h3>유효기간 연장 및 유효기간 경과 시 보상 기준</h3>
                                <p>상품교환권 및 금액권 최초 유효기간 366일입니다. 유효기간 연장은 1회(93일)이상 가능하며, 신청기간은 최대 5년까지 가능(고객센터를 통해 신청 가능)</p>
                                <h3>이용조건</h3>
                                <p>-본 상품은 테이크아웃만 가능합니다.<br>-본 상품 이용시 추가할인 및 쿠폰적립, 스탬프적립은 불가합니다.<br>-추가 요금 1,000원 결제 시 매장에서 이용 가능합니다.<br>-본 교환권은 타 제조음료로 교환 가능하며, 차액 발생 시 추가금에 대해 지불해야 합니다.<br>(단, 쿠폰가보다 낮은 상품으로 교환 시 잔액은 환불 드리지 않습니다.)</p>
                                <h3>이용제한</h3>
                                <p>-전국 컴포즈커피 매장에서 사용 가능합니다.(일부 매장 제외)<br>-결제 시 모바일 쿠폰을 제시해 주시면 됩니다.<br>-타 쿠폰 중복 사용 불가합니다.<br>-포인트 적립 및 제휴카드 할인 불가합니다.<br>-현금으로 교환이 불가합니다.</p>
                                <h3>수량</h3>
                                <p>교환권 1매</p>
                                <h3>환불 조건 및 방법</h3>
                                <p>1.미사용 상품교환권 (구매가격 기준) 환불조건 : 최초 유효기간 경과 전 100% 환불, 경과 후 90% 환불이 가능
                                    <br>2.미사용 금액권 (구매가격 기준) 환불조건 : 최초 유효기간 경과 전 100% 환불, 경과 후 90% 환불이 가능
                                    <br>3.금액권에 대한 잔액 환급 조건
                                    <br>    1) 최초 유효기간 경과 전 권면가액의 100분의 60 (1만원 이하 금액권 100분의 80)이상 이용했을 경우 잔액을 반환(단, 구매가격을 기준으로 사용비율에 따라 계산하여 남은 비율로 잔액 산정)
                                    <br>    2) 최초 유효기간 경과 후 상기 기준 반환 대상 금액의 90% 환불가능
                                    <br>4. 환불 요청권자 : 최종소지자
                                    <br>5. 환불 방법 : 고객센터(1644-5093)를 통해 신청 가능후 90% 환불
                                    <br>- 잔액 환급 조건
                                    <br>① 최초 유효기간 경과 전 권면가액의 100분의 60 (1만원 이하 금액권 100분의 80)이상 이용했을 경우 잔액을 반환
                                    <br>(단, 구매가격을 기준으로 사용비율에 따라 계산하여 남은 비율로 잔액 산정)
                                    <br>② 최초 유효기간 경과 후 상기 기준 반환 대상 금액의 90% 환불됩니다. (고객센터를 통해 환불 신청 가능)
                                    <br>본 모바일 교환권은 지급보증 및 피해보상보험 계약없이 자체 신용으로 발행합니다.
                                </p>
                                <h3>소비자 상담 전화번호</h3>
                                <p>1644-5093<br>(평일 09:30 ~ 18:00 (공휴일 제외) | 점심 12:00 ~ 13:00)</p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 결제 정보 -->
                <section> 
                    <form id="requestForm">
                        <div class="pament_info">
                            <h2>결제정보</h2>
                            <div class="payment_content" id="gift" style="">
                                <div class="gift_tab">
                                    <h4>
                                        <span>선물 받을 사람</span>
                                        <em> (1회 최대 5개 구매 가능)</em>
                                    </h4>
                                    <div class="gift_content" id="writing" style="display: block;">
                                        <ul>
                                            <li>
                                                <div class="phone_num">
                                                    <input type="text" class="name" name="writingName" placeholder="이름">
                                                    <input type="tel" class="number_phone" name="writingPhone" placeholder="‘-’ 없이 입력">
                                                </div>
                                                <div class="counting">
                                                    <div class="number_c">
                                                        <div class="select">
                                                            <select class="num select-hidden" rel="writing">
                                                                <option value="1">1</option>
                                                                <option value="2">2</option>
                                                                <option value="3">3</option>
                                                                <option value="4">4</option>
                                                                <option value="5 ">5</option>
                                                            </select>
                                                            <div class="select-styled">
                                                                1
                                                            </div>
                                                            <ul class="select-options" style="display: none;">
                                                                <li rel="1">1</li>
                                                                <li rel="2">2</li>
                                                                <li rel="3">3</li>
                                                                <li rel="4">4</li>
                                                                <li rel="5">5</li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <button class="c_min"></button>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="messge_ar" style="">
                                    <h4 >메시지</h4>
                                    <div class="me_box">
                                        <textarea name="message" id="message" placeholder="메시지를 입력해주세요"></textarea>
                                        <p>
                                            <i class="num" id="msgSize">0/100</i>
                                                Byte 
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="sum_payment">
                                <span>총 결제금액</span>
                                <span>
                                    <strong class="num" id="userPrice">1,470</strong>
                                    <em>원</em>
                                </span>
                            </div>
                            <div class="method_area">
                                <h3>받는사람</h3>
                                <div class="in_chack">
                                    <span class="box_radio">
                                        <input type="radio" name="orderType" id="orderType0" value="ORDTP01" checked="">
                                        <label for="orderType0">신용카드</label>
                                    </span>
                                    <span class="box_radio">
                                        <input type="radio" name="orderType" id="orderType1" value="ORDTP02">
                                        <label for="orderType1">간편결제</label>
                                    </span>
                                </div>
                            </div>
                            <div class="agreement_ar">
                                <div class="chack_ar">
                                    <div class="hd_chack">
                                        <div class="check_box_all">
                                            <input class="chk_all" type="checkbox" id="agreeAll">
                                            <label for="agreeAll">전체 동의</label>
                                        </div>
                                        <div class="btn_ar">
                                            <button id="ch_t" class="on"></button>
                                        </div>
                                    </div>
                                    <ul class="ch_tn" style="display: block;">
                                        <li>
                                            <div class="ch_l">
                                                <div class="check_box_s">
                                                    <input type="checkbox" class="agreeCheckbox" id="agree1">
                                                    <label for="agree1">[필수] 위 상품의 정보를 확인하셨으며, 구매에 동의하십니까? (전자상거래법 제 8조 2항에 의거)</label>
                                                </div>
                                            </div>
                                            <div class="btn_trams">
                                                <button class=""></button>
                                            </div>
                                            <div class="in_trems">
                                               	 사업자와 전자결제업자 등은 전자적 대금지급이 이루어지는 경우 소비자의 청약의사가 진정한 의사 표시에 의한 것인지를 확인하기 위하여 다음 각 호의 사항에 대하여 명확히 고지하고, 고지한 사항에 대한 소비자의 확인절차를 대통령령으로 정하는 바에 따라 마련하여야 한다.<br><br>
                                                1. 재화 등의 내용 및 종류<br>2. 재화 등의 가격<br>3. 용역의 제공기간
                                            </div>
                                        </li>
                                        <li>
                                            <div class="ch_l">
                                                <div class="check_box_s">
                                                    <input type="checkbox" class="agreeCheckbox" id="agree2">
                                                    <label for="agree2">[필수] 결제취소 및 환불 관련 동의</label>
                                                </div>
                                            </div>
                                            <div class="btn_trams">
                                                <button class=""></button>
                                            </div>
                                            <div class="in_trems">
                                                1. 자체 결제취소는 7일 이내에만 가능하며, 여러 개를 한꺼번에 구매/선물하셨을 경우 모두 일괄 취소됩니다.<br><br>
										        2. 복수 상품 구매 시, 1건이라도 사용한 쿠폰이 존재할 경우 잔여 미사용 쿠폰은 고객센터를 통해 환불이 가능합니다.
                                            </div>
                                        </li>
                                        <li>
                                            <div class="ch-l">
                                                <div class="check_box_s">
                                                    <input type="checkbox" class="agreeCheckbox" id="agree3">
                                                    <label for="agree3">[필수] 제3자 정보제공 동의</label>
                                                </div>
                                            </div>
                                            <div class="btn_trams">
                                                <button class="last_btn"></button>
                                            </div>
                                            <div class="in_trems">
                                                * 정보통신망법 규정에 따라 잔디 선물하기를 이용하기 위해서는 아래 제 3자 정보제공 동의를 해주셔야 합니다. 개인정보 제공에 대한 동의를 거부할 권리가 있으며, 동의를 거부할 경우 서비스 이용이 제한됩니다.<br><br>
                                                1. 모바일상품 발송 처리업무 <br>
                                                - 위탁 받는 자(수탁자) : ㈜엘지유플러스 <br>
                                                - 위탁하는 업무의 내용 : 모바일 상품권 발송(휴대폰번호 선물하기 限)<br><br>
                                                2. 전자지불결제대행<br>
                                                - 위탁 받는 자(수탁자) : ㈜케이지이니시스<br>
                                                - 위탁하는 업무의 내용 : 전자지급결제대행<br><br>
                                                3. 모바일상품 CS처리<br>
                                                - 위탁 받는 자(수탁자) : ㈜효성ITX<br>
                                                - 위탁하는 업무의 내용 : 모바일 상품권 재발송, 유효기간연장, 매장방문정보 처리, 전화상담 업무, 환불처리<br>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="button_ar">
                        <button type="button" class="btn" id="orderSet">결제하기</button>
                    </div>
                </section>
            </div>
            <!-- 쿠폰함 부분 -->
	        <div class="coupon_box" style="display: none;">
	        	<ul>
	        		<li rel="in_gift" class="active">받은선물</li>
	        		<li rel="purchase" class="">구매내역</li>
	        	</ul>
	        	<!-- 받은 선물 -->
	        	<div class="coupon_area" id="in_gift" style="display: block;">
	        		<div class="coupon_list">
	        			<div class="list_area min">
	        				<div class="none_data">보유한 쿠폰이 없습니다.</div>
	        			</div>
	        		</div>
	        	</div>
	        	<!-- 구매 내역 -->
	        	<div class="purchase_list" style="display: none;">
	        		<div class="none_data">구매한 쿠폰이 없습니다.</div>
	        	</div>
	        </div>
        </div> 
        <!-- 컨테이너 div 끝-->
        <!-- footer 부분 -->
         <footer class="tip_bar type2">
            <div class="foot-top">
                <ul>
                    <li>
                        <a href="#">이용약관</a>
                    </li>
                    <li>
                        <span class="btn_customer_info on">고객센터</span>
                    </li>
                    <li>
                        <span class="btn_company_info">사업자정보</span>
                    </li>
                </ul>
            </div>
            <div class="foot_info2" style="display: none;">
                <div class="compm">
                    <p class="tit">
                        <span>
                           	 고객센터
                            <i class="num">1644-5093</i>
                        </span>
                    </p>
                    <p class="time">
                        <span>운영시간 : 평일 09:30 - 18:00 (공휴일제외) / 점심 12:00 - 13:00</span>
                    </p>
                    <p class="time">
                        <span>서울특별시 성동구 성수이로 10길 14, 904호,905호,10층 (성수동2가, 에이스하이엔드 성수타워)</span>
                    </p>
                    <p class="mail">
                        <span>고객센터 E-mail : cs@coopnc.com</span>
                    </p>
                </div>
            </div>
            <div class="foot_info" style="display: none;">
                <div class="compm">
                    <p>(주)쿠프마케팅 대표 : 전우정</p>
                    <p>사업자등록번호 : 105-86-86082</p>
                    <p>통신판매업신고번호 : 2010-서울성동-0671호</p>
                    <p>연락처 : 02-514-0237 FAX : 02-514-0237</p>
                    <p>E-mail : coop@coopnc.com</p>mjn 
                </div>
            </div>
            <p class="copy_right">Copyright ⓒ Coopmarketing All Rights Reserved</p>
         </footer>
    </div>
</body>
</html>