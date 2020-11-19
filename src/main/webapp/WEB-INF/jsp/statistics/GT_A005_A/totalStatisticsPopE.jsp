<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	<form action="${context}/statistics/selectStatisticsExcelDown.do" name="frm" id="frm" method="post">
		<input type="hidden" id="facilityId" name="facilityId" value="<c:out value='${params.facilityId}'/>" />
		<input type="hidden" id="facilityName" name="facilityName" value="안전지대" />
		<input type="hidden" id="arrHeader" name="arrHeader" value="" />
		<input type="hidden" id="statType" name="statType" value="전체현황" />
	</form>

	<ul class="tab">
		<li class="current"><a href="#this" data-target="total">전체현황</a></li>
		<li><a href="#this" data-target="where">조건별 현황</a></li>
		<li><a href="#this" data-target="detail">상세내역</a></li>
		<li><a href="#this" data-target="cross">교차로기준 현황</a></li>
	</ul>

	<div class="btn_wrap tp2 mg_t25">
		<div class="area_r">
			<a href="#this" class="btn btn_excel" style="width: 105px;">엑셀 다운로드</a>
		</div>
	</div>


<div class="mg_t15 chart"></div>

	<h3 class="h3_tp4" style="font-weight:400">구별 통계</h3>

	<div class="mg_t15 GuResult"></div>

	<h3 class="h3_tp4 mg_t25" style="font-weight:400">경찰서별 통계</h3>

	<div class="mg_t15 PoliceResult"></div>

	<h3 class="h3_tp4 mg_t25" style="font-weight:400">동별 통계</h3>

	<div class="mg_t15 mg_b30 DongResult"></div>

<script>
    var  statisticsTable = null;
    $(function() {
		$("ul.tab > li > a").on("click",function (evt) {
            $("#statistics").load("${context}/statistics/"+$(this).attr("data-target")+"StatisticsPopE.do?facilityId="+"${params.facilityId}");
        });

        $(".btn_excel").click(function() {
            html2canvas($("canvas")[0]).then(function(canvas) {
                $.ajax({
                    type : "post",
                    url : G.baseUrl + "statistics/imgUpload.json",
                    dataType : "json",
                    data : {
                        base64String : canvas.toDataURL("image/png"),
                    } ,
                    async : false,
                    success : function() {
                        $("#arrHeader").val("구분, 합계, 수량");
                        $("#frm").submit();
                    }
                });
            });
        });

        statisticsTable = new StatisticsTable("#content", {
            url					: "${context}/statistics/selectStatisticsInfo.json",
            reqData			: {facilityId : "${params.facilityId}"},
           loadComplete:function (json) {
			   var data = json.GuResult.slice(1, json.GuResult.length);
               var chartResult = new ChartResult(".chart",{
                   jsonData : data,
                   title			: "구별 통계"
               });
           },
        });
    });
</script>
