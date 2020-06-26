package com.sbs.java.blog.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/s/article/list")
public class ArticleListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String url = "jdbc:mysql://localhost:3306/blog?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true";
//		String user = "sbsst";
//		String password = "sbs123414";
		String user = "root";
		String password = "";
		String driverName = "com.mysql.cj.jdbc.Driver";

		Connection connection = null;

		String sql = "";
		sql += String.format("SELECT * article");

		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, user, password);
			response.getWriter().append("연결되었습니다.");

			request.getRequestDispatcher("/jsp/home/list.jsp").forward(request, response);
		} catch (SQLException e) {
			System.err.printf("[SQL 예외] : %s\n", e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.printf("[드라이버 클래스 로딩 예외] : %s\n", e.getMessage());
			// 해당 ERROR 발생시 lib에 mysql-connector-java-8.0.20.jar 파일이 존재하는지 확인!
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.err.printf("[SQL 예외, connection 닫기] : %s\n", e.getMessage());
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}