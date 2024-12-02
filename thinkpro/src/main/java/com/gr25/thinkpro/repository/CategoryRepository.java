package com.gr25.thinkpro.repository;

import com.gr25.thinkpro.domain.entity.Category;
import com.gr25.thinkpro.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByName(String name);
    List<Category> findAll();
    Category save(Category category);
    Category findCategoryByCategoryId(long id);

    @Transactional
    @Modifying
    void deleteByCategoryId(long id);
}
