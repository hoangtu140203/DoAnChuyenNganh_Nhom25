package com.gr25.thinkpro.controller.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {
    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }
    @GetMapping("/")
    public String getHomePage(Model model) {
        return "client/homepage/show";
    }
}
