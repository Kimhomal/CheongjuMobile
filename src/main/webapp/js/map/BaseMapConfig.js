(function(window, $) {
	"use strict";

	var BaseMapConfig = {

			Daum : {
				name : "Daum",
				korName : "다음지도",
				crsCode : "EPSG:5181",
//				extent : [168123.95, 436421.637360, 228039.09, 466843.609998],
				extent : [224656,323460, 268876,364775],
//				extent : [167621.67, 172758.23, 202031.19, 195834.38],

				//↓ 변경 후 해상도
				resolutions : [32, 16, 8, 4, 2, 1, 0.5, 0.25, 0.125,0.0625],

				//↓ 변경 전 해상도
				// resolutions : [38.218514142588134, 19.109257071294067, 9.554628535647034, 4.777314267823517, 2.3886571339117584, 1.1943285669558792, 0.5971642834779396, 0.2985821417389698,0.1492910708694849,0.0746455354347425],
//				center : [200773.86, 449844.44],

//					center : [237430.2184987092, 316175.20663535304],
					center : [243725.07990255085, 349450.5436916202],
				layers : {
					base : {
						name : "기본지도",
						url : "http://map{0-3}.daumcdn.net/map_2d/1806shn/L{z}/{y}/{x}.png",
//						http://map3.daumcdn.net/k3f_map_2d/1804vdy/L3/1930/939.png
						visible : true
					},
					satellite : {
						name : "위성지도",
						url : "http://s1.maps.daum-img.net/L{z}/{y}/{x}.jpg?v=160114",
						visible : false
					},
//					hybrid : {
//						name : "하이브리드",
//						url : "http://map{0-3}.daumcdn.net/map_hybrid/9ber/L{z}/{y}/{x}.png",
//						visible : true
//					},
				}
			},
			Naver : {
				name : "Naver",
				korName: "네이버지도",
				crsCode: "EPSG:5179",
				extent: [979949,1823412 , 1024128,1864682],
				//↓ 변경 후 해상도
				resolutions : [32, 16, 8, 4, 2, 1, 0.5, 0.25, 0.125,0.0625],
				//↓ 변경 전 해상도
				// resolutions : [38.218514142588134, 19.109257071294067, 9.554628535647034, 4.777314267823517, 2.3886571339117584, 1.1943285669558792, 0.5971642834779396, 0.2985821417389698,0.1492910708694849,0.0746455354347425],
				center: [999014.2364788984, 1849399.8427000858],
				layers: {
					base : {
						name : "기본지도",
//						url: "http://onetile{1-4}.map.naver.net/get/146/0/0/{z}/{x}/{-y}/bl_vc_bg/ol_vc_an",
						url : "http://onetile{1-4}.map.naver.net/get/194/0/0/{z}/{x}/{-y}/bl_vc_bg/ol_vc_an",
						visible : true
					},
					satellite: {
						name: "위성지도",
						url: "http://onetile{1-4}.map.naver.net/get/194/0/1/{z}/{x}/{-y}/bl_st_bg",
						visible: false
					},
					// hybrid: {
					// 	name : "하이브리드",
					// 	url : "http://onetile{1-4}.map.naver.net/get/194/0/0/{z}/{x}/{-y}/empty/ol_st_rd/ol_st_an",
					// 	visible : false
					// },
				}
			},

//			VWorld : {
//				name : "VWorld",
//				korName : "브이월드",
//				crsCode : "EPSG:3857",
//				extent : [14097459.98, 4498797.83, 14172948.52, 4537291.27],
////				resolutions : [38.218514142588134,19.109257071294067,9.554628535647034, 4.777314267823517,2.3886571339117584,1.1943285669558792,0.5971642834779396, 0.2985821417389698,0.1492910708694849,0.0746455354347425],
//				resolutions : [38.2185141357421,19.10925707,9.554628534, 4.777314267,2.388657133,1.194328567,
//				               0.597164283, 0.298582142,0.149291071,0.074645535],
//				center: [14135157.465643, 4518396.374957],
//
//				layers: {
//					base : {
//						name : "기본지도",
//						url : "http://xdworld.vworld.kr:8080/2d/Base/service/{z}/{x}/{y}.png",
//						visible : true
//					},
////					gray : {
////						name : "흑백지도",
////						url : "http://xdworld.vworld.kr:8080/2d/gray/201512/{z}/{x}/{y}.png",
////						visible : false
////					},
//					satellite : {
//						name : "위성지도",
//						url : "http://xdworld.vworld.kr:8080/2d/Satellite/service/{z}/{x}/{y}.jpeg",
//						visible : false
//					},
////					midnight : {
////						name : "야간지도",
////						url : "http://xdworld.vworld.kr:8080/2d/midnight/201512/{z}/{x}/{y}.png",
////						visible : false
////					},
//					hybrid : {
//						name : "하이브리드",
//						url : "http://xdworld.vworld.kr:8080/2d/Hybrid/service/{z}/{x}/{y}.png",
//						visible : false
//					}
//					//납품시 적용
////					base: {
////					name: "기본지도",
////					url: "http://api.vworld.kr/req/wmts/1.0.0/E4A59B05-0CF4-3654-BD0C-A169F70CCB34/Base/{z}/{y}/{x}.png",
////					visible: true
////				},
////				gray: {
////					name: "흑백지도",
////					url: "http://api.vworld.kr/req/wmts/1.0.0/E4A59B05-0CF4-3654-BD0C-A169F70CCB34/gray/{z}/{y}/{x}.png",
////					visible: false
////				},
////				satellite: {
////					name: "위성지도",
////					url: "http://api.vworld.kr/req/wmts/1.0.0/E4A59B05-0CF4-3654-BD0C-A169F70CCB34/Satellite/{z}/{y}/{x}.jpeg",
////					visible: false
////				},
////				midnight: {
////					name: "야간지도",
////					url: "http://api.vworld.kr/req/wmts/1.0.0/E4A59B05-0CF4-3654-BD0C-A169F70CCB34/midnight/{z}/{y}/{x}.png",
////					visible: false
////				},
////				hybrid: {
////					name: "하이브리드",
////					url: "http://api.vworld.kr/req/wmts/1.0.0/E4A59B05-0CF4-3654-BD0C-A169F70CCB34/Hybrid/{z}/{y}/{x}.png",
////					visible: false
////				}
//				}
//			},
			Baroemap : {
				name : "Baroemap",
				korName: "바로이맵",
				crsCode: "EPSG:5179",
           		extent : [979949,1823412 , 1024128,1864682],
          		// resolutions : [32, 16, 8, 4, 2, 1, 0.5, 0.25, 0.125, 0.0625],1954.597389, 977.2986945, 488.64934725, 244.324673625,
            // 2088.96, 1044.48, 522.24, 261.12, 130.56, 65.28,32.64, 16.32, 8.16, 4.08, 2.04, 1.02, 0.51, 0.255
           		resolutions: [32.64, 16.32, 8.16, 4.08, 2.04, 1.02, 0.51, 0.255],
          		center : [999013.8564226635, 1849399.3889883182],
				layers: {
					base : {
						name : "기본지도",
						url : "http://map.ngii.go.kr/openapi/Gettile.do?service=WMTS&request=GetTile&version=1.0.0&layer=korean_map&style=korean&format=image/png&tilematrixset=korean&tilematrix=L{z}&tilerow={x}&tilecol={y}&apikey=C5E774C432AFCCCA7B47BC819FBC42DE",
						// url : "http://map.ngii.go.kr/proxys/proxy/proxyTile.jsp?apikey=C5E774C432AFCCCA7B47BC819FBC42DE&URL=http://210.117.198.62:8081/2015_map/korean_map_tile/L{z}/{x}/{y}.png",
						visible : true
					},
                    // satellite: {
                    // 	name: "영상지도",
                    // 	url:  "http://210.117.198.120:8081/o2map/services?service=WMTS&request=GetTile&version=1.0.0&layer=AIRPHOTO&style=_null&format=image/jpg&tilematrixset=NGIS_AIR&tilematrix={z}&tilerow={x}&tilecol={y}",
                    // 	visible: false
                    // },
				}
			},
	}

	// baseMap selectBox settings and evt settings
	$.setBaseMapEvt = function(params, baseMapName) {
		var $baseMap = $("#baseMap");
		var $baseMapLayer = $("#baseMapLayer");

		$baseMap.empty();

		$.each(BaseMapConfig, function(idx, lyr){
			$baseMap.append(new Option(lyr.korName, lyr.name));
		});

		if (baseMapName) $baseMap.val(params.baseMap.name);
		$baseMap.off();
		$baseMap.on("change", function(){
			baseMapName = $(this).val();
			$baseMapLayer.show();
			$('#baseMapLayer').val("base");
			mapMaker.changeBaseMapWithName($(this).val());
			mapMaker.config.initBaseMap = $(this).val();
			$.setBaseMapEvt(mapMaker, params, $(this).val());
		});
		$baseMapLayer.empty();
		$.each(mapMaker.baseMap.layers, function(lyrName, lyr){
			$baseMapLayer.append(new Option(lyr.name, lyrName));
		});
		$baseMapLayer.off();
		$baseMapLayer.on("change", function(){
			var baseMapLayerName = $(this).val();
			mapMaker.mapAction.offBaseMapLayers();
			mapMaker.mapAction.setVisibilityById($baseMap.val() + "_" + baseMapLayerName);

		});
	}




	window.BaseMapConfig = BaseMapConfig;

})(window, jQuery);
