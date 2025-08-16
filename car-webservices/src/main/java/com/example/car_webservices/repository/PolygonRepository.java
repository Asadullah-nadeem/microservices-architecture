package com.example.car_webservices.repository;

import com.example.car_webservices.model.polygon.MyPolygon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolygonRepository extends MongoRepository<MyPolygon,String> {
}
