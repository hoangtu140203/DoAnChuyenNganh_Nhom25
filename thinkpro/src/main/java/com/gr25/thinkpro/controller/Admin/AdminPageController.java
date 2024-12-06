package com.gr25.thinkpro.controller.Admin;

import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class  AdminPageController {
    private final CustomerService customerService;

    @GetMapping("/admin/user")
    public String getAdminUserPage(Model model) {
        List<Customer> users = this.customerService.getCustomers();
        model.addAttribute("users",users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{Id}")
    public String getAdminUserViewPage(Model model, @PathVariable("Id") Long id) {
        Customer customer = this.customerService.getCustomerById(id);
        model.addAttribute("userview",customer);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/delete/{Id}")
    public String getdeleteAdminUserPage(Model model,@PathVariable("Id") Long id) {
        model.addAttribute("id",id);
        model.addAttribute("newUser",new Customer());
        return "admin/user/delete";
    }


    @PostMapping("/admin/user/delete")
    @Transactional
    public String deleteAdminUserPage(Model model, @ModelAttribute("newUser") Customer customer) {

        this.customerService.deleteCustomerById(customer.getCustomerId());
        return "redirect:/admin/user";
    }


}
