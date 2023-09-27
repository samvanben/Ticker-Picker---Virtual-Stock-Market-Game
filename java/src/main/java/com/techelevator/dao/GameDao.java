package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.Stock;
import com.techelevator.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GameDao {
    List<Game> getAllGames();
    BigDecimal getGameUserAvailableBalance(int gameId, int userId);
    BigDecimal getGameUserTotalBalance(int gameId, int userId);
    boolean subtractFromGameUserAvailableBalance(double amount, int gameId, int userId);
    boolean addToGameUserAvailableBalance(double amount, int gameId, int userId);
    boolean subtractFromGameUserTotalBalance(double amount, int gameId, int userId);
    boolean addToFromGameUserTotalBalance(double amount, int gameId, int userId);
    boolean addPlayerToGame(List<User> users, int gameId);
    List<User> getPlayersByGameId(int gameId);
    Map<String, BigDecimal> getOrderedPlayersByGameId(int gameId);
    List<Game> getGamesByUserId(int userId);
    Game getGameByGameId(int gameId);
    BigDecimal getAvailableBalanceByUserGame(int userId, int gameId);
    boolean changeGameOwner(int gameId, int userId);
    int createGame(Game gameToCreate);
    Game updateGame(Game updatedGame, int gameId);
    int deleteGame(int gameId);
}
