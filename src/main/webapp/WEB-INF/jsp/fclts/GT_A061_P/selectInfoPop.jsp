<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content"  style="overflow-y:auto;">
		<div class="btn_wrap tp2">
<%-- 		<c:if test="${params.mapMethod != null && params.mapMethod != ''&& params.mapMethod != 'ConstruCheck' && params.mapMethod != 'mapOld'}"> --%>
			<a href="#this" class="btn half_tp2 blue" id="viewMode">보기</a>
			<a href="#this" class="btn half_tp2 bor_blue_tp1" id="updateMode">수정</a>
<%-- 		</c:if> --%>
		</div>
<!-- 제어기 상세조회 : start-->
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
				<c:out value='${result.ctkMgrnu}'/>
			</div>
		</li>
		<li>
			<div class="tit">설치고가</div>
			<div class="cont">
				<c:out value='${result.eveNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">설치일자</div>
			<div class="cont">
				<c:out value='${result.esbYmd}'/>
			</div>
		</li>
		<li>
			<div class="tit">설치회사</div>
			<div class="cont">
				<c:out value='${result.esbCpy}'/>
			</div>
		</li>
		<li>
			<div class="tit">기종</div>
			<div class="cont">
				<c:out value='${result.mkMdl}'/>
			</div>
		</li>
		<li>
			<div class="tit">제어방식</div>
			<div class="cont">
				<c:out value='${result.ctlMetNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">배선방식</div>
			<div class="cont">
				<c:out value='${result.lineMetNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">신호기제식</div>
			<div class="cont">
				<c:out value='${result.conForNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">제조일자</div>
			<div class="cont">
				<c:out value='${result.mkYmd}'/>
			</div>
		</li>
		<li>
			<div class="tit">제조회사</div>
			<div class="cont">
				<c:out value='${result.mkCpy}'/>
			</div>
		</li>
		<li>
			<div class="tit">계량기관리번호</div>
			<div class="cont">
				<c:out value='${result.mea}'/>
			</div>
		</li>
		<li>
			<div class="tit">계량기제조일자</div>
			<div class="cont">
				<c:out value='${result.meaMkymd}'/>
			</div>
		</li>
		<li>
			<div class="tit">도로구분</div>
			<div class="cont">
				<c:out value='${result.rodKndNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">노후제어기교체일</div>
			<div class="cont">
				<c:out value='${result.chanYmd}'/>
			</div>
		</li>
		<li>
			<div class="tit">설치위치</div>
			<div class="cont">
				<c:out value='${result.layLoca}'/>
			</div>
		</li>
		<li>
			<div class="tit">연등지구분</div>
			<div class="cont">
				<c:out value='${result.ctlTypeNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">제어모드</div>
			<div class="cont">
				<c:out value='${result.ctlModNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">전적색상태</div>
			<div class="cont">
				<c:out value='${result.redCNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">현시정보</div>
			<div class="cont">
				<c:out value='${result.swTypeNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">점멸상태</div>
			<div class="cont">
				<c:out value='${result.fkrTypeNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">회전규제</div>
			<div class="cont">
				<c:out value='${result.turnCmmtNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">기타사항</div>
			<div class="cont">
				<c:out value='${result.etc}'/>
			</div>
		</li>
		<li>
			<div class="tit">비고란</div>
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
					<a id="ctrPop" name="ctrPop"href='#this' class='btn bor_blue_tp2 full_tp1'>교통신호기 관리대장</a>
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
<!-- //제어기 상세조회 : end-->

<!-- 제어기 수정 : start-->
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
			<div class="tit">설치고가</div>
			<div class="cont">
				<select id="eve" name="eve">
				</select>
			</div>
		</li>
		<li>
			<div class="tit">설치일자</div>
			<div class="cont">
				<input type="text" id="esbYmd" name="esbYmd" value="<c:out value='${result.esbYmd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">설치회사</div>
			<div class="cont">
				<input type="text" id="esbCpy" name="esbCpy" value="<c:out value='${result.esbCpy}'/>" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">기종</div>
			<div class="cont">
				<input type="text" id="mkMdl" name="mkMdl" value="<c:out value='${result.mkMdl}'/>" maxlength="50"/>
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
				<input type="text" id="mkCpy" name="mkCpy" value="<c:out value='${result.mkCpy}'/>" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">제조일자</div>
			<div class="cont">
				<input type="text" id="mkYmd" name="mkYmd" value="<c:out value='${result.mkYmd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">계량기관리번호</div>
			<div class="cont">
				<input type="text" id="mea" name="mea" value="<c:out value='${result.mea}'/>" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">계량기제조일자</div>
			<div class="cont">
				<input type="text" id="meaMkymd" name="meaMkymd" value="<c:out value='${result.meaMkymd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
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
				<input type="text" id="chanYmd" name="chanYmd" value="<c:out value='${result.chanYmd}'/>" class="ico_calendar datapicker_open" maxlength="10"/>
			</div>
		</li>
		<li>
			<div class="tit">설치위치</div>
			<div class="cont">
				<input type="text" id="layLoca" name="layLoca" value="<c:out value='${result.layLoca}'/>" maxlength="50"/>
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
				<input type="text" id="etc" name="etc" value="<c:out value='${result.etc}'/>" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">비고란</div>
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
<!-- //제어기 수정 : end-->

<script>
//# sourceURL= /fclts/a005_a/selectInfoPop.jsp.jsp
$(function(){

	var eve = new AjaxComboBox("#eve", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=EVE&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.eve}"});

	var ctlMet = new AjaxComboBox("#ctlMet", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CTL_MET&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.ctlMet}"});

	var conFor = new AjaxComboBox("#conFor", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CON_FOR&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.conFor}"});

	var rodKnd = new AjaxComboBox("#rodKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=ROD_KND&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.rodKnd}"});

	var lineMet = new AjaxComboBox("#lineMet", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=LINE_MET&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.lineMet}"});

	var ctlType = new AjaxComboBox("#ctlType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CTL_TYPE&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.ctlType}"});

	var ctlMod = new AjaxComboBox("#ctlMod", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CTL_MOD&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.ctlMod}"});

	var redC = new AjaxComboBox("#redC", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=RED_C&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.redC}"});

	var swType = new AjaxComboBox("#swType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SW_TYPE&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.swType}"});

	var fkrType = new AjaxComboBox("#fkrType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=FKR_TYPE&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.fkrType}"});

	var turnCmmt = new AjaxComboBox("#turnCmmt", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=TURN_CMMT&refCd=A061_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.turnCmmt}"});

	$("#ctrPop").click(function() {
		$("#selectFrm").popupWindow("${context}/report/signalMng/selectInfoPopM.do?mgrnu=${result.mgrnu}",{id:"selectInfoPopM",scrollbars:0,width:"1000",height:"800"});
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
