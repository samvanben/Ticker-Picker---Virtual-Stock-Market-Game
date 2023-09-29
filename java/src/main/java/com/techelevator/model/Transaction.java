package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transaction {
    private int transactionId;
    @NotEmpty(message = "Please enter stock")
    private int stockId;
    @NotEmpty(message = "Please enter game")
    private int gameId;
    @NotEmpty(message = "Please enter user")
    private int userId;
    @NotEmpty(message = "Please enter price")
    private BigDecimal price;
    @NotEmpty(message = "Please enter transaction type")
    private String transactionType;
    @NotEmpty(message = "Please enter number of shares for execution")
    private int numberOfShares;

    public Transaction() {

    }

    public Transaction(int transactionId, int stockId, int gameId, int userId, BigDecimal price, String transactionType, int numberOfShares) {
        this.transactionId = transactionId;
        this.stockId = stockId;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;
        this.transactionType = transactionType;
        this.numberOfShares = numberOfShares;
    }

    public Transaction(int stockId, int gameId, int userId, BigDecimal price, String transactionType, int numberOfShares) {
        this.stockId = stockId;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;
        this.transactionType = transactionType;
        this.numberOfShares = numberOfShares;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", stockId=" + stockId +
                ", gameId=" + gameId +
                ", userId=" + userId +
                ", price=" + price +
                ", transactionType='" + transactionType + '\'' +
                ", numberOfShares=" + numberOfShares +
                '}';
    }
}
