<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form action="${context}/board/notice/crudInfo.do" name="frmNotice" id="frmNotice" method="post">
	<input type="hidden" id="noticeid" name="noticeid" value=""> 
	<input type="hidden" id="method" name="method" value="">
</form>

<div class="sub-top-wrap">
	<div class="sub-top">
		<div class="left">
			<button class="ui tertiary icon button" onclick="location.href='<c:url value='/'/>'">
				<i class="angle left icon"></i>
				<i class="home icon"></i>
			</button>
		</div>
		<div class="center">공지사항</div>
	</div>
</div>

<div class="notice-table-wrap"></div>
<!-- //board_pager_wrap -->

<script>
$(function() {
	function ready(am) {
		if (document.readyState != 'loading'){
			am();
		} else {
			document.addEventListener('DOMContentLoaded', am);
		}
	}
	
	ready(function(){
		var listTable  = new ListTable({
			url	: '${context}/board/notice/selectInfoList.json',
			target: '.notice-table-wrap',
			onCellClick: function(id) {
				$('#method').val('select');
				$('#noticeid').val(id);
				$('#frmNotice').submit();
			}
		});
	});
	
	$('.btn_search').on('click',function(){
		searchResultList.reloadList($("#searchFrm").getFormToJsonData());
	});
	
	$("#insertNotice").on('click', function() {
		$("#method").val("insert");
		$("#frmNotice").submit();
	});
});
</script>