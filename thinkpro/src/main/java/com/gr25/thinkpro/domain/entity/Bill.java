package com.gr25.thinkpro.domain.entity;

import com.gr25.thinkpro.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class Bill extends DateAuditing implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private long billId;

    private String status;

    private String paymentMethod;

    private long feeShip;

    private long total;

    @OneToMany(mappedBy = "bill")
    List<BillDetail> billDetails;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
