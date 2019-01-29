package com.wowjoy.boot.mongodb.service;

import com.alibaba.fastjson.JSON;
import com.wowjoy.boot.mongodb.domain.Store;
import com.wowjoy.boot.mongodb.repository.StoreRepository;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author scq
 */
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public StoreService(StoreRepository storeRepository,
                        MongoTemplate mongoTemplate) {
        this.storeRepository = storeRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * {
     *   "location": {
     *     "$geoWithin": {
     *       "$geometry": {
     *         "type": "Polygon",
     *         "coordinates": [
     *           [
     *             [-73.992514,40.758934],
     *             [-73.961138,40.760348],
     *             [-73.991658,40.730006],
     *             [-73.992514,40.758934]
     *           ]
     *         ]
     *       }
     *     }
     *   }
     * }
     */
    public void geoJson() {
        List<Store> locationWithin = storeRepository.findByLocationWithin(new GeoJsonPolygon(
                new Point(-73.992514, 40.758934),
                new Point(-73.961138, 40.760348),
                new Point(-73.991658, 40.730006),
                new Point(-73.992514, 40.758934)
        ));
        System.out.println(JSON.toJSONString(locationWithin));


        List<Store> stores = storeRepository.findByLocationWithin(new Polygon(
                new Point(-73.992514, 40.758934),
                new Point(-73.961138, 40.760348),
                new Point(-73.991658, 40.730006)
        ));
        System.out.println(JSON.toJSONString(stores));
    }


}
