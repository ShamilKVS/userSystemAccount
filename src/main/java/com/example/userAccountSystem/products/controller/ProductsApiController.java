package com.example.userAccountSystem.products.controller;

import com.example.userAccountSystem.products.data.ProductDto;
import com.example.userAccountSystem.products.service.ProductService;
import com.example.userAccountSystem.users.service.Serialize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductsApiController {

    private final Serialize serialize;
    private final ProductService productService;

    @Autowired
    public ProductsApiController(final Serialize serialize, final ProductService productService) {
        this.serialize = serialize;
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the given details.",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,content = @Content(schema = @Schema(implementation = ProductControllerSwagger.PostCreateProductRequest.class))),
            responses = {@ApiResponse(responseCode = "200", description = "Product created successfully",content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductControllerSwagger.PostCreateProductResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")})
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.status(200).body(this.serialize.serialize(this.productService.createProduct(productDto)));
    }

    @GetMapping
    @Operation(summary = "Get all products",description = "Retrieves a list of all products.",
            responses = {@ApiResponse(responseCode = "200", description = "List of products retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductControllerSwagger.GetAllProductResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No products found")
            })
    public String getAllProducts(){
        return serialize.serialize(this.productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID", description = "Retrieves the details of a product using its ID.",
            responses = {@ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductControllerSwagger.PostCreateProductResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            })
    public String getProduct(@PathVariable Long id){
        return this.serialize.serialize(this.productService.getProduct(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product details",description = "Updates the details of an existing product.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(schema = @Schema(implementation = ProductControllerSwagger.PostCreateProductRequest.class))),
            responses = {@ApiResponse(responseCode = "200", description = "Product updated successfully",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductControllerSwagger.PostCreateProductResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Product not found"),@ApiResponse(responseCode = "400", description = "Invalid input data")})
    public String updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){

        return this.serialize.serialize(this.productService.updateProductDetails(id,productDto)) ;
    }
}
