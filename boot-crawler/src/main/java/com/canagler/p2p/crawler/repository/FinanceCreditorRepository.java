package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.FinanceCreditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 债权转让
 * Created by scq on 2018-07-12 10:27:06
 */
@Repository
public interface FinanceCreditorRepository extends MongoRepository<FinanceCreditor, String> {

	/**
	 * 分页
	 */
	Page<FinanceCreditor> findAllByWebNameIsLike(String name, Pageable pageable);
}
