package com.canagler.p2p.crawler.service;

import com.canagler.p2p.crawler.common.Constants;
import com.canagler.p2p.crawler.common.SpringDataPageable;
import com.canagler.p2p.crawler.domain.FinanceCreditor;
import com.canagler.p2p.crawler.domain.FinanceStatic;
import com.canagler.p2p.crawler.repository.FinanceCreditorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @Description 债权转让
 * Created by scq on 2018-07-12 10:27:59
 */
@Service(value = "financeCreditorService")
public class FinanceCreditorService extends BaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCreditorService.class);

	@Autowired
	private FinanceCreditorRepository financeCreditorRepository;

	public Page<FinanceCreditor> findList(Integer pageNumber, String name) {
		SpringDataPageable pageable = new SpringDataPageable();
		pageable.setPagenumber(pageNumber);
		pageable.setPagesize(Constants.PAGE_SIZE);
		pageable.setSort(Sort.by(Sort.Direction.DESC, "updateAt"));
		return financeCreditorRepository.findAllByWebNameIsLike(name, pageable);
	}

	public List<FinanceStatic> findInGroupByName() {
		TypedAggregation<FinanceCreditor> aggregation = newAggregation(FinanceCreditor.class,
				group("webName", "deadline").sum("transferAmount").as("totalTransfer"),
				sort(Sort.Direction.ASC, previousOperation(), "deadline"));

		AggregationResults<FinanceStatic> results = mongoTemplate.aggregate(aggregation, FinanceStatic.class);

		return results.getMappedResults();

	}
}
