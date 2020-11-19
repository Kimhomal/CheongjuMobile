<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<c:choose>
         <c:when test="${params.fileYn == 'Y'}">
<!--          		<li> -->
  			<div class="bxslider">
				<c:forEach var="fileList" items="${fileList}" varStatus="status">
                           <img src="<c:url value='/fclts/getFacImageView.do?mgrnu=${fileList.mgrnu}&atchFileId=${fileList.atchFileId}&sn=${fileList.sn}&gbn=fclts'/>" name="vImg" id="vImg" alt="사진이미지" width="210px" height="158px"/>
                       </c:forEach>
             </div>
<!-- 		</li> -->
	</c:when>
          <c:otherwise>
<!--           	<li> -->
			<div class="tit">사진</div>
			<div class="cont">
				첨부된 사진이 없습니다.
			</div>
<!-- 		</li> -->
	</c:otherwise>
  </c:choose>
<script>
$(function() {
	$("#preFileYn").val("${params.fileYn}")
});



</script>