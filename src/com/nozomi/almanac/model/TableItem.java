package com.nozomi.almanac.model;

public class TableItem {

	private int avatar;
	private String name;
	private String content;

	public TableItem(int avatar, String name, String content) {
		super();
		this.avatar = avatar;
		this.name = name;
		this.content = content;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}