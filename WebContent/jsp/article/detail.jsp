<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article a = (Article) request.getAttribute("a");
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>

<style>
.article-list-box-1 td {
	text-align: center;
}

img {
	width: 500px;
}

.another-post {
	position: absolute;
	padding: 5px;
	margin: 20px;
	text-align: center;
	border: 2px solid #bff038;
}

.another-post:hover {
	background: #bff038;
	transition: .5s;
}

.con {
	position: relative;
}

.left {
	left: 0;
}

.right {
	right: 0;
}

.detail-article {
	border: 1px solid #ccc;
	padding: 20px 30px;
}

.detail-article>div:nth-child(2) {
	margin: 10px 0;
	color: #949494;
}

.article-body {
	border-top: 5px double #ccc;
	margin-top: 20px;
	padding-top: 10px;
}

/* 200707 */
.toast-youtube-embed {
	background-color: red;
}

.toast-youtube-embed {
	position: relative;
}

.ratio-16-9::after {
	content: "";
	display: block;
	padding-top: 56.25%;
}

.ratio-1-1::after {
	content: "";
	display: block;
	padding-top: 100%;
}

.abs-full {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}
</style>

<!-- ============================ -->
<!-- 제이쿼리 로딩 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

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

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<!-- ============================ -->

<script
	src="${pageContext.request.contextPath}/resource/js/article/detail.js"></script>

<div class="con">
	<div class="con detail-article">
		<h1><%=a.getId()%>
			|
			<%=a.getTitle()%></h1>
		<div>
			등록날짜 :
			<%=a.getRegDate()%></div>
		<div class="article-body">

			<script type="text/x-template" id="origin1" style="display: none;"><%="\n" + a.getBody() + "\n"%></script>
			<%-- <div id="origin1" style="display: none"><%=a.getBody()%></div> --%>
			<div id="viewer1"></div>

			<script type="text/javascript">
				var editor1__initialValue = $('#origin1').html();
				var editor1 = new toastui.Editor({
					el : document.querySelector('#viewer1'),
					initialValue : editor1__initialValue,
					viewer : true,
					plugins: [toastui.Editor.plugin.codeSyntaxHighlight, youtubePlugin, replPlugin, codepenPlugin]
				});
			</script>

		</div>

	</div>
	<div class="con">
		<%
			for (int i = 0; i < articles.size(); i++) {
				if (a.getId() == articles.get(i).getId()) {
					if (i != 0) {
		%>
		<div class="another-post left">
			<a
				href="./detail?cateItemId=<%=a.getCateItemId()%>&id=<%=articles.get(i - 1).getId()%>">이전글</a>
		</div>
		<%
			}
				}
			}
		%>

		<%
			for (int i = 0; i < articles.size(); i++) {
				if (a.getId() == articles.get(i).getId()) {
					if (i != articles.size() - 1) {
		%>
		<div class="another-post right">
			<a
				href="./detail?cateItemId=<%=a.getCateItemId()%>&id=<%=articles.get(i + 1).getId()%>">다음글</a>
		</div>
		<%
			}
				}
			}
		%>
	</div>
</div>
<%@ include file="/jsp/part/foot.jspf"%>