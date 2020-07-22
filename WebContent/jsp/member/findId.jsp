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
</style>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/member/findId.js"></script>

<h1>
	<div class="flex flex-jc-c">ID 찾기</div>
</h1>
<div class="join-form-box con">
	<form action="doFindId" method="POST" class="join-form"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" type="text" placeholder="이름" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input name="email" type="email" placeholder="email" />
			</div>
		</div>
		<div class="form-row">
			<div class="input">
				<input type="submit" value="ID 찾기" />
			</div>
		</div>
	</form>
</div>
<%@ include file="/jsp/part/foot.jspf"%>