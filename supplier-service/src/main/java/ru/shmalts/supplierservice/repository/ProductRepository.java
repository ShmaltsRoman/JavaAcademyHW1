package ru.shmalts.supplierservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shmalts.supplierservice.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE (:price is null or p.price = :price) " +
            "and (:categoryId is null or p.categoryId = :categoryId) " +
            "and (:name is null or lower(p.name) like lower(concat('%', :name,'%'))) " +
            "and (:description is null or lower(p.description) like lower(concat('%', :description,'%')))")
    List<Product> findAllWithFilter(@Param(value = "price") BigDecimal price,
                                    @Param(value = "categoryId") Integer categoryId,
                                    @Param(value = "name") String name,
                                    @Param(value = "description") String description,
                                    Pageable pageable);

}
