package ru.shmalts.supplierservice.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shmalts.supplierservice.entity.Category;
import ru.shmalts.supplierservice.entity.Product;
import ru.shmalts.supplierservice.repository.CategoryRepository;
import ru.shmalts.supplierservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(BigDecimal price, Integer categoryId, String name, String description,
                                        Integer pageNum,
                                        Integer pageSize) {
        Pageable pageable;
        if (pageNum != null && pageSize != null) {
            pageable = PageRequest.of(pageNum, pageSize);
        } else {
            pageable = Pageable.unpaged();
        }

        return productRepository.findAllWithFilter(price, categoryId, name, description, pageable);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).get();
    }

    public Product updateProduct(Product product, Integer id) {
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
