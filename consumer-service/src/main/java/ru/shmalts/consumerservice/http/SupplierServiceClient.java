package ru.shmalts.consumerservice.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shmalts.consumerservice.dto.CategoryDto;
import ru.shmalts.consumerservice.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SupplierServiceClient {

    Logger logger = LoggerFactory.getLogger(SupplierServiceClient.class);
    private final RestTemplate restTemplate;

    @Value("${host:localhost}")
    private String host;

    public SupplierServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDto> getAllProducts(String name,
                                           String description,
                                           BigDecimal price,
                                           Integer categoryId,
                                           Integer pageNum,
                                           Integer pageSize) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl("http://" + host + ":8080/products/")
                .queryParam("pageSize", pageSize)
                .queryParam("pageNum", pageNum)
                .queryParam("name", name)
                .queryParam("description", description)
                .queryParam("price", price)
                .queryParam("categoryId", categoryId)
                .build();

        try {
            logger.info("SupplierServiceClient(getAllProducts) Request: {}", builder);
            ResponseEntity<ProductDto[]> result = restTemplate.exchange(builder.toUri(), HttpMethod.GET, null, ProductDto[].class);
            List<ProductDto> response = Arrays.asList(Objects.requireNonNull(result.getBody()));
            logger.info("SupplierServiceClient(getAllProducts) Response: {}", response);
            return Arrays.asList(Objects.requireNonNull(result.getBody()));
        } catch (Exception e) {
            logger.warn("SupplierServiceClient(getAllProducts) Error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<CategoryDto> getAllCategories() {
        try {
            logger.info("SupplierServiceClient(getAllCategories) Request");
            ResponseEntity<CategoryDto[]> result = restTemplate.exchange("http://" + host + ":8080/categories/", HttpMethod.GET, null, CategoryDto[].class);
            List<CategoryDto> response = Arrays.stream(Objects.requireNonNull(result.getBody())).collect(Collectors.toList());
            logger.info("SupplierServiceClient(getAllCategories) Response: {}", response);
            return response;
        } catch (Exception e) {
            logger.warn("SupplierServiceClient (getAllCategories) Error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public ProductDto createProduct(ProductDto productDto) {
        try {
            logger.info("SupplierServiceClient(createProduct) Request : {}", productDto);
            HttpEntity<ProductDto> request = new HttpEntity<>(productDto);
            ResponseEntity<ProductDto> result = restTemplate.exchange("http://" + host + ":8080/products/", HttpMethod.POST, request, ProductDto.class);
            ProductDto response = result.getBody();
            logger.info("SupplierServiceClient(createProduct) Response: {}", response);
            return response;
        } catch (Exception e) {
            logger.warn("SupplierServiceClient (createProduct) Error: {}", e.getMessage());
            return null;
        }
    }

    public ProductDto updateProduct(ProductDto productDto, Integer id) {
        try {
            logger.info("SupplierServiceClient(updateProduct) Request : {} {}", id, productDto);
            HttpEntity<ProductDto> request = new HttpEntity<>(productDto);
            ResponseEntity<ProductDto> result = restTemplate.exchange("http://" + host + ":8080/products/" + id, HttpMethod.PUT, request, ProductDto.class);
            ProductDto response = result.getBody();
            logger.info("SupplierServiceClient(updateProduct) Response: {}", response);
            return response;
        } catch (Exception e) {
            logger.warn("SupplierServiceClient (updateProduct) Error: {}", e.getMessage());
            return null;
        }
    }

    public boolean deleteProduct(Integer id) {
        try {
            logger.info("SupplierServiceClient(deleteProduct) Request : {}", id);
            restTemplate.exchange("http://" + host + ":8080/products/" + id, HttpMethod.DELETE, null, ProductDto.class);
            logger.info("SupplierServiceClient(deleteProduct) Response");
            return true;
        } catch (Exception e) {
            logger.warn("SupplierServiceClient (deleteProduct) Error: {}", e.getMessage());
            return false;
        }
    }
}
