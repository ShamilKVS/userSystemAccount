package com.example.userAccountSystem.products.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public class ProductControllerSwagger {

    @Schema(description = "PostCreateProductRequest")
    public static class PostCreateProductRequest{
        private PostCreateProductRequest(){

        }

        @Schema(example = "TATA STEEL", description = "Name of the product")
        private String name;

        @Schema(example = "20.5", description = "Price of the product")
        private BigDecimal price;

        @Schema(example = "100", description = "Available quantity of the product")
        private int quantity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
    @Schema(description = "PostCreateProductResponse")
    public static class PostCreateProductResponse{
        private PostCreateProductResponse(){

        }
        @Schema(example = "1", description = "ID of the product")
        private Long id;

        @Schema(example = "TATA STEEL", description = "Name of the product")
        private String name;

        @Schema(example = "20.5", description = "Price of the product")
        private BigDecimal price;

        @Schema(example = "100", description = "Available quantity of the product")
        private int quantity;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    @Schema(description = "GetAllProductResponse")
    public static class GetAllProductResponse{
        private GetAllProductResponse(){

        }
        @Schema(description = "List of Product")
        private List<ListProduct> listProducts;

        public List<ListProduct> getListProducts() {
            return listProducts;
        }

        public void setListProducts(List<ListProduct> listProducts) {
            this.listProducts = listProducts;
        }

        @Schema(description = "ListProduct")
        public static class ListProduct {
            private ListProduct() {

            }
            @Schema(example = "1", description = "ID of the product")
            private Long id;

            @Schema(example = "TATA STEEL", description = "Name of the product")
            private String name;

            @Schema(example = "20.5", description = "Price of the product")
            private BigDecimal price;

            @Schema(example = "100", description = "Available quantity of the product")
            private int quantity;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }
        }
    }
}
