package me.sridharpatil.ecom.productservice.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.controllers.dtos.CategoryRequestDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.CategoryResponseDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.services.CategoryService;
import me.sridharpatil.ecom.productservice.services.dtos.CategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create a new category
    @PostMapping()
    public ResponseEntity<CategoryResponseDto> createCategory(
            @RequestBody @Valid CategoryRequestDto requestDto
    ) throws CategoryAlreadyExistsException {

        log.debug("Received request to create a new category");
        // Map request to service dto
        CategoryRequest serviceRequestDto = new CategoryRequest();
        serviceRequestDto.setCategoryTitle(requestDto.getTitle());


        // Save category to database
        CategoryResponseDto responseDto =
                CategoryResponseDto.of(
                        categoryService.createCategory(serviceRequestDto)
                );

        log.info("Created category with id : {}", responseDto.getId());
        // Return response
        return ResponseEntity.ok(responseDto);
    }

    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable("id") Long id) throws CategoryNotFoundException {

        log.debug("Received request to get category by id : {}", id);

        // Get category by id
        CategoryResponseDto responseDto =
                CategoryResponseDto.of(
                        categoryService.getCategoryById(id)
                );

        // Return response
        log.info("Returning category with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // Get all categories
    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {

        log.debug("Received request to get all categories");

        List<CategoryResponseDto> resDtoList = new ArrayList<>();

        List<Category> categoryList = categoryService.getAllCategories();
        for (Category category : categoryList) {
            resDtoList.add(CategoryResponseDto.of(category));
        }

        log.info("Returning {} categories", resDtoList.size());
        return ResponseEntity.ok(resDtoList);
    }

    // Update category by id
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategoryById(
            @PathVariable("id") Long id,
            @RequestBody CategoryRequestDto requestDto
    ) throws CategoryNotFoundException {


        log.debug("Received request to update category by id : {}", id);

        // Map request to service dto
        CategoryRequest serviceRequestDto = new CategoryRequest();
        serviceRequestDto.setCategoryTitle(requestDto.getTitle());

        // Update category by id
        CategoryResponseDto responseDto =
                CategoryResponseDto.of(
                        categoryService.updateCategory(id, serviceRequestDto)
                );

        // Return response
        log.info("Updated category with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }


    // Delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> deleteCategoryById(@PathVariable("id") Long id) throws CategoryNotFoundException {

        log.debug("Received request to delete category by id : {}", id);

        // Delete category by id
        CategoryResponseDto responseDto =
                CategoryResponseDto.of(
                        categoryService.deleteCategory(id)
                );

        // Return response
        log.info("Deleted category with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

}
