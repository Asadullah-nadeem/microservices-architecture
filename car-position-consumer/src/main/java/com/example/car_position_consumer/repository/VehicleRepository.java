package com.example.car_position_consumer.repository;

import com.example.car_position_consumer.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface VehicleRepository extends MongoRepository<Vehicle,String> {
    List<Vehicle> findByPolygonId(String polygonId);
}
