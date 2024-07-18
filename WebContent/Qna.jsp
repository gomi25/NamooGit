<%@page import="dto.QnaAnswerDto"%>
<%@page import="dto.QnaAllQuestionDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.QnaDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	

	int loginMemberIdx = 1;   
	// 로그인 member_idx 가정. ----> 이후에는 (Integer)session.getAttribute("loginId") 등으로 변경해야 돼요~
	//int memberIdx = (Integer)session.getAttribute("loginMemberIdx");


	int qna_idx = 37;


	int pageNum = 1;
	try {
		pageNum = Integer.parseInt(request.getParameter("page"));
	} catch(Exception e) { }
	int startNum, endNum;
	int lastPageNum;
	
	QnaDao qDao = new QnaDao();
	//ArrayList<QnaAllQuestionDto> pagingQna = qDao.getQnaQuestionPage(pageNum);
	//ArrayList<QnaAllQuestionDto> listQna = qDao.getQnaAllQuestion();
	
	ArrayList<QnaAllQuestionDto> listQna = qDao.getQnaQuestionPage(pageNum);
	
	
	
	//페이지 나타내기
	lastPageNum = qDao.getLastPageNumber();
	endNum = (pageNum / 5 + 1) * 5;
    if (pageNum % 5 == 0) {
        endNum = pageNum + 4;
    }
    if (endNum > lastPageNum) {
        endNum = lastPageNum;
    }
    startNum = endNum - 4 > 0 ? endNum - 4 : 1;
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>namoo</title>
	<link href="https://intercom.help/jandi/assets/favicon" rel="icon">
	<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="css/Qna.css"/>
	<script src="${pageContext.request.contextPath}/js/Qna.js"></script>
	<script>

	</script>
</head>
<body>
	<div id="div_header1" >
		<div class="fl"><img alt="잔디로고" src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628"/><p>NAMOO</p></div>
		<div>
			<div class="fl"> <a href="https://www.naver.com">고객사례 </a></div> 
			<div class="fl"><a href="https://www.naver.com">도입문의</a></div>
			<div class="fl"><a href="https://www.naver.com">리소스</a></div>
			<div class="fl"><a href="https://www.naver.com"><img src="https://cdn.jandi.com/landing/_next/static/media/arrow_mini_down.ad231965c95d19a3510b2e6c538470fe.svg/"></a></div>
			<div class="fl"><a href="https://www.naver.com">블로그</a></div>
			
		</div>
		<div class="center" >
			<div class="fr"><a href="https://www.naver.com"><span>로그인</span></a></div>
			<div class="border fr"><a href="https://www.naver.com">다운로드</a></div>
		</div>
		
		

	</div>
	<hr class="color_grey"/>
	
	<div id="div_content1">
		
		<div> 
		<h1>도입문의</h1>
		<span>협업툴 전문 컨설턴트가 빠르게 안내드립니다.</span>
		</div>
		<div> 
		<img src="img/lotte.png"/>
		<img src="img/onnuri.png"/>
		<img src="img/nexen.png"/>
		<img src="img/hanssem.png"/>
		</div>
		<div> 
		<img src="img/yanadoo.png"/>
		<img src="img/daks.png"/>
		<img src="img/amore.png"/>
		<img src="img/hankookdoro.png"/>
		</div>
		
