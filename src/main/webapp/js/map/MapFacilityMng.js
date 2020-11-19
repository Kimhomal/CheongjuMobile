(function(window, $) {
	"use strict";
	var MapFacilityMng = {};

	MapFacilityMng.style = {
		wfs : function(feature, resolution) {

			var zoom = mapMaker.map.getView().getZoom();
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
				scale = 0.1;
			} // 스케일 수정 11.16

			var id = feature.getId();
			var style = null;
			var image = null;
//			var scale = 1;
			if (resolution < 1.5 ) {

				if (id.indexOf("GT_A001_A") > -1 || id.indexOf("GT_A003_A") > -1 ) { // 도로면
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color: 'rgba(140,140,140, 0)',
							width: 1
						}),
						fill : new ol.style.Fill({
							color: 'rgba(140,140,140, 0.8)',
							opacity: 1
						})
					})
				}else if(id.indexOf("GT_A054_P") > -1) { //노면문자표시
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
				} else if (id.indexOf("GT_C115_A") > -1) { //노인보호구역
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'cross',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(248,104,32, 1)',
                                size: 1,
                                spacing: 10,
                                angle: 1
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(248,104,32, 1)',
                            width : 1
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/C115_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_C109_A") > -1) { // 교통섬
					var kind = feature.get("KND");
					if(kind==1){ //교통섬
						style = new ol.style.Style({
	                        fill: new ol.style.FillPattern(
	                            {	pattern: 'crosses',
	                                image:  undefined,
	                                ratio: 2,
	                                color: 'rgba(0,255,0, 1)',
	                                size: 10*scale*2,
	                                spacing:20*scale*2,
	                                angle: angle+90
	                            }),
	                        stroke : new ol.style.Stroke({
	                            color : 'rgba(0,255,0, 1)',
	                            width : 2
	                        })
	                    });

//						style =  new ol.style.Style({
//							stroke : new ol.style.Stroke({
//								color: 'rgba(0, 255, 0, 1)',
//								width: 2
//							})
//						})

					}else{ //보행섬
						style = new ol.style.Style({
	                        fill: new ol.style.FillPattern(
	                            {	pattern: 'crosses',
	                                image:  undefined,
	                                ratio: 2,
	                                color: 'rgba(163, 123, 111, 1)',
	                                size: 10*scale*2,
	                                spacing:20*scale*2,
	                                angle: angle+90
	                            }),
	                        stroke : new ol.style.Stroke({
	                            color : 'rgba(0,255,0, 1)',
	                            width : 2
	                        })
	                    });


//						style =  new ol.style.Style({
//							stroke : new ol.style.Stroke({
//								color: 'rgba(163, 123, 111, 1)',
//								width: 2
//							})
//						})

					}

				} else if (id.indexOf("GT_A057_L") > -1) { // 부착대
					style =  new ol.style.Style({
						stroke : new ol.style.Stroke({
							color: 'rgba(255, 0, 0, 0.8)',
							width: 2
						})
					});

				} else if (id.indexOf("GT_A061_P") > -1) { // 제어기
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
				} else if (id.indexOf("GT_A062_P") > -1) { // 지주
					// var kind = feature.get("A062_KND_CDE");
					image = G.baseUrl + "images/map/mapicon/A062_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_A065_L") > -1) { // 표지병
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,0, 0.5)',
							lineDash : [4, 4],
							width : 2
						})
					});
				} else if (id.indexOf("GT_C118_P") > -1) { // BIS단말기
					image = G.baseUrl + "images/map/mapicon/C118_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: 1,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C111_P") > -1) { // 승강장
					image = G.baseUrl + "images/map/mapicon/C111_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: 1,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C100_P") > -1) { // 가로등(조명시설)
					image = G.baseUrl + "images/map/mapicon/C100_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C097_P") > -1) { // 전광판
					image = G.baseUrl + "images/map/mapicon/C097_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C096_P") > -1) { // 장애물표적표지
					image = G.baseUrl + "images/map/mapicon/C096_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C093_P") > -1) { // 시선유도표지
					image = G.baseUrl + "images/map/mapicon/C093_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C092_P") > -1) { // 시선유도봉
					image = G.baseUrl + "images/map/mapicon/C092_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C088_P") > -1) { // 갈매기표시
					var kind = feature.get("KND");
					image = G.baseUrl + "images/map/mapicon/C088_P_"+ kind + ".png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C051_P") > -1) { // 도로반사경
					image = G.baseUrl + "images/map/mapicon/C051_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C024_P") > -1) { // 통신맨홀
					image = G.baseUrl + "images/map/mapicon/C024_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_C022_P") > -1) { // 보행용조명
					image = G.baseUrl + "images/map/mapicon/C022_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});

				} else if (id.indexOf("GT_A090_P") > -1) { // 압버튼/보행자버튼통합
					image = G.baseUrl + "images/map/mapicon/A090_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_A069_P") > -1) { // cctv
					image = G.baseUrl + "images/map/mapicon/A069_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_A055_P") > -1) { // 노면방향표시

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
						scale = 0.1;
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
				} else if (id.indexOf("GT_A049_P") > -1) { // 검지기
					image = G.baseUrl + "images/map/mapicon/A049_P.png";
					style = new ol.style.Style({
						image: new ol.style.Icon({
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: scale,
							src: image,
						})
					});
				} else if (id.indexOf("GT_A078_L") > -1) { // 배선도
					var kind = feature.get("KND");
					if(kind == 1) { //배선도-인입선
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({
								color : 'rgba(0,255,50, 1.0)',
								lineDash : [5, 5],
								width : 2
							})
						})

					} else if(kind==2) {
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //배선도-신호선
								color : 'rgba(255,0,0, 1.0)',
								lineDash : [5, 5],
								width : 2
							})
						})
					} else if(kind==3) {
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //배선도-통신선
								color : 'rgba(0,0,255, 1.0)',
								lineDash : [5, 5],
								width : 2
							})
						})
					} else if(kind==4) {
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //배선도-조명등선
								color : 'rgba(255,255,0, 1.0)',
								lineDash : [5, 5],
								width : 2
							})
						})
					}
				} else if (id.indexOf("GT_A079_L") > -1) { // 유턴구역
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,255,1)',
							lineDash : [10, 10],
							width : 2
						})
					})

				} else if (id.indexOf("GT_A081_L") > -1) { // 정지선
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,255,1)',
							width : 2
						})
					})

				} else if (id.indexOf("GT_A083_L") > -1) { // 중앙선
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,228,0, 1.0)',
							width : 2
						})
					})

				} else if (id.indexOf("GT_A084_L") > -1) { // 차선
					var clr = feature.get("CLR");
					if(clr==1){
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //차선-흰색
								color : 'rgba(255,255,255, 1)',
								width : 2
							})
						})
					} else if(clr==2){
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //차선-황색
								color : 'rgba(255,228,0, 1.0)',
								width : 2
							})
						});
					} else if(clr==3){
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //차선-파란색
								color : 'rgba(0,0,255, 1.0)',
								width : 2
							})
						})
					}
				} else if (id.indexOf("GT_C123_L") > -1) { // 도로구간 - 색 수정해야해
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(0,0,255, 1.0)',
							width : 2
						})
					})
				} else if (id.indexOf("GT_A015_L") > -1) { // 철도선로
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(125,129,131, 1.0)',
							width : 2
						})
					})
				} else if (id.indexOf("GT_A080_A") > -1) { // 주차구획
					var clr = feature.get("CLR");
					if(clr==1) {
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //주차구획-흰색
								color : 'rgba(255,255,255, 1.0)',
								lineDash : [2, 2],
								width : 2
							})
						})
					} else if (clr==2) { //주차구획-파란색
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({
								color : 'rgba(0,0,255, 1.0)',
								lineDash : [2, 2],
								width : 2
							})
						})
					}
				} else if (id.indexOf("GT_A082_L") > -1) { // 주차금지
					var kind = feature.get("KND");
					if(kind==1) {
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({ //주차금지-흰색실선
								color : 'rgba(255,255,255, 1.0)',
								width : 2
							})
						})
					} else if (kind==2) { //주차구획-흰색점선
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({
								color : 'rgba(255,255,255, 1.0)',
								lineDash : [50, 50],
								width : 2
							})
						})
					} else if (kind==3) { //주차구획-황색실선
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({
								color : 'rgba(255,228,0, 1.0)',
								width : 2
							})
						})
					} else if (kind==4) { //주차구획-황색점선
						style = new ol.style.Style({
							stroke : new ol.style.Stroke({
								color : 'rgba(255,228,0, 1.0)',
								lineDash : [50, 50],
								width : 2
							})
						})
					}
				} else if (id.indexOf("GT_A111_A") > -1) { // 공영주차장
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,255, 1.0)',
							width : 2
						})
					})
				} else if (id.indexOf("GT_C094_A") > -1) { //점자블럭
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'dot',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(255, 255, 27, 1)',
                                size: 4,
                                spacing:11,
                                angle: 90
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(255, 255, 27, 1)',
                            width : 1
                        })
                    });
					// image = G.baseUrl + "images/map/mapicon/C094_A.png";
					// style = new ol.style.Style({
					// 	image: new ol.style.Icon({
					// 		anchor: [0.5, 0.5],
					// 		opacity: 1,
					// 		scale: scale,
					// 		src: image,
					// 	})
					// });
				} else if (id.indexOf("GT_A004_A") > -1) { //횡단보도
					var geom = feature.getGeometry();
					var angle = 0;
					var lineString;
					var length, maxLength, maxLineString, maxIndex;
					maxLength = 0;
					geom.getCoordinates().forEach(function(c){
						for(var i = 0; i < c.length - 1; i++) {
							lineString = new ol.geom.LineString([c[i], c[i + 1]]);
							length = lineString.getLength();
							if(length > maxLength){
								maxLength = length;
								maxLineString = lineString;
								maxIndex = i;
							}
						}
						angle = mapMaker.mapAction.getAngle(c[maxIndex + 1], c[maxIndex]);
					});
					style = new ol.style.Style({
						fill: new ol.style.FillPattern(
								{	pattern: 'hatch',
									image:  undefined,
									ratio: 2,
									color: "#ffffff",
									size: 10*scale*2,
									spacing:20*scale*2,
									angle: angle+90
								}),
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,255, 1.0)',
							width : 2
						})
					});
				} else if (id.indexOf("GT_A067_A") > -1) { //과속방지턱

                    var geom = feature.getGeometry();
                    var angle = 0;
                    var lineString;
                    var length, maxLength, maxLineString, maxIndex;
                    maxLength = 0;
                    geom.getCoordinates().forEach(function(c){
                        for(var i = 0; i < c.length - 1; i++) {
                            lineString = new ol.geom.LineString([c[i], c[i + 1]]);
                            length = lineString.getLength();
                            if(length > maxLength){
                                maxLength = length;
                                maxLineString = lineString;
                                maxIndex = i;
                            }
                        }
                        angle = mapMaker.mapAction.getAngle(c[maxIndex + 1], c[maxIndex]);
                    });
                    style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'hatch',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(255, 255, 27, 1)',
                                size: 5,
                                spacing:11,
                                angle: 0
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(255, 255, 27, 1)',
                            width : 1
                        })
                    });

					// image = G.baseUrl + "images/map/mapicon/A067_A.png";
					// style = new ol.style.Style({
					// 	image: new ol.style.Icon({
					// 		anchor: [0.5, 0.5],
					// 		opacity: 1,
					// 		scale: scale,
					// 		src: image,
					// 	})
					// });
				} else if (id.indexOf("GT_C103_A") > -1) { //일방통행
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(125,255,131, 0.6)',
						})
					});
				} else if (id.indexOf("GT_A065_L") > -1) { //표지병
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(255,255,0, 1)',
							lineDash : [50, 50],
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C098_A") > -1) { //충격흡수시설
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'hatch',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(255,125,0, 0.7)',
                                size: 2,
                                spacing:11,
                                angle: angle+90
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(255,125,0, 0.7)',
                            width : 1
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/C098_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_C095_A") > -1) { //입체횡단시설
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'cross',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(0,216,255, 1)',
                                size: 1,
                                spacing: 10,
                                angle: 1
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(120,120,192, 1)',
                            width : 2
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/C095_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_C059_A") > -1) { //방호울타리
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'hatch',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(49,46,51, 0.5)',
                                size: 10*scale*2,
                                spacing:20*scale*2,
                                angle: 90
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(49,46,51, 0.5)',
                            width : 2
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/C059_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_C091_A") > -1) { //미끄럼방지시설
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'dot',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(49,46,51, 0.5)',
                                size: 5,
                                spacing:11,
                                angle: 0
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(49,46,51, 0.5)',
                            width : 2
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/C091_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_A085_A") > -1) { //정차금지지대 - 색이 안보여
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,255, 1.0)',
							width : 2
						})
					})
				} else if (id.indexOf("GT_C107_A") > -1) { //컬러포장
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'dot',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(163, 123, 111, 1)',
                                size: 5,
                                spacing:11,
                                angle: 90
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(163, 123, 111, 1)',
                            width : 1
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/C107_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_A005_A") > -1) { //안전지대
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'hatch',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(255, 235, 36, 1)',
                                size: 5,
                                spacing:11,
                                angle: 110
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(255, 235, 36, 1)',
                            width : 1
                        })
                    });

