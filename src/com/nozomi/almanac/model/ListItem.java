package com.nozomi.almanac.model;

public class ListItem {

	private String name;
	private String good;
	private String bad;

	public ListItem(String name, String good, String bad) {
		this.name = name;
		this.good = good;
		this.bad = bad;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}
}