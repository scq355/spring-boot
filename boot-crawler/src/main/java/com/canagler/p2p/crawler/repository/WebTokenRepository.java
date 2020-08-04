package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.WebToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by scq on 2018-04-11 11:34:43
 */
@Repository
public interface WebTokenRepository extends MongoRepository<WebToken, String> {
}
