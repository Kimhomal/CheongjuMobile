// lnb
window.fn_lnb = function(){
	var _this = this;
	var $lnb = $(".lnb");
	var $lnb_list = $lnb.find(">li");
	var $btn_controll = $(".btn_lnb_ctr");
	var $lnb_panner = $(".lnb_panel");
	var $lnb_panel_cont = $lnb_panner.find(".lnb_panel_cont");

	var init = function(){
		$lnb_list.on("click", _this.toggle_lnb);
		$btn_controll.on("click", _this.toggle_panner);
	}

	_this.toggle_lnb = function(){
		var _this = $(this);
		var _thisIdx = _this.index();
		$lnb_list.removeClass("active");
		_this.addClass("active");

		// 패널 내용 교체 index값과 마크업의 순서가 같아야 한다.
		$lnb_panel_cont.each(function(i, obj) {
			var _obj = $(obj);
			if(_thisIdx == i) {
				_obj.addClass("active");
			} else {
				_obj.removeClass("active");
			}
		});

		panner_open();
		$btn_controll.addClass("active");
	}

	_this.toggle_panner = function(){
		var _this = $(this);
		if(_this.hasClass("active")) {
			panner_close();
			_this.removeClass("active");
		} else {
			panner_open();
			_this.addClass("active");
		}
	}

	// 패널 열기
	var panner_open = function(){
		$lnb_panner.show().stop().animate({left:190}, 'fast');
		$btn_controll.stop().animate({left:590}, 'fast');
	}

	// 패널 닫기
	var panner_close = function(){
		$lnb_panner.stop().animate({left:-50}, 'fast', function(e){
			$lnb_panner.hide();
		});
		$btn_controll.stop().animate({left:190}, 'fast');
	}

	init();	
}

window.fn_snb = function() {
	var _this = this;
	var $snb = $(".snb_wrap").find(".snb");
	var $snb_list = $snb.find(">li");
	var $mapCtr = $snb_list.find(".mapCtr_wrap");
	var $mapCtr_list = $mapCtr.find(">li");
	var $mapCtr_ico = $mapCtr_list.find(".ico");
	var $mapCtr_tooltip = $mapCtr_list.find(".tooltip");

	var init = function() {
		$mapCtr_list.on("mouseenter", _this.tooltip_open);
		$mapCtr_list.on("mouseleave", _this.tooltip_close);
	}

	_this.tooltip_open = function() {
		var _this = $(this);
		var _tg_idx = _this.index();

		$mapCtr_list.each(function(i, obj){
			var _this = $(obj);
			if(_tg_idx == i) {
				_this.find(".tooltip").show();
			} else {
				_this.find(".tooltip").hide();
			}
		});
	}

	_this.tooltip_close = function() {
		$mapCtr_tooltip.hide();
	}

	init();

}