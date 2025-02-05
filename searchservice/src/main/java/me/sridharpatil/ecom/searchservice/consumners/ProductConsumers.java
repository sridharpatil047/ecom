package me.sridharpatil.ecom.searchservice.consumners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.searchservice.consumners.dtos.ProductConsumerEventReqDto;
import me.sridharpatil.ecom.searchservice.documents.Product;
import me.sridharpatil.ecom.searchservice.services.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProductConsumers {

    ObjectMapper objectMapper;
    ProductService productService;

    public ProductConsumers(ObjectMapper objectMapper, ProductService productService) {
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    // Consumes when product is created in product service
    @KafkaListener(topics = "product-service.product-created", groupId = "search-service.consumers")
    public void consumeProductCreatedEvent(@Payload String message) throws JsonProcessingException {
        log.debug("Product created event consumed: {}", message);

        ProductConsumerEventReqDto productConsumerEventReqDto = objectMapper.readValue(message, ProductConsumerEventReqDto.class);

        productService.createProduct(
                Product.builder()
                        .id(productConsumerEventReqDto.getId())
                        .title(productConsumerEventReqDto.getTitle())
                        .description(productConsumerEventReqDto.getDescription())
                        .price(productConsumerEventReqDto.getPrice())
                        .category(Product.Category.builder()
                                .id(productConsumerEventReqDto.getCategory().getId())
                                .title(productConsumerEventReqDto.getCategory().getTitle())
                                .build())
                        .build());

    }

    // Consume when product is updated in product service
    @KafkaListener(topics = "product-service.product-updated", groupId = "search-service.consumers")
    public void consumeProductUpdatedEvent(@Payload String message) throws JsonProcessingException {
        log.debug("Product updated event consumed: {}", message);

        ProductConsumerEventReqDto productConsumerEventReqDto = objectMapper.readValue(message, ProductConsumerEventReqDto.class);

        productService.updateProduct(
                Product.builder()
                        .id(productConsumerEventReqDto.getId())
                        .title(productConsumerEventReqDto.getTitle())
                        .description(productConsumerEventReqDto.getDescription())
                        .price(productConsumerEventReqDto.getPrice())
                        .category(Product.Category.builder()
                                .id(productConsumerEventReqDto.getCategory().getId())
                                .title(productConsumerEventReqDto.getCategory().getTitle())
                                .build())
                        .build());

    }
}
