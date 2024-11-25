package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
