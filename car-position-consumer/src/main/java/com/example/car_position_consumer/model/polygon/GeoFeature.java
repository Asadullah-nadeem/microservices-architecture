package com.example.car_position_consumer.model.polygon;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoFeature {
    private String name;
    private GeometryPoint geometry;
}
