package com.gr25.thinkpro.service;
import com.gr25.thinkpro.domain.dto.request.ProductCriteriaDto;
import com.gr25.thinkpro.domain.entity.Bill;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillRepository billRepository;

    public List<Bill> findAll() {
        return this.orderRepository.findAll();
    }

    public Page<Bill> findAll(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }
    public Page<Bill> findAllBillToConfirm(Pageable pageable) {
        return this.orderRepository.findAllBillByStatus("WAITING",pageable);
    }

    public void deleteBill(long id) {
        orderRepository.deleteBillByBillId(id);
    }

    public Bill getBillById(long id) {
        return orderRepository.findBillByBillId(id);
    }

    public Optional<Bill> fetchBillById(long id) {
        return this.orderRepository.findById(id);
    }

    public void updateBill(Bill order) {
        this.orderRepository.save(order);
    }

    public void updateBillStatus(String status, long billId){
        Bill bill = orderRepository.findBillByBillId(billId);
        if(bill==null){
            throw new EntityNotFoundException("bill is not found");
        }
        orderRepository.updateBillStatus(status,billId);
    }

}
