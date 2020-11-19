(function(window, $) {
	"use strict";

	var SearchList = function(elem, options) {
		this.$elem = $(elem);
		this.options = options;
		this._init();
	};

	SearchList.prototype = {
		config : null,

		_defaults : {
			url					: "",
			type				: "post",
			reqData			: {},
			cache			: false,
			dataType		: "json",
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			async			: true,
			colModel		: [],
			colNames		: [],
			pagingYn		: true,
			tpVer			: "1",
			mouseCursor		: "pointer",
			onCellClick		: function(rowIdx, colIdx, cellContent, evt) {
			},
			onRowClick	: function(rowIdx, evt) {
			},
			loadComplete	: function(data) {
			},
		},

		_pageConfig : {
			rows : "10",
			page : "1",
		},

		_init : function () {
			this.config = $.extend({}, this._defaults, this.options);
			this.config.reqData = $.extend({}, this._pageConfig, this.config.reqData);
			this.$elem.children("div.acc_content").empty();
			this.$elem.children("div.acc_headar").empty();

			this.$elem.children("div.acc_headar").append(
					"<p class='tit'>"+this.config.reqData.name +"검색결과</p>"+
					"<p class='cnt'></p>"
			);
//			this._setTableStrucObj();
//			this._setHeader();
			this.loadList();
		},

		_jsonData : null,

		loadList : function () {
			var _self = this;
			$.ajax({
				type			: _self.config.type,
				url				: _self.config.url,
				dataType	: _self.config.dataType,
				contentType:	 _self.config.contentType,
				data			: _self.config.reqData,
				cache		: _self.config.cache,
				async		: _self.config.async,
				beforeSend	: function(){
					$.showBlock();
				},
				success		: function(jsonView){
					_self._jsonData = jsonView;
					_self._setList();
					_self.config.loadComplete(_self._jsonData.root);
				},
				complete	: function(){
					$.hideBlock();
				},
			});
		},

		reloadList : function (params) {
			if (!params) params = {};
			this.config.reqData = $.extend({}, this._pageConfig, params);
			this.loadList();
		},

		_setList : function () {
			var _self = this;
			this.$elem.children("div.acc_content").empty();
			this.$elem.children("div.acc_content").append(
					"<ul class='list_tp5'></ul>"
			);
			this.$elem.children("div.acc_content").append(
					"<div class='paging'></div>"
			);
			var $ul = _self.$elem.find("ul");
			$ul.empty();
			if(_self._jsonData.root.length != 0) {
				$.each(_self._jsonData.root, function(rowIdx, rowData){
//					var key = _self._jsonData.params.key.toLowerCase();

					var $row
					if("searchFacil"==_self._jsonData.pageName){
						$row = $("<li layerName='"+_self._jsonData.params.facil+"' key='"+rowData["mgrnu"]+"'>"+rowData["mgrnu"]+"</li>");

						}
					else if("searchXy"==_self._jsonData.pageName){

						$row = $("<li posY='"+rowData["miny"]+"' posX='"+rowData["minx"]+"'>"+rowData["guName"] + " " + rowData["dongName"]+"</li>");

						}
					else if("searchRoadwork"==_self._jsonData.pageName){
						_self.$elem.find("ul").removeClass();
						_self.$elem.find("ul").addClass("list_tp6");
						_self.$elem.find("ul").addClass("li_lh_tp1");
						$ul = _self.$elem.find("ul.list_tp6.li_lh_tp1");
						var text = "<span>"+rowData["cstNam"]+"</span>"
						text += "<a href='#' id='schedule' class='btn_sml bg_blue ab_r_t'>공정표</a>";
						text += "<a href='#' id='summary' class='btn_sml bg_dGray ab_r_b'>요약서</a>";
						$row = $("<li layerName='TBL_SUMMARY_LOC,LINK_SMR_NO' key='"+rowData["smrNo"]+"'cstNo='"+rowData["cstNo"]+"'>"+text+"</li>");

						}	else if("roadFacil"==_self._jsonData.pageName){
							_self.$elem.find("ul").removeClass();
							_self.$elem.find("ul").addClass("list_tp6");
							$ul = _self.$elem.find("ul.list_tp6");
							var text = "<span>"+rowData["mgrnu"]+"         ||          "+rowData["facilNm"]+"</span>"
							$row = $("<li layerName='"+rowData["layerNm"]+",MGRNU' key='"+rowData["mgrnu"]+"'>"+text+"</li>");

							}
					else {

						_self.$elem.find("ul").removeClass();
						_self.$elem.find("ul").addClass("list_tp4");
						$ul = _self.$elem.find("ul.list_tp4");
						var text = "<span>"+rowData["keyword"]+"</span>"
//						var text = "<span>"+rowData["cstNam"]+"</span>"
						if("searchCross"==_self._jsonData.pageName){
							$row = $("<li layerName='GT_A008_A' key='"+rowData["mgrnu"]+"'>"+text+"</li>");
						}else if("searchBuild"==_self._jsonData.pageName){
							$row = $("<li layerName='TL_SPBD_BULD,BUL_MAN_NO,SIG_CD' key='"+rowData["bulManNo"]+","+rowData["sigCd"]+"'>"+text+"</li>");
						}else if("searchDaumAddr"==_self._jsonData.pageName){
							$row = $("<li x='"+rowData["x"]+"' y='" + rowData["y"] + "'></li>");

							var $bldName = $("<p class = 'jibunAddr'><strong style='font-size: 13px;'>" + rowData["address"] + "</strong></p>");
							var $bldAddr = $("<p class = 'roadAddr'>" + rowData["road_address"] + "</p>");

							$row.append($bldName);
							$row.append($bldAddr);

						}else if("searchKeyword"==_self._jsonData.pageName){
							$row = $("<li x='"+rowData["x"]+"' y='" + rowData["y"] + "'></li>");

							var $bldName = $("<p><strong style='font-size: 13px;'>" + rowData["placeName"] + "</strong></p>");
							var $bldAddr = $("<p>" + rowData["name"] + "</p>");

							$row.append($bldName);
							$row.append($bldAddr);

						}else{
							$row = $("<li posY='"+rowData["yce"]+"' posX='"+rowData["xce"]+"'>"+text+"</li>");
						}
						}

					$row.css("cursor", _self.config.mouseCursor);

//					$row.on({
//						"mouseenter" : function() {
//							$(this).css("background", "#F6F6F6");
//						},
//						"mouseleave" : function() {
//							$(this).css("background", "");
//						},
//					});

					$row.off("click");
					$row.on({
						"click" : function(evt) {
							_self.config.onRowClick(rowIdx,evt);
						},
					});

					$ul.append($row);
				});

			} else {
				var $row = $("<li>"+"데이터가 존재하지 않습니다."+"</li>");
				$ul.append($row);
//				var $row = $("<tr>");
//				var $cell = $("<td colspan='"+ _self.config.colModel.length +"'>")
//				$cell.html("데이터가 존재하지 않습니다.")
//				$row.append($cell);
//				$tbody.append($row);
			}

			this._setPaging();
		},

		_pageCnt : 10,		//한 화면에 보여줄 페이지 갯수(1 2 3 4 5 6 7 8 9 10)

		_setPaging : function(){
			var $page = this.$elem.find("div.paging");

			var $p = this.$elem.find("p.cnt");
			$p.empty();

			// 전체 리스트 수
			var totalSize = this._jsonData.records;
			$p.append(
					totalSize + " 건"
				);

			// 현재 페이지
			var currentPage = this._jsonData.page;

			// 데이터 전체의 페이지 수
			var totalPage = this._jsonData.total;

			if(totalSize % this.config.reqData.rows == 0) totalPage -= 1;

			// 전체 페이지 수를 한화면에 보여줄 페이지로 나눈다.
			var totalPageList = Math.ceil(totalPage / this._pageCnt);

			// 페이지 리스트가 몇번째 리스트인지
			var pageList = Math.ceil(currentPage / this._pageCnt);

			// 페이지 정보 셋팅
			var pageInfoText = ""; // 페이지 정보를 담을 변수

			//////////////////////////////////////////////////////////////////////////////////////////
			var base = parseInt(this._jsonData.page, 10) - 1;

			if(base < 0) base = 0;

			base = base * parseInt(this.config.rows, 10);

			var from = base + 1;
			var to = base + this.config.rows;
			//////////////////////////////////////////////////////////////////////////////////////////

			// 페이지 리스트가 1보다 작으면 1로 초기화
			if(pageList < 1) pageList = 1;

			// 페이지 리스트가 총 페이지 리스트보다 커지면 총 페이지 리스트로 설정
			if(pageList > totalPageList) pageList = totalPageList;

			// 시작 페이지
			var startPageList = ((pageList - 1) * this._pageCnt) + 1;

			// 끝 페이지
			var endPageList = startPageList + this._pageCnt - 1;

			// 시작 페이지와 끝페이지가 1보다 작으면 1로 설정
			// 끝 페이지가 마지막 페이지보다 클 경우 마지막 페이지값으로 설정
			if(startPageList < 1) startPageList = 1;
			if(endPageList > totalPage) endPageList = totalPage;
			if(endPageList < 1) endPageList = 1;

			// 페이징 DIV에 넣어줄 태그 생성변수
			var pageInner = "";

//			pageInner += "<ul class='boardNav'>";
			// 페이지 리스트가 1이나 데이터가 없을 경우 (링크 빼고 흐린 이미지로 변경)
			if(pageList < 2){
				pageInner += "<a href='#' class='btn_first disabled'></a>\n"; //<<
				pageInner += "<a href='#' class='btn_prev disabled'></a>\n"; //<
			} else { // 이전 페이지 리스트가 있을 경우 (링크넣고 뚜렷한 이미지로 변경)
				var titleFirstPage = "첫 페이지로 이동";
				var titlePrePage = (startPageList - 10) + "페이지에서 " + (endPageList - 10) + "페이지까지 이동";

				pageInner += "<a class='btn_first' href='#' title='" + titleFirstPage + "'></a>\n";
				pageInner += "<a class='btn_prev' href='#' title='" + titlePrePage + "'></a>\n";
			}
//			pageInner += "</ul>";

//			pageInner += "<ul class='boardPage'>";
			// 페이지 숫자를 찍으며 태그생성 (현재페이지는 강조태그)
			for(var i = startPageList; i <= endPageList; i++){
				var titleGoPage = i + "페이지로 이동";

				if(i == currentPage){
					pageInner += "<a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a>\n";
				}else{
					pageInner += "<a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a>\n";
				}
			}
//			pageInner += "</ul>";

//			pageInner += "<ul class='boardNav'>";
			// 다음 페이지 리스트가 있을 경우
			if(totalPageList > pageList){
				var titleNextPage = (startPageList + 10) + "페이지에서 " + (endPageList + 10) + "페이지까지 이동";
				var titleLastPage = "마지막 페이지로 이동";

				pageInner += "<a class='btn_next' href='#' title='" + titleNextPage + "'></a>\n"; //>
				pageInner += "<a class='btn_last' href='#' title='" + titleLastPage + "'></a>\n"; //>>
			}

			// 현재 페이지리스트가 마지막 페이지 리스트일 경우
			if(totalPageList == pageList){
				pageInner += "<a href='#' class='btn_next disabled'></a>\n"; //>
				pageInner += "<a href='#' class='btn_last disabled'></a>\n"; //>>
			}
//			pageInner += "</ul>";

			var html = "";
//			html += "<div class='board_pager'>";
			html += pageInner;
//			html += "</div>"

			$page.empty();
			$page.append(html);

			this._setPagingEvt($page);
		},

		_setPagingEvt : function($page) {
			var self = this;

			$page.find("a").on("click", function(evt){
				evt.preventDefault();

				var className = $(this).attr("class");

				if(className != "" && className != null) {
					if(className.indexOf("disabled") != -1){
						return true;
					}
				}

				switch (className) {
					case "btn_first" :
						self._firstPage();
						break;
					case "btn_prev" :
						self._prePage();
						break;
					case "btn_next" :
						self._nextPage();
						break;
					case "btn_last" :
						self._lastPage();
						break;
					default :
						self._goPage($(this).attr("id"));
						break;
				}
			});
		},

		// 첫페이지로 이동
		_firstPage : function(){
			this.config.reqData.page = "1";
			this.loadList();
		},

		// 이전페이지 이동
		_prePage : function(){
			var currentPage = this._jsonData.page;

			currentPage -= this._pageCnt;
			var pageList = Math.ceil(currentPage / this._pageCnt);
			currentPage = (pageList - 1) * this._pageCnt + this._pageCnt;

			this.config.reqData.page = currentPage;
			this.loadList();
		},

		// 다음페이지 이동
		_nextPage : function(){
			var currentPage = this._jsonData.page;

			currentPage += this._pageCnt;
			var pageList = Math.ceil(currentPage / this._pageCnt);
			currentPage = (pageList - 1) * this._pageCnt + 1;

			this.config.reqData.page = currentPage;
			this.loadList();
		},

		// 마지막페이지 이동
		_lastPage : function(){
			var totalPage = this._jsonData.total;
			if(this._jsonData.records % this.config.reqData.rows == 0) totalPage -= 1;
			this.config.reqData.page = totalPage;
			this.loadList();
		},

		// 페이지 번호로 이동
		_goPage : function(num){
			this.config.reqData.page = num;
			this.loadList();
		},

		getData : function(rowIdx, key) {
			return this._jsonData.root[rowIdx][key];
		},

		pageMove : function(url, data) {
			var $form = $("<form>");

			$.each(data, function(key, val){
				var inputHtml = "<input type='hidden' id='" + key + "' name='" + key + "' value='" + val + "'/>";
				$form.append(inputHtml);
			});

			this.$elem.append($form);
			$form.attr({action : url, method : "post"}).submit();
			$form.remove();
		},
	}

	window.SearchList = SearchList;



})(window, jQuery);