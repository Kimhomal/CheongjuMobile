<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form action="${context}/board/notice/crudInfo.do" name="updateFrm" id="updateFrm" method="post">
	<input type="hidden" id="noticeid" name="noticeid" value="${result.seq}">
	<input type="hidden" id="method" name="method" value="${params.method}">
	<input type="hidden" id="fileYn" name="fileYn" />
	<input type="hidden" id="preFileYn" name="preFileYn" value="${params.fileYn}" />
	<input type="hidden" id="delFile" name="delFile" value="N"/>
	<input type="hidden" id="delFileId" name="delFileId" value=""/>


<h2 class="h2_tp1">공지사항</h2>
                    <p class="subTit">교통안전시설정보 홈페이지의 공지사항 안내 게시판입니다.</p>

                    <div class="board_tp1 mg_t25">
                        <table class="tbl_row_tp1">
                            <colgroup>
                                <col style="width:140px">
                                <col style="width:*">
                            </colgroup>
                            <caption>공지사항</caption>

                            <tbody>
                                <tr>
                                    <th scope="row">제목</th>
                                    <td><input type="text" class="size510" id="subject" name="subject" value="${result.subject}"></td>
                                </tr>
                                <tr>
                                    <th scope="row">등록자</th>
                                    <td><input type="text" class="size510" id="writer" name="writer" value="${result.writerNm}" readonly="readonly"></td>
                                </tr>
                                <tr>
                                    <th scope="row">전화번호</th>
                                    <td><input type="text" class="size510" id="telNum" name="telNum" value="${result.telNum}"></td>
                                </tr>
                                <tr>
                                    <th scope="row">내용</th>
                                    <td class="cont">
                                    <textarea id="ctt" name="ctt" cols="30" rows="10" class="size510"></textarea>
                                    </td>
                                </tr>
                                <c:choose>
									<c:when test="${params.fileYn == 'Y'}">
										<tr>
											<th scope="row">파일첨부</th>
											<td>
											<c:forEach var="fileList" items="${fileList}" varStatus="status">
													<div class="inp_wrap">${fileList.oglNam}<a href="#this" class="btn btn_del_tp1" data-target="${fileList.fileSeq}"></a></div>
											</c:forEach>
											<c:choose>
												<c:when test="${fn:length(fileList) == 3}">
													<div  id='nonFile' class='inp_wrap'>더 이상 파일을 첨부할 수 없습니다.</div>
												</c:when>
												<c:otherwise>
													<div class="inp_wrap"><input type="file" id="fileUpload" name ="fileUpload" accept=".pdf, .xlsx, .xls, .doc, .docx, .hwp"><a href="#this" class="btn btn_plus"></a></div>
													<span class="fs12"><em class="txt_red">※ 업로드 가능한 파일 (PDF, XLSX, XLS, DOC, DOCX, HWP)</em></span>
												</c:otherwise>
											</c:choose>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<th scope="row">파일첨부</th>
											<td>
												<span class="fs12"><em class="txt_red">※ 업로드 가능한 파일 (PDF, XLSX, XLS, DOC, DOCX, HWP)</em></span>
												<div class="inp_wrap"><input type="file" id="fileUpload" name ="fileUpload" accept=".pdf, .xlsx, .xls, .doc, .docx, .hwp"><a href="#this" class="btn btn_plus"></a></div>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
                            </tbody>
                        </table>
                    </div>

                    <!-- btn_wrap -->
                    
                    <div class="btn_wrap tp1">
                        <a href="#this" id="update" class="btn sml blue">수정완료</a>
                        <a href="#this" id="list" class="btn sml gray">취소</a>
                    </div>
                    </form>
<script>

var fileIdx = Number("${fn:length(fileList)}") != 3 ?Number("${fn:length(fileList)}") + 1 : Number("${fn:length(fileList)}");
var delIdx = new Array();
var delFileId = new Array(); // 삭제된 파일 키값이 담길 전역 변수

