package com.techelevator.dao;

import com.techelevator.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    BigDecimal getProfileBalanceByUserId(int userId);
    BigDecimal getProfileBalanceByUsername(String username);
    boolean create(String username, String password, String role);
    boolean updateUser(User updatedUser, int userId);
    boolean deleteUser(int userId);
}
