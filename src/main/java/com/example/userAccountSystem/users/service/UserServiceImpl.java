package com.example.userAccountSystem.users.service;

import com.example.userAccountSystem.products.data.Product;
import com.example.userAccountSystem.products.data.ProductRepository;
import com.example.userAccountSystem.products.handler.ProductException;
import com.example.userAccountSystem.transactions.data.Transaction;
import com.example.userAccountSystem.transactions.data.TransactionRepository;
import com.example.userAccountSystem.transactions.data.TransactionType;
import com.example.userAccountSystem.users.data.Purchase;
import com.example.userAccountSystem.users.data.User;
import com.example.userAccountSystem.users.data.UserDto;
import com.example.userAccountSystem.users.data.UserRepository;
import com.example.userAccountSystem.users.handler.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final TransactionRepository transactionRepository, final ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }


    @Transactional
    @Override
    public UserDto createUser(final UserDto userDto){
        if(userDto.getFirstName() == null || userDto.getLastName() == null ||
                userDto.getFirstName().isEmpty() || userDto.getLastName().isEmpty()){
            throw new UserException("Invalid Data");
        }
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setFullName(userDto.getLastName()+" "+userDto.getFirstName());
        user = this.userRepository.save(user);
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }


    @Transactional
    @Override
    public void purchaseProduct(final Long userId, Purchase purchase) {
        User user = getUserById(userId);
        Long productId = purchase.getProductId();
        Product product = this.productRepository.findById(productId).orElseThrow();

        if (purchase.getQuantity() > product.getQuantity()){
            throw new ProductException("Insufficient product quantity");
        }
        BigDecimal totalAmount = product.getPrice().multiply(new BigDecimal(purchase.getQuantity()));
        if (user.getBalance().compareTo(totalAmount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        user.setBalance(user.getBalance().subtract(totalAmount));
        user.addProduct(product,purchase.getQuantity().intValue());
        userRepository.save(user);

        Transaction transaction = new Transaction(user,product,totalAmount, purchase.getQuantity().intValue(), TransactionType.PURCHASE);
        transactionRepository.save(transaction);

        product.setQuantity(product.getQuantity()-purchase.getQuantity().intValue());
        this.productRepository.save(product);

    }

    @Transactional
    @Override
    public void sellProduct(final Long userId, Purchase purchase) {
        User user = getUserById(userId);
        Long productId = purchase.getProductId();
        Product product = this.productRepository.findById(productId).orElseThrow();
        int userQuantity = user.getPurchasedProductQuantity(productId);
        if (userQuantity < purchase.getQuantity()){
            throw new ProductException("Insufficient product quantity for user");
        }
        BigDecimal totalAmount = product.getPrice().multiply(new BigDecimal(purchase.getQuantity()));

        user.setBalance(user.getBalance().add(totalAmount));
        user.addProduct(product,-purchase.getQuantity().intValue());
        userRepository.save(user);

        Transaction transaction = new Transaction(user,product,totalAmount,purchase.getQuantity().intValue(), TransactionType.RETURN);
        transactionRepository.save(transaction);

        product.setQuantity(product.getQuantity()+purchase.getQuantity().intValue());
        this.productRepository.save(product);
    }

    @Transactional
    @Override
    public void updateBalance(final Long userId, BigDecimal amount) {
        User user = getUserById(userId);
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    @Override
    public UserDto getUser(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    private User getUserById(final Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
    }
}
