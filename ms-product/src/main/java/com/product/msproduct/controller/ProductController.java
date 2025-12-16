package com.product.msproduct.controller;

import com.product.msproduct.client.MembershipClient;
import com.product.msproduct.domain.Product;
import com.product.msproduct.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MembershipClient membershipClient;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody @Valid Product product) {
        return productService.create(product);
    }

    @GetMapping("/category/{category}")
    public List<Product> byCategory(@PathVariable String category) {
        return productService.byCategory(category);
    }

    @GetMapping("/available")
    public List<Product> available() {
        return productService.available();
    }


    @GetMapping("/check-member/{memberId}")
    public String checkMember(@PathVariable Long memberId) {
        return membershipClient.memberExists(memberId)
                ? "MEMBER EXISTS"
                : "MEMBER NOT FOUND";
    }
}
