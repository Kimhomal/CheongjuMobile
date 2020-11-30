<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<div id="header">
            <div class="innerArea">
                <div class="logo"><a href="/"><img src="${context}/images/common/logo_header.png" alt=""></a></div>
                <!-- gnb_wrap -->
                <div class="gnb_wrap">
                    <ul class="gnb">
                        <li><a href="#" title="">홈</a></li>
                        <li><a href="${context}/user/auth/login.do" title="">로그인</a></li>
                        <li><a href="#" title="">개인정보처리방침</a></li>
                        <li><a href="#" title=""><img src="${context}/images/common/cj_btn.png"></a></li>
                        <li><a href="#" title=""><img src="${context}/images/common/pol_btn.png"></a></li>
                    </ul>
                </div>
                <!-- //gnb_wrap -->
            </div>

            <!-- lnb_wrap -->
            <div class="lnb_wrap">
                <ul class="lnb"></ul>
            </div>
            <!-- //lnb_wrap -->
        </div>
        <!-- //header_wrap -->
        <hr>