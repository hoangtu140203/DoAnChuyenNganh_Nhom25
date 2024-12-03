package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category>findAll(){
        return categoryRepository.findAll();
    }


}
