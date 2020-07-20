package com.sbs.java.blog.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.service.MemberService;
import com.sbs.java.blog.util.Util;

public class MemberController extends Controller {

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "join":
			return doActionJoin(req, resp);
		case "doJoin":
			return doActionDoJoin(req, resp);
		case "login":
			return doActionLogin(req, resp);
		case "doLogin":
			return doActionDoLogin(req, resp);
		case "logout":
			return doActionLogout(req, resp);
		case "myPage":
			return doActionMyPage(req, resp);
		case "doMyPage":
			return doActionDoMyPage(req, resp);
		}
		return "";
	}

	private String doActionDoMyPage(HttpServletRequest req, HttpServletResponse resp) {
		int id = memberService.getMemberById((int)session.getAttribute("loginedMemberId")).getId();
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPw");
		String email = req.getParameter("email");
		
		if(nickname.trim().length()==0 && loginPw.trim().length()==0 && email.trim().length()==0) {
			return "html:<script> alert('수정된 정보가 없습니다.'); history.back(); </script>";
		}
		memberService.myPage(id, nickname, loginPw, email);
		return "html:<script> alert('회원 정보가 수정되었습니다.'); location.replace('../home/main'); </script>";
	}

	private String doActionMyPage(HttpServletRequest req, HttpServletResponse resp) {
		return "member/mypage.jsp";
	}

	private String doActionLogout(HttpServletRequest req, HttpServletResponse resp) {
		String nickname = memberService.getMemberById((int)session.getAttribute("loginedMemberId")).getNickname();
		session.removeAttribute("loginedMemberId");

		return "html:<script> alert('" + nickname + "님, 안녕히 가세요.'); location.replace('../home/main'); </script>";
	}

	private String doActionDoLogin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPw");

		Member m = null;
		m = memberService.login(loginId, loginPw);
		if(m!=null) {
			session.setAttribute("loginedMemberId", m.getId());
				return "html:<script> alert('" + memberService.getMemberById((int)session.getAttribute("loginedMemberId")).getNickname()
						+ "님, 안녕하세요.'); location.replace('../home/main'); </script>";
		}
		else {
			return "html:<script> alert('ID 혹은 PW가 틀렸습니다.'); location.replace('login');</script>";
		}
	}

	private String doActionLogin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/login.jsp";
	}

	private String doActionDoJoin(HttpServletRequest req, HttpServletResponse resp) {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPw");
		String email = req.getParameter("email");
		String loginPwConfirm = req.getParameter("loginPwConfirm");

		int id = -1;
		if (loginPwConfirm.equals(loginPw)) {
			id = memberService.join(loginId, name, nickname, loginPw, email);
		} else {
			return "html:<script> alert('비밀번호 확인이 틀렸습니다.'); history.back(); </script>";
		}

		if (id > 0) {
			return "html:<script> alert('" + id + "번째 회원 가입을 환영합니다.'); location.replace('../home/main'); </script>";
		} else {
			return "html:<script> alert('이미 존재하는 ID입니다.'); history.back(); </script>";
		}
	}

	private String doActionJoin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/join.jsp";
	}

}
