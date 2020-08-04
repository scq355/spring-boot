package com.canagler.p2p.crawler.common;

/**
 * 常量
 * Created by scq on 2018-04-05 10:22:03
 */
public class Constants {
	// 用户代理
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
	// 公司名
	public static final String COMPANY_NAME_QSW = "钱市网";
	// 分页
	public static final int PAGE_SIZE = 12; // 分页数据条数
	// 显示
	public static final Integer IS_SHOW = 1;
	// 顺序
	public static final Integer SORT_ASC = 1;
	// 倒序
	public static final Integer SORT_DESC = -1;
	// 最大请求次数
	public static final Integer MAX_REQUEST_NUM = 5;

	/******************字符串匹配相关*****************/
	// 完整网址开头关键字：http
	public static final String COMPLETE_ADDRESS_KEY_HTTP = "http";
	// 完整网址开头关键字：www
	public static final String COMPLETE_ADDRESS_KEY_WWW = "www";
	// a标签超链接排除类型：javascript
	public static final String HREF_KEY_JAVASCRIPT = "javascript:;";
	// a标签超链接排除类型：重复性链接
	public static final String HREF_KEY_DUPLICATE_KEY = "查看全文";
	// 长度为0的字符串
	public static final String STRING_ZERO = "";
	// 字符串-空格
	public static final String STRING_CHARACTER_SPACE = " ";
	// 字符串-斜杠
	public static final String STRING_CHARACTER_SLASH = "/";
	// 字符串-冒号
	public static final String STRING_CHARACTER_COLON = "...";
	// 字符串-冒号
	public static final String STRING_CHARACTER_STAR = "#";


	/******************时间格式*****************/
	// 日期
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	// 时间
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/******************处理状态*****************/
	// 0：初始可修改
	public static final Integer PROCESS_STATUS_NEW = 0;
	// 1：修改冻结
	public static final Integer PROCESS_STATUS_INIT = 1;
	// 2：分页配置完成
	public static final Integer PROCESS_STATUS_PAGING_AND_LOGIN = 2;
	// 3：登录配置完成
	public static final Integer PROCESS_STATUS_PAGIN = 3;
	// 4：可爬取状态（爬取等待）
	public static final Integer PROCESS_STATUS_LOGIN = 4;
	// 6：正在抓取状态
	public static final Integer PROCESS_IN_OPERATION = 5;
	// 5：爬取结束（成功）
	public static final Integer PROCESS_SUCCESS_END = 6;
	// 6：爬取结束（失败）
	public static final Integer PROCESS_FAIL_END = 7;
	// 6：爬取结束（c错误）
	public static final Integer PROCESS_ERROR_END = 8;

	/******************请求方式*****************/
	// GET请求
	public static final Integer REQUEST_GET = 0;
	// POST请求
	public static final Integer REQUEST_POST= 1;

	/***************内容跟标题是否在同一个页面**************/
	// 在同一个页面
	public static final Integer CONTENT_LOCATION_SAME = 0;
	// 在不同页面
	public static final Integer CONTENT_LOCATION_DIFF = 1;

	/***************分页加载刷新方式**************/
	// 局部刷新加载
	public static final Integer PAGING_LOCAL = 0;
	// 全页面刷新加载
	public static final Integer PAGIGN_GLOBAL = 1;

	/***************是否需要登录**************/
	// 不需要登录
	public static final Integer LOGIN_NOT_REQUIRED = 0;
	// 需要登录
	public static final Integer LOGIN_REQUIRED = 1;

	/***************抓取结果状态**************/
	// 未匹配到无需处理
	public static final Integer CRAWLER_RESULT_NOT_MACHED = 0;
	// 匹配等待处理
	public static final Integer CRAWLER_RESULT_MACHED_PROCESS = 1;
	// 匹配而且处理完成
	public static final Integer CRAWLER_RESULT_MACHED_PROCESS_SUCCESS = 2;

}
