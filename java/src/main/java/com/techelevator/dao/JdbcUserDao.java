package com.techelevator.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }
        return userId;
    }

    @Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			return null;
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password, String role) {
        String insertUserSql = "insert into users (username,password_hash,role) values (?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole) == 1;
    }

    @Override
    public boolean updateUser(User updatedUser, int userId) {
        boolean success = false;
        String sqlUpdateRelatedGameOwner = "UPDATE game SET owner_name='admin' WHERE owner_name=(SELECT username FROM users WHERE user_id=? ); ";
        String sqlUpdateUserInfo = "UPDATE users SET username=?, password_hash=?, role=?, profile_balance=?, first_name=?, last_name=? WHERE user_id=? ;";
        try {
            jdbcTemplate.update(sqlUpdateRelatedGameOwner, userId);
            int numberOfRows = jdbcTemplate.update(sqlUpdateUserInfo, updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getRole(), updatedUser.getProfileBalance(),
                    updatedUser.getFirstName(), updatedUser.getLastName(), userId);
            if (numberOfRows == 0 ){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            success = true;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return success;
    }

    @Override
    public boolean deleteUser(int userId) {
        boolean success = false;
        String sqlDeleteFromGameUser = "DELETE FROM game_user WHERE user_id = ?; " +
                "DELETE FROM game_user WHERE game_id = (SELECT game_id FROM game WHERE owner_name = (SELECT username FROM users WHERE user_id = ?));";
        String sqlDeleteFromTransaction = "DELETE FROM transaction WHERE user_id = ?;" +
                "DELETE FROM transaction WHERE game_id = (SELECT game_id FROM game WHERE owner_name = (SELECT username FROM users WHERE user_id = ?));";
        String sqlDeleteFromGame = "DELETE FROM game WHERE owner_name = (SELECT username FROM users WHERE user_id = ?);";
        String sqlDeleteFromUsers = "DELETE FROM users WHERE user_id = ?";
        try {
            jdbcTemplate.update(sqlDeleteFromGameUser, userId, userId);
            jdbcTemplate.update(sqlDeleteFromTransaction, userId, userId);
            jdbcTemplate.update(sqlDeleteFromGame, userId);
            int numberOfRows = jdbcTemplate.update(sqlDeleteFromUsers, userId);
            if (numberOfRows == 0 ){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            success = true;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return success;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }
}
