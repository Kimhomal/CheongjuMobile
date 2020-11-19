<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="detailFrm" id="detailFrm" method="post">
	<%--<input type="hidden" id="facilityId" name="facilityId" value="GT_A061_P">--%>
	<h2 class="h2_tp1">전체 통계</h2>

	<h3 class="h3_tp4 mg_t15" style="font-weight:400">교통안전시설물</h3>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp3">
			<li><a href="#this" data-target="GT_A049_P">검지기 (${trafficCnt.a049Cnt})</a></li>
			<li><a href="#this" data-target="GT_A111_A">공영주차장 (${trafficCnt.a111Cnt})</a></li>
			<li><a href="#this" data-target="GT_A054_P">노면문자표시 (${trafficCnt.a054Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A055_P">노면방향표시 (${trafficCnt.a055Cnt})</a></li>

			<li><a href="#this" data-target="GT_A078_L">배선도 (${trafficCnt.a078Cnt})</a></li>
			<li><a href="#this" data-target="GT_A057_L">부착대 (${trafficCnt.a057Cnt})</a></li>
			<li><a href="#this" data-target="GT_A110_P">신호등 (${trafficCnt.a110Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="1">보행등보조장치(X) (${trafficCnt.bohaengCnt})</a></li>

			<li><a href="#this" data-target="GT_A005_A">안전지대 (${trafficCnt.a005Cnt})</a></li>
			<li><a href="#this" data-target="GT_A064_P">안전표지 (${trafficCnt.a064Cnt})</a></li>
			<li><a href="#this" data-target="GT_A090_P">음향신호기 (${trafficCnt.a090Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A090_P">보행자버튼 (${trafficCnt.a090Cnt})</a></li>

			<li><a href="#this" data-target="GT_A079_L">유턴구역 (${trafficCnt.a079Cnt})</a></li>
			<li><a href="#this" data-target="GT_A081_L">정지선 (${trafficCnt.a081Cnt})</a></li>
			<li><a href="#this" data-target="GT_A085_A">정차금지지대 (${trafficCnt.a085Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A061_P">제어기 (${trafficCnt.a061Cnt})</a></li>

			<li><a href="#this" data-target="GT_A080_A">주차구획 (${trafficCnt.a080Cnt})</a></li>
			<li><a href="#this" data-target="GT_A082_L">주차금지 (${trafficCnt.a082Cnt})</a></li>
			<li><a href="#this" data-target="GT_A083_L">중앙선 (${trafficCnt.a083Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A062_P">지주 (${trafficCnt.a062Cnt})</a></li>

			<li><a href="#this" data-target="GT_A084_L">차선 (${trafficCnt.a084Cnt})</a></li>
			<li><a href="#this" data-target="GT_C024_P">통신맨홀 (${trafficCnt.c024Cnt})</a></li>
			<li><a href="#this" data-target="GT_A004_A">횡단보도 (${trafficCnt.a004Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A068_P">횡단보도예고표시 (${trafficCnt.a068Cnt})</a></li>

			<li><a href="#this" data-target="1">횡단보도조명(X) (${trafficCnt.jomeongCnt})</a></li>
		</ul>
	</div>

	<br>

	<h3 class="h3_tp4 mg_t15" style="font-weight:400">도로안전시설물</h3>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp3">
			<li><a href="#this" data-target="GT_A069_P">CCTV (${rodCnt.a069Cnt})</a></li>
			<li><a href="#this" data-target="GT_C100_P">가로등 (${rodCnt.c100Cnt})</a></li>
			<li><a href="#this" data-target="GT_C088_P">갈매기표지 (${rodCnt.c088Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C104_A">고원식교차로 (${rodCnt.c104Cnt})</a></li>

			<li><a href="#this" data-target="GT_A067_A">과속방지턱 (${rodCnt.a067Cnt})</a></li>
			<li><a href="#this" data-target="GT_C109_A">교통섬 (${rodCnt.c109Cnt})</a></li>
			<li><a href="#this" data-target="GT_C051_P">도로반사경 (${rodCnt.c051Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C091_A">미끄럼방지시설 (${rodCnt.c091Cnt})</a></li>

			<li><a href="#this" data-target="GT_C059_A">방호울타리 (${rodCnt.c059Cnt})</a></li>
			<li><a href="#this" data-target="GT_C092_P">시선유도봉 (${rodCnt.c092Cnt})</a></li>
			<li><a href="#this" data-target="GT_C093_P">시선유도표지 (${rodCnt.c093Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C095_A">입체횡단시설 (${rodCnt.c095Cnt})</a></li>

			<li><a href="#this" data-target="GT_C096_P">장애물표적표지 (${rodCnt.c096Cnt})</a></li>
			<li><a href="#this" data-target="GT_C097_P">전광판 (${rodCnt.c097Cnt})</a></li>
			<li><a href="#this" data-target="GT_C094_A">점자블럭 (${rodCnt.c094Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C098_A">충격흡수시설 (${rodCnt.c098Cnt})</a></li>

			<li><a href="#this" data-target="GT_C107_A">컬러포장 (${rodCnt.c107Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_A065_L">표지병 (${rodCnt.a065Cnt})</a></li>
		</ul>
	</div>

	<br>

	<h3 class="h3_tp4 mg_t15" style="font-weight:400">특수교통운영구역</h3>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp3">
			<li><a href="#this" data-target="GT_C115_A">노인보호구역 (${specialCnt.c115Cnt})</a></li>
			<li><a href="#this" data-target="GT_C101_A">어린이보호구역 (${specialCnt.c101Cnt})</a></li>
			<li><a href="#this" data-target="GT_C103_A">일방통행 (${specialCnt.c103Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C117_A">장애인보호구역 (${specialCnt.c117Cnt})</a></li>
		</ul>
	</div>

	<br>

	<h3 class="h3_tp4 mg_t15" style="font-weight:400">기본도</h3>

	<div class="tap_wrap mg_t15">
		<ul class="tapMenu tp3">
			<li><a href="#this" data-target="GT_A008_A">교차로 (${baseCnt.a008Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C114_A">자전거전용도로 (${baseCnt.c114Cnt})</a></li>
		</ul>
	</div>

	<br>

	<h3 class="h3_tp4 mg_t15" style="font-weight:400">기타</h3>

	<div class="tap_wrap mg_t15 mg_b30">
		<ul class="tapMenu tp3">
			<li><a href="#this" data-target="GT_C118_P">BIS단말기 (${etcCnt.c118Cnt})</a></li>
			<li class="mg_r0"><a href="#this" data-target="GT_C111_P">승강장 (${etcCnt.c111Cnt})</a></li>
		</ul>
	</div>

</form>
<script>

$(function() {

});


</script>