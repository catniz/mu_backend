package com.musinsa.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class LowestPriceProductsByCategoryDto {
    List<ProductResponseDto> products;
    Long totalPrice;
}
