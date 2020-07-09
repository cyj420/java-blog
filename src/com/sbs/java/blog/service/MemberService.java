package com.sbs.java.blog.service;

import java.sql.Connection;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;

public class MemberService extends Service{

	private MemberDao memberDao;

	public MemberService(Connection dbConn) {
		memberDao = new MemberDao(dbConn);
	}

	public int join(String loginId, String name, String nickname, String loginPw) {
		return memberDao.join(loginId, name, nickname, loginPw);
	}

	public Member login(String loginId, String loginPw) {
		return memberDao.login(loginId, loginPw);
	}

}
