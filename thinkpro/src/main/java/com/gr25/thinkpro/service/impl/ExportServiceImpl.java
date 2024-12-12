package com.gr25.thinkpro.service.impl;

import com.gr25.thinkpro.domain.dto.request.RevenueDTO;
import com.gr25.thinkpro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private DayReportService dayReportService;

    @Autowired
    private MonthReportService monthReportService;

    @Autowired
    private ExportPDFService exportPDFService;

    @Autowired
    private DashBoardService dashBoardService;

    @Override
    public byte[] exportAllTimeReport() throws Exception {
        Long totalRevenue = dashBoardService.getTotalRevenue();
        int totalProduct = dashBoardService.getTotalProduct();
        int totalCustomer = dashBoardService.getTotalCustomer();
        int totalBill = dashBoardService.getTotalBill();

        return exportPDFService.exportToPdf(
                null,
                0,
                0,
                totalRevenue,
                totalBill,
                totalCustomer,
                totalProduct, dashBoardService.getPieChartData(),
                dashBoardService.getProductsWithSales("desc"),
                dashBoardService.getDailyRevenue(LocalDate.now().withDayOfMonth(1).minusMonths(1), LocalDate.now()),
                dashBoardService.getMonthlyRevenue(String.valueOf(LocalDate.now().getYear())));
    }

    @Override
    public byte[] exportMonthReport(int month, int year) throws Exception {
        Long totalRevenue = monthReportService.getTotalRevenueByMonth(month, year);
        int totalProduct = monthReportService.getTotalProductByMonth(month, year);
        int totalCustomer = monthReportService.getTotalCustomerByMonth(month, year);
        int totalBill = monthReportService.getTotalBillByMonth(month, year);
        String endDate = "";
        if(month == LocalDate.now().getMonth().getValue() && year == LocalDate.now().getYear()){
            endDate = LocalDate.now().toString();
        }else{
            endDate = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1).toString();
        }
        return exportPDFService.exportToPdf(
                null,
                month,
                year,
                totalRevenue,
                totalBill,
                totalCustomer,
                totalProduct,
                monthReportService.getPieChartDataMonth(month, year),
                monthReportService.getProductsWithSalesByMonth(month, year, "desc"),
                dashBoardService.getDailyRevenue(LocalDate.of(year,month,1), LocalDate.parse(endDate)),
                null);
    }

    @Override
    public byte[] exportDayReport(String date) throws Exception {
        Long totalRevenue = dayReportService.getTotalRevenueByDate(date);
        int totalProduct = dayReportService.getTotalProductByDate(date);
        int totalCustomer = dayReportService.getTotalCustomerByDate(date);
        int totalBill = dayReportService.getTotalBillByDate(date);

        return exportPDFService.exportToPdf(
                date,
                0,
                0,
                totalRevenue,
                totalBill,
                totalCustomer,
                totalProduct,
                dayReportService.getPieChartDataDate(date),
                dayReportService.getProductsWithSalesByDates(date, "desc"),
                null,
                null);
    }
}
