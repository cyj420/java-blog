<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

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
.articleReply {
	border: 1px solid #a0a0a0;
	margin: 10px 0;
	padding: 10px;
}

.articleReplyDetail > form{
	display: inline-block;
}
.articleReplyDetail > form:first-child{
	padding-left: 10px;
}
.detail-article>.back-to-category:hover{
	font-weight: bold;
	color: red;
}
</style>

<div class="con">
	<div class="con detail-article">
		<a class="back-to-category" href="./list?cateItemId=${a.cateItemId}&page=1">
			${param.cateItemId}
		</a>
		<h1>${a.id}
			|
			${a.title}</h1>
		<div>
			작성자 : ${a.extra.writer }
		</div>
		<div class="etc">
			등록날짜 :
			${a.regDate}
			
			<c:if test="${a.regDate != a.updateDate}">
				(update : 
				${a.updateDate}
				)
			</c:if>
			
			<br>
			조회수 :
			${a.hit }
		</div>
		
		<c:if test="${loginedMemberId != -1 }">
			<c:if test="${loginedMemberId == a.writerId }">
				<div class="articleModifyAndDelete">
					<a href="./modify?id=${a.id}">수정</a>
				</div>
				<div class="articleModifyAndDelete">
					<a href="./delete?id=${a.id}">삭제</a>
				</div>
			</c:if>
		</c:if>
		
		<div class="article-body">

			<script type="text/x-template" id="origin1" style="display: none;">${a.getBodyForXTemplate()}
			</script>
			<div id="viewer1"></div>

			<script type="text/javascript">
				/* var editor1__initialValue = $('#origin1').html(); */
				var editor1__initialValue = getBodyFromXTemplate('#origin1');
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
				<div class="label">댓글(${articleReplies.size()})</div>
				<c:if test="${loginedMemberId != -1 }">
					<form action="doArticleReply" method="POST" class="comment-form"
						onsubmit="submitArticleReplyForm(this); return false;">
						<div class="form-row"  style="display: inline-block; width:40%;">
							<div class="input">
								<input name="writerId" type="hidden" value=${loginedMemberId} /> 
								<input name="articleId" type="hidden" value=${a.id} />
								<input name="articleCateId" type="hidden" value=${a.cateItemId} />
								<input name="body" type="text" placeholder="댓글을 입력해주세요." autocomplete="off" style="width: 95%;"/>
							</div>
						</div>
						<div class="form-row" style="display: inline-block;">
							<div class="input">
								<input type="submit" value="댓글 작성"/>
							</div>
						</div>
					</form>
				</c:if>
			<c:if test="${articleReplies != null }">
				<c:forEach items="${articleReplies}" var="ar">
					<div class="articleReplyDetail">
						${ar.extra.writer } : ${ar.body } [${ar.updateDate }]
						<c:if test="${loginedMemberId != -1}">
							<c:if test="${loginedMemberId == ar.writerId}">
								<form action="doArticleReplyModify" method="POST" class="comment-form">
									<input name="articleId" type="hidden" value="${a.id}"/>
									<input name="articleCateId" type="hidden" value="${a.cateItemId }"/>
									<input name="articleReplyId" type="hidden" value="${ar.id}"/>
									<input name="articleReplyBody" type="hidden" value="${ar.body}"/>
									<input type="submit" value="수정" />
								</form>
								<form action="doArticleReplyDelete" method="POST" class="comment-form">
									<input name="articleId" type="hidden" value="${a.id}"/>
									<input name="articleCateId" type="hidden" value="${a.cateItemId}"/>
									<input name="articleReplyId" type="hidden" value="${ar.id}"/>
									<input type="submit" value="삭제" />
								</form>
							</c:if>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${articleReplies.size() == 0 }">
				<div>첫번째 댓글을 남겨주세요!</div>
			</c:if>
		</div>
	</div>

	
	<!-- 다른 게시물로 이동 -->
	<div class="con another-post-con">
		<c:forEach var="i" begin="0" end="${articles.size() }" step="1">
			<c:if test="${a.id == articles[i].id }">
				<c:if test="${i != 0 }">
					<div class="another-post left">
						<a href="./detail?cateItemId=${a.cateItemId}&id=${articles[i-1].id}">이전글</a>
					</div>
				</c:if>
			</c:if>
		</c:forEach>

		<c:forEach var="i" begin="0" end="${articles.size() }" step="1">
			<c:if test="${a.id == articles[i].id }">
				<c:if test="${i != articles.size()-1 }">
					<div class="another-post right">
						<a href="./detail?cateItemId=${a.cateItemId}&id=${articles[i+1].id}">다음글</a>
					</div>
				</c:if>
			</c:if>
		</c:forEach>
	</div>
</div>
<%@ include file="/jsp/part/foot.jspf"%>