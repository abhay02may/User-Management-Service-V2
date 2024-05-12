package com.cts.usermanagement.service;

import com.cts.usermanagement.dto.UserRequest;
import com.cts.usermanagement.dto.UserResponse;
import com.cts.usermanagement.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    Long saveUser(UserRequest userRequest);

    UserResponse findUserById(Long userId) throws UserNotFoundException;

    List<UserResponse> findAllUsers();

    UserResponse updateUser(UserRequest userRequest) throws UserNotFoundException;

    Long deleteUserByUserId(Long userId);
}
