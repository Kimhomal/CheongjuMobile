<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="form" id="form" method="post">
<input type="hidden" name="currentPage" value="1">
<input type="hidden" name="countPerPage" value="1">
<input type="hidden" name="resultType" value="json">
<input type="hidden" name="confmKey" id="confmKey" style="width:250px;display:none" value="devU01TX0FVVEgyMDIwMDkxNjExMjk1MzExMDE5NTA="/>
<input type="hidden" id="drawingYn" name="drawingYn" value="Y">
<input type="hidden" id="method" name="method" value="${params.method}">
</form>

<div class="sub-top-wrap">
	<div class="sub-top">
		<div class="left">
			<button class="ui tertiary icon button" onclick="location.href='<c:url value='/'/>'">
				<i class="angle left icon"></i>
				<i class="home icon"></i>
			</button>
		</div>
		<div class="center">지도서비스</div>
		<!-- <div class="right">
			<button class="ui tertiary fluid icon button map-btn">
				<i class="map icon"></i>
			</button>
			<div class="ui popup hidden">
				<div class="header">지도 설정</div>
				<div class="ui form">
					<div class="field">
						<label>베이스맵</label>
						<div class="ui selection dropdown basemap-selector">
							<input name="basemap" type="hidden" value="naver">
							<i class="dropdown icon"></i>
							<div class="text">Default Value</div>
							<div class="menu">
								<div class="item" data-value="naver">네이버</div>
								<div class="item" data-value="kakao">카카오</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> -->
	</div>
</div>

<div id="map" class="map">
	<!-- 레이어 선택 팝업 -->
	<div class="layer_wrap layer_mapInfo" style="display: none;">
		<!-- layer_inner -->
		<div class="layer_inner">
			<div class="layer_header">
				<h3>중첩레이어</h3>
			</div>
			<!-- layer_body -->
			<div class="layer_body">
				<div class="layer_box">
					<ul class="mapInfo_list">
					</ul>
				</div>
			</div>
			<!-- //layer_body -->
		</div>
		<!-- //layer_inner -->
	</div>
	<!--//레이어 선택 팝업 -->
	<div id="mapPopup" class="ol-popup">
		<div id="mapPopup-content"></div>
	</div>
</div>

<script>
var proj = new Proj();
var mapMaker = null;
var facilityFt;


$(function() {
 	mapMaker = new MapMaker("map", {
		mapControl : {
			elem : "ul.mapCtr_wrap>li i",
			flag : "class",
			arrHandle : ["btn_distanceMeasure", "btn_areaMeasure", "btn_areaSearch", "btn_reset", "btn_fullView", "btn_printPop", "btn_roadView"]
		},
		facilityModal : new InfoModal(),
		sggCd : "${params.sggCode}",
	}); // 기본셋팅 및 맵 객체, 배경지도 추가

	mapMaker.mapEvtMng.onMapEvt(); // 지도 이벤트 셋팅

	mapMaker.mapLayerMng.addFacilityLayers(); // 시설물 레이어 추가

	mapMaker.mapLayerMng.setTempLayer(); // 임시레이어 추가 및 셋팅층 마커레이어를 만듬
// 	mapMaker.mapLayerMng.setFacilTempLayer(); // 임시레이어 추가 및 셋팅층

	if('${params.sggCode}' !=""){
		if('${params.sggCode}'.length >5){
			mapMaker.mapAction.setMoveFromFeature({
	            lyrId: "GT_LP_AA_EMD",
	            filter: "EMD_CD = '" + '${params.sggCode}' +"'",
	            zoomLevel :"4"
	        });

		}else{
			mapMaker.mapAction.setMoveFromFeature({
	            lyrId: "GT_LP_AA_SGG",
	            filter: "SIG_CD = '" + '${params.sggCode}' +"'",
	            zoomLevel :"2"
	        });
		}

	}

	mapMaker.setControl();
	mapMaker.setOverlayLayerTooltip({
		overlayTooltipElement : document.getElementsByClassName("layer_mapInfo")[0],
		overlayTooltipAppend : "<li>",
		overlayTooltipAppendElem : "ul",
	}); // 시설물 선택 시 레이어 툴팁 사용

	$.setBaseMapEvt({
		baseMapElem : "#baseMap",
		baseMapLayerElem : "#baseMapLayer",
	});

    $(".totalSearch").load(G.baseUrl + "search/searchComb.do", "method=Comb");

	$("#choose").hide();
	
	window.managePanels = new ManagePanels({
		map: mapMaker.map,
		position: '${params.position}'
	});
	
	/* $('.map-btn').popup({
		on: 'click',
		position: 'bottom right'
	});
	
	$('.basemap-selector').dropdown(); */
});


</script>