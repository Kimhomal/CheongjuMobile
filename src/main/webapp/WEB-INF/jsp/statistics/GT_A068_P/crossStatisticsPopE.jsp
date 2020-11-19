<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<ul class="tab">
	<li><a href="#this" data-target="total">전체현황</a></li>
	<li><a href="#this" data-target="where">조건별 현황</a></li>
	<li><a href="#this" data-target="detail">상세내역</a></li>
	<li class="current"><a href="#this" data-target="cross">교차로기준 현황</a></li>
</ul>

<form  action="${context}/statistics/selectStatisticsExcelDown.do" id="frm" name="frm" method="post">
	<input type="hidden" id="facilityId" name="facilityId" value="<c:out value='${params.facilityId}'/>" />
	<input type="hidden" id="facilityName" name="facilityName" value="횡단보도예고표시" />
	<input type="hidden" id="guCde" name="guCde" value="" />
	<input type="hidden" id="dongCde" name="dongCde" value="" />
	<input type="hidden" id="policeCde" name="policeCde" value="" />
	<input type="hidden" id="statGbn" name="statGbn" value="" />
	<input type="hidden" id="arrHeader" name="arrHeader" value="" />
	<input type="hidden" id="statType" name="statType" value="교차로기준현황" />

	<div class="contBox_tp3" style="width: 761px;">
		<div class="area_wrap">
			<div class="area_l w50p pd_r10">
				<div id="mGu" class="board"></div>
				<div id="mPolice" class="board"></div>
			</div>
			<div class="area_r w50p pd_r10">
				<div id="mDong" class="board"></div>
			</div>
		</div>
		<div class="search_wrap_tp3 mg_t10" style="width: 710px; padding: 15px 0 15px 138px;">
			<div class="inp_wrap">
				<div class="inp tit" style="width: 100px;">설치일</div>
				<div class="inp">
					<input type="text" id="stYmd" name="stYmd" class="ico_calendar datapicker_open_std" style="width:120px;">
				</div>
				<div class="inp">~</div>
				<div class="inp">
					<input type="text" id="enYmd" name="enYmd" class="ico_calendar datapicker_open_end" style="width:120px;">
				</div>

			</div>
			<div class="inp_wrap">
				<div class="inp tit" style="width: 100px;">교차로명</div>
				<div class="inp">
					<input type="text" id="cssNam" name="cssNam" class="" style="width:258px;">
				</div>
			</div>
		</div>
		<div class="btn_wrap tp3">
			<a href="#this" class="btn sml_tp3 blue" id="stat">통계보기</a>
			<a href="#this" class="btn btn_excel" id="excelDown" style="width: 105px;">엑셀 다운로드</a>
		</div>
	</div>
</form>

<h3 class="h3_tp4 mg_t15" style="font-weight:400">교차로기준 통계</h3>

<div class="mg_t15 CrossResult"></div>

<div class="board_pager_wrap"></div>

<script>

    var  statisticsTable = null;
    var  mGu = null;
    var  mDong = null;
    var  mPolice = null;
    $(function() {

        $("ul.tab > li > a").on("click",function (evt) {
            $("#statistics").load("${context}/statistics/"+$(this).attr("data-target")+"StatisticsPopE.do?facilityId="+"${params.facilityId}");
        });

        $("ul.tab > li > a").css({'border-bottom' : '0px'});

        setDatePicker();


        mGu = new MultiSelectBoard("#mGu", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=guCde'/>",
            type : "get",
            caption : "*시군구",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        mDong =  new MultiSelectBoard("#mDong", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=emdCde'/>",
            type : "get",
            caption : "*읍면동",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        mPolice =  new MultiSelectBoard("#mPolice", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=peCde'/>",
            type : "get",
            caption : "*경찰서",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        $(".btn_excel").click(function() {
            $("#guCde").val(mGu.getSelectVal());
            $("#dongCde").val(mDong.getSelectVal());
            $("#policeCde").val(mPolice.getSelectVal());
            $("#arrHeader").val("구분, 합계, 수량");
            $("#frm").submit();
        });

        statisticsTable = new StatisticsTable("#content", {
            url					: "${context}/statistics/selectCrossStatisticsInfo.json",
            pagingYn			: true,
            reqData			: {facilityId : "${params.facilityId}"}
        });

        $("#stat").on("click",function () {
            statisticsTable.reloadList({
                facilityId : "${params.facilityId}",
                guCde : mGu.getSelectVal(),
                dongCde : mDong.getSelectVal(),
                policeCde : mPolice.getSelectVal(),
                stYmd : $("#stYmd").val(),
                enYmd : $("#enYmd").val(),
                cssNam :  $("#cssNam").val()
            });
        });

    });
</script>
