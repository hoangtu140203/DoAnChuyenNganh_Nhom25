package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.Customer;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CartService {
    void handleAddProductToCart(String email, long productId, HttpSession session, long quantity);

    Cart fetchByUser(Customer currentUser);

    void handleRemoveCartDetail(long cartDetailId, HttpSession session);
}
