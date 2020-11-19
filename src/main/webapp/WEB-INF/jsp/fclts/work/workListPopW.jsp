<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form action="${context}/fclts/crudPopW.do" name="selectFrm" id="selectFrm" method="post">
	<input type="hidden" id="method" name="method" value="">
	<input type="hidden" id="facId" name="facId" value="<c:out value='${params.facId}'/>" />
	<input type="hidden" id="mgrnu" name="mgrnu" value="<c:out value='${params.mgrnu}'/>" />
	<input type="hidden" id="mgrnuIdx" name="mgrnuIdx" value="<c:out value='${params.mgrnuIdx}'/>" />
</form>
	<h2 class="h2_tp1">이력조회</h2>
<!-- 	<p class="subTit">시설물 이력조회</p> -->
	<div class="board_tp1 mg_t40">
		<div class="cnt"></div>
	</div>


	<!-- board_pager_wrap -->
	<div class="board_pager_wrap">
		<div class="btn_wrap ab_r">
			<a href="#this" id="insertWork" class="btn sml blue">등록</a>
		</div>
	</div>
	<!-- //board_pager_wrap -->



<script>
var searchResultList;
$(function() {

	searchResultList = new SearchResultList(".wPop_content", {
		url					: "${context}/fclts/selectWorkList.json",
		caption : "이력조회",
		reqData			: {method :"select",mgrnu:"${params.mgrnu}",facId:"${params.facId}"},
		colNames		: [
			{name : "번호", width : "10%"},
			{name : "시공일자", width : "25%"},
			{name : "유형", width : "10%"},
			{name : "시공자", width : "15%"},
			{name : "시공내용", width : "30%"}
		],
		colModel		: [
			{name : "num"},
			{name : "worktm"},
			{name : "workCdeNam"},
			{name : "workuser"},
			{name : "workBg"}
		],
		onCellClick		: function(rowIdx, colIdx, cellContent, evt) {
			$("#method").val("select");
			$("#mgrnu").val(searchResultList.getData(rowIdx, "mgrnu"));
			$("#mgrnuIdx").val(searchResultList.getData(rowIdx, "mgrnuIdx"));
			$("#selectFrm").submit();
		}
	});


	$("#insertWork").on('click', function() {
		$("#method").val("insert");
		$("#selectFrm").submit();
	});
})


</script>

</html>