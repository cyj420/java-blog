package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dao.ArticleDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;

public class ArticleService extends Service{

	private ArticleDao articleDao;

	public ArticleService(Connection dbConn) {
		articleDao = new ArticleDao(dbConn);
	}

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId, String searchKeywordType, String searchKeyword) {
		return articleDao.getForPrintListArticles(page, itemsInAPage, cateItemId, searchKeywordType, searchKeyword);
	}
	
	public int getFullPage(int cateItemId, int itemsInAPage, String searchKeywordType, String searchKeyword) {
		return articleDao.getFullPage(cateItemId, itemsInAPage, searchKeywordType, searchKeyword);
	}

//	public int getForPrintListArticlesCount(int cateItemId, ) {
//		return articleDao.getForPrintListArticlesCount(cateItemId);
//	}

	public Article getArticle(int id, int cateItemId) {
		return articleDao.getArticle(id, cateItemId);
	}

//	public List<Category> getForPrintCateItems() {
//		return articleDao.getForPrintCateItems();
//	}

//	public Category getCategory(int cateItemId) {
//		return articleDao.getCategory(cateItemId);
//	}
	public List<Category> getCategories() {
		return articleDao.getCategories();
	}
	public List<Article> getArticlesByCateItemId(int cateItemId){
		return articleDao.getArticlesByCateItemId(cateItemId);
	}

	public int write(int cateItemId, String title, String body) {
		return articleDao.write(cateItemId, title, body);
	}
}
