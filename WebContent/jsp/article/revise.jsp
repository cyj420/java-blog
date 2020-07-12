<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article a = (Article) request.getAttribute("a");
	String body = a.getBody();
%>

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
	<form action="doRevise" method="POST" class="write-form form1"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">카테고리 선택</div>
			<div class="input">
				<select name="cateItemId">
					<%-- <%
					/* 이 부분 수정 필요 */
						for (Category c : cateItems) {
							if(a!=null){
								if(c.getId()==a.getCateItemId()){
									%>
									<option value="<%=c.getId()%>"><%=c.getName()%></option>
									<%
								}
							}
							else{
								%>
								<option value="<%=c.getId()%>"><%=c.getName()%></option>
								<%
							}
						}
					%> --%>
					<%
						for (Category c : cateItems) {
					%>
					<option value="<%=c.getId()%>"><%=c.getName()%></option>
					<%
						}
					%>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="label">게시물 번호</div>
			<div class="input">
				<input name="id" type="text" value="<%=a.getId()%>" readonly/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" placeholder="" value="<%=a.getTitle()%>"/>
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<input type="hidden" name="body" />
  			<div id="editor1"></div>
		</div>
		<div class="form-row">
			<div class="label">제출</div>
			<div class="input">
				<input type="submit" value="제출" /> <a href="list">취소</a>
			</div>
		</div>
		<script>
			var editor1 = new toastui.Editor({
				el : document.querySelector("#editor1"),
				height : "600px",
				initialEditType : "markdown",
				previewStyle : "vertical",
				initialValue : "<%=a.getBody()%>",
				plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
						youtubePlugin, replPlugin, codepenPlugin ]
			});
		</script>
	</form>
</div>
<%@ include file="/jsp/part/foot.jspf"%>