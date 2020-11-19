<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content">
<!-- 안전표지 등록 : start-->

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
			<div class="tit">표지코드</div>
			<div class="cont">
<!-- 				<input type="text" id="mrkCde" name="mrkCde" value="" maxlength="50"/> -->
				<input type="text" id="mrkCde" name="mrkCde" value="<c:out value='${result.mrkCde}'/>" class="w80p readonly" readonly="readonly"/>
				<a href="#this" id="mrkSelect" class="btn sml_tp2 bor_gray_tp3" >선택</a>

			</div>
		</li>
		<li>
			<div class="tit">상세내용</div>
			<div class="cont">
				<input type="text" id="ntc" name="ntc" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">종류</div>
			<div class="cont">
				<select id="mrkKnd" name="mrkKnd">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">규격</div>
			<div class="cont">
				<select id="std" name="std">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">반사재</div>
			<div class="cont">
				<select id="mor" name="mor">
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
			<div class="tit">보조표시</div>
			<div class="cont">
				<select id="sptMrk" name="sptMrk">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">지주관리번호</div>
			<div class="cont">
				<input type="text" id="sprtMgrnu" name="sprtMgrnu" class="w80p readonly" value="" maxlength="30"/>
				<a href="#this" data-lyrId="GT_A062_P" data-inputId="sprtMgrnu" data-inputAttr ="MGRNU" class="btn sml_tp2 bor_gray_tp3 facilitySelect" >선택</a>
			</div>
		</li>
		<li>
			<div class="tit">지주재질</div>
			<div class="cont">
				<select id="sprtMop" name="sprtMop">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">지주종류</div>
			<div class="cont">
				<select id="sprtKnd" name="sprtKnd">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">도로종류</div>
			<div class="cont">
				<select id="rodKnd" name="rodKnd">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">도로형태</div>
			<div class="cont">
				<select id="rodFrm" name="rodFrm">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">부착대관리번호</div>
			<div class="cont">
				<input type="text" id="asnMgrnu" name="asnMgrnu" class="w80p readonly" value="" maxlength="30"/>
				<a href="#this" data-lyrId="GT_A057_L" data-inputId="asnMgrnu" data-inputAttr ="MGRNU" class="btn sml_tp2 bor_gray_tp3 facilitySelect" >선택</a>
			</div>
		</li>
		<li>
			<div class="tit">대장타입</div>
			<div class="cont">
				<select id="lgrType" name="lgrType">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">대장구분</div>
			<div class="cont">
				<select id="lgrGbn" name="lgrGbn">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">보조표지기록</div>
			<div class="cont">
				<input type="text" id="sptMrkEt" name="sptMrkEt" value="" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">표지관리번호</div>
			<div class="cont">
				<input type="text" id="mrkCdeWe" name="mrkCdeWe" value="" maxlength="50"/>
			</div>
		</li>


		</ul>

		<div class="btn_wrap tp1">
			<a href="javascript:insertInfo();" class="btn half_tp1 blue">등록</a>
			<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
		</div>
	</form>
</div>

<!-- //안전표지 등록 : end-->

<script>
//# sourceURL= /fclts/a005_a/insertInfoPop.jsp.jsp
$(function(){
	var jsonData = mapMaker.mapAction.setFtFromAttr(facilityFt, $("#insertFrm"));

	var mrkKnd = new AjaxComboBox("#mrkKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MRK_KND&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var std = new AjaxComboBox("#std", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=STD&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var mor = new AjaxComboBox("#mor", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MOR&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var mop = new AjaxComboBox("#mop", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MOP&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var sptMrk = new AjaxComboBox("#sptMrk", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SPT_MRK&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var sprtMop = new AjaxComboBox("#sprtMop", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SPRT_MOP&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var sprtKnd = new AjaxComboBox("#sprtKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SPRT_KND&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var rodKnd = new AjaxComboBox("#rodKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=ROD_KND&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var rodFrm = new AjaxComboBox("#rodFrm", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=ROD_FRM&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var eve = new AjaxComboBox("#eve", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=EVE&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var lgrType = new AjaxComboBox("#lgrType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=LGR_TYPE&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var lgrGbn = new AjaxComboBox("#lgrGbn", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=LGR_GBN&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	$("#mrkSelect").on("click",function(evt){
		$("#insertFrm").popupWindow("${context}/fclts/A064_P/igtSignPopW.do",{id:"igtSignPop",scrollbars:0,width:"545",height:"530"});
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
