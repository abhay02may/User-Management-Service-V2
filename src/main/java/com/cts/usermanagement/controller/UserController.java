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


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> saveUser(@RequestBody @Valid UserRequest userRequest){
       Long employeeId= userService.saveUser(userRequest);
       if(employeeId==null){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(employeeId, HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("employeeId") Long employeeId) throws UserNotFoundException {
      UserResponse userResponse= userService.findUserById(employeeId);
      if(userResponse==null){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers(){
        List<UserResponse> userResponseList= userService.findAllUsers();
        return new ResponseEntity<>(userResponseList,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest) throws UserNotFoundException {
        if(userRequest.getEmployeeId()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       UserResponse userResponse= userService.updateUser(userRequest);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Long> deleteUserByUserId(@PathVariable("employeeId") Long employeeId){
        Long deletedUserId=userService.deleteUserByUserId(employeeId);
        if(deletedUserId==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedUserId,HttpStatus.OK);
    }
}
