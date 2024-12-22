package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Bill;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.domain.entity.Product;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {

    List<Bill> findAll();

    void deleteBillByBillId(long billId);

    Bill findBillByBillId(long billId);
}