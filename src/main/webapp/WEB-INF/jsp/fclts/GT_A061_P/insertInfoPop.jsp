<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content">
<!-- 제어기 등록 : start-->

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
			<div class="tit">설치고가</div>
			<div class="cont">
				<select id="eve" name="eve">
				</select>
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
			<div class="tit">기종</div>
			<div class="cont">
				<input type="text" id="mkMdl" name="mkMdl" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit"><span class="necessary">*</span>제어방식</div>
			<div class="cont">
				<select id="ctlMet" name="ctlMet">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">배선방식</div>
			<div class="cont">
				<select id="lineMet" name="lineMet">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">신호기제식</div>
			<div class="cont">
				<select id="conFor" name="conFor">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">제조회사</div>
			<div class="cont">
				<input type="text" id="mkCpy" name="mkCpy" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">제조일자</div>
			<div class="cont">
				<input type="text" id="mkYmd" name="mkYmd" value="" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">계량기관리번호</div>
			<div class="cont">
				<input type="text" id="mea" name="mea" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">계량기제조일자</div>
			<div class="cont">
				<input type="text" id="meaMkymd" name="meaMkymd" value="" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">도로구분</div>
			<div class="cont">
				<select id="rodKnd" name="rodKnd">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">노후제어기교체일</div>
			<div class="cont">
				<input type="text" id="chanYmd" name="chanYmd" value="" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">설치위치</div>
			<div class="cont">
				<input type="text" id="layLoca" name="layLoca" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">연등지구분</div>
			<div class="cont">
				<select id="ctlType" name="ctlType">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">제어모드</div>
			<div class="cont">
				<select id="ctlMod" name="ctlMod">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">전적색상태</div>
			<div class="cont">
				<select id="redC" name="redC">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">현시정보</div>
			<div class="cont">
				<select id="swType" name="swType">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">점멸상태</div>
			<div class="cont">
				<select id="fkrType" name="fkrType">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">회전규제</div>
			<div class="cont">
				<select id="turnCmmt" name="turnCmmt">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">기타사항</div>
			<div class="cont">
				<input type="text" id="etc" name="etc" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">비고란</div>
			<div class="cont">
				<input type="text" id="rmk" name="rmk" value="" maxlength="50"/>
			</div>
		</li>


		</ul>

		<div class="btn_wrap tp1">
			<a href="javascript:insertInfo();" class="btn half_tp1 blue">등록</a>
			<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
		</div>
	</form>
</div>

<!-- //제어기 등록 : end-->

<script>
//# sourceURL= /fclts/a005_a/insertInfoPop.jsp.jsp
$(function(){
	var jsonData = mapMaker.mapAction.setFtFromAttr(facilityFt, $("#insertFrm"));

	var eve = new AjaxComboBox("#eve", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=EVE&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var ctlMet = new AjaxComboBox("#ctlMet", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CTL_MET&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var conFor = new AjaxComboBox("#conFor", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CON_FOR&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var rodKnd = new AjaxComboBox("#rodKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=ROD_KND&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var lineMet = new AjaxComboBox("#lineMet", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=LINE_MET&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var ctlType = new AjaxComboBox("#ctlType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CTL_TYPE&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var ctlMod = new AjaxComboBox("#ctlMod", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CTL_MOD&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var redC = new AjaxComboBox("#redC", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=RED_C&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var swType = new AjaxComboBox("#swType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SW_TYPE&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var fkrType = new AjaxComboBox("#fkrType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=FKR_TYPE&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var turnCmmt = new AjaxComboBox("#turnCmmt", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=TURN_CMMT&refCd=A061_P'/>",
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
