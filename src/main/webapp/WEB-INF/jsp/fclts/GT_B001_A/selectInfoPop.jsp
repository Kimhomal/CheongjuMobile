<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content"  style="overflow-y:auto;">
		<div class="btn_wrap tp2">
<%-- 		<c:if test="${params.mapMethod != null && params.mapMethod != ''&& params.mapMethod != 'ConstruCheck' && params.mapMethod != 'mapOld'}"> --%>
			<a href="#this" class="btn half_tp2 blue" id="viewMode">보기</a>
			<a href="#this" class="btn half_tp2 bor_blue_tp1" id="updateMode">수정</a>
<%-- 		</c:if> --%>
		</div>
<!-- 건물 상세조회 : start-->
<div class="tabCont active">
		<form id="selectFrm" name="selectFrm">
		<input type="hidden" id="method" name="method" value="<c:out value='${params.method}'/>"/>
		<input type="hidden" id="facId" name="facId" value="<c:out value='${params.facilityId}'/>" />
		<input type="hidden" id="drawingYn" name="drawingYn" value="<c:out value='${params.drawingYn}'/>" />
		<input type="hidden" id="mgrnu" name="mgrnu" value="<c:out value='${result.geomseq}'/>" />
		<input type="hidden" id="usrId" name="usrId" value="<c:out value='${sessionScope.auth.usrId}'/>" />
		<input type="hidden" id="cstStatCd" name="cstStatCd" value="<c:out value='${params.cstStatCd}'/>" />
		<input type="hidden" id="rulemgeid" name="rulemgeid" value="<c:out value='${params.rulemgeid}'/>" />
		<input type="hidden" id="rulerstid" name="rulerstid" value="<c:out value='${params.rulerstid}'/>" />
		<input type="hidden" id="pointXce" name="pointXce" />
		<input type="hidden" id="pointYce" name="pointYce" />
		<input type="hidden" id="geom" name="geom" />
		<input type="hidden" id="mapMethod" name="mapMethod" value="<c:out value='${params.mapMethod}'/>" />
		<input type="hidden" id="ctkMgrnu" name="ctkMgrnu" value="<c:out value='${result.ctkMgrnu}'/>" />


		<ul class="list_tp8 col_tp1">
		<li>
			<div class="tit">관리번호</div>
			<div class="cont">
				<c:out value='${result.geomseq}'/>
			</div>
		</li>
		<li>
			<div class="tit">건물명</div>
			<div class="cont">
				<c:out value='${result.buldNm}'/>
			</div>
		</li>

		</ul>
		</form>
		<div class="btn_wrap">
<%-- 			<c:if test="${params.mapMethod != null && params.mapMethod != ''&&params.mapMethod != 'ConstruCheck'}"> --%>
			<a href="#this" id="delete" class="btn half_tp1 blue">삭제</a>
			<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
<%-- 			</c:if> --%>
		</div>
</div>
<!-- //건물 상세조회 : end-->

