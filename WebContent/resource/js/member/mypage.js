let joinFormSubmitted = false;
function submitJoinForm(form) {
	if (joinFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}

	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length != 0) {
		form.loginPw.value = sha256(form.loginPw.value);
	}

	form.submit();
	joinFormSubmitted = true;
}