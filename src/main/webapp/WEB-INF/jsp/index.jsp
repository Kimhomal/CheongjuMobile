<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<form id="frm" name="frm" method="post">
	<input type='hidden' id='sggCode' name='sggCode' value=""/>
</form>

<!-- 로그인 페이지 -->
<div class="ui center aligned container main-wrap">
	<div class="content">
		<span class="maintxt">청주시와 함께 만들어가는</span><br>
		교통안전시설정보
	</div>
</div>

<div class="ui container content-wrap">
	<div class="ui raised segments">
		<div class="ui horizontal segments">
			<div class="ui segment ripple-effect">
				<div class="ui list">
					<div class="item">
						<!-- <div class="right floated content">
							<div class="description">2020-11-10</div>
						</div> -->
						<img class="ui mini image" src="<c:url value='/images/main/ico_notice_small.png'/>" alt="notice">
						<div class="content">
							<a class="header">공지사항</a>
							<div class="description">청주시 교통안전시설정보 제공</div>
							<div class="description">2020-11-10</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ui horizontal two segments">
			<div class="ui center aligned segment ripple-effect" onclick="location.href='<c:url value='/board/notice/selectNoticeList.do'/>'">
				<img class="ui centered image main" src="<c:url value='/images/main/ico_notice.png'/>" alt="notice">
				<div class="content-txt">공지사항</div>
			</div>
			<div class="ui center aligned segment ripple-effect" onclick="location.href='<c:url value='/introduce/introduce01.do'/>'">
				<img class="ui centered image main" src="<c:url value='/images/main/ico_traffic.png'/>" alt="notice">
				<div class="content-txt">교통안전시설물이란?</div>
			</div>
		</div>
		<div class="ui horizontal two segments">
			<div class="ui center aligned segment ripple-effect" onclick="location.href='<c:url value='/map/mapMain.do'/>'">
				<img class="ui centered image main" src="<c:url value='/images/main/ico_map.png'/>" alt="notice">
				<div class="content-txt">지도서비스</div>
			</div>
			<div class="ui center aligned segment ripple-effect" onclick="location.href='<c:url value='/map/mapMain.do?position=true'/>'">
				<img class="ui centered image main" src="<c:url value='/images/main/ico_pin.png'/>" alt="notice">
				<div class="content-txt">위치기반 시설물 검색</div>
			</div>
		</div>
	</div>
<!-- <div class="ui equal width internally celled grid">
	<div class="row">
		<div class="column">ho</div>
	</div>
	<div class="row">
		<div class="column">ho</div>
		<div class="column">ho</div>
	</div>
	<div class="row">
		<div class="column">ho</div>
		<div class="column">ho</div>
	</div>
</div> -->

</div>