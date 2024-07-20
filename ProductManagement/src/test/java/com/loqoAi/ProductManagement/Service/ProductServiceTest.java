package com.loqoAi.ProductManagement.Service;

import com.loqoAi.ProductManagement.Exceptions.BadRequestException;
import com.loqoAi.ProductManagement.model.Product;
import com.loqoAi.ProductManagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_Success() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        when(productRepository.findByNameAndCategory(anyString(), anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.addProduct(product);

        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepository, times(1)).findByNameAndCategory(anyString(), anyString());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddProduct_ProductAlreadyExists() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        when(productRepository.findByNameAndCategory(anyString(), anyString())).thenReturn(Optional.of(product));

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            productService.addProduct(product);
        });

        assertEquals("Product with name Product1 in category Category1 already exists.", thrown.getMessage());
        verify(productRepository, times(1)).findByNameAndCategory(anyString(), anyString());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testGetProducts() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        when(productRepository.findByFilters(anyString(), anyDouble(), anyDouble(), anyBoolean(), any(Sort.class)))
                .thenReturn(products);

        List<Product> result = productService.getProducts("Category1", 50.0, 150.0, true, "price", "asc");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getName());
    }

    @Test
    void testGetProductsByCategory() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        when(productRepository.findByCategory(anyString())).thenReturn(products);

        List<Product> result = productService.getProductsByCategory("Category1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product1", result.get(0).getName());
    }

    @Test
    void testSaveAll() {
        Product product = new Product(1L, "Product1", "Category1", 100.0, true, 4.5, new Date());
        List<Product> products = Collections.singletonList(product);
        when(productRepository.findByNameAndCategory(anyString(), anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Map<String, List<Product>> result = productService.saveAll(products);

        assertNotNull(result);
        assertTrue(result.get("savedProducts").contains(product));
        assertTrue(result.get("existingProducts").isEmpty());
    }
}
