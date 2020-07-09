package com.sbs.java.blog.dto;

import java.util.Map;

public class Member extends Dto{
	private String loginId;
	private String name;
	private String nickname;
	private String loginPw;
	
	public Member() {
		
	}
	
	public Member(Map<String, Object> row) {
		super(row);
		this.loginId=(String)row.get("loginId");
		this.name=(String)row.get("name");
		this.nickname=(String)row.get("nickname");
		this.loginPw=(String)row.get("loginPw");
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}
	
	
}
