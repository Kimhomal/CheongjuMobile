/* Common Function */

function urlOpen(url, width, height) {
    window.open(url, '', 'width=' + width + ', height=' + height + ', left=0, top=0, statusbar=0, scrollbars=0');
}

$(document).ready(function (e) {
    /* #181209 : 수정 end */
    $(document).on("click", ".treetable .btn_treeDown", function (e) {
        var $tr = $(this).closest("tr");
        var $tr_class = $tr.attr("data-node");

        $tr.next().after($tr);

        console.log($tr);

    });

    $(document).on("click", ".input_checkBox", function (e) {
        e.preventDefault(); //label이 있을경우 두번 클릭 방지
        var _this = $(this);
        var _checkBox = _this.find("input[type='checkbox']");
        var _chk_parent = _this.closest('.chk_wrap').find(".chk_parent");
        var _chk_child = _this.closest('.chk_wrap').find(".chk_child");

        if (_checkBox.hasClass("chk_parent")) {
            if (_this.hasClass("active")) {
                _this.removeClass("active");
                _checkBox.prop("checked", false);
                _chk_child.parent().removeClass("active");
                _chk_child.prop("checked", false);
            } else {
                _this.addClass("active");
                _checkBox.prop("checked", true);
                _chk_child.parent().addClass("active");
                _chk_child.prop("checked", true);
            }
        } else {
            if (_this.hasClass("active")) {
                _this.removeClass("active");
                _checkBox.prop("checked", false);
            } else {
                _this.addClass("active");
                _checkBox.prop("checked", true);
            }

            var _chk_len = _this.closest('.chk_wrap').find('.chk_child').length;
            var _chk_active = _this.closest('.chk_wrap').find('.chk_child:checked').length;

            if (_chk_active == _chk_len) {
                _chk_parent.prop("checked", true);
                _chk_parent.parent().addClass("active");
            } else {
                _chk_parent.prop("checked", false);
                _chk_parent.parent().removeClass("active");
            }
        }
    });

    // 공통 텝 메뉴
    /* #181209 : 수정 start */
    $(document).on("click", ".tapMenu > li", function (e) {
        var _this = $(this);
        var _idx = _this.index();
        var _obj = _this.closest(".tapMenu");

        _this.closest(".tap_wrap").find(".tap_content").removeClass("active").eq(_idx).addClass("active");

        _obj.find("li").each(function (i, obj) {
            if (i == _idx) {
                $(obj).addClass("active");
            } else {
                $(obj).removeClass("active");
            }
        });
    });
    /* #181209 : 수정 end */
});



window.fn_lnb = function () {
    var _this = this;
    var $lnbWrap = $(".lnb_wrap");
    var $lnb = $(".lnb");
    var $lnb_list = $lnb.find(">li");


    var init = function () {
        $lnb_list.on("mouseenter", _this.mouse_toggle.mouseenter);
        $lnb_list.on("mouseleave", _this.mouse_toggle.mouseleave);

        $lnb_list.on("focusin", _this.mouse_toggle.focusin);
        $lnbWrap.on("focusout", _this.keyboard_toggle.focusout);
    }

    _this.mouse_toggle = {
        mouseenter: function () {
            var _this = $(this);
            $lnbWrap.addClass("active");
            $lnb_list.removeClass("active");
            _this.addClass("active");
        },
        mouseleave: function () {
            $lnbWrap.removeClass("active");
            $lnb_list.removeClass("active");
        },
        focusin: function (e) {
            var _this = $(this);
            var _thisIdx = _this.index();
            $lnbWrap.addClass("active");
            // $lnb_list.addClass("active");
            $lnb_list.each(function (i, idx) {
                if (_thisIdx == i) {
                    $(idx).addClass("active");
                } else {
                    $(idx).removeClass("active");
                }
            });
        }
    }

    _this.keyboard_toggle = {

        focusout: function (e) {
            $lnbWrap.removeClass("active");
            $lnb_list.removeClass("active");
        }
    }
    init();
}

window.fn_bannerToggle = function () {
    var $banner = $(".bannerList_tp2");
    var $banner_list = $banner.find(">li");

    var init = function () {
        $banner_list.on('mouseenter', _this.toggle.mouseenter);
        $banner_list.on('mouseleave', _this.toggle.mouseleave);
    }

    _this.toggle = {
        mouseenter: function () {
            var _this = $(this);
            var _thisIdx = $banner_list.index(this);
            $banner_list.each(function (i, obj) {
                if (_thisIdx == i) {
                    _this.addClass("active");
                }
            });
        },
        mouseleave: function () {
            $banner_list.removeClass("active");
        }
    }
    init();
}

var modalLayer = function (that, options) {
    _this = this;
    var flag = true;
    var element = $(that).length ? $(that) : $('<div>');

    var defaults = {
        target: null,
        css: {},
        closeTarget: null,
        mask: true,
        maskClose: true,
        callbackEvent: {
            // return target, _this, element
            before: function () {},
            open: function () {},
            close: function () {}
        }
    };

    var options = $.extend({}, defaults, options);
    var target = options.target ? $(options.target) : $(element.attr('href'));
    var $btn_close = target.find('.lp_close');

    if (!target.length) {
        return false;
    }

    var init = function () {
        element.on('click', _this.lp_open);
        $btn_close.on('click', _this.lp_close);

    }
    _this.lp_open = function () {
        target.stop().fadeIn('fast');
        // gallerySlider();
    }

    _this.lp_close = function () {
        target.stop().fadeOut('fast');
    }

    /*var gallerySlider = function() {
    	if(flag) {
    		var gallerySlider = $(".gallerySlider").bxSlider({
    			infiniteLoop: true,
    			maxSlides: 1,
    			minSlides: 1,
    		});
    		flag = false;
    	}
    }*/
    init();

}

$.fn.extend({
    modalLayer: function (options) {
        return this.each(function () {
            if (!$(this).data("modalLayer")) {
                $(this).data("modalLayer", new modalLayer(this, options));
            }
        });
    },
});

//버튼 색 제거,추가
$('.tab_menu_btn').on('click', function () {
    $('.tab_menu_btn').removeClass('on');
    $(this).addClass('on')
});

//1번 컨텐츠
$('.tab_menu_btn1').on('click', function () {
    $('.tab_box').hide();
    $('.tab_box1').show();
});

//2번 컨텐츠
$('.tab_menu_btn2').on('click', function () {
    $('.tab_box').hide();
    $('.tab_box2').show();
});


//3번 컨텐츠
$('.tab_menu_btn3').on('click', function () {
    $('.tab_box').hide();
    $('.tab_box3').show();
});


//4번 컨텐츠
$('.tab_menu_btn4').on('click', function () {
    $('.tab_box').hide();
    $('.tab_box4').show();
});


//5번 컨텐츠
$('.tab_menu_btn5').on('click', function () {
    $('.tab_box').hide();
    $('.tab_box5').show();
});
