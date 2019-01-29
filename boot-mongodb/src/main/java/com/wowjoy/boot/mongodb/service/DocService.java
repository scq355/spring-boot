package com.wowjoy.boot.mongodb.service;

import com.wowjoy.boot.mongodb.domain.Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author scq
 */
@Service
public class DocService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void textSearch() {
        Query query = TextQuery.queryText(new TextCriteria().matchingAny("coffee", "cake")).sortByScore();
        List<Doc> page = mongoTemplate.find(query, Doc.class);

        TextQuery coffee = TextQuery.queryText(new TextCriteria().matching("coffee").matching("-cake"));
        TextQuery text = TextQuery.queryText(new TextCriteria().matching("cpffee").notMatching("cake"));


    }
}
