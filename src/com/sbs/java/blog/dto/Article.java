package com.sbs.java.blog.dto;

import java.util.Map;

public class Article extends Dto{
	private String updateDate;
	private String title;
	private String body;
	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	private int hit;
	private int cateItemId;
	private int displayStatus;
	
	public Article() {
		
	}
	
	public Article(Map<String, Object> row) {
		super(row);

		this.updateDate = (String) row.get("updateDate");
		this.cateItemId = (int) row.get("cateItemId");
		this.title = (String) row.get("title");
		this.body = (String) row.get("body");
		this.hit = (int) row.get("hit");
		this.displayStatus=(int)row.get("displayStatus");
	}
	
	@Override
	public String toString() {
		return "Article [Id()=" + getId() + ", title=" + title + ", body=" + body + ", getRegDate()=" + getRegDate()
				+ ", updateDate=" + updateDate + "]";
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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

	public int getCateItemId() {
		return cateItemId;
	}

	public void setCateItemId(int cateItemId) {
		this.cateItemId = cateItemId;
	}

	public int getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(int displayStatus) {
		this.displayStatus = displayStatus;
	}
}
