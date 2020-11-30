<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<!-- 사이드 메뉴 -->
<ons-splitter>
	<ons-splitter-side id="splitterMenu" side="left" collapse>
		<ons-page>
			<ons-list>
				<ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/'/>'" tappable>
					<img src="<c:url value='/images/common/logo_header.png'/>" alt="홈">
				</ons-list-item>
				<ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/introduce/introduce01.do'/>'" tappable>교통안전시설물이란</ons-list-item>
				<ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/board/notice/selectNoticeList.do'/>'" tappable>공지사항</ons-list-item>
				<ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/map/mapMain.do'/>'" tappable>지도서비스</ons-list-item>
				<%-- <ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/map/mapMain.do'/>'" expandable>지도서비스
					<div class="expandable-content">
						<ons-list>
							<ons-list-item modifier="longdivider" tappable>교차로 검색</ons-list-item>
							<ons-list-item modifier="longdivider" tappable>도로명주소 검색</ons-list-item>
							<ons-list-item modifier="longdivider" tappable>지번 검색</ons-list-item>
							<ons-list-item modifier="longdivider" tappable>건물명 검색</ons-list-item>
							<ons-list-item modifier="longdivider" tappable>교통안전시설물 검색</ons-list-item>
						</ons-list>
					</div>
				</ons-list-item> --%>
				<c:choose>
					<c:when test="${sessionScope.auth == null||sessionScope.auth.usrId eq 'everyone'}">
						<ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/user/auth/login.do'/>'" tappable>로그인</ons-list-item>
					</c:when>
					<c:otherwise>
						<ons-list-item modifier="longdivider" onclick="location.href='<c:url value='/user/auth/logout.do'/>'" tappable>
							<a href="#">로그아웃</a>
						</ons-list-item>
						<ons-list-item modifier="longdivider" style="background: #777; color: rgba(255,255,255, 0.8);">${sessionScope.auth.usrNam}님 환영합니다.</ons-list-item>
					</c:otherwise>
				</c:choose>
			</ons-list>
		</ons-page>
	</ons-splitter-side>
	<ons-splitter-content id="content" page="content.html"></ons-splitter-content>
</ons-splitter>