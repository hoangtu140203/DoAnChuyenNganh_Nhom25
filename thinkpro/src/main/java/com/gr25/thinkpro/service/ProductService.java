package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.dto.request.ProductCriteriaDto;
import com.gr25.thinkpro.domain.entity.Product;
import com.gr25.thinkpro.repository.ProductRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService  {
    private final ProductRepository productRepository;
    public Page<Product> findProduct(ProductCriteriaDto productCriteriaDto,Pageable pageable) {


        Page<Product> page = productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (productCriteriaDto.getName() != null && !productCriteriaDto.getName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + productCriteriaDto.getName() + "%"));
                }

                long min = 0, max = 0;
                if (productCriteriaDto.getPrice() != null && !productCriteriaDto.getPrice().isEmpty()) {
                    switch (productCriteriaDto.getPrice()) {
                        case "duoi-10-trieu":
                            min = 1;
                            max = 10000000;
                            break;
                        case "10-15-trieu":
                            min = 10000000;
                            max = 15000000;
                            break;
                        case "15-20-trieu":
                            min = 15000000;
                            max = 20000000;
                            break;
                        case "tren-20-trieu":
                            min = 20000000;
                            max = 200000000;
                            break;
                    }
                    predicates.add(criteriaBuilder.between(root.get("price"), min, max));
                }

                if (productCriteriaDto.getCategory() != null && !productCriteriaDto.getCategory().isEmpty()) {

                    Join<Object, Object> joinCategory = root.join("category", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(joinCategory.get("name"), productCriteriaDto.getCategory()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        }, pageable);
        return page;
    }
    public Product findProductById(Long id) {
        return productRepository.findByProductId(id).orElse(null);
    }

}
