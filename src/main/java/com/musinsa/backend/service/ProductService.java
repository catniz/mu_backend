package com.musinsa.backend.service;

import com.musinsa.backend.dto.ProductRequestDto;
import com.musinsa.backend.dto.ProductResponseDto;
import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.BrandRepository;
import com.musinsa.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponseDto::fromEntity).toList();
    }

    private Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand not found"));
    }

    public ProductResponseDto createProduct(ProductRequestDto newProduct) {
        Brand brand = getBrandById(newProduct.getBrandId());
        Product savedProduct = productRepository.save(newProduct.toEntity(brand));

        return ProductResponseDto.fromEntity(savedProduct);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto updatedProduct) {
        // fixme:: not found product exception, error handling
        Brand brand = getBrandById(updatedProduct.getBrandId());
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setBrand(brand);
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());

        return ProductResponseDto.fromEntity(product);
    }

    public void deleteProduct(Long id) {
        // fixme:: 브랜드 카테고리에 최소 1개는 있어야 함
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
