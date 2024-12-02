package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.customer.customerId = :customerId")
    void deleteByCustomerId(@Param("customerId") long customerId);
}
