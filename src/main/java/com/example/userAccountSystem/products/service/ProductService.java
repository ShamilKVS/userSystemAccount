package com.example.userAccountSystem.products.service;

import com.example.userAccountSystem.products.data.Product;
import com.example.userAccountSystem.products.data.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface ProductService {
    Product getProduct(Long productId);

    List<Product> getAllProducts();

    @Transactional
    ProductDto updateProductDetails(Long productId, ProductDto productDto);

    @Transactional
    ProductDto createProduct(ProductDto productDto);
}
