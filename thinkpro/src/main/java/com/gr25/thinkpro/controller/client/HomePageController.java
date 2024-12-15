package com.gr25.thinkpro.controller.client;


import com.gr25.thinkpro.domain.dto.request.ProductCriteriaDto;
import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.RoleRepository;
import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.CustomerService;
import com.gr25.thinkpro.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    @Autowired
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

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


    @GetMapping("/forget")
    public String getForgetPassword(Model model) {
        model.addAttribute("registerUser", new RegisterRequestDto());
        return "client/auth/forget";
    }

    @Autowired
    private JavaMailSender mailSender;

    private void sendEmailWithVerificationCode(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã xác thực của bạn");
        message.setText("Mã xác thực của bạn là: " + verificationCode);
        mailSender.send(message);
    }

    @PostMapping("/forget")
    public String handleForgetPassword(Model model, @ModelAttribute("registerUser") @Valid RegisterRequestDto registerRequestDto, BindingResult bindingResult
    , RedirectAttributes redirectAttributes, HttpServletRequest request ) {

        if(!customerService.existByEmail(registerRequestDto.getEmail())) {
            bindingResult.rejectValue("email", "error.registerRequestDto", "Email không tồn tại");
        }
        if(bindingResult.hasErrors()) {
            return "client/auth/forget";
        }

        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String verificationCode = String.valueOf(code);

        sendEmailWithVerificationCode(registerRequestDto.getEmail(), verificationCode);

        request.getSession().setAttribute("verificationCode", verificationCode);
        request.getSession().setAttribute("email", registerRequestDto.getEmail());
        redirectAttributes.addFlashAttribute("registerUser", new RegisterRequestDto());
        return "redirect:/forget/confirmed";
    }

    @GetMapping("/forget/confirmed")
    public String getForgetPasswordConfirm(Model model) {
        model.addAttribute("registerUser", new RegisterRequestDto());

        return "client/auth/confirmed";
    }

    @PostMapping("/forget/confirmed")
    public String handleConfirmCode(Model model, @ModelAttribute("registerUser") @Valid RegisterRequestDto registerRequestDto,
                                    RedirectAttributes redirectAttributes,HttpServletRequest request ) {
        String code = (String) request.getSession().getAttribute("verificationCode");
        if (registerRequestDto.getConfirmCode() != null && registerRequestDto.getConfirmCode().equals(code)) {
            return "redirect:/forget/confirmed/updatepass";
        }

        model.addAttribute("error", "Mã xác nhận không chính xác!");
        return "client/auth/confirmed";

    }

    @GetMapping ("/forget/confirmed/updatepass")
    public String getForgetPasswordUpdate(Model model) {
        model.addAttribute("registerUser", new RegisterRequestDto());
        return "client/auth/updatepass";
    }
    @PostMapping("/forget/confirmed/updatepass")
    public String ForgetPasswordUpdate(Model model, @ModelAttribute("registerUser") @Valid RegisterRequestDto registerRequestDto,
                                       BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes ) {

        if(!Objects.equals(registerRequestDto.getPassword(), registerRequestDto.getRePassword())){
            bindingResult.rejectValue("rePassword", "error.registerRequestDto","Mật khẩu xác thực không khớp");
        }
        if(bindingResult.hasErrors()) {
            return "client/auth/updatepass";
        }
        String email = (String) request.getSession().getAttribute("email");
        Customer customer = this.customerService.getCustomerByEmail(email);
        customer.setPassword(this.passwordEncoder.encode(registerRequestDto.getPassword()));
        this.customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("success","thanh cong");
        return "redirect:/login";
    }
}
