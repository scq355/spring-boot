package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.WebConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by scq on 2018-04-08 11:23:30
 */
@Repository
public interface WebConfigRepository extends MongoRepository<WebConfig, String> {

}
