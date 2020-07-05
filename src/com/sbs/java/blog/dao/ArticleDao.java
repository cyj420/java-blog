package com.sbs.java.blog.dao;

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
import com.sbs.java.blog.util.DBUtil;

public class ArticleDao {

	private Connection dbConn;
	private DBUtil dbUtil;

	public ArticleDao(Connection dbConn, HttpServletRequest req, HttpServletResponse resp) {
		this.dbConn = dbConn;
		this.dbUtil = new DBUtil(req, resp);
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId, int itemsInAPage, String searchKeyword) {
		String sql = "";

		int limitFrom = (page - 1) * itemsInAPage;

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}
		if(!searchKeyword.equals("")) {
			123
			System.out.println("에러뜰듯");
			sql += String.format("AND (title LIKE '%s%' ", searchKeyword);
			sql += String.format("OR `body` LIKE '%s%') ", searchKeyword);
		}
		else {
			System.out.println("공백이넹..");
		}
		sql += String.format("ORDER BY id DESC ");
		sql += String.format("LIMIT %d, %d", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public int getFullPage(int cateItemId, int itemsInAPage, String searchKeyword) {
		String sql = "";

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE 1 ");
		sql += String.format("AND displayStatus = 1 ");
//		sql += String.format("AND (title LIKE '%s%' ");
//		sql += String.format("OR `body` LIKE '%s%') ", searchKeyword);
		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}
		sql += String.format("ORDER BY id DESC");

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		int fullPage = 0;
		if (articles.size() != 0) {
			fullPage = (articles.size() - 1) / itemsInAPage + 1;
		}
		return fullPage;
	}

	public List<Category> getCategories() {
		String sql = "";

		sql += String.format("SELECT * FROM cateItem");

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<Category> categories = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			categories.add(new Category(row));
		}
		return categories;
	}

	public Article getArticle(int id, int cateItemId) {
		String sql = "";

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		sql += String.format("AND cateItemId = %d ", cateItemId);
		sql += String.format("AND id=" + id);

		Map<String, Object> row = dbUtil.selectRow(dbConn, sql);
		Article article = new Article(row);
		return article;
	}

	public List<Article> getArticlesByCateItemId(int cateItemId) {
		String sql = "";

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		sql += String.format("AND cateItemId = %d ", cateItemId);

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public List<Article> getArticles() {
		String sql = "";
		sql += String.format("SELECT * from article ");
		sql += String.format("WHERE displayStatus = 1 ");

		List<Map<String, Object>> rows = dbUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}
}