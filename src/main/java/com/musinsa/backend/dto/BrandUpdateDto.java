package com.musinsa.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BrandUpdateDto {
    @NotBlank(message = "Brand name cannot be empty or contain only spaces.")
    private String name;
}
