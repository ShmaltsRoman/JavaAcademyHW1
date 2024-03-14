package ru.shmalts.supplierservice.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.shmalts.supplierservice.dto.ProductDto;
import ru.shmalts.supplierservice.entity.Product;
import ru.shmalts.supplierservice.mapper.ProductMapper;
import ru.shmalts.supplierservice.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Request (createProduct) : {}", productDto);
        Product product = productMapper.toModel(productDto);
        ProductDto response = productMapper.toDTO(productService.createProduct(product));
        logger.info("Response (createProduct) : {}", response);

        return response;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<ProductDto> getAllProducts(@RequestParam(value = "price", required = false) BigDecimal price,
                                           @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize
                                           ) {
        logger.info("Request (getAllProducts)");
        List<ProductDto> response = productService.getAllProducts(price, categoryId, name, description, pageNum, pageSize).stream()
                .map(productMapper::toDTO).toList();
        logger.info("Response (getAllProducts) : {}", response);

        return response;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductDto getProductById(@PathVariable Integer id) {
        logger.info("Request (getProductById) : {}", id);
        ProductDto response = productMapper.toDTO(productService.getProductById(id));
        logger.info("Response (getProductById) : {}", response);

        return response;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ProductDto updateProduct(@RequestBody ProductDto productDto, @PathVariable Integer id) {
        logger.info("Request (updateProduct) : {} {}", id, productDto);
        Product product = productMapper.toModel(productDto);
        ProductDto response = productMapper.toDTO(productService.updateProduct(product, id));
        logger.info("Response (updateProduct) : {}", response);

        return response;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Integer id) {
        logger.info("Request (deleteProduct) : {}", id);
        productService.deleteProduct(id);
        logger.info("Response (deleteProduct)");
    }
}
