package com.cts.usermanagement.service;

import com.cts.usermanagement.dto.UserRequest;
import com.cts.usermanagement.dto.UserResponse;
import com.cts.usermanagement.entity.User;
import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long saveUser(UserRequest userRequest) {
        synchronized (this) {
            Long maxUserId = repository.getMaxUserId();
            userRequest.setEmployeeId(maxUserId);
        }
        repository.createUser(userRequest.getEmployeeId(), userRequest.getUserName(), userRequest.getDepartment(), userRequest.getManagerName());
        return userRequest.getEmployeeId();
    }

    @Transactional
    @Override
    public UserResponse findUserById(Long userId) throws UserNotFoundException {
        UserResponse userResponse = UserResponse.builder().build();
        User user = repository.findByUserId(userId);
        if (user != null) {
            BeanUtils.copyProperties(user, userResponse);
        } else {
            throw new UserNotFoundException("User for the Given user Id : " + userId + " not found", "USER_NOT_FOUND", 404);
        }

        return userResponse;
    }

    @Transactional
    @Override
    public List<UserResponse> findAllUsers() {
        List<User> allUsers = repository.findAllUsers();
        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList = allUsers.stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            return userResponse;
        }).collect(Collectors.toList());
        return userResponseList;
    }

    @Transactional
    @Override
    public UserResponse updateUser(UserRequest userRequest) throws UserNotFoundException {
        repository.updateUser(userRequest.getEmployeeId(), userRequest.getUserName(), userRequest.getDepartment(), userRequest.getManagerName());
        UserResponse userResponse = findUserById(userRequest.getEmployeeId());
        return userResponse;
    }

    @Override
    public Long deleteUserByUserId(Long userId) {
        repository.deleteUserByUserId(userId);
        return userId;
    }


}
