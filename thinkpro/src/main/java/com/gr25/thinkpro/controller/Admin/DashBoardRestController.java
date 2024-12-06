package com.gr25.thinkpro.controller.Admin;


import com.gr25.thinkpro.domain.dto.request.RevenueDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;
import com.gr25.thinkpro.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardRestController {
    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/daily")
    public List<RevenueDTO> getDailyRevenue(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return dashBoardService.getDailyRevenue(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }


    @GetMapping("top-products")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam String orderBy) {
        List<ProductDTO> products = dashBoardService.getProductsWithSales(orderBy);
        return ResponseEntity.ok(products);
    }
}
