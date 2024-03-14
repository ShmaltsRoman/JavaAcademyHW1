package ru.shmalts.supplierservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shmalts.supplierservice.dto.CategoryDto;
import ru.shmalts.supplierservice.dto.ProductDto;
import ru.shmalts.supplierservice.entity.Category;
import ru.shmalts.supplierservice.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
//    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDTO(Product model);

    @Mapping(target = "category.id", source = "categoryId")
    Product toModel(ProductDto dto);
}
