$(function() {

	"use strict";

	/* MenuManager */
	var MenuManager = function(elem, ajaxOpts) {
		this.$elem = $(elem);
		this.ajaxOpts = ajaxOpts;
		this.menuData = {};
		this._init();
	};

	MenuManager.prototype = {
		_init : function() {
			var _self = this;
			$.ajax({
				type		: "post",
				url			: _self.ajaxOpts.url,
				dataType	: "json",
				cache		: false,
				success : function(json) {
					_self.menuData = _self._convert(json);
					_self._buildTopMenu(_self.menuData);
					if(window.location.href.indexOf("index.do") < 0 && window.location.href.indexOf("user/auth/accessDenied.do") < 0 && window.location.href.indexOf("/error/error") < 0) {
						var rootMenu ="";
						if(_self.ajaxOpts.currentMenu != "" && _self.ajaxOpts.currentMenu.indexOf("M") > -1){
							rootMenu = _self.ajaxOpts.currentMenu.substr(0, 4).rpad("0", 10);
							$.cookie("rootMenu", rootMenu, { path : '/' });
							$.cookie("currentMenu", _self.ajaxOpts.currentMenu, { path : '/' });
						}else{
							rootMenu = $.cookie("rootMenu");
						}
						_self._buildLeftMenu($(_self.ajaxOpts.leftElement), _self.menuData[rootMenu]);
					}
				}
			});
		},

		_convert: function (rows) {
			var nodes = [];
			// top level
			for (i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (!this._exists(rows, row.parentId)){
					nodes.push({
						  menuId	: row.menuId
						, parentId	: row.parentId
						, label		: row.text
						, url			: row.url
						, useYn		: row.useYn
						, popupYn		: row.popupYn
						, progDesc		: row.progDesc
						, topMenuYn		: row.topMenuYn
					});
				}
			}

			var standby = [];
			for(var i=0; i<nodes.length; i++){
				standby.push(nodes[i]);
			}

			while(standby.length){
				var node = standby.shift(); //parent node

				//children nodes
				for(var i=0; i<rows.length; i++){
					var row = rows[i];
					if (row.parentId == node.menuId){
						var child = {
							  menuId	: row.menuId
							, parentId	: row.parentId
							, label		: row.text
							, url		: row.url
							, useYn		: row.useYn
							, popupYn		: row.popupYn
							, topMenuYn		: row.topMenuYn
							, engLabel		: node.progDesc
							, parentNM : node.label
							, parentYn : node.useYn
							, parentUrl : node.url
							, parentPop : node.popupYn
						};

						if (node.children){
							node.children.push(child);
						} else {
							node.children = [child];
						}

						standby.push(child);
					}
				}
			}

			// 대메뉴별 분리
			var self = this;
			var menuData = {}
			$.each(nodes, function (idx) {
				if ("children" in this) {
					menuData[this.menuId] = this['children'];
				}
			});

			return menuData;
		},

		_exists: function (rows, parentId) {
			for(var i=0; i<rows.length; i++){
				if (rows[i].menuId == parentId) return true;
			}
			return false;
		},

		_buildTopMenu: function (items) {
			var self = this;

			$.each(items, function () {
				if(this[0].topMenuYn == "Y"){
					var level = 0;
					var $mainLi = $("<li>");
					var $ul = $("<ul class='subMenu'>");
					$.each(this, function () {
						var cls = "";
						var url ="";

						if(level == 0){

							if(this.parentYn == "N"){
								cls = "m_disabled";
								url = "javascript:alert(\"해당메뉴에 대한 권한이 없습니다.\");";
							}else{
								if(this.parentPop ==  "Y"){
									url = "javascript:$.popupWindow(\""+G.baseUrl + this.parentUrl+"\",{scrollbars:0});";
								}else{
									url = G.baseUrl + this.parentUrl + "#" + this.menuId;
								}
							}

							var $a = $("<a  href='"+ url +"'  class='"+cls+"'>"+this.parentNM+"</a>");

							$mainLi.append($a);
						}

						if(this.useYn == "N"){
							cls = "m_disabled";
							url = "javascript:alert(\"해당메뉴에 대한 권한이 없습니다.\");";
						}else{
							cls = "";
							if(this.popupYn ==  "Y"){
								url = "javascript:$.popupWindow(\""+G.baseUrl + this.url+"\",{scrollbars:0});";
							}else{
								var menuId = (this.children && this.children.length > 0) ? this.children[0].menuId : this.menuId;
								url = G.baseUrl + this.url  + "#" + menuId;
							}
						}

						if (this.url == "1") this.label += " x"; // 미개발 메뉴 표시용

						var $subLi = $("<li>");
						var $aTag = $("<a href='"+ url +"' class='"+cls+"'>"+this.label+"</a>");
						$subLi.append($aTag);
						$ul.append($subLi);
						level=1;
					});
					$mainLi.append($ul);
					self.$elem.append($mainLi);
				}else if(this[0].parentNM == "지도서비스"){
                    var $mainLi = $("<li>");
                    var $ul = $("<ul class='subMenu'>");
                    var cls = "";
                    var url ="";
                    if(this[0].parentYn == "N"){
                        cls = "m_disabled";
                        url = "javascript:alert(\"해당메뉴에 대한 권한이 없습니다.\");";
                    }else{
                        if(this[0].parentPop ==  "Y"){
                            url = "javascript:$.popupWindow(\""+G.baseUrl + this[0].parentUrl+"\",{scrollbars:0});";
                        }else{
                            url = G.baseUrl + this[0].parentUrl + "#" + this[0].menuId;
                        }
                    }

                    var $a = $("<a  href='"+ url +"'  class='"+cls+"'>"+this[0].parentNM+"</a>");

                    $mainLi.append($a);
                    $mainLi.append($ul);
                    self.$elem.append($mainLi);
                }
			});

			self._buildUIEvt();

		},

		_buildLeftMenu: function (parent,items) {
			var self = this;
			var $ul = $("<ul class='snb_list'>");
			var idx = 0;

			$.each(items, function () {
				if(idx == 0) {
					parent.prepend($("<h1>"+this.parentNM+ "<em>"+this.engLabel+"</em></h1>"));
					idx ++;
				}

				if (this.children && this.children.length > 0) {
					var $li = $("<li>");
					if($.cookie("currentMenu") == this.menuId){
						$li.addClass("active");
					}

					var url ="";
					var cls ="";

					if(this.useYn == "N"){
						cls = "m_disabled";
						url = "javascript:alert(\"해당메뉴에 대한 권한이 없습니다.\");";
					}else{
						cls = "";
						if(this.popupYn ==  "Y"){
							url = "javascript:$.popupWindow(\""+G.baseUrl + this.url +"\",{scrollbars:0});";
						}else{
							url = G.baseUrl + this.url  + "#" +this.children[0].menuId;
						}
					}

					var $a = $("<a href='"+url +"' class='"+cls+"'>"+this.label+"</a>");
					var $div = $("<div class='subMenu'>");
					var $subUl = $("<ul>");
					$.each(this.children, function () {
						var $subLi = $("<li>");

						if($.cookie("currentMenu").substr(0,7)+"000" == this.parentId){
							$li.addClass("active");
						}

						if($.cookie("currentMenu") == this.menuId){
							$subLi.addClass("active");
							var $location = $(pageLocation);
							$location.empty();
							$location.append($("<li><a href='#' class='home'>Home</a></li>"));
							$location.append($("<li><a href='#'>"+this.parentNM+"</a></li>"));
							$location.append($("<li><a href='#'>"+this.label+"</a></li>"));
						}

						var subUrl ="";
						var subCls ="";

						if(this.useYn == "N"){
							subCls = "m_disabled";
							subUrl = "javascript:alert(\"해당메뉴에 대한 권한이 없습니다.\");";
						}else{
							subCls = "";
							if(this.popupYn ==  "Y"){
								subUrl = "javascript:$.popupWindow(\""+G.baseUrl + this.url +"\",{scrollbars:0});";
							}else{
								subUrl = G.baseUrl + this.url  + "#" + this.menuId;
							}
						}

						var $subA = $("<a href='"+subUrl +"' class='"+subCls+"'>"+this.label+"</a>");
						$subLi.append($subA);
						$subUl.append($subLi);
					});

					$div.append($subUl);
					$li.append($a);
					$li.append($div);
					$ul.append($li);
				} else {
					var $li = $("<li>");

					if($.cookie("currentMenu") == this.menuId){
						$li.addClass("active");
						var $location = $(pageLocation);
						$location.empty();
						$location.append($("<li><a href='#' class='home'>Home</a></li>"));
						$location.append($("<li><a href='#'>"+this.parentNM+"</a></li>"));
						$location.append($("<li><a href='#'>"+this.label+"</a></li>"));
					}

					var url ="";
					var cls ="";
					if(this.useYn == "N"){
						cls = "m_disabled";
						url = "javascript:alert(\"해당메뉴에 대한 권한이 없습니다.\");";
					}else{
						cls = "";
						if(this.popupYn ==  "Y"){
							url = "javascript:$.popupWindow(\""+G.baseUrl + this.url +"\",{scrollbars:0});";
						}else{
							url = G.baseUrl + this.url  + "#" + this.menuId;
						}
					}

					if (this.url == "1") this.label += " (x)"; // 미개발 메뉴 표시용

					var $a = $("<a href='"+url +"' class='"+cls+"'>"+this.label+"</a>");

					$li.append($a);
					$ul.append($li);
				}
			});
			parent.find("h1").after($ul);
		},

		_buildUIEvt: function () {
			var _this = this;
			var $lnbWrap = $(".lnb_wrap");
			var $lnb = $(".lnb");
			var $lnb_list = $lnb.find(">li");

			$lnb_list.on("mouseenter",  function() {
				var _this = $(this);
				$lnbWrap.addClass("active");
				$lnb_list.removeClass("active");
				_this.addClass("active");
			}).on("mouseleave", function() {
				$lnbWrap.removeClass("active");
				$lnb_list.removeClass("active");
			}).on("focusin", function(e){
				var _this = $(this);
				var _thisIdx = _this.index();
				$lnbWrap.addClass("active");
				$lnb_list.each(function(i, idx){
					if(_thisIdx == i) {
						$(idx).addClass("active");
					} else {
						$(idx).removeClass("active");
					}
				});
			});

			$lnbWrap.on("focusout",function(e){
				$lnbWrap.removeClass("active");
				$lnb_list.removeClass("active");
			});
		}

	}

	window.MenuManager = MenuManager;

	$.topMenu = new MenuManager("div#wrap>div#header>div.lnb_wrap>ul", {
		url: G.baseUrl + "system/menu/userMenu.json",
		leftElement : "div#container>div.innerArea>div#content>div#snb",
		pageLocation : "#pageLocation",
		currentMenu : window.location.hash.replace(/^#/, "")
	});
});