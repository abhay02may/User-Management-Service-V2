package com.cts.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String department;
    private String managerName;

}
