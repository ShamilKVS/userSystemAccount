package com.example.userAccountSystem.users.service;

import com.example.userAccountSystem.products.data.Product;
import com.example.userAccountSystem.products.data.ProductRepository;
import com.example.userAccountSystem.transactions.data.TransactionRepository;
import com.example.userAccountSystem.users.data.Purchase;
import com.example.userAccountSystem.users.data.User;
import com.example.userAccountSystem.users.data.UserDto;
import com.example.userAccountSystem.users.data.UserRepository;
import com.example.userAccountSystem.users.handler.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFirstName("Cristiano");
        user.setLastName("Ronaldo");
        user.setBalance(new BigDecimal("1000.00"));
        user.setFullName("Ronaldo Cristiano");

        userDto = new UserDto();
        userDto.setFirstName("Cristiano");
        userDto.setLastName("Ronaldo");

        product = new Product();
        product.setId(1L);
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto createdUser = userService.createUser(userDto);

        assertEquals("Cristiano", createdUser.getFirstName());
        assertEquals("Ronaldo", createdUser.getLastName());
        assertEquals("Ronaldo Cristiano", createdUser.getFullName());
    }

    @Test
    public void testPurchaseProduct() {
        Purchase purchase = new Purchase();
        purchase.setProductId(1L);
        purchase.setQuantity(5L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        userService.purchaseProduct(1L, purchase);

        assertEquals(5, user.getPurchasedProductQuantity(1L));
        assertEquals(new BigDecimal("500.00"), user.getBalance());
        assertEquals(5, product.getQuantity());
    }

    @Test
    public void testSellProduct() {
        // Setup initial purchase
        Purchase purchase = new Purchase();
        purchase.setProductId(1L);
        purchase.setQuantity(2L);
        user.addProduct(product, 2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        userService.sellProduct(1L, purchase);

        assertEquals(0, user.getPurchasedProductQuantity(1L));
        assertEquals(new BigDecimal("1000.00"), user.getBalance());
        assertEquals(12, product.getQuantity());
    }

    @Test
    public void testUpdateBalance() {
        BigDecimal amount = new BigDecimal("200.00");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateBalance(1L, amount);

        assertEquals(new BigDecimal("1200.00"), user.getBalance());
    }

    @Test
    public void testGetUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto foundUser = userService.getUser(1L);

        assertEquals("Cristiano", foundUser.getFirstName());
        assertEquals("Ronaldo", foundUser.getLastName());
    }

    @Test
    public void testCreateUserInvalidData() {
        UserDto invalidUserDto = new UserDto();
        assertThrows(UserException.class, () -> userService.createUser(invalidUserDto));
    }
}
