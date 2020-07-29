package com.sbs.java.blog.dto;

import java.util.Map;

public class ArticleReply extends Dto{
	private int articleId;
	private String body;
	private int writerId;
	
	public ArticleReply(Map<String, Object> row) {
		super(row);
		this.articleId=(int)row.get("articleId");
		this.body=(String)row.get("body");
		this.writerId=(int)row.get("writerId");
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
