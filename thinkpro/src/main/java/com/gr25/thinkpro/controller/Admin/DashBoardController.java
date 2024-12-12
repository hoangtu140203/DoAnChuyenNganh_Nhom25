package com.gr25.thinkpro.controller.Admin;


import com.gr25.thinkpro.domain.dto.request.RevenueDTO;
import com.gr25.thinkpro.domain.dto.request.ProductDTO;
import com.gr25.thinkpro.service.DashBoardService;
import com.gr25.thinkpro.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;


    @Autowired
    private ExportService exportService;

    @GetMapping("/admin")
    public String getDashBoardPage(Model model) {
        model.addAttribute("totalProduct", dashBoardService.getTotalProduct());
        model.addAttribute("totalBill", dashBoardService.getTotalBill());
        model.addAttribute("totalCustomer", dashBoardService.getTotalCustomer());
        model.addAttribute("totalRevenue", dashBoardService.getTotalRevenue());
        //model.addAttribute("dailyRevenue", dashBoardService.getDailyRevenue(LocalDate.now().withDayOfMonth(1).minusMonths(1), LocalDate.now()));
        model.addAttribute("pieChartData", dashBoardService.getPieChartData());
        model.addAttribute("date", LocalDate.now().withDayOfMonth(1).minusMonths(1));
        System.out.println(model.getAttribute("dailyRevenue"));
        System.out.println(model.getAttribute("pieChartData"));
        return "admin/dashboard/index";
    }


    @GetMapping("/top-products")
    @ResponseBody
    public List<ProductDTO> getTopProducts(@RequestParam String orderBy) {
        if (!"ASC".equalsIgnoreCase(orderBy) && !"DESC".equalsIgnoreCase(orderBy)) {
            throw new IllegalArgumentException("Invalid orderBy value: " + orderBy);
        }
        return dashBoardService.getProductsWithSales(orderBy);
    }

    @GetMapping("/revenue")
    @ResponseBody
    public List<RevenueDTO> getRevenue(@RequestParam(required = false) String startDate, @RequestParam(required = false) String year) {
        if (startDate != null) {
            return dashBoardService.getDailyRevenue(LocalDate.parse(startDate), LocalDate.now());
        } else if (year != null) {
            return dashBoardService.getMonthlyRevenue(year);
        } else {
            throw new IllegalArgumentException("Either startDate or year must be provided");
        }
    }

    @GetMapping("/revenue2")
    @ResponseBody
    public List<RevenueDTO> getRevenue2(@RequestParam int month) {
        return dashBoardService.getDailyRevenue(LocalDate.now().withMonth(month).withDayOfMonth(1), LocalDate.now().withMonth(month).withDayOfMonth(1).plusMonths(1).minusDays(1));
    }

    @GetMapping("/admin/export")
    @ResponseBody
    public byte[] exportReport() throws Exception {
        return exportService.exportAllTimeReport();
    }

}
