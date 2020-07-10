<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<style>
.login-form-box {
	width: 300px;
	font-size: 1.2rem;
	margin-top: 30px;
}

.login-form-box .form-row {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.login-form-box .form-row:not(:first-child) {
	margin-top: 10px;
}

.login-form-box .form-row:last-child {
	justify-content: center;
}

.login-form-box .form-row>.input>input {
	display: block;
	width: 100%;
	box-sizing: border-box;
	padding: 10px;
}
</style>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/member/login.js"></script>
<h1>
	<div class="flex flex-jc-c">로그인</div>
</h1>
<div class="login-form-box con">
	<form action="doLogin" method="POST" class="login-form"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">ID</div>
			<div class="input">
				<input name="loginId" type="text" placeholder="ID" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">PW</div>
			<div class="input">
				<input name="loginPw" type="password" placeholder="비밀번호" />
			</div>
		</div>
		<div class="form-row">
			<div class="input">
				<input type="submit" value="로그인" />
			</div>
		</div>
	</form>
</div>
<%@ include file="/jsp/part/foot.jspf"%>