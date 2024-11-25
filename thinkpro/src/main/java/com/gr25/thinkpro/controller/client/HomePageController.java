package com.gr25.thinkpro.controller.client;


import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.repository.CustomerRepository;
import com.gr25.thinkpro.repository.RoleRepository;
import com.gr25.thinkpro.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    private final CustomerService customerService;

    @GetMapping("/")
    public String getHomePage(Model model) {

        return "client/homepage/index";
    }

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
        }
        else if(customerService.existByEmail(registerRequestDto.getEmail())) {
            bindingResult.rejectValue("email", "error.registerRequestDto", "Email đã được sử dụng");
        }
        else if(customerService.existByEmail(registerRequestDto.getEmail())) {
            bindingResult.rejectValue("phone", "error.registerRequestDto", "Số điện thoại đã được sử dụng");
        }

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        customerService.registerCustomer(registerRequestDto);
        return "redirect:/login";
    }


}
