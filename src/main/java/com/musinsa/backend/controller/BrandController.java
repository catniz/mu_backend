package com.musinsa.backend.controller;

import com.musinsa.backend.dto.*;
import com.musinsa.backend.model.Category;
import com.musinsa.backend.service.BrandProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandProductService brandProductService;

    @GetMapping("/category/all")
    public List<Category> getValidCategories() {
        return Category.validValues();
    }

    @GetMapping("/all")
    public List<BrandResponseDto> getAllBrands() {
        return brandProductService.getAllBrands();
    }

    @GetMapping("/{brandId}")
    public BrandWithProductsDto getBrandById(@PathVariable Long brandId) {
        return brandProductService.getBrandWithProducts(brandId);
    }

    @PostMapping
    public BrandResponseDto createBrand(@RequestBody @Valid BrandCreateDto brandCreateDto) {
        return brandProductService.createBrand(brandCreateDto);
    }

    @PutMapping("/{brandId}")
    public void updateBrand(@PathVariable Long brandId, @RequestBody @Valid BrandUpdateDto brandUpdateDto) {
        brandProductService.updateBrand(brandId, brandUpdateDto);
    }

    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable Long brandId) {
        brandProductService.deleteBrand(brandId);
    }

    @PostMapping("/{brandId}/product")
    public ProductResponseDto addProductToBrand(@PathVariable Long brandId, @RequestBody @Valid ProductCreateDto product) {
        return brandProductService.addProduct(brandId, product);
    }

    @PutMapping("/product/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductUpdateDto product) {
        brandProductService.updateProduct(productId, product);
    }

    @DeleteMapping("/product/{productId}")
    public void removeProduct(@PathVariable Long productId) {
        brandProductService.deleteProduct(productId);
    }


}
