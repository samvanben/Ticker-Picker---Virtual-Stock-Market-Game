package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transaction {
    private int transactionId;
    private String symbol;
    private int gameId;
    private int userId;
    private BigDecimal price;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity = 0;

    public Transaction() {
    }

    public Transaction(int transactionId, String symbol, int gameId, int userId, BigDecimal price) {
        this.transactionId = transactionId;
        this.symbol = symbol;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;

    }

    public Transaction(String symbol, int gameId, int userId, BigDecimal price) {
        this.symbol = symbol;
        this.gameId = gameId;
        this.userId = userId;
        this.price = price;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", gameId=" + gameId +
                ", userId=" + userId +
                ", price=" + price +
                '}';
    }
}
