package com.example.car_webservices.service;

import com.example.car_webservices.model.Vehicle;
import com.example.car_webservices.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Optional<Vehicle> getVehicleByVin(String vin) {
        return vehicleRepository.findById(vin);
    }

    public List<Vehicle> getVehiclesByPolygonId(String polygonId) {
        return vehicleRepository.findByPolygonId(polygonId);
    }
}
