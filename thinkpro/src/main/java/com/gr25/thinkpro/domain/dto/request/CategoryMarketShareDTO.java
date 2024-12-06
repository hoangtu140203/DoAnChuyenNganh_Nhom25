package com.gr25.thinkpro.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryMarketShareDTO {
    private String name;
    private Long categoryRevenue;
    private double marketShare;


    @Override
    public String toString() {
        return "CategoryMarketShareDTO{" +
                "name='" + name + '\'' +
                ", categoryRevenue=" + categoryRevenue +
                ", marketShare=" + marketShare +
                '}';
    }
}
