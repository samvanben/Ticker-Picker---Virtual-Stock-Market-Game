package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transaction {
    @NotEmpty
    private int transactionId;
    @NotEmpty
    private int stockId;
    @NotEmpty
    private int gameId;
    @NotEmpty
    private int id;
    @NotEmpty
    private BigDecimal price;
    @NotEmpty
    private String buyOrSell;
    @NotEmpty
    private int numberOfShares;
    @NotEmpty
    private BigDecimal totalBalance;
    @NotEmpty
    private BigDecimal availableBalance;
    @NotEmpty
    private int stock;

    public Transaction() {

    }

    public Transaction(int transactionId, int stockId, int gameId, int id, BigDecimal price, String buyOrSell, int numberOfShares, BigDecimal totalBalance, BigDecimal availableBalance, int stock) {
        this.transactionId = transactionId;
        this.stockId = stockId;
        this.gameId = gameId;
        this.id = id;
        this.price = price;
        this.buyOrSell = buyOrSell;
        this.numberOfShares = numberOfShares;
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
        this.stock = stock;
    }

    public Transaction(int stockId, int gameId, int id, BigDecimal price, String buyOrSell, int numberOfShares, BigDecimal totalBalance, BigDecimal availableBalance, int stock) {
        this.stockId = stockId;
        this.gameId = gameId;
        this.id = id;
        this.price = price;
        this.buyOrSell = buyOrSell;
        this.numberOfShares = numberOfShares;
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
        this.stock = stock;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
