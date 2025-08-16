package com.example.car_position_producer.service;

import com.example.car_position_producer.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class VehiclesService {
    private RestTemplate restTemplate;

    @Value("${vehicles.url}")
    public String vehicleUrl;

    public VehiclesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Vehicle[] getVehicleInfo(){
        ResponseEntity<Vehicle[]> responseEntity = restTemplate.getForEntity(
                vehicleUrl+"/vehicles/Stuttgart", Vehicle[].class);

        return responseEntity.getBody();
    }


}
