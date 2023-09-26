package com.techelevator.dao;

import com.techelevator.model.Game;
import com.techelevator.model.User;

import java.time.LocalDate;
import java.util.List;

public interface GameDao {
    int createGame(Game gameToCreate);
    boolean addPlayerToGame(List<User> users, int gameId);
    List<User> getPlayersByGameId(int gameId);
    List<Game> getGamesByUserId(int userId);
    Game getGameByGameId(int gameId);
}
