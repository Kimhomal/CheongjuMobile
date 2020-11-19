<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content"  style="overflow-y:auto;">
		<div class="btn_wrap tp2">
<%-- 		<c:if test="${params.mapMethod != null && params.mapMethod != ''&& params.mapMethod != 'ConstruCheck' && params.mapMethod != 'mapOld'}"> --%>
			<a href="#this" class="btn half_tp2 blue" id="viewMode">보기</a>
			<a href="#this" class="btn half_tp2 bor_blue_tp1" id="updateMode">수정</a>
<%-- 		</c:if> --%>
		</div>
<!-- 어린이보호구역 상세조회 : start-->
<div class="tabCont active">
		<form id="selectFrm" name="selectFrm">
		<input type="hidden" id="method" name="method" value="<c:out value='${params.method}'/>"/>
		<input type="hidden" id="facId" name="facId" value="<c:out value='${params.facilityId}'/>" />
		<input type="hidden" id="drawingYn" name="drawingYn" value="<c:out value='${params.drawingYn}'/>" />
		<input type="hidden" id="mgrnu" name="mgrnu" value="<c:out value='${result.mgrnu}'/>" />
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
					<c:out value='${result.mgrnu}'/>
				</div>
			</li>
			<li>
				<div class="tit">공사관리번호</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="ctkMgrnu" name="ctkMgrnu" value="<c:out value='${result.ctkMgrnu}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">지정일자</div>
				<div class="cont">
					<c:out value='${result.regYmd}'/>
				</div>
			</li>
			<li>
				<div class="tit">개선완료일</div>
				<div class="cont">
					<c:out value='${result.esbYmd}'/>
				</div>
			</li>
			<li>
				<div class="tit">구분</div>
				<div class="cont">
					<c:out value='${result.schKnd}'/>
				</div>
			</li>
			<li>
				<div class="tit">학교명</div>
				<div class="cont">
					<c:out value='${result.nam}'/>
				</div>
			</li>
			<li>
				<div class="tit">전화번호</div>
				<div class="cont">
					<c:out value='${result.schTel}'/>
				</div>
			</li>
			<li>
				<div class="tit">세부주소</div>
				<div class="cont">
					<c:out value='${result.schAddr}'/>
				</div>
			</li>
			<li>
				<div class="tit">길이</div>
				<div class="cont">
					<c:out value='${result.len}'/>
				</div>
			</li>
			<li>
				<div class="tit">교통사고[사망]</div>
				<div class="cont">
					<c:out value='${result.die}'/>
				</div>
			</li>
			<li>
				<div class="tit">교통사고[부상]</div>
				<div class="cont">
					<c:out value='${result.inj}'/>
				</div>
			</li>
			<li>
				<div class="tit">도로폭</div>
				<div class="cont">
					<c:out value='${result.rodWid}'/>
				</div>
			</li>
			<li>
				<div class="tit">속도제한[㎞]</div>
				<div class="cont">
					<c:out value='${result.spdLtd}'/>
				</div>
			</li>
			<li>
				<div class="tit">특이사항</div>
				<div class="cont">
					<c:out value='${result.memo}'/>
				</div>
			</li>
			<li>
				<div class="tit">설치일자</div>
				<div class="cont">
					<c:out value='${result.esbYmd}'/>
				</div>
			</li>
			<li>
				<div class="tit">비고</div>
				<div class="cont">
					<c:out value='${result.rmk}'/>
				</div>
			</li>
			<li>
				<div class="tit">주소</div>
				<div class="cont">
					<c:out value='${result.addr}'/>
				</div>
			</li>
			<li>
				<div class="tit">교차로명</div>
				<div class="cont">
					<c:out value='${result.cssNam}'/>
				</div>
			</li>
			<li>
				<div class="tit">경찰서명</div>
				<div class="cont">
					<c:out value='${result.peNam}'/>
				</div>
			</li>
			<li>
				<div class="tit">어린이보호구역명</div>
				<div class="cont">
					<c:out value='${result.schNam}'/>
				</div>
			</li>
			<li>
				<div class="tit">면적</div>
				<div class="cont">
					<c:out value='${result.area}'/>
				</div>
			</li>
			<li>
				<div class="tit">X</div>
				<div class="cont">
					<c:out value='${result.xce}'/>
				</div>
			</li>
			<li>
				<div class="tit">Y</div>
				<div class="cont">
					<c:out value='${result.yce}'/>
				</div>
			</li>
			<li>
				<div class="tit">관리대장</div>
				<div class="cont">
					<a id="chilPop" name="chilPop"href='#this' class='btn bor_blue_tp2 full_tp1'>어린이보호구역 관리대장</a>
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
<!-- //어린이보호구역 상세조회 : end-->

