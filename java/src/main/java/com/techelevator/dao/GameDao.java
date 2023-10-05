package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.User;
import com.techelevator.model.GameUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GameDao {
    List<Game> getAllGames();
    public List<User> getListOfPlayersAvailableToBeAdd(int gameId);
    BigDecimal getGameUserAvailableBalance(int gameId, int userId);
    BigDecimal getGameUserTotalBalance(int gameId, int userId);
    boolean subtractFromGameUserAvailableBalance(BigDecimal amount, int gameId, int userId);
    boolean addToGameUserAvailableBalance(BigDecimal amount, int gameId, int userId);
    boolean subtractFromGameUserTotalBalance(BigDecimal amount, int gameId, int userId);
    boolean addToFromGameUserTotalBalance(BigDecimal amount, int gameId, int userId);
    boolean addPlayerToGame(List<User> users, int gameId);
//    Map<String, BigDecimal> orderGameMembersByTotalBalanceByGameId(int gameId);
    List<GameUser> orderGameMembersByTotalBalanceByGameId(int gameId);
    List<GameUser> orderGameMembersByAvailableBalanceByGameId(int gameId);
    List<Game> getGamesByUserId(int userId);
    Game getGameByGameId(int gameId);
    BigDecimal getAvailableBalanceByUserGame(int userId, int gameId);
    boolean changeGameOwner(int gameId, int userId);
    int createGame(Game game);
    Game updateGame(Game updatedGame, int gameId);
    int deleteGame(int gameId);

    boolean setGameStatusToFalse(int gameId);
    List<Game> getActiveGamesByUserId(int userId);
    public List<Game> getEndedGamesByUserId(int userId);
}
