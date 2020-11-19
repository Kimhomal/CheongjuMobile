<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="detailFrm" id="detailFrm" method="post">
	<%--<input type="hidden" id="facilityId" name="facilityId" value="GT_A061_P">--%>
	<h2 class="h2_tp1">특수교통운영구역</h2>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp2">
			<li class="active"><a href="#this" data-target="GT_C115_A">노인보호구역</a></li>
			<li><a href="#this" data-target="GT_C101_A">어린이보호구역</a></li>
			<li><a href="#this" data-target="GT_C103_A">일방통행</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C117_A">장애인보호구역</a></li>
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

	$("#statistics").load("${context}/statistics/totalStatisticsPopE.do",{facilityId:"GT_C115_A"});


});


</script>