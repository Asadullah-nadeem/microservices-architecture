package com.example.car_position_consumer.model.polygon;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeometryPoint {
    private String type;
    private List<Double> coordinates = null;
}
