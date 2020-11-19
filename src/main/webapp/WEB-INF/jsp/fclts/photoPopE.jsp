<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%--<li>--%>
    <div class="tit">사진첨부</div>
    <div class="cont">
        <span class="fs12"><em class="txt_red">※ 업로드가능파일(PNG, JPG, JPEG)</em></span><br/>
        <input type="file" id="fileUpload" name ="fileUpload" accept=".png, .jpg, .jpeg" class="w90p"><a href="#this" class="btn btn_plus"></a>
    </div>
<%--</li>--%>

<script>


$(function() {
	setAddFileEvt(); // 파일 추가 및 삭제 이벤트 부여

});

/**
 * 파일 - 및 + 버튼 눌렀을때 발생하는 이벤트 추가 함수
 */
function setAddFileEvt(){
	$('.btn_plus').off('click');
	$(".btn_plus").click(function(){
		var $parentTr = $(this).parents("div.cont");
		var $tmpTr = null;
		if(fileIdx < 5 ){
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
			alert("파일 첨부는 5개까지만 가능합니다.");
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
    $div.append($("<input type='file' id='fileUpload_"+id+"' name='fileUpload_"+id+"' class='w90p' accept='.png, .jpg, .jpeg'>"));
	$div.append($a);
	$target.append($div);
}


</script>