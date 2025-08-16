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
                // This is the onFailure part
                log.error("Error sending recovered message for key {}: {}", key, ex.getMessage());
            } else {
                // This is the onSuccess part
                log.info("Recovered message sent successfully for key: {}, value: {}, partition: {}",
                        key,
                        message,
                        result.getRecordMetadata().partition());
            }
        });
    }
}