<%@page import="java.util.ArrayList"%>
<%@page import="com.sbs.java.blog.dto.Category"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<Category> categories = (List<Category>) request.getAttribute("categories");
	int fullPage = (int) request.getAttribute("fullPage");
	int cateItemId = (int) request.getAttribute("cateItemId");
	System.out.println(cateItemId);
	int nowPage = (int) request.getAttribute("page");
%>
<style>
.article-list-box-1 td {
	text-align: center;
}

.category-list tbody>tr>td:nth-child(2) {
	text-align: left;
	padding-left: 20px;
}

td>a:hover {
	font-weight: bold;
	transition: .3s;
}

span>.not-selected-page:hover {
	color: blue;
}

.paging a:hover {
	color: blue;
}

.category-list {
	display: flex;
	max-width: 1000px;
	justify-content: space-around;
}

.category-name {
	display: inline-block;
	font-size: 1.4rem;
}

.category-name>a::after {
	content: "📁";
	opacity: 0;
	transition: opacity .3s;
}

.category-name>a:hover::after {
	opacity: 1;
}
</style>
</head>
<body>
	<!-- 카테고리 리스트 시작 -->
	<!-- <h1 class="con">카테고리 리스트</h1> -->
	<div class="article-list-box-1 con table-box category-list">
		<%
			for (Category c : categories) {
		%>
		<%-- <div><%=c.getId()%></div> --%>
		<div class="category-name">
			<a href="./list?cateItemId=<%=c.getId()%>&page=1"><%=c.getName()%></a>
		</div>
		<%
			}
		%>
	</div>
	<!-- 카테고리 리스트 끝 -->

	<!-- 게시물 리스트 시작 -->
	<%
		String categoryName = "";
		if (cateItemId == 0) {
	%>
	<h1 class="con">📂 전체 게시물 리스트</h1>
	<%
		} else {
	%>
	<h1 class="con">
		📋
		<%=categoryName = categories.get(cateItemId - 1).getName()%></h1>
		<div class="con back-to-list">
		<a href="./list">전체 게시판으로 돌아가기&#8594;</a>
		</div>
	<%
		}
	%>

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
						href="./detail?cateItemId=<%=article.getCateItemId()%>&id=<%=article.getId()%>"><%=article.getTitle()%></a></td>
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
		System.out.println(cateItemId);
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
				if (nowPage > 1 && nowPage < fullPage) {
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
	<!-- 게시물 리스트 끝 -->
	<%@ include file="/jsp/part/foot.jspf"%>