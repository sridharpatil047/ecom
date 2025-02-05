package me.sridharpatil.ecom.productservice.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.producers.dtos.ProductProducerDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ProductProducer {
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public ProductProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void productCreated(ProductProducerDto productProducerDto) throws JsonProcessingException {
        log.debug("Producing product created event to Kafka");
        kafkaTemplate.send("product-service.product-created",
                productProducerDto.getId().toString(),
                objectMapper.writeValueAsString(productProducerDto));
    }

    public void productUpdated(ProductProducerDto productProducerDto) throws JsonProcessingException {
        log.debug("Producing product updated event to Kafka");
        kafkaTemplate.send("product-service.product-updated",
                productProducerDto.getId().toString(),
                objectMapper.writeValueAsString(productProducerDto));
    }
}
