package com.musinsa.backend.controller;

import com.musinsa.backend.dto.ProductRequestDto;
import com.musinsa.backend.dto.ProductResponseDto;
import com.musinsa.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getProduct() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody @Valid ProductRequestDto product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
