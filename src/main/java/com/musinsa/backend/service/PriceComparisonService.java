package com.musinsa.backend.service;

import com.musinsa.backend.dto.*;
import com.musinsa.backend.model.Category;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PriceComparisonService {
    private final BrandService brandService;
    private final ProductService productService;

    public LowestPriceProductsByCategoryDto getLowestByCategory(List<Category> targetCategories) {
        List<ProductResponseDto> lowestProducts = targetCategories.stream().
                map(targetCategory -> getCategoryMinMaxPrice(targetCategory).getMinPriceProduct()).
                toList();
        Long totalPrice = lowestProducts.stream().mapToLong(ProductResponseDto::getPrice).sum();

        return LowestPriceProductsByCategoryDto.builder().
                totalPrice(totalPrice).
                products(lowestProducts).
                build();
    }

    public LowestPriceBrandDto getLowestBrand() {
        List<BrandWithProductsDto> allBrands = brandService.getAllBrandsWithProducts().stream().
                map(b -> b.distinctProductsByCategory(BrandWithProductsDto.selectCheapestProduct())).
                filter(b -> b.getProducts().size() == Category.validValues().size()).
                toList();

        Long lowestPrice = -1L;
        BrandWithProductsDto cheapestBrand = null;
        for (BrandWithProductsDto brand : allBrands) {
            if (lowestPrice == -1L || brand.getTotalPrice() < lowestPrice) {
                lowestPrice = brand.getTotalPrice();
                cheapestBrand = brand;
            }
        }

        if (lowestPrice == -1L) {
            throw new NoSuchElementException("No brand has products in all required categories");
        }
        return LowestPriceBrandDto.builder().
                brand(new BrandResponseDto(cheapestBrand.getId(), cheapestBrand.getName())).
                products(cheapestBrand.getProducts()).
                totalPrice(lowestPrice).
                build();
    }

    public CategoryMinMaxPriceDto getCategoryMinMaxPrice(Category category) {
        ProductResponseDto minProduct = null, maxProduct = null;

        List<ProductResponseDto> productsByCategory = productService.getByCategory(category);

        if (CollectionUtils.isEmpty(productsByCategory)) {
            throw new EntityNotFoundException("No products found for the given category " + category.getName());
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
