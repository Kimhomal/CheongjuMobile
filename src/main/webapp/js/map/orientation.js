(function(ol, ons){
	'use strict';
	
	var statics = {
		vectorLayer: undefined, // 기기 방향 감지 아이콘 레이어
		orientation: false, // 기기 방향 감지 기능 활성화 여부
		arrowRotate: 0, // 기기 방향 각도
		geolocation: undefined, // geolocation watchPosition event 반환 id
		position: undefined, // 현재 위치 좌표
		coordinatePopup: false, // 현재 위치 popup 기능 활성화 여부
		popupCallback: undefined, // moveend 이벤트 발생 시 실행할 popup 함수
		map: undefined
	} 
	
	var orientation = function(options){
		var that = this;
		var opt = options || {};
		
		this.map_ = statics.map = opt.map;
		if(!(this.map_ instanceof ol.Map)){
			console.error("gb.interaction.MeasureTip: 'map' is a required field.");
			return;
		}
		
		// 현재 위치
		this.position_ = undefined;
		
		var iconStyle = new ol.style.Style({
			image: new ol.style.Icon({
				anchor: [0.5, 46],
				anchorXUnits: 'fraction',
				anchorYUnits: 'pixels',
				src: (G.ctxPath || '') + '/images/pin.png'
			})
		});
		
		// 현재 위치 아이콘
		this.iconFeature_ = new ol.Feature(new ol.geom.Point([0, 0]));
		this.iconFeature_.set('style', iconStyle);
		
		/**
		 * 임시 vector source
		 * @type {ol.source.Vector}
		 * @private
		 */
		this.cameraSource_ = new ol.source.Vector({
			features: [this.iconFeature_]
		});
		
		/**
		 * 임시 vector layer
		 * @type {ol.layer.Vector}
		 * @private
		 */
		this.cameraVector_ = new ol.layer.Vector({
			source : this.cameraSource_,
			style: function(feature){
				return feature.get('style');
			}
		});
		
		
		this.popupId_ = 'mapPopup';
		
		/**
		 * 현재 좌표 popup
		 * @type {ol.Overlay}
		 * @private
		 */
		this.popup_ = new ol.Overlay({
			element: document.getElementById(this.popupId_),
			positioning: 'bottom-center',
			stopEvent: false,
			offset: [0, -50]
		});
		this.map_.addOverlay(this.popup_);
		
		var strokes = {
			'line' : new ol.style.Stroke({
				color : 'rgba(152,152,152,0.6)',
				width : 3,
				lineDash : [ 1, 4 ]
			}),
			'default' : new ol.style.Stroke({
				color : 'rgba(152,152,152,1.0)',
				width : 2
			})
		};

		var fill = new ol.style.Fill({
			color : 'rgba(255,0,0,1.0)'
		});

		var styles = {
			'circle' : new ol.style.Style({
				// stroke: strokes['circle'],
				image : new ol.style.Circle({
					fill : fill,
					radius : 10,
					stroke : strokes['default']
				})
			}),
			'triangle' : new ol.style.Style({
				image : new ol.style.RegularShape({
					stroke : strokes['default'],
					points : 3,
					radius : 8,
					rotation: statics.arrowRotate*Math.PI/180
				})
			}),
			'icon': new ol.style.Style({
				image: new ol.style.Icon({
//						anchor: [0.5, 0.96],
//						crossOrigin: 'anonymous',
					src: G.ctxPath + '/images/position_icon30p.png',
//						image: undefined,
//						imgSize: undefined
				})
			})
		};
			
		var locationFeature = new ol.Feature(new ol.geom.Point(this.map_.getView().getCenter()));
		locationFeature.set('style', styles.icon);
		
		statics.vectorLayer = new ol.layer.Vector({
			style: function(feature){
				return feature.get('style');
			},
			source: new ol.source.Vector({features: [locationFeature]}),
			visible: false
		});
		
		this.map_.addLayer(statics.vectorLayer);
		
		// Map 이동 시 watchPosition event 해제
		this.map_.on('pointerdrag', function(event){
			if(navigator.geolocation){
				navigator.geolocation.clearWatch(statics.geolocation);
			}
		});
	}
	
	orientation.prototype.setPositionMarker_ = function(position){
		if(!(position instanceof Array)){
			return;
		}
		
		if(position.length != 2){
			return;
		}
		
		this.position_ = position;
		this.map_.getView().setCenter(position);
		
		var features = this.cameraSource_.getFeatures();
		for(var i in features){
			features[i].getGeometry().setCoordinates(position);
		}
		
		document.getElementById('mapPopup-content').innerHTML = 
			'<table class="ui very basic unstackable table"><tbody>' + 
			'<tr><td class="label">x</td><td class="value">' + position[0].toFixed(3) + '</td></tr>' + 
			'<tr><td class="label">y</td><td class="value">' + position[1].toFixed(3) + '</td></tr>' + 
			'</tbody></table>';
		this.popup_.setPosition(position);
	}
	
	orientation.prototype.coordinatePopup = function(evt){
		var that = this;
		
		if(!statics.coordinatePopup){
			statics.popupCallback = function(evt){
				var center = that.map_.getView().getCenter();
				
				that.setPositionMarker_(center);
			}
			
			this.cameraVector_.setMap(this.map_);
			evt.currentTarget.className = evt.currentTarget.className + ' active'; // 버튼 스타일 변경(활성화)
			
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position, e) {
					var center = ol.proj.transform([position.coords.longitude, position.coords.latitude], 'EPSG:4326', that.map_.getView().getProjection().getCode());
					
					that.setPositionMarker_(center);
					
				}, onErrorGeolocation);
			} else {
				ons.notification.alert('현재 위치가 제공되지않는 기기(또는 브라우저)입니다.');
			}
			
			this.map_.on('moveend', statics.popupCallback);
			
			statics.coordinatePopup = true;
		} else {
			
			this.cameraVector_.setMap(null);
			evt.currentTarget.className = evt.currentTarget.className.replace(/\s*active/g, ''); // 버튼 스타일 변경(비활성화)
			this.popup_.setPosition(undefined);
			
			this.map_.un('moveend', statics.popupCallback);
			statics.coordinatePopup = false;
		}
	}
	
	// 기기 위치,방향 추적 기능 toggle 함수
	orientation.prototype.toggleOrientation = function(evt){
		var bool = statics.orientation;
		
		if(!bool){
			if(statics.coordinatePopup){
				this.coordinatePopup(evt);
			}
			
			this.requestOrientationPermission(); // 기기 방향 감지 이벤트 생성
			evt.currentTarget.className = evt.currentTarget.className + ' active'; // 버튼 스타일 변경(활성화)
		} else {
			// iOS 외 기기들 orientation event 해제
			window.removeEventListener('deviceorientationabsolute', orientationHandler_, true);
			
			// iOS 기기 orientation event 해제
			window.removeEventListener('deviceorientation', iOSorientationHandler_, true);
			
			statics.vectorLayer.setVisible(false);
			this.map_.render(); // 렌더링 초기화 - 맵 중앙 기기 방향 아이콘 제거 목적
			
			statics.orientation = false;
			
			evt.currentTarget.className = evt.currentTarget.className.replace(/\s*active/g, ''); // 버튼 스타일 변경(비활성화)
			
			if(navigator.geolocation){
				// watchPosition event 비활성
				navigator.geolocation.clearWatch(statics.geolocation);
			}
		}
	}
	
	orientation.prototype.requestOrientationPermission = function(){
		if(!window.DeviceOrientationEvent){
			ons.notification.alert('기기 방향 감지 기능이 제공되지않는 기기(또는 브라우저)입니다.');
			return;
		}
		
		if (typeof DeviceOrientationEvent.requestPermission === 'function') {
			DeviceOrientationEvent.requestPermission()
				.then(permissionState => {
					if (permissionState === 'granted') {
						var orientationEvent = window.addEventListener("deviceorientation", iOSorientationHandler_, true);
						statics.vectorLayer.setVisible(true);
					}
				})
				.catch(console.error);
		} else {
			// handle regular non iOS 13+ devices
			var orientationEvent = window.addEventListener("deviceorientationabsolute", orientationHandler_, true);
			window.addEventListener('MozOrientation', orientationHandler_, true);
			statics.vectorLayer.setVisible(true);
		}
		
		if(navigator.geolocation){
			// 사용자 기기 위치 추적 시작
			statics.geolocation = navigator.geolocation.watchPosition(onSuccessGeolocation_, onErrorGeolocation);
		}
		
		statics.orientation = true;
	}
	
	orientation.prototype.requestPositionPermission = function(){
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(onSuccessGeolocation_, onErrorGeolocation);
		} else {
			ons.notification.alert('현재 위치가 제공되지않는 기기(또는 브라우저)입니다.');
		}
	}
	
	// 현재 위치, 방향 아이콘 생성 함수
	function drawMbr_(){
		var mapObj = statics.map;
		var view = mapObj.getView();
		var center = ol.proj.transform([statics.position.coords.longitude, statics.position.coords.latitude], 'EPSG:4326', statics.map.getView().getProjection().getCode());
		var pixel = mapObj.getPixelFromCoordinate(center);
		var distance = 14;
		
		var rotate = statics.arrowRotate - 90;
		
		var triangleX = pixel[0] + distance * Math.cos(rotate*Math.PI/180);
		var triangleY = pixel[1] + distance * Math.sin(rotate*Math.PI/180);
		var trianglePixel = [triangleX, triangleY];
		var triangleCoord = mapObj.getCoordinateFromPixel(trianglePixel);
		
		var features;
		if(statics.vectorLayer instanceof ol.layer.Vector){
			features = statics.vectorLayer.getSource().getFeatures();
			for(var i in features){
				if(i == 0){
					features[i].getGeometry().setCoordinates(center);
					if(features[i].get('style') instanceof ol.style.Style){
						features[i].get('style').getImage().setRotation(statics.arrowRotate*Math.PI/180);
					}
				} else if(i == 1){
					features[i].getGeometry().setCoordinates(triangleCoord);
					if(features[i].get('style') instanceof ol.style.Style){
						features[i].get('style').getImage().setRotation(statics.arrowRotate*Math.PI/180);
					}
				}
			}
		}
	}
	
	// 기기 방향 감지 이벤트 발생 시 실행되는 함수
	function orientationHandler_(event){
		var absolute = event.absolute;
		var alpha = event.alpha;
		var beta = event.beta;
		var gamma = event.gamma;
		
		var rad;
		if(absolute){
			// 기기 방향 이벤트 반환값에 따라 지도를 회전시킴
//			rad = degToRad(alpha);
//			this.map_.getView().setRotation(rad);
			
			// 기기 방향 이벤트 함수가 반환하는 각도값은 시게방향 기준으로 각도가 증가하며 openlayers는 반시계 방향 기준이므로
			// 360 값에서 이벤트 값을 빼서 방향을 맞춤
			statics.arrowRotate = 360 - alpha;
			
			drawMbr_();
			
			statics.map.render();
		}
	}
	
	function iOSorientationHandler_(event){
		var alpha		= 360 - event.webkitCompassHeading;
		var beta		 = event.beta;
		var gamma		= event.gamma;
		
//		var rad = degToRad(alpha);
//		this.map_.getView().setRotation(rad);
		
		statics.arrowRotate = 360 - alpha;
		
		drawMbr_();
		
		statics.map.render();
	}
	
	// 기기 위치 감지 시 실행되는 함수
	function onSuccessGeolocation_(position) {
		var projection = statics.map.getView().getProjection().getCode();
		var zoom = statics.map.getView().getZoom();
		var center = ol.proj.transform([position.coords.longitude, position.coords.latitude], 'EPSG:4326', projection);
		
		statics.map.getView().setCenter(center);
		statics.map.getView().setZoom(statics.map.getView().getZoom());
        
		statics.position = position;
		
		if(statics.orientation){
			drawMbr_();
		}
	}
	
	// 기기 위치 감지 에러 발생 시 실행되는 함수
	function onErrorGeolocation(e) {
		alert(`ERROR(${e.code}): ${e.message}`);
	}
	
	// Degree를 radian 값으로 변환(기기 방향 alpha값을 openlayers에 사용하기 위함)
	function degToRad(deg) {
		var result = (deg * Math.PI / 180);
		return result;
	}
	
	window.Orientation = orientation;
}(ol, ons));