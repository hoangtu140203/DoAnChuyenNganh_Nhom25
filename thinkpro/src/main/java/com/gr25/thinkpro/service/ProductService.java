package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.RegisterRequestDto;
import com.gr25.thinkpro.domain.entity.Cart;
import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Customer;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;


    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public Product getProductById(long id) {
        return productRepository.findProductByProductId(id);
    }
    public void rqProduct(Product product, RedirectAttributes redirectAttributes) {

        if(product.getName() == null || product.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorName", "Tên không được để trống");
        }
        if(product.getQuantity()<=0){
            redirectAttributes.addFlashAttribute("errorQuantity", "Số lượng sản phẩm phải lớn hơn không");
        }
        if(product.getPrice()<=0 ){
            redirectAttributes.addFlashAttribute("errorPrice", "Giá sản phẩm phải lớn hơn không");
        }
        if (product.getDiscount()<=0){
            redirectAttributes.addFlashAttribute("errorDiscount", "Giảm giá sản phẩm phải lớn hơn không");
        }

    }
    public boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
