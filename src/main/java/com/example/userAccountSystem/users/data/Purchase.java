package com.example.userAccountSystem.users.data;

public class Purchase {
    private Long productId;
    private Long quantity;

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
