package com.canagler.p2p.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by scq on 2018-07-13 09:48:55
 */
@Service
public class BaseService {

	@Autowired
	public MongoTemplate mongoTemplate;

	@Value("${cookie.weidai}")
	public String COOKIE_WEIDAI;
	@Value("${cookie.wdzj}")
	public String COOKIE_WDZJ;

}
