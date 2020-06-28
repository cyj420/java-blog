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

@WebServlet("/s/article/detail")
public class ArticleDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		
		String url = "jdbc:mysql://localhost:3306/blog?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
		// 위에서 blog는 데이터베이스 이름
//		String user = "sbsst";
//		String password = "sbs123414";
		String user = "root";
		String password = "";
		String driverName = "com.mysql.cj.jdbc.Driver";

		int id = Integer.parseInt(req.getParameter("id"));
		
		String sql="";
		
		sql+=String.format("SELECT * ");
		sql+=String.format("FROM article ");
		sql+=String.format("WHERE id="+id);
		
		Connection conn = null;

		Article a = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
			
			Map<String, Object> row = DBUtil.selectRow(conn, sql);
			
			a = new Article(row);
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
		
		req.setAttribute("a", a);
		//위의 List<Article> articles 의 정보를 req에 담는 것. req는 jsp에서도 사용할 것이기에.
		req.getRequestDispatcher("/jsp/article/detail.jsp").forward(req, resp);
	}
}