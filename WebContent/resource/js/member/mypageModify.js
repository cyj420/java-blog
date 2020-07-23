let joinFormSubmitted = false;
function submitJoinForm(form) {
	if (joinFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}
	
	form.loginPw.value = form.loginPw.value.trim();
	if (form.loginPw.value.length == 0) {
		alert('기존 비밀번호를 입력해주세요.');
		form.loginPw.focus();
		return;
	}

	form.newPw.value = form.newPw.value.trim();
	if (form.newPw.value.length != 0) {
		form.newPw.value = sha256(form.newPw.value);
	}
	
	form.loginPw.value = sha256(form.loginPw.value);

	form.submit();
	joinFormSubmitted = true;
}