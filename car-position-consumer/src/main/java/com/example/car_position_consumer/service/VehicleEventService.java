package com.example.car_position_consumer.service;


import com.example.car_position_consumer.model.Vehicle;
import com.example.car_position_consumer.repository.VehicleRepository;
import com.example.car_position_consumer.util.PolygonHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;


//@Service
//@Slf4j
//public class VehicleEventService {
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    @Autowired
//    private PolygonHelper polygonHelper;
//
//    @Autowired
//    KafkaTemplate<String, Vehicle> kafkaTemplate;
//
//    public void processVehicleEvent(ConsumerRecord<String, Vehicle> consumerRecord) {
//        Vehicle vehicle = consumerRecord.value();
//        log.info("vehicleEvent : {} ", vehicle);
//
//        vehicle.setPolygonId(polygonHelper.findVehiclePolygon(vehicle.getPosition()));
//        vehicle.setLastModified(Instant.now());
//
//        vehicleRepository.save(vehicle);
//
//    }
//
//
//    public void handleRecovery(ConsumerRecord<String,Vehicle> record){
//        log.error("handleRecovery for {}", record);
//
//        String key = record.key();
//        Vehicle message = record.value();
//        //String message = record.value().replace(":0",":-1");
//
//        ListenableFuture<SendResult<String,Vehicle>> listenableFuture = kafkaTemplate.sendDefault(key, message);
//        listenableFuture.addCallback(new VehicleEventListenableFutureCallback(key,message));
//
//
//    }
//
//}
@Service
@Slf4j
public class VehicleEventService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PolygonHelper polygonHelper;

    @Autowired
    private KafkaTemplate<String, Vehicle> kafkaTemplate;

    public void processVehicleEvent(ConsumerRecord<String, Vehicle> consumerRecord) {
        Vehicle vehicle = consumerRecord.value();
        log.info("vehicleEvent : {} ", vehicle);

        // Ensure polygonHelper expects this object type
        vehicle.setPolygonId(polygonHelper.findVehiclePolygon(vehicle.getPosition()));
        vehicle.setLastModified(Instant.now());

        vehicleRepository.save(vehicle);
    }

    public void handleRecovery(ConsumerRecord<String, Vehicle> record) {
        log.error("handleRecovery for {}", record);

        String key = record.key();
        Vehicle message = record.value();

        CompletableFuture<SendResult<String, Vehicle>> future = kafkaTemplate.sendDefault(key, message);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send event for {}: {}", key, ex.getMessage(), ex);
            } else {
                log.info("Successfully sent event for {}: {}", key, message);
            }
        });
    }
}
