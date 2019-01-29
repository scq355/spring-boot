package com.wowjoy.boot.mongodb.repository;

import com.wowjoy.boot.mongodb.domain.Store;
import org.springframework.data.geo.Polygon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author scq
 */
public interface StoreRepository extends CrudRepository<Store, String> {
    List<Store> findByLocationWithin(Polygon polygon);
}
