package com.cts.usermanagement.repository;

import com.cts.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Procedure(procedureName  = "CREATE_USER")
    void createUser(Long userId,String userName, String department, String managerName);

    @Procedure(procedureName  = "UPDATE_USER")
    void updateUser(Long userId,String userName, String department, String managerName);

    @Procedure(procedureName  = "GET_USER_BY_ID")
    User findByUserId(Long userId);

    @Procedure(procedureName  = "GET_ALL_USERS")
    List<User> findAllUsers();

    @Procedure(procedureName  = "DELETE_USER")
    void deleteUserByUserId(Long userId);

    @Query(value = "SELECT MAX(USER_ID) FROM USER", nativeQuery = true)
    Long getMaxUserId();


}
