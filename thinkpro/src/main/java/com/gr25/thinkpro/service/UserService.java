package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CustomerRepository customerRepository;
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
