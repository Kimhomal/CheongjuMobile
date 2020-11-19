<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="detailFrm" id="detailFrm" method="post">
	<%--<input type="hidden" id="facilityId" name="facilityId" value="GT_A061_P">--%>
	<h2 class="h2_tp1">도로안전시설물</h2>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp2">
			<li class="active"><a href="#this" data-target="GT_A069_P">CCTV</a></li>
			<li><a href="#this" data-target="GT_C100_P">가로등</a></li>
			<li><a href="#this" data-target="GT_C088_P">갈매기표지</a></li>
			<li><a href="#this" data-target="GT_C104_A">고원식교차로</a></li>
			<li><a href="#this" data-target="GT_A067_A">과속방지턱</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C109_A">교통섬</a></li>

			<li><a href="#this" data-target="GT_C051_P">도로반사경</a></li>
			<li><a href="#this" data-target="GT_C091_A">미끄럼방지시설</a></li>
			<li><a href="#this" data-target="GT_C059_A">방호울타리</a></li>
			<li><a href="#this" data-target="GT_C092_P">시선유도봉</a></li>
			<li><a href="#this" data-target="GT_C093_P">시선유도표지</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C095_A">입체횡단시설</a></li>

			<li><a href="#this" data-target="GT_C096_P">장애물표적표지</a></li>
			<li><a href="#this" data-target="GT_C097_P">전광판</a></li>
			<li><a href="#this" data-target="GT_C094_A">점자블럭</a></li>
			<li><a href="#this" data-target="GT_C098_A">충격흡수시설</a></li>
			<li><a href="#this" data-target="GT_C107_A">컬러포장</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A065_L">표지병</a></li>
		</ul>
	</div>

	<div id="statistics" class="mg_t25"></div>


</form>
<script>

$(function() {

	// insertUseLog("통계관리","세부통계");
    $("ul.tapMenu > li > a").on("click",function () {
        $("#statistics").load("${context}/statistics/totalStatisticsPopE.do",{facilityId:$(this).attr("data-target")});
    });

	$("#statistics").load("${context}/statistics/totalStatisticsPopE.do",{facilityId:"GT_A069_P"});


});


</script>