<!-- 어린이보호구역 수정 : start-->
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
		<input type="hidden" id="area" name="area" />
		<input type="hidden" id="fileYn" name="fileYn" />
		<input type="hidden" id="preFileYn" name="preFileYn" value="" />
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
					<input type="text" class="w100p readonly" id="mgrnu" name="mgrnu" value="<c:out value='${result.mgrnu}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">공사관리번호</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="ctkMgrnu" name="ctkMgrnu" value="<c:out value='${result.ctkMgrnu}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">지정일자</div>
				<div class="cont">
					<input type="text" id="" name="regYmd" value="<c:out value='${result.regYmd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
				</div>
			</li>
			<li>
				<div class="tit">개선완료일</div>
				<div class="cont">
					<input type="text" id="esbYmd" name="esbYmd" value="<c:out value='${result.esbYmd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
				</div>
			</li>
			<li>
				<div class="tit">구분</div>
				<div class="cont">
					<select id="schKnd" name="schKnd">
					</select>
				</div>
			</li>
			<li>
				<div class="tit">학교명</div>
				<div class="cont">
					<input type="text" id="nam" name="nam" value="<c:out value='${result.nam}'/>" maxlength="50"/>
				</div>
			</li>

			<li>
				<div class="tit">전화번호</div>
				<div class="cont">
					<input type="text" id="schTel" name="schTel" value="<c:out value='${result.schTel}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">세부주소</div>
				<div class="cont">
					<input type="text" id="schAddr" name="schAddr" value="<c:out value='${result.schAddr}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">길이</div>
				<div class="cont">
					<input type="text" id="len" name="len" value="<c:out value='${result.len}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">교통사고[사망]</div>
				<div class="cont">
					<input type="text" id="die" name="die" value="<c:out value='${result.die}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">교통사고[부상]</div>
				<div class="cont">
					<input type="text" id="inj" name="inj" value="<c:out value='${result.inj}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">도로폭</div>
				<div class="cont">
					<input type="text" id="rodWid" name="rodWid" value="<c:out value='${result.rodWid}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">속도제한[㎞]</div>
				<div class="cont">
					<input type="text" id="spdLtd" name="spdLtd" value="<c:out value='${result.spdLtd}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">특이사항</div>
				<div class="cont">
					<input type="text" id="memo" name="memo" value="<c:out value='${result.memo}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">설치일자</div>
				<div class="cont">
					<input type="text" id="esbYmd" name="esbYmd" value="<c:out value='${result.esbYmd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
				</div>
			</li>
			<li>
				<div class="tit">비고</div>
				<div class="cont">
					<input type="text" id="rmk" name="rmk" value="<c:out value='${result.rmk}'/>" maxlength="50"/>
				</div>
			</li>
			<li>
				<div class="tit">주소</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="addr" name="addr" value="<c:out value='${result.addr}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">교차로명</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="cssNam" name="cssNam" value="<c:out value='${result.cssNam}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">경찰서명</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="peNam" name="peNam" value="<c:out value='${result.peNam}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">어린이보호구역명</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="schNam" name="schNam" value="<c:out value='${result.schNam}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">면적</div>
				<div class="cont">
					<input type="text" class="w100p readonly" value="<c:out value='${result.area}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">X</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="xce" name="xce" value="<c:out value='${result.xce}'/>" maxlength="30" readOnly/>
				</div>
			</li>
			<li>
				<div class="tit">Y</div>
				<div class="cont">
					<input type="text" class="w100p readonly" id="yce" name="yce" value="<c:out value='${result.yce}'/>" maxlength="30" readOnly/>
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
<!-- //어린이보호구역 수정 : end-->

<script>
//# sourceURL= /fclts/a005_a/selectInfoPop.jsp.jsp
$(function(){

	var schKnd = new AjaxComboBox("#schKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SCH_KND&refCd=C101_A'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.schKnd}"});

	$("#chilPop").click(function() {
		$("#selectFrm").popupWindow("${context}/report/cpzMng/selectInfoPopM.do?mgrnu=${result.mgrnu}",{id:"selectInfoPopM",scrollbars:0,width:"800",height:"500"});
	});

	$("#delete").click(function(){
		var $frm = $("#selectFrm");


		var ft  = new ol.Feature({
			geometry: mapMaker.control.edit.select.getFeatures().getArray()[0].clone().getGeometry().transform( mapMaker.crsCode, mapMaker.config.facilityCrsCode),
		});
		var pt = ol.extent.getCenter(ft.getGeometry().getExtent());

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
	var area = ft.getGeometry().getArea();
	var pt = ol.extent.getCenter(ft.getGeometry().getExtent());

	$frm.find('#pointXce').val(pt[0]);
	$frm.find('#pointYce').val(pt[1]);
	$("#minX").val(extent[0]);
	$("#minY").val(extent[1]);
	$("#maxX").val(extent[2]);
	$("#maxY").val(extent[3]);
	$("#area").val(area);

	var geom = mapMaker.getWkt(ft);

	$frm.find("#geom").val(geom);

	if(confirm("수정하시겠습니까?")) {
		$("input[type='file']").each(function(){
			if($(this).val() != ""){
				$("#fileYn").val("Y");
				return false;
			}
		});  // 파일 첨부된 내용이 있으면 fileYn에 구분값 Y를 준다.

		if($("#delFile").val() == "Y"){
			$("#delFileId").val(delFileId.join(","));
		} // 파일이 첨부되있었는데 삭제를 했을 경우 delFileId 에 삭제된 파일 키값을 넣어준다.

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
