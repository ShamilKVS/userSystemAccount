package com.example.userAccountSystem.users.data;

import com.example.userAccountSystem.products.data.Product;
import com.example.userAccountSystem.transactions.data.Transaction;
import com.example.userAccountSystem.transactions.data.TransactionType;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "display_name",nullable = false,unique = true)
    private String fullName;

    private BigDecimal balance = BigDecimal.ZERO;
    @Version
    private Long version;

    @ManyToMany
    @JoinTable(name = "user_product",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    // Map to store product IDs and their quantities
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_product_quantities", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Long, Integer> productQuantities = new HashMap<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String fullName, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

//    public int getPurchasedProductQuantity(final Long productId){
//        return getTransactions().stream().filter(trx -> trx.getProduct().getId().equals(productId))
//                .mapToInt(transaction -> transaction.getTransactionType().equals(TransactionType.PURCHASE)
//                        ? transaction.getProductQuantity() : -transaction.getProductQuantity()).sum();
//    }

    public int getPurchasedProductQuantity(final Long productId){
        return getProductQuantities().getOrDefault(productId,0);
    }
    public void addProduct(Product product, int quantity) {
        productQuantities.merge(product.getId(), quantity, Integer::sum);
    }

    public Map<Long, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

