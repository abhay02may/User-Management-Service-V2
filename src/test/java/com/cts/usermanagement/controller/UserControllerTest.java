package com.cts.usermanagement.controller;


import com.cts.usermanagement.config.UserServiceExceptionHandler;
import com.cts.usermanagement.dto.UserRequest;
import com.cts.usermanagement.dto.UserResponse;
import com.cts.usermanagement.exception.UserNotFoundException;
import com.cts.usermanagement.service.UserService;
import com.cts.usermanagement.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    @InjectMocks
    private UserController controller;
    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new UserServiceExceptionHandler()).build();
        this.mapper = new ObjectMapper();
    }

    @AfterEach
    void afterAll() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    public void testSaveUserWhenUserCreated_HappyPath() throws Exception {

        //given
        UserRequest userRequest = UserRequest.builder()
                .userName("Vinod Rai")
                .department("IT")
                .managerName("Abhay Srivastva")
                .build();
        Long employeeId=1234L;
        //when
        when(userService.saveUser(any())).thenReturn(employeeId);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userRequest))
                .headers(new HttpHeaders());

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveUserWhenInvalidRequestBody_UnHappyPath() throws Exception {

        //given
        UserRequest userRequest = UserRequest.builder()
                .userName("Johny Liver")
                .department(null)
                .managerName(null)
                .build();

        Long employeeId=1234L;

        //when
        when(userService.saveUser(any())).thenReturn(employeeId);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userRequest))
                .headers(new HttpHeaders());

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindAllUsers_HappyPath() {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        // Mocking the userService to return a list of UserResponse objects
        List<UserResponse> expectedUserResponseList = new ArrayList<>();
        expectedUserResponseList.add(new UserResponse(1L,"Abhay", "IT", "Anuj Kumar"));
        expectedUserResponseList.add(new UserResponse(2L,"Vinod", "HR", "Suresh"));
        when(userService.findAllUsers()).thenReturn(expectedUserResponseList);

        // Act
        ResponseEntity<List<UserResponse>> responseEntity = userController.findAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected HTTP status code should be OK");
        assertEquals(expectedUserResponseList, responseEntity.getBody(), "Expected and actual user response lists should match");
    }

    @Test
    public void testFindAllUsers_UnHappyPath() {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        // Mocking the userService to return an empty list
        List<UserResponse> expectedEmptyList = new ArrayList<>();
        when(userService.findAllUsers()).thenReturn(expectedEmptyList);

        // Act
        ResponseEntity<List<UserResponse>> responseEntity = userController.findAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected HTTP status code should be OK");
        assertEquals(expectedEmptyList, responseEntity.getBody(), "Expected and actual user response lists should be empty");
    }

    @Test
    public void testFindUserById_HappyPath() throws UserNotFoundException {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Long employeeId = 123L;
        UserResponse expectedUserResponse = new UserResponse(1L,"Abhay", "IT", "Suresh");

        when(userService.findUserById(anyLong())).thenReturn(expectedUserResponse);

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.findUserById(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected HTTP status code should be OK");
        //assertNotNull("User response should not be null", responseEntity.getBody());
        assertEquals(expectedUserResponse, responseEntity.getBody(), "Expected and actual user responses should match");
    }

    @Test
    public void testFindUserById_UnhappyPath() throws UserNotFoundException {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Long employeeId = 123L;

        //when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException("User not found","USER_NOT_FOUND",404));

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.findUserById(employeeId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Expected HTTP status code should be NOT_FOUND");
        assertEquals(null, responseEntity.getBody(), "Expected user response body should be null");
    }

    @Test
    public void testUpdateUser_HappyPath() throws UserNotFoundException {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        UserRequest userRequest = new UserRequest(1L,"Abhay", "IT", "Suresh");
        UserResponse expectedUserResponse = new UserResponse(1L,"Abhay", "IT", "Suresh");

        when(userService.updateUser(any(UserRequest.class))).thenReturn(expectedUserResponse);

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.updateUser(userRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected HTTP status code should be OK");
        assertNotNull(responseEntity.getBody(),"User response should not be null");
        assertEquals(expectedUserResponse, responseEntity.getBody(), "Expected and actual user responses should match");
    }

    @Test
    public void testUpdateUser_UnhappyPath_BadRequest() throws UserNotFoundException {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        UserRequest userRequest = new UserRequest(null,"Abhay", "IT", "Suresh");

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.updateUser(userRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Expected HTTP status code should be BAD_REQUEST");
        assertEquals(null, responseEntity.getBody(), "Expected user response body should be null");
    }

    @Test
    public void testDeleteUserByUserId_HappyPath() {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Long employeeId = 123L;
        Long expectedDeletedUserId = 123L;

        when(userService.deleteUserByUserId(employeeId)).thenReturn(expectedDeletedUserId);

        // Act
        ResponseEntity<Long> responseEntity = userController.deleteUserByUserId(employeeId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Expected HTTP status code should be OK");
        assertEquals(expectedDeletedUserId, responseEntity.getBody(),"Expected and actual deleted user IDs should match");
    }

    @Test
    public void testDeleteUserByUserId_UnhappyPath() {
        // Arrange
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Long employeeId = 123L;

        // Simulate user not found scenario
        when(userService.deleteUserByUserId(employeeId)).thenReturn(null);

        // Act
        ResponseEntity<Long> responseEntity = userController.deleteUserByUserId(employeeId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Expected HTTP status code should be NOT_FOUND");
        assertEquals( null, responseEntity.getBody(),"Expected response body should be null");
    }
}
