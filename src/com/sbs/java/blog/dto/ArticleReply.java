package com.sbs.java.blog.dto;

import java.util.Map;

public class ArticleReply extends Dto{
	private String name;
	private String updateDate;
	private int articleId;
	private String body;
	private int writerId;
	
	public ArticleReply(Map<String, Object> row) {
		super(row);
		this.name=(String)row.get("name");
		this.updateDate=(String)row.get("updateDate");
		this.articleId=(int)row.get("articleId");
		this.body=(String)row.get("body");
		this.writerId=(int)row.get("writerId");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
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
}
