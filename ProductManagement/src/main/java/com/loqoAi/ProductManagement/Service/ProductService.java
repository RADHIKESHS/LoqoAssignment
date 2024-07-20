package com.loqoAi.ProductManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.loqoAi.ProductManagement.Exceptions.BadRequestException;
import com.loqoAi.ProductManagement.model.Product;
import com.loqoAi.ProductManagement.repository.ProductRepository;

import java.util.*;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Adds a new product to the database.
     * 
     * @param product The product to be added.
     * @return The saved product.
     * @throws BadRequestException If the product already exists or validation fails.
     */
    public Product addProduct(Product product) {
        validateProduct(product);

        Optional<Product> existingProduct = productRepository.findByNameAndCategory(product.getName(), product.getCategory());
        if (existingProduct.isPresent()) {
            throw new BadRequestException("Product with name " + product.getName() + " in category " + product.getCategory() + " already exists.");
        }

        return productRepository.save(product);
    }

    /**
     * Retrieves a list of products based on various filters.
     * 
     * @param category The category to filter by.
     * @param minPrice The minimum price to filter by.
     * @param maxPrice The maximum price to filter by.
     * @param inStock Whether to filter by stock availability.
     * @param sortField The field to sort by.
     * @param sortOrder The order to sort by (asc/desc).
     * @return A list of products matching the filters.
     */
    public List<Product> getProducts(String category, Double minPrice, Double maxPrice, Boolean inStock, String sortField, String sortOrder) {
        Sort.Direction direction = getSortDirection(sortOrder);

        if (sortField == null || sortField.isEmpty()) {
            sortField = "createdAt";
        }

        Sort sort = Sort.by(direction, sortField);

        return productRepository.findByFilters(category, minPrice, maxPrice, inStock, sort);
    }

    /**
     * Determines the sorting direction.
     * 
     * @param sortOrder The sort order (asc/desc).
     * @return The Sort.Direction based on the sortOrder.
     * @throws BadRequestException If the sortOrder is invalid.
     */
    private Sort.Direction getSortDirection(String sortOrder) {
        if (sortOrder == null || sortOrder.isEmpty() || sortOrder.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        } else {
            throw new BadRequestException("Invalid sort order: " + sortOrder);
        }
    }

    /**
     * Retrieves products based on category.
     * 
     * @param category The category to filter by.
     * @return A list of products in the specified category.
     * @throws BadRequestException If the category is null or empty.
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw new BadRequestException("Category cannot be null or empty.");
        }

        return productRepository.findByCategory(category);
    }

    /**
     * Retrieves products within a specified price range.
     * 
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of products within the specified price range.
     * @throws BadRequestException If the price range is invalid.
     */
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new BadRequestException("Invalid price range.");
        }

        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    /**
     * Retrieves products based on stock availability.
     * 
     * @param inStock Whether to filter by stock availability.
     * @return A list of products based on stock availability.
     */
    public List<Product> getProductsByInStock(boolean inStock) {
        return productRepository.findByInStock(inStock);
    }

    /**
     * Bulk uploads multiple products and handles existing products.
     * 
     * @param products A list of products to be uploaded.
     * @return A map containing lists of saved and existing products.
     */
    public Map<String, List<Product>> saveAll(List<Product> products) {
        List<Product> savedProducts = new ArrayList<>();
        List<Product> existingProducts = new ArrayList<>();

        for (Product product : products) {
            try {
                Product savedProduct = addProduct(product);
                savedProducts.add(savedProduct);
            } catch (BadRequestException e) {
                existingProducts.add(product);
            }
        }

        return Map.of("savedProducts", savedProducts, "existingProducts", existingProducts);
    }

    /**
     * Validates product details before saving.
     * 
     * @param product The product to be validated.
     * @throws BadRequestException If the product name or category is null or empty.
     */
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new BadRequestException("Product name cannot be null or empty.");
        }
        if (product.getCategory() == null || product.getCategory().isEmpty()) {
            throw new BadRequestException("Product category cannot be null or empty.");
        }
    }
}
