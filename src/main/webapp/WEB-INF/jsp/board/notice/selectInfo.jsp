<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form action="${context}/board/notice/crudInfo.do" name="frmNotice" id="frmNotice" method="post">
	<input type="hidden" id="noticeid" name="noticeid" value="${result.seq}">
	<input type="hidden" id="method" name="method" value="">
</form>

<div class="sub-top-wrap">
	<div class="sub-top">
		<div class="left">
			<button class="ui tertiary icon button" onclick="location.href='<c:url value='/board/notice/selectNoticeList.do'/>'">
				<i class="angle left icon"></i>
				목록
			</button>
		</div>
		<div class="center">공지사항</div>
	</div>
</div>

<div class="ui container">
	<div class="ui vertical segment">
		<div class="ui small header">
			${result.subject}
			<div class="sub header">${result.indate}</div>
		</div>
	</div>
	<div class="ui vertical segment">
		<p><c:out value="${result.ctt}" escapeXml="false"/></p>
	</div>
	<div class="ui vertical segment">
		<div class="ui two buttons">
			<div class="ui labeled icon button"><i class="left chevron icon"></i>이전</div>
			<div class="ui right labeled icon button"><i class="right chevron icon"></i>다음</div>
		</div>
	</div>
</div>
<script>
$(function() {
	
	var $frm = $("#frmNotice");

	$("#update").click(function(){
		$("#method").val("update");
		$("#frmNotice").submit();
	});

	$("#delete").click(function(){
		$frm.find("#method").val("delete");

		if(confirm("삭제하시겠습니까?")) {
			var options = {
				url				: "${context}/board/notice/deleteInfo.json",
				type			: "post",
				dataType		: "json",
				success			:  function(json) {
					if(json.respFlag == "Y"){
						alert("정상적으로 삭제되었습니다.");
                        location.href="${context}/board/notice/selectNoticeList.do";
					} else {
						alert("오류발생, 다시 시도하여 주십시오");
					}
				},
				error : function(response) {
					alert("오류발생, 다시 시도하여 주십시오");
				},
			};

			$frm.ajaxSubmit( options );
		}

		$frm.find("#method").val("select");
	});

	$("#list").click(function(){
		location.href="${context}/board/notice/selectNoticeList.do";
	});
	
});

function fileDownload(path, svFileNam, rlFileNam) {
	location.href = "${context}/board/notice/fileDown.do?path="+path+"&svFileNam=" + svFileNam + "&rlFileNam=" + encodeURI(rlFileNam);
}


</script>