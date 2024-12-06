package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.CategoryMarketShareDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;
import com.gr25.thinkpro.domain.dto.request.RevenueDTO;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface MonthReportService {
    int getTotalProductByMonth(int month, int year);
    int getTotalBillByMonth(int month, int year);
    int getTotalCustomerByMonth(int month, int year);
    Long getTotalRevenueByMonth(int month, int year);


    List<CategoryMarketShareDTO> getPieChartDataMonth(int month, int year);
    List<ProductDTO> getProductsWithSalesByMonth(int month,int year,String orderBy);
}
