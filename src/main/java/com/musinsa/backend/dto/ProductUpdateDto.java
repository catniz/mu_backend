package com.musinsa.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateDto {
    @NotNull(message = "Price is required and cannot be null.")
    @Min(value = 1, message = "Price must be at least {value}.")
    private Long price;
}
