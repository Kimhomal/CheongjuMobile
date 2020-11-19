<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="frmTotalSearch" id="frmTotalSearch" method="post" onsubmit='return false;'>
<input type="hidden" id="ctkMgrnu" name="ctkMgrnu" value="">
<input type="hidden" id="ctkNam" name="ctkNam" value="">

                        <!-- search -->
                        <div class="search_tp1">
                            <input type="text" name="searchKeyword" id="searchKeyword">
                            <button type="button" id="searchComb" class="btn full blue_tp3">검색</button>
                        </div>
                        <!-- //search -->

                        <!-- search_result -->
                        <div class="search_result mg_t15">
                            <div class="acc_wrap">
                                <!-- acc_1 -->
                                <div id="Cross">
                                <div class="acc_headar">
                                    <p class="tit">교차로검색</p>
                                    <p class="cnt">0건</p>
                                </div>
                                <div class="acc_content">
                                    <ul class="list_tp4">
                                    </ul>
                                    <div class="paging">
                                    </div>
                                </div>
                                </div>
                                <!-- //acc_1 -->

                                <!-- acc_2 -->
                                <div id="DaumAddr">
                                <div class="acc_headar">
                                    <p class="tit">주소 검색</p>
                                    <p class="cnt">0건</p>
                                </div>
                                <div class="acc_content">
                                    <ul class="list_tp4">
                                    </ul>
                                    <div class="paging">
									</div>
                                </div>
                                </div>
                                <!-- //acc_2 -->

                                <!-- acc_4 -->
                                <div id="Keyword">
                                <div class="acc_headar">
                                    <p class="tit">건물명 검색</p>
                                    <p class="cnt">0건</p>
                                </div>
                                <div class="acc_content">
                                </div>
                                </div>
                                <!-- //acc_4 -->

</form>

<script>
var searchList;

$(function(){

	var list = new Array();
// 	if("${sessionScope.authResourceVO.M002001001.rMenuId}" == "TRUE"){
		list.push({type:"Cross",name:"교차로 "});
// 	}
		list.push({type: "DaumAddr", name: "주소 "});
		list.push({type: "Keyword", name: "건물명 "});



	var $frm = $("#frmTotalSearch");
	$("#searchComb").click(function(){
		$.each(list, function(idx, json){
			searchList = new SearchList("#"+json["type"], {
				url					: "${context}/search/search"+json["type"]+".json",
				reqData			: {
					name : json["name"],
					searchKeyword : $('#searchKeyword').val(),
					apikey			: "KakaoAK c39050419b19dc7b3b21d781264c19d9"
				},
				onRowClick		: function(rowIdx, evt) { // 로우 클릭 이벤트
					if(json["type"]=="Cross"){
						mapMaker.mapAction.setMoveFromFeature({
							lyrId : evt.currentTarget.getAttribute("layerName"),
							filter: "MGRNU = '" + evt.currentTarget.getAttribute("key")+"'"
						});

					}else if(json["type"]=="DaumAddr" || json["type"] == "Keyword"){
						 var coord = [evt.currentTarget.getAttribute("x"), evt.currentTarget.getAttribute("y")];
 						mapMaker.mapAction.setMakerCenter(coord,7,{oldProj : "EPSG:4326"});
					}else{
						var pos =[evt.currentTarget.getAttribute("posX"), evt.currentTarget.getAttribute("posY")];
						if(pos!=null){
							if(pos.indexOf("")>-1){
								alert("해당 결과에 대한 위치정보가 없습니다.")
								return true;
							}
						}
						mapMaker.mapAction.setMakerCenter(pos,7,{oldProj : "EPSG:5186"});
					}
				
					managePanels._modalList.totalSearch.hide();
				},
				loadComplete	: function(data) {
					setEvent();
				}
				});
			});
		});

	setEvent();

	$frm.find("input[type=text]").on("keyup", function(evt){
		if (evt.keyCode == 13) {
			evt.preventDefault();
			$("#searchComb").trigger("click");
		}
	});
});

function setEvent(){
	var $frm = $("#frmTotalSearch");
	$frm.find(".tit").off("click");
	$frm.find(".tit").on("click", function(event) {
		if($(this).parent().siblings().hasClass("active")){
			$(this).parent().siblings().removeClass("active");
		}
		else{
			$(this).parent().siblings().addClass("active");
		}
	});
}

</script>