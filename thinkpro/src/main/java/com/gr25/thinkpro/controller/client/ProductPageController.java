package com.gr25.thinkpro.controller.client;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.CustomerService;
import com.gr25.thinkpro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/{Id}")
    public String detailProductPage(@PathVariable("Id") long Id, Model model) {
        Product product = this.productService.getProductById(Id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/create")
    public String getcreateProductPage(Model model) {
        Product product = new Product();
        product.setCategory(new Category());
        model.addAttribute("newProduct", product);
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(Model model,@ModelAttribute("newProduct") Product product,RedirectAttributes redirectAttributes) {

        String categoryName = product.getCategory().getName();
        List<Category> categories = this.categoryService.getCategories();
        if(categoryName==null) {
            redirectAttributes.addFlashAttribute("errorCate", "Danh mục không được để trống");
        }


        this.productService.rqProduct(product,redirectAttributes);
        if (    redirectAttributes.containsAttribute("errorName") ||
                redirectAttributes.containsAttribute("errorQuantity") ||
                redirectAttributes.containsAttribute("errorPrice") ||
                redirectAttributes.containsAttribute("errorCate")||
                redirectAttributes.containsAttribute("errorDiscount")) {
            return "redirect:/admin/product/create";
        }

        for(Category c : categories){
            if(c.getName().equals(categoryName)){
                product.setCreatedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
                product.setLastModifiedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
                product.setCategory(c);
                this.productService.saveProduct(product);
                return "redirect:/admin/product";
            }
        }
        redirectAttributes.addFlashAttribute("errorCate", "Danh mục không tồn tại");
        return "redirect:/admin/product/create";
    }
}
