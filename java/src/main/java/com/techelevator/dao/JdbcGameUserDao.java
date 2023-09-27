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

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcGameUserDao implements gameUserDao{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcGameUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<gameUser> getGameUserByGameId(int gameId) {
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
        String sql = "INSERT INTO gameUser (game_id, user_id, available_balance, total_balance) " +
                "VALUES (?, ?, ?, ?);";
        try{
            gameUserId = jdbcTemplate.queryForObject(sql, int.class, gameUserToCreate.getGame_id(), gameUserToCreate.getUser_id(), gameUserToCreate.getAvailableBalance(), gameUserToCreate.getTotalBalance());
            gameUserToCreate.setGameUserId(gameUserId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return gameUserId;
    }

    @Override
    public boolean updateGameUser(gameUser updatedGameUser, int gameUserId) {
        boolean success = false;
        String sql = "UPDATE gameUser SET game_id = ?, user_id = ?, available_balance = ?, total_balance = ? WHERE game_user_id = ?;";
        try{
            int numberOfRows = jdbcTemplate.update(sql, updatedGameUser.getGame_id(), updatedGameUser.getUser_id(), updatedGameUser.getAvailableBalance(), updatedGameUser.getTotalBalance(), gameUserId);
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

    @Override
    public boolean deleteGameUser(int gameUserId) {
        boolean success = false;
        String sql = "Delete * from game_user where game_user_id = ?";
        try{
            int numberOfRows = jdbcTemplate.update(sql, gameUserId);
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
