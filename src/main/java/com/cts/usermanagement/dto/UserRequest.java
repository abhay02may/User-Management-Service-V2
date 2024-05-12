package com.cts.usermanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {

    private Long userId;
    private String userName;
    @NotEmpty(message = "department field should be not empty")
    private String department;
    @NotEmpty(message = "managerName field should be not empty")
    private String managerName;
}
