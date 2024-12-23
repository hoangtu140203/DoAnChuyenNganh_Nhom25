package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT COUNT(c) FROM Customer c")
    int countCustomers();
    Customer findByEmail(String email);
    List<Customer> findAll();

    Customer findCustomerByCustomerId(long customerId);

    Customer findCustomerByName(String name);
    @Transactional
    @Modifying
    void deleteCustomerByCustomerId(long customerId);

    Page<Customer> findByNameContaining(String name, Pageable pageable);
    Page<Customer> findCustomerByRoleRoleId(Long customerId, Pageable pageable);
    Page<Customer> findCustomersByNameAndRoleRoleId(String name, Long customerId, Pageable pageable);

    Customer save(Customer customer);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);


    @Query("SELECT COUNT(c) FROM Customer c WHERE MONTH(c.createdDate) = :month AND YEAR(c.createdDate) = :year")
    int countCustomersByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(c) FROM Customer c WHERE DATE(c.createdDate) = :date")
    int countCustomersByDate(LocalDate date);
}
