package com.gr25.thinkpro.controller.client;


import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class CategoryPageController {
    private final CategoryService categoryService;

    @GetMapping("/admin/category")
    public String CategoryPage(Model model,@RequestParam("page") Optional<String> page) {
        int pageNum = 1;
        try {
            if(page.isPresent()) {
                pageNum = Integer.parseInt(page.get());
            }
        }catch (Exception e) {}

        Pageable pageable = PageRequest.of(pageNum-1, 4);
        Page<Category> categoryPage = this.categoryService.findAll(pageable);
        List<Category> categories = categoryPage.getContent();

        model.addAttribute("cates", categories);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", categoryPage.getTotalPages());

        model.addAttribute("newCategory", new Category());
        return "admin/category/show";
    }


    @PostMapping("/admin/category")
    public String searchCategoryPage(Model model,@ModelAttribute("newCategory") Category category
    ,@RequestParam("page") Optional<String> page) {

        Category category1 = this.categoryService.getCategoryByName(category.getName());
        Page<Category> categoryPage;

        int pageNum = 1;
        try {
            if(page.isPresent()) {
                pageNum = Integer.parseInt(page.get());
            }
        }catch (Exception e) {}
        Pageable pageable = PageRequest.of(pageNum-1, 4);
        if (category1 != null) {
            categoryPage = this.categoryService.findBynameContaining(category1.getName(),pageable);
        } else {
            categoryPage = this.categoryService.findAll(pageable);
        }

        List<Category> categories = categoryPage.getContent();

        model.addAttribute("cates", categories);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        return "admin/category/show";
    }


    @GetMapping("/admin/category/create")
    public String getcreateCategoryPage(Model model) {
        model.addAttribute("newCate", new Category());
        return "admin/category/create";
    }

    @PostMapping("/admin/category/create")
    public String createCategoryPage(Model model,@ModelAttribute("newCate") Category category) {
        Category newCate=this.categoryService.getCategoryByName(category.getName());
        if(newCate==null) {
            category.setCreatedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
            category.setLastModifiedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
            this.categoryService.saveCategory(category);
            return "redirect:/admin/category";
        }
        else {
            model.addAttribute("error", "Danh mục đã tồn tại");
            return "admin/category/create";
        }

    }

    @GetMapping("/admin/category/update/{Id}")
    public String getupdateCategoryPage(Model model, @PathVariable long Id) {
        Category category = new Category();
        model.addAttribute("newCate", category);
        Category category1 = this.categoryService.getCategoryById(Id);
        model.addAttribute("category1", category1);
        return "admin/category/update";
    }

    @PostMapping("/admin/category/update/{Id}")
    public String updateCategoryPage(Model model,@PathVariable("Id") long id ,@ModelAttribute Category category) {

        List<Category> categories = this.categoryService.getCategories();
        for(Category c : categories) {
            if(Objects.equals(c.getName(), category.getName())) {
                model.addAttribute("error","Danh mục đã tồn tại");
                Category category1 = this.categoryService.getCategoryById(id);
                model.addAttribute("category1", category1);
                model.addAttribute("newCate", new Category());
                return "admin/category/update";
            }
        }
        Category cate = this.categoryService.getCategoryById(id);
            cate.setName(category.getName());
            cate.setLastModifiedDate(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
            this.categoryService.saveCategory(cate);
            return "redirect:/admin/category";


    }

    @GetMapping("/admin/category/delete/{Id}")
    public String getdeleteCategoryPage(Model model, @PathVariable long Id) {
        Category category = this.categoryService.getCategoryById(Id);
        model.addAttribute("cate",category);
        model.addAttribute("newCate", new Category());
        return "admin/category/delete";
    }

    @PostMapping("/admin/category/delete")
    @Transactional
    public String deleteCategoryPage(Model model,@ModelAttribute("newCate") Category category) {
        this.categoryService.deleteCategory(category.getCategoryId());
        return "redirect:/admin/category";
    }


}
