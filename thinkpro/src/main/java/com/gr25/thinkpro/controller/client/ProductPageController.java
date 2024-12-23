package com.gr25.thinkpro.controller.client;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.domain.entity.Image;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.CustomerService;
import com.gr25.thinkpro.service.ImageService;
import com.gr25.thinkpro.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductPageController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ImageService imageService;

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

        List<Image> images = new ArrayList<>();

        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("newProduct", product);
        model.addAttribute("images", images);
        return "admin/product/create";
    }


    @PostMapping("/admin/product/create")
    public String createProduct(Model model,
                                @ModelAttribute("newProduct") Product product,
                                @RequestParam("imageFiles") List<MultipartFile> imageFiles,
                                RedirectAttributes redirectAttributes) {
        log.info("Received product: {}", product);
        log.info("Received images: {}", imageFiles);
        Category categoryName = categoryService.getCategoryByName(product.getCategory().getName());

        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("categories", categories);

        this.productService.rqProduct(product,redirectAttributes);
        if (    redirectAttributes.containsAttribute("errorName") ||
                redirectAttributes.containsAttribute("errorQuantity") ||
                redirectAttributes.containsAttribute("errorPrice") ||
                redirectAttributes.containsAttribute("errorCate")||
                redirectAttributes.containsAttribute("errorDiscount")) {
            return "redirect:/admin/product/create";
        }
        product.setCreatedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        product.setLastModifiedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        product.setCategory(categoryName);
        productService.saveProduct(product);



        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                Image image = new Image();
                image.setName(file.getOriginalFilename());
                // Lưu ảnh vào cơ sở dữ liệu hoặc thư mục
                imageService.saveImage(image);
            }
        }

        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String getdeleteProductPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @Transactional
    @PostMapping("/admin/product/delete")
    public String postdeleteProductPage(Model model,@ModelAttribute("newProduct") Product product) {
        this.productService.deleteProduct(product.getProductId());
        return "redirect:/admin/product";
    }

}
