var fn_excelDown = function (url, form, flag) {
	var $form = $("<form>");

	var inputHtml = "";

	if (flag) inputHtml += "<input type='hidden' id='flag' name='flag' value='" + flag + "'/>";

	form.find("input, select").each(function() {
		inputHtml += "<input type='hidden' id='" + $(this).attr("id") + "' name='" + $(this).attr("name") + "' value='" + $(this).val() + "'/>";
	});

	$form.append(inputHtml);
	$form.appendTo("body");
	$form.attr({ action : url, method : "post" }).submit().remove();
}

var insertUseLog = function (connMenuL,connMenuM) {

	$.ajax({
		type		: 'post',
		url			: G.baseUrl+"system/logMng/insertUseLog.json",
		dataType	: 'json',
		data		: {connMenuL : connMenuL,
						connMenuM:connMenuM},
		async		: false,
		success		: function(jsonView){
		},
	});



}

/*
 * DatePicker 기본옵션
 */
G.datepickerOptions = {
//	dateFormat: 'yy-mm-dd',
//	prevText: '전월',
//	nextText: '차월',
//	monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
//	monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
//	dayNames : ['일','월','화','수','목','금','토'],
//	dayNamesShort : ['일','월','화','수','목','금','토'],
//	dayNamesMin : ['일','월','화','수','목','금','토'],
//	showMonthAfterYear : true,
//	yearSuffix : '년',
//	changeMonth: true,
//	changeYear: true,
//	 showOtherMonths: true, // 나머지 날짜도 화면에 표시, 선택은 불가
//	selectOtherMonths: false, // 나머지 날짜에도 선택을 하려면 true
};

var setDatePicker = function ($parent) {
	$.datepicker.regional['ko'] = { // Default regional settings
		dateFormat : 'yy-mm-dd', // [mm/dd/yy], [yy-mm-dd], [d M, y], [DD, d MM]
		prevText : '',
		nextText : '',
		monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames : ['일','월','화','수','목','금','토'],
		dayNamesShort : ['일','월','화','수','목','금','토'],
		dayNamesMin : ['일','월','화','수','목','금','토'],
//			weekHeader: 'Wk',
//			firstDay: 0,
//			isRTL: false,
		showMonthAfterYear : true,
		yearSuffix : '',
		changeMonth: true,
		changeYear: true,
	};

	$.datepicker.setDefaults($.datepicker.regional['ko']);

	var $datapicker_open;
//	var $ico_calendar;

	var $datapicker_open_std;
	var $datapicker_open_end;

	if ($parent) {
		$datapicker_open = $parent.find(".datapicker_open");
		$ico_calendar = $parent.find(".ico_calendar");
		$datapicker_open_std = $parent.find(".datapicker_open_std");
		$datapicker_open_end = $parent.find(".datapicker_open_end");
	} else {
		$datapicker_open = $(".datapicker_open");
		$ico_calendar = $(".ico_calendar");
		$datapicker_open_std = $(".datapicker_open_std");
		$datapicker_open_end = $(".datapicker_open_end");
	}

	/* datapicker */
	$datapicker_open.datepicker();
	$ico_calendar.datepicker();

//	$("#dedate").val($.datepicker.formatDate('yy-mm-dd', new Date()));

	/* datapicker period*/
	$datapicker_open_std.datepicker();

	if ($datapicker_open_end.val() != "") {
		$datapicker_open_std.datepicker("option", "maxDate", $datapicker_open_end.val());
	}

	$datapicker_open_std.datepicker("option", "onClose", function(selectedDate) {
		$datapicker_open_end.datepicker("option", "minDate", selectedDate);
	});

	$datapicker_open_end.datepicker();

	if ($datapicker_open_std.val() != "") {
		$datapicker_open_end.datepicker("option", "minDate", $datapicker_open_std.val());
	}

	$datapicker_open_end.datepicker("option", "onClose", function(selectedDate) {
		$datapicker_open_std.datepicker("option", "maxDate", selectedDate);
	});
}

$(function() {
	setDatePicker();
});