<!-- 건물 수정 : start-->
<div class="tabCont">
		<form id="updateFrm" name="updateFrm">
		<input type="hidden" id="geom" name="geom" />
		<input type="hidden" id="center" name="center" />
		<input type="hidden" id="facilityId" name="facilityId" value="<c:out value='${params.facilityId}'/>" />
		<input type="hidden" id="drawingYn" name="drawingYn" value="<c:out value='${params.drawingYn}'/>" />
		<input type="hidden" id="delFile" name="delFile" value="N"/>
		<input type="hidden" id="delFileId" name="delFileId" value=""/>
		<input type="hidden" id="newFileYn" name="newFileYn" value="N"/>
		<input type="hidden" id="usrId" name="usrId" value="<c:out value='${sessionScope.auth.usrId}'/>" />
		<input type="hidden" id="pointXce" name="pointXce" />
		<input type="hidden" id="pointYce" name="pointYce" />
		<input type="hidden" id="minX" name="minX" />
		<input type="hidden" id="minY" name="minY" />
		<input type="hidden" id="maxX" name="maxX" />
		<input type="hidden" id="maxY" name="maxY" />
		<input type="hidden" id="cstStatCd" name="cstStatCd" value="<c:out value='${params.cstStatCd}'/>" />
		<input type="hidden" id="rulemgeid" name="rulemgeid" value="<c:out value='${params.rulemgeid}'/>" />
		<input type="hidden" id="rulerstid" name="rulerstid" value="<c:out value='${params.rulerstid}'/>" />
		<input type="hidden" id="rnCdeYn" name="rnCdeYn" value="Y" />
		<input type="hidden" id="nwPeYn" name="nwPeYn" value="Y" />
		<input type="hidden" id="mapMethod" name="mapMethod" value="<c:out value='${params.mapMethod}'/>" />
		<input type="hidden" id="TfcbssYn" name="TfcbssYn" value="Y" />
		<input type="hidden" id="sixidYn" name="sixidYn" value="Y" />
		<ul class="list_tp8 col_tp1">
		<li>
			<div class="tit">관리번호</div>
			<div class="cont">
				<input type="text" class="w100p readonly" id="mgrnu" name="mgrnu" value="<c:out value='${result.geomseq}'/>" maxlength="30" readOnly/>
			</div>
		</li>
		<li>
			<div class="tit">건물명</div>
			<div class="cont">
				<input type="text" id="bldNam" name="bldNam" value="<c:out value='${result.buldNm}'/>" maxlength="50"/>
			</div>
		</li>

		</ul>
		</form>
	<div class="btn_wrap tp1">
		<a href="javascript:updateInfo();" class="btn half_tp1 blue">수정</a>
		<a href="#this" class="btn half_tp1 gray_tp4 modal_close">취소</a>
	</div>
</div>
</div>
<!-- //건물 수정 : end-->

<script>
//# sourceURL= /fclts/a005_a/selectInfoPop.jsp.jsp
$(function(){

	var direction = new AjaxComboBox("#direction", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=DIRECTION&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.direction}"});

	$("#delete").click(function(){
		var $frm = $("#selectFrm");

		var ft  = new ol.Feature({
			geometry: mapMaker.control.edit.select.getFeatures().getArray()[0].clone().getGeometry().transform( mapMaker.crsCode, mapMaker.config.facilityCrsCode),
		});
		var pt = ol.extent.getCenter(ft.getGeometry().getExtent());

		$frm.find('#pointXce').val(pt[0]);
		$frm.find('#pointYce').val(pt[1]);

		var geom = mapMaker.getWkt(ft);

		$frm.find("#geom").val(geom);


		if(confirm("삭제하시겠습니까?")) {
			var options = {
				url				: "${context}/fclts/deleteFacility.json",
				type			: "post",
				data			:{},
				dataType		: "json",
				success			:  function(json) {
					if(json.cnt == 1){

						alert("정상적으로 삭제되었습니다.");
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

	});

});

function updateInfo() {
	var $frm = $("#updateFrm");
	var ft  = new ol.Feature({
		geometry: mapMaker.control.edit.select.getFeatures().getArray()[0].clone().getGeometry().transform( mapMaker.crsCode, mapMaker.config.facilityCrsCode),
	});
	var extent = ft.getGeometry().getExtent();
	var pt = ol.extent.getCenter(ft.getGeometry().getExtent());


	$frm.find('#pointXce').val(pt[0]);
	$frm.find('#pointYce').val(pt[1]);
	$("#minX").val(extent[0]);
	$("#minY").val(extent[1]);
	$("#maxX").val(extent[2]);
	$("#maxY").val(extent[3]);

	var geom = mapMaker.getWkt(ft);

	$frm.find("#geom").val(geom);

	if(confirm("수정하시겠습니까?")) {

		var options = {
			url				: "${context}/fclts/updateFacility.json",
			type			: "post",
			data			:{},
			dataType		: "json",
			success			:  function(json) {
				if(json.cnt == 1){
					alert("정상적으로 수정되었습니다.");
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

//이미지 view
var imgObj = new Image();

function showImage(imgName, oglnam) {//이미지 크게보기
	imgObj.src = imgName;
	imageWin = window.open("", "imageWin", "width=540,height=478,top=0, left=530, scrollbars=no");
	imageWin.document.write("<html><body style='margin:0'>");
	imageWin.document.write("<img src='" + imgObj.src + "' border=0 width=540 height=478>");
	imageWin.document.write("</body><html>");
	imageWin.document.title = oglnam;
}

</script>