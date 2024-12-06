package com.gr25.thinkpro.service.impl;


import com.gr25.thinkpro.domain.dto.request.CategoryMarketShareDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;
import com.gr25.thinkpro.repository.BillRepository;
import com.gr25.thinkpro.repository.CategoryRepository;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.ProductRepository;
import com.gr25.thinkpro.service.DayReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DayReportServiceImpl implements DayReportService {

    @Autowired
    private BillRepository billRepository;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public int getTotalProductByDate(String date) {
        return productRepository.countProductsByDate(LocalDate.parse(date));
    }

    @Override
    public int getTotalBillByDate(String date) {
        return billRepository.countBillsByDate(LocalDate.parse(date));
    }

    @Override
    public int getTotalCustomerByDate(String date) {
        return customerRepository.countCustomersByDate(LocalDate.parse(date));
    }

    @Override
    public Long getTotalRevenueByDate(String date) {
        return billRepository.findTotalRevenueByCreatedDate(LocalDate.parse(date));
    }

    @Override
    public List<CategoryMarketShareDTO> getPieChartDataDate(String date) {
        List<Object[]> results = categoryRepository.findCategoryRevenueAndMarketShareDate(LocalDate.parse(date));
        return results.stream()
                .map(result -> new CategoryMarketShareDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue(),
                        ((Number) result[2]).doubleValue()))
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsWithSalesByDates(String date, String orderBy) {
        List<Object[]> results = productRepository.findProductsByDate(LocalDate.parse(date), orderBy.toUpperCase());

        // Map Object[] to ProductDTO
        return results.stream()
                .map(row -> new ProductDTO(
                        ((Number) row[0]).longValue(),  // productId
                        (String) row[1],               // productName
                        (String) row[2],               // categoryName
                        ((Number) row[3]).longValue(), // price
                        ((Number) row[4]).intValue()   // numberOfSales
                ))
                .collect(Collectors.toList());
    }
}
