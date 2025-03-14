package com.musinsa.backend.dto;

import com.musinsa.backend.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandResponseDto {
    private Long id;
    private String name;

    public static BrandResponseDto fromEntity(Brand brand) {
        return new BrandResponseDto(brand.getId(), brand.getName());
    }
}
