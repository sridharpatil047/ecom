package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.services.dtos.CategoryRequest;

import java.util.List;

public interface CategoryService {
    // Create
    Category createCategory(CategoryRequest requestDto) throws CategoryAlreadyExistsException;

    // Read
    Category getCategoryById(Long categoryId) throws CategoryNotFoundException;
    List<Category> getAllCategories();

    // Update
    Category updateCategory(Long categoryId, CategoryRequest requestDto) throws CategoryNotFoundException;
    Category updateCategoryPartial(Long categoryId, CategoryRequest requestDto);

    // Delete
    Category deleteCategory(Long categoryId) throws CategoryNotFoundException;
}
