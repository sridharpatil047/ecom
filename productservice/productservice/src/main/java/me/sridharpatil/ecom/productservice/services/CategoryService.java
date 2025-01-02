package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.services.dtos.CategoryRequestDto;

public interface CategoryService {
    // Create
    Category createCategory(CategoryRequestDto requestDto) throws CategoryAlreadyExistsException;

    // Read
    Category getCategoryById(Long categoryId) throws CategoryNotFoundException;

    // Update
    Category updateCategory(Long categoryId, CategoryRequestDto requestDto) throws CategoryNotFoundException;
    Category updateCategoryPartial(Long categoryId, CategoryRequestDto requestDto);

    // Delete
    Category deleteCategory(Long categoryId) throws CategoryNotFoundException;
}