//Automatically cancel unfinished ajax requests
//when the user navigates elsewhere.
(function(window, $) {
	$.xhrPool = [];
	$.xhrAbort = function() {
		$.each($.xhrPool, function(idx, jqXHR) {
			jqXHR.abort();
		});
	};

	var oldbeforeunload = window.onbeforeunload;
	window.onbeforeunload = function() {
		var r = oldbeforeunload ? oldbeforeunload() : undefined;
		if (r == undefined) {
			$.xhrAbort();
		}
		return r;
	}

	$(document).ajaxSend(function(e, jqXHR, options) {
		$.xhrPool.push(jqXHR);
	});
	$(document).ajaxComplete(function(e, jqXHR, options) {
		$.xhrPool = $.grep($.xhrPool, function(x) {
			return x != jqXHR
		});
	});


	$.xhrCheckData = function(data) {
		if (typeof data == "undefined") return false;

		if (data.hasOwnProperty("error")) {
			var errorCode = data["error"];

			switch (errorCode) {
				case "401":
					$.xhrAbort();
//					alert("로그인후 다시 시도하세요. / Ajax 요청 실패");
//					top.location.href = G.baseUrl + "user/auth/login.do";
				break;
				default:
					alert("Ajax 요청 실패 / [" + errorCode + "] " + data["message"]);

				break;
			}

			return false;

		} else {
			return true;

		}
	}

})(window, jQuery);

(function(window, $) {
	/* Combobox 실행대기 후 실행 */
	$.waitForLazyRunners = function(callback) {
		var run = function() {
			if (!$.runner || $.runner <= 0) {
				clearInterval(interval);
				if(typeof callback === 'function') {
					callback();
				}
			} else {
				//console.log("wait...");
			}
		};
		var interval = setInterval(run, 30);
	}

})(window, jQuery);

(function($, undefined) {
	$.fn.extend({

		popupWindow : function(href, options, method) {
			if($(this).get(0).tagName != "FORM")  {
				alert("form 태그가 아닙니다.");
				return;
			}

			var options = $.extend({}, {
				id		: "_popup",
				width	: screen.availWidth,
				height	: screen.availHeight,
				scrollbars : 0
			}, options);
			if(options.open == "ConstSelect"){//준공 도면에서 시설물 관리와 시설물 입력창을 2개 동시에 볼수 있게 구분값을 줌
				options.id = "ConstSelect";
			}else if(options.open == "ConstUpdate"){//준공 도면에서 시설물 관리와 시설물 입력창을 2개 동시에 볼수 있게 구분값을 줌
				options.id = "ConstUpdate";
			}else if(options.open == "MapModify"){//준공 도면에서 시설물 관리와 지도수정에서 지도수정버튼  2개 동시에 볼수 있게 구분값을 줌
				options.id = "MapModify";
			}
			var winparam = 'resizable=1,status=1,dependent=1';
			$.each(options, function(key, val) {
				if (key != "id") winparam += ", " + key + "=" + val;
			});

			var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
			var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

			var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
			var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

			var left = ((width / 2) - (options.width / 2)) + dualScreenLeft;
			var top = ((height / 2) - (options.height / 2)) + dualScreenTop;

			if (!options.hasOwnProperty("top")) winparam += ', top=' + top;
			if (!options.hasOwnProperty("left")) winparam += ', left=' + left;

			$(this).attr("target", options.id) ;

			if(!method) method = "post";

			$(this).attr({action : href, method : method});

			window.open('',options.id,winparam);

			$(this).submit();

			// return popUp;
		},

		modalWindow : function(url, params) {
			params = $.extend({}, {
				reqData : {},
				options : {
					width : ""
				}
			}, params);

			var $self = $(this);

			$self.load(url, params.reqData, function(){
				$(this).css("width", params.options.width);
				$(this).find(".modal-header").css("background", "#0CA1D9");

				$self.modal();
			});
		},

		// 레이어 팝업 - 열기
		lp_open : function(url, params) {
			params = $.extend({}, {
				reqData : {},
				width : "350px",
			}, params);
			$(this).load(url, params.reqData, function(){
//				if(params.width != "") $(this).children().css("width", params.width);

				$(this).draggable({
					containment :"parent",
					handle : "div.lp_header",
				});

				$(this).css("z-index", "600");
				$(this).css("width", params.width);
				$(this).fadeIn("fast");
			});
		},

		// 레이어팝업 - 닫기
		lp_close : function() {
			$(this).html("");
			$(this).fadeOut('fast');
		},

	});

	$.extend({
		popupWindow : function(href, options) {
			var options = $.extend({}, {
				id		: href+"_popup",
				width	: screen.availWidth,
				height	: screen.availHeight,
				scrollbars : 0
			}, options);
			var winparam = 'resizable=1,status=1,dependent=1';
			$.each(options, function(key, val) {
				if (key != "id") winparam += ", " + key + "=" + val;
			});

			var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
			var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

			var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
			var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

			var left = ((width / 2) - (options.width / 2)) + dualScreenLeft;
			var top = ((height / 2) - (options.height / 2)) + dualScreenTop;

			if (!options.hasOwnProperty("top")) winparam += ', top=' + top;
			if (!options.hasOwnProperty("left")) winparam += ', left=' + left;

			window.open(href, options.id, winparam);
		},

		// 레이어 팝업 - 열기
		lp_open : function(url, elem, params) {
			params = $.extend({}, {
				reqData : {},
				colWidth : ["28%", "*"],
				width : "",
				className : "",
				searchFlag : false,
			}, params);

			var $elem = $(elem);

			$elem.load(url, params.reqData, function(){
				if(params.width != "") $(this).children().css("width", params.width);
				if(params.className != "") $(this).children().toggleClass("tp2 "+ params.className);

				var $table = $(this).find("table");

				var $colGroup = $("<colgroup/>");
				$.each(params.colWidth, function(){
					$colGroup.append("<col width='" + this + "' />");
				});

				$table.prepend($colGroup);

				if (params.searchFlag) {
					$self.draggable({
//						cursor :"pointer",
						containment :"parent",
//						cancel: ".mapControl_wrap"
						handle : "div.lp_header",
					});
				}


				$(this).fadeIn('fast');
			})
		},

		// 레이어팝업 - 닫기
		lp_close : function(elem) {
			var $elem = $(elem);
			var $lp = $elem.closest(".lp_wrap");
			$lp.html("");
			$lp.fadeOut('fast');

			var $mapSearch = $lp.next();

			if ($mapSearch.length > 0 && $mapSearch.hasClass("lp_wrap") && $mapSearch.attr("id") == "lp_map_wrap" && $lp.next().css("display") == "block") {
				$.lp_close($mapSearch);
			}
		},
	});

})(jQuery);


