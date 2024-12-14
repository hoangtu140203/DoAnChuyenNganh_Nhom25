package com.gr25.thinkpro.service.impl;

import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.CartDetail;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.repository.CartDetailRepository;
import com.gr25.thinkpro.repository.CartRepository;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.ProductRepository;
import com.gr25.thinkpro.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {

        Customer user = customerRepository.findByEmail(email);
        if (user != null) {
            // check user đã có Cart chưa ? nếu chưa -> tạo mới
            Cart cart = cartRepository.findByCustomer(user);

            if (cart == null) {
                // tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setCustomer(user);
                otherCart.setSum(0);

                cart = cartRepository.save(otherCart);
            }

            // save cart_detail
            // tìm product by id

            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail oldDetail = cartDetailRepository.findByCartAndProduct(cart, realProduct);
                //
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);

                    if(quantity >= 0 && quantity <= realProduct.getQuantity()) {
                        cd.setQuantity(quantity);
                    }

                    cd.setQuantity(quantity);

                    cd.setCreatedDate(LocalDateTime.now());
                    cd.setLastModifiedDate(LocalDateTime.now());
                    cartDetailRepository.save(cd);

                    // update cart (sum);
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {

                    if(oldDetail.getQuantity() + quantity <= realProduct.getQuantity()) {
                        oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    }
                    else {

                    }

                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);

                    cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    public Cart fetchByUser(Customer user) {
        return cartRepository.findByCustomer(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                cartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                cartRepository.deleteById(currentCart.getCartId());
                session.setAttribute("sum", 0);
            }
        }
    }
}
