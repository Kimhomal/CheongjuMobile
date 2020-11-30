/**
 * 레이어 추가 및 맵 객체를 추가하는 곳
 */
(function(window, $) {
	"use strict";

	var MapMaker = function(mapId, options){
		this.mapId = mapId;
		this._options = options;
		this.init();
	};

	MapMaker.prototype = {
		// 임시레이어
		layer : {
			angle : null,			// 각도
			marker : null,
			polyline : null,
			polygon : null,
			searchBox : null,
			searchPolygon : null,
			searchCircle : null,
			draw : null,			// 그리기 임시 레이어
			search : null,			// 검색(주소 및 도로명)
			measure : null,			// 측정
			roadView : null,
			point : null,
			sign : null,
			dxfLayer : null,
			smrInfo : null
		},
		overlay : {
			overlayElement : null,				// 측정용
			overlayLayer : null,				// 측정용
			overlayViewElement : null,			// 측정용
			overlayViewLayer : null,			// 측정용
			overlayFacilityElement : null,		// 시설물 거리 면적
			overlayTooltipElement : null,		// 시설물 선택 레이어 툴팁
			overlayTooltipLayer : null,			// 시설물 선택 레이어 툴팁
			overlayTooltipAppend : null,		// 시설물 선택 레이어 툴팁
			overlayTooltipAppendElem : null,	// 시설물 선택 레이어 툴팁
			overlayDrawElement : null,			// 그리기용
			overlayDrawLayer : null,			// 그리기용
		},
		control : {
			draw : {
				point : null,
				polyline : null,
				polygon : null,
				searchBox : null,
				searchPolygon : null,
				searchCircle : null,
				drawCircle : null,
				drawPolygon : null,
				drawPoint : null,
				drawSquare : null,
				drawLineString : null,
			},
			edit : {
				select : null,
				modify : null
			},
			measure : {
				polyline : null,
				polygon : null,
			},
			angle : {
				select : null,
				modify : null,
			},
			roadView : {
				point : null,
			},
			search : {
				polygon : null,
			},
		},

		drag : {
			control : null,			// 드래그 컨트롤
			selectedFeature : null, // 선택한 피처
			copyFlag : false,		// 복사 기능 여부
		},

		rotateFeature : null,		// 회전할 피처
		eveCde : "0",		// 지상지하구분
		symbol : null,

		map : null,					// OL Map 객체

		mapAction : null,			// 지도 액션 관리
		mapLayerMng : null,		// 레이어 관리
		mapEvtMng : null,			// 지도 이벤트 관리

		baseMapList : [],			// 배경지도 리스트(ex : 바로이맵, 다음, 네이버 등등)
		baseMapLayers : null,	// 배경지도 서브 리스트(ex:기본지도, 위성지도 등등)

		baseMap : null,				// 배경지도 객체
		mapName : null,			// 배경지도 한글 이름
		crsCode : null,				// 배경지도 좌표체계 코드
		center : null,					// 중심 좌표
		zoomLvl : null,				// 줌 레벨
		sggCd : null,				// 시군구코드
		config : null,					// MapMaker 설정 객체

		moveEndEvt : null,
		_defaults : {
			proxyWms : G.baseUrl + "map/proxy/wms.do",
			proxyWfs : G.baseUrl + "map/proxy/wfs.do",
			noProxyWms : "http://1.234.21.200:8888/geoserver/tgis_loc/wms",
			noProxyWfs : "http://1.234.21.200:8888/geoserver/tgis_loc/wfs",
//			proxyBackground : G.baseUrl + "proxy/proxy.jsp?url=",
			proxyBackground : "",
			workspace : "tgis",
			facilityCrsCode : "EPSG:5181",
			initBaseMap : "Daum",
			arrOlDefaultCtrl : [ new ol.control.ScaleLine(), new ol.control.ZoomSlider() ],
			defaultsCtrl : { attribution: false },
			interactions : {shiftDragZoom : false,altShiftDragRotate : false,doubleClickZoom : false,pinchRotate : false,interactionSelectPointerMove : true},
		},
		init : function(){

			var _self = this;
//			_self.config =  _self._options;
			_self.config = $.extend({}, _self._defaults, _self._options);

			$.each(BaseMapConfig, function(idx, lyr){
				_self.baseMapList.push(lyr);
			});

			_self.baseMap = BaseMapConfig[_self.config.initBaseMap];

			_self.mapName = _self.baseMap.korName;
			_self.crsCode = _self.baseMap.crsCode;

			_self.center = _self.baseMap.center;
			_self.zoomLvl = 7;

			_self.map = new ol.Map({
				renderer : 'canvas',
				controls : ol.control.defaults(_self.config.defaultsCtrl).extend(_self.config.arrOlDefaultCtrl),
				target : _self.mapId,
//				loadTilesWhileAnimating : true,
				interactions : ol.interaction.defaults(_self.config.interactions),
				view : _self.getViewByCrsCode(_self.crsCode)
			});

			_self.mapAction = new MapAction(_self); //지도 액션 처리
			_self.mapLayerMng = new MapLayerMng(_self); //지도 레이어 관리
			_self.mapEvtMng = new MapEvtMng(_self); //지도 이벤트 관리

			_self.addBaseMapLayers(); //배경지도 레이어 추가(BaseMapConfig)

			_self.mapAction.initCenter(_self.center);



		},

		getWktFromGeom : function(geom) {
			var format = new ol.format.WKT();
			var wkt = format.writeGeometry(geom);
			return wkt;
		},

		getViewByCrsCode : function(crsCode) {
			var resolutions;

			if (crsCode == "EPSG:4326") {
				resolutions = [ 0.02197265625, 0.010986328125, 0.0054931640625, 0.00274658203125, 0.001373291015625, 0.0006866455078125, 0.00034332275390625, 0.000171661376953125, 0.0000858306884765625, 0.00004291534423828125, 0.000021457672119140625, 0.000010728836059570312, 0.000005364418029785156, 0.000002682209014892578 ]
			} else {
				resolutions = this.baseMap.resolutions;
			}

			var projection = new ol.proj.Projection({
				code : crsCode,
				units : 'm',
				axisOrientation : 'enu'
			});
			var baseMap = null;
			if(crsCode == "EPSG:5181"){
				baseMap = BaseMapConfig["Daum"];
			}else if(crsCode == "EPSG:3857"){
				baseMap = BaseMapConfig["VWorld"];
			}else if(crsCode == "EPSG:5179"){
				baseMap = BaseMapConfig["Naver"];
			}else if(crsCode == "EPSG:5179"){
				baseMap = BaseMapConfig["Baroemap"];
			}
			var center = ol.proj.transform(this.baseMap.center, this.baseMap.crsCode, projection);

			var mapView = new ol.View({
				projection : projection,
				center : center,
				resolutions : resolutions,
				extent : baseMap.extent,
			})

			return mapView;
		},

		addBaseMapLayers : function(params) {
			this.mapLayerMng.addBaseMapLayers();
		},

		changeBaseMapWithName : function(baseMapName){
			var baseMapCrsCode;
			var oldProj;
			var newProj;
			var coord;
			var zoom;
			var baseMap = BaseMapConfig[baseMapName];
			baseMapCrsCode = baseMap.crsCode;
			oldProj = this.baseMap.crsCode;
			coord = this.map.getView().getCenter();
			zoom = this.map.getView().getZoom();

			this.changeCrsCode(baseMapCrsCode);
			this.changeBaseMap(baseMap);
			this.mapAction.setCenter(coord, zoom, oldProj, baseMapCrsCode);

		},

		changeCrsCode : function(crsCode){
			var prevCrsCode = this.crsCode;
			this.crsCode = crsCode;
			this.map.setView(this.getViewByCrsCode(crsCode));
//			this.mapAction.setExtent();

			this.mapAction.zoomExtent();

			if(prevCrsCode != this.crsCode){
				this.mapAction.transformFeature(prevCrsCode, this.crsCode);
			}
		},

		changeBaseMap : function(baseMap) {
			this.baseMap = baseMap;
			// basemap 교체
			this.addBaseMapLayers();
		},

		createMeasureTooltip : function() {
			this.overlay.overlayElement = document.createElement('div');
			this.overlay.overlayElement.className = this.mapAction.measure.properties.overlayClassName;

			this.overlay.overlayLayer = new ol.Overlay({
				element : this.overlay.overlayElement,
				offset : [ 0, -15 ],
				positioning : 'bottom-center'
			});
			this.map.addOverlay(this.overlay.overlayLayer);
		},

		createHelpTooltip : function(){
			if (this.overlay.overlayViewLayer) {
				this.map.removeOverlay(this.overlay.overlayViewLayer);
			}
			this.overlay.overlayViewElement = document.createElement('div');
			this.overlay.overlayViewElement.className = this.mapAction.measure.properties.overlayViewClassName;
			this.overlay.overlayViewLayer = new ol.Overlay({
				element : this.overlay.overlayViewElement,
				offset : [ 15, 0 ],
				positioning : 'center-left'
			});
			this.map.addOverlay(this.overlay.overlayViewLayer);
		},

		setControl : function() {
			var _self = this;

			var drawStyle = new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(102,204,204, 0.3)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(102,204,204, 1)',
				}),
				image : new ol.style.Circle({
					radius : 6,
					fill : new ol.style.Fill({
						color : 'rgba(255,255,255, 0)'
					}),
					stroke : new ol.style.Stroke({
						color : 'rgba(102,204,204, 1)',
						width : 2
					})
				})
			}); //그릴때 스타일 정의

			var measureStyle = new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 255, 255, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(255, 0, 0, 0.5)',
					lineDash : [ 10, 10 ],
					width : 2
				}),
				image : new ol.style.Circle({
					radius : 5,
					stroke : new ol.style.Stroke({
						color : 'rgba(0, 0, 0, 0.7)'
					}),
					fill : new ol.style.Fill({
						color : 'rgba(255, 255, 255, 0.2)'
					})
				})
			}); //측정 스타일 정의

			var angleModifyStyle = new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(102,204,204, 0)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(102,204,204, 0)',
				}),
				image : new ol.style.Circle({
					radius : 6,
					fill : new ol.style.Fill({
						color : 'rgba(255,255,255, 0)'
					}),
					stroke : new ol.style.Stroke({
						color : 'rgba(102,204,204, 0)',
						width : 2
					})
				})
			}); // 각도 아이콘 스타일 정의
			var angleSelectStyle = new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(102,204,204, 1)'
				}),
				stroke : new ol.style.Stroke({
					color : 'rgba(102,204,204, 1)',
				}),
				image : new ol.style.Circle({
					radius : 6,
					fill : new ol.style.Fill({
						color : 'rgba(255,255,255, 1)'
					}),
					stroke : new ol.style.Stroke({
						color : 'rgba(102,204,204, 1)',
						width : 2
					})
				})
			}); // 각도 아이콘 스타일 정의

			// vertext 스타일 정의
			var vertexStyles = [
				new ol.style.Style({
					stroke : new ol.style.Stroke({
						color : 'blue',
						width : 3
					}),
					fill : new ol.style.Fill({
						color : 'rgba(0, 0, 255, 0.1)'
					})
				}),
				new ol.style.Style({
					image : new ol.style.Circle({
						radius : 6,
						fill : new ol.style.Fill({
							color : 'orange'
						})
					}),
					geometry : function(feature) {
						var geometryType =  feature.getGeometry().getType();
						var geometry = null;
						var coordinates = new Array();

						switch(true){
							case geometryType.indexOf("Point") > -1:
								coordinates = feature.getGeometry().getCoordinates();
								geometry =  new ol.geom.Point(coordinates);
								break;
							case geometryType.indexOf("MultiPolygon") > -1:
								coordinates = feature.getGeometry().getCoordinates()[0][0];
								geometry =  new ol.geom.MultiPoint(coordinates);
								break;
							case geometryType == "MultiLineString":
								coordinates = feature.getGeometry().getCoordinates()[0];
								geometry =  new ol.geom.MultiPoint(coordinates);
								break;
							case geometryType.indexOf("LineString") > -1:
								coordinates = feature.getGeometry().getCoordinates();
								geometry =  new ol.geom.MultiPoint(coordinates);
								break;
							default:
								coordinates = feature.getGeometry().getCoordinates()[0];
								geometry =  new ol.geom.MultiPoint(coordinates);
								break;
						}

						return geometry;
					}
				})
			];



			var select = new ol.interaction.Select({
				style : function(feature, resolution) {

					// 하드코딩
					var zoom = _self.map.getView().getZoom();
					var scale;

					if (zoom == 9) {
						scale = 0.8;
					} else if (zoom == 8) {
						scale = 0.5;
					} else if (zoom == 7) {
						scale = 0.2;
					} else if (zoom == 6) {
						scale = 0.1;
					} else if (zoom == 5) {
						scale = 0.1;
					} else {
						return 0;
					} // 스케일 수정 11.16


					var style;
					var styles = [];
					if(resolution > 1.5){
						return vertexStyles;
					}else if(resolution <= 1.5 ){
						var id = feature.get("id");

						if (!id) id = feature.getId().split(".")[0];

						var image = "";
						if (id.indexOf("GT_A054_P") > -1) { // 문자표시
							var charCtt = feature.get("CTT");
							var DRN = feature.get("DRN");
							style = new ol.style.Style({
								text: new ol.style.Text({
									text: charCtt == undefined ? "" : "\n\n"+charCtt,
									font: 5+zoom+'px NanumBarunGothicBold',
									anchor: [0.5, 0.5],
									opacity: 1,
									scale: scale,
									fill: new ol.style.Fill({
										color: '#ffffff'
									}),
									stroke: new ol.style.Stroke({
										color: '#000000', width: 3 // 굵기 수정 11.16
									}),
									rotation: DRN * Math.PI / 180
								})
							});
						}else if (id.indexOf("GT_A110_P") > -1) { //신호등
                            var cde = feature.get("SIGNAL_CDE");
                            var use = feature.get("USE");
                            var angle = feature.get("DRN");

                            if(use == 1) {
                                if(cde == 'A2') { //횡형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'A3') { //횡형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'A4') { //횡형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'A5') { //횡형5색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_5.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B2') { //종형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Blue_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B3') { //종형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Blue_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B4') { //종형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Blue_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B5') { //종형5색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Blue_5.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'VA') { //가변형가변등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_VA.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'SW') { //보행등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_SW.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'ST1') { //시간표시등+보행등(도형형)
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_ST1.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'ST2') { //시간표시등+보행등(계수형)
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Red_ST2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A1)') { //경보형횡형1색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_1.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A2)') { //경보형횡형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A3)') { //경보형횡형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A4)') { //경보형횡형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B1)') { //경보형종형1색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_1.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B2)') { //경보형종형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B3)') { //경보형종형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B4)') { //경보형종형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Green_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                }
                            } else { //use == 2
                                if(cde == 'A2') { //횡형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'A3') { //횡형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'A4') { //횡형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'A5') { //횡형5색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_5.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B2') { //종형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B3') { //종형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B4') { //종형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'B5') { //종형5색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_5.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'VA') { //가변형가변등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_VA.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'SW') { //보행등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_SW.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'ST1') { //시간표시등+보행등(도형형)
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_ST1.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'ST2') { //시간표시등+보행등(계수형)
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_ST2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A1)') { //경보형횡형1색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_1.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A2)') { //경보형횡형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A3)') { //경보형횡형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(A4)') { //경보형횡형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B1)') { //경보형종형1색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_1.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B2)') { //경보형종형2색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_2.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B3)') { //경보형종형3색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_3.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                } else if(cde == 'WA(B4)') { //경보형종형4색등
                                    image = G.baseUrl + "images/map/mapicon/A110_P_Yellow_4.png";
                                    style = new ol.style.Style({
                                        image: new ol.style.Icon({
                                            anchor: [0, 0.5],
                                            opacity: 1,
                                            scale: scale,
                                            src: image,
                                            rotation: angle * Math.PI / 180
                                        })
                                    });
                                }

                            }


                        } else if (id.indexOf("GT_A055_P") > -1) { // 방향표시
							if (zoom == 9) {
								scale = 0.5;
							} else if (zoom == 8) {
								scale = 0.2;
							} else if (zoom == 7) {
								scale = 0.1;
							} else if (zoom == 6) {
								scale = 0.2;
							} else if (zoom == 5) {
								scale = 0.1;
							} else {
								return 0;
							} // 스케일 수정 11.16


							var kind = feature.get("DRN_CDE");
							var angle = feature.get("DRN");
							image = G.baseUrl + "images/map/mapicon/A055_P_" + kind + ".png";
							style = new ol.style.Style({
								image: new ol.style.Icon({
									anchor: [0, 0.5],
									opacity: 1,
									scale: scale,
									src: image,
									rotation: angle * Math.PI / 180
								})
							});
						}else if (id.indexOf("GT_A068_P") > -1) { // 횡단보도예고표시
							if (zoom == 9) {
								scale = 0.5;
							} else if (zoom == 8) {
								scale = 0.2;
							} else if (zoom == 7) {
								scale = 0.1;
							} else if (zoom == 6) {
								scale = 0.2;
							} else if (zoom == 5) {
								scale = 0.1;
							} else {
								return 0;
							} // 스케일 수정 11.16


							var angle = feature.get("DRN");
							image = G.baseUrl + "images/map/mapicon/A068_P.png";
							style = new ol.style.Style({
								image: new ol.style.Icon({
									anchor: [0.5, 0.5],
									opacity: 1,
									scale: scale,
									src: image,
									rotation: angle * Math.PI / 180
								})
							});
						}
						else if (id.indexOf("GT_A064_P") > -1) {
							var cde = feature.get("MRK_CDE");
							var	image = G.baseUrl + "images/map/mark/"+ cde + ".png";
							style = new ol.style.Style({
									image: new ol.style.Icon({
										anchor: [0.5, 0.5],
										opacity: 1,
//										scale: scale-0.2,
										scale: 0.3,
										src: image,
									})
								});
						}else if (id.indexOf("GT_A069_P") > -1) {
							image = G.baseUrl + "images/map/mapicon/A069_P.png";
							style = new ol.style.Style({
								image: new ol.style.Icon({
									anchor: [0.5, 0.5],
									opacity: 1,
									scale: scale,
									src: image,
								})
							});
						} else if (id.indexOf("A073_P") > -1) {
							image = G.baseUrl + "images/map/tgis/mapicon/A073_P.png";

							style = new ol.style.Style({
								image: new ol.style.Icon({
									anchor: [0.5,0.5],
									opacity: 1,
									scale: scale,
									src: image,
								})
							});
						}else if (id.indexOf("GT_A061_P") > -1) { // 제어기
							var cde = feature.get("CTL_MET");

							image = G.baseUrl + "images/map/mapicon/A061_P_" + cde + ".png";

							style = new ol.style.Style({
								image: new ol.style.Icon({
									anchor: [0.5, 0.5],
									opacity: 1,
									scale: scale,
									src: image,
								})
							});
						}else if (id.indexOf("GT_A062_P") > -1) { // 지주
							image = G.baseUrl + "images/map/mapicon/A062_P.png";
							style = new ol.style.Style({
								image: new ol.style.Icon({
									anchor: [0.5, 0.5],
									opacity: 1,
									scale: scale,
									src: image,
								})
							});
						}
						else {
//							 style = vertexStyles;
						}

						if (style != null) styles.push(style);

						if (style != vertexStyles) {
							$.each(vertexStyles, function(){
								styles.push(this);
							});
						}

						return styles;
					}
				},
				toggleCondition : ol.events.condition.singleClick
			});

			var modify = new ol.interaction.Modify({
				features : select.getFeatures(),
			});

			$.each(_self.control, function(kind){

				$.each(this, function(name){
					var control;
					if (kind == "draw" || kind == "measure") {
						var typeNm = name.substring(0,1).toUpperCase() + name.substring(1);

						if (typeNm == "Polyline") {
							typeNm = "LineString";
						} else if (typeNm.indexOf("Draw") > -1) {
							typeNm = typeNm.replace("Draw", "");
						} else if (typeNm == "SearchPolygon" || typeNm == "SearchCircle") {
							typeNm = typeNm.replace("Search", "");
						}

						var source;
						var style;
						var opt = null;

						if (kind == "draw") {
							style = drawStyle;

							source = _self.layer[name.indexOf("draw") > -1 ? "draw" : name].getSource();

							if (name == "searchBox") {
								typeNm = "Circle";
								opt = {
									geometryFunction : ol.interaction.Draw.createBox()
								};
							} else if (name == "square" || name == "drawSquare") {
								typeNm = "Circle";
								opt = {
									geometryFunction : ol.interaction.Draw.createRegularPolygon(4),
								};
							}
						} else {
							style = measureStyle;
							source = _self.layer[kind].getSource();
						}

						var drawOpt = $.extend({}, {
							source : source,
							type : typeNm,
							style : style,
						}, opt);

						control = new ol.interaction.Draw(drawOpt);
						// if (kind == "draw" && (name == "point" || name == "polyline" || name == "polygon")) {
						//
						// } else if (name != "draw" && name.indexOf("draw") > -1) {
						//
                        // }

					} else if (kind == "angle") {
						if (name == "select") {
							control = new ol.interaction.Select({
								layers : [_self.layer[kind]],
								toggleCondition : ol.events.condition.singleClick,
								style : angleSelectStyle
							});
						} else {
							control = new ol.interaction.Modify({
								features : _self.control[kind].select.getFeatures(),
								style : angleModifyStyle
							});

							control.on("modifyend", function(e){
								_self.stopAngle();
								_self.createAngle(_self.rotateFeature);
							});

						}
					} else {
							control = name == "select" ? select : modify;
							if (name == "select") {
								control = select;
							} else {
								control = modify;
							}
					}
					control.setActive(false);
					_self.control[kind][name] = control;
					_self.map.addInteraction(_self.control[kind][name]);
				});
			});
		},

		setOverlayLayerTooltip : function(params) {
			this.overlay = $.extend({}, this.overlay, params);

			this.overlay.overlayTooltipLayer = new ol.Overlay({
				id: 'layer-tooltip',
				element: this.overlay.overlayTooltipElement,
				offset: [10, 0],
				positioning: 'bottom-left'
			});

			this.map.addOverlay(this.overlay.overlayTooltipLayer);
		},

		getWkt : function (feature) {
			var format = new ol.format.WKT();
			var geom = feature.getGeometry();

			if (geom instanceof ol.geom.Circle) {
				geom = ol.geom.Polygon.fromCircle(geom);
			}

			var wkt = format.writeGeometry(geom);

			var type = geom.getType();

			switch(type){
				case "MultiLineString" :
					wkt = wkt.replace("MULTILINESTRING(", "LINESTRING");
					wkt = wkt.substr(0,wkt.length -1);
					break;
//				case "Polygon" :
//					wkt = wkt.replace("POLYGON", "MULTIPOLYGON(");
//					wkt += ")";
//					break;
			}

			return wkt;
		},
		createAngle : function (feature) {

			if(feature.getGeometry().getType() != "Point") return;

			this.rotateFeature  = feature;

			$.each(this.control.angle, function(){
				this.setActive(true);
			});

			var angle = feature.get("DRN");

			// if(feature.get("id").indexOf("GT_A110_P" )  > -1 || feature.get("id").indexOf("GT_A055_P" )  > -1  ){
            //     angle = angle+90;
			// }

			if(angle == null || angle == "" || angle == "undefined") {
                // if(feature.get("id").indexOf("GT_A110_P" )  > -1 || feature.get("id").indexOf("GT_A055_P" )  > -1  ){
                //     feature.set("DRN", 90);
                //     angle = 90;
                // }else{
                    feature.set("DRN",0);
                    angle = 0;
				// }
			}

            if(feature.get("id").indexOf("GT_A110_P" )  > -1 || feature.get("id").indexOf("GT_A055_P" )  > -1  ){
                angle = angle+90;
            }



			var angleFeature = new ol.Feature({
				id : "angle",
				title : "angle",
				geometry : new ol.geom.Point(this.mapAction.moveToDistance(feature.getGeometry().getCoordinates(), 3, angle)),
			});

			var _self = this;

			angleFeature.on("change", function(evt) {
				//각도 계산하는 부분인데 수정해야함 - 한봄 -
				var featureCoord = _self.rotateFeature.getGeometry().getCoordinates();
				var curAngle =  _self.mapAction.getAngle(evt.target.getGeometry().getCoordinates(), featureCoord);
                if(_self.rotateFeature.get("id").indexOf("GT_A110_P" )  > -1 || _self.rotateFeature.get("id").indexOf("GT_A055_P" )  > -1  ){
                    curAngle = curAngle - 90;
                }
				_self.rotateFeature.set("DRN",-(-curAngle));
				if(_self.rotateFeature.get("DRN")<0){
					_self.rotateFeature.set("DRN",_self.rotateFeature.get("DRN")+360);
				}
				$('#drn').val(_self.rotateFeature.get("DRN"));
				_self.rotateFeature.set("DRN", curAngle);

			});

			this.control.angle.select.getFeatures().push(angleFeature);
		},

		stopAngle : function () {
			$.each(this.control.angle, function(){
				this.setActive(false);
			});

			this.layer.angle.getSource().clear();

			this.control.angle.select.getFeatures().clear();
		},
	}

	window.MapMaker = MapMaker;

})(window, jQuery);