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
import com.sbs.java.blog.util.DBUtil;

@WebServlet("/s/article/list")
public class ArticleListServlet extends HttpServlet {
	private List<Article> getArticles(){
		String url = "jdbc:mysql://localhost:3306/blog?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
		// 위에서 blog는 데이터베이스 이름
//		String user = "sbsst";
//		String password = "sbs123414";
		String user = "root";
		String password = "";
		String driverName = "com.mysql.cj.jdbc.Driver";

		String sql="";
		
		List<Article> articles = new ArrayList<>();
		
		sql+=String.format("SELECT * ");
		sql+=String.format("FROM article ");
		sql+=String.format("ORDER BY id DESC");
		
		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
			
			List<Map<String, Object>> rows = DBUtil.selectRows(conn, sql);
			
			for(Map<String, Object>row:rows) {
				articles.add(new Article(row));
			}
			
			System.out.println(rows);
		} catch (ClassNotFoundException e) {
			System.err.println("[ClassNotFoundException 예외]");
			System.err.println("msg : "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[SQLException 예외]");
			System.err.println("msg : "+e.getMessage());
		}
		finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("[SQLException 예외]");
					System.err.println("msg : "+e.getMessage());
				}
			}
		}
		return articles;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		
		List<Article> articles = getArticles();
		
		req.setAttribute("articles", articles);
		//위의 List<Article> articles 의 정보를 req에 담는 것. req는 jsp에서도 사용할 것이기에.
		req.getRequestDispatcher("/jsp/article/list.jsp").forward(req, resp);
	}
}