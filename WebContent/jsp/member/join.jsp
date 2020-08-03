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
<%-- <script
	src="${pageContext.request.contextPath}/resource/js/member/join.js"></script> --%>

<script>
	var JoinForm__validLoginId = '';
	function submitJoinForm(form) {
		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		if (form.loginId.value != JoinForm__validLoginId) {
			alert('다른 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.nickname.value = form.nickname.value.trim();

		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('로그인 비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('로그인 비번확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}

		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('로그인 비번확인이 일치하지 않습니다. form.loginPw.value: '
					+ form.loginPw.value + ', form.loginPwConfirm.value: '
					+ form.loginPwConfirm.value);
			form.loginPwConfirm.focus();
			return;
		}

		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.loginPwConfirm.value = '';

		form.submit();
	}

	function JoinForm__checkLoginIdDup(input) {
		var form = input.form;

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			return;
		}

		// ajax 페이지 유지한 상태로 (미니)폼이 감.
		$.get(
		// /s/member/getLoginIdDup만 날라감. 일종의 액션
		'getLoginIdDup', {
			loginId : form.loginId.value
		}, function(data) { // 미니폼을 보내고 응답이 왔을 때 후속조치
			var $message = $(form.loginId).next();

			if (data.resultCode.substr(0, 2) == 'S-') {
				$message.empty().append(
						'<div style="color:green; font-size: .9rem;">'
								+ data.msg + '</div>');
				JoinForm__validLoginId = data.loginId;
			} else {
				$message.empty().append(
						'<div style="color:red; font-size: .9rem;">' + data.msg
								+ '</div>');
				JoinForm__validLoginId = '';
			}
		},
		// MemberController에서도 json 양식을 일치시켜야 함. > 후에 잭슨으로 바꿀 예정
		'json');
	}
</script>


<h1>
	<div class="flex flex-jc-c">회원가입</div>
</h1>
<div class="join-form-box con">
	<form action="doJoin" method="POST" class="join-form"
		onsubmit="submitJoinForm(this); return false;">
		<input type="hidden" name="loginPwReal" />
		<div class="form-row">
			<div class="label">ID</div>
			<div class="input">
				<input onkeyup="JoinForm__checkLoginIdDup(this)" name="loginId"
					type="text" placeholder="ID" autocomplete="off" />
				<div class="message-msg"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input name="name" type="text" placeholder="이름" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">닉네임</div>
			<div class="input">
				<input name="nickname" type="text" placeholder="닉네임" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input name="email" type="email" placeholder="email" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">PW</div>
			<div class="input">
				<input name="loginPw" type="password" placeholder="비밀번호" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">PW 확인</div>
			<div class="input">
				<input name="loginPwConfirm" type="password"
					placeholder="비밀번호를 다시 입력하세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="input">
				<input type="submit" value="회원가입" />
			</div>
		</div>
	</form>
</div>
<%@ include file="/jsp/part/foot.jspf"%>