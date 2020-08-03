<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/jsp/part/head.jspf"%>

</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/article/list.css">
<body>
	<!-- 카테고리 리스트 시작 -->
	<div class="article-list-box-1 con table-box category-list">
		<c:forEach items="${cateItems}" var="c">
			<c:if test="${ c.id == param.cateItemId}">
				<div class="category-name selected-category">
					<a href="./list?cateItemId=${c.id}&page=1">${c.name}</a>
				</div>
			</c:if>
			<c:if test="${c.id != param.cateItemId}">
				<div class="category-name">
					<a href="./list?cateItemId=${c.id}&page=1">${c.name}</a>
				</div>
			</c:if>
		</c:forEach>
	</div>
	<!-- 카테고리 리스트 끝 -->

	<!-- 게시물 리스트 시작 -->
	<c:if test="${cateItemId == 0}">
		<h1 class="con">
			📂 전체 게시물 리스트
			<c:forEach var="i" begin="1" end="5" step="1">
			</c:forEach>
		</h1>
	</c:if>
	<c:if test="${cateItemId != 0}">
		<h1 class="con">
			📋 ${cateItems[cateItemId - 1].name}
			<c:if test="${searchKeyword.trim().length()} == 0">
				(${articleService.getArticlesByCateItemId(cateItemId).length()}?????)
			</c:if>
		</h1>
		<div class="con back-to-list">
			<a href="./list" style="margin-left: 30px;">전체 게시판으로 돌아가기&#8594;</a>
		</div>
	</c:if>

	<div class="article-list-box-1 con table-box">
		<table class="table article-table">
			<colgroup>
				<col width="50" class="can-delete" />
				<col width="300" />
				<col width="250" class="can-delete" />
				<col width="100" />
				<col width="100" />
			</colgroup>
			<thead>
				<tr>
					<th class="can-delete">No.</th>
					<th>제목</th>
					<th class="can-delete">등록날짜</th>
					<th>작성자</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${articles}" var="article">
					<tr>
						<td class="can-delete">${article.id}</td>
						<td class="text-align-left"><a
							href="./detail?cateItemId=${article.cateItemId}&id=${article.id}">${article.title}</a></td>
						<td class="can-delete">${article.regDate}</td>
						<td>${article.extra.writer }</td>
						<td>${article.hit}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- 페이징 -->
	<div class="con paging">
		<c:if test="${page > 1 }">
			<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${page-1}">
			◀
			</a>
			<c:forEach var="i" begin="1" end="${fullPage}" step="1">
			</c:forEach>
		</c:if>
			<c:if test="${page == i }">
				<span>
					<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${i}"
					style="font-weight: bold; color: red">[${i}]
					</a>
				</span>
			</c:if>
			<c:forEach var="i" begin="1" end="${fullPage}" step="1">
				<c:if test="${page == i }">
					<span>
						<a class="selected-page" 
						href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${i}">[${i }]
						</a>
					</span>
				</c:if>
				<c:if test="${page != i }">
					<span>
						<a class="not-selected-page" 
						href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${i}">[${i }]
						</a>
					</span>
				</c:if>
			</c:forEach>
		<c:if test="${page < fullPage }">
			<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${page + 1}">▶</a>
		</c:if>
	</div>
	<!-- 게시물 리스트 끝 -->
	<!-- 검색 시작 -->
	<div class="con search-box flex flex-jc-c">

		<form action="${pageContext.request.contextPath}/s/article/list">
			<input type="hidden" name="page" value="1" /> <input type="hidden"
				name="cateItemId" value="${param.cateItemId}" /> <input
				type="hidden" name="searchKeywordType" value="title" /> <input
				type="text" name="searchKeyword" value="${param.searchKeyword}" />
			<button type="submit">검색</button>
		</form>

	</div>
	<!-- 검색 끝 -->
	<%@ include file="/jsp/part/foot.jspf"%>