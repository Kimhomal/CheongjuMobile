<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content">
<!-- 신호등 등록 : start-->

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
		<li>
			<div class="tit">신호등명</div>
			<div class="cont">
				<input type="text" id="snlpNam" name="snlpNam" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">설치일자</div>
			<div class="cont">
				<input type="text" id="esbYmd" name="esbYmd" value="" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">설치회사</div>
			<div class="cont">
				<input type="text" id="esbCpy" name="esbCpy" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>지주ID</div>
			<div class="cont">
				<input type="text" id="sprtMgrnu" name="sprtMgrnu" class="w80p readonly" value="" maxlength="30"/>
				<a href="#this" data-lyrId="GT_A062_P" data-inputId="sprtMgrnu" data-inputAttr ="MGRNU" class="btn sml_tp2 bor_gray_tp3 facilitySelect" >선택</a>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>제어기ID</div>
			<div class="cont">
				<input type="text" id="ctrMgrnu" name="ctrMgrnu" class="w80p readonly" value="" maxlength="30"/>
				<a href="#this" data-lyrId="GT_A061_P" data-inputId="ctrMgrnu" data-inputAttr ="MGRNU" class="btn sml_tp2 bor_gray_tp3 facilitySelect" >선택</a>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>부착대관리번호</div>
			<div class="cont">
				<input type="text" id="asnMgrnu" name="asnMgrnu" class="w80p readonly" value="" maxlength="30"/>
				<a href="#this" data-lyrId="GT_A057_L" data-inputId="asnMgrnu" data-inputAttr ="MGRNU" class="btn sml_tp2 bor_gray_tp3 facilitySelect" >선택</a>
			</div>
		</li>
		<li>
			<div class="tit">방향구분</div>
			<div class="cont">
				<select id="drnType" name="drnType">
				</select>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>방향</div>
			<div class="cont">
				<input type="text" id="drn" name="drn" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>종류</div>
			<div class="cont">
				<select id="signalCde" name="signalCde">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">종류2</div>
			<div class="cont">
				<select id="knd" name="knd">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">구분</div>
			<div class="cont">
				<select id="gubun" name="gubun">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">재질</div>
			<div class="cont">
				<select id="mop" name="mop">
				</select>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>용도</div>
			<div class="cont">
				<select id="use" name="use">
				</select>
			</div>
		</li>

		</ul>

		<div class="btn_wrap tp1">
			<a href="javascript:insertInfo();" class="btn half_tp1 blue">등록</a>
			<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
		</div>
	</form>
</div>

<!-- //신호등 등록 : end-->

<script>
//# sourceURL= /fclts/a005_a/insertInfoPop.jsp.jsp
$(function(){
	var jsonData = mapMaker.mapAction.setFtFromAttr(facilityFt, $("#insertFrm"));

	var gubun = new AjaxComboBox("#gubun", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=GUBUN&refCd=A110_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var knd = new AjaxComboBox("#knd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=KND&refCd=A110_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var signalCde = new AjaxComboBox("#signalCde", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SIGNAL_CDE&refCd=A110_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var mop = new AjaxComboBox("#mop", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MOP&refCd=A110_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var direction = new AjaxComboBox("#direction", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=DIRECTION&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var use = new AjaxComboBox("#use", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=USE&refCd=A110_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

    mapMaker.mapAction.setImgFromAttr(facilityFt, {
        "signalCde" : "",
        "use" : "",
    });

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
