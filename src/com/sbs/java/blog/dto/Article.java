package com.sbs.java.blog.dto;

import java.util.Map;

public class Article extends Dto{
	private String title;
	private String body;
	private int writerId;
	private int hit;
	private int cateItemId;

	public Article(Map<String, Object> row) {
		super(row);

		this.cateItemId = (int) row.get("cateItemId");
		this.title = (String) row.get("title");
		this.body = (String) row.get("body");
		this.hit = (int) row.get("hit");
		this.writerId = (int)row.get("writerId");
	}
	
	public String getBodyForXTemplate() {
		return body.replaceAll("(?i)script", "<!--REPLACE:script-->").trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getWriterId() {
		return writerId;
	}

	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getCateItemId() {
		return cateItemId;
	}

	public void setCateItemId(int cateItemId) {
		this.cateItemId = cateItemId;
	}

	@Override
	public String toString() {
		return "Article [cateItemId=" + cateItemId + ", writerId=" + writerId + ", hit=" + hit + ", title=" + title
				+ ", body=" + body + ", getId()=" + getId() + ", getRegDate()=" + getRegDate() + ", getUpdateDate()="
				+ getUpdateDate() + ", getExtra()=" + getExtra() + "]";
	}
}
