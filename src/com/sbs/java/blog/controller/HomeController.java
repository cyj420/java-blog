package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;

public class HomeController extends Controller {

	public HomeController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String doAction() {
		switch(actionMethodName) {
		case "main":
			return doActionHome(req, resp);
		case "aboutMe":
			return doActionAboutMe(req, resp);
		}
		return "";
	}

	private String doActionAboutMe(HttpServletRequest req, HttpServletResponse resp) {
		return "home/aboutMe.jsp";
	}

	private String doActionHome(HttpServletRequest req, HttpServletResponse resp) {
		List<Article> hotArticles = articleService.getHotArticles();
		List<Article> newArticles = articleService.getNewArticles();
		req.setAttribute("hotArticles", hotArticles);
		req.setAttribute("newArticles", newArticles);
		return "home/main.jsp";
	}
	
	@Override
	public String getControllerName() {
		return "home";
	}

}
