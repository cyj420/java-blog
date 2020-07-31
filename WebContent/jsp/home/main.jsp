<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/home/main.css">
<h1 class="con">Home</h1>
<body>
<div class="con">
	<div>HOT(5)</div>
		<table class="main-table">
			<colgroup>
				<col width="50" />
				<col width="250" />
				<col width="100" />
			</colgroup>
			<thead>
				<tr>
					<th class="can-delete">No.</th>
					<th>제목</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${hotArticles}" var="a">
					<tr>
						<td class="can-delete">${a.id}</td>
						<td class="text-align-left"><a
							href="./detail?cateItemId=${a.cateItemId}&id=${a.id}">${a.title}</a></td>
						<td>${a.hit}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	<div>NEW(5)</div>
		<div>
			<table class="main-table">
				<colgroup>
					<col width="50" />
					<col width="300" />
					<col width="250" />
				</colgroup>
				<thead>
					<tr>
						<th class="can-delete">No.</th>
						<th>제목</th>
						<th class="can-delete">등록날짜</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${newArticles}" var="a">
						<tr>
							<td class="can-delete">${a.id}</td>
							<td class="text-align-left"><a
								href="./detail?cateItemId=${a.cateItemId}&id=${a.id}">${a.title}</a></td>
							<td class="can-delete">${a.regDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</div>
</body>
<%@ include file="/jsp/part/foot.jspf"%>