package com.example.userAccountSystem.users.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

final class UserControllerSwagger {

    private UserControllerSwagger() {}

    @Schema(description = "PostUserCreateResponse")
    public static final class PostUserCreateResponse {

        private PostUserCreateResponse() {}

        @Schema(example = "Cristiano")
        public String firstName;

        @Schema(example = "Ronaldo")
        public String lastName;

        @Schema(example = "500")
        public BigDecimal balance;
    }

    @Schema(description = "GetUserCreateResponse")
    public static final class GetUserCreateResponse {

        private GetUserCreateResponse() {}

        @Schema(example = "1")
        public Long id;

        @Schema(example = "Cristiano")
        public String firstName;

        @Schema(example = "Ronaldo")
        public String lastName;

        @Schema(example = "Ronaldo Cristiano")
        public String fullName;
    }

    @Schema(description = "PostPurchaseRequest")
    public static final class PostPurchaseRequest {

        private PostPurchaseRequest() {
        }

        @Schema(example = "1")
        public Long productId;

        @Schema(example = "10")
        public Long quantity;
    }
    @Schema(description = "PostPurchaseResponse")
    public static final class PostPurchaseResponse {

        private PostPurchaseResponse() {
        }

        @Schema(example = "2")
        public Long id;

        @Schema(example = "Cristiano")
        public String firstName;

        @Schema(example = "Ronaldo")
        public String lastName;

        @Schema(example = "Ronaldo Cristiano")
        public String fullName;

        @Schema(example = "1054.60")
        public BigDecimal balance;

        @Schema(description = "List of product quantities")
        public List<ProductQuantity> productQuantities;

        @Schema(description = "Product quantity details")
        public static class ProductQuantity {

            @Schema(example = "1")
            public Long productId;

            @Schema(example = "22")
            public Integer quantity;
        }
    }
    @Schema(description = "UpdateUserBalanceResponse")
    public static class UpdateUserBalanceResponse{
        private UpdateUserBalanceResponse(){

        }
        @Schema(example = "Balance updated to: ")
        private String message;
    }

    @Schema(description = "GetAllUsersResponse")
    public static class GetAllUsersResponse{
        private GetAllUsersResponse(){

        }

        @Schema(description = "List of users")
        public List<PostPurchaseResponse> users;
    }


}
