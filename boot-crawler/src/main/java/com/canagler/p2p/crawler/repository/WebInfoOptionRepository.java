package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.WebInfoOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by scq on 2018-04-11 17:01:46
 */
@Repository
public interface WebInfoOptionRepository extends MongoRepository<WebInfoOption, String>{
}
