package me.sridharpatil.ecom.cartservice.services.product;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {
    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long productId) throws Exception {

        log.debug("Getting product by id: {}", productId);

        String url = UriComponentsBuilder
                .fromUriString("lb://productservice")
                .path("/private/products/{productId}")
                .buildAndExpand(productId)
                .toUriString();
        log.debug("URL is: {}", url);

        try {
            return restTemplate.getForObject(url, Product.class);
        }catch (RestClientException e){
            log.error("Error getting product by id: {}", productId);
            return null;
        }
    }
}
