package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Stock {
    private int stockId;
    @NotEmpty
    private String symbol;
    private BigDecimal askPrice;
    private BigDecimal bidPrice;
    @NotEmpty
    private String companyName;
    private String description;
    private String CEO;

    public Stock() {
    }

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public Stock(String symbol, BigDecimal bidPrice) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
    }

    public Stock(int stockId, String symbol, BigDecimal askPrice, BigDecimal bidPrice, String companyName, String description, String CEO) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.companyName = companyName;
        this.description = description;
        this.CEO = CEO;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(BigDecimal askPrice) {
        this.askPrice = askPrice;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", symbol='" + symbol + '\'' +
                ", askPrice=" + askPrice +
                ", bidPrice=" + bidPrice +
                ", companyName='" + companyName + '\'' +
                ", description='" + description + '\'' +
                ", CEO='" + CEO + '\'' +
                '}';
    }
}
