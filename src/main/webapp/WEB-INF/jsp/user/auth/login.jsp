<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<div class="sub-top-wrap">
	<div class="sub-top">
		<div class="left">
			<button class="ui tertiary icon button" onclick="location.href='<c:url value='/'/>'">
				<i class="angle left icon"></i>
				<i class="home icon"></i>
			</button>
		</div>
		<div class="center">로그인</div>
	</div>
</div>

<div class="ui container mtb-auto">
	<form class="ui form" id="loginForm" name="loginForm" method="post">
		<div class="field">
			<div class="ui left icon input">
				<i class="user icon"></i>
				<input type="text" id="usrId" name="usrId" placeholder="ID" value="admin">
			</div>
		</div>
		<div class="field">
			<div class="ui left icon input">
				<i class="lock icon"></i>
				<input type="password" id="pw" name="pw" onkeydown="JavaScript:enterCheck();" placeholder="Password" value="Git7100!@">
			</div>
		</div>
		<div class="ui fluid large teal submit button" onclick="loginAction();">로그인</div>
		<div class="ui center aligned vertical segment">
			<div class="ui horizontal divided center aligned list">
				<div class="item">아이디 찾기</div>
				<div class="item">비밀번호 찾기</div>
			</div>
		</div>
	</form>
</div>

<script>
$(function() {
	//아이디저장
	if($.cookie("loginId") != undefined){
		$("#usrId").val($.cookie("loginId"));
		$("#loginForm input:checkbox[name='checkId']").attr("checked", true);
	}

    loginAction();
	
});

function enterCheck() {
	if(event.keyCode == 13){
		loginAction();
	}
	
}

function loginAction() {
	$("#loginForm").attr("target", "_self");
	
	var expires = 30;

	var id = $("#usrId").val();
	var pw = $("#pw").val();

	if (id == "" || id == null) {
		alert("아이디를 입력하세요");
	} else if (pw == "" || pw == null) {
		alert("비밀번호를 입력하세요");
	}else {
		var checked = $("#loginForm input:checkbox[name='checkId']").is(":checked");
		if (checked) {
			$.cookie("loginId", id, { path: '/', expires: expires });
		}else {
			$.removeCookie("loginId",{ path: '/'});
		}
		$.ajax({
			type : "post",
			url : "${context}/user/auth/checkLoginUser.json",
			cache : false,
			data : $("#loginForm").serialize(),
			success : function (json, status) {
				var flag = (json.hasOwnProperty("confirm")) ? json.confirm : "N";
				var message = (json.hasOwnProperty("message")) ? json.message : "";
				if (flag == "S"){
					alert(message);
					return;
				}
				if (flag == "Y" && confirm(message) != true) return;
				$("#loginForm").attr("action","${context}/user/auth/loginAction.do");
		    	$("#loginForm").submit();
			},
			error : function () {
				return;
			}
		});
	}
	
}

</script>
