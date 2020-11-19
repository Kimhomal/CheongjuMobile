(function(window, $) {
	"use strict";

	var MapAction = function(mapMaker) {
		this._mapMaker = mapMaker;
		this.properties.oldProj = this._mapMaker.config.facilityCrsCode
	};

	MapAction.prototype = {

		properties : {
			oldProj : "",
			zoomLevel :"7"
		},

		measure : {
			properties : {
				continueMsg : '마우스 좌측 버튼 더블 클릭시 마침',
				overlayClassName : 'measure-tooltip tooltip-static',
				overlayViewClassName : 'measure-tooltip',
				wgs84Sphere : new ol.Sphere(6378137),
				isMeasure : false
			},
			feature : null,
			features : null,
		},

		roadView : {
			properties : {
				continueMsg : '마우스 우측 버튼 클릭시 마침',
				overlayClassName : 'measure-tooltip tooltip-static',
				overlayViewClassName : 'measure-tooltip',
				wgs84Sphere : new ol.Sphere(6378137),
				isRoadView : false
			},
			feature : null,
			features : null,
		},

		search : {
			properties : {
				continueMsg : '마우스 우측 버튼 클릭시 마침',
				overlayClassName : 'measure-tooltip tooltip-static',
				overlayViewClassName : 'measure-tooltip',
				wgs84Sphere : new ol.Sphere(6378137),
				isSearch : false
			},
			feature : null,
			features : null,
		},

		drawProperties : {
			$btns : null,
			// overlayClassName : 'draw-tooltip',
			overlayClassName : 'ol-popup',
			overlayText : "",
			$input : null,
			isOverlay : false,
		},

		layer : {
			properties : {
				isSearchMode : false, // 반경검색 여부
				isFeatureCreate : false,		// 피처 생성 여부
				isSearchRoadMode : false,		// 피처 생성 여부
//				isEditSelect : false,			// 편집 중 다른 시설물 선택 여부 -> 지우시길
				isFeatureSelect : false,		// 피처 선택 여부
//				isFeatureEdit : false,		// 피처 수정 여부
				isRoadViewMode : false,	// 로드뷰 모드 여부
				isTargetSelect : false,		// 편집 중 다른 시설물 선택 여부
				isSearchArea : false,			// 범위 검색 여부
				isDraw : false,			// 다각형 Draw 여부
				isDrawSelect : false,			// 그리기 선택 여부
//				isSelectExclude : false,		// 시설물 선택 시 선택 제외 여부
				isSync : false,		// 시설물 선택 시 선택 제외 여부
				isFacilSelect : false, //교차로 선택
				isFacilConnect : false, //스냅
			},
			select : { // 키값 가져오기 위한 변수 선언 - 한봄 -
				$input : null,
				id : null,
				target : null
			}
		},

		initAttr : null,

		mapRotationAngle : 0,

		initCenter : function(center) {
			this._mapMaker.map.getView().setCenter(center);
			this._mapMaker.map.getView().setZoom(this._mapMaker.zoomLvl);
		},

		setExtent : function() {
			var extent = ol.proj.transformExtent(this._mapMaker.baseMap.extent, this._mapMaker.baseMap.crsCode, this._mapMaker.crsCode);
			this._mapMaker.map.getView().fit(extent);
		},

		zoomExtent : function(extent) {
			if(extent == undefined){
				var baseMap  = null;
				if($("#baseMap").length > 0){
					baseMap = BaseMapConfig[$("#baseMap").val()];
				}else{
					baseMap = BaseMapConfig[this._mapMaker.initBaseMap];
				}
				this._mapMaker.map.getView().setCenter(baseMap.center);
				this._mapMaker.map.getView().setZoom(0);
			}else{
				this._mapMaker.map.getView().fit(extent);
			}

		},

		setCenter : function (coord, zoom, oldProj, newProj) {
			var center = null;
			if (oldProj != undefined){
				center = ol.proj.transform(coord, oldProj, newProj)
			}else{
				center = ol.proj.transform(coord, this._mapMaker.config.facilityCrsCode, this._mapMaker.crsCode)
			}
			this._mapMaker.map.getView().setCenter(center);
			this._mapMaker.map.getView().setZoom(zoom);
		},

		setPrintLayerVisible : function () {
			var _self = this;
			var layers = _self._mapMaker.map.getLayers().getArray();
			var layer = null;
//			console.log( JSON.stringify(layers) );
			$.each(layers, function(idx, lyr){
				lyr.setVisible(opener.mapMaker.map.getLayers().getArray()[idx].getVisible());
			});
		},

		//디비 좌표값을로 센터에 마커 찍기
		setMakerCenter : function (coord, zoom,params) {
			this._mapMaker.layer.marker.getSource().clear();
			if(params != undefined) this.properties = $.extend({}, this.properties, params);
			var center = ol.proj.transform(coord,  this.properties.oldProj, this._mapMaker.crsCode);
			this._mapMaker.map.getView().setCenter(center);
			this._mapMaker.map.getView().setZoom(zoom);

			var pointFeat = new ol.Feature({//임시포인트 레이어 임
				geometry : new ol.geom.Point(center),
			});
			this._mapMaker.layer.marker.getSource().addFeature(pointFeat);

		},

		offBaseMapLayers : function() {
			$.each(this._mapMaker.baseMapLayers, function(){
				this.setVisible(false);
			});
		},

		onOffLayersByKind : function(visible, kind) {
			var _self = this;
			$.each(this._mapMaker.mapLayerMng.layers, function(parKey, parVal){
				$.each(parVal, function(layerName, layer){
					if (kind == layer.get("kind") || kind == layer.get("category")) {
						layer.setVisible(visible);
                        if(layer.get("id")=="A057_L"){
                            var temp = _self._mapMaker.mapLayerMng.getLayerById("sign");
                            temp.setVisible(visible);
                        }
					}
				});
			});
		},

		setVisibilityById : function(id){
			var lyr = this._mapMaker.mapLayerMng.getLayerById(id);
			if(lyr != null){
				if(id=="A057_L"){
					var temp = this._mapMaker.mapLayerMng.getLayerById("sign");
					temp.setVisible(!temp.getVisible());
				}
				lyr.setVisible(!lyr.getVisible());
				return true;
			}
		},

		setVisibilityByLayer : function(id,visible){
			var lyr = this._mapMaker.mapLayerMng.getLayerById(id);
			if(lyr != null){
				lyr.setVisible(visible);
				return true;
			}
		},

		transformFeature : function(oldCrsCode, newCrsCode) {
			this._mapMaker.map.getLayers().forEach(function(layer) {
				if(layer instanceof ol.layer.Vector) {
					layer.getSource().getFeatures().forEach(function(feature){
						feature.getGeometry().transform(oldCrsCode, newCrsCode);
					});
				}
			});
		},

		startMeasure : function(type) {
			this.offControl();
			var map = this._mapMaker.map;

			if (map == null || map == "" || map == "undefined") return;

			this._initMeasure();
			this.measure.properties.isMeasure = true;

			this.measure.feature = null;
			this.measure.features = this._mapMaker.layer.measure.getSource().getFeatures();

			var control = this._mapMaker.control.measure[type];
			control.setActive(true);

			this._mapMaker.createMeasureTooltip();
			this._mapMaker.createHelpTooltip();

			control.on("drawstart", function(evt){
				this.measure.feature = evt.feature;
			}, this);

			control.on('drawend', function(evt) {
				this.measure.features.push(evt.feature);

				this._mapMaker.overlay.overlayElement.className = this.measure.properties.overlayClassName;
				this._mapMaker.overlay.overlayLayer.setOffset([0, -7]);

				this.measure.feature = null;
				this._mapMaker.overlay.overlayLayer = null;

				this._mapMaker.createMeasureTooltip();
			}, this);

			this._mapMaker.mapEvtMng.setMeasureEvt();
		},
		mapControlHandle : function(bDisabled) {
			var mapControl = this._mapMaker.config.mapControl;

			$(mapControl.elem).each(function(){
				var _this = $(this);

				$.each(mapControl.arrHandle, function(){
					var bCompare = mapControl.flag == "class" ? _this.hasClass(this) : _this.attr(mapControl.flag) == this;

					if (bCompare) {
						if (bDisabled) {
							_this.off("click");
							_this.on("click", function(){
								alert("시설물 편집 중에는 실행할 수 없습니다.");
							});
						} else {
							$(mapControl.elem).off("click");
							$.setMapControlEvt();
						}
					}
				});
			});
		},

		lyrClear : function () {

			$.each(this._mapMaker.mapLayerMng.layers.wfs, function(){
				if(this instanceof ol.layer.Vector) {
					this.getSource().clear();
				}
			});

			$.each(this._mapMaker.layer, function(){
				if(this instanceof ol.layer.Vector && this.get("title") != "sign") {
					this.getSource().clear();
				}
			});

		},

		offControl : function () {
			$.each(this._mapMaker.control, function(kind, control){
				$.each(control, function(name){
					this.setActive(false);
//					if (kind == "draw" || kind == "measure") {
//						this.setActive(false);
//					} else {
//						return true;
//					}
				});
			});

			//control off 할 때, 각 여부 체크 false로 변경;;;
//			var layerProperties = this.layer.properties;
//			$.each(layerProperties, function(key, property){
//				if (typeof property === "boolean") layerProperties[key] = false;
//			});

			this.layer.properties.isSearchArea = false;
			this.layer.properties.isFeatureCreate = false;
			this.layer.properties.isSearchMode = false;
			this.layer.properties.isRoadViewMode = false;

			var $measureOverlay = $("div." + this.measure.properties.overlayViewClassName);

			var overlayClassName = this.measure.properties.overlayClassName;

			if($measureOverlay.length != 0) {
				$measureOverlay.each(function(){
					if (!$(this).hasClass(overlayClassName)) {
						$(this).remove();
					}
				});
			}

			this.layer.properties.isDraw = false;
//			this.layer.properties.isSelectExclude = false;
			this.drawProperties.isOverlay = false;
		},
		setControl : function (keyValue) {
			$.each(this._mapMaker.control.draw, function(kind, control){
				if(kind == keyValue){
					this.setActive(true);
				}else{
					this.setActive(false);
				}
			});
//			$.each(this._mapMaker.control.edit, function(kind, control){
//				if(kind == keyValue){
//					this.setActive(true);
//				}else{
//					this.setActive(false);
//				}
//			});
		},

		_initMeasure : function() {
			this._mapMaker.map.un("pointermove"); // 확인 필요
			$.each(this._mapMaker.control.measure, function(){
				this.setActive(false);
			});
			this.measure.properties.isMeasure = false;
		},

		offMeasure : function() {
			var mapMaker = this._mapMaker;

			mapMaker.layer.measure.getSource().clear();
			$("div." + this.measure.properties.overlayViewClassName).remove();
			$.each(this._mapMaker.overlay, function(key, val){
				if (this instanceof ol.Overlay && key.indexOf("Tooltip") < 0) {
					mapMaker.map.removeOverlay(this);
				}
			});
			this._initMeasure();
		},

		calculateDistance : function(line) {
			var length;
			var sourceProj = this._mapMaker.map.getView().getProjection();

			if (sourceProj != "EPSG:4326") {
				var coordinates = line.getCoordinates();
				length = 0;
				for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
					var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
					var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
					length += this.measure.properties.wgs84Sphere.haversineDistance(c1, c2);
				}
			} else {
				length = Math.round(line.getLength() * 100) / 100;
			}
			var output = (Math.round(length * 100) / 100) + ' ' + 'm';
			// if (length > 100) {
			// 	output = (Math.round(length / 1000 * 100) / 100) + ' ' + 'km';
			// } else {
			// 	output = (Math.round(length * 100) / 100) + ' ' + 'm';
			// }
			return output;
		},

		calculateArea : function(polygon) {
			var area;
			var sourceProj = this._mapMaker.map.getView().getProjection();

			if (sourceProj != "EPSG:4326") {
				var geom = /** @type {ol.geom.Polygon} */
				(polygon.clone().transform(sourceProj, 'EPSG:4326'));
				var coordinates = geom.getLinearRing(0).getCoordinates();
				area = Math.abs(this.measure.properties.wgs84Sphere.geodesicArea(coordinates));
			} else {
				area = polygon.getArea();
			}
			var output = (Math.round(area * 100) / 100) + ' ' + 'm<sup>2</sup>';
			// if (area > 10000) {
			// 	output = (Math.round(area / 1000000 * 100) / 100) + ' '
			// 			+ 'km<sup>2</sup>';
			// } else {
			// 	output = (Math.round(area * 100) / 100) + ' ' + 'm<sup>2</sup>';
			// }
			return output;
		},

		allClear : function () {
			this.offMeasure();
			this.lyrClear();
			this.offControl();
			this.mapControlHandle(false);
		},

		startRoadView : function (params) {
			var map = this._mapMaker.map;

			if (map == null || map == "" || map == "undefined") return;

			this.layer.properties.isRoadViewMode = true;

		},


		onFeatureMove : function(x, y){
			this._mapMaker.mapAction.lyrClear();
			var pos = [y, x];
			var coord = ol.proj.transform(pos, "EPSG:4326", this._mapMaker.crsCode);
			var pointFt = new ol.Feature({
				geometry : new ol.geom.Point([coord[0], coord[1]])
			});
			pointFt.setStyle(new ol.style.Style({
				image: new ol.style.Icon({
					size: [128,128],
					anchor: [0.5, 1.0],
					opacity: 1,
					scale: 0.3,
					src: G.baseUrl + "images/main/pin_v1.png",
				})
			}));
			this._mapMaker.map.getView().setCenter(coord);
			this._mapMaker.layer.roadView.getSource().addFeature(pointFt);
		},

		isRotateFacility : function (facilityId) {
			var flag = false;

			if (facilityId.indexOf("GT_A054_P") > -1 || facilityId.indexOf("GT_A055_P") > -1 || facilityId.indexOf("GT_A110_P") > -1
				|| facilityId.indexOf("GT_A068_P") > -1) {
				flag = true;
			}

			return flag;
		},



		drawShape : function (params) {
			this.offControl();

			this.layer.properties.isDrawSelect = true;	// 도형 그릴 시 지도 선택 Block

			/*if (params.$btn.hasClass("black"))*/ this.layer.properties.isDraw = true;
			// else this.layer.properties.isDraw = false;

			// this.drawProperties.$btns = params.$btn.parent().children();

			if (this.layer.properties.isDraw) {
				// params.$btn.removeClass("black");
				// params.$btn.addClass("blue");
				this._mapMaker.control.draw[params.type].setActive(true);
			} else {
				// params.$btn.removeClass("blue");
				// params.$btn.addClass("black");
			}
		},
		startDrawFacility : function (params) {
			var _self = this;

			_self.offControl();
			this.mapControlHandle(true);
			//도로점용 임시추가
			if(params.reqData.facilityId == "A057_L"){
				mapMaker.mapAction.layer.properties.isFacilConnect=true;
			}
			if(params.reqData.facilityId == "GT_A004_A"){
				params.controlName = "polyline";
			}else{
				params.controlName = _self.getControlName(params.reqData.facilityId.split("_")[2]);
			}
//			switch(facilityId){
//				case "a110_p" :
//						git.layer.snap.snapOnById("a057_l");
//						git.layer.snap.snapOnById("a062_p");
//					break;
//				case "a057_l" : git.layer.snap.snapOnById("a062_p");
//					break;
//			}

//			this._mapMaker.layer.roadView.getSource().addFeature(pointFt);
			_self._mapMaker.control.draw[params.controlName].setActive(true);
			_self.layer.properties.isFeatureCreate = true;

			params.source = _self._mapMaker.layer[params.controlName].getSource();

			this._mapMaker.mapEvtMng.drawParams = params;
		},

		getControlName : function (type) {
			var controlName = "";

			if (type == "A") {
				controlName = "polygon";
			} else if (type == "L") {
				controlName = "polyline";
			} else if (type == "P") {
				controlName = "point";
			}

			return controlName;
		},

		stopDrawFacility : function (params) {
			if (params.feature != null && params.method != "insert") {
				this.editDrawFacility(params.feature, false);
			}

			this.mapControlHandle(false);

			if (this._mapMaker.drag.control) this._mapMaker.map.removeInteraction(this._mapMaker.drag.control);

//			this.viewEditToolbar(false);

			if (!params.isSelectMode) this._mapMaker.control.edit.select.getFeatures().clear();

			this._mapMaker.control.edit.modify.setActive(false);
			if (params.source != null) params.source.clear();

			if(this.layer.properties.isTargetSelect){
				var lyr = this._mapMaker.mapLayerMng.getLayerById(this.layer.select.target);
				lyr.setStyle(MapFacilityMng.style.wfs);
			}

			this._mapMaker.drag.copyFlag = false;
			this.layer.properties.isFeatureCreate = false;
			this.layer.properties.isFeatureSelect = false;
			this.layer.properties.isTargetSelect = false;  // 키값 가져오기 위한 변수 선언 - 한봄 -
			this._mapMaker.stopAngle();
		},

		editDrawFacility : function (feature, isEdit) {
			var wkt = "";
			var format = new ol.format.WKT();

			//차선일 때
			if (feature.getId().indexOf("A058_L") > -1) {
				var tlLcCd = feature.get("A058_KND_CDE");
				var tlMcCd = feature.get("A058_KND2_CDE");
				if((tlLcCd == "001" && (tlMcCd == "003" || tlMcCd == "004" || tlMcCd == "005")) || (tlLcCd == "002" && (tlMcCd == "001" || tlMcCd == "002")) ||
					(tlLcCd == "005" && (tlMcCd == "003" || tlMcCd == "004")) || (tlLcCd == "008" && (tlMcCd == "002" || tlMcCd == "003")) ||
					(tlLcCd == "017" && (tlMcCd == "003" || tlMcCd == "004"))){
					//수정중일 때
					if(isEdit) wkt = feature.get("oriWkt");
					else wkt = feature.get("viewWkt");

					var readFeature = format.readFeature(wkt, {
						// dataProjection: this._mapMaker.config.facilityCrsCode,
						// featureProjection: this._mapMaker.baseMap.crsCode,
					});

					feature.setGeometry(readFeature.getGeometry());
				} else {
					var geom = feature.get("geom") ;

					if ( geom != null ){
						feature.getGeometry().setCoordinates(geom);
					}
				}
			}
//			else if (feature.getId().indexOf("A004_A") > -1) { //횡단보도일 때
//				//수정중일 때
//				if(isEdit) wkt = feature.get("oriWkt");
//				else wkt = feature.get("viewWkt");
//
//				var readFeature = format.readFeature(wkt, {
//					// dataProjection: this._mapMaker.config.facilityCrsCode,
//					// featureProjection: this._mapMaker.baseMap.crsCode,
//				});
//
//				feature.getGeometry().setPolygons(readFeature.getGeometry().getPolygons());
//			}
			else {
				var geom = feature.get("geom") ;

				if ( geom != null ){
					feature.getGeometry().setCoordinates(geom);
				}

				if (this.isRotateFacility(feature.getId())) feature.set("DRN", feature.get("oriAngle"));
			}

			this._setOriginalAttr(feature);	// 피처 속성 원복
		},

		_setOriginalAttr : function (feature) {
			if (this.initAttr == null) return;

			$.each(this.initAttr, function(key, val) {
				feature.set(key, val);
			});
		},

		getFeatureProp : function (params) {
			var _self = this;
			// if(params.reqData.facilityId != "a001_a"){
//				this._mapMaker.control.edit.modify.setActive(false);
				this.layer.properties.isFeatureSelect = true; // 시설물 선택 시 true

				if (params.reqData.method != "insert") {
					this.initAttr = null;
					this.editDrawFacility(params.feature, true);	// 차선 및 횡단보도 원복
					params.feature.set("geom", params.feature.getGeometry().getCoordinates()); //기존 좌표 저장
					this._mapMaker.stopAngle();
				} else {
					this.viewEditToolbar(true, params.reqData.method);

					if (this.isRotateFacility(params.reqData.facilityId)) {
						this._mapMaker.stopAngle();
                        params.feature.set("id",params.reqData.facilityId);
						this._mapMaker.createAngle(params.feature);
					}
				}
				params = $.extend({}, {
					url : G.baseUrl + "fclts/modalInfoPop.do",
				}, params);

				if (this._mapMaker.config.drawingYn == "Y") {
					// 도면관리에 사용될 값 설정
					params.reqData = $.extend({}, {
						drawingYn : this._mapMaker.config.drawingYn,
						cstStatCd : this._mapMaker.config.cstStatCd,
						gbn : this._mapMaker.config.gbn,
					}, params.reqData);
				}
				//기존 코드
//				params.reqData.cstNo = this._mapMaker.config.cstNo;	// 공사번호 값 셋팅
//				params.reqData.cstCpyNam = this._mapMaker.config.cstCpyNam;	// 공사번호 값 셋팅

				params.reqData.cstNo = this._mapMaker.cstNo;	// 공사번호 값 셋팅
				params.reqData.cstStep = this._mapMaker.cstStep;	// 공사단계 값 셋팅
				params.reqData.cstStepDetail = this._mapMaker.cstStepDetail;	// 공사단계 값 셋팅
				// + 도로명 값 셋팅도 해줘야함. roadNam가지고 오는 부분 찾아야함
//				params.reqData.roadNam = ?

				facilityFt = params.source.getFeatures()[0] == null ? params.feature : params.source.getFeatures()[0];
//
				if(params.reqData.facilityId.indexOf("TMP")>-1){
					params.reqData.facilityId = params.reqData.facilityId.replace("_TMP","")
				}

				this._mapMaker.config.facilityModal.open(params.url, {
					width : params.reqData.facilityId == "a001_a" ? "150px" : "350px",
					reqData : params.reqData,
					closeFunc : function() {
						_self.stopDrawFacility({
							source : params.source,
							feature : params.feature,
							method : params.reqData.method
						});
					},
					setModeChange : function(mode) {

						if (mode == "viewMode") {
							_self.viewEditToolbar(false);

							params.feature.getGeometry().setCoordinates(params.feature.get("geom"));
							_self._mapMaker.control.edit.modify.setActive(false);
//							_self.layer.properties.isFeatureEdit = false;

							_self._mapMaker.stopAngle();

							if (_self.isRotateFacility(params.reqData.facilityId)) {
								params.feature.set("DRN", params.feature.get("oriAngle"));
							}

							_self._setOriginalAttr(params.feature);

							if (_self._mapMaker.drag.control) {
								_self._mapMaker.map.removeInteraction(_self._mapMaker.drag.control);
								_self._mapMaker.control.edit.select.getFeatures().clear();
								_self._mapMaker.control.edit.select.getFeatures().push(_self._mapMaker.drag.selectedFeature);
							}
						} else if(mode == "updateMode"){
							_self.viewEditToolbar(true);

							_self._mapMaker.control.edit.modify.setActive(true);
//							_self.layer.properties.isFeatureEdit = true;

							if (_self.isRotateFacility(params.reqData.facilityId)) {
                                params.feature.set("id",params.reqData.facilityId);
								_self._mapMaker.createAngle(params.feature);
								_self._mapMaker.mapEvtMng.setFeatureChangeRotateEvt(params.feature);
							}

							if(bxslider.length != 0) bxslider.redrawSlider();

						}
					}
				});
//			 }else{
//			 	_self._mapMaker.control.edit.select.getFeatures().clear();
//			 }
		},

		viewEditToolbar : function(bVisible, method) {
			if (method == "insert") {
				$(this._mapMaker.config.editToolbar).children().eq(0).hide();
			} else {
				$(this._mapMaker.config.editToolbar).children().eq(0).show();
			}

			if (bVisible) {
				$(this._mapMaker.config.editToolbar).show();
			} else {
				$(this._mapMaker.config.editToolbar).hide();
			}
		},
		setFtFromAttr : function (feature, $parent) {
			var data = feature.getProperties();

			var drawingYn = this._mapMaker.config.drawingYn;

			var selectJson = {};

			$.each(data, function(key, val){
				if(drawingYn != "Y" && key == "cst_no") return true;
				var camelCaseKey = $.toCamelCase(key);
				var element = "#" + camelCaseKey;

				var $input = $parent.find(element);
				if ($input.length > 0) {
					if ($input.prop("type") == "select-one") {
						selectJson[camelCaseKey] = val;
					} else {
						$input.val(val);
					}
				}
			});

			return selectJson;
		},

		getSortLineLength : function (arr) {
			for(var i = 0;i<arr.length;i++)
				for(var j = i+1;j<arr.length;j++)
					if(arr[i].getLength()>arr[j].getLength()){
						var tmp = arr[j];
						arr[j] = arr[i];
						arr[i] = tmp;
					}
			return arr;
		},

		// 각도 계산
		getAngle : function (endPos, startPos) {
			if(endPos==undefined||startPos==undefined)
				return 0;
			var dx = endPos[0] - startPos[0];
			var dy = endPos[1] - startPos[1];

			var angle = (Math.atan2(dx, dy) * 180) / Math.PI;

			if(angle < 0)
				angle += 360;

			return angle;
		},
		// 포인트 이동
		moveToDistance : function (coord, dis, angle) {
			var x1 = dis * Math.sin(angle * (Math.PI / 180)).toFixed(6);
			var y1 = dis * Math.cos(angle * (Math.PI / 180)).toFixed(6);

			return [coord[0] + x1, coord[1] + y1];
		},

		// 두점사이의 거리
		getDistance : function(coord1, coord2) {
			return new ol.geom.LineString([coord1, coord2]).getLength();
		},

		createHatchBrush : function(dAngle, rgbString, width) {
			var linewidth = width;

			if(width == undefined){
				linewidth = 1;
			}

			var cnv = document.createElement('canvas');
			var ctx = cnv.getContext('2d');
			cnv.width = 10;
			cnv.height = 10;
			ctx.lineWidth = 1;
			ctx.beginPath();

			//임의값
			if (dAngle <= 45) {
				ctx.moveTo(0, 0);
				ctx.lineTo(10, 10);
			} else if (dAngle > 45 && dAngle <= 90) {
				ctx.moveTo(5, 0);
				ctx.lineTo(5, 10);
			} else if (dAngle > 90 && dAngle <= 135) {
				ctx.moveTo(0, 10);
				ctx.lineTo(10, 0);
			} else {
				ctx.moveTo(10, 5);
				ctx.lineTo(0, 5);
			}

			ctx.strokeStyle = 'rgb(' + rgbString + ')';
			ctx.stroke();
			return ctx.createPattern(cnv, 'repeat');
		},

		setImgFromAttr : function (feature, params) {

			this.initAttr = {};

			var initAttr = this.initAttr;

			$.each(params, function(key, val){
				var underScoreKey = $.toUnderscore(key).toUpperCase();

				initAttr[underScoreKey] = val;

				$("#" + key).on("change", function(){
					feature.set(underScoreKey, $(this).val());
				});
			});
		},

        /**
         * 피처로 지도 이동
         * @param params(json)
         * key (lyrId : 레이어 명, filter : where 조건문)
         * By. 한봄
         */
        getCoordFromFeature: function (params) {
            var fts = this.getFeatureByKey(params.lyrId, params.filter);
            var coordinates = ol.proj.transform(fts.features[0].getGeometry().getCoordinates(), this.properties.oldProj, this._mapMaker.crsCode);
            return coordinates
        },

        /**
         * 피처로 지도 이동
         * @param params(json)
         * key (lyrId : 레이어 명, filter : where 조건문)
         * By. 한봄
         */
        setMoveFromFeature: function (params) {
            var fts = this.getFeatureByKey(params.lyrId, params.filter);
           var reqParms = $.extend({}, this.properties, params);
            this.setMakerCenter(ol.extent.getCenter(fts.features[0].getGeometry().getExtent()),reqParms.zoomLevel,{oldProj : "EPSG:5181"});
//            var extent = ol.proj.transformExtent(fts.features[0].getGeometry().getExtent(), this.properties.oldProj, this._mapMaker.crsCode);
//            var view = this._mapMaker.map.getView();
//            view.fit(extent);
        },

        /**
         * wfs 레이어 Filter
         * @method
         * @param sql
         * @param keyword 키워드
         */
        getFeatureByKey: function (lyrId, filter) {
            var geojsonFormat = new ol.format.GeoJSON();
            var features = null;
            $.ajax({
                url: this._mapMaker.config.noProxyWfs,
                dataType: 'json',
                async: false,
                data: {
                    srs: this._mapMaker.config.facilityCrsCode,
                    request: 'GetFeature',
                    version: '1.0.0',
                    typename: this._mapMaker.config.workspace + ':' + lyrId,
                    outputFormat: 'application/json',
                    CQL_FILTER: filter
                },
                success: function (response) {
                    features = {
                        count: response.totalFeatures,
                        features: geojsonFormat.readFeatures(response)
                    };
                },
                error: function (a, b, c) {
                    console.log("error");
                }
            });
            return features;
        },

        getFeatureByCrood : function(lyrId, crood){
            var geojsonFormat = new ol.format.GeoJSON();
            var features = null;
            $.ajax({
                url:  this._mapMaker.config.noProxyWfs,
                dataType: 'json',
                async: false,
                data:{
                    srs: this._mapMaker.config.facilityCrsCode,
                    request: 'GetFeature',
                    version: '1.0.0',
                    typename: this._mapMaker.config.workspace + ':' + lyrId,
                    outputFormat: 'application/json',
                    CQL_FILTER: "DWITHIN(XGEO, POINT("+crood[0]+" "+crood[1]+"),0.5,meters)"
                },
                success:function(response){
                    features = {
                        count : response.totalFeatures,
                        features :geojsonFormat.readFeatures(response)
                    };
                },
                error : function(a, b, c){
                    console.log("error");
                }
            });
            return features;
        },

        /**
         * OpenLayers 피처 To 퍼버
         * @param feature Openlayers 피처 객체
         * @param distance  버퍼 거리
         * @param endCapType (CAP_FLAT, CAP_ROUND, CAP_SQUARE)
         *  jsts.min.js  Lib 사용
         */
        setFeatureToBuffer: function (feature, distance,endCapType) {
            var geojsonFormat = new ol.format.GeoJSON();
            var geojsonFeature = geojsonFormat.writeFeatureObject(feature);
            var ns_buffer = jsts.operation.buffer.BufferParameters;
            var options = options || {};
            var quadrantSegments = options.quadrantSegments || ns_buffer.DEFAULT_QUADRANT_SEGMENTS;
            var endCapStyle = ns_buffer.CAP_ROUND;
            if (['CAP_FLAT','CAP_ROUND','CAP_SQUARE'].indexOf(endCapType) != -1) {
                endCapStyle = ns_buffer[endCapType];
            }
            var reader = new jsts.io.GeoJSONReader();
            var inputGeometry = reader.read(geojsonFeature.geometry);
            inputGeometry = inputGeometry.buffer(distance, quadrantSegments, endCapStyle);
            var geometrybufferGeoJSON = new jsts.io.GeoJSONWriter().write(inputGeometry);
            var formatter = new ol.format.GeoJSON();
            return  formatter.readFeatures({
                "type": "Feature",
                "properties": geojsonFeature.properties,
                "geometry": geometrybufferGeoJSON
            })[0];
        }
	}

	window.MapAction = MapAction;

})(window, jQuery);