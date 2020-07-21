let joinFormSubmitted = false;
function submitJoinForm(form) {
	if (joinFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}
	form.loginId.value = form.loginId.value.trim();
	if (form.loginId.value.length == 0) {
		alert('아이디를 입력해주세요.');
		form.loginId.focus();
		return;
	}

	form.name.value = form.name.value.trim();
	if (form.name.value.length == 0) {
		alert('이름을 입력해주세요.');
		form.name.focus();
		return;
	}

	form.email.value = form.email.value.trim();
	if (form.email.value.length == 0) {
		alert('email을 입력해주세요.');
		form.email.focus();
		return;
	}

	form.submit();
	joinFormSubmitted = true;
}