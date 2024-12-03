package com.gr25.thinkpro.controller.client;


import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.CustomerService;
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
public class CategoryPageController {
    private final CategoryService categoryService;

    @GetMapping("/admin/category")
    public String CategoryPage(Model model) {
        List<Category> categories = this.categoryService.getCategories();
        model.addAttribute("cates", categories);
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
        model.addAttribute("id",Id);
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
