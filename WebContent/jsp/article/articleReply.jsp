<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/highlight.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/styles/default.min.css">

<!-- 하이라이트 라이브러리, 언어 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/css.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/javascript.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/xml.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php-template.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/sql.min.js"></script>

<!-- 코드 미러 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css" />

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/article/write.css">
<script
	src="${pageContext.request.contextPath}/resource/js/article/write.js"></script>
<div class="write-form-box con">
<h1>댓글 수정</h1>
	<form action="doArticleReplyDoModify" method="POST" class="write-form form1">
		<div class="form-row flex-jc-c">
			<div class="label">내용</div>
			<input type="hidden" name="articleId" value="${articleId}"/>
			<input type="hidden" name="articleCateId" value="${articleCateId}"/>
			<input type="hidden" name="arId" value="${articleReplyId}"/>
			<input type="text" name="arBody" value="${articleReplyBody}"/>
		</div>
		<div class="form-row">
			<div class="input">
				<input type="submit" value="제출" 
				style="width: 50px; height: 50px; display: inline-block; bottom:0; margin-left:50%;"/>
			</div>
		</div>
	</form>
	<button onclick="history.back()" style="width: 50px; height: 50px; display: inline-block; position: absolute; bottom:0; left: 60px;margin-left: 50%;">
	취소
	</button>
</div>
<%@ include file="/jsp/part/foot.jspf"%>