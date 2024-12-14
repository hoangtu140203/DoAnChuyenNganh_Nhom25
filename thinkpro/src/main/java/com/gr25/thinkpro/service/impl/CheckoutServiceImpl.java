package com.gr25.thinkpro.service.impl;

import com.gr25.thinkpro.domain.entity.*;
import com.gr25.thinkpro.repository.*;
import com.gr25.thinkpro.service.CheckoutService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final BillDetalRepository billDetalRepository;

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
    public List<Bill> fetchOrderByUser(Customer currentUser) {
        return billRepository.findByCustomer(currentUser);
    }


    public void handlePlaceOrder(
            Customer user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

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
                order.setPaymentMethod("CASH_ON_DELIVERY");
                order.setFeeShip(0L);
                order.setCreatedDate(LocalDateTime.now());
                order.setLastModifiedDate(LocalDateTime.now());
                order.setStatus("PENDING");

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
                    billDetalRepository.save(orderDetail);
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
