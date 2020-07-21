package com.sbs.java.blog.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class MemberDao extends Dao {
	private Connection dbConn;

	public MemberDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public int join(String loginId, String name, String nickname, String loginPw, String email) {
		// 기존에 존재하는 ID인지 확인
		if(checkIdExists(loginId)) {
			return -1;
		}
		else {
			SecSql sql = new SecSql();
			
			sql.append("INSERT INTO member ");
			sql.append("SET regDate = NOW() ");
			sql.append(", updateDate = NOW() ");
			sql.append(", loginId = ? ", loginId);
			sql.append(", name = ? ", name);
			sql.append(", nickname = ? ", nickname);
			sql.append(", loginPw = ? ", loginPw);
			sql.append(", email = ? ", email);
			
			return DBUtil.insert(dbConn, sql);
		}
	}

	private boolean checkIdExists(String loginId) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM member ");
		sql.append("WHERE loginId = ? ", loginId);
		
		if(DBUtil.selectRow(dbConn, sql).isEmpty()) {
			return false;
		}
		return true;
	}

	public Member login(String loginId, String loginPw) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM member ");
		sql.append("WHERE loginId = ? ", loginId);
		sql.append("AND loginPw = ? ", loginPw);
		
		if(!DBUtil.selectRow(dbConn, sql).isEmpty()) {
			Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
			Member m = new Member(row);
			return m;
		}

		return null;
	}
	
	public List<Member> getMembers(){
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM member ");
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Member> members = new ArrayList<>();
		
		for (Map<String, Object> row : rows) {
			members.add(new Member(row));
		}
		
		return members;
	}
	
	public Member getMemberById(int id) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM member ");
		sql.append("WHERE id = ? ", id);
		
		if(!DBUtil.selectRow(dbConn, sql).isEmpty()) {
			Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
			Member m = new Member(row);
			return m;
		}
		return null;
	}

	public void myPage(int id, String nickname, String loginPw, String email) {
		SecSql sql = new SecSql();

		sql.append("UPDATE member SET ");
		if(nickname.trim().length()!=0) {
			sql.append("nickname = ?, ", nickname);
		}
		if(loginPw.trim().length()!=0) {
			sql.append("loginPw = ?, ", loginPw);
		}
		if(email.trim().length()!=0) {
			sql.append("email = ?, ", email);
		}
		sql.append("updateDate = NOW() ");
		sql.append("WHERE id = ? ", id);

		DBUtil.update(dbConn, sql);
	}

	public int findPw(String loginId, String name, String email) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT * FROM member ");
		sql.append("WHERE loginId = ? ", loginId);
		sql.append("AND name = ? ", name);
		sql.append("AND email = ? ", email);
		
		if(!DBUtil.selectRow(dbConn, sql).isEmpty()) {
			Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
			Member m = new Member(row);
			return m.getId();
		}
		return 0;
	}

	public String resetPw(int id) throws NoSuchAlgorithmException {
		String pw = ""+System.currentTimeMillis();
		String newPw = transformPw(pw);
		
		SecSql sql = new SecSql();

		sql.append("UPDATE member SET ");
		sql.append("loginPw = ?, ", newPw);
		sql.append("updateDate = NOW() ");
		sql.append("WHERE id = ? ", id);

		DBUtil.update(dbConn, sql);
		return pw;
	}

	private String transformPw(String msg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());
		return bytesToHex(md.digest());
	}

	private String bytesToHex(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		for(byte b : digest) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}