package com.example.car_webservices.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle,String> {
    List<Vehicle> findByPolygonId(String polygonId);
}
