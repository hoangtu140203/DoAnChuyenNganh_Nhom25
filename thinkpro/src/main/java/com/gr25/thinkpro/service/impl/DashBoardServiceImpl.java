package com.gr25.thinkpro.service.impl;

import com.gr25.thinkpro.domain.dto.request.CategoryMarketShareDTO;
import com.gr25.thinkpro.domain.dto.request.RevenueDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;
import com.gr25.thinkpro.repository.BillRepository;
import com.gr25.thinkpro.repository.CategoryRepository;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.ProductRepository;
import com.gr25.thinkpro.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private  BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public int getTotalProduct() {
        return productRepository.countProducts();
    }

    @Override
    public int getTotalBill() {
        return billRepository.countBills();
    }

    @Override
    public int getTotalCustomer() {
        return customerRepository.countCustomers();
    }

    @Override
    public Long getTotalRevenue() {
        return billRepository.getTotalRevenue();
    }

    @Override
    public List<RevenueDTO> getDailyRevenue(LocalDate startDate, LocalDate endDate) {
        List<Object[]> rawResults = billRepository.getDailyRevenue(startDate.toString(), endDate.toString());

        return rawResults.stream()
                .map(record -> new RevenueDTO(
                        record[0].toString(),
                        ((Number) record[1]).longValue() // Ensure correct casting to Long
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RevenueDTO> getMonthlyRevenue(String year) {
        List<Object[]> results = billRepository.findMonthlyRevenue(year);
        return results.stream()
                .map(row -> new RevenueDTO((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }

    @Override
    public List<CategoryMarketShareDTO> getPieChartData() {
        List<Object[]> results = categoryRepository.getCategoryRevenueAndMarketShare();
        return results.stream()
                .map(row -> new CategoryMarketShareDTO(
                        (String) row[0],  // name
                        ((Number) row[1]).longValue(),  // category_revenue
                        ((Number) row[2]).doubleValue()   // market_share
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsWithSales(String orderBy) {
        List<Object[]> results = productRepository.findAllProductsWithSales(orderBy);

        return results.stream()
                .map(row -> new ProductDTO(
                        ((Number) row[0]).longValue(),  // product_id
                        (String) row[1],                  // name
                        (String) row[2],                  // category_name
                        ((Number) row[3]).longValue(),  // price
                        ((Number) row[4]).intValue()      // total_sales
                ))
                .collect(Collectors.toList());
    }
}
