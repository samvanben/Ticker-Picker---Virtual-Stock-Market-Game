package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
public class JdbcGameDao implements GameDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcGameDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getPlayersByGameId(int gameId) {
        List<User> players = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_id in "
                + "(SELECT user_id FROM game_user WHERE game_id = ?)";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
            while (results.next()) {
                User user = mapRowToUser(results);
                players.add(user);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return players;
    }

    @Override
    public Map<String, BigDecimal> getOrderedPlayersByGameId(int gameId) {
        Map<String, BigDecimal> orderedMap = new LinkedHashMap<>();
        String sql = "SELECT total_balance, username FROM game_user JOIN users ON users.user_id=game_user.user_id WHERE game_id = ? ORDER BY total_balance desc; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql);
            while (SqlRowSet.next()){
                BigDecimal totalBalance = SqlRowSet.getBigDecimal("total_balance");
                String username = SqlRowSet.getNString("username");
                orderedMap.put(username, totalBalance);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return orderedMap;
    }

    @Override
    public List<Game> getGamesByUserId(int userId) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game WHERE game_id in "
                + "(SELECT game_id FROM game_user WHERE user_id = ?)";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Game game = mapRowToGame(results);
                games.add(game);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return games;
    }

    @Override
    public Game getGameByGameId(int gameId) {
        Game game = new Game();
        String sql = "SELECT * FROM game WHERE game_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
            if(results.next()) {
                game = mapRowToGame(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return game;
    }

    @Override
    public int createGame(Game gameToCreate) {
        int gameId = 0;
        String sql = "INSERT INTO game (name_of_game, game_start_date, game_end_date, owner_name, is_real_game) VALUES (?, ?, ?, ?, ?) RETURNING game_id";
        try{
            gameId = jdbcTemplate.queryForObject(sql, int.class, gameToCreate.getNameOfGame(), gameToCreate.getStartDate(),
                    gameToCreate.getEndDate(), gameToCreate.getOwnerName(), gameToCreate.isRealGame());
            gameToCreate.setGameId(gameId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return gameId;
    }

    @Override
    public List<Game> getAllGames() {
        return null;
    }

    @Override
    public BigDecimal getGameUserAvailableBalance(int gameId, int userId) {
        String sql = "SELECT available_balance FROM game_user WHERE game_id=? AND user_id=?; ";
        BigDecimal availableBalance;
        try {
            availableBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, gameId, userId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return availableBalance;
    }

    @Override
    public BigDecimal getGameUserTotalBalance(int gameId, int userId) {
        String sql = "SELECT total_balance FROM game_user WHERE game_id=? AND user_id=?; ";
        BigDecimal totalBalance;
        try {
            totalBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, gameId, userId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return totalBalance;
    }

    @Override
    public boolean subtractFromGameUserAvailableBalance(double amount, int gameId, int userId) {
        boolean success = false;
        String sql = "UPDATE game_user SET available_balance = available_balance - ? WHERE game_id=? AND user_id=?; ";
        try {
            int numberOfRows = jdbcTemplate.update(sql, amount, gameId, userId);
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
    public boolean addToGameUserAvailableBalance(double amount, int gameId, int userId) {
        boolean success = false;
        String sql = "UPDATE game_user SET available_balance = available_balance + ? WHERE game_id=? AND user_id=?; ";
        try {
            int numberOfRows = jdbcTemplate.update(sql, amount, gameId, userId);
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
    public boolean subtractFromGameUserTotalBalance(double amount, int gameId, int userId) {
        boolean success = false;
        String sql = "UPDATE game_user SET total_balance = total_balance - ? WHERE game_id=? AND user_id=?; ";
        try {
            int numberOfRows = jdbcTemplate.update(sql, amount, gameId, userId);
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
    public boolean addToFromGameUserTotalBalance(double amount, int gameId, int userId) {
        boolean success = false;
        String sql = "UPDATE game_user SET total_balance = total_balance + ? WHERE game_id=? AND user_id=?; ";
        try {
            int numberOfRows = jdbcTemplate.update(sql, amount, gameId, userId);
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
    public boolean addPlayerToGame(List<User> users, int gameId) {
        boolean success = false;
        String sql = "INSERT INTO game_user (game_id, user_id) VALUES (?, ?)";
        for(User user: users){
            try{
                int numberOfRows = jdbcTemplate.update(sql, user.getId(), gameId);
                if (numberOfRows == 0 ){
                    throw new DaoException("Zero rows affected, expected at least one");
                }
                success = true;
            } catch (CannotGetJdbcConnectionException e){
                throw new DaoException( "cannot connect to server or database", e);
            } catch (DataIntegrityViolationException e){
                throw new DaoException("data integrity violation", e);
            }
        }
        return success;
    }

    @Override
    public Game updateGame(Game updatedGame, int gameId) {
        Game newGame = null;
        boolean success = false;
        String sql = "UPDATE game SET(name_of_game = ?, game_start_date = ?, game-end_date = ?, owner_name = ?, is_real_game = ?) WHERE game_id = ?;";
        try{
            int numberOfRow = jdbcTemplate.update(sql, updatedGame.getNameOfGame(), updatedGame.getStartDate(), updatedGame.getEndDate(), updatedGame.getOwnerName(), updatedGame.isRealGame(), gameId);
            if(numberOfRow == 0){
                throw new DaoException("No rows affected");
            }else{
                newGame = getGameByGameId(gameId);
                success = true;
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("No access");
        }catch(DataIntegrityViolationException e){
            throw new DaoException("dtae integrity violation");
        }



        return newGame;

    }

    @Override
    public int deleteGame(int gameId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM game WHERE game_id = ?;";
        try {
            numberOfRows = jdbcTemplate.update(sql, gameId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public BigDecimal getAvailableBalanceByUserGame(int userId, int gameId) {
        return null;
    }

    @Override
    public boolean changeGameOwner(int gameId, int userId) {
        boolean success = false;
        String sql = "UPDATE game SET owner_name = (SELECT username FROM users WHERE user_id=? ) WHERE game_id=? ;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, userId, gameId);
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

    private Game mapRowToGame(SqlRowSet results) {
        Game game = new Game();
        game.setGameId((results.getInt("game_id")));
        game.setNameOfGame(results.getString("name_of_game"));
        game.setStartDate(results.getDate("game_start_date").toLocalDate());
        game.setEndDate(results.getDate("game_end_date").toLocalDate());
        game.setOwnerName(results.getString("owner_name"));
        game.setRealGame(results.getBoolean("is_real_game"));
        return game;
    }

    private User mapRowToUser(SqlRowSet results) {
        User user = new User();
        user.setId(results.getInt("user_id"));
        user.setUsername(results.getString("username"));
        user.setPassword(results.getString("password_hash"));
        return user;
    }
}
