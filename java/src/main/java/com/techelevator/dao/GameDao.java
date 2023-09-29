package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.User;
import com.techelevator.model.GameUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GameDao {
    List<Game> getAllGames();
    public Map<String, Integer> getListOfPlayersAvailableToBeAdd(int gameId);
    BigDecimal getGameUserAvailableBalance(int gameId, int userId);
    BigDecimal getGameUserTotalBalance(int gameId, int userId);
    boolean subtractFromGameUserAvailableBalance(BigDecimal amount, int gameId, int userId);
    boolean addToGameUserAvailableBalance(BigDecimal amount, int gameId, int userId);
    boolean subtractFromGameUserTotalBalance(double amount, int gameId, int userId);
    boolean addToFromGameUserTotalBalance(double amount, int gameId, int userId);
    boolean addPlayerToGame(List<User> users, int gameId);
    List<GameUser> getPlayersByGameId(int gameId);
    Map<String, BigDecimal> orderGameMembersByTotalBalanceByGameId(int gameId);
    List<Game> getGamesByUserId(int userId);
    Game getGameByGameId(int gameId);
    BigDecimal getAvailableBalanceByUserGame(int userId, int gameId);
    boolean changeGameOwner(int gameId, int userId);
    int createGame(Game game);
    Game updateGame(Game updatedGame, int gameId);
    int deleteGame(int gameId);
}