/**
 *
 * @param $
 * @param undefined
 */
(function($, undefined) {
	$.fn.extend({
		getFormToJsonData : function() {

			var params = {};

			var arr = $(this).serializeArray();

			$.each(arr, function(){
				var name;
				$.each(this, function(key, value){
					if (key == "name") {
						name = value;
					} else if(key == "value") {
						if(!$.isEmpty(value)) {
							params[name] = $.trim(value);
						}else{
							params[name] = "";
						}
					}
				});
			});

			return params;
		},
		isEmptyAlertForm : function() {
			var bValidation = false;
			var fValidation = true;
			var $tr = $(this).find("table>tbody>tr");

			$tr.each(function() {
				// var $fileDiv = $(this).find("td");
				// var $component;
				//파일 확장자 제한(추가가능)
				// var fileExt=["jpg", "pdf", "xlsx", "xls", "hwp", "jpeg", "png","jpg", "zip", "dxf", "JPG", "PDF", "XLSX", "XLS", "HWP", "JPEG", "PNG","JPG", "ZIP", "DXF", "DWG", "dwg"];
// 				$fileDiv.each(function(){
// 					$component = $(this).find("input[type=file]");
// 					$component.each(function(){
// 						var $temp=$(this)[0].value;
// 						if($temp!=""){
// 							fValidation = false;
// 							$(fileExt).each(function(){
// 								if($temp.endsWith(this.toString())){
// 									fValidation = true;
// 								}
// 							});
// 							if(fValidation!=true){
// 								alert("해당확장자는 업로드 할 수 없습니다.");
// 								$(this).focus();
// 								bValidation = false;
// 								return false;
// 							}
// 						}
// //						$(this).value.endsWith(".jpeg")
// 					});
// 				});
				var $th = $(this).children("th");

				$th.each(function(){
					if($(this).children("span.necessary").length > 0) {
						var strTh = $(this).text().replace("*", "");

						var $component = $(this).next().find("input, select, file, textarea");

						$component.each(function(){
							if(!$(this).is("[type='hidden']")) {
								if(!$(this).is("[type='file']")) $(this).trimSpace();

								if($(this).isEmptyAlert(strTh)) {
									bValidation = false;
									return false;
								} else {
									bValidation = true;
								}
							}
						});
					} else {
						bValidation = true;
					}
					if(fValidation!=true){
						bValidation = false;
					}
					return bValidation;
				});

				return bValidation;
			});

			return bValidation;
		},
		isDivEmptyAlertForm : function() {//교통시설물 입력창 전용 span
			var bValidation = false;

			var $li = $(this).find("ul>li");

			$li.each(function(){
				var $fileDiv = $(this).children("div.cont_wrap");
				var $component;
				//파일 확장자 제한(추가가능)
				var fileExt=["jpg", "pdf", "xlsx", "xls", "hwp", "jpeg", "png","jpg", "zip", "dxf", "JPG", "PDF", "XLSX", "XLS", "HWP", "JPEG", "PNG","JPG", "ZIP", "DXF"];
				$fileDiv.each(function(){

					$component = $(this).find("input[type=file]");
					$component.each(function(){
						var $temp=$(this)[0].value;
						if($temp!=""){
							var fValidation = false;
							$(fileExt).each(function(){
								if($temp.endsWith(this.toString())){
									fValidation = true;
								}
							});
							if(fValidation!=true){
								alert("해당확장자는 업로드 할 수 없습니다.");
								$(this).focus();
								bValidation = false;
								return false;
							}
						}
//						$(this).value.endsWith(".jpeg")
					});
				});
				var $div = $(this).children("div.tit");

				$div.each(function(){


					if($(this).children("span.necessary").length > 0) {
						var strTh = $(this).text().replace("*", "");
						$component = $(this).next().find("input, select, file, textarea");

						$component.each(function(){
							if(!$(this).is("[type='hidden']")) {
								if(!$(this).is("[type='file']")) $(this).trimSpace();

								if($(this).isEmptyAlert(strTh)) {
									bValidation = false;
									return false;
								} else {
									bValidation = true;
								}
							}
						});
					} else {
						bValidation = true;
					}

					return bValidation;
				});

				return bValidation;
			});

			return bValidation;
		},
	});
})(jQuery);

