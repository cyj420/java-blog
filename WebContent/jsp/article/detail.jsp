<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article a = (Article) request.getAttribute("a");
%>
<style>
.article-list-box-1 td {
	text-align: center;
}
</style>

</head>
<body>
	<h1 class="con">게시물 상세보기</h1>

	<div class="article-list-box-1 con">
		<div>ID : <%=a.getId()%></div>
		<div>등록날짜 : <%=a.getRegDate()%></div>
		<div>갱신날짜 : <%=a.getUpdateDate()%></div>
		<div>제목 : <%=a.getTitle() %></div>
		<div>내용 : <%=a.getBody() %></div>
		<%-- <table>
			<colgrou>
			<col width="50" />
			<col width="150" />
			<col width="150" />
			</colgrou>
			<thead>
				<tr>
					<th>ID</th>
					<th>등록날짜</th>
					<th>갱신날짜</th>
					<th>제목</th>
					<th>내용</th>
				</tr>
			<tbody>
				<tr>
					<td><%=a.getId()%></td>
					<td><%=a.getRegDate()%></td>
					<td><%=a.getUpdateDate()%></td>
					<td><%=a.getTitle() %></td>
					<td><%=a.getBody() %></td>
				</tr>
			</tbody>
			</thead>
		</table> --%>
	</div>
	<%@ include file="/jsp/part/foot.jspf"%>