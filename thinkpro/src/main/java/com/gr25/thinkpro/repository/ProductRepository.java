package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gr25.thinkpro.domain.entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Modifying
    @Query("UPDATE Product p SET p.category = null WHERE p.category.categoryId = :categoryId")
    void updateCategoryToNull(@Param("categoryId") long categoryId);
   
    @Query("SELECT p FROM Product p WHERE p.productId=?1")
    Optional<Product> findByProductId(Long id);
    @Override
  
    List<Product> findAll();

    Product findProductByProductId(long productId);

    Product save(Product product);

    boolean existsByName(String name);

}
