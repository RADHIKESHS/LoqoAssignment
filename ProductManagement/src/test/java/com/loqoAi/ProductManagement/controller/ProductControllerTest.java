package com.loqoAi.ProductManagement.controller;

import com.loqoAi.ProductManagement.Service.ProductService;
import com.loqoAi.ProductManagement.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_Success() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.addProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product1", response.getBody().getName());
    }



    @Test
    void testBulkUpload() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        Map<String, List<Product>> result = Map.of("savedProducts", products, "existingProducts", new ArrayList<>());
        when(productService.saveAll(anyList())).thenReturn(result);

        ResponseEntity<Map<String, List<Product>>> response = productController.bulkUpload(products);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("savedProducts"));
        assertTrue(response.getBody().get("savedProducts").contains(product));
        assertTrue(response.getBody().get("existingProducts").isEmpty());
    }

    @Test
    void testGetProducts() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        when(productService.getProducts(anyString(), anyDouble(), anyDouble(), anyBoolean(), anyString(), anyString()))
                .thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getProducts("Category1", 50.0, 150.0, true, "price", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Product1", response.getBody().get(0).getName());
    }

    @Test
    void testGetProductsByCategory() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        when(productService.getProductsByCategory(anyString())).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getProductsByCategory("Category1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Product1", response.getBody().get(0).getName());
    }

    @Test
    void testGetProductsByPriceRange() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        when(productService.getProductsByPriceRange(anyDouble(), anyDouble())).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getProductsByPriceRange(50.0, 150.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Product1", response.getBody().get(0).getName());
    }
}
