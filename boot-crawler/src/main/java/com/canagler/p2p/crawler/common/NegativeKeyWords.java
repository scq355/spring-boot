package com.canagler.p2p.crawler.common;

/**
 * 排除类关键字
 * Created by scq on 2018-04-09 11:03:05
 */
public enum NegativeKeyWords {
	WORD_1("0", 1), WORD_2("无", 2), WORD_3("没有", 3), WORD_4("不可能", 4), WORD_5("零", 5), WORD_6("不会", 6);

	private String name;
	private int index;

	// 构造方法
	NegativeKeyWords(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (NegativeKeyWords words : NegativeKeyWords.values()) {
			if (words.getIndex() == index) {
				return words.name;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
