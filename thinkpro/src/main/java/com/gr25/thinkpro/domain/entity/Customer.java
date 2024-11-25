package com.gr25.thinkpro.domain.entity;

import com.gr25.thinkpro.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
@Builder
public class Customer extends DateAuditing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private long customerId;

    private String email;

    private String password;

    private String name;

    private String address;

    private String phone;

    private Date dob;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    private List<Bill> bills;
}
