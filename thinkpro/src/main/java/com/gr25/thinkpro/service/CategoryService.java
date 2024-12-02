package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String categoryName) {
        return this.categoryRepository.findCategoryByName(categoryName);
    }
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findCategoryByCategoryId(id);
    }

    public void deleteCategory(long id) {
        this.productRepository.updateCategoryToNull(id);
        this.categoryRepository.deleteByCategoryId(id);
    }
}
