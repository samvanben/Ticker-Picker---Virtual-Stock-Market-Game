package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.Stock;
import com.techelevator.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface GameDao {

    boolean addPlayerToGame(List<User> users, int gameId);
    List<User> getPlayersByGameId(int gameId);
    List<Game> getGamesByUserId(int userId);
    Game getGameByGameId(int gameId);
    BigDecimal getAvailableBalanceByUserGame(int userId, int gameId);
    int createGame(Game gameToCreate);
    boolean updateGame(Game updatedGame, int gameId);
    boolean deleteGame(int gameId);
}
