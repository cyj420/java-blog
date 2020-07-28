function submitJoinForm(form) {
	form.loginId.value = form.loginId.value.trim();
	if (form.loginId.value.length == 0) {
		alert('아이디를 입력해주세요.');
		form.loginId.focus();
		return;
	}

	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length == 0) {
		alert('비밀번호를 입력해주세요.');
		form.loginPw.focus();
		return;
	}

	form.loginPwReal.value = sha256(form.loginPw.value);
	form.loginPw.value = '';
//	form.loginPw.value = sha256(form.loginPw.value);
//	form.loginPwConfirm.value = sha256(form.loginPwConfirm.value);
//	form.loginPw.value = '';

	form.submit();
}