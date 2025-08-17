package com.example.car_position_consumer.config;

import com.example.car_position_consumer.model.Vehicle;
import com.example.car_position_consumer.service.VehicleEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@Slf4j
public class VehicleEventsConsumerConfig {

    private final VehicleEventService vehicleEventService;
    private final KafkaProperties kafkaProperties;

    // Use constructor injection
    public VehicleEventsConsumerConfig(VehicleEventService vehicleEventService, KafkaProperties kafkaProperties) {
        this.vehicleEventService = vehicleEventService;
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory
                .getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.kafkaProperties.buildConsumerProperties())));
        factory.setConcurrency(1);

        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 2L);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                (consumerRecord, exception) -> {
                    // Check if the record's value is of the expected type before casting
                    if (exception instanceof RecoverableDataAccessException && consumerRecord.value() instanceof Vehicle) {
                        log.info("Inside recoverable logic. Attempting to recover...");
                        try {
                            // Now the cast is much safer
                            vehicleEventService.handleRecovery((ConsumerRecord<String, Vehicle>) consumerRecord);
                        } catch (Exception e) {
                            log.error("Recovery handler threw exception: {}", e.getMessage(), e);
                            throw new RuntimeException(e);
                        }
                    } else {
                        log.error("Non-recoverable exception or type mismatch for record {}: {}", consumerRecord, exception.getMessage(), exception);
                        throw new RuntimeException(exception.getMessage(), exception);
                    }
                },
                fixedBackOff
        );

        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}