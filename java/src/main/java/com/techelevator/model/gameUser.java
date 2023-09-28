package com.techelevator.model;

import java.math.BigDecimal;

public class gameUser {
    private int gameUserId=0;
    private int game_id;
    private int user_id;
    private BigDecimal availableBalance;
    private BigDecimal totalBalance;
    public gameUser() {
    }
    public gameUser(int game_id, int user_id, BigDecimal availableBalance, BigDecimal totalBalance) {
        this.game_id = game_id;
        this.user_id = user_id;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }
    public gameUser(int gameUserId, int game_id, int user_id, BigDecimal availableBalance, BigDecimal totalBalance) {
        this.gameUserId = gameUserId;
        this.game_id = game_id;
        this.user_id = user_id;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }

    public int getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(int gameUserId) {
        this.gameUserId = gameUserId;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
        return "gameUser{" +
                "gameUserId=" + gameUserId +
                ", game_id=" + game_id +
                ", user_id=" + user_id +
                ", availableBalance=" + availableBalance +
                ", totalBalance=" + totalBalance +
                '}';
    }
}
