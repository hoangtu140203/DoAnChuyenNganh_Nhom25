package com.gr25.thinkpro.controller.Admin;
import com.gr25.thinkpro.domain.entity.Bill;
import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.FeedBack;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.FeedBackService;
import com.gr25.thinkpro.service.OrderService;
import com.gr25.thinkpro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderPageController {
    private final OrderService orderService;
    @GetMapping("/admin/order")
    public String getDashboard(Model model,
                               @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 1);
        Page<Bill> ordersPage = this.orderService.findAll(pageable);
        List<Bill> orders = ordersPage.getContent();

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        return "admin/order/show";
    }
    @GetMapping("/admin/order/{billId}")
    public String detailOrderPage(@PathVariable("billId") long Id, Model model) {
        Bill order = this.orderService.getBillById(Id);
        model.addAttribute("order", order);
        return "admin/order/details";
    }
    @GetMapping("/admin/order/delete/{id}")
    public String getdeleteOrderPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("newBill", new Bill());
        return "admin/order/delete";
    }

    @Transactional
    @PostMapping("/admin/order/delete")
    public String postdeleteOrderPage(Model model,@ModelAttribute("newBill") Bill order) {
        this.orderService.deleteBill(order.getBillId());
        return "redirect:/admin/order";
    }
    @GetMapping("/admin/order/update/{billId}")
    public String getUpdateOrderPage(Model model, @PathVariable long billId) {
        Optional<Bill> currentOrder = this.orderService.fetchBillById(billId);
        model.addAttribute("newBill", currentOrder.get());
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newBill") Bill order) {
        this.orderService.updateBill(order);
        return "redirect:/admin/order";

    }
}