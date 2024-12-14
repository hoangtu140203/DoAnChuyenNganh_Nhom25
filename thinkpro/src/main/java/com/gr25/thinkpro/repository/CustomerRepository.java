package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
    List<Customer> findAll();

    Customer findCustomerByCustomerId(long customerId);
    Customer findCustomerByName(String name);
    @Transactional
    @Modifying
    void deleteCustomerByCustomerId(long customerId);

    Page<Customer> findByNameContaining(String customerId, Pageable pageable);
    Customer save(Customer customer);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
