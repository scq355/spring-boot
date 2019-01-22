package com.canagler.p2p.crawler.interceptors;

import com.canagler.p2p.crawler.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by scq on 2018-04-10 17:15:14
 */
@RestControllerAdvice(basePackages = "com.canagler.p2p.crawler")
public class CrawlerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CrawlerExceptionHandler.class);

	@Value("${response.error.debug}")
	private boolean responseErrorDebug;

	/**
	 * 异常通用处理
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Result> handleException(Exception e) {
		LOG.error("[异常处理]错误信息为:", e);
		return new ResponseEntity<>(new Result(Result.ERROR.getCode(), Result.ERROR.getMsg()), HttpStatus.OK);
	}
}
