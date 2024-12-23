package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.domain.entity.Role;
import com.gr25.thinkpro.repository.CartRepository;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> findCustomersByName(String name,Pageable page) {
        return this.customerRepository.findByNameContaining(name,page);
    }
    public Page<Customer> findCustomersById(Long id,Pageable page) {
        return this.customerRepository.findCustomerByRoleRoleId(id,page);
    }
    public Customer getCustomerByName(String name) {
        return this.customerRepository.findCustomerByName(name);
    }

    public Page<Customer> findCustomersByNameAndRoleId(String name, Long roleId, Pageable pageable) {
        return this.customerRepository.findCustomersByNameAndRoleRoleId(name,roleId,pageable);
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findCustomerByCustomerId(id);
    }

    public void deleteCustomerById(long id) {
        this.cartRepository.deleteByCustomerId(id);
        this.customerRepository.deleteCustomerByCustomerId(id);
    }

    public Customer getCurrentCustomer() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return this.customerRepository.findByEmail(email);
        }
        else {
            return null;
        }
    }
    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void sendEmailWithVerificationCode(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã xác thực của bạn");
        message.setText("Mã xác thực của bạn là: " + verificationCode);
        mailSender.send(message);
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
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
        customer.setName(requestDto.getName());
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
