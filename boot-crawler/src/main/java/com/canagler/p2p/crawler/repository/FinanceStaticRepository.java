package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.FinanceStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by scq on 2018-07-16 11:07:56
 */
@Repository
public interface FinanceStaticRepository extends MongoRepository<FinanceStatic, String> {

	/**
	 * 按名模糊匹配
	 * @param webName 网站名
	 * @param pageable 分页对象
	 */
	Page<FinanceStatic> findAllByWebNameIsLike(String webName, Pageable pageable);

	/**
	 * 根据网站名&期限获取统计数据
	 * @param webName 网站名
	 * @param deadline 期限
	 */
	Optional<FinanceStatic> findByWebNameAndDeadline(@NonNull String webName, @NonNull Integer deadline);
}
