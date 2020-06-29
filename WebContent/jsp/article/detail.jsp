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
</style>
<%-- <!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
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
	src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

<script
	src="${pageContext.request.contextPath}/resource/js/home/detail.js"></script> --%>

</head>
<body>
	<h1 class="con">게시물 상세보기</h1>

	<div class="article-list-box-1 con">
		<div>
			번호 :
			<%=a.getId()%>
		</div>
		<div>
			등록날짜 :
			<%=a.getRegDate()%></div>
		<div>
			갱신날짜 :
			<%=a.getUpdateDate()%></div>
		<div>
			제목 :
			<%=a.getTitle()%></div>
		<div>
			내용 :
			<%=a.getBody()%></div>
		<%
			for (int i = 0; i < articles.size(); i++) {
				if (a.getId() == articles.get(i).getId()) {
					if (i != 0) {
		%>
		<div>
			<a href="./detail?id=<%=articles.get(i - 1).getId()%>">이전글</a>
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
		<div>
			<a href="./detail?id=<%=articles.get(i + 1).getId()%>">다음글</a>
		</div>
		<%
			}
				}
			}
		%>
	</div>
	<%@ include file="/jsp/part/foot.jspf"%>