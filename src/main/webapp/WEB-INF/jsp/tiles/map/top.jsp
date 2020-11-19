<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<div id="header">
            <div class="logo">
                <img src="${context}/images/mapService/logo_header.png" alt="교통안전시설물관리시스템">
            </div>
            <div class="location">
                <span>현재위치 :</span> <i id="addr"></i>
                <span class="mg_l20">좌표 : </span> <i id="coor"></i>
            </div>
        </div>