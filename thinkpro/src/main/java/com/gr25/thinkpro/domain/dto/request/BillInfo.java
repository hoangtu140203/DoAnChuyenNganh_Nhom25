package com.gr25.thinkpro.domain.dto.request;

import com.gr25.thinkpro.domain.BillStatus;
import com.gr25.thinkpro.domain.entity.BillDetail;
import com.gr25.thinkpro.domain.entity.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class BillInfo {
    private long billId;

    private BillStatus status;

    private String paymentMethod;

    private long feeShip;

    private long total;

    List<BillDetail> billDetails;

    private Customer customer;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String createdDate;
}
