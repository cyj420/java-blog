let joinFormSubmitted = false;
function submitJoinForm(form) {
	if (joinFormSubmitted) {
		alert('처리 중입니다.');
		return;
	}
	form.submit();
	joinFormSubmitted = true;
}