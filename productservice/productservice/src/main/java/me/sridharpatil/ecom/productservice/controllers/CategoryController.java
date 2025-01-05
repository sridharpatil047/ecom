package me.sridharpatil.ecom.productservice.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerCategoryReqDto;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerCategoryResDto;
import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.services.CategoryService;
import me.sridharpatil.ecom.productservice.services.dtos.CategoryRequestDto;
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
    public ResponseEntity<ControllerCategoryResDto> createCategory(
            @RequestBody @Valid ControllerCategoryReqDto requestDto
    ) throws CategoryAlreadyExistsException {

        log.debug("Received request to create a new category");
        // Map request to service dto
        CategoryRequestDto serviceRequestDto = new CategoryRequestDto();
        serviceRequestDto.setCategoryTitle(requestDto.getTitle());


        // Save category to database
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.createCategory(serviceRequestDto)
                );

        log.info("Created category with id : {}", responseDto.getId());
        // Return response
        return ResponseEntity.ok(responseDto);
    }

    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<ControllerCategoryResDto> getCategoryById(@PathVariable("id") Long id) throws CategoryNotFoundException {

        log.debug("Received request to get category by id : {}", id);

        // Get category by id
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.getCategoryById(id)
                );

        // Return response
        log.info("Returning category with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

    // Get all categories
    @GetMapping()
    public ResponseEntity<List<ControllerCategoryResDto>> getAllCategories() {

        log.debug("Received request to get all categories");

        List<ControllerCategoryResDto> resDtoList = new ArrayList<>();

        List<Category> categoryList = categoryService.getAllCategories();
        for (Category category : categoryList) {
            resDtoList.add(ControllerCategoryResDto.of(category));
        }

        log.info("Returning {} categories", resDtoList.size());
        return ResponseEntity.ok(resDtoList);
    }

    // Update category by id
    @PutMapping("/{id}")
    public ResponseEntity<ControllerCategoryResDto> updateCategoryById(
            @PathVariable("id") Long id,
            @RequestBody ControllerCategoryReqDto requestDto
    ) throws CategoryNotFoundException {


        log.debug("Received request to update category by id : {}", id);

        // Map request to service dto
        CategoryRequestDto serviceRequestDto = new CategoryRequestDto();
        serviceRequestDto.setCategoryTitle(requestDto.getTitle());

        // Update category by id
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.updateCategory(id, serviceRequestDto)
                );

        // Return response
        log.info("Updated category with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }


    // Delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ControllerCategoryResDto> deleteCategoryById(@PathVariable("id") Long id) throws CategoryNotFoundException {

        log.debug("Received request to delete category by id : {}", id);

        // Delete category by id
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.deleteCategory(id)
                );

        // Return response
        log.info("Deleted category with id : {}", responseDto.getId());
        return ResponseEntity.ok(responseDto);
    }

}
