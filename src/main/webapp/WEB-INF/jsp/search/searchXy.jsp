<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<form name="frmCoordSearch" id="frmCoordSearch" method="post" onsubmit='return false;'>
    
                        <!-- search -->
                        <div class="search_tp1">
                        	<select name="coordinate" id="coordinate" title="좌표계" class="coordinate">
                                <option value="EPSG:4326" selected>EPSG:4326</option>
                        		<option value="EPSG:5186">EPSG:5186</option>
                        	</select>
                            <input type="text" name="xce" id="xce" placeholder="X좌표">
                            <input type="text" name="yce" id="yce" placeholder="Y좌표">
                            <button type="button" id="searchXy" class="btn full blue_tp3">검색</button>
                        </div>
                        <!-- //search -->

</form>

<script>
var searchList;

$(function(){
	
	
	var $frm = $("#frmCoordSearch");
	
	$("#searchXy").click(function(){
		var xce = $("#xce").val();
		var yce = $("#yce").val();
		
		if(xce == "") {
			alert("X 좌표를 입력해주세요.");
			return;
		}
		if(yce == "") {
			alert("Y 좌표를 입력해주세요.");
			return;
		}
		
		mapMaker.mapAction.setMakerCenter([xce, yce],7,{oldProj : $("#coordinate").val()});
		
		managePanels._modalList.coordSearch.hide()
		});

	$frm.find("input[type=text]").on("keyup", function(evt){
		if (evt.keyCode == 13) {
			evt.preventDefault();
			$("#searchXy").trigger("click");
		}
	});
});


</script>