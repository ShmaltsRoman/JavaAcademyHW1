package ru.shmalts.supplierservice.mapper;

import org.mapstruct.Mapper;
import ru.shmalts.supplierservice.dto.CategoryDto;
import ru.shmalts.supplierservice.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDTO(Category model);

    Category toModel(CategoryDto dto);
}
