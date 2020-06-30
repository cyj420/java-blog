<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>
<style>
.article-list-box-1 td {
	text-align: center;
}

td>a:hover {
	color: red;
}
</style>
<title>게시물 리스트</title>

</head>
<body>
	<h1 class="con">게시물 리스트</h1>

	<div class="article-list-box-1 con table-box">
		<table>
			<colgroup>
			<col width="50" />
			<col width="150" />
			<col width="150" />
			</colgroup>
			<thead>
				<tr>
					<th>ID</th>
					<th>제목</th>
					<th>등록날짜</th>
					<th>갱신날짜</th>
				</tr>
			<tbody>
				<%
					for (Article article : articles) {
				%>
				<tr>
					<td><%=article.getId()%></td>
					<td class="text-align-left"><a
						href="./detail?id=<%=article.getId()%>"><%=article.getTitle()%></a></td>
						<!-- ./부분은 없어도 ㄱㅊ -->
					<td><%=article.getRegDate()%></td>
					<td><%=article.getUpdateDate()%></td>
				</tr>
				<%
					}
				%>
			</tbody>
			</thead>
		</table>
	</div>
	<%@ include file="/jsp/part/foot.jspf"%>