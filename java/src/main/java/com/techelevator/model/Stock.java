package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Stock {
    @NotEmpty
    private int stockId;
    @NotEmpty
    private String symbol;
    @NotEmpty
    private BigDecimal askPrice;
    @NotEmpty
    private BigDecimal bidPrice;
    @NotEmpty
    private String companyName;
    @NotEmpty
    private BigDecimal exchange;
    @NotEmpty
    private String industry;
    @NotEmpty
    private String description;
    @NotEmpty
    private String CEO;

    public Stock() {
    }

    public Stock(int stockId, String symbol, BigDecimal askPrice, BigDecimal bidPrice, String companyName, BigDecimal exchange, String industry, String description, String CEO) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.companyName = companyName;
        this.exchange = exchange;
        this.industry = industry;
        this.description = description;
        this.CEO = CEO;
    }

    public Stock(String symbol, BigDecimal askPrice, BigDecimal bidPrice, String companyName, BigDecimal exchange, String industry, String description, String CEO) {
        this.symbol = symbol;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.companyName = companyName;
        this.exchange = exchange;
        this.industry = industry;
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

    public BigDecimal getExchange() {
        return exchange;
    }

    public void setExchange(BigDecimal exchange) {
        this.exchange = exchange;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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
                ", exchange=" + exchange +
                ", industry='" + industry + '\'' +
                ", description='" + description + '\'' +
                ", CEO='" + CEO + '\'' +
                '}';
    }
}
