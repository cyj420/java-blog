<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<style>
.mini-title{
	text-align: center;
}
tr > td {
    text-align: center;
}
</style>
<h1 class="con">Home</h1>
<body>
<div class="con">
	<h2 class="mini-title">HOT(${hotArticles.size()})</h2>
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
			<c:forEach items="${hotArticles}" var="article">
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
		
	<h2 class="mini-title">NEW(${newArticles.size()})</h2>
	<div>
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
			<c:forEach items="${newArticles}" var="article">
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
</div>
</body>
<%@ include file="/jsp/part/foot.jspf"%>