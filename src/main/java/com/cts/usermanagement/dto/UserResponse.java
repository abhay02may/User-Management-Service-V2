package com.cts.usermanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private Long employeeId;
    private String userName;
    private String department;
    private String managerName;
}
