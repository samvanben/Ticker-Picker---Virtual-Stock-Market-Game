package com.techelevator.dao;

import com.techelevator.model.Stock;
import com.techelevator.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface StockDao {
    List<Stock> getAllStocks();
    List<Stock> getStocksByOneUserOfGame(int userId, int gameId);
    int createStock(Stock stock);
    BigDecimal getStockPriceBySymbol(String symbol);
}
