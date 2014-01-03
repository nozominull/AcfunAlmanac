package com.nozomi.almanac.model;

public class TableItem {

	private int c;
	private String a;
	private String b;

	public TableItem(int c, String a, String b) {
		this.c = c;
		this.a = a;
		this.b = b;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}