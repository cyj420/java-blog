package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.util.DBUtil;

public class ArticleDao {

	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId, int itemsInAPage) {
		String sql = "";

		int limitFrom = (page - 1) * itemsInAPage;

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}
		sql += String.format("ORDER BY id DESC ");
		sql += String.format("LIMIT %d, %d", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public int getFullPage(int cateItemId, int itemsInAPage) {
		String sql = "";

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		sql += String.format("AND cateItemId = %d ", cateItemId);
		sql += String.format("ORDER BY id DESC");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
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

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
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

		Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
		Article article = new Article(row);
		return article;
	}

	public List<Article> getArticlesByCateItemId(int cateItemId) {
		String sql = "";

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		sql += String.format("AND cateItemId = %d ", cateItemId);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}
}