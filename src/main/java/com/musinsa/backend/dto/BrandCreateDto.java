package com.musinsa.backend.dto;

import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Category;
import com.musinsa.backend.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BrandCreateDto {
    @NotBlank(message = "Brand name cannot be empty or contain only spaces.")
    private String name;
    @Valid
    private List<ProductCreateDto> products;

    public void validateHasAllCategoryProducts() {
        Set<Category> categories = products.stream().map(ProductCreateDto::getCategory).collect(Collectors.toSet());
        if (categories.size() != Category.validValues().size()) {
            throw new IllegalArgumentException("All categories must be provided.");
        }
    }

    public Brand toEntity() {
        Brand brand = new Brand();

        brand.setName(name);
        List<Product> productEntities = products.stream().map(p -> p.toEntity(brand)).toList();
        brand.setProducts(productEntities);

        return brand;
    }
}

