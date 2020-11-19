<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="detailFrm" id="detailFrm" method="post">
	<%--<input type="hidden" id="facilityId" name="facilityId" value="GT_A061_P">--%>
	<h2 class="h2_tp1">기본도</h2>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp2">
			<li class="active"><a href="#this" data-target="GT_A008_A">교차로</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C114_A">자전거전용도로</a></li>
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

	$("#statistics").load("${context}/statistics/totalStatisticsPopE.do",{facilityId:"GT_A008_A"});


});


</script>