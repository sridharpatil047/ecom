package me.sridharpatil.ecom.productservice.services;

import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.repositories.CategoryRepository;
import me.sridharpatil.ecom.productservice.services.dtos.CategoryRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryRequestDto requestDto) throws CategoryAlreadyExistsException {

        String categoryTitle = requestDto.getCategoryTitle().toLowerCase();

        // Check if category with same title already exists
        Optional<Category> optionalCategory = categoryRepository.findByTitle(categoryTitle);
        if (optionalCategory.isPresent() && !optionalCategory.get().isDeleted()) {
            throw new CategoryAlreadyExistsException("Category with title " + categoryTitle + " already exists");
        }

        // Since category with same title does not exist, create a new category
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setDeleted(false);
            return categoryRepository.save(category);
        }

        Category category = new Category();
        category.setTitle(categoryTitle);

        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws CategoryNotFoundException {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty() || optionalCategory.get().isDeleted()) throw new CategoryNotFoundException("Category with id " + categoryId + " not found");

        return optionalCategory.get();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryRequestDto requestDto) throws CategoryNotFoundException {

        // Check if category with given id exists, if not throw exception
        Category category = getCategoryById(categoryId);

        // Since category with given id exists, update the category
        category.setTitle(requestDto.getCategoryTitle());

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategoryPartial(Long categoryId, CategoryRequestDto requestDto) {
        return null;
    }

    @Override
    public Category deleteCategory(Long categoryId) throws CategoryNotFoundException {

        // Check if category with given id exists, if not return null
        Category category = getCategoryById(categoryId);

        // Since category with given id exists, soft delete the category
        category.setDeleted(true);

        return categoryRepository.save(category);
    }
}
