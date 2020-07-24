package com.sbs.java.blog.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;

public class MemberService extends Service{

	private MemberDao memberDao;

	public MemberService(Connection dbConn) {
		memberDao = new MemberDao(dbConn);
	}

	public int join(String loginId, String name, String nickname, String loginPw, String email) {
		return memberDao.join(loginId, name, nickname, loginPw, email);
	}

	public Member login(String loginId, String loginPw) {
		return memberDao.login(loginId, loginPw);
	}

	public List<Member> getMembers(){
		return memberDao.getMembers();
	}
	public Member getMemberById(int id){
		return memberDao.getMemberById(id);
	}

	public void myPage(int id , String nickname, String newPw, String email, boolean mailAuthStatusReset) {
		memberDao.myPage(id, nickname, newPw, email, mailAuthStatusReset);
	}

	public int findPw(String loginId, String name, String email) {
		return memberDao.findPw(loginId, name, email);
	}

	public String resetPw(int id) throws NoSuchAlgorithmException {
		return memberDao.resetPw(id);
	}

	public String findLoginId(String name, String email) {
		return memberDao.findLoginId(name, email);
	}

	public String transformString(String str) {
		try {
			return memberDao.transformString(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void doAuthMail(int id) {
		memberDao.doAuthMail(id);
	}
}
