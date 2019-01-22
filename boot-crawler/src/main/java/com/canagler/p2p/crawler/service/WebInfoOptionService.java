package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.domain.WebInfoOption;
import com.canagler.p2p.crawler.repository.WebInfoOptionRepository;
import com.google.common.base.Strings;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 网页登录分页相关信息
 * Created by scq on 2018-04-11 17:01:27
 */
@Service(value = "webInfoOptionService")
public class WebInfoOptionService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebInfoOptionService.class);

	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private WebInfoOptionRepository webInfoOptionRepository;

	/**
	 * 网页登录分页相关信息列表
	 */
	public List<WebInfoOption> list() {
		return webInfoOptionRepository.findAll();
	}

	/**
	 * 保存记录
	 * @param id：ID
	 * @param pagingType：分页（0：全局刷新，1：局部刷新）
	 * @param loginRequired：登录需要（0：不需要，1：需要）
	 */
	public void save(String id, Integer requestType, Integer pagingType, Integer loginRequired, String name) {
		try {
			infoOptionUpdateWebInfo(id);
			webInfoOptionRepository.save(new WebInfoOption(id, requestType, pagingType, loginRequired, name));

		} catch (Exception e) {
			LOGGER.error("添加网页登录分页相关信息出错：{}", e.getMessage());
		}
	}

	/**
	 * 根据ID获取数据
	 * @param id：ID
	 */
	public WebInfoOption findById(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return null;
		}
		Optional<WebInfoOption> optionalWebInfoOption = webInfoOptionRepository.findById(id);
		return optionalWebInfoOption.orElse(null);
	}

	/**
	 * 根据ID删除记录
	 * @param id：ID
	 */
	public void deleteById(String id) {
		webInfoOptionRepository.deleteById(id);
	}

	/**
	 * 字段修改
	 * @param id：ID
	 * @param pagingType：是否分页
	 * @param loginRequired：是否登录
	 * @param name：网站名称
	 */
	public UpdateResult updateWebInfoOption(String id, Integer requestType, Integer pagingType, Integer loginRequired, String name) {
		try {
			infoOptionUpdateWebInfo(id);
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = new Update();
			update.set("requestType", requestType);
			update.set("pagingType", pagingType);
			update.set("loginRequired", loginRequired);
			update.set("name", name);
			return mongoTemplate.updateMulti(query, update, WebInfoOption.class);
		} catch (Exception e) {
			LOGGER.error("字段修改出错：{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 更新关联状态
	 * @param id：ID
	 */
	private void infoOptionUpdateWebInfo(String id) {
		WebInfo webInfo = webInfoService.findById(id);
		if (webInfo != null) {
			Integer processStatus = Constants.PROCESS_STATUS_PAGING_AND_LOGIN;
			webInfo.setProcessStatus(processStatus);
			webInfoService.save(webInfo);
		}
	}
}
