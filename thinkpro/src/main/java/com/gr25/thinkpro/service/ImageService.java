package com.gr25.thinkpro.service;

import com.gr25.thinkpro.domain.entity.Image;
import com.gr25.thinkpro.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
    public void deleteImage(long id) {
        imageRepository.deleteByProductId(id);
    }
}
