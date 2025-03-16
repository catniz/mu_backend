package com.musinsa.backend.dto;

import com.musinsa.backend.model.Category;
import com.musinsa.backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    private BrandResponseDto brand;
    private Category category;
    private Long price;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .brand(BrandResponseDto.fromEntity(product.getBrand()))
                .category(product.getCategory())
                .price(product.getPrice())
                .build();
    }
}
