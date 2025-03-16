package com.musinsa.backend.service;

import com.musinsa.backend.dto.CategoryMinMaxPriceDto;
import com.musinsa.backend.dto.ProductResponseDto;
import com.musinsa.backend.model.Category;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceComparisonService {
    private final ProductService productService;

    public CategoryMinMaxPriceDto getCategoryMinMaxPrice(Category category) {
        ProductResponseDto minProduct = null, maxProduct = null;

        List<ProductResponseDto> productsByCategory = productService.getByCategory(category);

        if (productsByCategory.isEmpty()) {
            throw new EntityNotFoundException("No products found for the given category.");
        }

        for (ProductResponseDto product : productsByCategory) {
            if (minProduct == null || minProduct.getPrice() > product.getPrice()) {
                minProduct = product;
            }
            if (maxProduct == null || maxProduct.getPrice() < product.getPrice()) {
                maxProduct = product;
            }
        }

        return CategoryMinMaxPriceDto.builder().
                category(category).
                minPriceProduct(minProduct).
                maxPriceProduct(maxProduct).
                build();
    }
}