(function($, undefined) {
	$.fn.extend({
		showBlock : function(options){
			options = $.extend({}, {
				message : "<img alt='로딩' src='" + G.baseUrl + "images/common/spinner.gif' align=''>",
				css : {
					backgroundColor : "rgba(0,0,0,0.0)",
					color : "#000000",
					border : "0px solid #aa00",
				}
			}, options)

			$(this).block(options);
		},

		hideBlock : function(){
			$(this).unblock();
		}
	});

	$.extend({
		showBlock: function(options) {
			options = $.extend({}, {
				message : "<img alt='로딩' src='" + G.baseUrl + "images/common/spinner.gif' align=''>",
				css : {
					backgroundColor : "rgba(0,0,0,0.0)",
					color : "#000000",
					border : "0px solid #aa00",
				}
			}, options)

			$.blockUI(options);
		},

		hideBlock: function() {
			$.unblockUI();
		},
	});
})(jQuery);

/**
 * @param $
 * @param undefined
 *
 * isEmpty : 비어있는지 확인 return bool
 * isNum : 숫자인지 확인 return bool
 * is$Empty : jquery 객체가 비어있는지 확인 return bool
 * isEmptyAlert : jquery 객체가 비어있는지 확인 후 메시지창 return bool
 * isLengthBetween : 최소/최대 글자 return bool
 * isByteBetween : 최소/최대 바이트 return bool
 * deleteSpace : 공백(space)제거 return string
 * trimSpace : 맨 앞/뒤 공백제거 return string
 * lpad : 왼쪽으로 글자 채우기 return string
 * rpad : 오른쪽으로 글자 채우기 return string
 * byteSize : 바이트 크기 return string
 *
 */
