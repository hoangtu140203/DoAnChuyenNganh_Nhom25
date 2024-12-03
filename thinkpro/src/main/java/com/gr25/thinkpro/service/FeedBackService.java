package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.entity.FeedBack;
import com.gr25.thinkpro.repository.FeedBackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;

    public Optional<Float> getAvgRateByProductId(long productId) {
        return feedBackRepository.getAvgRateByProductId(productId);
    }

    public Page<FeedBack> findByProductId(long productId, Pageable pageable) {
        return feedBackRepository.findAllByProductId(productId, pageable);
    }
}
