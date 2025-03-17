package com.musinsa.backend.service;

import com.musinsa.backend.dto.BrandWithProductsDto;
import com.musinsa.backend.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<BrandWithProductsDto> getAllBrandsWithProducts() {
        return brandRepository.findAll().stream().map(BrandWithProductsDto::fromEntity).toList();
    }
}
