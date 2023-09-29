package com.techelevator.dao;

import com.techelevator.model.GameUser;

import java.util.List;

public interface GameUserDao {

    List<GameUser> getPlayerByGameId(int gameId);
    int createGameUser(GameUser gameUserToCreate);
    boolean updateGameUser(GameUser updatedGameUser, int gameUserId);
    boolean deleteGameUser(int gameUserId);
}
