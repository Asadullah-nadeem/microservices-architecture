package com.example.car_position_producer.producer;

import com.example.car_position_producer.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Slf4j
public class VehicleEventListenableFutureCallback  implements ListenableFutureCallback<SendResult<String, Vehicle>> {
    private final String key;
    private final Vehicle value;

    public VehicleEventListenableFutureCallback(String key, Vehicle value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void onFailure(Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    @Override
    public void onSuccess(SendResult<String, Vehicle> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }
}   
