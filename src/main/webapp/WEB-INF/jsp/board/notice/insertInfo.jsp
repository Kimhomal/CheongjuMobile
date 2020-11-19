<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form id="insertFrm" name="insertFrm" method="post" enctype="multipart/form-data">
<input type="hidden" id="method" name="method" value="${params.method}" />
<input type="hidden" id="fileYn" name="fileYn" value="N" />

<h2 class="h2_tp1">공지사항</h2>
                    <p class="subTit">교통안전시설정보 홈페이지의 공지사항 안내 게시판입니다.</p>

                    <div class="board_tp1 mg_t25">
                        <div class="cnt txt_blue_tp1">* 으로 표시된 항목은 필수입력 항목입니다.</div>
                        <table class="tbl_row_tp1">
                            <colgroup>
                                <col style="width:140px">
                                <col style="width:*">
                            </colgroup>
                            <caption>공지사항 등록</caption>

                            <tbody>
                                <tr>
                                    <th scope="row"><span class="txt_red necessary">*</span> 제목</th>
                                    <td><input id="subject" name="subject" type="text" class="size510" value=""></td>
                                </tr>
                                <%--<tr>--%>
                                    <%--<th scope="row"><span class="txt_red necessary">*</span>전화번호</th>--%>
                                    <%--<td><input type="text" id="telNum" name="telNum" class="size510" value=""></td>--%>
                                <%--</tr>--%>
                                <tr>
                                    <th scope="row"><span class="txt_red necessary">*</span>내용</th>
                                    <td><textarea name="ctt" id="ctt" cols="30" rows="10" class="size510"></textarea></td>
                                </tr>
                                <tr>
                                    <th scope="row">파일첨부</th>
                                    <td>
                                        <div class="inp_wrap"><input type="file" id="fileUpload" name ="fileUpload"><a href="#this" class="btn btn_plus"></a></div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- btn_wrap -->
                    <div class="btn_wrap tp1">
                        <a href="#this" id="save" class="btn sml blue">등록완료</a>
                        <a href="#this" id="cancel" class="btn sml gray">취소</a>
                    </div>
</form>                    
 

 <script>
 
 var fileIdx = 1;
 var delIdx = new Array();
 
 $(function() {
	 
	 setAddFileEvt(); // 파일 추가 및 삭제 이벤트 부여
	 
	 var $frm = $("#insertFrm");

		$("#save").click(function(){

			if(!$frm.isEmptyAlertForm()) return;

	        if( $("#fileUpload").val() != undefined && $("#fileUpload").val() != "" ){
	            var ext = $("#fileUpload").val().split(".").pop().toLowerCase();
	            if($.inArray(ext, ["pdf", "xlsx", "xls", "doc", "docx", "hwp"]) == -1) {
	                alert("pdf, xlsx, xls, doc, docx, hwp 파일만 업로드할 수 있습니다.");
	                return;
	            }
	        }

			if(confirm("저장하시겠습니까?")) {

				$("input[type='file']").each(function(){
					if($(this).val() != ""){
						$("#fileYn").val("Y");
						return false;
					}
				});

				var options = {
					url				: "${context}/board/notice/insertInfo.json",
					type			: "post",
					dataType		: "json",
					success			:  function(json) {
						if(json.respFlag == "Y"){
							alert("정상적으로 저장되었습니다.");
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
		});
		
		$("#cancel").click(function(){
			location.href="${context}/board/notice/selectNoticeList.do";
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