package com.example.userAccountSystem.users.data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private BigDecimal balance;
    private List<ProductQuantity> productQuantities;

    // Constructors
    public UserDto() {}

    public UserDto(Long id, String firstName, String lastName, String fullName, BigDecimal balance,List<ProductQuantity> productQuantities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.balance = balance;
        this.productQuantities = productQuantities;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public List<ProductQuantity> getProductQuantities() { return productQuantities; }
    public void setProductQuantities(List<ProductQuantity> productQuantities) { this.productQuantities = productQuantities; }
}
