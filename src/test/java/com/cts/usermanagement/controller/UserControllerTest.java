package com.cts.usermanagement.controller;

import com.cts.usermanagement.dto.UserRequest;
import com.cts.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
@WithMockUser
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testSaveUser_positiveFlow() {
        // Create a sample user request
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("John Doe");
        userRequest.setDepartment("IT");
        userRequest.setManagerName("Jane Doe");
        Long employeeId=123L;

        // Mock the userService's saveUser method to return a user ID
        when(userService.saveUser(any(UserRequest.class))).thenReturn(employeeId);

        // Make the save user request
        ResponseEntity<Long> responseEntity = userController.saveUser(userRequest);

        // Verify that the userService's createUser method was called with the correct arguments
        verify(userService).saveUser(any(UserRequest.class));

        // Check the response status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Check the response body for the created user ID
        assertEquals(employeeId, responseEntity.getBody().longValue());
    }

   /* @Test
    public void createStudentCourse() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Abhay Srivastava");
        userRequest.setDepartment("IT");
        userRequest.setManagerName("Jane Doe");
        Long employeeId=123L;

        String userRequestJson = "{\"userName\":\"Abhay Srivastava\",\"department\":\"IT\",\"managerName\":\"Jane Doe\"}";


        // studentService.addCourse to respond back with mockCourse
        when(userService.saveUser(any(UserRequest.class))).thenReturn(employeeId);

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user")
                .accept(MediaType.APPLICATION_JSON).content(userRequestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/user",
                response.getHeader(HttpHeaders.LOCATION));

    }*/

}
