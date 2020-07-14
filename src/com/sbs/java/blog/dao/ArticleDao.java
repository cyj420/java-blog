package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;
import com.sbs.java.blog.util.Util;

public class ArticleDao extends Dao {
	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId, int itemsInAPage, String searchKeywordType,
			String searchKeyword) {
		SecSql sql = new SecSql();

		int limitFrom = (page - 1) * itemsInAPage;

		sql.append("SELECT * FROM article ");
		sql.append("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			sql.append("AND cateItemId = ? ", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql.append("AND title LIKE CONCAT('%%', ?, '%%') ", searchKeyword);
		}
		sql.append("ORDER BY id DESC ");
		sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public int getFullPage(int cateItemId, int itemsInAPage, String searchKeywordType, String searchKeyword) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt FROM article ");
		sql.append("WHERE 1 ");
		sql.append("AND displayStatus = 1 ");
		if (cateItemId != 0) {
			sql.append("AND cateItemId = ? ", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql.append("AND title LIKE CONCAT('%%', ?, '%%') ", searchKeyword);
		}
		sql.append("ORDER BY id DESC");

		int numberOfArticles = DBUtil.selectRowIntValue(dbConn, sql);

		int fullPage = 0;
		if (numberOfArticles != 0) {
			fullPage = (numberOfArticles - 1) / itemsInAPage + 1;
		}
		return fullPage;
	}

	public List<Category> getCategories() {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM cateItem");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Category> categories = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			categories.add(new Category(row));
		}
		return categories;
	}

	public Article getArticle(int id, int cateItemId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article ");
		sql.append("WHERE displayStatus = 1 ");
		sql.append("AND cateItemId = ? ", cateItemId);
		sql.append("AND id=" + id);

		Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
		Article article = new Article(row);
		return article;
	}

	public List<Article> getArticlesByCateItemId(int cateItemId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article ");
		sql.append("WHERE displayStatus = 1 ");
		sql.append("AND cateItemId = ? ", cateItemId);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public List<Article> getArticles() {
		SecSql sql = new SecSql();

		sql.append("SELECT * from article ");
		sql.append("WHERE displayStatus = 1 ");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public int write(int cateItemId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article ");
		sql.append("SET regDate = NOW() ");
		sql.append(", updateDate = NOW() ");
		sql.append(", writerId = ? ", );
		sql.append(", title = ? ", title);
		sql.append(", body = ? ", body);
		sql.append(", displayStatus = 1 ");
		sql.append(", hit = 0 ");
		sql.append(", cateItemId = ? ", cateItemId);

		return DBUtil.insert(dbConn, sql);
	}

	public int increaseHit(int id) {
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, sql);
	}

	public int delete(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article ");
		sql.append("WHERE id = ? ", id);

		return DBUtil.update(dbConn, sql);
	}

	public int modify(int id, int cateItemId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article SET ");
		sql.append("cateItemId = ? ", cateItemId);
		sql.append(", title = ? ", title);
		sql.append(", body = ? ", body);
		sql.append(", updateDate = NOW() ");
		sql.append("WHERE id = ? ", id);

		return DBUtil.update(dbConn, sql);
	}

	public Article getArticleById(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM article ");
		sql.append("WHERE displayStatus = 1 ");
		sql.append("AND id=" + id);

		Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
		Article article = new Article(row);
		return article;
	}

	public List<ArticleReply> getArticleRepliesByArticleId(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT * FROM articleReply ");
		sql.append("WHERE articleId = ? ", id);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<ArticleReply> comments = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			comments.add(new ArticleReply(row));
		}
		return comments;
	}

	public void addArticleReply(int writerId, int articleId, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleReply ");
		sql.append("SET regDate = NOW() ");
		sql.append(", updateDate = NOW() ");
		sql.append(", writerId = ? ", writerId);
		sql.append(", articleId = ? ", articleId);
		sql.append(", body = ? ", body);

		DBUtil.insert(dbConn, sql);
	}

	public void deleteArticleReply(int articleReplyId) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM articleReply ");
		sql.append("WHERE id = ? ", articleReplyId);

		DBUtil.update(dbConn, sql);
	}

	public void modifyArticleReply(int arId, String arBody) {
		SecSql sql = new SecSql();

		sql.append("UPDATE articleReply SET ");
		sql.append("body = ? ", arBody);
		sql.append("WHERE id = ? ", arId);

		DBUtil.update(dbConn, sql);
	}
}