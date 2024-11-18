package com.gr25.thinkpro.domain.entity;

import com.gr25.thinkpro.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Builder
public class Category extends DateAuditing implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private long categoryId;
    private String name;

    @OneToMany(mappedBy = "category")
    List<Product> products;
}
