package com.canagler.p2p.crawler.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 抓取信息类型枚举
 * Created by scq on 2018-07-12 11:47:07
 */
public enum InfoTypeEnum {
	INFO_DISCUSS(1, "舆情评论"),INFO_FINANCE(2, "债权转让");

	private Integer type;
	private String info;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	InfoTypeEnum(Integer type, String info) {
		this.type = type;
		this.info = info;
	}

	public static Map<Integer, InfoTypeEnum> infoTypeEnumMap;

	static {
		infoTypeEnumMap = new HashMap<>();
		for (InfoTypeEnum statusEnum : values()) {
			infoTypeEnumMap.put(statusEnum.getType(), statusEnum);
		}
	}

	public static InfoTypeEnum getByType(Integer type) {
		if (type != null) {
			return infoTypeEnumMap.get(type);
		}
		return null;
	}
}
