<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<style>
.join-form-box {
	width: 300px;
	font-size: 1.2rem;
	margin-top: 30px;
}

.join-form-box .form-row {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.join-form-box .form-row:not(:first-child) {
	margin-top: 10px;
}

.join-form-box .form-row:last-child {
	justify-content: center;
}

.join-form-box .form-row>.input>input {
	display: block;
	width: 100%;
	box-sizing: border-box;
	padding: 10px;
}
.color-red {
	color:red;
}
.authButton:hover{
	color:red;
}
</style>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/member/mypageModify.js"></script>
<h1>
	<div class="flex flex-jc-c">회원 정보 수정</div>
</h1>
<div class="join-form-box con">
	<form action="doMyPage" method="POST" class="join-form"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">ID</div>
			<div class="input">
				<input name="loginId" type="text" placeholder="${loginedMember.loginId}" disabled="disabled"/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" type="text" placeholder="${loginedMember.name}" disabled="disabled"/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">닉네임</div>
			<div class="input">
				<input name="nickname" type="text" placeholder="${loginedMember.nickname}"/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input name="email" type="email" placeholder="${loginedMember.email}" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">변경할 PW</div>
			<div class="input">
				<input name="newPw" type="password" placeholder="새로운 비밀번호" />
			</div>
		</div>
		<div class="form-row">
			<div class="label" style="color:red;">*PW 확인</div>
			<div class="input">
				<input name="loginPw" type="password" placeholder="기존 비밀번호" />
			</div>
		</div>
		<div class="form-row">
			<div class="input">
				<input type="submit" value="수정" />
			</div>
		</div>
	</form>
	<button onclick="history.back()" style="width: 50px; height: 42px; display: inline-block; position: absolute; bottom:0; left: 40px;margin-left: 50%;">
	취소
	</button>
</div>
<%@ include file="/jsp/part/foot.jspf"%>