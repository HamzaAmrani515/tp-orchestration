package com.product.msproduct.service;

import com.product.msproduct.domain.Product;
import com.product.msproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product create(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public List<Product> byCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> available() {
        return productRepository.findByActiveTrueAndQuantityGreaterThan(0);
    }
}
