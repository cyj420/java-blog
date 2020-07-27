package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.util.Util;

public class ArticleController extends Controller {

	public ArticleController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "list":
			return doActionList(req, resp);
		case "detail":
			return doActionDetail(req, resp);
		case "write":
			return doActionWrite(req, resp);
		case "doWrite":
			return doActionDoWrite(req, resp);
		case "delete":
			return doActionDelete(req, resp);
		case "modify":
			return doActionModify(req, resp);
		case "doModify":
			return doActionDoModify(req, resp);
		case "doArticleReply":
			return doActionDoArticleReply(req, resp);
		case "doArticleReplyDelete":
			return doActionDoArticleReplyDelete(req, resp);
		case "doArticleReplyModify":
			return doActionDoArticleReplyModify(req, resp);
		case "doArticleReplyDoModify":
			return doActionDoArticleReplyDoModify(req, resp);
		}
		return "";
	}

	private String doActionDoArticleReplyDoModify(HttpServletRequest req, HttpServletResponse resp) {
		int articleId = Util.getInt(req, "articleId");
		int articleCateId = Util.getInt(req, "articleCateId");
		int arId = Util.getInt(req, "arId");
		String arBody = req.getParameter("arBody");
		articleService.modifyArticleReply(arId, arBody);
		
		// 수정필요
		return String.format("html:<script> alert('댓글을 수정하였습니다.'); location.replace('detail?cateItemId=%d&id=%d'); </script>", articleCateId, articleId);
	}

	private String doActionDoArticleReplyModify(HttpServletRequest req, HttpServletResponse resp) {
		int articleId = Util.getInt(req, "articleId");
		int articleCateId = Util.getInt(req, "articleCateId");
		int articleReplyId = Util.getInt(req, "articleReplyId");
		String articleReplyBody = req.getParameter("articleReplyBody");
		
		req.setAttribute("articleId", articleId);
		req.setAttribute("articleCateId", articleCateId);
		req.setAttribute("articleReplyId", articleReplyId);
		req.setAttribute("articleReplyBody", articleReplyBody);
		
		return "article/articleReply.jsp";
	}

	private String doActionDoArticleReplyDelete(HttpServletRequest req, HttpServletResponse resp) {
		int articleId = Util.getInt(req, "articleId");
		int articleCateId = Util.getInt(req, "articleCateId");
		int articleReplyId = Util.getInt(req, "articleReplyId");
		articleService.deleteArticleReply(articleReplyId);
		
		return String.format("html:<script> alert('댓글을 삭제하였습니다.'); location.replace('detail?cateItemId=%d&id=%d'); </script>", articleCateId, articleId);
	}

	private String doActionDoArticleReply(HttpServletRequest req, HttpServletResponse resp) {
		int writerId = Util.getInt(req, "writerId");
		int articleId = Util.getInt(req, "articleId");
		int articleCateId = Util.getInt(req, "articleCateId");
		String body = req.getParameter("body");
		
		articleService.addArticleReply(writerId, articleId, body);
		return String.format("html:<script> alert('댓글을 작성하였습니다.'); location.replace('detail?cateItemId=%d&id=%d'); </script>", articleCateId, articleId);
	}

	private String doActionDoModify(HttpServletRequest req, HttpServletResponse resp) {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int cateItemId = Util.getInt(req, "cateItemId");
		int id = Util.getInt(req, "id");
		
		articleService.modify(id, cateItemId, title, body);
		
		return String.format("html:<script> alert('%d번 게시물을 수정하였습니다.'); "
				+ "location.replace('detail?cateItemId=%d&id=%d'); </script>", id, cateItemId, id);
	}

	private String doActionModify(HttpServletRequest req, HttpServletResponse resp) {
		if(Util.empty(req, "id")) {
			return "html:수정할 게시물의 id를 입력해주세요.";
		}
		int id = Util.getInt(req, "id");
		
		Article a = articleService.getArticleById(id);
		
		req.setAttribute("a", a);
		return "article/modify.jsp";
	}

	private String doActionDelete(HttpServletRequest req, HttpServletResponse resp) {
		if(Util.empty(req, "id")) {
			return "html:삭제할 게시물의 id를 입력해주세요.";
		}
		int id = Util.getInt(req, "id");
		articleService.delete(id);
		return "html:<script> alert('" + id + "번 게시물이 삭제되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionDoWrite(HttpServletRequest req, HttpServletResponse resp) {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int cateItemId = Util.getInt(req, "cateItemId");
		int writerId = (int)session.getAttribute("loginedMemberId");
		
		int id = articleService.write(cateItemId, title, body, writerId);
		
		return "html:<script> alert('" + id + "번 게시물이 생성되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionWrite(HttpServletRequest req, HttpServletResponse resp) {
		return "article/write.jsp";
	}

	// detail
	private String doActionDetail(HttpServletRequest req, HttpServletResponse resp) {
		long startTime = System.nanoTime();
		if(Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}
		if(Util.isNum(req, "id")) {
			return "html:id를 정수로 입력해주세요.";
		}
		int id = Util.getInt(req, "id");
		
		if(Util.empty(req, "cateItemId")) {
			return "html:cateItemId를 입력해주세요.";
		}
		if(Util.isNum(req, "cateItemId")) {
			return "html:cateItemId를 정수로 입력해주세요.";
		}
		int cateItemId = Integer.parseInt(req.getParameter("cateItemId"));

		articleService.increaseHit(id);
		
		Article a = articleService.getArticle(id, cateItemId);
		List<Article> articles = articleService.getArticlesByCateItemId(cateItemId);
		List<ArticleReply> articleReplies = articleService.getArticleRepliesByArticleId(id);
		
		req.setAttribute("a", a);
		req.setAttribute("articles", articles);
		req.setAttribute("articleReplies", articleReplies);
		req.setAttribute("articleService", articleService);
		req.setAttribute("memberService", memberService);
		
		long endTime = System.nanoTime();
		long estimatedTime = endTime-startTime;
		double seconds = estimatedTime/1000000000.0;
		System.out.println("seconds : "+seconds);
		
		return "article/detail.jsp";
	}
	
	// list
	private String doActionList(HttpServletRequest req, HttpServletResponse resp) {
		long startTime = System.nanoTime();
		
		int cateItemId = 0;
		try {
			cateItemId = Integer.parseInt(req.getParameter("cateItemId"));
		} catch (NumberFormatException e) {
		}

		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		
		String searchKeywordType = "";

		if (!Util.empty(req, "searchKeywordType")) {
			searchKeywordType = Util.getString(req, "searchKeywordType");
		}
		
		String searchKeyword = "";
		if(req.getParameter("searchKeyword")!=null) {
			searchKeyword = req.getParameter("searchKeyword");
		}

		List<Category> categories = null;
		List<Article> articles = null;

		// 페이지당 존재하는 게시물 수
		int itemsInAPage = 5;

		articles = articleService.getForPrintListArticles(page, cateItemId, itemsInAPage, searchKeywordType, searchKeyword);
		categories = articleService.getCategories();
		int fullPage = articleService.getFullPage(cateItemId, itemsInAPage, searchKeywordType, searchKeyword);

		req.setAttribute("fullPage", fullPage);
		req.setAttribute("categories", categories);
		req.setAttribute("articles", articles);
		req.setAttribute("page", page);
		req.setAttribute("cateItemId", cateItemId);
		req.setAttribute("searchKeyword", searchKeyword);

		long endTime = System.nanoTime();
		long estimatedTime = endTime-startTime;
		double seconds = estimatedTime/1000000000.0;
		System.out.println("seconds : "+seconds);
		
		return "article/list.jsp";
	}

}
