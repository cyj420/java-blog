package com.sbs.java.blog.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.util.DBUtil;

@WebServlet("/s/article/list")
public class ArticleListServlet extends HttpServlet {
	private List<Article> getArticles(int itemsInAPage, int cateItemId, int page) {
		String url = "jdbc:mysql://site29.iu.gy:3306/site29?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
		// 위에서 blog는 데이터베이스 이름
		String user = "site29";
		String password = "sbs123414";
		String driverName = "com.mysql.cj.jdbc.Driver";

		String sql = "";

		List<Article> articles = new ArrayList<>();

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		sql += String.format("AND cateItemId = %d ", cateItemId);
		sql += String.format("ORDER BY id DESC ");
		sql += String.format("LIMIT %d, %d", (page - 1) * itemsInAPage, itemsInAPage);

		Connection conn = null;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);

			List<Map<String, Object>> rows = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> row : rows) {
				articles.add(new Article(row));
			}
		} catch (ClassNotFoundException e) {
			System.err.println("[ClassNotFoundException 예외]");
			System.err.println("msg : " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("[SQLException 예외]");
			System.err.println("msg : " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("[SQLException 예외]");
					System.err.println("msg : " + e.getMessage());
				}
			}
		}
		return articles;
	}

	private int getFullPage(int itemsInAPage, int cateItemId) {
		String url = "jdbc:mysql://site29.iu.gy:3306/site29?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
		String user = "site29";
		String password = "sbs123414";
		String driverName = "com.mysql.cj.jdbc.Driver";

		String sql = "";

		List<Article> articles = new ArrayList<>();

		sql += String.format("SELECT * FROM article ");
		sql += String.format("WHERE displayStatus = 1 ");
		sql += String.format("AND cateItemId = %d ", cateItemId);
		sql += String.format("ORDER BY id DESC");

		Connection conn = null;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);

			List<Map<String, Object>> rows = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> row : rows) {
				articles.add(new Article(row));
			}
		} catch (ClassNotFoundException e) {
			System.err.println("[ClassNotFoundException 예외]");
			System.err.println("msg : " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("[SQLException 예외]");
			System.err.println("msg : " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("[SQLException 예외]");
					System.err.println("msg : " + e.getMessage());
				}
			}
		}
		int fullPage = 0;
		if (articles.size() != 0) {
			fullPage = (articles.size() - 1) / itemsInAPage + 1;
		}
		return fullPage;
	}

	private List<Category> getCategories() {
		String url = "jdbc:mysql://site29.iu.gy:3306/site29?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
		String user = "site29";
		String password = "sbs123414";
		String driverName = "com.mysql.cj.jdbc.Driver";

		String sql = "";

		List<Category> categories = new ArrayList<>();

		sql += String.format("SELECT * FROM cateItem");

		Connection conn = null;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);

			List<Map<String, Object>> rows = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> row : rows) {
				categories.add(new Category(row));
			}
		} catch (ClassNotFoundException e) {
			System.err.println("[ClassNotFoundException 예외]");
			System.err.println("msg : " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("[SQLException 예외]");
			System.err.println("msg : " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("[SQLException 예외]");
					System.err.println("msg : " + e.getMessage());
				}
			}
		}
		return categories;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		int cateItemId = 0;
		try {
			cateItemId = Integer.parseInt(req.getParameter("cateItemId"));
		} catch (NumberFormatException e) {
		}

		int page = 0;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (NumberFormatException e) {
		}

		List<Category> categories = null;
		List<Article> articles = null;
		int fullPage = 0;

		if (cateItemId == 0 || page == 0) {
			categories = getCategories();
		} else {
			int itemsInAPage = 5;
			articles = getArticles(itemsInAPage, cateItemId, page);
			fullPage = getFullPage(itemsInAPage, cateItemId);
		}

		req.setAttribute("page", page);
		req.setAttribute("cateItemId", cateItemId);
		req.setAttribute("fullPage", fullPage);
		if (articles != null) {
			req.setAttribute("articles", articles);
		} else {
			req.setAttribute("categories", categories);
		}
		req.getRequestDispatcher("/jsp/article/list.jsp").forward(req, resp);
	}
}