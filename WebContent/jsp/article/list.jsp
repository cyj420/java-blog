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
	int nowPage = (int) request.getAttribute("page");
	String searchKeyword = (String) request.getAttribute("searchKeyword");
%>
</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/article/list.css">
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
	
	<!-- 
	<div class="con">
		<form action="./list" method="GET">
	      <input type="text" name="searchKeyword" />
	      <input type="submit" value="검색" />
	    </form>
	</div>
 	-->

	<div class="article-list-box-1 con table-box">
		<table class="table article-table">
			<colgroup>
				<col width="50" />
				<col width="200" />
				<col width="250" />
				<col width="100" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>제목</th>
					<th>등록날짜</th>
					<th>조회수</th>
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
					<td><%=article.getHit()%></td>
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
		<span><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=i%>"
			style="font-weight: bold; color: red">[<%=i%>]
		</a></span>
		<%
				} else {
		%>
		<span><a class="not-selected-page"
			href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=i%>">[<%=i%>]
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
				<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=nowPage - 1%>">이전
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
				<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=nowPage + 1%>">다음
					페이지</a>
			</div>
			<%
				}
			%>
		</div>
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