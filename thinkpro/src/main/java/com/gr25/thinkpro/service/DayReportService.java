package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.CategoryMarketShareDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;

import java.util.List;

public interface DayReportService {
    int getTotalProductByDate(String date);
    int getTotalBillByDate(String date);
    int getTotalCustomerByDate(String date);
    Long getTotalRevenueByDate(String date);


    List<CategoryMarketShareDTO> getPieChartDataDate(String date);
    List<ProductDTO> getProductsWithSalesByDates(String date, String orderBy);
}
