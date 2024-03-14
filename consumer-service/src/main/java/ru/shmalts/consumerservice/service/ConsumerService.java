package ru.shmalts.consumerservice.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.shmalts.consumerservice.dto.ProductDto;
import ru.shmalts.consumerservice.http.SupplierServiceClient;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class ConsumerService implements ApplicationRunner {
    private final SupplierServiceClient supplierServiceClient;

    public ConsumerService(SupplierServiceClient supplierServiceClient) {
        this.supplierServiceClient = supplierServiceClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        TimerTask task = new TimerTask() {
            public void run() {
                runClientRequests();
            }
        };
        new Timer("Timer").schedule(task, 0,10000L);
    }

    private void runClientRequests() {
        supplierServiceClient.getAllCategories();
        supplierServiceClient.createProduct(new ProductDto(0, "newProduct","description NewProduct", new BigDecimal("10.10"), 2));
        supplierServiceClient.updateProduct(new ProductDto(0, "updatedProduct","description updatedProduct", new BigDecimal("10.10"), 2), 3);
        supplierServiceClient.deleteProduct(3);
        supplierServiceClient.getAllProducts(null, null, null, 2, null, null);
        supplierServiceClient.getAllProducts("BM", null, null, null, null, null);
        supplierServiceClient.getAllProducts(null, null, null, null, 1, 3);
    }
}
