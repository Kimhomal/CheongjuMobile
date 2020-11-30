<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form id="selectInfoFrm" name="selectInfoFrm">
	<input type="hidden" name="mgrnu" value="${params.mgrnu}" />
	<input type="hidden" name="method" value="${params.method}" />
	<input type="hidden" name="facId" value="<c:out value='${params.facId}'/>" />
	<input type="hidden" name="mgrnuIdx" value="<c:out value='${params.mgrnuIdx}'/>" />

		<h2 class="h2_tp1">이력조회</h2>
			<table class="tbl_row_tp1">
            <colgroup>
                <col style="width:140px">
                <col style="width:*">
            </colgroup>
            <caption>이력 조회</caption>
				<tbody>
					<tr>
	                    <th scope="row">시공일자</th>
	                    <td colspan="3">${result.worktm}</td>
               		 </tr>
					<tr>
						<th scope="row">시공자</th>
                    	<td colspan="3">${result.workuser}</td>
					</tr>
					<tr>
						<th scope="row">이력구분</th>
						<td colspan="3">${result.workCdeNam}</td>
					</tr>
					<tr>
						<th scope="row">시공내용</th>
                    	<td colspan="3">${result.workBg}</td>

					</tr>

				</tbody>
			</table>
			<div class="btn_wrap">
                <span class="area_l">
                   <a href="#this" id="list" class="btn sml blue l">목록</a>
                </span>
                <span class="area_r">
                   <a href="#this" id="update" class="btn sml blue_tp1 r">수정</a>
                   <a href="#this" id="deleteWork" class="btn sml gray_tp1 r">삭제</a>
                </span>
            </div>
		</form>

<script>
$(function() {
	$('#workBg').val("${result.workBg}".ltrim().split('<br>').join("\r\n"));

	var workCde = new AjaxComboBox("#workCde", {
		url				: "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=WORK_CDE&refCd=CHG_HISTORY'/>",
		baseItem	: {name: "--- 선택 ---", value: ""}
	}).load();

	var $frm = $("#selectInfoFrm");

	$("#update").click(function(){
		//$("#method").val("update");
		//$("#selectInfoFrm").submit();
		
		$("#selectInfoFrm input[name=method]").val("update");
		
		var modal = $(this).closest('ons-modal');
		var dataList = $('#selectInfoFrm').formToArray();
		var obj = {};
		for(var i in dataList){
			obj[dataList[i].name] = dataList[i].value;
		}
		
		var a = document.getElementById('myNavigator');
		a.pushPage('subModalPage3.html').then(function(){
			modal.find('.history-modify').load('${context}/fclts/crudPopW.do', obj, function(){
				console.log(this);
			})
		});
		
	});
	
	$("#deleteWork").click(function() {
		if(confirm("삭제하시겠습니까?")) {
			var modal = $(this).closest('ons-modal');
			var options = {
				url				: "${context}/fclts/deleteWorkList.json",
				type			: "post",
				dataType		: "json",
				success			:  function(json) {
					if(json.cnt == 1){
						alert("정상적으로 삭제되었습니다.");
						
						var url = "${context}/fclts/workListPopW.do?mgrnu="+$("#selectInfoFrm input[name=mgrnu]").val()+"&method=select" + "&facId="+$("#selectInfoFrm input[name=facId]").val() + "&mgrnuIdx="+$("#selectInfoFrm input[name=mgrnuIdx]").val();
						
						document.querySelector('#myNavigator').resetToPage('subModalPage1.html').then(function(){
							modal.find('.modal-content').load(url, {}, function(){
								console.log(this);
							})
						});
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
});

</script>

