package com.example.demo.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Product> getProductsByCategory(Long categoryId, int page, int size) {
        return productRepository.findAllByCategoryId(categoryId, PageRequest.of(page, size));
    }

    public Page<Product> getProductsBySeller(Long sellerId, int page, int size) {
        return productRepository.findAllBySellerId(sellerId, PageRequest.of(page, size));
    }

    public Page<Product> searchProducts(String query, int page, int size) {
        return productRepository.searchByName(query, PageRequest.of(page, size));
    }

    public Page<Product> getTopRatedProducts(int page, int size) {
        return productRepository.findAllByOrderByRatingDesc(PageRequest.of(page, size));
    }

    public Page<Product> getProductsByPriceRange(Double minPrice, Double maxPrice, int page, int size) {
        return productRepository.findByPriceBetween(minPrice, maxPrice, PageRequest.of(page, size));
    }
}
