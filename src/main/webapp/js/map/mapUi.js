$(function(e){
	$.setPanel = function() {
		$("div.lnb_panel_cont").each(function(){
			var $self = $(this);

			if ($self.hasClass("totalSearch")) {
				$self.load(G.baseUrl + "search/searchComb.do", "method=Comb");
			}else if($self.hasClass("facilitiesSearch")) {

				var facilTeb = new AjaxComboBox("#facilTeb", {
					jsonData : {
							trafficfacility : "교통안전시설물",
							roadFacility :  "도로안전시설물",
							specialTraffic :"특수교통운영구역",
							basic :"기본도",
							exceptionFacility :"기타"
						},
					baseItem 	: {name: "-- 선택 --", value: ""},
				}).load({select: "trafficfacility"});

                $.waitForLazyRunners(function () {
					$("#facilTeb").trigger("change");
                });


                $("#facilTeb").on("change",function(evt){
					var fcltsJson = { };
					var _self = $(this);
					$.each(mapMaker.mapLayerMng.layers.wfs, function(key, lyr){
						if(lyr.get("mgrnuYn") == "Y") {
							if(_self.val() == lyr.get("kind")) fcltsJson[key] = lyr.get("title");
						}

					});
					var facilities = new AjaxComboBox("#facilities", {
						jsonData : fcltsJson,
						baseItem 	: {name: "-- 선택 --", value: ""}
					}).load({select: "00"});

				});

				$("#facilities").on("change", function(evt) {
					if($("#facilities").val() == "GT_A061_P") {
						$("#choose").show();
						var choose = new AjaxComboBox("#choose", {
							jsonData : {mgrnu : "관리번호",
										mea : "계량기관리번호"},
										baseItem 	: {name: "-- 선택 --", value: ""},
						}).load();
					}else {
						$("#choose").hide();
					}
				});

				$("#choose").on("change", function(evt) {
					$(this).next().attr("placeholder", $("#choose option:selected").text())
				});

				$self.find("#searchFacil").click(function(){
					searchList = new SearchList($self.find(".acc_wrap"), {
						url					: G.baseUrl +"search/searchFacil.json",
						reqData : {
							name : $self.find("#facilities option:selected").text(),
							facil : $self.find('#facilities').val(),
							searchKeyword : $self.find('#searchKeyword').val(),
							choose : $self.find("#choose option:selected").text()
						},
						onRowClick		: function(rowIdx, evt) { // 로우 클릭 이벤트
							 mapMaker.mapAction.setMoveFromFeature({
	                                lyrId: evt.currentTarget.getAttribute("layerName"),
	                                filter: "MGRNU = '" + evt.currentTarget.getAttribute("key")+"'"
	                            });
							 
							 managePanels._modalList.facilitySearch.hide()
						}
					});
				});
			}else if($self.hasClass("regularSearch")) {
				/* 도엽 대분류 */
				var regularL = new AjaxComboBox("#gbn1", {
					url			: G.baseUrl +"search/searchRegularCode.json?gbn=1",
					remoteKey	: "digit",
				});

				/* 도엽 중분류 */
				var regularM = new AjaxComboBox("#gbn2", {
					url			: G.baseUrl +"search/searchRegularCode.json?gbn=2",
					remoteKey	: "digit2",
					baseItem 	: {name: "-- 선택 --", value: "0"}
				});

				/* 도엽 소분류 */
				var regularS = new AjaxComboBox("#gbn3", {
					url			: G.baseUrl +"search/searchRegularCode.json?gbn=3",
//		 			remoteKey	: "umdCode",
					baseItem 	: {name: "-- 선택 --", value: "0"}
				});

				regularM.setChain({parent: regularL, depends: "#gbn1"});
				regularS.setChain({parent: regularM, depends: "#gbn1, #gbn2"});
				regularL.load({select: "00"});

				$self.find("#searchRegular").click(function(){
					$.ajax({
						type 			: 'post',
						url 			: G.baseUrl +"search/searchRegular.json",
						dataType 	: "json",
						data 			: {
							gbn1 : $("#gbn1").val(),
							gbn2 : $("#gbn2").val(),
							gbn3 : $("#gbn3").val()
						},
						cache		: false,
						success : function(json){
							var pos =[json.xce, json.yce];
							var coord = ol.proj.transform(pos, "EPSG:5186", mapMaker.crsCode);
							mapMaker.map.getView().setCenter(coord);
							mapMaker.map.getView().setZoom(json.zoomLvl);
							//도곽레이어 켜기
							var lyr = mapMaker.mapLayerMng.getLayerById("GTBL_REGULAR_INDEX");
							if(!lyr.getVisible()){
								mapMaker.mapAction.setVisibilityById("GTBL_REGULAR_INDEX");
							}
						}
					});
				});
			}else if($self.hasClass("croodSearch")) { //좌표검색
				$self.load(G.baseUrl + "search/searchXy.do", "method=Xy");

			}else if($self.hasClass("layerMng")) {  //레이어 관리
				var $parUl = $self.find("ul.layerList");
				$.each({
					trafficfacility : {
						title : "교통안전시설물",
						mid : {
							trafficfacility : "전체보기"
						}
					},
					roadFacility : {
						title : "도로안전시설물",
						mid : {
							roadFacility : "전체보기"
						}
					},
					specialTraffic : {
						title : "특수교통운영구역",
						mid : {
							specialTraffic : "전체보기"
						}
					},
					basic : {
						title : "기본도",
						mid : {
							basic : "전체보기"
						}
					},
					exceptionFacility : {
						title : "기타",
						mid : {
							exceptionFacility : "전체보기"
						}
					},
				}, function(parKey, parVal){

					var $kindLi = $("<li>");
					$kindLi.append("<a href='#this' class=''>" + parVal.title + "</a>");

					var $kindUl = $("<ul class='checkList accordionMenu_sub'></ul>");
					$kindUl.on("click", function(e){
						e.stopPropagation();
					});

					$.each(parVal.mid, function(midKey, midVal){
						var $li = $("<li>");
						var $div = $("<div class='imgCheckBox'>");

						var $btnPlus = $("<button class='btn btn_expand'>");
						$btnPlus.on("click", function(e){
							e.stopPropagation();

							var _this = $(this);

							if(_this.hasClass("active")) {
								_this.siblings(".subList").slideUp('fast');
								_this.removeClass("active");
							} else {
								_this.siblings(".subList").slideDown('fast');
								_this.addClass("active");
							}
						});

						$div.append($btnPlus);
						$div.append("<input type='checkbox' name='layerList' id='" + midKey + "' />");

						var $parentlbl = $("<label for='" + midKey + "' class='tit'>" + midVal + "</label>");
						$parentlbl.off();
						$parentlbl.on("click", function(){
							var _this = $(this);
							var inputTit = _this.siblings("input");
							var checked = inputTit.is(":checked");

							if(checked) {
								_this.siblings(".subList").find("input").prop("checked", false);
								_this.siblings(".subList").find(".imgCheckBox").removeClass("chk");
								_this.closest(".imgCheckBox").removeClass("chk");
							} else {
								_this.siblings(".subList").find("input").prop("checked", true);
								_this.siblings(".subList").find(".imgCheckBox").addClass("chk");
								_this.closest(".imgCheckBox").addClass("chk");
							}

							mapMaker.mapAction.onOffLayersByKind(!checked, midKey);
						});

						$div.append($parentlbl);

						var $ul = $("<ul class='subList'>");

						$.each(mapMaker.mapLayerMng.layers, function(key, val){
							$.each(val, function(layerName, layer){
								if ((midKey != layer.get("kind")) ) return true;

								$ul.append(
									"<li class='imgCheckBox "+layerName+"'>" +
										"<input type='checkbox' name='" + layerName + "_sub' id='" + layerName + "' />" +
										"<label for='" + layerName + "'>&nbsp</label><span data-target="+layerName+">" + layer.get("title")+"</span>"+
									"</li>"
								);

								$div.append($ul);

                                if (layer.getVisible()) {
                                    $div.addClass("chk");
                                    $div.find("#"+layer.get("kind")).prop("checked", true);
                                    $div.find("#"+layerName).prop("checked", true);
                                    $ul.find("li."+ layerName).addClass("chk");
                                }
							});
						});

						$li.append($div);
						$kindUl.append($li);
					});

					$kindLi.append($kindUl);
					$parUl.append($kindLi);
				});
                var $childChk = $("ul.subList").find("input[type=checkbox]");
                $childChk.off();
                $childChk.on("click", function(){
                    var _this = $(this);
                    var inputLen = $("ul.subList").find("input:checked").length; // checked된 input 갯수

                    if(_this.is(":checked")) {
                        _this.closest(".subList").siblings("input").prop("checked", true);
                        _this.parent(".imgCheckBox").addClass("chk");
                        _this.closest(".subList").closest(".imgCheckBox").addClass("chk");
                    } else {
                        _this.parent(".imgCheckBox").removeClass("chk");
                    }


                    mapMaker.mapAction.setVisibilityById(_this.attr("id"));

                    if(inputLen <= 0) {
                        _this.closest(".subList").siblings("input").prop("checked", false);
                        _this.closest(".subList").closest(".imgCheckBox").removeClass("chk");
                    }
                });

                var $layerChk = $("ul.subList").find('span');
                $layerChk.off();
                $layerChk.on("click", function() {
                	var _this = $(this);
                	var layerEvt = setInterval(function() {
                    	mapMaker.mapAction.setVisibilityById(_this.attr("data-target"));
                    }, 500);
                	var stopEvt = setTimeout(function() {
                		clearInterval(layerEvt);
                	}, 3000);
                })

			}

			$self.find("input[type=text]").on("keyup", function(evt){
				if (evt.keyCode == 13) {
					evt.preventDefault();
					$self.find(".btn.full.blue_tp3").trigger("click");
				}
			});
		});
		
		new fn_accordionMenu();
	}
	
	//지도 아콘디언 메뉴 이벤트 셋팅
	window.fn_accordionMenu = function(el) {
		var _this = this;
		var $accordionMenu = $(".accordionMenu");
		var $et_obj = $accordionMenu.find(">li");
//		var $et_obj = $accordionMenu.children("li");

		this._setup = function(){
			$et_obj.on("click", _this._category.toggle);
		};

		this._category =  {
			flog : false,
			//카테고리 선택 열기
			toggle : function(evt) {
				/*if(_this.category.flog) {
					return flase
				}*/
				// _this.category.flog = true;
				var currentIdx = $et_obj.index(this);

				$et_obj.each(function(i){
					var _this = $(this);
					// console.log(i)
					if(i == currentIdx) {
						if (_this.hasClass("active")) {
							_this.find(".accordionMenu_sub").stop().slideUp('fast');
							_this.removeClass("active");
						} else {
							_this.find(".accordionMenu_sub").stop().slideDown('fast');
							_this.addClass("active");
						}
					}
				});
			}
		}
		this._setup();
		return this;
	}

}());

