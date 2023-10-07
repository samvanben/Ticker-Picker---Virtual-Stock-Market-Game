package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.GameUser;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

@Component
public class JdbcGameDao implements GameDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcGameDao(DataSource dataSource, TransactionDao transactionDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                Game game = mapRowToGame(results);
                games.add(game);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return games;
    }

    @Override
    public List<User> getListOfPlayersAvailableToBeAdd(int gameId) {
        List<User> players = new LinkedList<>();
        String sql = "SELECT * FROM users WHERE user_id NOT IN (SELECT user_id FROM game_user WHERE game_id=?); ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
            while(results.next()) {
                User user = mapRowToUser(results);
                players.add(user);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return players;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }

    @Override
    public BigDecimal getAvailableBalanceByUserGame(int userId, int gameId) {
        BigDecimal bigDecimal = new BigDecimal(0);
        String sql = "SELECT available_balance FROM game_user WHERE user_id = ? AND game_id = ?; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, gameId);
            if(results.next()) {
                bigDecimal = results.getBigDecimal("available_balance");
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return bigDecimal;
    }

    @Override
    public List<GameUser> orderGameMembersByTotalBalanceByGameId(int gameId) {
        List<GameUser> orderedList = new LinkedList<>();
        String sql = "SELECT users.username, available_balance, total_balance FROM game_user JOIN users ON users.user_id = game_user.user_id WHERE game_id = ? ORDER BY total_balance desc; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, gameId);
            while (SqlRowSet.next()){
                GameUser gameUser = mapRowToGameUserPartially(SqlRowSet);
                orderedList.add(gameUser);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return orderedList;
    }

    @Override
    public List<GameUser> orderGameMembersByAvailableBalanceByGameId(int gameId) {
        List<GameUser> orderedList = new LinkedList<>();
        String sql = "SELECT users.username, available_balance, total_balance FROM game_user JOIN users ON users.user_id = game_user.user_id WHERE game_id = ? ORDER BY available_balance desc; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, gameId);
            while (SqlRowSet.next()){
                GameUser gameUser = mapRowToGameUserPartially(SqlRowSet);
                orderedList.add(gameUser);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return orderedList;
    }

    private GameUser mapRowToGameUserPartially(SqlRowSet results) {
        GameUser gameUser = new GameUser();
        gameUser.setUsername((results.getString("username")));
        gameUser.setAvailableBalance((results.getBigDecimal("available_balance")));
        gameUser.setTotalBalance((results.getBigDecimal("total_balance")));
        return gameUser;
    }

    @Override
    public List<Game> getGamesByUserId(int userId) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT game_user.game_id, name_of_game, game_start_date, game_end_date, owner_name, is_current_game, " +
                "game_user.available_balance, game_user.total_balance, game_user.user_id FROM game " +
                "JOIN game_user ON game_user.game_id = game.game_id " +
                "WHERE game_user.game_id in (SELECT game_id FROM game_user WHERE user_id = ?) AND game_user.user_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while (results.next()) {
                Game game = mapRowToGameAddBalance(results);
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
        int playerId = 0;
        String sql = "INSERT INTO game (name_of_game, owner_name, game_end_date) VALUES (?, ?, ?) RETURNING game_id";
        String addOwnerToGameSql = "INSERT INTO game_user(game_id, user_id) VALUES (?, ?) RETURNING game_user_id; ";
        String getUserIdSql = "SELECT user_id FROM users WHERE username=? ";
        try{
            // create a game on database
            int gameId = jdbcTemplate.queryForObject(sql, int.class, gameToCreate.getNameOfGame(), gameToCreate.getOwnerName(), gameToCreate.getEndDate());
            gameToCreate.setGameId(gameId);

            // get creator userId from database
            int userId = 0;
            SqlRowSet results = jdbcTemplate.queryForRowSet(getUserIdSql, gameToCreate.getOwnerName());
            if(results.next()) {
                userId = results.getInt("user_id");
            }

            // add owner to the game
            playerId = jdbcTemplate.queryForObject(addOwnerToGameSql, int.class, gameId, userId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return playerId;
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
    public boolean subtractFromGameUserAvailableBalance(BigDecimal amount, int gameId, int userId) {
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
    public boolean addToGameUserAvailableBalance(BigDecimal amount, int gameId, int userId) {
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
    public boolean subtractFromGameUserTotalBalance(BigDecimal amount, int gameId, int userId) {
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
    public boolean addToFromGameUserTotalBalance(BigDecimal amount, int gameId, int userId) {
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
        String sql = "UPDATE game SET(name_of_game = ?, game_start_date = ?, game-end_date = ?, owner_name = ?) WHERE game_id = ?;";
        try{
            int numberOfRow = jdbcTemplate.update(sql, updatedGame.getNameOfGame(), updatedGame.getStartDate(), updatedGame.getEndDate(), updatedGame.getOwnerName(), gameId);
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

        if (results.getDate("game_start_date") != null) {
            game.setStartDate(results.getDate("game_start_date").toLocalDate());
        }
        if (results.getDate("game_end_date") != null) {
            game.setEndDate(results.getDate("game_end_date").toLocalDate());
        }
        game.setOwnerName(results.getString("owner_name"));
        return game;
    }

    private Game mapRowToGameAddBalance(SqlRowSet results) {
        Game game = new Game();
        game.setGameId((results.getInt("game_id")));
        game.setNameOfGame(results.getString("name_of_game"));

        if (results.getDate("game_start_date") != null) {
            game.setStartDate(results.getDate("game_start_date").toLocalDate());
        }
        if (results.getDate("game_end_date") != null) {
            game.setEndDate(results.getDate("game_end_date").toLocalDate());
        }
        game.setOwnerName(results.getString("owner_name"));
        game.setAvailableBalance(results.getBigDecimal("available_balance"));
        game.setTotalBalance(results.getBigDecimal("total_balance"));
        game.setUserId(results.getInt("user_id"));
        return game;
    }

    public boolean setGameStatusToFalse(int gameId){
        int numberOfRow = 0;
        String sql = "UPDATE game SET is_current_game = false WHERE game_id = ?;";
        try{
            numberOfRow = jdbcTemplate.update(sql, gameId);
            if(numberOfRow == 0){
                throw new DaoException("No rows affected");
            }
            return numberOfRow==0 ? false : true;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("No access");
        }catch(DataIntegrityViolationException e){
            throw new DaoException("data integrity violation");
        }
    }

    @Override
    public List<Game> getActiveGamesByUserId(int userId) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT game_user.game_id, name_of_game, game_start_date, game_end_date, owner_name, is_current_game, " +
                "game_user.available_balance, game_user.total_balance, game_user.user_id FROM game " +
                "JOIN game_user ON game_user.game_id = game.game_id " +
                "WHERE game_user.game_id in (SELECT game_id FROM game_user WHERE user_id = ?) AND game_user.user_id = ? AND is_current_game = true; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while (results.next()) {
                Game game = mapRowToGameAddBalance(results);
                games.add(game);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return games;
    }

    @Override
    public List<Game> getEndedGamesByUserId(int userId) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT game_user.game_id, name_of_game, game_start_date, game_end_date, owner_name, is_current_game, " +
                "game_user.available_balance, game_user.total_balance, game_user.user_id FROM game " +
                "JOIN game_user ON game_user.game_id = game.game_id " +
                "WHERE game_user.game_id in (SELECT game_id FROM game_user WHERE user_id = ?) AND game_user.user_id = ? AND is_current_game = false; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while (results.next()) {
                Game game = mapRowToGameAddBalance(results);
                games.add(game);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return games;
    }
}
