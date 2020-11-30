(function($) {
	var blockOption = {
		showBlock : function(){
			var modal = document.querySelector('ons-modal.loading');
			modal.show();
		},
		hideBlock : function(){
			var modal = document.querySelector('ons-modal.loading');
			modal.hide();
		}
	}
	
	$.fn.extend(blockOption);
	$.extend(blockOption);
})(jQuery);

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
	
	/*
	 * DatePicker 기본옵션
	 */
	G.datepickerOptions = {
//		dateFormat: 'yy-mm-dd',
//		prevText: '전월',
//		nextText: '차월',
//		monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
//		monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
//		dayNames : ['일','월','화','수','목','금','토'],
//		dayNamesShort : ['일','월','화','수','목','금','토'],
//		dayNamesMin : ['일','월','화','수','목','금','토'],
//		showMonthAfterYear : true,
//		yearSuffix : '년',
//		changeMonth: true,
//		changeYear: true,
//		 showOtherMonths: true, // 나머지 날짜도 화면에 표시, 선택은 불가
//		selectOtherMonths: false, // 나머지 날짜에도 선택을 하려면 true
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
//				weekHeader: 'Wk',
//				firstDay: 0,
//				isRTL: false,
			showMonthAfterYear : true,
			yearSuffix : '',
			changeMonth: true,
			changeYear: true,
		};

		$.datepicker.setDefaults($.datepicker.regional['ko']);

		var $datapicker_open;
//		var $ico_calendar;

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

//		$("#dedate").val($.datepicker.formatDate('yy-mm-dd', new Date()));

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
//	setDatePicker();
})(window, jQuery);

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