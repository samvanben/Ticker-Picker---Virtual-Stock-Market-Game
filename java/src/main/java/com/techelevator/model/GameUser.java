package com.techelevator.model;

import java.math.BigDecimal;

public class GameUser {

    private int gameUserId;

    private int gameId;

    private int userId;

    private BigDecimal availableBalance;

    private BigDecimal totalBalance;
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public GameUser() {
    }
    public GameUser(int gameId, int userId, BigDecimal availableBalance, BigDecimal totalBalance) {
        this.gameId = gameId;
        this.userId = userId;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }
    public GameUser(int gameUserId, int gameId, int userId, BigDecimal availableBalance, BigDecimal totalBalance) {
        this.gameUserId = gameUserId;
        this.gameId = gameId;
        this.userId = userId;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }

    public int getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(int gameUserId) {
        this.gameUserId = gameUserId;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "GameUser{" +
                "gameUserId=" + gameUserId +
                ", game_id=" + userId +
                ", user_id=" + userId +
                ", availableBalance=" + availableBalance +
                ", totalBalance=" + totalBalance +
                '}';
    }
}
