(function(window, $) {
	"use strict";

	var SearchResultList = function(elem, options) {
		this.$elem = $(elem);
		this.options = options;
		this._init();
	};

	SearchResultList.prototype = {
		config : null,

		_defaults : {
			url					: "",
			type				: "post",
			reqData			: {},
			cache			: false,
			dataType		: "json",
			async			: true,
			colModel		: [],
			colNames		: [],
			pagingYn		: true,
			pageCnt		: 10,
			tableClass 	: "tbl_col_tp1",
			boardClass	: "div.board_tp1",
			mouseCursor: "pointer",
			mouseHover	: true,
			emptyMessage : "데이터가 존재하지 않습니다.",
			tableYN			: "Y",
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

			if(this.config.tableYN == "Y"){
				this._setTableStrucObj();
				this._setHeader();
			}

			this.loadList();
		},

		_setTableStrucObj : function () {
			this.$elem.children(this.config.boardClass).append(
				"<table class='"+this.config.tableClass+"'>" +
				"<caption>"+this.config.caption+"</caption>"+
					"<colgroup>" +
					"</colgroup>" +
					"<thead>" +
						"<tr>" +
						"</tr>" +
					"</thead>" +
					"<tbody>" +
					"</tbody>" +
				"</table>"
			);
		},

		_setHeader : function () {
			var colGroupHtml = "";
			var theadHtml = "";

			$.each(this.config.colNames, function(idx, col){
				if(col.name =="checkBox"){//해더값에 checkbox 넣고 싶을때
					theadHtml += "<th scope='col'><input type='checkbox' id=chk_parent name=chk_parent></th>";
					colGroupHtml += "<col width='" + col.width + "' />";
				}else{
				theadHtml += "<th scope='col'>" + col.name + "</th>";
				colGroupHtml += "<col width='" + col.width + "' />";
				}
			});

			this.$elem.find("table").children("colgroup").append(colGroupHtml);
			this.$elem.find("table").children("thead").children("tr").append(theadHtml);
		},

		_jsonData : null,

		loadList : function () {
			var _self = this;
			$.ajax({
				type			: _self.config.type,
				url				: _self.config.url,
				dataType	: _self.config.dataType,
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
			if(params.hasOwnProperty("url")){
				this.config.url = params.url ;
			}
			this.config.reqData = $.extend({}, this._pageConfig, params);
			this.loadList();
		},

		_setList : function () {
			var _self = this;

			if(_self.config.tableYN == "Y"){
				var $tbody = _self.$elem.find("table>tbody");
				$tbody.empty();

				if(_self._jsonData.root.length != 0) {
					$.each(_self._jsonData.root, function(rowIdx, rowData){
						if(_self.config.colModel[0][0] != undefined){
							$.each(_self.config.colModel, function(colIdx, colData){
								var $row = $("<tr>");
								$.each(this, function(i, value){
									var $cell;
									var strSpan = value.rowspan != undefined ? "rowspan = " + value.rowspan :"";
									if (value.formatter) {
										var colValue ;
										// 체크박스
										if(value.formatter == "checkBox"){
											var id = "chk" + rowData["num"];
											colValue = '<input type="checkbox" id="' + id + '" name="chk">';
										}else if(value.formatter == "rowNum"){
											colValue = rowIdx + 1
										}else {
											colValue = value.formatter(rowData[value.name], rowData, ((_self._jsonData.records - rowIdx) - _self._jsonData.page * _self.config.pageCnt +_self.config.pageCnt) ,$row);
										}
											$cell = $("<td "+strSpan+">").append(colValue);
									} else {
											var $cell = $("<td "+strSpan+">" + rowData[value.name] + "</td>");
									}
									$cell.off("click");
									//체크박스 외의 곳 눌렀을때만 cellClick 적용
									$cell.on("click", function(evt){
										_self.config.onCellClick(rowIdx, colIdx, rowData[value.name], evt);
									});
									$row.append($cell);
								});
								$tbody.append($row);
							});
						}else{
							var $row = $("<tr>");
							$.each(_self.config.colModel, function(colIdx, colData){
								var $cell;
								if (colData.formatter) {
									var colValue ;
									// 체크박스
									if(colData.formatter == "checkBox"){
										var id = "chk" + rowData["num"];
										colValue = '<input type="checkbox" id="' + id + '" name="chk">';
									}else if(colData.formatter == "rowNum"){
										colValue =rowIdx + 1
									} else {
										colValue = colData.formatter(rowData[colData.name], rowData, ((_self._jsonData.records - rowIdx) - _self._jsonData.page * _self.config.pageCnt +_self.config.pageCnt),$row);
									}
										$cell = $("<td>").append(colValue);
								} else {
										var $cell = $("<td>" + rowData[colData.name] + "</td>");
								}

								$cell.off("click");

								//체크박스 외의 곳 눌렀을때만 cellClick 적용
								if(colData.formatter != "checkBox") {
									$cell.on("click", function(evt){
										_self.config.onCellClick(rowIdx, colIdx, rowData[colData.name], evt);
									});
								}

								$row.append($cell);
							});

							$row.css("cursor", _self.config.mouseCursor);

							$row.off("click");
							$row.on({
								"click" : function(evt) {
									_self.config.onRowClick(rowIdx, evt);
								}
							});
							if(_self.config.mouseHover){
								$row.on({
									"mouseenter" : function() {
										$(this).css("background", "#F6F6F6");
									},
									"mouseleave" : function() {
										$(this).css("background", "");
									},
								});
							}

							$tbody.append($row);
						}


					});

				} else {
					var $row = $("<tr>");

					var $cell = $("<td colspan='"+ (_self.config.colModel[0][0] == undefined ? _self.config.colModel.length : _self.config.colModel[0].length) +"'>")
					$cell.html(_self.config.emptyMessage);
					$row.append($cell);
					$tbody.append($row);
				}
			}else{
				_self.$elem.empty();

				var $div =  $("<div class='acc_headar'></div>");
				$div.append("<p class='tit'>검색결과</p>");
				$div.append("<p class='cnt'>"+_self._jsonData.records+" 건</p>");

				_self.$elem.append($div);
				_self.$elem.append("<div class='acc_content active'></div>");

				if(_self._jsonData.root.length != 0) {

					var $ul = $("<ul class='list_tp4'>");

					$.each(_self._jsonData.root, function(rowIdx, rowData){
						var $li;
						$.each(_self.config.colModel, function(colIdx, colData){
							if (colData.formatter) {
								$li = $("<li></li>").append(colData.formatter(rowData[colData.name], rowData,$row));
							} else {
								var $a = $("<a href='#this'>"+ rowData[colData.name] + "</a>");
								$a.on("click", function(evt){
									_self.config.onRowClick(rowData, evt);
								});
								$li = $("<li></li>").append($a);
						}
//						$li.css("white-space", "nowrap");
							if(_self.config.mouseHover){
								$li.on({
									"mouseenter" : function() {
										$(this).css("background", "#F6F6F6");
									},
									"mouseleave" : function() {
										$(this).css("background", "");
									},
								});
							}
						$ul.append($li);
					});
					_self.$elem.find(".acc_content").append($ul);
				});
			}
		}
			this._setPaging();
		},

//		_pageCnt : 10,		//한 화면에 보여줄 페이지 갯수(1 2 3 4 5 6 7 8 9 10)

		_setPaging : function(){

			var $page = null;

			if(this.config.tableYN == "Y"){
				$page = this.$elem.find("div.board_pager_wrap");

				var $div = this.$elem.find("div.cnt");
				$div.empty();

				// 전체 리스트 수
				var totalSize = this._jsonData.records;

				if(!this.config.pagingYn) {
					if ($page.length > 0) $page.remove();
					return;
				}

				if (totalSize == 0) {
					$div.append("총 0건");
				} else {
					$div.append(
						"총 " + this._jsonData.records + "건"
					);
				}
			}else{
				this.$elem.find(".acc_content").append("<div class='paging'></div>")

				$page = this.$elem.find("div.paging");
			}

			// 현재 페이지
			var currentPage = this._jsonData.page;

			// 데이터 전체의 페이지 수
			var totalPage = this._jsonData.total;

			if(totalSize % this.config.reqData.rows == 0) totalPage -= 1;

			// 전체 페이지 수를 한화면에 보여줄 페이지로 나눈다.
			var totalPageList = Math.ceil(totalPage / this.config.pageCnt);

			// 페이지 리스트가 몇번째 리스트인지
			var pageList = Math.ceil(currentPage / this.config.pageCnt);

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
			var startPageList = ((pageList - 1) * this.config.pageCnt) + 1;

			// 끝 페이지
			var endPageList = startPageList + this.config.pageCnt - 1;

			// 시작 페이지와 끝페이지가 1보다 작으면 1로 설정
			// 끝 페이지가 마지막 페이지보다 클 경우 마지막 페이지값으로 설정
			if(startPageList < 1) startPageList = 1;
			if(endPageList > totalPage) endPageList = totalPage;
			if(endPageList < 1) endPageList = 1;

			if(this.config.tableYN == "Y"){
				// 페이징 DIV에 넣어줄 태그 생성변수
				var pageInner = "";

				pageInner += "<ul class='boardNav'>";
				// 페이지 리스트가 1이나 데이터가 없을 경우 (링크 빼고 흐린 이미지로 변경)
				if(pageList < 2){
					pageInner += "<li><a href='#' class='btn_first disabled'></a></li>"; //<<
					pageInner += "<li><a href='#' class='btn_prev disabled'></a></li>"; //<
				} else { // 이전 페이지 리스트가 있을 경우 (링크넣고 뚜렷한 이미지로 변경)
					var titleFirstPage = "첫 페이지로 이동";
					var titlePrePage = (startPageList - 10) + "페이지에서 " + (endPageList - 10) + "페이지까지 이동";

					pageInner += "<li><a class='btn_first' href='#' title='" + titleFirstPage + "'></a></li>";
					pageInner += "<li><a class='btn_prev' href='#' title='" + titlePrePage + "'></a></li>";
				}
//				pageInner += "</ul>";

//				pageInner += "<ul class='boardPage'>";
				// 페이지 숫자를 찍으며 태그생성 (현재페이지는 강조태그)
				for(var i = startPageList; i <= endPageList; i++){
					var titleGoPage = i + "페이지로 이동";

					if(i == currentPage){
						pageInner += "<li class='on'><a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a></li>";
					}else{
						pageInner += "<li><a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a></li>";
					}
				}
//				pageInner += "</ul>";

//				pageInner += "<ul class='boardNav'>";
				// 다음 페이지 리스트가 있을 경우
				if(totalPageList > pageList){
					var titleNextPage = (startPageList + 10) + "페이지에서 " + (endPageList + 10) + "페이지까지 이동";
					var titleLastPage = "마지막 페이지로 이동";

					pageInner += "<li><a class='btn_next' href='#' title='" + titleNextPage + "'></a></li>"; //>
					pageInner += "<li><a class='btn_last' href='#' title='" + titleLastPage + "'></a></li>"; //>>
				}

				// 현재 페이지리스트가 마지막 페이지 리스트일 경우
				if(totalPageList == pageList){
					pageInner += "<li><a href='#' class='btn_next disabled'></a></li>"; //>
					pageInner += "<li><a href='#' class='btn_last disabled'></a></li>"; //>>
				}
				pageInner += "</ul>";

				var html = "";
				html += "<div class='board_pager'>";
				html += pageInner;
				html += "</div>"

				$page.find(".board_pager").remove();
				$page.prepend(html);
			}else{
				// 페이징 DIV에 넣어줄 태그 생성변수
				var pageInner = "";

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

				// 페이지 숫자를 찍으며 태그생성 (현재페이지는 강조태그)
				for(var i = startPageList; i <= endPageList; i++){
					var titleGoPage = i + "페이지로 이동";

					if(i == currentPage){
						pageInner += "<a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a>\n";
					}else{
						pageInner += "<a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a>\n";
					}
				}

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

				var html = "";
				html += pageInner;

//				$page.remove();
				$page.append(html);
			}

			this._setPagingEvt($page);
		},

		_setPagingEvt : function($page) {
			var _self = this;

			var $a = _self.config.tableYN == "Y"  ? $page.find("ul>li>a") : $page.find("a");

			$a.on("click", function(evt){
				evt.preventDefault();

				var className = $(this).attr("class");

				if(className != "" && className != null) {
					if(className.indexOf("disabled") != -1){
						return true;
					}
				}

				switch (className) {
					case "btn_first" :
						_self._firstPage();
						break;
					case "btn_prev" :
						_self._prePage();
						break;
					case "btn_next" :
						_self._nextPage();
						break;
					case "btn_last" :
						_self._lastPage();
						break;
					default :
						_self._goPage($(this).attr("id"));
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

			currentPage -= this.config.pageCnt;
			var pageList = Math.ceil(currentPage / this.config.pageCnt);
			currentPage = (pageList - 1) * this.config.pageCnt + this.config.pageCnt;

			this.config.reqData.page = currentPage;
			this.loadList();
		},

		// 다음페이지 이동
		_nextPage : function(){
			var currentPage = this._jsonData.page;

			currentPage += this.config.pageCnt;
			var pageList = Math.ceil(currentPage / this.config.pageCnt);
			currentPage = (pageList - 1) * this.config.pageCnt + 1;

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

		getDataCnt : function() {
			return this._jsonData.records;
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

	window.SearchResultList = SearchResultList;

//	-------------다중 선택 테이블 Ver.서울-------------------------

	var MultiSelectBoard = function(elem, options) {
		this.$elem = $(elem);
		this.options = options;
		this._init();
	};

	MultiSelectBoard.prototype = {
			config : null,
			selectCnt : 0,
			_defaults : {
				url					: "",
				type				: "post",
				reqData			: {},
				cache			: false,
				dataType		: "json",
				async			: true,
				tableClass 	: "tbl_col_tp5",
				boardClass	: "div.board",
				allSelect		: false,
				colWidth		: [],
				select		: [],
			},

			_init : function () {
				this.config = $.extend({}, this._defaults, this.options);
				this.config.reqData = $.extend({}, this._pageConfig, this.config.reqData);
				this._setTableStrucObj();
				this._setHeader();
				this.loadList();
			},

			_setTableStrucObj : function () {
				this.$elem.append(
					"<h4 class='h4_tp2'>"+this.config.caption+"</h4>" +
					"<table class='"+this.config.tableClass+"'>" +
					"<caption>"+this.config.caption+"</caption>"+
						"<colgroup>" +
						"</colgroup>" +
						"<thead>" +
							"<tr>" +
								"<th scope='col'>" +
									"<span class='fl'>0개 선택</span>" +
									"<a href='#this'  class='delete btn tiny_tp2 bor_blue_tp1 fr'>전체 지우기</a>" +
								"</th>" +
								"<th scope='col'>" +
									"<a href='#this'  class='select btn tiny_tp2 bor_blue_tp1 fr'>전체 선택</a>" +
								"</th>" +
							"</tr>" +
						"</thead>" +
						"<tbody>" +
						"</tbody>" +
					"</table>"
				);
			},

			changeCaption : function (caption) {
				this.$elem.find(".h4_tp2").text(caption);
			},

			_setHeader : function () {
				var colGroupHtml = "";
				$.each(this.config.colWidth, function(idx, col){
					colGroupHtml += "<col width='" + col.width + "' />";
				});
				this.$elem.find("table").children("colgroup").append(colGroupHtml);
			},

			_jsonData : null,

			loadList : function () {
				var _self = this;
				if(!$.isEmpty(_self.config.url)) {
					$.ajax({
						type			: _self.config.type,
						url				: _self.config.url,
						dataType	: _self.config.dataType,
						data			: _self.config.reqData,
						cache		: _self.config.cache,
						async		: _self.config.async,
						success		: function(jsonView){
							_self._jsonData = jsonView;
							_self._setList(jsonView);
						}
					});
				}else{
					var json = _self._convert(_self.config.jsonData);
					_self._jsonData =json;
					_self._setList(json);
				}
			},

			_convert : function (json) {
				var dataset = [];

				if(!$.isArray(json)){
					for (var index in json) {
						if (json.hasOwnProperty(index)) {
							dataset.push([index, json[index]]);
						}
					}
				}else{
					dataset = json;
				}

				return dataset;
			},

			_setList : function (json) {
					var _self = this;
					var $tbody = _self.$elem.find("table>tbody");
					var $txtCnt = _self.$elem.find("table>thead>tr>th>span.fl");
					$tbody.empty();
					if(json != 0) {
						var $row = $("<tr>");
						var $td_F = $("<td>");
						var $ul_F = $("<ul class='list_tp9 sel'></ul>");
						var $td_S = $("<td>");
						var $ul_S = $("<ul class='list_tp9 del'></ul>");
						$.each(json, function(rowIdx, rowData){
							var $li =  null;
							var $a = null;
							$a = $("<a href='#this' class='btn btn_minus_blue_tp2 fr'></a>");
							$a.click(function(evt){
                                evt.stopPropagation();
								 _self.selectCnt--;
								$txtCnt.text(_self.selectCnt + "개 선택");
								$(this).parent().removeClass("selected");
								$(this).parent().hide();
								$ul_S.find("li[id='"+rowData[0]+"']").show();
							});
//	 						"++"
							$li =  $("<li id='"+rowData[0]+"'  "+($.inArray(rowData[0], _self.config.select) != -1 ? "class='selected'" : "style='display:none'")+">"+rowData[1]+"</li>");
							$li.on({
								"mouseenter" : function() {
									$(this).css("background", "#ECFAEF");
								},
								"mouseleave" : function() {
									$(this).css("background", "");
								},
                                "click" : function() {
                                    _self.selectCnt--;
                                    $txtCnt.text(_self.selectCnt + "개 선택");
                                    $(this).removeClass("selected");
                                    $(this).hide();
                                    $ul_S.find("li[id='"+rowData[0]+"']").show();
                                }
							});
							$li.append($a);
							$ul_F.append($li);
							$a = $("<a href='#this' class='btn btn_add_blue_tp2 fr'></a>");
							$a.click(function(evt){
                                evt.stopPropagation();
								 _self.selectCnt++;
								$txtCnt.text(_self.selectCnt + "개 선택");
								var tempLi = $ul_F.find("li[id='"+rowData[0]+"']");
								tempLi.addClass("selected");
								tempLi.show();
								$(this).parent().hide();
							});
							$li =  $("<li id='"+rowData[0]+"' style='"+($.inArray(rowData[0], _self.config.select) != -1 ? "display:none" : "")+"'>"+rowData[1]+"</li>");
							$li.on({
								"mouseenter" : function() {
									$(this).css("background", "#ECFAEF");
								},
								"mouseleave" : function() {
									$(this).css("background", "");
								},
                                "click" : function() {
                                    _self.selectCnt++;
                                    $txtCnt.text(_self.selectCnt + "개 선택");
                                    var tempLi = $ul_F.find("li[id='"+rowData[0]+"']");
                                    tempLi.addClass("selected");
                                    tempLi.show();
                                    $(this).hide();
                                    // console.log("해야됨");
                                }
							});
							$li.append($a);
							$ul_S.append($li);
						});
						$td_F.append($ul_F);
						$row.append($td_F);
						$td_S.append($ul_S);
						$row.append($td_S);
						$tbody.append($row);
						 _self.selectCnt = _self.config.select.length;
						$txtCnt.text(_self.selectCnt + "개 선택");
						_self._setEvent();
					}
			},

			_setEvent : function () {
				var _self = this;
				var $delete = _self.$elem.find("table>thead>tr>th>a.delete");
				var $select = _self.$elem.find("table>thead>tr>th>a.select");
				var $txtCnt = _self.$elem.find("table>thead>tr>th>span.fl");
				var $selLi = _self.$elem.find("table>tbody>tr>td>ul.sel>li");
				var $delLi = _self.$elem.find("table>tbody>tr>td>ul.del>li");
				$delete.click(function(evt){
					$selLi.each(function(){
						$(this).removeClass("selected");
						$(this).hide();
					});
					$delLi.each(function(){
						$(this).show();
					});
					_self.selectCnt = 0;
					$txtCnt.text("0개 선택");
				});

				$select.click(function(evt){
					$selLi.each(function(){
						if(!$(this).hasClass("selected")){
							$(this).addClass("selected");
						}
						$(this).show();
					});

					$delLi.each(function(){
						$(this).hide();
					});
					_self.selectCnt = $selLi.length;
					$txtCnt.text($selLi.length+"개 선택");
				});

				if(_self.config.allSelect){
					$select.trigger("click");
				}
			},
			getSelectVal : function () {
				var $li = this.$elem.find("table>tbody>tr>td>ul.sel>li.selected");
				var result = new Array();
				$li.each(function(){
					result.push($(this).attr("id"));
				});
				return result.join(",");
			},
			reloadList : function (params) {
//				if( this.config.type == "get"){
//					this.config.url = params.url;
//				}else{
				if (!params) params = {};
				if(params.hasOwnProperty("url")){
					this.config.url = params.url ;
				}
				this.config.reqData = $.extend({}, this._pageConfig, params);
//				}
				this.loadList();
			},
	}

	window.MultiSelectBoard = MultiSelectBoard;

//	-------------다중 선택 테이블 Ver.서울-------------------------


//	-------------메뉴 관리 테이블(트리) Ver.서울-------------------------

	var TreeTable = function(elem, options) {
		this.$elem = $(elem);
		this.options = options;
		this._init();
	};

	TreeTable.prototype = {
			config : null,

			_defaults : {
				url					: "",
				type				: "post",
				reqData			: {},
				cache			: false,
				dataType		: "json",
				async			: true,
				colModel		: [],
				colNames		: [],
				tableClass 	: "tbl_col_tp2 pd_tp2",
				boardClass	: "div.board_tp1",
				loadComplete	: function(data) {
				},
			},

			_init : function () {
				this.config = $.extend({}, this._defaults, this.options);
				this.config.reqData = $.extend({}, this._pageConfig, this.config.reqData);
				this._setTableStrucObj();
				this._setHeader();
				this.loadList();
			},

			_setTableStrucObj : function () {
				this.$elem.children(this.config.boardClass).append(
					"<table class='"+this.config.tableClass+"'>" +
					"<caption>"+this.config.caption+"</caption>"+
						"<colgroup>" +
						"</colgroup>" +
						"<thead>" +
							"<tr>" +
							"</tr>" +
						"</thead>" +
						"<tbody>" +
						"</tbody>" +
					"</table>"
				);
			},

			_setHeader : function () {
				var colGroupHtml = "";
				var theadHtml = "";

				$.each(this.config.colNames, function(idx, col){
					theadHtml += "<th scope='col'>" + col.name + "</th>";
					colGroupHtml += "<col width='" + col.width + "' />";
				});

				this.$elem.find("table").children("colgroup").append(colGroupHtml);
				this.$elem.find("table").children("thead").children("tr").append(theadHtml);
			},

			_jsonData : null,

			loadList : function () {
				var _self = this;
				$.ajax({
					type			: _self.config.type,
					url				: _self.config.url,
					dataType	: _self.config.dataType,
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

			_setList : function (json) {
				var _self = this;

				var $tbody = _self.$elem.find("table>tbody");
				$tbody.empty();

				if(_self._jsonData.root.length != 0) {
					$.each(_self._jsonData.root, function(rowIdx, rowData){
						var $row = $("<tr data-tt-id	= "+rowData["menuId"]+(rowData["parMenuId"] == "0" ? "" : " data-tt-parent-id = "+rowData["parMenuId"])+">");
						$.each(_self.config.colModel, function(colIdx, colData){
							var cls = "";
							if(colIdx == 0 || colIdx == 1){
								cls = "class='l'"
							}

							var $cell;
							if (colData.formatter) {
								var colValue = colData.formatter(rowData[colData.name], rowData, ((_self._jsonData.records - rowIdx) - _self._jsonData.page * _self.config.pageCnt +_self.config.pageCnt),$row);
								$cell = $("<td "+cls+">").append(colValue);
							} else {
									var $cell = $("<td "+cls+">" + rowData[colData.name] + "</td>");
							}

							$row.on({
								"mouseenter" : function() {
									$(this).css("background", "#F6F6F6");
								},
								"mouseleave" : function() {
									$(this).css("background", "");
								},
							});

							$row.append($cell);
						});
						$tbody.append($row);
					});
				}
			},

			reloadList : function (params) {
				this.config.reqData = $.extend({}, this._pageConfig, params);
				this.loadList();
			},
	}

	window.TreeTable = TreeTable;
//	-------------메뉴 관리 테이블(트리) Ver.서울-------------------------



//	-------------표지 리스트 Ver.서울-------------------------

	var SafetySingList = function($elem, options) {
		this.$elem = $elem;
		this.options = options;
		this._init();
	};

	SafetySingList.prototype = {
		config : null,

		_defaults : {
			url					: "",
			type				: "post",
			reqData			: {},
			pageCnt		: 10,
			cache			: false,
			dataType		: "json",
			async			: true,
			onRowClick	: function(rowIdx,$elem, evt) {
			},
		},

		_pageConfig : {
			pageCnt : 5,			//한 화면에 보여줄 페이지 갯수(1 2 3 4 5)
			rows : "10",
			page : "1",
		},

		_init : function () {
			this.config = $.extend({}, this._defaults, this.options);
			this.config.reqData = $.extend({}, this._pageConfig, this.config.reqData);
			if(!this.$elem.hasClass("sign_body")) this.$elem = this.$elem.find("div.sign_body");
			this.loadList();
		},

		_jsonData : null,

		loadList : function () {
			var _self = this;
			$.ajax({
				type			: _self.config.type,
				url				: _self.config.url,
				dataType	: _self.config.dataType,
				data			: _self.config.reqData,
				cache		: _self.config.cache,
				async		: _self.config.async,
				beforeSend	: function(){
					$.showBlock();
				},
				success		: function(jsonView){
					_self._jsonData = jsonView;
					_self._setList();
				},
				complete	: function(){
					$.hideBlock();
				},
			});
		},

		_setList : function () {
			var _self = this;

			_self.$elem.empty();

			if(_self._jsonData.root.length != 0) {
				var $ul = $("<ul class='signList'>");

				$.each(_self._jsonData.root, function(rowIdx, rowData){

					var $li = $("<li data-url='"+G.baseUrl+"fclts/A064_P/getSafetySignImg.do?mrkCde=" + rowData.mrkCde+"' data-value='"+rowData.mrkCde+"'></li>");

					var $a = $("<a href='#this'>"+rowData.ctt+"</a>");

					$a.css("background", "url('"+G.baseUrl+"fclts/A064_P/getSafetySignImg.do?mrkCde=" + rowData.mrkCde+"') center top no-repeat");

					$a.css("background-size", "60px 60px");

					$li.append($a);

					$li.on("click", function(evt){
						_self.config.onRowClick(rowIdx,$li, evt);
					});

					$ul.append($li);

				});

				_self.$elem.append($ul);
			}

			this._setPaging();
		},

		_setPaging : function(){

			var $page = null;

//			if(this.config.tableYN == "Y"){
				$page = $("div.board_pager_wrap");

//				var $div = this.$elem.find("div.cnt");
//				$div.empty();

				// 전체 리스트 수
				var totalSize = this._jsonData.records;

//				if(!this.config.pagingYn) {
//					if ($page.length > 0) $page.remove();
//					return;
//				}

//				if (totalSize == 0) {
//					$div.append("총 0건");
//				} else {
//					$div.append(
//						"총 " + this._jsonData.records + "건"
//					);
//				}
//			}else{
//				this.$elem.find(".acc_content").append("<div class='paging'></div>")
//
//				$page = this.$elem.find("div.paging");
//			}

			// 현재 페이지
			var currentPage = this._jsonData.page;

			// 데이터 전체의 페이지 수
			var totalPage = this._jsonData.total;

			if(totalSize % this.config.reqData.rows == 0) totalPage -= 1;

			// 전체 페이지 수를 한화면에 보여줄 페이지로 나눈다.
			var totalPageList = Math.ceil(totalPage / this.config.pageCnt);

			// 페이지 리스트가 몇번째 리스트인지
			var pageList = Math.ceil(currentPage / this.config.pageCnt);

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
			var startPageList = ((pageList - 1) * this.config.pageCnt) + 1;

			// 끝 페이지
			var endPageList = startPageList + this.config.pageCnt - 1;

			// 시작 페이지와 끝페이지가 1보다 작으면 1로 설정
			// 끝 페이지가 마지막 페이지보다 클 경우 마지막 페이지값으로 설정
			if(startPageList < 1) startPageList = 1;
			if(endPageList > totalPage) endPageList = totalPage;
			if(endPageList < 1) endPageList = 1;

				// 페이징 DIV에 넣어줄 태그 생성변수
				var pageInner = "";

				pageInner += "<ul class='boardNav'>";
				// 페이지 리스트가 1이나 데이터가 없을 경우 (링크 빼고 흐린 이미지로 변경)
				if(pageList < 2){
					pageInner += "<li><a href='#' class='btn_first disabled'></a></li>"; //<<
					pageInner += "<li><a href='#' class='btn_prev disabled'></a></li>"; //<
				} else { // 이전 페이지 리스트가 있을 경우 (링크넣고 뚜렷한 이미지로 변경)
					var titleFirstPage = "첫 페이지로 이동";
					var titlePrePage = (startPageList - 10) + "페이지에서 " + (endPageList - 10) + "페이지까지 이동";

					pageInner += "<li><a class='btn_first' href='#' title='" + titleFirstPage + "'></a></li>";
					pageInner += "<li><a class='btn_prev' href='#' title='" + titlePrePage + "'></a></li>";
				}
//				pageInner += "</ul>";

//				pageInner += "<ul class='boardPage'>";
				// 페이지 숫자를 찍으며 태그생성 (현재페이지는 강조태그)
				for(var i = startPageList; i <= endPageList; i++){
					var titleGoPage = i + "페이지로 이동";

					if(i == currentPage){
						pageInner += "<li class='on'><a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a></li>";
					}else{
						pageInner += "<li><a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a></li>";
					}
				}
//				pageInner += "</ul>";

//				pageInner += "<ul class='boardNav'>";
				// 다음 페이지 리스트가 있을 경우
				if(totalPageList > pageList){
					var titleNextPage = (startPageList + 10) + "페이지에서 " + (endPageList + 10) + "페이지까지 이동";
					var titleLastPage = "마지막 페이지로 이동";

					pageInner += "<li><a class='btn_next' href='#' title='" + titleNextPage + "'></a></li>"; //>
					pageInner += "<li><a class='btn_last' href='#' title='" + titleLastPage + "'></a></li>"; //>>
				}

				// 현재 페이지리스트가 마지막 페이지 리스트일 경우
				if(totalPageList == pageList){
					pageInner += "<li><a href='#' class='btn_next disabled'></a></li>"; //>
					pageInner += "<li><a href='#' class='btn_last disabled'></a></li>"; //>>
				}
				pageInner += "</ul>";

				var html = "";
				html += "<div class='board_pager'>";
				html += pageInner;
				html += "</div>"

				$page.find(".board_pager").remove();
				$page.prepend(html);

			this._setPagingEvt($page);
		},

		_setPagingEvt : function($page) {
			var _self = this;

			var $a = _self.config.tableYN == "Y"  ? $page.find("ul>li>a") : $page.find("a");

			$a.on("click", function(evt){
				evt.preventDefault();

				var className = $(this).attr("class");

				if(className != "" && className != null) {
					if(className.indexOf("disabled") != -1){
						return true;
					}
				}

				switch (className) {
					case "btn_first" :
						_self._firstPage();
						break;
					case "btn_prev" :
						_self._prePage();
						break;
					case "btn_next" :
						_self._nextPage();
						break;
					case "btn_last" :
						_self._lastPage();
						break;
					default :
						_self._goPage($(this).attr("id"));
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

			currentPage -= this.config.pageCnt;
			var pageList = Math.ceil(currentPage / this.config.pageCnt);
			currentPage = (pageList - 1) * this.config.pageCnt + this.config.pageCnt;

			this.config.reqData.page = currentPage;
			this.loadList();
		},

		// 다음페이지 이동
		_nextPage : function(){
			var currentPage = this._jsonData.page;

			currentPage += this.config.pageCnt;
			var pageList = Math.ceil(currentPage / this.config.pageCnt);
			currentPage = (pageList - 1) * this.config.pageCnt + 1;

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

		reloadList : function (params) {
			this.config.reqData = $.extend({}, this._pageConfig, params);
			this.loadList();
		},
	}

	window.SafetySingList = SafetySingList;
//	------------표지 리스트 Ver.서울-------------------------



//	------------통계 테이블 Ver.청주-------------------------
    var StatisticsTable = function(elem, options) {
        this.$elem = $(elem);
        this.options = options;
        this._init();
    };

    StatisticsTable.prototype = {
        config : null,

        _defaults : {
            url					: "",
            type				: "post",
            pagingYn		: false,
            pageCnt		: 10,
            reqData			: {},
            cache			: false,
            dataType		: "json",
            async			: true,
            tableClass 	: "tbl_col_tp4",
            loadComplete	: function(data) {
            },
        },

        _pageConfig : {
            rows : "30",
            page : "1",
        },

        _init : function () {
            this.config = $.extend({}, this._defaults, this.options);
            this.config.reqData = $.extend({}, this._pageConfig, this.config.reqData);
            this.loadList();
        },

        _jsonData : null,

        loadList : function () {
            var _self = this;
            $.ajax({
                type			: _self.config.type,
                url				: _self.config.url,
                dataType	: _self.config.dataType,
                data			: _self.config.reqData,
                cache		: _self.config.cache,
                async		: _self.config.async,
                beforeSend	: function(){
                    $.showBlock();
                },
                success		: function(jsonView){
                    _self._jsonData = jsonView;
                    _self._setList(jsonView);
                    _self.config.loadComplete(_self._jsonData);
                },
                complete	: function(){
                    $.hideBlock();
                },
            });
        },

        _setList : function (json) {
            var _self = this;

            if(_self._jsonData.length != 0) {
                $.each(json, function(key, value){
                	if(key != "total" && key != "records" &&key != "page"){
                        $("."+key).empty();
                        $("."+key).append(
                            "<table class='"+_self.config.tableClass+"' id='"+key+"'>" +
                            "<colgroup>" +
                            "</colgroup>" +
                            "<thead>" +
                            "<tr>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>" +
                            "<tr>" +
                            "</tr>" +
                            "</tbody>" +
                            "</table>"
                        );
                        var idx = 0;
                        var lastIdx = value.length;
                        $.each(value, function(k,v){
                            if(typeof v === "object"){
                                $.each(v, function(k2){
                                    if(idx == 0){
                                    	if(k2 != "num" && k2 != "totalrows"){
                                            $('#'+key+' > thead > tr:last').append("<th>" + k2.toLowerCase() + "</th>")
										}
                                    }
                                });
                                idx++;
                                $.each(v, function(k2,v2){
                                    if(k2 != "num" && k2 != "totalrows"){
                                        $('#'+key+' > tbody > tr:last').append("<td>" + v2 + "</td>")
                                    }
                                });
                                if(idx < lastIdx)  $('#'+key+' > tbody:last').append("<tr></tr>");
                            }else{
                                $('#'+key+' > thead > tr:last').append("<th>" + k + "</th>");
                                $('#'+key+' > tbody > tr').append("<td>" + v + "</td>");
                            }
                        });
					}
                });
            }

            if(this.config.pagingYn){
                this._setPaging();
			}
        },

        _setPaging : function(){

            var $page = null;

			$page = this.$elem.find("div.board_pager_wrap");

			// 전체 리스트 수
			var totalSize = this._jsonData.records;

            // 현재 페이지
            var currentPage = this._jsonData.page;

            // 데이터 전체의 페이지 수
            var totalPage = this._jsonData.total;

            if(totalSize % this.config.reqData.rows == 0) totalPage -= 1;

            // 전체 페이지 수를 한화면에 보여줄 페이지로 나눈다.
            var totalPageList = Math.ceil(totalPage / this.config.pageCnt);

            // 페이지 리스트가 몇번째 리스트인지
            var pageList = Math.ceil(currentPage / this.config.pageCnt);

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
            var startPageList = ((pageList - 1) * this.config.pageCnt) + 1;

            // 끝 페이지
            var endPageList = startPageList + this.config.pageCnt - 1;

            // 시작 페이지와 끝페이지가 1보다 작으면 1로 설정
            // 끝 페이지가 마지막 페이지보다 클 경우 마지막 페이지값으로 설정
            if(startPageList < 1) startPageList = 1;
            if(endPageList > totalPage) endPageList = totalPage;
            if(endPageList < 1) endPageList = 1;
                // 페이징 DIV에 넣어줄 태그 생성변수
                var pageInner = "";

                pageInner += "<ul class='boardNav'>";
                // 페이지 리스트가 1이나 데이터가 없을 경우 (링크 빼고 흐린 이미지로 변경)
                if(pageList < 2){
                    pageInner += "<li><a href='#' class='btn_first disabled'></a></li>"; //<<
                    pageInner += "<li><a href='#' class='btn_prev disabled'></a></li>"; //<
                } else { // 이전 페이지 리스트가 있을 경우 (링크넣고 뚜렷한 이미지로 변경)
                    var titleFirstPage = "첫 페이지로 이동";
                    var titlePrePage = (startPageList - 10) + "페이지에서 " + (endPageList - 10) + "페이지까지 이동";

                    pageInner += "<li><a class='btn_first' href='#' title='" + titleFirstPage + "'></a></li>";
                    pageInner += "<li><a class='btn_prev' href='#' title='" + titlePrePage + "'></a></li>";
                }
//				pageInner += "</ul>";

//				pageInner += "<ul class='boardPage'>";
                // 페이지 숫자를 찍으며 태그생성 (현재페이지는 강조태그)
                for(var i = startPageList; i <= endPageList; i++){
                    var titleGoPage = i + "페이지로 이동";

                    if(i == currentPage){
                        pageInner += "<li class='on'><a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a></li>";
                    }else{
                        pageInner += "<li><a href='#' id='" + (i) + "' title='" + titleGoPage + "'>"+(i)+"</a></li>";
                    }
                }
//				pageInner += "</ul>";

//				pageInner += "<ul class='boardNav'>";
                // 다음 페이지 리스트가 있을 경우
                if(totalPageList > pageList){
                    var titleNextPage = (startPageList + 10) + "페이지에서 " + (endPageList + 10) + "페이지까지 이동";
                    var titleLastPage = "마지막 페이지로 이동";

                    pageInner += "<li><a class='btn_next' href='#' title='" + titleNextPage + "'></a></li>"; //>
                    pageInner += "<li><a class='btn_last' href='#' title='" + titleLastPage + "'></a></li>"; //>>
                }

                // 현재 페이지리스트가 마지막 페이지 리스트일 경우
                if(totalPageList == pageList){
                    pageInner += "<li><a href='#' class='btn_next disabled'></a></li>"; //>
                    pageInner += "<li><a href='#' class='btn_last disabled'></a></li>"; //>>
                }
                pageInner += "</ul>";

                var html = "";
                html += "<div class='board_pager'>";
                html += pageInner;
                html += "</div>";

                $page.find(".board_pager").remove();
                $page.prepend(html);

            this._setPagingEvt($page);
        },

        _setPagingEvt : function($page) {
            var _self = this;

            var $a = _self.config.tableYN == "Y"  ? $page.find("ul>li>a") : $page.find("a");

            $a.on("click", function(evt){
                evt.preventDefault();

                var className = $(this).attr("class");

                if(className != "" && className != null) {
                    if(className.indexOf("disabled") != -1){
                        return true;
                    }
                }

                switch (className) {
                    case "btn_first" :
                        _self._firstPage();
                        break;
                    case "btn_prev" :
                        _self._prePage();
                        break;
                    case "btn_next" :
                        _self._nextPage();
                        break;
                    case "btn_last" :
                        _self._lastPage();
                        break;
                    default :
                        _self._goPage($(this).attr("id"));
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

            currentPage -= this.config.pageCnt;
            var pageList = Math.ceil(currentPage / this.config.pageCnt);
            currentPage = (pageList - 1) * this.config.pageCnt + this.config.pageCnt;

            this.config.reqData.page = currentPage;
            this.loadList();
        },

        // 다음페이지 이동
        _nextPage : function(){
            var currentPage = this._jsonData.page;

            currentPage += this.config.pageCnt;
            var pageList = Math.ceil(currentPage / this.config.pageCnt);
            currentPage = (pageList - 1) * this.config.pageCnt + 1;

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

        getDataCnt : function() {
            return this._jsonData.records;
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

        reloadList : function (params) {
            this.config.reqData = $.extend({}, this._pageConfig, params);
            this.loadList();
        },
    }

    window.StatisticsTable = StatisticsTable;

    //	------------통계 테이블 Ver.청주-------------------------


})(window, jQuery);