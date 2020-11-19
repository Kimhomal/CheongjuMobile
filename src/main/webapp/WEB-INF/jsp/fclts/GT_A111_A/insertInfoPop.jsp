<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content">
<!-- 공영주차장 등록 : start-->

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
	<input type="hidden" id="area" name="area" />
	<input type="hidden" id="fileYn" name="fileYn" />
	<input type="hidden" id="facilityId" name="facilityId" value="<c:out value='${params.facilityId}'/>" />
	<input type="hidden" id="drawingYn" name="drawingYn" value="Y" />
	<input type="hidden" id="cstNo" name="cstNo" value="<c:out value='${params.cstNo}'/>"/>
	<input type="hidden" id="cstStatCd" name="cstStatCd" value="<c:out value='${params.cstStatCd}'/>" />
	<input type="hidden" id="gbn" name="gbn" value="<c:out value='${params.gbn}'/>" />
		<ul class="list_tp8 col_tp1">
		<li>
			<div class="tit">시설물 입력</div>
			<div class="cont">
				지형지물은 도형 편집만 가능합니다.
			</div>
		</li>
		<li>
			<div class="tit">관리번호</div>
			<div class="cont">
				<input type="text" class="w100p readonly" id="mgrnu" name="mgrnu" value="" maxlength="30" readOnly/>
			</div>
		</li>
		<li>
			<div class="tit">주차장명</div>
			<div class="cont">
				<input type="text" id="nam" name="nam" value="" maxlength="30"/>
			</div>
		</li>
		<li>
			<div class="tit">주차장면수</div>
			<div class="cont">
				<input type="text" id="cap" name="cap" value="" maxlength="30"/>
			</div>
		</li>
		<li>
			<div class="tit">장애인면수</div>
			<div class="cont">
				<input type="text" id="disCap" name="disCap" value="" maxlength="30"/>
			</div>
		</li>
		<li>
			<div class="tit">급지<span class="necessary">*</span></div>
			<div class="cont">
				<select id="geupji" name="geupji">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">구분<span class="necessary">*</span></div>
			<div class="cont">
				<select id="gubun" name="gubun">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">유무료<span class="necessary">*</span></div>
			<div class="cont">
				<select id="paidFree" name="paidFree">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">설치일</div>
			<div class="cont">
				<input type="text" id="esbYmd" name="esbYmd" value="" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">비고</div>
			<div class="cont">
				<input type="text" id="etc" name="etc" value="" maxlength="30"/>
			</div>
		</li>
		</ul>

		<div class="btn_wrap tp1">
			<a href="javascript:insertInfo();" class="btn half_tp1 blue">등록</a>
			<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
		</div>
	</form>
</div>

<!-- //공영주차장 등록 : end-->

<script>
//# sourceURL= /fclts/a005_a/insertInfoPop.jsp.jsp
$(function(){
	var jsonData = mapMaker.mapAction.setFtFromAttr(facilityFt, $("#insertFrm"));

	var geupji = new AjaxComboBox("#geupji", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=GEUPJI&refCd=A111_A'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var gubun = new AjaxComboBox("#gubun", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=GUBUN&refCd=A111_A'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var paidFree = new AjaxComboBox("#paidFree", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=PAID_FREE&refCd=A111_A'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

});

function insertInfo() {
// 	var ft = mapMaker.layer.polygon.getSource().getFeatures()[0];

	var feature  = new ol.Feature({
		geometry: mapMaker.layer.polygon.getSource().getFeatures()[0].clone().getGeometry().transform( mapMaker.crsCode, mapMaker.config.facilityCrsCode),
	});

	var extent = feature.getGeometry().getExtent();
	var area = feature.getGeometry().getArea();

	var $frm = $("#insertFrm");


	$("#minX").val(extent[0]);
	$("#minY").val(extent[1]);
	$("#maxX").val(extent[2]);
	$("#maxY").val(extent[3]);
	$("#area").val(area);


	var pt = ol.extent.getCenter(feature.getGeometry().getExtent());

	$frm .find("#xce").val(pt[0]);
	$frm .find("#yce").val(pt[1]);

	var geom = mapMaker.getWkt(feature);
	$("#geom").val(geom);
	var center = "POINT(" + pt[0] + " " + pt[1] + ")";
	$("#center").val(center);


	if(!$frm.isDivEmptyAlertForm()) return;

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
