package com.musinsa.backend.dto;

import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Category;
import com.musinsa.backend.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDto {
    @NotNull(message = "Category is required and cannot be null.")
    private Category category;
    @NotNull(message = "Price is required and cannot be null.")
    @Min(value = 1, message = "Price must be at least {value}.")
    private Long price;

    public Product toEntity(Brand brand) {
        Product product = new Product();
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(price);
        return product;
    }
}
