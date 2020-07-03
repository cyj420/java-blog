package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.service.ArticleService;
import com.sbs.java.blog.util.DBUtil;

public class ArticleController extends Controller {

	private ArticleService articleService;

	public ArticleController(Connection dbConn) {
		articleService = new ArticleService(dbConn);
	}

	@Override
	public String doAction(String actionMethodName, HttpServletRequest req, HttpServletResponse resp) {
		switch (actionMethodName) {
		case "list":
			return doActionList(req, resp);
		case "detail":
			return doActionDetail(req, resp);
		}
		return "";
	}

	// detail
	private String doActionDetail(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		int cateItemId = Integer.parseInt(req.getParameter("cateItemId"));

		Article a = articleService.getArticle(id, cateItemId);
		List<Article> articles = articleService.getArticlesByCateItemId(cateItemId);

		req.setAttribute("a", a);
		req.setAttribute("articles", articles);

		return "article/detail.jsp";
	}

	// list
	private String doActionList(HttpServletRequest req, HttpServletResponse resp) {
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

		List<Category> categories = null;
		List<Article> articles = null;
		int itemsInAPage = 5;

		articles = articleService.getForPrintListArticles(page, cateItemId, itemsInAPage);
		categories = articleService.getCategories();
		int fullPage = articleService.getFullPage(cateItemId, itemsInAPage);
		System.out.println("fullPage : " + fullPage);

		req.setAttribute("fullPage", fullPage);
		req.setAttribute("categories", categories);
		req.setAttribute("articles", articles);
		req.setAttribute("page", page);
		req.setAttribute("cateItemId", cateItemId);

		return "article/list.jsp";
	}
}
