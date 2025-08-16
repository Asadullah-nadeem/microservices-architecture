package com.example.car_position_producer.producer;

import com.example.car_position_producer.model.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

import java.util.List;


@Component
@Slf4j
public class VehicleEventProducer {

    private final String topic = "vehicle-position-events";
    private final KafkaTemplate<String, Vehicle> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public VehicleEventProducer(KafkaTemplate<String, Vehicle> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

//    public ListenableFuture<SendResult<String,Vehicle>> sendVehicleEvents(Vehicle vehicle) {
//
//        String key = vehicle.getVin();
//
//        ProducerRecord<String,Vehicle> producerRecord = buildProducerRecord(key, vehicle, topic);
//
//        ListenableFuture<SendResult<String,Vehicle>> listenableFuture =  kafkaTemplate.send(producerRecord);
//        listenableFuture.addCallback(new VehicleEventListenableFutureCallback(key,vehicle));
//
//        return listenableFuture;
//    }

    public CompletableFuture<SendResult<String, Vehicle>> sendVehicleEvents(Vehicle vehicle) {
        String key = vehicle.getVin();
        ProducerRecord<String, Vehicle> producerRecord = buildProducerRecord(key, vehicle, topic);

        CompletableFuture<SendResult<String, Vehicle>> future = kafkaTemplate.send(producerRecord);
        future.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                log.error("Failed to send event for {}: {}", key, throwable.getMessage(), throwable);
            } else {
                log.info("Successfully sent event for {}: {}", key, vehicle);
            }
        });

        return future;
    }

    private ProducerRecord<String, Vehicle> buildProducerRecord(String key, Vehicle value, String topic) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "Rest Call".getBytes()));

        return new ProducerRecord<String, Vehicle>(topic, null, key, value, recordHeaders);
    }
}   
