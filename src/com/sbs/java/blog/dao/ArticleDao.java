package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.util.DBUtil;

public class ArticleDao extends Dao {
	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId, int itemsInAPage, String searchKeywordType,
			String searchKeyword) {
		String sql = "";

		int limitFrom = (page - 1) * itemsInAPage;

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
//		if(!searchKeyword.equals("")) {
//			에러 발생
//			sql += String.format("AND (title LIKE '%s%' ", searchKeyword);
//			sql += String.format("OR `body` LIKE '%s%') ", searchKeyword);

			sql += String.format("AND title LIKE CONCAT('%%', '%s', '%%')", searchKeyword);
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

//	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
//		String sql = "";
//
//		sql += String.format("SELECT COUNT(*) AS cnt ");
//		sql += String.format("FROM article ");
//		sql += String.format("WHERE displayStatus = 1 ");
//
//		if (cateItemId != 0) {
//			sql += String.format("AND cateItemId = %d ", cateItemId);
//		}
//
//		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
//			sql += String.format("AND title LIKE CONCAT('%%', '%s', '%%')", searchKeyword);
//		}
//
//		int count = dbUtil.selectRowIntValue(dbConn, sql);
//		return count;
//	}
	public int getFullPage(int cateItemId, int itemsInAPage, String searchKeywordType, String searchKeyword) {
		String sql = "";

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE 1 ");
		sql += String.format("AND displayStatus = 1 ");
//		sql += String.format("AND (title LIKE '%s%' ", searchKeyword);
//		sql += String.format("OR `body` LIKE '%s%') ", searchKeyword);
		if (cateItemId != 0) {
			sql += String.format("AND cateItemId = %d ", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql += String.format("AND title LIKE CONCAT('%%', '%s', '%%')", searchKeyword);
		}
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

	public List<Article> getArticles() {
		String sql = "";
		sql += String.format("SELECT * from article ");
		sql += String.format("WHERE displayStatus = 1 ");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		return articles;
	}

	public int write(int cateItemId, String title, String body) {
		String sql = "";

		sql += String.format("INSERT INTO article ");
		sql += String.format("SET regDate = NOW() ");
		sql += String.format(", updateDate = NOW() ");
		sql += String.format(", title = '%s' ", title);
		sql += String.format(", body = '%s' ", body);
		sql += String.format(", displayStatus = 1 ");
		sql += String.format(", cateItemId = '%d' ", cateItemId);

		return DBUtil.insert(dbConn, sql);
	}

	public int join(String loginId, String name, String nickname, String loginPw) {
		// 기존에 존재하는 ID인지 확인
		if(checkIdExists(loginId)) {
			return -1;
		}
		else {
			String sql = "";
			
			sql += String.format("INSERT INTO member ");
			sql += String.format("SET loginId = '%s' ", loginId);
			sql += String.format(", name = '%s' ", name);
			sql += String.format(", nickname = '%s' ", nickname);
			sql += String.format(", loginPw = '%s' ", loginPw);
			
			return DBUtil.insert(dbConn, sql);
		}
	}

	private boolean checkIdExists(String loginId) {
		String sql = "";
		
		sql += String.format("SELECT * FROM member ");
		sql += String.format("WHERE loginId = '%s' ", loginId);
		
		if(DBUtil.selectRow(dbConn, sql).isEmpty()) {
			return false;
		}
		return true;
	}
}