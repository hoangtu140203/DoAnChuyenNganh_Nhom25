package com.gr25.thinkpro.controller.client;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.FeedBack;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.service.CategoryService;
import com.gr25.thinkpro.service.FeedBackService;
import com.gr25.thinkpro.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final FeedBackService feedBackService;


    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable(name = "id") long id, @RequestParam(name = "pageNum") Optional<Integer> pageNum) {
        Product pr = this.productService.findProductById(id);
        List<Category> categories = categoryService.findAll();
        if(!pageNum.isPresent()) {
           pageNum=Optional.of(1);
        }
        PageRequest pageRequest = PageRequest.of(pageNum.get()-1, 2);

        Page<FeedBack> page = feedBackService.findByProductId(id, pageRequest);
        float rate = feedBackService.getAvgRateByProductId(id).orElse(0.0f);
        model.addAttribute("rate", rate);
        model.addAttribute("categories", categories);
        model.addAttribute("feedbacks", page.getContent());
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        model.addAttribute("currentPage", page.getNumber()+1);
        model.addAttribute("totalPages", page.getTotalPages());

        return "client/product/detail";
    }
}
