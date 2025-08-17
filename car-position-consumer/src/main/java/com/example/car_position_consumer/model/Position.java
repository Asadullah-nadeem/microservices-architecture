package com.example.car_position_consumer.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position{
    private double latitude;
    private double longitude;
}