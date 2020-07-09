package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.DBUtil;

public class MemberDao extends Dao {
	private Connection dbConn;

	public MemberDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public int join(String loginId, String name, String nickname, String loginPw) {
		// 기존에 존재하는 ID인지 확인
		if(checkIdExists(loginId)) {
			return -1;
		}
		else {
			String sql = "";
			
			sql += String.format("INSERT INTO member ");
			sql += String.format("SET regDate = NOW() ");
			sql += String.format(", loginId = '%s' ", loginId);
			sql += String.format(", name = '%s' ", name);
			sql += String.format(", nickname = '%s' ", nickname);
			sql += String.format(", loginPw = '%s' ", loginPw);
			
			return DBUtil.insert(dbConn, sql);
		}
	}

	private boolean checkIdExists(String loginId) {
		String sql = "";
		
		sql += String.format("SELECT * FROM member ");
		sql += String.format("WHERE loginId = '%s' ", loginId);
		
		if(DBUtil.selectRow(dbConn, sql).isEmpty()) {
			return false;
		}
		return true;
	}

	public Member login(String loginId, String loginPw) {
		String sql = "";
		
		sql += String.format("SELECT * FROM member ");
		sql += String.format("WHERE loginId = '%s' ", loginId);
		sql += String.format("AND loginPw = '%s' ", loginPw);
		
		if(!DBUtil.selectRow(dbConn, sql).isEmpty()) {
			Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
			Member m = new Member(row);
			return m;
		}

		return null;
	}
}