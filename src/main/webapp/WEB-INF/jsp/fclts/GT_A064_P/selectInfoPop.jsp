<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<div id ="traffic" class="lp_content"  style="overflow-y:auto;">
		<div class="btn_wrap tp2">
<%-- 		<c:if test="${params.mapMethod != null && params.mapMethod != ''&& params.mapMethod != 'ConstruCheck' && params.mapMethod != 'mapOld'}"> --%>
			<a href="#this" class="btn half_tp2 blue" id="viewMode">보기</a>
			<a href="#this" class="btn half_tp2 bor_blue_tp1" id="updateMode">수정</a>
<%-- 		</c:if> --%>
		</div>
<!-- 안전표지 상세조회 : start-->
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
			<div class="tit">표지코드</div>
			<div class="cont">
				<c:out value='${result.mrkCde}'/>
			</div>
		</li>
		<li>
			<div class="tit">상세내용</div>
			<div class="cont">
				<c:out value='${result.ntc}'/>
			</div>
		</li>
		<li>
			<div class="tit">종류</div>
			<div class="cont">
				<c:out value='${result.mrkKndNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">규격</div>
			<div class="cont">
				<c:out value='${result.stdNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">반사재</div>
			<div class="cont">
				<c:out value='${result.morNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">재질</div>
			<div class="cont">
				<c:out value='${result.mopNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">지주관리번호</div>
			<div class="cont">
				<c:out value='${result.sprtMgrnu}'/>
			</div>
		</li>
		<li>
			<div class="tit">지주재질</div>
			<div class="cont">
				<c:out value='${result.sprtMopNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">지주종류</div>
			<div class="cont">
				<c:out value='${result.sprtKndNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">도로종류</div>
			<div class="cont">
				<c:out value='${result.rodKndNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">도로형태</div>
			<div class="cont">
				<c:out value='${result.rodFrmNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">부착대관리번호</div>
			<div class="cont">
				<c:out value='${result.asnMgrnu}'/>
			</div>
		</li>
		<li>
			<div class="tit">대장타입</div>
			<div class="cont">
				<c:out value='${result.lgrTypeNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">대장구분</div>
			<div class="cont">
				<c:out value='${result.lgrGbnNam}'/>
			</div>
		</li>
		<li>
			<div class="tit">보조표지기록</div>
			<div class="cont">
				<c:out value='${result.sptMrkEt}'/>
			</div>
		</li>
		<li>
			<div class="tit">표지관리번호</div>
			<div class="cont">
				<c:out value='${result.mrkCdeWe}'/>
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
				<a id="safePop" name="safePop"href='#this' class='btn bor_blue_tp2 full_tp1'>안전표지 관리대장</a>
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
<!-- //안전표지 상세조회 : end-->

<!-- 안전표지 수정 : start-->
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
			<div class="tit">표지코드</div>
			<div class="cont">
				<input type="text" id="mrkCde" name="mrkCde" value="<c:out value='${result.mrkCde}'/>" class="w80p readonly" readonly="readonly"/>
				<a href="#this" id="mrkSelect" class="btn sml_tp2 bor_gray_tp3" >선택</a>
			</div>
		</li>
		<li>
			<div class="tit">상세내용</div>
			<div class="cont">
				<input type="text" id="ntc" name="ntc" value="<c:out value='${result.ntc}'/>" maxlength="50"/>
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
				<input type="text" id="sprtMgrnu" name="sprtMgrnu" class="w80p readonly" value="<c:out value='${result.sprtMgrnu}'/>" maxlength="30"/>
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
				<input type="text" id="asnMgrnu" name="asnMgrnu" class="w80p readonly" value="<c:out value='${result.asnMgrnu}'/>" maxlength="30"/>
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
				<input type="text" id="sptMrkEt" name="sptMrkEt" value="<c:out value='${result.sptMrkEt}'/>" maxlength="50"/>
			</div>
		</li>
		<li>
			<div class="tit">표지관리번호</div>
			<div class="cont">
				<input type="text" id="mrkCdeWe" name="mrkCdeWe" value="<c:out value='${result.mrkCdeWe}'/>" maxlength="50"/>
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
<!-- //안전표지 수정 : end-->

<script>
//# sourceURL= /fclts/a005_a/selectInfoPop.jsp.jsp
$(function(){

	var mrkKnd = new AjaxComboBox("#mrkKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MRK_KND&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.mrkKnd}"});

	var std = new AjaxComboBox("#std", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=STD&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.std}"});

	var mor = new AjaxComboBox("#mor", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MOR&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.mor}"});

	var mop = new AjaxComboBox("#mop", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=MOP&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.mop}"});

	var sptMrk = new AjaxComboBox("#sptMrk", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SPT_MRK&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.sptMrk}"});

	var sprtMop = new AjaxComboBox("#sprtMop", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SPRT_MOP&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.sprtMop}"});

	var sprtKnd = new AjaxComboBox("#sprtKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=SPRT_KND&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.sprtKnd}"});

	var rodKnd = new AjaxComboBox("#rodKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=ROD_KND&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.rodKnd}"});

	var rodFrm = new AjaxComboBox("#rodFrm", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=ROD_FRM&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.rodFrm}"});

	var eve = new AjaxComboBox("#eve", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=EVE&refCd=ALL'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.eve}"});

	var lgrType = new AjaxComboBox("#lgrType", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=LGR_TYPE&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.lgrType}"});

	var lgrGbn = new AjaxComboBox("#lgrGbn", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=LGR_GBN&refCd=A064_P'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.lgrGbn}"});


	$("#mrkSelect").on("click",function(evt){
		$("#updateFrm").popupWindow("${context}/fclts/A064_P/igtSignPopW.do",{id:"igtSignPop",scrollbars:0,width:"545",height:"530"});
	});

	$("#safePop").click(function() {
		$("#selectFrm").popupWindow("${context}/report/signMng/selectInfoPopM.do?mgrnu=${result.mgrnu}",{id:"selectInfoPopM",scrollbars:0,width:"1000",height:"800"});
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
