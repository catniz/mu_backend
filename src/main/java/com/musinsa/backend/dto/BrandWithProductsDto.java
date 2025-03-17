package com.musinsa.backend.dto;

import com.musinsa.backend.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BrandWithProductsDto {
    private Long id;
    private String name;
    private List<ProductResponseDto> products;

    public static BrandWithProductsDto fromEntity(Brand brand) {
        List<ProductResponseDto> products = brand.getProducts().stream().map(ProductResponseDto::fromEntity).toList();
        return new BrandWithProductsDto(brand.getId(), brand.getName(), products);
    }

    public Long getTotalPrice() {
        return products.stream().mapToLong(ProductResponseDto::getPrice).sum();
    }

    public BrandWithProductsDto distinctProductsByCategory(BinaryOperator<ProductResponseDto> merge) {
        List<ProductResponseDto> distinctProducts = this.products.stream().collect(
                Collectors.toMap(ProductResponseDto::getCategory, Function.identity(), merge, LinkedHashMap::new)).
                values().stream().toList();
        return new BrandWithProductsDto(this.id, this.name, distinctProducts);
    }

    public static BinaryOperator<ProductResponseDto> selectCheapestProduct() {
        return (p1, p2) -> p1.getPrice() <= p2.getPrice() ? p1 : p2;
    }
}
