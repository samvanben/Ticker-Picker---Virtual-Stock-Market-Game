package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.User;
import com.techelevator.model.gameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcGameUserDao implements gameUserDao{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcGameUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<gameUser> getPlayerByGameId(int gameId) {
        List<gameUser> gameUsers = new ArrayList<>();
        String sql = "SELECT * FROM game_user WHERE game_id = ? ;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
            while (results.next()) {
                gameUser gameUser = mapRowToGameUser(results);
                gameUsers.add(gameUser);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return gameUsers;
    }

    @Override
    public int createGameUser(gameUser gameUserToCreate) {
        int gameUserId = 0;
        String sql = "INSERT INTO game_user (game_id, user_id, available_balance, total_balance) VALUES (?, ?, ?, ?)" +
                " RETURNING game_user_id; ";
        try{
            gameUserId = jdbcTemplate.queryForObject(sql, int.class, gameUserToCreate.getGame_id(), gameUserToCreate.getUser_id(), gameUserToCreate.getAvailableBalance(), gameUserToCreate.getTotalBalance());
            gameUserToCreate.setGameUserId(gameUserId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        String addOwnerToGame = "";
        return gameUserId;
    }

    @Override
    public boolean updateGameUser(gameUser updatedGameUser, int gameUserId) {
        String sql = "UPDATE game_user SET game_id = ?, user_id = ?, available_balance = ?, total_balance = ? WHERE game_user_id = ?; ";
        try{
            int numberOfRows = jdbcTemplate.update(sql, updatedGameUser.getGame_id(), updatedGameUser.getUser_id(),
                    updatedGameUser.getAvailableBalance(), updatedGameUser.getTotalBalance(), gameUserId);
            if(numberOfRows == 0 ){
                throw new DaoException("no rows affected");
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return true;
    }

    @Override
    public boolean deleteGameUser(int userId) {
        boolean success = false;
        String sql = "Delete * from game_user where game_user_id = ?";
        try{
            int numberOfRows = jdbcTemplate.update(sql, userId);
            if(numberOfRows == 0 ){
                throw new DaoException("no rows affected");
            }else{
                success = true;
            }
        } catch (CannotGetJdbcConnectionException e){
        throw new DaoException( "cannot connect to server or database", e);
    } catch (DataIntegrityViolationException e){
        throw new DaoException("data integrity violation", e);
    }
        return success;
    }

    private gameUser mapRowToGameUser(SqlRowSet results) {
        gameUser gameUser = new gameUser();
        gameUser.setGameUserId((results.getInt("game_user_id")));
        gameUser.setGame_id((results.getInt("game_id")));
        gameUser.setUser_id((results.getInt("user_id")));
        gameUser.setAvailableBalance((results.getBigDecimal("available_balance")));
        gameUser.setTotalBalance((results.getBigDecimal("total_balance")));
        return gameUser;
    }
}
