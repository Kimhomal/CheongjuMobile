<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="detailFrm" id="detailFrm" method="post">
	<%--<input type="hidden" id="facilityId" name="facilityId" value="GT_A061_P">--%>
	<h2 class="h2_tp1">교통안전시설물</h2>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp2">
			<li class="active"><a href="#this" data-target="GT_A049_P">검지기</a></li>
			<li><a href="#this" data-target="GT_A111_A">공영주차장</a></li>
			<li><a href="#this" data-target="GT_A054_P">노면문자표시</a></li>
			<li><a href="#this" data-target="GT_A055_P">노면방향표시</a></li>
			<li><a href="#this" data-target="GT_A078_L">배선도</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A057_L">부착대</a></li>

			<li><a href="#this" data-target="GT_A110_P">신호등</a></li>
			<li><a href="#this" data-target="1">보행등보조장치(X)</a></li>
			<li><a href="#this" data-target="GT_A005_A">안전지대</a></li>
			<li><a href="#this" data-target="GT_A064_P">안전표지</a></li>
			<li><a href="#this" data-target="GT_A090_P">음향신호기</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A090_P">보행자버튼</a></li>

			<li><a href="#this" data-target="GT_A079_L">유턴구역</a></li>
			<li><a href="#this" data-target="GT_A081_L">정지선</a></li>
			<li><a href="#this" data-target="GT_A085_A">정차금지지대</a></li>
			<li><a href="#this" data-target="GT_A061_P">제어기</a></li>
			<li><a href="#this" data-target="GT_A080_A">주차구획</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A082_L">주차금지</a></li>

			<li><a href="#this" data-target="GT_A083_L">중앙선</a></li>
			<li><a href="#this" data-target="GT_A062_P">지주</a></li>
			<li><a href="#this" data-target="GT_A084_L">차선</a></li>
			<li><a href="#this" data-target="GT_C024_P">통신맨홀</a></li>
			<li><a href="#this" data-target="GT_A004_A">횡단보도</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A068_P">횡단보도예고표시</a></li>

			<li><a href="#this" data-target="1">횡단보도조명(X)</a></li>
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

	$("#statistics").load("${context}/statistics/totalStatisticsPopE.do",{facilityId:"GT_A049_P"});


});


</script>