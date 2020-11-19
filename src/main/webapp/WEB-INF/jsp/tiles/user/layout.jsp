<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>교통안전시설물관리시스템</title>
<tiles:insertAttribute name="header" />
</head>

<body style="overflow-x:auto; overflow-y:auto;">
<tiles:insertAttribute name="side" ignore="true" />
<template id="content.html">
	<ons-page id="mainPage">
		<tiles:insertAttribute name="topMenu" ignore="true" />
		<tiles:insertAttribute name="content" />
		<tiles:insertAttribute name="footer" ignore="true" />
	</ons-page>
</template>
</body>
</html>