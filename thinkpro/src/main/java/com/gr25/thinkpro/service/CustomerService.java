package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.repository.CartRepository;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public boolean existByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public boolean existByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    public void registerCustomer(RegisterRequestDto requestDto) {
        Customer customer = new Customer();
        customer.setEmail(requestDto.getEmail());
        customer.setPhone(requestDto.getPhone());
        customer.setRole(roleRepository.findByRoleName("ROLE_USER"));
        customer.setCreatedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        customer.setLastModifiedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        customer.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        customerRepository.save(customer);

        Cart cart = new Cart();
        cart.setSum(0);
        cart.setCustomer(customer);

        cartRepository.save(cart);

        customer.setCart(cart);

        customerRepository.save(customer);
    }
}