$(function() {
	
	$('#ctt').val("${result.ctt}".ltrim().split('<br>').join("\r\n"));
	
	$(".btn_del_tp1").click(function(){

		$("#delFile").val("Y");  // 파일을 삭제 했을때 delFile 구분값 Y를 준다.
		delFileId.push($(this).attr("data-target")); // 삭제한 파일 Id 배열에 넣어줌
		fileIdx--;  // 최대 파일 첨부 갯수 제한 값 --

		if($("input[type=file]").length == 0){
			var $parentTr = $(this).parents("td");
			fileIdx++;   // 최대 파일 첨부 갯수 제한 값 ++
			setFileTag($parentTr,fileIdx,"btn_plus",function(){
				$(this).parent().remove();
				fileIdx--;
			});
			setAddFileEvt();
		} // 파일 태그가 없을 경우 파일 태그 및 이벤트 추가

		$(this).parent().remove();   // 클릭한 객체의 부모 태그 제거

		if($("#nonFile").length > 0){
			$("#nonFile").remove();
		} // 더이상 파일첨부를 할수 없습니다. 메시지 제거
	});
	
	setAddFileEvt(); // 파일 추가 및 삭제 이벤트 부여
	
	var $frm = $("#updateFrm");

	$("#update").click(function(){

		if(!$frm.isEmptyAlertForm()) return;

        if( $("#fileUpload").val() != undefined && $("#fileUpload").val() != "" ){
            var ext = $("#fileUpload").val().split(".").pop().toLowerCase();
            if($.inArray(ext, ["pdf", "xlsx", "xls", "doc", "docx", "hwp"]) == -1) {
                alert("pdf, xlsx, xls, doc, docx, hwp 파일만 업로드할 수 있습니다.");
                return;
            }
        }
        
        if(confirm("수정하시겠습니까?")) {
			$("input[type='file']").each(function(){
				if($(this).val() != ""){
					$("#fileYn").val("Y");
					return false;
				}
			}); // 파일 첨부된 내용이 있으면 fileYn에 구분값 Y를 준다.

			if($("#delFile").val() == "Y"){
				$("#delFileId").val(delFileId.join(","));
			} // 파일이 첨부되있었는데 삭제를 했을 경우 delFileId 에 삭제된 파일 키값을 넣어준다.

			var options = {
				url				: "${context}/board/notice/updateInfo.json",
				type			: "post",
				dataType		: "json",
				success			:  function(json) {
					if(json.respFlag == "Y"){
						alert("정상적으로 수정되었습니다.");
						location.href="${context}/board/notice/crudInfo.do?noticeid="+$("#noticeid").val()+"&method=select";
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
	});

	$("#list").click(function(){
		if(confirm("취소하시겠습니까?")) {
			$("#method").val("select");
	 		$("#updateFrm").submit();
		}
	});
	
});

/**
 * 파일 - 및 + 버튼 눌렀을때 발생하는 이벤트 추가 함수
 */
function setAddFileEvt(){
	$('.btn_plus').off('click');
	$(".btn_plus").click(function(){
		var $parentTr = $(this).parents("td");
		var $tmpTr = null;
		if(fileIdx < 3 ){
			if(delIdx.length != 0) {
				fileIdx++;
				setFileTag($parentTr,delIdx[0],"btn_minus",function(){
					delIdx.push($(this).attr("id"));
					fileIdx--;
				});
				delIdx.splice($.inArray(delIdx[0], delIdx), 1);
			}else{
				fileIdx++;
				setFileTag($parentTr,fileIdx,"btn_minus",function(){
					$(this).parent().remove();
					fileIdx--;
				});
			}
		}else{
			alert("파일 첨부는 3개까지만 가능합니다.");
		}
	});
}

/**
 * 파일 태그 동적 추가
 * $target : 파일 태그를 추가할 타겟 태그
	 id : 추가 될 파일 태그 Id로 사용할 전역 변수
	 className : 추가될 파일 태그 대상이 + 및 - 구분 클래스 명
	 evt : + 및 - 버튼 클릭할때 발생할 이벤트
 */
function setFileTag($target,id,className,evt) {
	var $div = $("<div class='inp_wrap'></div>");
	var $a = $("<a href='#this' id='"+id+"' class='btn "+className+"'></a>");
	$a.click(evt);
	$div.append($("<input type='file' id='fileUpload_"+id+"' name='fileUpload_"+id+"' accept='.pdf, .xlsx, .xls, .hwp'>"));
	$div.append($a);
	$target.append($div);
}



</script>