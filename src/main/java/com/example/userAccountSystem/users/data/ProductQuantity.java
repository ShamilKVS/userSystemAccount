package com.example.userAccountSystem.users.data;

public class ProductQuantity {
    private Long productId;
    private Integer quantity;

    public ProductQuantity(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
