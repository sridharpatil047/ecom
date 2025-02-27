package me.sridharpatil.ecom.productservice.services;

import lombok.extern.log4j.Log4j2;
import me.sridharpatil.ecom.productservice.exceptions.CategoryAlreadyExistsException;
import me.sridharpatil.ecom.productservice.exceptions.CategoryNotFoundException;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.repositories.CategoryRepository;
import me.sridharpatil.ecom.productservice.services.dtos.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CategoryServiceImpl implements CategoryService{
    CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryRequest requestDto) throws CategoryAlreadyExistsException {

        log.debug("Checking if category with title {} exists", requestDto.getCategoryTitle());
        String categoryTitle = requestDto.getCategoryTitle().toLowerCase();

        // Check if category with same title already exists
        log.debug("Checking if category with title {} exists", categoryTitle);
        Optional<Category> optionalCategory = categoryRepository.findByTitle(categoryTitle);
        if (optionalCategory.isPresent() && !optionalCategory.get().isDeleted()) {
            log.error("Category with title {} already exists", categoryTitle);
            throw new CategoryAlreadyExistsException("Category with title " + categoryTitle + " already exists");
        }

        // Since category with same title does not exist, create a new category
        log.debug("Creating a new category with title : {}", categoryTitle);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setDeleted(false);
            return categoryRepository.save(category);
        }

        Category category = new Category();
        category.setTitle(categoryTitle);

        log.info("Saving category with title : {}", categoryTitle);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws CategoryNotFoundException {

        log.debug("Checking if category with id {} exists", categoryId);
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty() || optionalCategory.get().isDeleted()) {
            log.error("Category with id {} not found", categoryId);
            throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
        }

        log.info("Returning category with id : {}", categoryId);
        return optionalCategory.get();
    }

    @Override
    public List<Category> getAllCategories() {
        log.debug("Getting all categories");
        return categoryRepository.findByDeletedFalse();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryRequest requestDto) throws CategoryNotFoundException {

        // Check if category with given id exists, if not throw exception
        log.debug("Checking if category with id {} exists", categoryId);
        Category category = getCategoryById(categoryId);

        // Since category with given id exists, update the category
        log.debug("Updating category with id : {}", categoryId);
        category.setTitle(requestDto.getCategoryTitle());

        log.info("Saving category with id : {}", categoryId);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategoryPartial(Long categoryId, CategoryRequest requestDto) {
        return null;
    }

    @Override
    public Category deleteCategory(Long categoryId) throws CategoryNotFoundException {

        // Check if category with given id exists, if not return null
        log.debug("Checking if category with id {} exists", categoryId);
        Category category = getCategoryById(categoryId);

        // Since category with given id exists, soft delete the category
        log.debug("Deleting category with id : {}", categoryId);
        category.setDeleted(true);

        return categoryRepository.save(category);
    }
}
