package com.joken.notice.message.model;

/**
 * 图文消息实体
 * <p>。
 * @version V0.0.1, Aug 5, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class Article {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String description;
	/**
	 * 地址
	 */
	private String url;
	/**
	 * 图片地址
	 */
	private String picUrl;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}