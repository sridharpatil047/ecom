package me.sridharpatil.ecom.productservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.sridharpatil.ecom.productservice.configs.TestSecurityConfig;
import me.sridharpatil.ecom.productservice.controllers.dtos.ControllerProductReqDto;
import me.sridharpatil.ecom.productservice.models.Category;
import me.sridharpatil.ecom.productservice.models.Product;
import me.sridharpatil.ecom.productservice.services.ProductService;
import me.sridharpatil.ecom.productservice.services.dtos.ProductRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = {"test"})
@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @MockBean private ProductService productService;

    @Autowired private MockMvc mockMvc;

//    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private Category category;
    private ControllerProductReqDto controllerProductReqDto;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        category = new Category();
        category.setId(1L);
        category.setTitle("Category Title");

        product = new Product();
        product.setTitle("Product Title");
        product.setDescription("Product Description");
        product.setPrice(123.0);
        product.setCategory(category);

        controllerProductReqDto = ControllerProductReqDto.builder()
                .title("Product Title")
                .description("Product Description")
                .price(123.0)
                .categoryId(1L)
                .build();

        objectMapper = new ObjectMapper();

//        @NotNull @NotBlank
//        private String title;
//        private String description;
//        private Double price;
//
//        @NotNull
//        private Long categoryId;
    }

    @Test
    public void whenValidRequestThenReturnProduct() throws Exception {

        // Mock
        when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(product);

        // Act
        String requestBody = objectMapper.writeValueAsString(controllerProductReqDto);
        mockMvc.perform(post("/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("test", "test"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Product Title"));

    }
}