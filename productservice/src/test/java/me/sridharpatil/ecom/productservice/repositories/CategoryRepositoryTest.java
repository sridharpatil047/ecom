package me.sridharpatil.ecom.productservice.repositories;

import me.sridharpatil.ecom.productservice.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private String title;
    private Category category;

    @BeforeEach
    void setUp() {
        title = "title";

        category = new Category();
        category.setTitle(title);
        categoryRepository.save(category);
    }
    @Test
    public void whenValidTitleIsProvided_thenReturnCategory() {

        // Act
        Optional<Category> optionalCategory = categoryRepository.findByTitle(title);

        assertNotNull(optionalCategory);
        assertInstanceOf(Optional.class, optionalCategory);
        assertTrue(optionalCategory.isPresent());
        assertEquals(category.getTitle(), optionalCategory.get().getTitle());

    }

}