package com.gr25.thinkpro.domain.dto.request;

import lombok.Data;

@Data
public class CartRequestDto {
    private long quantity;
    private long productId;
}