<!-- 신청자 정보 -->
	<form action="QnaInsert.jsp">
		<div id="div_content2">
			<span>신청자 정보</span>
			<div id="div_content1_input">
				<div class="fl">
					<span>이름</span>
					<div class="on"></div>
					<br>
					<input type="text" name="name" placeholder="이름을 입력해 주세요" class="green-border"/>
				</div>
				<div class="fr">
					<span>연락처</span><br>
					<input type="text" name="phone_number" placeholder="예)010-1234-5678" class="green-border"/>
				</div>
				<div class="fl">
					<span>직책</span><br>
					<input type="text" name="position" placeholder="담당하고 계시는 직책" class="green-border"/>
				</div>
				<div class="fr">
					<span>이메일</span><br>
					<input type="text" name="email" placeholder="예)jandi@jandi.com" class="green-border"/>
				</div>
				<div class="fl">
					<span>산업군</span><br>
					<select name="industry" >
						<option value="" disabled selected>산업군 선택</option>
		 				 <option value="1">IT</option>
		 				 <option value="2">에이전시</option>
		 				 <option value="3">비영리</option>
		 				 <option value="4">유통·커머스</option>
		 				 <option value="5">제조</option>
		 				 <option value="6">F&B</option>
		 				 <option value="7">교육</option>
		 				 <option value="8">헬스케어</option>
		 				 <option value="9">미디어</option>
		 				 <option value="10">공공기관</option>
		 				 <option value="11">금융</option>
		 				 <option value="12">기타</option>
		  			</select>
				</div>
				<div class="fr">
					<span>인원수</span><br>
					<select name="count" >
						<option value="" disabled selected>인원수 선택</option>
		 				 <option value="1">0~9명</option>
		 				 <option value="2">10~49명</option>
		 				 <option value="3">50~99명</option>
		 				 <option value="4">100~499명</option>
		 				 <option value="5">500~999명</option>
		 				 <option value="6">1000명 이상</option>
		  			</select>
					
				</div>
				<div class="fl">
					<span>문의 사항</span><br>
					<select name="question" >
						<option value="" disabled selected>문의 사항 선택</option>
		 				 <option value="1">가격문의</option>
		 				 <option value="2">컨설팅 요청</option>
		 				 <option value="3">바우처 지원</option>
		 				 <option value="4">자료 요청</option>
		 				 <option value="5">기타</option>
		  			</select>
				</div>
				
				<div>
					<textarea name="content" placeholder="업무용 협업툴, 전자결재, 근태관리, 시스템 연동으로 우리 기업의 업무 생산성을 향상시키고 싶으신가요? &#10;최적화된 업무 환경을 구성하는데 겪고 계신 어려움을 잔디 팀에서 함께 해결해 드리겠습니다. "></textarea>
				</div>
				<div>
					<input type="checkbox" name="agreement" id="check1" value="1">
					<label for="check1" >
					<span>
						[필수] 요청하신 문의 내용에 대한 서비스 제공을 위해서 필요한 최소한의 <span>[개인정보]</span>이므로 동의하셔야 서비스를 이용할 수 있습니다.
					</span>
					</label>
				</div>
				<!-- <div class="secret_text">
					<input type="checkbox" id="check2">
					<label for="check2">
					<span>
						비밀 글
					</span>
					</label>
				</div>
				<div class="password_input">
					<input type="text" id="check3" placeholder="[필수]비밀번호를 입력하세요">
					<label for="check3">
					</label>
				</div>
				 -->
				<div>
				<button>컨설팅 신청하기</button>
				</div>
				
			</div>
			
		</div>
	</form>
<!-- QnA부분 -->
		<div id="div_QnA">
			<div>
				컨설팅 Q&A
			</div>
			<div id="QnA_content">
				<table>
					<tr>
						<th class="head">답변상태</th>
						<th>내용</th>
						<th class="head">작성자</th>
						<th class="head">작성일</th>
					</tr>
<%--
					<tr class="qna">
						<td>답변완료</td>
						<td>안녕하세요.</td>
						<td>Hello</td>
						<td>2024.05.28</td>
					</tr>
					<tr class="qna_answer">
						<td></td>
						<td><div class="answer"><p>답변<p></div>안녕하세요.답변입니다.</td>
						<td>판매자</td>
						<td>2024.05.29</td>
					</tr>
