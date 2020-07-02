package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dao.ArticleDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService(Connection dbConn) {
		articleDao = new ArticleDao(dbConn);
	}

	public List<Article> getForPrintListArticles(int page, int cateItemId, int itemsInAPage) {
		return articleDao.getForPrintListArticles(page, cateItemId, itemsInAPage);
	}

	public List<Category> getCategories() {
		return articleDao.getCategories();
	}

	public int getFullPage(int cateItemId, int itemsInAPage) {
		return articleDao.getFullPage(cateItemId, itemsInAPage);
	}

	public Article getArticle(int id, int cateItemId) {
		return articleDao.getArticle(id, cateItemId);
	}

	public List<Article> getArticlesByCateItemId(int cateItemId) {
		return articleDao.getArticlesByCateItemId(cateItemId);
	}

}
