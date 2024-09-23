package com.example.userAccountSystem.users.controller;

import com.example.userAccountSystem.users.data.Purchase;
import com.example.userAccountSystem.users.data.UserDto;
import com.example.userAccountSystem.users.service.Serialize;
import com.example.userAccountSystem.users.service.UserReadService;
import com.example.userAccountSystem.users.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UsersApiControllerTest {

    @InjectMocks
    private UsersApiController usersApiController;

    @Mock
    private UserService userService;

    @Mock
    private UserReadService userReadService;

    @Mock
    private Serialize serialize;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersApiController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Cristiano");
        userDto.setLastName("Ronaldo");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("firstName", "Cristiano");
        jsonObject.put("lastName", "Ronaldo");

        JSONObject createJson = new JSONObject();
        createJson.put("firstName", "Cristiano");
        createJson.put("lastName", "Ronaldo");

        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
//        when(serialize.serialize(any(UserDto.class))).thenReturn(jsonObject.toString());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonObject.toString()));

        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    public void testPurchase() throws Exception {
        Purchase purchase = new Purchase();
        purchase.setProductId(1L);
        purchase.setQuantity(2L);
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("Cristiano");
        userDto.setLastName("Ronaldo");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("firstName", "Cristiano");
        jsonObject.put("lastName", "Ronaldo");

        JSONObject productJson = new JSONObject();
        productJson.put("productId", 1);
        productJson.put("quantity", 2);

        when(userReadService.getUserById(1L)).thenReturn(userDto);
        when(serialize.serialize(any(UserDto.class))).thenReturn(jsonObject.toString());

        mockMvc.perform(post("/users/1/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonObject.toString()));

        verify(userService, times(1)).purchaseProduct(eq(1L), any(Purchase.class));
    }
}
