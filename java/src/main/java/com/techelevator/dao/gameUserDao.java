package com.techelevator.dao;

import com.techelevator.model.gameUser;

import java.util.List;

public interface gameUserDao {

    List<gameUser> getGameUserByGameId(int gameId);

    int createGameUser(gameUser GameUserToCreate);
    boolean updateGameUser(gameUser updatedGameUser, int gameUserId);
    boolean deleteGameUser(int gameUserId);
}
