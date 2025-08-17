package com.example.car_position_consumer.consumer;

import com.example.car_position_consumer.model.Vehicle;
import com.example.car_position_consumer.service.VehicleEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VehicleEventsConsumer {

    private final VehicleEventService vehicleEventService;

    // Use constructor injection instead of @Autowired
    public VehicleEventsConsumer(VehicleEventService vehicleEventService) {
        this.vehicleEventService = vehicleEventService;
    }

    @KafkaListener(topics = {"vehicle-position-events"})
    public void onMessage(ConsumerRecord<String, Vehicle> consumerRecord) {
        log.info("ConsumerRecord : {} ", consumerRecord );
        vehicleEventService.processVehicleEvent(consumerRecord);
    }
}