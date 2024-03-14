package ru.shmalts.supplierservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.shmalts.supplierservice.dto.CategoryDto;
import ru.shmalts.supplierservice.entity.Category;
import ru.shmalts.supplierservice.mapper.CategoryMapper;
import ru.shmalts.supplierservice.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Request (createCategory) : {}", categoryDto);
        Category category = categoryMapper.toModel(categoryDto);
        CategoryDto response =  categoryMapper.toDTO(categoryService.createCategory(category));
        logger.info("Response (createCategory) : {}", response);

        return response;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<CategoryDto> getAllCategories() {
        logger.info("Request (getAllCategories)");
        List<CategoryDto> response = categoryService.getAllCategories().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Response (getAllCategories) : {}", response);

        return response;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CategoryDto getCategoryById(@PathVariable Integer id) {
        logger.info("Request (getCategoryById) : {}", id);
        CategoryDto response = categoryMapper.toDTO(categoryService.getCategoryById(id));
        logger.info("Response (getCategoryById) : {}", response);

        return response;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer id) {
        logger.info("Request (updateCategory) : {}", categoryDto);
        Category category = categoryMapper.toModel(categoryDto);
        CategoryDto response = categoryMapper.toDTO(categoryService.updateCategory(category, id));
        logger.info("Response (updateCategory) : {}", response);

        return response;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable Integer id) {
        logger.info("Request (deleteCategory) : {}", id);
        categoryService.deleteCategory(id);
        logger.info("Response (deleteCategory)");
    }
}
