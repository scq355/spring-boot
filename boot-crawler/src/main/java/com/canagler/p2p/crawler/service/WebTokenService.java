package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.WebConfig;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.domain.WebInfoOption;
import com.canagler.p2p.crawler.domain.WebToken;
import com.canagler.p2p.crawler.repository.WebInfoRepository;
import com.canagler.p2p.crawler.repository.WebTokenRepository;
import com.google.common.base.Strings;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by scq on 2018-04-11 11:33:45
 */
@Service(value = "webTokenService")
public class WebTokenService extends BaseService {
	private final Logger LOGGER = LoggerFactory.getLogger(WebTokenService.class);

	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private WebTokenRepository webTokenRepository;

	/**
	 * 显示网页TOKEN列表
	 */
	public List<WebToken> list() {
		return webTokenRepository.findAll();
	}

	/**
	 * 添加网站凭证
	 * @param id：URL
	 * @param name：网站名称
	 * @param userName：用户名
	 * @param password：密码
	 * @param certificate：凭证
	 */
	public void save(String id, String name, String userName, String password, String certificate) {
		try {
			webTokenUpdateWebInfo(id);
			WebToken webToken = new WebToken(id, name, userName, password, certificate);
			webTokenRepository.save(webToken);
		} catch (Exception e) {
			LOGGER.error("添加网站凭证出错：{}", e.getMessage());
		}
	}

	/**
	 * 根据ID获取数据
	 * @param id：ID
	 */
	public WebToken findById(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return null;
		}
		Optional<WebToken> optionalWebToken = webTokenRepository.findById(id);
		return optionalWebToken.orElse(null);
	}

	/**
	 * 修改网页凭证
	 * @param id：ID
	 * @param webName：网站名
	 * @param userName：用户名
	 * @param password：密码
	 * @param certificate：凭证
	 */
	public UpdateResult updateWebToken(String id, String webName,
									   String userName, String password,
									   String certificate) {
		try {
			webTokenUpdateWebInfo(id);
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update();
			update.set("webName", webName);
			update.set("userName", userName);
			update.set("password", password);
			update.set("certificate", certificate);
			return mongoTemplate.updateMulti(query, update, WebToken.class);
		} catch (Exception e) {
			LOGGER.error("修改网页凭证出错：{}", e.getMessage());
		}
		return null;
	}


	/**
	 * 更新关联状态
	 * @param id：ID
	 */
	private void webTokenUpdateWebInfo(String id) {
		WebInfo webInfo = webInfoService.findById(id);
		if (webInfo != null) {
			webInfo.setProcessStatus(Constants.PROCESS_STATUS_LOGIN);
			webInfoService.save(webInfo);
		}
	}
}
