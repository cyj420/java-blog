<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/home/main.css">
<h1 class="con">Home</h1>
<body>
<div class="con">
	<div>HOT(5)</div>
		<c:forEach items="${hotArticles}" var="a">
				<div>${a.id} | <a
					href="./detail?cateItemId=${a.cateItemId}&id=${a.id}">${a.title}</a> | ${a.hit }
				</div>
		</c:forEach>
	<div>NEW(5)</div>
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
					<c:forEach items="${newArticles}" var="a">
						<tr>
							<td class="can-delete">${a.id}</td>
							<td class="text-align-left"><a
								href="./detail?cateItemId=${a.cateItemId}&id=${a.id}">${a.title}</a></td>
							<td class="can-delete">${a.regDate}</td>
							<td>${a.extra.writer }</td>
							<td>${a.hit}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</div>
</body>
<%@ include file="/jsp/part/foot.jspf"%>