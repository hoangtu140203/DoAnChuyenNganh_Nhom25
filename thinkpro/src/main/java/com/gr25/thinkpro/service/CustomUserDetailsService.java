package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username);
        if (customer == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new User(
                customer.getEmail(),
                customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority( customer.getRole().getRoleName())));
    }

}