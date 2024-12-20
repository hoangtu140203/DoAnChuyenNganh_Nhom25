package com.gr25.thinkpro.service.impl;

import com.gr25.thinkpro.domain.BillStatus;
import com.gr25.thinkpro.domain.dto.request.BillInfo;
import com.gr25.thinkpro.domain.dto.request.PaymentInfo;
import com.gr25.thinkpro.domain.dto.request.PaymentInfoResponse;
import com.gr25.thinkpro.domain.entity.*;
import com.gr25.thinkpro.repository.*;
import com.gr25.thinkpro.service.CheckoutService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                cartDetailRepository.save(currentCartDetail);
            }
        }
    }


    @Override
    public List<BillInfo> fetchOrderByUser(Customer currentUser) {
        List<Bill> bills = billRepository.findByCustomer(currentUser);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<BillInfo> billInfos = new ArrayList<>();
        bills.forEach(bill -> {
            BillInfo billInfo = new BillInfo();
            billInfo.setBillDetails(bill.getBillDetails());
            billInfo.setCreatedDate(bill.getCreatedDate().format(formatter));
            billInfo.setBillId(bill.getBillId());
            billInfo.setFeeShip(bill.getFeeShip());
            billInfo.setCustomer(bill.getCustomer());
            billInfo.setTotal(bill.getTotal());
            billInfo.setPaymentMethod(bill.getPaymentMethod());
            billInfo.setStatus(bill.getStatus());
            billInfo.setReceiverName(bill.getReceiverName());
            billInfo.setReceiverPhone(bill.getReceiverPhone());
            billInfo.setReceiverAddress(bill.getReceiverAddress());
            billInfos.add(billInfo);
        });
        Collections.reverse(billInfos);
        return billInfos;
    }

    @Override
    public void cancelBill(String email, long oderId, HttpSession session, int i) {
        Bill bill = billRepository.findById(oderId).orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setStatus(BillStatus.CANCELLED);
        billRepository.save(bill);
    }

    @Override
    public PaymentInfo getPaymentInfo() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAccountName("DO THANH VINH");
        paymentInfo.setBankId("MB");
        paymentInfo.setAccountNo("25112000003");
        paymentInfo.setDescription("KHACH HANG THINKPRO CHUYEN KHOAN");
        return paymentInfo;
    }


    public void handlePlaceOrder(
            Customer user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone, String paymentMethod) {

        // step 1: get cart by user
        Cart cart = cartRepository.findByCustomer(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                Bill order = new Bill();
                order.setCustomer(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setPaymentMethod(paymentMethod);
                order.setFeeShip(0L);
                order.setCreatedDate(LocalDateTime.now());
                order.setLastModifiedDate(LocalDateTime.now());
                order.setStatus(BillStatus.WAITING);

                long sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getProduct().getPrice();
                }
                order.setTotal(sum);
                order = billRepository.save(order);

                // create orderDetail

                for (CartDetail cd : cartDetails) {
                    BillDetail orderDetail = new BillDetail();
                    orderDetail.setBill(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getProduct().getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    orderDetail.setCreatedDate(LocalDateTime.now());
                    orderDetail.setLastModifiedDate(LocalDateTime.now());
                    billDetailRepository.save(orderDetail);
                }

                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    cartDetailRepository.deleteById(cd.getId());
                }

                cartRepository.deleteById(cart.getCartId());

                // step 3 : update session
                session.setAttribute("sum", 0);
            }
        }

    }
}
