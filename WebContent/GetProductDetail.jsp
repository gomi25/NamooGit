<%@page import="dto.GiftDto"%>
<%@page import="dao.GiftDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>

<%
    int giftIdx = Integer.parseInt(request.getParameter("giftIdx"));
    GiftDao giftDao = new GiftDao();
    GiftDto product = new GiftDto();
    
    try {
        product = giftDao.getProductById(giftIdx);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
    	$("#orderSet").click(function(event) {
    		event.preventDefault();
   		    var IMP = window.IMP; 
   		    IMP.init('imp27173663'); 
   		    IMP.request_pay({
   		    	pg : "kakaopay", 
   		        pay_method : 'card',
   		        merchant_uid : 'merchant_' + new Date().getTime(),
   		        name : '결제',
   		        // 여기부터
   		        amount : 27000,
   		        buyer_email : '구매자 이메일', // 스크립틀릿... Gift.jsp -> 
   		        buyer_name : '구매자 이름',
   		        buyer_tel : '구매자 번호',
   		        buyer_addr : '구매자 주소',
   		        buyer_postcode : '구매자 주소',
   		        // 여기까지
   		        m_redirect_url : 'redirect url'
   		    }, function(rsp) {
   		        if ( rsp.success ) {
   		            var msg = '결제가 완료되었습니다.';
   		            alert(msg);
   		            location.href='http://localhost:9091/Z.Namoo_WEB/Gift.jsp'; // 뒤에 DB 처리 해야 함
   		        } else {
   		            var msg = '결제에 실패하였습니다.';
   		            rsp.error_msg;
   		        }
   		    });
    	});
    	
        // 전체 동의 체크박스와 하위 체크박스 연동
        $('#agreeAll').on('change', function() {
            $('.agreeCheckbox').prop('checked', $(this).is(':checked')).change();
        });

        $('.agreeCheckbox').on('change', function() {
            var allChecked = $('.agreeCheckbox').length === $('.agreeCheckbox:checked').length;
            $('#agreeAll').prop('checked', allChecked);
        });

        // 체크박스 클릭 시 배경 이미지 변경
        $('input[type="checkbox"]').on('change', function() {
            if ($(this).is(':checked')) {
                $(this).next('label').css({
                    'background': 'url(https://imagec.multicon.co.kr/ServiceCommon/jandi/resources/images/ic-s-check-on.svg) no-repeat',
                    'background-size': '22px auto',
                    'background-position': 'left top',
                    'font-size': '13px',
                    'line-height': '22px'
                });
            } else {
                $(this).next('label').css({
                    'background': '',
                    'background-size': '',
                    'background-position': '',
                    'font-size': '',
                    'line-height': ''
                });
            }
        });
        
        // 닫기 버튼 클릭 시 전체 상품 리스트로 이동
        $(".btn_close").click(function() {
            $(".main_content").css("display", "block");
            $(".product_detail").css("display", "none");
            $(".coupon_box").css("display", "none");
        });
    });
</script>

<div class="p_detail">
    <div class="img_box">
        <img class="lazy" src="<%= product.getGiftImgUrl() %>" onerror="this.src='/resources/images/noImg.png'" alt="<%= product.getGiftName() %>" style="">
    </div>
    <div class="p_d_info">
        <p class="brand_name"><%= product.getBrandName() %></p>
        <h5><%= product.getGiftName() %></h5>
        <p class="sale_price">
            <em class="num"><%= product.getPrice() %></em>
        </p>
        <span class="sale_percent">
            <em class="num"><%= product.getReducedPrice() != null ? 100 - (product.getReducedPrice() * 100 / product.getPrice()) : 0 %>%</em>
        </span>
        <span class="real_price">
            <em class="num"><%= product.getReducedPrice() != null ? product.getReducedPrice() : product.getPrice() %></em>
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
        <div class="info_inner info_ad_01" style="display: none">
            <div>
                <h3>발행자</h3>
                <p>(주)쿠프마케팅</p>
                <h3>유효기간</h3>
                <p>12개월(366일)</p>
                <h3>유효기간 연장 및 유효기간 경과 시 보상 기준</h3>
                <p>상품교환권 및 금액권 최초 유효기간 366일입니다. 유효기간 연장은 1회(93일)이상 가능하며, 신청기간은 최대 5년까지 가능(고객센터를 통해 신청 가능)</p>
                <h3>이용조건</h3>
                <p>-본 상품은 테이크아웃만 가능합니다.<br>-본 상품 이용시 추가할인 및 쿠폰적립, 스탬프적립은 불가합니다.<br>-추가 요금 1,000원 결제 시 매장에서 이용 가능합니다.<br>-본 교환권은 타 제조음료로 교환 가능하며, 차액 발생 시 추가금에 대해 지불해야 합니다.<br>(단, 쿠폰가보다 낮은 상품으로 교환 시 잔액은 환불 드리지 않습니다.)</p>
                <h3>이용제한</h3>
                <p>-전국 <%=product.getBrandName()%> 매장에서 사용 가능합니다.(일부 매장 제외)<br>-결제 시 모바일 쿠폰을 제시해 주시면 됩니다.<br>-타 쿠폰 중복 사용 불가합니다.<br>-포인트 적립 및 제휴카드 할인 불가합니다.<br>-현금으로 교환이 불가합니다.</p>
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
                    <strong class="num" id="userPrice"><%= product.getReducedPrice() != null ? product.getReducedPrice() : product.getPrice() %></strong>
                    <em>원</em>
                </span>
            </div>
            <div class="method_area" style="display:none;">
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

<!-- 결제완료, 결제 요청 취소 화면 -->
<div class="pop_box full" id="pcOrderCheck" style="left: -1px; position: fixed; top: 0px; z-index: 9999; opacity: 1; display: ;">
    <button class="b-close"></button>
    <div class="pop_content ab_mid">
        <div class="in_t">
            <p class="str">브라우저를 통해 결제 화면으로 연결됩니다.<br>결제가 완료되면 아래[결제완료]를 눌러주세요</p>
            <button class="comp" id="pcOrderCheckGo">결제완료</button>
            <p class="poi" style="display: ;">
                <span id="checkPgConfimMsg"></span>
                <br>
              	  아래 [결제 요청 취소] 안내를 참조해주세요.
            </p>
        </div>
        <div class="in_t">
            <p class="str">
                	결제도중 브라우저를 닫았거나 다른상품으로 변심,<br>결제가 실패된 경우 아래[결제 요청 취소]를 눌러주세요
            </p>
            <button class="cancel" id="pcOrderCancel">결제 요청 취소</button>
        </div>
    </div>
</div>
