package com.wowjoy.boot.mongodb.service;

import com.alibaba.fastjson.JSON;
import com.wowjoy.boot.mongodb.domain.Person;
import com.wowjoy.boot.mongodb.domain.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author scq
 */
@Service
public class VenueService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperations mongoOperations;

    /**
     * 地理空间查询
     */
    public void geography() {
        // Circle
        Circle circle = new Circle(-73.99171, 40.738868, 0.01);
        List<Venue> venues = mongoTemplate.find(query(where("location").within(circle)), Venue.class);
        System.out.println(JSON.toJSONString(venues));

        // 使用球面坐标查找场地
        circle = new Circle(-73.99171, 40.738868, 0.003712240453784);
        venues = mongoTemplate.find(query(where("location").withinSphere(circle)), Venue.class);

        // Box
        Box box = new Box(new Point(-73.99756, 40.73083), new Point(-73.988135, 40.741404));
        venues = mongoTemplate.find(query(where("location").within(box)), Venue.class);

        // Point
        Point point = new Point(-73.99171, 40.738868);
        venues = mongoTemplate.find(query(where("location").near(point).maxDistance(0.01)), Venue.class);

        venues = mongoTemplate.find(query(where("location").near(point).minDistance(0.01).maxDistance(100)), Venue.class);


        point = new Point(-73.99171, 40.738868);
        // 查找Point使用球面坐标附近的场地
        venues = mongoTemplate.find(query(where("location").nearSphere(point).maxDistance(0.003712240453784)), Venue.class);

        // 查找周围10英里内的所有餐馆
        NearQuery nearQuery = NearQuery.near(point).maxDistance(new Distance(10, Metrics.MILES));
        GeoResults<Person> geoResults = mongoOperations.geoNear(nearQuery, Person.class);
    }


}
