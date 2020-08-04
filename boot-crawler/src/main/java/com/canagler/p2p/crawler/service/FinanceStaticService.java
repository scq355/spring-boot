package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.common.SpringDataPageable;
import com.canagler.p2p.crawler.domain.FinanceStatic;
import com.canagler.p2p.crawler.repository.FinanceStaticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by scq on 2018-07-16 10:50:49
 */
@Service(value = "financeStaticService")
public class FinanceStaticService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceStatic.class);

	@Autowired
	private FinanceCreditorService financeCreditorService;
	@Autowired
	private FinanceStaticRepository financeStaticRepository;

	/**
	 * 按照日期获取全部统计数据
	 */
	public List<FinanceStatic> findAll() {
		Sort sort = Sort.by(Sort.Direction.DESC, "deadline");
		return financeStaticRepository.findAll(sort);
	}

	/**
	 * 分页获取列表数据
	 */
	public Page<FinanceStatic> findList(String webName, Integer pageNumber) {
		SpringDataPageable pageable = new SpringDataPageable();
		pageable.setPagenumber(pageNumber);
		pageable.setPagesize(Constants.PAGE_SIZE);
		Sort sort = Sort.by(Sort.Direction.DESC, "deadline");
		pageable.setSort(sort);
		return financeStaticRepository.findAllByWebNameIsLike(webName, pageable);
	}


	/**
	 * 债权转让数据更新
	 */
	@Transactional
	public void staticDataUpdate() {
		LOGGER.info("更新债权统计数据,当前时间={}", new Date());
		List<FinanceStatic> financeStaticList = financeCreditorService.findInGroupByName();
		List<FinanceStatic> financeStaticListOld = financeStaticRepository.findAll();
		Date nowDate = new Date();
		if (financeStaticListOld.size() == 0) {
			List<FinanceStatic> firstList = new ArrayList<>();
			for (FinanceStatic now : financeStaticList) {
				FinanceStatic financeStatic = new FinanceStatic(now.getWebName(), now.getDeadline(), now.getTotalTransfer(), new Date());
				firstList.add(financeStatic);
			}
			mongoTemplate.insertAll(firstList);
		} else {
			for (FinanceStatic financeStatic : financeStaticList) {
				for (FinanceStatic old : financeStaticListOld) {
					if (old.getWebName().equals(financeStatic.getWebName()) && old.getDeadline().equals(financeStatic.getDeadline())) {
						// 存在的数据更新
						Query query = new Query(Criteria.where("_id").is(old.getId()));
						Update update = new Update().set("webName", old.getWebName())
								.set("deadline", old.getDeadline())
								.set("totalTransferPrevious", old.getTotalTransfer())
								.set("totalTransfer", financeStatic.getTotalTransfer())
								.set("now", nowDate)
								.set("previous", old.getNow());

						mongoTemplate.findAndModify(query, update, FinanceStatic.class);
						break;
					}
				}
				// 不存在的数据插入
				Optional<FinanceStatic> optional = financeStaticRepository.findByWebNameAndDeadline(financeStatic.getWebName(), financeStatic.getDeadline());
				if (!optional.isPresent()) {
					FinanceStatic fsInsert = new FinanceStatic();
					fsInsert.setWebName(financeStatic.getWebName());
					fsInsert.setDeadline(financeStatic.getDeadline());
					fsInsert.setTotalTransferPrevious(new BigDecimal(0));
					fsInsert.setTotalTransfer(financeStatic.getTotalTransfer());
					fsInsert.setNow(nowDate);
					fsInsert.setPrevious(nowDate);
					financeStaticRepository.insert(fsInsert);
				}
			}
		}
	}
}
