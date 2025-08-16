package com.example.car_position_consumer.repository;

import com.example.car_position_consumer.model.polygon.MyPolygon;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PolygonRepository extends MongoRepository<MyPolygon,String> {
}