//					image = G.baseUrl + "images/map/mapicon/A005_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_C104_A") > -1) { //고원식교차로
                    style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'cross',
                                image:  undefined,
                                ratio: 2,
                                color: '#46ff1c',
                                size: 4,
                                spacing:11,
                                angle: 0
                            }),
                        stroke : new ol.style.Stroke({
                            color : '#46ff1c',
                            width : 1
                        })
                    });


					// image = G.baseUrl + "images/map/mapicon/C104_A.png";
					// style = new ol.style.Style({
					// 	image: new ol.style.Icon({
					// 		anchor: [0.5, 0.5],
					// 		opacity: 1,
					// 		scale: scale,
					// 		src: image,
					// 	})
					// });
				} else if (id.indexOf("GT_C117_A") > -1) { //장애인보호구역
					style = new ol.style.Style({
                        fill: new ol.style.FillPattern(
                            {	pattern: 'cross',
                                image:  undefined,
                                ratio: 2,
                                color: 'rgba(15,41,237, 1)',
                                size: 1,
                                spacing: 10,
                                angle: 1
                            }),
                        stroke : new ol.style.Stroke({
                            color : 'rgba(15,41,237, 1)',
                            width : 1
                        })
                    });


//					image = G.baseUrl + "images/map/mapicon/C117_A.png";
//					style = new ol.style.Style({
//						image: new ol.style.Icon({
//							anchor: [0.5, 0.5],
//							opacity: 1,
//							scale: scale,
//							src: image,
//						})
//					});
				} else if (id.indexOf("GT_A019_A") > -1) { //철도역사플랫폼
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(127,131,138, 0.5)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C101_A") > -1) { //어린이보호구역
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(255,255,138, 0.5)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_B001_A") > -1) { //건물
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(127,131,138, 0.5)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C114_A") > -1) { //자전거전용도로
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,138, 1)',
							// lineDash : [50, 50],
							width : 2
						})
					})
				} else if (id.indexOf("GT_A068_P") > -1) { //횡단보도예고표시

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
						scale = 0.1;
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
				} else if (id.indexOf("GT_A008_A") > -1) { //교차로
					var charCtt = feature.get("CSS_NAM");
					style = new ol.style.Style({
						text: new ol.style.Text({
							text: charCtt == undefined ? "" : charCtt,
							font: 5+zoom+'px NanumBarunGothicBold',
							anchor: [0.5, 0.5],
							opacity: 1,
							scale: 1,
							fill: new ol.style.Fill({
								color: '#0014ff'
							}),
							stroke: new ol.style.Stroke({
								color: '#ffffff', width: 3 // 굵기 수정 11.16
							}),
						}),
							stroke : new ol.style.Stroke({
								color : 'rgba(255,0,0, 1)',
								lineDash : [2, 2],
								width : 2
							}),
					})
				} else if (id.indexOf("GT_A110_P") > -1) { //신호등
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


                } else if (id.indexOf("GT_C120_A") > -1) { //실폭도로
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(116,120,120, 1)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_B101_A") > -1) { //건물군
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(127,131,138, 0.5)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C116_A") > -1) { //지하차도
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(116,120,120, 1)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C113_A") > -1) { //고가도로
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(116,120,120, 1)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_A011_A") > -1) { //터널
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(116,120,120, 1)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C121_A") > -1) { //자동차전용도로
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(116,120,120, 1)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_A007_A") > -1) { //교량
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(127,131,138, 0.5)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C128_A") > -1) { //공원
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(66,215,77, 0.5)',
							width : 1.5
						})
					});
				} else if (id.indexOf("A064_P") > -1) { // 안전표지
					var cde = feature.get("MRK_CDE");
					var	image = G.baseUrl + "images/map/mark/"+ cde + ".png";
					style = new ol.style.Style({
							image: new ol.style.Icon({
								anchor: [0.5, 0.5],
								opacity: 1,
//								scale: scale-0.2,
								scale: 0.3,
								src: image,
							})
						});
				} else if (id.indexOf("GT_C129_L") > -1) { //도로속도구간
					style = new ol.style.Style({
						fill: new ol.style.Fill({
							color : 'rgba(255,0,0, 0.6)',
							width : 1.5
						})
					});
				} else if (id.indexOf("GT_C087_L") > -1) { //노선 - 색수정해야해
					style = new ol.style.Style({
						stroke : new ol.style.Stroke({
							color : 'rgba(255,255,138, 1)',
							lineDash : [50, 50],
							width : 2
						})
					})
				}











				return style;
				}
		},
	};

	MapFacilityMng.layer = {
		wms : {},
		wfs : {
			GT_A001_A : {
				id : "GT_A001_A",
				layerKorName : "도로면",
				kind : "basic",
				maxResolution : 1,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				 style : MapFacilityMng.style.wfs
			},
			GT_A003_A : {
				id : "GT_A003_A",
				layerKorName : "인도",
				kind : "basic",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				 style : MapFacilityMng.style.wfs
			},
			GT_C123_L : {
				id : "GT_C123_L",
				layerKorName : "도로구간",
				kind : "specialTraffic",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C116_A : {
				id : "GT_C116_A",
				layerKorName : "지하차도",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C120_A : {
				id : "GT_C120_A",
				layerKorName : "실폭도로",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C121_A : {
				id : "GT_C121_A",
				layerKorName : "자동차전용도로",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C122_L : {
				id : "GT_C122_L",
				layerKorName : "기초구간",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_A011_A : {
				id : "GT_A011_A",
				layerKorName : "터널",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_A079_L : {
				id : "GT_A079_L",
				layerKorName : "유턴구역",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C024_P : {
				id : "GT_C024_P",
				layerKorName : "통신맨홀",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},

			GT_A007_A : {
				id : "GT_A007_A",
				layerKorName : "교량",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C103_A : {
				id : "GT_C103_A",
				layerKorName : "일방통행",
				kind : "specialTraffic",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A015_L : {
				id : "GT_A015_L",
				layerKorName : "철도선로",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C113_A : {
				id : "GT_C113_A",
				layerKorName : "고가도로",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C114_A : {
				id : "GT_C114_A",
				layerKorName : "자전거전용도로",
				kind : "basic",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A005_A : {
				id : "GT_A005_A",
				layerKorName : "안전지대",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_B001_A : {
				id : "GT_B001_A",
				layerKorName : "건물",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_B101_A : {
				id : "GT_B101_A",
				layerKorName : "건물군",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C128_A : {
				id : "GT_C128_A",
				layerKorName : "공원",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C117_A : {
				id : "GT_C117_A",
				layerKorName : "장애인보호구역",
				kind : "specialTraffic",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},

			GT_C101_A : {
				id : "GT_C101_A",
				layerKorName : "어린이보호구역",
				kind : "specialTraffic",
				maxResolution : 1,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C115_A : {
				id : "GT_C115_A",
				layerKorName : "노인보호구역",
				kind : "specialTraffic",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
             GT_A111_A : {
			 	id : "GT_A111_A",
			 	layerKorName : "공영주차장",
			 	kind : "trafficfacility",
			 	maxResolution : 0.5,
			 	category : "etc",
			 	visible : true,
			 	mgrnuYn : "Y",
			 	style : MapFacilityMng.style.wfs
			 },
			GT_C091_A : {
				id : "GT_C091_A",
				layerKorName : "미끄럼방지시설",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C109_A : {
				id : "GT_C109_A",
				layerKorName : "교통섬",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
//			GT_A008_P : {
//				id : "GT_A008_P",
//				layerKorName : "교차로",
//				kind : "basic",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},
			GT_A078_L : {
				id : "GT_A078_L",
				layerKorName : "배선도",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A019_A : {
				id : "GT_A019_A",
				layerKorName : "철도역사(플랫폼)",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_A049_P : {
				id : "GT_A049_P",
				layerKorName : "검지기",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A054_P : {
				id : "GT_A054_P",
				layerKorName : "노면문자표시",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A055_P : {
				id : "GT_A055_P",
				layerKorName : "노면방향표시",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A004_A : {
				id : "GT_A004_A",
				layerKorName : "횡단보도",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A057_L : {
				id : "GT_A057_L",
				layerKorName : "부착대",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A061_P : {
				id : "GT_A061_P",
				layerKorName : "제어기",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A062_P : {
				id : "GT_A062_P",
				layerKorName : "지주",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C104_A : {
				id : "GT_C104_A",
				layerKorName : "고원식교차로",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A008_A : {
				id : "GT_A008_A",
				layerKorName : "교차로",
				kind : "basic",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A065_L : {
				id : "GT_A065_L",
				layerKorName : "표지병",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A067_A : {
				id : "GT_A067_A",
				layerKorName : "과속방지턱",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A068_P : {
				id : "GT_A068_P",
				layerKorName : "횡단보도예고표시",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A069_P : {
				id : "GT_A069_P",
				layerKorName : "CCTV",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A080_A : {
				id : "GT_A080_A",
				layerKorName : "주차구획",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A081_L : {
				id : "GT_A081_L",
				layerKorName : "정지선",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A082_L : {
				id : "GT_A082_L",
				layerKorName : "주차금지",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A083_L : {
				id : "GT_A083_L",
				layerKorName : "중앙선",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A084_L : {
				id : "GT_A084_L",
				layerKorName : "차선",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A085_A : {
				id : "GT_A085_A",
				layerKorName : "정차금지지대",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A090_P : {
				id : "GT_A090_P",
				layerKorName : "압버튼/보행자버튼통합",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C022_P : {
				id : "GT_C022_P",
				layerKorName : "보행용조명",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C051_P : {
				id : "GT_C051_P",
				layerKorName : "도로반사경",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C059_A : {
				id : "GT_C059_A",
				layerKorName : "방호울타리",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C087_L : {
				id : "GT_C087_L",
				layerKorName : "노선",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : false,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C088_P : {
				id : "GT_C088_P",
				layerKorName : "갈매기표지",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},

			GT_C092_P : {
				id : "GT_C092_P",
				layerKorName : "시선유도봉",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C093_P : {
				id : "GT_C093_P",
				layerKorName : "시선유도표지",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C094_A : {
				id : "GT_C094_A",
				layerKorName : "점자블럭",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C095_A : {
				id : "GT_C095_A",
				layerKorName : "입체횡단시설",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C096_P : {
				id : "GT_C096_P",
				layerKorName : "장애물표적표지",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C097_P : {
				id : "GT_C097_P",
				layerKorName : "전광판",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_A064_P : {
				id : "GT_A064_P",
				layerKorName : "안전표지",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C098_A : {
				id : "GT_C098_A",
				layerKorName : "충격흡수시설",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C100_P : {
				id : "GT_C100_P",
				layerKorName : "가로등(조명시설)",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},

			GT_C107_A : {
				id : "GT_C107_A",
				layerKorName : "컬러포장",
				kind : "roadFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C125_P : {
				id : "GT_C125_P",
				layerKorName : "도로시설물위치",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_C126_L : {
				id : "GT_C126_L",
				layerKorName : "연결선",
				kind : "landFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
//			GT_C127_P : {
//				id : "GT_C127_P",
//				layerKorName : "출입구",
//				kind : "exceptionFacility",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},

//			GT_LP_AA_SGG : {
//				id : "GT_LP_AA_SGG",
//				layerKorName : "시군구",
//				kind : "landFacility",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},
//			GT_LP_AA_EMD : {
//				id : "GT_LP_AA_EMD",
//				layerKorName : "읍면동",
//				kind : "landFacility",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},
//			GT_LP_AA_RI : {
//				id : "GT_LP_AA_RI",
//				layerKorName : "리",
//				kind : "landFacility",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},
//			GT_LP_PA_CBND : {
//				id : "GT_LP_PA_CBND",
//				layerKorName : "지적",
//				kind : "landFacility",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},
//			GT_POLICE : {
//				id : "GT_POLICE",
//				layerKorName : "경찰서",
//				kind : "landFacility",
//				maxResolution : 0.5,
//				category : "etc",
//				visible : true,
//				style : MapFacilityMng.style.wfs
//			},
			GT_C124_P : {
				id : "GT_C124_P",
				layerKorName : "도로명판뷰",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
			GT_A110_P : {
				id : "GT_A110_P",
				layerKorName : "신호등",
				kind : "trafficfacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C111_P : {
				id : "GT_C111_P",
				layerKorName : "승강장(버스정류장)",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C118_P : {
				id : "GT_C118_P",
				layerKorName : "BIS단말기",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "Y",
				style : MapFacilityMng.style.wfs
			},
			GT_C129_L : {
				id : "GT_C129_L",
				layerKorName : "도로속도구간",
				kind : "exceptionFacility",
				maxResolution : 0.5,
				category : "etc",
				visible : true,
				mgrnuYn : "N",
				gbn : "N",
				style : MapFacilityMng.style.wfs
			},
		},
	};

	window.MapFacilityMng = MapFacilityMng;

})(window, jQuery);