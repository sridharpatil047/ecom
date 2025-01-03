package me.sridharpatil.ecom.productservice.controllers;

import jakarta.validation.Valid;
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

        // Map request to service dto
        CategoryRequestDto serviceRequestDto = new CategoryRequestDto();
        serviceRequestDto.setCategoryTitle(requestDto.getTitle());


        // Save category to database
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.createCategory(serviceRequestDto)
                );

        // Return response
        return ResponseEntity.ok(responseDto);
    }

    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<ControllerCategoryResDto> getCategoryById(@PathVariable("id") Long id) throws CategoryNotFoundException {

        // validate id
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        // Get category by id
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.getCategoryById(id)
                );

        // Return response
        return ResponseEntity.ok(responseDto);
    }

    // Get all categories
    @GetMapping()
    public ResponseEntity<List<ControllerCategoryResDto>> getAllCategories() {

        List<ControllerCategoryResDto> resDtoList = new ArrayList<>();

        List<Category> categoryList = categoryService.getAllCategories();
        for (Category category : categoryList) {
            resDtoList.add(ControllerCategoryResDto.of(category));
        }

        return ResponseEntity.ok(resDtoList);
    }

    // Update category by id
    @PutMapping("/{id}")
    public ResponseEntity<ControllerCategoryResDto> updateCategoryById(
            @PathVariable("id") Long id,
            @RequestBody ControllerCategoryReqDto requestDto
    ) throws CategoryNotFoundException {

        // Validate request
        if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Map request to service dto
        CategoryRequestDto serviceRequestDto = new CategoryRequestDto();
        serviceRequestDto.setCategoryTitle(requestDto.getTitle());

        // Update category by id
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.updateCategory(id, serviceRequestDto)
                );

        // Return response
        return ResponseEntity.ok(responseDto);
    }


    // Delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ControllerCategoryResDto> deleteCategoryById(@PathVariable("id") Long id) throws CategoryNotFoundException {

        // validate id
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        // Delete category by id
        ControllerCategoryResDto responseDto =
                ControllerCategoryResDto.of(
                        categoryService.deleteCategory(id)
                );

        // Return response
        return ResponseEntity.ok(responseDto);
    }

}
