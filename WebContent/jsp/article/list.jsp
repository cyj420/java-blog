<%@page import="com.sbs.java.blog.dto.Category"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = null;
	List<Category> categories = null;
	int fullPage = 0;

	if (request.getAttribute("articles") != null) {
		articles = (List<Article>) request.getAttribute("articles");
		fullPage = (int) request.getAttribute("fullPage");
	} else {
		categories = (List<Category>) request.getAttribute("categories");
	}

	System.out.println(fullPage);
	int cateItemId = (int) request.getAttribute("cateItemId");
	int nowPage = (int) request.getAttribute("page");
%>
<style>
.article-list-box-1 td {
	text-align: center;
}

td>a:hover {
	font-weight: bold;
	color: red;
	transition:.3s;
}

span>.not-selected-page:hover {
	color: blue;
}
.paging a:hover{
	color: blue;
}
</style>
</head>
<body>
	<!-- 카테고리 리스트 시작 -->
	<%
		if (articles == null) {
	%>
	<h1 class="con">카테고리 리스트</h1>
	<div class="article-list-box-1 con table-box">
		<table class="table">
			<colgroup>
				<col width="10%" />
				<col width="50%" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>게시판</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Category c : categories) {
				%>
				<tr>
					<td><%=c.getId()%></td>
					<td><a href="./list?cateItemId=<%=c.getId()%>&page=1"><%=c.getName()%></a></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
	<%
		}
	%>
	<!-- 카테고리 리스트 끝 -->

	<!-- 게시물 리스트 시작 -->
	<%
		if (articles != null) {
	%>
	<h1 class="con">게시물 리스트</h1>

	<div class="article-list-box-1 con table-box">
		<table class="table article-table">
			<colgroup>
				<col width="50" />
				<col width="200" />
				<col width="250" />
				<col width="250" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>제목</th>
					<th>등록날짜</th>
					<th>갱신날짜</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Article article : articles) {
				%>
				<tr>
					<td><%=article.getId()%></td>
					<td class="text-align-left"><a
						href="./detail?cateItemId=<%=cateItemId%>&id=<%=article.getId()%>"><%=article.getTitle()%></a></td>
					<td><%=article.getRegDate()%></td>
					<td><%=article.getUpdateDate()%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
		<div class="con paging">
			<%
				for (int i = 1; i <= fullPage; i++) {
							if (i == nowPage) {
			%>
			<span><a href="./list?cateItemId=<%=cateItemId%>&page=<%=i%>"
				style="font-weight: bold; color: red">[<%=i%>]
			</a></span>
			<%
				} else {
			%>
			<span><a class="not-selected-page"
				href="./list?cateItemId=<%=cateItemId%>&page=<%=i%>">[<%=i%>]
			</a></span>
			<%
				}
						}
			%>
			<div>
			<%
				if (nowPage > 1) {
			%>
				<div class="previous-article paging-only-one">
					<a href="./list?cateItemId=<%=cateItemId%>&page=<%=nowPage - 1%>">이전
						페이지</a>
				</div>
				<%
					}
			if(nowPage>1 && nowPage<fullPage){
				%>
				&nbsp;&nbsp;&nbsp;
				<%
			}
					if (nowPage < fullPage) {
				%>
				<div class="next-article paging-only-one">
					<a href="./list?cateItemId=<%=cateItemId%>&page=<%=nowPage + 1%>">다음
						페이지</a>
				</div>
			<%
				}
			%>
			</div>
		</div>
	<%
		}
	%>
	<!-- 게시물 리스트 끝 -->
	<%@ include file="/jsp/part/foot.jspf"%>