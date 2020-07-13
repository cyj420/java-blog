function submitArticleReplyForm(form) {
	form.body.value = form.body.value.trim();
	  if ( form.body.value.length == 0 ) {
	    alert('댓글 내용을 입력해주세요.');
	    form.body.focus();
	    return;
	  }
	  form.submit();
}