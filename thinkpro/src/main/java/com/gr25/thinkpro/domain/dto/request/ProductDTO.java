package com.gr25.thinkpro.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {
    private Long productId;
    private String name;
    private String categoryName;
    private Long price;
    private Integer totalSales;

    // Constructor, Getters, and Setters
}