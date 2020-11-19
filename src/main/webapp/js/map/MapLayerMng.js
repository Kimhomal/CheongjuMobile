(function(window, $) {
	"use strict";

	var MapLayerMng = function(mapMaker) {
		this._mapMaker = mapMaker;
	};

	MapLayerMng.prototype = {
		boundary : {
			lp_aa_sido : "시도",
			lp_aa_sgg : "시군구",
			lp_aa_emd : "읍면동",
			lp_pa_bbnd : "지번 올드",
			lp_pa_cbnd : "지번"
		},

		layers : {
			wms : {},
			wfs : {},
		},

		_addWfsLayer : function(lyrName, params){
			var _self = this;
			var geoJsonFormat = new ol.format.GeoJSON();
//			var wktFormat = new ol.format.WKT();
			var maxRes = params.maxResolution;
			if(maxRes == null || maxRes == "" || maxRes == "undefined") maxRes = 1.5;

			_self.layers.wfs[lyrName] = new ol.layer.Vector({
				name: lyrName,
				id: lyrName,
				title : params.layerKorName,
				kind : params.kind,
				category : params.category,
				maxResolution : maxRes,
				mgrnuYn : params.mgrnuYn,
				gbn : params.gbn,
				source: new ol.source.Vector({
					loader: function(extent, resolution, projection) {

						if((!_self._mapMaker.mapAction.layer.properties.featureEditMode || _self.layers.wfs[lyrName].getSource().getFeatures().length == 0) && !_self._mapMaker.mapAction.layer.properties.isFeatureSelect) {
							_self.layers.wfs[lyrName].getSource().clear(true);

							extent = ol.proj.transformExtent(extent,  _self._mapMaker.crsCode, _self._mapMaker.config.facilityCrsCode);

                            var reqData =	{
                                request: "GetFeature",
                                version: "1.1.1",
                                typename:  _self._mapMaker.config.workspace + ":"  + lyrName  + (_self._mapMaker.config.drawingYn == "Y" && lyrName != "A003_A" && lyrName != "A001_A" && lyrName != "C080_A" ? "_TMP" : ""),
                                outputFormat: "application/json",
                                bbox: extent.join(",") + "," + _self._mapMaker.config.facilityCrsCode
                            };


							$.ajax({
								url : _self._mapMaker.config.noProxyWfs,
								dataType : "json",
								cache : false,
//								async : false,
                                data :reqData,
								beforeSend	: function(){
									$.showBlock();
								},
								success:function(response){
									var fs = geoJsonFormat.readFeatures(response, {
										dataProjection : _self._mapMaker.config.facilityCrsCode ,
										featureProjection : _self._mapMaker.crsCode
									});

                                    fs.forEach(function(feature){
                                        if (_self._mapMaker.mapAction.isRotateFacility(lyrName)) {	// 회전객체
                                            fs.forEach(function(feature){
                                                feature.set("oriAngle", feature.get("DRN"));
                                            });
                                        }
                                    });

									_self.layers.wfs[lyrName].getSource().addFeatures(fs);
								},
								complete	: function(){
									$.hideBlock();
								},
								error : function(a, b, c){
									console.log("error : " + lyrName);
								}
							})
						}
					},
					strategy: ol.loadingstrategy.bbox
				}),
				visible : params.visible,
				style : params.style
			});

			_self._mapMaker.map.addLayer(_self.layers.wfs[lyrName]);
		},
		_addWmsLayer : function(lyrName, params){
			this.layers.wms[lyrName] = new ol.layer.Tile({
				kind : params.kind,
				title : params.layerKorName,
				id : lyrName,
				visible : params.visible,
//				maxResolution : 0.5,
				source : new ol.source.TileWMS({
					projection: this._mapMaker.config.facilityCrsCode,
//					url : "http://1.234.21.200:8080/geoserver/seoul/wms",
					url : this._mapMaker.config.noProxyWms,
					params : {
						LAYERS : lyrName,
						VERSION : '1.1.0',
						QUERY_LAYERS: lyrName,
						FORMAT : "image/png"
//						tiled		: true
					},
					serverType : "geoserver"
				})
			});

			this._mapMaker.map.addLayer(this.layers.wms[lyrName]);
		},

		_setBaseMapLayer : function(params){
			var _self = this;
			var baseMapLyr;
			if(params.layerName.indexOf("Daum")>-1){
				baseMapLyr = new ol.layer.Tile({
					kind : params.kind,
					title : params.layerKorName,
					id : params.layerName,
					visible : params.visible,
					source : new ol.source.XYZ({
						url : params.url,
						projection : params.crsCode,
						tileGrid : params.tileGrid,
					})
				});
			}else{
				baseMapLyr = new ol.layer.Tile({
					kind : params.kind,
					title : params.layerKorName,
					id : params.layerName,
					visible : params.visible,
					source : new ol.source.XYZ({
						url : params.url,
						projection : params.crsCode,
						tileGrid : params.tileGrid,
						crossOrigin: "Anonymous"
					})
				});
			}

			return baseMapLyr;
		},

		addBaseMapLayers : function(){

			var _self = this;

			$.each(_self._mapMaker.baseMapLayers, function(idx, lyr){
				_self._mapMaker.map.removeLayer(lyr);
			});

			_self._mapMaker.baseMapLayers = [];

			var proxyBackground = _self._mapMaker.config.proxyBackground;

			$.each(_self._mapMaker.baseMap.layers, function(lyrName, lyr){

				var baseMapName = _self._mapMaker.baseMap.name;
				var tileGrid;
				var layer;

				if (baseMapName == "Naver" ) {
					tileGrid = new ol.tilegrid.TileGrid({
						extent : [ 90112, 1192896, 1990673, 2765760 ],
						tileSize : 256,
						resolutions : [ 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1, 0.5, 0.25 ],
						minZoom : 1,
					});
				} else if (baseMapName == "Daum") {
					tileGrid = new ol.tilegrid.TileGrid({
						extent : [ (-30000 - 524288), (-60000 - 524288), (494288 + 524288), (988576 + 524288) ],
						origin : [ -30000, -60000 ],
						tileSize : 256,
						resolutions : [ 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1, 0.5, 0.25 ],
						minZoom : 1,
					});
				} else if (baseMapName == "Baroemap") {
                    tileGrid = new ol.tilegrid.TileGrid({
                        extent: [-200000.0, -3015.4524155292, 3803015.45241553, 4000000.0],
                        origin: [-200000, 4000000],
                        tileSize : 256,
                        resolutions : [ 2088.96, 1044.48, 522.24, 261.12, 130.56, 65.28,32.64, 16.32, 8.16, 4.08, 2.04, 1.02, 0.51, 0.255 ],
                        minZoom : 1
                    })
                }

				layer = _self._setBaseMapLayer({
					layerName : baseMapName + "_" + lyrName,
					layerKorName : lyr.name,
					url : proxyBackground + lyr.url,
					visible : lyr.visible,
					crsCode : _self._mapMaker.baseMap.crsCode,
					kind : "base",
					tileGrid : tileGrid,
				});

				var tileUrlFunction;
				var zRegEx = /\{z\}/g;
				var xRegEx = /\{x\}/g;
				var yRegEx = /\{y\}/g;
				var jRegEx = /\{j\}/g;
				var kRegEx = /\{k\}/g;

				if (baseMapName == "Daum") {
					tileUrlFunction = function(tileCoord, pixelRatio, projection) {
						if (tileCoord == null) return undefined;

						var s = Math.floor(Math.random() * 4); // 0 ~ 3
						var z = this.tileGrid.getResolutions().length - tileCoord[0];
						var x = tileCoord[1].toString();
						var y = tileCoord[2].toString();

						return proxyBackground + lyr.url.replace(xRegEx, x).replace(yRegEx, y).replace(zRegEx, z).replace("{0-3}", s);
					};

					layer.getSource().setTileUrlFunction(tileUrlFunction);

				} else if (baseMapName == "Baroemap") {

                    tileUrlFunction =   function(tileCoord, pixelRatio, projection) {
                        if (tileCoord === null) return undefined;

                        var z = $.lpad((tileCoord[0] + 5).toString(), 2, "0");
                        var x = Math.abs(tileCoord[2].toString())-1;
                        var y = tileCoord[1].toString();

                        return proxyBackground + lyr.url.replace(xRegEx, x).replace(yRegEx, y).replace(zRegEx, z);

                    };

                    layer.getSource().setTileUrlFunction(tileUrlFunction);
                }

				_self._mapMaker.baseMapLayers.push(layer);
			});

			$.each(_self._mapMaker.baseMapLayers, function(idx, lyr){
				_self._mapMaker.map.getLayers().insertAt(idx, lyr);
			});
		},
		/**
		 * 행정경계 레이어 추가
		 * @date
		 * @method
		 * @name addBoundaryLayers
		 * @param visible(bool type) -> true : 행정경계 레이어 visible true 및 투명처리 / false : 행정경계 레이어 visible false 및 파란색경계
		 */
		addBoundaryLayers : function(){
			var _self = this;

			$.each(this.boundary, function(lyrNm, korLyrNm){
				_self._addWmsLayer(lyrNm, {
					layerKorName : korLyrNm,
					kind : "allBasic",
					visible : false
				});
			});
		},

		addFacilityLayers : function(){
			var _self = this;

			//WMS
			$.each(MapFacilityMng.layer.wms, function(k, v){
				_self._addWmsLayer(k, v);
			});

			//WFS
			$.each(MapFacilityMng.layer.wfs, function(k, v){
				_self._addWfsLayer(k, v);
			});

			//도로점용
			$.each(MapFacilityMng.layer.roadWfs, function(k, v){
				_self._addRoadFclts(k, v);
			});


		},

		setTempLayer : function() {
			var _self = this;

			$.each(_self._mapMaker.layer, function(layerId){
				var layer;
				if(layerId == "measure" ||layerId == "search") {
					layer = new ol.layer.Vector({
						source : new ol.source.Vector(),
						style : new ol.style.Style({
							fill : new ol.style.Fill({
								color : 'rgba(255, 255, 255, 0.2)'
							}),
							stroke : new ol.style.Stroke({
								color : 'red',
								width : 2
							}),
							image : new ol.style.Circle({
								radius : 7,
								fill : new ol.style.Fill({
									color : '#ffcc33'
								})
							})
						})
					});
				} else if (layerId == "marker") {
					layer = new ol.layer.Vector({
						source : new ol.source.Vector(),
						style : new ol.style.Style({
							image: new ol.style.Icon({
								size: [128,128],
								anchor: [0.5, 1.0],
								opacity: 1,
								scale: 0.3,
								src: G.baseUrl + "images/main/pin_v1.png",
							})
						})
					});
				}else {
					layer = new ol.layer.Vector({
						title : layerId,
						id	: layerId,
						maxResolution : (layerId=="sign" ? 0.5:1),
						source: new ol.source.Vector(),
					});
					if (layerId == "point" || layerId == "polyline" || layerId == "polygon") {
						_self._mapMaker.mapEvtMng.setAddFeatEvt(layer.getSource());
					} else if (layerId.indexOf("draw")  > -1) {
						layer.setStyle(function(feature){
							var geom = feature.getGeometry();

							if (geom instanceof ol.geom.Polygon) {
								return new ol.style.Style({
									fill : new ol.style.Fill({
										color : 'rgba(255, 255, 255, 0.0)'
									}),
									stroke : new ol.style.Stroke({
										color : 'rgba(255, 0, 0, 1)',
										width : 2
									}),
								});
							} else if (geom instanceof ol.geom.LineString) {
								var styles = [
									new ol.style.Style({
										stroke: new ol.style.Stroke({
											color: 'rgba(255, 0, 0, 1)',
											width: 2
										})
									})
								];

								geom.forEachSegment(function(start, end) {
									var dx = end[0] - start[0];
									var dy = end[1] - start[1];
									var rotation = Math.atan2(dy, dx);
									// arrows
									styles.push(new ol.style.Style({
										geometry: new ol.geom.Point(end),
										image: new ol.style.Icon({
											src: G.baseUrl + 'images/map/arrow_red.png',
											anchor: [0.75, 0.5],
											rotateWithView: true,
											rotation: -rotation
										})
									}));
								});

								return styles;
							}
						});

						if (layerId.indexOf("Real") == -1) _self._mapMaker.mapEvtMng.setAddFeatEvt(layer.getSource(), "D");
					}
					else if (layerId.indexOf("search") != -1 && layerId != "search") {
						layer.setStyle(new ol.style.Style({
							fill : new ol.style.Fill({
								color : 'rgba(255, 255, 255, 0.1)'
							}),
							stroke : new ol.style.Stroke({
								color : 'rgba(0, 84, 255, 1.0)',
								width : 2
							}),
						}));
						_self._mapMaker.mapEvtMng.setAddFeatEvt(layer.getSource(), "S");
					}
				}
				_self._mapMaker.layer[layerId] = layer;
				_self._mapMaker.map.addLayer(_self._mapMaker.layer[layerId]);
			});
		},

		getLayerById : function(id){
			var layers = this._mapMaker.map.getLayers().getArray();
			var layer = null;
			$.each(layers, function(idx, lyr){
				if(lyr.get("id") == id) {
					layer = lyr;
					return false;
				}
			});

			return layer;
		},

        allLayerRemove : function(){
			var _self=this;
            var layers = _self._mapMaker.map.getLayers().getArray();
            $.each(layers, function(idx, lyr){
            	if(lyr != null) {
            		if(lyr.get("kind") != undefined && lyr.get("kind") != "base") {
                        _self._mapMaker.map.removeLayer(lyr);
                   }
				}
            });
        }
	}

	window.MapLayerMng = MapLayerMng;

})(window, jQuery);