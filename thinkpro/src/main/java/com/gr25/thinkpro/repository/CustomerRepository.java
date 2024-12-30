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

    @Query(value = "SELECT c.* FROM customers c WHERE c.role_id = ?1 and c.is_deleted = false",countQuery = "SELECT COUNT(*) FROM customers c WHERE c.role_id = ?1 and c.is_deleted = false",nativeQuery = true)
    Page<Customer> findCustomerByRoleRoleId(Long roleId, Pageable pageable);


    Page<Customer> findCustomersByNameAndRoleRoleId(@Param("name") String name, @Param("roleId") Long roleId, Pageable pageable);

    Customer save(Customer customer);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Transactional
    @Modifying
    @Query(value = "UPDATE customers c set c.is_deleted = true WHERE  c.customer_id = ?1",nativeQuery = true)
    void deleteCustomer(long id);

    @Query("SELECT COUNT(c) FROM Customer c WHERE MONTH(c.createdDate) = :month AND YEAR(c.createdDate) = :year")
    int countCustomersByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(c) FROM Customer c WHERE DATE(c.createdDate) = :date")
    int countCustomersByDate(LocalDate date);
}