(function($, undefined) {
	// ------------- 객체정보 모달 생성 모듈 start ------------- //
	var infoModal = function(options){
		var that = this;
		var opt = options || {};
		this.target = opt.target || 'body';
		
		this.modalId = '';
		this._closeFunc = undefined;
		
		this._createInfoModal(this.target);
	}
	
	infoModal.prototype = {
		// modal div ID 중복 체크
		_getIndexId: function(id, index){
			var i = index || 1;
			if($('#' + id + i).length){
				return this._getIndexId(id, ++i);
			} else {
				return id + i;
			}
		},
		_createInfoModal: function(target){
			var that = this;
			var target = $(target);
			var html = '';
			
			this.modalId = this._getIndexId('detail-modal');
			
			html += '<ons-modal id="' + this.modalId + '" direction="up">';
			html += '<ons-page>';
			html += '<ons-toolbar>';
			html += '<div class="center">상세내용</div>';
			html += '<div class="right">';
			html += '<ons-toolbar-button class="close-btn">닫기</ons-toolbar-button>';
			html += '</div>';
			html += '</ons-toolbar>';
			html += '<div class="ui container lp_wrap modal-content"></div>';
			html += '<div id="modal-controller-1" style="display: none;"><div class="modal_close"></div></div>';
			html += '</ons-page>';
			html += '</ons-modal>';
		
			target.append(html);
			
			// 기존 web 코드와의 연동을 위한 이벤트 바인딩 (정보보기창 닫기)
			$('#' + this.modalId).find('#modal-controller-1 .modal_close').click(function(){
				document.querySelector('#' + that.modalId).hide({
					callback: that.close,
					instance: that
				});
			})
			
			// 모바일 코드에서의 정보보기창 닫기 이벤트 바인딩 (정보보기창 닫기)
			$('#' + this.modalId).find('.close-btn').on('click', function(){
				document.querySelector('#' + that.modalId).hide({
					callback: that.close,
					instance: that
				});
			});
			
//			$("#modal-controller-1").find(".modal_close").trigger("click");
			
			document.querySelector('#' + this.modalId).setAttribute('animation', 'lift'); // modal show&hide animation
		},
		open: function(url, params){
			var that = this;
			this._closeFunc = params.closeFunc;
			
			$('#' + this.modalId + ' .modal-content').load(url, params.reqData, function(){
				var $self = $(this);
				
				if ($self.find("input.datapicker_open").length > 0) $self.find("input.datapicker_open").datepicker();
				
				if (params.reqData.method == "select") {
					$self.find(".btn_wrap.tp2 > a").on("click", function(){//수정및 조회 버튼
						var _this = $(this);
						var $tabList = $(".btn_wrap.tp2 >a");
						var _thisIdx = _this.index();
//						bor_blue_tp1
						$tabList.removeClass("blue");
						$tabList.addClass("bor_blue_tp1");
						_this.removeClass("bor_blue_tp1");
						_this.addClass("blue");

						$self.find(".tabCont").removeClass("active");
						$self.find(".tabCont").eq(_thisIdx).addClass("active");

						params.setModeChange($(this).attr("id")); // 조회 및 수정 탭 변경 시
					});
				}

				if(!$(".facilitySelect").is$Empty()){  // 키값 가져오기 위한 이벤트 선언 - 한봄 -
					$(".facilitySelect").on("click", function(evt){
						mapMaker.mapAction.layer.properties.isFeatureCreate = false;
						mapMaker.mapAction.layer.properties.isFeatureSelect = false;
						mapMaker.mapAction.layer.properties.isTargetSelect = true;
						mapMaker.mapAction.layer.select = $.extend({}, mapMaker.mapAction.layer.select, {
							lyrId : $(this).attr("data-lyrId"),
							inputId : $(this).attr("data-inputId"),
							inputAttr : $(this).attr("data-inputAttr"),
							subId : $(this).attr("data-subId"),
							subAttr : $(this).attr("data-subAttr")
						});
					});
				}
				
				document.querySelector('#' + that.modalId).show();
			});
		},
		close: function(){
			if(this.instance._closeFunc instanceof Function){
				this.instance._closeFunc();
			}
		}
	}
	window.InfoModal = infoModal;
	// ------------- 객체정보 모달 생성 모듈 end ------------- //
	
	var managePanels = function(options){
		var that = this;
		
		var defaults = {
				layerMng: {
				title: '레이어관리',
				cloneTarget: '.layerMng',
				icon: 'fa-layer-group'
			},
			totalSearch: {
				title: '통합검색',
				cloneTarget: '.totalSearch',
				icon: 'fa-search',
				group: 'search'
			},
			facilitySearch:{
				title: '시설물검색',
				cloneTarget: '.facilitiesSearch',
				icon: 'fa-search',
				group: 'search'
			},
			coordSearch:{
				title: '좌표검색',
				cloneTarget: '.croodSearch',
				icon: 'fa-search',
				group: 'search'
			}
		}
		
		var opt = options || {};
		this.target = opt.target || 'body';
		this.manages = opt.manages || ['layerMng', 'totalSearch', 'facilitySearch', 'coordSearch'];
		this.map = opt.map || undefined;
		
		this._dial = this._createDial(this.target);
		this._modalList = {};
		this._groupList = {};
		
		for(var i in this.manages){
			this._modalList[this.manages[i]] = this._createModal(defaults[this.manages[i]])[0];
		}
		
		$('.clone-list').remove();
		$.setPanel();
	}
	
	managePanels.prototype = {
		_createDial: function(target){
			var that = this;
			var trg = target || 'ons-page';
			
			var dial = $('<ons-speed-dial position="bottom right" direction="up">');
			var html = '';
			
			html += '<ons-fab>';
			html += '<ons-icon icon="fa-ellipsis-v"></ons-icon>';
			html += '</ons-fab>';
			
			dial.append(html);
			$(trg).append(dial);
			return dial;
		},
		_createModal: function(options){
			var that = this;
			var opt = options || {}
			var target = 'body';
			var title = opt.title || '제목';
			var cloneTarget = opt.cloneTarget || undefined;
			var icon = opt.icon || 'fa-search';
			var group = opt.group || undefined;
			var closeCommand = "$(this).closest('ons-modal')[0].hide();";
			var html = '';
			
			html += '<ons-page>';
			html += '<ons-toolbar>';
			html += '<div class="center">' + title + '</div>';
			html += '<div class="right">';
			html += '<ons-toolbar-button class="close-btn" onclick="' + closeCommand + '">닫기</ons-toolbar-button>';
			html += '</div>';
			html += '</ons-toolbar>';
			html += '<div class="ui container modal-content">';
			html += '</div>'
			html += '</ons-page>';
			
			var modal = $('<ons-modal direction="up">').append(html);
			$(target).append(modal);
			
			if(cloneTarget){
				$(cloneTarget).appendTo(modal.find('.modal-content'));
			}
			
			if(!group){
				this._addDialItem(icon, function(){
					modal[0].show();
				});
			} else {
				if(!this._groupList[group]){
					this._groupList[group] = [];
					
					this._addDialItem(icon, function(){
						var copy = that._groupList[group].slice();
						copy.push({
							label: '취소',
							icon: 'md-close'
						})
						
						ons.openActionSheet({
							cancelable: true,
							buttons: copy,
							callback: function(index){
								that._groupList[group][index].modal.show();
							}
						})
					});
				}
				
				this._groupList[group].push({
					label: title,
					icon: 'md-square-o',
					modal: modal[0]
				});
			}
			
			modal[0].setAttribute('animation', 'lift');
			return modal;
		},
		_addDialItem: function(icon, clickFunc){
			var icon = $('<ons-icon icon="' + icon + '">');
			var item = $('<ons-speed-dial-item>').append(icon);
			var callback = clickFunc;
			this._dial.append(item);
			
			item.on('click', function(e){
				if(callback instanceof Function){
					callback(e);
				}
			});
		},
		_activeOrientation: {
			// 모바일 기기 위치 추적 기능 활성화
			this.orientation = new Orientation({
				map: this.map
			})
		}
	}
	
	window.ManagePanels = managePanels;
})(jQuery);