package com.musinsa.backend.dto;

import com.musinsa.backend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryMinMaxPriceDto {
    private Category category;
    private ProductResponseDto minPriceProduct;
    private ProductResponseDto maxPriceProduct;
}
