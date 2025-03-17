package com.musinsa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LowestPriceBrandDto {
    BrandResponseDto brand;
    List<ProductResponseDto> products;
    Long totalPrice;
}

