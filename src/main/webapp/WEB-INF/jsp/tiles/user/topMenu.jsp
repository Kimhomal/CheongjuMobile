<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
	<div id="wrap" class="member">
		<!-- header_wrap -->
		<div id="header">
			<div class="innerArea">
				<div class="logo"><a href="/" title="홈"><img src="${context}/images/common/logo_header.png" alt="홈"></a></div>
				<!-- gnb_wrap -->
				<div class="gnb_wrap">
					<ul class="gnb">

				<c:choose>
						<c:when test="${sessionScope.auth == null||sessionScope.auth.usrId eq 'everyone'}"><%-- 비회원로그인 확인 --%>
							<li><a href="/" title="홈">홈</a></li>
							<li><a href="${context}/user/auth/login.do#M011002000" title="로그인">로그인</a></li>
							<li><a href="javascript:$.popupWindow('${context}/system/member/joinMemberPopW.do',{id:'memberInfo',scrollbars:1, width : '545', height : '770'});" title="회원가입신청">회원가입신청</a></li>
						</c:when>
						<c:otherwise>
							<li><p class="tit"><br></p></li>
							<li><a href="/" title="홈">홈</a></li>
							<li><a href="${context}/user/info/usrInfoChk.do#M012001000" title="개인정보">개인정보</a></li>
							<c:if test ="${sessionScope.auth.roleCde eq 'R0001'}">
								<li><a href="${context}/system/member/selectInfoList.do#M010001000" title="관리">관리</a></li>
								<li><a href="javascript:$.popupWindow('${context}/system/member/insertInfoPopW.do',{id:'memberInfo',scrollbars:1, width : '545', height : '910'});" title="사용자등록" >사용자등록</a></li>
							</c:if>
							<li><a href="${context}/user/auth/logout.do" title="">로그아웃</a></li>
						</c:otherwise>
				</c:choose>
						<%--<li><a href="${context}/main/stieMap.do" title="사이트맵">사이트맵</a></li>--%>
					</ul>
					
				</div>
				<!-- //gnb_wrap -->
			</div>

			<!-- lnb_wrap -->
			<div class="lnb_wrap">
				<ul class="lnb"></ul>
			</div>
			<!-- //lnb_wrap -->
		</div>
		<!-- //header_wrap -->
		<hr>
