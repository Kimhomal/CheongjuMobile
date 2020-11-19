<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<c:choose>
    <c:when test="${params.fileYn == 'Y'}">
        <div class="bxslider2">
            <c:forEach var="fileList" items="${fileList}" varStatus="status">
                <div class="photoDel">
                    <img src="<c:url value='/fclts/getFacImageView.do?mgrnu=${fileList.mgrnu}&atchFileId=${fileList.atchFileId}&sn=${fileList.sn}&gbn=fclts'/>" data-target="${fileList.sn}" name="vImg" id="vImg" alt="사진이미지" width="320px" height="158px"/>
                    <a href="#this" class="btn btn_del_tp1" data-target="${fileList.sn}"></a>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
<!--         <li> -->
            <div class="tit"></div>
            <div class="cont">
                첨부된 사진이 없습니다.
            </div>
<!--         </li> -->
    </c:otherwise>
</c:choose>
<script>
$(function() {

	$(".btn_del_tp1").click(function(){
		$("#delFile").val("Y");  // 파일을 삭제 했을때 delFile 구분값 Y를 준다.
		delFileId.push($(this).attr("data-target")); // 삭제한 파일 Id 배열에 넣어줌
		$(this).parent().remove();   // 클릭한 객체의 부모 태그 제거
		fileIdx--;  // 최대 파일 첨부 갯수 제한 값 --
		bxslider.reloadSlider();
		if(bxslider.getSlideCount()== 0){
			$(".bx-wrapper").remove();
		}


	});


});
</script>