<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
		<!-- footer -->
		<div id="footer">
			<!-- partnerSite -->
			<div class="partnerSite">
				<div class="innerArea">
<!-- 					<h3 class="h3_tp2">관련사이트</h3> -->
<!-- 					<ul class="partnerSite_list"> -->
<!-- 						<li> -->
<%-- 						<img src="${context}/images/common/img_partnerSite.png" alt="관련사이트" usemap="#Map_linksite"> --%>
<!-- 						<MAP name=Map_linksite id=Map_linksite> -->
<!-- 							<AREA href="http://seoul.go.kr" shape=rect coords=10,10,97,44 target=_blank alt="서울특별시" onfocus='blur()'> -->
<!-- 							<AREA href="http://gis.seoul.go.kr" shape=rect coords=112,12,202,38 target=_blank alt="서울시GIS" onfocus='blur()'> -->
<!-- 							<AREA href="http://3dgis.seoul.go.kr" shape=rect coords=232,13,317,45 target=_blank alt="서울특별시 3차원 공간정보시스템" onfocus='blur()'> -->
<!-- 							<AREA href="http://120dasan.seoul.go.kr" shape=rect coords=341,11,419,38 target=_blank alt="120 다산콜센터" onfocus='blur()'> -->
<!-- 							<AREA href="http://www.smpa.go.kr" shape=rect coords=442,16,544,42 target=_blank alt="서울지방경찰청" onfocus='blur()'> -->
<!-- 							<AREA href="http://democracy.seoul.go.kr" shape=rect coords=569,15,654,38 target=_blank alt="민주주의서울" onfocus='blur()'> -->
<!-- 							<AREA href="http://www.ngii.go.kr" shape=rect coords=681,14,771,41 target=_blank alt="국토지리정보원" onfocus='blur()'> -->
<!-- 							<AREA href="http://www.juso.go.kr" shape=rect coords=788,13,907,42 target=_blank alt="도로명주소안내" onfocus='blur()'> -->
<!-- 						</MAP> -->
<!-- 						</li> -->
<!-- 			</ul> -->
				</div>
			</div>
			<!-- //partnerSite -->
			<div class="innerArea">
				<div class="logo_f"><img src="${context}/images/common/logo_footer.png" alt="로고"></div>
				<div class="f_info">
					<p class="address">서울특별시 중구 덕수궁길 15 서소문청사 1동 도시교통본부 교통운영과   |   홈페이지 문의 02-2133-1261   |   <a href="javascript:$.popupWindow('https://www.seoul.go.kr/helper/privacy.do?type=www',{scrollbars:0});" title="개인정보처리방침">개인정보처리방침</a></p>
					<p class="copyright">Copyrightⓒ 서울특별시청 All right reserved. </p>
				</div>

				<%--<ul class="cntInfo">--%>
					<%--<li>--%>
						<%--<p class="tit">오늘</p>--%>
						<%--<p id="acsCnt" class="cnt"><c:out value="${viewCnt.acsCnt}"/></p>--%>
					<%--</li>--%>
					<%--<li>--%>
						<%--<p class="tit">전체</p>--%>
						<%--<p id="totCnt" class="cnt"><c:out value="${viewCnt.totCnt}"/></p>--%>
					<%--</li>--%>
				<%--</ul>--%>
			</div>
		</div>
		<!-- //footer -->
	</div>
	<!-- 서울 GNB 영역 (id 는 전체 문서에서 중복이 되지 않으면 됨)-->
	<div id="seoul-common-gnb" style="position: relative;z-index: 999;"></div>

<script>
$(function(){
	//방문자수 가져오기
	$.ajax({
		type 			: 'post',
		url 			: "${context}/main/selectTodayViewCnt.json",
		dataType 	: "json",
		cache		: false,
		success : function(json){
			$("#acsCnt").text(json.acsCnt);
			$("#totCnt").text(json.totCnt);
// 			$("#croAddr").val(json.addr);
		}
	});
});
</script>