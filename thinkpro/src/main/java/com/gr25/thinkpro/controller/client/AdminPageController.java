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
public class AdminPageController {


    @GetMapping("/admin/user")
    public String getAdminPage(Model model) {
        return "admin/user/show";
    }
}
