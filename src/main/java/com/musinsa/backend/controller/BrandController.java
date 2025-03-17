package com.musinsa.backend.controller;

import com.musinsa.backend.dto.*;
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

    @GetMapping("/all")
    public List<BrandWithProductsDto> getAllBrandsWithProducts() {
        return brandProductService.getAllBrandsWithProducts();
    }

    @PostMapping
    public void createBrand(@RequestBody @Valid BrandCreateDto brandCreateDto) {
        brandProductService.createBrand(brandCreateDto);
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
    public void addProductToBrand(@PathVariable Long brandId, @RequestBody @Valid ProductCreateDto product) {
        brandProductService.addProduct(brandId, product);
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
