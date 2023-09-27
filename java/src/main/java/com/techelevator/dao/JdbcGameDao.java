package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
