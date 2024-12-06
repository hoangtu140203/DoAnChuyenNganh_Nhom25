package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.CategoryMarketShareDTO;
import com.gr25.thinkpro.domain.dto.request.RevenueDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;

import java.time.LocalDate;
import java.util.List;

public interface DashBoardService {
    int getTotalProduct();
    int getTotalBill();
    int getTotalCustomer();
    Long getTotalRevenue();

    List<RevenueDTO> getDailyRevenue(LocalDate startDate, LocalDate endDate);
    List<RevenueDTO> getMonthlyRevenue(String year);
    List<CategoryMarketShareDTO> getPieChartData();
    List<ProductDTO> getProductsWithSales(String orderBy);

}
