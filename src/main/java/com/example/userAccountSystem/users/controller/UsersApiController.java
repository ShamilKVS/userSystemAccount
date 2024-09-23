package com.example.userAccountSystem.users.controller;

import com.example.userAccountSystem.users.data.Purchase;
import com.example.userAccountSystem.users.data.UserDto;
import com.example.userAccountSystem.users.service.Serialize;
import com.example.userAccountSystem.users.service.UserReadService;
import com.example.userAccountSystem.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("users")
@Tag(name = "Users", description = "Operations related to user management")
public class UsersApiController {

    private final UserService userService;
    private final UserReadService userReadService;
    private final Serialize serialize;

    @Autowired
    public UsersApiController(final UserService userService, final UserReadService userReadService, final Serialize serialize) {
        this.userService = userService;
        this.userReadService = userReadService;
        this.serialize = serialize;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Registers a user with mandatory fields: first name, last name",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( required = true,content = @Content(schema = @Schema(implementation = UserControllerSwagger.PostUserCreateResponse.class))),
            responses = { @ApiResponse( responseCode = "200", description = "User created successfully",content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserControllerSwagger.GetUserCreateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userDto = userService.createUser(userDto);
        return ResponseEntity.status(200).body(userDto);
    }

    @PostMapping("/{id}/purchase")
    @Operation(summary = "Purchase new product", description = "Purchase a product\", description = \"Allows a user to purchase a product.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( required = true,content = @Content(schema = @Schema(implementation = UserControllerSwagger.PostPurchaseRequest.class))),
            responses = { @ApiResponse( responseCode = "200", description = "Purchase successful",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserControllerSwagger.PostPurchaseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<String> purchase(@PathVariable Long id, @RequestBody Purchase purchase) {
        if (purchase == null || purchase.getProductId() == null || purchase.getQuantity() == null || purchase.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid purchase data");
        }
        userService.purchaseProduct(id, purchase);
        UserDto user = userReadService.getUserById(id);
        return ResponseEntity.ok(this.serialize.serialize(user));
    }

    @PostMapping("/{id}/sell")
    @Operation(summary = "Sell the product", description = "Sell a product\", description = \"Allows a user to sell a product.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( required = true,content = @Content(schema = @Schema(implementation = UserControllerSwagger.PostPurchaseRequest.class))),
            responses = { @ApiResponse( responseCode = "200", description = "sell successful",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserControllerSwagger.PostPurchaseResponse.class)) ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<String> sell(@PathVariable Long id, @RequestBody Purchase purchase) {
        if (purchase == null || purchase.getProductId() == null || purchase.getQuantity() == null || purchase.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid sell data");
        }
        userService.sellProduct(id, purchase);
        return ResponseEntity.ok("Sell successful");
    }

    @PutMapping("/{id}/updatebalance")
    @Operation(summary = "Update user balance", description = "Updates the balance of the user.",
            responses = { @ApiResponse( responseCode = "200", description = "Updated user balance successful",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserControllerSwagger.UpdateUserBalanceResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {
        userService.updateBalance(id, amount);
        return ResponseEntity.ok("Balance updated to: " + amount);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches user details using the user ID.",
            responses = { @ApiResponse( responseCode = "200", description = "Fetch user by id successful",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserControllerSwagger.PostPurchaseResponse.class)) ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<String> getUser(@PathVariable Long id) {;
        return ResponseEntity.ok(this.serialize.serialize(this.userReadService.getUserById(id)));
    }


    @GetMapping
    @Operation(summary = "Get all users", description = "Fetches all users in the system.",
            responses = { @ApiResponse( responseCode = "200", description = "Fetch all users successful",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserControllerSwagger.GetAllUsersResponse.class)) ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok(this.serialize.serialize(this.userReadService.getAllUsers()));
    }
}
