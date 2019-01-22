package com.canagler.p2p.crawler.util;

import com.canagler.p2p.crawler.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by scq on 2018-04-08 17:46:27
 */
public class NumberUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtil.class);

	/**
	 * 判断是否为整数
	 * @param str:字符串
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 获取精确到秒的时间戳
	 */
	public static int getSecondTimestamp(Date date){
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0,length-3));
		} else {
			return 0;
		}
	}

	/**
	 * 获取页码边界值（一共有多少页）
	 * @param pagingStr：截取的分页字符串 eg:
	 * eg1: 12345678910... 350 / 350 页下一页
	 * eg2: 首页 上一页 1 2 3 4 5 ... 下一页 末页 共312条数据 1/16
	 * eg3: 2 3 4 5 6 7 8 9 10 ... 38 下一页
	 */
	public static Integer getPageBounds(String pagingStr) {
		Integer pageBound = 1;
		try {
			String sperator = Constants.STRING_ZERO;
			String targetStr = pagingStr;
			if (pagingStr.contains(Constants.STRING_CHARACTER_SLASH)) {
				sperator = Constants.STRING_CHARACTER_SLASH;
			} else if (pagingStr.contains(Constants.STRING_CHARACTER_COLON)) {
				targetStr = pagingStr.replace(Constants.STRING_CHARACTER_COLON, Constants.STRING_CHARACTER_STAR);
				sperator = Constants.STRING_CHARACTER_STAR;
			} else if (pagingStr.contains(Constants.STRING_CHARACTER_SPACE)) {
				sperator = Constants.STRING_CHARACTER_SPACE;
			} else {
				// 未知情况....
			}
			String[] pagingArray = targetStr.split(sperator);

			String boundStr = pagingArray[pagingArray.length - 1];
			String regexStr = "[^0-9]";
			Pattern pattern = Pattern.compile(regexStr);
			Matcher matcher = pattern.matcher(boundStr);
			String bound = matcher.replaceAll("").trim();
			pageBound = Integer.parseInt(bound);
		} catch (Exception e) {
			LOGGER.error("获取页面数值出错：{}", e.getMessage());
		}
		return pageBound;
	}
}
