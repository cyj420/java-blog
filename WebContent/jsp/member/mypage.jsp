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

.join-form-box .form-row>.input>input {
	display: block;
	width: 100%;
	box-sizing: border-box;
	padding: 10px;
}

.color-red {
	color: red;
}
.authButton {
	color: red;
}
.authButton:hover {
	color: blue;
}
.for-button{
	text-align: center;
}
.for-button>button{
	margin: 20px;
	height: 40px;
}
</style>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/member/mypage.js"></script>
<h1>
	<div class="flex flex-jc-c">회원 정보</div>
</h1>
<div class="join-form-box con">
	<form action="authMail" method="POST" class="join-form"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">ID</div>
			<div class="input">
				<input name="loginId" type="text"
					value="${loginedMember.id}"
					readonly />
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" type="text"
					value="${loginedMember.name}"
					readonly />
			</div>
		</div>
		<div class="form-row">
			<div class="label">닉네임</div>
			<div class="input">
				<input name="nickname" type="text"
					value="${loginedMember.nickname}"
					readonly />
			</div>
		</div>
		<div class="form-row">
			<c:if test="${loginedMember.mailAuthStatus == 0}">
		    	<div class="label">
					이메일
				</div>
			</c:if>
	 
			<c:if test="${loginedMember.mailAuthStatus != 0}">
				<div class="label color-red">
					이메일<br>(인증완료)
				</div>
			</c:if>
			<div class="input">
				<input name="email" type="email"
					value="${loginedMember.email}"
					readonly />
			</div>
			<c:if test="${loginedMember.mailAuthStatus == 0}">
		    	<button type="submit" style="position: absolute; left: 160px;margin-left: 50%;">인증 메일 보내기</button>
			</c:if>
		</div>
	</form>
</div>
<div class="for-button">
	<button onclick="location.href='./myPageModify'">수정하기</button>
</div>
<%@ include file="/jsp/part/foot.jspf"%>