(function($, undefined) {
	$.fn.extend({
		isEmpty : function() {
			if($(this).val() == "" || $(this).val() == null) {
				return true;
			} else {
				return false;
			}
		},
		isNum : function() {
			var ch = "\0";
			var flag = true;
			var value = $(this).val();
			for (var i = 0, ch = value.charAt(i);
				(i < value.length) && (flag); ch = value.charAt(++i)) {
				if ((ch >= '0') && (ch <= '9'));
				else if( (i == 0)&&(ch == '-'));
				else flag = false;
			}
			return flag;
		},
		isEmptyAlert : function(names, gbn) {
			if($(this).is$Empty()){
				alert("존재하지 않은 객체:"+names);
				return true;
			}
			if($(this).prop("type") == "text" || $(this).prop("type") == "textarea") {
				if($(this).val() == null || $(this).val().replace(/ /gi,"") == "" || $(this).val() == gbn){
					alert(names + "을(를) 입력하십시오.");
					$(this).focus();
					return true;
				}
			} else if ($(this).prop("type") == "select" || $(this).prop("type") == "select-one" || $(this).prop("type") == "select-multiple"){
				if($(this).val() == null || $(this).val().replace(/ /gi,"") == "" || $(this).val() == gbn){
					alert(names + "을(를) 선택하십시오.");
					$(this).focus();
					return true;
				}
			} else if ($(this).prop("type") == "file") {
				if($(this).val() == null || $(this).val().replace(/ /gi,"") == "" || $(this).val() == gbn) {
					alert(names + "을(를) 선택하십시오.");
					$(this).focus();
					return true;
				}
			} else if ($(this).prop("type") == "hidden") {
				if($(this).val() == null || $(this).val().replace(/ /gi,"") == "" || $(this).val() == gbn) {
					alert(names + "를 선택하십시오.");
					return true;
				}
			} 
			

			return false;
		},
		is$Empty : function() {
			if($(this).length == 0) {
				return true;
			} else {
				return false;
			}
		},
		isLengthBetween : function(min, max) {
			var length = $(this).val().length;

			if(min <= length && length <= max) {
				return true;
			} else {
				return false;
			}
		},
		isByteBetween : function(min, max) {
			var byteSize = $(this).byteSize();

			if(min <= byteSize && byteSize <= max) {
				return true;
			} else {
				return false;
			}
		},
		deleteSpace : function() {
			var val = $(this).val();
			val = val.replace(/ /g, "");	//space
			val = val.replace(/	/g, "");	//tab
			$(this).val(val);
		},
		trimSpace : function() {
			var val = $(this).val();
			val = $.trim(val);
			$(this).val(val);
		},
		lpad : function(totalLen, strRepl) {
			var strAdd = "";
			var diffLen = totalLen - $(this).val().length;

			for(var i = 0; i < diffLen; i++) {
				strAdd += strRepl;
			}

			var val = strAdd + $(this).val();

			$(this).val(val);
		},
		rpad : function(totalLen, strRepl) {
			var strAdd = "";
			var diffLen = totalLen - $(this).val().length;

			for(var i = 0; i < diffLen; i++) {
				strAdd += strRepl;
			}

			var val = $(this).val() + strAdd;

			$(this).val(val);
		},
		byteSize : function() {
			var val = $(this).val();
			var byteSize = 0;

			for(var i = 0; i < val.length; i++) {
				if(val.charCodeAt(i) > 255) {
					byteSize += 2;
				} else {
					byteSize += 1;
				}
			}
			return byteSize;
		},
		toUnderscore : function() {
			var val = $(this).val();
			return val.replace(/([A-Z])/g, function($1){return "_"+$1.toLowerCase();});
		}
	});

	$.extend({
		isEmpty : function(str) {
			if(str == "" || str == null) {
				return true;
			} else {
				return false;
			}
		},
		is$Empty : function($elem) {
			if($elem.length == 0) {
				return true;
			} else {
				return false;
			}
		},
		isLengthBetween : function(str, min, max) {
			var length = str.length;

			if(min <= length && length <= max) {
				return true;
			} else {
				return false;
			}
		},
		isByteBetween : function(str, min, max) {
			var byteSize = $.byteSize(str);

			if(min <= byteSize && byteSize <= max) {
				return true;
			} else {
				return false;
			}
		},
		deleteSpace : function(str) {
			var val = str.replace(/ /g, "");	//space
			val = val.replace(/	/g, "");	//tab

			return val;
		},
		trimSpace : function(str) {
			var val = $.trim(str);

			return val;
		},
		lpad : function(str, totalLen, strRepl) {
			var strAdd = "";
			var diffLen = totalLen - str.length;

			for(var i = 0; i < diffLen; i++) {
				strAdd += strRepl;
			}

			return strAdd + str;
		},
		rpad : function(str, totalLen, strRepl) {
			var strAdd = "";
			var diffLen = totalLen - str.length;

			for(var i = 0; i < diffLen; i++) {
				strAdd += strRepl;
			}

			return str + strAdd;
		},
		byteSize : function(str) {
			var val = str;
			var byteSize = 0;

			for(var i = 0; i < val.length; i++) {
				if(val.charCodeAt(i) > 255) {
					byteSize += 2;
				} else {
					byteSize += 1;
				}
			}

			return byteSize;
		},
		toUnderscore : function(str) {
			return str.replace(/([A-Z])/g, function($1){return "_"+$1.toLowerCase();});
		},
		toCamelCase : function(str) {
			return str.replace(/(_[a-z])/g, function($1){return $1.toUpperCase().replace("_", "");});
		},
	});
})(jQuery);

