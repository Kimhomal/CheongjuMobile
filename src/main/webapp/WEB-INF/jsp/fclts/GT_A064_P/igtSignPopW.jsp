<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

	<h3 class="h3_tp3">유형선택</h3>
		<div class="inp_wrap">
			<div class="inp tp3 w15p">표지유형</div>
			<div class="inp w20p">
				<select name="mrkKnd" id="mrkKnd" class="w90p"></select>
			</div>
			<div class="inp tp3 w15p">코드번호</div>
			<div class="inp w30p">
				<input  type="text" name="mrkCde" id="mrkCde"></input>
			</div>
			<div class="inp">
				<a href="#this" id="signSearch" class="btn sml blue">검색</a>
			</div>
		</div>

		<div class="mg_t15 sign_body"></div>
		<div class="board_pager_wrap"></div>

<script>
//# sourceURL= /fclts/a064_p/safetySignPop.jsp.jsp

$(function(){

	var mrkKnd = new AjaxComboBox("#mrkKnd", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=mrkKnd'/>",
	}).load();

	$("#mrkKnd").on("change",function(evt){
		markList.reloadList({
			mrkKnd : $("#mrkKnd").val(),
			mrkCde : $("#mrkCde").val(),
			rows : "10",
		});
	});

	$("#signSearch").on("click",function(evt){
		markList.reloadList({
			mrkKnd : $("#mrkKnd").val(),
			mrkCde : $("#mrkCde").val(),
			rows : "10",
		});
	});

	var markList = new SafetySingList($("div.sign_body"), {
		url : "${context}/fclts/A064_P/getIgtSignList.json",
		reqData : {
			mrkKnd : "주의",
			mrkCde : $("#mrkCde").val(),
			rows : "10",
		},
		onRowClick	: function(rowIdx,$elem, evt) {
			var src = $elem.attr("data-url");
			opener.$("#sign").attr("src",src);
			opener.$("#mrkCde").val($elem.attr("data-value"));
			window.close();
		}
	});

});

</script>