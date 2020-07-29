<%@page import="com.sbs.java.blog.service.ArticleService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sbs.java.blog.dto.Category"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> --%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	ArticleService articleService = (ArticleService) request.getAttribute("articleService");
	int fullPage = (int) request.getAttribute("fullPage");
	int nowPage = (int) request.getAttribute("page");
	String searchKeyword = (String) request.getAttribute("searchKeyword");
%>
</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/article/list.css">
<body>
	${cateItems}
	<!-- 카테고리 리스트 시작 -->
	<div class="article-list-box-1 con table-box category-list">
		<c:forEach items="${cateItems}" var="c">
			<div class="category-name selected-category">
				<a href="./list?cateItemId=${c.id}&page=1">${c.name}!</a>
			</div>

		</c:forEach>
	</div>
	<!-- 카테고리 리스트 끝 -->

	<!-- 게시물 리스트 시작 -->
	<%
		String categoryName = "";
	%>
	<c:if test="${cateItemId} == 0">
		<h1 class="con">
			📂 전체 게시물 리스트
			<%-- ${fn:length(searchKeyword.trim())} --%>

			<!-- 	int n = 0;
			n+=articleService.getArticlesByCateItemId(i).size(); -->
			<c:forEach var="i" begin="1" end="5" step="1">
			</c:forEach>

			<%-- 
		<%
		if(searchKeyword.trim().length()==0){
		int n = 0;
		for(int i=1; i<=cateItems.size(); i++){
			n+=articleService.getArticlesByCateItemId(i).size();
		}
		%> 
		(<%=n %>)
		<%
		}
		%>
		--%>
		</h1>
	</c:if>
	<c:if test="${cateItemId} != 0">
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
				<col width="200" />
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
						<td>${article.nickname}작성자닉네임</td>
						<%-- <td><%=ms.getMemberById(article.getWriterId()).getNickname()%></td> --%>
						<td>${article.hit}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="con paging">
		<%
			if (nowPage > 1) {
		%>
		<a
			href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=nowPage - 1%>">◀</a>
		<%
			}
			for (int i = 1; i <= fullPage; i++) {
				if (i == nowPage) {
		%>
		<span><a
			href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=i%>"
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
			if (nowPage < fullPage) {
		%>
		<a
			href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=nowPage + 1%>">▶</a>
		<%
			}
		%>
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