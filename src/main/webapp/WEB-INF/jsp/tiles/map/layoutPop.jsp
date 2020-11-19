<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/common/taglibs.jsp" %>

<div class="lp_inner">
<!-- modal_header -->
<div class="lp_header">
	<h1><c:out value="${params.title}" /></h1>
	<a href="#this" class="lp_close modal_close"></a>
</div>
<!-- //modal_header -->

<!-- modal_body -->

	<tiles:insertAttribute name="content" />
<!-- //modal_body -->


</div>

        <script>
            var bxslider = null;
            var fileIdx = Number("${fn:length(fileList)}") !=5 ?Number("${fn:length(fileList)}") + 1 : Number("${fn:length(fileList)}");
            var delIdx = new Array();
            var delFileId = new Array(); // 삭제된 파일 키값이 담길 전역 변수
			var method = "${params.method}";
            $(function(){
            	var facilityId = "${params.facilityId}";

            	if(facilityId != "GT_B001_A" && facilityId != "GT_C113_A" && facilityId != "GT_C116_A" && facilityId != "GT_C120_A"&& facilityId != "GT_C123_L" ){
            		   if(method == "insert"){
                           $("#insertFrm").find(".list_tp8").prepend("<li class='photo'></li>");
                           $(".photo").load(G.baseUrl + "fclts/photoPopE.do");
                       }else{
                           $("#updateFrm").find(".list_tp8").prepend("<li class='photo'></li>");
                           $(".photo").load(G.baseUrl + "fclts/photoPopE.do");
                           $("#selectFrm").find(".list_tp8").prepend("<li class='selectPhoto'></li>");
                           $("#updateFrm").find(".list_tp8").prepend("<li class='updatePhoto'></li>");
                           $(".selectPhoto").load(G.baseUrl + "fclts/selectPhotoPopE.do?mgrnu=${params.mgrnu}", function(){
                           	 $('.bxslider').bxSlider({
                                    mode: 'fade',
                                    slideWidth: 400
                                });
              				});
                           $(".updatePhoto").load(G.baseUrl + "fclts/updatePhotoPopE.do?mgrnu=${params.mgrnu}", function(){
                           	   bxslider = $('.bxslider2').bxSlider({
                                      mode: 'fade',
                                      slideWidth: 400
                                  });
          					});
							var $li = $("<li><div class='tit'>이력 조회</div></li>");
							var $div = $("<div class='cont'></div>");
							var $a = $("<a href='#this' class='btn bor_blue_tp2 full_tp1'>이력관리</a>");
							$a.on("click",function(evt){
								//팝업 여는 소스
								$("#selectFrm").popupWindow("${context}/fclts/workListPopW.do",{id:"workListPopW",scrollbars:0,width:"500",height:"400"});
							});
							$div.append($a);
                           $("#selectFrm").find(".list_tp8").append($li.append($div));
       				}

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
            	}



    });

</script>