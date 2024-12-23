package com.gr25.thinkpro.controller.Admin;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class  AdminPageController {
    private final CustomerService customerService;

    @GetMapping("/admin/user")
    public String getAdminUserPage(Model model,@RequestParam("page") Optional<String> page) {
        int pageNum = 1;

        try {
            if(page.isPresent()) {
                pageNum = Integer.parseInt(page.get());
            }
        }
        catch (Exception e) {

        }

        long id=2;
        Pageable pageable = PageRequest.of(pageNum-1, 4);
        Page<Customer> customerPage =this.customerService.findCustomersById(id,pageable);
        List<Customer> users = customerPage.getContent();

        model.addAttribute("users",users);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("newUser",new Customer());
        return "admin/user/show";
    }


    @PostMapping("/admin/user")
    public String searchCustomerPage(Model model, @ModelAttribute("newUser") Customer customer,
                                     @RequestParam("page") Optional<String> page) {

        String name = customer.getName();
        Page<Customer> customerPage;

        int pageNum = 1;

        try {
            if(page.isPresent()) {
                pageNum = Integer.parseInt(page.get());
            }
        }
        catch (Exception e) {

        }
        Pageable pageable = PageRequest.of(pageNum - 1, 4);

        if (StringUtils.hasText(name)) {
            customerPage = customerService.findCustomersByNameAndRoleId(name, 2L, pageable);
        } else {
            customerPage = customerService.findCustomersById(2L, pageable);
        }

        List<Customer> customers = customerPage.getContent();

        model.addAttribute("users", customers);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        return "admin/user/show";
    }
//    @PostMapping("/admin/user")
//    public String searchCustomerPage(Model model,@ModelAttribute("newUser") Customer customer
//            ,@RequestParam("page") Optional<String> page) {
//
//        List<Customer> cus=this.customerService.getCustomers();
//        String name = customer.getName();
//        List<Customer> search = List.of(customer);
//        Customer customer1 = this.customerService.getCustomerByName(customer.getName());
//
//
//        Page<Customer> customerPage;
//        int pageNum = 1;
//        long id=2;
//        try {
//            if(page.isPresent()) {
//                pageNum = Integer.parseInt(page.get());
//            }
//        }catch (Exception e) {}
//        Pageable pageable = PageRequest.of(pageNum-1, 4);
//
//        for(Customer c:cus) {
//            if(c.getName().equals(name) && c.getRole().getRoleId()==2) {
//                search.add(c);
//            }
//        }
//        if (customer1 != null && customer1.getRole().getRoleId()==2) {
//            customerPage = (Page<Customer>) search;
//        } else {
//            customerPage = this.customerService.findCustomersById(id,pageable);;
//
//        }
//
//        List<Customer> customers = customerPage.getContent();
//
//        model.addAttribute("users", customers);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", customerPage.getTotalPages());
//        return "admin/user/show";
//    }

    @GetMapping("/admin/user/{Id}")
    public String getAdminUserViewPage(Model model, @PathVariable("Id") Long id) {
        Customer customer = this.customerService.getCustomerById(id);
        model.addAttribute("userview",customer);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/delete/{Id}")
    public String getdeleteAdminUserPage(Model model,@PathVariable("Id") Long id) {
        Customer customer = this.customerService.getCustomerById(id);
        model.addAttribute("cus",customer);
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
