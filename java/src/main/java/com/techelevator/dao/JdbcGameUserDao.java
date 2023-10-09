package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGameUserDao implements GameUserDao{
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcGameUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<GameUser> getPlayerByGameId(int gameId) {
        List<GameUser> GameUsers = new ArrayList<>();
        String sql = "SELECT * FROM game_user WHERE game_id = ? ;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameId);
            while (results.next()) {
                GameUser gameUser = mapRowToGameUser(results);
                GameUsers.add(gameUser);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return GameUsers;
    }

    @Override
    public int createGameUser(GameUser gameUserToCreate) {
        int gameUserId = 0;
        String sql = "INSERT INTO game_user (game_id, user_id) VALUES (?, ?)" +
                " RETURNING game_user_id; ";
        try{
            gameUserId = jdbcTemplate.queryForObject(sql, int.class, gameUserToCreate.getGameId(), gameUserToCreate.getUserId());
            gameUserToCreate.setGameUserId(gameUserId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return gameUserId;
    }

    private GameUser mapRowToGameUser(SqlRowSet results) {
        GameUser gameUser = new GameUser();
        gameUser.setGameUserId((results.getInt("game_user_id")));
        gameUser.setGameId((results.getInt("game_id")));
        gameUser.setUserId((results.getInt("user_id")));
        gameUser.setAvailableBalance((results.getBigDecimal("available_balance")));
        gameUser.setTotalBalance((results.getBigDecimal("total_balance")));
        return gameUser;
    }
}
