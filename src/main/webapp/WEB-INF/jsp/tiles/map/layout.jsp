<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>교통안전시설물관리시스템</title>
<tiles:insertAttribute name="header" />
</head>

<body>
	<tiles:insertAttribute name="side" ignore="true" />
	<template id="content.html">
		<ons-page id="mainPage">
			<tiles:insertAttribute name="topMenu" ignore="true" />
			<tiles:insertAttribute name="content" />
			<tiles:insertAttribute name="footer" ignore="true" />
		</ons-page>
	</template>
	
	<ons-modal class="loading" direction="up">
		<div style="text-align: center">
			<p>
				<ons-icon icon="md-spinner" size="28px" spin></ons-icon> Loading...
			</p>
		</div>
	</ons-modal>
	
	<div class="clone-list" style="display: none;">
		<!-- lnb_panel_cont : #통합검색 -->
		<div class="lnb_panel_cont totalSearch active"></div>
		
	    <!-- lnb_panel_cont #레이어관리 -->
		<div class="lnb_panel_cont layerMng" style="overflow-y: auto;" >
			<div class="search_tp1">
				<div class="panel_cont">
					<p class="exp_tp1 mar_b10">충분히 확대해야 레이어가 보입니다.</p>
					<ul class="layerList accordionMenu">
					</ul>
				</div>
			</div>
		</div>
		<!-- //lnb_panel_cont : 레이어관리-->
	
	
		<!-- lnb_panel_cont #시설물검색 -->
		<div class="lnb_panel_cont facilitiesSearch">
			<div class="search_tp1">
				<select name="facilTeb" id="facilTeb"></select>
				<select name="facilities" id="facilities"></select>
				<select name="choose" id="choose"></select>
				<input type="text" name="searchKeyword" id="searchKeyword" placeholder="관리번호">
				<button type="button" id="searchFacil" class="btn full blue_tp3">검색</button>
			</div>
			<div class="search_result mg_t15">
				<div class="acc_wrap no_active">
					<div class="acc_headar"></div>
					<div class="acc_content active">
						<div class="paging"></div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- lnb_panel_cont #좌표검색 -->
		<div class="lnb_panel_cont croodSearch"></div>
		<!-- //lnb_panel_cont : 좌표검색-->
	</div>
</body>
</html>