package com.loqoAi.ProductManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.loqoAi.ProductManagement.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    List<Product> findByInStock(boolean inStock);

    @Query("SELECT p FROM Product p WHERE (:category is null or p.category = :category) " +
            "AND (:minPrice is null or p.price >= :minPrice) " +
            "AND (:maxPrice is null or p.price <= :maxPrice) " +
            "AND (:inStock is null or p.inStock = :inStock)")
    List<Product> findByFilters(@Param("category") String category,
                                @Param("minPrice") Double minPrice,
                                @Param("maxPrice") Double maxPrice,
                                @Param("inStock") Boolean inStock,
                                Sort sort);

	Optional<Product> findByNameAndCategory(String name, String category);
}

