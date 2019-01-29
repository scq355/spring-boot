package com.wowjoy.boot.mongodb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Store {
    @Id
    private String id;

    private GeoJsonPoint location;


    public GeoJsonPoint getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", location=" + location +
                '}';
    }
}

