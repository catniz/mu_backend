package com.musinsa.backend.service;

import com.musinsa.backend.dto.*;
import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Category;
import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.BrandRepository;
import com.musinsa.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandProductService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public List<BrandWithProductsDto> getAllBrandsWithProducts() {
        return brandRepository.findAll().stream().map(BrandWithProductsDto::fromEntity).toList();
    }

    public List<ProductResponseDto> getAllProductsByCategory(Category category) {
        return productRepository.findByCategory(category).stream().map(ProductResponseDto::fromEntity).toList();
    }

    public void createBrand(BrandCreateDto newBrand) {
        newBrand.validateHasAllCategoryProducts();

        Brand newBrandEntity = newBrand.toEntity();
        brandRepository.save(newBrandEntity);
    }

    @Transactional
    public void updateBrand(Long brandId, BrandUpdateDto updateBrand) {
        Brand brand = getBrandById(brandId);
        brand.setName(updateBrand.getName());
    }

    public void deleteBrand(Long brandId) {
        Brand brand = getBrandById(brandId);
        brandRepository.delete(brand);
    }

    public void addProduct(Long brandId, ProductCreateDto productCreateDto) {
        Brand brand = getBrandById(brandId);
        Product product = productCreateDto.toEntity(brand);
        product.setBrand(brand);
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateDto updateProduct) {
        Product product = getProductById(productId);
        product.setPrice(updateProduct.getPrice());
    }

    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        Brand brand = product.getBrand();

        List<Product> sameCategoryProducts = productRepository.findByBrandIdAndCategory(brand.getId(), product.getCategory());
        if (sameCategoryProducts.size() <= 1) {
            throw new IllegalStateException(
                    String.format("At least one product must remain in brand '%s' and category '%s'.",
                            brand.getName(), product.getCategory())
            );
        }

        productRepository.delete(product);
    }


    private Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Brand not found"));
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }


}
