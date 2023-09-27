package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transaction {
    private int transactionId;
    @NotEmpty
    private int stockId;
    @NotEmpty
    private int gameId;
    @NotEmpty
    private int userId;
    @NotEmpty
    private BigDecimal price;
    @NotEmpty
    private String transactionType;
    @NotEmpty
    private int numberOfShares;
    private BigDecimal totalBalance;
    private BigDecimal availableBalance;

    public Transaction() {

    }

    public Transaction(int transactionId, int stockId, int gameId, int userId, BigDecimal price, String transactionType, int numberOfShares, BigDecimal totalBalance, BigDecimal availableBalance) {
        this.transactionId = transactionId;
        this.stockId = stockId;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;
        this.transactionType = transactionType;
        this.numberOfShares = numberOfShares;
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
    }

    public Transaction(int stockId, int gameId, int userId, BigDecimal price, String transactionType, int numberOfShares, BigDecimal totalBalance, BigDecimal availableBalance) {
        this.stockId = stockId;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;
        this.transactionType = transactionType;
        this.numberOfShares = numberOfShares;
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getGameId() {
        return gameId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

}
