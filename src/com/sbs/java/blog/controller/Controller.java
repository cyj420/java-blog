package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.service.ArticleService;
import com.sbs.java.blog.service.MemberService;
import com.sbs.java.blog.util.Util;

public abstract class Controller {

	protected Connection dbConn;
	protected String actionMethodName;
	protected HttpServletRequest req;
	protected HttpServletResponse resp;

	HttpSession session;

	protected ArticleService articleService;
	protected MemberService memberService;

	public Controller(Connection dbConn, String actionMethodName, HttpServletRequest req, HttpServletResponse resp) {
		this.dbConn = dbConn;
		this.actionMethodName = actionMethodName;
		this.req = req;
		this.resp = resp;
		articleService = new ArticleService(dbConn);
		memberService = new MemberService(dbConn);
		this.session = req.getSession();
	}

	public void beforeAction() {
		String currentUrl = req.getRequestURI();

		if (req.getQueryString() != null) {
			currentUrl += "?" + req.getQueryString();
		}
		String encodedCurrentUrl = Util.getUrlEncoded(currentUrl);
		// 액션 전 실행
		// 이 메서드는 모든 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		List<Category> cateItems = articleService.getCategories();
		req.setAttribute("cateItems", cateItems);
		req.setAttribute("articleService", articleService);
		req.setAttribute("memberService", memberService);

		// 로그아웃 후 가야하는 곳, 기본적으로 현재 URL
		req.setAttribute("encodedAfterLogoutRedirectUri", encodedCurrentUrl);
	}

	public void afterAction() {
		// 액션 후 실행
	}

	public abstract String doAction();

	public String executeAction() {
		beforeAction();
		String rs = doAction();
		afterAction();

		return rs;
	}
}
