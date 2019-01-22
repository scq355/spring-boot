package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.FirstPageContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by scq on 2018-04-18 09:20:34
 */
@Repository
public interface FirstPageContentRepository extends MongoRepository<FirstPageContent, String>{
}
