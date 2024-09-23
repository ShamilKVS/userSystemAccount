package com.example.userAccountSystem.products.service;

import com.example.userAccountSystem.products.data.Product;
import com.example.userAccountSystem.products.data.ProductDto;
import com.example.userAccountSystem.products.data.ProductRepository;
import com.example.userAccountSystem.products.handler.ProductException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(final Long productId){
        return this.productRepository.findById(productId).orElseThrow(() -> new ProductException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    @Transactional
    @Override
    public ProductDto updateProductDetails(final Long productId, final ProductDto productDto){
        Product product = getProduct(productId);
        if(productDto.getQuantity() != 0 && product.getQuantity() != productDto.getQuantity()){
            product.setQuantity(productDto.getQuantity());
        }
        if (productDto.getName() !=  null && !productDto.getName().isEmpty() && !product.getName().equals(productDto.getName())){
            product.setName(productDto.getName());
        }
        if(productDto.getPrice() != null && productDto.getPrice().compareTo(BigDecimal.ZERO) > 0 && productDto.getPrice().compareTo(product.getPrice()) != 0){
            product.setPrice(productDto.getPrice());
        }
        product = this.productRepository.save(product);
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }

    @Transactional
    @Override
    public ProductDto createProduct(final ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        product = this.productRepository.save(product);
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }

}