--%>
					<% for(QnaAllQuestionDto qDto : listQna) { %>
					    <tr class="qna" qna_idx="<%=qDto.getQnaIdx() %>">
					        <td><div class="reply_status"><%=qDto.getReplyCondition() == 1 ? "답변완료" : "미답변" %></div></td>
					        <td>
					            <div class="fl qna_content"><%=qDto.getContent() %></div>
					            <% if(qDto.getReplyCondition() == 0) { %>
					        <% if(loginMemberIdx == 0) { %> 
							<button class="fr qna_button" onclick="showAnswerForm('<%=qDto.getQnaIdx() %>')">답글달기</button>					            <% } %>
					        <%} %>
					        </td>
					        <td><%=qDto.getName() %></td>
					        <td><%=qDto.getQuestionDate().split(" ")[0].replace("-",".") %></td>
					    </tr>
					    <tr class="add_answer" id="answer_form_<%=qDto.getQnaIdx() %>" style="display: none;">
					        <td></td>
					        <td>
					            <form action="QnaAnswerInsert.jsp" method="post">
					                <input type="hidden" name="qna_idx" value="<%= qDto.getQnaIdx() %>">
					                <div>
					                    <input class="fl add_answer_input" type="text" name="content">
					                </div>
					                <button class="add_answer_submit" type="submit">입력</button>
					                <% if(loginMemberIdx == 0) { %> 
					                <button class="add_answer_button" type="button">수정</button>
					           		<% } %>
					            </form>
					        </td>
					        <td></td>
					        <td></td>
					    </tr>
					    <% QnaAnswerDto aDto = qDao.getQnaAnswer(qDto.getQnaIdx());
					    if(aDto != null) { %>
					    <tr class="qna_answer" qna_idx="<%=qDto.getQnaIdx() %>">
					        <td></td>
					        <td>
					            <br/>
					            <div class="answer"><p>답변<p></div>
					            <span><%=aDto.getContent() %></span>
					            <% if(loginMemberIdx == 0) { %> 
					            <div class="fr answer_button">
					            	<button class="answer_update" >수정</button>
					            	<button>삭제</button>
					            </div>
					            <% } %>
					        </td>
					        <td>관리자</td>
					        <td><%=aDto.getAnswer_date().split(" ")[0].replace("-",".") %></td>
					    </tr>
					    <% } %>
					<% } %>
					
				</table>
				<div id="pagination">
					<% if(startNum > 1) { %>
						<a id="prev" href="Qna.jsp?page=<%=startNum-5%>">&lt; </a> 
					<% } %>
					<% for(int i=startNum; i<=endNum; i++) { %>
						<% if(i != pageNum) { %>
							<a href="Qna.jsp?page=<%=i%>"><%=i%></a> 
						<% } else { %>
						<span><%=i%></span>
						<% } %>
					<% } %>
					<% if(lastPageNum > endNum) { %>
					<a href="Qna.jsp?page=<%=startNum+1%>">&gt; </a> 
					<% } %>
				</div>
				
				
				
			</div>
			
		</div>
		
	</div>
	
	<!-- footer 부분 -->
	<div id="div_footer" >
		<div id="footerblock" >
			<!-- 왼쪽 부분  -->
			<div id=footer_left class="fl">
				<div><img src="https://jandi-box.com/teams/0/logo.png?timestamp=20190628"><p>NAMOO</p></div>
					<ul>
						<li><img src="img/facebook.png"><a href="https://www.facebook.com/JANDI.SKorea"><span>Facebook</span></a></li>
						<li><img src="img/instagram.png"><a href="https://www.instagram.com/jandi.korea">Instagram</a></li>
						<li><img src="img/youtube.png"><a href="https://www.youtube.com/channel/UCtEhu2u_lDa58OAJg121flQ">YouTube</a></li>
						<li><img src="img/blog.png"><a href="https://blog.naver.com/jandi-korea">Blog</a></li>
						<li><img src="img/linkedin.png"><a href="https://www.linkedin.com/showcase/jandi-korea">LinkedIn</a></li>
						<li><img src="img/newsletter.png"><a href="https://page.stibee.com/subscriptions/86645?groupIds=226036">Newsletter</a></li>
					</ul>
			</div>
			<!-- 오른쪽 부분  -->
			<div id="footer_right" class="fr">
				<div id="footer_info1" >
					<span>JANDI</span>
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
				<span>Toss Lab,Inc.</span>
					<ul>
						<li><a href="https://www.jandi.com/landing/kr/company">회사소개</a></li>
						<li><a href="https://www.jandi.com/landing/kr/company">채용</a></li>
						<li><a href="https://blog.jandi.com/ko/">잔디 블로그</a></li>
						<li><a href="https://www.jandi.com/landing/kr/partner/aws">파트너</a></li>

					</ul>
				</div>
				</div>
			</div>
			<!-- footer 아래부분 -->
			<div id="footer_bottom">
				<div id="footer_bottom_info">
					<div>
						<a href="/landing/kr/private/policy"><sapn>개인정보처리방침 | </sapn></a>
						<a href="href="/landing/kr/terms/service"><sapn>이용약관 </sapn></a>
					</div>
				</div>
				<div id="footer_bottom_info2">
			
					<span>(주)토스랩  | 대표이사: 이영걸 | 서울특별시 강남구 봉은사로 524(인터컨티넨탈 서울 코엑스), 스파크플러스 코엑스점 지하1층 L226 | 이메일:support@tosslab.com </span>
					<br>
					<span>사업자등록번호: 220-88-81740 | 통신판매업신고번호: 2016-서울강남-00237 | © 2014-2024 Toss Lab, Inc.</span>
					
				</div>
			</div>
		</div>
		
		
	
	<div style="clear:both;"></div>

</body>
</html>