<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page import="com.sbs.java.blog.util.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article a = (Article) request.getAttribute("a");
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<ArticleReply> articleReplies = (List<ArticleReply>) request.getAttribute("articleReplies");
%>

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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/article/detail.css">

<script
	src="${pageContext.request.contextPath}/resource/js/article/detail.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/article/articleReply.js"></script>

<style>
.detail-article>div:nth-child(3) {
	display: inline-block;
}

.detail-article>div:nth-child(4) {
	border: 1px solid black;
	display: inline-block;
}

.detail-article>div:nth-child(4):hover {
	color: red;
	background: #666666;
	transition: background .5s;
}

.detail-article>div:nth-child(5) {
	border: 1px solid black;
	display: inline-block;
}

.detail-article>div:nth-child(5):hover {
	color: red;
	background: #666666;
	transition: background .5s;
	background: #666666;
}

.articleReply {
	border: 1px solid red;
	padding: 10px;
}
</style>
<div class="con">
	<div class="con detail-article">
		<h1><%=a.getId()%>
			|
			<%=a.getTitle()%></h1>
		<div>
			등록날짜 :
			<%=a.getRegDate()%></div>
		<div>
			조회수 :
			<%=a.getHit()%>&nbsp&nbsp&nbsp&nbsp
		</div>
		<div>
			<a href="./revise?id=<%=a.getId()%>">수정</a>
		</div>
		<div>
			<a href="./delete?id=<%=a.getId()%>">삭제</a>
		</div>
		<div class="article-body">

			<script type="text/x-template" id="origin1" style="display: none;"><%="\n" + a.getBody() + "\n"%></script>
			<div id="viewer1"></div>

			<script type="text/javascript">
				var editor1__initialValue = $('#origin1').html();
				var editor1 = new toastui.Editor({
					el : document.querySelector('#viewer1'),
					initialValue : editor1__initialValue,
					viewer : true,
					plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
							youtubePlugin, replPlugin, codepenPlugin ]
				});
			</script>

		</div>
		<div class="articleReply">
			<%
				// 댓글 작성은 로그인 상태여야만 가능
				if (Util.m != null) {
			%>
			<form action="doArticleReply" method="POST" class="comment-form"
				onsubmit="submitArticleReplyForm(this); return false;">
				<div class="form-row">
					<div class="label">댓글</div>
					<div class="input">
						<input name="writerId" type="hidden" value=<%=Util.m.getId()%> readonly /> 
						<input name="articleId" type="hidden" value=<%=a.getId()%> readonly /> 
						<input name="body" type="text" placeholder="댓글을 입력해주세요." />
					</div>
				</div>
				<div class="form-row">
					<div class="input">
						<input type="submit" value="댓글 작성" />
					</div>
				</div>
			</form>
			<%
				}
				if (!articleReplies.isEmpty()) {
					for (ArticleReply ar : articleReplies) {
			%>
			<div>
				<%=ar.getWriterId()%>
				:
				<%=ar.getBody()%>
			</div>
			<%
				}
				} else {
			%>
			<div>첫번째 댓글을 남겨주세요!</div>
			<%
				}
			%>
		</div>
	</div>
	<div class="con another-post-con">
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