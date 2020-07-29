package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.blog.dto.Category;
import com.sbs.java.blog.dto.Member;
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
		// 액션 전 실행
		// 이 메서드는 모든 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		List<Category> cateItems = articleService.getCategories();

		req.setAttribute("cateItems", cateItems);

		// 사용자 관련 정보를 리퀘스트 객체에 정리해서 넣기
		int loginedMemberId = -1;
		boolean isLogined = false;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			isLogined = true;
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		req.setAttribute("loginedMemberId", loginedMemberId);
		req.setAttribute("loginedMember", loginedMember);
		req.setAttribute("isLogined", isLogined);

		// 현재 URL

		String currentUri = req.getRequestURI();

		if (req.getQueryString() != null) {
			currentUri += "?" + req.getQueryString();
		}

		String encodedCurrentUri = Util.getUriEncoded(currentUri);

		// 현재 접속된 페이지와 관련된 유용한 정보 담기
		req.setAttribute("currentUri", currentUri);
		req.setAttribute("encodedCurrentUri", encodedCurrentUri);
		req.setAttribute("encodedAfterLoginRedirectUri", encodedCurrentUri);
		req.setAttribute("noBaseCurrentUri", req.getRequestURI().replace(req.getContextPath(), ""));

		// 로그인 페이지에서 로그인 페이지로 이동하는 버튼을 또 누른 경우
		// 기존 afterLoginRedirectUri 정보를 유지시키기 위한 로직
		if (currentUri.contains("/s/member/login")) {
			String encodedOldAfterLoginRedirectUri = Util.getString(req, "afterLoginRedirectUri", "");
			encodedOldAfterLoginRedirectUri = Util.getUriEncoded(encodedOldAfterLoginRedirectUri);
			req.setAttribute("encodedAfterLoginRedirectUri", encodedOldAfterLoginRedirectUri);
		}
		
		// 로그아웃 후 가야하는 곳, 기본적으로 현재 URL
		req.setAttribute("encodedAfterLogoutRedirectUri", encodedCurrentUri);
		
		
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
