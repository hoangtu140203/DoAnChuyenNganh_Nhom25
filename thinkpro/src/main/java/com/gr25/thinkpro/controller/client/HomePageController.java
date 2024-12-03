package com.gr25.thinkpro.controller.client;


import com.gr25.thinkpro.domain.dto.request.ProductCriteriaDto;
import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.CustomerService;
import com.gr25.thinkpro.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    private final CustomerService customerService;

    private final ProductService productService;

    private final CategoryService categoryService;


    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/session-expired")
    public String getSessionExpiredPage(Model model) {
        return "client/error/session-expired";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterRequestDto());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(Model model, @ModelAttribute("registerUser") @Valid RegisterRequestDto registerRequestDto, BindingResult bindingResult) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getRePassword())) {
            bindingResult.rejectValue("rePassword", "error.registerRequestDto", "Mật khẩu không khớp");
        } else if (customerService.existByEmail(registerRequestDto.getEmail())) {
            bindingResult.rejectValue("email", "error.registerRequestDto", "Email đã được sử dụng");
        } else if (customerService.existByEmail(registerRequestDto.getEmail())) {
            bindingResult.rejectValue("phone", "error.registerRequestDto", "Số điện thoại đã được sử dụng");
        }

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        customerService.registerCustomer(registerRequestDto);
        return "redirect:/login";
    }

    @GetMapping(value = {"/", "/home"})
    public String getHomePage(Model model, @ModelAttribute ProductCriteriaDto productCriteriaDto,
                              @RequestParam(name = "pageNum") Optional<Integer> pageNum,
                              @RequestParam(name = "sortBy") Optional<String> sortBy,
                              @RequestParam(name = "isAscending") Optional<Boolean> isAscending) {
        if(!pageNum.isPresent()) {
            pageNum=Optional.of(1);
        }
        PageRequest pageRequest = PageRequest.of(pageNum.get()-1 , 8,
                isAscending.orElse(true) ? Sort.by(sortBy.orElse("createdDate")).ascending() : Sort.by(sortBy.orElse("createdDate")).descending());
        Page<Product> page = productService.findProduct(productCriteriaDto, pageRequest);
        model.addAttribute("products", page.getContent());
        model.addAttribute("currentPage", page.getNumber()+1);
        model.addAttribute("totalPages", page.getTotalPages());
        List<Category> categories=categoryService.findAll();
        model.addAttribute("categories",categories);
        return "client/homepage/index";
    }


}
