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
<c:if test="${a.writerId != loginedMemberId }">
	<script>alert('글 수정에 대한 권한이 없습니다.'); location.href = '../home/main';</script>
</c:if>
	<form action="doModify" method="POST" class="write-form form1"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">카테고리 선택</div>
			<div class="input">
				<select name="cateItemId">
				<c:forEach items="${cateItems}" var="c">
					<c:if test="${c.id == a.cateItemId }">
						<option value="${c.id }" selected="selected">${c.name }</option>
					</c:if>
					<c:if test="${c.id != a.cateItemId }">
						<option value="${c.id }">${c.name }</option>
					</c:if>
				</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="label">게시물 번호</div>
			<div class="input">
				<input name="id" type="text" value="${a.id }" readonly/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" placeholder="" value="${a.title }"/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<input type="hidden" name="body" />
			<script type="text/x-template" id="origin1" style="display:none;">${a.bodyForXTemplate}</script>
  			<div id="editor1" style="width:100%"></div>
		</div>
		<div class="form-row">
			<div class="input">
				<input type="submit" value="수정" />
			</div>
		</div>
		<script>
			var editor1 = new toastui.Editor({
				el : document.querySelector("#editor1"),
				height : "600px",
				initialEditType : "markdown",
				previewStyle : "vertical",
				initialValue : $('#origin1').html().trim().replace(/<!--REPLACE:script-->/gi, 'script'),
				plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
						youtubePlugin, replPlugin, codepenPlugin ]
			});
		</script>
	</form>
	<button onclick="history.back()" style="width: 50px; height: 40px; display: inline-block; width:100%; margin: 20px 0;">
	취소
	</button>
</div>
<%@ include file="/jsp/part/foot.jspf"%>