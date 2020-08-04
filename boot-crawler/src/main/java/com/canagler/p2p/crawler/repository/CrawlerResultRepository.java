package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.CrawlerResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by scq on 2018-04-08 11:23:08
 */
@Repository
public interface CrawlerResultRepository extends MongoRepository<CrawlerResult, String>{
	/**
	 * 根据ID获取记录
	 * @param id：ID
	 */
	Optional<CrawlerResult> findById(@NonNull String id);

	/**
	 * 根据状态获取数据列表
	 * @param statusList
	 * @return
	 */
	List<CrawlerResult> findAllByStatusNotIn(List<Integer> statusList);

	Page<CrawlerResult> findAllByStatusNotInAndNameLike(List<Integer> statusList, String name, Pageable pageable);

}
