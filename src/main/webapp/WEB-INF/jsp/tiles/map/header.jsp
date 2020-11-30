<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<link rel="shortcut icon" type="text/css" href="<c:url value='/images/favicon.png'/>">

<link rel="stylesheet" type="text/css" href="<c:url value='/js/plugins/onsenui/css/onsenui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/js/plugins/onsenui/css/onsen-css-components.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/js/plugins/onsenui/css/font_awesome/css/fontawesome.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/js/plugins/semantic/semantic.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/plugins/jquery/jquery.bxslider.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common/common.css'/>">

<link rel="stylesheet" type="text/css" href="<c:url value='/css/map/ol4/ol.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/map/mapService.css'/>">

<script type="text/javascript">
    var G = {};
    G.baseUrl = "${pageContext.request.contextPath}/";
    G.ctxPath = '${pageContext.request.contextPath}';
</script>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>교통안전시설물관리시스템</title>

<script type="text/javascript" src="<c:url value='/js/plugins/jquery/jquery-3.2.1.js'/>"></script>

<script type="text/javascript" src="<c:url value='/js/plugins/jquery/jquery.form.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/plugins/jquery/jquery.cookie.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/plugins/jqueryui/jquery-ui.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/plugins/jquery/jquery.bxslider.min.js'/>"></script>

<script src="<c:url value='/js/plugins/onsenui/js/onsenui.min.js'/>"></script>
<script src="<c:url value='/js/plugins/semantic/semantic.js'/>"></script>

<!-- custom -->
<script src="<c:url value='/js/common/common.js'/>"></script>
<script src="<c:url value='/js/custom/combobox.js'/>"></script>
<script src="<c:url value='/js/custom/search.js'/>"></script>
<script src="<c:url value='/js/custom/board.js'/>"></script>

<!-- map 관련 js -->
<script type="text/javascript" src="<c:url value='/js/common/mapService.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/analysis/turf.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/ol4/gistfile.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/ol4/ol-debug.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/ol4/rAF.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/proj/proj4.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/proj/projection.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/BaseMapConfig.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/MapAction.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/MapEvtMng.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/MapFacilityMng.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/MapLayerMng.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/MapMaker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/orientation.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/mapUi.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/ol-ext/ol-ext.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/map/analysis/jsts.min.js'/>"></script>