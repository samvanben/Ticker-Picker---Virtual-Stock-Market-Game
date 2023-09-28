package com.techelevator.dao;

import com.techelevator.model.gameUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface gameUserDao {

    List<gameUser> getPlayerByGameId(int gameId);
    int createGameUser(gameUser GameUserToCreate);
    boolean updateGameUser(gameUser updatedGameUser, int gameUserId);
    boolean deleteGameUser(int gameUserId);
}
