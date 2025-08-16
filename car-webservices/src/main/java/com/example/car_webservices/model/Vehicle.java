package com.example.car_webservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Update;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Vehicle {
    private Long id;
    @Id
    private String vin;
    private String numberPlate;
    private Update.Position position;
    private Double fuel;
    private String model;
    private String polygonId;
    private Instant lastModified;

}