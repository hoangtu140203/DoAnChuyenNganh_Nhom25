package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Modifying
    @Query("UPDATE Product p SET p.category = null WHERE p.category.categoryId = :categoryId")
    void updateCategoryToNull(@Param("categoryId") long categoryId);

    @Override
    List<Product> findAll();

    Product findProductByProductId(long productId);

    Product save(Product product);

    boolean existsByName(String name);
}
