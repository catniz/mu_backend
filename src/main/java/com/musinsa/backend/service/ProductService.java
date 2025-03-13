package com.musinsa.backend.service;

import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        // fixme:: not found product exception, error handling
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setBrand(updatedProduct.getBrand());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());

        return product;
    }

    public void deleteProduct(Long id) {
        // fixme:: 브랜드 카테고리에 최소 1개는 있어야 함
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
