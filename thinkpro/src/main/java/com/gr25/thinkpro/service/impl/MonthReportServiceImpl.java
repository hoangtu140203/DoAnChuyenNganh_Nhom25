package com.gr25.thinkpro.service.impl;

import com.gr25.thinkpro.domain.dto.request.CategoryMarketShareDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;
import com.gr25.thinkpro.repository.BillRepository;
import com.gr25.thinkpro.repository.CategoryRepository;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.ProductRepository;
import com.gr25.thinkpro.service.MonthReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthReportServiceImpl implements MonthReportService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public int getTotalProductByMonth(int month, int year) {
        return productRepository.countProductsByMonthAndYear(month, year);
    }

    @Override
    public int getTotalBillByMonth(int month, int year) {
        return billRepository.countBillsByMonthAndYear(month, year);
    }

    @Override
    public int getTotalCustomerByMonth(int month, int year) {
        return customerRepository.countCustomersByMonthAndYear(month, year);
    }

    @Override
    public Long getTotalRevenueByMonth(int month, int year) {
        return billRepository.findTotalRevenue(month, year);
    }

    @Override
    public List<CategoryMarketShareDTO> getPieChartDataMonth(int month, int year) {
        List<Object[]> results = categoryRepository.findCategoryMarketShares(month, year);
        return results.stream()
                .map(result -> new CategoryMarketShareDTO(
                        (String) result[0],
                        ((Number) result[1]).longValue(),
                        ((Number) result[2]).doubleValue()))
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsWithSalesByMonth(int month, int year, String orderBy) {
        List<Object[]> results = productRepository.getProductSalesByMonth(month, year, orderBy);
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
