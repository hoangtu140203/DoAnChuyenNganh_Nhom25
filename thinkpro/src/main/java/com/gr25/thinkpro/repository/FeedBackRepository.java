package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

    @Query("SELECT AVG(f.rate) FROM Product p JOIN p.feedBacks f WHERE p.productId=?1")
    Optional<Float> getAvgRateByProductId(long productId);

    @Query(value = "SELECT f.* FROM products p INNER JOIN feedback f " +
            "ON p.product_id = f.product_id WHERE p.product_id = ?1 " +
            "ORDER BY f.created_date DESC",
            countQuery = "SELECT COUNT(f.feedback_id) FROM products p INNER JOIN feedback f " +
                    "ON p.product_id = f.product_id WHERE p.product_id = ?1 " +
                    "GROUP BY p.product_id",
            nativeQuery = true)
    Page<FeedBack> findAllByProductId(long productId, Pageable pageable);
}
