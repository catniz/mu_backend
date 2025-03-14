package com.musinsa.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class BrandRequestDto {
    @NotBlank(message = "Brand name cannot be empty or contain only spaces.")
    private String name;
    // fixme:: 모든 카테고리당 최소 1개의 상품이 있어야 함
}
