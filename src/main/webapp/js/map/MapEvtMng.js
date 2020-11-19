/**
 * 지도 이벤트 관리
 */
(function(window, $){
	var MapEvtMng = function(mapMaker){
		this._mapMaker = mapMaker;
		this._init();
	};

	MapEvtMng.prototype = {
		properties : {
			boundaryFlag 		: false,
			facilitySelectFlag 	: false,
			xElem					: "xce", // 지도 좌표 넣어주는 input 기본 id * By. 한봄
			yElem					: "yce", // 지도 좌표 넣어주는 input 기본 id * By. 한봄
			addrElem				: "loca", // 지도 주소 넣어주는 input 기본 id  * By. 한봄
			parentFlag			: false,
			message			: "",
		},

		drawParams : null,	// 시설물 편집 시 설정 값 (json -> ol.source.vector 의 addfeature 에서 사용)

		_selectFeatureProperties : {},	 // 선택 피처 임시 저장 json 변수

		mapEvt : {
			click : null,
			rightclick : null,
			pointermove : null,
			moveend : null,
			dblclick : null,
		},

		_init : function () {
			var _self = this;
			var a062Geom;
			var secondGeom;
			var clickFunc = function(evt){


				 if (_self._mapMaker.mapAction.layer.properties.isSearchMode) {
//				 	_self._mapMaker.mapAction.stopDrawFacility(_self._selectFeatureProperties);
					 var coord = evt.originalEvent.coordinate;

					 coord = ol.proj.transform(coord, mapMaker.crsCode, "EPSG:5181");

					 $("#modal-controller-2").commonModal(G.baseUrl +"map/roadPop.do", {
						reqData : {  title : "도로폭 조회", xce :coord[0], yce : coord[1]}
				 	});

//					 var features = _self._mapMaker.mapAction.getFeatureByCrood("GT_LP_PA_CBND", coord);
////					 var pnu = features.features[0].get("PNU");
////					 alert(pnu)
//					 $.ajax({
//                     type : 'post',
//                     url : G.baseUrl + "map/getPnu.json", // 만들어줘요
//                     dataType : "json",
//                     data : "coord="+coord,
//                     cache : false,
//                     success : function(response){
//
////                         $(mapMaker.config.locationElem).html("청주시 " + response.road);
//                         alert(road);
//                     }
//                 });
					 _self._mapMaker.mapAction.layer.properties.isSearchMode = false;
					 return;
				 }

				if (!_self._mapMaker.mapAction.layer.properties.isFacilSelec&&!_self._mapMaker.mapAction.layer.properties.isFacilConnect&&(_self._mapMaker.mapAction.measure.properties.isMeasure || _self._mapMaker.mapAction.layer.properties.isFeatureCreate
					|| _self._mapMaker.mapAction.layer.properties.isFeatureSelect /*|| _self._mapMaker.mapAction.layer.properties.isEditSelect
					|| _self._mapMaker.control.edit.modify.getActive()*/ || _self._mapMaker.mapAction.layer.properties.isSearchMode || _self._mapMaker.mapAction.layer.properties.isSearchRoadMode
//					|| _self._mapMaker.mapAction.layer.properties.isSearchArea || _self._mapMaker.layer.searchBox.getSource().getFeatures().length > 0
//					|| _self._mapMaker.layer.searchPolygon.getSource().getFeatures().length > 0 || _self._mapMaker.layer.searchCircle.getSource().getFeatures().length > 0
					|| _self._mapMaker.mapAction.layer.properties.isDraw || _self._mapMaker.mapAction.layer.properties.isDrawSelect)) return;

				evt = evt.originalEvent;

				if (_self._mapMaker.mapAction.drawProperties.isOverlay) {
					_self._mapMaker.createOverlayPopup(evt.coordinate);
					_self._mapMaker.mapAction.drawProperties.isOverlay = false;
					return;
				}
				if ( _self._mapMaker.mapAction.layer.properties.isRoadViewMode) {
					var pos = evt.coordinate;
					var pointFt = new ol.Feature({
						geometry : new ol.geom.Point([pos[0], pos[1]])
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
					_self._mapMaker.map.getView().setCenter(pos);
					_self._mapMaker.map.getView().setZoom(9);
					_self._mapMaker.layer.roadView.getSource().addFeature(pointFt);
					var coord = ol.proj.transform(pos, _self._mapMaker.crsCode, "EPSG:4326");
					var y = coord[0];
					var x = coord[1];
				$.popupWindow(G.baseUrl + 'map/roadView.do?x='+x+'&y='+y, {id:"roadView",scrollbars : 0,width : "1000",height : "600",});


					 _self._mapMaker.mapAction.layer.properties.isRoadViewMode = false;
					return;
				}

				var $layerTooltip;

				// 하드코딩
				if (_self._mapMaker.overlay.overlayTooltipElement != null && !_self.properties.boundaryFlag) {
					$layerTooltip = $(_self._mapMaker.overlay.overlayTooltipElement).find(_self._mapMaker.overlay.overlayTooltipAppendElem);
					$layerTooltip.empty();
					$(_self._mapMaker.overlay.overlayTooltipElement).hide();
				}

				var map = _self._mapMaker.map;

				if(_self._mapMaker.mapAction.layer.properties.isFacilSelect){ // 교차로 연결

					var lyr = _self._mapMaker.mapLayerMng.getLayerById("A008_P");
					map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
						if(lyr == layer){
							$('#cssNum').val(feature.get("CSS_NUM"));
							$('#cssNam').val(feature.get("CSS_NAM"));
							_self._mapMaker.mapAction.layer.properties.isFacilSelect = false;
						}
					},
					{hitTolerance: 10});
					return;
				}

				if(_self._mapMaker.mapAction.layer.properties.isTargetSelect){ // 시설물 키값 가져오기 - 한봄 -
					var properties = _self._mapMaker.mapAction.layer.select;
					var lyr = _self._mapMaker.mapLayerMng.getLayerById(properties.lyrId);
					map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
						if(lyr == layer){
							$("#"+properties.inputId).val(feature.get(properties.inputAttr));
							if(properties.subId != ""){
								$("#"+properties.subId).val(feature.get(properties.subAttr));
							}
                            mapMaker.mapAction.layer.properties.isTargetSelect = false;
						}
					});
//					mapMaker.mapAction.layer.properties.isFeatureCreate = true;
//					mapMaker.mapAction.layer.properties.isFeatureSelect = true;
//					lyr.setStyle(MapFacilityMng.style.wfs);
					return;
				}

				var isSelectChange = true;

				var lyrId = "";
				var nLyrCnt = 0; // 지도 클릭 시 레이어 갯수

				// WFS 레이어일때
				map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
					if (layer.get("title") == null || layer.get("title").indexOf("draw") > -1 || layer.get("id") == "sign" || layer.get("id") == undefined||layer.get("title").indexOf("roadView") > -1 ) return;
					lyrId = layer.get("id");


						var features = _self._mapMaker.control.edit.select.getFeatures();

						if (isSelectChange && features.getLength() > 0) features.clear();
						isSelectChange = false;

						feature.set("layerTitle", layer.get("title"));
						features.push(feature);

						var title = layer.get("title");
						title += "(" + (feature.get("MGRNU") == undefined ? feature.get("GEOMSEQ") : feature.get("MGRNU")) + ")";

						var controlName = _self._mapMaker.mapAction.getControlName(lyrId.split("_")[2]);

						if(controlName == "") return;

						var $row = $(_self._mapMaker.overlay.overlayTooltipAppend);
						$row.append(title);

						$row.on({
							click : function () {

								$(_self._mapMaker.overlay.overlayTooltipElement).hide();

								if (_self.properties.facilitySelectFlag) {
									_self._mapMaker.mapAction.layer.properties.isFeatureSelect = false;
								}else {
									var source = _self._mapMaker.layer[controlName].getSource();

									_self._selectFeatureProperties = {
										source : source,
										feature : feature,
										method : "select",
										isSelectMode : true,
									};
									_self._mapMaker.mapAction.getFeatureProp({
										reqData :{
											facilityId : layer.get("id"),
											title : title,
											method : "select",
											mgrnu : (feature.get("MGRNU") == undefined ? feature.get("GEOMSEQ") : feature.get("MGRNU")),
											mapMethod : $("#method").val(),//지도 메소드로 조회 및 수정 버튼 권한
										},
										source : source,
										feature : feature,
									});
								}
							},
							mouseenter : function () {
								features.clear();
								features.push(feature);
							},
							mouseleave : function () {
								// features.clear();
							}
						});

						$layerTooltip.append($row);
						nLyrCnt++;

				}, {
					hitTolerance : 10
				});

				if (!isSelectChange) {
					if(_self._mapMaker.control.edit.select.getFeatures().getLength() > 1){
						$(_self._mapMaker.overlay.overlayTooltipElement).show();
						_self._mapMaker.overlay.overlayTooltipLayer.setPosition(evt.coordinate);
					} else {
						$layerTooltip.children().trigger("click");
					}
				} else {
					$(_self._mapMaker.overlay.overlayTooltipElement).hide();
					_self._mapMaker.control.edit.select.getFeatures().clear();
				}
			};

			_self.mapEvt.click = clickFunc;

			_self.mapEvt.pointermove = function(evt) {
                _self._mapMaker.map.getViewport().style.cursor = _self._mapMaker.map.hasFeatureAtPixel(evt.originalEvent.pixel) ? "pointer" : "";
				var cord = evt.originalEvent.coordinate;
				var coord = ol.proj.transform(cord, _self._mapMaker.crsCode, "EPSG:5186");
				$("#coor").html(coord[0].toFixed(4) + ", " + coord[1].toFixed(4));
			};

			_self.mapEvt.moveend = function(evt) {
                 var coord = ol.proj.transform(mapMaker.map.getView().getCenter(), mapMaker.crsCode, "EPSG:5181");
                 $.ajax({
                     type : 'post',
                     url : G.baseUrl + "map/getCenterAddr.json", // 만들어줘요
                     dataType : "json",
                     data : "coord="+coord,
                     cache : false,
                     success : function(response){
                         // $(mapMaker.config.locationElem).html("청주시 " + response.addr);
                         $("#addr").html(response.addr);
                     }
                 });
			};

			_self._mapMaker.map.getViewport().addEventListener('contextmenu', function (evt) {
				evt.preventDefault();
			});

		},

		onMapEvt : function () {
			var $map = $(this._mapMaker.map);

			$.each(this.mapEvt, function(key, val){
//				if (key == "rightclick") return true;
				$map.on(key, val);
			});
		},

		offMapEvt : function () {
			var $map = $(this._mapMaker.map);

			$.each(this.mapEvt, function(key){
//				if (key == "rightclick") return true;
				$map.off(key);
			});
		},

		onMapEvtByName : function (evtName, params) {
			this.properties = $.extend({}, this.properties, params);

			var $map = $(this._mapMaker.map);
			$map.off(evtName);
			$map.on(evtName, this.mapEvt[evtName]);
		},

		/**
		 * 지도 클릭 이벤트 추가 (좌표 세팅)
		 * @param params(json)
		 * key (xElem : X좌표가 입력될 Input ID, yElem : Y좌표가 입력될 Input ID)
		 * 파라미터 값 않넣을 경우 기본 값 (xElem : xce, yElem : yce)
		 * By. 한봄
		 */
		setCoordByMapEvt : function (params) {
			var _self = this;
			_self.properties = $.extend({}, _self.properties, params); // 좌표 넣는 Element가 변경 될 경우 변수로 받아서 기본값 변경 그대로 쓸꺼면 안넘겨줘도됨 By. 한봄
			_self._mapMaker.map.on("click",function(evt){
				_self._mapMaker.layer.marker.getSource().clear();
				var coord = ol.proj.transform(evt.coordinate,  _self._mapMaker.crsCode, _self._mapMaker.config.facilityCrsCode);//좌표변환 (5179->5186)
				$("#"+_self.properties.xElem).val(coord[0]);
				$("#"+_self.properties.yElem).val(coord[1]);

				var pointFeat = new ol.Feature({//임시포인트 레이어 임
					geometry : new ol.geom.Point(evt.coordinate),
				});
				_self._mapMaker.layer.marker.getSource().addFeature(pointFeat);
			});
		},

		/**
		 * 지도 클릭 이벤트 추가 ( 주소 및 좌표 세팅)
		 * @param params(json)
		 * key (xElem : X좌표가 입력될 Input ID, yElem : Y좌표가 입력될 Input ID, addrElem : 주소가 입력될 Input ID,parentFlag : 부모창에 있는 요소를 활용할것인지)
		 * 파라미터 값 않넣을 경우 기본 값 (xElem : xce, yElem : yce, addrElem : loca, parentFlag : false,message: 알럿창 띄울 메세지)
		 * By. 한봄
		 */
		setAddrByMapEvt : function (params) {
			var _self = this;
			_self.properties = $.extend({}, _self.properties, params);  // 좌표 넣는 Element가 변경 될 경우 변수로 받아서 기본값 변경 그대로 쓸꺼면 안넘겨줘도됨 By. 한봄
			_self._mapMaker.map.on("click",function(evt){
				_self._mapMaker.layer.marker.getSource().clear();
				var coord = ol.proj.transform(evt.coordinate,  _self._mapMaker.crsCode, _self._mapMaker.config.facilityCrsCode);//좌표변환 (5179->5186)
				var addr = "";
				$.ajax({
					type : 'post',
					url : G.baseUrl + "map/getCenterAddr.json",
					dataType : "json",
					data : "coord="+coord,
					cache : false,
					async : false,
					success : function(response){
						addr = response.addr;
					}
				});

				if(!_self.properties.parentFlag){
					$("#"+_self.properties.xElem).val(coord[0]);
					$("#"+_self.properties.yElem).val(coord[1]);
					$("#"+_self.properties.addrElem).val(addr);
				}else{
					opener.$("#"+_self.properties.xElem).val(coord[0]);
					opener.$("#"+_self.properties.yElem).val(coord[1]);
					opener.$("#"+_self.properties.addrElem).val(addr);
				}

				var pointFeat = new ol.Feature({//임시포인트 레이어 임
					geometry : new ol.geom.Point(evt.coordinate),
				});
				_self._mapMaker.layer.marker.getSource().addFeature(pointFeat);
				if(_self.properties.message != ""){
					alert(_self.properties.message);
				}

			});
		},

		/**
		 * MapEvtMng 기본 Properties 값 변경
		 * @param params(json)
		 * 변경할 Properties 값 json 형태로 넘김
		 * By. 한봄
		 */
		setProperties : function(params) {
			var _self = this;
			_self.properties = $.extend({}, _self.properties, params);
		},

		setAddFeatEvt : function(source, flag) {
			var _self = this;
			var addFeatEvt = function(evt){
				if (flag == "S") {
//					_self._mapMaker.mapAction.offControl();
//					_self._mapMaker.mapAction.layer.properties.isSearchArea = false;
//					_self._mapMaker.mapAction.getSearchAreaProp({
//						feature : evt.feature,
//					});
				} else if (flag == "D") {
					var feature = evt.feature;
					if (feature.getGeometry() instanceof ol.geom.Point) {
						_self._mapMaker.createOverlayPopup({feature : feature});
					}
				} else {

					var feature = evt.feature;

					var facilityId = _self.drawParams.reqData.facilityId;

					feature.set("id", facilityId);

					_self._mapMaker.mapAction.offControl();
					_self._mapMaker.control.edit.select.getFeatures().push(feature);

					_self.drawParams.feature = feature;
					_self._mapMaker.mapAction.getFeatureProp(_self.drawParams); //show modal

					_self._mapMaker.control.edit.modify.setActive(true);

					if (_self.drawParams.controlName == "point") {
						if (_self._mapMaker.mapAction.isRotateFacility(facilityId)) {
							_self.setFeatureChangeRotateEvt(feature);
						}
					}
				}
			};
			source.on("addfeature", addFeatEvt);
		},

		setFeatureChangeRotateEvt : function (feature) {
			var _self = this;

			feature.on("change", function(evt){
				_self._mapMaker.stopAngle();
				_self._mapMaker.createAngle(evt.target);
			});
		},

		setMeasureEvt : function () {
			//측정 시 마우스 무브 이벤트
			var map = this._mapMaker.map;
			var mapAction = this._mapMaker.mapAction;
			var overlay = this._mapMaker.overlay;

			map.on("pointermove", function(evt){
				if (evt.dragging) {
					return;
				}

				var helpMsg = '측정을 시작하십시오.(마우스 우측 버튼 클릭시 측정 종료)';
				var tooltipCoord = evt.coordinate;

				if (mapAction.measure.feature) {
					var output;
					var geom = mapAction.measure.feature.getGeometry();
					if (geom instanceof ol.geom.Polygon) {
						output = mapAction.calculateArea(geom);
						tooltipCoord = geom.getInteriorPoint().getCoordinates();
					} else if (geom instanceof ol.geom.LineString) {
						output = mapAction.calculateDistance(geom);
						tooltipCoord = geom.getLastCoordinate();
					}
					helpMsg = mapAction.measure.properties.continueMsg;

					overlay.overlayElement.innerHTML = output;
					overlay.overlayLayer.setPosition(tooltipCoord);
				}

				overlay.overlayViewElement.innerHTML = helpMsg;
				overlay.overlayViewLayer.setPosition(evt.coordinate);
			});

			//마우스 오른쪽 버튼 클릭 이벤트(초기화)
			map.getViewport().addEventListener("contextmenu", function(evt){
				evt.preventDefault();
				mapAction.offMeasure();
			});
		},
	};

	window.MapEvtMng = MapEvtMng;
})(window, jQuery);