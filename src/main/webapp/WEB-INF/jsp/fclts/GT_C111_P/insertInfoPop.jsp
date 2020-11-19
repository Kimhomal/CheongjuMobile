<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content">
<!-- 승강장 등록 : start-->

	<form id="insertFrm" name="insertFrm">
	<input type="hidden" id="method" name="method" value="${params.method}" />
	<input type="hidden" id="geom" name="geom" />
	<input type="hidden" id="center" name="center" />
	<input type="hidden" id="xce" name="xce" />
	<input type="hidden" id="yce" name="yce" />
	<input type="hidden" id="minX" name="minX" />
	<input type="hidden" id="minY" name="minY" />
	<input type="hidden" id="maxX" name="maxX" />
	<input type="hidden" id="maxY" name="maxY" />
	<input type="hidden" id="fileYn" name="fileYn" />
	<input type="hidden" id="facilityId" name="facilityId" value="<c:out value='${params.facilityId}'/>" />
	<input type="hidden" id="drawingYn" name="drawingYn" value="Y" />
	<input type="hidden" id="cstNo" name="cstNo" value="<c:out value='${params.cstNo}'/>"/>
	<input type="hidden" id="cstStatCd" name="cstStatCd" value="<c:out value='${params.cstStatCd}'/>" />
	<input type="hidden" id="gbn" name="gbn" value="<c:out value='${params.gbn}'/>" />
		<ul class="list_tp8 col_tp1">
			<li>
				<div class="tit">관리번호</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="mgrnu" name="mgrnu" value="" maxlength="30" readOnly/>
				</div>
			</li>
	<!-- 		<li> -->
	<!-- 			<div class="tit">정류장ID</div> -->
	<!-- 			<div class="cont"> -->
	<!-- 				<input type="text" id="stationId" name="stationId" class="w80p readonly" value="" maxlength="30" /> -->
	<!-- 				<a href="#this" data-lyrId="GT_C111_P" data-inputId="stationId" data-inputAttr ="MGRNU" class="btn sml_tp2 bor_gray_tp3 facilitySelect" >선택</a> -->
	<!-- 			</div> -->
	<!-- 		</li> -->
			<li>
				<div class="tit"><span class="necessary">*</span>정류장명</div>
				<div class="cont">
					<input type="text" id="name" name="name" value="" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit"><span class="necessary">*</span>구조</div>
				<div class="cont">
					<input type="text" id="structure" name="structure" value="" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit"><span class="necessary">*</span>관리책임자</div>
				<div class="cont">
					<input type="text" id="mngNam" name="mngNam" value="" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit"><span class="necessary">*</span>부관리자</div>
				<div class="cont">
					<input type="text" id="subMngNam" name="subMngNam" value="" maxlength="50"/>
				</div>
			</li>

		</ul>

		<div class="btn_wrap tp1">
			<a href="javascript:insertInfo();" class="btn half_tp1 blue">등록</a>
			<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
		</div>
	</form>
</div>

<!-- //승강장 등록 : end-->

<script>
//# sourceURL= /fclts/a005_a/insertInfoPop.jsp.jsp
$(function(){
	var jsonData = mapMaker.mapAction.setFtFromAttr(facilityFt, $("#insertFrm"));

	var direction = new AjaxComboBox("#direction", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=DIRECTION&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

});

function insertInfo() {
// 	var ft = mapMaker.layer.polygon.getSource().getFeatures()[0];

	var feature  = new ol.Feature({
		geometry: mapMaker.layer.point.getSource().getFeatures()[0].clone().getGeometry().transform( mapMaker.crsCode, mapMaker.config.facilityCrsCode),
	});

	var extent = feature.getGeometry().getExtent();
	var coord = feature.getGeometry().getCoordinates();

	var $frm = $("#insertFrm");

	$("#minX").val(extent[0]);
	$("#minY").val(extent[1]);
	$("#maxX").val(extent[2]);
	$("#maxY").val(extent[3]);
	$frm .find("#xce").val(coord[0]);
	 $frm .find("#yce").val(coord[1]);


	var pt = ol.extent.getCenter(extent);

	var geom = mapMaker.getWkt(feature);
	$("#geom").val(geom);
	var center = "POINT(" + pt[0] + " " + pt[1] + ")";
	$("#center").val(center);

	if(!$frm.isDivEmptyAlertForm()) return;
	// if($frm.find("#cstNo").isEmptyAlert("공사")) return;

	if(confirm("등록하시겠습니까?")) {
		$("input[type='file']").each(function(){
			if($(this).val() != ""){
				$("#fileYn").val("Y");
				return false;
			}
		});  // 파일 첨부된 내용이 있으면 fileYn에 구분값 Y를 준다.

		var options = {
			url				: "${context}/fclts/insertFacility.json",
			type			: "post",
			dataType		: "json",
			success			:  function(json) {
				if(json.cnt == 1){
					alert("정상적으로 등록되었습니다.");
					var lyr = mapMaker.mapLayerMng.getLayerById($("#facilityId").val());
					lyr.getSource().clear();
					$("#modal-controller-1").find(".modal_close").trigger("click");
				} else {
					alert("오류발생, 다시 시도하여 주십시오");
				}
			},
			error : function(response) {
				alert("오류발생, 다시 시도하여 주십시오");
			},
		};

		$frm.ajaxSubmit( options );
	}
}

</script>
