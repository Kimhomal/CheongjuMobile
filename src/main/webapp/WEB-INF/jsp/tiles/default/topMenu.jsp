<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<ons-toolbar>
	<div class="left">
		<ons-toolbar-button onclick="document.querySelector('#splitterMenu').open()">
			<ons-icon icon="md-menu"></ons-icon>
		</ons-toolbar-button>
	</div>

	<div class="center">
		<div style="display: flex; height: 100%;">
			<img onclick="location.href='<c:url value='/'/>'" src="<c:url value='/images/common/logo_header.png'/>" alt="Cheongju icon" style="margin: auto;">
		</div>
	</div>
</ons-toolbar>