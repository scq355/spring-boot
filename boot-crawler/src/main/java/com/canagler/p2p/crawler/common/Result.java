package com.canagler.p2p.crawler.common;

/**
 * 结果集
 * Created by scq on 2018-04-04 10:02:16
 */
public class Result {

	public static final Result SUCCESS = new Result(1, "success");
	public static final Result ERROR = new Result(0, "error");
	public static final Result ERROR_SYSTEM = new Result(101, "system error");

	private int code;
	private String msg;
	private Object context;

	public Result(int code, String msg) {
		this(code, msg, null);
	}

	public Result(int code, String msg, Object context) {
		this.code = code;
		this.msg = msg;
		this.context = context;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result copyThis() {
		return new Result(code, msg, null);
	}

	public static Result error(String error) {
		return new Result(ERROR.code, error);
	}

	public static Result error(Object ctx) {
		Result r = Result.ERROR_SYSTEM.copyThis();
		r.setContext(ctx);
		return r;
	}

	public static Result success(Object ctx) {
		Result r = Result.SUCCESS.copyThis();
		r.setContext(ctx);
		return r;
	}
}
