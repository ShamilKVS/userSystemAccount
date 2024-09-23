package com.example.userAccountSystem.transactions.data;

import com.example.userAccountSystem.products.data.Product;
import com.example.userAccountSystem.users.data.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "product_quantity",nullable = false)
    private int productQuantity;

    @Column(name = "transaction_date", nullable = false, updatable = false)
    private LocalDateTime transactionDate;


    public Transaction(final User user,final Product product,final BigDecimal amount,final int productQuantity,final TransactionType transactionType) {
         this.user = user;
        this.product = product;
        this.amount = amount;
        this.productQuantity = productQuantity;
        this.transactionType = transactionType;
    }
    @PrePersist
    public void prePersist() {
        this.transactionDate = LocalDateTime.now();
    }

    public Product getProduct() {
        return product;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
