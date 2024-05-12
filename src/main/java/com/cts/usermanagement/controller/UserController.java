package com.cts.usermanagement.controller;

import com.cts.usermanagement.dto.UserRequest;
import com.cts.usermanagement.dto.UserResponse;
import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }*/

    @PostMapping
    public ResponseEntity<Long> saveUser(@RequestBody @Valid UserRequest userRequest){
       Long userId= userService.saveUser(userRequest);
       return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("userId") Long userId) throws UserNotFoundException {
      UserResponse userResponse= userService.findUserById(userId);
      return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers(){
        List<UserResponse> userResponseList= userService.findAllUsers();
        return new ResponseEntity<>(userResponseList,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserRequest userRequest){
        if(userRequest.getUserId()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUserByUserId(@PathVariable("userId") Long userId){
        Long deletedUserId=userService.deleteUserByUserId(userId);
        return new ResponseEntity<>(deletedUserId,HttpStatus.OK);
    }
}
