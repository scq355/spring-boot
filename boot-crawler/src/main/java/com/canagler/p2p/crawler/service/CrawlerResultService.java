package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.common.SpringDataPageable;
import com.canagler.p2p.crawler.domain.CrawlerResult;
import com.canagler.p2p.crawler.domain.WebConfig;
import com.canagler.p2p.crawler.domain.WebInfo;
import com.canagler.p2p.crawler.repository.CrawlerResultRepository;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by scq on 2018-04-08 11:26:44
 */
@Service(value = "crawlerResultService")
public class CrawlerResultService extends BaseService {
	@Autowired
	private CrawlerResultRepository crawlerResultRepository;

	/**
	 * 分页获取列表数据
	 * @param status：状态
	 */
	public Page<CrawlerResult> findList(Integer status, Integer pageNumber, String name) {
		List<Integer> statusList = new ArrayList<>();
		statusList.add(status);
		SpringDataPageable pageable = new SpringDataPageable();
		pageable.setPagenumber(pageNumber);
		pageable.setPagesize(Constants.PAGE_SIZE);
		Sort sort = Sort.by(Sort.Direction.DESC, "updateAt");
		pageable.setSort(sort);
		Page<CrawlerResult> crawlerResultPage = crawlerResultRepository.findAllByStatusNotInAndNameLike(statusList, name, pageable);
		return crawlerResultPage;
	}

	/**
	 * 根据ID获取抓取记录结果
	 * @param id：ID
	 */
	public CrawlerResult findById(String id) {
		if (StringUtils.isNoneBlank(id)) {
			Optional<CrawlerResult> optionalCrawlerResult = crawlerResultRepository.findById(id);
			return optionalCrawlerResult.orElse(null);
		}
		return null;
	}

	/**
	 * 更具ID更新状态为已解决
	 * @param id:ID
	 */
	public Integer updateStatus(String id) {
		Long modifiedCount = 0L;
		if (StringUtils.isNoneBlank(id)) {
			Query query = new Query(Criteria.where("_id").is(id));
			Update update = Update.update("status", Constants.CRAWLER_RESULT_MACHED_PROCESS_SUCCESS);
			update.set("updateAt", new Date());
			UpdateResult result = mongoTemplate.updateFirst(query, update, CrawlerResult.class);
			modifiedCount = result.getModifiedCount();
		}
		return modifiedCount.intValue();
	}


	/**
	 * 保存抓取结果
	 * @param crawlerResult：结果对象
	 */
	public void save(CrawlerResult crawlerResult) {
		crawlerResultRepository.save(crawlerResult);
	}


	/**
	 * 数据条数统计
	 */
	public Long count() {
		return crawlerResultRepository.count();
	}
}