(function($, undefined) {

    var ChartResult = function(elem, options) {
        this.$elem = $(elem);
        this.options = options;
        this._init();
    };

    ChartResult.prototype = {
        config : null,

        _defaults : {
            jsonData			: {},
            title					: "",
			chartId				:"canvas"
        },

        _init : function () {
            this.config = $.extend({}, this._defaults, this.options);
            this._setElemInit();
            this.loadChart(this.config.jsonData);
        },

        _setElemInit : function () {
            this.$elem.find("#"+this.config.chartId).remove();
        	this.$elem.append($("<canvas id='"+this.config.chartId+"'></canvas>"));
        },

        chartJs : null,

        loadChart : function (data) {
            var labels = [];
            var datasets = [];
            var idx = 0;
            var colorIdx = 0;
            var color = Chart.helpers.color;
            var colorName = ["red","orange","green","yellow","blue","grey","purple"];
            $.each(data, function(key,value) {
                var i = 0;
                $.each(value, function(k,v){
                    if(k=="구분"){
                        labels.push(v.toLowerCase());
                    }  else{
                        if(k!="합계") {
                            if(idx==0){
                                datasets.push({
                                    label: k,
                                    backgroundColor: color(window.chartColors[colorName[colorIdx]]).alpha(0.5).rgbString(),
                                    borderColor: window.chartColors[colorName[colorIdx]],
                                    borderWidth: 2,
                                    data: [v.replace(/,/g,"")]
                                } );
                                colorIdx++;
                            }  else{
                                datasets[i].data.push(v.replace(/,/g,""));
                                i++;
                            }
                        }
                    }
                });
                idx++;
            });
            this.chartJs =  new Chart(this.$elem.find("#"+this.config.chartId), {
                type: 'bar',
                data: {
                    labels : labels,
                    datasets : datasets
                },
                options: {
                    indexAxis: 'y',
                    elements: {
                        bar: {
                            borderWidth: 3
                        }
                    },
                    scales: {
                        xAxes: [{
                            categoryPercentage: 1,
                            barPercentage: 0.5
                        }]
                    },
                    responsive: true,
                    legend: {
                        position: 'right'
                    },
                    title: {
                        display: true,
                        text:  this.config.title
                    },
                    events: false,
                    tooltips: {
                        enabled: false
                    },
                    hover: {
                        animationDuration: 0
                    },
                    animation: {
                        duration: 1,
                        onComplete: function () {
                            var chartInstance = this.chart,
                                ctx = chartInstance.ctx;
                            ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, Chart.defaults.global.defaultFontStyle, Chart.defaults.global.defaultFontFamily);
                            ctx.textAlign = 'center';
                            ctx.textBaseline = 'bottom';

                            this.data.datasets.forEach(function (dataset, i) {
                                var meta = chartInstance.controller.getDatasetMeta(i);
                                meta.data.forEach(function (bar, index) {
                                    var data = dataset.data[index].trim();
                                    ctx.fillText((data=="0"?"":data), bar._model.x, bar._model.y - 5);
                                });
                            });
                        }
                    }
                }
            });
        },
    }

    window.ChartResult = ChartResult;

})(jQuery);


String.prototype.lpad = function(padString, length) {
	var str = this;
	while (str.length < length) str = padString + str;
	return str;
}

String.prototype.rpad = function(padString, length) {
	var str = this;
	while (str.length < length) str = str + padString;
	return str;
}

String.prototype.trim = function() {
	return  this.replace(/(^\s*)|(\s*$)/gi, "");
};

String.prototype.ltrim = function() {
	return  this.replace(/^\s+/, "");
};

String.prototype.rtrim = function() {
	return  this.replace(/\s+$/, "");
};

String.prototype.startsWith = function(str) {
	if(this.length<str.length){return false;}
	return this.indexOf(str)==0;
}

String.prototype.endsWith = function(str) {
	if(this.length<str.length){return false;}
	return this.lastIndexOf(str)+str.length==this.length;
}


