package com.gr25.thinkpro.domain.entity;

import com.gr25.thinkpro.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="feedback")
public class FeedBack extends DateAuditing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;

    private String content;

    private int rate;

    private String createBy;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
