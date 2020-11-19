<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form id="updateFrm" name="updateFrm">
	<input type="hidden" id="mgrnu" name="mgrnu" value="${params.mgrnu}" />
	<input type="hidden" id="method" name="method" value="${params.method}" />
	<input type="hidden" id="facId" name="facId" value="<c:out value='${params.facId}'/>" />
	<input type="hidden" id="mgrnuIdx" name="mgrnuIdx" value="<c:out value='${params.mgrnuIdx}'/>" />

		<h2 class="h2_tp1">이력수정</h2>
			<table class="tbl_row_tp1">
            <colgroup>
                <col style="width:140px">
                <col style="width:*">
            </colgroup>
            <caption>이력 수정</caption>
				<tbody>
					<tr>
	                    <th scope="row">시공일자</th>
	                    <td><input id="worktm" name="worktm" type="text" class="ico_calendar datapicker_open" value="${result.worktm}" style="width:100%"></td>
               		 </tr>
					<tr>
						<th scope="row">시공자</th>
                    	<td><input id="workuser" name="workuser" type="text" style="width:100%" value="${result.workuser}"></td>
					</tr>
					<tr>
						<th scope="row">이력구분</th>
						<td><select name="workCde" id="workCde" style="width:100%"></select></td>
					</tr>
					<tr>
						<th scope="row">시공내용</th>
                    	<td><textarea cols="40" rows="8" name="workBg" id="workBg" style="width:100%"></textarea></td>

					</tr>

				</tbody>
			</table>
					<div class="btn_wrap area_r">
				<a href="#this" id="save" class="btn sml blue">수정완료</a>
				<a href="#this" id="list" class="btn sml gray">취소</a>
			</div>
		</form>

<script>
$(function() {

	$('#workBg').val("${result.workBg}".ltrim().split('<br>').join("\r\n"));

	var workCde = new AjaxComboBox("#workCde", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=WORK_CDE&refCd=CHG_HISTORY'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load({select: "${result.workCde}"});

	var $frm = $("#updateFrm");

	$("#save").click(function() {
		if(confirm("수정하시겠습니까?")) {

			var options = {
				url				: "${context}/fclts/updateWorkList.json",
				type			: "post",
				dataType		: "json",
				success			:  function(json) {
					if(json.cnt == 1){
						alert("정상적으로 수정되었습니다.");
						location.href="${context}/fclts/crudPopW.do?mgrnu="+$("#mgrnu").val()+"&method=select" + "&facId="+$("#facId").val() + "&mgrnuIdx="+$("#mgrnuIdx").val();
					} else {
						alert("오류발생, 다시 시도하여 주십시오1");
					}
				},
				error : function(response) {
					alert("오류발생, 다시 시도하여 주십시오2");
				},
			};

			$frm.ajaxSubmit( options );
		}
	});

	$("#list").click(function(){
		if(confirm("취소하시겠습니까?")) {
			$("#method").val("select");
	 		$("#updateFrm").submit();
		}
	});


});

</script>

