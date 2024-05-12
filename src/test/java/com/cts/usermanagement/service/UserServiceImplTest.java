package com.cts.usermanagement.service;

import com.cts.usermanagement.dto.UserRequest;
import com.cts.usermanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testSaveUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Virat");
        userRequest.setDepartment("SPORTS");
        userRequest.setManagerName("Sourabh");

        Long userId = userService.saveUser(userRequest);
        assertNotNull(userId);
    }

    @Test
    void findUserById() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserByUserId() {
    }
}